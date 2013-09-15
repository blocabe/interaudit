# NSIS Install script for Derive
# Frederic Culot - Intrasoft Intl.
#
# $Id: setup.nsi,v 1.1 2009/10/13 16:11:57 culfre Exp $

#
# To compile this script, the following directories must be set up:
#
# /-+
#   |              __ *.jar (client specific jars)
#   +-- Client --/__ StartDerive.bat (file containing: 'cd /D "@@INSTALL_DIR@@\Client && start javaw -jar "derive-1.0.jar"')
#   |
#   |            __ derive.ini (derive configuration file - the @@INSTALL_DIR@@ strings will be replaced by the installer)
#   +-- Conf --/__  derive.properties (client configuration file)
#   |
#   +-- Database --/ Derive.mdb (access database)
#   |
#   +-- Doc --/ User guide DERIVE 2009 EN.pdf (displayed at the end of the install process)
#   |
#   |                 __ InetLoad/, JREDyna.sh <-- see http://nsis.sourceforge.net/Java_Runtime_Environment_Dynamic_Installer
#   +-- Installer --/__ setup.nsi (this script)
#   |
#   +-- Misc --/ logo_derive.bmp, logo_derive.ico
#   |
#   +-- Server --/ Execs/ (derive.exe + dlls + XlsGenerator: everything needed to run the server)
#                     
#
# Note:
# In the client launch script (StartDerive.bat) and in the server configuration
# file (derive.ini), the string:
#                                @@INSTALL_DIR@@
# will automatically be replaced by the install root directory during the 
# install process.
#

Name Derive

RequestExecutionLevel user

# General Symbol Definitions
!define REGKEY "SOFTWARE\$(^Name)"
!define VERSION 1.0
!define COMPANY "Intrasoft Intl."
!define URL http://www.intrasoft-intl.com
!define REGISTRYROOT HKCU

# Derive Specific Symbol definitions
!define SERVER_CONFIG_FILE derive.ini 
!define CLIENT_CONFIG_FILE derive.properties
!define CLIENT_LAUNCH_SCRIPT StartDerive.bat
!define DERIVE_MANUAL "User guide DERIVE 2009 EN.pdf"

# MUI Symbol Definitions
!define MUI_ICON "..\Misc\logo_derive.ico"
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_FINISHPAGE_SHOWREADME "$INSTDIR\Doc\${DERIVE_MANUAL}"
!define MUI_FINISHPAGE_SHOWREADME_TEXT "View DERIVE User Guide"
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\orange-uninstall-nsis.ico"
!define MUI_UNFINISHPAGE_NOAUTOCLOSE
!define MUI_LANGDLL_REGISTRY_ROOT ${REGISTRYROOT}
!define MUI_LANGDLL_REGISTRY_KEY ${REGKEY}
!define MUI_LANGDLL_REGISTRY_VALUENAME InstallerLanguage

# Included files
!include Sections.nsh
!include MUI2.nsh

# Reserved Files
!insertmacro MUI_RESERVEFILE_LANGDLL
ReserveFile "${NSISDIR}\Plugins\AdvSplash.dll"

# Variables
Var StartMenuGroup

# Installer pages
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

# Automatic JRE detection
# Needs InetLoad and JREDyna.sh (see comments at the beginning of this script)
!define JRE_VERSION "1.6"
!define JRE_URL "http://javadl.sun.com/webapps/download/AutoDL?BundleId=33787"
!include "JREDyna.nsh"
!insertmacro CUSTOM_PAGE_JREINFO

# Installer languages
!insertmacro MUI_LANGUAGE English
!insertmacro MUI_LANGUAGE French

# Installer attributes
OutFile ..\install.exe
InstallDir $INSTDIR # This is set in the MUI_PAGE_DIRECTORY installer page
InstProgressFlags smooth

