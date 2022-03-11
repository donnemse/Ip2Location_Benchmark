package com.yuganji;
import java.util.Random;

public final class NetUtil {
    private NetUtil() {
        throw new IllegalStateException("NetUtil is Utility class");
    }
    private static Random random = new Random();

    public static String genIp() {
        StringBuffer sb = new StringBuffer();
        sb.append((random.nextInt(254) + 1));
        sb.append('.');
        sb.append((random.nextInt(254) + 1));
        sb.append('.');
        sb.append((random.nextInt(254) + 1));
        sb.append('.');
        sb.append((random.nextInt(254) + 1));
        return sb.toString();
    }

    public static long ip2long(String str) {
        long result = 0L;
        try {
            String[] ipAddressInArray = str.toString().split("\\.");
            for (int i = 0; i < ipAddressInArray.length; i++) {
                int power = 3 - i;
                int ip = Integer.parseInt(ipAddressInArray[i]);
                result += ip * Math.pow(256, power);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
