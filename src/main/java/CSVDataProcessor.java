import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
public class CSVDataProcessor {
    
    public static void main(String args[]) throws URISyntaxException {
        processData();
    }

    // Process the CSV files and generate a result CSV file
    //    Processing tasks:
    //    Split the name field into first_name, and last_name
    //    Remove any zeros prepended to the price field
    //    Delete any rows which do not have a name
    //    Create a new field named above_100, which is true if the price is strictly greater than 100
    public static void processData(){

    }

    // Reads the dataset CSV file line by line
    public static List<String[]> readDataLineByLine(String dataset)
    {
        List<String[]> datasetList = null;
        try(CSVReader reader = new CSVReaderBuilder(
                new FileReader(dataset))
                .withSkipLines(1)           // skip the first line, header info
                .build()){
            datasetList = reader.readAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return datasetList;
    }
}
