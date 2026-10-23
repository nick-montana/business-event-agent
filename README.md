# business-event-agent

[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)
TODO: Put more badges here.

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

```sh
com.intelliplatforms.bizevents.endpointUrl: https://{ENVIRONMENT_ACTIVE_GATE_NAME}:9999/mbeacon/{tenant_id}
com.intelliplatforms.bizevents.manufacturer: {TEAM_NAME}
com.intelliplatforms.bizevents.applicationName: {APPLICATION_NAME}
com.intelliplatforms.bizevents.applicationVersion: {APPLICATION_NAME}
com.intelliplatforms.bizevents.operatingSystem: {KUBERNETES_CLUSTER_NAME}
com.intelliplatforms.bizevents.waitForInitCompletion: true/false
com.intelliplatforms.bizevents.disableSslVerification: true/false
com.intelliplatforms.bizevents.logLevel: INFO
```

## Maintainers

[@nick-montana](https://github.com/nick-montana)

## Contributing

See [the contributing file](contributing.md)!



Small note: If editing the README, please conform to the
[standard-readme](https://github.com/RichardLitt/standard-readme) specification.

## License

MIT © 2026 nick-montana
