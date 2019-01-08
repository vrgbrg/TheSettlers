package flow;

import javax.swing.*;

import java.awt.*;

public class Building extends Cell {
    private String name;
    private int[][] shape;
    private int price;

    public Building(String value, String name, int[][] shape, int price) {
        super(value);
        this.name = name;
        this.shape = shape;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Building(String value) {
        super(value);
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public String toString() {
        // return "Building is: " + name + " with price: " + price;
        return super.toString();
    }
}

