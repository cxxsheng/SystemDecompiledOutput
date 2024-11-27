package com.android.server.am;

/* loaded from: classes.dex */
public final class BroadcastStats {
    static final java.util.Comparator<com.android.server.am.BroadcastStats.ActionEntry> ACTIONS_COMPARATOR = new java.util.Comparator<com.android.server.am.BroadcastStats.ActionEntry>() { // from class: com.android.server.am.BroadcastStats.1
        @Override // java.util.Comparator
        public int compare(com.android.server.am.BroadcastStats.ActionEntry actionEntry, com.android.server.am.BroadcastStats.ActionEntry actionEntry2) {
            if (actionEntry.mTotalDispatchTime < actionEntry2.mTotalDispatchTime) {
                return -1;
            }
            if (actionEntry.mTotalDispatchTime > actionEntry2.mTotalDispatchTime) {
                return 1;
            }
            return 0;
        }
    };
    long mEndRealtime;
    long mEndUptime;
    final android.util.ArrayMap<java.lang.String, com.android.server.am.BroadcastStats.ActionEntry> mActions = new android.util.ArrayMap<>();
    final long mStartRealtime = android.os.SystemClock.elapsedRealtime();
    final long mStartUptime = android.os.SystemClock.uptimeMillis();

    static final class PackageEntry {
        int mSendCount;

        PackageEntry() {
        }
    }

    static final class ViolationEntry {
        int mCount;

        ViolationEntry() {
        }
    }

    static final class ActionEntry {
        final java.lang.String mAction;
        long mMaxDispatchTime;
        int mReceiveCount;
        int mSkipCount;
        long mTotalDispatchTime;
        final android.util.ArrayMap<java.lang.String, com.android.server.am.BroadcastStats.PackageEntry> mPackages = new android.util.ArrayMap<>();
        final android.util.ArrayMap<java.lang.String, com.android.server.am.BroadcastStats.ViolationEntry> mBackgroundCheckViolations = new android.util.ArrayMap<>();

        ActionEntry(java.lang.String str) {
            this.mAction = str;
        }
    }

    public void addBroadcast(java.lang.String str, java.lang.String str2, int i, int i2, long j) {
        com.android.server.am.BroadcastStats.ActionEntry actionEntry = this.mActions.get(str);
        if (actionEntry == null) {
            actionEntry = new com.android.server.am.BroadcastStats.ActionEntry(str);
            this.mActions.put(str, actionEntry);
        }
        actionEntry.mReceiveCount += i;
        actionEntry.mSkipCount += i2;
        actionEntry.mTotalDispatchTime += j;
        if (actionEntry.mMaxDispatchTime < j) {
            actionEntry.mMaxDispatchTime = j;
        }
        com.android.server.am.BroadcastStats.PackageEntry packageEntry = actionEntry.mPackages.get(str2);
        if (packageEntry == null) {
            packageEntry = new com.android.server.am.BroadcastStats.PackageEntry();
            actionEntry.mPackages.put(str2, packageEntry);
        }
        packageEntry.mSendCount++;
    }

    public void addBackgroundCheckViolation(java.lang.String str, java.lang.String str2) {
        com.android.server.am.BroadcastStats.ActionEntry actionEntry = this.mActions.get(str);
        if (actionEntry == null) {
            actionEntry = new com.android.server.am.BroadcastStats.ActionEntry(str);
            this.mActions.put(str, actionEntry);
        }
        com.android.server.am.BroadcastStats.ViolationEntry violationEntry = actionEntry.mBackgroundCheckViolations.get(str2);
        if (violationEntry == null) {
            violationEntry = new com.android.server.am.BroadcastStats.ViolationEntry();
            actionEntry.mBackgroundCheckViolations.put(str2, violationEntry);
        }
        violationEntry.mCount++;
    }

