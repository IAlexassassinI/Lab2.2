package LAB_2_2.Graphic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;


/**
 * Create Viewer to show changed values in big text screen
 */
public class Viewer extends JDialog {
    JButton ok;
    JPanel MainPanel;
    Viewer THIS = this;


    /**
     * Create Viewer to show changed values in big text screen
     * @param owner
     * @param modal
     * @param ForView
     * @throws IOException
     */
    public Viewer(Frame owner, boolean modal, String ForView) throws IOException {
        super(owner, modal);
        initMainPanel(owner, ForView);
        initListeners();
        pack();
    }

    final static Color BackGroundColor = new Color(102, 187, 106);
    final static Color BackGroundTextColor = new Color(200, 230, 201);


    /**
     * Create Viewer to show changed values in big text screen
     * @param owner
     * @param ForView
     * @throws IOException
     */
    private void initMainPanel(Frame owner, String ForView) throws IOException {
        this.setTitle("Changes viewer");
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(400,420);

        MainPanel = new JPanel(new BorderLayout());
        JLabel Inform = new JLabel("Changes:");
        MainPanel.add(Inform, BorderLayout.PAGE_START);


        JTextArea BigText = new JTextArea(32,48);
        BigText.setText(ForView);
        BigText.setCaretPosition(0);
        BigText.setEditable(false);


        JScrollPane Scroller = new JScrollPane(BigText);
        Scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        Scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        MainPanel.add(Scroller,BorderLayout.CENTER);

        this.ok = new JButton("Завершити перегляд");
        JPanel OkButtonPanel = new JPanel(new FlowLayout());
        OkButtonPanel.add(ok);
        MainPanel.add(OkButtonPanel, BorderLayout.PAGE_END);

        this.add(MainPanel);
        this.setResizable(false);
        this.setLocationRelativeTo(owner);

        this.setBackground(BackGroundColor);
        this.getContentPane().setBackground(BackGroundColor);
        MainPanel.setBackground(BackGroundColor);
        BigText.setBackground(BackGroundTextColor);
        OkButtonPanel.setBackground(BackGroundColor);

        Image Icon = ImageIO.read(new File("Resourses\\Stock.png"));
        this.setIconImage(Icon);


    }

    /**
     * initListeners
     */
    private void initListeners() {
        ActionListener CloseOnClick = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                THIS.dispose();
            }
        };

        KeyListener EnterPressed = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    THIS.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        ok.addActionListener(CloseOnClick);
        ok.addKeyListener(EnterPressed);
    }


}