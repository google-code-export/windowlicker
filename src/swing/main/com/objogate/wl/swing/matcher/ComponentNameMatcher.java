package com.objogate.wl.swing.matcher;

import java.awt.Component;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matchers;

public class ComponentNameMatcher extends FeatureMatcher<Component, String> {
    public ComponentNameMatcher(String name) {
      super(Matchers.equalTo(name), "compenent with name", "name");
    }

    @Override
    protected String featureValueOf(Component actual) {
      return actual.getName();
    }
}
