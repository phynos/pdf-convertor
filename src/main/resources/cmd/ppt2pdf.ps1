param (
	[string]$source = $(Throw "You have to specify a source path.")
)

function Convert-PPTtoPdf
{
    param(
        [string]$source = $(Throw "You have to specify a source path.")
    )
	
	$extensionSize = 3
	if ($source.EndsWith("pptx")) {
		$extensionSize = 4
	}

	$destiny = $source.Substring(0, $source.Length - $extensionSize) + "pdf"
	$saveaspath = $destiny
	
	#Add-Type -AssemblyName office-ErrorAction SilentlyContinue
	Add-Type -AssemblyName Microsoft.Office.Interop.powerpoint
		
	$pptObj = new-object -ComObject "PowerPoint.application"
	#$pptObj.visible = [Microsoft.Office.Core.MsoTriState]::msoType
	$pptObj.visible = $true
	
	$opt = [Microsoft.Office.Interop.PowerPoint.PpSaveAsFileType]::ppSaveAsPDF
	$presentation = $pptObj.Presentations.open($source)
	$pptObj.visible = $false
	$presentation.SaveAs($saveaspath, $opt)
	$presentation.Close()
	$pptObj.quit()
	
	echo "Converted file: $source"

	ps powerpnt | kill
}

Convert-PPTtoPdf($source)
