package com.android.server.flags;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes.dex */
public class FlagsShellCommand {
    private final com.android.server.flags.FlagOverrideStore mFlagStore;

    FlagsShellCommand(com.android.server.flags.FlagOverrideStore flagOverrideStore) {
        this.mFlagStore = flagOverrideStore;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x003e, code lost:
    
        if (r1.equals("help") != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int process(java.lang.String[] strArr, java.io.OutputStream outputStream, java.io.OutputStream outputStream2) {
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(outputStream);
        com.android.internal.util.FastPrintWriter fastPrintWriter2 = new com.android.internal.util.FastPrintWriter(outputStream2);
        if (strArr.length == 0) {
            return printHelp(fastPrintWriter);
        }
        char c = 0;
        java.lang.String lowerCase = strArr[0].toLowerCase(java.util.Locale.ROOT);
        switch (lowerCase.hashCode()) {
            case 102230:
                if (lowerCase.equals("get")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 113762:
                if (lowerCase.equals("set")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3198785:
                break;
            case 3322014:
                if (lowerCase.equals("list")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 96768678:
                if (lowerCase.equals("erase")) {
                    c = 4;
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
                return printHelp(fastPrintWriter);
            case 1:
                return listCmd(strArr, fastPrintWriter, fastPrintWriter2);
            case 2:
                return setCmd(strArr, fastPrintWriter, fastPrintWriter2);
            case 3:
                return getCmd(strArr, fastPrintWriter, fastPrintWriter2);
            case 4:
                return eraseCmd(strArr, fastPrintWriter, fastPrintWriter2);
            default:
                return unknownCmd(fastPrintWriter);
        }
    }

    private int printHelp(java.io.PrintWriter printWriter) {
        printWriter.println("Feature Flags command, allowing listing, setting, getting, and erasing of");
        printWriter.println("local flag overrides on a device.");
        printWriter.println();
        printWriter.println("Commands:");
        printWriter.println("  list [namespace]");
        printWriter.println("    List all flag overrides. Namespace is optional.");
        printWriter.println();
        printWriter.println("  get <namespace> <name>");
        printWriter.println("    Return the string value of a specific flag, or <unset>");
        printWriter.println();
        printWriter.println("  set <namespace> <name> <value>");
        printWriter.println("    Set a specific flag");
        printWriter.println();
        printWriter.println("  erase <namespace> <name>");
        printWriter.println("    Unset a specific flag");
        printWriter.flush();
        return 0;
    }

    private int listCmd(java.lang.String[] strArr, java.io.PrintWriter printWriter, java.io.PrintWriter printWriter2) {
        java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> flags;
        if (!validateNumArguments(strArr, 0, 1, strArr[0], printWriter2)) {
            printWriter2.println("Expected `" + strArr[0] + " [namespace]`");
            printWriter2.flush();
            return -1;
        }
        if (strArr.length == 2) {
            flags = this.mFlagStore.getFlagsForNamespace(strArr[1]);
        } else {
            flags = this.mFlagStore.getFlags();
        }
        if (flags.isEmpty()) {
            printWriter.println("No overrides set");
        } else {
            int length = "namespace".length();
            int length2 = "flag".length();
            int length3 = "value".length();
            for (java.util.Map.Entry<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> entry : flags.entrySet()) {
                length = java.lang.Math.max(length, entry.getKey().length());
                for (java.util.Map.Entry<java.lang.String, java.lang.String> entry2 : entry.getValue().entrySet()) {
                    length2 = java.lang.Math.max(length2, entry2.getKey().length());
                    length3 = java.lang.Math.max(length3, entry2.getValue().length());
                }
            }
            printWriter.print(java.lang.String.format("%-" + length + "s", "namespace"));
            printWriter.print(' ');
            printWriter.print(java.lang.String.format("%-" + length2 + "s", "flag"));
            printWriter.print(' ');
            printWriter.println("value");
            for (int i = 0; i < length; i++) {
                printWriter.print('=');
            }
            printWriter.print(' ');
            for (int i2 = 0; i2 < length2; i2++) {
                printWriter.print('=');
            }
            printWriter.print(' ');
            for (int i3 = 0; i3 < length3; i3++) {
                printWriter.print('=');
            }
            printWriter.println();
            for (java.util.Map.Entry<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> entry3 : flags.entrySet()) {
                for (java.util.Map.Entry<java.lang.String, java.lang.String> entry4 : entry3.getValue().entrySet()) {
                    printWriter.print(java.lang.String.format("%-" + length + "s", entry3.getKey()));
                    printWriter.print(' ');
                    printWriter.print(java.lang.String.format("%-" + length2 + "s", entry4.getKey()));
                    printWriter.print(' ');
                    printWriter.println(entry4.getValue());
                }
            }
        }
        printWriter.flush();
        return 0;
    }

    private int setCmd(java.lang.String[] strArr, java.io.PrintWriter printWriter, java.io.PrintWriter printWriter2) {
        if (!validateNumArguments(strArr, 3, strArr[0], printWriter2)) {
            printWriter2.println("Expected `" + strArr[0] + " <namespace> <name> <value>`");
            printWriter2.flush();
            return -1;
        }
        this.mFlagStore.set(strArr[1], strArr[2], strArr[3]);
        printWriter.println("Flag " + strArr[1] + "." + strArr[2] + " is now " + strArr[3]);
        printWriter.flush();
        return 0;
    }

    private int getCmd(java.lang.String[] strArr, java.io.PrintWriter printWriter, java.io.PrintWriter printWriter2) {
        if (!validateNumArguments(strArr, 2, strArr[0], printWriter2)) {
            printWriter2.println("Expected `" + strArr[0] + " <namespace> <name>`");
            printWriter2.flush();
            return -1;
        }
        java.lang.String str = this.mFlagStore.get(strArr[1], strArr[2]);
        printWriter.print(strArr[1] + "." + strArr[2] + " is ");
        if (str == null || str.isEmpty()) {
            printWriter.println("<unset>");
        } else {
            printWriter.println("\"" + str.translateEscapes() + "\"");
        }
        printWriter.flush();
        return 0;
    }

    private int eraseCmd(java.lang.String[] strArr, java.io.PrintWriter printWriter, java.io.PrintWriter printWriter2) {
        if (!validateNumArguments(strArr, 2, strArr[0], printWriter2)) {
            printWriter2.println("Expected `" + strArr[0] + " <namespace> <name>`");
            printWriter2.flush();
            return -1;
        }
        this.mFlagStore.erase(strArr[1], strArr[2]);
        printWriter.println("Erased " + strArr[1] + "." + strArr[2]);
        return 0;
    }

    private int unknownCmd(java.io.PrintWriter printWriter) {
        printWriter.println("This command is unknown.");
        printHelp(printWriter);
        printWriter.flush();
        return -1;
    }

    private boolean validateNumArguments(java.lang.String[] strArr, int i, java.lang.String str, java.io.PrintWriter printWriter) {
        return validateNumArguments(strArr, i, i, str, printWriter);
    }

    private boolean validateNumArguments(java.lang.String[] strArr, int i, int i2, java.lang.String str, java.io.PrintWriter printWriter) {
        int length = strArr.length - 1;
        if (length < i) {
            printWriter.println("Less than " + i + " arguments provided for \"" + str + "\" command.");
            return false;
        }
        if (length <= i2) {
            return true;
        }
        printWriter.println("More than " + i2 + " arguments provided for \"" + str + "\" command.");
        return false;
    }
}
