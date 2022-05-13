package LAB_2_2;

import LAB_2_2.Graphic.MainMenu;


public class Main {

    public static void main(String[] args) {


        try {
            MainMenu MM = new MainMenu(new Stock());
            MM.setVisible(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
