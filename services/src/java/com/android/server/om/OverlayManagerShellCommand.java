package com.android.server.om;

/* loaded from: classes2.dex */
final class OverlayManagerShellCommand extends android.os.ShellCommand {
    private static final java.util.Map<java.lang.String, java.lang.Integer> TYPE_MAP = java.util.Map.of("color", 28, "string", 3, "drawable", -1);
    private final android.content.Context mContext;
    private final android.content.om.IOverlayManager mInterface;

    OverlayManagerShellCommand(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.om.IOverlayManager iOverlayManager) {
        this.mContext = context;
        this.mInterface = iOverlayManager;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(@android.annotation.Nullable java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            switch (str.hashCode()) {
                case -1361113425:
                    if (str.equals("set-priority")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1298848381:
                    if (str.equals("enable")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1097094790:
                    if (str.equals("lookup")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -794624300:
                    if (str.equals("enable-exclusive")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals("list")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1135695531:
                    if (str.equals("partition-order")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1671308008:
                    if (str.equals("disable")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 2016903117:
                    if (str.equals("fabricate")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
            }
        } catch (android.os.RemoteException e) {
            errPrintWriter.println("Remote exception: " + e);
            return -1;
        } catch (java.lang.IllegalArgumentException e2) {
            errPrintWriter.println("Error: " + e2.getMessage());
            return -1;
        }
        return handleDefaultCommands(str);
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Overlay manager (overlay) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  dump [--verbose] [--user USER_ID] [[FIELD] PACKAGE[:NAME]]");
        outPrintWriter.println("    Print debugging information about the overlay manager.");
        outPrintWriter.println("    With optional parameters PACKAGE and NAME, limit output to the specified");
        outPrintWriter.println("    overlay or target. With optional parameter FIELD, limit output to");
        outPrintWriter.println("    the corresponding SettingsItem field. Field names are all lower case");
        outPrintWriter.println("    and omit the m prefix, i.e. 'userid' for SettingsItem.mUserId.");
        outPrintWriter.println("  list [--user USER_ID] [PACKAGE[:NAME]]");
        outPrintWriter.println("    Print information about target and overlay packages.");
        outPrintWriter.println("    Overlay packages are printed in priority order. With optional");
        outPrintWriter.println("    parameters PACKAGE and NAME, limit output to the specified overlay or");
        outPrintWriter.println("    target.");
        outPrintWriter.println("  enable [--user USER_ID] PACKAGE[:NAME]");
        outPrintWriter.println("    Enable overlay within or owned by PACKAGE with optional unique NAME.");
        outPrintWriter.println("  disable [--user USER_ID] PACKAGE[:NAME]");
        outPrintWriter.println("    Disable overlay within or owned by PACKAGE with optional unique NAME.");
        outPrintWriter.println("  enable-exclusive [--user USER_ID] [--category] PACKAGE");
        outPrintWriter.println("    Enable overlay within or owned by PACKAGE and disable all other overlays");
        outPrintWriter.println("    for its target package. If the --category option is given, only disables");
        outPrintWriter.println("    other overlays in the same category.");
        outPrintWriter.println("  set-priority [--user USER_ID] PACKAGE PARENT|lowest|highest");
        outPrintWriter.println("    Change the priority of the overlay to be just higher than");
        outPrintWriter.println("    the priority of PARENT If PARENT is the special keyword");
        outPrintWriter.println("    'lowest', change priority of PACKAGE to the lowest priority.");
        outPrintWriter.println("    If PARENT is the special keyword 'highest', change priority of");
        outPrintWriter.println("    PACKAGE to the highest priority.");
        outPrintWriter.println("  lookup [--user USER_ID] [--verbose] PACKAGE-TO-LOAD PACKAGE:TYPE/NAME");
        outPrintWriter.println("    Load a package and print the value of a given resource");
        outPrintWriter.println("    applying the current configuration and enabled overlays.");
        outPrintWriter.println("    For a more fine-grained alternative, use 'idmap2 lookup'.");
        outPrintWriter.println("  fabricate [--user USER_ID] [--target-name OVERLAYABLE] --target PACKAGE");
        outPrintWriter.println("            --name NAME [--file FILE] ");
        outPrintWriter.println("            PACKAGE:TYPE/NAME ENCODED-TYPE-ID/TYPE-NAME ENCODED-VALUE");
        outPrintWriter.println("    Create an overlay from a single resource. Caller must be root. Example:");
        outPrintWriter.println("      fabricate --target android --name LighterGray \\");
        outPrintWriter.println("                android:color/lighter_gray 0x1c 0xffeeeeee");
        outPrintWriter.println("  partition-order");
        outPrintWriter.println("    Print the partition order from overlay config and how this order");
        outPrintWriter.println("    got established, by default or by /product/overlay/partition_order.xml");
    }

    private int runList() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    default:
                        errPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg != null) {
                    java.util.List overlayInfosForTarget = this.mInterface.getOverlayInfosForTarget(nextArg, i);
                    if (overlayInfosForTarget.isEmpty()) {
                        android.content.om.OverlayInfo overlayInfo = this.mInterface.getOverlayInfo(nextArg, i);
                        if (overlayInfo != null) {
                            printListOverlay(outPrintWriter, overlayInfo);
                        }
                        return 0;
                    }
                    outPrintWriter.println(nextArg);
                    int size = overlayInfosForTarget.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        printListOverlay(outPrintWriter, (android.content.om.OverlayInfo) overlayInfosForTarget.get(i2));
                    }
                    return 0;
                }
                java.util.Map allOverlays = this.mInterface.getAllOverlays(i);
                for (java.lang.String str : allOverlays.keySet()) {
                    outPrintWriter.println(str);
                    java.util.List list = (java.util.List) allOverlays.get(str);
                    int size2 = list.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        printListOverlay(outPrintWriter, (android.content.om.OverlayInfo) list.get(i3));
                    }
                    outPrintWriter.println();
                }
                return 0;
            }
        }
    }

    private void printListOverlay(java.io.PrintWriter printWriter, android.content.om.OverlayInfo overlayInfo) {
        java.lang.String str;
        switch (overlayInfo.state) {
            case 2:
                str = "[ ]";
                break;
            case 3:
            case 6:
                str = "[x]";
                break;
            case 4:
            case 5:
            default:
                str = "---";
                break;
        }
        printWriter.println(java.lang.String.format("%s %s", str, overlayInfo.getOverlayIdentifier()));
    }

    private int runEnableDisable(boolean z) throws android.os.RemoteException {
        char c;
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    default:
                        errPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                this.mInterface.commit(new android.content.om.OverlayManagerTransaction.Builder().setEnabled(android.content.om.OverlayIdentifier.fromString(getNextArgRequired()), z, i).build());
                return 0;
            }
        }
    }

    private int runPartitionOrder() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Partition order (low to high priority): " + this.mInterface.getPartitionOrder());
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Established by ");
        sb.append(this.mInterface.isDefaultPartitionOrder() ? "default" : "/product/overlay/partition_order.xml");
        outPrintWriter.println(sb.toString());
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x003b, code lost:
    
        if (r6.equals("--user") != false) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runFabricate() throws android.os.RemoteException {
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        if (android.os.Binder.getCallingUid() != 0) {
            errPrintWriter.println("Error: must be root to fabricate overlays through the shell");
            return 1;
        }
        java.lang.String str = null;
        java.lang.String str2 = "";
        java.lang.String str3 = null;
        java.lang.String str4 = "";
        java.lang.String str5 = str4;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 0;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -935414873:
                        if (nextOption.equals("--target-name")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1045221602:
                        if (nextOption.equals("--config")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333013276:
                        if (nextOption.equals("--file")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333243947:
                        if (nextOption.equals("--name")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333469547:
                        break;
                    case 1519107889:
                        if (nextOption.equals("--target")) {
                            c = 1;
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
                        android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 1:
                        str4 = getNextArgRequired();
                        break;
                    case 2:
                        str5 = getNextArgRequired();
                        break;
                    case 3:
                        str2 = getNextArgRequired();
                        break;
                    case 4:
                        str = getNextArgRequired();
                        break;
                    case 5:
                        str3 = getNextArgRequired();
                        break;
                    default:
                        errPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                if (str2.isEmpty()) {
                    errPrintWriter.println("Error: Missing required arg '--name'");
                    return 1;
                }
                if (str4.isEmpty()) {
                    errPrintWriter.println("Error: Missing required arg '--target'");
                    return 1;
                }
                if (str != null && getRemainingArgsCount() > 0) {
                    errPrintWriter.println("Error: When passing --file don't pass resource name, type, and value as well");
                    return 1;
                }
                android.content.om.FabricatedOverlay fabricatedOverlay = new android.content.om.FabricatedOverlay(str2, str4);
                fabricatedOverlay.setTargetOverlayable(str5);
                fabricatedOverlay.setOwningPackage(com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME);
                if (str != null) {
                    int addOverlayValuesFromXml = addOverlayValuesFromXml(fabricatedOverlay, str4, str);
                    if (addOverlayValuesFromXml != 0) {
                        return addOverlayValuesFromXml;
                    }
                } else if (addOverlayValue(fabricatedOverlay, getNextArgRequired(), getNextArgRequired(), java.lang.String.join(" ", peekRemainingArgs()), str3) != 0) {
                    return 1;
                }
                this.mInterface.commit(new android.content.om.OverlayManagerTransaction.Builder().registerFabricatedOverlay(fabricatedOverlay).build());
                return 0;
            }
        }
    }

    private int addOverlayValuesFromXml(android.content.om.FabricatedOverlay fabricatedOverlay, java.lang.String str, java.lang.String str2) {
        int next;
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        java.io.File file = new java.io.File(str2);
        if (!file.exists()) {
            errPrintWriter.println("Error: File does not exist");
            return 1;
        }
        if (!file.canRead()) {
            errPrintWriter.println("Error: File is unreadable");
            return 1;
        }
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
            try {
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                do {
                    next = resolvePullParser.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                resolvePullParser.require(2, (java.lang.String) null, "overlay");
                while (true) {
                    int next2 = resolvePullParser.next();
                    if (next2 == 1) {
                        fileInputStream.close();
                        return 0;
                    }
                    if (next2 == 2) {
                        java.lang.String name = resolvePullParser.getName();
                        if (!name.equals(com.android.server.pm.Settings.TAG_ITEM)) {
                            errPrintWriter.println(android.text.TextUtils.formatSimple("Error: Unexpected tag: %s at line %d", new java.lang.Object[]{name, java.lang.Integer.valueOf(resolvePullParser.getLineNumber())}));
                        } else {
                            if (!resolvePullParser.isEmptyElementTag()) {
                                errPrintWriter.println("Error: item tag must be empty");
                                fileInputStream.close();
                                return 1;
                            }
                            java.lang.String attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, "target");
                            if (android.text.TextUtils.isEmpty(attributeValue)) {
                                errPrintWriter.println("Error: target name missing at line " + resolvePullParser.getLineNumber());
                                fileInputStream.close();
                                return 1;
                            }
                            int indexOf = attributeValue.indexOf(47);
                            if (indexOf < 0) {
                                errPrintWriter.println("Error: target malformed, missing '/' at line " + resolvePullParser.getLineNumber());
                                fileInputStream.close();
                                return 1;
                            }
                            java.lang.String substring = attributeValue.substring(0, indexOf);
                            java.lang.String attributeValue2 = resolvePullParser.getAttributeValue((java.lang.String) null, "value");
                            if (android.text.TextUtils.isEmpty(attributeValue2)) {
                                errPrintWriter.println("Error: value missing at line " + resolvePullParser.getLineNumber());
                                fileInputStream.close();
                                return 1;
                            }
                            if (addOverlayValue(fabricatedOverlay, str + ':' + attributeValue, substring, attributeValue2, resolvePullParser.getAttributeValue((java.lang.String) null, "config")) != 0) {
                                fileInputStream.close();
                                return 1;
                            }
                        }
                    }
                }
            } finally {
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return 1;
        } catch (org.xmlpull.v1.XmlPullParserException e2) {
            e2.printStackTrace();
            return 1;
        }
    }

    private int addOverlayValue(android.content.om.FabricatedOverlay fabricatedOverlay, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        int parseUnsignedInt;
        int parseUnsignedInt2;
        java.lang.String lowerCase = str2.toLowerCase(java.util.Locale.getDefault());
        if (TYPE_MAP.containsKey(lowerCase)) {
            parseUnsignedInt = TYPE_MAP.get(lowerCase).intValue();
        } else if (lowerCase.startsWith("0x")) {
            parseUnsignedInt = java.lang.Integer.parseUnsignedInt(lowerCase.substring(2), 16);
        } else {
            parseUnsignedInt = java.lang.Integer.parseUnsignedInt(lowerCase);
        }
        if (parseUnsignedInt == 3) {
            fabricatedOverlay.setResourceValue(str, parseUnsignedInt, str3, str4);
            return 0;
        }
        if (parseUnsignedInt < 0) {
            android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(str3, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
            if (str3.endsWith(".9.png")) {
                fabricatedOverlay.setNinePatchResourceValue(str, openFileForSystem, str4);
                return 0;
            }
            fabricatedOverlay.setResourceValue(str, openFileForSystem, str4);
            return 0;
        }
        if (str3.startsWith("0x")) {
            parseUnsignedInt2 = java.lang.Integer.parseUnsignedInt(str3.substring(2), 16);
        } else {
            parseUnsignedInt2 = java.lang.Integer.parseUnsignedInt(str3);
        }
        fabricatedOverlay.setResourceValue(str, parseUnsignedInt, parseUnsignedInt2, str4);
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    private int runEnableExclusive() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        byte b = false;
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 66265758:
                        if (nextOption.equals("--category")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 0;
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
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 1:
                        b = true;
                        break;
                    default:
                        errPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                return b != false ? !this.mInterface.setEnabledExclusiveInCategory(nextArgRequired, i) ? 1 : 0 : !this.mInterface.setEnabledExclusive(nextArgRequired, true, i) ? 1 : 0;
            }
        }
    }

    private int runSetPriority() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    default:
                        errPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                java.lang.String nextArgRequired2 = getNextArgRequired();
                return "highest".equals(nextArgRequired2) ? !this.mInterface.setHighestPriority(nextArgRequired, i) ? 1 : 0 : "lowest".equals(nextArgRequired2) ? !this.mInterface.setLowestPriority(nextArgRequired, i) ? 1 : 0 : !this.mInterface.setPriority(nextArgRequired, nextArgRequired2, i) ? 1 : 0;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runLookup() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        int i = 0;
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption == null) {
                java.lang.String nextArgRequired = getNextArgRequired();
                java.lang.String nextArgRequired2 = getNextArgRequired();
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(.*?):(.*?)/(.*?)").matcher(nextArgRequired2);
                if (!matcher.matches()) {
                    errPrintWriter.println("Error: bad resource name, doesn't match package:type/name");
                    return 1;
                }
                try {
                    android.content.res.Resources resourcesForApplication = this.mContext.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getResourcesForApplication(nextArgRequired);
                    android.content.res.AssetManager assets = resourcesForApplication.getAssets();
                    try {
                        assets.setResourceResolutionLoggingEnabled(true);
                        try {
                            android.util.TypedValue typedValue = new android.util.TypedValue();
                            resourcesForApplication.getValue(nextArgRequired2, typedValue, false);
                            java.lang.CharSequence coerceToString = typedValue.coerceToString();
                            java.lang.String lastResourceResolution = assets.getLastResourceResolution();
                            resourcesForApplication.getValue(nextArgRequired2, typedValue, true);
                            java.lang.CharSequence coerceToString2 = typedValue.coerceToString();
                            if (z) {
                                outPrintWriter.println(lastResourceResolution);
                            }
                            if (coerceToString.equals(coerceToString2)) {
                                outPrintWriter.println(coerceToString);
                            } else {
                                outPrintWriter.println(((java.lang.Object) coerceToString) + " -> " + ((java.lang.Object) coerceToString2));
                            }
                            assets.setResourceResolutionLoggingEnabled(false);
                            return 0;
                        } catch (android.content.res.Resources.NotFoundException e) {
                            try {
                                int identifier = resourcesForApplication.getIdentifier(matcher.group(3), matcher.group(2), matcher.group(1));
                                if (identifier == 0) {
                                    throw new android.content.res.Resources.NotFoundException();
                                }
                                android.content.res.TypedArray obtainTypedArray = resourcesForApplication.obtainTypedArray(identifier);
                                if (z) {
                                    outPrintWriter.println(assets.getLastResourceResolution());
                                }
                                android.util.TypedValue typedValue2 = new android.util.TypedValue();
                                for (int i2 = 0; i2 < obtainTypedArray.length(); i2++) {
                                    obtainTypedArray.getValue(i2, typedValue2);
                                    outPrintWriter.println(typedValue2.coerceToString());
                                }
                                obtainTypedArray.recycle();
                                assets.setResourceResolutionLoggingEnabled(false);
                                return 0;
                            } catch (android.content.res.Resources.NotFoundException e2) {
                                errPrintWriter.println("Error: failed to get the resource " + nextArgRequired2);
                                assets.setResourceResolutionLoggingEnabled(false);
                                return 1;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        assets.setResourceResolutionLoggingEnabled(false);
                        throw th;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                    errPrintWriter.println(java.lang.String.format("Error: failed to get resources for package %s for user %d", nextArgRequired, java.lang.Integer.valueOf(i)));
                    return 1;
                }
            }
            switch (nextOption.hashCode()) {
                case 1333469547:
                    if (nextOption.equals("--user")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1737088994:
                    if (nextOption.equals("--verbose")) {
                        c = 1;
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
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    break;
                case 1:
                    z = true;
                    break;
                default:
                    errPrintWriter.println("Error: Unknown option: " + nextOption);
                    return 1;
            }
        }
    }
}
