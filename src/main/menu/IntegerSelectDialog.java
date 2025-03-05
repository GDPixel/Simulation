package main.menu;

import java.util.List;
import java.util.Scanner;

public class IntegerSelectDialog implements Dialog<Integer> {

    private final String title;
    private final String error;
    private final List<Integer> values;

    public IntegerSelectDialog(String title, String error, List<Integer> values) {
        this.title = title;
        this.error = error;
        this.values = values;
    }

    @Override
    public Integer input() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(title);
            String input = scanner.nextLine();

            if (isInteger(input)) {
                var result = Integer.parseInt(input);
                if (values.contains(result)) {
                    return result;
                }
            }
            System.out.println(error);
        }
    }

    private boolean isInteger(String input) {
        return input.matches("^-?\\d+$");
    }
}