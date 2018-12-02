package model;

public class TypeRoom {

    private int idTypeRoom ;
    private  String typeRoom;
    private  double rentPerMonth;
    private double rentPerDay ;
    private String status;

    public TypeRoom(int idTypeRoom, String typeRoom, double rentPerMonth, double rentPerDay, String status) {
        this.idTypeRoom = idTypeRoom;
        this.typeRoom = typeRoom;
        this.rentPerMonth = rentPerMonth;
        this.rentPerDay = rentPerDay;
        this.status = status;
    }

    public int getIdTypeRoom() {
        return idTypeRoom;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public double getRentPerDay() {
        return rentPerDay;
    }

    public double getRentPerMonth() {
        return rentPerMonth;
    }

    public String getStatus() {
        return status;
    }

}
