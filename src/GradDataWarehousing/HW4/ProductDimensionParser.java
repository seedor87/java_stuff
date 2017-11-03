package GradDataWarehousing.HW4;

import java.io.*;

import static GradDataWarehousing.HWResources.Utils.isMilkSku;

/**
 * Created by robertseedorf on 9/26/17.
 */
public class ProductDimensionParser {

    static String ALL_PRODUCTS_PATH = "." +
            File.separatorChar + "src" +
            File.separatorChar + "GradDataWarehousing" +
            File.separatorChar + "HW1" +
            File.separatorChar + "Products";  // list of skus and prices
    static final String OUTPUT_PATH = "." + File.separatorChar + "ProductDimensionTable.csv"; //results go here
    static final String sep = ", ";
    static BufferedWriter bw;

    static String manufacturer;
    static String product_name;
    static String size;
    static String category;
    static String sku;
    static String price;
    static int product_key = 0;
    static int product_class_id = 0;
    static String subcategory = "";
    static String department = "";
    static String family = "";
    static int num_per_case = 12;
    static String brand_name = "";
    static String supplier = "";


    /**
     * Method to write particular line to txt file now
     */
    public static void write(Object... args) throws IOException{

        /** Uncomment to print each entry */
//         println(args);

        String delim = "";
        for (Object o : args) {
            bw.write(delim);
            bw.write(o.toString());
            delim = sep;
        }
        bw.write("\n");
    }

    public static void main(String[] args) {
        try {
            FileInputStream fis= new FileInputStream(ALL_PRODUCTS_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            FileOutputStream fos = new FileOutputStream(OUTPUT_PATH);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            write("Product Key",
                    "SKU",
                    "Product Name",
                    "Product Class ID",
                    "Subcategory",
                    "Category",
                    "Department",
                    "Product Family",
                    "Size",
                    "Num Per Case",
                    "Brand Name",
                    "Manufacturer",
                    "Supplier");
            String line = br.readLine(); // throw away first line
            while((line = br.readLine()) != null) {
                product_key++;
                String[] fields = line.split("\\|");
                manufacturer = fields[0];
                product_name = fields[1];
                size = fields[2];
                category = fields[3];
                sku = fields[4];
                price = fields[5];

                if(isMilkSku(Integer.parseInt(sku))) {
                    supplier = "Rowan Dairy";
                } else {
                    supplier = "Rowan Grocery";
                }
                write(product_key,
                        sku,
                        product_name,
                        product_class_id,
                        subcategory,
                        category,
                        department,
                        family,
                        size,
                        num_per_case,
                        brand_name,
                        manufacturer,
                        supplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.flush();
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
