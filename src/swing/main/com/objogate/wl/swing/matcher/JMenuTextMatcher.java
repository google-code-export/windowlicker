package com.objogate.wl.swing.matcher;

import javax.swing.JMenu;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matchers;

public class JMenuTextMatcher extends FeatureMatcher<JMenu, String> {
  public JMenuTextMatcher(String expectedText) {
    super(Matchers.equalTo(expectedText), "JMenu with text", "text");
  }

  @Override
  protected String featureValueOf(JMenu actual) {
    return actual.getText();
  }
}
