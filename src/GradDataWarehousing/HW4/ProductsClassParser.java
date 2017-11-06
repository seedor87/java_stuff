package GradDataWarehousing.HW4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Utils.ConsolePrinting.println;


/**
 * Created by robertseedorf on 11/3/17.
 */
public class ProductsClassParser {

    static String PRODUCTS_CLASS_PATH = "." +
            File.separatorChar + "src" +
            File.separatorChar + "GradDataWarehousing" +
            File.separatorChar + "HW1" +
            File.separatorChar + "product_class.txt";  // list of categories and such
    static String OUTPUT_PATH = "." + File.separatorChar + "product_class.csv";
    static final String sep = ", ";
    public static String[] PRODUCT_CLASS_ID = new String[113];
    public static String[] PRODUCT_SUBCATEGORY = new String[113];
    public static String[] PRODUCT_CATEGORY = new String[113];
    public static String[] PRODUCT_DEPARTMENT = new String[113];
    public static String[] PRODUCT_FAMILY = new String[113];
    public static ArrayList<String[]> ALL = new ArrayList<>();

    static BufferedWriter bw;

    static String product_class_id;
    static String product_subcategory;
    static String product_category;
    static String product_department;
    static String product_family;

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

    /**
     * Used to sort based on provided indexes.
     * The indexes are extracted and for each index, for each each element, at the given index the elements are compared until a match is not found and they are sorted
     */
    public static <E extends Comparable> void custSort(List<E[]> arr, int... indexes) {
        arr.sort((E[] o1, E[] o2) -> {
            int val;
            for (int index : indexes) {
                val = o1[index].compareTo(o2[index]);
                if (val != 0) {
                    return val;
                } // else { keep going }
            }
            return 0;
        });
    }

    public ProductsClassParser() {
        try {
            FileInputStream fis = new FileInputStream(PRODUCTS_CLASS_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            FileOutputStream fos = new FileOutputStream(OUTPUT_PATH);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            int index = 0;

            String line = br.readLine(); // throw away first line
            String[] fields = line.split("\t");
            write(fields);
            while((line = br.readLine()) != null) {
                fields = line.split("\t");
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
                ALL.add(fields);

                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        custSort(ALL, 4,3,2,1);

        try {
            for(String[] fields : ALL) {
                write(fields);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new ProductsClassParser();
    }
}
