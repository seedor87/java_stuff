package Utils.Collections.BinaryHeaps;

import Utils.Timers.AbstractTimer;
import Utils.Timers.SYSTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Utils.ConsolePrinting.*;

public class BinaryHeapTests {

    static List<Integer> input = new ArrayList<>(Arrays.asList(4142, 2886, 4649, 6390, 7381, 6592, 117, 9375, 1693, 7913, 7143, 6388, 723, 565, 9971, 3714, 5565, 629, 3525, 7117, 8502, 1639, 831, 8678, 323, 4856, 4317, 1209, 6737, 985, 3429, 4082, 8813, 511, 2928, 3455, 6816, 20, 1872, 2449, 1077, 6460, 6206, 2124, 6537, 786, 6705, 7870, 61, 6400, 2892, 9020, 9818, 2877, 1748, 424, 1075, 8977, 4924, 6895, 3184, 1221, 5776, 3599, 4396, 3285, 2187, 7189, 8924, 6087, 2245, 3540, 4297, 3845, 1324, 1586, 429, 7637, 2477, 7900, 5095, 8411, 7359, 266, 5674, 9204, 8973, 8877, 6872, 9027, 7102, 1621, 9126, 5977, 1395, 1167, 4877, 5581, 108, 7772, 1293, 2779, 8871, 1853, 397, 8649, 1158, 9401, 7299, 5172, 3003, 1089, 3308, 9529, 1491, 5067, 7065, 6271, 1933, 4858, 7239, 2322, 3126, 4895, 377, 4091, 4754, 7913, 6420, 170, 2376, 7527, 1943, 9222, 5898, 6798, 9492, 3099, 1728, 5287, 7984, 9362, 3552, 1216, 7123, 1950, 5036, 354, 9243, 8786, 2169, 1586, 180, 2945, 5543, 8657, 6556, 9949, 6817, 8174, 6182, 737, 9715, 6588, 6334, 8362, 8115, 366, 5491, 1632, 6462, 9342, 8710, 290, 7376, 9789, 9710, 7055, 6655, 8068, 7093, 4689, 1140, 4393, 3058, 852, 5255, 2590, 6676, 7797, 7558, 1220, 3671, 4533, 3697, 9021, 6630, 2813, 7957, 5166, 5118, 8571, 8644, 8243, 6443, 1194, 6877, 6396, 497, 7008, 5437, 2597, 1785, 816, 7234, 676, 1510, 5344, 6424, 2747, 5987, 9078, 7650, 9470, 3899, 3915, 8550, 6222, 2084, 7425, 3445, 7972, 4936, 8513, 661, 1881, 7218, 8749, 8526, 5794, 418, 7348, 2045, 9913, 7709, 9731, 7448, 4486, 7268, 4169, 2879, 5436, 9438, 5715, 6587, 9776, 1189, 7078, 5254, 2533, 9787, 9612, 8054, 1891, 8198, 2997, 3895, 3478, 7535, 206, 628, 6818, 5203, 1199, 2226, 4435, 9732, 7103, 5433, 1491, 4143, 7810, 528, 5365, 8282, 6562, 5508, 3791, 9242, 507, 8116, 9876, 4782, 4640, 700, 1199, 8876, 7180, 941, 5804, 6038, 7315, 7429, 8976, 6793, 9063, 6304, 1241, 8978, 4966, 2458, 9391, 5500, 9408, 2079, 4458, 4106, 6950, 3618, 3277, 9277, 5086, 1750, 8400, 1491, 3139, 1180, 7516, 3757, 1977, 106, 7104, 8252, 5199, 6196, 864, 8199, 6701, 4698, 4365, 2459, 2573, 142, 2351, 1775, 3638, 9664, 1278, 1554, 8707, 1208, 6401, 8900, 274, 3231, 2365, 5310, 1816, 3962, 4175, 9710, 8785, 611, 2309, 2547, 8022, 9686, 1671, 6354, 6599, 2118, 3843, 7699, 5960, 698, 5478, 2315, 6505, 3287, 310, 1174, 7868, 6266, 1532, 1744, 187, 8434, 4863, 2817, 8146, 2968, 9099, 1027, 5208, 877, 1121, 929, 6907, 5118, 6392, 2190, 7641, 7782, 2625, 7566, 4283, 1648, 5080, 3365, 5036, 951, 5366, 1770, 8263, 8509, 2164, 1496, 5038, 7892, 1306, 1345, 9223, 9052, 2357, 47, 1601, 3866, 5974, 2937, 4644, 7841, 1303, 7758, 8358, 5263, 6809, 4561, 154, 8055, 4506, 6977, 4372, 3670, 3794, 2195, 5495, 772, 5692, 8574, 703, 1546, 9169, 675, 1866, 474, 3039, 5975, 5792, 111, 9705, 6127, 9940, 4082, 4217, 3287, 2867, 3765, 3745, 847, 3226, 3808, 8334, 1474, 1924, 2023, 6701, 9684, 8688, 5940, 3176, 6599, 1073, 8422, 6377, 5034, 5951, 8773, 4463, 3455, 9562, 496, 1086, 9671, 2927, 3797, 6625, 8573, 8612, 4557, 4523, 3147, 6829, 7602, 4170, 3661, 5281, 9613, 797, 3168, 1686, 288, 7692, 6934, 296, 2381, 6886, 5466, 1705, 6577, 4232, 154, 9651, 6721, 6537, 9125, 8457, 3679, 755, 9715, 7740, 1417, 8923, 683, 4216, 3502, 2658, 3525, 6366, 8893, 7188, 2317, 5661, 6957, 8125, 304, 506, 7507, 443, 6967, 5758, 2819, 735, 3293, 3358, 1526, 1597, 7022, 5238, 1075, 7571, 5726, 5695, 3661, 2743, 8915, 6096, 3498, 7800, 6224, 6512, 4700, 6391, 7774, 9289, 2189, 6958, 9321, 9432, 379, 5774, 6933, 5617, 702, 6263, 2348, 3952, 1186, 3856, 7333, 6282, 774, 6038, 5889, 8850, 5887, 1345, 3493, 8583, 1817, 1986, 1967, 6026, 7580, 1113, 6312, 9518, 9943, 9044, 3365, 2696, 7403, 7121, 8377, 3846, 9430, 340, 1642, 7148, 7643, 4820, 3054, 8025, 9995, 623, 2402, 3944, 162, 6542, 4614, 4339, 6752, 5780, 6713, 8321, 3890, 9840, 8941, 848, 1836, 7073, 8151, 2206, 7254, 3273, 4453, 7969, 9632, 1434, 5596, 3751, 6675, 8781, 4956, 6167, 838, 1826, 9298, 9398, 2362, 5922, 8059, 6822, 8850, 5120, 9120, 7593, 3794, 78, 1235, 3187, 2899, 7083, 3059, 8413, 271, 4538, 5999, 886, 1600, 6046, 901, 6068, 1120, 3201, 5222, 2888, 5404, 484, 9368, 6416, 10, 1041, 9207, 7734, 9188, 2476, 6425, 4709, 4272, 5928, 1196, 6587, 1709, 4309, 1263, 8682, 1900, 7607, 9306, 8089, 2760, 4547, 666, 6840, 9188, 7026, 9533, 9969, 3904, 7800, 6406, 4793, 5080, 2654, 1038, 2165, 6841, 7163, 1048, 6650, 5439, 9905, 158, 909, 9856, 1908, 1389, 3977, 9551, 4726, 4330, 7022, 1487, 7917, 6303, 6534, 9716, 661, 8646, 5860, 4503, 7266, 2753, 6758, 2070, 2926, 2404, 3161, 3733, 5025, 2592, 6859, 1061, 5439, 3116, 2235, 8751, 4363, 5506, 844, 4462, 8041, 9491, 7017, 1078, 288, 5848, 4915, 8740, 6446, 3830, 2438, 4260, 4198, 9469, 5386, 1865, 3733, 2173, 5060, 5853, 115, 2342, 1980, 485, 9156, 4794, 7994, 5503, 1182, 4177, 8074, 8603, 6501, 5017, 4078, 211, 6161, 5688, 9219, 5017, 5377, 8128, 8411, 1931, 5401, 8141, 6585, 3573, 2071, 1758, 8956, 8324, 3844, 3149, 2082, 6530, 1013, 8797, 6942, 3571, 6225, 8655, 405, 4955, 1453, 6529, 3410, 5193, 9651, 3060, 2953, 6740, 1634, 1496, 3224, 3633, 4430, 9798, 4713, 2270, 8728, 7977, 5860, 5902, 3432, 5466, 8209, 8578, 1510, 5191, 6908, 3424, 841, 2032, 9617, 2834, 9383, 9053, 3271, 9629, 1242, 2079, 4825, 9916, 6052, 7298, 3151, 9417, 6297, 2875, 9995, 7304, 6763, 5380, 1295, 848, 6883, 6065, 844, 5127, 395, 4830, 7526, 9609, 6281, 4823, 4691, 1860, 2533, 3043, 2930, 3314, 7387, 1358, 4904, 8052, 2940, 7138, 7893, 9064, 1295, 5457, 7701, 9335, 677, 9714, 179, 942, 8202, 1013, 7336, 3446, 9751, 9397, 3313, 3490, 9109, 7625, 9087, 2053, 1948, 3526, 7351, 8413, 2201, 4341, 7946, 2432, 3822, 6108, 9359, 3505, 7315, 2924, 6411, 4287, 1610, 8151, 1363, 6635, 7376, 6875, 8472, 8691, 5871, 7248, 4562, 2272, 4001, 4554, 4520, 1624, 9472, 6794, 5978, 3921, 6600, 279, 9490, 4787, 2893, 2853, 2703, 5209, 7769, 1760, 1681, 500, 3230, 3367, 9763, 5515, 6825, 5223, 9553, 9986, 5383, 4979, 3979, 4630, 5890, 6589, 7266, 5363, 2980, 1044, 6496, 2946, 6701));

    public static void arrayBasedTest() {
        ArrayBasedBinaryHeap<Integer> bh = new ArrayBasedBinaryHeap().setSize(input.size());
        for(Integer i : input) {
            bh.push(i);
            println(bh);
        }
    }

    public static void listBasedTest() {
        ListBasedBinaryHeap<Integer> bh = new ListBasedBinaryHeap<>();
        for(Integer i : input) {
            bh.push(i);
            println(bh);
        }
    }

    public static void llBasedTest() {
        LinkedListBasedBinaryHeap<Integer> bh = new LinkedListBasedBinaryHeap<>();
        for(Integer i : input) {
            bh.push(i);
            println(bh);
        }
    }

    public static void main(String args[]) {

        double total_time = 0;
        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        int i;
        for (i = 1; i <= 10; i++) {
            timer.start();

            llBasedTest();
            listBasedTest();
            arrayBasedTest();

            timer.stop();
            total_time += timer.getElapsed();
            println(timer);
        }
        println(FG_BRIGHT_GREEN, total_time, ":", total_time / (i-1));

    }
}
