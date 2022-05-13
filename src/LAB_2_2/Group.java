package LAB_2_2;

import java.util.ArrayList;
import java.util.Objects;

public class Group extends ArrayList<Product> {

    private String name;
    private boolean visible;

    public Group(String name) {
        this.name = name;
        this.visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public double getTotalCost() {
        double totalCost = 0;
        for(Product product : this) {
            totalCost += product.getTotalCost();
        }
        return totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
