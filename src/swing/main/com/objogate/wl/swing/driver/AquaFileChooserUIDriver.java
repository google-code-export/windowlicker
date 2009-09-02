package com.objogate.wl.swing.driver;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.awt.Component;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.hamcrest.Description;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.objogate.exception.Defect;
import com.objogate.wl.gesture.Gestures;
import com.objogate.wl.swing.matcher.ComponentMatchers;
import com.objogate.wl.swing.matcher.JLabelTextMatcher;

class AquaFileChooserUIDriver extends MetalFileChooserUIDriver {
    public AquaFileChooserUIDriver(JFileChooserDriver jFileChooserDriver) {
        super(jFileChooserDriver);
    }

    @Override 
    public void selectFile(String fileName) {
        JTableDriver fileEntry = new JTableDriver(parentOrOwner, JTable.class);
        fileEntry.selectCell(new JLabelTextMatcher(Matchers.equalTo(fileName)));

        //there seems to be a bug in the chooser, the first two selections are lost so we reselect it
        try {
            TimeUnit.MILLISECONDS.sleep(Gestures.MIN_TIME_TO_WAIT_TO_AVOID_DOUBLE_CLICK);
            fileEntry.selectCell(new JLabelTextMatcher(Matchers.equalTo(fileName)));
            TimeUnit.MILLISECONDS.sleep(Gestures.MIN_TIME_TO_WAIT_TO_AVOID_DOUBLE_CLICK);
            fileEntry.selectCell(new JLabelTextMatcher(Matchers.equalTo(fileName)));
        } catch (InterruptedException e) {
            throw new Defect("Unable to select file", e);
        }
    }

    @Override
    public void intoDir(String directoryName) {
        selectFile(directoryName);
        parentOrOwner.performGesture(Gestures.doubleClickMouse());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void createNewFolder(String folderName) {
        new AbstractButtonDriver<JButton>(parentOrOwner, JButton.class, ComponentMatchers.withButtonText("New Folder")).click();
        
        JTextFieldDriver textDriver = new JTextFieldDriver(parentOrOwner, JTextField.class, jTextFieldWithFilePromptSibling());
        textDriver.typeText(folderName);

        //hack (nick): can't get hold of the 'Create' button!  so use the keyboard to navigate to it
        textDriver.typeText("\t");//move focus to 'Cancel' button
        textDriver.typeText("\t");//move focus to 'Create' button
        textDriver.typeText(" ");//select 'Create' button
    }

    @SuppressWarnings("unchecked")
    @Override
    public void upOneFolder() {
        JComboBoxDriver boxDriver = new JComboBoxDriver(parentOrOwner, JComboBox.class,
                comboBoxWithMacTypeModel(), comboBoxModelWithMinSize(2));
        boxDriver.selectItem(1);
    }

    @Override
    public void documents() {
        throw new UnsupportedOperationException("There is no 'Documents' button in the Aqua L&F");
    }

    @Override
    public void home() {
        throw new UnsupportedOperationException("There is no 'Home' button in the Aqua L&F");
    }

    @Override
    public void desktop() {
        throw new UnsupportedOperationException("There is no 'Desktop' button in the Aqua L&F");
    }

    private Matcher<JTextField> jTextFieldWithFilePromptSibling() {
      final Matcher<? super String> fileLabelMatcher = equalTo("File:");
      return new TypeSafeDiagnosingMatcher<JTextField>() {
        @Override
        protected boolean matchesSafely(JTextField jTextField, Description mismatchDescription) {
          Component component = jTextField.getParent().getComponent(0);
          if (! (component instanceof JLabel)) {
            mismatchDescription.appendText("sibling was a ").appendText(component.getClass().getSimpleName());
            return false;
          }
          String jLabelText = ((JLabel) component).getText();
          if (! fileLabelMatcher.matches(jLabelText)) {
            mismatchDescription.appendText("Label text");
            fileLabelMatcher.describeMismatch(jLabelText, mismatchDescription);
            return false;
          }
          return true;
        }
        public void describeTo(Description description) {
            description.appendText("JTextField with JLabel sibling with text").appendDescriptionOf(fileLabelMatcher);
        }
      };
    }

    private Matcher<JComboBox> comboBoxWithMacTypeModel() {
      return new FeatureMatcher<JComboBox, String>(containsString("DirectoryComboBoxModel"), "JComboBox with model type", "model type") {
        @Override
        protected String featureValueOf(JComboBox actual) {
          return actual.getModel().getClass().getSimpleName();
        }
        
      };
    }

    private Matcher<JComboBox> comboBoxModelWithMinSize(int minSize) {
      return new FeatureMatcher<JComboBox, Integer>(greaterThanOrEqualTo(minSize), "JComboBox with model with size", "model size") {
        @Override
        protected Integer featureValueOf(JComboBox actual) {
          return actual.getModel().getSize();
        }
      };
    }
}
