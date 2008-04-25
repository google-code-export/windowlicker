package com.objogate.wl.driver;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import com.objogate.exception.Defect;
import com.objogate.wl.*;
import com.objogate.wl.gesture.GesturePerformer;
import com.objogate.wl.gesture.Gestures;
import static com.objogate.wl.gesture.Gestures.*;
import com.objogate.wl.gesture.Tracker;

public class JTableDriver extends ComponentDriver<JTable> {

    public JTableDriver(ComponentDriver<? extends Container> containerDriver, Matcher<? super JTable>... matchers) {
        super(containerDriver, JTable.class, matchers);
    }

    public JTableDriver(GesturePerformer gesturePerformer, JTable component) {
        super(gesturePerformer, component);
    }

    public JTableDriver(GesturePerformer gesturePerformer, JTable component, Prober prober) {
        super(gesturePerformer, component, prober);
    }

    public JTableDriver(GesturePerformer gesturePerformer, ComponentSelector<JTable> componentSelector) {
        super(gesturePerformer, componentSelector);
    }

    public JTableDriver(GesturePerformer gesturePerformer, ComponentSelector<JTable> componentSelector, Prober prober) {
        super(gesturePerformer, componentSelector, prober);
    }

    public JTableDriver(ComponentDriver<? extends Component> parentOrOwner, ComponentSelector<JTable> componentSelector) {
        super(parentOrOwner, componentSelector);
    }

    public JTableDriver(ComponentDriver<? extends Component> parentOrOwner, Class<JTable> componentType, Matcher<? super JTable>... matchers) {
        super(parentOrOwner, componentType, matchers);
    }

    public static boolean arrayContains(int[] stuff, int item) {
        for (int selectedRow : stuff) {
            if (item == selectedRow)
                return true;
        }
        return false;
    }

    public void selectCells(Cell... cells) {
        scrollCellToVisible(cells[0]);
        
        Gesture[] gestures = new Gesture[cells.length];
        for (int i = 0; i < cells.length; i++) {
           gestures[i] = sequence(
                   moveMouseTo(pointIn(cells[i])),
                   leftClickMouse()
           );
        }

        performGesture(whileHoldingMultiSelect(sequence(gestures)));
    }

    public void selectCell(final int row, final int col) {
        selectCells(cell(row, col));
    }

    public void selectCell(final Matcher<? extends JComponent> matcher) {
        final Cell cell = hasCell(matcher);

        if (cell == null)
            throw new Defect("Cannot find cell");

        selectCells(cell);
    }

    public void dragMouseOver(Cell start, Cell end) {
        scrollCellToVisible(start);

        performGesture(
                moveMouseTo(pointIn(start)),
                whileHoldingMouseButton(Gestures.BUTTON1,
                        moveMouseTo(pointIn(end)))
        );
    }

    private Tracker pointIn(Cell start) {
        return offset(relativeMidpointOfColumn(start.col), rowOffset(start.row));
    }

    public Cell hasCell(final Matcher<? extends JComponent> matcher) {
        WithCellMatcher withCellMatching = new WithCellMatcher(matcher);
        
        is(withCellMatching);

        return withCellMatching.foundCell;
    }

    public Component editCell(int row, int col) {
        mouseOverCell(row, col);
        performGesture(Gestures.doubleClickMouse());

        JTableCellManipulation manipulation = new JTableCellManipulation(row, col);
        perform("finding cell editor", manipulation);

        return manipulation.getEditorComponent();
    }

    public void mouseOverCell(Cell cell) {
        mouseOverCell(cell.row, cell.col);
    }

    public void mouseOverCell(int row, int col) {
        scrollCellToVisible(row, col);

        int y = rowOffset(row);
        int x = relativeMidpointOfColumn(col);

        moveMouseToOffset(x, y);
    }

    private int rowOffset(int row) {
        int rowHeight = rowHeight();
        return (rowHeight * row) + (rowHeight / 2);
    }

    private int relativeMidpointOfColumn(final int col) {
        ColumnManipulation manipulation = new ColumnManipulation(col);
        perform("column mid point", manipulation);
        return manipulation.getMidPoint();
    }

