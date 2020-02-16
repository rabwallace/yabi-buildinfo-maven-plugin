/*******************************************************************************************
 * Copyright ©2020 Rab Wallace
 * This file is part of yabi-buildinfo-maven-plugin.
 *
 * yabi-buildinfo-maven-plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * yabi-buildinfo-maven-plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with yabi-buildinfo-maven-plugin.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************************/

package com.metaconflux.yabibuildinfo;

import com.metaconflux.yabibuildinfo.version.BuildInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BuildInfoFileWriter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy (HH:mm:ss)");
    private BufferedWriter writer;
    private String className;
    private LocalDateTime buildTime;

    public BuildInfoFileWriter(@NotNull final File javaFile, @NotNull final String packageName, @NotNull final LocalDateTime buildTime) throws IOException {
        this.buildTime = buildTime;
        writer = new BufferedWriter(new FileWriter(javaFile));

        writer.write("/***********************************************************************************************");
        writer.newLine();
        writer.write("* Generated by yabi-buildinfo-maven-plugin (version: " + BuildInfo.version + ")");
        writer.newLine();

        writer.write("* Built: " + buildTime.format(formatter));
        writer.newLine();
        writer.write("***********************************************************************************************/");
        writer.newLine();

        writer.write("package " + packageName + ';');
        writer.newLine();
    }

    public void writeImport(@NotNull final String importName) throws IOException {
        writer.write("import " + importName + ';');
        writer.newLine();
    }

    public void writeClass(@NotNull final String className) throws IOException {
        this.className = className;
        writer.newLine();
        writer.write("public class " + className + " {");
        writer.newLine();
    }

    public void writeConstructor() throws IOException {
        writer.newLine();
        writer.write("\tpublic " + className + "(){}");
        writer.newLine();
    }

    public void writeMainClass(@NotNull final String productName, @NotNull final String component, @NotNull final LocalDateTime buildTime,
                               @NotNull final YabiBuildInfoMavenPlugin.ProjectStage projectStage,
                               @NotNull final String version) throws IOException {
        final String productName_ = "Project: " + productName;
        final String projectStage_ = "Stage: " + projectStage.toString();
        final String version_ = "Version: " + version;
        final String buildTime_ = "Build time: " + buildTime.format(formatter);

        writer.newLine();
        writer.write("\tpublic static void main(String[] args) {");
        writer.newLine();

        writer.write("\t\tSystem.out.println(\"" + productName_ + "\");");
        writer.newLine();

        if (component != null) {
            final String component_ = "Component: " + component;
            writer.write("\t\tSystem.out.println(\"" + component_ + "\");");
            writer.newLine();
        }

        writer.write("\t\tSystem.out.println(\"" + projectStage_ + "\");");
        writer.newLine();

        writer.write("\t\tSystem.out.println(\"" + version_ + "\");");
        writer.newLine();

        writer.write("\t\tSystem.out.println(\"" + buildTime_ + "\");");
        writer.newLine();

        writer.write("\t}");
        writer.newLine();
    }

    public void writeBuildDateTimeVariables() throws IOException {
        int year = buildTime.getYear();
        int month = buildTime.getMonthValue();
        int day = buildTime.getDayOfMonth();
        int hour = buildTime.getHour();
        int minute = buildTime.getMinute();
        int second = buildTime.getSecond();

        writer.write("\tpublic static final LocalDateTime buildDateTime = LocalDateTime.of(" + year + ", " + month +
                ", " + day + ", " + hour + ", " + minute + ", " + second + ");");
        writer.newLine();
    }

    public void writeVersion(@NotNull final String version) throws IOException {
        writer.write("\tpublic static final String version = \"" + version + "\";");
        writer.newLine();
    }

    public void writeProductDetails(@NotNull final String productName, @Nullable String productCodeName,
                                    @Nullable String productUrl, @NotNull String component) throws IOException {
        writer.write("\tpublic static final String productName = \"" + productName + "\";");
        writer.newLine();
        if (productCodeName != null) {
            writer.write("\tpublic static final String productCodeName = \"" + productCodeName + "\";");
            writer.newLine();
        }
        if (productUrl != null) {
            writer.write("\tpublic static final String productUrl = \"" + productUrl + "\";");
            writer.newLine();
        }
        if (component != null) {
            writer.write("\tpublic static final String component = \"" + component + "\";");
            writer.newLine();
        }
    }

    public void writeAuthorDetails(@Nullable final String author, @Nullable String authorEmail) throws IOException {
        if (author != null) {
            writer.write("\tpublic static final String author = \"" + author + "\";");
            writer.newLine();
        }
        if (authorEmail != null) {
            writer.write("\tpublic static final String authorEmail = \"" + authorEmail + "\";");
            writer.newLine();
        }
    }

    public void writeCompanyDetails(@Nullable final String company, @Nullable String companyEmail) throws IOException {
        if (company != null) {
            writer.write("\tpublic static final String company = \"" + company + "\";");
            writer.newLine();
        }
        if (companyEmail != null) {
            writer.write("\tpublic static final String companyEmail = \"" + companyEmail + "\";");
            writer.newLine();
        }
    }

    public void writeTeamDetails(@Nullable final String team, @Nullable String teamEmail) throws IOException {
        if (team != null) {
            writer.write("\tpublic static final String team = \"" + team + "\";");
            writer.newLine();
        }
        if (teamEmail != null) {
            writer.write("\tpublic static final String teamEmail = \"" + teamEmail + "\";");
            writer.newLine();
        }
    }

    public void writeProjectStage(@NotNull final String projectStage) throws IOException {
        writer.write("\tpublic static final ProjectStage projectStage = ProjectStage." + projectStage + ";");
        writer.newLine();
    }

    public void writeMethod(@NotNull final String methodName) throws IOException {
        writer.newLine();
        writer.write("public String " + methodName + "() {");
        writer.newLine();
    }

    public void writeCloseBrace() throws IOException {
        writer.write('}');
        writer.newLine();
    }

    public void write(@NotNull final String txt) throws IOException {
        writer.write(txt);
        writer.newLine();
    }

    public void newline() throws IOException {
        writer.newLine();
    }

    public void close() throws IOException {
        writer.close();
    }

