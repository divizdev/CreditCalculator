package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 22.07.2017.
 */
public interface IPayment {

    /**
     * Остаток кредита на начало периода
     * @return
     */
    double getBalance();

    /**
     * Проценты по кредиту в этом периоде
     * @return
     */
    double getPercent();

    /**
     * Основной долг в этом периоде
     * @return
     */
    double getDebt();

    /**
     * Сумма платежа
     * @return
     */
    double getAmount();


}
