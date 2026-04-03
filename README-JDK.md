Required JDK for this project

This project uses Spring Boot 3.x and is configured to use Java 21 (see pom.xml property `java.version`). Spring Boot 3 requires Java 17+ (Java 21 recommended). If your machine currently uses an older JDK (for example Java 8), builds will fail with class file version errors.

Quick Windows (cmd.exe) instructions

1) GUI installer (recommended)
- Download a Temurin/Adoptium or other JDK 21 installer:
  - https://adoptium.net/temurin/releases/
- Run the installer and complete installation.

2) Chocolatey (if you have Chocolatey installed)
- Open an elevated cmd.exe (Run as Administrator) and run:

```
choco install temurin21jdk -y
```

3) Set JAVA_HOME and update PATH (cmd.exe)
- Adjust the path below to where the JDK was installed. Example default: `C:\Program Files\Java\jdk-21`.

```
setx JAVA_HOME "C:\Program Files\Java\jdk-21"
setx PATH "%PATH%;%JAVA_HOME%\bin"
```

- After running `setx` you must close and reopen any cmd.exe windows for the changes to take effect.

4) Verify Java and Maven see the new JDK

Open a new cmd.exe and run:

```
java -version
mvn -version
```

`java -version` should show a Java 17+ (preferably 21) runtime. `mvn -version` should show the same Java version under "Java version".

5) Re-run the project build

```
mvn -DskipTests package
```

If the build still fails with a class version mismatch, ensure there aren't older JDK paths earlier in your PATH and that JAVA_HOME points to the correct installation.

IDE notes (IntelliJ/Eclipse)
- In IntelliJ: File → Project Structure → Project SDK: select the installed JDK (21). Also set the project's language level accordingly.
- In Eclipse: Window → Preferences → Java → Installed JREs: add/select the new JDK.

If you want, after you've installed JDK 17/21 and re-run the build, paste the `java -version` and `mvn -version` outputs here and I'll re-run a build/check from this project to confirm everything compiles.

