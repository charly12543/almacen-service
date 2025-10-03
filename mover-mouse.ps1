Set objShell = CreateObject("WScript.Shell")

' Crear icono en bandeja con HTA
Set objApp = CreateObject("Shell.Application")
MsgBox "El script está activo. Para detenerlo, ciérralo desde el Administrador de tareas (wscript.exe).", vbInformation, "Teams Verde Activo"

Do While True
    WScript.Sleep 30000 ' 30 segundos
    objShell.SendKeys("{SCROLLLOCK}")
    WScript.Sleep 30000
    objShell.SendKeys("{SCROLLLOCK}")
Loop
