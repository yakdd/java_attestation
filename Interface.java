import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.Random;

public interface Interface {

    static HashMap<String, Integer> putWeights(String[] toyType) {
        /**
         * Расстановка весов для игрушек пользователем.
         * Для всех видов игрушек кроме последней веса определяются пользователем,
         * для последнего вида вес расчитывается атоматически.
         */
        Main.weightMap = new HashMap<>();
        System.out.printf("\nРасставьте веса (частоту выпадения) для каждого из %d видов игрушек.\n", toyType.length);

        int rest = 99;
        int currentWeight;
        for (int i = 0; i < toyType.length; i++) {
            if (i == toyType.length - 1) {
                System.out.printf("Доля последней игрушки %s = %d\n", toyType[i], rest);
                Main.weightMap.put(toyType[i], rest);
            } else {
                System.out.printf("%d. %s: ", i + 1, toyType[i]);
                System.out.printf("выберете число от 1 до %d: ", rest);

                while (true) {
                    currentWeight = Main.scan.nextInt();
                    Main.scan.nextLine();
                    if (currentWeight < rest && currentWeight > 0) {
                        Main.weightMap.put(toyType[i], currentWeight);
                        rest -= currentWeight;
                        break;
                    } else {
                        System.out.print("Ошибка ввода. Попробуйте еще раз: ");
                    }
                }
            }
        }
        return Main.weightMap;
    }

    static int setWeight(String toyType, HashMap<String, Integer> weightMap) {
        int toyWeight = 0;
        for (var pair : weightMap.entrySet()) {
            if (pair.getKey().equals(toyType)) {
                toyWeight = pair.getValue();
            }
        }
        return toyWeight;
    }

    static void addToy(int count, HashMap<String, Integer> weightMap) {
        String toyType;
        int type = new Random().nextInt(5);
        switch (type) {
            case 0:
                toyType = "Машинка";
                ClassToy car = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                Main.toys.add(car);
                break;
            case 1:
                toyType = "Кукла";
                ClassToy doll = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                Main.toys.add(doll);
                break;
            case 2:
                toyType = "Плюшевая игрушка";
                ClassToy plush = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                Main.toys.add(plush);
                break;
            case 3:
                toyType = "Настольная игра";
                ClassToy board = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                Main.toys.add(board);
                break;
            case 4:
                toyType = "Конструктор";
                ClassToy kit = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                Main.toys.add(kit);
                break;
            case 5:
                toyType = "Робот";
                ClassToy robot = new ClassToy(count, toyType, setWeight(toyType, weightMap));
                Main.toys.add(robot);
                break;
        }
    }

    static HashMap<String, Integer[]> lotteryScale(HashMap<String, Integer> weightMap) {
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
        Main.scaleMap = new HashMap<>();

        int scale = 0;
        for (var pair : weightMap.entrySet()) {
            int leftLimit = scale + 1;
            int rightLimit = scale + pair.getValue();
            scale = rightLimit;
            Integer[] arr = { leftLimit, rightLimit };
            Main.scaleMap.put(pair.getKey(), arr);
        }
        return Main.scaleMap;
    }

    static ClassToy startLottery(HashMap<String, Integer[]> scaleMap, ArrayList<ClassToy> toys) {

        int prize = new Random().nextInt(99) + 1;
        for (var pair : scaleMap.entrySet()) {
            // проеряем, в какой диапазон попадает случайное число prize:
            if (prize >= pair.getValue()[0] && prize <= pair.getValue()[1]) {
                String prizeNameToy = pair.getKey();
                System.out.printf("   Выпало: >>> %s\n", prizeNameToy);
                boolean find = true;
                for (ClassToy toy : toys) {
                    if (toy.getName().equals(prizeNameToy)) {
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

    static void awardPrize(Queue<ClassToy> prizeToys) throws IOException {

        int count = prizeToys.size();
        while (!prizeToys.isEmpty()) {

            ClassToy nextToy = prizeToys.poll();
            try (FileWriter file = new FileWriter("prize.txt", true)) {
                file.append(String.format("%s-й приз: %s, id: %d, вес: %d.\n",
                        count,
                        nextToy.getName(),
                        nextToy.getId(),
                        nextToy.getWeight()));
            } catch (IOException ex) {
                System.out.println("Ошибка записи в файл.");
            }

            System.out.printf("Выдача призов! %d место: %s\n", count, nextToy);
            count--;
        }
    }

    static void printAll(ArrayList<ClassToy> toys) {

        System.out.println("-----------------------------");
        System.out.println("Набор игрушек:");
        for (int i = 0; i < toys.size(); i++) {
            System.out.printf("%d. ", i + 1);
            System.out.println(toys.get(i));
        }
        System.out.println("-----------------------------");
    }

    static void printPrize(Queue<ClassToy> toys) {

        System.out.println("-----------------------------");
        System.out.println("Призы:");
        for (ClassToy prizeToy : toys) {
            System.out.println(prizeToy);
        }
        System.out.println("-----------------------------");
    }

    static void printScaleWeights(HashMap<String, Integer[]> scaleMap) {
        for (var pair : scaleMap.entrySet()) {
            System.out.printf("%s: ", pair.getKey());
            System.out.println(Arrays.toString(pair.getValue()));
        }
    }
}
