package LAB_2_2;

public class Stock {

    /*private ArrayList<Group> groups;

    public Stock() {
        this.groups = new ArrayList<>();
    }

    public double getTotalCost() {
        double totalCost = 0;
        for(Group group : this.groups) {
            totalCost += group.getTotalCost();
        }
        return totalCost;
    }

    public ArrayList<Group> getAllGroups() {
        return this.groups;
    }

    public ArrayList<Group> getGroups(String groupNamePart) {
        ArrayList<Group> res = new ArrayList<>();
        for(Group group : this.groups) {
            if(group.getName().matches("\\." + groupNamePart + "\\.")) {
                res.add(group);
            }
        }
        return res;
    }

    public Group getGroup(String groupName) throws GroupNotExistException {
        for(Group group : this.groups) {
            if(group.getName().equals(groupName)) return group;
        }
        throw new GroupNotExistException();
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> res = new ArrayList<>();
        for(Group group : this.groups) {
            res.addAll(group.getAllProducts());
        }
        return res;
    }

    public ArrayList<Product> getProducts(String productNamePart) {
        ArrayList<Product> res = new ArrayList<>();
        for(Group group : this.groups) {
            res.addAll(group.getProducts(productNamePart));
        }
        return res;
    }

    public Product getProduct(String productName) throws ProductNotExistException {
        Product res = null;
        for(Group group : this.groups) {
            try {
                res = group.getProduct(productName)
            }
            catch (ProductNotExistException e){

            }
        }
        if(res == null) throw new ProductNotExistException();
        return res;
    }

    public void addProduct(Product product, Group group) throws ProductAlreadyExistException {

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
        Product res = null;
        for(Group group : this.groups) {
            try {
                res = group.getProduct(product.getName());
                group.editProduct(product, newProduct);
            }
            catch (ProductNotExistException e){

            }
        }
        if(res == null) throw new ProductNotExistException();
    }

    public void removeProduct(Product product) throws ProductNotExistException {
        //if(!this.products.contains(product))throw new ProductNotExistException();
        //this.products.remove(product);
    }*/

}
