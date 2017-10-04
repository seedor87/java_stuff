package GradDataWarehousing.HW2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static Utils.ConsolePrinting.printlnDelim;

/**
 * created by Joe Alacqua on 9/27/17
 *
 * based on ProductParserForNames by robertseedorf
 */
public class ProductParserForItemTypes {

    static final String DELIM = "\\|";
    static String allProductsFilePath = "." +
            File.separatorChar + "src" +
            File.separatorChar + "GradDataWarehousing" +
            File.separatorChar + "HW1" +
            File.separatorChar + "Products";  // pre-processed list of skus and prices
    static Map<Integer, String> skuItemTypeMap = new HashMap<>();

    public static Map<Integer, String> generateSkuItemTypeMap() {
        /**
         * One example line
         *
         *  Manufacturer|Product Name|Size|itemType|SKU|BasePrice
         */
        try {
            FileInputStream fs= new FileInputStream(allProductsFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line= br.readLine();
            while((line = br.readLine()) != null) {
                String[] fields = line.split(DELIM);
                Integer sku = Integer.parseInt(fields[4]);
                String itemType = fields[3];
                skuItemTypeMap.put(sku, itemType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skuItemTypeMap;
    }

    public static void main(String[] args) {
        printlnDelim("\n", generateSkuItemTypeMap());
    }

}
