package LAB_2_2.Graphic;

import LAB_2_2.Exceptions.GroupAlreadyExistException;
import LAB_2_2.Exceptions.GroupNotExistException;
import LAB_2_2.Exceptions.ProductAlreadyExistException;
import LAB_2_2.Exceptions.ProductNotExistException;
import LAB_2_2.Model.Group;
import LAB_2_2.Model.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Menu for adding Group or Product
 */
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
    JTextField DescriptionOfGroup;

    Product InProd;
    Group InGroup;

    MainMenu Owner;

    Image Icon = null;

    final static Color BackGroundColor = new Color(200, 230, 201);

    /**
     * Create Menu for adding Group or Product
     * @param owner
     * @param modal
     * @param PG
     * @throws IOException
     */
    AddMenu(Frame owner, boolean modal, Object PG) throws IOException {
        super(owner, modal);
        this.setIconImage(Icon);
        Owner = (MainMenu)owner;
        InProd = null;
        InGroup = null;
        Group GG = null;
        if(PG != null){
            if(PG.getClass() == Group.class){
                GG = (Group)PG;
            }
            else if(PG.getClass() == Product.class){
                GG = ((Product)PG).getGroup();
            }
        }
        this.setTitle("Add menu");
        initMainPanel(owner, GG);
        initListeners();
    }

    /**
     * Create Menu for adding Group or Product
     * @param owner
     * @param modal
     * @param prod
     * @throws IOException
     */
    AddMenu(Frame owner, boolean modal, Product prod) throws IOException {
        super(owner, modal);
        Owner = (MainMenu)owner;
        InProd = prod;
        InGroup = null;
        initMainPanel(owner, null);
        initListeners();
        this.setTitle("Edit menu");
    }

    /**
     * Create Menu for adding Group or Product
     * @param owner
     * @param modal
     * @param group
     * @throws IOException
     */
    AddMenu(Frame owner, boolean modal, Group group) throws IOException {
        super(owner, modal);
        Owner = (MainMenu)owner;
        InProd = null;
        InGroup = group;
        initMainPanel(owner, null);
        initListeners();
        this.setTitle("Edit menu");
    }

    /**
     * initMainPanel
     * @param owner
     * @param GG
     * @throws IOException
     */
    private void initMainPanel(Frame owner, Group GG) throws IOException {

        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(300,420);
        this.setResizable(false);
        this.setLocationRelativeTo(owner);
        Icon = ImageIO.read(new File("Resourses\\Stock.png"));
        this.setIconImage(Icon);

        MainPanel = new JPanel(new BorderLayout());
        this.add(MainPanel);



        String FromWhatChoose[] = {"Product", "Group of products"};
        ChooseWhatToAdd = new JComboBox<>(FromWhatChoose);


        CardForChange = new JPanel(new CardLayout());
        MainPanel.add(CardForChange, BorderLayout.CENTER);
        //CardForChange.setPreferredSize(new Dimension(200,240));

        PanelButtons = new JPanel(new FlowLayout());
        MainPanel.add(PanelButtons, BorderLayout.SOUTH);

        Apply = new JButton("Apply");
        PanelButtons.add(Apply);

        Cancel = new JButton("Cancel");
        PanelButtons.add(Cancel);




        PanelToAddProduct = new JPanel(new GridLayout(0, 1));
        CardForChange.add(PanelToAddProduct, "PRODUCT");

        NameOfProduct = new JTextField(16);
        PanelToAddProduct.add(new JLabel("Enter name of product"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(NameOfProduct));

        DescriptionOfProduct = new JTextField(16);
        PanelToAddProduct.add(new JLabel("Enter description of product"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(DescriptionOfProduct));

        ProducerOfProduct = new JTextField(16);
        PanelToAddProduct.add(new JLabel("Enter producer of product"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(ProducerOfProduct));

        PriceOfProduct = new JTextField(16);
        PanelToAddProduct.add(new JLabel("Enter price of product"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(PriceOfProduct));

        QuantityOfProduct = new JTextField(16);
        PanelToAddProduct.add(new JLabel("Enter quantity of product"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(QuantityOfProduct));

        Group Groups[] = Owner.OS_Model.getStockByFilter(null,null).toArray(Group[]::new);
        GroupOfProduct = new JComboBox<Group>(Groups); //TODO Add variants from database
        PanelToAddProduct.add(new JLabel("Link new product to group of products"));
        PanelToAddProduct.add(new JPanel(new FlowLayout()).add(GroupOfProduct));


        PanelToAddGroup = new JPanel(new GridLayout(0, 1));
        CardForChange.add(PanelToAddGroup, "GROUP");

        NameOfGroup = new JTextField(16);
        PanelToAddGroup.add(new JLabel("Enter name of group of product"));
        PanelToAddGroup.add(new JPanel(new FlowLayout()).add(NameOfGroup));

        DescriptionOfGroup = new JTextField(16);
        PanelToAddGroup.add(new JLabel("Enter description of group of product"));
        PanelToAddGroup.add(new JPanel(new FlowLayout()).add(DescriptionOfGroup));

        int ElementDeltaKostil = PanelToAddProduct.getComponentCount() - PanelToAddGroup.getComponentCount();
        for(int i = 0; i < ElementDeltaKostil; i++){
            PanelToAddGroup.add(new JLabel());
        }

        if(InGroup == null && InProd == null){
            MainPanel.add(new JPanel(new FlowLayout()).add(ChooseWhatToAdd), BorderLayout.NORTH);
            if(GG != null){
                GroupOfProduct.setSelectedItem(GG);
            }
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

        this.getContentPane().setBackground(BackGroundColor);
        MainPanel.setBackground(BackGroundColor);
        PanelToAddProduct.setBackground(BackGroundColor);
        PanelButtons.setBackground(BackGroundColor);
        PanelToAddGroup.setBackground(BackGroundColor);



    }

    /**
     * initListeners
     */
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

    /**
     * AddNewProduct
     * @return
     * @throws NumberFormatException
     */
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

    /**
     * AddNewGroup
     * @return Group
     */
    private Group AddNewGroup(){
        String Name = NameOfGroup.getText();
        String Description = DescriptionOfGroup.getText();
        return new Group(Name, Description);
    }

    /**
     * HandelComboboxChooseWhatToAdd
     */
    private void HandelComboboxChooseWhatToAdd(){
        CardLayout TMP_CardLayout = (CardLayout)this.CardForChange.getLayout();
        if(ChooseWhatToAdd.getSelectedIndex() == 0){
            TMP_CardLayout.show(this.CardForChange, "PRODUCT");
        }
        else if(ChooseWhatToAdd.getSelectedIndex() == 1){
            TMP_CardLayout.show(this.CardForChange, "GROUP");
        }
    }

    /**
     * SetProductFields
     */
    private void SetProductFields(){
        NameOfProduct.setText(InProd.getName());
        DescriptionOfProduct.setText(InProd.getDescription());
        ProducerOfProduct.setText(InProd.getProducer());
        PriceOfProduct.setText(""+InProd.getPrice());
        QuantityOfProduct.setText(""+InProd.getQuantity());
        GroupOfProduct.setSelectedItem(InProd.getGroup());
    }

    /**
     * SetGroupFields
     */
    private void SetGroupFields(){
        NameOfGroup.setText(InGroup.getName());
        DescriptionOfGroup.setText(InGroup.getDescription());
    }

    /**
     * HandleApply
     * @throws ProductAlreadyExistException
     * @throws GroupNotExistException
     * @throws NumberFormatException
     * @throws GroupAlreadyExistException
     * @throws ProductNotExistException
     */
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
            Group TMP_Group = AddNewGroup();
            this.Owner.OS_Model.editGroup(InGroup.getName(), TMP_Group.getName(), TMP_Group.getDescription());
        }
        this.dispose();
    }

}
