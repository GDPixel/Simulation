package main.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final int FIRST_ID = 1;

    private final Scanner scanner = new Scanner(System.in);
    private final String title;
    private final String error;
    private final String description;
    private int idCounter = FIRST_ID;
    private final List<Item> items;

    public Menu(String title, String description, String error) {
        this.title = title;
        this.description = description;
        this.error = error;
        items = new ArrayList<>();
    }

    public void addItem(String title, Runnable action) {
        items.add(new Item(idCounter, title, action));
        idCounter++;
    }

    public void show() {
        System.out.println(title);
        for (Item item : items) {
            System.out.printf("%d. %s%n", item.id, item.title);
        }
    }

    public void select() {
        System.out.println(description);
        while (true) {
            String input = scanner.nextLine();
            if (isInteger(input)) {
                int option = Integer.parseInt(input);
                if (FIRST_ID <= option && option <= items.getLast().id) {
                    Item item = items.get(option - FIRST_ID);
                    item.action.run();
                    break;
                }
            }
            System.out.println(error);
        }
    }


    private boolean isInteger(String input) {
        return input.matches("^-?\\d+$");
    }

    private record Item(int id, String title, Runnable action) {
    }
}
