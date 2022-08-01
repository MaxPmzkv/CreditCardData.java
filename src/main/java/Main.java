import au.com.bytecode.opencsv.CSVReader;
import java.io.*;
import java.util.*;
public class Main {


    public static final String CSV_FILENAME = "data/movementList.csv";
    private static final ArrayList<String> inc = new ArrayList<>();


    public static void main(String[] args) {

        Movements bank = new Movements(CSV_FILENAME);
        System.out.println("Сумма доходов: " + bank.getIncomeSum());
        System.out.println("Сумма расходов: " + bank.getExpenseSum());
        bank.getOrganizationCheck();
        try {

            CSVReader reader = new CSVReader(new FileReader(CSV_FILENAME), ',', '"', 1);

            List<String[]> allRows = reader.readAll();


            for (String[] row : allRows) {
                //System.out.println(Arrays.toString(row));
                inc.add(row[7].replaceAll(",", "."));


            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        double outcome = 0;
        for(int i = 0; i < inc.size(); i++){
            outcome += Double.parseDouble(inc.get(i));

        }
        System.out.println("\nРасход " + outcome);
        




    }
}
