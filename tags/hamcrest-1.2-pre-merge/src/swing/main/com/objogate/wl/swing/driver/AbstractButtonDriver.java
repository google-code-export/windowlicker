package com.objogate.wl.swing.driver;

import static org.hamcrest.Matchers.not;

import java.awt.Component;

import javax.swing.AbstractButton;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.objogate.wl.Prober;
import com.objogate.wl.Query;
import com.objogate.wl.internal.PropertyQuery;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.internal.query.MnemonicQuery;
import com.objogate.wl.swing.internal.query.TextQuery;


public class AbstractButtonDriver<T extends AbstractButton> extends ComponentDriver<T>
        implements TextQuery, MnemonicQuery 
{

    public AbstractButtonDriver(GesturePerformer gesturePerformer, ComponentSelector<T> componentSelector, Prober prober) {
        super(gesturePerformer, componentSelector, prober);
    }

    public AbstractButtonDriver(ComponentDriver<? extends Component> parentOrOwner, ComponentSelector<T> componentSelector) {
        super(parentOrOwner, componentSelector);
    }

    public AbstractButtonDriver(ComponentDriver<? extends Component> parentOrOwner, Class<T> componentType, Matcher<? super T>... matchers) {
        super(parentOrOwner, componentType, matchers);
    }

    public void click() {
        leftClickOnComponent();
    }

    public void hasText(Matcher<? super String> matcher) {
        has(text(), matcher);
    }

    private Query<T, String> text() {
        return new Query<T, String>() {
            public String query(T button) {
                return button.getText();
            }

            public void describeTo(Description description) {
                description.appendText("text");
            }
        };
    }

    public void mnemonic(Matcher<? super Character> matcher) {
        has(mnemonic(), matcher);
    }

    private static <B extends AbstractButton> Query<B, Character> mnemonic() {
        return new PropertyQuery<B, Character>("mnemonic") {
            public Character query(B button) {
                return (char) button.getMnemonic();
            }
        };
    }

    public void isChecked() {
        is(selected());
    }

    public void isNotChecked() {
        is(not(selected()));
    }

    private Matcher<AbstractButton> selected() {
        return new TypeSafeDiagnosingMatcher<AbstractButton>() {
            public void describeTo(Description description) {
                description.appendText("is selected");
            }

            @Override
            protected boolean matchesSafely(AbstractButton item, Description mismatchDescription) {
              if (item.isSelected()) {
                return true;
              }
              mismatchDescription.appendText("was not selected");
              return false;
            }
        };
    }
}
