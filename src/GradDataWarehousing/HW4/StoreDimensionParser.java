package GradDataWarehousing.HW4;

import java.io.*;

/**
 * Created by robertseedorf on 11/2/17.
 */
public class StoreDimensionParser {

    static final String OUTPUT_PATH = "." + File.separatorChar + "StoreDimensionTable.csv"; //results go here
    static final String sep = ", ";
    static BufferedWriter bw;

    static int store_key = 1;
    static String store_manager = "you";
    static String store_street_address = "Address";
    static String store_town = "A Town";
    static String store_zip = "XXXXX";
    static String store_phone = "555-555-55555";
    static String store_state = "NJ";

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

    public static void main(String[] args) {
        try {
            FileOutputStream fos = new FileOutputStream(OUTPUT_PATH);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            write("Store Key",
                    "Store Manager",
                    "Store Address",
                    "Store Town",
                    "Store Zip",
                    "Store Phone",
                    "Store State");

            // Insert you values for all fields here
            write(store_key,
                    store_manager,
                    store_street_address,
                    store_town,
                    store_zip,
                    store_phone,
                    store_state);
            // Insert you values for all fields here
            write(store_key,
                    store_manager,
                    store_street_address,
                    store_town,
                    store_zip,
                    store_phone,
                    store_state);
            // Insert you values for all fields here
            write(store_key,
                    store_manager,
                    store_street_address,
                    store_town,
                    store_zip,
                    store_phone,
                    store_state);

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
