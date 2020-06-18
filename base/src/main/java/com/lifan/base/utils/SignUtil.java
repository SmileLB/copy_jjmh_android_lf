package com.lifan.base.utils;

import android.text.TextUtils;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SignUtil {

    public static String signSha1(String in) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            byte[] bytes = in.getBytes("utf-8");
            byte[] digest = messageDigest.digest(bytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                int val = (int)(digest[i] & 0xff);
                if (val < 16)
                    sb.append(0);
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static Key createKey(String pwd) {

        try {
            // 生成key
            KeyGenerator keyGenerator;
            //构造密钥生成器，指定为AES算法,不区分大小写
            keyGenerator = KeyGenerator.getInstance("AES/CBC/PKCS7Padding");
            keyGenerator.init(32,new SecureRandom(pwd.getBytes("utf-8")));
            //产生原始对称密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得原始对称密钥的字节数组
            byte[] keyBytes = secretKey.getEncoded();
            // key转换,根据字节数组生成AES密钥
            Key key = new SecretKeySpec(keyBytes, "AES");
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 解密
     * @param result 加密后的密文byte数组
     */
    public static String decrypt(byte[] result, String pwd) {

        Cipher cipher;
        try {
//            Key key = createKey(pwd);
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
//            AlgorithmParameterSpec paramSpec = new IvParameterSpec(new byte[16]);
            SecretKeySpec secretKeySpec = new SecretKeySpec(pwd.getBytes("utf-8"),"AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            result = cipher.doFinal(result);
            return new String(result,"utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    private static String ALGO = "AES";
    private static String ALGO_MODE = "AES/CBC/NoPadding";
    private static String akey = "11111111111111111111111111111111";
    private static String aiv = "22222222222222222222222222222222";

//    public static void main(String[] args) throws Exception {
//        Test aes = new Test();//创建AES
//        JSONObject data = new JSONObject();//创建Json的加密对象
//        data.put("haha", "hehe");
//        System.out.println("原始数据:"+data.toJSONString());
//        String rstData = pkcs7padding(data.toJSONString());//进行PKCS7Padding填充
//        String passwordEnc = aes.encrypt(rstData);//进行java的AES/CBC/NoPadding加密
//        String passwordDec = aes.decrypt(passwordEnc);//解密
//        System.out.println("加密之后的字符串:"+passwordEnc);
//        System.out.println("解密后的数据:"+passwordDec);
//    }


    public static String encrypt(String Data) throws Exception {
        try {
            byte[] iv = toByteArray(aiv);//因为要求IV为16byte，而此处aiv串为32位字符串，所以将32位字符串转为16byte
            Cipher cipher = Cipher.getInstance(ALGO_MODE);
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = Data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(akey.getBytes("utf-8"), ALGO);
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            String EncStr = new String(Base64.encode(encrypted,Base64.DEFAULT));//将cipher加密后的byte数组用base64加密生成字符串
            return EncStr ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptedData) throws Exception {
        try {
            byte[] encrypted1 = (Base64.decode(encryptedData,Base64.DEFAULT));
            byte[] iv = toByteArray(aiv);
            Cipher cipher = Cipher.getInstance(ALGO_MODE);
            SecretKeySpec keyspec = new SecretKeySpec(akey.getBytes("utf-8"), ALGO);
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();//此处添加trim（）是为了去除多余的填充字符，就不用去填充了，具体有什么问题我还没有遇到，有强迫症的同学可以自己写一个PKCS7UnPadding函数
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //此函数是将字符串每两个字符合并生成byte数组
    public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() >> 1];
        int index = 0;
        for (int i = 0; i < hexString.length(); i++) {
            if (index  > hexString.length() - 1)
                return byteArray;
            byte highDit = (byte) (Character.digit(hexString.charAt(index), 16) & 0xFF);
            byte lowDit = (byte) (Character.digit(hexString.charAt(index + 1), 16) & 0xFF);
            byteArray[i] = (byte) (highDit << 4 | lowDit);
            index += 2;
        }
        System.out.println(byteArray.length);
        return byteArray;
    }
    //此函数是pkcs7padding填充函数
    public static String pkcs7padding(String data) {
        int bs = 16;
        int padding = bs - (data.length() % bs);
        String padding_text = "";
        for (int i = 0; i < padding; i++) {
            padding_text += (char)padding;
        }
        return data+padding_text;
    }


    public static String sign(String value) {
        String result = "";
        String encodeValue = Base64.encodeToString(value.getBytes(), Base64.NO_WRAP);
        result = md5(encodeValue).substring(8, 24);
        return result;
    }

    @NonNull
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}

