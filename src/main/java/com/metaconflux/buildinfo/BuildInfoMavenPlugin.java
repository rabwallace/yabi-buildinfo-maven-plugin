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
 * Goal which builds a java class file containing build-time information that is made available at runtime.
 *
 * @phase generate-sources
 * @--goal buildinfo
 * @author Rab Wallace
 */
@Mojo(name = "buildinfo")
public class BuildInfoMavenPlugin extends AbstractMojo {
    private static final String PLUGIN_NAME = "buildinfo-maven-plugin";
    private static final String PLUGIN_VERSION = "0.1";
    protected static final String DEFAULT_PLUGIN_DEFAULT_CLASSNAME = "BuildInfo";

    public static enum ProjectStage {
        PROOF_OF_CONCEPT,
        DEVELOPMENT,
        TEST,
        ALPHA,
        BETA,
        PRODUCTION
    }

    private final Log log = getLog();


    @Parameter(property = "overwrite", defaultValue = "true")
    private boolean overwrite;

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




    @Parameter(property = "productName")
    private String productName;

    @Parameter(property = "productCodeName")
    private String productCodeName;

    @Parameter(property = "productUrl")
    private String productUrl;

    @Parameter(property = "version")
    private String version;

    @Parameter(property = "copyright")
    private String copyright;

    @Parameter(property = "author")
    private String author;

    @Parameter(property = "authorEmail")
    private String authorEmail;

    @Parameter(property = "company")
    private String company;

    @Parameter(property = "companyEmail")
    private String companyEmail;

    @Parameter(property = "team")
    private String team;

    @Parameter(property = "teamEmail")
    private String teamEmail;

    @Parameter(property = "shieldsioUrl")
    private String shieldsioUrl;



    private LocalDateTime buildTime = LocalDateTime.now();

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
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

    public String getProductCodeName() {
        return productCodeName;
    }

    public void setProductCodeName(String productCodeName) {
        this.productCodeName = productCodeName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getShieldsioUrl() {
        return shieldsioUrl;
    }

    public void setShieldsioUrl(String shieldsioUrl) {
        this.shieldsioUrl = shieldsioUrl;
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

        if (outFile.exists()) {
            if (!overwrite)
                throw new MojoExecutionException("FAIL: cannot overwrite " + javaClassname + ", delete file or set <overwrite>true</overwrite>");
            else
                outFile.delete();
        }

        try {
            if (!outFile.createNewFile()) {
                throw new MojoExecutionException("FAIL: srcRoot can't create file");
            }
        } catch (IOException e) {
            throw new MojoExecutionException("FAIL: srcRoot can't create file. " + e.getMessage());
        }

        return outFile;
    }

    protected void writeBuildInfoFile(@NotNull final File outFile) throws IOException {
        BuildInfoFileWriter bif = new BuildInfoFileWriter(outFile, javaPackage, buildTime);

        try {
            bif.writeImport("java.time.LocalDateTime");

            bif.writeClass(javaClassname);
            bif.writeProjectStageEnum();
            bif.writeBuildDateTimeVariables();
            bif.writeVersion(version);
            bif.writeProductDetails(productName, productCodeName, productUrl);
            bif.writeCompanyDetails(company, companyEmail);
            bif.writeTeamDetails(team, teamEmail);
            bif.writeAuthorDetails(author, authorEmail);
            bif.writeProjectStage(projectStage.toString());
            bif.writeCopyright(copyright);
            bif.writeShieldsioUrl(shieldsioUrl);
            bif.writeConstructor();
            bif.writeGetter("productName", "String");
            bif.writeGetter("buildDateTime", "LocalDateTime");
            bif.writeGetter("version", "String");
            bif.writeGetter("projectStage", "ProjectStage");
            bif.writeCloseBrace();
        } finally {
            bif.close();
        }
    }
}