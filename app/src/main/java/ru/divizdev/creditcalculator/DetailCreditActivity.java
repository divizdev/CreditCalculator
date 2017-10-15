package ru.divizdev.creditcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import ru.divizdev.creditcalculator.BL.Calculator;
import ru.divizdev.creditcalculator.BL.ICalculation;
import ru.divizdev.creditcalculator.BL.OptionsCredit;

public class DetailCreditActivity extends AppCompatActivity {

    private EditText _etMonthlyPayment;
    private Calculator _calc;
    private ICalculation _calculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_credit);

        bind();

        int month, year, amountCredit, sum;
        double percent;
        Intent intent = getIntent();
        month = intent.getIntExtra("month", 0);
        year = intent.getIntExtra("year", 0);

        sum = Integer.valueOf(intent.getStringExtra("amountCredit"));
        percent = Double.valueOf(intent.getStringExtra("percent"));

        _calc = new Calculator();

        OptionsCredit optionsCredit = new OptionsCredit(month + year*12, percent, sum);

        _calculation = _calc.calculation(optionsCredit);

        _etMonthlyPayment.setText(String.valueOf(_calculation.getPayment(0).getObligatoryPayment()));

    }

    private void bind() {
        _etMonthlyPayment = (EditText) findViewById(R.id.edit_text_monthly_payment);
    }
}
