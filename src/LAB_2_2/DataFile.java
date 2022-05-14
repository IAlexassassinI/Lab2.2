package LAB_2_2;

import LAB_2_2.Model.Group;
import LAB_2_2.Model.Product;

import java.io.*;
import java.util.ArrayList;

/**
 * For save and load stock from directory
 */
public class DataFile {

    private Stock stock;
    private File directory;

    /**
     * constructor
     *
     * @param directory directory to save or load
     * @param stock stock to work with
     */

    public DataFile(File directory, Stock stock) {
        this.stock = stock;
        this.directory = directory;
    }

    /**
     * constructor
     *
     * @param directory directory to save or load
     */

    public DataFile(File directory) {
        this.stock = null;
        this.directory = directory;
    }

    /**
     * saves groups and products
     *
     * @return true if saved successfully and false if not
     */

    public boolean saveAll() {
        if(stock == null){
            return false;
        }
        if(directory.exists()){
            if(!deleteDirectory(directory)) return false;
        }
        if(!directory.mkdir())return false;
        try {
            FileWriter fw  = new FileWriter(directory.getAbsolutePath() + "\\groups");
            for(int i = 0; i < stock.getGroups().size(); i++) {
                fw.write(stock.getGroups().get(i).getName() + "\n");
                if(!saveGroup(directory, stock.getGroups().get(i), i+1)){
                    //System.out.println("!saveGroup" + stock.getGroups().get(i));
                    return false;
                }
            }
            fw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * saves groups
     *
     * @param parentDirectory parent directory
     * @param group group to save
     * @param groupNum group number
     * @return true if saved successfully and false if not
     */

    private boolean saveGroup(File parentDirectory, Group group, int groupNum) {
        File groupDirectory = new File(parentDirectory.getAbsolutePath() + "\\" + groupNum);
        if(!groupDirectory.mkdir())return false;
        for(int i = 0; i < group.size(); i++) {
            if(!saveProduct(groupDirectory, group.get(i), i+1))return false;
        }
        return true;
    }

    /**
     * saves products in certain group
     *
     * @param groupDirectory directory of group
     * @param product product to save
     * @param productNum number of product
     * @return true if saved successfully and false if not
     */

    private boolean saveProduct(File groupDirectory, Product product, int productNum) {
        File productFile = new File(groupDirectory.getAbsolutePath() + "\\" + productNum);
        try {
            FileWriter fw = new FileWriter(productFile);
            fw.write(product.getName() + "\n");
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

    /**
     * loads groups and products
     *
     * @return true if loaded successfully and false if not
     */

    public boolean loadAll() {
        if(stock == null) return false;
        if(!directory.isDirectory()) return false;
        if(!directory.exists())return false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(directory.getAbsolutePath() + "\\groups"));
            ArrayList<Group> groups = new ArrayList<>();
            for(File f : directory.listFiles()) {
                if(f.isDirectory() && !loadGroup(f, groups, reader.readLine())){
                    reader.close();
                    return false;
                }
            }
            stock.setGroups(groups);
            reader.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * loads certain group
     *
     * @param f file of group to load from
     * @param groups list of groups
     * @param groupName name of group
     * @return true if loaded successfully and false if not
     */

    private boolean loadGroup(File f, ArrayList<Group> groups, String groupName) {
        if(!f.isDirectory()) return false;
        Group group = new Group(groupName);
        for (File file : f.listFiles()) {
            if(!loadProducts(file, group))return false;
        }
        groups.add(group);
        return true;
    }

    /**
     * loads certain product
     *
     * @param file file of product to load from
     * @param group group of product
     * @return true if loaded successfully and false if not
     */

    private boolean loadProducts(File file, Group group) {
        if(!file.isFile()) return false;
        try  {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Product product = new Product(reader.readLine(), reader.readLine(), reader.readLine(), Double.parseDouble(reader.readLine()), Integer.parseInt(reader.readLine()), group);
            group.add(product);
            reader.close();
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * deletes directory
     *
     * @param file directory to delete
     * @return true if deleted successfully and false if not
     */

    private boolean deleteDirectory(File file) {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            else if(!subfile.delete()) return false;
        }
        return file.delete();
    }

}
