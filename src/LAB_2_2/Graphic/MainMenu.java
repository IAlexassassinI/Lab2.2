package LAB_2_2.Graphic;

import LAB_2_2.Exceptions.GroupNotExistException;
import LAB_2_2.Exceptions.ProductNotExistException;
import LAB_2_2.Group;
import LAB_2_2.Main;
import LAB_2_2.Model;
import LAB_2_2.Product;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;


public class MainMenu extends JFrame{

    private JPanel PanelMain;

    private JPanel PanelSearch;
    private JLabel LabelForProductSearch;
    private JTextField TextFieldForProductSearch;
    private JLabel LabelForGroupSearch;
    private JTextField TextFieldForGroupSearch;
    private JButton Search;

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

    private JTable_GP TableInfo;
    private JScrollPane ScrollerInfo;

    private JTable_GP TableDelta;
    private JScrollPane ScrollerDelta;

    Model OS_Model;




    Object[][] DataForInfo = {
            { new Product("1","1","1",1,1,new Group("AAA")), 0, "", "", "", ""},
            { new Group("WWW"), 1, "", "", "", "22"},
            { "CCCCC", 2, "", "", "", "22"},
            { "DDDDD", 3, "", "", "", "22"},
            { "EEEEE", 4, "", "", "", "22"},
    };

    Object[][] DataForDelta = {
            { new Product("1","1","1",1,1,new Group("AAA")), 0, "", "", ""},
            { new Group("WWW"), 1, "", "", ""},
            { "CCCCC", 2, "", "", ""},
            { "DDDDD", 3, "", "", ""},
            { "EEEEE", 4, "", "", ""},
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

        Search = new JButton("Search");
        PanelSearch.add(Search);




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

        DefaultTableModelInfo DTMI = new DefaultTableModelInfo(DataForInfo);
        TableInfo = new JTable_GP(DTMI);
        TableInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ScrollerInfo = new JScrollPane(TableInfo);
        CardPanelCenter.add(ScrollerInfo, "FOR_INFO");



        DefaultTableModelDelta DTMD = new DefaultTableModelDelta(DataForDelta);
        TableDelta = new JTable_GP(DTMD);
        TableDelta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ScrollerDelta = new JScrollPane(TableDelta);
        CardPanelCenter.add(ScrollerDelta, "FOR_DELTA");

        UpdateDeltaTable();

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
                        UpdateAll();
                    }
                    catch(ProductNotExistException E){

                    }
                    catch(GroupNotExistException E){

                    }
                }
                else if(e.getSource() == Edit){
                    DoEdit();
                    UpdateAll();
                }
                else if(e.getSource() == Search){
                    UpdateAll();
                }
            }
        };

        Apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(DataForDelta[TableDelta.getSelectedRow()][0]);
            }
        });

        GlobalInfo.addActionListener(ButtonPressed);
        Delta.addActionListener(ButtonPressed);
        Add.addActionListener(ButtonPressed);
        Delete.addActionListener(ButtonPressed);
        Edit.addActionListener(ButtonPressed);
        Search.addActionListener(ButtonPressed);
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
            Rows[i] = DataForInfo[SelectedRows[i]][0];
        }
        for(int i = 0; i < SelectedRows.length; i++){
            if(Rows[i].getClass() == Product.class){
               OS_Model.removeProduct((Product)Rows[i]);
            }
            else if(Rows[i].getClass() == Group.class){
                OS_Model.removeGroup(((Group)Rows[i]).getName()); //TODO Maybe
            }
        }
    }

    private void DoEdit(){
        int SelectedRowIs = TableInfo.getSelectedRow();
        if(SelectedRowIs == -1){
            return;
        }
        Object Obj = DataForInfo[SelectedRowIs][0];
        if(Obj.getClass() == Product.class){
            AddMenu AddMenu = new AddMenu(this, true, (Product)Obj);
            AddMenu.setVisible(true);
        }
        else if(Obj.getClass() == Group.class){
            AddMenu AddMenu = new AddMenu(this, true, (Group)Obj);
            AddMenu.setVisible(true);
        }
    }

    private void UpdateInfoTable(){
        DefaultTableModelInfo DTMI = new DefaultTableModelInfo(DataForInfo);
        TableInfo.setDefaultRenderer(TableColumn.class, new CellRenderer_GP());


        TableInfo.setModel(DTMI);
    }

    private void UpdateDeltaTable(){
        DefaultTableModelDelta DTMD = new DefaultTableModelDelta(DataForDelta);

        JTextField OnlyNumsField = new JTextField();
        OnlyNumsField.addKeyListener(new ConsumeNotDigits());

        TableDelta.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(OnlyNumsField));
        TableDelta.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(OnlyNumsField));

        TableDelta.setModel(DTMD);
    }

    private void DoSearch(){
        String ProductSearch = TextFieldForProductSearch.getText();
        String GroupSearch = TextFieldForGroupSearch.getText();
        ArrayList<Group> ModelData = OS_Model.getStockByFilter(GroupSearch, ProductSearch);
        DataForInfo = ParseToInfoTableData(ModelData);
        DataForDelta = ParseToDeltaTableData(ModelData);
    }

    void UpdateAll(){
        DoSearch();
        UpdateInfoTable();
        UpdateDeltaTable();
    }


    //TODO Wait for implementation of ArrayGropus
    private Object[][] ParseToInfoTableData(ArrayList<Group> ModelData){
        LinkedList<Object[]> Res = new LinkedList<Object[]>();
        for(int i = 0; i < ModelData.size(); i++){

            Group TMP_Group = ModelData.get(i);
            if(TMP_Group.isVisible()){
                Res.add(ParseGroupToInfoRow(TMP_Group));
                for(int j = 0; j < TMP_Group.size(); j++){
                    if(TMP_Group.get(j).isVisible()){
                        Res.add(ParseProductToInfoRow(TMP_Group.get(j)));
                    }
                }
            }

        }
        return Res.toArray(new Object[][]{});
    }

    private Object[][] ParseToDeltaTableData(ArrayList<Group> ModelData){
        LinkedList<Object[]> Res = new LinkedList<Object[]>();
        for(int i = 0; i < ModelData.size(); i++){
            Group TMP_Group = ModelData.get(i);
            if(TMP_Group.isVisible()){
                Res.add(ParseGroupToDeltaRow(TMP_Group));
                for(int j = 0; j < TMP_Group.size(); j++){
                    if(TMP_Group.get(j).isVisible()){
                        Res.add(ParseProductToDeltaRow(TMP_Group.get(j)));
                    }
                }
            }
        }
        return Res.toArray(new Object[][]{});
    }

    private Object[] ParseProductToInfoRow(Product InProduct){
        return new Object[]{InProduct, InProduct.getDescription(), InProduct.getProducer(), ""+InProduct.getPrice(), ""+InProduct.getQuantity(), ""+InProduct.getTotalCost()};
    }

    private Object[] ParseProductToDeltaRow(Product InProduct){
        return new Object[]{InProduct, ""+InProduct.getQuantity(), "", ""};
    }

    private Object[] ParseGroupToInfoRow(Group InGroup){
        return new Object[]{InGroup, "", "", "" ,"", ""+InGroup.getTotalCost()};
    }

    private Object[] ParseGroupToDeltaRow(Group InGroup){
        return new Object[]{InGroup, "", "", ""};
    }


}
