package top.gochiusa.newsapi.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class Utils {

    public static String stringToMD5(String plainText) {
        byte[] secretBytes;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无法获取MD5算法");
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }

    public static String toString(List<?> list) {
        StringBuilder builder = new StringBuilder();
        // 依次遍历、拼接成类似于(1,2,3)的形式
        for (int i = 0; i < list.size() - 1; i++) {
            builder.append(list.get(i));
            builder.append(",");
        }
        builder.append(list.get(list.size() - 1));
        return builder.toString();
    }

    /**
     * 获取当前系统时间的字符串
     *
     * @return 表示当前时间的字符串
     */
    public static String currentDate() {
        return new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ").format(new Date());
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
