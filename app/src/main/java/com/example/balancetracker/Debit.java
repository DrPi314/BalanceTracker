package com.example.balancetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.Integer.parseInt;

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
        StringBuilder obal = new StringBuilder();
        String file = "debit.txt";
        if (amount == null) {
            try {
                FileInputStream fin = openFileInput(file);
                int c;
                while( (c = fin.read()) != -1){
                    obal.append(Character.toString((char)c));
                }
                fin.close();
                balance.setText(obal);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                balance.setText("0.00");
                Toast.makeText(getBaseContext(),"enter amount", Toast.LENGTH_SHORT).show();
            }

        } else {
            try {
                FileInputStream fin = openFileInput(file);
                int c;
                while( (c = fin.read()) != -1){
                    obal.append(Character.toString((char)c));
                }
                fin.close();
                String cbal = String.valueOf(parseInt(obal.toString()) + parseInt(amount.getText().toString()));
                balance.setText(cbal);
                try {
                    FileOutputStream fos = openFileOutput(file, MODE_PRIVATE);
                    fos.write(cbal.getBytes());
                    fos.close();
                    Toast.makeText(getBaseContext(),"file saved", Toast.LENGTH_SHORT).show();
                    amount.setText(null);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    File debit = new File(file);
                    FileOutputStream fos = new FileOutputStream(debit);
                    fos.write(cbal.getBytes());
                    fos.close();
                    Toast.makeText(getBaseContext(),"file saved", Toast.LENGTH_SHORT).show();
                    amount.setText(null);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                balance.setText(amount.getText().toString());
                amount.setText(null);
            }
        }
    }
}
