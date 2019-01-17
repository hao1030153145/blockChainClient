package com.smartdatachain.api.util;

import java.io.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 包: com.smartdatachain.api.util
 * 源文件:FileUtil.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月29日
 */
public class FileUtil
{
    public static File getZipFile(List<File> fileList,  File zipFile) throws
            IOException
    {
        // 文件输出流
        FileOutputStream outputStream = new FileOutputStream(zipFile);
        // 压缩流
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        int size = fileList.size();
        // 压缩列表中的文件
        for (int i = 0;i < size;i++) {
            File file = fileList.get(i);
            zipFile(file, zipOutputStream);
        }
        // 关闭压缩流、文件流
        zipOutputStream.close();
        outputStream.close();
        return zipFile;
    }

    /**
     * 将文件数据写入文件压缩流
     * @param file 带压缩文件
     * @param zipOutputStream 压缩文件流
     * @throws IOException
     */
    private static void zipFile(File file, ZipOutputStream zipOutputStream) throws IOException {
        if (file.exists()) {
            if (file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ZipEntry entry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(entry);

                final int MAX_BYTE = 10 * 1024 * 1024; // 最大流为10MB
                long streamTotal = 0; // 接收流的容量
                int streamNum = 0; // 需要分开的流数目
                int leaveByte = 0; // 文件剩下的字符数
                byte[] buffer; // byte数据接受文件的数据

                streamTotal = bis.available(); // 获取流的最大字符数
                streamNum = (int) Math.floor(streamTotal / MAX_BYTE);
                leaveByte = (int) (streamTotal % MAX_BYTE);

                if (streamNum > 0) {
                    for (int i = 0;i < streamNum;i++) {
                        buffer = new byte[MAX_BYTE];
                        bis.read(buffer, 0, MAX_BYTE);
                        zipOutputStream.write(buffer, 0, MAX_BYTE);
                    }
                }

                // 写入剩下的流数据
                buffer = new byte[leaveByte];
                bis.read(buffer, 0, leaveByte); // 读入流
                zipOutputStream.write(buffer, 0, leaveByte); // 写入流
                zipOutputStream.closeEntry(); // 关闭当前的zip entry

                // 关闭输入流
                bis.close();
                fis.close();
            }
        }
    }

    public static String getRandomPWd(int length){
        String val = "";
        Random random = new Random();
        //length为几位密码
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static String getAttr(){
        return "0x59C044A75249eC5e0bEF5b1F0C8493E643674d46";
    }

    public static String getRandomNum(int num){
        Random r = new Random();
        double douNum = r.nextDouble();
        NumberFormat nbf= NumberFormat.getInstance();
        nbf.setMinimumFractionDigits(num);
        return nbf.format(douNum);
    }


}
