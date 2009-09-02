package com.objogate.wl.swing.matcher;

import javax.swing.AbstractButton;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class AbstractButtonTextMatcher<T extends AbstractButton> extends TypeSafeDiagnosingMatcher<T> {
    private Matcher<? super String> equalToText;

    public AbstractButtonTextMatcher(String text) {
        this.equalToText = Matchers.equalTo(text);
    }

    @Override
    protected boolean matchesSafely(T buttonAlike, Description mismatchDescription) {
      String value = buttonAlike.getText();
      if (equalToText.matches(value)) {
        return true;
      }
      equalToText.describeMismatch(value, mismatchDescription);
      return false;
    }

    public void describeTo(Description description) {
        description.appendText("with text ").appendDescriptionOf(equalToText);
    }
}
