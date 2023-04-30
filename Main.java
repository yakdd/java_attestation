import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
// import java.util.Map;
import java.util.Random;
import java.util.Scanner;
// import java.util.Map.Entry;

public class Main {

    static String[] toyType = { "Машника", "Кукла", "Плюшевая игрушка", "Настольная игра", "Конструктор", "Робот" };

    static ArrayList<ClassToy> toys = new ArrayList<>();
    static ArrayList<ClassToy> prizeToys = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);
    static HashMap<String, Integer> weightMap;
    static HashMap<String, Integer[]> scaleMap;

    public static void main(String[] args) {

        System.out.println("\nРозыгрыш в магазине игрушек.".toUpperCase());

        weightMap = putWeights(toyType);

        int count = 0;
        for (int i = 1; i <= toyType.length; i++) {
            count = i;
            addToy(count, weightMap);
        }
        System.out.printf("Добавлено %d игрушек для розыгрыша.\n", toyType.length);

        while (true) {
            System.out.println("Добавить еще: 1\nХватит: 0");
            String next = scan.nextLine();
            if (next.equals("0")) {
                scan.close();
                break;
            } else {
                count++;
                addToy(count, weightMap);
            }
        }

        printAll(toys, "Набор игрушек для розыгрыша:");

        scaleMap = lotteryScale(weightMap);

        // for (var pair : scaleMap.entrySet()) {
        // System.out.printf("%s: ", pair.getKey());
        // System.out.println(Arrays.toString(pair.getValue()));
        // }

        int prizewinner = 3; // количество розыгрышей

        for (int i = 0; i < prizewinner; i++) {
            while (true) {
                ClassToy prizeToy = startLottery(scaleMap, toys);
                if (prizeToy != null) {
                    int toyIndex = toys.indexOf(prizeToy);
                    toys.remove(toyIndex);
                    break;
                }
            }
            printAll(prizeToys, "Призы:");
        }

        // printAll(toys, "Набор игрушек для розыгрыша:");
    }

    private static HashMap<String, Integer> putWeights(String[] toyType) {
        /**
         * Расстановка весов для игрушек пользователем.
         * Для первых пяти видов игрушек веса определяются пользователем,
         * для последнего вида вес расчитывается атоматически.
         */
        weightMap = new HashMap<>();
        System.out.printf("\nРасставьте веса для каждого из %d видов игрушек.\n", toyType.length);

        int rest = 99;
        int currentWeight;
        for (int i = 0; i < toyType.length; i++) {
            if (i == toyType.length - 1) {
                currentWeight = rest;
                System.out.printf("Доля последней игрушки %s = %d\n", toyType[i], rest);
                weightMap.put(toyType[i], currentWeight);
            } else {
                System.out.printf("%d. %s: ", i + 1, toyType[i]);
                System.out.printf("выберете число от 1 до %d: ", rest);

                while (true) {
                    currentWeight = scan.nextInt();
                    scan.nextLine();
                    if (currentWeight < rest && currentWeight > 0) {
                        weightMap.put(toyType[i], currentWeight);
                        rest -= currentWeight;
                        break;
                    } else {
                        System.out.print("Ошибка ввода. Попробуйте еще раз: ");
                    }
                }
            }
        }
        return weightMap;
    }

    private static int setWeight(String toyType, HashMap<String, Integer> weightMap) {
        int toyWeight = 0;
        for (var pair : weightMap.entrySet()) {
            if (pair.getKey() == toyType) {
                toyWeight = pair.getValue();
            }
        }
        return toyWeight;
    }

    private static void addToy(int count, HashMap<String, Integer> weightMap) {
        int type = new Random().nextInt(5);
        String toyType;
        switch (type) {
            case 0:
                toyType = "Машника";
                ClassToy car = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                toys.add(car);
                break;
            case 1:
                toyType = "Кукла";
                ClassToy doll = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                toys.add(doll);
                break;
            case 2:
                toyType = "Плюшевая игрушка";
                ClassToy plush = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                toys.add(plush);
                break;
            case 3:
                toyType = "Настольная игра";
                ClassToy board = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                toys.add(board);
                break;
            case 4:
                toyType = "Конструктор";
                ClassToy kit = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                toys.add(kit);
                break;
            case 5:
                toyType = "Робот";
                ClassToy robot = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                toys.add(robot);
                break;
        }
    }

    private static HashMap<String, Integer[]> lotteryScale(HashMap<String, Integer> weightMap) {
        /**
         * По данным карты "Игрушка=вес" составляем шкалу весов для розыгрыша
         * "Игрушка=[мин.значение, макс.значение]"
         * Т.е. например:
         * игрушка1, вес=15 => [1, 15]
         * игрушка2, вес=5 => [16, 20]
         * игрушка3, вес=11 => [21, 31]
         * ...
         * игрушка6 [х, 99]
         */
        scaleMap = new HashMap<>();

        int scale = 0;
        for (var pair : weightMap.entrySet()) {
            int leftLimit = scale + 1;
            int rightLimit = scale + pair.getValue();
            scale = rightLimit;
            Integer[] arr = { leftLimit, rightLimit };
            scaleMap.put(pair.getKey(), arr);
        }
        return scaleMap;
    }

    private static ClassToy startLottery(HashMap<String, Integer[]> scaleMap, ArrayList<ClassToy> toys) {

        int prize = new Random().nextInt(99) + 1;
        for (var pair : scaleMap.entrySet()) {
            if (prize >= pair.getValue()[0] && prize <= pair.getValue()[1]) {
                String prizeNameToy = pair.getKey();
                System.out.printf("   Выпало: >>> %s\n", prizeNameToy);
                boolean find = true;
                for (ClassToy toy : toys) {
                    if (toy.getName().equals(prizeNameToy)) {
                        prizeToys.add(toy);
                        return toy;
                    } else {
                        find = false;
                    }
                }
                if (!find) {
                    System.out.println("   ... Такой игрушки нет в призовом фонде. Повторим розыгрыш!");
                }
            }
        }
        return null;
    }

    private static void printAll(ArrayList<ClassToy> toys, String title) {

        System.out.printf("--- %s ---------------\n", title);
        for (int i = 0; i < toys.size(); i++) {
            System.out.printf("%d. ", i + 1);
            System.out.println(toys.get(i));
        }
        System.out.println("-----------------------------");
    }
}