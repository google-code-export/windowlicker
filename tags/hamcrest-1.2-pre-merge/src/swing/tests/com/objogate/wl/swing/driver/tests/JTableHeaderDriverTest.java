package com.objogate.wl.swing.driver.tests;

import static com.objogate.wl.swing.probe.ComponentIdentity.selectorFor;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;

import com.objogate.wl.swing.driver.JTableHeaderDriver;

public class  JTableHeaderDriverTest extends AbstractComponentDriverTest<JTableHeaderDriver> {
    private ReallyBigTable table = new ReallyBigTable();

    @Before
    public void setUp() throws Exception {
        view(new JScrollPane(table));
        driver = driverFor(table);
    }
    
    private JTableHeaderDriver driverFor(JTable drivenTable) {
      JScrollPane pane = new JScrollPane(drivenTable);
      pane.setPreferredSize(new Dimension(800, 600));
      view(pane);

      return new JTableHeaderDriver(gesturePerformer, selectorFor(drivenTable.getTableHeader()), prober);
    }
    
    @Test public void 
    canMoveColumnsToTheRight() throws Exception {
        driver.moveColumn(2, 3);

        driver.indexOfColumnIdentifiedBy("c", 5);

        driver.moveColumn("b", 1);

        driver.indexOfColumnIdentifiedBy("b", 2);
    }

    @Test public void 
    canMoveColumnsToTheLeft() throws Exception {
        driver.moveColumn(4, -1);

        driver.indexOfColumnIdentifiedBy("e", 3);

        driver.moveColumn("c", -2);

        driver.indexOfColumnIdentifiedBy("c", 0);
    }

    @Test public void 
    canMoveOffScreenColumns() throws Exception {
        driver.moveColumn(20, -5);

        driver.indexOfColumnIdentifiedBy("u", 15);
    }

    @Test public void 
    canResizeColumnByIdentifier() throws Exception {
        int widthBefore = driver.getColumnWidth("d");
        int delta = 25;

        driver.resizeColumn("d", delta);

        driver.widthOfColumn("d", widthBefore + delta);
    }

    @Test public void 
    canResizeColumnByIndex() throws Exception {
        int widthBefore = driver.getColumnWidth(2);
        int delta = -10;

        driver.resizeColumn(2, delta);

        driver.widthOfColumn(2, widthBefore + delta);
    }
}