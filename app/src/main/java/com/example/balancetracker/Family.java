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

public class Family extends AppCompatActivity {

    public EditText amount;
    public EditText balNana;
    public EditText balDad;
    public Button addNana;
    public Button addDad;
    public Button payNana;
    public Button payDad;
    public Button home;
    static String fBalNana = "BalNana.txt";
    static String fBalDad = "BalDad.txt";
    static String fToNana = "toNana.txt";
    static String fToDad = "toDad.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        amount = findViewById(R.id.eAmount);

        balNana = findViewById(R.id.eBalNana);
        balNana.setText(getStringFromFile(fBalNana));

        balDad = findViewById(R.id.eBalDad);
        balDad.setText(getStringFromFile(fBalDad));

        addNana = findViewById(R.id.bAddNana);
        addNana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNana();
            }
        });

        addDad = findViewById(R.id.bAddDad);
        addDad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDad();
            }
        });

        payNana = findViewById(R.id.bPayNana);
        payNana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayNana();
            }
        });

        payDad = findViewById(R.id.bPayDad);
        payDad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayDad();
            }
        });

        home = findViewById(R.id.bHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Family.this, MainActivity.class));
            }
        });
    }

    private void AddNana() {
        String obal = getStringFromFile(fBalNana);
        if(amount != null) {
            String cbal;
            if (obal.length() > 0) {
                cbal = String.valueOf(parseDouble(obal) + parseDouble(amount.getText().toString()));
            } else if (parseDouble(amount.getText().toString()) > 0) {
                cbal = amount.getText().toString();
            } else {
                cbal = "0.00";
            }
            balNana.setText(cbal);

            try {
                writeStringToFile(fBalNana, cbal);
                Toast.makeText(getBaseContext(), "file saved", Toast.LENGTH_SHORT).show();
                amount.setText(null);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void AddDad() {
        String obal = getStringFromFile(fBalDad);
        if(amount != null) {
            String cbal;
            if (obal.length() > 0) {
                cbal = String.valueOf(parseDouble(obal) + parseDouble(amount.getText().toString()));
            } else if (parseDouble(amount.getText().toString()) > 0) {
                cbal = amount.getText().toString();
            } else {
                cbal = "0.00";
            }
            balDad.setText(cbal);

            try {
                writeStringToFile(fBalDad, cbal);
                Toast.makeText(getBaseContext(), "file saved", Toast.LENGTH_SHORT).show();
                amount.setText(null);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void PayNana() {
        String obal = getStringFromFile(fBalNana);
        String cbal = String.valueOf(parseDouble(obal) - parseDouble(amount.getText().toString()));
        String otonana = getStringFromFile(fToNana);
        String ctonana = String.valueOf((parseDouble(otonana) + parseDouble(amount.getText().toString())));
        try {
            writeStringToFile(fToNana, ctonana);
            writeStringToFile(fBalNana, cbal);
            amount.setText(null);
            balNana.setText(cbal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PayDad() {
        String obal = getStringFromFile(fBalDad);
        String cbal = String.valueOf(parseDouble(obal) - parseDouble(amount.getText().toString()));
        String otodad = getStringFromFile(fToDad);
        String ctodad = String.valueOf(parseDouble(otodad) + parseDouble(amount.getText().toString()));
        try {
            writeStringToFile(fToDad, ctodad);
            writeStringToFile(fBalDad, cbal);
            amount.setText(null);
            balDad.setText(cbal);
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
