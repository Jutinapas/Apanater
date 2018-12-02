package model;

import java.util.Calendar;

public class ManageDatePay {
    private int days;




    public ManageDatePay(){
        Calendar calendar = Calendar.getInstance();
        // show number of days in this month
        days = calendar.getActualMaximum(Calendar.DATE);
    }



    public int getDays() {
        return days;
    }
}
