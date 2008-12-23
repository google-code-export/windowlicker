package com.objogate.wl.swing.matcher;

import java.util.Collection;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class EmptyCollectionMatcher {

    @SuppressWarnings("unchecked")
    @Factory
    public static <C extends Collection<?>> Matcher<C> isEmpty() {
        return (Matcher<C>) Matchers.empty();
    }
}
