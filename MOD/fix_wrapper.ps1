$wrapperDir = "c:\Users\eleog\OneDrive\Desktop\MOD\gradle\wrapper"
$wrapperJar = "$wrapperDir\gradle-wrapper.jar"

Write-Host "Fixing gradle-wrapper.jar..."
Write-Host "Downloading from Maven Central..."

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

$urls = @(
    "https://raw.githubusercontent.com/gradle/gradle/v7.4.2/gradle/wrapper/gradle-wrapper.jar",
    "https://services.gradle.org/distributions/gradle-7.4.2-bin.zip"
)

try {
    # Backup old corrupted jar
    if (Test-Path $wrapperJar) {
        Remove-Item $wrapperJar -Force
    }
    
    $downloaded = $false
    foreach ($url in $urls) {
        try {
            Write-Host "Trying: $url"
            $ProgressPreference = 'SilentlyContinue'
            Invoke-WebRequest -Uri $url -OutFile $wrapperJar -UseBasicParsing
            $ProgressPreference = 'Continue'
            $downloaded = $true
            break
        } catch {
            Write-Host "Failed: $_"
        }
    }
    
    if (-not $downloaded) {
        throw "All download methods failed"
    }
    
    $size = (Get-Item $wrapperJar).Length
    Write-Host "Downloaded gradle-wrapper.jar ($size bytes)"
    Write-Host "Ready to build!"
}
catch {
    Write-Host "ERROR: $_"
    Write-Host "Try manually downloading from:"
    Write-Host "https://repo1.maven.org/maven2/org/gradle/gradle-wrapper/7.4.2/gradle-wrapper-7.4.2.jar"
}
