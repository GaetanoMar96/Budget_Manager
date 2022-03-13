package budget;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        float income = 0;
        Scanner sc = new Scanner(System.in);
        Payment payment = new Payment();
        float inc = 0;
        while(true) {
            print_actions();
            int choice = sc.nextInt();
            if(choice == 0) {
                System.out.println();
                break; }
            switch (choice) {
                case 1:
                    System.out.println();
                    System.out.println("Enter income:");
                    inc = sc.nextInt();
                    income += inc;
                    System.out.println("Income was added!");
                    System.out.println();
                    break;

                case 2:
                    while(true) {
                        System.out.println();
                        String category = payment.category_purchase(sc);
                        System.out.println();
                        if (category.equals(""))
                            break;
                        System.out.println("Enter purchase name:");
                        sc.nextLine();
                        String name = sc.nextLine();
                        System.out.println("Enter its price:");
                        float price = Float.parseFloat(sc.nextLine());
                        payment.add_purchase(name, price, category);
                        income -= price;
                    }
                    break;

                case 3:
                    while(true) {
                        System.out.println();
                        boolean condition = payment.get_purchase(sc);
                        System.out.println();
                        if (!condition)
                            break;
                    }
                    break;

                case 4:
                    System.out.println();
                    System.out.println("Balance: $" + income);
                    System.out.println();
                    break;

                case 5:
                    payment.save_purchases(inc);
                    System.out.println();
                    System.out.println("Purchases were saved!");
                    System.out.println();
                    break;

                case 6:
                    income = payment.load_purchases();
                    System.out.println();
                    System.out.println("Purchases were loaded!");
                    System.out.println();
                    break;

                case 7:
                    while(true) {
                        System.out.println();
                        payment.print_sort();
                        int sort = sc.nextInt();
                        System.out.println();
                        if (sort == 4)
                            break;
                        else if (sort == 1)
                            payment.sort_all();
                        else if (sort == 2)
                            payment.sort_by_type();
                        else {

                            System.out.println("Choose the type of purchase");
                            System.out.println("1) Food");
                            System.out.println("2) Clothes");
                            System.out.println("3) Entertainment");
                            System.out.println("4) Other");
                            int category = sc.nextInt();
                            payment.sort_by_certain_type(payment.add_map.get(category));
                        }
                    }
                    break;
            }
        }
        sc.close();
        System.out.println("Bye!");
    }

    public static void print_actions() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("0) Exit");
    }

}


