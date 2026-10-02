$ErrorActionPreference = 'Stop'
$taskRoot = Split-Path -Parent $PSScriptRoot
$taskRequired = @('app/build.gradle', 'app/src/main/AndroidManifest.xml',
    'gradle/wrapper/gradle-wrapper.jar', 'gradle/wrapper/gradle-wrapper.properties')
$taskMissing = @($taskRequired | Where-Object { -not (Test-Path -LiteralPath (Join-Path $taskRoot $_)) })
if ($taskMissing.Count -gt 0) {
    Write-Output 'Incomplete Android source tree. Missing:'
    $taskMissing | ForEach-Object { Write-Output $_ }
    exit 1
}
Write-Output 'Required module and wrapper files exist; this does not verify an Android build.'
