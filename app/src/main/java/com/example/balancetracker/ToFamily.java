package com.example.balancetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import static java.lang.Double.parseDouble;

public class ToFamily extends AppCompatActivity {

    public EditText toNana;
    public EditText toDad;
    public Button transfer;
    public Button home;
    static String ftonana = "toNana.txt";
    static String ftodad = "toDad.txt";
    static String fdebit = "debit.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_family);

        toNana = findViewById(R.id.eToNana);
        toNana.setText(getStringFromFile(ftonana));

        toDad = findViewById(R.id.eToDad);
        toDad.setText(getStringFromFile(ftodad));

        home = findViewById(R.id.bHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToFamily.this, MainActivity.class));
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

    private void Transfer(){
        String obaltonana = getStringFromFile(ftonana);
        String obaltodad = getStringFromFile(ftodad);
        String odebit = getStringFromFile(fdebit);
        String cdebit = String.valueOf(parseDouble(odebit) - parseDouble(obaltonana) - parseDouble(obaltodad));
        try {
            writeStringToFile(ftonana, "0.00");
            toNana.setText(null);
            writeStringToFile(ftodad, "0.00");
            toDad.setText(null);
            writeStringToFile(fdebit, cdebit);
        } catch (Exception e) {
            e.printStackTrace();
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
            return stringBuilder.toString();
        } catch (Exception e) {
            System.out.println("No previous debit file found, continuing...");
            return "0.00";
        }
    }

    private void writeStringToFile(String file, String value) throws Exception {
        FileOutputStream fos = openFileOutput(file, MODE_PRIVATE);
        fos.write(value.getBytes());
        fos.close();
    }
}
