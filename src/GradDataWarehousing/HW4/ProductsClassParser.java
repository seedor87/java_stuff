package GradDataWarehousing.HW4;

import java.io.*;

import static Utils.ConsolePrinting.println;

/**
 * Created by robertseedorf on 11/3/17.
 */
public class ProductsClassParser {

    static String ALL_PRODUCTS_PATH = "." +
            File.separatorChar + "src" +
            File.separatorChar + "GradDataWarehousing" +
            File.separatorChar + "HW1" +
            File.separatorChar + "product_class.txt";  // list of categories and such
    static final String sep = ", ";
    public static String[] PRODUCT_CLASS_ID = new String[113];
    public static String[] PRODUCT_SUBCATEGORY = new String[113];
    public static String[] PRODUCT_CATEGORY = new String[113];
    public static String[] PRODUCT_DEPARTMENT = new String[113];
    public static String[] PRODUCT_FAMILY = new String[113];

    static String product_class_id;
    static String product_subcategory;
    static String product_category;
    static String product_department;
    static String product_family;

    public ProductsClassParser() {
        try {
            FileInputStream fis= new FileInputStream(ALL_PRODUCTS_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            int index = 0;

            String line = br.readLine(); // throw away first line
            while((line = br.readLine()) != null) {
                String[] fields = line.split("\t");
                product_class_id = fields[0];
                product_subcategory = fields[1];
                product_category = fields[2];
                product_department = fields[3];
                product_family = fields[4];

                PRODUCT_CLASS_ID[index] = product_class_id;
                PRODUCT_SUBCATEGORY[index] = product_subcategory;
                PRODUCT_CATEGORY[index] = product_category;
                PRODUCT_DEPARTMENT[index] = product_department;
                PRODUCT_FAMILY[index] = product_family;

                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ProductsClassParser();
    }
}
