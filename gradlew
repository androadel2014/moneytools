#!/bin/sh
# Gradle wrapper starter for MoneyTools (Gradle 8.13).
# Uses gradle/wrapper/gradle-wrapper.jar when present; otherwise downloads Gradle 8.13 once and runs it.
APP_HOME=$(cd "$(dirname "$0")" && pwd)
if [ -n "$JAVA_HOME" ]; then JAVA_EXE="$JAVA_HOME/bin/java"; else JAVA_EXE=java; fi
if [ -f "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" ]; then
 exec "$JAVA_EXE" -Xmx64m -Xms64m -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
fi
GRADLE_USER_HOME=${GRADLE_USER_HOME:-$HOME/.gradle}
DIST_DIR="$GRADLE_USER_HOME/wrapper/dists/gradle-8.13-bin"
if [ ! -x "$DIST_DIR/gradle-8.13/bin/gradle" ]; then
 mkdir -p "$DIST_DIR"
 curl -L -o "$DIST_DIR/gradle-8.13-bin.zip" https://services.gradle.org/distributions/gradle-8.13-bin.zip
 unzip -q -o "$DIST_DIR/gradle-8.13-bin.zip" -d "$DIST_DIR"
fi
exec "$DIST_DIR/gradle-8.13/bin/gradle" "$@"
