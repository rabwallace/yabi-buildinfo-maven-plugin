package com.metaconflux.buildinfo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Goal which touches a timestamp file.
 *
 *
 * @ --goal builddate
 * @ --phase process-sources
 */
@Mojo(name = "buildinfo")
public class BuildInfoMavenPlugin extends AbstractMojo {
    private static final String PLUGIN_NAME = "buildinfo-maven-plugin";
    private static final String PLUGIN_VERSION = "0.1";
    protected static final String DEFAULT_PLUGIN_DEFAULT_CLASSNAME = "BuildInfo";

    public enum ProjectStage {
        PROOF_OF_CONCEPT,
        DEVELOPMENT,
        TEST,
        ALPHA,
        BETA,
        PRODUCTION
    }

    private final Log log = getLog();



    /*
    REQUIRED
    --------------------
    productName            string
    javaPackage            string
    srcRoot                string

    OPTIONAL
    --------------------
    javaClassname          string (default: BuildInfo)
    projectStage           enum (default: DEVELOPMENT)
    mkdir                  boolean (default: true)


    Version 2
    --------------------
    projectCodeName         string
    copyright               string
    logoUrl                 string
    shieldsioBadgeUrl       string
    author                  string
    authorEmail             string
    team                    string
    teamEmail               string
    companyName             string
    companyEmail            string
     */

    @Parameter(property = "productName")
    private String productName;

    @Parameter(property = "javaClassname", defaultValue = DEFAULT_PLUGIN_DEFAULT_CLASSNAME)
    private String javaClassname;

    @Parameter(property = "javaPackage")
    private String javaPackage;

    @Parameter(property = "srcRoot")
    private String srcRoot;

    @Parameter(property = "mkdir", defaultValue = "true")
    private boolean mkdir;

    @Parameter(property = "projectStage", defaultValue = "DEVELOPMENT")
    private ProjectStage projectStage;


    private LocalDateTime buildTime = LocalDateTime.now();

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getJavaClassname() {
        return javaClassname;
    }

    public void setJavaClassname(String javaClassname) {
        if (javaClassname.endsWith(".java"))
            javaClassname = javaClassname.replace(".java", "");

        this.javaClassname = javaClassname;
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }

    public String getSrcRoot() {
        return srcRoot;
    }

    public void setSrcRoot(String srcRoot) {
        this.srcRoot = srcRoot;
    }

    public boolean isMkdir() {
        return mkdir;
    }

    public void setMkdir(boolean mkdir) {
        this.mkdir = mkdir;
    }

    public ProjectStage getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(ProjectStage projectStage) {
        this.projectStage = projectStage;
    }

    public LocalDateTime getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(LocalDateTime buildTime) {
        this.buildTime = buildTime;
    }

    public void execute() throws MojoExecutionException {
        displayGreeting();

        setJavaClassname(javaClassname);
        validate();
        final File fullPackageDirectory = getPackageDirectory();
        validateOutputDirectory(fullPackageDirectory);

        final File packageDirectory = getPackageDirectory();

        final File outFile = createBuildInfoFile(packageDirectory);


        try {
            writeBuildInfoFile(outFile);
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage());
        }

    }

    private void displayGreeting() {
        final String dashes = "----------------------------------------------------------------------------";
        log.info(dashes);
        log.info(PLUGIN_NAME + "    version: " + PLUGIN_VERSION);
        log.info(dashes);
    }

    protected void validate() throws MojoExecutionException {
        if (productName == null || productName.length() == 0) {
            throw new MojoExecutionException("FAIL: productName not specified");
        }

        if (javaPackage == null || javaPackage.length() == 0) {
            throw new MojoExecutionException("FAIL: javaPackage not specified");
        }

        if (srcRoot == null) {
            throw new MojoExecutionException("FAIL: srcRoot not specified");
        }
    }

    protected File getPackageDirectory() {
        String packageDirectory = javaPackage.replace(".", File.separator);

        //TODO check & remove leading separator(s)
//        if (srcRoot.startsWith(File.pathSeparator)) {
//            srcRoot = srcRoot.rem substring(1)
//        }

        if (!srcRoot.endsWith(File.separator))
            srcRoot += File.separator;

        String fullDirectory = srcRoot + packageDirectory;

        File packageDir = new File(fullDirectory);
        return packageDir;

//        return packageDirectory;
    }

    protected void validateOutputDirectory(@NotNull final File pkgDir) throws MojoExecutionException {

//        final File pkgDir = new File(fullPackageDir);

        if (!pkgDir.exists()) {
            if (!mkdir)
                throw new MojoExecutionException("FAIL: srcRoot not found, either create directory, or set mkdir to true");
            else {
                if (!pkgDir.mkdirs()) {
                    throw new MojoExecutionException("FAIL: error occurred creating package directory");
                }
            }
        }

        if (!pkgDir.isDirectory()) {
            throw new MojoExecutionException("FAIL: srcRoot not a directory");
        }

        if (!pkgDir.canWrite()) {
            throw new MojoExecutionException("FAIL: srcRoot not writable");
        }

//        if (mkdir && !srcRoot.mkdir()) {
//            throw new MojoExecutionException("FAIL: srcRoot can't create directory");
//        }

    }

    protected File createBuildInfoFile(@NotNull final File pkgDir) throws MojoExecutionException {

        File outFile = new File(pkgDir, javaClassname + ".java");
        try {
            if (!outFile.createNewFile()) {
                throw new MojoExecutionException("FAIL: srcRoot can't create file");
            }
        } catch (IOException e) {
            throw new MojoExecutionException("FAIL: srcRoot can't create file. " + e.getMessage());
        }

        return outFile;
    }

//    protected void writeBuildInfoFile(@NotNull final File outFile) throws IOException {
//        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
//
//        try {
//            writer.write("package " + javaPackage + ';');
//            writer.newLine();
//
//            writer.write("import java.time.LocalDateTime;");
//            writer.newLine();
//
//            writer.write("public class " + javaClassname + " {");
//            writer.newLine();
//
//            writer.write("public " + javaClassname + "() {}");
//            writer.newLine();
//            writer.newLine();
//
//            writer.write("public String getBuildDate() {");
//            writer.newLine();
//            writer.write("return " + buildTime.toString() + ';');
//            writer.newLine();
//
//            writer.write("}");
//            writer.newLine();
//
//            writer.write("}");
//        } finally {
//            writer.close();
//        }
//    }

    protected void writeBuildInfoFile(@NotNull final File outFile) throws IOException {
        BuildInfoFileWriter bif = new BuildInfoFileWriter(outFile, javaPackage);

        try {
            bif.writeImport("java.time.LocalDateTime");

            bif.writeClass(javaClassname);

            bif.writeBuildDateTimeVariables(buildTime);
//            bif.write("\tprivate int year=" + buildTime.getYear() + ';');
//            bif.write("\tprivate int month=" + buildTime.getMonthValue() + ';');
//            bif.write("\tprivate int day=" + buildTime.getDayOfMonth() + ';');
//            bif.write("\tprivate int hour=" + buildTime.getHour() + ';');
//            bif.write("\tprivate int minute=" + buildTime.getMinute() + ';');
//            bif.write("\tprivate int second=" + buildTime.getSecond() + ';');
//            bif.write("\tprivate LocalDateTime buildDateTime;");

            bif.writeConstructor();

            bif.writeGetBuildDate();

            bif.writeCloseBrace();
        } finally {
            bif.close();
        }
    }

}