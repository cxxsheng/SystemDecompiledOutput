package com.android.internal.app.procstats;

/* loaded from: classes4.dex */
public final class DumpUtils {
    public static final java.lang.String[] ADJ_MEM_NAMES_CSV;
    static final int[] ADJ_MEM_PROTO_ENUMS;
    static final java.lang.String[] ADJ_MEM_TAGS;
    public static final java.lang.String[] ADJ_SCREEN_NAMES_CSV;
    static final int[] ADJ_SCREEN_PROTO_ENUMS;
    static final java.lang.String[] ADJ_SCREEN_TAGS;
    static final java.lang.String CSV_SEP = "\t";
    private static final int[] PROCESS_STATS_STATE_TO_AGGREGATED_STATE;
    public static final java.lang.String[] STATE_LABELS;
    public static final java.lang.String STATE_LABEL_CACHED;
    public static final java.lang.String STATE_LABEL_TOTAL;
    public static final java.lang.String[] STATE_NAMES = new java.lang.String[16];
    public static final java.lang.String[] STATE_NAMES_CSV;
    static final int[] STATE_PROTO_ENUMS;
    static final java.lang.String[] STATE_TAGS;

    static {
        STATE_NAMES[0] = "Persist";
        STATE_NAMES[1] = "Top";
        STATE_NAMES[4] = "BFgs";
        STATE_NAMES[2] = "BTop";
        STATE_NAMES[3] = "Fgs";
        STATE_NAMES[5] = "ImpFg";
        STATE_NAMES[6] = "ImpBg";
        STATE_NAMES[7] = "Backup";
        STATE_NAMES[8] = "Service";
        STATE_NAMES[9] = "ServRst";
        STATE_NAMES[10] = "Receivr";
        STATE_NAMES[11] = "HeavyWt";
        STATE_NAMES[12] = "Home";
        STATE_NAMES[13] = "LastAct";
        STATE_NAMES[14] = "Cached";
        STATE_NAMES[15] = "Frozen";
        STATE_LABELS = new java.lang.String[16];
        STATE_LABELS[0] = "Persistent";
        STATE_LABELS[1] = "       Top";
        STATE_LABELS[4] = "   Bnd Fgs";
        STATE_LABELS[2] = "   Bnd Top";
        STATE_LABELS[3] = "       Fgs";
        STATE_LABELS[5] = "    Imp Fg";
        STATE_LABELS[6] = "    Imp Bg";
        STATE_LABELS[7] = "    Backup";
        STATE_LABELS[8] = "   Service";
        STATE_LABELS[9] = "Service Rs";
        STATE_LABELS[10] = "  Receiver";
        STATE_LABELS[11] = " Heavy Wgt";
        STATE_LABELS[12] = "    (Home)";
        STATE_LABELS[13] = "(Last Act)";
        STATE_LABELS[14] = "  (Cached)";
        STATE_LABELS[15] = "    Frozen";
        STATE_LABEL_CACHED = "  (Cached)";
        STATE_LABEL_TOTAL = "     TOTAL";
        STATE_NAMES_CSV = new java.lang.String[16];
        STATE_NAMES_CSV[0] = "pers";
        STATE_NAMES_CSV[1] = "top";
        STATE_NAMES_CSV[4] = "bfgs";
        STATE_NAMES_CSV[2] = "btop";
        STATE_NAMES_CSV[3] = "fgs";
        STATE_NAMES_CSV[5] = "impfg";
        STATE_NAMES_CSV[6] = "impbg";
        STATE_NAMES_CSV[7] = android.content.Context.BACKUP_SERVICE;
        STATE_NAMES_CSV[8] = "service";
        STATE_NAMES_CSV[9] = "service-rs";
        STATE_NAMES_CSV[10] = "receiver";
        STATE_NAMES_CSV[11] = "heavy";
        STATE_NAMES_CSV[12] = "home";
        STATE_NAMES_CSV[13] = "lastact";
        STATE_NAMES_CSV[14] = "cached";
        STATE_NAMES_CSV[15] = "frzn";
        STATE_TAGS = new java.lang.String[16];
        STATE_TAGS[0] = "p";
        STATE_TAGS[1] = "t";
        STATE_TAGS[4] = "y";
        STATE_TAGS[2] = "z";
        STATE_TAGS[3] = "g";
        STATE_TAGS[5] = android.app.backup.FullBackup.FILES_TREE_TOKEN;
        STATE_TAGS[6] = android.app.blob.XmlTags.TAG_BLOB;
        STATE_TAGS[7] = android.app.blob.XmlTags.ATTR_UID;
        STATE_TAGS[8] = android.app.blob.XmlTags.TAG_SESSION;
        STATE_TAGS[9] = "x";
        STATE_TAGS[10] = "r";
        STATE_TAGS[11] = "w";
        STATE_TAGS[12] = "h";
        STATE_TAGS[13] = android.app.blob.XmlTags.TAG_LEASEE;
        STATE_TAGS[14] = android.app.backup.FullBackup.APK_TREE_TOKEN;
        STATE_TAGS[15] = "e";
        STATE_PROTO_ENUMS = new int[16];
        STATE_PROTO_ENUMS[0] = 1;
        STATE_PROTO_ENUMS[1] = 2;
        STATE_PROTO_ENUMS[4] = 20;
        STATE_PROTO_ENUMS[2] = 19;
        STATE_PROTO_ENUMS[3] = 16;
        STATE_PROTO_ENUMS[5] = 3;
        STATE_PROTO_ENUMS[6] = 4;
        STATE_PROTO_ENUMS[7] = 5;
        STATE_PROTO_ENUMS[8] = 6;
        STATE_PROTO_ENUMS[9] = 7;
        STATE_PROTO_ENUMS[10] = 8;
        STATE_PROTO_ENUMS[11] = 9;
        STATE_PROTO_ENUMS[12] = 10;
        STATE_PROTO_ENUMS[13] = 11;
        STATE_PROTO_ENUMS[14] = 12;
        STATE_PROTO_ENUMS[15] = 17;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE = new int[16];
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[0] = 1;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[1] = 2;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[4] = 3;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[2] = 3;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[3] = 4;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[5] = 5;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[6] = 6;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[7] = 6;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[8] = 6;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[9] = 0;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[10] = 7;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[11] = 6;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[12] = 8;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[13] = 8;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[14] = 8;
        PROCESS_STATS_STATE_TO_AGGREGATED_STATE[15] = 8;
        ADJ_SCREEN_NAMES_CSV = new java.lang.String[]{"off", "on"};
        ADJ_MEM_NAMES_CSV = new java.lang.String[]{"norm", "mod", "low", "crit"};
        ADJ_SCREEN_TAGS = new java.lang.String[]{android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, "1"};
        ADJ_SCREEN_PROTO_ENUMS = new int[]{1, 2};
        ADJ_MEM_TAGS = new java.lang.String[]{"n", "m", android.app.blob.XmlTags.TAG_LEASEE, "c"};
        ADJ_MEM_PROTO_ENUMS = new int[]{1, 2, 3, 4};
    }

