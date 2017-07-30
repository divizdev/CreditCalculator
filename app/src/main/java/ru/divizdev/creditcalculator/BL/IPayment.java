package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 22.07.2017.
 */
public interface IPayment {

    /**
     * Остаток кредита
     * @return
     */
    double getBalance();

    /**
     * Проценты
     * @return
     */
    double getPercent();

    /**
     * Основной долг
     * @return
     */
    double getDebt();

    /**
     * Сумма платежа
     * @return
     */
    double getAmount();
}
