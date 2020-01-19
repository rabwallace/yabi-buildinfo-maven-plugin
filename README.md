![buildinfo-maven-plugin v1.0](https://img.shields.io/badge/buildinfo-v1.0-green)
# buildinfo-maven-plugin
A simple maven plugin to build, and make available at runtime, build & project information, easily configurable from the pom.xml.

Released as an open source project (License Pending).

######Author
[Rab Wallace](https://github.com/rabwallace)
---

####Maven Dependency
    <dependency>
        <groupId>com.metaconflux</groupId>
        <artifactId>buildinfo-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

##Plugin Configuration (pom.xml)
The plugin has a few mandatory config settings and several optional settings. All are string values (except where stated):
####Required configuration
    <productName>
    <javaClassname> (default: "BuildInfo", this is the name of the java source file that will be generated)
    <javaPackage>
    <srcRoot>

####Optional configuration
    <version>
    <projectStage> (enum, default: DEVELOPMENT)
    <mkdir> (boolean, default: true, can the plugin generated the required directory)
    <overwrite> (boolean, default true, can the plugin overwrite any previous buildinfo output)
    <productCodeName>
    <productUrl>
    <description>
    <copyright>
    <logoUrl>
    <shieldsioUrl>
    <author>
    <authorEmail>
    <team>
    <teamEmail>
    <companyName>
    <companyEmail>

####Values of the ProjectStage enumeration:
        - PROOF_OF_CONCEPT,
        - DEVELOPMENT,
        - TEST,
        - ALPHA,
        - BETA,
        - PRODUCTION

####Example pom.xml configuration
            <build>
                <plugins>
                    <plugin>
                    <groupId>com.metaconflux</groupId>
                        <artifactId>buildinfo-maven-plugin</artifactId>
                        <version>1.0-SNAPSHOT</version>
                        <configuration>
                            <productName>buildinfo-maven-plugin</productName>
                            <version>1.0</version>
                            <javaClassname>BuildInfo</javaClassname>
                            <javaPackage>com.metaconflux.buildinfo.version</javaPackage>
                            <srcRoot>\Users\User\IdeaProjects\buildinfo-maven-plugin\src\main\java</srcRoot>
                            <projectStage>DEVELOPMENT</projectStage>

                            <copyright></copyright>
                            <author>Rab Wallace</author>
                            <authorEmail>javarab@yahoo.com</authorEmail>
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