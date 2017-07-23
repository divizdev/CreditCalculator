package ru.divizdev.creditcalculator;

import org.junit.Before;
import org.junit.Test;
import ru.divizdev.creditcalculator.BL.Calculator;
import ru.divizdev.creditcalculator.BL.ICalculation;

import static org.junit.Assert.*;

/**
 * Created by diviz on 22.07.2017.
 */
public class CreditCalculatorTest {

//    Кредит в размере 100 000 р.
//    Процентная ставка 10%.
//    Сроком погашения кредита возьмём 6 месяцев.
//    Для начала рассчитаем ежемесячный платёж = 17 156,14

//    1 месяц
//    Проценты: 100000 * 0,1 / 12 = 833,33
//    Основной долг: 17156,14 – 833, 33 = 16322,81
//    2 месяц
//    Остаток кредита: 100000 – 16322,81 = 83677,19
//    Проценты: 83677,19 * 0,1/12 = 697,31
//    Основной долг: 17156,14 – 697,31 = 1458,83
//    3 месяц
//    Остаток кредита: 83677,19 — 16458,83 = 67218,36
//    Проценты: 67218,36 *0,1/12 = 560,15
//    Основной долг: 17156,14 – 560,15 = 16595,99
//    4 месяц
//    Остаток кредита: 67218,36 — 16595,99 = 50622,38
//    Проценты: 50622.38 * 0,1/12 = 421.85
//    Основной долг: 17156,14 – 421,85 = 16734,29
//    5 месяц
//    Остаток кредита: 50622,38 — 16734,29 = 33888,09
//    Проценты: 33888,09 * 0,1/12 = 282,40
//    Основной долг: 17156,14 – 282,40 = 16873,74
//    6 месяц
//    Остаток кредита: 33888.09 — 16873.74 = 17014,35
//    Проценты: 17014,35 * 0,1/12 = 141,79
//    Основной долг: 17156,14 – 141,79 = 17014,35



    private Calculator _calc;
    private ICalculation _calculation;

    @Before
    public void setUp(){
        _calc = new Calculator();

        _calculation = _calc.calculation(6, 10, 100000);
    }

    @Test
    public void simpleCalculate() throws Exception {

        ICalculation calculation;

        calculation = _calc.calculation(36, 20, 1000000);
        assertEquals(37163.58, calculation.getMonthlyPayment(), 2);


        assertEquals(17156.14, _calculation.getMonthlyPayment(),  2);

    }

    @Test
    public void overpaymentCalculate() throws  Exception{

        assertEquals(2936.84, _calculation.getOverpayment(), 2);
    }

    @Test
    public void secondPaymentRemainder() throws Exception{

        assertEquals(83677.19, _calculation.getPayment(1).getBalance(), 2);

    }


    @Test
    public void fifthPaymentRemainder() throws Exception{

        assertEquals(33888.09, _calculation.getPayment(4).getBalance(), 2);

    }

    @Test
    public void lastPaymentRemainder() throws Exception{

        assertEquals(17014.35, _calculation.getPayment(5).getBalance(), 2);

    }

    @Test
    public void secondPaymentPercent() throws Exception{
        assertEquals(697.31, _calculation.getPayment(1).getPercent(), 2);

    }

    @Test
    public void lastPaymentPercent() throws Exception{

        assertEquals(141.79, _calculation.getPayment(5).getPercent(), 2);

    }

}

