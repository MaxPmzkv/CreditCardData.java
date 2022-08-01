import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.readAllLines;

public class Movements {


    private final ArrayList<String> description = new ArrayList<>();
    private final ArrayList<String> income = new ArrayList<>();
    private final ArrayList<String> expense = new ArrayList<>();
    private final Map<String, Double> expenses = new HashMap();
    private String pathMovementsCsv;


    public Movements(String pathMovementsCsv) {
        this.pathMovementsCsv = pathMovementsCsv;

    }

    private String getCsvFilenamePath() {
        return pathMovementsCsv;
    }


    public double getIncomeSum() {
        try {
            List<String> lines = readAllLines(Path.of(getCsvFilenamePath()));
            for (String line : lines) {
                String[] fragments = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", 8);
                if (fragments.length != 8) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }

                income.add(fragments[6]);


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return   income.stream()
        .filter(s -> s.matches("\"?[0-9]+,?[0-9]*\"?"))
                .map(s -> s.replaceAll("\"", ""))
                .mapToDouble(s -> Double.parseDouble(s.replaceAll(",", ".")))
                .sum();


    }

    public double getExpenseSum() {
        try {
            List<String> lines = readAllLines(Path.of(getCsvFilenamePath()));
            for (String line : lines) {
                String[] fragments = line.split(",", 8);
                if (fragments.length != 8) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }

                expense.add(fragments[7]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return expense.stream()
                .filter(s -> s.matches("\"?[0-9]+,?[0-9]*\"?"))
                .map(s -> s.replaceAll("\"", ""))
                .mapToDouble(s -> Double.parseDouble(s.replaceAll(",", ".")))
                .sum();
    }
    public void getOrganizationCheck()
    {
        try {
            List<String> lines = readAllLines(Path.of(getCsvFilenamePath()));
            for (String line : lines) {
                String[] fragments = line.split(",", 8);
                if (fragments.length != 8) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                description.add(fragments[5]);


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        for (int i = 1; i < description.size(); i++) {

            String name;
            double money = 0.0;

            String[] str = description.get(i).replaceAll("\\\\", "/").split(("\\s{4,}"));
            name = str[1];
            name = name.substring(name.lastIndexOf("/") + 1).trim();



            for (int j = 0; j < description.size(); j++) {
                if (description.get(j).contains(name)) {
                    money += Double.parseDouble(expense.get(j)
                            .replaceAll("\"", "")
                            .replaceAll(",", "."));
                }
                expenses.put(name, money);
            }
        }
        expenses.forEach((key, value) -> System.out.println(key + " - " + value));
    }





}
