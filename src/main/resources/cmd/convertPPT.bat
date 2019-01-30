@echo off
set DOC_DIR=%1
set MY_PS=%~dp0ppt2pdf.ps1
set CMD="ls %DOC_DIR% *.ppt* ¨CRecurse | %%{ %MY_PS% $_.fullname }"
powershell -Command %CMD%
rem powershell -file excel2pdf.ps1