//    public void writeGetter(String memberName, String memberType) throws IOException {
//        String tmp = memberName.toUpperCase();
//        String getter = "get" + tmp.charAt(0) + memberName.substring(1);
//
//        writer.newLine();
//        writer.write("\tpublic ");
//        writer.write(memberType);
//        writer.write(" " + getter + "() { ");
//        writer.write("return " + memberName + "; }");
//        writer.newLine();
//    }

    public void writeProjectStageEnum() throws IOException {
        writer.newLine();
        writer.write("\tpublic enum ProjectStage { PROOF_OF_CONCEPT, DEVELOPMENT, TEST, ALPHA, BETA, PRODUCTION}");
        writer.newLine();
    }

    public void writeCopyright(@Nullable String copyright) throws IOException {
        if (copyright != null) {
            writer.write("\tpublic static final String copyright = \"" + copyright + "\";");
            writer.newLine();
        }
    }

    public void writeDescription(@Nullable String description) throws IOException {
        if (description != null) {
            writer.write("\tpublic static final String description = \"" + description + "\";");
            writer.newLine();
        }
    }

    public void writeShieldsioUrl(@Nullable String shieldsioUrl) throws IOException {
        if (shieldsioUrl != null) {
            writer.write("\tpublic static final String shieldsioUrl = \"" + shieldsioUrl + "\";");
            writer.newLine();
        }
    }

    public void writeLogoUrl(@Nullable String logoUrl) throws IOException {
        if (logoUrl != null) {
            writer.write("\tpublic static final String logoUrl = \"" + logoUrl + "\";");
            writer.newLine();
        }
    }

    public void writeGetFormattedBuildDateTime() throws IOException {
        writer.newLine();
        writer.write("\tpublic static String getFormattedBuildDateTime(String format) {");
        writer.newLine();
        writer.write("\t\tfinal DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);");
        writer.newLine();
        writer.write("\t\treturn buildDateTime.format(formatter);");
        writer.newLine();
        writer.write("\t}");
        writer.newLine();
    }
}