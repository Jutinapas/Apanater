package model;

public class Room {

    private int id_room;
    private String room_name;
    private int id_type_room;
    private int floor;
    private String status;

    public Room(int id_room, String room_name, int id_type_room, int floor, String status) {
        this.id_room = id_room;
        this.room_name = room_name;
        this.id_type_room = id_type_room;
        this.floor = floor;
        this.status = status;
    }

    public int getId_room() {
        return id_room;
    }

    public String getRoom_name() {
        return room_name;
    }

    public int getId_type_room() {
        return id_type_room;
    }

    public int getFloor() {
        return floor;
    }

    public String getStatus() {
        return status;
    }
}
