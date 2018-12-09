import model.DBConnector;
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
        DBConnector.getDBConnector().createTypeRoomTable();
        typeRoomsAllOld = DBConnector.getDBConnector().selectAllTypeRoom();
        name = "testUnit"+(typeRoomsAllOld.size()+1);
    }


    //TypeRoom
    @Test
    void insertTypeRoom() {
        DBConnector.getDBConnector().insertTypeRoom(name,1D,2D);

        ArrayList<TypeRoom> typeRoomsAllNew = DBConnector.getDBConnector().selectAllTypeRoom();

        assertEquals(typeRoomsAllOld.size()+1,typeRoomsAllNew.size());
    }


    @Test
    void updateTypeRoom() {
        DBConnector.getDBConnector().insertTypeRoom(name,1D,2D);
        int id  = DBConnector.getDBConnector().getIDTyperoomFromNameTypeRoom(name);
        ArrayList<TypeRoom> typeRoomsAllNew = DBConnector.getDBConnector().selectAllTypeRoom();
        String nameNew = "testUnit"+(typeRoomsAllNew.size()+1);

        DBConnector.getDBConnector().updateTypeRoom(id,nameNew,3D,4D);
        int idNew  = DBConnector.getDBConnector().getIDTyperoomFromNameTypeRoom(nameNew);
        assertEquals(id,idNew);
    }


    @Test
    void deleteTypeRoom() {
        DBConnector.getDBConnector().insertTypeRoom(name,1D,2D);

        ArrayList<TypeRoom> typeRoomsAllOld1 = DBConnector.getDBConnector().selectAllTypeRoom();
        int id  = DBConnector.getDBConnector().getIDTyperoomFromNameTypeRoom(name);
        DBConnector.getDBConnector().deleteTypeRoom(id);

        ArrayList<TypeRoom> typeRoomsDelete = DBConnector.getDBConnector().selectAllTypeRoom();
        assertEquals(typeRoomsAllOld1.size()-1 ,typeRoomsDelete.size());
    }


    @Test
    void getTypeRoom() {
        DBConnector.getDBConnector().insertTypeRoom(name,1D,2D);

        int id  = DBConnector.getDBConnector().getIDTyperoomFromNameTypeRoom(name);
        TypeRoom typeRoom = DBConnector.getDBConnector().getTypeRoomByID(id);
        assertEquals(name,typeRoom.getTypeRoom());
    }





}
