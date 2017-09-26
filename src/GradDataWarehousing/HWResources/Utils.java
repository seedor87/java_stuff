package GradDataWarehousing.HWResources;

import static GradDataWarehousing.HWResources.HW1Arrays.MILKS;

public class Utils {

    public static boolean isMilkSku(Integer sku) {
        for (SkuPrice skuPrice : MILKS) {
            if (skuPrice.getSku() == sku) {
                return true;
            }
        }
        return false;
    }
}
