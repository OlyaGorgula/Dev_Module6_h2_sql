package org.feature.query_into_object;

public class ProjectPrices {
    private final String name;
    private final int price;

    public ProjectPrices(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProjectPrices{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
