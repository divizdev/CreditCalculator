package ru.divizdev.creditcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

    private NumberPicker _npYear;
    private NumberPicker _npMonth;
    private Spinner _spinnerTypeCalculation;
    private Button _buttonCalc;
    private EditText _etAmountCredit;
    private EditText _etPercent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind();
        initialActivity();
    }

    private void initialActivity() {
        _npYear.setValue(0);
        _npYear.setMinValue(0);
        _npYear.setMaxValue(30);

        _npMonth.setValue(0);
        _npMonth.setMaxValue(12);
        _npMonth.setMinValue(0);
        _npMonth.setWrapSelectorWheel(true);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.text_type_calculation));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinnerTypeCalculation.setAdapter(arrayAdapter);
        _buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailCreditActivity.class);
                intent.putExtra("year", _npYear.getValue());
                intent.putExtra("month", _npMonth.getValue());
                intent.putExtra("amountCredit", _etAmountCredit.getText().toString());
                intent.putExtra("percent", _etPercent.getText().toString());
                startActivity(intent);
            }
        });

    }

    private void bind() {
        _npYear = (NumberPicker) findViewById(R.id.np_year);
        _npMonth = (NumberPicker) findViewById(R.id.np_month);
        _spinnerTypeCalculation = (Spinner) findViewById(R.id.spinner);
        _buttonCalc = (Button) findViewById(R.id.button_calc);
        _etAmountCredit = (EditText) findViewById(R.id.edit_text_amount_credit);
        _etPercent = (EditText) findViewById(R.id.edit_text_percent);
    }
}
