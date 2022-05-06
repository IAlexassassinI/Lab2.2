package LAB_2_2;

import Exceptions.NegativePriceException;
import Exceptions.NegativeQuantityException;
import Exceptions.ProductAlreadyExistException;
import Exceptions.ProductNotExistException;

import java.util.ArrayList;

public class Group {

    private String name;
    private boolean visible;
    private ArrayList<Product> products;

    public Group(String name) {
        this.name = name;
        this.products = new ArrayList<>();
        this.visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        visible = visible;
    }

    public double getTotalCost() {
        double totalCost = 0;
        for(Product product : this.products) {
            totalCost += product.getTotalCost();
        }
        return totalCost;
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }

    public ArrayList<Product> getProducts(String productNamePart) {
        ArrayList<Product> res = new ArrayList<>();
        for(Product product : this.products) {
            if(product.getName().matches("\\." + productNamePart + "\\.")) res.add(product);
        }
        return res;
    }

    public Product getProduct(String productName) throws ProductNotExistException {
        for(Product product : this.products) {
            if(product.getName().equals(productName)) return product;
        }
        throw new ProductNotExistException();
    }

    public void addProduct(Product product) throws ProductAlreadyExistException {
        if(products.contains(product)) throw new ProductAlreadyExistException();
        this.products.add(product);
    }

    //TODO

    public void editProduct(String productName, String newProductName, String newProductDescription, String newProductProducer, double newProductPrice, int newProductQuantity) throws ProductNotExistException, NegativePriceException, NegativeQuantityException {
        Product product = getProduct(productName);
        if(!(newProductName == null)) product.setName(newProductName);
        if(!(newProductDescription == null)) product.setDescription(newProductDescription);
        if(!(newProductProducer == null)) product.setProducer(newProductProducer);
        if(newProductPrice < 0) product.setPrice(newProductPrice);
        if(newProductQuantity < 0) product.setQuantity(newProductQuantity);
    }

    public void editProduct(Product product, Product newProduct) throws ProductNotExistException {
        if(!this.products.contains(product))throw new ProductNotExistException();
        this.products.remove(product);
        this.products.add(newProduct);
    }

    public void removeProduct(Product product) throws ProductNotExistException {
        if(!this.products.contains(product))throw new ProductNotExistException();
        this.products.remove(product);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
