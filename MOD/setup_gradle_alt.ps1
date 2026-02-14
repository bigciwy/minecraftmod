# Alternative Gradle Wrapper Setup
# Downloads gradle wrapper jar from Maven Central

$wrapperDir = "c:\Users\eleog\OneDrive\Desktop\MOD\gradle\wrapper"
$wrapperJar = "$wrapperDir\gradle-wrapper.jar"

Write-Host "Attempting alternative gradle-wrapper.jar download..."
Write-Host ""

# Method 1: Try official gradle.org CDN
$urls = @(
    "https://gradle.org/gradle-4.10-wrapper.jar",
    "https://repo.gradle.org/gradle/gradle-wrapper-7.4.2.jar",
    "https://services.gradle.org/distributions/gradle-7.4.2-bin.zip"
)

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

foreach ($url in $urls) {
    try {
        Write-Host "Trying: $url"
        Invoke-WebRequest -Uri $url -OutFile $wrapperJar -UseBasicParsing -TimeoutSec 30
        Write-Host "✓ Successfully downloaded!"
        exit 0
    } catch {
        Write-Host "✗ Failed: $_"
    }
}

Write-Host ""
Write-Host "All download methods failed."
Write-Host ""
Write-Host "=== ALTERNATIVE SOLUTION ==="
Write-Host ""
Write-Host "Install Gradle using Chocolatey:"
Write-Host "1. Open PowerShell as Administrator"
Write-Host "2. Run: choco install gradle"
Write-Host ""
Write-Host "Then compile with:"
Write-Host "  gradle build"
