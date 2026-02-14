# Setup Gradle Wrapper
# This script downloads the gradle-wrapper.jar needed to build the project

$wrapperDir = "c:\Users\eleog\OneDrive\Desktop\MOD\gradle\wrapper"
$wrapperJar = "$wrapperDir\gradle-wrapper.jar"

Write-Host "Setting up Gradle Wrapper..."

# Create gradle/wrapper directory if it doesn't exist
if (-not (Test-Path $wrapperDir)) {
    New-Item -ItemType Directory -Path $wrapperDir -Force | Out-Null
    Write-Host "Created directory: $wrapperDir"
}

# Download gradle-wrapper.jar
$url = "https://gradle-wrapper.gradle-wrapper.com/gradle-wrapper-7.4.2.jar"
$outputFile = $wrapperJar

try {
    Write-Host "Downloading gradle-wrapper.jar..."
    
    # Use Invoke-WebRequest for the download
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
    Invoke-WebRequest -Uri "https://services.gradle.org/distributions/gradle-7.4.2-bin.zip" -OutFile "$env:TEMP\gradle.zip" -UseBasicParsing
    
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    $zip = [System.IO.Compression.ZipFile]::OpenRead("$env:TEMP\gradle.zip")
    $wrapperEntry = $zip.Entries | Where-Object { $_.Name -eq "gradle-wrapper.jar" } | Select-Object -First 1
    
    if ($wrapperEntry) {
        [System.IO.Compression.ZipFileExtensions]::ExtractToFile($wrapperEntry, $outputFile, $true)
    } else {
        throw "gradle-wrapper.jar not found in zip"
    }
    
    $zip.Dispose()
    Remove-Item "$env:TEMP\gradle.zip"
    
    Write-Host "Successfully downloaded gradle-wrapper.jar"
    Write-Host "Saved to: $outputFile"
    
    # Verify file exists
    if (Test-Path $outputFile) {
        $fileSize = (Get-Item $outputFile).Length / 1MB
        Write-Host "File size: $fileSize MB"
        Write-Host ""
        Write-Host "Gradle wrapper setup complete!"
        Write-Host "You can now run: .\gradlew.bat build"
    } else {
        Write-Host "ERROR: File was not created"
    }
    
} catch {
    Write-Host "ERROR: Failed to download gradle-wrapper.jar"
    Write-Host "Error: $_"
    Write-Host ""
    Write-Host "Alternative solution:"
    Write-Host "1. Download from: https://services.gradle.org/distributions/gradle-7.4.2-bin.zip"
    Write-Host "2. Extract gradle-wrapper.jar to: $wrapperDir"
}
