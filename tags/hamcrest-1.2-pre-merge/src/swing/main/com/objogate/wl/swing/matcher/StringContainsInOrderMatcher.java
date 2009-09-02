package com.objogate.wl.swing.matcher;

import static java.util.Arrays.asList;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.text.StringContainsInOrder;


public class StringContainsInOrderMatcher {
    @Factory
    public static Matcher<String> containsInOrder(String... substrings) {
        return containsInOrder(asList(substrings));
    }

    @Factory
    public static Matcher<String> containsInOrder(Iterable<String> substrings) {
        return new StringContainsInOrder(substrings);
    }

}
