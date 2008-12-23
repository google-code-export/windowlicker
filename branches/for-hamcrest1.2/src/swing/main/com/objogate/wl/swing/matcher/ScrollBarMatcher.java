package com.objogate.wl.swing.matcher;

import java.awt.Container;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class ScrollBarMatcher extends TypeSafeDiagnosingMatcher<JScrollBar> {
    private final Container parent;
    private final int orientation;

    public ScrollBarMatcher(JScrollPane parent, int orientation) {
        this.parent = parent;
        this.orientation = orientation;
    }

    @Override
    protected boolean matchesSafely(JScrollBar item, Description mismatchDescription) {
      if (parent != item.getParent()) {
        mismatchDescription.appendText("parent was ").appendValue(item.getParent());
        return false;
      }
      if (orientation != item.getOrientation()) {
        mismatchDescription.appendText("orientation was " + item.getOrientation());
        return false;
      }
      return true;
    }

    public void describeTo(Description description) {
        description.appendText("scrollbar within parent ").appendValue(parent)
                   .appendText(", with orientation " + orientation);
    }
}

