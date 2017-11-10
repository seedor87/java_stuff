package GradDataWarehousing.HW4;

import java.io.*;
import java.util.Scanner;

import static GradDataWarehousing.HWResources.Utils.isMilkSku;
import static Utils.ConsolePrinting.FG_CYAN;
import static Utils.ConsolePrinting.print;
import static Utils.ConsolePrinting.println;

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
    static int index;
    static boolean skip = false;
    static ProductsClassParser pcp;

    static String manufacturer;
    static String product_name;
    static String size;
    static String type;
    static String sku;
    static String price;
    static int product_key = 0;
    static String product_class_id = "";
    static String subcategory = "";
    static String category = "";
    static String department = "";
    static String family = "";
    static int num_per_case = 12;
    static String brand_name = "";
    static String supplier = "";
    static int source = 0;


    public static boolean attemptToCategorize(String name, String type, String manuf) {
        for (int i = 0; i < pcp.PRODUCT_CATEGORY.length; i++) {
            if(name.contains(pcp.PRODUCT_CATEGORY[i]) || type.contains(pcp.PRODUCT_CATEGORY[i]) || manuf.contains(pcp.PRODUCT_CATEGORY[i]) ||
                    name.contains(pcp.PRODUCT_SUBCATEGORY[i]) || type.contains(pcp.PRODUCT_SUBCATEGORY[i]) || manuf.contains(pcp.PRODUCT_SUBCATEGORY[i]) ||
                    name.contains(pcp.PRODUCT_DEPARTMENT[i]) || type.contains(pcp.PRODUCT_DEPARTMENT[i]) || manuf.contains(pcp.PRODUCT_DEPARTMENT[i])) {
                product_class_id =  pcp.PRODUCT_CLASS_ID[i];
                subcategory =       pcp.PRODUCT_SUBCATEGORY[i];
                category =          pcp.PRODUCT_CATEGORY[i];
                department =        pcp.PRODUCT_DEPARTMENT[i];
                family =            pcp.PRODUCT_FAMILY[i];
                return true;
            }
        }
        return false;
    }
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
                    "Supplier",
                    "Source");

            pcp = new ProductsClassParser();
            Scanner scan = new Scanner(System.in);

            String line = br.readLine(); // throw away first line
            while((line = br.readLine()) != null) {

                skip = false;

                product_key++;
                String[] fields = line.split("\\|");
                manufacturer = fields[0];
                brand_name = manufacturer;
                product_name = fields[1];
                size = fields[2];
                type = fields[3];
                sku = fields[4];
                price = fields[5];

                boolean res = attemptToCategorize(product_name, type, manufacturer);
                if(!res) {
                    print(FG_CYAN, product_key, "");
                    println(fields);
                    print("Please Choose which from the key: ");
                    index = scan.nextInt() - 1;
                    scan.nextLine();

                    if (index < 0) {
                        skip = true;
                    }
                }

                if(skip) {
                    product_class_id = "";
                    subcategory = "";
                    category = "";
                    department = "";
                    family = "";
                }

                if(res) {
                    source = 3;
                } else {
                    source = 2;
                }

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
                        supplier,
                        source);
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