    private int rowHeight() {
        JTableRowHeightManipulation tableManipulation = new JTableRowHeightManipulation();
        perform("row height", tableManipulation);
        return tableManipulation.getRowHeight();
    }

    public void hasSelectedCells(final Cell... cells) {
        is(new SelectedCellsMatcher(cells));
    }

    public void scrollCellToVisible(Cell cell) {
        scrollCellToVisible(cell.row, cell.col);
    }

    //todo (nick): this should be a gesture
    public void scrollCellToVisible(final int row, final int col) {
        perform("table scrolling", new ComponentManipulation<JTable>() {
            public void manipulate(JTable table) {
                table.scrollRectToVisible(table.getCellRect(row, col, true));
            }
        });
    }

    public void cellHasColour(int row, Object columnIdentifier, Matcher<Color> foregroundColor, Matcher<Color> backgroundColor) {
        cellHasBackgroundColor(row, columnIdentifier, backgroundColor);
        cellHasForegroundColor(row, columnIdentifier, foregroundColor);
    }

    public void cellHasColour(int row, int col, Matcher<Color> foregroundColor, Matcher<Color> backgroundColor) {
        cellHasBackgroundColor(row, col, backgroundColor);
        cellHasForegroundColor(row, col, foregroundColor);
    }

    public void cellHasBackgroundColor(final int row, final Object columnIdentifier, Matcher<Color> backgroundColor) {
      cellHasBackgroundColor(cell(row, columnIdentifier), backgroundColor);
    }

    public void cellHasBackgroundColor(final int row, final int col, Matcher<Color> backgroundColor) {
      cellHasBackgroundColor(cell(row, col), backgroundColor);
    }
    
    public void cellHasForegroundColor(final int row, final Object columnIdentifier, Matcher<Color> foregroundColor) {
      cellHasForegroundColor(cell(row, columnIdentifier), foregroundColor);
    }

    public void cellHasForegroundColor(final int row, final int col, Matcher<Color> foregroundColor) {
      cellHasForegroundColor(cell(row, col), foregroundColor);
    }
    
    public void cellRenderedWithText(final int row, final Object columnIdentifier, Matcher<String> expectedText) {
        cellRenderedWithText(cell(row, columnIdentifier), expectedText);
    }

    public void cellRenderedWithText(final int row, final int col, Matcher<String> expectedText) {
        cellRenderedWithText(cell(row, col), expectedText);
    }

    public void cellHasForegroundColor(final AbstractCell cell, Matcher<Color> foregroundColor) {
      has(renderedCell(cell, foregroundColor()), foregroundColor);
    }
    
    public void cellHasBackgroundColor(final AbstractCell cell, Matcher<Color> backgroundColor) {
      has(renderedCell(cell, backgroundColor()), backgroundColor);
    }

    public void cellRenderedWithText(final AbstractCell cell, Matcher<String> expectedText) {
      has(renderedCell(cell, labelText()), expectedText);
    }

    
    public static <T> Query<JTable, T> renderedCell(AbstractCell cell, Query<Component, T> detail) {
      return new RenderedCellQuery<T>(cell, detail);
    }
    
    public static Query<Component, Color> foregroundColor() {
      return new Query<Component, Color>() {
        public Color query(Component component) { return component.getForeground(); }
        public void describeTo(Description description) { description.appendText("foreground colour"); }
      };
    }

    public static Query<Component, Color> backgroundColor() {
      return new Query<Component, Color>() {
        public Color query(Component component) { return component.getBackground(); }
        public void describeTo(Description description) { description.appendText("background colour"); }
      };
    }

    public static IndentifierCell cell(final int row, final Object columnIdentifier) {
      return new IndentifierCell(row, columnIdentifier);
    }

    public static Cell cell(int row, int col) {
      return new Cell(row, col);
    }

