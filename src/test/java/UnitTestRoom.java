import model.Room;
import model.SqlConnection;
import model.TypeRoom;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UnitTestRoom {
    String nameType;

    @BeforeEach
    void init(){
        SqlConnection.getSqlConnection().createRoomTable();
        SqlConnection.getSqlConnection().createTypeRoomTable();
        ArrayList<TypeRoom> typeRooms = SqlConnection.getSqlConnection().selectAllTypeRoom();
        nameType = "typeRooms"+(typeRooms.size()+1);
        SqlConnection.getSqlConnection().insertTypeRoom(nameType,1D,2D);
    }


    //Room
    @Test
    void insertRoom() {
        int idTypeRoom = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(nameType);
        ArrayList<Room> RoomsOld = SqlConnection.getSqlConnection().selectAllRoom();
        String name = "room"+(RoomsOld.size()+1);
        SqlConnection.getSqlConnection().insertRoom(name,idTypeRoom,2);
        ArrayList<Room> RoomsNew = SqlConnection.getSqlConnection().selectAllRoom();
        assertEquals(RoomsOld.size()+1,RoomsNew.size());
    }


    @Test
    void updateRoom() {
        int idTypeRoom = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(nameType);
        ArrayList<Room> RoomsOld = SqlConnection.getSqlConnection().selectAllRoom();
        String name = "room"+(RoomsOld.size()+1);
        SqlConnection.getSqlConnection().insertRoom(name,idTypeRoom,3);

        int id = SqlConnection.getSqlConnection().getIDroomByNameRoom(name);
        String nameNew = name+"update";
        SqlConnection.getSqlConnection().updateRoom(id,nameNew,3,idTypeRoom);

        assertEquals(id,SqlConnection.getSqlConnection().getIDroomByNameRoom(nameNew));
    }


    @Test
    void deleteRoom() {
        int idTypeRoom = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(nameType);
        ArrayList<Room> RoomsOld = SqlConnection.getSqlConnection().selectAllRoom();
        String name = "room"+(RoomsOld.size()+1);

        SqlConnection.getSqlConnection().insertRoom(name,idTypeRoom,4);

        ArrayList<Room> RoomsOld2 = SqlConnection.getSqlConnection().selectAllRoom();
        int id = SqlConnection.getSqlConnection().getIDroomByNameRoom(name);

        SqlConnection.getSqlConnection().deleteRoom(id);

        ArrayList<Room> RoomsDelete = SqlConnection.getSqlConnection().selectAllRoom();
        assertEquals(RoomsOld2.size()-1 ,RoomsDelete.size());
    }


    @Test
    void getRoom() {
        int idTypeRoom = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(nameType);
        ArrayList<Room> RoomsOld = SqlConnection.getSqlConnection().selectAllRoom();
        String name = "room"+(RoomsOld.size()+1);

        SqlConnection.getSqlConnection().insertRoom(name,idTypeRoom,5);
        int id = SqlConnection.getSqlConnection().getIDroomByNameRoom(name);
        Room room = SqlConnection.getSqlConnection().getRoomByID(id);
        assertEquals(name,room.getRoom_name());
    }
}
