package Models;

public class Debt {
    private int id_debt;
    private int id_reserve;
    private String date_pay_debt;
    private double debt_balance;
    private String status;

    public Debt(int id_debt, int id_reserve, String date_pay_debt, double debt_balance, String status) {
        this.id_debt = id_debt;
        this.id_reserve = id_reserve;
        this.date_pay_debt = date_pay_debt;
        this.debt_balance = debt_balance;
        this.status = status;
    }

    public int getId_debt() {
        return id_debt;
    }

    public int getId_reserve() {
        return id_reserve;
    }

    public String getDate_pay_debt() {
        return date_pay_debt;
    }

    public double getDebt_balance() {
        return debt_balance;
    }

    public String getStatus() {
        return status;
    }
}
