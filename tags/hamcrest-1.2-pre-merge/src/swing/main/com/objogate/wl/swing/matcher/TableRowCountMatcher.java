package com.objogate.wl.swing.matcher;

import static org.hamcrest.Matchers.equalTo;

import javax.swing.JTable;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class TableRowCountMatcher extends FeatureMatcher<JTable, Integer> {
    public TableRowCountMatcher(Matcher<? super Integer> rowCountMatcher) {
       super(rowCountMatcher, "table with row count", "row count");
    }

    @Override
    protected Integer featureValueOf(JTable actual) {
      return actual.getRowCount();
    }

    public static Matcher<JTable> withRowCount(int n) {
        return withRowCount(equalTo(n));
    }

    public static Matcher<JTable> withRowCount(Matcher<? super Integer> rowCountMatcher) {
        return new TableRowCountMatcher(rowCountMatcher);
    }

}
