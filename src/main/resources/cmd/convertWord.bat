@echo off
set DOC_DIR=%1
set MY_PS=%~dp0doc2pdf.ps1
set CMD="ls %DOC_DIR% *.doc* ¨CRecurse | %%{ %MY_PS% $_.fullname }"
powershell -Command %CMD%
rem powershell -file doc2pdf.ps1