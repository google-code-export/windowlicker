package com.objogate.wl.swing.driver;

import javax.swing.JToggleButton;

import com.objogate.wl.swing.matcher.AbstractButtonTextMatcher;

class WindowsFileChooserUIDriver extends MetalFileChooserUIDriver {
    private static final String DESKTOP_BUTTON_TEXT = "Desktop";
    private static final String HOME_BUTTON_TEXT = "My Documents";

    public WindowsFileChooserUIDriver(JFileChooserDriver jFileChooserDriver) {
        super(jFileChooserDriver);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void desktop() {
        new AbstractButtonDriver<JToggleButton>(parentOrOwner, JToggleButton.class,
                new AbstractButtonTextMatcher<JToggleButton>(wrapInWindowsHtml(DESKTOP_BUTTON_TEXT))).click();
    }

    @Override
    public void home() {
        throw new UnsupportedOperationException("There is no 'Home' button in the Windows L&F");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void documents() {
        new AbstractButtonDriver<JToggleButton>(parentOrOwner, JToggleButton.class,
            new AbstractButtonTextMatcher<JToggleButton>(wrapInWindowsHtml(HOME_BUTTON_TEXT))).click();
    }

    private String wrapInWindowsHtml(String buttonText) {
      return "<html><center>" + buttonText + "</center></html>";
  }
}
