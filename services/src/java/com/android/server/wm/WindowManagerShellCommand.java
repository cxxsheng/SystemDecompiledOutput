package com.android.server.wm;

/* loaded from: classes3.dex */
public class WindowManagerShellCommand extends android.os.ShellCommand {
    private final android.view.IWindowManager mInterface;
    private final com.android.server.wm.WindowManagerService mInternal;
    private final com.android.server.wm.LetterboxConfiguration mLetterboxConfiguration;

    public WindowManagerShellCommand(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mInterface = windowManagerService;
        this.mInternal = windowManagerService;
        this.mLetterboxConfiguration = windowManagerService.mLetterboxConfiguration;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -2001980078:
                    if (str.equals("get-letterbox-style")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -1959253708:
                    if (str.equals("get-multi-window-config")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case -1829173266:
                    if (str.equals("get-ignore-orientation-request")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1693379742:
                    if (str.equals("set-ignore-orientation-request")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -1067396926:
                    if (str.equals("tracing")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1032601556:
                    if (str.equals("disable-blur")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case -1014709755:
                    if (str.equals("dump-visible-window-views")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -336752166:
                    if (str.equals("folded-area")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -229462135:
                    if (str.equals("dismiss-keyguard")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 3530753:
                    if (str.equals("size")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 93018176:
                    if (str.equals("set-multi-window-config")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 108404047:
                    if (str.equals("reset")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 109403696:
                    if (str.equals("shell")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 188660544:
                    if (str.equals("user-rotation")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 342281055:
                    if (str.equals("logging")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 344144277:
                    if (str.equals("set-sandbox-display-apis")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 731885899:
                    if (str.equals("reset-letterbox-style")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 749259358:
                    if (str.equals("set-letterbox-style")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1336606893:
                    if (str.equals("reset-multi-window-config")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 1552717032:
                    if (str.equals("density")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1583955111:
                    if (str.equals("fixed-to-user-rotation")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1910897543:
                    if (str.equals("scaling")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 6:
                    com.android.internal.protolog.LegacyProtoLogImpl singleInstance = com.android.internal.protolog.ProtoLogImpl_1545807451.getSingleInstance();
                    if (singleInstance instanceof com.android.internal.protolog.LegacyProtoLogImpl) {
                        int onShellCommand = singleInstance.onShellCommand(this);
                        if (onShellCommand != 0) {
                            outPrintWriter.println("Not handled, please use `adb shell dumpsys activity service SystemUIService WMShell` if you are looking for ProtoLog in WMShell");
                        }
                        break;
                    } else {
                        outPrintWriter.println("Command not supported. Only supported when using legacy ProtoLog.");
                        break;
                    }
            }
        } catch (android.os.RemoteException e) {
            outPrintWriter.println("Remote exception: " + e);
            return -1;
        }
        return handleDefaultCommands(str);
    }

    private int getDisplayId(java.lang.String str) {
        if (!"-d".equals(str)) {
            str = getNextOption();
        }
        if (str != null && "-d".equals(str)) {
            try {
                return java.lang.Integer.parseInt(getNextArgRequired());
            } catch (java.lang.NumberFormatException e) {
                getErrPrintWriter().println("Error: bad number " + e);
            } catch (java.lang.IllegalArgumentException e2) {
                getErrPrintWriter().println("Error: " + e2);
            }
        }
        return 0;
    }

    private void printInitialDisplaySize(java.io.PrintWriter printWriter, int i) {
        android.graphics.Point point = new android.graphics.Point();
        android.graphics.Point point2 = new android.graphics.Point();
        try {
            this.mInterface.getInitialDisplaySize(i, point);
            this.mInterface.getBaseDisplaySize(i, point2);
            printWriter.println("Physical size: " + point.x + "x" + point.y);
            if (!point.equals(point2)) {
                printWriter.println("Override size: " + point2.x + "x" + point2.y);
            }
        } catch (android.os.RemoteException e) {
            printWriter.println("Remote exception: " + e);
        }
    }

    private int runDisplaySize(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int parseDimension;
        java.lang.String nextArg = getNextArg();
        int displayId = getDisplayId(nextArg);
        if (nextArg == null) {
            printInitialDisplaySize(printWriter, displayId);
            return 0;
        }
        if ("-d".equals(nextArg)) {
            printInitialDisplaySize(printWriter, displayId);
            return 0;
        }
        int i = -1;
        if ("reset".equals(nextArg)) {
            parseDimension = -1;
        } else {
            int indexOf = nextArg.indexOf(120);
            if (indexOf <= 0 || indexOf >= nextArg.length() - 1) {
                getErrPrintWriter().println("Error: bad size " + nextArg);
                return -1;
            }
            java.lang.String substring = nextArg.substring(0, indexOf);
            java.lang.String substring2 = nextArg.substring(indexOf + 1);
            try {
                int parseDimension2 = parseDimension(substring, displayId);
                parseDimension = parseDimension(substring2, displayId);
                i = parseDimension2;
            } catch (java.lang.NumberFormatException e) {
                getErrPrintWriter().println("Error: bad number " + e);
                return -1;
            }
        }
        if (i >= 0 && parseDimension >= 0) {
            this.mInterface.setForcedDisplaySize(displayId, i, parseDimension);
        } else {
            this.mInterface.clearForcedDisplaySize(displayId);
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSetBlurDisabled(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        char c;
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            printWriter.println("Blur supported on device: " + android.view.CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED);
            printWriter.println("Blur enabled: " + this.mInternal.mBlurController.getBlurEnabled());
            return 0;
        }
        int i = 1;
        switch (nextArg.hashCode()) {
            case 48:
                if (nextArg.equals("0")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (nextArg.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3569038:
                if (nextArg.equals("true")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (nextArg.equals("false")) {
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
            case 1:
                break;
            case 2:
            case 3:
                i = 0;
                break;
            default:
                getErrPrintWriter().println("Error: expected true, 1, false, 0, but got " + nextArg);
                return -1;
        }
        android.provider.Settings.Global.putInt(this.mInternal.mContext.getContentResolver(), "disable_window_blurs", i);
        return 0;
    }

    private void printInitialDisplayDensity(java.io.PrintWriter printWriter, int i) {
        try {
            int initialDisplayDensity = this.mInterface.getInitialDisplayDensity(i);
            int baseDisplayDensity = this.mInterface.getBaseDisplayDensity(i);
            printWriter.println("Physical density: " + initialDisplayDensity);
            if (initialDisplayDensity != baseDisplayDensity) {
                printWriter.println("Override density: " + baseDisplayDensity);
            }
        } catch (android.os.RemoteException e) {
            printWriter.println("Remote exception: " + e);
        }
    }

    private int runDisplayDensity(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int displayIdByUniqueId;
        java.lang.String nextArg = getNextArg();
        java.lang.String nextOption = getNextOption();
        java.lang.String nextArg2 = getNextArg();
        int i = -1;
        if ("-d".equals(nextOption) && nextArg2 != null) {
            try {
                displayIdByUniqueId = java.lang.Integer.parseInt(nextArg2);
            } catch (java.lang.NumberFormatException e) {
                getErrPrintWriter().println("Error: bad number " + e);
            }
        } else {
            if ("-u".equals(nextOption) && nextArg2 != null) {
                displayIdByUniqueId = this.mInterface.getDisplayIdByUniqueId(nextArg2);
                if (displayIdByUniqueId == -1) {
                    getErrPrintWriter().println("Error: the uniqueId is invalid ");
                    return -1;
                }
            }
            displayIdByUniqueId = 0;
        }
        if (nextArg == null) {
            printInitialDisplayDensity(printWriter, displayIdByUniqueId);
            return 0;
        }
        if ("-d".equals(nextArg)) {
            printInitialDisplayDensity(printWriter, displayIdByUniqueId);
            return 0;
        }
        if (!"reset".equals(nextArg)) {
            try {
                int parseInt = java.lang.Integer.parseInt(nextArg);
                if (parseInt >= 72) {
                    i = parseInt;
                } else {
                    getErrPrintWriter().println("Error: density must be >= 72");
                    return -1;
                }
            } catch (java.lang.NumberFormatException e2) {
                getErrPrintWriter().println("Error: bad number " + e2);
                return -1;
            }
        }
        if (i > 0) {
            this.mInterface.setForcedDisplayDensityForUser(displayIdByUniqueId, i, -2);
        } else {
            this.mInterface.clearForcedDisplayDensityForUser(displayIdByUniqueId, -2);
        }
        return 0;
    }

    private void printFoldedArea(java.io.PrintWriter printWriter) {
        android.graphics.Rect foldedArea = this.mInternal.getFoldedArea();
        if (foldedArea.isEmpty()) {
            printWriter.println("Folded area: none");
            return;
        }
        printWriter.println("Folded area: " + foldedArea.left + "," + foldedArea.top + "," + foldedArea.right + "," + foldedArea.bottom);
    }

    private int runDisplayFoldedArea(java.io.PrintWriter printWriter) {
        java.lang.String nextArg = getNextArg();
        android.graphics.Rect rect = new android.graphics.Rect();
        if (nextArg == null) {
            printFoldedArea(printWriter);
            return 0;
        }
        if ("reset".equals(nextArg)) {
            rect.setEmpty();
        } else {
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(-?\\d+),(-?\\d+),(-?\\d+),(-?\\d+)").matcher(nextArg);
            if (!matcher.matches()) {
                getErrPrintWriter().println("Error: area should be LEFT,TOP,RIGHT,BOTTOM");
                return -1;
            }
            rect.set(java.lang.Integer.parseInt(matcher.group(1)), java.lang.Integer.parseInt(matcher.group(2)), java.lang.Integer.parseInt(matcher.group(3)), java.lang.Integer.parseInt(matcher.group(4)));
        }
        this.mInternal.setOverrideFoldedArea(rect);
        return 0;
    }

    private int runDisplayScaling(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        if (com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_AUTO.equals(nextArgRequired)) {
            this.mInterface.setForcedDisplayScalingMode(getDisplayId(nextArgRequired), 0);
        } else if ("off".equals(nextArgRequired)) {
            this.mInterface.setForcedDisplayScalingMode(getDisplayId(nextArgRequired), 1);
        } else {
            getErrPrintWriter().println("Error: scaling must be 'auto' or 'off'");
            return -1;
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSandboxDisplayApis(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i;
        char c;
        java.lang.String nextArgRequired = getNextArgRequired();
        if (!"-d".equals(nextArgRequired)) {
            i = 0;
        } else {
            i = java.lang.Integer.parseInt(getNextArgRequired());
            nextArgRequired = getNextArgRequired();
        }
        boolean z = true;
        switch (nextArgRequired.hashCode()) {
            case 48:
                if (nextArgRequired.equals("0")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (nextArgRequired.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3569038:
                if (nextArgRequired.equals("true")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (nextArgRequired.equals("false")) {
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
            case 1:
                break;
            case 2:
            case 3:
                z = false;
                break;
            default:
                getErrPrintWriter().println("Error: expecting true, 1, false, 0, but we get " + nextArgRequired);
                return -1;
        }
        this.mInternal.setSandboxDisplayApis(i, z);
        return 0;
    }

    private int runDismissKeyguard(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInterface.dismissKeyguard((com.android.internal.policy.IKeyguardDismissCallback) null, (java.lang.CharSequence) null);
        return 0;
    }

    private int parseDimension(java.lang.String str, int i) throws java.lang.NumberFormatException {
        int i2;
        if (str.endsWith("px")) {
            return java.lang.Integer.parseInt(str.substring(0, str.length() - 2));
        }
        if (str.endsWith("dp")) {
            try {
                i2 = this.mInterface.getBaseDisplayDensity(i);
            } catch (android.os.RemoteException e) {
                i2 = 160;
            }
            return (java.lang.Integer.parseInt(str.substring(0, str.length() - 2)) * i2) / 160;
        }
        return java.lang.Integer.parseInt(str);
    }

    private int runDisplayUserRotation(java.io.PrintWriter printWriter) {
        int i;
        int parseInt;
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            return printDisplayUserRotation(printWriter, 0);
        }
        if (!"-d".equals(nextArg)) {
            i = 0;
        } else {
            i = java.lang.Integer.parseInt(getNextArgRequired());
            nextArg = getNextArg();
        }
        if (nextArg == null) {
            return printDisplayUserRotation(printWriter, i);
        }
        if ("free".equals(nextArg)) {
            this.mInternal.thawDisplayRotation(i, "WindowManagerShellCommand#free");
            return 0;
        }
        if (!"lock".equals(nextArg)) {
            getErrPrintWriter().println("Error: argument needs to be either -d, free or lock.");
            return -1;
        }
        java.lang.String nextArg2 = getNextArg();
        if (nextArg2 == null) {
            parseInt = -1;
        } else {
            try {
                parseInt = java.lang.Integer.parseInt(nextArg2);
            } catch (java.lang.IllegalArgumentException e) {
                getErrPrintWriter().println("Error: " + e.getMessage());
                return -1;
            }
        }
        this.mInternal.freezeDisplayRotation(i, parseInt, "WindowManagerShellCommand#lock");
        return 0;
    }

    private int printDisplayUserRotation(java.io.PrintWriter printWriter, int i) {
        int displayUserRotation = this.mInternal.getDisplayUserRotation(i);
        if (displayUserRotation < 0) {
            getErrPrintWriter().println("Error: check logcat for more details.");
            return -1;
        }
        if (!this.mInternal.isDisplayRotationFrozen(i)) {
            printWriter.println("free");
            return 0;
        }
        printWriter.print("lock ");
        printWriter.println(displayUserRotation);
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runFixedToUserRotation(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i;
        boolean z;
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            printFixedToUserRotation(printWriter, 0);
            return 0;
        }
        if (!"-d".equals(nextArg)) {
            i = 0;
        } else {
            i = java.lang.Integer.parseInt(getNextArgRequired());
            nextArg = getNextArg();
        }
        if (nextArg == null) {
            return printFixedToUserRotation(printWriter, i);
        }
        int i2 = 1;
        switch (nextArg.hashCode()) {
            case -1609594047:
                if (nextArg.equals(com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED)) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 270940796:
                if (nextArg.equals(com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED)) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 1544803905:
                if (nextArg.equals("default")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                i2 = 2;
                break;
            case true:
                break;
            case true:
                i2 = 0;
                break;
            default:
                getErrPrintWriter().println("Error: expecting enabled, disabled or default, but we get " + nextArg);
                return -1;
        }
        this.mInterface.setFixedToUserRotation(i, i2);
        return 0;
    }

    private int printFixedToUserRotation(java.io.PrintWriter printWriter, int i) {
        switch (this.mInternal.getFixedToUserRotation(i)) {
            case 0:
                printWriter.println("default");
                return 0;
            case 1:
                printWriter.println(com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
                return 0;
            case 2:
                printWriter.println(com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED);
                return 0;
            default:
                getErrPrintWriter().println("Error: check logcat for more details.");
                return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSetIgnoreOrientationRequest(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i;
        char c;
        java.lang.String nextArgRequired = getNextArgRequired();
        if (!"-d".equals(nextArgRequired)) {
            i = 0;
        } else {
            i = java.lang.Integer.parseInt(getNextArgRequired());
            nextArgRequired = getNextArgRequired();
        }
        boolean z = true;
        switch (nextArgRequired.hashCode()) {
            case 48:
                if (nextArgRequired.equals("0")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (nextArgRequired.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3569038:
                if (nextArgRequired.equals("true")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (nextArgRequired.equals("false")) {
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
            case 1:
                break;
            case 2:
            case 3:
                z = false;
                break;
            default:
                getErrPrintWriter().println("Error: expecting true, 1, false, 0, but we get " + nextArgRequired);
                return -1;
        }
        this.mInterface.setIgnoreOrientationRequest(i, z);
        return 0;
    }

    private int runGetIgnoreOrientationRequest(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i;
        if (!"-d".equals(getNextArg())) {
            i = 0;
        } else {
            i = java.lang.Integer.parseInt(getNextArgRequired());
        }
        printWriter.println("ignoreOrientationRequest " + this.mInternal.getIgnoreOrientationRequest(i) + " for displayId=" + i);
        return 0;
    }

    private void dumpLocalWindowAsync(final android.view.IWindow iWindow, final android.os.ParcelFileDescriptor parcelFileDescriptor) {
        com.android.server.IoThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowManagerShellCommand.this.lambda$dumpLocalWindowAsync$0(iWindow, parcelFileDescriptor);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpLocalWindowAsync$0(android.view.IWindow iWindow, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                try {
                    iWindow.executeCommand("DUMP_ENCODED", (java.lang.String) null, parcelFileDescriptor);
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    private int runDumpVisibleWindowViews(java.io.PrintWriter printWriter) {
        final int i;
        if (!this.mInternal.checkCallingPermission("android.permission.DUMP", "runDumpVisibleWindowViews()")) {
            throw new java.lang.SecurityException("Requires DUMP permission");
        }
        try {
            java.util.zip.ZipOutputStream zipOutputStream = new java.util.zip.ZipOutputStream(getRawOutputStream());
            try {
                final java.util.ArrayList arrayList = new java.util.ArrayList();
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        com.android.server.wm.RecentTasks recentTasks = this.mInternal.mAtmService.getRecentTasks();
                        if (recentTasks != null) {
                            i = recentTasks.getRecentsComponentUid();
                        } else {
                            i = -1;
                        }
                        this.mInternal.mRoot.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.wm.WindowManagerShellCommand.this.lambda$runDumpVisibleWindowViews$1(i, arrayList, (com.android.server.wm.WindowState) obj);
                            }
                        }, false);
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    android.util.Pair pair = (android.util.Pair) it.next();
                    try {
                        byte[] bArr = ((com.android.internal.os.ByteTransferPipe) pair.second).get();
                        zipOutputStream.putNextEntry(new java.util.zip.ZipEntry((java.lang.String) pair.first));
                        zipOutputStream.write(bArr);
                    } catch (java.io.IOException e) {
                    }
                }
                zipOutputStream.close();
            } finally {
            }
        } catch (java.io.IOException e2) {
            printWriter.println("Error fetching dump " + e2.getMessage());
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runDumpVisibleWindowViews$1(int i, java.util.ArrayList arrayList, com.android.server.wm.WindowState windowState) {
        boolean z = windowState.getUid() == i;
        if (windowState.isVisible() || z) {
            com.android.internal.os.ByteTransferPipe byteTransferPipe = null;
            try {
                com.android.internal.os.ByteTransferPipe byteTransferPipe2 = new com.android.internal.os.ByteTransferPipe();
                try {
                    android.os.ParcelFileDescriptor writeFd = byteTransferPipe2.getWriteFd();
                    if (!windowState.isClientLocal()) {
                        windowState.mClient.executeCommand("DUMP_ENCODED", (java.lang.String) null, writeFd);
                    } else {
                        dumpLocalWindowAsync(windowState.mClient, writeFd);
                    }
                    arrayList.add(android.util.Pair.create(windowState.getName(), byteTransferPipe2));
                } catch (android.os.RemoteException | java.io.IOException e) {
                    byteTransferPipe = byteTransferPipe2;
                    if (byteTransferPipe != null) {
                        byteTransferPipe.kill();
                    }
                }
            } catch (android.os.RemoteException | java.io.IOException e2) {
            }
        }
    }

    private int runSetFixedOrientationLetterboxAspectRatio(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        try {
            float parseFloat = java.lang.Float.parseFloat(getNextArgRequired());
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mLetterboxConfiguration.setFixedOrientationLetterboxAspectRatio(parseFloat);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: bad aspect ratio format " + e);
            return -1;
        } catch (java.lang.IllegalArgumentException e2) {
            getErrPrintWriter().println("Error: aspect ratio should be provided as an argument " + e2);
            return -1;
        }
    }

    private int runSetDefaultMinAspectRatioForUnresizableApps(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        try {
            float parseFloat = java.lang.Float.parseFloat(getNextArgRequired());
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mLetterboxConfiguration.setDefaultMinAspectRatioForUnresizableApps(parseFloat);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: bad aspect ratio format " + e);
            return -1;
        } catch (java.lang.IllegalArgumentException e2) {
            getErrPrintWriter().println("Error: aspect ratio should be provided as an argument " + e2);
            return -1;
        }
    }

    private int runSetLetterboxActivityCornersRadius(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        try {
            int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mLetterboxConfiguration.setLetterboxActivityCornersRadius(parseInt);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: bad corners radius format " + e);
            return -1;
        } catch (java.lang.IllegalArgumentException e2) {
            getErrPrintWriter().println("Error: corners radius should be provided as an argument " + e2);
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSetLetterboxBackgroundType(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock;
        try {
            java.lang.String nextArgRequired = getNextArgRequired();
            int i = 2;
            switch (nextArgRequired.hashCode()) {
                case -1700528003:
                    if (nextArgRequired.equals("app_color_background_floating")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                case -231186968:
                    if (nextArgRequired.equals("app_color_background")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 1216433359:
                    if (nextArgRequired.equals("solid_color")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 1474694658:
                    if (nextArgRequired.equals("wallpaper")) {
                        z = 3;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    i = 0;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            this.mLetterboxConfiguration.setLetterboxBackgroundTypeOverride(i);
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                case true:
                    i = 1;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                case true:
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                case true:
                    i = 3;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                default:
                    getErrPrintWriter().println("Error: 'solid_color', 'app_color_background' or 'wallpaper' should be provided as an argument");
                    return -1;
            }
        } catch (java.lang.IllegalArgumentException e) {
            getErrPrintWriter().println("Error: 'solid_color', 'app_color_background' or 'wallpaper' should be provided as an argument" + e);
            return -1;
        }
    }

    private int runSetLetterboxBackgroundColorResource(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        try {
            int identifier = this.mInternal.mContext.getResources().getIdentifier(getNextArgRequired(), "color", "com.android.internal");
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mLetterboxConfiguration.setLetterboxBackgroundColorResourceId(identifier);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        } catch (android.content.res.Resources.NotFoundException e) {
            getErrPrintWriter().println("Error: color in '@android:color/resource_name' format should be provided as an argument " + e);
            return -1;
        }
    }

    private int runSetLetterboxBackgroundColor(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        try {
            android.graphics.Color valueOf = android.graphics.Color.valueOf(android.graphics.Color.parseColor(getNextArgRequired()));
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mLetterboxConfiguration.setLetterboxBackgroundColor(valueOf);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        } catch (java.lang.IllegalArgumentException e) {
            getErrPrintWriter().println("Error: color in #RRGGBB format should be provided as an argument " + e);
            return -1;
        }
    }

    private int runSetLetterboxBackgroundWallpaperBlurRadius(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        try {
            int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mLetterboxConfiguration.setLetterboxBackgroundWallpaperBlurRadiusPx((int) android.util.TypedValue.applyDimension(1, parseInt, this.mInternal.mContext.getResources().getDisplayMetrics()));
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: blur radius format " + e);
            return -1;
        } catch (java.lang.IllegalArgumentException e2) {
            getErrPrintWriter().println("Error: blur radius should be provided as an argument " + e2);
            return -1;
        }
    }

    private int runSetLetterboxBackgroundWallpaperDarkScrimAlpha(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        try {
            float parseFloat = java.lang.Float.parseFloat(getNextArgRequired());
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mLetterboxConfiguration.setLetterboxBackgroundWallpaperDarkScrimAlpha(parseFloat);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: bad alpha format " + e);
            return -1;
        } catch (java.lang.IllegalArgumentException e2) {
            getErrPrintWriter().println("Error: alpha should be provided as an argument " + e2);
            return -1;
        }
    }

    private int runSetLetterboxHorizontalPositionMultiplier(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        try {
            float parseFloat = java.lang.Float.parseFloat(getNextArgRequired());
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mLetterboxConfiguration.setLetterboxHorizontalPositionMultiplier(parseFloat);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: bad multiplier format " + e);
            return -1;
        } catch (java.lang.IllegalArgumentException e2) {
            getErrPrintWriter().println("Error: multiplier should be provided as an argument " + e2);
            return -1;
        }
    }

    private int runSetLetterboxVerticalPositionMultiplier(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        try {
            float parseFloat = java.lang.Float.parseFloat(getNextArgRequired());
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mLetterboxConfiguration.setLetterboxVerticalPositionMultiplier(parseFloat);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: bad multiplier format " + e);
            return -1;
        } catch (java.lang.IllegalArgumentException e2) {
            getErrPrintWriter().println("Error: multiplier should be provided as an argument " + e2);
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSetLetterboxDefaultPositionForHorizontalReachability(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock;
        try {
            java.lang.String nextArgRequired = getNextArgRequired();
            int i = 1;
            switch (nextArgRequired.hashCode()) {
                case -1364013995:
                    if (nextArgRequired.equals("center")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 3317767:
                    if (nextArgRequired.equals("left")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 108511772:
                    if (nextArgRequired.equals("right")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    i = 0;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            this.mLetterboxConfiguration.setDefaultPositionForHorizontalReachability(i);
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                case true:
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                case true:
                    i = 2;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                default:
                    getErrPrintWriter().println("Error: 'left', 'center' or 'right' are expected as an argument");
                    return -1;
            }
        } catch (java.lang.IllegalArgumentException e) {
            getErrPrintWriter().println("Error: 'left', 'center' or 'right' are expected as an argument" + e);
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSetLetterboxDefaultPositionForVerticalReachability(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock;
        try {
            java.lang.String nextArgRequired = getNextArgRequired();
            int i = 2;
            switch (nextArgRequired.hashCode()) {
                case -1383228885:
                    if (nextArgRequired.equals("bottom")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                case -1364013995:
                    if (nextArgRequired.equals("center")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 115029:
                    if (nextArgRequired.equals("top")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    i = 0;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            this.mLetterboxConfiguration.setDefaultPositionForVerticalReachability(i);
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                case true:
                    i = 1;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                case true:
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                default:
                    getErrPrintWriter().println("Error: 'top', 'center' or 'bottom' are expected as an argument");
                    return -1;
            }
        } catch (java.lang.IllegalArgumentException e) {
            getErrPrintWriter().println("Error: 'top', 'center' or 'bottom' are expected as an argument" + e);
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSetPersistentLetterboxPositionForHorizontalReachability(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock;
        try {
            java.lang.String nextArgRequired = getNextArgRequired();
            int i = 1;
            switch (nextArgRequired.hashCode()) {
                case -1364013995:
                    if (nextArgRequired.equals("center")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 3317767:
                    if (nextArgRequired.equals("left")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 108511772:
                    if (nextArgRequired.equals("right")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    i = 0;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            this.mLetterboxConfiguration.setPersistentLetterboxPositionForHorizontalReachability(false, i);
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                case true:
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                case true:
                    i = 2;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                default:
                    getErrPrintWriter().println("Error: 'left', 'center' or 'right' are expected as an argument");
                    return -1;
            }
        } catch (java.lang.IllegalArgumentException e) {
            getErrPrintWriter().println("Error: 'left', 'center' or 'right' are expected as an argument" + e);
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSetPersistentLetterboxPositionForVerticalReachability(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock;
        try {
            java.lang.String nextArgRequired = getNextArgRequired();
            int i = 2;
            switch (nextArgRequired.hashCode()) {
                case -1383228885:
                    if (nextArgRequired.equals("bottom")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                case -1364013995:
                    if (nextArgRequired.equals("center")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 115029:
                    if (nextArgRequired.equals("top")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    i = 0;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            this.mLetterboxConfiguration.setPersistentLetterboxPositionForVerticalReachability(false, i);
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                case true:
                    i = 1;
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                case true:
                    windowManagerGlobalLock = this.mInternal.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                    }
                    break;
                default:
                    getErrPrintWriter().println("Error: 'top', 'center' or 'bottom' are expected as an argument");
                    return -1;
            }
        } catch (java.lang.IllegalArgumentException e) {
            getErrPrintWriter().println("Error: 'top', 'center' or 'bottom' are expected as an argument" + e);
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSetBooleanFlag(java.io.PrintWriter printWriter, java.util.function.Consumer<java.lang.Boolean> consumer) throws android.os.RemoteException {
        char c;
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            boolean z = true;
            switch (nextArg.hashCode()) {
                case 48:
                    if (nextArg.equals("0")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 49:
                    if (nextArg.equals("1")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3569038:
                    if (nextArg.equals("true")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 97196323:
                    if (nextArg.equals("false")) {
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
                case 1:
                    break;
                case 2:
                case 3:
                    z = false;
                    break;
                default:
                    getErrPrintWriter().println("Error: expected true, 1, false, 0, but got " + nextArg);
                    return -1;
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    consumer.accept(java.lang.Boolean.valueOf(z));
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        }
        getErrPrintWriter().println("Error: expected true, 1, false, 0, but got empty input.");
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x013b, code lost:
    
        if (r0.equals("--aspectRatio") != false) goto L86;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0142 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x016a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0179 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0188 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0197 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01a6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x01b5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x01c4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01d3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01d7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01db A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01df A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01e3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01f1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01ff A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x020d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0211 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0215 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0219 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x021d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0221 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0225 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0229 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x022d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0231 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x015b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSetLetterboxStyle(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        if (peekNextArg() == null) {
            getErrPrintWriter().println("Error: No arguments provided.");
        }
        while (true) {
            char c = 0;
            if (peekNextArg() == null) {
                return 0;
            }
            java.lang.String nextArg = getNextArg();
            switch (nextArg.hashCode()) {
                case -2007271181:
                    break;
                case -1688278685:
                    if (nextArg.equals("--isEducationEnabled")) {
                        c = 17;
                        switch (c) {
                            case 0:
                                runSetFixedOrientationLetterboxAspectRatio(printWriter);
                                break;
                            case 1:
                                runSetDefaultMinAspectRatioForUnresizableApps(printWriter);
                                break;
                            case 2:
                                runSetLetterboxActivityCornersRadius(printWriter);
                                break;
                            case 3:
                                runSetLetterboxBackgroundType(printWriter);
                                break;
                            case 4:
                                runSetLetterboxBackgroundColor(printWriter);
                                break;
                            case 5:
                                runSetLetterboxBackgroundColorResource(printWriter);
                                break;
                            case 6:
                                runSetLetterboxBackgroundWallpaperBlurRadius(printWriter);
                                break;
                            case 7:
                                runSetLetterboxBackgroundWallpaperDarkScrimAlpha(printWriter);
                                break;
                            case '\b':
                                runSetLetterboxHorizontalPositionMultiplier(printWriter);
                                break;
                            case '\t':
                                runSetLetterboxVerticalPositionMultiplier(printWriter);
                                break;
                            case '\n':
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setIsHorizontalReachabilityEnabled(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            case 11:
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration2 = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration2);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda4
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setIsVerticalReachabilityEnabled(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            case '\f':
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration3 = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration3);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda5
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setIsAutomaticReachabilityInBookModeEnabled(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            case '\r':
                                runSetLetterboxDefaultPositionForHorizontalReachability(printWriter);
                                break;
                            case 14:
                                runSetLetterboxDefaultPositionForVerticalReachability(printWriter);
                                break;
                            case 15:
                                runSetPersistentLetterboxPositionForHorizontalReachability(printWriter);
                                break;
                            case 16:
                                runSetPersistentLetterboxPositionForVerticalReachability(printWriter);
                                break;
                            case 17:
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration4 = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration4);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda6
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setIsEducationEnabled(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            case 18:
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration5 = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration5);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda7
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setIsSplitScreenAspectRatioForUnresizableAppsEnabled(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            case 19:
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration6 = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration6);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda8
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setIsDisplayAspectRatioEnabledForFixedOrientationLetterbox(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            case 20:
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration7 = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration7);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda9
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setTranslucentLetterboxingOverrideEnabled(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            case 21:
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration8 = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration8);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda10
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setUserAppAspectRatioSettingsOverrideEnabled(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            case 22:
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration9 = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration9);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda11
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setUserAppAspectRatioFullscreenOverrideEnabled(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            case 23:
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration10 = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration10);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda12
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setCameraCompatRefreshEnabled(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            case 24:
                                final com.android.server.wm.LetterboxConfiguration letterboxConfiguration11 = this.mLetterboxConfiguration;
                                java.util.Objects.requireNonNull(letterboxConfiguration11);
                                runSetBooleanFlag(printWriter, new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.wm.LetterboxConfiguration.this.setCameraCompatRefreshCycleThroughStopEnabled(((java.lang.Boolean) obj).booleanValue());
                                    }
                                });
                                break;
                            default:
                                getErrPrintWriter().println("Error: Unrecognized letterbox style option: " + nextArg);
                                return -1;
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                case -1440939136:
                    if (nextArg.equals("--verticalPositionMultiplier")) {
                        c = '\t';
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -1310848756:
                    if (nextArg.equals("--defaultPositionForVerticalReachability")) {
                        c = 14;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -1294369338:
                    if (nextArg.equals("--isDisplayAspectRatioEnabledForFixedOrientationLetterbox")) {
                        c = 19;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -1264068297:
                    if (nextArg.equals("--isCameraCompatRefreshEnabled")) {
                        c = 23;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -1052930822:
                    if (nextArg.equals("--defaultPositionForHorizontalReachability")) {
                        c = '\r';
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -1031747914:
                    if (nextArg.equals("--persistentPositionForVerticalReachability")) {
                        c = 16;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -1009939225:
                    if (nextArg.equals("--cornerRadius")) {
                        c = 2;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -951337176:
                    if (nextArg.equals("--backgroundType")) {
                        c = 3;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -911250737:
                    if (nextArg.equals("--isSplitScreenAspectRatioForUnresizableAppsEnabled")) {
                        c = 18;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -335739429:
                    if (nextArg.equals("--wallpaperBlurRadius")) {
                        c = 6;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -302214401:
                    if (nextArg.equals("--isUserAppAspectRatioFullscreenEnabled")) {
                        c = 22;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -301215364:
                    if (nextArg.equals("--isHorizontalReachabilityEnabled")) {
                        c = '\n';
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case -69722518:
                    if (nextArg.equals("--isVerticalReachabilityEnabled")) {
                        c = 11;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case 229853520:
                    if (nextArg.equals("--wallpaperDarkScrimAlpha")) {
                        c = 7;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case 304986101:
                    if (nextArg.equals("--isTranslucentLetterboxingEnabled")) {
                        c = 20;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case 557317429:
                    if (nextArg.equals("--backgroundColor")) {
                        c = 4;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case 875005988:
                    if (nextArg.equals("--persistentPositionForHorizontalReachability")) {
                        c = 15;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case 935353942:
                    if (nextArg.equals("--isCameraCompatRefreshCycleThroughStopEnabled")) {
                        c = 24;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case 1033642083:
                    if (nextArg.equals("--backgroundColorResource")) {
                        c = 5;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case 1066804362:
                    if (nextArg.equals("--minAspectRatioForUnresizable")) {
                        c = 1;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case 1070248110:
                    if (nextArg.equals("--horizontalPositionMultiplier")) {
                        c = '\b';
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case 1416509399:
                    if (nextArg.equals("--isUserAppAspectRatioSettingsEnabled")) {
                        c = 21;
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                case 1739415288:
                    if (nextArg.equals("--isAutomaticReachabilityInBookModeEnabled")) {
                        c = '\f';
                        switch (c) {
                        }
                    }
                    c = 65535;
                    switch (c) {
                    }
                    break;
                default:
                    c = 65535;
                    switch (c) {
                    }
                    break;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:10:0x0021. Please report as an issue. */
    private int runResetLetterboxStyle(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        if (peekNextArg() == null) {
            resetLetterboxStyle();
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            while (true) {
                try {
                    char c = 0;
                    if (peekNextArg() == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return 0;
                    }
                    java.lang.String nextArg = getNextArg();
                    switch (nextArg.hashCode()) {
                        case -2064669968:
                            if (nextArg.equals("wallpaperDarkScrimAlpha")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1229148346:
                            if (nextArg.equals("IsDisplayAspectRatioEnabledForFixedOrientationLetterbox")) {
                                c = 17;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1135892646:
                            if (nextArg.equals("defaultPositionForHorizontalReachability")) {
                                c = 11;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1124529213:
                            if (nextArg.equals("isEducationEnabled")) {
                                c = 15;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1034497596:
                            if (nextArg.equals("persistentPositionForHorizontalReachability")) {
                                c = '\r';
                                break;
                            }
                            c = 65535;
                            break;
                        case -567226646:
                            if (nextArg.equals("minAspectRatioForUnresizable")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case -563782898:
                            if (nextArg.equals("horizontalPositionMultiplier")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case -559641828:
                            if (nextArg.equals("isHorizontalReachabilityEnabled")) {
                                c = '\t';
                                break;
                            }
                            c = 65535;
                            break;
                        case -39374981:
                            if (nextArg.equals("wallpaperBlurRadius")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 208616300:
                            if (nextArg.equals("defaultPositionForVerticalReachability")) {
                                c = '\f';
                                break;
                            }
                            c = 65535;
                            break;
                        case 583595847:
                            if (nextArg.equals("cornerRadius")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 691402838:
                            if (nextArg.equals("persistentPositionForVerticalReachability")) {
                                c = 14;
                                break;
                            }
                            c = 65535;
                            break;
                        case 814923786:
                            if (nextArg.equals("isVerticalReachabilityEnabled")) {
                                c = '\n';
                                break;
                            }
                            c = 65535;
                            break;
                        case 883700309:
                            if (nextArg.equals("isTranslucentLetterboxingEnabled")) {
                                c = 18;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1092174483:
                            if (nextArg.equals("aspectRatio")) {
                                break;
                            }
                            c = 65535;
                            break;
                        case 1109312992:
                            if (nextArg.equals("verticalPositionMultiplier")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 1287124693:
                            if (nextArg.equals("backgroundColor")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1396867991:
                            if (nextArg.equals("isCameraCompatRefreshEnabled")) {
                                c = 21;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1427509640:
                            if (nextArg.equals("backgroundType")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1869151343:
                            if (nextArg.equals("isSplitScreenAspectRatioForUnresizableAppsEnabled")) {
                                c = 16;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1870284982:
                            if (nextArg.equals("isCameraCompatRefreshCycleThroughStopEnabled")) {
                                c = 22;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1892753783:
                            if (nextArg.equals("isUserAppAspectRatioSettingsEnabled")) {
                                c = 19;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2102105247:
                            if (nextArg.equals("isUserAppAspectRatioFullscreenEnabled")) {
                                c = 20;
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
                            this.mLetterboxConfiguration.resetFixedOrientationLetterboxAspectRatio();
                            break;
                        case 1:
                            this.mLetterboxConfiguration.resetDefaultMinAspectRatioForUnresizableApps();
                            break;
                        case 2:
                            this.mLetterboxConfiguration.resetLetterboxActivityCornersRadius();
                            break;
                        case 3:
                            this.mLetterboxConfiguration.resetLetterboxBackgroundType();
                            break;
                        case 4:
                            this.mLetterboxConfiguration.resetLetterboxBackgroundColor();
                            break;
                        case 5:
                            this.mLetterboxConfiguration.resetLetterboxBackgroundWallpaperBlurRadiusPx();
                            break;
                        case 6:
                            this.mLetterboxConfiguration.resetLetterboxBackgroundWallpaperDarkScrimAlpha();
                            break;
                        case 7:
                            this.mLetterboxConfiguration.resetLetterboxHorizontalPositionMultiplier();
                            break;
                        case '\b':
                            this.mLetterboxConfiguration.resetLetterboxVerticalPositionMultiplier();
                            break;
                        case '\t':
                            this.mLetterboxConfiguration.resetIsHorizontalReachabilityEnabled();
                            break;
                        case '\n':
                            this.mLetterboxConfiguration.resetIsVerticalReachabilityEnabled();
                            break;
                        case 11:
                            this.mLetterboxConfiguration.resetDefaultPositionForHorizontalReachability();
                            break;
                        case '\f':
                            this.mLetterboxConfiguration.resetDefaultPositionForVerticalReachability();
                            break;
                        case '\r':
                            this.mLetterboxConfiguration.resetPersistentLetterboxPositionForHorizontalReachability();
                            break;
                        case 14:
                            this.mLetterboxConfiguration.resetPersistentLetterboxPositionForVerticalReachability();
                            break;
                        case 15:
                            this.mLetterboxConfiguration.resetIsEducationEnabled();
                            break;
                        case 16:
                            this.mLetterboxConfiguration.resetIsSplitScreenAspectRatioForUnresizableAppsEnabled();
                            break;
                        case 17:
                            this.mLetterboxConfiguration.resetIsDisplayAspectRatioEnabledForFixedOrientationLetterbox();
                            break;
                        case 18:
                            this.mLetterboxConfiguration.resetTranslucentLetterboxingEnabled();
                            break;
                        case 19:
                            this.mLetterboxConfiguration.resetUserAppAspectRatioSettingsEnabled();
                            break;
                        case 20:
                            this.mLetterboxConfiguration.resetUserAppAspectRatioFullscreenEnabled();
                            break;
                        case 21:
                            this.mLetterboxConfiguration.resetCameraCompatRefreshEnabled();
                            break;
                        case 22:
                            this.mLetterboxConfiguration.resetCameraCompatRefreshCycleThroughStopEnabled();
                            break;
                        default:
                            getErrPrintWriter().println("Error: Unrecognized letterbox style option: " + nextArg);
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return -1;
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0055 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSetMultiWindowConfig() {
        boolean z;
        if (peekNextArg() == null) {
            getErrPrintWriter().println("Error: No arguments provided.");
        }
        int i = 0;
        while (peekNextArg() != null) {
            java.lang.String nextArg = getNextArg();
            switch (nextArg.hashCode()) {
                case 1485032610:
                    if (nextArg.equals("--supportsNonResizable")) {
                        z = false;
                        switch (z) {
                            case false:
                                i += runSetSupportsNonResizableMultiWindow();
                                break;
                            case true:
                                i += runSetRespectsActivityMinWidthHeightMultiWindow();
                                break;
                            default:
                                getErrPrintWriter().println("Error: Unrecognized multi window option: " + nextArg);
                                return -1;
                        }
                    }
                    z = -1;
                    switch (z) {
                    }
                case 1714039607:
                    if (nextArg.equals("--respectsActivityMinWidthHeight")) {
                        z = true;
                        switch (z) {
                        }
                    }
                    z = -1;
                    switch (z) {
                    }
                    break;
                default:
                    z = -1;
                    switch (z) {
                    }
                    break;
            }
        }
        return i == 0 ? 0 : -1;
    }

    private int runSetSupportsNonResizableMultiWindow() {
        java.lang.String nextArg = getNextArg();
        if (!nextArg.equals("-1") && !nextArg.equals("0") && !nextArg.equals("1")) {
            getErrPrintWriter().println("Error: a config value of [-1, 0, 1] must be provided as an argument for supportsNonResizableMultiWindow");
            return -1;
        }
        int parseInt = java.lang.Integer.parseInt(nextArg);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mAtmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mInternal.mAtmService.mSupportsNonResizableMultiWindow = parseInt;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    private int runSetRespectsActivityMinWidthHeightMultiWindow() {
        java.lang.String nextArg = getNextArg();
        if (!nextArg.equals("-1") && !nextArg.equals("0") && !nextArg.equals("1")) {
            getErrPrintWriter().println("Error: a config value of [-1, 0, 1] must be provided as an argument for respectsActivityMinWidthHeightMultiWindow");
            return -1;
        }
        int parseInt = java.lang.Integer.parseInt(nextArg);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mAtmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mInternal.mAtmService.mRespectsActivityMinWidthHeightMultiWindow = parseInt;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    private int runGetMultiWindowConfig(java.io.PrintWriter printWriter) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mAtmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                printWriter.println("Supports non-resizable in multi window: " + this.mInternal.mAtmService.mSupportsNonResizableMultiWindow);
                printWriter.println("Respects activity min width/height in multi window: " + this.mInternal.mAtmService.mRespectsActivityMinWidthHeightMultiWindow);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    private int runResetMultiWindowConfig() {
        int integer = this.mInternal.mContext.getResources().getInteger(android.R.integer.config_sidefpsSkipWaitForPowerAcquireMessage);
        int integer2 = this.mInternal.mContext.getResources().getInteger(android.R.integer.config_reduceBrightColorsStrengthDefault);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mAtmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mInternal.mAtmService.mSupportsNonResizableMultiWindow = integer;
                this.mInternal.mAtmService.mRespectsActivityMinWidthHeightMultiWindow = integer2;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    private void resetLetterboxStyle() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mLetterboxConfiguration.resetFixedOrientationLetterboxAspectRatio();
                this.mLetterboxConfiguration.resetDefaultMinAspectRatioForUnresizableApps();
                this.mLetterboxConfiguration.resetLetterboxActivityCornersRadius();
                this.mLetterboxConfiguration.resetLetterboxBackgroundType();
                this.mLetterboxConfiguration.resetLetterboxBackgroundColor();
                this.mLetterboxConfiguration.resetLetterboxBackgroundWallpaperBlurRadiusPx();
                this.mLetterboxConfiguration.resetLetterboxBackgroundWallpaperDarkScrimAlpha();
                this.mLetterboxConfiguration.resetLetterboxHorizontalPositionMultiplier();
                this.mLetterboxConfiguration.resetLetterboxVerticalPositionMultiplier();
                this.mLetterboxConfiguration.resetIsHorizontalReachabilityEnabled();
                this.mLetterboxConfiguration.resetIsVerticalReachabilityEnabled();
                this.mLetterboxConfiguration.resetEnabledAutomaticReachabilityInBookMode();
                this.mLetterboxConfiguration.resetDefaultPositionForHorizontalReachability();
                this.mLetterboxConfiguration.resetDefaultPositionForVerticalReachability();
                this.mLetterboxConfiguration.resetPersistentLetterboxPositionForHorizontalReachability();
                this.mLetterboxConfiguration.resetPersistentLetterboxPositionForVerticalReachability();
                this.mLetterboxConfiguration.resetIsEducationEnabled();
                this.mLetterboxConfiguration.resetIsSplitScreenAspectRatioForUnresizableAppsEnabled();
                this.mLetterboxConfiguration.resetIsDisplayAspectRatioEnabledForFixedOrientationLetterbox();
                this.mLetterboxConfiguration.resetTranslucentLetterboxingEnabled();
                this.mLetterboxConfiguration.resetUserAppAspectRatioSettingsEnabled();
                this.mLetterboxConfiguration.resetUserAppAspectRatioFullscreenEnabled();
                this.mLetterboxConfiguration.resetCameraCompatRefreshEnabled();
                this.mLetterboxConfiguration.resetCameraCompatRefreshCycleThroughStopEnabled();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    private int runGetLetterboxStyle(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                printWriter.println("Corner radius: " + this.mLetterboxConfiguration.getLetterboxActivityCornersRadius());
                printWriter.println("Horizontal position multiplier: " + this.mLetterboxConfiguration.getLetterboxHorizontalPositionMultiplier(false));
                printWriter.println("Vertical position multiplier: " + this.mLetterboxConfiguration.getLetterboxVerticalPositionMultiplier(false));
                printWriter.println("Horizontal position multiplier (book mode): " + this.mLetterboxConfiguration.getLetterboxHorizontalPositionMultiplier(true));
                printWriter.println("Vertical position multiplier (tabletop mode): " + this.mLetterboxConfiguration.getLetterboxVerticalPositionMultiplier(true));
                printWriter.println("Horizontal position multiplier for reachability: " + this.mLetterboxConfiguration.getHorizontalMultiplierForReachability(false));
                printWriter.println("Vertical position multiplier for reachability: " + this.mLetterboxConfiguration.getVerticalMultiplierForReachability(false));
                printWriter.println("Aspect ratio: " + this.mLetterboxConfiguration.getFixedOrientationLetterboxAspectRatio());
                printWriter.println("Default min aspect ratio for unresizable apps: " + this.mLetterboxConfiguration.getDefaultMinAspectRatioForUnresizableApps());
                printWriter.println("Is horizontal reachability enabled: " + this.mLetterboxConfiguration.getIsHorizontalReachabilityEnabled());
                printWriter.println("Is vertical reachability enabled: " + this.mLetterboxConfiguration.getIsVerticalReachabilityEnabled());
                printWriter.println("Is automatic reachability in book mode enabled: " + this.mLetterboxConfiguration.getIsAutomaticReachabilityInBookModeEnabled());
                printWriter.println("Default position for horizontal reachability: " + com.android.server.wm.LetterboxConfiguration.letterboxHorizontalReachabilityPositionToString(this.mLetterboxConfiguration.getDefaultPositionForHorizontalReachability()));
                printWriter.println("Default position for vertical reachability: " + com.android.server.wm.LetterboxConfiguration.letterboxVerticalReachabilityPositionToString(this.mLetterboxConfiguration.getDefaultPositionForVerticalReachability()));
                printWriter.println("Current position for horizontal reachability:" + com.android.server.wm.LetterboxConfiguration.letterboxHorizontalReachabilityPositionToString(this.mLetterboxConfiguration.getLetterboxPositionForHorizontalReachability(false)));
                printWriter.println("Current position for vertical reachability:" + com.android.server.wm.LetterboxConfiguration.letterboxVerticalReachabilityPositionToString(this.mLetterboxConfiguration.getLetterboxPositionForVerticalReachability(false)));
                printWriter.println("Is education enabled: " + this.mLetterboxConfiguration.getIsEducationEnabled());
                printWriter.println("Is using split screen aspect ratio as aspect ratio for unresizable apps: " + this.mLetterboxConfiguration.getIsSplitScreenAspectRatioForUnresizableAppsEnabled());
                printWriter.println("Is using display aspect ratio as aspect ratio for all letterboxed apps: " + this.mLetterboxConfiguration.getIsDisplayAspectRatioEnabledForFixedOrientationLetterbox());
                printWriter.println("    Is activity \"refresh\" in camera compatibility treatment enabled: " + this.mLetterboxConfiguration.isCameraCompatRefreshEnabled());
                printWriter.println("    Refresh using \"stopped -> resumed\" cycle: " + this.mLetterboxConfiguration.isCameraCompatRefreshCycleThroughStopEnabled());
                printWriter.println("Background type: " + com.android.server.wm.LetterboxConfiguration.letterboxBackgroundTypeToString(this.mLetterboxConfiguration.getLetterboxBackgroundType()));
                printWriter.println("    Background color: " + java.lang.Integer.toHexString(this.mLetterboxConfiguration.getLetterboxBackgroundColor().toArgb()));
                printWriter.println("    Wallpaper blur radius: " + this.mLetterboxConfiguration.getLetterboxBackgroundWallpaperBlurRadiusPx());
                printWriter.println("    Wallpaper dark scrim alpha: " + this.mLetterboxConfiguration.getLetterboxBackgroundWallpaperDarkScrimAlpha());
                printWriter.println("Is letterboxing for translucent activities enabled: " + this.mLetterboxConfiguration.isTranslucentLetterboxingEnabled());
                printWriter.println("Is the user aspect ratio settings enabled: " + this.mLetterboxConfiguration.isUserAppAspectRatioSettingsEnabled());
                printWriter.println("Is the fullscreen option in user aspect ratio settings enabled: " + this.mLetterboxConfiguration.isUserAppAspectRatioFullscreenEnabled());
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runWmShellCommand(java.io.PrintWriter printWriter) {
        char c;
        java.lang.String nextArg = getNextArg();
        switch (nextArg.hashCode()) {
            case -1067396926:
                if (nextArg.equals("tracing")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3198785:
                if (nextArg.equals("help")) {
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
                return runWmShellTracing(printWriter);
            default:
                return runHelp(printWriter);
        }
    }

    private int runHelp(java.io.PrintWriter printWriter) {
        printWriter.println("Window Manager Shell commands:");
        printWriter.println("  help");
        printWriter.println("    Print this help text.");
        printWriter.println("  tracing <start/stop>");
        printWriter.println("    Start/stop shell transition tracing.");
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runWmShellTracing(java.io.PrintWriter printWriter) {
        char c;
        java.lang.String nextArg = getNextArg();
        switch (nextArg.hashCode()) {
            case -390772652:
                if (nextArg.equals("save-for-bugreport")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3540994:
                if (nextArg.equals("stop")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (nextArg.equals("start")) {
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
                this.mInternal.mTransitionTracer.startTrace(printWriter);
                return 0;
            case 1:
                this.mInternal.mTransitionTracer.stopTrace(printWriter);
                return 0;
            case 2:
                this.mInternal.mTransitionTracer.saveForBugreport(printWriter);
                return 0;
            default:
                getErrPrintWriter().println("Error: expected 'start' or 'stop', but got '" + nextArg + "'");
                return -1;
        }
    }

    private int runReset(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int displayId = getDisplayId(getNextArg());
        this.mInterface.clearForcedDisplaySize(displayId);
        this.mInterface.clearForcedDisplayDensityForUser(displayId, -2);
        this.mInternal.setOverrideFoldedArea(new android.graphics.Rect());
        this.mInterface.setForcedDisplayScalingMode(displayId, 0);
        this.mInternal.thawDisplayRotation(displayId, "WindowManagerShellCommand#runReset");
        this.mInterface.setFixedToUserRotation(displayId, 0);
        this.mInterface.setIgnoreOrientationRequest(displayId, false);
        resetLetterboxStyle();
        this.mInternal.setSandboxDisplayApis(displayId, true);
        runResetMultiWindowConfig();
        printWriter.println("Reset all settings for displayId=" + displayId);
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Window manager (window) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  size [reset|WxH|WdpxHdp] [-d DISPLAY_ID]");
        outPrintWriter.println("    Return or override display size.");
        outPrintWriter.println("    width and height in pixels unless suffixed with 'dp'.");
        outPrintWriter.println("  density [reset|DENSITY] [-d DISPLAY_ID] [-u UNIQUE_ID]");
        outPrintWriter.println("    Return or override display density.");
        outPrintWriter.println("  folded-area [reset|LEFT,TOP,RIGHT,BOTTOM]");
        outPrintWriter.println("    Return or override folded area.");
        outPrintWriter.println("  scaling [off|auto] [-d DISPLAY_ID]");
        outPrintWriter.println("    Set display scaling mode.");
        outPrintWriter.println("  dismiss-keyguard");
        outPrintWriter.println("    Dismiss the keyguard, prompting user for auth if necessary.");
        outPrintWriter.println("  disable-blur [true|1|false|0]");
        outPrintWriter.println("  user-rotation [-d DISPLAY_ID] [free|lock] [rotation]");
        outPrintWriter.println("    Print or set user rotation mode and user rotation.");
        outPrintWriter.println("  dump-visible-window-views");
        outPrintWriter.println("    Dumps the encoded view hierarchies of visible windows");
        outPrintWriter.println("  fixed-to-user-rotation [-d DISPLAY_ID] [enabled|disabled|default]");
        outPrintWriter.println("    Print or set rotating display for app requested orientation.");
        outPrintWriter.println("  set-ignore-orientation-request [-d DISPLAY_ID] [true|1|false|0]");
        outPrintWriter.println("  get-ignore-orientation-request [-d DISPLAY_ID] ");
        outPrintWriter.println("    If app requested orientation should be ignored.");
        outPrintWriter.println("  set-sandbox-display-apis [true|1|false|0]");
        outPrintWriter.println("    Sets override of Display APIs getRealSize / getRealMetrics to reflect ");
        outPrintWriter.println("    DisplayArea of the activity, or the window bounds if in letterbox or");
        outPrintWriter.println("    Size Compat Mode.");
        printLetterboxHelp(outPrintWriter);
        printMultiWindowConfigHelp(outPrintWriter);
        outPrintWriter.println("  reset [-d DISPLAY_ID]");
        outPrintWriter.println("    Reset all override settings.");
        if (!android.os.Build.IS_USER) {
            outPrintWriter.println("  tracing (start | stop)");
            outPrintWriter.println("    Start or stop window tracing.");
            outPrintWriter.println("  logging (start | stop | enable | disable | enable-text | disable-text)");
            outPrintWriter.println("    Logging settings.");
        }
    }

    private void printLetterboxHelp(java.io.PrintWriter printWriter) {
        printWriter.println("  set-letterbox-style");
        printWriter.println("    Sets letterbox style using the following options:");
        printWriter.println("      --aspectRatio aspectRatio");
        printWriter.println("        Aspect ratio of letterbox for fixed orientation. If aspectRatio <= 1.0");
        printWriter.println("        both it and R.dimen.config_fixedOrientationLetterboxAspectRatio will");
        printWriter.println("        be ignored and framework implementation will determine aspect ratio.");
        printWriter.println("      --minAspectRatioForUnresizable aspectRatio");
        printWriter.println("        Default min aspect ratio for unresizable apps which is used when an");
        printWriter.println("        app is eligible for the size compat mode.  If aspectRatio <= 1.0");
        printWriter.println("        both it and R.dimen.config_fixedOrientationLetterboxAspectRatio will");
        printWriter.println("        be ignored and framework implementation will determine aspect ratio.");
        printWriter.println("      --cornerRadius radius");
        printWriter.println("        Corners radius for activities in the letterbox mode. If radius < 0,");
        printWriter.println("        both it and R.integer.config_letterboxActivityCornersRadius will be");
        printWriter.println("        ignored and corners of the activity won't be rounded.");
        printWriter.println("      --backgroundType [reset|solid_color|app_color_background");
        printWriter.println("          |app_color_background_floating|wallpaper]");
        printWriter.println("        Type of background used in the letterbox mode.");
        printWriter.println("      --backgroundColor color");
        printWriter.println("        Color of letterbox which is be used when letterbox background type");
        printWriter.println("        is 'solid-color'. Use (set)get-letterbox-style to check and control");
        printWriter.println("        letterbox background type. See Color#parseColor for allowed color");
        printWriter.println("        formats (#RRGGBB and some colors by name, e.g. magenta or olive).");
        printWriter.println("      --backgroundColorResource resource_name");
        printWriter.println("        Color resource name of letterbox background which is used when");
        printWriter.println("        background type is 'solid-color'. Use (set)get-letterbox-style to");
        printWriter.println("        check and control background type. Parameter is a color resource");
        printWriter.println("        name, for example, @android:color/system_accent2_50.");
        printWriter.println("      --wallpaperBlurRadius radius");
        printWriter.println("        Blur radius for 'wallpaper' letterbox background. If radius <= 0");
        printWriter.println("        both it and R.dimen.config_letterboxBackgroundWallpaperBlurRadius");
        printWriter.println("        are ignored and 0 is used.");
        printWriter.println("      --wallpaperDarkScrimAlpha alpha");
        printWriter.println("        Alpha of a black translucent scrim shown over 'wallpaper'");
        printWriter.println("        letterbox background. If alpha < 0 or >= 1 both it and");
        printWriter.println("        R.dimen.config_letterboxBackgroundWallaperDarkScrimAlpha are ignored");
        printWriter.println("        and 0.0 (transparent) is used instead.");
        printWriter.println("      --horizontalPositionMultiplier multiplier");
        printWriter.println("        Horizontal position of app window center. If multiplier < 0 or > 1,");
        printWriter.println("        both it and R.dimen.config_letterboxHorizontalPositionMultiplier");
        printWriter.println("        are ignored and central position (0.5) is used.");
        printWriter.println("      --verticalPositionMultiplier multiplier");
        printWriter.println("        Vertical position of app window center. If multiplier < 0 or > 1,");
        printWriter.println("        both it and R.dimen.config_letterboxVerticalPositionMultiplier");
        printWriter.println("        are ignored and central position (0.5) is used.");
        printWriter.println("      --isHorizontalReachabilityEnabled [true|1|false|0]");
        printWriter.println("        Whether horizontal reachability repositioning is allowed for ");
        printWriter.println("        letterboxed fullscreen apps in landscape device orientation.");
        printWriter.println("      --isVerticalReachabilityEnabled [true|1|false|0]");
        printWriter.println("        Whether vertical reachability repositioning is allowed for ");
        printWriter.println("        letterboxed fullscreen apps in portrait device orientation.");
        printWriter.println("      --defaultPositionForHorizontalReachability [left|center|right]");
        printWriter.println("        Default position of app window when horizontal reachability is.");
        printWriter.println("        enabled.");
        printWriter.println("      --defaultPositionForVerticalReachability [top|center|bottom]");
        printWriter.println("        Default position of app window when vertical reachability is.");
        printWriter.println("        enabled.");
        printWriter.println("      --persistentPositionForHorizontalReachability [left|center|right]");
        printWriter.println("        Persistent position of app window when horizontal reachability is.");
        printWriter.println("        enabled.");
        printWriter.println("      --persistentPositionForVerticalReachability [top|center|bottom]");
        printWriter.println("        Persistent position of app window when vertical reachability is.");
        printWriter.println("        enabled.");
        printWriter.println("      --isEducationEnabled [true|1|false|0]");
        printWriter.println("        Whether education is allowed for letterboxed fullscreen apps.");
        printWriter.println("      --isSplitScreenAspectRatioForUnresizableAppsEnabled [true|1|false|0]");
        printWriter.println("        Whether using split screen aspect ratio as a default aspect ratio for");
        printWriter.println("        unresizable apps.");
        printWriter.println("      --isTranslucentLetterboxingEnabled [true|1|false|0]");
        printWriter.println("        Whether letterboxing for translucent activities is enabled.");
        printWriter.println("      --isUserAppAspectRatioSettingsEnabled [true|1|false|0]");
        printWriter.println("        Whether user aspect ratio settings are enabled.");
        printWriter.println("      --isUserAppAspectRatioFullscreenEnabled [true|1|false|0]");
        printWriter.println("        Whether user aspect ratio fullscreen option is enabled.");
        printWriter.println("      --isCameraCompatRefreshEnabled [true|1|false|0]");
        printWriter.println("        Whether camera compatibility refresh is enabled.");
        printWriter.println("      --isCameraCompatRefreshCycleThroughStopEnabled [true|1|false|0]");
        printWriter.println("        Whether activity \"refresh\" in camera compatibility treatment should");
        printWriter.println("        happen using the \"stopped -> resumed\" cycle rather than");
        printWriter.println("        \"paused -> resumed\" cycle.");
        printWriter.println("  reset-letterbox-style [aspectRatio|cornerRadius|backgroundType");
        printWriter.println("      |backgroundColor|wallpaperBlurRadius|wallpaperDarkScrimAlpha");
        printWriter.println("      |horizontalPositionMultiplier|verticalPositionMultiplier");
        printWriter.println("      |isHorizontalReachabilityEnabled|isVerticalReachabilityEnabled");
        printWriter.println("      |isEducationEnabled|defaultPositionMultiplierForHorizontalReachability");
        printWriter.println("      |isTranslucentLetterboxingEnabled|isUserAppAspectRatioSettingsEnabled");
        printWriter.println("      |persistentPositionMultiplierForHorizontalReachability");
        printWriter.println("      |persistentPositionMultiplierForVerticalReachability");
        printWriter.println("      |defaultPositionMultiplierForVerticalReachability]");
        printWriter.println("    Resets overrides to default values for specified properties separated");
        printWriter.println("    by space, e.g. 'reset-letterbox-style aspectRatio cornerRadius'.");
        printWriter.println("    If no arguments provided, all values will be reset.");
        printWriter.println("  get-letterbox-style");
        printWriter.println("    Prints letterbox style configuration.");
    }

    private void printMultiWindowConfigHelp(java.io.PrintWriter printWriter) {
        printWriter.println("  set-multi-window-config");
        printWriter.println("    Sets options to determine if activity should be shown in multi window:");
        printWriter.println("      --supportsNonResizable [configValue]");
        printWriter.println("        Whether the device supports non-resizable activity in multi window.");
        printWriter.println("        -1: The device doesn't support non-resizable in multi window.");
        printWriter.println("         0: The device supports non-resizable in multi window only if");
        printWriter.println("            this is a large screen device.");
        printWriter.println("         1: The device always supports non-resizable in multi window.");
        printWriter.println("      --respectsActivityMinWidthHeight [configValue]");
        printWriter.println("        Whether the device checks the activity min width/height to determine ");
        printWriter.println("        if it can be shown in multi window.");
        printWriter.println("        -1: The device ignores the activity min width/height when determining");
        printWriter.println("            if it can be shown in multi window.");
        printWriter.println("         0: If this is a small screen, the device compares the activity min");
        printWriter.println("            width/height with the min multi window modes dimensions");
        printWriter.println("            the device supports to determine if the activity can be shown in");
        printWriter.println("            multi window.");
        printWriter.println("         1: The device always compare the activity min width/height with the");
        printWriter.println("            min multi window dimensions the device supports to determine if");
        printWriter.println("            the activity can be shown in multi window.");
        printWriter.println("  get-multi-window-config");
        printWriter.println("    Prints values of the multi window config options.");
        printWriter.println("  reset-multi-window-config");
        printWriter.println("    Resets overrides to default values of the multi window config options.");
    }
}
