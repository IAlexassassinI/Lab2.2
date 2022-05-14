package LAB_2_2.Model;

import LAB_2_2.Exceptions.NegativePriceException;
import LAB_2_2.Exceptions.SellMoreThenInStockException;
import java.util.Objects;

/**
 * class that is responsible for product
 */

public class Product {

    private String name;
    private String description;
    private String producer;
    private double price;
    private int quantity;
    private Group group;
    private boolean visible;

    /**
     * constructor
     *
     * @param name name of product
     * @param description description of product
     * @param producer producer of product
     * @param price price of product
     * @param group group of products
     */

    public Product(String name, String description, String producer, double price, Group group) {
        this(name, description, producer, price, 0, group);
    }

    /**
     * constructor
     *
     * @param name name of product
     * @param description description of product
     * @param producer producer of product
     * @param price price of product
     * @param quantity quantity of products
     * @param group group of product
     */

    public Product(String name, String description, String producer, double price, int quantity, Group group) {
        this.name = name;
        this.description = description;
        this.producer = producer;
        this.price = price;
        this.quantity = quantity;
        this.group = group;
        this.visible = true;
    }

    /**
     * returns is visible
     *
     * @return is visible
     */

    public boolean isVisible() {
        return this.visible;
    }

    /**
     * sets visibility
     *
     * @param visible is product visible or not
     */

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * returns total cost of product
     *
     * @return total cost of product
     */

    public double getTotalCost() {
        return this.quantity * this.price;
    }

    /**
     * changes quantity and returns it
     *
     * @param delta how to change quantity
     * @return new quantity
     * @throws SellMoreThenInStockException
     */

    public int delta(int delta) throws SellMoreThenInStockException {
        if(this.quantity + delta < 0) throw new SellMoreThenInStockException();
        this.quantity += delta;
        return this.quantity;
    }

    /**
     * returns equal objects or not
     *
     * @param o any objects
     * @return equal objects or not
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    /**
     * returns name of product
     *
     * @return name of product
     */

    public String getName() {
        return name;
    }

    /**
     * returns description of product
     *
     * @return description of product
     */

    public String getDescription() {
        return description;
    }

    /**
     * returns producer of product
     *
     * @return producer of product
     */

    public String getProducer() {
        return producer;
    }

    /**
     * returns price of product
     *
     * @return price of product
     */

    public double getPrice() {
        return price;
    }

    /**
     * returns quantity of product
     *
     * @return quantity of product
     */

    public int getQuantity() {
        return quantity;
    }

    /**
     * sets new name of product
     *
     * @param name new name of product
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets new description of product
     *
     * @param description new description of product
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * sets new producer of product
     *
     * @param producer new producer of product
     */

    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * sets new price of product
     *
     * @param price new price of product
     * @throws NegativePriceException
     */

    public void setPrice(double price) throws NegativePriceException {
        if(price < 0) throw new NegativePriceException();
        this.price = price;
    }

    /**
     * sets new quantity of product
     *
     * @param quantity new quantity of product
     * @throws SellMoreThenInStockException
     */

    public void setQuantity(int quantity) throws SellMoreThenInStockException {
        if(quantity < 0) throw new SellMoreThenInStockException();
        this.quantity = quantity;
    }

    /**
     * returns group of product
     *
     * @return group of product
     */

    public Group getGroup() {
        return group;
    }

    /**
     * sets new group of product
     *
     * @param group new group of product
     */

    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * returns information about product in string
     *
     * @return information about product in string
     */

    @Override
    public String toString() {
        return name;
    }
}
