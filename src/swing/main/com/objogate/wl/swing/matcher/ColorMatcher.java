package com.objogate.wl.swing.matcher;

import java.awt.Color;

import org.hamcrest.Description;
import org.hamcrest.core.IsEqual;

public class ColorMatcher extends IsEqual<Color> {
    public ColorMatcher(Color expected) {
      super(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("color matching ");
        super.describeTo(description);
    }
}
