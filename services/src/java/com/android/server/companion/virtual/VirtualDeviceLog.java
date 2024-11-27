package com.android.server.companion.virtual;

/* loaded from: classes.dex */
final class VirtualDeviceLog {
    private static final int MAX_ENTRIES = 16;
    private final android.content.Context mContext;
    private final java.util.ArrayDeque<com.android.server.companion.virtual.VirtualDeviceLog.LogEntry> mLogEntries = new java.util.ArrayDeque<>();
    public static int TYPE_CREATED = 0;
    public static int TYPE_CLOSED = 1;
    private static final java.time.format.DateTimeFormatter DATE_FORMAT = java.time.format.DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS").withZone(java.time.ZoneId.systemDefault());

    VirtualDeviceLog(android.content.Context context) {
        this.mContext = context;
    }

    void logCreated(int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.companion.virtual.Flags.dumpHistory();
            addEntry(new com.android.server.companion.virtual.VirtualDeviceLog.LogEntry(TYPE_CREATED, i, java.lang.System.currentTimeMillis(), i2));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void logClosed(int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.companion.virtual.Flags.dumpHistory();
            addEntry(new com.android.server.companion.virtual.VirtualDeviceLog.LogEntry(TYPE_CLOSED, i, java.lang.System.currentTimeMillis(), i2));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void addEntry(com.android.server.companion.virtual.VirtualDeviceLog.LogEntry logEntry) {
        this.mLogEntries.push(logEntry);
        if (this.mLogEntries.size() > 16) {
            this.mLogEntries.removeLast();
        }
    }

    void dump(java.io.PrintWriter printWriter) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.companion.virtual.Flags.dumpHistory();
            printWriter.println("VirtualDevice Log:");
            com.android.server.companion.virtual.VirtualDeviceLog.UidToPackageNameCache uidToPackageNameCache = new com.android.server.companion.virtual.VirtualDeviceLog.UidToPackageNameCache(this.mContext.getPackageManager());
            java.util.Iterator<com.android.server.companion.virtual.VirtualDeviceLog.LogEntry> it = this.mLogEntries.iterator();
            while (it.hasNext()) {
                it.next().dump(printWriter, "  ", uidToPackageNameCache);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    static class LogEntry {
        private final int mDeviceId;
        private final int mOwnerUid;
        private final long mTimestamp;
        private final int mType;

        LogEntry(int i, int i2, long j, int i3) {
            this.mType = i;
            this.mDeviceId = i2;
            this.mTimestamp = j;
            this.mOwnerUid = i3;
        }

        void dump(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.companion.virtual.VirtualDeviceLog.UidToPackageNameCache uidToPackageNameCache) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(str);
            sb.append(com.android.server.companion.virtual.VirtualDeviceLog.DATE_FORMAT.format(java.time.Instant.ofEpochMilli(this.mTimestamp)));
            sb.append(" - ");
            sb.append(this.mType == com.android.server.companion.virtual.VirtualDeviceLog.TYPE_CREATED ? "START" : "CLOSE");
            sb.append(" Device ID: ");
            sb.append(this.mDeviceId);
            sb.append(" ");
            sb.append(this.mOwnerUid);
            sb.append(" (");
            sb.append(uidToPackageNameCache.getPackageName(this.mOwnerUid));
            sb.append(")");
            printWriter.println(sb);
        }
    }

    private static class UidToPackageNameCache {
        private final android.content.pm.PackageManager mPackageManager;
        private final android.util.SparseArray<java.lang.String> mUidToPackagesCache = new android.util.SparseArray<>();

        public UidToPackageNameCache(android.content.pm.PackageManager packageManager) {
            this.mPackageManager = packageManager;
        }

        java.lang.String getPackageName(int i) {
            java.lang.String str;
            if (this.mUidToPackagesCache.contains(i)) {
                return this.mUidToPackagesCache.get(i);
            }
            java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid != null && packagesForUid.length > 0) {
                str = packagesForUid[0];
                if (packagesForUid.length > 1) {
                    str = str + ",...";
                }
            } else {
                str = "";
            }
            this.mUidToPackagesCache.put(i, str);
            return str;
        }
    }
}
