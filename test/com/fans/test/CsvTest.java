package com.fans.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CsvTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CsvTest test = new CsvTest();
//		test.writeCsv();
		
		test.readeCsv();
//		String in = "aaaaa,\"bb\"\"b,bb\",cccccc,\"dd,,ddd\"\"dddd\"";
		
//		test.readCsvByInputStream(in);
	}
	
	
    /**
    * 读取CSV文件
    */
    public void  readeCsv(){
        try {    
             
            ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据
            String csvFilePath = "e:/test.csv";
             CsvReader reader = new CsvReader(csvFilePath,',',Charset.forName("GBK"));    //一般用这编码读就可以了    
             
//             reader.readHeaders(); // 跳过表头   如果需要表头的话，不要写这句。
             
             while(reader.readRecord()){ //逐行读入除表头的数据    
                 csvList.add(reader.getValues());
             }            
             reader.close();
             
             for(int row=0;row<csvList.size();row++){
                 
                 String  cell = csvList.get(row)[1]; //取得第row行第0列的数据
                 System.out.println(cell);
                 
             }
             
             
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    /**
     * 写入CSV文件
     */
    public void writeCsv(){
        try {
            
            String csvFilePath = "e:/test.csv";
             CsvWriter wr =new CsvWriter(csvFilePath,',',Charset.forName("SJIS"));
             String[] contents = {"aaaaa","bb\"b,bb","cccccc","dd,,ddd\"dddd"};                    
             wr.writeRecord(contents);
             wr.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
    }
    
    
    public void readCsvByInputStream(String in){
    	InputStream is = new ByteArrayInputStream(in.getBytes()); 
    	 try {    
             
             ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据
              CsvReader reader = new CsvReader(is,Charset.forName("SJIS"));    //一般用这编码读就可以了    
              
//              reader.readHeaders(); // 跳过表头   如果需要表头的话，不要写这句。
              
              while(reader.readRecord()){ //逐行读入除表头的数据    
                  csvList.add(reader.getValues());
              }            
              reader.close();
              
              for(int row=0;row<csvList.size();row++){
                  
                  String  cell = csvList.get(row)[1]; //取得第row行第0列的数据
                  System.out.println(cell);
                  
              }
         }catch(Exception ex){
             System.out.println(ex);
         }
    }


}
