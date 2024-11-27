package com.android.server.utils;

/* loaded from: classes2.dex */
public final class PriorityDump {
    public static final java.lang.String PRIORITY_ARG = "--dump-priority";
    public static final java.lang.String PRIORITY_ARG_CRITICAL = "CRITICAL";
    public static final java.lang.String PRIORITY_ARG_HIGH = "HIGH";
    public static final java.lang.String PRIORITY_ARG_NORMAL = "NORMAL";
    private static final int PRIORITY_TYPE_CRITICAL = 1;
    private static final int PRIORITY_TYPE_HIGH = 2;
    private static final int PRIORITY_TYPE_INVALID = 0;
    private static final int PRIORITY_TYPE_NORMAL = 3;
    public static final java.lang.String PROTO_ARG = "--proto";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface PriorityType {
    }

    private PriorityDump() {
        throw new java.lang.UnsupportedOperationException();
    }

    public static void dump(com.android.server.utils.PriorityDump.PriorityDumper priorityDumper, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        int i = 0;
        if (strArr == null) {
            priorityDumper.dump(fileDescriptor, printWriter, strArr, false);
        }
        java.lang.String[] strArr2 = new java.lang.String[strArr.length];
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        while (i < strArr.length) {
            if (strArr[i].equals("--proto")) {
                z = true;
            } else if (strArr[i].equals(PRIORITY_ARG)) {
                int i4 = i + 1;
                if (i4 < strArr.length) {
                    i3 = getPriorityType(strArr[i4]);
                    i = i4;
                }
            } else {
                strArr2[i2] = strArr[i];
                i2++;
            }
            i++;
        }
        if (i2 < strArr.length) {
            strArr2 = (java.lang.String[]) java.util.Arrays.copyOf(strArr2, i2);
        }
        switch (i3) {
            case 1:
                priorityDumper.dumpCritical(fileDescriptor, printWriter, strArr2, z);
                break;
            case 2:
                priorityDumper.dumpHigh(fileDescriptor, printWriter, strArr2, z);
                break;
            case 3:
                priorityDumper.dumpNormal(fileDescriptor, printWriter, strArr2, z);
                break;
            default:
                priorityDumper.dump(fileDescriptor, printWriter, strArr2, z);
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int getPriorityType(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1986416409:
                if (str.equals(PRIORITY_ARG_NORMAL)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1560189025:
                if (str.equals(PRIORITY_ARG_CRITICAL)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2217378:
                if (str.equals(PRIORITY_ARG_HIGH)) {
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
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            default:
                return 0;
        }
    }

    public interface PriorityDumper {
        default void dumpCritical(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
        }

        default void dumpHigh(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
        }

        default void dumpNormal(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
        }

        default void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
            dumpCritical(fileDescriptor, printWriter, strArr, z);
            dumpHigh(fileDescriptor, printWriter, strArr, z);
            dumpNormal(fileDescriptor, printWriter, strArr, z);
        }
    }
}
