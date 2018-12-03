import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import model.SqlConnection;

import static org.junit.jupiter.api.Assertions.*;

public class StepOfUAT {

    // 1 Edit Apartment
    @Given("^หอพักมีชี่อหอพักเป็น (.*) และวันชำระเงินประจำเดือนเป็นวันที่ (\\d+)")
    public void given_apartment(String name, int day) {
        SqlConnection.getSqlConnection().updateApartmentNameAndDatePayMoney(name,day + "");
    }
    @When("^กดแก้ไขข้อมูลหอพัก จากนั้นใส่ชื่อหอพักเป็น (.*) และวันชำระเงินประจำเดือนเป็นวันที่ (\\d+) แล้วกดตกลง")
    public void when_edit_apartment(String name, int day) {
        SqlConnection.getSqlConnection().updateApartmentNameAndDatePayMoney(name,day + "");
    }
    @Then("^ชื่อหอพักคือ (.*) และวันชำระเงินประจำเดือนคือวันที่ (\\d+)")
    public void then_edit_apartment(String name, int day) {
        assertEquals(name, SqlConnection.getSqlConnection().selectNameOfApartment());
        assertEquals(day + "", SqlConnection.getSqlConnection().selectDatePayMoney());
    }

    // 2 Create Type Room
    @When("^กดเพิ่มประเภท จากนั้นใส่ชื่อประเภทเป็น (.*) และราคาค่าเช่ารายเดือนเป็น (\\d+) ราคาค่าเช่ารายวันเป็น (\\d+) แล้วกดตกลง")
    public void when_create_type_room(String name, double monthly, double daily) {

    }
    @Then("^มีประเภทห้องใหม่ และจำนวนประเภทห้องเพิ่มขึ้น (\\d+)")
    public void then_create_type_room(int increase) {

    }


}
