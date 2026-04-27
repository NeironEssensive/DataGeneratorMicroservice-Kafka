# 📡 Data Generator Microservice

**A reactive Kafka producer that simulates IoT sensor networks — temperature, voltage, power — with dynamic scheduling, typed topic routing, and XML-driven serializer configuration.**

Not a "Hello Kafka" tutorial. This is a microservice that thinks about topic retention, schema separation, and non-blocking I/O from day one.

---

## 🎯 What Problem This Solves

You're building a monitoring system, a dashboard, or an alerting pipeline. You need **realistic telemetry data** flowing through Kafka **before the real sensors are deployed**. This service generates that data — on demand, configurable, and routed to separate topics by measurement type.

---

## 🏗️ Architecture
┌─────────────────────────────────────────────────────┐
│ HTTP API (optional) │
│ POST /api/v1/data/test/send │
└──────────────────────┬──────────────────────────────┘
│
┌──────────────────────▼──────────────────────────────┐
│ TestDataService │
│ ScheduledExecutorService — configurable interval │
│ Random sensorId (1-10), measurement (15-100) │
└──────────────────────┬──────────────────────────────┘
│
┌──────────────────────▼──────────────────────────────┐
│ KafkaDataService │
│ Routes by MeasurementType to correct topic │
│ Uses Reactor KafkaSender (non-blocking) │
└──────────────────────┬──────────────────────────────┘
│
┌─────────────┼─────────────┐
▼ ▼ ▼
┌─────────────┐ ┌─────────────┐ ┌─────────────┐
│ temperature │ │ voltage │ │ power │
│ topic │ │ topic │ │ topic │
│ 5 partitions│ │ 5 partitions│ │ 5 partitions│
│ 7-day ret. │ │ 7-day ret. │ │ 7-day ret. │
└─────────────┘ └─────────────┘ └─────────────┘

text

---

## 🧠 Design Decisions Worth Noticing

| Decision | Why It's Not Obvious |
|----------|----------------------|
| **XML-driven serializer config** | Serializer classes are loaded from `producer.xml`, not hardcoded. Swap Avro for JSON without touching Java code |
| **Reactor Kafka, not imperative** | `KafkaSender.send()` returns `Mono` — non-blocking, backpressure-ready |
| **Typed topics, not one generic** | Temperature goes to `data-temperature`, voltage to `data-voltage`. Downstream consumers filter by subscription, not by parsing payloads |
| **Topic retention = 7 days** | Explicit `RETENTION_MS_CONFIG` via `TopicBuilder`. No relying on broker defaults |
| **ScheduledExecutorService, not @Scheduled** | Gives dynamic control over delay at runtime via API parameters |
| **MeasurementType enum** | Type safety in routing. No magic strings |

---

## 🧬 Data Model

```java
public class Data {
    private Long sensorId;           // 1-10 (simulated)
    private LocalDateTime timestamp; // now()
    private double measurement;      // 15.0 - 100.0
    private MeasurementType type;    // TEMPERATURE | VOLTAGE | POWER
}
🔀 Topic Routing Logic
java
String topic = switch (data.getMeasurementType()) {
    case TEMPERATURE -> "data-temperature";
    case VOLTAGE -> "data-voltage";
    case POWER -> "data-power";
};
No if-else chain. No reflection. Just a clean switch expression — Java 17+ style.

🛠️ Tech Stack
https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white
https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white
https://img.shields.io/badge/Reactor-6DB33F?style=for-the-badge&logo=react&logoColor=white
https://img.shields.io/badge/XML_Config-8B8B8B?style=for-the-badge&logo=xml&logoColor=white
https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white

🚀 Quick Run
bash
# 1. Start Kafka (broker on localhost:9092 or configure .env)
# 2. Clone & build
git clone https://github.com/NeironEssensive/data-generator-microservice.git
cd data-generator-microservice
mvn clean package

# 3. Run
java -jar target/data-generator-microservice.jar

# 4. Trigger test data generation
curl -X POST http://localhost:8081/api/v1/data/test/send \
  -H "Content-Type: application/json" \
  -d '{"measurementTypes": ["TEMPERATURE", "VOLTAGE"], "delayInSeconds": 5}'
🧪 What I'd Add Next
Avro schema registry integration — schema evolution for real IoT pipelines

Dead Letter Queue for failed produces

Micrometer metrics — produce rate, error rate, latency percentiles

Docker Compose with Kafka + Zookeeper + this service

Integration test with Testcontainers Kafka
