![yabi-buildinfo-maven-plugin v1.0](https://img.shields.io/badge/yabi--buildinfo-v1.0-success)

#####YABI (Yet Another Build Info) Maven Plugin

    COPYRIGHT
    yabi-buildinfo-maven-plugin
    Copyright ©2020 Rab Wallace
    This software is released under the terms of the GNU General Public License GPLv3 or later
    and is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    either express or implied.
    See the License (COPYING.txt) for the specific language governing permissions and
    limitations under the License.
    http://www.gnu.org/licenses/gpl-3.0.en.html


#####yabi-buildinfo-maven-plugin
A simple, freely available maven plugin to build, and make available at runtime, build & project information, easily configurable from the pom.xml.

Released as an open source project licensed under <b>GNU GPLv3 or later</b>.

######Author
[Rab Wallace](https://github.com/rabwallace)
email: javarab@yahoo.com
---

#####Maven Dependency
    <dependency>
        <groupId>com.metaconflux</groupId>
        <artifactId>yabi-buildinfo-maven-plugin</artifactId>
        <version>1.0</version>
    </dependency>

#####Plugin Configuration (pom.xml)
The plugin has a few mandatory config settings and several optional settings. All are string values (except where stated):
#####Required configuration
    <productName>
    <javaClassname> (default: "BuildInfo", this is the name of the java source file that will be generated)
    <javaPackage>
    <srcRoot>

#####Optional configuration
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

#####Values of the ProjectStage enumeration:
        - PROOF_OF_CONCEPT,
        - DEVELOPMENT,
        - TEST,
        - ALPHA,
        - BETA,
        - PRODUCTION

#####Example pom.xml configuration
            <build>
                <plugins>
                    <plugin>
                    <groupId>com.metaconflux</groupId>
                        <artifactId>yab-buildinfo-maven-plugin</artifactId>
                        <version>1.0</version>
                        <configuration>
                            <productName>yabi-buildinfo-maven-plugin</productName>
                            <version>1.0</version>
                            <javaClassname>BuildInfo</javaClassname>
                            <javaPackage>com.metaconflux.yabibuildinfo.version</javaPackage>
                            <srcRoot>\Users\User\IdeaProjects\yabi-buildinfo-maven-plugin\src\main\java</srcRoot>
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