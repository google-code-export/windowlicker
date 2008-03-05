package com.objogate.wl.probe;

import com.objogate.wl.Probe;
import org.hamcrest.Description;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerProbe implements ActionListener, Probe {
    private boolean hasBeenPerformed = false;

    public synchronized void actionPerformed(ActionEvent e) {
        hasBeenPerformed = true;
    }

    public void probe() {
        // nothing to do but wait for the action
    }

    public synchronized boolean isSatisfied() {
        return hasBeenPerformed;
    }

    public void describeTo(Description description) {
        description.appendText("click action should have been performed");
    }

    public void describeFailureTo(Description description) {
        if (hasBeenPerformed) {
            description.appendText("the action was performed");
        }
        else {
            description.appendText("the action was not performed");
        }
    }
}
