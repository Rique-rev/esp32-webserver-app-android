package com.example.appledcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
//    Configuration config = new Configuration();
//    String url = config.recuperaEsp32IP();
    String fileName;
//    String url = "http://192.168.15.34/";


    String statusLedVerde;
    String statusLedVermelho ;
    String statusLedAzul;
    String statusLedLaranja;
    String statusLedAmarelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        final Button btLedVerde = findViewById(R.id.btLedVerde);
        final Button btLedVermelho = findViewById(R.id.btLedVermelho);
        final Button btLedAzul = findViewById(R.id.btLedAzul);
        final Button btLedLaranja = findViewById(R.id.btLedLaranja);
        final Button btLedAmarelo = findViewById(R.id.btLedAmarelo);

        Button btAcendeTodos = findViewById(R.id.btAcendeTodos);
        Button btApagaTodos = findViewById(R.id.btApagaTodos);
        Button btPiscarLeds = findViewById(R.id.btPiscarLeds);

        ImageButton btConfig = findViewById(R.id.btConfig);
        ImageButton btRefresh = findViewById(R.id.btRefresh);

        final TextView txtResponse = findViewById(R.id.txtResponse);

        final TextView txtStatusLedVerde = findViewById(R.id.txtStatusLedVerde);
        final TextView txtStatusLedVermelho = findViewById(R.id.txtStatusLedVermelho);
        final TextView txtStatusLedAzul = findViewById(R.id.txtStatusLedAzul);
        final TextView txtStatusLedLaranja = findViewById(R.id.txtStatusLedLaranja);
        final TextView txtStatusLedAmarelo = findViewById(R.id.txtStatusLedAmarelo);

