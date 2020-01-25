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

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


//@RunWith(JUnitPlatform.class)
public class TestYabiBuildInfoPlugin {
    private static final String PRODUCT_NAME = "This is the Product Name";
    private static final String CLASSNAME = "MyOutput.java";
    private static final String JAVA_PACKAGE = "com.text.TestPlugin";
    private static final String SRCROOT = "/test/dir";
    private static final String VERSION = "v1.0";
    private static final YabiBuildInfoMavenPlugin.ProjectStage STAGE_DEVELOPMENT = YabiBuildInfoMavenPlugin.ProjectStage.DEVELOPMENT;
    private static final YabiBuildInfoMavenPlugin.ProjectStage STAGE_PRODUCTION = YabiBuildInfoMavenPlugin.ProjectStage.PRODUCTION;

    private class Plugin extends YabiBuildInfoMavenPlugin {
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
        final String EXPECTED_PATH = "C:\\test\\dir\\com\\test\\plugin\\buildinfo";

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
        plugin.setOverwrite(true);

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
        plugin.setOverwrite(true);

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
    public void test_WriteBuildInfoFile_overwriteFail() {
        String FULL_DIRECTORY = "\\tmp\\TestPlugin";
        FULL_DIRECTORY = FULL_DIRECTORY.replace('.', File.separatorChar);

        final File dir = new File(FULL_DIRECTORY);

        Plugin plugin = (Plugin) buildPlugin();
        plugin.setOverwrite(true);

        //create a fresh copy first (overwrite if necessary)
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


        //all should be ok so far
        //now try to repeat but do not allow fie overwrite, this *should* throw an exception
        plugin.setOverwrite(false);

        //create a fresh copy first (overwrite if necessary)
        final File buildInfoFile2;
        try {
            buildInfoFile2 = plugin.createBuildInfoFile(dir);
            fail("ERROR: exception should have been thrown");
//            assertNotNull(buildInfoFile2);
        } catch (MojoExecutionException e) {
//            fail(e.getMessage());
//            return;
        }


        //try to create a new copy but do not overwrite - force fail
//        plugin.setOverwrite(false);

//        try {
//            plugin.writeBuildInfoFile(buildInfoFile2);
//            fail("Should not have got here.");
//        } catch (IOException e) {
////            fail(e.getMessage());
//        }


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


    private YabiBuildInfoMavenPlugin buildPlugin() {
        Plugin plugin = new Plugin();

        plugin.setProductName(PRODUCT_NAME);
        plugin.setSrcRoot(SRCROOT);
        plugin.setMkdir(true);
        plugin.setJavaPackage(JAVA_PACKAGE);
        plugin.setJavaClassname(CLASSNAME);
        plugin.setProjectStage(STAGE_DEVELOPMENT);
        plugin.setVersion(VERSION);

        return plugin;
    }
}