    public static Query<Component, String> labelText() {
      return new Query<Component, String>() {
        public String query(Component cell) {
          if (cell instanceof JLabel) {
            JLabel label = (JLabel) cell;
            return label.getText();
          } else {
              throw new Defect("Rendered component is not a JLabel but a " + cell.getClass().getName());
          }
        }

        public void describeTo(Description description) {
            description.appendText("text");
        }
      };
    }
    public static abstract class AbstractCell {
      public final int row;

      public AbstractCell(int row) { this.row = row; }
      public abstract Cell asCellIn(JTable table);
    }
    
    
    public static class Cell extends AbstractCell {
        public final int col;

        public Cell(int row, int col) {
          super(row);
          this.col = col;
        }
        public Object valueFrom(JTable table) {
          return table.getValueAt(row, col);
        }
        @Override public Cell asCellIn(JTable unused) { return this; }

        @Override public String toString() {
            return "r" + row + " x " + "c" + col;
        }
    }

    public static class IndentifierCell extends AbstractCell {
      public final Object columnIdentifier;

      public IndentifierCell(int row, Object columnIdentifier) {
        super(row);
        this.columnIdentifier = columnIdentifier;
      }

      @Override public Cell asCellIn(JTable table) {
        return new Cell(row, viewIndex(table));
      }
      @Override public String toString() {
          return "row " + row + " at " + columnIdentifier;
      }
      private int viewIndex(JTable table) {
        int modelIndex = table.getColumn(columnIdentifier).getModelIndex();
        return table.convertColumnIndexToView(modelIndex);
      }
  }

    private class SelectedCellsMatcher extends TypeSafeMatcher<JTable> {
        public Cell unselectedCell;
        private final Cell[] cells;

        public SelectedCellsMatcher(Cell... cells) {
            this.cells = cells;
        }

        @Override
        public boolean matchesSafely(JTable table) {
            for (Cell cell : cells) {
                if (!table.isCellSelected(cell.row, cell.col)) {
                    this.unselectedCell = cell;
                    return false;
                }
            }
            return true;
        }

        public void describeTo(Description description) {
            description.appendText("cell " + unselectedCell + " is not selected");
        }
    }
    
    private static final class WithCellMatcher extends TypeSafeMatcher<JTable> {
      private final Matcher<? extends JComponent> matcher;
      Cell foundCell;

      WithCellMatcher(Matcher<? extends JComponent> matcher) {
        this.matcher = matcher;
      }

      @Override public boolean matchesSafely(JTable table) {
          int rowCount = table.getRowCount();
          int columnCount = table.getColumnCount();

          for (int row = 0; row < rowCount; row++) {
              for (int col = 0; col < columnCount; col++) {
                  Cell cell = cell(row, col);
                  if (matcher.matches(JTableCellManipulation.render(table, cell))) {
                      foundCell = cell;
                      return true;
                  }
              }
          }
          return false;
      }

      public void describeTo(Description description) {
          description.appendText("with cell ")
                     .appendDescriptionOf(matcher);
      }
    }

    private static class JTableRowHeightManipulation implements ComponentManipulation<JTable> {
        private int rowHeight;

        public void manipulate(JTable component) {
            rowHeight = component.getRowHeight();
        }

        public int getRowHeight() {
            return rowHeight;
        }
    }

    private static class ColumnManipulation implements ComponentManipulation<JTable> {
        private int midpoint;
        private final int col;

        public ColumnManipulation(int col) {
            this.col = col;
        }

        public void manipulate(JTable component) {
            midpoint = JTableHeaderDriver.ColumnManipulation.midpointOfColumn(col, component.getColumnModel());
        }

        public int getMidPoint() {
            return midpoint;
        }
    }

    private static class RenderedCellQuery<T> implements Query<JTable, T> {
      private final AbstractCell location;
      private final Query<Component, T> detail;

      public RenderedCellQuery(AbstractCell location, Query<Component, T> detail) {
          this.location = location;
          this.detail = detail;
      }

      public T query(JTable table) {
          return detail.query(JTableCellManipulation.render(table, location));
      }

      public void describeTo(Description description) {
          description.appendDescriptionOf(detail)
                     .appendText(" in cell at " + location);
      }
    }
}