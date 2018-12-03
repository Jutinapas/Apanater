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
        SqlConnection.getSqlConnection().createTypeRoomTable();
        SqlConnection.getSqlConnection().createRoomTable();
        SqlConnection.getSqlConnection().createReservationTable();
        SqlConnection.getSqlConnection().createDebtTable();

        ArrayList<TypeRoom> typeRooms = SqlConnection.getSqlConnection().selectAllTypeRoom();
        String nameType = "typeRoom"+(typeRooms.size()+1);
        SqlConnection.getSqlConnection().insertTypeRoom(nameType,1D,2D);
        idType = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(nameType);

        ArrayList<Room> rooms = SqlConnection.getSqlConnection().selectAllRoom();
        String nameRoom = "room"+(rooms.size()+1);
        SqlConnection.getSqlConnection().insertRoom(nameRoom,idType,300);
        idRoom = SqlConnection.getSqlConnection().getIDroomByNameRoom(nameRoom);

        dateIn= (1980+rooms.size())+"-11-01";
        dateOut = (1980+rooms.size())+"-12-01";

    }


    //ReservationAndDebt
    @Test
    void insertReservation() {
        ArrayList<Reservation> reservationsOld = SqlConnection.getSqlConnection().selectAllReservation();
        SqlConnection.getSqlConnection().insertReservation(LocalDate.parse(dateIn),LocalDate.parse(dateOut),idRoom,"MONTHLY","user","012");
        ArrayList<Reservation> reservationsNew = SqlConnection.getSqlConnection().selectAllReservation();

        assertEquals(reservationsOld.size()+1,reservationsNew.size());
    }

    @Test
    void deleteTypeRoom() {
        SqlConnection.getSqlConnection().insertReservation(LocalDate.parse(dateIn),LocalDate.parse(dateOut),idRoom,"MONTHLY","user","012");
        ArrayList<Reservation> reservationsOld = SqlConnection.getSqlConnection().selectAllReservation();

        int id = SqlConnection.getSqlConnection().getIdReservationByName(dateIn,dateOut,idRoom);
        SqlConnection.getSqlConnection().deleteReservationById(id);
        ArrayList<Reservation> reservationsNew = SqlConnection.getSqlConnection().selectAllReservation();
        assertEquals(reservationsOld.size()-1,reservationsNew.size());

    }

    @Test
    void insertDebt() {
        ArrayList<Debt> debtsOld = SqlConnection.getSqlConnection().selectAllFromDebt();
        SqlConnection.getSqlConnection().insertReservation(LocalDate.parse(dateIn),LocalDate.parse(dateOut),idRoom,"MONTHLY","user","012");
        int id = SqlConnection.getSqlConnection().getIdReservationByName(dateIn,dateOut,idRoom);
        SqlConnection.getSqlConnection().insertDebt(id,dateOut,2D);

        ArrayList<Debt> debtsNew = SqlConnection.getSqlConnection().selectAllFromDebt();
        assertEquals(debtsOld.size()+1,debtsNew.size());
    }


    @Test
    void deleteDebt() {
        SqlConnection.getSqlConnection().insertReservation(LocalDate.parse(dateIn),LocalDate.parse(dateOut),idRoom,"MONTHLY","user","012");
        int id = SqlConnection.getSqlConnection().getIdReservationByName(dateIn,dateOut,idRoom);
        SqlConnection.getSqlConnection().insertDebt(id,dateOut,2D);
        ArrayList<Debt> debtsOld = SqlConnection.getSqlConnection().selectActiveDebt();

        int idDebt = SqlConnection.getSqlConnection().getIDDebtFromIDReserve(id);
        SqlConnection.getSqlConnection().updateStatusInDebt(idDebt);
        ArrayList<Debt> debtsNew = SqlConnection.getSqlConnection().selectActiveDebt();
        assertEquals(debtsOld.size()+1,debtsNew.size());

    }
}
