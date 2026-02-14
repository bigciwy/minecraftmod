# Download gradle and extract wrapper jar
$tempDir = "$env:TEMP\gradle_setup"
$wrapperDir = "c:\Users\eleog\OneDrive\Desktop\MOD\gradle\wrapper"
$zipPath = "$tempDir\gradle-7.4.2-bin.zip"

if (-not (Test-Path $tempDir)) {
    New-Item -ItemType Directory -Path $tempDir -Force | Out-Null
}

Write-Host "Downloading gradle-7.4.2-bin.zip..."

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
$url = "https://services.gradle.org/distributions/gradle-7.4.2-bin.zip"

try {
    $ProgressPreference = 'SilentlyContinue'
    Invoke-WebRequest -Uri $url -OutFile $zipPath -UseBasicParsing
    $ProgressPreference = 'Continue'
    
    Write-Host "Extracting gradle-wrapper.jar..."
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    
    $zip = [System.IO.Compression.ZipFile]::OpenRead($zipPath)
    $wrapperEntry = $zip.Entries | Where-Object { $_.Name -eq "gradle-wrapper.jar" } | Select-Object -First 1
    
    if ($wrapperEntry) {
        [System.IO.Compression.ZipFileExtensions]::ExtractToFile($wrapperEntry, "$wrapperDir\gradle-wrapper.jar", $true)
        Write-Host "Setup complete!"
    }
    
    $zip.Dispose()
    Remove-Item $zipPath -Force
}
catch {
    Write-Host "ERROR: $_"
}
