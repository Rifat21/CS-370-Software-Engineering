package edu.qc.seclass.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class TipCalculatorActivity extends AppCompatActivity {

    private int tip15 =0, tip20=0, tip25=0, total15=0, total20=0, total25=0;
    private double check= 0 , party=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
    }

    public void compute(View view){
        EditText textCheck = findViewById(R.id.checkAmountValue);
        EditText textParty = findViewById(R.id.partySizeValue);

        if(textCheck.getText().toString().isEmpty() || textParty.getText().toString().isEmpty()){
            Toast.makeText(TipCalculatorActivity.this, "Empty or incorrect value(s)!", Toast.LENGTH_LONG).show();
            return;
        }

        check = Double.parseDouble(textCheck.getText().toString());
        party = Double.parseDouble(textParty.getText().toString());

        if(check <= 0 || party <=0){
            Toast.makeText(TipCalculatorActivity.this, "Empty or incorrect value(s)!", Toast.LENGTH_LONG).show();
            return;
        }

        double individualCost = check / party;


        tip15 = (int) Math.ceil(individualCost * .15);
        tip20 = (int) Math.ceil(individualCost * .20);
        tip25 = (int) Math.ceil(individualCost * .25);

        total15 = (int) Math.ceil(individualCost+tip15);
        total20 = (int) Math.ceil(individualCost+tip20);
        total25 = (int) Math.ceil(individualCost+tip25);


        display();
    }

    private void display(){
        EditText tip1 = findViewById(R.id.fifteenPercentTipValue);
        tip1.setText("" + tip15);

        EditText tip2 = findViewById(R.id.twentyPercentTipValue);
        tip2.setText("" + tip20);

        EditText tip3 = findViewById(R.id.twentyfivePercentTipValue);
        tip3.setText("" + tip25);

        EditText total1 = findViewById(R.id.fifteenPercentTotalValue);
        total1.setText("" + total15);

        EditText total2 = findViewById(R.id.twentyPercentTotalValue);
        total2.setText("" + total20);

        EditText total3 = findViewById(R.id.twentyfivePercentTotalValue);
        total3.setText("" + total25);

    }
}
