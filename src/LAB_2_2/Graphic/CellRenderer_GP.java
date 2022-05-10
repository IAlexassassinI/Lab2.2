package LAB_2_2.Graphic;


import LAB_2_2.Group;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CellRenderer_GP extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        //Cells are by default rendered as a JLabel.
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        //Get the status for the current row.
        DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
        if (tableModel.getValueAt(row, 0).getClass() == Group.class) {
            l.setBackground(Color.GREEN);
        } else {
            l.setBackground(Color.RED);
        }

        //Return the JLabel which renders the cell.
        return l;
    }
}
