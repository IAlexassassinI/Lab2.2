package LAB_2_2.Graphic;

import LAB_2_2.Exceptions.GroupNotExistException;
import LAB_2_2.Exceptions.ProductNotExistException;
import LAB_2_2.Exceptions.ProductsAndDeltasArraysNotMatchException;
import LAB_2_2.Exceptions.SellMoreThenInStockException;
import LAB_2_2.Graphic.Utils.ConsumeNotDigits;
import LAB_2_2.Graphic.Utils.DefaultTableModelDelta;
import LAB_2_2.Graphic.Utils.DefaultTableModelInfo;
import LAB_2_2.Graphic.Utils.JTable_GP;
import LAB_2_2.Group;
import LAB_2_2.Model;
import LAB_2_2.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
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
    private JButton Save;
    private JButton Load;

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

    public final static File WHERE_TO_SAVE = new File("Saves");


    Object[][] DataForInfo = {};

    Object[][] DataForDelta = {};




    public MainMenu(Model Model) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super("Stock Controller");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        OS_Model = Model;
        this.setSize(800, 600);
        //this.setResizable(false);
        this.setLocationRelativeTo(null);
        OS_Model.load(WHERE_TO_SAVE);



        initMainMenu();
        initListeners();
        UpdateAll();
    }

    final static Color BackGroundColor = new Color(102, 187, 106);

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

        Save = new JButton("Save");
        PanelGridButtons.add(Save);

        Load = new JButton("Load");
        PanelGridButtons.add(Load);




        PanelBottom = new JPanel(new BorderLayout());
        PanelMain.add(PanelBottom, BorderLayout.SOUTH);


        PanelBottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        PanelBottom.add(PanelBottomLeft, BorderLayout.WEST);

        LabelGlobalValue = new JLabel("Global Value of goods in stock: "+OS_Model.getTotalCost());
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

        PanelMain.setBackground(BackGroundColor);
        PanelLeft.setBackground(BackGroundColor);
        PanelBottom.setBackground(BackGroundColor);
        PanelSearch.setBackground(BackGroundColor);
        PanelBottomRightDeleteAdd.setBackground(BackGroundColor);
        PanelBottomLeft.setBackground(BackGroundColor);
        PanelBottomRightChange.setBackground(BackGroundColor);
        PanelGridButtons.setBackground(BackGroundColor);





        UpdateDeltaTable();

        this.add(PanelMain);
    }

    private void initListeners(){
        MainMenu THIS = this;

        ActionListener ButtonPressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(TableDelta.isEditing()){
                    TableDelta.getCellEditor().stopCellEditing();
                }
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
                else if(e.getSource() == Apply){
                    try {
                        HandelApply();
                        UpdateAll();
                    }
                    catch(NumberFormatException E){
                        JOptionPane.showMessageDialog(THIS, "You inputted incorrect numbers", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(SellMoreThenInStockException E){
                        JOptionPane.showMessageDialog(THIS, "You trying to sell more than have in stock", "SellMoreThenInStockException", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(ProductNotExistException E){
                        JOptionPane.showMessageDialog(THIS, "Product not exist", "ProductNotExistException", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(ProductsAndDeltasArraysNotMatchException E){
                        System.err.println("WTF");
                    }
                }
                else if(e.getSource() == Cancel){
                    HandelCancel();
                }
                else if(e.getSource() == Save){
                    THIS.OS_Model.save(THIS.WHERE_TO_SAVE);
                }
                else if(e.getSource() == Load){
                    THIS.OS_Model.load(THIS.WHERE_TO_SAVE);
                }
            }
        };

        WindowListener CloseWindowPanel = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(THIS, "Are you sure you want to close this window?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    THIS.OS_Model.save(THIS.WHERE_TO_SAVE);
                    System.exit(0);
                }
            }
        };

        this.addWindowListener(CloseWindowPanel);

        Save.addActionListener(ButtonPressed);
        Load.addActionListener(ButtonPressed);
        Cancel.addActionListener(ButtonPressed);
        Apply.addActionListener(ButtonPressed);
        GlobalInfo.addActionListener(ButtonPressed);
        Delta.addActionListener(ButtonPressed);
        Add.addActionListener(ButtonPressed);
        Delete.addActionListener(ButtonPressed);
        Edit.addActionListener(ButtonPressed);
        Search.addActionListener(ButtonPressed);
        TableDelta.addKeyListener(new ConsumeNotDigits());
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
                OS_Model.removeGroup(((Group)Rows[i]).getName());
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
        TableInfo.setModel(DTMI);
    }

    private void UpdateDeltaTable(){
        DefaultTableModelDelta DTMD = new DefaultTableModelDelta(DataForDelta);
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
        LabelGlobalValue.setText("Global Value of goods in stock: "+OS_Model.getTotalCost());
        UpdateInfoTable();
        UpdateDeltaTable();
    }

    private void HandelApply() throws NumberFormatException, SellMoreThenInStockException, ProductNotExistException, ProductsAndDeltasArraysNotMatchException {
        ArrayList<Product> ForObrProd = new ArrayList<>();

        ArrayList<Integer> PlusDelta = new ArrayList<>();
        ArrayList<Integer> MinusDelta = new ArrayList<>();

        ArrayList<Integer> ForObrInts = new ArrayList<>();
        for(int i = 0; i < DataForDelta.length; i++){
            if(DataForDelta[i][0].getClass() == Product.class){
                ForObrProd.add((Product)DataForDelta[i][0]);

                String InDelta = (String)TableDelta.getDataModel().getValueAt(i,2);
                if(InDelta == ""){
                    PlusDelta.add(0);
                }
                else {
                    PlusDelta.add(Integer.parseInt(InDelta));
                }

                InDelta = (String)TableDelta.getDataModel().getValueAt(i,3);
                if(InDelta == ""){
                    MinusDelta.add(0);
                }
                else {
                    MinusDelta.add(Integer.parseInt(InDelta));
                }
            }
        }

        StringBuilder ForOutput = new StringBuilder();
        for(int i = 0; i < ForObrProd.size(); i++){
            ForObrInts.add(PlusDelta.get(i)-MinusDelta.get(i));
            if(PlusDelta.get(i) != 0 || MinusDelta.get(i) != 0){
                ForOutput.append(ForObrProd.get(i)).append(": Get: ").append(PlusDelta.get(i)).append(" | Sold: ").append(MinusDelta.get(i)).append("\n");
            }
        }

        OS_Model.delta(ForObrProd, ForObrInts);
        if(ForOutput.toString().length() != 0){
            Viewer MessageViewer = new Viewer(this, true, ForOutput.toString());
            MessageViewer.setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(this, "There are no changes!");
        }
    }

    private void HandelCancel(){
        UpdateAll();
    }

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