CRCCheck on
XPStyle on
ShowInstDetails show
VIProductVersion 1.0.0.0
VIAddVersionKey /LANG=${LANG_ENGLISH} ProductName Derive
VIAddVersionKey /LANG=${LANG_ENGLISH} ProductVersion "${VERSION}"
VIAddVersionKey /LANG=${LANG_ENGLISH} CompanyName "${COMPANY}"
VIAddVersionKey /LANG=${LANG_ENGLISH} CompanyWebsite "${URL}"
VIAddVersionKey /LANG=${LANG_ENGLISH} FileVersion "${VERSION}"
VIAddVersionKey /LANG=${LANG_ENGLISH} FileDescription ""
VIAddVersionKey /LANG=${LANG_ENGLISH} LegalCopyright ""
InstallDirRegKey ${REGISTRYROOT} "${REGKEY}" Path
ShowUninstDetails show

# Installer sections
Section !Server SEC0000
    
    # Detect available JRE in the first install section
    call DownloadAndInstallJREIfNecessary
    
    SetOutPath $INSTDIR\Server\Execs
    SetOverwrite on
    File ..\Server\Execs\zlib1.dll
    File ..\Server\Execs\derive.exe
    File ..\Server\Execs\ErrorDE.xml
    File ..\Server\Execs\ErrorEN.xml
    File ..\Server\Execs\ErrorFR.xml
    File ..\Server\Execs\conditional_formula.xml
    File ..\Server\Execs\converthtm.xsl    
    File ..\Server\Execs\gdal11.dll
    File ..\Server\Execs\iconv.dll
    File ..\Server\Execs\libxml2.dll
    File ..\Server\Execs\libxslt.dll
    File ..\Server\Execs\msvcr70.dll
    File ..\Server\Execs\XlsGenerator.cmd
    SetOutPath $INSTDIR\Server\Execs\XlsGenerator
    SetOutPath $INSTDIR\Server\Execs\XlsGenerator\lib
    File ..\Server\Execs\XlsGenerator\lib\jxl.jar
    File ..\Server\Execs\XlsGenerator\lib\log4j-1.2.14.jar
    File ..\Server\Execs\XlsGenerator\lib\xerces.jar
    SetOutPath $INSTDIR\Server\Execs\XlsGenerator\src
    SetOutPath $INSTDIR\Server\Execs\XlsGenerator\src\lu
    SetOutPath $INSTDIR\Server\Execs\XlsGenerator\src\lu\intrasoft
    SetOutPath $INSTDIR\Server\Execs\XlsGenerator\src\lu\intrasoft\xlsgenerator
    File ..\Server\Execs\XlsGenerator\src\lu\intrasoft\xlsgenerator\JExcelWorkBook.class
    File ..\Server\Execs\XlsGenerator\src\lu\intrasoft\xlsgenerator\JExcelWorkBookHandler.class
    File ..\Server\Execs\XlsGenerator\src\lu\intrasoft\xlsgenerator\MiscUtils.class
    File ..\Server\Execs\XlsGenerator\src\lu\intrasoft\xlsgenerator\XlsGeneratorMain.class
    SetOutPath $INSTDIR\Server
    SetOutPath $INSTDIR\Server\Logs
    SetOutPath $INSTDIR\Server\Logs\derive
    WriteRegStr ${REGISTRYROOT} "${REGKEY}\Components" Server 1
SectionEnd

