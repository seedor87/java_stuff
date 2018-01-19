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
                    "Store StopwatchState");


            store_key = 105;
            // Insert you values for all fields here
            store_manager = "Bob Seedorf";
            store_street_address = "515 Mullica Hill Rd.";
            store_town = "Pleasantville";
            store_zip = "08028";
            store_phone = "609-867-5309";
            store_state = "NJ";
            write(store_key,
                    store_manager,
                    store_street_address,
                    store_town,
                    store_zip,
                    store_phone,
                    store_state);

            store_key = 111;
            store_manager = "Joe Alacqua";
            store_street_address = "201 Mullica Hill Rd.";
            store_town = "Pitman";
            store_zip = "08028";
            store_phone = "856-555-5555";
            store_state = "NJ";
            write(store_key,
                    store_manager,
                    store_street_address,
                    store_town,
                    store_zip,
                    store_phone,
                    store_state);

            store_key = 113;
            // Insert you values for all fields here
            store_manager = "Sam Chibuike Adieze";
            store_street_address = "200 Mullica Hill Rd.";
            store_town = "Glassboro";
            store_zip = "08028";
            store_phone = "123-456-7890";
            store_state = "NJ";
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
