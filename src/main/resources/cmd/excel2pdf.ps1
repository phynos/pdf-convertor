param (
    [string]$source = $(Throw "You have to specify a source path.")
)

function Convert-ExcelToPdf
{
    param(
        [string]$source = $(Throw "You have to specify a source path.")
    )
        
    $extensionSize = 3
    if ($source.EndsWith("xlsx")) {
        $extensionSize = 4
    }
    
    $destiny = $source.Substring(0, $source.Length - $extensionSize) + "pdf"
    $saveaspath = $destiny
    $formatPDF = 17

    $xlFixedFormat = "Microsoft.Office.Interop.Excel.xlFixedFormatType" -as [type]
    $excel = new-object -ComObject excel.application
    $excel.Visible = $true
    $workbook = $excel.workbooks.open($source,0)
    $excel.Visible = $false    
    # 
    $workbook.ActiveSheet.PageSetup.Orientation = 2
    #$excel.PrintCommunication = $false
    $workbook.ActiveSheet.PageSetup.FitToPagesTall = $false
    $workbook.ActiveSheet.PageSetup.FitToPagesWide = 1
    #$excel.PrintCommunication = $true
    $workbook.Saved = $true
    $workbook.ExportAsFixedFormat($xlFixedFormat::xlTypePDF,$saveaspath)

    $excel.Workbooks.close()
    $excel.Quit()

    echo "Converted file: $source"

    ps EXCEL | kill
}

# "C:\Users\Administrator\Desktop\pdf\Excel.xlsx"
Convert-ExcelToPdf($source)



