package yeppy.service.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import yeppy.service.databaseUtility.*;
import yeppy.service.databaseUtility.mysql.MySQLConnection;
import yeppy.service.logger.ServiceLogger;
import yeppy.service.models.RegisterRequestModel;

import static yeppy.service.databaseUtility.DBConnectionFactory.getConnection;

public class Helper {
    public static String verifyLogin(String username, String password) {
        MySQLConnection db = new MySQLConnection();
        try {
            String sql = "SELECT user_id FROM yeppy.user WHERE username = ? AND password = ?";
            PreparedStatement stmt = db.getCon().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean existUser(String email){
        MySQLConnection db = new MySQLConnection();
        try {
            // Construct the query
            String query =
                    "SELECT COUNT(*) FROM yeppy.user WHERE username=?;";
            // Create the prepared statement
            PreparedStatement ps = db.getCon().prepareStatement(query);
            // Set the parameters
            ps.setString(1, email);

            // Execute query
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getInt(1)>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean insertUserToDb(RegisterRequestModel requestModel){
        MySQLConnection db = new MySQLConnection();
        try {
            // Construct the query
            String query =
                    "INSERT INTO yeppy.user (user_id, username, password) VALUES (?, ?, ?);";
            // Create the prepared statement
            if(db == null){
                System.out.println("db is null");
            }
            if(db.getCon() == null){
                System.out.println("con is null");
            }
            PreparedStatement ps = db.getCon().prepareStatement(query);
            // Set the parameters
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, requestModel.getUsername());
            ps.setString(3, requestModel.getPassword());;

            // Execute query
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int UserLikeBusiness(String userId, String businessId, String[] categories){
        MySQLConnection db = new MySQLConnection();
        try {
            // Construct the query
            String query =
                    "INSERT INTO yeppy.like (user_id, business_id) VALUES (?, ?);";
            // Create the prepared statement
            PreparedStatement ps = db.getCon().prepareStatement(query);
            // Set the parameters
            ps.setString(1, userId);
            ps.setString(2, businessId);

            // Execute query
            ps.execute();

            // If a user likes a business he haven't liked before, then update user_preferences table
            for(String category : categories){
                if(!existCategory(userId, category)){
                    String query2 =
                            "INSERT INTO yeppy.user_preferences (user_id, category, count) VALUES (?, ?, ?);";
                    // Create the prepared statement
                    PreparedStatement ps2 = db.getCon().prepareStatement(query2);
                    // Set the parameters
                    ps2.setString(1, userId);
                    ps2.setString(2, category);
                    ps2.setInt(3, 1);

                    // Execute query
                    ps2.execute();
                } else {
                    String query2 =
                            "UPDATE yeppy.user_preferences SET count = count + 1 WHERE user_id=? AND category=?;";
                    // Create the prepared statement
                    PreparedStatement ps2 = db.getCon().prepareStatement(query2);
                    // Set the parameters
                    ps2.setString(1, userId);
                    ps2.setString(2, category);

                    // Execute query
                    ps2.execute();
                }
            }

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 2;
    }

    public static boolean existCategory(String userId, String category){
        MySQLConnection db = new MySQLConnection();
        try {
            // Construct the query
            String query =
                    "SELECT COUNT(*) FROM yeppy.user_preferences WHERE user_id=? AND category=?;";
            // Create the prepared statement
            PreparedStatement ps = db.getCon().prepareStatement(query);
            // Set the parameters
            ps.setString(1, userId);
            ps.setString(2,category);

            // Execute query
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getInt(1)>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean existLike(String userId, String businessId){
        MySQLConnection db = new MySQLConnection();
        try {
            // Construct the query
            String query =
                    "SELECT COUNT(*) FROM yeppy.like WHERE user_id=? AND business_id=?;";
            // Create the prepared statement
            PreparedStatement ps = db.getCon().prepareStatement(query);
            // Set the parameters
            ps.setString(1, userId);
            ps.setString(2, businessId);

            // Execute query
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getInt(1)>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static List<String> getTopCategories(String userId){
        MySQLConnection db = new MySQLConnection();
        try {
            // Construct the query
            String query =
                    "SELECT category FROM yeppy.user_preferences WHERE user_id=? ORDER BY count DESC LIMIT 3;";
            // Create the prepared statement
            PreparedStatement ps = db.getCon().prepareStatement(query);
            // Set the parameters
            ps.setString(1, userId);

            // Execute query
            ResultSet rs = ps.executeQuery();
            List<String> list = new ArrayList<>();
            while(rs.next()){
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
