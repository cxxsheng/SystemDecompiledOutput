package com.android.server.graphics.fonts;

/* loaded from: classes.dex */
public class FontManagerShellCommand extends android.os.ShellCommand {
    private static final int MAX_SIGNATURE_FILE_SIZE_BYTES = 8192;
    private static final java.lang.String TAG = "FontManagerShellCommand";

    @android.annotation.NonNull
    private final com.android.server.graphics.fonts.FontManagerService mService;

    FontManagerShellCommand(@android.annotation.NonNull com.android.server.graphics.fonts.FontManagerService fontManagerService) {
        this.mService = fontManagerService;
    }

    public int onCommand(java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            getErrPrintWriter().println("Only shell or root user can execute font command.");
            return 1;
        }
        return execCommand(this, str);
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Font service (font) commands");
        outPrintWriter.println("help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("dump [family name]");
        outPrintWriter.println("    Dump all font files in the specified family name.");
        outPrintWriter.println("    Dump current system font configuration if no family name was specified.");
        outPrintWriter.println();
        outPrintWriter.println("update [font file path] [signature file path]");
        outPrintWriter.println("    Update installed font files with new font file.");
        outPrintWriter.println();
        outPrintWriter.println("update-family [family definition XML path]");
        outPrintWriter.println("    Update font families with the new definitions.");
        outPrintWriter.println();
        outPrintWriter.println("install-debug-cert [cert file path]");
        outPrintWriter.println("    Install debug certificate file. This command can be used only on");
        outPrintWriter.println("    debuggable device with root user.");
        outPrintWriter.println();
        outPrintWriter.println("clear");
        outPrintWriter.println("    Remove all installed font files and reset to the initial state.");
        outPrintWriter.println();
        outPrintWriter.println(com.android.server.am.HostingRecord.HOSTING_TYPE_RESTART);
        outPrintWriter.println("    Restart FontManagerService emulating device reboot.");
        outPrintWriter.println("    WARNING: this is not a safe operation. Other processes may misbehave if");
        outPrintWriter.println("    they are using fonts updated by FontManagerService.");
        outPrintWriter.println("    This command exists merely for testing.");
        outPrintWriter.println();
        outPrintWriter.println("status");
        outPrintWriter.println("    Prints status of current system font configuration.");
    }

    void dumpAll(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter) {
        dumpFontConfig(indentingPrintWriter, this.mService.getSystemFontConfig());
    }

