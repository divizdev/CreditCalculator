package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 27.08.2017.
 */
public class OptionsCredit {


    private final int _months;
    private final double _percentMonth;
    private final double _interestRate;
    private final double _amountCredit;

    public OptionsCredit(int months,  double interestRate, double amountCredit) {
        _months = months;
        _percentMonth = interestRate / 12f / 100f;
        _interestRate = interestRate;
        _amountCredit = amountCredit;
    }

    /**
     *
     * @return Длительность кредита
     */
    public int getMonths() {
        return _months;
    }


    /**
     *
     * @return процент в месяц
     */
    public double getPercentMonth() {
        return _percentMonth;
    }

    /**
     *
     * @return процентная ставка
     */
    public double getInterestRate() {
        return _interestRate;
    }

    /**
     *
     * @return Сумма кредита
     */
    public double getAmountCredit() {
        return _amountCredit;
    }
}
