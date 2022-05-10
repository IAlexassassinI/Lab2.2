package LAB_2_2.Graphic;

import LAB_2_2.Group;

import javax.swing.table.DefaultTableModel;

public class DefaultTableModelDelta extends DefaultTableModel {
    private static final String[] ColumnsForDelta = {"Name", "Quantity", "+++", "---"};
    DefaultTableModelDelta(Object Data[][]){
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
