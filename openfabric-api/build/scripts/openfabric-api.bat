@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  openfabric-api startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and OPENFABRIC_API_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\openfabric-api-1.0.0-SNAPSHOT-plain.jar;%APP_HOME%\lib\elide-spring-boot-starter-4.8.0.jar;%APP_HOME%\lib\spring-dotenv-2.5.4.jar;%APP_HOME%\lib\postgresql-42.5.1.jar;%APP_HOME%\lib\spring-boot-starter-thymeleaf-2.7.5.jar;%APP_HOME%\lib\thymeleaf-spring5-3.0.15.RELEASE.jar;%APP_HOME%\lib\thymeleaf-extras-java8time-3.0.4.RELEASE.jar;%APP_HOME%\lib\thymeleaf-3.0.15.RELEASE.jar;%APP_HOME%\lib\ognl-3.1.26.jar;%APP_HOME%\lib\javassist-3.22.0-GA.jar;%APP_HOME%\lib\liquibase-core-4.17.2.jar;%APP_HOME%\lib\javax.annotation-api-1.3.2.jar;%APP_HOME%\lib\springfox-boot-starter-3.0.0.jar;%APP_HOME%\lib\spring-boot-starter-web-2.7.5.jar;%APP_HOME%\lib\spring-boot-starter-mail-2.7.5.jar;%APP_HOME%\lib\spring-boot-starter-data-jpa-2.7.5.jar;%APP_HOME%\lib\spring-boot-starter-security-2.7.5.jar;%APP_HOME%\lib\elide-graphql-4.8.0.jar;%APP_HOME%\lib\elide-datastore-jpa-4.8.0.jar;%APP_HOME%\lib\elide-swagger-4.8.0.jar;%APP_HOME%\lib\elide-datastore-hibernate-4.8.0.jar;%APP_HOME%\lib\elide-core-4.8.0.jar;%APP_HOME%\lib\elide-annotations-4.8.0.jar;%APP_HOME%\lib\elide-spring-boot-autoconfigure-4.8.0.jar;%APP_HOME%\lib\spring-boot-starter-json-2.7.5.jar;%APP_HOME%\lib\spring-boot-starter-aop-2.7.5.jar;%APP_HOME%\lib\spring-boot-starter-jdbc-2.7.5.jar;%APP_HOME%\lib\spring-boot-starter-2.7.5.jar;%APP_HOME%\lib\swagger-core-1.6.2.jar;%APP_HOME%\lib\elide-test-helpers-4.8.0.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.13.4.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.13.4.jar;%APP_HOME%\lib\jackson-module-parameter-names-2.13.4.jar;%APP_HOME%\lib\jackson-module-jaxb-annotations-2.13.4.jar;%APP_HOME%\lib\jackson-databind-2.13.4.2.jar;%APP_HOME%\lib\springfox-oas-3.0.0.jar;%APP_HOME%\lib\swagger-models-2.1.2.jar;%APP_HOME%\lib\springfox-swagger2-3.0.0.jar;%APP_HOME%\lib\springfox-swagger-common-3.0.0.jar;%APP_HOME%\lib\swagger-models-1.6.2.jar;%APP_HOME%\lib\jackson-annotations-2.13.4.jar;%APP_HOME%\lib\jackson-core-2.13.4.jar;%APP_HOME%\lib\jackson-dataformat-yaml-2.13.4.jar;%APP_HOME%\lib\snakeyaml-1.33.jar;%APP_HOME%\lib\dotenv-java-2.2.3.jar;%APP_HOME%\lib\guava-30.1-jre.jar;%APP_HOME%\lib\checker-qual-3.5.0.jar;%APP_HOME%\lib\jaxb-api-2.3.1.jar;%APP_HOME%\lib\opencsv-5.7.1.jar;%APP_HOME%\lib\springfox-data-rest-3.0.0.jar;%APP_HOME%\lib\springfox-bean-validators-3.0.0.jar;%APP_HOME%\lib\springfox-swagger-ui-3.0.0.jar;%APP_HOME%\lib\hibernate-core-5.6.12.Final.jar;%APP_HOME%\lib\springfox-spring-webmvc-3.0.0.jar;%APP_HOME%\lib\springfox-spring-webflux-3.0.0.jar;%APP_HOME%\lib\springfox-spring-web-3.0.0.jar;%APP_HOME%\lib\springfox-schema-3.0.0.jar;%APP_HOME%\lib\springfox-spi-3.0.0.jar;%APP_HOME%\lib\springfox-core-3.0.0.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\spring-plugin-metadata-2.0.0.RELEASE.jar;%APP_HOME%\lib\spring-plugin-core-2.0.0.RELEASE.jar;%APP_HOME%\lib\spring-data-jpa-2.7.5.jar;%APP_HOME%\lib\graphql-java-6.0.jar;%APP_HOME%\lib\HikariCP-4.0.3.jar;%APP_HOME%\lib\spring-data-commons-2.7.5.jar;%APP_HOME%\lib\spring-boot-starter-logging-2.7.5.jar;%APP_HOME%\lib\logback-classic-1.2.11.jar;%APP_HOME%\lib\log4j-to-slf4j-2.17.2.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.36.jar;%APP_HOME%\lib\slf4j-api-1.7.36.jar;%APP_HOME%\lib\spring-boot-starter-tomcat-2.7.5.jar;%APP_HOME%\lib\spring-webmvc-5.3.23.jar;%APP_HOME%\lib\spring-security-web-5.7.4.jar;%APP_HOME%\lib\spring-web-5.3.23.jar;%APP_HOME%\lib\spring-context-support-5.3.23.jar;%APP_HOME%\lib\jakarta.mail-1.6.7.jar;%APP_HOME%\lib\jakarta.transaction-api-1.3.3.jar;%APP_HOME%\lib\jakarta.persistence-api-2.2.3.jar;%APP_HOME%\lib\spring-aspects-5.3.23.jar;%APP_HOME%\lib\spring-security-config-5.7.4.jar;%APP_HOME%\lib\spring-boot-autoconfigure-2.7.5.jar;%APP_HOME%\lib\spring-boot-2.7.5.jar;%APP_HOME%\lib\spring-security-core-5.7.4.jar;%APP_HOME%\lib\spring-context-5.3.23.jar;%APP_HOME%\lib\spring-aop-5.3.23.jar;%APP_HOME%\lib\antlr4-runtime-4.9.1.jar;%APP_HOME%\lib\javax.ws.rs-api-2.1.1.jar;%APP_HOME%\lib\commons-text-1.10.0.jar;%APP_HOME%\lib\commons-lang3-3.12.0.jar;%APP_HOME%\lib\commons-collections4-4.4.jar;%APP_HOME%\lib\commons-beanutils-1.9.4.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\rsql-parser-2.1.0.jar;%APP_HOME%\lib\rxjava-2.2.20.jar;%APP_HOME%\lib\validation-api-2.0.1.Final.jar;%APP_HOME%\lib\juel-impl-2.2.7.jar;%APP_HOME%\lib\juel-api-2.2.7.jar;%APP_HOME%\lib\jansi-2.0.1.jar;%APP_HOME%\lib\classgraph-4.8.90.jar;%APP_HOME%\lib\encoder-1.2.3.jar;%APP_HOME%\lib\gson-2.8.6.jar;%APP_HOME%\lib\jakarta.xml.bind-api-2.3.3.jar;%APP_HOME%\lib\jakarta.activation-api-1.2.2.jar;%APP_HOME%\lib\javax.activation-api-1.2.0.jar;%APP_HOME%\lib\swagger-annotations-2.1.2.jar;%APP_HOME%\lib\mapstruct-1.3.1.Final.jar;%APP_HOME%\lib\swagger-annotations-1.6.2.jar;%APP_HOME%\lib\spring-orm-5.3.23.jar;%APP_HOME%\lib\spring-jdbc-5.3.23.jar;%APP_HOME%\lib\spring-tx-5.3.23.jar;%APP_HOME%\lib\spring-beans-5.3.23.jar;%APP_HOME%\lib\jakarta.annotation-api-1.3.5.jar;%APP_HOME%\lib\spring-expression-5.3.23.jar;%APP_HOME%\lib\spring-core-5.3.23.jar;%APP_HOME%\lib\tomcat-embed-websocket-9.0.68.jar;%APP_HOME%\lib\tomcat-embed-core-9.0.68.jar;%APP_HOME%\lib\tomcat-embed-el-9.0.68.jar;%APP_HOME%\lib\jakarta.activation-1.2.1.jar;%APP_HOME%\lib\aspectjweaver-1.9.7.jar;%APP_HOME%\lib\hibernate-commons-annotations-5.1.2.Final.jar;%APP_HOME%\lib\jboss-logging-3.4.3.Final.jar;%APP_HOME%\lib\byte-buddy-1.12.9.jar;%APP_HOME%\lib\antlr-2.7.7.jar;%APP_HOME%\lib\jandex-2.4.2.Final.jar;%APP_HOME%\lib\jaxb-runtime-2.3.1.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\error_prone_annotations-2.3.4.jar;%APP_HOME%\lib\j2objc-annotations-1.3.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\commons-collections-3.2.2.jar;%APP_HOME%\lib\reactive-streams-1.0.3.jar;%APP_HOME%\lib\java-dataloader-2.0.1.jar;%APP_HOME%\lib\spring-jcl-5.3.23.jar;%APP_HOME%\lib\txw2-2.3.1.jar;%APP_HOME%\lib\istack-commons-runtime-3.0.7.jar;%APP_HOME%\lib\stax-ex-1.8.jar;%APP_HOME%\lib\FastInfoset-1.2.15.jar;%APP_HOME%\lib\spring-security-crypto-5.7.4.jar;%APP_HOME%\lib\attoparser-2.0.5.RELEASE.jar;%APP_HOME%\lib\unbescape-1.1.6.RELEASE.jar;%APP_HOME%\lib\logback-core-1.2.11.jar;%APP_HOME%\lib\log4j-api-2.17.2.jar


@rem Execute openfabric-api
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %OPENFABRIC_API_OPTS%  -classpath "%CLASSPATH%" ai.openfabric.api.Application %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable OPENFABRIC_API_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%OPENFABRIC_API_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
