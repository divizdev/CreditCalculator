package ru.divizdev.creditcalculator.BL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diviz on 22.07.2017.
 */
public class AnnuityCalculation implements  ICalculation{
    private final double _overpayment;
    private double _monthlyPayment;
    private List<IPayment> _paymentList;


    public AnnuityCalculation(int months, int interestRate, int amountCredit) {
        double percentMonth = interestRate / 12f / 100f;
        _monthlyPayment = amountCredit * (percentMonth + percentMonth / ( Math.pow((1 + percentMonth), months) - 1));
        _overpayment =  _monthlyPayment * months - amountCredit;

        _paymentList = new ArrayList<>(months);

        double remainder = amountCredit;

        for (int i = 0; i < months; i++) {

            double percent = remainder * percentMonth;

            _paymentList.add(new Payment(remainder, percent));

            remainder = remainder - ( _monthlyPayment -  percent);

        }
    }

    public double getMonthlyPayment() {
        return _monthlyPayment;
    }

    @Override
    public double getOverpayment() {
        return _overpayment;
    }

    @Override
    public IPayment getPayment(int index) {
        if (index>=0 && index < _paymentList.size()) {
            return _paymentList.get(index);
        }
        return null;
    }
}
