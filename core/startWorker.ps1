param(
    [string]$arg1,
    [string]$arg2
)

$directory = "..\worker\worker"

# Check if the directory exists
if (-not (Test-Path $directory)) {
    Write-Host "Directory does not exist: $directory"
    exit 1
}

Set-Location -Path $directory

$escapedArg2 = "`'$arg2`'"

$mavenArgs = ".\mvnw spring-boot:run -D spring-boot.run.arguments=`"$arg1 $escapedArg2`""

Write-Host "Running command: $mavenArgs"

Start-Process -FilePath ".\mvnw" -ArgumentList "spring-boot:run", "-D spring-boot.run.arguments=`"$arg1 '$arg2'`"" -Wait

Set-Location -Path "..\\.."
