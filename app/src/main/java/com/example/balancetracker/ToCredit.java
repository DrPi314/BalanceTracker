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

public class ToCredit extends AppCompatActivity {

    public EditText balance;
    public EditText amount;
    public Button home;
    public Button submit;
    public Button transfer;
    static String ftocredit = "tocredit.txt";
    static String fdebit = "debit.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_credit);

        balance = findViewById(R.id.eBalance);
        balance.setText(getStringFromFile(ftocredit));

        amount = findViewById(R.id.eNew);

        home = findViewById(R.id.bHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToCredit.this, MainActivity.class));
            }
        });

        submit = findViewById(R.id.bSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });

        transfer = findViewById(R.id.bTransfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transfer();
            }
        });

    }


    private void Submit() {
        String obal = getStringFromFile(ftocredit);
        if(amount != null) {

            // get current balance adding original balance to amount if original balance exists
            String cbal;
            if (obal.length() > 0) {
                cbal = String.valueOf(parseDouble(obal) + parseDouble(amount.getText().toString()));
            } else if (parseDouble(amount.getText().toString()) > 0) {
                cbal = amount.getText().toString();
            } else {
                cbal = "0.00";
            }
            balance.setText(cbal);

            try {
                writeStringToFile(ftocredit, cbal);
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

    private void Transfer(){
        String obal = getStringFromFile(ftocredit);
        String cbal = String.valueOf(parseDouble(obal) - parseDouble(amount.getText().toString()));
        String odebit = getStringFromFile(fdebit);
        String cdebit = String.valueOf(parseDouble(odebit) - parseDouble(amount.getText().toString()));
        try {
            writeStringToFile(ftocredit, cbal);
            amount.setText(null);
            balance.setText(cbal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeStringToFile(String file, String value) throws Exception {
        FileOutputStream fos = openFileOutput(file, MODE_PRIVATE);
        fos.write(value.getBytes());
        fos.close();
    }
}
