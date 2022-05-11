package LAB_2_2;

import LAB_2_2.Exceptions.*;
import LAB_2_2.Graphic.MainMenu;

import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        try {
            MainMenu MM = new MainMenu(new Stock());
            MM.setVisible(true);
        }
        catch (Exception e){

        }

    }
}
