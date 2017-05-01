package com.artbrain.util;

import java.util.Random;

/**
 * Created by hongyu on 2017/4/30.
 */
public class RandomStringGenerator {
    public static void main(String[] args) {
        System.out.println(getRandomString(50));
    }
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
