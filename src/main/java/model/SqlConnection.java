package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

//hi
public class SqlConnection {

    private static SqlConnection sqlConnection =null;



    private SqlConnection(){};

    public static SqlConnection getSqlConnection() {
        if (sqlConnection ==null){
            sqlConnection = new SqlConnection();
        }

        return sqlConnection;
    }

    private static Connection connect() {
        Connection connection =null;

        try {
            if (connection ==null) {
                Class.forName("org.sqlite.JDBC");
                String dbURL = "jdbc:sqlite:Apartment.db";
                connection = DriverManager.getConnection(dbURL);
            }
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return connection;
    }



    public static boolean checkTableExist(Connection conn, String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }


    public  void createAllTable(){
        createApartmentTable();
        createDebtTable();
        createReservationTable();
        createRoomTable();
        createTypeRoomTable();


    }

    public  void createApartmentTable(){
        Connection c = connect();

        try {
            if (!checkTableExist(c,"Apartment")) {
                if (c != null) {
                    String query = "CREATE TABLE `Apartment` (\n" +
                            "\t`id_apartment`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "\t`name_apartment`\tTEXT,\n" +
                            "\t`date_pay_money`\tTEXT,\n" +
                            "\t`status`\tTEXT\n" +
                            ");";
                    Statement s = c.createStatement();
                    s.execute(query);
                    s.close();


                 //   System.out.println("create Apartment table");
                    String query2 = "Insert into Apartment(name_apartment,date_pay_money,status) values(?,?,?) ";
                    PreparedStatement test = c.prepareStatement(query2);
                    test.setString(1,"default_name");
                    test.setString(2,"1");
                    test.setString(3,"active");
                    test.executeUpdate();
                    test.close();
                    c.close();

                }
            }
        } catch (SQLException e) {

          //  System.out.println(e);
        }

    }
    public  void createDebtTable(){
        Connection c = connect();
        try {
            if (!checkTableExist(c,"Debt")) {
                if (c != null) {
                    String query = "CREATE TABLE `Debt` (\n" +
                            "\t`id_debt`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "\t`id_reserve`\tINTEGER,\n" +
                            "\t`date_pay_debt`\tTEXT,\n" +
                            "\t`debt_balance`\tREAL,\n" +
                            "\t`status`\tTEXT,\n" +
                            "\tFOREIGN KEY(`id_reserve`) REFERENCES `Reservation`(`id_reserve`)\n" +
                            ");";
                    Statement s = c.createStatement();
                    s.execute(query);

                    c.close();
                  //  System.out.println("create Debt table");
                }
            }
        } catch (SQLException e) {
          //  System.out.println(e);
        }

    }
    public  void createReservationTable(){
        Connection c = connect();

        try {
            if (!checkTableExist(c,"Reservation")) {
                if (c != null) {
                    String query = "CREATE TABLE `Reservation` (\n" +
                            "\t`id_reserve`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "\t`date_check_in`\tTEXT,\n" +
                            "\t`date_check_out`\tTEXT,\n" +
                            "\t`id_room`\tINTEGER,\n" +
                            "\t`type_reserve`\tTEXT,\n" +
                            "\t`name_guest`\tTEXT,\n" +
                            "\t`phone_number`\tTEXT,\n" +
                            "\t`status`\tTEXT,\n" +
                            "\tFOREIGN KEY(`id_room`) REFERENCES `Room`\n" +
                            ");";
                    Statement s = c.createStatement();
                    s.execute(query);

                    c.close();
                   // System.out.println("create Reservation table");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    public  void createRoomTable(){
        Connection c = connect();
        try {
            if (!checkTableExist(c,"Room")) {
                if (c != null) {
                    String query = "CREATE TABLE `Room` (\n" +
                            "\t`id_room`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "\t`room_name`\tTEXT,\n" +
                            "\t`id_type_room`\tINTEGER,\n" +
                            "\t`floor`\tINTEGER,\n" +
                            "\t`status`\tTEXT,\n" +
                            "\tFOREIGN KEY(`id_type_room`) REFERENCES `TypeRoom`\n" +
                            ");";
                    Statement s = c.createStatement();
                    s.execute(query);

                    c.close();
                  //  System.out.println("create Room table");
                }
            }
        } catch (SQLException e) {
           // System.out.println(e);
        }

    }
    public  void createTypeRoomTable(){
        Connection c = connect();
        try {
            if (!checkTableExist(c,"TypeRoom")) {
                if (c != null) {
                    String query = "CREATE TABLE `TypeRoom` (\n" +
                            "\t`id_type_room`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "\t`type_room`\tTEXT,\n" +
                            "\t`rent_per_month`\tREAL,\n" +
                            "\t`rent_per_day`\tREAL,\n" +
                            "\t`status`\tTEXT\n" +
                            ");";
                    Statement s = c.createStatement();
                    s.execute(query);

                    c.close();
                   // System.out.println("create TypeRoom table");
                }
            }
        } catch (SQLException e) {
          //  System.out.println(e);
        }

    }
    public  String selectNameOfApartment(){
        Connection c = connect();
        String nameApartment ="";
        try {
//            System.out.println("aa");
            if (c != null) {
                String query = "Select name_apartment from Apartment where status = 'active' ";
                Statement s = c.createStatement();

                ResultSet rs = s.executeQuery(query);
                nameApartment = rs.getString(1);
                c.close();
            }
        }catch (SQLException e){
           // System.out.println("eerrrrr");
            System.out.println(e);
        }
        return nameApartment;
    }

    public  String selectDatePayMoney(){
        Connection c = connect();
        String datePayMoney ="";
        try {
            if (c != null) {
                String query = "Select date_pay_money from Apartment where status = 'active'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);
                datePayMoney = rs.getString(1);
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return datePayMoney;

    }

    public  void updateApartmentNameAndDatePayMoney(String nameApart , String datePay){
        Connection c = connect();

        try {
            if (c != null) {
                String query = "Update Apartment Set status = ? Where status = ?";

                PreparedStatement ps = c.prepareStatement(query);
                ps.setString(1,"unactive");
                ps.setString(2,"active");
                ps.executeUpdate();
                ps.close();


                String query2 = "Insert into Apartment(name_apartment,date_pay_money,status) values(?,?,?) ";
                PreparedStatement s = c.prepareStatement(query2);
                s.setString(1,nameApart);
                s.setString(2,datePay);
                s.setString(3,"active");
                s.executeUpdate();
                s.close();


                c.close();



            }
        }catch (SQLException e){
            System.out.println(e);
        }

    }




    public String selectSubjectName(){
        Connection c = connect();
        String subName ="";
        try {
            if (c != null) {
                String query = "Select subjectName from Course";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);
                subName = rs.getString(1);
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return subName;
    }

    //เพิ่มชนิดห้องพัก
    public void insertTypeRoom(String typeRoom ,double rentPerMonth,double rentPerDay){
        Connection c = connect();

        try {
            if (c != null) {
                        String query2 ="Insert into TypeRoom(type_room,rent_per_month,rent_per_day,status) values(?,?,?,?)";
                        PreparedStatement p = c.prepareStatement(query2);
                        p.setString(1,typeRoom);
                        p.setDouble(2,rentPerMonth);
                        p.setDouble(3,rentPerDay);
                        p.setString(4,"active");
                        p.executeUpdate();

                c.close();

            }
        }catch (SQLException e){
            System.out.println(e);
        }

    }


       //แสดงชนิดห้องพักทั้งหมด
    public ArrayList<TypeRoom> selectAllTypeRoom(){

        Connection c = connect();
        ArrayList<TypeRoom> r = new ArrayList<>();
        try {
            if (c != null) {
                String query = "Select * from TypeRoom where status = 'active'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);
                while (rs.next()){

                    r.add(new TypeRoom(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4),rs.getString(5)));
                }
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;
    }

    // แก้ไขชนิดห้องพัก
    public void updateTypeRoom(int idTypeRoom ,String name ,Double month ,Double day){

        Connection c = connect();

        try {
            if (c != null) {
                String query = "Update TypeRoom Set type_room = ? , rent_per_month = ? , rent_per_day = ? Where id_type_room = ?";
                PreparedStatement ps = c.prepareStatement(query);
                ps.setString(1,name);
                ps.setDouble(2,month);
                ps.setDouble(3,day);
                ps.setInt(4,idTypeRoom);
                ps.executeUpdate();
                ps.close();

                c.close();



            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    //ลบชนิดห้องพัก
    public void deleteTypeRoom(int idTypeRoom ){

        Connection c = connect();

        try {
            if (c != null) {
                String query = "Update TypeRoom Set status = ? Where id_type_room = ?";

                PreparedStatement ps = c.prepareStatement(query);
                ps.setString(1,"unactive");
               ps.setInt(2,idTypeRoom);
                ps.executeUpdate();
                ps.close();
                c.close();



            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    //เพิ่มห้องพัก
    public void insertRoom(String nameRoom,int id_type_Room,int floor){
        Connection c = connect();
        try {
            if (c != null) {

                        String query2 ="Insert into Room(room_name,id_type_room,floor,status) values(?,?,?,?)";
                        PreparedStatement p = c.prepareStatement(query2);
                        p.setString(1,nameRoom);
                        p.setInt(2,id_type_Room);
                        p.setInt(3,floor);
                        p.setString(4,"active");
                        p.executeUpdate();
                c.close();

            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }


    public int getIDroomByNameRoom(String n){

        Connection c = connect();
      int subName =0;
        try {
            if (c != null) {
                String query = "Select id_room from Room where room_name = '"+n+"'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);

                subName = rs.getInt(1);
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return subName;

    }

    public int getIDTyperoomFromNameTypeRoom(String n){
        Connection c = connect();
        int subName =0;
        try {
            if (c != null) {
                String query = "Select id_type_room from TypeRoom where type_room = '"+n+"'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);

                subName = rs.getInt(1);
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return subName;

    }

    //ลบห้องพัก
    public void deleteRoom(int idRoom ){

        Connection c = connect();

        try {
            if (c != null) {
                String query = "Update Room Set status = ? Where id_room = ?";

                PreparedStatement ps = c.prepareStatement(query);
                ps.setString(1,"unactive");
                ps.setInt(2,idRoom);
                ps.executeUpdate();
                ps.close();
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }
    //แก้ไขห้องพัก
    public void updateRoom(int id_room,String nameRoom,int floor,int idTypeRoom){

        Connection c = connect();

        try {
            if (c != null) {
                String query = "Update Room Set room_name = ? , floor = ? , id_type_room = ? Where id_room = ?";
                PreparedStatement ps = c.prepareStatement(query);
                ps.setString(1,nameRoom);
                ps.setInt(2,floor);
                ps.setInt(3,idTypeRoom);
                ps.setInt(4,id_room);
                ps.executeUpdate();
                ps.close();
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    //แสดงห้องพัก
    public ArrayList<Room>selectAllRoom(){
        Connection c = connect();
        ArrayList<Room> r = new ArrayList<>();
        try {
            if (c != null) {
                int count =0;
                String query2 = "Select count(id_room)  from Room";
                Statement s2 = c.createStatement();
                ResultSet rs2 = s2.executeQuery(query2);
                while (rs2.next()){
                    count = rs2.getInt(1);
                }
                if (count==0) {
                    System.out.println("no room");
                }
                else {
                    String query = "Select * from Room where status ='active'";
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery(query);
                    while (rs.next()) {
                        r.add(new Room(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
                    }
                }

                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;

    }

    public ArrayList<Room>selectAllRoomWithType(int id){
        Connection c = connect();
        ArrayList<Room> r = new ArrayList<>();
        try {
            if (c != null) {
                int count =0;
                String query2 = "Select count(id_room) from Room";
                Statement s2 = c.createStatement();
                ResultSet rs2 = s2.executeQuery(query2);
                while (rs2.next()){
                    count = rs2.getInt(1);
                }
                if (count==0) {
                    System.out.println("no room");
                }
                else {
                    String query = "Select * from Room where status ='active' and id_type_room ='" + id + "'";
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery(query);
                    while (rs.next()) {
                        r.add(new Room(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
                    }
                }

                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;

    }

    public ArrayList<Room>selectAllRoomWithFloor(int floor){
        Connection c = connect();
        ArrayList<Room> r = new ArrayList<>();
        try {
            if (c != null) {
                int count =0;
                String query2 = "Select count(id_room) from Room";
                Statement s2 = c.createStatement();
                ResultSet rs2 = s2.executeQuery(query2);
                while (rs2.next()){
                    count = rs2.getInt(1);
                }
                if (count==0) {
                    System.out.println("no room");
                }
                else {
                    String query = "Select * from Room where status ='active' and floor ='" + floor + "'";
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery(query);
                    while (rs.next()) {
                        r.add(new Room(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
                    }
                }

                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;

    }

    public ArrayList<Room>selectAllRoomWithTypeAndFloor(int id, int floor){
        Connection c = connect();
        ArrayList<Room> r = new ArrayList<>();
        try {
            if (c != null) {
                int count =0;
                String query2 = "Select count(id_room) from Room";
                Statement s2 = c.createStatement();
                ResultSet rs2 = s2.executeQuery(query2);
                while (rs2.next()){
                    count = rs2.getInt(1);
                }
                if (count==0) {
                    System.out.println("no room");
                }
                else {
                    String query = "Select * from Room where status ='active' and id_type_room ='" + id + "' and floor ='" + floor + "'";
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery(query);
                    while (rs.next()) {
                        r.add(new Room(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
                    }
                }

                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;
    }
    public void deleteReservationById(int id){

        Connection c = connect();

        try {
            if (c != null) {
                String query = "Update Reservation Set status = ? Where id_reserve = ?";

                PreparedStatement ps = c.prepareStatement(query);
                ps.setString(1,"unactive");
                ps.setInt(2,id);
                ps.executeUpdate();
                ps.close();
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public ArrayList<Reservation> selectReservationWithRoom(int id) {
        Connection c = connect();
        ArrayList<Reservation> r = new ArrayList<>();
        try {
            if (c != null) {
                int count =0;
                String query2 = "Select count(id_reserve) from Reservation";
                Statement s2 = c.createStatement();
                ResultSet rs2 = s2.executeQuery(query2);
                while (rs2.next()){
                    count = rs2.getInt(1);
                }
                if (count==0) {
                    System.out.println("no reserve");
                }
                else {
                    String query = "Select * from Reservation where status ='active' and id_room ='" + id + "'";
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery(query);
                    while (rs.next()) {
                        r.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
                    }
                }

                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;
    }


    public SortedSet<Integer> selectIDRoomThatReservationNotInRange(LocalDate date_in, LocalDate date_out){

        Connection c = connect();
        SortedSet<Integer> r = new TreeSet<>();
        try {
            if (c != null) {

                String query = "Select * from Reservation where status = 'active'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {

                    String date_check_in_of_database = rs.getString(2);
                    String date_check_out_of_database = rs.getString(3);
                    //convert string in database to local date because in database we keep type date in string
                    LocalDate ld1 = LocalDate.parse(date_check_in_of_database);
                    LocalDate ld2 = LocalDate.parse(date_check_out_of_database);

                    if (!(date_out.isBefore(ld1) || date_in.isAfter(ld2)))
                        r.add(rs.getInt(4));

                }

                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;

    }


    public SortedSet<Integer> selectIDRoomThatReservationNotInRangeFilterByIdTypeRoom(LocalDate date_in, LocalDate date_out,int idTypeRoom){

        Connection c = connect();
        SortedSet<Integer> r = new TreeSet<>();
        try {
            if (c != null) {

                String query = "Select * from Reservation left join Room on Reservation.id_room = Room.id_room";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {
                    String date_check_in_of_database = rs.getString(2);
                    String date_check_out_of_database = rs.getString(3);
                    //convert string in database to local date because in database we keep type date in string
                    LocalDate ld1 = LocalDate.parse(date_check_in_of_database);
                    LocalDate ld2 = LocalDate.parse(date_check_out_of_database);
                    if (!(date_out.isBefore(ld1) || date_in.isAfter(ld2)))
                        r.add(rs.getInt(4));

                }

                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;

    }

    public SortedSet<Integer> selectIDRoomThatReservationNotInRangeFilterByFloor(LocalDate date_in, LocalDate date_out,int floor){

        Connection c = connect();
        SortedSet<Integer> r = new TreeSet<>();
        try {
            if (c != null) {

                String query = "Select * from Reservation left join Room on Reservation.id_room = Room.id_room";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {
                    String date_check_in_of_database = rs.getString(2);
                    String date_check_out_of_database = rs.getString(3);
                    //convert string in database to local date because in database we keep type date in string
                    LocalDate ld1 = LocalDate.parse(date_check_in_of_database);
                    LocalDate ld2 = LocalDate.parse(date_check_out_of_database);
                    if (!(date_out.isBefore(ld1) || date_in.isAfter(ld2)))
                        r.add(rs.getInt(4));

                }

                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;

    }

    public SortedSet<Integer> selectIDRoomThatReservationNotInRangeFilterByIdTypeRoomAndFloor(LocalDate date_in, LocalDate date_out,int idTypeRoom,int floor){

        Connection c = connect();
        SortedSet<Integer> r = new TreeSet<>();
        try {
            if (c != null) {

                String query = "Select * from Reservation left join Room on Reservation.id_room = Room.id_room";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {
                    String date_check_in_of_database = rs.getString(2);
                    String date_check_out_of_database = rs.getString(3);
                    //convert string in database to local date because in database we keep type date in string
                    LocalDate ld1 = LocalDate.parse(date_check_in_of_database);
                    LocalDate ld2 = LocalDate.parse(date_check_out_of_database);
                    if (!(date_out.isBefore(ld1) || date_in.isAfter(ld2)))
                        r.add(rs.getInt(4));

                }

                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;

    }




    //get room by id
    public Room getRoomByID(int idRoom){
        Connection c = connect();

        try {
            if (c != null) {


                String query = "Select * from Room where status = 'active' and id_room = '"+Integer.toString(idRoom)+"'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);
                Room r = new Room(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getString(5));


                c.close();
                return r;
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return null;
    }


    //get typeroom by id
    public TypeRoom getTypeRoomByID(int idTypeRoom){
        Connection c = connect();

        try {
            if (c != null) {


                String query = "Select * from TypeRoom where status = 'active' and id_type_room = '"+idTypeRoom+"'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);
                TypeRoom r = new TypeRoom(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4),rs.getString(5));


                c.close();
                return r;
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return null;
    }

    //get Reservation by id
    public Reservation getReservationByID(int idReserve){
        Connection c = connect();

        try {
            if (c != null) {


                String query = "Select * from Reservation where status = 'active' and id_reserve = '"+Integer.toString(idReserve)+"'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);
                Reservation r = new Reservation(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));


                c.close();
                return r;
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return null;
    }

    //get ArraysList Debt

    public ArrayList<Debt> selectAllFromDebt(){

        Connection c = connect();
        ArrayList<Debt> r = new ArrayList<>();
        try {
            if (c != null) {

                String query = "Select * from Debt where status = 'active'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {
                    r.add(new Debt(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getDouble(4),rs.getString(5)));

                }
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return r;

    }

    public ArrayList<Debt> selectAllFromDebtNotPaid(){

        Connection c = connect();
        ArrayList<Debt> r = new ArrayList<>();
        try {
            if (c != null) {

                String query = "Select * from Debt where status = 'unactive'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);

                while (rs.next()) {
                    r.add(new Debt(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getDouble(4),rs.getString(5)));

                }
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return r;

    }

    public void insertDebt(int id_reserve, String date_pay_debt, double debt_balance){
        Connection c = connect();
        int count =0;
        try {
            if (c != null) {

                if (count==0) {
                    String query2 = "Insert into Debt(id_reserve, date_pay_debt, debt_balance, status) values(?,?,?,?)";
                    PreparedStatement p = c.prepareStatement(query2);
                    p.setString(1, id_reserve + "");
                    p.setString(2, date_pay_debt);
                    p.setDouble(3, debt_balance);
                    p.setString(4,"active");
                    p.executeUpdate();
                }
                c.close();

            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }



    public String getStringTypeRoomFromIDRoom(int id){

        Connection c = connect();
        String subName ="";
        try {
            if (c != null) {
                String query = "Select type_room from TypeRoom where id_type_room = '"+Integer.toString(id)+"'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);

                subName = rs.getString(1);
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return subName;

    }

    public void insertReservation(LocalDate dIn,LocalDate dOut,int idRoom ,String typeReserve,String nameGuest,String phoneNum){
        Connection c = connect();
        int count =0;
        try {
            if (c != null) {
                if (count==0) {
                    String query2 = "Insert into Reservation(date_check_in,date_check_out,id_room,type_reserve,name_guest,phone_number,status) values(?,?,?,?,?,?,?)";
                    PreparedStatement p = c.prepareStatement(query2);
                    p.setString(1, dIn.toString());
                    p.setString(2, dOut.toString());
                    p.setInt(3, idRoom);
                    p.setString(4, typeReserve);
                    p.setString(5,nameGuest);
                    p.setString(6,phoneNum);
                    p.setString(7,"active");
                    p.executeUpdate();
                    p.close();
                }
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public int getRecentReservation() {
        int id = 0;
        Connection c = connect();
        try {
            if (c != null) {

                String query = "SELECT * FROM Reservation ORDER BY id_reserve DESC LIMIT 1";
                Statement statement = c.createStatement();
                ResultSet rs = statement.executeQuery(query);
                id = rs.getInt(1);
                c.close();

            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return id;
    }














/*
    public static ArrayList<Course> selectRow(){
        Connection c = connect();
        ArrayList<Course> h = new ArrayList<>();
        try {
            if (c != null) {
                String query = "Select * from Course";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);
                while (rs.next()){
                    h.add(new Course(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
                }
                c.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return h;

    }
    */














}
