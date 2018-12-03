import model.SqlConnection;
import model.TypeRoom;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestApartment {


    @BeforeEach
    void setup(){
        SqlConnection.getSqlConnection().createApartmentTable();
    }

    //Apartment
    @Test
    void updateApartmentName() {
        String nameOld = SqlConnection.getSqlConnection().selectNameOfApartment();
        SqlConnection.getSqlConnection().updateApartmentNameAndDatePayMoney(nameOld + "1", "2");
        String nameNew = SqlConnection.getSqlConnection().selectNameOfApartment();
        assertEquals(true, !nameNew.equals(nameOld));
    }

}

//-------------------------------------------------------------------------------------------------

////  TypeRoom
//
//    @Test
//    void   insertTypeRoom() {
//        ArrayList<TypeRoom> typeRoomsTest = SqlConnection.getSqlConnection().selectAllTypeRoom();
//        assertEquals(typeRooms.size()+4,typeRoomsTest.size());
//
//    }
//
//    @Test
//    void   checkThisTypeRoomIsAlreadyExist() {
//        boolean b = SqlConnection.getSqlConnection().checkThisTypeRoomIsAlreadyExist("testTypeRoom2");
//        assertEquals(true,b);
//    }
//
//
//    @Test
//    void   updateTypeRoom() {
//        SqlConnection.getSqlConnection().updateTypeRoom(id3,"testTypeRoom3Update",3D,4D);
//        assertEquals(id3,SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom("testTypeRoom3Update"));
//    }
//
//
//    @Test
//    void   deleteTypeRoom() {
//        SqlConnection.getSqlConnection().deleteTypeRoom(id4);
//        ArrayList<TypeRoom> typeRoomsDelete = SqlConnection.getSqlConnection().selectAllTypeRoom();
//        assertEquals(typeRoomsAdd.size()-1,typeRoomsDelete.size());
//    }
//
//
//    //-------------------------------------------------------------------------------------------------------------------
//
//
//
//
////    @Test
////    void   checkThisRoomIsAlreadyExist() {
////        boolean b = SqlConnection.getSqlConnection().checkThisRoomIsAlreadyExist("b2000");
////        assertEquals(true,b);
////
////    }
////
////
////
////
////    @Test
////    void   insertRoom() {
////        SqlConnection.getSqlConnection().insertRoom("unitestroom",16,1);
////        assertEquals(11,SqlConnection.getSqlConnection().getIDroomByNameRoom("unitestroom"));
////    }
////
////    @Test
////    void    getIDroomByNameRoom() {
////        assertEquals(11,SqlConnection.getSqlConnection().getIDroomByNameRoom("unitestroom"));
////    }
////
////    @Test
////    void    deleteRoom() {
////        SqlConnection.getSqlConnection().deleteRoom(11);
////        assertEquals(2,SqlConnection.getSqlConnection().selectAllRoom().size());
////    }
////
////    @Test
////    void    updateRoom() {
////        SqlConnection.getSqlConnection().updateRoom(11,"unitestroom4",2,17);
////        assertEquals(11,SqlConnection.getSqlConnection().getIDroomByNameRoom("unitestroom4"));
////    }
////
////    @Test
////    void    selectAllRoom() {
////        assertEquals(2,SqlConnection.getSqlConnection().selectAllRoom().size());
////    }
////
////    @Test
////    void    selectAllRoomWithType() {
////        assertEquals(1,SqlConnection.getSqlConnection().selectAllRoomWithType(17).size());
////    }
////
////    @Test
////    void    selectAllRoomWithFloor() {
////        assertEquals(1,SqlConnection.getSqlConnection().selectAllRoomWithFloor(201).size());
////    }
////
////    @Test
////    void    selectAllRoomWithTypeAndFloor() {
////        assertEquals(1,SqlConnection.getSqlConnection().selectAllRoomWithTypeAndFloor(16,201).size());
////    }
////
////    @Test
////    void    selectReservationWithRoom() {
////        assertEquals(1,SqlConnection.getSqlConnection().selectReservationWithRoom(6).size());
////    }
////
////
////    @Test
////    void    deleteReservationById() {
////        SqlConnection.getSqlConnection().deleteReservationById(7);
////        assertEquals(null,SqlConnection.getSqlConnection().getReservationByID(7));
////    }
////
////    @Test
////    void   selectIDRoomThatReservationNotInRange() {
////        SqlConnection.getSqlConnection().deleteReservationById(7);
////        assertEquals(1,SqlConnection.getSqlConnection().selectIDRoomThatReservationNotInRange(LocalDate.parse("2019-12-13"),LocalDate.parse("2020-01-13")).size());
////    }
////
////    @Test
////    void   getTypeRoomByID() {
////        assertEquals("test1",SqlConnection.getSqlConnection().getTypeRoomByID(16).getTypeRoom());
////    }
////
////    @Test
////    void   getReservationByID() {
////        assertEquals("025852825",SqlConnection.getSqlConnection().getReservationByID(9).getPhone_number());
////    }
////
////
////    @Test
////    void    getStringTypeRoomFromIDRoom() {
////        assertEquals("test1",SqlConnection.getSqlConnection().getStringTypeRoomFromIDRoom(16));
////    }
////
////    @Test
////    void    getRecentReservation() {
////        assertEquals(9,SqlConnection.getSqlConnection().getRecentReservation());
////    }
//}
