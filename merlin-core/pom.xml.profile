<!--
  Licensed to the Apache Software Foundation (ASF) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The ASF licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  -->

<!-- pom for DataCommons , initinally contains bundle nd util files -->

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<scm>
		<connection>scm:git:git://github.corp.inmobi.com/platform/conduit</connection>
		<url>https://github.corp.inmobi.com/platform/conduit</url>
		<developerConnection>scm:git:git@github.corp.inmobi.com/platform/conduit.git</developerConnection>
	</scm>

	<parent>
		<groupId>com.inmobi</groupId>
		<artifactId>inmobi-master-pom</artifactId>
		<version>1.0.1</version>
	</parent>

	<groupId>com.inmobi.qa</groupId>
	<artifactId>MerlinHelper</artifactId>
	<version>0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>MerlinHelper-0.1</name>
	<url>http://maven.apache.org</url>

	<repositories>
		<repository>
			<id>releases</id>
			<name>Releases</name>
			<url>http://maven.scm.corp.inmobi.com/content/repositories/releases/</url>
		</repository>
	</repositories>
	<profiles>
		<profile>
			<activation>
				<property>
					<name>hadoop</name>
					<value>one</value>
				</property>
			</activation>
			<id>hadoop-1</id>
			<dependencies>

				<dependency>
					<artifactId>hadoop-core</artifactId>
					<groupId>org.apache.hadoop</groupId>
					<version>0.20.2-cdh3u0</version>
					<exclusions>
						<exclusion>
							<groupId>hsqldb</groupId>
							<artifactId>hsqldb</artifactId>
						</exclusion>
					</exclusions>
				</dependency>

				<dependency>
					<groupId>org.apache.hbase</groupId>
					<artifactId>hbase</artifactId>
					<version>0.90.1-cdh3u0</version>
					<exclusions>
						<exclusion>
							<groupId>org.apache.thrift</groupId>
							<artifactId>thrift</artifactId>
						</exclusion>
					</exclusions>
				</dependency>

			</dependencies>
		</profile>

		<profile>
			<activation>
				<property>
					<name>hadoop</name>
					<value>two</value>
				</property>
			</activation>
			<id>hadoop-2</id>
			<dependencies>
				<dependency>
					<artifactId>hadoop-core</artifactId>
					<groupId>org.apache.hadoop</groupId>
					<version>2.0.0-mr1-cdh4.2.0</version>
					<exclusions>
						<exclusion>
							<groupId>hsqldb</groupId>
							<artifactId>hsqldb</artifactId>
						</exclusion>
					</exclusions>
				</dependency>

				<dependency>
					<groupId>org.apache.hadoop</groupId>
					<artifactId>hadoop-common</artifactId>
					<version>2.0.0-cdh4.2.0</version>
				</dependency>

				<dependency>
					<groupId>org.apache.hadoop</groupId>
					<artifactId>hadoop-hdfs</artifactId>
					<version>2.0.0-cdh4.2.0</version>
				</dependency>

				<dependency>
					<groupId>org.apache.hbase</groupId>
					<artifactId>hbase</artifactId>
					<version>0.94.6-cdh4.3.0</version>
					<exclusions>
						<exclusion>
							<groupId>org.apache.thrift</groupId>
							<artifactId>thrift</artifactId>
						</exclusion>
						<exclusion>
							<groupId>org.jruby</groupId>
							<artifactId>jruby-complete</artifactId>
						</exclusion>
					</exclusions>
				</dependency>

			</dependencies>
		</profile>
	</profiles>
	
	<dependencies>

		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>1.5.8</version>
		</dependency>

		<dependency>
			<groupId>org.testng</groupId>
			<artifactId>testng</artifactId>
			<version>6.0.1</version>
		</dependency>

		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpclient</artifactId>
			<version>4.1.2</version>
		</dependency>

		<dependency>
			<groupId>org.apache.activemq</groupId>
			<artifactId>activemq-core</artifactId>
			<version>5.4.3</version>
		</dependency>

		<dependency>
			<groupId>com.jcraft</groupId>
			<artifactId>jsch</artifactId>
			<version>0.1.44-1</version>
		</dependency>

		<dependency>
			<groupId>net.sf.json-lib</groupId>
			<artifactId>json-lib</artifactId>
			<version>2.4</version>
			<classifier>jdk15</classifier>
		</dependency>

		<dependency>
			<groupId>org.codehaus.jackson</groupId>
			<artifactId>jackson-mapper-asl</artifactId>
			<version>1.9.5</version>
		</dependency>

		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.1</version>
			<scope>compile</scope>
		</dependency>

		<dependency>
			<groupId>net.sf.dozer</groupId>
			<artifactId>dozer</artifactId>
			<version>5.2.0</version>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.0.8</version>
		</dependency>

		<dependency>
			<groupId>xmlunit</groupId>
			<artifactId>xmlunit</artifactId>
			<version>1.3</version>
		</dependency>

		<dependency>
			<groupId>org.apache.oozie</groupId>
			<artifactId>oozie-client</artifactId>
			<version>3.1.4</version>
		</dependency>

		<dependency>
			<groupId>joda-time</groupId>
			<artifactId>joda-time</artifactId>
			<version>2.0</version>
		</dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>0.11.2</version>
		</dependency>

		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>2.4</version>
		</dependency>
	</dependencies>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>2.3.2</version>
				<configuration>
					<source>1.6</source>
					<target>1.6</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-source-plugin</artifactId>
				<executions>
					<execution>
						<goals>
							<goal>jar</goal>
							<goal>test-jar</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<executions>
					<execution>
						<goals>
							<goal>test-jar</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<artifactId>maven-surefire-plugin</artifactId>
				<configuration>
					<argLine>-Denvironment=${config.file}</argLine>
					<parallel>methods</parallel>
					<threadCount>1</threadCount>
					<source>1.6</source>
					<target>1.6</target>
					<verbose>true</verbose>
					<groups>${testng.groups}</groups>
					<excludedGroups>${testng.exclude.groups}</excludedGroups>
				</configuration>
			</plugin>
		</plugins>

	</build>

</project>
  
