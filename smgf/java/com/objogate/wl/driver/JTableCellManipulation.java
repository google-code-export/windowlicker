package com.objogate.wl.driver;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.objogate.wl.ComponentManipulation;
import com.objogate.wl.driver.JTableDriver.Cell;
import com.objogate.wl.driver.JTableDriver.IndentifierCell;

public class JTableCellManipulation implements ComponentManipulation<JTable> {
    private final JTableDriver.AbstractCell location;

    private Component cell;
    private Component editorComponent;

    public JTableCellManipulation(int row, int col) {
      this.location = new Cell(row, col);
    }

    public JTableCellManipulation(int row, Object columnIdentifier) {
      this.location = new IndentifierCell(row, columnIdentifier);
    }

    public void manipulate(JTable table) {
      this.cell = render(table, location.asCellIn(table));
      this.editorComponent = editor(table, location.asCellIn(table));
    }

    private Component editor(JTable table, Cell rowCol) {
        TableCellEditor tableCellEditor = table.getCellEditor(rowCol.row, rowCol.col);
        return tableCellEditor.getTableCellEditorComponent(table, 
                  rowCol.valueFrom(table), 
                  false, rowCol.row, rowCol.col);
    }

    public static Component render(JTable table, int row, Object columIdentifier) {
      return render(table, new IndentifierCell(row, columIdentifier).asCellIn(table));
    }

    public static Component render(JTable table, int row, int col) {
      return render(table, new Cell(row, col));
    }
    
    public static Component render(JTable table, Cell rowCol) {
        TableCellRenderer cellRenderer = table.getCellRenderer(rowCol.row, rowCol.col);
        boolean isSelected = JTableDriver.arrayContains(table.getSelectedRows(), rowCol.row);
        boolean hasFocus = isSelected && JTableDriver.arrayContains(table.getSelectedColumns(), rowCol.col);
        return cellRenderer.getTableCellRendererComponent(table, 
                              rowCol.valueFrom(table), 
                              isSelected, hasFocus, rowCol.row, rowCol.col);
    }

    public Component getRenderedCell() {
        return cell;
    }

    public Component getEditorComponent() {
        return editorComponent;
    }
}
