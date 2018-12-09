import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestReservationAndDebt {
    String dateIn,dateOut;
    int idType,idRoom;

    @BeforeEach
    void init(){
        DBConnector.getDBConnector().createTypeRoomTable();
        DBConnector.getDBConnector().createRoomTable();
        DBConnector.getDBConnector().createReservationTable();
        DBConnector.getDBConnector().createDebtTable();

        ArrayList<TypeRoom> typeRooms = DBConnector.getDBConnector().selectAllTypeRoom();
        String nameType = "typeRoom"+(typeRooms.size()+1);
        DBConnector.getDBConnector().insertTypeRoom(nameType,1D,2D);
        idType = DBConnector.getDBConnector().getIDTyperoomFromNameTypeRoom(nameType);

        ArrayList<Room> rooms = DBConnector.getDBConnector().selectAllRoom();
        String nameRoom = "room"+(rooms.size()+1);
        DBConnector.getDBConnector().insertRoom(nameRoom,idType,300);
        idRoom = DBConnector.getDBConnector().getIDroomByNameRoom(nameRoom);

        dateIn= (1980+rooms.size())+"-11-01";
        dateOut = (1980+rooms.size())+"-12-01";

    }


    //ReservationAndDebt
    @Test
    void insertReservation() {
        ArrayList<Reservation> reservationsOld = DBConnector.getDBConnector().selectAllReservation();
        DBConnector.getDBConnector().insertReservation(LocalDate.parse(dateIn),LocalDate.parse(dateOut),idRoom,"MONTHLY","user","012");
        ArrayList<Reservation> reservationsNew = DBConnector.getDBConnector().selectAllReservation();

        assertEquals(reservationsOld.size()+1,reservationsNew.size());
    }

    @Test
    void deleteTypeRoom() {
        DBConnector.getDBConnector().insertReservation(LocalDate.parse(dateIn),LocalDate.parse(dateOut),idRoom,"MONTHLY","user","012");
        ArrayList<Reservation> reservationsOld = DBConnector.getDBConnector().selectAllReservation();

        int id = DBConnector.getDBConnector().getIdReservationByName(dateIn,dateOut,idRoom);
        DBConnector.getDBConnector().deleteReservationById(id);
        ArrayList<Reservation> reservationsNew = DBConnector.getDBConnector().selectAllReservation();
        assertEquals(reservationsOld.size()-1,reservationsNew.size());

    }

    @Test
    void insertDebt() {
        ArrayList<Debt> debtsOld = DBConnector.getDBConnector().selectAllFromDebt();
        DBConnector.getDBConnector().insertReservation(LocalDate.parse(dateIn),LocalDate.parse(dateOut),idRoom,"MONTHLY","user","012");
        int id = DBConnector.getDBConnector().getIdReservationByName(dateIn,dateOut,idRoom);
        DBConnector.getDBConnector().insertDebt(id,dateOut,2D);

        ArrayList<Debt> debtsNew = DBConnector.getDBConnector().selectAllFromDebt();
        assertEquals(debtsOld.size()+1,debtsNew.size());
    }


    @Test
    void deleteDebt() {
        DBConnector.getDBConnector().insertReservation(LocalDate.parse(dateIn),LocalDate.parse(dateOut),idRoom,"MONTHLY","user","012");
        int id = DBConnector.getDBConnector().getIdReservationByName(dateIn,dateOut,idRoom);
        DBConnector.getDBConnector().insertDebt(id,dateOut,2D);
        ArrayList<Debt> debtsOld = DBConnector.getDBConnector().selectActiveDebt();

        int idDebt = DBConnector.getDBConnector().getIDDebtFromIDReserve(id);
        DBConnector.getDBConnector().updateStatusInDebt(idDebt);
        ArrayList<Debt> debtsNew = DBConnector.getDBConnector().selectActiveDebt();
        assertEquals(debtsOld.size()+1,debtsNew.size());

    }
}