//        Configuration config = new Configuration();
//        final String url = config.recuperaEsp32IP();


        // LED VERDE
        btLedVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileName = getApplicationContext().getFilesDir().getPath().toString() + "/esp32_ip.txt";
                final Configuration config = new Configuration();
                String url = "http://" + config.recuperaEsp32IP(fileName) + "/";

                OkHttpClient client = new OkHttpClient();

                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                JSONObject data = new JSONObject();

                try {
                    data.put("cor", "verde");


                }catch (JSONException e) {
                    Log.d("Construção JSON Body", "JSON Exception");
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(JSON, data.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor\nVerifique o endereço de IP", Toast.LENGTH_LONG).show();
                            }
                        });


                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            updateButtonsState(myResponse, btLedVerde, btLedVermelho, btLedAzul, btLedLaranja, btLedAmarelo);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtResponse.setText("OK");
                                    txtStatusLedVerde.setText(statusLedVerde);
                                    txtStatusLedVermelho.setText(statusLedVermelho);
                                    txtStatusLedAzul.setText(statusLedAzul);
                                    txtStatusLedLaranja.setText(statusLedLaranja);
                                    txtStatusLedAmarelo.setText(statusLedAmarelo);
                                }
                            });
                        }
                    }
                });

            }
        });


        // LED VERMELHO
        btLedVermelho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileName = getApplicationContext().getFilesDir().getPath().toString() + "/esp32_ip.txt";
                final Configuration config = new Configuration();
                String url = "http://" + config.recuperaEsp32IP(fileName) + "/";

                OkHttpClient client = new OkHttpClient();

                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                JSONObject data = new JSONObject();
                try {
                    data.put("cor", "vermelho");

                }catch (JSONException e) {
                    Log.d("Construção JSON Body", "JSON Exception");
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(JSON, data.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor\nVerifique o endereço de IP", Toast.LENGTH_LONG).show();
                            }
                        });

                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            updateButtonsState(myResponse, btLedVerde, btLedVermelho, btLedAzul, btLedLaranja, btLedAmarelo);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtResponse.setText("OK");
                                    txtStatusLedVerde.setText(statusLedVerde);
                                    txtStatusLedVermelho.setText(statusLedVermelho);
                                    txtStatusLedAzul.setText(statusLedAzul);
                                    txtStatusLedLaranja.setText(statusLedLaranja);
                                    txtStatusLedAmarelo.setText(statusLedAmarelo);
                                }
                            });
                        }
                    }
                });

            }
        });


        // LED AZUL
        btLedAzul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileName = getApplicationContext().getFilesDir().getPath().toString() + "/esp32_ip.txt";
                final Configuration config = new Configuration();
                String url = "http://" + config.recuperaEsp32IP(fileName) + "/";

                OkHttpClient client = new OkHttpClient();

                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                JSONObject data = new JSONObject();
                try {
                    data.put("cor", "azul");

                }catch (JSONException e) {
                    Log.d("Construção JSON Body", "JSON Exception");
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(JSON, data.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor\nVerifique o endereço de IP", Toast.LENGTH_LONG).show();
                            }
                        });
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            updateButtonsState(myResponse, btLedVerde, btLedVermelho, btLedAzul, btLedLaranja, btLedAmarelo);


                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtResponse.setText("OK");
                                    txtStatusLedVerde.setText(statusLedVerde);
                                    txtStatusLedVermelho.setText(statusLedVermelho);
                                    txtStatusLedAzul.setText(statusLedAzul);
                                    txtStatusLedLaranja.setText(statusLedLaranja);
                                    txtStatusLedAmarelo.setText(statusLedAmarelo);
                                }
                            });
                        }
                    }
                });

            }
        });


        // LED VERDE
        btLedLaranja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileName = getApplicationContext().getFilesDir().getPath().toString() + "/esp32_ip.txt";
                final Configuration config = new Configuration();
                String url = "http://" + config.recuperaEsp32IP(fileName) + "/";

                OkHttpClient client = new OkHttpClient();

                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                JSONObject data = new JSONObject();
                try {
                    data.put("cor", "laranja");

                }catch (JSONException e) {
                    Log.d("Construção JSON Body", "JSON Exception");
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(JSON, data.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor\nVerifique o endereço de IP", Toast.LENGTH_LONG).show();
                            }
                        });
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            updateButtonsState(myResponse, btLedVerde, btLedVermelho, btLedAzul, btLedLaranja, btLedAmarelo);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtResponse.setText("OK");
                                    txtStatusLedVerde.setText(statusLedVerde);
                                    txtStatusLedVermelho.setText(statusLedVermelho);
                                    txtStatusLedAzul.setText(statusLedAzul);
                                    txtStatusLedLaranja.setText(statusLedLaranja);
                                    txtStatusLedAmarelo.setText(statusLedAmarelo);
                                }
                            });
                        }
                    }
                });

            }
        });


        // LED AMARELO
        btLedAmarelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileName = getApplicationContext().getFilesDir().getPath().toString() + "/esp32_ip.txt";
                final Configuration config = new Configuration();
                String url = "http://" + config.recuperaEsp32IP(fileName) + "/";

                OkHttpClient client = new OkHttpClient();

                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                JSONObject data = new JSONObject();
                try {
                    data.put("cor", "amarelo");

                }catch (JSONException e) {
                    Log.d("Construção JSON Body", "JSON Exception");
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(JSON, data.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor\nVerifique o endereço de IP", Toast.LENGTH_LONG).show();
                            }
                        });
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            updateButtonsState(myResponse, btLedVerde, btLedVermelho, btLedAzul, btLedLaranja, btLedAmarelo);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtResponse.setText("OK");
                                    txtStatusLedVerde.setText(statusLedVerde);
                                    txtStatusLedVermelho.setText(statusLedVermelho);
                                    txtStatusLedAzul.setText(statusLedAzul);
                                    txtStatusLedLaranja.setText(statusLedLaranja);
                                    txtStatusLedAmarelo.setText(statusLedAmarelo);
                                }
                            });
                        }
                    }
                });

            }
        });


        // ACENDER TODOS OS LEDS
        btAcendeTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileName = getApplicationContext().getFilesDir().getPath().toString() + "/esp32_ip.txt";
                final Configuration config = new Configuration();
                String url = "http://" + config.recuperaEsp32IP(fileName) + "/";

                txtResponse.setText("Requesting...");

                OkHttpClient client = new OkHttpClient();
                String endpoint = url + "acendeTudo";

                Request request = new Request.Builder()
                        .url(endpoint)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor\nVerifique o endereço de IP", Toast.LENGTH_LONG).show();
                            }
                        });
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            updateButtonsState(myResponse, btLedVerde, btLedVermelho, btLedAzul, btLedLaranja, btLedAmarelo);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtResponse.setText("OK");
                                    txtStatusLedVerde.setText(statusLedVerde);
                                    txtStatusLedVermelho.setText(statusLedVermelho);
                                    txtStatusLedAzul.setText(statusLedAzul);
                                    txtStatusLedLaranja.setText(statusLedLaranja);
                                    txtStatusLedAmarelo.setText(statusLedAmarelo);
                                }
                            });
                        }
                    }
                });

            }
        });

        // APAGAR TODOS OS LEDS
        btApagaTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = getApplicationContext().getFilesDir().getPath().toString() + "/esp32_ip.txt";
                final Configuration config = new Configuration();
                String url = "http://" + config.recuperaEsp32IP(fileName) + "/";

                txtResponse.setText("Requesting...");

                OkHttpClient client = new OkHttpClient();
                String endpoint = url + "apagaTudo";

                Request request = new Request.Builder()
                        .url(endpoint)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor\nVerifique o endereço de IP", Toast.LENGTH_LONG).show();
                            }
                        });
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            updateButtonsState(myResponse, btLedVerde, btLedVermelho, btLedAzul, btLedLaranja, btLedAmarelo);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtResponse.setText("OK");
                                    txtStatusLedVerde.setText(statusLedVerde);
                                    txtStatusLedVermelho.setText(statusLedVermelho);
                                    txtStatusLedAzul.setText(statusLedAzul);
                                    txtStatusLedLaranja.setText(statusLedLaranja);
                                    txtStatusLedAmarelo.setText(statusLedAmarelo);
                                }
                            });
                        }
                    }
                });

            }
        });


        // PISCAR LEDS
        btPiscarLeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = getApplicationContext().getFilesDir().getPath().toString() + "/esp32_ip.txt";
                final Configuration config = new Configuration();
                String url = "http://" + config.recuperaEsp32IP(fileName) + "/";

                txtResponse.setText("Requesting...");

                OkHttpClient client = new OkHttpClient();
                String endpoint = url + "pisca";

                Request request = new Request.Builder()
                        .url(endpoint)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor\nVerifique o endereço de IP", Toast.LENGTH_LONG).show();
                            }
                        });
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            updateButtonsState(myResponse, btLedVerde, btLedVermelho, btLedAzul, btLedLaranja, btLedAmarelo);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtResponse.setText("OK");
                                    txtStatusLedVerde.setText(statusLedVerde);
                                    txtStatusLedVermelho.setText(statusLedVermelho);
                                    txtStatusLedAzul.setText(statusLedAzul);
                                    txtStatusLedLaranja.setText(statusLedLaranja);
                                    txtStatusLedAmarelo.setText(statusLedAmarelo);
                                }
                            });
                        }
                    }
                });

            }
        });


        // REGRESH
        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileName = getApplicationContext().getFilesDir().getPath().toString() + "/esp32_ip.txt";
                final Configuration config = new Configuration();
                String url = "http://" + config.recuperaEsp32IP(fileName) + "/";

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor\nVerifique o endereço de IP", Toast.LENGTH_LONG).show();
                            }
                        });
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            updateButtonsState(myResponse, btLedVerde, btLedVermelho, btLedAzul, btLedLaranja, btLedAmarelo);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtResponse.setText("Atualizado");
                                    txtStatusLedVerde.setText(statusLedVerde);
                                    txtStatusLedVermelho.setText(statusLedVermelho);
                                    txtStatusLedAzul.setText(statusLedAzul);
                                    txtStatusLedLaranja.setText(statusLedLaranja);
                                    txtStatusLedAmarelo.setText(statusLedAmarelo);

                                }
                            });
                        }
                    }
                });



            }
        });




    }


    private void updateButtonsState(String myResponse, View btLedVerde, View btLedVermelho, View btLedAzul, View btLedLaranja, View btLedAmarelo){
        try {
            JSONObject jsonResponse = new JSONObject(myResponse);
            if(jsonResponse.getBoolean("verde")){
                statusLedVerde = "ON";
                btLedVerde.setBackgroundColor(Color.parseColor("#9FF781"));
            }else{
                statusLedVerde = "OFF";
                btLedVerde.setBackgroundColor(Color.parseColor("#D8D8D8"));
            }


            if(jsonResponse.getBoolean("vermelho")){
                statusLedVermelho = "ON";
                btLedVermelho.setBackgroundColor(Color.parseColor("#FE2E2E"));
            }else{
                statusLedVermelho = "OFF";
                btLedVermelho.setBackgroundColor(Color.parseColor("#D8D8D8"));
            }

            if(jsonResponse.getBoolean("azul")){
                statusLedAzul = "ON";
                btLedAzul.setBackgroundColor(Color.parseColor("#2E64FE"));
            }else{
                statusLedAzul = "OFF";
                btLedAzul.setBackgroundColor(Color.parseColor("#D8D8D8"));
            }

            if(jsonResponse.getBoolean("laranja")){
                statusLedLaranja = "ON";
                btLedLaranja.setBackgroundColor(Color.parseColor("#FE9A2E"));
            }else{
                statusLedLaranja = "OFF";
                btLedLaranja.setBackgroundColor(Color.parseColor("#D8D8D8"));
            }

            if(jsonResponse.getBoolean("amarelo")){
                statusLedAmarelo = "ON";
                btLedAmarelo.setBackgroundColor(Color.parseColor("#F4FA58"));
            }else{
                statusLedAmarelo = "OFF";
                btLedAmarelo.setBackgroundColor(Color.parseColor("#D8D8D8"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Ir para a pagina de Configuração
    public void irParaConfiguracao(View v) {
        Intent intent = new Intent(this, Configuration.class);
        startActivity(intent);


    };


}
