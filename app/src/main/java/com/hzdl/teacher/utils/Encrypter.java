package com.hzdl.teacher.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by wangshuai on 2018/1/4.
 */

public class Encrypter {


    // Default length with Default 128
    private final static int IV_LENGTH = 16;

    // key AES encryption
    private final static int DEFAULT_READ_WRITE_BLOCK_BUFFER_SIZE = 1024;

    private final static String ALGO_RANDOM_NUM_GENERATOR = "SHA1PRNG";
    private final static String ALGO_SECRET_KEY_GENERATOR = "AES";
    private final static String ALGO_VIDEO_ENCRYPTOR = "AES/CBC/PKCS5Padding";

    public static final String PATH = "C:";

    public static void encode(File f1, File f2, String password) throws Exception {

        byte[] passwd = password.getBytes();

        //获取原视频文件
        BufferedInputStream bisOld = new BufferedInputStream(new FileInputStream(f1));
        //输出加密后的流文件
        BufferedOutputStream bosNew = new BufferedOutputStream(new FileOutputStream(f2));

        byte[] buffer = new byte[DEFAULT_READ_WRITE_BLOCK_BUFFER_SIZE];

        int len = 0;

        int i = 0;
        //加密文件
        while ((len = bisOld.read(buffer)) > 0) {
            bosNew.write(buffer, 0, len);
            if(i == 10){
                //写入密钥
                bosNew.write(passwd);
            }
            i++;
        }

        bosNew.flush();
        bosNew.close();
        bisOld.close();

    }

    /**
     * 解密
     *
     * @throws Exception
     */
    public static void decode(File f1,File f2,String password) throws Exception {
        byte[] passwd = password.getBytes();

        //获取加密文件
        BufferedInputStream bisOld = new BufferedInputStream(new FileInputStream(f1));
        //输出解密后流文件
        BufferedOutputStream bosNew = new BufferedOutputStream(new FileOutputStream(f2));

        byte[] buffer = new byte[1024];
        int len = 0;

        int i = 0;
        while ((len = bisOld.read(buffer)) > 0) {
            bosNew.write(buffer, 0, len);
            if(i == 10){
                //获取密钥
                bisOld.read(passwd);
                passwd = null;
            }
            i++;
        }

        bosNew.flush();
        bosNew.close();
        bisOld.close();
    }

    /**
     * ASE加密
     * @param key
     * @param paramSpec
     * @param in
     * @param out
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static void encrypt(SecretKey key, AlgorithmParameterSpec paramSpec, InputStream in, OutputStream out)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IOException {
        try {
            //先进行AES加密
            Cipher c = Cipher.getInstance(ALGO_VIDEO_ENCRYPTOR);
            c.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            out = new CipherOutputStream(out, c);
            int count = 0;

            byte[] buffer = new byte[DEFAULT_READ_WRITE_BLOCK_BUFFER_SIZE];
            while ((count = in.read(buffer)) >= 0) {
                out.write(buffer, 0, count);
            }
        } finally {
            out.close();
        }
    }

    /**
     * AES解密
     * @param key
     * @param paramSpec
     * @param in
     * @param out
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static void decrypt(SecretKey key, AlgorithmParameterSpec paramSpec, InputStream in, OutputStream out)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IOException {
        try {
            Cipher c = Cipher.getInstance(ALGO_VIDEO_ENCRYPTOR);
            c.init(Cipher.DECRYPT_MODE, key, paramSpec);
            out = new CipherOutputStream(out, c);
            int count = 0;
            byte[] buffer = new byte[DEFAULT_READ_WRITE_BLOCK_BUFFER_SIZE];
            while ((count = in.read(buffer)) >= 0) {
                out.write(buffer, 0, count);
            }
        } finally {
            out.close();
        }
    }
    public static void main(String[] args) {
        jiaMiAndJIemi();
    }


    public static void jiaMiAndJIemi(){
        String fileName = "1-8-1-1.mp4";

        //D:\天赋\melodyx\video\第八课
        File files = new File("D:\\加密\\源文件");
        if(files.isDirectory()){
            File[] listFiles = files.listFiles();
            for (File f : listFiles){
                String fName = f.getName();
                if(fName.endsWith("mp4")){
                    operateFile(f);
                    System.out.println(f.getName() + "解密完成");
                }
            }
        }else{
            System.out.println("这不是一个文件夹");
        }
    }

    public static void operateFile(File inFile){

        File outFile1 = new File("D:\\加密\\加密\\加密1\\"+ inFile.getName().split("\\.")[0] +".amm");
        File outFile2 = new File("D:\\加密\\加密\\"+ inFile.getName().split("\\.")[0] +".amm");

        File outFile_dec1 = new File("D:\\加密\\解密\\解密1\\"+ inFile.getName().split("\\.")[0] +".mp4");
        File outFile_dec2 = new File("D:\\加密\\解密\\"+ inFile.getName().split("\\.")[0] +".mp4");

        try {
            SecretKey key = KeyGenerator.getInstance(ALGO_SECRET_KEY_GENERATOR).generateKey();

            byte[] keyData = key.getEncoded();
            SecretKey key2 = new SecretKeySpec(keyData, 0, keyData.length, ALGO_SECRET_KEY_GENERATOR);

            byte[] iv = new byte[IV_LENGTH];
            SecureRandom.getInstance(ALGO_RANDOM_NUM_GENERATOR).nextBytes(iv);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);

            Encrypter.encrypt(key, paramSpec, new FileInputStream(inFile), new FileOutputStream(outFile1));
            Encrypter.encode(outFile1,outFile2,"melodyx");

            Encrypter.decode(outFile2,outFile_dec1,"melodyx");
            Encrypter.decrypt(key2, paramSpec, new FileInputStream(outFile_dec1), new FileOutputStream(outFile_dec2));

        } catch (Exception e) {
        }
    }

}
