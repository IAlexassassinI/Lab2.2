package LAB_2_2.Graphic;

import LAB_2_2.Exceptions.GroupAlreadyExistException;
import LAB_2_2.Exceptions.GroupNotExistException;
import LAB_2_2.Exceptions.ProductAlreadyExistException;
import LAB_2_2.Exceptions.ProductNotExistException;
import LAB_2_2.Group;
import LAB_2_2.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddMenu extends JDialog {

    JButton Apply;

    JButton Cancel;
    JPanel PanelButtons;
    JPanel MainPanel;

    JComboBox ChooseWhatToAdd;
    JPanel CardForChange;

    JPanel PanelToAddProduct;
    JTextField NameOfProduct;
    JTextField DescriptionOfProduct;
    JTextField ProducerOfProduct;
    JTextField PriceOfProduct;
    JTextField QuantityOfProduct;
    JComboBox GroupOfProduct;

    JPanel PanelToAddGroup;
    JTextField NameOfGroup;

    Product InProd;
    Group InGroup;

    MainMenu Owner;

    //TODO Must refresh Table
    AddMenu(Frame owner, boolean modal){
        super(owner, modal);
        Owner = (MainMenu)owner;
        InProd = null;
        InGroup = null;
        initMainPanel(owner);
        initListeners();
    }

    AddMenu(Frame owner, boolean modal, Product prod){
        super(owner, modal);
        Owner = (MainMenu)owner;
        InProd = prod;
        InGroup = null;
        initMainPanel(owner);
        initListeners();
    }

    AddMenu(Frame owner, boolean modal, Group group){
        super(owner, modal);
        Owner = (MainMenu)owner;
        InProd = null;
        InGroup = group;
        initMainPanel(owner);
        initListeners();
    }

    private void initMainPanel(Frame owner){
        this.setTitle("Add menu");
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(300,420);
        this.setResizable(false);
        this.setLocationRelativeTo(owner);

        MainPanel = new JPanel(new BorderLayout());
        this.add(MainPanel);


        String FromWhatChoose[] = {"Product", "Group of products"};
        ChooseWhatToAdd = new JComboBox<>(FromWhatChoose);


        CardForChange = new JPanel(new CardLayout());
        MainPanel.add(CardForChange, BorderLayout.CENTER);

        PanelButtons = new JPanel(new FlowLayout());
        MainPanel.add(PanelButtons, BorderLayout.SOUTH);

        Apply = new JButton("Apply");
        PanelButtons.add(Apply);

        Cancel = new JButton("Cancel");
        PanelButtons.add(Cancel);




        PanelToAddProduct = new JPanel(new GridLayout(0, 1));
        CardForChange.add(PanelToAddProduct, "PRODUCT");

        NameOfProduct = new JTextField(16);
        PanelToAddProduct.add(new JLabel("Enter name of new product"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(NameOfProduct));

        DescriptionOfProduct = new JTextField(16);
        PanelToAddProduct.add(new JLabel("Enter description of new product"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(DescriptionOfProduct));

        ProducerOfProduct = new JTextField(16);
        PanelToAddProduct.add(new JLabel("Enter producer of new product"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(ProducerOfProduct));

        PriceOfProduct = new JTextField(16);
        PanelToAddProduct.add(new JLabel("Enter price of new product"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(PriceOfProduct));

        QuantityOfProduct = new JTextField(16);
        PanelToAddProduct.add(new JLabel("Enter quantity of new product"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(QuantityOfProduct));

        Group Groups[] = Owner.OS_Model.getStockByFilter(null,null).toArray(Group[]::new);
        GroupOfProduct = new JComboBox<Group>(Groups); //TODO Add variants from database
        PanelToAddProduct.add(new JLabel("Link new product to group of products"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(GroupOfProduct));


        PanelToAddGroup = new JPanel(new GridLayout(0, 1));
        CardForChange.add(PanelToAddGroup, "GROUP");

        NameOfGroup = new JTextField(16);
        PanelToAddGroup.add(new JLabel("Enter name of new group of product"));
        PanelToAddGroup.add(new JPanel(new FlowLayout()).add(NameOfGroup));

        int ElementDeltaKostil = PanelToAddProduct.getComponentCount() - PanelToAddGroup.getComponentCount();
        for(int i = 0; i < ElementDeltaKostil; i++){
            PanelToAddGroup.add(new JLabel());
        }

        if(InGroup == null && InProd == null){
            MainPanel.add(new JPanel(new FlowLayout()).add(ChooseWhatToAdd), BorderLayout.NORTH);
        }
        else if(InProd != null){
            CardLayout TMP_CardLayout = (CardLayout)this.CardForChange.getLayout();
            TMP_CardLayout.show(this.CardForChange, "PRODUCT");
            SetProductFields();
        }
        else if(InGroup != null){
            CardLayout TMP_CardLayout = (CardLayout)this.CardForChange.getLayout();
            TMP_CardLayout.show(this.CardForChange, "GROUP");
            SetGroupFields();
        }

    }

    private void initListeners(){
        AddMenu THIS = this;
        ItemListener ComboBox = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getSource() == ChooseWhatToAdd){
                    HandelComboboxChooseWhatToAdd();
                }
            }
        };

        ActionListener ButtonIsPressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == Cancel){
                    THIS.dispose();
                }
                else if(e.getSource() == Apply){
                    try {
                        HandleApply();
                        THIS.Owner.UpdateAll();
                    }
                    catch(NumberFormatException E){
                        JOptionPane.showMessageDialog(THIS, "You inputted incorrect numbers", "NumberFormatException", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(GroupAlreadyExistException E){
                        JOptionPane.showMessageDialog(THIS, "Group already exist", "GroupAlreadyExistException", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(ProductNotExistException E){
                        JOptionPane.showMessageDialog(THIS, "Product not exist", "ProductNotExistException", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(ProductAlreadyExistException E){
                        JOptionPane.showMessageDialog(THIS, "Product already exist", "ProductAlreadyExistException", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(GroupNotExistException E){
                        JOptionPane.showMessageDialog(THIS, "Group not exist", "GroupNotExistException", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };

        KeyListener ConsumeNotDigits = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(('0' <= c && c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == '.')){

                }
                else{
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        ChooseWhatToAdd.addItemListener(ComboBox);
        GroupOfProduct.addItemListener(ComboBox);
        Apply.addActionListener(ButtonIsPressed);
        Cancel.addActionListener(ButtonIsPressed);
        PriceOfProduct.addKeyListener(ConsumeNotDigits);
        QuantityOfProduct.addKeyListener(ConsumeNotDigits);

    }

    private Object[] AddNewProduct() throws NumberFormatException{
        String Name = NameOfProduct.getText();
        String Description = DescriptionOfProduct.getText();
        String Producer = ProducerOfProduct.getText();
        String Price = PriceOfProduct.getText();
        double price = Double.parseDouble(Price);
        String Quantity = QuantityOfProduct.getText();
        int quantity = Integer.parseInt(Quantity);
        Group GG = (Group)GroupOfProduct.getSelectedItem();
        return new Object[]{new Product(Name,Description,Producer,price,quantity,GG), GG};
    }

    private Group AddNewGroup(){
        String Name = NameOfGroup.getText();
        return new Group(Name);
    } //TODO Maybe would need to remade

    private void HandelComboboxChooseWhatToAdd(){
        CardLayout TMP_CardLayout = (CardLayout)this.CardForChange.getLayout();
        if(ChooseWhatToAdd.getSelectedIndex() == 0){
            TMP_CardLayout.show(this.CardForChange, "PRODUCT");
        }
        else if(ChooseWhatToAdd.getSelectedIndex() == 1){
            TMP_CardLayout.show(this.CardForChange, "GROUP");
        }
    }

    private void SetProductFields(){
        NameOfProduct.setText(InProd.getName());
        DescriptionOfProduct.setText(InProd.getDescription());
        ProducerOfProduct.setText(InProd.getProducer());
        PriceOfProduct.setText(""+InProd.getPrice());
        QuantityOfProduct.setText(""+InProd.getQuantity());
        GroupOfProduct.setSelectedItem(InProd.getGroup());
    }

    private void SetGroupFields(){
        NameOfGroup.setText(InGroup.getName());
    }

    private void HandleApply() throws ProductAlreadyExistException, GroupNotExistException, NumberFormatException, GroupAlreadyExistException, ProductNotExistException {
        if(InGroup == null && InProd == null){
            //ADD
            if(ChooseWhatToAdd.getSelectedIndex() == 0){
                Object Prod[] = AddNewProduct();
                this.Owner.OS_Model.addProduct((Product)Prod[0],(Group)Prod[1]);
            }
            else if(ChooseWhatToAdd.getSelectedIndex() == 1){
                this.Owner.OS_Model.addGroup(AddNewGroup());
            }
        }
        else if(InProd != null){
            //EDIT
            this.Owner.OS_Model.editProduct(InProd, (Product)AddNewProduct()[0]);
        }
        else if(InGroup != null){
            //EDIT
            this.Owner.OS_Model.editGroup(InGroup.getName(), AddNewGroup().getName());
        }
        this.dispose();
    }

}
