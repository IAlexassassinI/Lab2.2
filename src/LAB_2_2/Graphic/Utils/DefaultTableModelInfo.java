package LAB_2_2.Graphic.Utils;


import javax.swing.table.DefaultTableModel;

public class DefaultTableModelInfo extends DefaultTableModel {
    private static final String[] ColumnsForInfo = {"Name", "Description", "Producer", "Price", "Quantity", "Total value"};
    public DefaultTableModelInfo(Object Data[][]){
        super(Data, ColumnsForInfo);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
