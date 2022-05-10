package LAB_2_2.Graphic;

import LAB_2_2.Exceptions.GroupNotExistException;
import LAB_2_2.Exceptions.ProductNotExistException;
import LAB_2_2.Group;
import LAB_2_2.Model;
import LAB_2_2.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MainMenu extends JFrame{

    private JPanel PanelMain;

    private JPanel PanelSearch;
    private JLabel LabelForProductSearch;
    private JTextField TextFieldForProductSearch;
    private JLabel LabelForGroupSearch;
    private JTextField TextFieldForGroupSearch;

    private JPanel PanelLeft;
    private JPanel PanelGridButtons;
    private JButton GlobalInfo;
    private JButton Delta;

    private JPanel PanelBottom;
    private JPanel PanelBottomLeft;
    private JLabel LabelGlobalValue;


    private JPanel CardPanelBottomRight;
    //===================================
    private JPanel PanelBottomRightChange;
    private JButton Cancel;
    private JButton Edit;
    private JButton Apply;
    //===================================
    //===================================
    private JPanel PanelBottomRightDeleteAdd;
    private JButton Delete;
    private JButton Add;
    //===================================

    private JPanel CardPanelCenter;

    private JTable TableInfo;
    private JScrollPane ScrollerInfo;

    private JTable TableDelta;
    private JScrollPane ScrollerDelta;

    Model OS_Model;

    String[] ColumnsForInfo = {"Name", "Description", "Producer", "Price", "Quantity", "Total value"};
    String[] ColumnsForDelta = {"Name", "Quantity", "+++", "---"};

    Object[][] DataForInfo = {
            { "AAAAA", 0, "", "", "", "22" , "111"},
            { "BBBBB", 1, "", "", "", "22"  , "11"},
            { "CCCCC", 2, "", "", "", "22"  , "11"},
            { "DDDDD", 3, "", "", "", "22"  , "11"},
            { "EEEEE", 4, "", "", "", "22"  , "11"},
    };

    Object[][] DataForDelta = {
            { "AAAAA", 0, "", ""},
            { "BBBBB", 1, "", ""},
            { "CCCCC", 2, "", ""},
            { "DDDDD", 3, "", ""},
            { "EEEEE", 4, "", ""},
    };





    public MainMenu(Model Model){
        super("Stock Controller");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        OS_Model = Model;
        this.setSize(800, 600);
        //this.setResizable(false);
        this.setLocationRelativeTo(null);



        initMainMenu();
        initListeners();
    }

    private void initMainMenu(){
        PanelMain = new JPanel(new BorderLayout());
        JLabel BorderCreator = new JLabel("   ");
        PanelMain.add(BorderCreator, BorderLayout.EAST);



        PanelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        PanelMain.add(PanelSearch, BorderLayout.NORTH);

        LabelForProductSearch = new JLabel("Search by product name");
        PanelSearch.add(LabelForProductSearch);

        TextFieldForProductSearch = new JTextField(16);
        PanelSearch.add(TextFieldForProductSearch);

        LabelForGroupSearch = new JLabel("Search by group name");
        PanelSearch.add(LabelForGroupSearch);

        TextFieldForGroupSearch = new JTextField(16);
        PanelSearch.add(TextFieldForGroupSearch);




        PanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        PanelMain.add(PanelLeft, BorderLayout.WEST);
        PanelGridButtons = new JPanel(new GridLayout(0,1));
        PanelLeft.add(PanelGridButtons);

        GlobalInfo = new JButton("GlobalInfo");
        PanelGridButtons.add(GlobalInfo);

        Delta = new JButton("Delta");
        PanelGridButtons.add(Delta);




        PanelBottom = new JPanel(new BorderLayout());
        PanelMain.add(PanelBottom, BorderLayout.SOUTH);


        PanelBottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        PanelBottom.add(PanelBottomLeft, BorderLayout.WEST);

        LabelGlobalValue = new JLabel("Global Value of goods in stock");
        PanelBottomLeft.add(LabelGlobalValue);

        CardPanelBottomRight = new JPanel(new CardLayout());
        PanelBottom.add(CardPanelBottomRight, BorderLayout.EAST);


        PanelBottomRightDeleteAdd = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        CardPanelBottomRight.add(PanelBottomRightDeleteAdd, "FOR_INFO");

        Delete = new JButton("Delete");
        PanelBottomRightDeleteAdd.add(Delete);

        Edit = new JButton("Edit");
        PanelBottomRightDeleteAdd.add(Edit);

        Add = new JButton("Add");
        PanelBottomRightDeleteAdd.add(Add);





        PanelBottomRightChange = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        CardPanelBottomRight.add(PanelBottomRightChange, "FOR_DELTA");

        Cancel = new JButton("Cancel");
        PanelBottomRightChange.add(Cancel);

        Apply = new JButton("Apply");
        PanelBottomRightChange.add(Apply);



        CardPanelCenter = new JPanel(new CardLayout());
        PanelMain.add(CardPanelCenter, BorderLayout.CENTER);

        DefaultTableModel DTM = new DefaultTableModel(DataForInfo, ColumnsForInfo){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TableInfo = new JTable(DTM);
        TableInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ScrollerInfo = new JScrollPane(TableInfo);
        CardPanelCenter.add(ScrollerInfo, "FOR_INFO");




        TableDelta = new JTable(DataForDelta, ColumnsForDelta);
        TableDelta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ScrollerDelta = new JScrollPane(TableDelta);
        CardPanelCenter.add(ScrollerDelta, "FOR_DELTA");



        this.add(PanelMain);
    }

    private void initListeners(){
        MainMenu THIS = this;

        ActionListener ButtonPressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == GlobalInfo){
                    changeMenuToInfo();
                }
                else if(e.getSource() == Delta){
                    changeMenuToDelta();
                }
                else if(e.getSource() == Add){
                    AddMenu AddMenu = new AddMenu(THIS, true);
                    AddMenu.setVisible(true);
                }
                else if(e.getSource() == Delete){
                    try {
                        DeleteSelectedRows();
                    }
                    catch(ProductNotExistException E){

                    }
                    catch(GroupNotExistException E){

                    }
                }
                else if(e.getSource() == Edit){
                    int SelectedRowIs = TableInfo.getSelectedRow();
                    Object Obj = DataForInfo[SelectedRowIs][6];
                    if(Obj.getClass() == Product.class){
                        AddMenu AddMenu = new AddMenu(THIS, true, (Product)Obj);
                        AddMenu.setVisible(true);
                    }
                    else if(Obj.getClass() == Group.class){
                        AddMenu AddMenu = new AddMenu(THIS, true, (Group)Obj);
                        AddMenu.setVisible(true);
                    }
                }
            }
        };

        GlobalInfo.addActionListener(ButtonPressed);
        Delta.addActionListener(ButtonPressed);
        Add.addActionListener(ButtonPressed);
        Delete.addActionListener(ButtonPressed);
        Edit.addActionListener(ButtonPressed);
    }

    private void changeMenuToDelta(){
        CardLayout TMP_CardLayout = (CardLayout)this.CardPanelBottomRight.getLayout();
        TMP_CardLayout.show(this.CardPanelBottomRight, "FOR_DELTA");

        TMP_CardLayout = (CardLayout)this.CardPanelCenter.getLayout();
        TMP_CardLayout.show(this.CardPanelCenter, "FOR_DELTA");
    }

    private void changeMenuToInfo(){
        CardLayout TMP_CardLayout = (CardLayout)this.CardPanelBottomRight.getLayout();
        TMP_CardLayout.show(this.CardPanelBottomRight, "FOR_INFO");

        TMP_CardLayout = (CardLayout)this.CardPanelCenter.getLayout();
        TMP_CardLayout.show(this.CardPanelCenter, "FOR_INFO");
    }

    private void DeleteSelectedRows() throws GroupNotExistException, ProductNotExistException {
        int SelectedRows[] = TableInfo.getSelectedRows();
        Object Rows[] = new Object[SelectedRows.length];
        for (int i = 0; i < SelectedRows.length; i++){
            Rows[i] = DataForInfo[SelectedRows[i]][5];
        }
        for(int i = 0; i < SelectedRows.length; i++){
            if(Rows[i].getClass() == Product.class){
               OS_Model.removeProduct((Product)Rows[i]);
            }
            else{
                OS_Model.removeGroup(((Group)Rows[i]).getName()); //TODO Maybe
            }
        }
        UpdateInfoTable();
    }

    private void UpdateInfoTable(){ //TODO Make Refresh All method
        TableInfo.setModel(new DefaultTableModel(DataForInfo,ColumnsForInfo){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }) ;
    }



    //TODO Wait for implementation of ArrayGropus
    private Object[][] ParseToInfoTableData(ArrayList<Group> ModelData){
        int Rows = ModelData.size();
        int Cols = 7; //4
        Object Res[][]  = new Object[Rows][Cols];

        for(int i = 0; i < Rows; i++){
            Group TMP_Group = ModelData.get(i);

        }

        return null;
    }

    private Object[] ParseGroupToInfoRow(Group InGroup){
        return new Object[]{InGroup.getName(), "", "", "" ,"", InGroup.getTotalCost(), InGroup};
    }


}