    @dalvik.annotation.optimization.NeverCompile
    public boolean dumpStats(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mActions.size());
        for (int size = this.mActions.size() - 1; size >= 0; size--) {
            arrayList.add(this.mActions.valueAt(size));
        }
        java.util.Collections.sort(arrayList, ACTIONS_COMPARATOR);
        boolean z = false;
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            com.android.server.am.BroadcastStats.ActionEntry actionEntry = (com.android.server.am.BroadcastStats.ActionEntry) arrayList.get(size2);
            if (str2 == null || actionEntry.mPackages.containsKey(str2)) {
                printWriter.print(str);
                printWriter.print(actionEntry.mAction);
                printWriter.println(":");
                printWriter.print(str);
                printWriter.print("  Number received: ");
                printWriter.print(actionEntry.mReceiveCount);
                printWriter.print(", skipped: ");
                printWriter.println(actionEntry.mSkipCount);
                printWriter.print(str);
                printWriter.print("  Total dispatch time: ");
                android.util.TimeUtils.formatDuration(actionEntry.mTotalDispatchTime, printWriter);
                printWriter.print(", max: ");
                android.util.TimeUtils.formatDuration(actionEntry.mMaxDispatchTime, printWriter);
                printWriter.println();
                for (int size3 = actionEntry.mPackages.size() - 1; size3 >= 0; size3--) {
                    printWriter.print(str);
                    printWriter.print("  Package ");
                    printWriter.print(actionEntry.mPackages.keyAt(size3));
                    printWriter.print(": ");
                    printWriter.print(actionEntry.mPackages.valueAt(size3).mSendCount);
                    printWriter.println(" times");
                }
                for (int size4 = actionEntry.mBackgroundCheckViolations.size() - 1; size4 >= 0; size4--) {
                    printWriter.print(str);
                    printWriter.print("  Bg Check Violation ");
                    printWriter.print(actionEntry.mBackgroundCheckViolations.keyAt(size4));
                    printWriter.print(": ");
                    printWriter.print(actionEntry.mBackgroundCheckViolations.valueAt(size4).mCount);
                    printWriter.println(" times");
                }
                z = true;
            }
        }
        return z;
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dumpCheckinStats(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print("broadcast-stats,1,");
        printWriter.print(this.mStartRealtime);
        printWriter.print(",");
        printWriter.print(this.mEndRealtime == 0 ? android.os.SystemClock.elapsedRealtime() : this.mEndRealtime);
        printWriter.print(",");
        printWriter.println((this.mEndUptime == 0 ? android.os.SystemClock.uptimeMillis() : this.mEndUptime) - this.mStartUptime);
        for (int size = this.mActions.size() - 1; size >= 0; size--) {
            com.android.server.am.BroadcastStats.ActionEntry valueAt = this.mActions.valueAt(size);
            if (str == null || valueAt.mPackages.containsKey(str)) {
                printWriter.print("a,");
                printWriter.print(this.mActions.keyAt(size));
                printWriter.print(",");
                printWriter.print(valueAt.mReceiveCount);
                printWriter.print(",");
                printWriter.print(valueAt.mSkipCount);
                printWriter.print(",");
                printWriter.print(valueAt.mTotalDispatchTime);
                printWriter.print(",");
                printWriter.print(valueAt.mMaxDispatchTime);
                printWriter.println();
                for (int size2 = valueAt.mPackages.size() - 1; size2 >= 0; size2--) {
                    printWriter.print("p,");
                    printWriter.print(valueAt.mPackages.keyAt(size2));
                    com.android.server.am.BroadcastStats.PackageEntry valueAt2 = valueAt.mPackages.valueAt(size2);
                    printWriter.print(",");
                    printWriter.print(valueAt2.mSendCount);
                    printWriter.println();
                }
                for (int size3 = valueAt.mBackgroundCheckViolations.size() - 1; size3 >= 0; size3--) {
                    printWriter.print("v,");
                    printWriter.print(valueAt.mBackgroundCheckViolations.keyAt(size3));
                    com.android.server.am.BroadcastStats.ViolationEntry valueAt3 = valueAt.mBackgroundCheckViolations.valueAt(size3);
                    printWriter.print(",");
                    printWriter.print(valueAt3.mCount);
                    printWriter.println();
                }
            }
        }
    }
}
