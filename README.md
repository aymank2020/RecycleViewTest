# RecycleViewTest

This checkout is an incomplete Android project skeleton. `settings.gradle` includes
`:app`, but the repository has no `app/` module or Android sources. The Gradle launcher
scripts are present without `gradle/wrapper/gradle-wrapper.jar` and
`gradle/wrapper/gradle-wrapper.properties`. The current tree cannot build an APK.

Run `powershell -File scripts/check-project.ps1` for a reproducible structural check.
It exits with code 1 and lists required missing paths. It does not change the project.

Recovery plan:

1. Recover the original `app/` module and Gradle wrapper from a known source revision
   or backup, including package name, resources and license attribution.
2. Use the recovered wrapper's documented Java and Android SDK requirements; keep
   `local.properties` on the local machine, outside source control.
3. Build the recovered app, then verify RecyclerView empty/data/scroll and rotation
   behavior through its launcher activity before adding new features.

No application has been invented in place of the missing source.
See the [Gradle Wrapper guide](https://docs.gradle.org/current/userguide/gradle_wrapper.html).
