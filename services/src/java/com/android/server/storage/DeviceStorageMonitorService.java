package com.android.server.storage;

/* loaded from: classes2.dex */
public class DeviceStorageMonitorService extends com.android.server.SystemService {
    public static final java.lang.String EXTRA_SEQUENCE = "seq";
    private static final long HIGH_CHECK_INTERVAL = 36000000;
    private static final long LOW_CHECK_INTERVAL = 60000;
    private static final int MSG_CHECK_HIGH = 2;
    private static final int MSG_CHECK_LOW = 1;
    static final int OPTION_FORCE_UPDATE = 1;
    static final java.lang.String SERVICE = "devicestoragemonitor";
    private static final java.lang.String TAG = "DeviceStorageMonitorService";
    private static final java.lang.String TV_NOTIFICATION_CHANNEL_ID = "devicestoragemonitor.tv";
    private com.android.server.storage.DeviceStorageMonitorService.CacheFileDeletedObserver mCacheFileDeletedObserver;
    private volatile int mForceLevel;
    private final android.os.Handler mHandler;
    private final android.os.HandlerThread mHandlerThread;
    private final com.android.server.storage.DeviceStorageMonitorInternal mLocalService;
    private android.app.NotificationManager mNotifManager;
    private final android.os.Binder mRemoteService;
    private final java.util.concurrent.atomic.AtomicInteger mSeq;
    private final android.util.ArrayMap<java.util.UUID, com.android.server.storage.DeviceStorageMonitorService.State> mStates;
    private static final long DEFAULT_LOG_DELTA_BYTES = android.util.DataUnit.MEBIBYTES.toBytes(64);
    private static final long BOOT_IMAGE_STORAGE_REQUIREMENT = android.util.DataUnit.MEBIBYTES.toBytes(250);

    private static class State {
        private static final int LEVEL_FULL = 2;
        private static final int LEVEL_LOW = 1;
        private static final int LEVEL_NORMAL = 0;
        private static final int LEVEL_UNKNOWN = -1;
        public long lastUsableBytes;
        public int level;

