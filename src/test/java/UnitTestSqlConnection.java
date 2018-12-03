import model.SqlConnection;
import model.TypeRoom;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestSqlConnection {


    @Test
    void selectNameOfApartment() {
        String s = SqlConnection.getSqlConnection().selectNameOfApartment();
        assertEquals("default_name",s);
    }

    @Test
    void  selectDatePayMoney() {
    String s = SqlConnection.getSqlConnection().selectDatePayMoney();
    assertEquals("1",s);

    }

    @Test
    void   checkThisTypeRoomIsAlreadyExist() {
        boolean b = SqlConnection.getSqlConnection().checkThisTypeRoomIsAlreadyExist("test1");
        assertEquals(true,b);

    }

    // Not good
    @Test
    void   checkThisRoomIsAlreadyExist() {
        boolean b = SqlConnection.getSqlConnection().checkThisTypeRoomIsAlreadyExist("b2000");
        assertEquals(false,b);

    }


    @Test
    void   insertTypeRoom() {
        SqlConnection.getSqlConnection().insertTypeRoom("unitest",1D,2D);
        assertEquals(19,SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom("unitest"));

    }


    @Test
    void   selectAllTypeRoom() {
        ArrayList<TypeRoom> typeRooms = SqlConnection.getSqlConnection().selectAllTypeRoom();
        assertEquals(3,typeRooms.size());
    }


    @Test
    void   updateTypeRoom() {
        SqlConnection.getSqlConnection().updateTypeRoom(19,"unitest4",3D,4D);

        assertEquals(19,SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom("unitest4"));
    }

    @Test
    void    getIDTyperoomFromNameTypeRoom(){
        assertEquals(19,SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom("unitest4"));
    }


    @Test
    void   deleteTypeRoom() {
        SqlConnection.getSqlConnection().deleteTypeRoom(19);

        ArrayList<TypeRoom> typeRooms = SqlConnection.getSqlConnection().selectAllTypeRoom();
        assertEquals(2,typeRooms.size());
    }


    @Test
    void   insertRoom() {
        SqlConnection.getSqlConnection().insertRoom("unitestroom",16,1);
        assertEquals(11,SqlConnection.getSqlConnection().getIDroomByNameRoom("unitestroom"));
    }

    @Test
    void    getIDroomByNameRoom() {
        assertEquals(11,SqlConnection.getSqlConnection().getIDroomByNameRoom("unitestroom"));
    }

    @Test
    void    deleteRoom() {
        SqlConnection.getSqlConnection().deleteRoom(11);
        assertEquals(2,SqlConnection.getSqlConnection().selectAllRoom().size());
    }

    @Test
    void    updateRoom() {
        SqlConnection.getSqlConnection().updateRoom(11,"unitestroom4",2,17);
        assertEquals(11,SqlConnection.getSqlConnection().getIDroomByNameRoom("unitestroom4"));
    }

    @Test
    void    selectAllRoom() {
        assertEquals(2,SqlConnection.getSqlConnection().selectAllRoom().size());
    }

    @Test
    void    selectAllRoomWithType() {
        assertEquals(1,SqlConnection.getSqlConnection().selectAllRoomWithType(17).size());
    }

    @Test
    void    selectAllRoomWithFloor() {
        assertEquals(1,SqlConnection.getSqlConnection().selectAllRoomWithFloor(201).size());
    }

    @Test
    void    selectAllRoomWithTypeAndFloor() {
        assertEquals(1,SqlConnection.getSqlConnection().selectAllRoomWithTypeAndFloor(16,201).size());
    }

    @Test
    void    selectReservationWithRoom() {
        assertEquals(1,SqlConnection.getSqlConnection().selectReservationWithRoom(6).size());
    }


    @Test
    void    deleteReservationById() {
        SqlConnection.getSqlConnection().deleteReservationById(7);
        assertEquals(null,SqlConnection.getSqlConnection().getReservationByID(7));
    }

    @Test
    void   selectIDRoomThatReservationNotInRange() {
        SqlConnection.getSqlConnection().deleteReservationById(7);
        assertEquals(1,SqlConnection.getSqlConnection().selectIDRoomThatReservationNotInRange(LocalDate.parse("2019-12-13"),LocalDate.parse("2020-01-13")).size());
    }

    @Test
    void   getTypeRoomByID() {
        assertEquals("test1",SqlConnection.getSqlConnection().getTypeRoomByID(16).getTypeRoom());
    }

    @Test
    void   getReservationByID() {
        assertEquals("025852825",SqlConnection.getSqlConnection().getReservationByID(9).getPhone_number());
    }


    @Test
    void    getStringTypeRoomFromIDRoom() {
        assertEquals("test1",SqlConnection.getSqlConnection().getStringTypeRoomFromIDRoom(16));
    }

    @Test
    void    getRecentReservation() {
        assertEquals(9,SqlConnection.getSqlConnection().getRecentReservation());
    }
}
