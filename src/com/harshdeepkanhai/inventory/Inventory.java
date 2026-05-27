package com.harshdeepkanhai.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Inventory {

    private Map<String, Item> items = new HashMap<>();

    public void addItem(Item item) {
        String key = item.getName().toLowerCase();
        if (items.containsKey(key)) {
            System.out.println("Item already exists. Use update instead.");
            return;
        }
        items.put(key, item);
        System.out.println("Added: " + item);
    }

    public void removeItem(String name) {
        String key = name.toLowerCase();
        Item removed = items.remove(key);
        if (removed == null) {
            System.out.println("Item not found: " + name);
        } else {
            System.out.println("Removed: " + removed.getName());
        }
    }

    public Optional<Item> findItem(String name) {
        return Optional.ofNullable(items.get(name.toLowerCase()));
    }

    public void updateQuantity(String name, int newQty) {
        findItem(name).ifPresentOrElse(
                item -> {
                    item.setQuantity(newQty);
                    System.out.println("Updated: " + item);
                },
                () -> System.out.println("Not found: " + name)
        );
    }

    public void listAll() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\n--- Inventory ---");
        items.values().forEach(System.out::println);
        double total = items.values().stream()
                .mapToDouble(Item::totalValue)
                .sum();
        System.out.printf("Total value: $%.2f%n", total);
    }

}
