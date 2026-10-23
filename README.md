# Business Event Agent - Java/SpringBoot Reference Implementation

[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

OpenPipeline Beacon Forwarder for business-grade data

## Table of Contents

- [Security](#security)
- [Background](#background)
- [Install](#install)
- [Usage](#usage)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [License](#license)

## Security

Network connection between the bizevent-agent and the server-side occurs through a middle management server using a TLS handshake



## Background

An "event" is a data record expressing an occurrence and its context. Events are routed from an event producer (the source) to interested event consumers. The routing can  Events will contain two types of information: the Event Data representing the Occurrence and Context metadata providing contextual information about the Occurrence. A single occurrence MAY result in more than one event.

Business Events are registered using a dedicated function inside the BizEventAgent Service Component. 

When Business Events are registered through the dedicated function they are tied to the current server session id.


![Alt Text](./assets/images/sessions_event_data.svg)

## Install

Download this repository folder as a ZIP 

build.gradle.kts
```sh

dependencies {

    implementation("org.springframework.boot:spring-boot-starter")
    implementation(files("{PATH_TO_JAR}"))
}
```

## Usage

Bind the configurations to the business event agent using application.properties files
```java

rumagent.bizevents.enabled: true
rumagent.bizevents.endpointUrl: https://{ENVIRONMENT_ACTIVE_GATE_NAME}:9999/mbeacon/{tenant_id}
rumagent.bizevents.manufacturer: {TEAM_NAME}
rumagent.bizevents.applicationName: {APPLICATION_NAME}
rumagent.bizevents.applicationVersion: {APPLICATION_NAME}
rumagent.bizevents.operatingSystem: {KUBERNETES_CLUSTER_NAME}
rumagent.bizevents.waitForInitCompletion: true/false
rumagent.bizevents.disableSslVerification: true/false
rumagent.bizevents.logLevel: INFO
```

When the library is successfully installed as a dependency you can then inject it into your REST API Service components
```java
/* SpringBoot/Java REST API Service Component Demonstrating how to send business events */
public class ExampleService{
    
  
    @Autowired
    private BizEventAgent bizEventAgent; 

    public void doBusinessLogic() {

        // JSONValue is a required value type unique to openkit
        Map <String, JSONValue> attributes = new HashMap<String, JSONValue>();

        attributes.put("event.name", JSONStringValue.fromString("Confirmed Booking")); // 
        attributes.put("screen", JSONStringValue.fromString("booking-confirmation"));
        attributes.put("product", JSONStringValue.fromString("Danube Anna Hotel"));
        attributes.put("amount", JSONNumberValue.fromDouble(358.35));
        attributes.put("currency", JSONStringValue.fromString("USD"));
        attributes.put("reviewScore", JSONNumberValue.fromDouble(4.8));
        
        // register the business event record
        bizEventAgent.sendBizEvent("com.companyname.backend.service", attributes);
    }
}
```
## Maintainers

[@nick-montana](https://github.com/nick-montana)

## Contributing

See [the contributing file](contributing.md)!



Small note: If editing the README, please conform to the
[standard-readme](https://github.com/RichardLitt/standard-readme) specification.

## License

MIT © 2026 nick-montana
