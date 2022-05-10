package LAB_2_2.Graphic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ConsumeNotDigits implements KeyListener {
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
