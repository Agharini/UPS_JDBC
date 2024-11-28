package com.jdbc.connect;
import java.util.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class Studentdb {
	private static Connection connect=null;
	static Scanner sc=new Scanner(System.in);
	public static void main(String[]args) {
		String url="jdbc:mysql://localhost:3306/harima";
		String user="root";
		String password="Hari@cit15";
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connect = DriverManager.getConnection(url,user,password);
//		System.out.println("Yes! Successfully connected");
		 System.out.println("1.Insert record");
		 System.out.println("Enter choice");
		 int ch=sc.nextInt();
		 switch(ch) {
		 case 1:
			 insert_rec();
			 break;
		 
		 case 2:
			 select_rec();
			 break;
		 
		 case 3:
			 update_rec();
			 break;
		 case 4:
			 delete_rec();
			 break;
		 
		 case 5:
			 sel_rec();
			 break;
		 case 6:
			 transcation();
			 break;
		 case 7:
			 batchProcessing();
		 }
			 
	}
	catch(Exception e){
		throw new RuntimeException ("Hello! Error",e);
		
	}
	}
	public static void insert_rec() throws SQLException {
		String sql="insert into student (name,percentage,address)values(?,?,?)";
		PreparedStatement x=connect.prepareStatement(sql);
		
		System.out.println("Enter name");
		String name=sc.next();
		x.setString(1,name);
		
		System.out.println("Enter percentage");
		double per=sc.nextDouble();
		x.setDouble(2, per);
		
		System.out.println("Enter address");
		String address=sc.next();
		x.setString(3, address);
		x.executeUpdate();
//		int rows=x.executeUpdate();
//		System.out.println(rows);
//		if(rows>0) {
//			System.out.println("inserted!");
//		}
//		else {
//			System.out.println("Not inserted!");
//		}
	}
	public static void select_rec() throws SQLException{
		Statement stmt = connect.createStatement();
		String sel="select * from student";
		ResultSet rs = stmt.executeQuery(sel);	
		while (rs.next()) {
         String name = rs.getString("name");
            double p=rs.getDouble("percentage");
            String adr=rs.getString("address");
            System.out.println( " Name: " + name+" percentage:" + p +" address:"+adr);
        }
	}
	public static void update_rec() throws SQLException{
		Statement st = connect.createStatement();
		String sql = "UPDATE student SET name='Kala' WHERE address like 'c%'";
        int row = st.executeUpdate(sql);
        System.out.println("Rows affected:"+row);
	}
	public static void delete_rec() throws SQLException{
		String h=sc.next();
		String sql="delete from student where address='"+h+"'";
		Statement st=connect.createStatement();
		st.executeUpdate(sql);
		}
	public static void sel_rec() throws SQLException{
		
		int x=sc.nextInt();
		String sel="select * from student where rollno="+x;
		Statement st=connect.createStatement();
		ResultSet r=st.executeQuery(sel);
		while(r.next()) {
			int rn=r.getInt("rollno");
			String nm=r.getString("name");
			System.out.println("rollno"+rn+" Name:"+nm);
		}
//		else {
//			System.out.println("Error");
//		}
		
	}
    public static void transcation() throws SQLException 
    {
    	connect.setAutoCommit(false);//telling jdbc as false
    	String sql1="insert into student (name,percentage,address) values('jeeva',60,'andhra')";
    	String sql2="insert into student (name,percentage,address) values('shree',80,'u.P')";
    	PreparedStatement preparedStatement=connect.prepareStatement(sql1);
//    	preparedStatement.executeUpdate();
    	int row1=preparedStatement.executeUpdate();
    	 preparedStatement=connect.prepareStatement(sql2);
//    	 preparedStatement.executeUpdate();
    	 int row2=preparedStatement.executeUpdate();
    	 if(row1>  0&& row2>0) {
    		 connect.commit();
    		 System.out.println("committed");
    	 }else {
    		 connect.rollback();
    		 System.out.println("Rolled back");
    	 }
      }
    public static void batchProcessing() throws SQLException
    {
    	//combining the multiple operation
    	connect.setAutoCommit(false);
    	
    	
//    	String sql1="insert into student (name,percentage,address) values('raj',44,'jammu')";
//    	String sql2="insert into student (name,percentage,address) values('pinky',54,'srinagar')";
//    	String sql3="insert into student (name,percentage,address) values('romal',74,'pondy')";
//    	String sql4="insert into student (name,percentage,address) values('rose',94,'karnataka')";
//    	String sql5="insert into student (name,percentage,address) values('jack',44,'h.p')";
//        String sql6="UPDATE student SET name='Kavitha' WHERE address like 'u%'";
    	String sql7="delete from student where name='raj'";
    Statement statement=connect.createStatement();
//    statement.addBatch(sql1);
//    statement.addBatch(sql2);
//    statement.addBatch(sql3);
//    statement.addBatch(sql4);
//    statement.addBatch(sql5);
    statement.addBatch(sql7);
    int[] rows=statement.executeBatch();
    for(int i:rows) 
    
    	if(i>0) 
    	continue;
      else 
    	  connect.rollback();
    System.out.print("Done");
    	  
    	 connect.commit();
    
   
    }


}

