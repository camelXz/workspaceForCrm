package com.wkcto.crm.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 动力节点7777
 */
public class Poi {
    public static void main(String[] args) throws Exception {
        Student s1 = new Student("A0001","zs",23);
        Student s2 = new Student("A0002","ls",24);
        Student s3 = new Student("A0003","ww",25);
        Student s4 = new Student("A0004","zl",26);
        Student s5 = new Student("A0005","sq",27);

        List<Student> sList = new ArrayList<>();
        sList.add(s1);
        sList.add(s2);
        sList.add(s3);
        sList.add(s4);
        sList.add(s5);
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet=workbook.createSheet("学生信息表");
        HSSFRow row=sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("编号");
        cell=row.createCell(1);
        cell.setCellValue("姓名");
        cell=row.createCell(2);
        cell.setCellValue("年龄");

        for (int i=0;i<sList.size();i++){
            Student student = sList.get(i);
            row=sheet.createRow(i+1);

            cell=row.createCell(0);
           cell.setCellValue(student.getId());
           cell=row.createCell(1);
           cell.setCellValue(student.getName());
           cell=row.createCell(2);
           cell.setCellValue(student.getAge());
        }
        OutputStream outputStream=new FileOutputStream("D:\\Student.xls");
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();

    }
}
