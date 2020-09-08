package com.example.appledcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;

public class Configuration extends AppCompatActivity {

    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        this.fileName = getApplicationContext().getFilesDir().getPath().toString() + "/esp32_ip.txt";

        final EditText esp32IP = findViewById(R.id.etxtEsp32IP);
        Button btSalvar = findViewById(R.id.btSalvar);
        Button btExibir = findViewById(R.id.btExibir);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = esp32IP.getText().toString();

                Configuration.this.gravaEsp32IP(fileName, content);

            }
        });

        btExibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = recuperaEsp32IP(fileName);
                esp32IP.setText(content);
            }
        });

    }

    public void gravaEsp32IP(String fileName, String data) {
        try {
            OutputStreamWriter bufferSaida = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
            bufferSaida.write(data);
            bufferSaida.close();
        }
        catch (FileNotFoundException e) {
            Toast.makeText(this, "Falha na abertura do arquivo", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Falha na codificação do arquivo", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        } catch (IOException e) {
            Toast.makeText(this, "Falha na gravação do arquivo", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        Toast.makeText(this, "I.P salvo", Toast.LENGTH_SHORT).show();

    }

    public String recuperaEsp32IP(String fileName) {

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

            StringBuilder stringBuilder = new StringBuilder();

            String linha = bufferedReader.readLine();


            while (linha != null) {
                stringBuilder.append(linha);
                linha = bufferedReader.readLine();
            }

            return stringBuilder.toString();


        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Falha na codificação do arquivo", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Falha na abertura do arquivo", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        } catch (IOException e) {
            Toast.makeText(this, "Falha na recuperação do arquivo", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return "";
    }

}
