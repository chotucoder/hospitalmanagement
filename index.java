package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

import static java.lang.Class.forName;

public class HospitalManagmentSystem {
   private static final String  url="jdbc:mysql://127.0.0.1:3306/HospitalManagementSystem";
   private static final String  userName="root";
   private static final String  password="Chotu@1663";
   public static void main(String[] args) {
      try{
         Class.forName("com.mysql.cj.jdbc.Driver");
      }catch(ClassNotFoundException e){
         e.printStackTrace();
      }
      Scanner sc=new Scanner(System.in);
      try{
         Connection connection= DriverManager.getConnection(url,userName,password);
         Pasent pasent =new Pasent(connection,sc);
         Doctor doctor= new Doctor(connection);
         while(true){
            System.out.println(" hospital Management System ");
            System.out.println("1. Add Patient");
            System.out.println("2. view patient");
            System.out.println("3. view doctor");
            System.out.println("4. book appointment");
            System.out.println("5. Exit");
            System.out.println("Enter your choice");
            int choice=sc.nextInt();
            switch(choice){
               case 1:
                  //add patient
                  pasent.addpasent();
                  System.out.println(2);
                  break;
                  case 2:
                     //view patient
                     pasent.viewpasent();
                     System.out.println();
                     break;
                     case 3:
                        //view Doctor
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                        case 4:
                           // book appointment
                           appointment(pasent,doctor,connection,sc);
                           System.out.println();
                           break;
               case 5:
                  //Exit
                  System.out.println("Thank you for using MY  Hospital Management System");
                  return;
               default:
                  System.out.println(" enter valid choice");
            }

         }
      }catch(SQLException e){
         e.printStackTrace();
      }

   }
   public static void appointment( Pasent pasent , Doctor doctor,Connection connection,Scanner sc){
      System.out.println(" enter patient id: ");
      int patientId=sc.nextInt();
      System.out.println(" enter doctor id: ");
      int doctorId=sc.nextInt();
      System.out.println(" enter appointment date(yyyy-MM-dd): ");
      String appointmentDate=sc.next();
      if(pasent.getpasentbyId(patientId) && doctor.getdoctorbyId(doctorId)) {
         if (checkDoctorAvailablity(doctorId, appointmentDate, connection)) {
            String appointmentsQuery = "insert into appointments values(?,?,?)";
            try {
               PreparedStatement preparedStatement = connection.prepareStatement(appointmentsQuery);
               preparedStatement.setInt(1, patientId);
               preparedStatement.setInt(2, doctorId);
               preparedStatement.setString(3, appointmentDate);
               int rowsaffecter = preparedStatement.executeUpdate();
               if (rowsaffecter<0) {
                  System.out.println("Appointment booked successfully");
               } else {
                  System.out.println("Appointment booking failed");
               }
            } catch (SQLException e) {
               e.printStackTrace();
            }
         } else {
            System.out.println(" doctor is not avalibleon this date ");
         }
      }else{
         System.out.println(" Either doctor or patient is doesnt exists!!!.. ");
      }

   }
   public  static boolean checkDoctorAvailablity(int doctorId,String appointmentDate,Connection connection){
      String query="select count(*)from appointments where doctor_id=? AND appointment_date=?";
     try{
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1, doctorId);
        preparedStatement.setString(2, appointmentDate);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()) {
           int count = resultSet.getInt(1);
              if(count==0){
              return true;
              }else{
              return false;
           }
        }
     }catch(SQLException e){
        e.printStackTrace();
     }
     return false;
   }
}
