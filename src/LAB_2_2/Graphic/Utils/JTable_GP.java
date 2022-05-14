package LAB_2_2.Graphic.Utils;

import LAB_2_2.Group;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * JTable for menu to draw cells correctly
 */
public class JTable_GP extends JTable {

    final static Color BackGroundColor = new Color(200, 230, 201);

    /**
     * JTable for menu to draw cells correctly
     * @param Model
     */
    public JTable_GP(TableModel Model){
        super(Model);
        this.setBackground(BackGroundColor);
        this.setFillsViewportHeight(true);
    }


    /**
     * @return dataModel
     */
    public TableModel getDataModel(){
        return dataModel;
    }

    JTable_GP THIS = this;

    final static Color color1 = new Color(165, 214, 167);
    final static Color color2 = new Color(185, 246, 202);
    final static Color color3 = new Color(46, 125, 50, 255);

    final static Color color1Text = new Color(0, 0, 0);
    final static Color color2Text = new Color(0, 0, 0);
    final static Color color3Text = new Color(255, 255, 255);

    final static Color color1Selected = new Color(165-40, 214-40, 167+50);//.brighter().brighter();
    final static Color color2Selected = new Color(185-40, 246-40, 202+50);//.brighter().brighter();
    final static Color color3Selected = new Color(46-40, 125-40, 50+50, 255);//.brighter().brighter();

    final static Color color1SelectedText = new Color(0, 0, 0);//.darker().darker();
    final static Color color2SelectedText = new Color(0, 0, 0);//.darker().darker();
    final static Color color3SelectedText = new Color(255, 255, 255);//.darker().darker();

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){

        Component c = super.prepareRenderer(renderer, row, column);



        if(this.isCellSelected(row, column)){
            if((row & 1) == 0){
                c.setBackground(color1Selected);
                c.setForeground(color1SelectedText);
            }
            else{
                c.setBackground(color2Selected);
                c.setForeground(color2SelectedText);
            }

            if(this.dataModel.getValueAt(row, 0).getClass() == Group.class){
                c.setBackground(color3Selected);
                c.setForeground(color3SelectedText);
            }
        }
        else {
            if((row & 1) == 0){
                c.setBackground(color1);
                c.setForeground(color1Text);
            }
            else{
                c.setBackground(color2);
                c.setForeground(color2Text);
            }

            if(this.dataModel.getValueAt(row, 0).getClass() == Group.class){
                c.setBackground(color3);
                c.setForeground(color3Text);
            }
        }

        return c;
    }





}