Section !Client SEC0001
    SetOutPath $INSTDIR\Client
    SetOverwrite on
    File ..\Client\xws-security-2.0-ea2.jar
    File ..\Client\activation-1.1.jar
    File ..\Client\Batik.jar
    File ..\Client\binding-2.0.6.jar
    File ..\Client\crimson.jar
    File ..\Client\derive-1.0.jar
    File ..\Client\forms-1.2.0.jar
    File ..\Client\iText.jar
    File ..\Client\jaxws-api.jar
    File ..\Client\jcalendar.jar
    File ..\Client\log4j-1.2.14.jar
    File ..\Client\saaj-api-1.3.jar
    File ..\Client\saaj-impl.jar
    File ..\Client\sbs-client_1.1.jar
    File ..\Client\${CLIENT_LAUNCH_SCRIPT}
    File ..\Client\stax-api-1.0.1.jar
    File ..\Client\wstx-asl-3.2.0.jar
    File ..\Client\xalan-2.7.0.jar
    File ..\Client\xmlsec-2.0.jar
    SetOutPath $SMPROGRAMS\$StartMenuGroup
    CreateShortcut $SMPROGRAMS\$StartMenuGroup\Derive.lnk $INSTDIR\Client\${CLIENT_LAUNCH_SCRIPT} "" "$INSTDIR\Misc\logo_derive.ico"
    CreateShortcut $INSTDIR\Derive.lnk $INSTDIR\Client\${CLIENT_LAUNCH_SCRIPT} "" "$INSTDIR\Misc\logo_derive.ico"
    CreateShortcut $DESKTOP\Derive.lnk $INSTDIR\Client\${CLIENT_LAUNCH_SCRIPT} "" "$INSTDIR\Misc\logo_derive.ico"
    
    # Now write appropriate paths in the configuration file
    Push @@INSTALL_DIR@@                                #text to be replaced
    Push $INSTDIR                                       #replace with
    Push all                                            #replace all occurrences
    Push all                                            #replace all occurrences
    Push $INSTDIR\Client\${CLIENT_LAUNCH_SCRIPT}        #file to replace in
    Call AdvReplaceInFile                      #call find and replace function
    
    WriteRegStr ${REGISTRYROOT} "${REGKEY}\Components" Client 1
SectionEnd

Section !Database SEC0002
    SetOutPath $INSTDIR\Database
    SetOverwrite on
    File ..\Database\Derive.mdb
    WriteRegStr ${REGISTRYROOT} "${REGKEY}\Components" Database 1
SectionEnd

Section "!User Directory" SEC0003
    SetOutPath $INSTDIR
    SetOverwrite on
    SetOutPath $INSTDIR\Users
    SetOutPath $INSTDIR\Users\derive    
    SetOutPath $SMPROGRAMS\$StartMenuGroup
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\Derive Data.lnk" $INSTDIR\Users\derive
    WriteRegStr ${REGISTRYROOT} "${REGKEY}\Components" "User Directory" 1
SectionEnd

Section !Documentation SEC0004
    SetOutPath $INSTDIR
    SetOverwrite on
    SetOutPath $INSTDIR\Doc
    File "..\Doc\${DERIVE_MANUAL}"
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\Derive User Guide.lnk" "$INSTDIR\Doc\${DERIVE_MANUAL}"    
    WriteRegStr ${REGISTRYROOT} "${REGKEY}\Components" Documentation 1
SectionEnd

Section !Configuration SEC0005

    # Server configuration file
    SetOutPath $INSTDIR\Server\Execs
    SetOverwrite on
    File ..\Conf\${SERVER_CONFIG_FILE}
    # Now write appropriate paths in the configuration file
    Push @@INSTALL_DIR@@                                #text to be replaced
    Push $INSTDIR                                       #replace with
    Push all                                            #replace all occurrences
    Push all                                            #replace all occurrences
    Push $INSTDIR\Server\Execs\${SERVER_CONFIG_FILE}    #file to replace in
    Call AdvReplaceInFile                      #call find and replace function

    # Client configuration file
    SetOutPath $INSTDIR\Client
    SetOverwrite on    
    File ..\Conf\${CLIENT_CONFIG_FILE}
     
    WriteRegStr ${REGISTRYROOT} "${REGKEY}\Components" Configuration 1
SectionEnd

Section !Misc SEC0006
    SetOutPath $INSTDIR
    SetOverwrite on
    SetOutPath $INSTDIR\Misc
    File ..\Misc\logo_derive.bmp
    File ..\Misc\logo_derive.ico
    WriteRegStr ${REGISTRYROOT} "${REGKEY}\Components" Misc 1
SectionEnd

