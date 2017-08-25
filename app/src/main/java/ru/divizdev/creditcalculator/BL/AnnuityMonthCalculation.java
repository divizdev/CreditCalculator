package ru.divizdev.creditcalculator.BL;

public class AnnuityMonthCalculation {

    private int _months;
    private double _percentMonth;

    private AnnuityMonthCalculation _lastCalculation;
    private IPayment _payment;

    public AnnuityMonthCalculation(int months, double interestRate, double amountCredit){

        _months = months;
        _percentMonth = interestRate / 12f / 100f;

        double payment = calcMonthlyPayment(_months, amountCredit, _percentMonth);

        double percent = amountCredit * _percentMonth;

        double debt = payment - percent;

        _payment = new Payment(amountCredit, percent, debt);

    }

    public AnnuityMonthCalculation(AnnuityMonthCalculation lastCalculation){
        _lastCalculation = lastCalculation;
        _months = lastCalculation.getMonths();
        _percentMonth = lastCalculation.getPercentMonth();

        double balance = _lastCalculation.getPayment().getBalance() - _lastCalculation.getPayment().getDebt();
        double percent = balance * _percentMonth;
        double debt = _lastCalculation.getPayment().getAmount() - percent;
        _payment = new Payment(balance, percent, debt);

    }

    public int getMonths() {
        return _months;
    }

    public double getPercentMonth() {
        return _percentMonth;
    }

    public AnnuityMonthCalculation getLastCalculation() {
        return _lastCalculation;
    }

    public IPayment getPayment(){
        return _payment;
    }

    private double calcMonthlyPayment(int months, double amountCredit, double percentMonth) {
        return amountCredit * (percentMonth + percentMonth / (Math.pow((1 + percentMonth), months) - 1));
    }


}
