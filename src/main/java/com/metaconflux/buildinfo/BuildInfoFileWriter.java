package com.metaconflux.buildinfo;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BuildInfoFileWriter {
    private BufferedWriter writer;

//    private String TAB = "\t";
//    private String indent = "";
    private String className;

    public BuildInfoFileWriter(@NotNull final File javaFile, @NotNull final String packageName) throws IOException {
        writer = new BufferedWriter(new FileWriter(javaFile));
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
        writer.write("\tpublic " + className + "() {");
        writer.newLine();

        writer.write("\t\tbuildDateTime = LocalDateTime.of(year, month, day, hour, minute, second);");
        writer.newLine();

        writer.write("\t}");
        writer.newLine();
    }

    public void writeBuildDateTimeVariables(@NotNull final LocalDateTime buildTime) throws IOException {
        writer.write("\tprivate final int year=" + buildTime.getYear() + ';');
        writer.newLine();
        writer.write("\tprivate final int month=" + buildTime.getMonthValue() + ';');
        writer.newLine();
        writer.write("\tprivate final int day=" + buildTime.getDayOfMonth() + ';');
        writer.newLine();
        writer.write("\tprivate final int hour=" + buildTime.getHour() + ';');
        writer.newLine();
        writer.write("\tprivate final int minute=" + buildTime.getMinute() + ';');
        writer.newLine();
        writer.write("\tprivate final int second=" + buildTime.getSecond() + ';');
        writer.newLine();
        writer.write("\tprivate final LocalDateTime buildDateTime;");
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

    public void writeGetBuildDate() throws IOException {
        writer.newLine();
        writer.write("\tpublic LocalDateTime getBuildDateTime() { return buildDateTime; }");
        writer.newLine();
    }
}