Section -post SEC0007
    WriteRegStr ${REGISTRYROOT} "${REGKEY}" Path $INSTDIR
    SetOutPath $INSTDIR
    WriteUninstaller $INSTDIR\uninstall.exe
    SetOutPath $SMPROGRAMS\$StartMenuGroup
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\$(^UninstallLink).lnk" $INSTDIR\uninstall.exe
    WriteRegStr ${REGISTRYROOT} "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayName "$(^Name)"
    WriteRegStr ${REGISTRYROOT} "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayVersion "${VERSION}"
    WriteRegStr ${REGISTRYROOT} "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" Publisher "${COMPANY}"
    WriteRegStr ${REGISTRYROOT} "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" URLInfoAbout "${URL}"
    WriteRegStr ${REGISTRYROOT} "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayIcon $INSTDIR\uninstall.exe
    WriteRegStr ${REGISTRYROOT} "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" UninstallString $INSTDIR\uninstall.exe
    WriteRegDWORD ${REGISTRYROOT} "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoModify 1
    WriteRegDWORD ${REGISTRYROOT} "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoRepair 1
SectionEnd

# Macro for selecting uninstaller sections
!macro SELECT_UNSECTION SECTION_NAME UNSECTION_ID
    Push $R0
    ReadRegStr $R0 ${REGISTRYROOT} "${REGKEY}\Components" "${SECTION_NAME}"
    StrCmp $R0 1 0 next${UNSECTION_ID}
    !insertmacro SelectSection "${UNSECTION_ID}"
    GoTo done${UNSECTION_ID}
next${UNSECTION_ID}:
    !insertmacro UnselectSection "${UNSECTION_ID}"
done${UNSECTION_ID}:
    Pop $R0
!macroend

# Uninstaller sections
Section /o -un.Misc UNSEC0006
    Delete /REBOOTOK $INSTDIR\Misc\logo_derive.bmp
    Delete /REBOOTOK $INSTDIR\Misc\logo_derive.ico
    RmDir /r /REBOOTOK $INSTDIR\Misc
    DeleteRegValue ${REGISTRYROOT} "${REGKEY}\Components" Misc
SectionEnd

Section /o -un.Configuration UNSEC0005
    Delete /REBOOTOK $INSTDIR\Server\Execs\${SERVER_CONFIG_FILE}
    Delete /REBOOTOK $INSTDIR\Client\${CLIENT_CONFIG_FILE}
    DeleteRegValue ${REGISTRYROOT} "${REGKEY}\Components" Configuration
SectionEnd

Section /o -un.Documentation UNSEC0004
    Delete /REBOOTOK "$INSTDIR\Doc\${DERIVE_MANUAL}"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\Derive User Guide.lnk"    
    RmDir /r /REBOOTOK $INSTDIR\Doc
    DeleteRegValue ${REGISTRYROOT} "${REGKEY}\Components" Documentation
SectionEnd

Section /o "-un.User Directory" UNSEC0003
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\Derive Data.lnk"
    Delete /REBOOTOK $INSTDIR\Users\*
    RmDir /r /REBOOTOK $INSTDIR\Users\*
    RmDir /r /REBOOTOK $INSTDIR\Users
    DeleteRegValue ${REGISTRYROOT} "${REGKEY}\Components" "User Directory"
SectionEnd

Section /o -un.Database UNSEC0002
    Delete /REBOOTOK $INSTDIR\Database\Derive.mdb
    RmDir /r /REBOOTOK $INSTDIR\Database
    DeleteRegValue ${REGISTRYROOT} "${REGKEY}\Components" Database
SectionEnd

Section /o -un.Client UNSEC0001
    Delete /REBOOTOK $DESKTOP\Derive.lnk
    Delete /REBOOTOK $INSTDIR\Derive.lnk
    Delete /REBOOTOK $SMPROGRAMS\$StartMenuGroup\Derive.lnk
    Delete /REBOOTOK $INSTDIR\Client\*
    RmDir /r /REBOOTOK $INSTDIR\Client
    DeleteRegValue ${REGISTRYROOT} "${REGKEY}\Components" Client
SectionEnd

Section /o -un.Server UNSEC0000
    RmDir /r /REBOOTOK $INSTDIR\Server\Logs\*
    RmDir /r /REBOOTOK $INSTDIR\Server\Logs
    RmDir /r /REBOOTOK $INSTDIR\Server\Execs
    Delete /REBOOTOK $INSTDIR\Server\*
    RmDir /r /REBOOTOK $INSTDIR\Server
    DeleteRegValue ${REGISTRYROOT} "${REGKEY}\Components" Server
