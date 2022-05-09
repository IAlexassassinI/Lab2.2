package LAB_2_2;

import LAB_2_2.Exceptions.*;
import LAB_2_2.Graphic.MainMenu;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Model M = new Model() {
            @Override
            public double getTotalCost() {
                return 0;
            }

            @Override
            public double getTotalCost(String groupName) {
                return 0;
            }

            @Override
            public void addGroup(Group group) throws GroupAlreadyExistException {

            }

            @Override
            public void addProduct(Product product, Group group) throws ProductAlreadyExistException, GroupNotExistException {

            }

            @Override
            public ArrayList<Group> getStockByFilter(String groupNamePart, String productNamePart) {
                return null;
            }

            @Override
            public void editProduct(Product product, Product newProduct) throws ProductNotExistException {

            }

            @Override
            public void editGroup(String groupName, String newGroup) throws GroupNotExistException {

            }

            @Override
            public void removeProduct(Product product) throws ProductNotExistException {

            }

            @Override
            public void removeGroup(String groupName) throws GroupNotExistException {

            }

            @Override
            public void delta(ArrayList<Product> products, ArrayList<Integer> deltas) throws ProductNotExistException, SellMoreThenInStockException, ProductsAndDeltasArraysNotMatchException {

            }
        };
        MainMenu MM = new MainMenu(M);
        MM.setVisible(true);

    }
}
