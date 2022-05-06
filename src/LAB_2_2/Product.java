package LAB_2_2;

import Exceptions.NegativePriceException;
import Exceptions.SellMoreThenInStockException;

import java.util.Objects;

;

public class Product {

    private String name;
    private String description;
    private String producer;
    private double price;
    private int quantity;
    private Group group;
    private boolean visible;

    public Product(String name, String description, String producer, double price, Group group) {
        this(name, description, producer, price, 0, group);
    }

    public Product(String name, String description, String producer, double price, int quantity, Group group) {
        this.name = name;
        this.description = description;
        this.producer = producer;
        this.price = price;
        this.quantity = quantity;
        this.group = group;
        this.visible = true;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public double getTotalCost() {
        return this.quantity * this.price;
    }

    public int delta(int delta) throws SellMoreThenInStockException {
        if(this.quantity + delta < 0) throw new SellMoreThenInStockException();
        this.quantity += delta;
        return this.quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getProducer() {
        return producer;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setPrice(double price) throws NegativePriceException {
        if(price < 0) throw new NegativePriceException();
        this.price = price;
    }

    public void setQuantity(int quantity) throws SellMoreThenInStockException {
        if(quantity < 0) throw new SellMoreThenInStockException();
        this.quantity = quantity;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
