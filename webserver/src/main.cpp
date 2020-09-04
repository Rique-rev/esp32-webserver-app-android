#include <Arduino.h>
#include <WiFi.h>

#define PORT 80
#define mtIDENTIFICAR 0
#define mtGET 1
#define mtPOST 2
#define mtOUTRO 3

WiFiServer server(PORT);
WiFiClient client;
char ssid[] = "*********";    // SSID
char pass[] = "*********"; // Senha

void setup()
{
  Serial.begin(115200);
  // WiFi.mode(WIFI_STA);

  /*
    Definir os pinos dos leds

  */

  WiFi.begin(ssid, pass);
  Serial.print("Tentando conectar a rede: ");
  Serial.println(ssid);
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(10000);
  }

  Serial.println("Conectado!");
  Serial.print("ESP32 WebServer IP: ");
  Serial.println(WiFi.localIP());

  server.begin();
}

void loop()
{
  client = server.available();

  if (client)
  {
    byte metodo = mtIDENTIFICAR;
    String url;
    unsigned long tamanhoConteudo = 0;

    String linha = "";
    while (client.connected())
    {
      if (client.available())
      {
        char c = client.read();
        Serial.write(c);
        
        if (c == '\n')
        {
          if (linha.length() == 0)
          {
            if (metodo == mtGET)
            {
              if (url == "/")
              {
                Serial.println("HOME *****************************************");
                client.println(F("HTTP/1.1 200 OK"));
                client.println(F("Content-Type: text/html"));
                client.println(F("Connection: close"));
                client.println();
              }
              else
              {
                client.println(F("HTTP/1.1 404 Not Found"));
                client.println(F("Content-Type: text/html"));
                client.println(F("Connection: close"));
                client.println();
              }
            }
          }
          else if (c != '\r')
          {
            linha += c;
          }
        }
      }
    }

    client.stop();
  }
}

