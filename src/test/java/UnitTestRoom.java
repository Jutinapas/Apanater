import model.Room;
import model.DBConnector;
import model.TypeRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UnitTestRoom {
    String nameType;

    @BeforeEach
    void init(){
        DBConnector.getDBConnector().createRoomTable();
        DBConnector.getDBConnector().createTypeRoomTable();
        ArrayList<TypeRoom> typeRooms = DBConnector.getDBConnector().selectAllTypeRoom();
        nameType = "typeRooms"+(typeRooms.size()+1);
        DBConnector.getDBConnector().insertTypeRoom(nameType,1D,2D);
    }


    //Room
    @Test
    void insertRoom() {
        int idTypeRoom = DBConnector.getDBConnector().getIDTyperoomFromNameTypeRoom(nameType);
        ArrayList<Room> RoomsOld = DBConnector.getDBConnector().selectAllRoom();
        String name = "room"+(RoomsOld.size()+1);
        DBConnector.getDBConnector().insertRoom(name,idTypeRoom,2);
        ArrayList<Room> RoomsNew = DBConnector.getDBConnector().selectAllRoom();
        assertEquals(RoomsOld.size()+1,RoomsNew.size());
    }


    @Test
    void updateRoom() {
        int idTypeRoom = DBConnector.getDBConnector().getIDTyperoomFromNameTypeRoom(nameType);
        ArrayList<Room> RoomsOld = DBConnector.getDBConnector().selectAllRoom();
        String name = "room"+(RoomsOld.size()+1);
        DBConnector.getDBConnector().insertRoom(name,idTypeRoom,3);

        int id = DBConnector.getDBConnector().getIDroomByNameRoom(name);
        String nameNew = name+"update";
        DBConnector.getDBConnector().updateRoom(id,nameNew,3,idTypeRoom);

        assertEquals(id, DBConnector.getDBConnector().getIDroomByNameRoom(nameNew));
    }


    @Test
    void deleteRoom() {
        int idTypeRoom = DBConnector.getDBConnector().getIDTyperoomFromNameTypeRoom(nameType);
        ArrayList<Room> RoomsOld = DBConnector.getDBConnector().selectAllRoom();
        String name = "room"+(RoomsOld.size()+1);

        DBConnector.getDBConnector().insertRoom(name,idTypeRoom,4);

        ArrayList<Room> RoomsOld2 = DBConnector.getDBConnector().selectAllRoom();
        int id = DBConnector.getDBConnector().getIDroomByNameRoom(name);

        DBConnector.getDBConnector().deleteRoom(id);

        ArrayList<Room> RoomsDelete = DBConnector.getDBConnector().selectAllRoom();
        assertEquals(RoomsOld2.size()-1 ,RoomsDelete.size());
    }


    @Test
    void getRoom() {
        int idTypeRoom = DBConnector.getDBConnector().getIDTyperoomFromNameTypeRoom(nameType);
        ArrayList<Room> RoomsOld = DBConnector.getDBConnector().selectAllRoom();
        String name = "room"+(RoomsOld.size()+1);

        DBConnector.getDBConnector().insertRoom(name,idTypeRoom,5);
        int id = DBConnector.getDBConnector().getIDroomByNameRoom(name);
        Room room = DBConnector.getDBConnector().getRoomByID(id);
        assertEquals(name,room.getRoom_name());
    }
}
