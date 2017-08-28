package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 27.08.2017.
 */
public class AnnuitySeparateCalculationDecreasePayment extends AbstractSeparateCalculation {

    private final ISeparateCalculation _separateCalculation;
    private final OptionsCredit _optionsCredit;
    private final IPayment _payment;


    public AnnuitySeparateCalculationDecreasePayment(ISeparateCalculation separateCalculation, double payment) {


        _separateCalculation = separateCalculation;
        int index = getIndex();


        double percent = separateCalculation.getPayment().getBalance() * separateCalculation.getPercentMonth();
        double debt = payment - percent;


        OptionsCredit optionsCredit = new OptionsCredit(separateCalculation.getMonths() - index - 1,
                separateCalculation.getOptionsCredit().getInterestRate(), separateCalculation.getPayment().getBalance() - debt);

        double monthlyPayment = calcObligatoryPayment(optionsCredit);

        _optionsCredit = separateCalculation.getOptionsCredit();

        _payment = new Payment(separateCalculation.getPayment().getBalance(), percent, debt, monthlyPayment);


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
    public double getObligatoryPayment() {
        return _payment.getObligatoryPayment();
    }

    @Override
    public OptionsCredit getOptionsCredit() {
        return _optionsCredit;
    }
}
