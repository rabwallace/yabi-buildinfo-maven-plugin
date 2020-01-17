package com.metaconflux.buildinfo;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


//@RunWith(JUnitPlatform.class)
public class TestBuildInfoPlugin {
    private static final String PRODUCT_NAME = "This is the Product Name";
    private static final String CLASSNAME = "MyOutput.java";
    private static final String JAVA_PACKAGE = "com.text.TestPlugin";
    private static final String SRCROOT = "/test/dir";
    private static final BuildInfoMavenPlugin.ProjectStage STAGE_DEVELOPMENT = BuildInfoMavenPlugin.ProjectStage.DEVELOPMENT;
    private static final BuildInfoMavenPlugin.ProjectStage STAGE_PRODUCTION = BuildInfoMavenPlugin.ProjectStage.PRODUCTION;

    private class Plugin extends BuildInfoMavenPlugin {
    }

    @Test
    public void testPlugin_ProductName() {
        Plugin plugin = new Plugin();
        plugin.setProductName(PRODUCT_NAME);
        assertEquals(PRODUCT_NAME, plugin.getProductName());
    }

    @Test
    public void testPlugin_JavaClassname() {
        Plugin plugin = new Plugin();

//        assertEquals(BuildVersionMavenPlugin.DEFAULT_PLUGIN_DEFAULT_CLASSNAME, plugin.getJavaClassname());

        plugin.setJavaClassname("TestClassname.java");
        assertEquals("TestClassname", plugin.getJavaClassname());
    }

    @Test
    public void testPlugin_JavaPackage() {
        Plugin plugin = new Plugin();
        plugin.setJavaPackage(JAVA_PACKAGE);
        assertEquals(JAVA_PACKAGE, plugin.getJavaPackage());
    }

    @Test
    public void testPlugin_Mkdir() {
        Plugin plugin = new Plugin();

        plugin.setMkdir(true);
        assertEquals(true, plugin.isMkdir());

        plugin.setMkdir(false);
        assertEquals(false, plugin.isMkdir());
    }

    @Test
    public void testPlugin_SrcRoot() {
        Plugin plugin = new Plugin();
        plugin.setSrcRoot(SRCROOT);
        assertEquals(SRCROOT, plugin.getSrcRoot());
    }

    @Test
    public void testPlugin_ProjectStage() {
        Plugin plugin = new Plugin();

        plugin.setProjectStage(STAGE_DEVELOPMENT);
        assertEquals(STAGE_DEVELOPMENT, plugin.getProjectStage());

        plugin.setProjectStage(STAGE_PRODUCTION);
        assertEquals(STAGE_PRODUCTION, plugin.getProjectStage());
    }

    @Test
    public void testPlugin_Validate() {
        Plugin plugin = (Plugin) buildPlugin();
        try {
            plugin.validate();
        } catch (MojoExecutionException e) {
            fail();
        }
    }

    @Test
    public void testPlugin_GetPackageDirectory() {
        final String PACKAGE_NAME = "com.test.plugin.buildinfo";
        final String EXPECTED_PATH = PACKAGE_NAME.replace('.', File.separatorChar);

        Plugin plugin = (Plugin) buildPlugin();
        plugin.setJavaPackage(PACKAGE_NAME);

        final File fullPackageDirectory = plugin.getPackageDirectory();

        assertEquals(EXPECTED_PATH, fullPackageDirectory.getAbsolutePath());
    }

    @Test
    public void test_ValidateOutputDirectory() {
        String FULL_DIRECTORY = "\\tmp\\TestPlugin";
        FULL_DIRECTORY = FULL_DIRECTORY.replace('.', File.separatorChar);

        final Plugin plugin = (Plugin) buildPlugin();
        final File fullDir = new File(FULL_DIRECTORY);
        try {
            plugin.validateOutputDirectory(fullDir);
        } catch (MojoExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test_CreateBuildInfoFile() {
        String FULL_DIRECTORY = "\\tmp\\TestPlugin";
        FULL_DIRECTORY = FULL_DIRECTORY.replace('.', File.separatorChar);

        final File dir = new File(FULL_DIRECTORY);

        Plugin plugin = (Plugin) buildPlugin();

        try {
            File buildInfoFile = plugin.createBuildInfoFile(dir);
            assertNotNull(buildInfoFile);
        } catch (MojoExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test_WriteBuildInfoFile() {
        String FULL_DIRECTORY = "\\tmp\\TestPlugin";
        FULL_DIRECTORY = FULL_DIRECTORY.replace('.', File.separatorChar);

        final File dir = new File(FULL_DIRECTORY);

        Plugin plugin = (Plugin) buildPlugin();

        final File buildInfoFile;
        try {
            buildInfoFile = plugin.createBuildInfoFile(dir);
            assertNotNull(buildInfoFile);
        } catch (MojoExecutionException e) {
            fail(e.getMessage());
            return;
        }

        try {
            plugin.writeBuildInfoFile(buildInfoFile);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test_Execute() {
        final String SRC_ROOT = "\\Users\\User\\IdeaProjects\\buildinfo-maven-plugin\\src\\test\\java";
        final String PACKAGE = "com.metaconflux.testoutput";
        final String FILENAME = "BuildInfo";

        //test for previous test output (delete if found)
        final String FULL_OUTPUT_DIRECTORY = SRC_ROOT + "\\com\\metaconflux\\testoutput\\" + FILENAME + ".java";
        File buildInfoJavaFile = new File(FULL_OUTPUT_DIRECTORY);
        if (buildInfoJavaFile.exists())
            buildInfoJavaFile.delete();


        Plugin plugin = (Plugin) buildPlugin();
        plugin.setSrcRoot(SRC_ROOT);
        plugin.setJavaPackage(PACKAGE);
        plugin.setJavaClassname(FILENAME);
        plugin.setMkdir(true);

        try {
            plugin.execute();
        } catch (MojoExecutionException e) {
            fail(e.getMessage());
            return;
        }

        //Test output
        buildInfoJavaFile = new File(FULL_OUTPUT_DIRECTORY);

        assertTrue(buildInfoJavaFile.exists());
    }


    private BuildInfoMavenPlugin buildPlugin() {
        Plugin plugin = new Plugin();

        plugin.setProductName(PRODUCT_NAME);
        plugin.setSrcRoot(SRCROOT);
        plugin.setMkdir(true);
        plugin.setJavaPackage(JAVA_PACKAGE);
        plugin.setJavaClassname(CLASSNAME);
        plugin.setProjectStage(STAGE_DEVELOPMENT);

        return plugin;
    }
}