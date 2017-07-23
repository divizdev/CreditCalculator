package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 22.07.2017.
 */
public class Payment implements IPayment {

    private final double _balance;
    private final double _percent;


    public Payment(double balance, double percent) {

        _balance = balance;
        _percent = percent;
    }

    @Override
    public double getBalance() {
        return _balance;
    }

    @Override
    public double getPercent() {
        return _percent;
    }
}
