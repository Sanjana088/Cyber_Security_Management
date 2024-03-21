package Users;

import java.util.Scanner;

public class Program {


    private int id;
    private String scope;
    private String rules;
    private String description;
    private double rewardsAmount;

    public Program(  String scope, String rules, String description, double rewardsAmount){
        this.scope = scope;
        this.rules = rules;
        this.description = description;
        this.rewardsAmount = rewardsAmount;
    }
    // Constructor including id (for existing programs)
    public Program(int id, String scope, String rules, String description, double rewardsAmount) {
        this.id = id;
        this.scope = scope;
        this.rules = rules;
        this.description = description;
        this.rewardsAmount = rewardsAmount;
    }

    public int getId() {
        return id;
    }



    public String getScope(){
        return scope;
    }

    public void setScope(String scope){
        this.scope = scope;
    }

    public String getRules(){
        return rules;
    }

    public void setRules(String rules){
        this.rules = rules;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public double getRewardsAmount(){
        return rewardsAmount;
    }

    public void setRewardsAmount(double rewardsAmount){
        this.rewardsAmount = rewardsAmount;
    }

    public static Program createProgram(Scanner scanner){
        System.out.println("Enter Program Scope:");
        String scope = scanner.nextLine();

        System.out.println("Enter Program Rules:");
        String rules = scanner.nextLine();

        System.out.println("Enter Program Description:");
        String description = scanner.nextLine();

        System.out.println("Enter Rewards Amount:");
        double rewardsAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        return new Program(scope, rules, description, rewardsAmount);
    }

    public void editProgram(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Select an option to edit:");
            System.out.println("1. Edit Program Scope");
            System.out.println("2. Edit Program Rules");
            System.out.println("3. Edit Program Description");
            System.out.println("4. Edit Rewards Amount");
            System.out.println("5. Back to Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Enter new Program Scope:");
                    setScope(scanner.nextLine());
                    System.out.println("Program Scope updated.");
                    break;
                case 2:
                    System.out.println("Enter new Program Rules:");
                    setRules(scanner.nextLine());
                    System.out.println("Program Rules updated.");
                    break;
                case 3:
                    System.out.println("Enter new Program Description:");
                    setDescription(scanner.nextLine());
                    System.out.println("Program Description updated.");
                    break;
                case 4:
                    System.out.println("Enter new Rewards Amount:");
                    double newAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character
                    if (newAmount >= 0) {
                        setRewardsAmount(newAmount);
                        System.out.println("Rewards Amount updated.");
                    } else {
                        System.out.println("Invalid amount. Please enter a positive number.");
                    }
                    break;
                case 5:
                    System.out.println("Returning to Menu...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    @Override
    public String toString() {
        return "Program{" +
                "scope='" + scope + '\'' +
                ", rules='" + rules + '\'' +
                ", description='" + description + '\'' +
                ", rewardsAmount=" + rewardsAmount +
                '}';
    }

}
