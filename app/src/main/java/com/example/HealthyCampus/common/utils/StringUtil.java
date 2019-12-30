package com.example.HealthyCampus.common.utils;

import android.text.TextUtils;

import com.example.HealthyCampus.common.data.Bean.CookDetailBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class StringUtil {


    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }


    public static String formatInputStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String toMD5(String source) {
        if (null == source || "".equals(source)) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(source.getBytes());
            return toHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String toHex(byte[] buf) {
        if (buf == null) return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (byte aBuf : buf) {
            appendHex(result, aBuf);
        }
        return result.toString();
    }


    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "^((1[3,5,7,8][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    public static boolean isPassword(String password) {
        String regex = "(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,15}$";
        if (TextUtils.isEmpty(password)) {
            return false;
        } else {
            return password.matches(regex);
        }
    }

    public static String dealHtmlText(String data) {
        return data
                .replace("</td><td>", " : ")
                .replace("<td>", "")
                .replace("</td>", "")
                .replace("</tr>", "\n\n")
                .replace("<tr>", "");
    }


    public static List<String> getStepArray(String whole) {
        String[] array = whole.split(",");
        return java.util.Arrays.asList(array);
    }

    public static List<CookDetailBean> getIngredientsArray(String whole) {
        String ingredient[];
        List<CookDetailBean> list = new LinkedList<>();
        String[] array = whole.split(",");
        for (String anArray : array) {
            ingredient = anArray.split(":");
            CookDetailBean cookDetailBean = new CookDetailBean();
            cookDetailBean.setMaterial(ingredient[0]);
            cookDetailBean.setQuantity(ingredient[1]);
            list.add(cookDetailBean);
        }
        return list;
    }

}
