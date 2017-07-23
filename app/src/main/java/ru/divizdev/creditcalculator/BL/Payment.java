package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 22.07.2017.
 */
public class Payment implements  IPayment {

    private  final double _remainder;


    public Payment(double remainder){

        _remainder = remainder;
    }

    @Override
    public double getRemainder() {
        return _remainder;
    }
}
