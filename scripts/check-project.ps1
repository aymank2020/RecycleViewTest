$ErrorActionPreference = 'Stop'
$taskRoot = Split-Path -Parent $PSScriptRoot
$taskJava = 'app/src/main/java/net/aymanx/examples/recycleview/'
$taskRequired = @('app/build.gradle', 'app/src/main/AndroidManifest.xml',
    'gradle/wrapper/gradle-wrapper.jar', 'gradle/wrapper/gradle-wrapper.properties',
    ($taskJava + 'MainActivity.java'), ($taskJava + 'PhotoAdapter.java'),
    ($taskJava + 'ShapeTileView.java'), ($taskJava + 'GalleryState.java'),
    ($taskJava + 'PhotoCatalog.java'), 'app/src/main/res/layout/activity_main.xml',
    'app/src/main/res/layout/item_photo.xml')
$taskMissing = @($taskRequired | Where-Object { -not (Test-Path -LiteralPath (Join-Path $taskRoot $_) -PathType Leaf) })
if ($taskMissing.Count -gt 0) {
    Write-Output 'Incomplete Android source tree. Missing:'
    $taskMissing | ForEach-Object { Write-Output $_ }
    exit 1
}
Write-Output 'Required module and wrapper files exist; this does not verify an Android build.'
