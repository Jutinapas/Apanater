package model;

public class Reservation {
    private int id_reservation;
    private String date_check_in;
    private String date_check_out;
    private int id_room;
    private String type_reserve;
    private String name_guest;
    private String phone_number;
    private String status;


    public Reservation(int id_reservation, String date_check_in, String date_check_out, int id_room, String type_reserve, String name_guest, String phone_number, String status) {
        this.id_reservation = id_reservation;
        this.date_check_in = date_check_in;
        this.date_check_out = date_check_out;
        this.id_room = id_room;
        this.type_reserve = type_reserve;
        this.name_guest = name_guest;
        this.phone_number = phone_number;
        this.status = status;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public String getDate_check_in() {
        return date_check_in;
    }

    public String getDate_check_out() {
        return date_check_out;
    }

    public int getId_room() {
        return id_room;
    }

    public String getType_reserve() {
        return type_reserve;
    }

    public String getName_guest() {
        return name_guest;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getStatus() {
        return status;
    }

}
