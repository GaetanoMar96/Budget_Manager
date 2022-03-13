package budget;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Payment {
    public Map<String, List<Purchase>> categories = new HashMap<>();
    public List<Purchase> foods = new ArrayList<>();
    public List<Purchase> clothes = new ArrayList<>();
    public List<Purchase> entertainments = new ArrayList<>();
    public List<Purchase> others = new ArrayList<>();
    public List<Purchase> all = new ArrayList<>();

    public float tot_sum = 0;
    public float tot_sum_foods = 0;
    public float tot_sum_clothes = 0;
    public float tot_sum_entertainments = 0;
    public float tot_sum_others = 0;

    public Map<Integer, String> add_map =
            Map.of(1, "Food",
                    2, "Clothes",
                    3, "Entertainment",
                    4, "Other",
                    5, "Back");

    public Map<Integer, String> get_map =
            Map.of(1, "Food",
                    2, "Clothes",
                    3, "Entertainment",
                    4, "Other",
                    5, "All",
                    6, "Back");

    public Payment() {

    }

    public String category_purchase(Scanner sc) {
        list_actions();
        int choice = sc.nextInt();
        if (choice == 5)
            return "";
        return add_map.get(choice);
    }

    public void add_purchase(String name, Float price, String category) {
        Purchase purchase = new Purchase(name, price, category);
        tot_sum += price;
        handle_category(category, purchase);
        System.out.println("Purchase was added!");
    }

    public boolean get_purchase(Scanner sc) {
        print_actions();
        int choice = sc.nextInt();
        if (choice == 6)
            return false;
        System.out.println();
        String category = get_map.get(choice);
        if (categories.get(category).isEmpty())
            System.out.println("The purchase list is empty");
        else {
            System.out.println(category + ":");
            for (Purchase purchase: categories.get(category))
                System.out.printf("%s $%.2f\n",purchase.getName(),purchase.getPrice());
            switch (category) {
                case "Food":
                    System.out.printf("Total sum: $%.2f",tot_sum_foods);
                    break;
                case "Clothes":
                    System.out.printf("Total sum: $%.2f",tot_sum_clothes);
                    break;
                case "Entertainment":
                    System.out.printf("Total sum: $%.2f",tot_sum_entertainments);
                    break;
                case "Other":
                    System.out.printf("Total sum: $%.2f",tot_sum_others);
                    break;
                case "All":
                    System.out.printf("Total sum: $%.2f", tot_sum);
                    break;
            }
        }
        return true;
    }

    public void list_actions() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");
    }

    public void print_actions() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) All");
        System.out.println("6) Back");
    }

    public void print_sort() {
        System.out.println();
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
    }

    public void handle_category(String category, Purchase purchase) {
        all.add(purchase);
        categories.put("All", all);
        switch (purchase.getCategory()) {
            case "Food":
                tot_sum_foods += purchase.getPrice();
                foods.add(purchase);
                categories.put(category, foods);
                break;
            case "Clothes":
                tot_sum_clothes += purchase.getPrice();
                clothes.add(purchase);
                categories.put(category, clothes);
                break;
            case "Entertainment":
                tot_sum_entertainments += purchase.getPrice();
                entertainments.add(purchase);
                categories.put(category, entertainments);
                break;
            case "Other":
                tot_sum_others += purchase.getPrice();
                others.add(purchase);
                categories.put(category, others);
                break;
        }
    }

    public void save_purchases(float income) {
        float price;
        income = Float.parseFloat(new DecimalFormat(".##").format(income));
        try {
            File file = new File("purchases.txt");
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file, true);
                writer.write(income + "\n");
                if (!all.isEmpty()) {
                    for(Purchase purchase : all) {
                        price = purchase.getPrice();
                        price = Float.parseFloat(new DecimalFormat(".00").format(price));
                        writer.write(purchase.getCategory() + "," + purchase.getName() + "," + price + "\n");
                    }
                }
                writer.close(); }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float load_purchases() {
        File f = new File("purchases.txt");
        float income = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String[] list_of_purchase;
            line = br.readLine();
            income = Float.parseFloat(line);
            while ((line = br.readLine()) != null) {
                list_of_purchase = line.split(",");
                float price = Float.parseFloat(list_of_purchase[2]);
                Purchase purchase = new Purchase(list_of_purchase[1], price, list_of_purchase[0]);
                tot_sum += price;
                handle_category(list_of_purchase[0], purchase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return income - tot_sum;
    }

    public void sort_by_type() {
        System.out.println();
        LinkedHashMap<String, Float> reverseSortedMap = new LinkedHashMap<>();
        System.out.println("Types:");
        Map<String, Float> types_map =
                Map.of("Food -", tot_sum_foods,
                        "Entertainment -", tot_sum_entertainments,
                        "Clothes -", tot_sum_clothes,
                        "Other -", tot_sum_others);
        types_map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));;
        for(Map.Entry<String, Float> entry : reverseSortedMap.entrySet()) {
            if (entry.getValue() == 0)
                System.out.printf("%s $%d\n", entry.getKey(), 0);
            else
                System.out.printf("%s $%.2f\n", entry.getKey(), entry.getValue());
        }
        if (tot_sum == 0)
            System.out.printf("Total sum: $%d", 0);
        else
            System.out.printf("Total sum: $%.2f", tot_sum);
    }

    public void sort_all() {
        System.out.println();
        if (all.isEmpty())
            System.out.println("The purchase list is empty!");
        else {
            System.out.println("All:");
            all.sort((o1, o2) -> Float.compare(o2.getPrice(), o1.getPrice()));
            for (Purchase purchase : all)
                System.out.printf("%s $%.2f\n", purchase.getName(), purchase.getPrice());
            System.out.printf("Total: $%.2f", tot_sum);
        }
    }

    public void sort_by_certain_type(String category) {
        System.out.println();
        switch (category) {
            case "Food":
                if (foods.isEmpty())
                    System.out.println("The purchase list is empty!");
                else {
                    System.out.println(category + ":");
                    foods.sort((o1, o2) -> Float.compare(o2.getPrice(), o1.getPrice()));
                    for (Purchase purchase : foods)
                        System.out.printf("%s $%.2f\n", purchase.getName(), purchase.getPrice());
                    System.out.printf("Total sum: $%.2f",tot_sum_foods);
                }
                break;
            case "Clothes":
                if (clothes.isEmpty())
                    System.out.println("The purchase list is empty!");
                else {
                    System.out.println(category + ":");
                    clothes.sort((o1, o2) -> Float.compare(o2.getPrice(), o1.getPrice()));
                    for (Purchase purchase : clothes)
                        System.out.printf("%s $%.2f\n", purchase.getName(), purchase.getPrice());
                    System.out.printf("Total sum: $%.2f",tot_sum_clothes);
                }
                break;
            case "Entertainment":
                if (entertainments.isEmpty())
                    System.out.println("The purchase list is empty!");
                else {
                    System.out.println(category + ":");
                    entertainments.sort((o1, o2) -> Float.compare(o2.getPrice(), o1.getPrice()));
                    for (Purchase purchase : entertainments)
                        System.out.printf("%s $%.2f\n", purchase.getName(), purchase.getPrice());
                    System.out.printf("Total sum: $%.2f",tot_sum_entertainments);
                }
                break;
            case "Other":
                if (others.isEmpty())
                    System.out.println("The purchase list is empty!");
                else {
                    System.out.println(category + ":");
                    others.sort((o1, o2) -> Float.compare(o2.getPrice(), o1.getPrice()));
                    for (Purchase purchase : others)
                        System.out.printf("%s $%.2f\n", purchase.getName(), purchase.getPrice());
                    System.out.printf("Total sum: $%.2f",tot_sum_others);
                }
                break;
        }
    }
}
