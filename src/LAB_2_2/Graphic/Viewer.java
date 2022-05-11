package LAB_2_2.Graphic;

import javax.swing.*;
import javax.swing.text.html.FormView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Viewer extends JDialog {
    JButton ok;
    JPanel MainPanel;
    Viewer THIS = this;

    public Viewer(Frame owner, boolean modal, String ForView){
        super(owner, modal);
        initMainPanel(owner, ForView);
        initListeners();
        pack();
    }

    final static Color BackGroundColor = new Color(200, 230, 201);

    private void initMainPanel(Frame owner, String ForView) {
        this.setTitle("Secret information");
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
    }

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