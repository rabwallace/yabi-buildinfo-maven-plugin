# buildinfo-maven-plugin
A simple maven plugin to build and provide build-time information, easily configurable from the pom.xml.

    <dependency>
        <groupId>com.metaconflux</groupId>
        <artifactId>buildinfo-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>


Required configuration
    
    productName
    javaClassname
    javaPackage
    srcRoot

Optional configuration

    javaClassname (default: "BuildInfo")
    projectStage (default: DEVELOPMENT)
    mkdir (default: true)
    productCodeName
    productUrl
    copyright
    logoUrl
    shieldsioUrl
    author
    authorEmail
    team
    teamEmail
    companyName
    companyEmail


Values of the ProjectStage enumeration:

        PROOF_OF_CONCEPT,
        DEVELOPMENT,
        TEST,
        ALPHA,
        BETA,
        PRODUCTION


Example pom.xml configuration


            <build>
                <plugins>
                    <plugin>
                    <groupId>com.metaconflux</groupId>
                        <artifactId>buildinfo-maven-plugin</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <configuration>
                            <productName>buildinfo-maven-plugin</productName>
                            <version>0.1</version>
                            <javaClassname>BuildInfo</javaClassname>
                            <javaPackage>com.metaconflux.buildinfo.version</javaPackage>
                            <srcRoot>\Users\User\IdeaProjects\buildinfo-maven-plugin\src\main\java</srcRoot>
                            <projectStage>TEST</projectStage>

                            <copyright></copyright>
                            <author></author>
                            <authorEmail></authorEmail>
                            <company></company>
                            <companyEmail></companyEmail>
                            <productCodeName></productCodeName>
                        </configuration>

                        <executions>
                            <execution>
                                <id>generate-sources</id>
                                <phase>generate-sources</phase>
                                <goals>
                                    <goal>buildinfo</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>