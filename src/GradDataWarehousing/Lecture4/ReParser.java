package GradDataWarehousing.Lecture4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static Utils.ConsolePrinting.println;

/**
 * Created by robertseedorf on 9/26/17.
 */
public class ReParser {

    static String allProductsFilePath = "." +
            File.separatorChar + "src" +
            File.separatorChar + "GradDataWarehousing" +
            File.separatorChar + "HW1" +
            File.separatorChar + "Products";  // pre-processed list of skus and prices

    public static void main(String[] args) {
        try {
            FileInputStream fs= new FileInputStream(allProductsFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line;
            while((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");
                String manufacturer = fields[0];
                String name = fields[1];
                String size = fields[2];
                String type = fields[3];
                String sku = fields[4];
                String price = fields[5];
                println(fields);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
