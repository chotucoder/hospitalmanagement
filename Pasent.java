package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Pasent {
   private Connection connection;
   private Scanner scanner;
   public Pasent(Connection connection,Scanner scanner){
      this.connection=connection;
      this.scanner=scanner;
   }
   public void addpasent(){
//       System.out.println("Enter Pasent ID");
//       int id=scanner.nextInt();
       System.out.print("Enter Pasents name: ");
       String Name=scanner.next();
       System.out.print("Enter Pasent age: ");
       int Age=scanner.nextInt();
       System.out.print("Enter Pasent gender: ");
       String Gender=scanner.next();

      try{
         String query="insert into pasent(name,age,gender) values(?,?,?)";
         PreparedStatement ps=connection.prepareStatement(query);
         ps.setString(1,Name);
         ps.setInt(2,Age);
         ps.setString(3,Gender);
         int affected=ps.executeUpdate();
         if(affected>0){
             System.out.println("Pasent has been added successfully");
         }
         else {
             System.out.println("Pasent has not been added successfully");
         }

      }catch (SQLException e){
          e.printStackTrace();
      }
   }
   public void viewpasent(){
       String query="select * from pasent";
       try{
           PreparedStatement ps=connection.prepareStatement(query);
           ResultSet rs=ps.executeQuery();
           System.out.println("Pasent : ");
           System.out.println("+-----------+-------------+---------1+");
           System.out.println("| name        |age      | Gender    |");
           System.out.println("+-----------+-------------+---------+");
           while (rs.next()) {
               int pid=rs.getInt("id");
               String name=rs.getString("name");
               int age=rs.getInt("age");
               String gender=rs.getString("gender");
//               System.out.print("|%-11s|%-13s|%-9s|%-11s|\n");
               System.out.println(pid+"|        "+name+"|       "+age+"|            "+gender);
               System.out.println("+-----------+-------------+--------+-----------+");
           }
       }catch(SQLException e){
           e.printStackTrace();
       }
   }
   public boolean getpasentbyId(int id){
       String query="select * from pasent where id=?";
       try{
           PreparedStatement ps=connection.prepareStatement(query);
           ps.setInt(1,id);
           ResultSet rs=ps.executeQuery();
           if(rs.next()){
               return true;
           }else{
               return false;
           }

       }catch(SQLException e){
           e.printStackTrace();
       }
       return false;
   }
}
