import model.SqlConnection;
import model.TypeRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestTypeRoom {

    @BeforeEach
    void init(){
        SqlConnection.getSqlConnection().createTypeRoomTable();

    }


    //TypeRoom
    @Test
    void insertTypeRoom() {
        ArrayList<TypeRoom> typeRoomsAllOld = SqlConnection.getSqlConnection().selectAllTypeRoom();
        String name = "testUnit"+(typeRoomsAllOld.size()+1);

        SqlConnection.getSqlConnection().insertTypeRoom(name,1D,2D);

        ArrayList<TypeRoom> typeRoomsAllNew = SqlConnection.getSqlConnection().selectAllTypeRoom();

        assertEquals(typeRoomsAllOld.size()+1,typeRoomsAllNew.size());
    }


    @Test
    void updateTypeRoom() {
        ArrayList<TypeRoom> typeRoomsAllOld = SqlConnection.getSqlConnection().selectAllTypeRoom();
        String name = "testUnit"+(typeRoomsAllOld.size()+1);
        SqlConnection.getSqlConnection().insertTypeRoom(name,1D,2D);
        int id  = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(name);
        ArrayList<TypeRoom> typeRoomsAllNew = SqlConnection.getSqlConnection().selectAllTypeRoom();
        String nameNew = "testUnit"+(typeRoomsAllNew.size()+1);

        SqlConnection.getSqlConnection().updateTypeRoom(id,nameNew,3D,4D);
        int idNew  = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(nameNew);
        assertEquals(id,idNew);
    }


//    @Test
//    void deleteTypeRoom() {
//        SqlConnection.getSqlConnection().deleteTypeRoom(id);
//        ArrayList<TypeRoom> typeRoomsDelete = SqlConnection.getSqlConnection().selectAllTypeRoom();
//        assertEquals(true ,typeRoomsDelete.size() < typeRoomsCheck.size());
//    }
//
//
//    @Test
//    void getTypeRoom() {
//        TypeRoom typeRoom = SqlConnection.getSqlConnection().getTypeRoomByID(id);
//        assertEquals("test"+(typeRooms.size()+1),typeRoom.getTypeRoom());
//    }





}
