import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main implements Interface {

    static String[] toyType = { "Машинка", "Кукла", "Плюшевая игрушка", "Настольная игра", "Конструктор", "Робот" };
    static int prizewinner = 3; // количество розыгрышей

    static ArrayList<ClassToy> toys = new ArrayList<>();
    static PriorityQueue<ClassToy> prizeToys = new PriorityQueue<>();
    static HashMap<String, Integer> weightMap;
    static HashMap<String, Integer[]> scaleMap;
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        System.out.println("\nРозыгрыш в магазине игрушек.".toUpperCase());

        // Определение весов для игрушек:
        weightMap = Interface.putWeights(toyType);

        // Добавление игрушек для розыгрыша:
        int count = 0;
        for (int i = 1; i <= toyType.length; i++) {
            count = i;
            Interface.addToy(count, weightMap);
        }
        System.out.printf("\nДобавлено %d игрушек для розыгрыша.\n", toyType.length);

        while (true) {
            System.out.println("Добавить еще: 1\nХватит: 0");
            String next = scan.nextLine();
            if (next.equals("0")) {
                scan.close();
                break;
            } else {
                count++;
                Interface.addToy(count, weightMap);
            }
        }
        Interface.printAll(toys);

        // Составление шкалы весов для розыгрыша:
        scaleMap = Interface.lotteryScale(weightMap);
        // Interface.printScaleWeights(scaleMap);

        // Начало розыгрыша:
        for (int i = 0; i < prizewinner; i++) {
            while (true) {
                ClassToy prizeToy = Interface.startLottery(scaleMap, toys);
                if (prizeToy != null) {
                    prizeToys.add(prizeToy);
                    int toyIndex = toys.indexOf(prizeToy);
                    toys.remove(toyIndex);
                    break;
                }

            }
        }
        Interface.printPrize(prizeToys);

        // Выдача призов:
        Interface.awardPrize(prizeToys);
    }
}