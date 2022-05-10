package LAB_2_2;

import LAB_2_2.Exceptions.GroupAlreadyExistException;

import java.io.File;

public class Tester {

    public static void main(String[] args) throws GroupAlreadyExistException {
        Stock stock = new Stock();
        Group group1 = new Group("крупи");
        group1.add(new Product("гречка4", "звичайна гречка", "ALMA", 15, group1));
        stock.addGroup(group1);
        if(!stock.save(new File("testStock"))) System.out.println("false");;
        /*for(Group group : stock.getGroups()) {
            System.out.println(group.getName());
            for(Product product : group) {
                System.out.println("   " + product.getName());
            }
        }*/
    }

}