    private void dumpSingleFontConfig(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull android.text.FontConfig.Font font) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("style = ");
        sb.append(font.getStyle());
        sb.append(", path = ");
        sb.append(font.getFile().getAbsolutePath());
        if (font.getTtcIndex() != 0) {
            sb.append(", index = ");
            sb.append(font.getTtcIndex());
        }
        if (!font.getFontVariationSettings().isEmpty()) {
            sb.append(", axes = ");
            sb.append(font.getFontVariationSettings());
        }
        if (font.getFontFamilyName() != null) {
            sb.append(", fallback = ");
            sb.append(font.getFontFamilyName());
        }
        indentingPrintWriter.println(sb.toString());
        if (font.getOriginalFile() != null) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Font is updated from " + font.getOriginalFile());
            indentingPrintWriter.decreaseIndent();
        }
    }

    private void dumpFontConfig(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull android.text.FontConfig fontConfig) {
        java.util.List fontFamilies = fontConfig.getFontFamilies();
        indentingPrintWriter.println("Named Family List");
        indentingPrintWriter.increaseIndent();
        java.util.List namedFamilyLists = fontConfig.getNamedFamilyLists();
        for (int i = 0; i < namedFamilyLists.size(); i++) {
            android.text.FontConfig.NamedFamilyList namedFamilyList = (android.text.FontConfig.NamedFamilyList) namedFamilyLists.get(i);
            indentingPrintWriter.println("Named Family (" + namedFamilyList.getName() + ")");
            indentingPrintWriter.increaseIndent();
            java.util.List families = namedFamilyList.getFamilies();
            for (int i2 = 0; i2 < families.size(); i2++) {
                android.text.FontConfig.FontFamily fontFamily = (android.text.FontConfig.FontFamily) families.get(i2);
                indentingPrintWriter.println("Family");
                java.util.List fontList = fontFamily.getFontList();
                indentingPrintWriter.increaseIndent();
                for (int i3 = 0; i3 < fontList.size(); i3++) {
                    dumpSingleFontConfig(indentingPrintWriter, (android.text.FontConfig.Font) fontList.get(i3));
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Dump Fallback Families");
        indentingPrintWriter.increaseIndent();
        int i4 = 0;
        for (int i5 = 0; i5 < fontFamilies.size(); i5++) {
            android.text.FontConfig.FontFamily fontFamily2 = (android.text.FontConfig.FontFamily) fontFamilies.get(i5);
            if (fontFamily2.getName() == null) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder("Fallback Family [");
                int i6 = i4 + 1;
                sb.append(i4);
                sb.append("]: lang=\"");
                sb.append(fontFamily2.getLocaleList().toLanguageTags());
                sb.append("\"");
                if (fontFamily2.getVariant() != 0) {
                    sb.append(", variant=");
                    switch (fontFamily2.getVariant()) {
                        case 1:
                            sb.append("Compact");
                            break;
                        case 2:
                            sb.append("Elegant");
                            break;
                        default:
                            sb.append("Unknown");
                            break;
                    }
                }
                indentingPrintWriter.println(sb.toString());
                java.util.List fontList2 = fontFamily2.getFontList();
                indentingPrintWriter.increaseIndent();
                for (int i7 = 0; i7 < fontList2.size(); i7++) {
                    dumpSingleFontConfig(indentingPrintWriter, (android.text.FontConfig.Font) fontList2.get(i7));
                }
                indentingPrintWriter.decreaseIndent();
                i4 = i6;
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Dump Family Aliases");
        indentingPrintWriter.increaseIndent();
        java.util.List aliases = fontConfig.getAliases();
        for (int i8 = 0; i8 < aliases.size(); i8++) {
            android.text.FontConfig.Alias alias = (android.text.FontConfig.Alias) aliases.get(i8);
            indentingPrintWriter.println("alias = " + alias.getName() + ", reference = " + alias.getOriginal() + ", width = " + alias.getWeight());
        }
        indentingPrintWriter.decreaseIndent();
    }

    private void dumpFallback(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull android.graphics.fonts.FontFamily[] fontFamilyArr) {
        for (android.graphics.fonts.FontFamily fontFamily : fontFamilyArr) {
            dumpFamily(indentingPrintWriter, fontFamily);
        }
    }

    private void dumpFamily(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull android.graphics.fonts.FontFamily fontFamily) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Family:");
        if (fontFamily.getLangTags() != null) {
            sb.append(" langTag = ");
            sb.append(fontFamily.getLangTags());
        }
        if (fontFamily.getVariant() != 0) {
            sb.append(" variant = ");
            switch (fontFamily.getVariant()) {
                case 1:
                    sb.append("Compact");
                    break;
                case 2:
                    sb.append("Elegant");
                    break;
                default:
                    sb.append("UNKNOWN");
                    break;
            }
        }
        indentingPrintWriter.println(sb.toString());
        for (int i = 0; i < fontFamily.getSize(); i++) {
            indentingPrintWriter.increaseIndent();
            try {
                dumpFont(indentingPrintWriter, fontFamily.getFont(i));
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                indentingPrintWriter.decreaseIndent();
                throw th;
            }
        }
    }

    private void dumpFont(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull android.graphics.fonts.Font font) {
        java.io.File file = font.getFile();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(font.getStyle());
        sb.append(", path = ");
        sb.append(file == null ? "[Not a file]" : file.getAbsolutePath());
        if (font.getTtcIndex() != 0) {
            sb.append(", index = ");
            sb.append(font.getTtcIndex());
        }
        android.graphics.fonts.FontVariationAxis[] axes = font.getAxes();
        if (axes != null && axes.length != 0) {
            sb.append(", axes = \"");
            sb.append(android.graphics.fonts.FontVariationAxis.toFontVariationSettings(axes));
            sb.append("\"");
        }
        indentingPrintWriter.println(sb.toString());
    }

    private void writeCommandResult(android.os.ShellCommand shellCommand, com.android.server.graphics.fonts.FontManagerService.SystemFontException systemFontException) {
        java.io.PrintWriter errPrintWriter = shellCommand.getErrPrintWriter();
        errPrintWriter.println(systemFontException.getErrorCode());
        errPrintWriter.println(systemFontException.getMessage());
        android.util.Slog.e(TAG, "Command failed: " + java.util.Arrays.toString(shellCommand.getAllArgs()), systemFontException);
    }

    private int dump(android.os.ShellCommand shellCommand) {
        if (!com.android.internal.util.DumpUtils.checkDumpPermission(this.mService.getContext(), TAG, shellCommand.getErrPrintWriter())) {
            return 1;
        }
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(shellCommand.getOutPrintWriter(), "  ");
        java.lang.String nextArg = shellCommand.getNextArg();
        android.text.FontConfig systemFontConfig = this.mService.getSystemFontConfig();
        if (nextArg == null) {
            dumpFontConfig(indentingPrintWriter, systemFontConfig);
            return 0;
        }
        android.graphics.fonts.FontFamily[] fontFamilyArr = (android.graphics.fonts.FontFamily[]) android.graphics.fonts.SystemFonts.buildSystemFallback(systemFontConfig).get(nextArg);
        if (fontFamilyArr == null) {
            indentingPrintWriter.println("Font Family \"" + nextArg + "\" not found");
            return 0;
        }
        dumpFallback(indentingPrintWriter, fontFamilyArr);
        return 0;
    }

    private int installCert(android.os.ShellCommand shellCommand) throws com.android.server.graphics.fonts.FontManagerService.SystemFontException {
        if (!android.os.Build.IS_DEBUGGABLE) {
            throw new java.lang.SecurityException("Only debuggable device can add debug certificate");
        }
        if (android.os.Binder.getCallingUid() != 0) {
            throw new java.lang.SecurityException("Only root can add debug certificate");
        }
        java.lang.String nextArg = shellCommand.getNextArg();
        if (nextArg == null) {
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10008, "Cert file path argument is required.");
        }
        java.io.File file = new java.io.File(nextArg);
        if (!file.isFile()) {
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10008, "Cert file (" + file + ") is not found");
        }
        this.mService.addDebugCertificate(nextArg);
        this.mService.restart();
        shellCommand.getOutPrintWriter().println("Success");
        return 0;
    }

    private int update(android.os.ShellCommand shellCommand) throws com.android.server.graphics.fonts.FontManagerService.SystemFontException {
        android.os.ParcelFileDescriptor openFileForSystem;
        android.os.ParcelFileDescriptor openFileForSystem2;
        java.lang.String nextArg = shellCommand.getNextArg();
        if (nextArg == null) {
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10003, "Font file path argument is required.");
        }
        java.lang.String nextArg2 = shellCommand.getNextArg();
        if (nextArg2 != null) {
            try {
                openFileForSystem = shellCommand.openFileForSystem(nextArg, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
                try {
                    openFileForSystem2 = shellCommand.openFileForSystem(nextArg2, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
                    try {
                    } finally {
                    }
                } finally {
                }
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Error while closing files", e);
            }
            if (openFileForSystem == null) {
                throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10001, "Failed to open font file");
            }
            if (openFileForSystem2 == null) {
                throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10002, "Failed to open signature file");
            }
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(openFileForSystem2.getFileDescriptor());
                try {
                    int available = fileInputStream.available();
                    if (available > 8192) {
                        throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10005, "Signature file is too large");
                    }
                    byte[] bArr = new byte[available];
                    if (fileInputStream.read(bArr, 0, available) != available) {
                        throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10004, "Invalid read length");
                    }
                    fileInputStream.close();
                    this.mService.update(-1, java.util.Collections.singletonList(new android.graphics.fonts.FontUpdateRequest(openFileForSystem, bArr)));
                    openFileForSystem2.close();
                    openFileForSystem.close();
                    shellCommand.getOutPrintWriter().println("Success");
                    return 0;
                } catch (java.lang.Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.IOException e2) {
                throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10004, "Failed to read signature file.", e2);
            }
        }
        throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10003, "Signature file argument is required.");
    }

    private int updateFamily(android.os.ShellCommand shellCommand) throws com.android.server.graphics.fonts.FontManagerService.SystemFontException {
        java.lang.String nextArg = shellCommand.getNextArg();
        if (nextArg == null) {
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10003, "XML file path argument is required.");
        }
        try {
            android.os.ParcelFileDescriptor openFileForSystem = shellCommand.openFileForSystem(nextArg, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
            try {
                java.util.List<android.graphics.fonts.FontUpdateRequest> parseFontFamilyUpdateXml = parseFontFamilyUpdateXml(new java.io.FileInputStream(openFileForSystem.getFileDescriptor()));
                openFileForSystem.close();
                this.mService.update(-1, parseFontFamilyUpdateXml);
                shellCommand.getOutPrintWriter().println("Success");
                return 0;
            } finally {
            }
        } catch (java.io.IOException e) {
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10006, "Failed to open XML file.", e);
        }
    }

    private static java.util.List<android.graphics.fonts.FontUpdateRequest> parseFontFamilyUpdateXml(java.io.InputStream inputStream) throws com.android.server.graphics.fonts.FontManagerService.SystemFontException {
        try {
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                int next = resolvePullParser.next();
                if (next != 1) {
                    if (next == 2) {
                        int depth = resolvePullParser.getDepth();
                        java.lang.String name = resolvePullParser.getName();
                        if (depth == 1) {
                            if (!"fontFamilyUpdateRequest".equals(name)) {
                                throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10007, "Expected <fontFamilyUpdateRequest> but got: " + name);
                            }
                        } else if (depth != 2) {
                            continue;
                        } else if ("family".equals(name)) {
                            arrayList.add(new android.graphics.fonts.FontUpdateRequest(android.graphics.fonts.FontUpdateRequest.Family.readFromXml(resolvePullParser)));
                        } else {
                            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(-10007, "Expected <family> but got: " + name);
                        }
                    }
                } else {
                    return arrayList;
                }
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            throw new com.android.server.graphics.fonts.FontManagerService.SystemFontException(0, "Failed to parse xml", e);
        }
    }

    private int clear(android.os.ShellCommand shellCommand) {
        this.mService.clearUpdates();
        shellCommand.getOutPrintWriter().println("Success");
        return 0;
    }

    private int restart(android.os.ShellCommand shellCommand) {
        this.mService.restart();
        shellCommand.getOutPrintWriter().println("Success");
        return 0;
    }

    private int status(android.os.ShellCommand shellCommand) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(shellCommand.getOutPrintWriter(), "  ");
        android.text.FontConfig systemFontConfig = this.mService.getSystemFontConfig();
        indentingPrintWriter.println("Current Version: " + systemFontConfig.getConfigVersion());
        indentingPrintWriter.println("Last Modified Date: " + java.time.LocalDateTime.ofEpochSecond(systemFontConfig.getLastModifiedTimeMillis(), 0, java.time.ZoneOffset.UTC).format(java.time.format.DateTimeFormatter.ISO_DATE_TIME));
        indentingPrintWriter.println("Number of updated font files: " + this.mService.getFontFileMap().size());
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int execCommand(@android.annotation.NonNull android.os.ShellCommand shellCommand, @android.annotation.Nullable java.lang.String str) {
        char c;
        if (str == null) {
            return shellCommand.handleDefaultCommands((java.lang.String) null);
        }
        try {
            switch (str.hashCode()) {
                case -2084349744:
                    if (str.equals("install-debug-cert")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -892481550:
                    if (str.equals("status")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -838846263:
                    if (str.equals("update")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3095028:
                    if (str.equals("dump")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 94746189:
                    if (str.equals("clear")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1097506319:
                    if (str.equals(com.android.server.am.HostingRecord.HOSTING_TYPE_RESTART)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1135462632:
                    if (str.equals("update-family")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return dump(shellCommand);
                case 1:
                    return update(shellCommand);
                case 2:
                    return updateFamily(shellCommand);
                case 3:
                    return clear(shellCommand);
                case 4:
                    return restart(shellCommand);
                case 5:
                    return status(shellCommand);
                case 6:
                    return installCert(shellCommand);
                default:
                    return shellCommand.handleDefaultCommands(str);
            }
        } catch (com.android.server.graphics.fonts.FontManagerService.SystemFontException e) {
            writeCommandResult(shellCommand, e);
            return 1;
        }
    }
}
