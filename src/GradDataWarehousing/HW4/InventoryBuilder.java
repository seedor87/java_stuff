package GradDataWarehousing.HW4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static GradDataWarehousing.HWResources.Utils.isMilkSku;

public class InventoryBuilder {

    static final String DELIM = " , ";
    static final String INPUT_PATH = "." + File.separatorChar + "output2.csv";   // start from this file
    static Map<Integer, Integer> inventoryMap = new HashMap<>();
    static Map<Integer, Integer> avgsMap = new HashMap<>();
    static Map<Integer, Integer> casesYTD = new HashMap<>();

    public static Map<Integer, Integer> buildInventory() {

        try {
            FileInputStream fs= new FileInputStream(INPUT_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line = br.readLine();    // throw away for header
            while((line = br.readLine()) != null) {
                String[] fields = line.split(DELIM);
                Integer sku = Integer.parseInt(fields[0]);
                Integer avg = Integer.parseInt(fields[3]);
                int quantityOrdered;
                if (isMilkSku(sku)) {
                    quantityOrdered = (int) 1.5 * avg;

                } else {
                    quantityOrdered = (int) 3 * avg;
                }
                int leftOver = quantityOrdered % 12;
                int numCases = quantityOrdered / 12;
                if(leftOver != 0) {
                    numCases += 1;
                }
                inventoryMap.putIfAbsent(sku, numCases * 12);
                casesYTD.putIfAbsent(sku, numCases);
                avgsMap.put(sku, avg);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventoryMap;
    }

}
