package GradDataWarehousing.HW3;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static GradDataWarehousing.HWResources.Utils.isMilkSku;

public class InventoryBuilder {

    static final String DELIM = " , ";
    static final String INPUT_PATH = "." + File.separatorChar + "output2.csv";   // start from this file
    static Map<Integer, Integer> inventoryMap = new HashMap<>();
    static Map<Integer, Integer> avgsMap = new HashMap<>();

    public static Map<Integer, Integer> buildInventory() {

        try {
            FileInputStream fs= new FileInputStream(INPUT_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line = br.readLine();    // throw away for header
            while((line = br.readLine()) != null) {
                String[] fields = line.split(DELIM);
                Integer sku = Integer.parseInt(fields[0]);
                Integer avg = Integer.parseInt(fields[3]);
                if (isMilkSku(sku)) {
                    inventoryMap.put(sku, (int) (1.5 * avg));
                } else {
                    inventoryMap.put(sku, 3 * avg);
                }
                avgsMap.put(sku, avg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventoryMap;
    }

}
