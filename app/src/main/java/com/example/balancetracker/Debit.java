package com.example.balancetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import static java.lang.Double.parseDouble;

public class Debit extends AppCompatActivity {

    public EditText balance;
    public EditText amount;
    public Button home;
    public Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit);

        balance = findViewById(R.id.eBalance);

        amount = findViewById(R.id.eNew);

        home = findViewById(R.id.bHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Debit.this, MainActivity.class));
            }
        });

        submit = findViewById(R.id.bSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });

    }


    private void Submit() {
        if(amount != null) {
            String file = "debit.txt";
            String obal = getStringFromFile(file);

            // get current balance adding original balance to amount if original balance exists
            String cbal;
            if (obal.length() > 0) {
                cbal = String.valueOf(parseDouble(obal) + parseDouble(amount.getText().toString()));
            } else {
                cbal = amount.getText().toString();
            }
            balance.setText(cbal);

            try {
                writeStringToFile(file, cbal);
                Toast.makeText(getBaseContext(), "file saved", Toast.LENGTH_SHORT).show();
                amount.setText(null);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getStringFromFile(String file) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fin = openFileInput(file);
            int c;
            while ((c = fin.read()) != -1) {
                stringBuilder.append(Character.toString((char) c));
            }
            fin.close();
        } catch (Exception e) {
            System.out.println("No previous debit file found, continuing...");
        }
        return stringBuilder.toString();
    }

    private void writeStringToFile(String file, String value) throws Exception {
        FileOutputStream fos = openFileOutput(file, MODE_PRIVATE);
        fos.write(value.getBytes());
        fos.close();
    }
}
