package com.objogate.wl.swing.matcher;

import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class AbstractButtonAcceleratorMatcher<T extends AbstractButton> extends TypeSafeDiagnosingMatcher<T> {
    private int keyCode;

    public AbstractButtonAcceleratorMatcher(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    protected boolean matchesSafely(T buttonAlike, Description mismatchDescription) {
      int mnemonic = buttonAlike.getMnemonic();
      if (mnemonic == keyCode) {
        return true;
      }
      mismatchDescription.appendText("accelerator was ").appendValue(mnemonic);
      return false;
    }

    public void describeTo(Description description) {
        description.appendText("accelerator equals '");
        description.appendValue(KeyEvent.getKeyText(keyCode));
        description.appendText("'");
    }

}
