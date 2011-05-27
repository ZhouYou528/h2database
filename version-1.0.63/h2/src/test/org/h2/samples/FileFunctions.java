/*
 * Copyright 2004-2007 H2 Group. Licensed under the H2 License, Version 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.samples;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class FileFunctions {

    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:", "sa", "");
        Statement stat = conn.createStatement();
        stat.execute("CREATE ALIAS READ_TEXT_FILE FOR \"org.h2.samples.FileFunctions.readTextFile\" ");
        stat.execute("CREATE ALIAS READ_TEXT_FILE_WITH_ENCODING FOR \"org.h2.samples.FileFunctions.readTextFileWithEncoding\" ");
        stat.execute("CREATE ALIAS READ_FILE FOR \"org.h2.samples.FileFunctions.readFile\" ");
        ResultSet rs = stat.executeQuery("CALL READ_FILE('test.txt')");
        rs.next();
        byte[] data = rs.getBytes(1);
        System.out.println("length: " + data.length);
        rs = stat.executeQuery("CALL READ_TEXT_FILE('test.txt')");
        rs.next();
        String text = rs.getString(1);
        System.out.println("text: " + text);
        conn.close();
    }

    public static String readTextFile(String fileName) throws IOException {
        byte[] buff = readFile(fileName);
        String s = new String(buff);
        return s;
    }

    public static String readTextFileWithEncoding(String fileName, String encoding) throws IOException {
        byte[] buff = readFile(fileName);
        String s = new String(buff, encoding);
        return s;
    }

    public static byte[] readFile(String fileName) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        try {
            byte[] buff = new byte[(int) file.length()];
            file.readFully(buff);
            return buff;
        } finally {
            file.close();
        }
    }
}