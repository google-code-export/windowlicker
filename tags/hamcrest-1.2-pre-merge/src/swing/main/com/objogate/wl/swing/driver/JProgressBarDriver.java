package com.objogate.wl.swing.driver;

import java.awt.Component;

import javax.swing.JProgressBar;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import com.objogate.wl.Prober;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class JProgressBarDriver extends ComponentDriver<JProgressBar> {
    public JProgressBarDriver(GesturePerformer gesturePerformer, ComponentSelector<JProgressBar> jProgressBarComponentSelector, Prober prober) {
        super(gesturePerformer, jProgressBarComponentSelector, prober);
    }

    public JProgressBarDriver(ComponentDriver<? extends Component> parentOrOwner, ComponentSelector<JProgressBar> jProgressBarComponentSelector) {
        super(parentOrOwner, jProgressBarComponentSelector);
    }

    public JProgressBarDriver(ComponentDriver<? extends Component> parentOrOwner, Class<JProgressBar> componentType, Matcher<? super JProgressBar>... matchers) {
        super(parentOrOwner, componentType, matchers);
    }

    public void hasMinimum(final Matcher<Integer> matcher) {
        is(new FeatureMatcher<JProgressBar, Integer>(matcher, "progress bar with minimum value", "minimum value") {
          @Override
          protected Integer featureValueOf(JProgressBar actual) {
            return actual.getMinimum();
          }
        });
    }

    public void hasMaximum(final Matcher<Integer> matcher) {
      is(new FeatureMatcher<JProgressBar, Integer>(matcher, "progress bar with maximum value", "maximum value") {
        @Override
        protected Integer featureValueOf(JProgressBar actual) {
          return actual.getMaximum();
        }
      });
    }

    public void hasValue(final Matcher<? super Integer> matcher) {
      is(new FeatureMatcher<JProgressBar, Integer>(matcher, "progress bar with value", "value") {
        @Override
        protected Integer featureValueOf(JProgressBar actual) {
          return actual.getValue();
        }
      });
    }

    public void hasString(final Matcher<String> matcher) {
      is(new FeatureMatcher<JProgressBar, String>(matcher, "progress bar with string", "string") {
        @Override
        protected String featureValueOf(JProgressBar actual) {
          return actual.getString();
        }
      });
    }

    public void hasPercentComplete(final Matcher<Double> matcher) {
      is(new FeatureMatcher<JProgressBar, Double>(matcher, "progress bar with percentage complete", "percentage complete") {
        @Override
        protected Double featureValueOf(JProgressBar actual) {
          return actual.getPercentComplete();
        }
      });
    }
}