        private State() {
            this.level = 0;
            this.lastUsableBytes = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isEntering(int i, int i2, int i3) {
            return i3 >= i && (i2 < i || i2 == -1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isLeaving(int i, int i2, int i3) {
            return i3 < i && (i2 >= i || i2 == -1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static java.lang.String levelToString(int i) {
            switch (i) {
                case -1:
                    return "UNKNOWN";
                case 0:
                    return com.android.server.utils.PriorityDump.PRIORITY_ARG_NORMAL;
                case 1:
                    return "LOW";
                case 2:
                    return "FULL";
                default:
                    return java.lang.Integer.toString(i);
            }
        }
    }

    private com.android.server.storage.DeviceStorageMonitorService.State findOrCreateState(java.util.UUID uuid) {
        com.android.server.storage.DeviceStorageMonitorService.State state = this.mStates.get(uuid);
        if (state == null) {
            com.android.server.storage.DeviceStorageMonitorService.State state2 = new com.android.server.storage.DeviceStorageMonitorService.State();
            this.mStates.put(uuid, state2);
            return state2;
        }
        return state;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkLow() {
        int i;
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) getContext().getSystemService(android.os.storage.StorageManager.class);
        int i2 = this.mSeq.get();
        for (android.os.storage.VolumeInfo volumeInfo : storageManager.getWritablePrivateVolumes()) {
            java.io.File path = volumeInfo.getPath();
            long storageFullBytes = storageManager.getStorageFullBytes(path);
            long storageLowBytes = storageManager.getStorageLowBytes(path);
            if (path.getUsableSpace() < (3 * storageLowBytes) / 2) {
                try {
                    ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).freeStorage(volumeInfo.getFsUuid(), storageLowBytes * 2, 0);
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, e);
                }
            }
            java.util.UUID convert = android.os.storage.StorageManager.convert(volumeInfo.getFsUuid());
            com.android.server.storage.DeviceStorageMonitorService.State findOrCreateState = findOrCreateState(convert);
            long totalSpace = path.getTotalSpace();
            long usableSpace = path.getUsableSpace();
            int i3 = findOrCreateState.level;
            if (this.mForceLevel != -1) {
                i = this.mForceLevel;
                i3 = -1;
            } else if (usableSpace <= storageFullBytes) {
                i = 2;
            } else if (usableSpace <= storageLowBytes) {
                i = 1;
            } else if (android.os.storage.StorageManager.UUID_DEFAULT.equals(convert) && usableSpace < BOOT_IMAGE_STORAGE_REQUIREMENT) {
                i = 1;
            } else {
                i = 0;
            }
            if (java.lang.Math.abs(findOrCreateState.lastUsableBytes - usableSpace) > DEFAULT_LOG_DELTA_BYTES || i3 != i) {
                com.android.server.EventLogTags.writeStorageState(convert.toString(), i3, i, usableSpace, totalSpace);
                findOrCreateState.lastUsableBytes = usableSpace;
            }
            updateNotifications(volumeInfo, i3, i);
            updateBroadcasts(volumeInfo, i3, i, i2);
            findOrCreateState.level = i;
        }
        if (!this.mHandler.hasMessages(1)) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), 60000L);
        }
        if (!this.mHandler.hasMessages(2)) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2), HIGH_CHECK_INTERVAL);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkHigh() {
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) getContext().getSystemService(android.os.storage.StorageManager.class);
        int i = android.provider.DeviceConfig.getInt("storage_native_boot", "storage_threshold_percent_high", 20);
        for (android.os.storage.VolumeInfo volumeInfo : storageManager.getWritablePrivateVolumes()) {
            java.io.File path = volumeInfo.getPath();
            if (path.getUsableSpace() < (path.getTotalSpace() * i) / 100) {
                try {
                    ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).freeAllAppCacheAboveQuota(volumeInfo.getFsUuid());
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, e);
                }
            }
        }
        if (!this.mHandler.hasMessages(2)) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2), HIGH_CHECK_INTERVAL);
        }
    }

    public DeviceStorageMonitorService(android.content.Context context) {
        super(context);
        this.mSeq = new java.util.concurrent.atomic.AtomicInteger(1);
        this.mForceLevel = -1;
        this.mStates = new android.util.ArrayMap<>();
        this.mLocalService = new com.android.server.storage.DeviceStorageMonitorInternal() { // from class: com.android.server.storage.DeviceStorageMonitorService.2
            @Override // com.android.server.storage.DeviceStorageMonitorInternal
            public void checkMemory() {
                com.android.server.storage.DeviceStorageMonitorService.this.mHandler.removeMessages(1);
                com.android.server.storage.DeviceStorageMonitorService.this.mHandler.obtainMessage(1).sendToTarget();
            }

            @Override // com.android.server.storage.DeviceStorageMonitorInternal
            public boolean isMemoryLow() {
                return android.os.Environment.getDataDirectory().getUsableSpace() < getMemoryLowThreshold();
            }

            @Override // com.android.server.storage.DeviceStorageMonitorInternal
            public long getMemoryLowThreshold() {
                return ((android.os.storage.StorageManager) com.android.server.storage.DeviceStorageMonitorService.this.getContext().getSystemService(android.os.storage.StorageManager.class)).getStorageLowBytes(android.os.Environment.getDataDirectory());
            }
        };
        this.mRemoteService = new android.os.Binder() { // from class: com.android.server.storage.DeviceStorageMonitorService.3
            @Override // android.os.Binder
            protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
                if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.storage.DeviceStorageMonitorService.this.getContext(), com.android.server.storage.DeviceStorageMonitorService.TAG, printWriter)) {
                    com.android.server.storage.DeviceStorageMonitorService.this.dumpImpl(fileDescriptor, printWriter, strArr);
                }
            }

            public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
                com.android.server.storage.DeviceStorageMonitorService.this.new Shell().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }
        };
        this.mHandlerThread = new android.os.HandlerThread(TAG, 10);
        this.mHandlerThread.start();
        this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper()) { // from class: com.android.server.storage.DeviceStorageMonitorService.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.server.storage.DeviceStorageMonitorService.this.checkLow();
                        break;
                    case 2:
                        com.android.server.storage.DeviceStorageMonitorService.this.checkHigh();
                        break;
                }
            }
        };
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        android.content.Context context = getContext();
        this.mNotifManager = (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class);
        this.mCacheFileDeletedObserver = new com.android.server.storage.DeviceStorageMonitorService.CacheFileDeletedObserver();
        this.mCacheFileDeletedObserver.startWatching();
        if (context.getPackageManager().hasSystemFeature("android.software.leanback")) {
            this.mNotifManager.createNotificationChannel(new android.app.NotificationChannel(TV_NOTIFICATION_CHANNEL_ID, context.getString(android.R.string.deleteText), 4));
        }
        publishBinderService(SERVICE, this.mRemoteService);
        publishLocalService(com.android.server.storage.DeviceStorageMonitorInternal.class, this.mLocalService);
        this.mHandler.removeMessages(1);
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    class Shell extends android.os.ShellCommand {
        Shell() {
        }

        public int onCommand(java.lang.String str) {
            return com.android.server.storage.DeviceStorageMonitorService.this.onShellCommand(this, str);
        }

        public void onHelp() {
            com.android.server.storage.DeviceStorageMonitorService.dumpHelp(getOutPrintWriter());
        }
    }

    int parseOptions(com.android.server.storage.DeviceStorageMonitorService.Shell shell) {
        int i = 0;
        while (true) {
            java.lang.String nextOption = shell.getNextOption();
            if (nextOption != null) {
                if ("-f".equals(nextOption)) {
                    i = 1;
                }
            } else {
                return i;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    int onShellCommand(com.android.server.storage.DeviceStorageMonitorService.Shell shell, java.lang.String str) {
        char c;
        if (str == null) {
            return shell.handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = shell.getOutPrintWriter();
        switch (str.hashCode()) {
            case 108404047:
                if (str.equals("reset")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1526871410:
                if (str.equals("force-low")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1692300408:
                if (str.equals("force-not-low")) {
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
                int parseOptions = parseOptions(shell);
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                this.mForceLevel = 1;
                int incrementAndGet = this.mSeq.incrementAndGet();
                if ((parseOptions & 1) != 0) {
                    this.mHandler.removeMessages(1);
                    this.mHandler.obtainMessage(1).sendToTarget();
                    outPrintWriter.println(incrementAndGet);
                }
                return 0;
            case 1:
                int parseOptions2 = parseOptions(shell);
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                this.mForceLevel = 0;
                int incrementAndGet2 = this.mSeq.incrementAndGet();
                if ((parseOptions2 & 1) != 0) {
                    this.mHandler.removeMessages(1);
                    this.mHandler.obtainMessage(1).sendToTarget();
                    outPrintWriter.println(incrementAndGet2);
                }
                return 0;
            case 2:
                int parseOptions3 = parseOptions(shell);
                getContext().enforceCallingOrSelfPermission("android.permission.DEVICE_POWER", null);
                this.mForceLevel = -1;
                int incrementAndGet3 = this.mSeq.incrementAndGet();
                if ((parseOptions3 & 1) != 0) {
                    this.mHandler.removeMessages(1);
                    this.mHandler.obtainMessage(1).sendToTarget();
                    outPrintWriter.println(incrementAndGet3);
                }
                return 0;
            default:
                return shell.handleDefaultCommands(str);
        }
    }

    static void dumpHelp(java.io.PrintWriter printWriter) {
        printWriter.println("Device storage monitor service (devicestoragemonitor) commands:");
        printWriter.println("  help");
        printWriter.println("    Print this help text.");
        printWriter.println("  force-low [-f]");
        printWriter.println("    Force storage to be low, freezing storage state.");
        printWriter.println("    -f: force a storage change broadcast be sent, prints new sequence.");
        printWriter.println("  force-not-low [-f]");
        printWriter.println("    Force storage to not be low, freezing storage state.");
        printWriter.println("    -f: force a storage change broadcast be sent, prints new sequence.");
        printWriter.println("  reset [-f]");
        printWriter.println("    Unfreeze storage state, returning to current real values.");
        printWriter.println("    -f: force a storage change broadcast be sent, prints new sequence.");
    }

    void dumpImpl(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
        if (strArr == null || strArr.length == 0 || "-a".equals(strArr[0])) {
            android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) getContext().getSystemService(android.os.storage.StorageManager.class);
            indentingPrintWriter.println("Known volumes:");
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mStates.size(); i++) {
                java.util.UUID keyAt = this.mStates.keyAt(i);
                com.android.server.storage.DeviceStorageMonitorService.State valueAt = this.mStates.valueAt(i);
                if (android.os.storage.StorageManager.UUID_DEFAULT.equals(keyAt)) {
                    indentingPrintWriter.println("Default:");
                } else {
                    indentingPrintWriter.println(keyAt + ":");
                }
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.printPair("level", com.android.server.storage.DeviceStorageMonitorService.State.levelToString(valueAt.level));
                indentingPrintWriter.printPair("lastUsableBytes", java.lang.Long.valueOf(valueAt.lastUsableBytes));
                indentingPrintWriter.println();
                java.util.Iterator it = storageManager.getWritablePrivateVolumes().iterator();
                while (true) {
                    if (it.hasNext()) {
                        android.os.storage.VolumeInfo volumeInfo = (android.os.storage.VolumeInfo) it.next();
                        java.io.File path = volumeInfo.getPath();
                        if (java.util.Objects.equals(keyAt, android.os.storage.StorageManager.convert(volumeInfo.getFsUuid()))) {
                            indentingPrintWriter.print("lowBytes=");
                            indentingPrintWriter.print(storageManager.getStorageLowBytes(path));
                            indentingPrintWriter.print(" fullBytes=");
                            indentingPrintWriter.println(storageManager.getStorageFullBytes(path));
                            indentingPrintWriter.print("path=");
                            indentingPrintWriter.println(path);
                            break;
                        }
                    }
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("mSeq", java.lang.Integer.valueOf(this.mSeq.get()));
            indentingPrintWriter.printPair("mForceState", com.android.server.storage.DeviceStorageMonitorService.State.levelToString(this.mForceLevel));
            indentingPrintWriter.println();
            indentingPrintWriter.println();
            return;
        }
        new com.android.server.storage.DeviceStorageMonitorService.Shell().exec(this.mRemoteService, (java.io.FileDescriptor) null, fileDescriptor, (java.io.FileDescriptor) null, strArr, (android.os.ShellCallback) null, new android.os.ResultReceiver(null));
    }

    private void updateNotifications(android.os.storage.VolumeInfo volumeInfo, int i, int i2) {
        android.content.Context context = getContext();
        java.util.UUID convert = android.os.storage.StorageManager.convert(volumeInfo.getFsUuid());
        if (!com.android.server.storage.DeviceStorageMonitorService.State.isEntering(1, i, i2)) {
            if (com.android.server.storage.DeviceStorageMonitorService.State.isLeaving(1, i, i2)) {
                this.mNotifManager.cancelAsUser(convert.toString(), 23, android.os.UserHandle.ALL);
                com.android.internal.util.FrameworkStatsLog.write(130, java.util.Objects.toString(volumeInfo.getDescription()), 1);
                return;
            }
            return;
        }
        android.content.Intent intent = new android.content.Intent("android.os.storage.action.MANAGE_STORAGE");
        intent.putExtra("android.os.storage.extra.UUID", convert);
        intent.addFlags(268435456);
        java.lang.CharSequence text = context.getText(android.R.string.lockscreen_permanent_disabled_sim_instructions);
        java.lang.CharSequence text2 = context.getText(android.R.string.lockscreen_pattern_instructions);
        android.app.Notification build = new android.app.Notification.Builder(context, com.android.internal.notification.SystemNotificationChannels.ALERTS).setSmallIcon(android.R.drawable.stat_ecb_mode).setTicker(text).setColor(context.getColor(android.R.color.system_notification_accent_color)).setContentTitle(text).setContentText(text2).setContentIntent(android.app.PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, android.os.UserHandle.CURRENT)).setStyle(new android.app.Notification.BigTextStyle().bigText(text2)).setVisibility(1).setCategory("sys").extend(new android.app.Notification.TvExtender().setChannelId(TV_NOTIFICATION_CHANNEL_ID)).build();
        build.flags |= 32;
        this.mNotifManager.notifyAsUser(convert.toString(), 23, build, android.os.UserHandle.ALL);
        com.android.internal.util.FrameworkStatsLog.write(130, java.util.Objects.toString(volumeInfo.getDescription()), 2);
    }

    private void updateBroadcasts(android.os.storage.VolumeInfo volumeInfo, int i, int i2, int i3) {
        if (!java.util.Objects.equals(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, volumeInfo.getFsUuid())) {
            return;
        }
        android.content.Intent putExtra = new android.content.Intent("android.intent.action.DEVICE_STORAGE_LOW").addFlags(85983232).putExtra(EXTRA_SEQUENCE, i3);
        android.content.Intent putExtra2 = new android.content.Intent("android.intent.action.DEVICE_STORAGE_OK").addFlags(85983232).putExtra(EXTRA_SEQUENCE, i3);
        if (com.android.server.storage.DeviceStorageMonitorService.State.isEntering(1, i, i2)) {
            getContext().sendStickyBroadcastAsUser(putExtra, android.os.UserHandle.ALL);
        } else if (com.android.server.storage.DeviceStorageMonitorService.State.isLeaving(1, i, i2)) {
            getContext().removeStickyBroadcastAsUser(putExtra, android.os.UserHandle.ALL);
            getContext().sendBroadcastAsUser(putExtra2, android.os.UserHandle.ALL);
        }
        android.content.Intent putExtra3 = new android.content.Intent("android.intent.action.DEVICE_STORAGE_FULL").addFlags(67108864).putExtra(EXTRA_SEQUENCE, i3);
        android.content.Intent putExtra4 = new android.content.Intent("android.intent.action.DEVICE_STORAGE_NOT_FULL").addFlags(67108864).putExtra(EXTRA_SEQUENCE, i3);
        if (com.android.server.storage.DeviceStorageMonitorService.State.isEntering(2, i, i2)) {
            getContext().sendStickyBroadcastAsUser(putExtra3, android.os.UserHandle.ALL);
        } else if (com.android.server.storage.DeviceStorageMonitorService.State.isLeaving(2, i, i2)) {
            getContext().removeStickyBroadcastAsUser(putExtra3, android.os.UserHandle.ALL);
            getContext().sendBroadcastAsUser(putExtra4, android.os.UserHandle.ALL);
        }
    }

    private static class CacheFileDeletedObserver extends android.os.FileObserver {
        public CacheFileDeletedObserver() {
            super(android.os.Environment.getDownloadCacheDirectory().getAbsolutePath(), 512);
        }

        @Override // android.os.FileObserver
        public void onEvent(int i, java.lang.String str) {
            com.android.server.EventLogTags.writeCacheFileDeleted(str);
        }
    }
}
