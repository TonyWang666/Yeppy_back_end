package yeppy.service.core;

import yeppy.service.databaseUtility.mysql.MySQLConnection;
import yeppy.service.models.RegisterRequestModel;

import java.sql.Connection;

import static yeppy.service.core.Helper.insertUserToDb;

//public class main {
//    public static void main(String[] args){
//        Connection con = new MySQLConnection().getCon();
//        RegisterRequestModel rm = new RegisterRequestModel("root2","password");
//        insertUserToDb(rm);
//    }
//}
