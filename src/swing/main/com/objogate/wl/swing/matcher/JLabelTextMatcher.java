package com.objogate.wl.swing.matcher;

import static org.hamcrest.Matchers.equalTo;

import javax.swing.JLabel;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class JLabelTextMatcher extends FeatureMatcher<JLabel, String> {
    public JLabelTextMatcher(Matcher<? super String> matcher) {
      super(matcher, "label with text", "text");
    }

    @Override
    protected String featureValueOf(JLabel label) {
      return label.getText();
    }


    public static JLabelTextMatcher withLabelText(String text) {
      return withLabelText(equalTo(text));
    }

    public static JLabelTextMatcher withLabelText(Matcher<? super String> text) {
        return new JLabelTextMatcher(text);
    }

}
