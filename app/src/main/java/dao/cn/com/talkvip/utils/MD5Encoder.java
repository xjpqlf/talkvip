package dao.cn.com.talkvip.utils;

import java.security.MessageDigest;

/**
 * @name dao.cn.com.talkvip.utils
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/4/17 12:06
 * @change uway
 * @chang 2017/4/17 12:06
 * @class describe
 */

public class MD5Encoder {
    /**
     * Md5Encoder
     *
     * @param string
     * @return
     * @throws Exception
     */
    public static String encode(String string) throws Exception {
        byte[] hash = MessageDigest.getInstance("MD5").digest(
                string.getBytes("UTF-8"));
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

}
