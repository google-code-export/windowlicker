/**
 * Matches an iterable collection of Components against an array of Matchers.
 * Each Matcher has to match the component at its position in the iteration. 
 */
package com.objogate.wl.swing.matcher;

import java.awt.Component;
import java.util.Iterator;

import javax.swing.JComponent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public final class IterableComponentsMatcher extends TypeSafeDiagnosingMatcher<Iterable<? extends Component>> {
  private final Matcher<? extends JComponent>[] matchers;

  public IterableComponentsMatcher(Matcher<? extends JComponent>[] matchers) {
    this.matchers = matchers;
  }

  @Override
  protected boolean matchesSafely(Iterable<? extends Component> components, Description mismatchDescription) {
    Iterator<? extends Component> iterator = components.iterator();
    int componentIx = 0;
    for (Matcher<? extends JComponent> matcher : matchers) {
      if (!iterator.hasNext()) {
        mismatchDescription.appendText("no component that ").appendDescriptionOf(matcher);
        return false;
      }
      Component component = iterator.next();
      if (! matcher.matches(component)) {
        mismatchDescription.appendText("component " + componentIx + " " );
        matcher.describeMismatch(component, mismatchDescription);
        return false;
      }
      componentIx++;
    }
    if (iterator.hasNext()) {
      mismatchDescription.appendText("extra component: ").appendValue(iterator.next());
      return false;
    }
    return true;
  }

  public void describeTo(Description description) {
    description.appendValueList("with cells ", ", ", "", matchers);
  }

  public static Matcher<Iterable<? extends Component>> matching(final Matcher<? extends JComponent>... matchers) {
    return new IterableComponentsMatcher(matchers);
  }

}