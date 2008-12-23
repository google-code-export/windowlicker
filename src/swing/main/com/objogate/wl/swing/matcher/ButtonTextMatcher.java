package com.objogate.wl.swing.matcher;

import javax.swing.JButton;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matchers;

public class ButtonTextMatcher extends FeatureMatcher<JButton, String> {
    public ButtonTextMatcher(String expectedText) {
        super(Matchers.equalTo(expectedText), "JButton with text", "text");
    }

    @Override
    protected String featureValueOf(JButton actual) {
      return actual.getText();
    }
}
