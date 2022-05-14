package LAB_2_2.Graphic.Utils;


import javax.swing.table.DefaultTableModel;

/**
 * Default Table Model for Info JTable
 */
public class DefaultTableModelInfo extends DefaultTableModel {
    private static final String[] ColumnsForInfo = {"Name", "Description", "Producer", "Price", "Quantity", "Total value"};

    /**
     * Default Table Model for Info JTable
     * @param Data
     */
    public DefaultTableModelInfo(Object Data[][]){
        super(Data, ColumnsForInfo);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
