![yabi-buildinfo-maven-plugin v1.02](https://img.shields.io/badge/yabi--buildinfo--maven--plugin-v1.02-success)

##### YABI (Yet Another Build Info) Maven Plugin

    Copyright ©2020 Rab Wallace
    This file is part of yabi-buildinfo-maven-plugin.
 
    yabi-buildinfo-maven-plugin is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
 
    yabi-buildinfo-maven-plugin is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
 
    You should have received a copy of the GNU General Public License
    along with yabi-buildinfo-maven-plugin.  If not, see <https://www.gnu.org/licenses/>.


##### yabi-buildinfo-maven-plugin
A simple, freely available maven plugin to build, and make available at runtime, build & project information, easily configurable from the pom.xml.
Released as an open source project licensed under <b>GNU GPLv3 or later</b>.

###### Author
[Rab Wallace](https://github.com/rabwallace)
email: javarab@yahoo.com
---

### Table of Contents ###
* [Installation](#installation)
* [Maven Configuration](#maven-configuration)
* [Usage](#usage)
* [Bugs & Feature Requests](#bugs-and-feature-requests)
* [Licence](#licence)


Installation
------------
To install yabi-buildinfo-maven-plugin, just add the following dependency to your Maven project
```xml
<dependency>
    <groupId>com.github.rabwallace.yabibuildinfo</groupId>
    <artifactId>yabi-buildinfo-maven-plugin</artifactId>
    <version>1.02</version>
</dependency>
```

Maven Configuration
-------------------

The plugin has a few mandatory config settings and several optional settings. All are string values (except where stated):

```xml
    <productName>
    <javaClassname> (default: "BuildInfo", this is the name of the java source file that will be generated)
    <javaPackage>
    <srcRoot>
```

##### Optional configuration
```xml
    <version>
    <projectStage> (enum, default: DEVELOPMENT)
    <mkdir> (boolean, default: true, can the plugin generated the required directory)
    <overwrite> (boolean, default true, can the plugin overwrite any previous buildinfo output)
    <mainclass> (boolean, defaut: true) if true, produces a main class allowing you to say "java -jar myjar.jar" to get buildinfo data.
    <productCodeName>
    <component> Use it to specify the name of a subpart or module of the main product.
    <productUrl> Link to your products webpage.
    <description> Description of the product.
    <copyright> Your copyright statement.
    <logoUrl> Url link to your products logo.
    <shieldsioUrl> Url link for a ShieldsIo badge.
    <author>
    <authorEmail>
    <team>
    <teamEmail>
    <companyName>
    <companyEmail>
```

##### Values of the ProjectStage enumeration:
```java
- PROOF_OF_CONCEPT,
- DEVELOPMENT,
- TEST,
- ALPHA,
- BETA,
- PRODUCTION
```

##### Example pom.xml configuration
```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.github.rabwallace.yabibuildinfo</groupId>
            <artifactId>yabi-buildinfo-maven-plugin</artifactId>
            <version>1.02</version>
            <configuration>
                <productName>MyProductNameGoesHere</productName>
                <version>1.0</version>
                <javaClassname>BuildInfo</javaClassname>
                <javaPackage>com.myproject.version</javaPackage>
                <srcRoot>${project.build.sourceDirectory}</srcRoot>
                <projectStage>DEVELOPMENT</projectStage>
                <mainclass>true</mainclass>
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
```

Usage
-----
When the plugin runs, it will produce a small Java class with the build time information. The default name of this
class is `BuildInfo.java` (or you can override this using `<javaClassname>`). This class has public static final values
for all the build-time data it has assembled.

Bugs and Feature Requests
-------------------------
To report any bugs or make any feature requests, submit them here:
[https://github.com/rabwallace/yabi-buildinfo-maven-plugin/issues](https://github.com/rabwallace/yabi-buildinfo-maven-plugin/issues)

Licence
-------
yabi-buildinfo-maven-plugin is licenced under the `GNU GPLv3 or later later` licence. See `COPYING` file for details.`

