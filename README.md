## PDF转换器
包含2部分，一部分是PDF转换，一部分是PDF浏览。  

### PDF转换
实现了WORD，EXCEL,PPT,TXT转换成PDF，目前支持2种方式：MicrosoftOffice和OpenOffice

### PDF浏览
PC端浏览器可以直接打开PDF文件，也提供PDFJS插件来浏览PDF，移动端直接用PDFJS插件来浏览。  


## 基于MicrosoftOffice+PowerShell模式  
此模式必须先打开PowerShell运行权限，可在命令行执行命令：  
<code>Set-ExecutionPolicy Unrestricted</code>
#### 优点
- 性能最好
- 完美转换（基于官方原生软件）

#### 缺点
- 只支持windows系统
- 必须安装Microsoft-Office2007+
- 必须支持PowerShell脚本（Win7以上操作系统）
- 灵活性相对较差（依赖于COM接口）

## 基于OpenOffice+jodconverter模式
此模式必须在安装OpenOffice和开启其转换服务，在windows下可在其安装目录运行：  
<code>soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp" -nofirststartwizard</code>
#### 优点
- 跨平台
- 灵活度高

#### 缺点
- 转换慢
- 复杂文档转换不完美

## 更新日志
[点击查看](UPDATE.md)  

有什么建议，请发送邮件至phynos@126.com。