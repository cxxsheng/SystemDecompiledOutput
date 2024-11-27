package com.android.internal.app.procstats;

/* loaded from: classes4.dex */
public class SysMemUsageTable extends com.android.internal.app.procstats.SparseMappingTable.Table {
    public SysMemUsageTable(com.android.internal.app.procstats.SparseMappingTable sparseMappingTable) {
        super(sparseMappingTable);
    }

    public void mergeStats(com.android.internal.app.procstats.SysMemUsageTable sysMemUsageTable) {
        int keyCount = sysMemUsageTable.getKeyCount();
        for (int i = 0; i < keyCount; i++) {
            int keyAt = sysMemUsageTable.getKeyAt(i);
            mergeStats(com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt), sysMemUsageTable.getArrayForKey(keyAt), com.android.internal.app.procstats.SparseMappingTable.getIndexFromKey(keyAt));
        }
    }

    public void mergeStats(int i, long[] jArr, int i2) {
        int orAddKey = getOrAddKey((byte) i, 16);
        mergeSysMemUsage(getArrayForKey(orAddKey), com.android.internal.app.procstats.SparseMappingTable.getIndexFromKey(orAddKey), jArr, i2);
    }

    public long[] getTotalMemUsage() {
        long[] jArr = new long[16];
        int keyCount = getKeyCount();
        for (int i = 0; i < keyCount; i++) {
            int keyAt = getKeyAt(i);
            mergeSysMemUsage(jArr, 0, getArrayForKey(keyAt), com.android.internal.app.procstats.SparseMappingTable.getIndexFromKey(keyAt));
        }
        return jArr;
    }

    public static void mergeSysMemUsage(long[] jArr, int i, long[] jArr2, int i2) {
        int i3 = i + 0;
        long j = jArr[i3];
        long j2 = jArr2[i2 + 0];
        int i4 = 1;
        if (j == 0) {
            jArr[i3] = j2;
            while (i4 < 16) {
                jArr[i + i4] = jArr2[i2 + i4];
                i4++;
            }
            return;
        }
        if (j2 > 0) {
            long j3 = j + j2;
            jArr[i3] = j3;
            for (int i5 = 16; i4 < i5; i5 = 16) {
                int i6 = i + i4;
                int i7 = i2 + i4;
                if (jArr[i6] > jArr2[i7]) {
                    jArr[i6] = jArr2[i7];
                }
                int i8 = i4;
                jArr[i6 + 1] = (long) (((jArr[r10] * j) + (jArr2[i7 + 1] * j2)) / j3);
                int i9 = i6 + 2;
                int i10 = i7 + 2;
                if (jArr[i9] < jArr2[i10]) {
                    jArr[i9] = jArr2[i10];
                }
                i4 = i8 + 3;
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, int[] iArr, int[] iArr2) {
        int i;
        int i2;
        int i3 = -1;
        for (int i4 = 0; i4 < iArr.length; i4++) {
            int i5 = -1;
            for (int i6 = 0; i6 < iArr2.length; i6++) {
                int i7 = iArr[i4];
                int i8 = iArr2[i6];
                int i9 = (i7 + i8) * 16;
                long valueForId = getValueForId((byte) i9, 0);
                if (valueForId > 0) {
                    printWriter.print(str);
                    if (iArr.length <= 1) {
                        i = i3;
                    } else {
                        com.android.internal.app.procstats.DumpUtils.printScreenLabel(printWriter, i3 != i7 ? i7 : -1);
                        i = i7;
                    }
                    if (iArr2.length <= 1) {
                        i2 = i5;
                    } else {
                        com.android.internal.app.procstats.DumpUtils.printMemLabel(printWriter, i5 != i8 ? i8 : -1, (char) 0);
                        i2 = i8;
                    }
                    printWriter.print(": ");
                    printWriter.print(valueForId);
                    printWriter.println(" samples:");
                    dumpCategory(printWriter, str, "  Cached", i9, 1);
                    dumpCategory(printWriter, str, "  Free", i9, 4);
                    dumpCategory(printWriter, str, "  ZRam", i9, 7);
                    dumpCategory(printWriter, str, "  Kernel", i9, 10);
                    dumpCategory(printWriter, str, "  Native", i9, 13);
                    i5 = i2;
                    i3 = i;
                }
            }
        }
    }

    private void dumpCategory(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, int i, int i2) {
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print(": ");
        byte b = (byte) i;
        android.util.DebugUtils.printSizeValue(printWriter, getValueForId(b, i2) * 1024);
        printWriter.print(" min, ");
        android.util.DebugUtils.printSizeValue(printWriter, getValueForId(b, i2 + 1) * 1024);
        printWriter.print(" avg, ");
        android.util.DebugUtils.printSizeValue(printWriter, getValueForId(b, i2 + 2) * 1024);
        printWriter.println(" max");
    }
}
