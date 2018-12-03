import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import model.SqlConnection;
import model.TypeRoom;

import static org.junit.jupiter.api.Assertions.*;

public class StepOfUAT {


    SqlConnection ins;
    @Before
    public void setup() {
        ins = SqlConnection.getSqlConnection();
    }

    // 1 Edit Apartment
    @Given("^หอพักมีชี่อหอพักเป็น (.*) และวันชำระเงินประจำเดือนเป็นวันที่ (\\d+)")
    public void given_apartment(String name, int day) {
        ins.updateApartmentNameAndDatePayMoney(name,day + "");
    }
    @When("^กดแก้ไขข้อมูลหอพัก จากนั้นใส่ชื่อหอพักเป็น (.*) และวันชำระเงินประจำเดือนเป็นวันที่ (\\d+) แล้วกดตกลง")
    public void when_edit_apartment(String name, int day) {
        ins.updateApartmentNameAndDatePayMoney(name,day + "");
    }
    @Then("^ชื่อหอพักคือ (.*) และวันชำระเงินประจำเดือนคือวันที่ (\\d+)")
    public void then_edit_apartment(String name, int day) {
        assertEquals(name, ins.selectNameOfApartment());
        assertEquals(day + "", ins.selectDatePayMoney());
    }

    // 2 Create Type Room
    int type_room_size_2;
    @When("^กดเพิ่มประเภท จากนั้นใส่ชื่อประเภทเป็น (.*) และราคาค่าเช่ารายเดือนเป็น (\\d+) ราคาค่าเช่ารายวันเป็น (\\d+) แล้วกดตกลง")
    public void when_create_type_room(String name, double monthly, double daily) {
        type_room_size_2 = ins.selectAllTypeRoom().size();
        ins.insertTypeRoom(name, monthly, daily);
    }
    @Then("^มีประเภทห้องใหม่ชื่อประเภทเป็น (.*) และราคาค่าเช่ารายเดือนเป็น (\\d+) ราคาค่าเช่ารายวันเป็น (\\d+) และจำนวนประเภทห้องเพิ่มขึ้น")
    public void then_create_type_room(String name, double monthly, double daily) {
        TypeRoom new_type = ins.getTypeRoomByID(ins.getIDTyperoomFromNameTypeRoom(name));
        assertEquals(name, new_type.getTypeRoom());
        assertEquals(monthly, new_type.getRentPerMonth());
        assertEquals(daily, new_type.getRentPerDay());
        assertEquals(type_room_size_2, ins.selectAllTypeRoom().size());
    }

    // 3 Edit Type Room
    @Given("^มีประเภทห้องที่ชื่อประเภทเป็น (.*) และราคาค่าเช่ารายเดือนเป็น (\\d+) ราคาค่าเช่ารายวันเป็น (\\d+)")
    public void given_edit_type_room(String name, double monthly, double daily) {
        ins.insertTypeRoom(name, monthly, daily);
    }
    @When("^กดแก้ไขประเภทห้องจากประเภทห้อง(.*) จากนั้นใส่ชื่อประเภทเป็น (.*) และราคาค่าเช่ารายเดือนเป็น (\\d+) ราคาค่าเช่ารายวันเป็น (\\d+) แล้วกดตกลง")
    public void when_edit_type_room(String name1, String name, double monthly, double daily) {
        int id = ins.getRecentTypeRoom();
        ins.updateTypeRoom(id, name, monthly, daily);
    }
    @Then("^ประเภทห้องเดิมถูกมีชื่อประเภทคือ (.*) และมีราคาค่าเช่ารายเดือนคือ (\\d+) ราคาค่าเช่ารายวันคือ (\\d+)")
    public void then_edit_type_room(String name, double monthly, double daily) {
        TypeRoom new_type = ins.getTypeRoomByID(ins.getIDTyperoomFromNameTypeRoom(name));
        assertEquals(new_type.getTypeRoom(), name);
        assertEquals(new_type.getRentPerMonth(), monthly);
        assertEquals(new_type.getRentPerDay(), daily);
    }


    // 4 Delete Type Room
    int type_room_size_4;
    @When("^กดลบประเภทห้องจากประเภทห้อง(.*)")
    public void when_delete_type_room(String name) {
        type_room_size_4 = ins.selectAllTypeRoom().size();
        ins.deleteTypeRoom(ins.getRecentTypeRoom());
    }
    @Then("^ประเภทห้องถูกลบ และจำนวนประเภทห้องลดลง")
    public void then_delete_type_room() {
        assertEquals(type_room_size_4 - 1, ins.selectAllTypeRoom().size());
    }

    

}
