#define WIFI_SSID ""
#define WIFI_PSK ""

#define LedVerde 32
#define LedVermelho 33
#define LedAzul 25
#define LedLaranja 26
#define LedAmarelo 27

// We will use wifi
#include <WiFi.h>
#include <HTTPServer.hpp>
#include <HTTPRequest.hpp>
#include <HTTPResponse.hpp>
#include <ArduinoJson.h>
#include <map>

using namespace httpsserver; // PORTA DEFAULT: 443

HTTPServer server = HTTPServer();

void handleRoot(HTTPRequest *req, HTTPResponse *res);
void handlePisca(HTTPRequest *req, HTTPResponse *res);
void handle404(HTTPRequest *req, HTTPResponse *res);

void AcendeApagaLed(DynamicJsonDocument requestBody);

void setup()
{
  pinMode(LedVerde, OUTPUT);
  pinMode(LedVermelho, OUTPUT);
  pinMode(LedAzul, OUTPUT);
  pinMode(LedLaranja, OUTPUT);
  pinMode(LedAmarelo, OUTPUT);

  Serial.begin(115200);

  Serial.println("Setting up WiFi");
  WiFi.begin(WIFI_SSID, WIFI_PSK);
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(500);
  }
  Serial.print("Connected. IP=");
  Serial.println(WiFi.localIP());

  ResourceNode *nodeRootGET = new ResourceNode("/", "GET", &handleRoot);
  server.registerNode(nodeRootGET);

  ResourceNode *nodePisca = new ResourceNode("/pisca", "GET", &handlePisca);
  server.registerNode(nodePisca);

  ResourceNode *nodeRootPOST = new ResourceNode("/", "POST", &handleRoot);
  server.registerNode(nodeRootPOST);

  ResourceNode *node404 = new ResourceNode("", "GET", &handle404);
  server.setDefaultNode(node404);

  Serial.println("Starting server...");
  server.start();
  if (server.isRunning())
  {
    Serial.println("Server ready.");
  }
}

void loop()
{
  // This call will let the server do its work
  server.loop();

  // Other code would go here...
  delay(1);
}

void handleRoot(HTTPRequest *req, HTTPResponse *res)
{

  // Devolve o estado (acesso/apagado) de todos os leds
  if (req->getMethod() == "GET")
  {
    res->setHeader("Content-Type", "text/plain");
    DynamicJsonDocument statusLeds(256);

    bool statusLedVerde = digitalRead(LedVerde);
    bool statusLedVermelho = digitalRead(LedVermelho);
    bool statusLedAzul = digitalRead(LedAzul);
    bool statusLedLaranja = digitalRead(LedLaranja);
    bool statusLedAmarelo = digitalRead(LedAmarelo);

    statusLeds["verde"] = statusLedVerde;
    statusLeds["vermelho"] = statusLedVermelho;
    statusLeds["azul"] = statusLedAzul;
    statusLeds["laranja"] = statusLedLaranja;
    statusLeds["amarelo"] = statusLedAmarelo;

    String response;
    serializeJson(statusLeds, response);

    res->println(response);
  }
  // Altera o estado (acesso/apagado) de um led
  else if (req->getMethod() == "POST")
  {
    DynamicJsonDocument bodyJson(256);

    res->setHeader("Content-Type", "text/plain");

    // Pegando o tamanho (em bytes) do body
    const int bufferSize = req->getContentLength();
    // Criando um buffer (variavel que armazenara o conteudo em bytes do body) do tamanho correto
    byte buffer[bufferSize];

    while (!(req->requestComplete()))
    {
      // Lendo os bytes do body e armazenando dentro do buffer
      req->readBytes(buffer, bufferSize);
    }

    // Convertendo o conteudo do buffer em string (conjunto de chars)
    String bodyStr;
    for (char c : buffer)
      bodyStr += c;

    deserializeJson(bodyJson, bodyStr);
    AcendeApagaLed(bodyJson);

    res->print("OK");
  }
}

void handlePisca(HTTPRequest *req, HTTPResponse *res)
{
  res->setHeader("Content-Type", "text/plain");

  unsigned long tempoReferencia = 0;
  int piscaDelay = 500; // 1000ms = 1s
  int count = 0;
  bool statusLed = HIGH;

  while (count < 20)
  {
    if ((millis() - tempoReferencia) >= piscaDelay)
    {

      digitalWrite(LedVerde, statusLed);
      digitalWrite(LedVermelho, statusLed);
      digitalWrite(LedAzul, statusLed);
      digitalWrite(LedLaranja, statusLed);
      digitalWrite(LedAmarelo, statusLed);

      tempoReferencia = millis();
      statusLed = !statusLed;
      count++;
    }
  }

  res->print("OK");
}

void handle404(HTTPRequest *req, HTTPResponse *res)
{
  // Discard request body, if we received any
  // We do this, as this is the default node and may also server POST/PUT requests
  req->discardRequestBody();

  // Set the response status
  res->setStatusCode(404);
  res->setStatusText("Not Found");

  // Set content type of the response
  res->setHeader("Content-Type", "text/html");

  // Write a tiny HTTP page
  res->println("<!DOCTYPE html>");
  res->println("<html>");
  res->println("<head><title>Not Found</title></head>");
  res->println("<body><h1>404 Not Found</h1><p>The requested resource was not found on this server.</p></body>");
  res->println("</html>");
}

void AcendeApagaLed(DynamicJsonDocument requestBody)
{

  std::map<String, int> codigoCor = {
      {"verde", 1},
      {"vermelho", 2},
      {"azul", 3},
      {"laranja", 4},
      {"amarelo", 5}};

  String corLed = requestBody["cor"];
  String status = requestBody["status"];
  int codigo = codigoCor[corLed];

  // Serial.println(corLed);
  switch (codigo)
  {
  case 1:
    if (status == "on")
    {
      digitalWrite(LedVerde, HIGH);
    }
    else if (status == "off")
    {
      digitalWrite(LedVerde, LOW);
    }
    break;

  case 2:
    if (status == "on")
    {
      digitalWrite(LedVermelho, HIGH);
    }
    else if (status == "off")
    {
      digitalWrite(LedVermelho, LOW);
    }
    break;

  case 3:
    if (status == "on")
    {
      digitalWrite(LedAzul, HIGH);
    }
    else if (status == "off")
    {
      digitalWrite(LedAzul, LOW);
    }
    break;

  case 4:
    if (status == "on")
    {
      digitalWrite(LedLaranja, HIGH);
    }
    else if (status == "off")
    {
      digitalWrite(LedLaranja, LOW);
    }
    break;

  case 5:
    if (status == "on")
    {
      digitalWrite(LedAmarelo, HIGH);
    }
    else if (status == "off")
    {
      digitalWrite(LedAmarelo, LOW);
    }
    break;

  default:
    break;
  }
}