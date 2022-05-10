package LAB_2_2.Graphic;

import LAB_2_2.Group;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class JTable_GP extends JTable {

    JTable_GP(TableModel Model){
        super(Model);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
        Component c = super.prepareRenderer(renderer, row, column);
        Color color1 = new Color(220,220,220);
        Color color2 = Color.WHITE;
        if((row & 1) == 0){
            c.setBackground(color1);
        }
        else{
            c.setBackground(color2);
        }
        if(getValueAt(row, 0).getClass() == Group.class){
            c.setBackground(new Color(0, 255, 255, 255));
        }

        return c;
    }
}
