package LAB_2_2.Graphic;

import javax.swing.*;
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

    //TODO CHANGE WITH MENU
    private JPanel CardPanelBottomRight;
    //===================================
    private JPanel PanelBottomRightChange;
    private JButton Cancel;
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



    String[] columns = {
            "Product", "Quantity"
    };

    Object[][] data1 = {
            { "AAAAA", 0 },
            { "BBBBB", 1 },
            { "CCCCC", 2 },
            { "DDDDD", 3 },
            { "EEEEE", 4 },
    };



    static Object[][] data2 = {
            { "AAAAA", 0 },
            { "BBBBB", 1 },
            { "CCCCC", 2 },
            { "DDDDD", 3 },
            { "EEEEE", 4 },
            { "AAAAA", 0 },
            { "BBBBB", 1 },
            { "CCCCC", 2 },
            { "DDDDD", 3 },
            { "EEEEE", 4 },
    };


    public MainMenu(){
        super("Stock Controller");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

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

        //TODO CHANGE WITH MENU
        PanelBottomRightDeleteAdd = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        CardPanelBottomRight.add(PanelBottomRightDeleteAdd, "FOR_INFO");

        Delete = new JButton("Delete");
        PanelBottomRightDeleteAdd.add(Delete);

        Add = new JButton("Add");
        PanelBottomRightDeleteAdd.add(Add);




        //TODO CHANGE WITH MENU
        PanelBottomRightChange = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        CardPanelBottomRight.add(PanelBottomRightChange, "FOR_DELTA");

        Cancel = new JButton("Cancel");
        PanelBottomRightChange.add(Cancel);

        Apply = new JButton("Apply");
        PanelBottomRightChange.add(Apply);



        CardPanelCenter = new JPanel(new CardLayout());
        PanelMain.add(CardPanelCenter, BorderLayout.CENTER);

        TableInfo = new JTable(data1, columns);
        TableInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ScrollerInfo = new JScrollPane(TableInfo);
        CardPanelCenter.add(ScrollerInfo, "FOR_INFO");




        TableDelta = new JTable(data2, columns);
        TableDelta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ScrollerDelta = new JScrollPane(TableDelta);
        CardPanelCenter.add(ScrollerDelta, "FOR_DELTA");



        this.add(PanelMain);
    }

    private void initListeners(){
        MainMenu THIS = this;

        ActionListener ChangeMenuToInfo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenuToInfo();
            }
        };

        ActionListener ChangeMenuToDelta = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenuToDelta();
            }
        };

        Apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(THIS.TableInfo.getValueAt(0,0));
                System.out.println(THIS.data1[0][0]);

            }
        });

        GlobalInfo.addActionListener(ChangeMenuToInfo);
        Delta.addActionListener(ChangeMenuToDelta);
    }

    private void changeMenuToDelta(){
        CardLayout TMP_CardLayout = (CardLayout)this.CardPanelBottomRight.getLayout();
        TMP_CardLayout.show(this.CardPanelBottomRight, "FOR_DELTA");

        TMP_CardLayout = (CardLayout)this.CardPanelCenter.getLayout();
        TMP_CardLayout.show(this.CardPanelCenter, "FOR_DELTA");

        System.out.println("Delta");
    }

    private void changeMenuToInfo(){
        CardLayout TMP_CardLayout = (CardLayout)this.CardPanelBottomRight.getLayout();
        TMP_CardLayout.show(this.CardPanelBottomRight, "FOR_INFO");

        TMP_CardLayout = (CardLayout)this.CardPanelCenter.getLayout();
        TMP_CardLayout.show(this.CardPanelCenter, "FOR_INFO");

        System.out.println("Info");
    }


}
