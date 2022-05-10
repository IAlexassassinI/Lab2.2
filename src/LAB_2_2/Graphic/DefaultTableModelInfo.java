package LAB_2_2.Graphic;

import LAB_2_2.Group;

import javax.swing.table.DefaultTableModel;

public class DefaultTableModelInfo extends DefaultTableModel {
    private static final String[] ColumnsForInfo = {"Name", "Description", "Producer", "Price", "Quantity", "Total value"};
    DefaultTableModelInfo(Object Data[][]){
        super(Data, ColumnsForInfo);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
