package study.jll.com.textaidlclientapplication;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class HMACSHA1 {
    public static byte[] getHMACSHA1(byte[] base, byte[] key) {
        byte[] result = null;
        String type = "HmacSHA1";
        try {
            SecretKeySpec secret = new SecretKeySpec(key, type);
            Mac mac = Mac.getInstance(type);
            mac.init(secret);
            byte[] digest = mac.doFinal(base);
//            StringBuilder sb = new StringBuilder();
//            for (byte b : digest) {
//                sb.append(byteToString(b));
//            }
//            result = sb.toString();
            result = digest;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String byteToString(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0f];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    /**
     * 使用 HMAC-SHA1 签名方法对data进行签名
     *
     * @param key 密钥
     * @return 加密后的字符串
     */
    public static String hmac_sha1(String datas, String key) {
        String reString = "";

        try {
            byte[] data = key.getBytes("UTF-8");
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance("HmacSHA1");
            //用给定密钥初始化 Mac 对象
            mac.init(secretKey);

            byte[] text = datas.getBytes("UTF-8");
            //完成 Mac 操作
            byte[] text1 = mac.doFinal(text);
            StringBuilder sb = new StringBuilder();
            for (byte b : text1) {
                sb.append(byteToString(b));
            }
            reString = sb.toString();

        } catch (Exception e) {
            // TODO: handle exception
        }

        return reString;

    }

    private static final String LOG_TAG = "HMACTest";
    private static final String REGISTER_HMAC_KEY = "64A05F40B79FDA765B472067F126D35F783ACD4A";

    public static byte[] stringToSign(byte[] data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(
                    REGISTER_HMAC_KEY.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(secret);
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, "Hash algorithm SHA-1 is not supported", e);
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Encoding UTF-8 is not supported", e);
        } catch (InvalidKeyException e) {
            Log.e(LOG_TAG, "Invalid key", e);
        }
        return null;
    }

}
