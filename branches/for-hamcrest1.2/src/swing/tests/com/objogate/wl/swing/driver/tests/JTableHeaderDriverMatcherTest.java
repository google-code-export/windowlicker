package com.objogate.wl.swing.driver.tests;

import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import static com.objogate.wl.swing.probe.ComponentIdentity.selectorFor;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import com.objogate.wl.swing.driver.JTableHeaderDriver;

public class  JTableHeaderDriverMatcherTest extends AbstractComponentDriverTest<JTableHeaderDriver> {
    private JTable table = JTableDriverRowMatchingTest.makeSmallTable();

    @Before
    public void setUp() throws Exception {
        view(new JScrollPane(table));
        driver = driverFor(table);
    }
    
    @SuppressWarnings("unchecked")
    @Test public void
    detectsHasHeadersMatching() {
      driver.hasHeaders(matching(withLabelText("one"), withLabelText("two"), withLabelText("three"))); 
    }

    @SuppressWarnings("unchecked")
    @Test public void
    reportsTooManyHeaders() {
      reportsWrongHeaders(matching(withLabelText("one"), withLabelText("two")),
                         "because extra component",
                          "headers with cells <label with text \"one\">, <label with text \"two\">");
    }

    @SuppressWarnings("unchecked")
    @Test public void
    reportsTooFewHeaders() {
      reportsWrongHeaders(matching(withLabelText("one"), withLabelText("two"), withLabelText("three"), withLabelText("four")),
                          "because no component is label with text \"four\"",
                          "headers with cells <label with text \"one\">, <label with text \"two\">, <label with text \"three\">, <label with text \"four\">");
    }

    @SuppressWarnings("unchecked")
    @Test public void
    reportsMismatchedHeaders() {
      reportsWrongHeaders(matching(withLabelText("one"), withLabelText("three"), withLabelText("two")), 
                          "because component 1 text was \"two\"",
                          "headers with cells <label with text \"one\">, <label with text \"three\">, <label with text \"two\">");
    }

    private void reportsWrongHeaders(Matcher<Iterable<? extends Component>> matchers, String... expectedMessages) {
      prober.setTimeout(300);
      try {
        driver.hasHeaders(matchers); 
      } catch (AssertionError expected) {
        for (String expectedMessage : expectedMessages) {
          assertThat(expected.getMessage(), containsString(expectedMessage));
        }
        return;
      }
      fail("Should have failed");
    }

    private JTableHeaderDriver driverFor(JTable drivenTable) {
      JScrollPane pane = new JScrollPane(drivenTable);
      pane.setPreferredSize(new Dimension(800, 600));
      view(pane);

      return new JTableHeaderDriver(gesturePerformer, selectorFor(drivenTable.getTableHeader()), prober);
    }
}