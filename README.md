![yabi-buildinfo-maven-plugin v1.02](https://img.shields.io/badge/yabi--buildinfo--maven--plugin-v1.02-success)

##### yabi-buildinfo-maven-plugin
YABI (Yet Another Build Info): A simple, freely-available Maven plugin to build, and make available at runtime, build & project
information, easily configurable from the pom.xml. Released as an open source project licensed under <b>GNU GPLv3 or later</b>.


### Table of Contents ###
* [Installation](#installation)
* [Maven Configuration](#maven-configuration)
    * [Example minimal configuration](#example-minimal-configuration)
    * [Example full configuration](#example-full-configuration)
* [Usage](#usage)
* [Bugs & Feature Requests](#bugs-and-feature-requests)
* [Licence](#licence)
* [Author](#author)


Installation
------------
To install yabi-buildinfo-maven-plugin, add the following dependency to your Maven project:
```xml
<dependency>
    <groupId>com.github.rabwallace.yabibuildinfo</groupId>
    <artifactId>yabi-buildinfo-maven-plugin</artifactId>
    <version>1.02</version>
</dependency>
```

Maven Configuration
-------------------

The plugin has a few mandatory config settings and several optional settings. All are string values (except where stated). There is a full example below.

```xml
    <productName> Put your project por product name here
    <javaClassname> (default: "BuildInfo") this is the name of the java source file that will be generated
    <javaPackage> The package you want your java
    <srcRoot> The path to your java source directory, usually ${project.build.sourceDirectory}
```

##### Optional configuration
You won't need them all, every project is different. 
```xml
    <version> The current version of your project
    <projectStage> (enum, default: DEVELOPMENT) You can use this to define what development stage your project is in
    <mkdir> (boolean, default: true) Allow the plugin to generate the required directory
    <overwrite> (boolean, default: true) Allow the plugin to overwrite any previous buildinfo file
    <mainclass> (boolean, default: true) Produces a main() method inside your BuildInfo class allowing "java -jar myjar.jar" to get buildinfo data
    <productCodeName> If your project build stage has a current "code name" 
    <component> Use it to specify the name of a subpart or module of the main product
    <productUrl> URL to your products web page
    <description> Description of the product
    <copyright> Your copyright statement
    <logoUrl> URL to your products logo
    <shieldsioUrl> URL for a ShieldsIo badge
    <author> The place to claim your fame
    <authorEmail> So your fans can contact you
    <team> Your build team or department
    <teamEmail>
    <companyName>
    <companyEmail>
```

##### Values of the ProjectStage enumeration:
```java
PROOF_OF_CONCEPT
DEVELOPMENT
TEST
ALPHA
BETA
PRODUCTION
```

Example minimal configuration
-----------------------------
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

Example full configuration
--------------------------
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
                <mkdir>true</mkdir>
                <overwrite>false</overwrite>
                <mainclass>true</mainclass>
                <productCodeName>Project Donut</productCodeName> 
                <component>Inter-process bridge thingy</component> 
                <productUrl>http://zzz.zzzzzzzzz.zzz/our-product</productUrl>
                <description>Description of the ZZZ product</description>
                <copyright>Copyright 2020 Company-ZZZ</copyright>>
                <logoUrl>http://zzz.zzzzzzzzz.zzz/ourLogo.png</logoUrl>
                <shieldsioUrl>https://img.shields.io/badge/zzzzz-your-badge</shieldsioUrl>
                <author> The place to claim your fame
                <authorEmail> So your fans can contact you
                <team>Awesome Dev Team</team>
                <teamEmail>devteam@zzzzzzzzz.zzz</teamEmail>
                <companyName>Company ZZZ</companyName>
                <companyEmail>info@zzzzzzzzz.zzz</companyEmail>
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
class is `BuildInfo.java` (you can override this using `<javaClassname>`). This class has `public static final` values
for all the build-time data it has assembled.

The `<version>` tag can take any value, number or text. If your build process uses version label substitution, this tag would
be the place to put it.

Bugs and Feature Requests
-------------------------
To report any bugs or make any feature requests, submit them here:<br>
[https://github.com/rabwallace/yabi-buildinfo-maven-plugin/issues](https://github.com/rabwallace/yabi-buildinfo-maven-plugin/issues)

Licence
-------
yabi-buildinfo-maven-plugin is licenced under the `GNU GPLv3 or later later` licence. See `COPYING.txt` file for details.`

```text
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
```

Author
------
[Rab Wallace](https://github.com/rabwallace)
email: javarab@yahoo.com
