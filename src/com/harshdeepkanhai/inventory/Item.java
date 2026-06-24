package com.harshdeepkanhai.inventory;

public class Item {
    private String name;
    private double price;
    private int quantity;
    private Category category;

    public Item(String name, double price, int quantity, Category category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double totalValue() {
        return price * quantity;
    }

    public Category getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return String.format("%s | $%.2f | qty: %d | category %s", name, price, quantity, category);
    }

    public String toCsv() {
        return name + "," + price + "," + quantity + "," + category;
    }
}