SectionEnd

Section -un.post UNSEC0007
    DeleteRegKey ${REGISTRYROOT} "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^UninstallLink).lnk"
    Delete /REBOOTOK $INSTDIR\uninstall.exe
    DeleteRegValue ${REGISTRYROOT} "${REGKEY}" Path
    DeleteRegKey /IfEmpty ${REGISTRYROOT} "${REGKEY}\Components"
    DeleteRegKey /IfEmpty ${REGISTRYROOT} "${REGKEY}"
    RmDir /REBOOTOK $SMPROGRAMS\$StartMenuGroup
    RmDir $INSTDIR
SectionEnd

# Installer functions

#
# This callback will be called when the installer is nearly finished 
# initializing. 
Function .onInit
    InitPluginsDir
    StrCpy $StartMenuGroup Derive
    Push $R1
    File /oname=$PLUGINSDIR\spltmp.bmp ..\Misc\logo_derive.bmp
    advsplash::show 1000 600 400 -1 $PLUGINSDIR\spltmp
    Pop $R1
    Pop $R1
    !insertmacro MUI_LANGDLL_DISPLAY
FunctionEnd

# Uninstaller functions
Function un.onInit
    ReadRegStr $INSTDIR ${REGISTRYROOT} "${REGKEY}" Path
    StrCpy $StartMenuGroup Derive
    !insertmacro MUI_UNGETLANGUAGE
    !insertmacro SELECT_UNSECTION Server ${UNSEC0000}
    !insertmacro SELECT_UNSECTION Client ${UNSEC0001}
    !insertmacro SELECT_UNSECTION Database ${UNSEC0002}
    !insertmacro SELECT_UNSECTION "User Directory" ${UNSEC0003}
    !insertmacro SELECT_UNSECTION Documentation ${UNSEC0004}
    !insertmacro SELECT_UNSECTION Configuration ${UNSEC0005}
    !insertmacro SELECT_UNSECTION Misc ${UNSEC0006}
FunctionEnd

# Section Descriptions
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
!insertmacro MUI_DESCRIPTION_TEXT ${SEC0000} $(SEC0000_DESC)
!insertmacro MUI_DESCRIPTION_TEXT ${SEC0001} $(SEC0001_DESC)
!insertmacro MUI_DESCRIPTION_TEXT ${SEC0002} $(SEC0002_DESC)
!insertmacro MUI_DESCRIPTION_TEXT ${SEC0003} $(SEC0003_DESC)
!insertmacro MUI_DESCRIPTION_TEXT ${SEC0004} $(SEC0004_DESC)
!insertmacro MUI_DESCRIPTION_TEXT ${SEC0005} $(SEC0005_DESC)
!insertmacro MUI_DESCRIPTION_TEXT ${SEC0006} $(SEC0006_DESC)
!insertmacro MUI_FUNCTION_DESCRIPTION_END

# Installer Language Strings
# TODO Update the Language Strings with the appropriate translations.

LangString ^UninstallLink ${LANG_ENGLISH} "Uninstall $(^Name)"
LangString ^UninstallLink ${LANG_FRENCH} "Uninstall $(^Name)"

LangString SEC0000_DESC ${LANG_ENGLISH} "Installs necessary components for the Derive server"
LangString SEC0001_DESC ${LANG_ENGLISH} "Installs Derive Client"
LangString SEC0002_DESC ${LANG_ENGLISH} "Installs Derive Database"
LangString SEC0003_DESC ${LANG_ENGLISH} "Creates User Directory"
LangString SEC0004_DESC ${LANG_ENGLISH} "Installs Derive documentation"
LangString SEC0005_DESC ${LANG_ENGLISH} "Configuration files set up"
LangString SEC0006_DESC ${LANG_ENGLISH} "Miscellaneous  files"

