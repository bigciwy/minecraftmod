$wrapperJar = "c:\Users\eleog\OneDrive\Desktop\MOD\gradle\wrapper\gradle-wrapper.jar"

# Download correct gradle-wrapper.jar from Maven Central
$url = "https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar"

Write-Host "Downloading correct gradle-wrapper.jar..."

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

try {
    $ProgressPreference = 'SilentlyContinue'
    Invoke-WebRequest -Uri $url -OutFile $wrapperJar -UseBasicParsing
    $ProgressPreference = 'Continue'
    
    $size = (Get-Item $wrapperJar).Length / 1MB
    Write-Host "Downloaded: $size MB"
    Write-Host "Ready to build!"
}
catch {
    Write-Host "Error: $_"
}
