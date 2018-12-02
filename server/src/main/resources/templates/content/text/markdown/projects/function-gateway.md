---
publishedAt: 2018-12-02
title: Function-Gateway
headline: Function-Gateway
description: Kostengünstige Alternative zum API-Gateway von AWS, das AWS Lambdas basierend auf HTTP Anfragen aufruft.
tags: 
  - project
project:
  languages:
    - Kotlin
---
Da das API-Gateway von AWS recht teuer ist ($ 3,5 für 1 Mio. Anfragen) und ich das API-Gateway für sehr Anfragen reiche
Zwecke genutzt habe wurden die Kosten schnell sehr hoch. Mit function-gateway wollte ich daher in erster Linie meine Kosten
senken, aber die Komfortabilität des API-Gateways nicht aufgeben.

## Einsatzzweck
Für meine [LaMetric App Displify](https://github.com/KennethWussmann/lametric-displify) habe ich eine Lambda im Einsatz,
die zuvor vom API-Gateway aufgerufen wurde und entsprechend eine JSON-Antwort für die [LaMetric Time](https://lametric.com/)
bereitstellte. 
Dieser API-Gateway Endpoint wurde mehrere Millionen Mal von den einzelnen LaMetric Geräten der Nutzer aufgerufen und hat
so natürlich auch einiges an Kosten verursacht. 

Genau da setzt nun mein function-gateway an. Das kann ich auf jedem Server einfach deployen und habe die gleiche Funktion
nur kostengünstig. Momentan hoste ich eine Instanz auf der kleinsten DigitalOcean Instanz ($ 5).

Natürlich kann das AWS API-Gateway noch einiges mehr. Allerdings möchte ich mit function-gateway keine identische Kopie 
entwickeln. Sollte ich aber mal weitere Features benötigen, hab ich schon eine gute Basis.

Wichtig war mir, dass der Development und Deployment-Flow gleich bleibt. So habe ich vorher das [Serverless Framework]() 
genutzt um meine API sehr einfach auf AWS bereitstellen zu können. Das Serverless Framework nimmt einem dabei die komplette
Konfiguration des API-Gateway von AWS ab. Das wollte ich mit function-gateway ebenso einfach und schnell haben und habe dazu
ein Serverless Plugin geschrieben: [serverless-function-gateway](https://github.com/KennethWussmann/serverless-function-gateway).
Damit habe ich immer noch den genau gleichen Flow, ohne mich mit umständlichen Konfigurationen der API-Routen rumschlagen zu müssen.

## Technologien
Das function-gateway ist eigentlich nur eine Spring Boot Applikation, die dynamisch neue REST-Controller registriert und
Lambda Funktionen aufruft. 
Natürlich macht sie Gebrauch vom AWS Lambda SDK um Lambdas aufzulisten und auszuführen. 

## Resolver
Die Resolver dienen dazu aus verschiedenen Quellen die Information zu ziehen welche Lambda bei welcher URL und welcher HTTP
Methode aufgerufen werden soll. 
Ich habe das System der Resolver recht erweiterbar und offen gehalten um flexibel neue Resolver hinzufügen zu können.
Generell kann man sich die Resolver zusammen stecken wie es in den eigenen Flow passt. 

### Static
Dieser Resolver erlaubt es in der function-gateway Konfiguration fixe Routen einzutragen die bereitgestellt werden. 

### Crawl AWS
Dieser Resolver fragt im einstellbaren Intervall bei AWS die existierenden Lambdas ab und erstellt basierend auf den Tags
der Lambdas die Routen.

### Webhook
Der Webhook erlaubt es von außen aktiv Routen zu erstellen. Das wird für das Serverless Plugin genutzt um neue Routen 
direkt nach dem Lambda-Deployment anzukündigen.

### Database
Dieser Resolver liest direkt aus einer Datenbank die Routen aus und stellt sie bereit. Das dient dazu, damit auch mehrere
function-gateways im Cluster betrieben werden können und trotzdem alle die gleichen Routen bereitstellen. 

# Alternativen
Natürlich habe ich zuvor abgewogen, welche fertigen Alternativen es gibt. Da wäre zum Beispiel [Kong](https://konghq.com/), was nach einem sehr
mächtigen Gateway aussieht, mit Features die ich in naher Zukunft nie brauchen werde. Aber wenn, dann wird das wohl meine 
erste Wahl. 

# Links
GitHub: [function-gateway](https://github.com/KennethWussmann/function-gateway)<br />
GitHub: [serverless-function-gateway](https://github.com/KennethWussmann/serverless-function-gateway)