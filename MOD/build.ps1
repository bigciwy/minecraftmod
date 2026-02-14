param(
    [string]$GradlePath = ""
)

# Common Gradle installation paths
$possiblePaths = @(
    "C:\Gradle\gradle-7.4.2\bin\gradle.bat",
    "C:\gradle-7.4.2\bin\gradle.bat",
    "$env:USERPROFILE\gradle-7.4.2\bin\gradle.bat",
    "C:\tools\gradle\bin\gradle.bat",
    "D:\gradle-7.4.2\bin\gradle.bat",
    "C:\opt\gradle\bin\gradle.bat"
)

if ($GradlePath -and (Test-Path $GradlePath)) {
    Write-Host "Using Gradle: $GradlePath"
    & $GradlePath build
    exit
}

foreach ($path in $possiblePaths) {
    if (Test-Path $path) {
        Write-Host "Found Gradle at: $path"
        & $path build
        exit
    }
}

Write-Host "Gradle not found in common locations"
Write-Host ""
Write-Host "Please tell me the path to your gradle.bat file"
Write-Host "Or run: download_gradle_wrapper.ps1"
