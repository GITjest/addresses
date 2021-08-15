package reader;

import model.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<Location> read(String fileName) {
        List<Location> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if(values.length > 1) {
                    records.add(new Location(csvValueToDouble(values[0]), csvValueToDouble(values[1])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    private static double csvValueToDouble(String value) {
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
