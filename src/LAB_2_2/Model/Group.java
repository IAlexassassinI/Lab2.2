package LAB_2_2.Model;

import java.util.ArrayList;
import java.util.Objects;

/**
 *  class which is responsible for Group
 */

public class Group extends ArrayList<Product> {

    private String name;
    private String description;
    private boolean visible;

    /**
     * constructor
     *
     * @param name name of group
     * @param description description of group
     */

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
        this.visible = true;
    }

    /**
     * returns is visible
     *
     * @return is visible
     */

    public boolean isVisible() {
        return visible;
    }

    /**
     * sets visibility
     *
     * @param visible visibility
     */

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * returns total cost of this group
     *
     * @return total cost of this group
     */

    public double getTotalCost() {
        double totalCost = 0;
        for(Product product : this) {
            totalCost += product.getTotalCost();
        }
        return totalCost;
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
        Group group = (Group) o;
        return Objects.equals(name, group.name);
    }

    /**
     * returns name of group
     *
     * @return name of group
     */

    public String getName() {
        return this.name;
    }

    /**
     * sets new name of group
     *
     * @param name new name of group
     */

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns information about group in string
     *
     * @return information about group in string
     */

    @Override
    public String toString() {
        return this.name;
    }
}
