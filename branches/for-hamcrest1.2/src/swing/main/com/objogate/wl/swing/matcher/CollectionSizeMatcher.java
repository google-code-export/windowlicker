package com.objogate.wl.swing.matcher;

import java.util.Collection;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class CollectionSizeMatcher {
    @Factory
    public static Matcher<? super Collection<? extends Object>> ofSize(Matcher<? super Integer> sizeMatcher) {
        return Matchers.hasSize(sizeMatcher);
    }

    @Factory
    public static Matcher<? super Collection<? extends Object>> ofSize(int expectedSize) {
        return Matchers.hasSize(expectedSize);
    }
}
