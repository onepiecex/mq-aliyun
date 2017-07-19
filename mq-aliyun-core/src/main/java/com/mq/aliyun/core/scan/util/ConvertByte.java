package com.mq.aliyun.core.scan.util;

import java.io.*;

/**
 * Created by wangziqing on 16/3/15.
 */
public class ConvertByte {
    public static byte[] objectToBytes(final Serializable object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }

    public static Object bytesToObject(final byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = null;
        Object obj = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = objectInputStream.readObject();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally{
            try {
                objectInputStream.close();
                byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return obj;
    }

    public static void main(String[] args) {
        String s = "aa";
        byte[] bytes = ConvertByte.objectToBytes(new String("a"));
        Object o = ConvertByte.bytesToObject(bytes);
        System.out.println(o);
    }


}
