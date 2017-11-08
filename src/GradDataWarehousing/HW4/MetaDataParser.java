package GradDataWarehousing.HW4;

import java.io.*;
import java.util.Random;

/**
 * Created by robertseedorf on 11/2/17.
 */
public class MetaDataParser {

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
    static String type;
    static String sku;
    static String price;
    enum sources {
        original, mapped, matched
    }
    static sources source;
    static int flag = 1;

    /**
     * Method to write particular line to txt file now
     */
    public static void write(Object... args) throws IOException {

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
     * Method to generate one (1) random integer between low (inclusive) and hi (exclusive)
     */
    public static int randRange(int low, int hi) {
        return new Random().nextInt(hi-low) + low;
    }

    public static void main(String[] args) {
        try {
            FileInputStream fis= new FileInputStream(ALL_PRODUCTS_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            FileOutputStream fos = new FileOutputStream(OUTPUT_PATH);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            write("SKU",
                    "Source");
            String line = br.readLine(); // throw away first line
            while((line = br.readLine()) != null) {
                flag = randRange(1,4);
                switch(flag) {
                    case 1:
                        source = sources.original;
                        break;
                    case 2:
                        source = sources.mapped;
                        break;
                    case 3:
                        source = sources.matched;
                        break;
                    default:
                        source = sources.original;
                        break;
                }
                String[] fields = line.split("\\|");
                manufacturer = fields[0];
                product_name = fields[1];
                size = fields[2];
                type = fields[3];
                sku = fields[4];
                price = fields[5];
                write(sku,
                        source);
                flag += 1;
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
