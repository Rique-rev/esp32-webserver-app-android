#define WIFI_SSID "**********"
#define WIFI_PSK "**********"

// We will use wifi
#include <WiFi.h>
#include <HTTPServer.hpp>
#include <HTTPRequest.hpp>
#include <HTTPResponse.hpp>
#include <ArduinoJson.h>

using namespace httpsserver; // PORTA DEFAULT: 443

HTTPServer server = HTTPServer();

void handleRoot(HTTPRequest *req, HTTPResponse *res);
void handle404(HTTPRequest *req, HTTPResponse *res);

void setup()
{

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

  if (req->getMethod() == "GET")
  {
    res->setHeader("Content-Type", "text/html");

    res->println("<!DOCTYPE html>");
    res->println("<html>");
    res->println("<head><title>Hello World!</title></head>");
    res->println("<body>");
    res->println("<h1>Hello World!</h1>");
    res->print("<p>Your server is running for ");
    // A bit of dynamic data: Show the uptime
    res->print((int)(millis() / 1000), DEC);
    res->println(" seconds.</p>");
    res->println("</body>");
    res->println("</html>");
  }
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

    Serial.println(bodyStr);

    res->print(bodyStr);
  }
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