Set objShell = CreateObject("WScript.Shell")
Do While True
    WScript.Sleep 30000 ' 30 segundos
    objShell.SendKeys("{SCROLLLOCK}")
    WScript.Sleep 30000
    objShell.SendKeys("{SCROLLLOCK}")
Loop
