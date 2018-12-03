import model.Room;
import model.SqlConnection;
import model.TypeRoom;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UnitTestRoom {
//    ArrayList<Room> Rooms,RoomsCheck;
//    int id,idType;
//
//    @BeforeEach
//    void init(){
//        SqlConnection.getSqlConnection().createRoomTable();
//        SqlConnection.getSqlConnection().createTypeRoomTable();
//        ArrayList<TypeRoom> typeRooms = SqlConnection.getSqlConnection().selectAllTypeRoom();
//
//        SqlConnection.getSqlConnection().insertTypeRoom("test"+(typeRooms.size()+1),1D,2D);
//        idType = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom("test"+(typeRooms.size()+1));
//
//        Rooms = SqlConnection.getSqlConnection().selectAllRoom();
//        SqlConnection.getSqlConnection().insertRoom("test"+(Rooms.size()+1),idType,2);
//
//        id = SqlConnection.getSqlConnection().getIDroomByNameRoom("test"+(Rooms.size()+1));
//        RoomsCheck = SqlConnection.getSqlConnection().selectAllRoom();
//
//
//    }
//
//
//    //Room
//    @Test
//    void insertRoom() {
//
//        ArrayList<Room> RoomsUpdate = SqlConnection.getSqlConnection().selectAllRoom();
//        assertEquals(true,RoomsUpdate.size()>Rooms.size());
//    }
//
//
//    @Test
//    void updateRoom() {
//        SqlConnection.getSqlConnection().updateRoom(id,"test"+(Rooms.size()+1)+"update",2,idType);
//        assertEquals(id,SqlConnection.getSqlConnection().getIDroomByNameRoom("test"+(Rooms.size()+1)+"update"));
//    }
//
//
//    @Test
//    void deleteRoom() {
//        SqlConnection.getSqlConnection().deleteRoom(id);
//        ArrayList<Room> RoomsDelete = SqlConnection.getSqlConnection().selectAllRoom();
//        assertEquals(true ,RoomsDelete.size() < RoomsCheck.size());
//    }
//
//
//    @Test
//    void getRoom() {
//        Room room = SqlConnection.getSqlConnection().getRoomByID(id);
//        assertEquals("test"+(Rooms.size()+1),room.getRoom_name());
//    }
}
