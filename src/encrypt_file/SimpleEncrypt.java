package encrypt_file;

import java.io.*;

/**
 * 简单的加密方法，
 *
 * 常见的流加密算法有：RC4
 * 常见的块加密算法有：DES、3DES、AES、Blowfish 等等
 *
 * MD5 肯定是不能加密文件的，MD5 是消息摘要，只能对文件内容进行摘要，看看这个文件是否被改动过。
 *
 * 密码学主要分为：
 *
 * 对称加密算法、非对称加密算法（公钥密码学）、消息摘要、消息认证码（MAC）、数字签名
 *
 * 这些在 Java 中都能支持。
 *
 * @author shiyuquan
 * Create Time: 2019/7/14 9:01
 */
public class SimpleEncrypt {

    private SimpleEncrypt() {}

    private static String path = System.getProperty("user.dir");

    /**
     * 创建加密文件
     * @param str 文件内容
     * @return file
     */
    public static File createEnctyptFile(String str) {
        File file = new File(path);
        // 写文件
        try {
            try (FileWriter writer = new FileWriter(file, true);
                 BufferedWriter out = new BufferedWriter(writer)) {

                out.write(encryptString(str));
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * 字符串加密方法
     */
    public static String encryptString(String str) {
        byte[] b = str.getBytes();
        return encryptByte(b);
    }

    /**
     * 字符串解密方法
     */
    public static String decryptString(String str) {
        byte[] b = str.getBytes();
        return decryptByte(b);
    }

    /**
     * 字节码加密方法
     */
    public static String encryptByte(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            b[i] += 1;
        }
        return new String(b);
    }

    /**
     * 字节码解密方法
     */
    public static String decryptByte(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            b[i] -= 1;
        }
        return new String(b);
    }

}
