package com.harshdeepkanhai.inventory;

import java.util.Scanner;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String FILENAME = "inventory.csv";

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        FileStore store = new FileStore();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        inventory.loadItems(store.loadFromFile(FILENAME));
        System.out.println("=== Inventory Manager ===");
        printMenu();

        while (running) {
            System.out.print("\n> ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "add" -> handleAdd(scanner, inventory);
                case "remove" -> handleRemove(scanner, inventory);
                case "update" -> handleUpdate(scanner, inventory);
                case "search" -> handleSearch(scanner, inventory);
                case "list" -> inventory.listAll();
                case "bycategory" -> handleByCategory(scanner, inventory);
                case "help" -> printMenu();
                case "quit" -> {
                    store.saveToFile(FILENAME, inventory.getAllItems());
                    running = false;
                    System.out.println("Bye!");
                }
                default -> System.out.println(
                        "Unknown command. Type 'help'.");
            }
        }
        scanner.close();
    }

    private static void handleByCategory(Scanner sc, Inventory inv) {
        System.out.println("Category: ");
        try {
            Category category = Category.valueOf(sc.nextLine().trim().toUpperCase());
            inv.listByCategory(category);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Category Entered");
        }

    }

    private static void handleAdd(
            Scanner sc, Inventory inv) {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Price: ");
        try {
            double price = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Quantity: ");
            int qty = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Category: ");
            String category = sc.nextLine().trim();
            inv.addItem(new Item(name, price, qty, Category.valueOf(category.toUpperCase())));
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, Item not added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Category, Item not added. Valid Categories are: " +
                    Arrays.toString(Category.values()));
        }
    }

    private static void handleRemove(
            Scanner sc, Inventory inv) {
        System.out.print("Item Name to remove: ");
        try {
            inv.removeItem(sc.nextLine().trim());
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleUpdate(
            Scanner sc, Inventory inv) {
        System.out.print("Item name: ");
        String name = sc.nextLine().trim();
        System.out.print("New Quantity: ");
        try {
            int qty = Integer.parseInt(sc.nextLine().trim());
            inv.updateQuantity(name, qty);
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleSearch(
            Scanner sc, Inventory inv) {
        System.out.print("Search name: ");
        String name = sc.nextLine().trim();
        inv.findItem(name).ifPresentOrElse(
                item -> System.out.println("Found: " + item),
                () -> System.out.println("Not found: " + name));
    }

    private static void printMenu() {
        System.out.println(
                "Commands: add | remove | update | search | list | bycategory | quit");
    }

}