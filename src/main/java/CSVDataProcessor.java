import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVDataProcessor {

    public static String DATASET_CSV1 = "dataset1.csv";
    public static String DATASET_CSV2 = "dataset2.csv";

    public static String HEADER_FIRSTNAME = "first_name";
    public static String HEADER_LASTNAME = "last_name";
    public static String HEADER_PRICE = "price";
    public static String HEADER_ABOVE100 = "above_100";
    public static String PROCESSED_FILE_NAME= "\\Processed.csv";

    public static String DELIMITER_SPACE = " ";

    public static void main(String args[]) {
        processData();
    }

    // Process the CSV files and generate a result CSV file
    //    Processing tasks:
    //    Split the name field into first_name, and last_name
    //    Remove any zeros prepended to the price field
    //    Delete any rows which do not have a name
    //    Create a new field named above_100, which is true if the price is strictly greater than 100
    public static void processData(){
        // Read Dataset1 and Dataset2
        List<String[]> datasetList = readDataLineByLine(CSVDataProcessor.class.getResource(DATASET_CSV1).getPath());
        datasetList.addAll(readDataLineByLine(CSVDataProcessor.class.getResource(DATASET_CSV2).getPath()));

        // Add header row for the new processed CSV file to be generated
        List<String[]> processedDatasetList = new ArrayList<>();
        String[] processedDataset= {HEADER_FIRSTNAME,HEADER_LASTNAME,HEADER_PRICE,HEADER_ABOVE100};
        processedDatasetList.add(processedDataset);

        int linecount = 1;
        for(String[] data:datasetList){
            linecount +=1;
            if(data.length == 2){
                // If the data row has both Name and Price field
                if(!data[0].isEmpty()){
                    processedDataset = new String[4];
                    // First Name
                    processedDataset[0]  = data[0].substring(0,data[0].lastIndexOf(DELIMITER_SPACE));
                    // Last Name
                    processedDataset[1]  = data[0].substring(data[0].lastIndexOf(DELIMITER_SPACE));
                    // Price. Check if there is leading 0 and remove them in price column
                    processedDataset[2] = data[1] .replaceAll("^0+(?=.)", "");
                    // Above 100. Check if above 100 and mark it as true
                    if(Double.parseDouble(data[1]) > 100){
                        processedDataset[3] = "true";
                    }
                }// If the data row has only name
                else if(!data[1].isEmpty()){
                    // check and process if the data is Name
                    processedDataset = new String[4];
                    // First Name
                    processedDataset[0]  = data[1].substring(0,data[1].lastIndexOf(DELIMITER_SPACE));
                    // Last Name
                    processedDataset[1]  = data[1].substring(data[1].lastIndexOf(DELIMITER_SPACE));
                }
                processedDatasetList.add(processedDataset);
            }
        }

        Path resourceDirectory = Paths.get("src","main","resources").toAbsolutePath();
        try (CSVWriter writer = new CSVWriter(new FileWriter(resourceDirectory.toString() + PROCESSED_FILE_NAME))) {
            writer.writeAll(processedDatasetList);
        } catch (Exception e) {
            e.printStackTrace();
        }
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