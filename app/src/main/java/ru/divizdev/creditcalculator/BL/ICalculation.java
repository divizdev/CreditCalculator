package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 22.07.2017.
 */
public interface ICalculation {


    double getOverpayment();

    IPayment getPayment(int index);

    void setRepayment(int index, double payment, TypeRepayment decreasePayment);
}
