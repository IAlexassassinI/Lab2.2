package LAB_2_2.Graphic.Utils;

import LAB_2_2.Group;

import javax.swing.table.DefaultTableModel;

/**
 * Default Table Model for Delta JTable
 */
public class DefaultTableModelDelta extends DefaultTableModel {
    private static final String[] ColumnsForDelta = {"Name", "Quantity", "+++", "---"};

    /**
     * Default Table Model for Delta JTable
     * @param Data
     */
    public DefaultTableModelDelta(Object Data[][]){
        super(Data, ColumnsForDelta);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if(getValueAt(row, 0).getClass() == Group.class){
            return false;
        }
        return column >= 2;
    }



}
