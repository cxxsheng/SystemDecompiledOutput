package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class HdmiControlShellCommand extends android.os.ShellCommand {
    private static final java.lang.String TAG = "HdmiShellCommand";
    private final android.hardware.hdmi.IHdmiControlService.Stub mBinderService;
    final java.util.concurrent.CountDownLatch mLatch = new java.util.concurrent.CountDownLatch(1);
    java.util.concurrent.atomic.AtomicInteger mCecResult = new java.util.concurrent.atomic.AtomicInteger();
    android.hardware.hdmi.IHdmiControlCallback.Stub mHdmiControlCallback = new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiControlShellCommand.1
        public void onComplete(int i) {
            com.android.server.hdmi.HdmiControlShellCommand.this.getOutPrintWriter().println(" done (" + com.android.server.hdmi.HdmiControlShellCommand.this.getResultString(i) + ")");
            com.android.server.hdmi.HdmiControlShellCommand.this.mCecResult.set(i);
            com.android.server.hdmi.HdmiControlShellCommand.this.mLatch.countDown();
        }
    };

    HdmiControlShellCommand(android.hardware.hdmi.IHdmiControlService.Stub stub) {
        this.mBinderService = stub;
    }

    public int onCommand(java.lang.String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        try {
            return handleShellCommand(str);
        } catch (java.lang.Exception e) {
            getErrPrintWriter().println("Caught error for command '" + str + "': " + e.getMessage());
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Error handling hdmi_control shell command: ");
            sb.append(str);
            android.util.Slog.e(TAG, sb.toString(), e);
            return 1;
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("HdmiControlManager (hdmi_control) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  onetouchplay, otp");
        outPrintWriter.println("      Send the \"One Touch Play\" feature from a source to the TV");
        outPrintWriter.println("  vendorcommand --device_type <originating device type>");
        outPrintWriter.println("                --destination <destination device>");
        outPrintWriter.println("                --args <vendor specific arguments>");
        outPrintWriter.println("                [--id <true if vendor command should be sent with vendor id>]");
        outPrintWriter.println("      Send a Vendor Command to the given target device");
        outPrintWriter.println("  cec_setting get <setting name>");
        outPrintWriter.println("      Get the current value of a CEC setting");
        outPrintWriter.println("  cec_setting set <setting name> <value>");
        outPrintWriter.println("      Set the value of a CEC setting");
        outPrintWriter.println("  setsystemaudiomode, setsam [on|off]");
        outPrintWriter.println("      Sets the System Audio Mode feature on or off on TV devices");
        outPrintWriter.println("  setarc [on|off]");
        outPrintWriter.println("      Sets the ARC feature on or off on TV devices");
        outPrintWriter.println("  deviceselect <device id>");
        outPrintWriter.println("      Switch to device with given id");
        outPrintWriter.println("      The device's id is represented by its logical address.");
        outPrintWriter.println("  history_size get");
        outPrintWriter.println("      Gets the number of messages that can be stored in dumpsys history");
        outPrintWriter.println("  history_size set <new_size>");
        outPrintWriter.println("      Changes the number of messages that can be stored in dumpsys history to new_size");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int handleShellCommand(java.lang.String str) throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        switch (str.hashCode()) {
            case -1962118964:
                if (str.equals("history_size")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -956246195:
                if (str.equals("onetouchplay")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -905786704:
                if (str.equals("setarc")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -905769923:
                if (str.equals("setsam")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -467124088:
                if (str.equals("setsystemaudiomode")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -25969966:
                if (str.equals("deviceselect")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 110379:
                if (str.equals("otp")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 621295875:
                if (str.equals("vendorcommand")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1322280018:
                if (str.equals("cec_setting")) {
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
            case 0:
            case 1:
                return oneTouchPlay(outPrintWriter);
            case 2:
                return vendorCommand(outPrintWriter);
            case 3:
                return cecSetting(outPrintWriter);
            case 4:
            case 5:
                return setSystemAudioMode(outPrintWriter);
            case 6:
                return setArcMode(outPrintWriter);
            case 7:
                return deviceSelect(outPrintWriter);
            case '\b':
                return historySize(outPrintWriter);
            default:
                getErrPrintWriter().println("Unhandled command: " + str);
                return 1;
        }
    }

    private int deviceSelect(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        if (getRemainingArgsCount() != 1) {
            throw new java.lang.IllegalArgumentException("Expected exactly 1 argument.");
        }
        int parseInt = java.lang.Integer.parseInt(getNextArg());
        printWriter.print("Sending Device Select...");
        this.mBinderService.deviceSelect(parseInt, this.mHdmiControlCallback);
        return (receiveCallback("Device Select") && this.mCecResult.get() == 0) ? 0 : 1;
    }

    private int oneTouchPlay(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        printWriter.print("Sending One Touch Play...");
        this.mBinderService.oneTouchPlay(this.mHdmiControlCallback);
        return (receiveCallback("One Touch Play") && this.mCecResult.get() == 0) ? 0 : 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int vendorCommand(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        char c;
        if (6 > getRemainingArgsCount()) {
            throw new java.lang.IllegalArgumentException("Expected 3 arguments.");
        }
        java.lang.String nextOption = getNextOption();
        java.lang.String str = "";
        int i = -1;
        int i2 = -1;
        boolean z = false;
        while (nextOption != null) {
            switch (nextOption.hashCode()) {
                case -347347485:
                    if (nextOption.equals("--device_type")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -234325394:
                    if (nextOption.equals("--destination")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1492:
                    if (nextOption.equals("-a")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1495:
                    if (nextOption.equals("-d")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case android.net.util.NetworkConstants.ETHER_MTU /* 1500 */:
                    if (nextOption.equals("-i")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1511:
                    if (nextOption.equals("-t")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1387195:
                    if (nextOption.equals("--id")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1332872829:
                    if (nextOption.equals("--args")) {
                        c = 5;
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
                    i = java.lang.Integer.parseInt(getNextArgRequired());
                    break;
                case 2:
                case 3:
                    i2 = java.lang.Integer.parseInt(getNextArgRequired());
                    break;
                case 4:
                case 5:
                    str = getNextArgRequired();
                    break;
                case 6:
                case 7:
                    z = java.lang.Boolean.parseBoolean(getNextArgRequired());
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown argument: " + nextOption);
            }
            nextOption = getNextArg();
        }
        java.lang.String[] split = str.split(":");
        int length = split.length;
        byte[] bArr = new byte[length];
        for (int i3 = 0; i3 < length; i3++) {
            bArr[i3] = (byte) java.lang.Integer.parseInt(split[i3], 16);
        }
        printWriter.println("Sending <Vendor Command>");
        this.mBinderService.sendVendorCommand(i, i2, bArr, z);
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x001b, code lost:
    
        if (r0.equals("set") != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int cecSetting(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        char c = 1;
        if (getRemainingArgsCount() < 1) {
            throw new java.lang.IllegalArgumentException("Expected at least 1 argument (operation).");
        }
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case 102230:
                if (nextArgRequired.equals("get")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 113762:
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                java.lang.String nextArgRequired2 = getNextArgRequired();
                try {
                    printWriter.println(nextArgRequired2 + " = " + this.mBinderService.getCecSettingStringValue(nextArgRequired2));
                } catch (java.lang.IllegalArgumentException e) {
                    printWriter.println(nextArgRequired2 + " = " + this.mBinderService.getCecSettingIntValue(nextArgRequired2));
                }
                return 0;
            case 1:
                java.lang.String nextArgRequired3 = getNextArgRequired();
                java.lang.String nextArgRequired4 = getNextArgRequired();
                try {
                    this.mBinderService.setCecSettingStringValue(nextArgRequired3, nextArgRequired4);
                    printWriter.println(nextArgRequired3 + " = " + nextArgRequired4);
                } catch (java.lang.IllegalArgumentException e2) {
                    int parseInt = java.lang.Integer.parseInt(nextArgRequired4);
                    this.mBinderService.setCecSettingIntValue(nextArgRequired3, parseInt);
                    printWriter.println(nextArgRequired3 + " = " + parseInt);
                }
                return 0;
            default:
                throw new java.lang.IllegalArgumentException("Unknown operation: " + nextArgRequired);
        }
    }

    private int setSystemAudioMode(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        if (1 > getRemainingArgsCount()) {
            throw new java.lang.IllegalArgumentException("Please indicate if System Audio Mode should be turned \"on\" or \"off\".");
        }
        java.lang.String nextArg = getNextArg();
        if (nextArg.equals("on")) {
            printWriter.println("Setting System Audio Mode on");
            this.mBinderService.setSystemAudioMode(true, this.mHdmiControlCallback);
        } else if (nextArg.equals("off")) {
            printWriter.println("Setting System Audio Mode off");
            this.mBinderService.setSystemAudioMode(false, this.mHdmiControlCallback);
        } else {
            throw new java.lang.IllegalArgumentException("Please indicate if System Audio Mode should be turned \"on\" or \"off\".");
        }
        return (receiveCallback("Set System Audio Mode") && this.mCecResult.get() == 0) ? 0 : 1;
    }

    private int setArcMode(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        if (1 > getRemainingArgsCount()) {
            throw new java.lang.IllegalArgumentException("Please indicate if ARC mode should be turned \"on\" or \"off\".");
        }
        java.lang.String nextArg = getNextArg();
        if (nextArg.equals("on")) {
            printWriter.println("Setting ARC mode on");
            this.mBinderService.setArcMode(true);
        } else if (nextArg.equals("off")) {
            printWriter.println("Setting ARC mode off");
            this.mBinderService.setArcMode(false);
        } else {
            throw new java.lang.IllegalArgumentException("Please indicate if ARC mode should be turned \"on\" or \"off\".");
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int historySize(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        char c;
        if (1 > getRemainingArgsCount()) {
            throw new java.lang.IllegalArgumentException("Use 'set' or 'get' for the command action");
        }
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case 102230:
                if (nextArgRequired.equals("get")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 113762:
                if (nextArgRequired.equals("set")) {
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
                printWriter.println("CEC dumpsys message history size = " + this.mBinderService.getMessageHistorySize());
                return 0;
            case 1:
                java.lang.String nextArgRequired2 = getNextArgRequired();
                try {
                    int parseInt = java.lang.Integer.parseInt(nextArgRequired2);
                    if (this.mBinderService.setMessageHistorySize(parseInt)) {
                        printWriter.println("Setting CEC dumpsys message history size to " + parseInt);
                    } else {
                        printWriter.println("Message history size not changed, was it lower than the minimum size?");
                    }
                    return 0;
                } catch (java.lang.NumberFormatException e) {
                    printWriter.println("Cannot set CEC dumpsys message history size to " + nextArgRequired2);
                    return 1;
                }
            default:
                throw new java.lang.IllegalArgumentException("Unknown operation: " + nextArgRequired);
        }
    }

    private boolean receiveCallback(java.lang.String str) {
        try {
            if (!this.mLatch.await(2000L, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                getErrPrintWriter().println(str + " timed out.");
                return false;
            }
            return true;
        } catch (java.lang.InterruptedException e) {
            getErrPrintWriter().println("Caught InterruptedException");
            java.lang.Thread.currentThread().interrupt();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getResultString(int i) {
        switch (i) {
            case 0:
                return "Success";
            case 1:
                return "Timeout";
            case 2:
                return "Source not available";
            case 3:
                return "Target not available";
            case 4:
            default:
                return java.lang.Integer.toString(i);
            case 5:
                return "Exception";
            case 6:
                return "Incorrect mode";
            case 7:
                return "Communication Failed";
        }
    }
}
