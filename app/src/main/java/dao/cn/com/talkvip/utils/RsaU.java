package dao.cn.com.talkvip.utils;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by uway on 2017/3/17.
 */
public class RsaU {


    private static final String ALGORITHM = "RSA";

//沙箱
    private static final String RSA_PUBLICE ="\n" +
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDiU9OAIZB+T/Nw+jmW1XJfhaju\n" +
            "tsgY2cmQicOB/3LXIhIRxWy555bEvxlhf/j0xGO3T95ZiXXnI75mW5DU6A2C9DQj\n" +
            "rHDUj7iq0V6yFiSLcuzyxedsFndEIV2ky7jdJKlZBoeybaFAni6ESFBB8tpELZ+S\n" +
            "tk9P3K6nY9VgLS30gQIDAQAB";
//正式
  /*  private static final String RSA_PUBLICE ="\n" +
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZ+I4cuH7qrgfOdGx4YG3zw8hR\n" +
        "XwJGZ3iPhIpxRwvYiZ2oJAPAQZeuDWuQxbEXl0KuYzDnLaGBBf8xTDP7S9JEV9D6\n" +
        "TqI433oxz7HFXz/0CfuI6Glp5LuyW5R5+OvyRBRJ6MHhR9PZvnmQx+LhXohkqtab\n" +
        "a0+b0ejB8YXSsnV4qQIDAQAB";*/

    /**
     * 得到公钥
     * @param algorithm
     * @param bysKey
     * @return
     */
    private static PublicKey getPublicKeyFromX509(String algorithm,
                                                  String bysKey) throws NoSuchAlgorithmException, Exception {
        byte[] decodedKey = Base64.decode(bysKey,Base64.DEFAULT);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(x509);
    }

    /**
     * 使用公钥加密
     * @param content

     * @return
     */
    public static String encryptByPublic(String content) {
        try {
            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, RSA_PUBLICE);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);

            byte plaintext[] = content.getBytes("UTF-8");
            byte[] output = cipher.doFinal(plaintext);

            String s = new String(Base64.encode(output,Base64.DEFAULT));

            return s;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 使用公钥解密
     * @param content 密文

     * @return 解密后的字符串
     */
    public static String decryptByPublic(String content) {
        try {
            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, RSA_PUBLICE);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pubkey);
            InputStream ins = new ByteArrayInputStream(Base64.decode(content,Base64.DEFAULT));
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            byte[] buf = new byte[128];
            int bufl;
            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;
                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }
                writer.write(cipher.doFinal(block));
            }
            return new String(writer.toByteArray(), "utf-8");
        } catch (Exception e) {
            return null;
        }
    }

}