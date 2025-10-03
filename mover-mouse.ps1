Add-Type -AssemblyName System.Windows.Forms

while ($true) {
    [System.Windows.Forms.Cursor]::Position = '0,0'
    Start-Sleep -Milliseconds 500
    [System.Windows.Forms.Cursor]::Position = '5,5'
    Start-Sleep -Milliseconds 500
}
