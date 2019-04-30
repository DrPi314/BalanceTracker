package com.example.balancetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Costco extends AppCompatActivity {

    public Button bHome;
    public Button bSubmit;
    TextView tCostco;
    EditText eBalance;
    TextView tBalance;
    EditText eCharge;
    TextView tNewCharge;
    EditText eDateCharge;
    EditText eTimeCharge;
    EditText eDescCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costco);

        bHome = findViewById(R.id.bHome);
        bSubmit = findViewById(R.id.bSubmit);
        tCostco = findViewById(R.id.tDad);
        eBalance = findViewById(R.id.eBalance);
        tBalance = findViewById(R.id.tBalance);
        eCharge = findViewById(R.id.eCharge);
        tNewCharge = findViewById(R.id.tNewCharge);
        eDateCharge = findViewById(R.id.eDateCharge);
        eTimeCharge = findViewById(R.id.eTimeCharge);
        eDescCharge = findViewById(R.id.eDescCharge);

        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Costco.this, MainActivity.class));
            }
        });
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
    }

    public void Submit() {

        List<String> submission = new ArrayList<String>();
        submission.add(eCharge.getText().toString() + ", ");
        submission.add(eDateCharge.getText().toString() + ", ");
        submission.add(eTimeCharge.getText().toString() + ", ");
        submission.add(eDescCharge.getText().toString() + ".");

        try
        {
            FileOutputStream fos = openFileOutput("Data.txt", MODE_APPEND);
            OutputStreamWriter opw = new OutputStreamWriter(fos);
            opw.write(String.valueOf(submission));
            opw.close();
            Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();

            try {
                int newChg = parseInt(eCharge.getText().toString());
                try {
                    int oldBal = parseInt(eBalance.getText().toString());
                    int newBal = oldBal + newChg;
                    eBalance.setText(newBal);
                } catch (NumberFormatException eo) {
                    int oldBal = 0;
                    int newBal = oldBal + newChg;
                    eBalance.setText(newBal);

                    try
                    {
                        FileOutputStream bfos = openFileOutput("Balance.txt", MODE_PRIVATE);
                        OutputStreamWriter bopw = new OutputStreamWriter(bfos);
                        bopw.write(eBalance.getText().toString());
                        bopw.close();
                    } catch (Exception ew) {
                        ew.printStackTrace();
                    }
                }
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        eCharge.setText("");
        eDateCharge.setText("");
        eTimeCharge.setText("");
        eDescCharge.setText("");
    }

                //opw.write([eCharge.getText().toString(), eDateCharge.getText().toString(), eTimeCharge.getText().toString(), eDescCharge.getText().toString()]);



}
