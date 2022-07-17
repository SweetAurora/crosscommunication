# crosscommunication
Useful for cross-communication between a forge &amp; spigot server.


The wiki tab contains basic information on the usage of this API. In case you're using maven, you're able to import this artifact from my nexus repository [here](https://nexus.sweetaurora.tech/).

An example would be:
```xml
    <repositories>
        <repository>
            <id>sweetaurora-maven</id>
            <url>https://nexus.sweetaurora.tech/repository/combined-repo/</url>
        </repository>
        ...
    </repositories>

    <dependencies>
        <dependency>
          <groupId>nl.aurora</groupId>
          <artifactId>crosscommunication</artifactId>
          <version>1.0.1</version>
        </dependency>
        ...
    </dependencies>
```

You can find examples for other tools on the nexus website by selecting the artifact and changing the "usage" option on the right lower side after selecting.
