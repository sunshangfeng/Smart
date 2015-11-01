package com.ssf.smart;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Android on 2015/10/30.
 */
public class MD5Utils {

    /**
     * Md5 32位 or 16位 加密
     *
     * @param plainText
     * @return 32位加密
     */
    public static String Md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // Log.e("555","result: " + buf.toString());//32位的加密
            //Log.e("555","result: " + buf.toString().substring(8,24));//16位的加密

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
}