LangString SEC0000_DESC ${LANG_FRENCH} "Installe les composants nécessaires au fonctionnement du serveur Derive"
LangString SEC0001_DESC ${LANG_FRENCH} "Installe le client permettant de se connecter au serveur Derive"
LangString SEC0002_DESC ${LANG_FRENCH} "Installe la base de données nécessaire au bon fonctionnement de Derive"
LangString SEC0003_DESC ${LANG_FRENCH} "Créé le répertoire utilisateur"
LangString SEC0004_DESC ${LANG_FRENCH} "Installe la documentation de Derive"
LangString SEC0005_DESC ${LANG_FRENCH} "Créé les fichiers de configuration"
LangString SEC0006_DESC ${LANG_FRENCH} "Ressources diverses"

#
# Function used to replace text into a file.
# Taken from http://nsis.sourceforge.net/Category:Code_Examples
#
# Note:
# The text replacement is case insensitive.
#
# Usage:
# AdvReplaceInFile "text to be replaced" "replace with" "all" "all" "file"
#
# Example:  
#    Push @MY_VARIABLE@                         #text to be replaced
#    Push ${MY_NEW_VARIABLE}                    #replace with
#    Push all                                   #replace all occurrences
#    Push all                                   #replace all occurrences
#    Push $INSTDIR\my_conf_file                 #file to replace in
#    Call AdvReplaceInFile                      #call find and replace function
#
Function AdvReplaceInFile
 
         ; call stack frame:
         ;   0 (Top Of Stack) file to replace in
         ;   1 number to replace after (all is valid)
         ;   2 replace and onwards (all is valid)
         ;   3 replace with
         ;   4 to replace
 
         ; save work registers and retrieve function parameters
         Exch $0 ;file to replace in
         Exch 4
         Exch $4 ;to replace
         Exch
         Exch $1 ;number to replace after
         Exch 3
         Exch $3 ;replace with
         Exch 2
         Exch $2 ;replace and onwards
         Exch 2
         Exch 
         Push $5 ;minus count
         Push $6 ;universal
         Push $7 ;end string
         Push $8 ;left string
         Push $9 ;right string
         Push $R0 ;file1
         Push $R1 ;file2
         Push $R2 ;read
         Push $R3 ;universal
         Push $R4 ;count (onwards)
         Push $R5 ;count (after)
         Push $R6 ;temp file name
         GetTempFileName $R6
         FileOpen $R1 $0 r ;file to search in
         FileOpen $R0 $R6 w ;temp file
                  StrLen $R3 $4
                  StrCpy $R4 -1
                  StrCpy $R5 -1
        loop_read:
         ClearErrors
         FileRead $R1 $R2 ;read line
         IfErrors exit
         StrCpy $5 0
         StrCpy $7 $R2
 
        loop_filter:
         IntOp $5 $5 - 1
         StrCpy $6 $7 $R3 $5 ;search
         StrCmp $6 "" file_write2
         StrCmp $6 $4 0 loop_filter
 
         StrCpy $8 $7 $5 ;left part
         IntOp $6 $5 + $R3
         StrCpy $9 $7 "" $6 ;right part
         StrCpy $7 $8$3$9 ;re-join
 
         IntOp $R4 $R4 + 1
         StrCmp $2 all file_write1
         StrCmp $R4 $2 0 file_write2
         IntOp $R4 $R4 - 1
 
         IntOp $R5 $R5 + 1
         StrCmp $1 all file_write1
         StrCmp $R5 $1 0 file_write1
         IntOp $R5 $R5 - 1
         Goto file_write2
 
        file_write1:
         FileWrite $R0 $7 ;write modified line
         Goto loop_read
 
        file_write2:
         FileWrite $R0 $R2 ;write unmodified line
         Goto loop_read
 
        exit:
         FileClose $R0
         FileClose $R1
 
         SetDetailsPrint none
         Delete $0
         Rename $R6 $0
         Delete $R6
         SetDetailsPrint both
 
         Pop $R6
         Pop $R5
         Pop $R4
         Pop $R3
         Pop $R2
         Pop $R1
         Pop $R0
         Pop $9
         Pop $8
         Pop $7
         Pop $6
         Pop $5
         Pop $4
         Pop $3
         Pop $2
         Pop $1
         Pop $0
FunctionEnd