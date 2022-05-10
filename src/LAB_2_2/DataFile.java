package LAB_2_2;

import java.io.*;
import java.util.ArrayList;

/**
 * For save and load from file
 */
public class DataFile {

    private Stock stock;
    private File directory;

    public DataFile(File directory, Stock stock) {
        this.stock = stock;
        this.directory = directory;
    }

    public DataFile(File directory) {
        this.stock = null;
        this.directory = directory;
    }

    public boolean saveAll() {
        if(stock == null){
            return false;
        }
        if(directory.exists()){
            if(!deleteDirectory(directory)) return false;
        }
        if(!directory.mkdir())return false;
        for(Group group : stock.getGroups()) {
            if(!saveGroup(directory, group))return false;
        }
        return true;
    }

    private boolean saveGroup(File parentDirectory, Group group) {
        File groupDirectory = new File(parentDirectory.getAbsolutePath() + "\\" + group.getName());
        if(!groupDirectory.mkdir())return false;
        for(Product product : group) {
            if(!saveProduct(groupDirectory, product))return false;
        }
        return true;
    }

    private boolean saveProduct(File groupDirectory, Product product) {
        File productFile = new File(groupDirectory.getAbsolutePath() + "\\" + product.getName());
        try {
            FileWriter fw = new FileWriter(productFile);
            fw.write(product.getDescription() + "\n");
            fw.write(product.getProducer() + "\n");
            fw.write(String.valueOf(product.getPrice()) + "\n");
            fw.write(product.getQuantity() + "\n");
            fw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean loadAll() {
        if(stock == null) return false;
        if(!directory.isDirectory()) return false;
        if(!directory.exists())return false;
        ArrayList<Group> groups = new ArrayList<>();
        for(File f : directory.listFiles()) {
            if(!loadGroup(f, groups)) return false;
        }
        stock.setGroups(groups);
        return true;
    }

    private boolean loadGroup(File f, ArrayList<Group> groups) {
        if(!f.isDirectory()) return false;
        Group group = new Group(f.getName());
        for (File file : f.listFiles()) {
            if(!loadProducts(file, group))return false;
        }
        groups.add(group);
        return true;
    }

    private boolean loadProducts(File file, Group group) {
        if(!file.isFile()) return false;
        try  {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Product product = new Product(file.getName(), reader.readLine(), reader.readLine(), Double.parseDouble(reader.readLine()), Integer.parseInt(reader.readLine()), group);
            group.add(product);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    private boolean deleteDirectory(File file)
    {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            if(subfile.delete()) return false;
        }
        if(!file.delete())return false;
        return true;
    }

}
