package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 27.08.2017.
 */
public class AnnuitySeparateCalculationDecreasePayment implements ISeparateCalculation {

    private final ISeparateCalculation _separateCalculation;
    private final OptionsCredit _optionsCredit;
    private final IPayment _payment;
    private final double _monthlyPayment;

    public AnnuitySeparateCalculationDecreasePayment(ISeparateCalculation separateCalculation, double payment) {
        _separateCalculation = separateCalculation;
        int index = getIndex();


        double percent = separateCalculation.getPayment().getBalance() * separateCalculation.getPercentMonth();
        double debt = payment - percent;
        _payment = new Payment(separateCalculation.getPayment().getBalance(), percent, debt);

        OptionsCredit optionsCredit = new OptionsCredit(separateCalculation.getMonths() - index - 1,
                separateCalculation.getOptionsCredit().getInterestRate(), separateCalculation.getPayment().getBalance() - debt);

        _monthlyPayment = calcMonthlyPayment(optionsCredit);

        _optionsCredit = separateCalculation.getOptionsCredit();


    }

    private int getIndex() {
        int index = 0;
        ISeparateCalculation last = getLastCalculation();
        while (last != null) {
            index++;
            last = last.getLastCalculation();
        }
        return index;
    }

    @Override
    public int getMonths() {
        return _optionsCredit.getMonths();
    }

    @Override
    public double getPercentMonth() {
        return _optionsCredit.getPercentMonth();
    }

    @Override
    public ISeparateCalculation getLastCalculation() {
        return _separateCalculation.getLastCalculation();
    }

    @Override
    public IPayment getPayment() {
        return _payment;
    }

    @Override
    public double getMonthlyPayment() {
        return _monthlyPayment;
    }

    @Override
    public OptionsCredit getOptionsCredit() {
        return _optionsCredit;
    }


    private double calcMonthlyPayment(OptionsCredit optionsCredit) {
        int months = optionsCredit.getMonths();
        double amountCredit = optionsCredit.getAmountCredit();
        double percentMonth = optionsCredit.getPercentMonth();
        return amountCredit * (percentMonth + percentMonth / (Math.pow((1 + percentMonth), months) - 1));
    }
}
