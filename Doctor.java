package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Doctor{
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctors(){
        String query="select * from doctor";
        try{
            PreparedStatement ps=connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Doctor : ");
            System.out.println("+-----------+-------------+------------------+");
            System.out.println("| Doctor id | name        |specelization     |");
            System.out.println("+-----------+-------------+------------------+");
            while (rs.next()) {
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String specelization=rs.getString("specelization");
//                System.out.printf("|%-11s|%-13s|%-18s|\n");
                System.out.println(id+"         |"+name+"     |"+specelization+"      |");
                System.out.println("+-----------+-------------+------------------+");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getdoctorbyId(int id){
        String query="select * from Doctor where id=";
//        String query="select * from Doctor where id="+id;
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
