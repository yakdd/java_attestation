import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static ArrayList<ClassToy> toys = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        int count = 0;

        while (true) {
            count++;
            System.out.println("Добавить еще игрушку? - нажми 1\nХватит? - нажми 0");
            String next = scan.nextLine();
            if (next.equals("0")) {
                scan.close();
                break;
            } else {
                addToy(count);
            }
        }

        printAll(toys);

    }

    private static void addToy(int count) {
        int type = new Random().nextInt(5);
        switch (type) {
            case 0:
                ClassToy car = new ClassToy(count, "Машика", 15);
                toys.add(car);
                break;
            case 1:
                ClassToy doll = new ClassToy(count, "Кукла", 10);
                toys.add(doll);
                break;
            case 2:
                ClassToy plush = new ClassToy(count, "Плюшевая игрушка", 10);
                toys.add(plush);
                break;
            case 3:
                ClassToy board = new ClassToy(count, "Настольная игра", 20);
                toys.add(board);
                break;
            case 4:
                ClassToy kit = new ClassToy(count, "Конструктор", 25);
                toys.add(kit);
                break;
            case 5:
                ClassToy robot = new ClassToy(count, "Робот", 20);
                toys.add(robot);
                break;
        }
    }

    private static void printAll(ArrayList<ClassToy> toys) {
        for (ClassToy toy : toys) {
            System.out.println(toy);
        }
    }
}