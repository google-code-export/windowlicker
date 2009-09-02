package com.objogate.wl.swing.driver.tests;

import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import static com.objogate.wl.swing.probe.ComponentIdentity.selectorFor;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;

import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.matcher.IterableComponentsMatcher;

public class JTableDriverRowMatchingTest extends AbstractComponentDriverTest<JTableDriver> {
    public final JTable table = makeSmallTable();
    public static final NamedColor BLACK = NamedColor.color("BLACK");
    public static final NamedColor WHITE = NamedColor.color("WHITE");
    public static final NamedColor YELLOW = NamedColor.color("YELLOW");

    @Before public void setUp() throws Exception {
      table.setRowHeight(table.getRowHeight() + 10);
      driver = createDriverFor(table);
    }

    private JTableDriver createDriverFor(JTable drivenTable) {
      JScrollPane pane = new JScrollPane(drivenTable);
      pane.setPreferredSize(new Dimension(800, 600));
      view(pane);

      return new JTableDriver(gesturePerformer, selectorFor(drivenTable), prober);
    }

    @SuppressWarnings("unchecked")
    @Test public void
    detectsHasRowMatching() {
      driver.hasRow(IterableComponentsMatcher.matching(withLabelText("2x1"), withLabelText("2x2"), withLabelText("2x3"))); 
    }

    
    @SuppressWarnings("unchecked")
    @Test public void
    reportsWhenDoesNotHaveRowMatching() {
      prober.setTimeout(300);
      try {
        driver.hasRow(IterableComponentsMatcher.matching(withLabelText("2x1"), withLabelText("1x2"), withLabelText("2x3")));
      } catch (AssertionError expected) {
        assertThat(expected.getMessage(), 
                   containsString("row with cells <label with text \"2x1\">, <label with text \"1x2\">, <label with text \"2x3\">"));
        return;
      }
      fail("Should have failed");
    }
    
    public static JTable makeSmallTable() {
      return new JTable(
          new Object[][] { new Object[] { "1x1", "1x2", "1x3" },
                           new Object[] { "2x1", "2x2", "2x3" } },
          new Object[] { "one", "two", "three" } );
    }
}
