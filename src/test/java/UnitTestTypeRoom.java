import model.SqlConnection;
import model.TypeRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestTypeRoom {
    ArrayList<TypeRoom> typeRoomsAllOld ;
    String name ;

    @BeforeEach
    void init(){
        SqlConnection.getSqlConnection().createTypeRoomTable();
        typeRoomsAllOld = SqlConnection.getSqlConnection().selectAllTypeRoom();
        name = "testUnit"+(typeRoomsAllOld.size()+1);
    }


    //TypeRoom
    @Test
    void insertTypeRoom() {
        SqlConnection.getSqlConnection().insertTypeRoom(name,1D,2D);

        ArrayList<TypeRoom> typeRoomsAllNew = SqlConnection.getSqlConnection().selectAllTypeRoom();

        assertEquals(typeRoomsAllOld.size()+1,typeRoomsAllNew.size());
    }


    @Test
    void updateTypeRoom() {
        SqlConnection.getSqlConnection().insertTypeRoom(name,1D,2D);
        int id  = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(name);
        ArrayList<TypeRoom> typeRoomsAllNew = SqlConnection.getSqlConnection().selectAllTypeRoom();
        String nameNew = "testUnit"+(typeRoomsAllNew.size()+1);

        SqlConnection.getSqlConnection().updateTypeRoom(id,nameNew,3D,4D);
        int idNew  = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(nameNew);
        assertEquals(id,idNew);
    }


    @Test
    void deleteTypeRoom() {
        SqlConnection.getSqlConnection().insertTypeRoom(name,1D,2D);

        ArrayList<TypeRoom> typeRoomsAllOld1 = SqlConnection.getSqlConnection().selectAllTypeRoom();
        int id  = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(name);
        SqlConnection.getSqlConnection().deleteTypeRoom(id);

        ArrayList<TypeRoom> typeRoomsDelete = SqlConnection.getSqlConnection().selectAllTypeRoom();
        assertEquals(typeRoomsAllOld1.size()-1 ,typeRoomsDelete.size());
    }


    @Test
    void getTypeRoom() {
        SqlConnection.getSqlConnection().insertTypeRoom(name,1D,2D);

        int id  = SqlConnection.getSqlConnection().getIDTyperoomFromNameTypeRoom(name);
        TypeRoom typeRoom = SqlConnection.getSqlConnection().getTypeRoomByID(id);
        assertEquals(name,typeRoom.getTypeRoom());
    }





}
