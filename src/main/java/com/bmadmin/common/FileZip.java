package com.bmadmin.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by 金玮良 on 2017/6/14 0014.
 */
public class FileZip {

    /**
     * @param zipFileName 压缩后的zip文件名称
     * @param inputFile   需要压缩的文件
     * @throws Exception
     */
    public static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                zipFileName));
        BufferedOutputStream bo = new BufferedOutputStream(out);
        zip(out, inputFile, inputFile.getName(), bo);
        bo.close();
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base,
                            BufferedOutputStream bo) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/"));
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + "/" + fl[i].getName(), bo);
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b);
            }
            bo.flush();
            bi.close();
            in.close();
        }
    }

//    public static void main(String[] args) {
//        try {
//            zip("E:\\logs\\error.zip",new File("E:\\logs\\error.log"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//public static void main(String[] args) {
//    getFileName();
//
//}

    public static List<String> getFileName(String path) {
        List<String> result = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) {
            return result;
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                //如果是目录
            } else {
                //如果是文件
                result.add(fs.getName());
            }
        }
        return result;
    }


}
