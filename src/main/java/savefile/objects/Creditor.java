package savefile.objects;

public class Creditor {
    private String country;
    private double interest;
    private double debt;
    private boolean warPaid;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public boolean isWarPaid() {
        return warPaid;
    }

    public void setWarPaid(boolean warPaid) {
        this.warPaid = warPaid;
    }
}