    private DumpUtils() {
    }

    public static void printScreenLabel(java.io.PrintWriter printWriter, int i) {
        switch (i) {
            case -1:
                printWriter.print("     ");
                break;
            case 0:
                printWriter.print("SOff/");
                break;
            case 4:
                printWriter.print(" SOn/");
                break;
            default:
                printWriter.print("????/");
                break;
        }
    }

    public static void printScreenLabelCsv(java.io.PrintWriter printWriter, int i) {
        switch (i) {
            case -1:
                break;
            case 0:
                printWriter.print(ADJ_SCREEN_NAMES_CSV[0]);
                break;
            case 4:
                printWriter.print(ADJ_SCREEN_NAMES_CSV[1]);
                break;
            default:
                printWriter.print("???");
                break;
        }
    }

    public static void printMemLabel(java.io.PrintWriter printWriter, int i, char c) {
        switch (i) {
            case -1:
                printWriter.print("    ");
                if (c != 0) {
                    printWriter.print(' ');
                    break;
                }
                break;
            case 0:
                printWriter.print("Norm");
                if (c != 0) {
                    printWriter.print(c);
                    break;
                }
                break;
            case 1:
                printWriter.print(" Mod");
                if (c != 0) {
                    printWriter.print(c);
                    break;
                }
                break;
            case 2:
                printWriter.print(" Low");
                if (c != 0) {
                    printWriter.print(c);
                    break;
                }
                break;
            case 3:
                printWriter.print("Crit");
                if (c != 0) {
                    printWriter.print(c);
                    break;
                }
                break;
            default:
                printWriter.print("????");
                if (c != 0) {
                    printWriter.print(c);
                    break;
                }
                break;
        }
    }

