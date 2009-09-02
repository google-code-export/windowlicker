package com.objogate.wl.swing.matcher;

import java.awt.Component;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class ComponentEnabledMatcher extends TypeSafeDiagnosingMatcher<Component> {
    @Override
    protected boolean matchesSafely(Component item, Description mismatchDescription) {
      if (item.isEnabled()) {
        return true; 
      }
      mismatchDescription.appendText("was not enabled");
      return false;
    }
  
    public void describeTo(Description description) {
      description.appendText("enabled");
    }

}
