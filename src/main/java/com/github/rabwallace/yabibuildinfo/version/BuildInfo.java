/***********************************************************************************************
* Generated by yabi-buildinfo-maven-plugin (version: 1.01)
* Built: 17/05/2020 (19:09:12)
***********************************************************************************************/
package com.github.rabwallace.yabibuildinfo.version;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BuildInfo {

	public enum ProjectStage { PROOF_OF_CONCEPT, DEVELOPMENT, TEST, ALPHA, BETA, PRODUCTION}
	public static final LocalDateTime buildDateTime = LocalDateTime.of(2020, 5, 17, 19, 9, 12);
	public static final String version = "1.02";
	public static final String productName = "yabi-buildinfo-maven-plugin";
	public static final String productUrl = "https://github.com/rabwallace/yabi-buildinfo-maven-plugin";
	public static final String author = "Rab Wallace";
	public static final String authorEmail = "javarab@yahoo.com";
	public static final ProjectStage projectStage = ProjectStage.PRODUCTION;
	public static final String copyright = "Copyright 2020 Rab Wallace, GNU General Public License GPLv3 or later";
	public static final String description = "A maven plugin that generates build-time information.";
	public static final String shieldsioUrl = "https://img.shields.io/badge/yabi--buildinfo-v1.0-success";

	public BuildInfo(){}

	public static void main(String[] args) {
		System.out.println("Project: yabi-buildinfo-maven-plugin");
		System.out.println("Stage: PRODUCTION");
		System.out.println("Version: 1.02");
		System.out.println("Build time: 17/05/2020 (19:09:12)");
	}

	public static String getFormattedBuildDateTime(String format) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return buildDateTime.format(formatter);
	}
}