    public static void printMemLabelCsv(java.io.PrintWriter printWriter, int i) {
        if (i >= 0) {
            if (i <= 3) {
                printWriter.print(ADJ_MEM_NAMES_CSV[i]);
            } else {
                printWriter.print("???");
            }
        }
    }

    public static void printPercent(java.io.PrintWriter printWriter, double d) {
        double d2 = d * 100.0d;
        if (d2 < 1.0d) {
            printWriter.print(java.lang.String.format("%.2f", java.lang.Double.valueOf(d2)));
        } else if (d2 < 10.0d) {
            printWriter.print(java.lang.String.format("%.1f", java.lang.Double.valueOf(d2)));
        } else {
            printWriter.print(java.lang.String.format("%.0f", java.lang.Double.valueOf(d2)));
        }
        printWriter.print("%");
    }

    public static void printProcStateTag(java.io.PrintWriter printWriter, int i) {
        printArrayEntry(printWriter, STATE_TAGS, printArrayEntry(printWriter, ADJ_MEM_TAGS, printArrayEntry(printWriter, ADJ_SCREEN_TAGS, i, 64), 16), 1);
    }

    public static void printProcStateTagProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, long j3, int i) {
        printProto(protoOutputStream, j3, STATE_PROTO_ENUMS, printProto(protoOutputStream, j2, ADJ_MEM_PROTO_ENUMS, printProto(protoOutputStream, j, ADJ_SCREEN_PROTO_ENUMS, i, 64), 16), 1);
    }

    public static void printAdjTag(java.io.PrintWriter printWriter, int i) {
        printArrayEntry(printWriter, ADJ_MEM_TAGS, printArrayEntry(printWriter, ADJ_SCREEN_TAGS, i, 4), 1);
    }

    public static void printProcStateAdjTagProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, int i) {
        printProto(protoOutputStream, j2, ADJ_MEM_PROTO_ENUMS, printProto(protoOutputStream, j, ADJ_SCREEN_PROTO_ENUMS, i, 64), 16);
    }

    public static void printProcStateDurationProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i, long j2) {
        long start = protoOutputStream.start(j);
        printProto(protoOutputStream, 1159641169923L, STATE_PROTO_ENUMS, i, 1);
        protoOutputStream.write(1112396529668L, j2);
        protoOutputStream.end(start);
    }

    public static void printProcStateTagAndValue(java.io.PrintWriter printWriter, int i, long j) {
        printWriter.print(',');
        printProcStateTag(printWriter, i);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(j);
    }

    public static void printAdjTagAndValue(java.io.PrintWriter printWriter, int i, long j) {
        printWriter.print(',');
        printAdjTag(printWriter, i);
        printWriter.print(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        printWriter.print(j);
    }

    public static long dumpSingleTime(java.io.PrintWriter printWriter, java.lang.String str, long[] jArr, int i, long j, long j2) {
        long j3 = 0;
        int i2 = -1;
        int i3 = 0;
        while (i3 < 8) {
            int i4 = -1;
            int i5 = 0;
            while (i5 < 4) {
                int i6 = i5 + i3;
                long j4 = jArr[i6];
                java.lang.String str2 = "";
                if (i == i6) {
                    j4 += j2 - j;
                    if (printWriter != null) {
                        str2 = " (running)";
                    }
                }
                if (j4 != 0) {
                    if (printWriter != null) {
                        printWriter.print(str);
                        printScreenLabel(printWriter, i2 != i3 ? i3 : -1);
                        printMemLabel(printWriter, i4 != i5 ? i5 : -1, (char) 0);
                        printWriter.print(": ");
                        android.util.TimeUtils.formatDuration(j4, printWriter);
                        printWriter.println(str2);
                        i2 = i3;
                        i4 = i5;
                    }
                    j3 += j4;
                }
                i5++;
            }
            i3 += 4;
        }
        if (j3 != 0 && printWriter != null) {
            printWriter.print(str);
            printWriter.print("    TOTAL: ");
            android.util.TimeUtils.formatDuration(j3, printWriter);
            printWriter.println();
        }
        return j3;
    }

    public static void dumpAdjTimesCheckin(java.io.PrintWriter printWriter, java.lang.String str, long[] jArr, int i, long j, long j2) {
        for (int i2 = 0; i2 < 8; i2 += 4) {
            for (int i3 = 0; i3 < 4; i3++) {
                int i4 = i3 + i2;
                long j3 = jArr[i4];
                if (i == i4) {
                    j3 += j2 - j;
                }
                if (j3 != 0) {
                    printAdjTagAndValue(printWriter, i4, j3);
                }
            }
        }
    }

    private static void dumpStateHeadersCsv(java.io.PrintWriter printWriter, java.lang.String str, int[] iArr, int[] iArr2, int[] iArr3) {
        boolean z;
        int length = iArr != null ? iArr.length : 1;
        int length2 = iArr2 != null ? iArr2.length : 1;
        int length3 = iArr3 != null ? iArr3.length : 1;
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < length2; i2++) {
                for (int i3 = 0; i3 < length3; i3++) {
                    printWriter.print(str);
                    if (iArr != null && iArr.length > 1) {
                        printScreenLabelCsv(printWriter, iArr[i]);
                        z = true;
                    } else {
                        z = false;
                    }
                    if (iArr2 != null && iArr2.length > 1) {
                        if (z) {
                            printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        }
                        printMemLabelCsv(printWriter, iArr2[i2]);
                        z = true;
                    }
                    if (iArr3 != null && iArr3.length > 1) {
                        if (z) {
                            printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        }
                        printWriter.print(STATE_NAMES_CSV[iArr3[i3]]);
                    }
                }
            }
        }
    }

    public static void dumpProcessSummaryLocked(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, java.util.ArrayList<com.android.internal.app.procstats.ProcessState> arrayList, int[] iArr, int[] iArr2, int[] iArr3, long j, long j2) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            arrayList.get(size).dumpSummary(printWriter, str, str2, iArr, iArr2, iArr3, j, j2);
        }
    }

    public static void dumpProcessListCsv(java.io.PrintWriter printWriter, java.util.ArrayList<com.android.internal.app.procstats.ProcessState> arrayList, boolean z, int[] iArr, boolean z2, int[] iArr2, boolean z3, int[] iArr3, long j) {
        printWriter.print("process");
        printWriter.print(CSV_SEP);
        printWriter.print("uid");
        printWriter.print(CSV_SEP);
        printWriter.print("vers");
        dumpStateHeadersCsv(printWriter, CSV_SEP, z ? iArr : null, z2 ? iArr2 : null, z3 ? iArr3 : null);
        printWriter.println();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.internal.app.procstats.ProcessState processState = arrayList.get(size);
            printWriter.print(processState.getName());
            printWriter.print(CSV_SEP);
            android.os.UserHandle.formatUid(printWriter, processState.getUid());
            printWriter.print(CSV_SEP);
            printWriter.print(processState.getVersion());
            processState.dumpCsv(printWriter, z, iArr, z2, iArr2, z3, iArr3, j);
            printWriter.println();
        }
    }

    public static int printArrayEntry(java.io.PrintWriter printWriter, java.lang.String[] strArr, int i, int i2) {
        int i3 = i / i2;
        if (i3 >= 0 && i3 < strArr.length) {
            printWriter.print(strArr[i3]);
        } else {
            printWriter.print('?');
        }
        return i - (i3 * i2);
    }

    public static int printProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, int[] iArr, int i, int i2) {
        int i3 = i / i2;
        if (i3 >= 0 && i3 < iArr.length) {
            protoOutputStream.write(j, iArr[i3]);
        }
        return i - (i3 * i2);
    }

    public static java.lang.String collapseString(java.lang.String str, java.lang.String str2) {
        if (str2.startsWith(str)) {
            int length = str2.length();
            int length2 = str.length();
            if (length == length2) {
                return "";
            }
            if (length >= length2 && str2.charAt(length2) == '.') {
                return str2.substring(length2);
            }
        }
        return str2;
    }

    public static int aggregateCurrentProcessState(int i) {
        int i2;
        int i3 = i / 64;
        try {
            i2 = PROCESS_STATS_STATE_TO_AGGREGATED_STATE[i % 16];
        } catch (java.lang.IndexOutOfBoundsException e) {
            i2 = 0;
        }
        return (i2 << 15) | i3;
    }

    public static void printAggregatedProcStateTagProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, int i) {
        try {
            protoOutputStream.write(j2, i >> 15);
        } catch (java.lang.IndexOutOfBoundsException e) {
            protoOutputStream.write(j2, 0);
        }
        try {
            protoOutputStream.write(j, ADJ_SCREEN_PROTO_ENUMS[i & 15]);
        } catch (java.lang.IndexOutOfBoundsException e2) {
            protoOutputStream.write(j, 0);
        }
    }
}
