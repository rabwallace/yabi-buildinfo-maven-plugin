/***********************************************************************************************
* Generated by yabi-buildinfo-maven-plugin (version: 1.0)
* Built: 25/01/2020 (16:06:26)
***********************************************************************************************/
package com.metaconflux.yabibuildinfo.version;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BuildInfo {

	public enum ProjectStage { PROOF_OF_CONCEPT, DEVELOPMENT, TEST, ALPHA, BETA, PRODUCTION}
	public static final LocalDateTime buildDateTime = LocalDateTime.of(2020, 1, 25, 16, 6, 26);
	public static final String version = "1.0";
	public static final String productName = "yabi-buildinfo-maven-plugin";
	public static final String productUrl = "https://github.com/rabwallace/yabi-buildinfo-maven-plugin";
	public static final String author = "Rab Wallace";
	public static final String authorEmail = "javarab@yahoo.com";
	public static final ProjectStage projectStage = ProjectStage.TEST;
	public static final String copyright = "Copyright 2020 Rab Wallace, GNU General Public License GPLv3 or later";
	public static final String description = "A maven plugin that generates build-time information.";
	public static final String shieldsioUrl = "https://img.shields.io/badge/yabi--buildinfo-v1.0-success";

	public BuildInfo(){}

	public String getProductName() { return productName; }

	public LocalDateTime getBuildDateTime() { return buildDateTime; }

	public String getVersion() { return version; }

	public ProjectStage getProjectStage() { return projectStage; }

	public static String getFormattedBuildDateTime(String format) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return buildDateTime.format(formatter);
	}
}
