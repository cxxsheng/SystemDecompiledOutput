package com.android.server;

/* loaded from: classes.dex */
public final class DropBoxManagerService extends com.android.server.SystemService {
    private static final long COMPRESS_THRESHOLD_BYTES = 16384;
    private static final int DEFAULT_AGE_SECONDS = 259200;
    private static final int DEFAULT_MAX_FILES = 1000;
    private static final int DEFAULT_MAX_FILES_LOWRAM = 300;
    private static final int DEFAULT_QUOTA_KB = 10240;
    private static final int DEFAULT_QUOTA_PERCENT = 10;
    private static final int DEFAULT_RESERVE_PERCENT = 0;
    private static final java.util.List<java.lang.String> DISABLED_BY_DEFAULT_TAGS = java.util.List.of("data_app_wtf", "system_app_wtf", "system_server_wtf");
    private static final long ENFORCE_READ_DROPBOX_DATA = 296060945;
    private static final boolean PROFILE_DUMP = false;
    private static final int PROTO_MAX_DATA_BYTES = 262144;
    private static final int QUOTA_RESCAN_MILLIS = 5000;
    private static final java.lang.String TAG = "DropBoxManagerService";
    private com.android.server.DropBoxManagerService.FileList mAllFiles;
    private int mBlockSize;
    private volatile boolean mBooted;
    private int mCachedQuotaBlocks;
    private long mCachedQuotaUptimeMillis;
    private final android.content.ContentResolver mContentResolver;
    private final java.io.File mDropBoxDir;
    private android.util.ArrayMap<java.lang.String, com.android.server.DropBoxManagerService.FileList> mFilesByTag;
    private final com.android.server.DropBoxManagerService.DropBoxManagerBroadcastHandler mHandler;
    private long mLowPriorityRateLimitPeriod;
    private android.util.ArraySet<java.lang.String> mLowPriorityTags;
    private int mMaxFiles;
    private final android.content.BroadcastReceiver mReceiver;
    private android.os.StatFs mStatFs;
    private final com.android.internal.os.IDropBoxManagerService.Stub mStub;

    private class ShellCmd extends android.os.ShellCommand {
        private ShellCmd() {
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
                    case -1412652367:
                        if (str.equals("restore-defaults")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -529247831:
                        if (str.equals("add-low-priority")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -444925274:
                        if (str.equals("remove-low-priority")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1936917209:
                        if (str.equals("set-rate-limit")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
            } catch (java.lang.Exception e) {
                outPrintWriter.println(e);
            }
            switch (c) {
                case 0:
                    com.android.server.DropBoxManagerService.this.setLowPriorityRateLimit(java.lang.Long.parseLong(getNextArgRequired()));
                    return 0;
                case 1:
                    com.android.server.DropBoxManagerService.this.addLowPriorityTag(getNextArgRequired());
                    return 0;
                case 2:
                    com.android.server.DropBoxManagerService.this.removeLowPriorityTag(getNextArgRequired());
                    return 0;
                case 3:
                    com.android.server.DropBoxManagerService.this.restoreDefaults();
                    return 0;
                default:
                    return handleDefaultCommands(str);
            }
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Dropbox manager service commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  set-rate-limit PERIOD");
            outPrintWriter.println("    Sets low priority broadcast rate limit period to PERIOD ms");
            outPrintWriter.println("  add-low-priority TAG");
            outPrintWriter.println("    Add TAG to dropbox low priority list");
            outPrintWriter.println("  remove-low-priority TAG");
            outPrintWriter.println("    Remove TAG from dropbox low priority list");
            outPrintWriter.println("  restore-defaults");
            outPrintWriter.println("    restore dropbox settings to defaults");
        }
    }

    private class DropBoxManagerBroadcastHandler extends android.os.Handler {
        static final int MSG_SEND_BROADCAST = 1;
        static final int MSG_SEND_DEFERRED_BROADCAST = 2;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.ArrayMap<java.lang.String, android.content.Intent> mDeferredMap;
        private final java.lang.Object mLock;

        DropBoxManagerBroadcastHandler(android.os.Looper looper) {
            super(looper);
            this.mLock = new java.lang.Object();
            this.mDeferredMap = new android.util.ArrayMap<>();
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.content.Intent remove;
            switch (message.what) {
                case 1:
                    prepareAndSendBroadcast((android.content.Intent) message.obj, null);
                    return;
                case 2:
                    synchronized (this.mLock) {
                        remove = this.mDeferredMap.remove((java.lang.String) message.obj);
                    }
                    if (remove != null) {
                        prepareAndSendBroadcast(remove, createBroadcastOptions(remove));
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        private void prepareAndSendBroadcast(android.content.Intent intent, android.os.Bundle bundle) {
            if (!com.android.server.DropBoxManagerService.this.mBooted) {
                intent.addFlags(1073741824);
            }
            com.android.server.feature.flags.Flags.enableReadDropboxPermission();
            com.android.server.DropBoxManagerService.this.getContext().sendBroadcastAsUser(intent, android.os.UserHandle.ALL, "android.permission.READ_LOGS", bundle);
        }

        private android.content.Intent createIntent(java.lang.String str, long j) {
            android.content.Intent intent = new android.content.Intent("android.intent.action.DROPBOX_ENTRY_ADDED");
            intent.putExtra("tag", str);
            intent.putExtra("time", j);
            intent.putExtra("android.os.extra.DROPPED_COUNT", 0);
            return intent;
        }

        private android.os.Bundle createBroadcastOptions(android.content.Intent intent) {
            android.os.BundleMerger bundleMerger = new android.os.BundleMerger();
            bundleMerger.setDefaultMergeStrategy(1);
            bundleMerger.setMergeStrategy("time", 4);
            bundleMerger.setMergeStrategy("android.os.extra.DROPPED_COUNT", 25);
            return android.app.BroadcastOptions.makeBasic().setDeliveryGroupPolicy(2).setDeliveryGroupMatchingKey("android.intent.action.DROPBOX_ENTRY_ADDED", intent.getStringExtra("tag")).setDeliveryGroupExtrasMerger(bundleMerger).setDeferralPolicy(2).toBundle();
        }

        public void sendBroadcast(java.lang.String str, long j) {
            sendMessage(obtainMessage(1, createIntent(str, j)));
        }

        public void maybeDeferBroadcast(java.lang.String str, long j) {
            synchronized (this.mLock) {
                try {
                    android.content.Intent intent = this.mDeferredMap.get(str);
                    if (intent == null) {
                        this.mDeferredMap.put(str, createIntent(str, j));
                        sendMessageDelayed(obtainMessage(2, str), com.android.server.DropBoxManagerService.this.mLowPriorityRateLimitPeriod);
                    } else {
                        intent.putExtra("time", j);
                        intent.putExtra("android.os.extra.DROPPED_COUNT", intent.getIntExtra("android.os.extra.DROPPED_COUNT", 0) + 1);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public DropBoxManagerService(android.content.Context context) {
        this(context, new java.io.File("/data/system/dropbox"), com.android.server.FgThread.get().getLooper());
    }

    @com.android.internal.annotations.VisibleForTesting
    public DropBoxManagerService(android.content.Context context, java.io.File file, android.os.Looper looper) {
        super(context);
        this.mAllFiles = null;
        this.mFilesByTag = null;
        this.mLowPriorityRateLimitPeriod = 0L;
        this.mLowPriorityTags = null;
        this.mStatFs = null;
        this.mBlockSize = 0;
        this.mCachedQuotaBlocks = 0;
        this.mCachedQuotaUptimeMillis = 0L;
        this.mBooted = false;
        this.mMaxFiles = -1;
        this.mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.DropBoxManagerService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.DropBoxManagerService.this.mCachedQuotaUptimeMillis = 0L;
                new java.lang.Thread() { // from class: com.android.server.DropBoxManagerService.1.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            com.android.server.DropBoxManagerService.this.init();
                            com.android.server.DropBoxManagerService.this.trimToFit();
                        } catch (java.io.IOException e) {
                            android.util.Slog.e(com.android.server.DropBoxManagerService.TAG, "Can't init", e);
                        }
                    }
                }.start();
            }
        };
        this.mStub = new com.android.internal.os.IDropBoxManagerService.Stub() { // from class: com.android.server.DropBoxManagerService.2
            public void addData(java.lang.String str, byte[] bArr, int i) {
                com.android.server.DropBoxManagerService.this.addData(str, bArr, i);
            }

            public void addFile(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) {
                com.android.server.DropBoxManagerService.this.addFile(str, parcelFileDescriptor, i);
            }

            public boolean isTagEnabled(java.lang.String str) {
                return com.android.server.DropBoxManagerService.this.isTagEnabled(str);
            }

            public android.os.DropBoxManager.Entry getNextEntry(java.lang.String str, long j, java.lang.String str2) {
                return getNextEntryWithAttribution(str, j, str2, null);
            }

            public android.os.DropBoxManager.Entry getNextEntryWithAttribution(java.lang.String str, long j, java.lang.String str2, java.lang.String str3) {
                return com.android.server.DropBoxManagerService.this.getNextEntry(str, j, str2, str3);
            }

            public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
                com.android.server.DropBoxManagerService.this.dump(fileDescriptor, printWriter, strArr);
            }

            /* JADX WARN: Multi-variable type inference failed */
            public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
                new com.android.server.DropBoxManagerService.ShellCmd().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }
        };
        this.mDropBoxDir = file;
        this.mContentResolver = getContext().getContentResolver();
        this.mHandler = new com.android.server.DropBoxManagerService.DropBoxManagerBroadcastHandler(looper);
        com.android.server.LocalServices.addService(com.android.server.DropBoxManagerInternal.class, new com.android.server.DropBoxManagerService.DropBoxManagerInternalImpl());
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("dropbox", this.mStub);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        switch (i) {
            case 500:
                android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                intentFilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
                getContext().registerReceiver(this.mReceiver, intentFilter);
                this.mContentResolver.registerContentObserver(android.provider.Settings.Global.CONTENT_URI, true, new android.database.ContentObserver(new android.os.Handler()) { // from class: com.android.server.DropBoxManagerService.3
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        com.android.server.DropBoxManagerService.this.mReceiver.onReceive(com.android.server.DropBoxManagerService.this.getContext(), null);
                    }
                });
                getLowPriorityResourceConfigs();
                break;
            case 1000:
                this.mBooted = true;
                break;
        }
    }

    public com.android.internal.os.IDropBoxManagerService getServiceStub() {
        return this.mStub;
    }

    public void addData(java.lang.String str, byte[] bArr, int i) {
        addEntry(str, new java.io.ByteArrayInputStream(bArr), bArr.length, i);
    }

    public void addFile(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) {
        try {
            android.system.StructStat fstat = android.system.Os.fstat(parcelFileDescriptor.getFileDescriptor());
            if (!android.system.OsConstants.S_ISREG(fstat.st_mode)) {
                throw new java.lang.IllegalArgumentException(str + " entry must be real file");
            }
            addEntry(str, new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor), fstat.st_size, i);
        } catch (android.system.ErrnoException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    public void addEntry(java.lang.String str, java.io.InputStream inputStream, long j, int i) {
        boolean z;
        if ((i & 4) == 0 && j > COMPRESS_THRESHOLD_BYTES) {
            i |= 4;
            z = true;
        } else {
            z = false;
        }
        addEntry(str, new com.android.server.DropBoxManagerService.SimpleEntrySource(inputStream, j, z), i);
    }

    public static class SimpleEntrySource implements com.android.server.DropBoxManagerInternal.EntrySource {
        private final boolean forceCompress;
        private final java.io.InputStream in;
        private final long length;

        public SimpleEntrySource(java.io.InputStream inputStream, long j, boolean z) {
            this.in = inputStream;
            this.length = j;
            this.forceCompress = z;
        }

        @Override // com.android.server.DropBoxManagerInternal.EntrySource
        public long length() {
            return this.length;
        }

        @Override // com.android.server.DropBoxManagerInternal.EntrySource
        public void writeTo(java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
            if (this.forceCompress) {
                java.util.zip.GZIPOutputStream gZIPOutputStream = new java.util.zip.GZIPOutputStream(new java.io.FileOutputStream(fileDescriptor));
                android.os.FileUtils.copy(this.in, gZIPOutputStream);
                gZIPOutputStream.close();
                return;
            }
            android.os.FileUtils.copy(this.in, new java.io.FileOutputStream(fileDescriptor));
        }

        @Override // com.android.server.DropBoxManagerInternal.EntrySource, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws java.io.IOException {
            android.os.FileUtils.closeQuietly(this.in);
        }
    }

    public void addEntry(java.lang.String str, com.android.server.DropBoxManagerInternal.EntrySource entrySource, int i) {
        java.io.File file;
        java.io.File file2 = null;
        try {
            try {
                android.util.Slog.i(TAG, "add tag=" + str + " isTagEnabled=" + isTagEnabled(str) + " flags=0x" + java.lang.Integer.toHexString(i));
                if ((i & 1) != 0) {
                    throw new java.lang.IllegalArgumentException();
                }
                init();
                if (!isTagEnabled(str)) {
                    libcore.io.IoUtils.closeQuietly(entrySource);
                    return;
                }
                long length = entrySource.length();
                long trimToFit = trimToFit();
                if (length > trimToFit) {
                    android.util.Slog.w(TAG, "Dropping: " + str + " (" + length + " > " + trimToFit + " bytes)");
                    logDropboxDropped(6, str, 0L);
                    file = null;
                } else {
                    file = new java.io.File(this.mDropBoxDir, "drop" + java.lang.Thread.currentThread().getId() + ".tmp");
                    try {
                        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
                        try {
                            entrySource.writeTo(fileOutputStream.getFD());
                            fileOutputStream.close();
                        } catch (java.lang.Throwable th) {
                            try {
                                fileOutputStream.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (java.io.IOException e) {
                        e = e;
                        file2 = file;
                        android.util.Slog.e(TAG, "Can't write: " + str, e);
                        logDropboxDropped(5, str, 0L);
                        libcore.io.IoUtils.closeQuietly(entrySource);
                        if (file2 != null) {
                            file2.delete();
                            return;
                        }
                        return;
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        file2 = file;
                        libcore.io.IoUtils.closeQuietly(entrySource);
                        if (file2 != null) {
                            file2.delete();
                        }
                        throw th;
                    }
                }
                long createEntry = createEntry(file, str, i);
                if (this.mLowPriorityTags == null || !this.mLowPriorityTags.contains(str)) {
                    this.mHandler.sendBroadcast(str, createEntry);
                } else {
                    this.mHandler.maybeDeferBroadcast(str, createEntry);
                }
                libcore.io.IoUtils.closeQuietly(entrySource);
            } catch (java.io.IOException e2) {
                e = e2;
            }
        } catch (java.lang.Throwable th4) {
            th = th4;
        }
    }

    private void logDropboxDropped(int i, java.lang.String str, long j) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DROPBOX_ENTRY_DROPPED, i, str, j);
    }

    public boolean isTagEnabled(java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (DISABLED_BY_DEFAULT_TAGS.contains(str)) {
                return com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED.equals(android.provider.Settings.Global.getString(this.mContentResolver, "dropbox:" + str));
            }
            android.content.ContentResolver contentResolver = this.mContentResolver;
            return !com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED.equals(android.provider.Settings.Global.getString(contentResolver, "dropbox:" + str));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean checkPermission(int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        if (getContext().checkCallingPermission("android.permission.PEEK_DROPBOX_DATA") == 0) {
            return true;
        }
        com.android.server.feature.flags.Flags.enableReadDropboxPermission();
        getContext().enforceCallingOrSelfPermission("android.permission.READ_LOGS", TAG);
        switch (((android.app.AppOpsManager) getContext().getSystemService(android.app.AppOpsManager.class)).noteOp(43, i, str, str2, (java.lang.String) null)) {
            case 0:
                break;
            case 3:
                getContext().enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", TAG);
                break;
        }
        return true;
    }

    public synchronized android.os.DropBoxManager.Entry getNextEntry(java.lang.String str, long j, java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
        try {
            if (!checkPermission(android.os.Binder.getCallingUid(), str2, str3)) {
                return null;
            }
            try {
                init();
                com.android.server.DropBoxManagerService.FileList fileList = str == null ? this.mAllFiles : this.mFilesByTag.get(str);
                if (fileList == null) {
                    return null;
                }
                for (com.android.server.DropBoxManagerService.EntryFile entryFile : fileList.contents.tailSet(new com.android.server.DropBoxManagerService.EntryFile(j + 1))) {
                    if (entryFile.tag != null) {
                        if ((entryFile.flags & 1) != 0) {
                            return new android.os.DropBoxManager.Entry(entryFile.tag, entryFile.timestampMillis);
                        }
                        java.io.File file = entryFile.getFile(this.mDropBoxDir);
                        try {
                            return new android.os.DropBoxManager.Entry(entryFile.tag, entryFile.timestampMillis, file, entryFile.flags);
                        } catch (java.io.IOException e) {
                            android.util.Slog.wtf(TAG, "Can't read: " + file, e);
                        }
                    }
                }
                return null;
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "Can't init", e2);
                return null;
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void setLowPriorityRateLimit(long j) {
        this.mLowPriorityRateLimitPeriod = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void addLowPriorityTag(java.lang.String str) {
        this.mLowPriorityTags.add(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void removeLowPriorityTag(java.lang.String str) {
        this.mLowPriorityTags.remove(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void restoreDefaults() {
        getLowPriorityResourceConfigs();
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x02df A[Catch: all -> 0x0095, TryCatch #15 {all -> 0x0095, blocks: (B:4:0x0007, B:10:0x0015, B:11:0x0019, B:14:0x002c, B:16:0x002f, B:18:0x0039, B:20:0x0045, B:22:0x004f, B:24:0x005a, B:29:0x0066, B:31:0x0070, B:33:0x007b, B:35:0x0085, B:37:0x0098, B:41:0x009e, B:54:0x00ce, B:57:0x00d3, B:59:0x011b, B:60:0x0124, B:62:0x012a, B:64:0x0139, B:65:0x013e, B:66:0x014d, B:68:0x0153, B:71:0x0160, B:73:0x0164, B:74:0x0169, B:77:0x0181, B:79:0x018c, B:83:0x0196, B:85:0x019b, B:86:0x01a5, B:88:0x01b0, B:89:0x01b5, B:92:0x01c2, B:95:0x01da, B:97:0x01f3, B:112:0x027c, B:117:0x0281, B:115:0x02df, B:152:0x02c2, B:154:0x02c7, B:159:0x02cf, B:162:0x02d4, B:166:0x02d9, B:204:0x01e2, B:205:0x01e7, B:207:0x017f, B:213:0x02ec, B:215:0x02f3, B:217:0x0302, B:221:0x02f8, B:222:0x02fd, B:227:0x030d), top: B:3:0x0007, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02c2 A[Catch: all -> 0x0095, TRY_ENTER, TRY_LEAVE, TryCatch #15 {all -> 0x0095, blocks: (B:4:0x0007, B:10:0x0015, B:11:0x0019, B:14:0x002c, B:16:0x002f, B:18:0x0039, B:20:0x0045, B:22:0x004f, B:24:0x005a, B:29:0x0066, B:31:0x0070, B:33:0x007b, B:35:0x0085, B:37:0x0098, B:41:0x009e, B:54:0x00ce, B:57:0x00d3, B:59:0x011b, B:60:0x0124, B:62:0x012a, B:64:0x0139, B:65:0x013e, B:66:0x014d, B:68:0x0153, B:71:0x0160, B:73:0x0164, B:74:0x0169, B:77:0x0181, B:79:0x018c, B:83:0x0196, B:85:0x019b, B:86:0x01a5, B:88:0x01b0, B:89:0x01b5, B:92:0x01c2, B:95:0x01da, B:97:0x01f3, B:112:0x027c, B:117:0x0281, B:115:0x02df, B:152:0x02c2, B:154:0x02c7, B:159:0x02cf, B:162:0x02d4, B:166:0x02d9, B:204:0x01e2, B:205:0x01e7, B:207:0x017f, B:213:0x02ec, B:215:0x02f3, B:217:0x0302, B:221:0x02f8, B:222:0x02fd, B:227:0x030d), top: B:3:0x0007, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02c7 A[Catch: all -> 0x0095, IOException -> 0x0285, TRY_ENTER, TRY_LEAVE, TryCatch #14 {IOException -> 0x0285, blocks: (B:117:0x0281, B:154:0x02c7), top: B:116:0x0281 }] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x02cf A[Catch: all -> 0x0095, TRY_ENTER, TRY_LEAVE, TryCatch #15 {all -> 0x0095, blocks: (B:4:0x0007, B:10:0x0015, B:11:0x0019, B:14:0x002c, B:16:0x002f, B:18:0x0039, B:20:0x0045, B:22:0x004f, B:24:0x005a, B:29:0x0066, B:31:0x0070, B:33:0x007b, B:35:0x0085, B:37:0x0098, B:41:0x009e, B:54:0x00ce, B:57:0x00d3, B:59:0x011b, B:60:0x0124, B:62:0x012a, B:64:0x0139, B:65:0x013e, B:66:0x014d, B:68:0x0153, B:71:0x0160, B:73:0x0164, B:74:0x0169, B:77:0x0181, B:79:0x018c, B:83:0x0196, B:85:0x019b, B:86:0x01a5, B:88:0x01b0, B:89:0x01b5, B:92:0x01c2, B:95:0x01da, B:97:0x01f3, B:112:0x027c, B:117:0x0281, B:115:0x02df, B:152:0x02c2, B:154:0x02c7, B:159:0x02cf, B:162:0x02d4, B:166:0x02d9, B:204:0x01e2, B:205:0x01e7, B:207:0x017f, B:213:0x02ec, B:215:0x02f3, B:217:0x0302, B:221:0x02f8, B:222:0x02fd, B:227:0x030d), top: B:3:0x0007, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x02d4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:167:? A[Catch: all -> 0x0095, SYNTHETIC, TRY_ENTER, TryCatch #15 {all -> 0x0095, blocks: (B:4:0x0007, B:10:0x0015, B:11:0x0019, B:14:0x002c, B:16:0x002f, B:18:0x0039, B:20:0x0045, B:22:0x004f, B:24:0x005a, B:29:0x0066, B:31:0x0070, B:33:0x007b, B:35:0x0085, B:37:0x0098, B:41:0x009e, B:54:0x00ce, B:57:0x00d3, B:59:0x011b, B:60:0x0124, B:62:0x012a, B:64:0x0139, B:65:0x013e, B:66:0x014d, B:68:0x0153, B:71:0x0160, B:73:0x0164, B:74:0x0169, B:77:0x0181, B:79:0x018c, B:83:0x0196, B:85:0x019b, B:86:0x01a5, B:88:0x01b0, B:89:0x01b5, B:92:0x01c2, B:95:0x01da, B:97:0x01f3, B:112:0x027c, B:117:0x0281, B:115:0x02df, B:152:0x02c2, B:154:0x02c7, B:159:0x02cf, B:162:0x02d4, B:166:0x02d9, B:204:0x01e2, B:205:0x01e7, B:207:0x017f, B:213:0x02ec, B:215:0x02f3, B:217:0x0302, B:221:0x02f8, B:222:0x02fd, B:227:0x030d), top: B:3:0x0007, inners: #8 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.util.Iterator<com.android.server.DropBoxManagerService.EntryFile> it;
        java.lang.Throwable th;
        java.io.InputStreamReader inputStreamReader;
        java.io.File file;
        try {
            if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(getContext(), TAG, printWriter)) {
                try {
                    init();
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
                    boolean z = false;
                    boolean z2 = false;
                    boolean z3 = false;
                    for (int i = 0; strArr != null && i < strArr.length; i++) {
                        if (strArr[i].equals("-p") || strArr[i].equals("--print")) {
                            z2 = true;
                        } else if (strArr[i].equals("-f") || strArr[i].equals("--file")) {
                            z3 = true;
                        } else if (strArr[i].equals("--proto")) {
                            z = true;
                        } else {
                            if (strArr[i].equals("-h") || strArr[i].equals("--help")) {
                                printWriter.println("Dropbox (dropbox) dump options:");
                                printWriter.println("  [-h|--help] [-p|--print] [-f|--file] [timestamp]");
                                printWriter.println("    -h|--help: print this help");
                                printWriter.println("    -p|--print: print full contents of each entry");
                                printWriter.println("    -f|--file: print path of each entry's file");
                                printWriter.println("    --proto: dump data to proto");
                                printWriter.println("  [timestamp] optionally filters to only those entries.");
                                return;
                            }
                            if (strArr[i].startsWith("-")) {
                                sb.append("Unknown argument: ");
                                sb.append(strArr[i]);
                                sb.append("\n");
                            } else {
                                arrayList.add(strArr[i]);
                            }
                        }
                    }
                    if (z) {
                        dumpProtoLocked(fileDescriptor, arrayList);
                        return;
                    }
                    sb.append("Drop box contents: ");
                    sb.append(this.mAllFiles.contents.size());
                    sb.append(" entries\n");
                    sb.append("Max entries: ");
                    sb.append(this.mMaxFiles);
                    sb.append("\n");
                    sb.append("Low priority rate limit period: ");
                    sb.append(this.mLowPriorityRateLimitPeriod);
                    sb.append(" ms\n");
                    sb.append("Low priority tags: ");
                    sb.append(this.mLowPriorityTags);
                    sb.append("\n");
                    if (!arrayList.isEmpty()) {
                        sb.append("Searching for:");
                        java.util.Iterator<java.lang.String> it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            java.lang.String next = it2.next();
                            sb.append(" ");
                            sb.append(next);
                        }
                        sb.append("\n");
                    }
                    sb.append("\n");
                    java.util.Iterator<com.android.server.DropBoxManagerService.EntryFile> it3 = this.mAllFiles.contents.iterator();
                    int i2 = 0;
                    while (it3.hasNext()) {
                        com.android.server.DropBoxManagerService.EntryFile next2 = it3.next();
                        if (matchEntry(next2, arrayList)) {
                            int i3 = i2 + 1;
                            if (z2) {
                                sb.append("========================================\n");
                            }
                            sb.append(android.text.format.TimeMigrationUtils.formatMillisWithFixedFormat(next2.timestampMillis));
                            sb.append(" ");
                            sb.append(next2.tag == null ? "(no tag)" : next2.tag);
                            java.io.File file2 = next2.getFile(this.mDropBoxDir);
                            if (file2 == null) {
                                sb.append(" (no file)\n");
                                it = it3;
                            } else if ((next2.flags & 1) != 0) {
                                sb.append(" (contents lost)\n");
                                it = it3;
                            } else {
                                sb.append(" (");
                                if ((next2.flags & 4) != 0) {
                                    sb.append("compressed ");
                                }
                                sb.append((next2.flags & 2) != 0 ? "text" : "data");
                                sb.append(", ");
                                sb.append(file2.length());
                                sb.append(" bytes)\n");
                                if (z3 || (z2 && (next2.flags & 2) == 0)) {
                                    if (!z2) {
                                        sb.append("    ");
                                    }
                                    sb.append(file2.getPath());
                                    sb.append("\n");
                                }
                                if ((next2.flags & 2) == 0 || !z2) {
                                    it = it3;
                                } else {
                                    android.os.DropBoxManager.Entry entry = null;
                                    java.io.InputStreamReader inputStreamReader2 = null;
                                    entry = null;
                                    try {
                                        try {
                                            java.lang.String str = next2.tag;
                                            it = it3;
                                            try {
                                                long j = next2.timestampMillis;
                                                int i4 = next2.flags;
                                                file = file2;
                                                try {
                                                    android.os.DropBoxManager.Entry entry2 = new android.os.DropBoxManager.Entry(str, j, file, i4);
                                                    if (z2) {
                                                        try {
                                                            inputStreamReader = new java.io.InputStreamReader(entry2.getInputStream());
                                                            try {
                                                                try {
                                                                    char[] cArr = new char[4096];
                                                                    boolean z4 = false;
                                                                    while (true) {
                                                                        int read = inputStreamReader.read(cArr);
                                                                        if (read <= 0) {
                                                                            break;
                                                                        }
                                                                        try {
                                                                            sb.append(cArr, 0, read);
                                                                            z4 = cArr[read + (-1)] == '\n';
                                                                            if (sb.length() > 65536) {
                                                                                printWriter.write(sb.toString());
                                                                                try {
                                                                                    sb.setLength(0);
                                                                                } catch (java.io.IOException e) {
                                                                                    e = e;
                                                                                    entry = entry2;
                                                                                    try {
                                                                                        sb.append("*** ");
                                                                                        sb.append(e.toString());
                                                                                        sb.append("\n");
                                                                                        android.util.Slog.e(TAG, "Can't read: " + file, e);
                                                                                        if (entry != null) {
                                                                                        }
                                                                                        if (inputStreamReader != null) {
                                                                                        }
                                                                                        if (z2) {
                                                                                        }
                                                                                        it3 = it;
                                                                                        i2 = i3;
                                                                                    } catch (java.lang.Throwable th2) {
                                                                                        th = th2;
                                                                                        if (entry != null) {
                                                                                            entry.close();
                                                                                        }
                                                                                        if (inputStreamReader != null) {
                                                                                            throw th;
                                                                                        }
                                                                                        try {
                                                                                            inputStreamReader.close();
                                                                                            throw th;
                                                                                        } catch (java.io.IOException e2) {
                                                                                            throw th;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (java.io.IOException e3) {
                                                                            e = e3;
                                                                        }
                                                                    }
                                                                    if (!z4) {
                                                                        try {
                                                                            sb.append("\n");
                                                                        } catch (java.io.IOException e4) {
                                                                            e = e4;
                                                                            entry = entry2;
                                                                            sb.append("*** ");
                                                                            sb.append(e.toString());
                                                                            sb.append("\n");
                                                                            android.util.Slog.e(TAG, "Can't read: " + file, e);
                                                                            if (entry != null) {
                                                                                entry.close();
                                                                            }
                                                                            if (inputStreamReader != null) {
                                                                                inputStreamReader.close();
                                                                            }
                                                                            if (z2) {
                                                                            }
                                                                            it3 = it;
                                                                            i2 = i3;
                                                                        }
                                                                    }
                                                                    inputStreamReader2 = inputStreamReader;
                                                                } catch (java.lang.Throwable th3) {
                                                                    th = th3;
                                                                    entry = entry2;
                                                                    if (entry != null) {
                                                                    }
                                                                    if (inputStreamReader != null) {
                                                                    }
                                                                }
                                                            } catch (java.io.IOException e5) {
                                                                e = e5;
                                                            }
                                                        } catch (java.io.IOException e6) {
                                                            e = e6;
                                                            inputStreamReader = null;
                                                        } catch (java.lang.Throwable th4) {
                                                            th = th4;
                                                            inputStreamReader = null;
                                                        }
                                                    }
                                                    entry2.close();
                                                    if (inputStreamReader2 != null) {
                                                        try {
                                                            inputStreamReader2.close();
                                                        } catch (java.io.IOException e7) {
                                                        }
                                                    }
                                                } catch (java.io.IOException e8) {
                                                    e = e8;
                                                    inputStreamReader = null;
                                                    sb.append("*** ");
                                                    sb.append(e.toString());
                                                    sb.append("\n");
                                                    android.util.Slog.e(TAG, "Can't read: " + file, e);
                                                    if (entry != null) {
                                                    }
                                                    if (inputStreamReader != null) {
                                                    }
                                                    if (z2) {
                                                    }
                                                    it3 = it;
                                                    i2 = i3;
                                                }
                                            } catch (java.io.IOException e9) {
                                                e = e9;
                                                file = file2;
                                                inputStreamReader = null;
                                                sb.append("*** ");
                                                sb.append(e.toString());
                                                sb.append("\n");
                                                android.util.Slog.e(TAG, "Can't read: " + file, e);
                                                if (entry != null) {
                                                }
                                                if (inputStreamReader != null) {
                                                }
                                                if (z2) {
                                                }
                                                it3 = it;
                                                i2 = i3;
                                            }
                                        } catch (java.lang.Throwable th5) {
                                            th = th5;
                                            inputStreamReader = null;
                                        }
                                    } catch (java.io.IOException e10) {
                                        e = e10;
                                        it = it3;
                                    }
                                }
                                if (z2) {
                                    sb.append("\n");
                                }
                            }
                            it3 = it;
                            i2 = i3;
                        }
                    }
                    if (i2 == 0) {
                        sb.append("(No entries found.)\n");
                    }
                    if (strArr == null || strArr.length == 0) {
                        if (!z2) {
                            sb.append("\n");
                        }
                        sb.append("Usage: dumpsys dropbox [--print|--file] [YYYY-mm-dd] [HH:MM:SS] [tag]\n");
                    }
                    printWriter.write(sb.toString());
                } catch (java.io.IOException e11) {
                    printWriter.println("Can't initialize: " + e11);
                    android.util.Slog.e(TAG, "Can't init", e11);
                }
            }
        } catch (java.lang.Throwable th6) {
            throw th6;
        }
    }

    private boolean matchEntry(com.android.server.DropBoxManagerService.EntryFile entryFile, java.util.ArrayList<java.lang.String> arrayList) {
        java.lang.String formatMillisWithFixedFormat = android.text.format.TimeMigrationUtils.formatMillisWithFixedFormat(entryFile.timestampMillis);
        int size = arrayList.size();
        boolean z = true;
        for (int i = 0; i < size && z; i++) {
            java.lang.String str = arrayList.get(i);
            z = formatMillisWithFixedFormat.contains(str) || str.equals(entryFile.tag);
        }
        return z;
    }

    private void dumpProtoLocked(java.io.FileDescriptor fileDescriptor, java.util.ArrayList<java.lang.String> arrayList) {
        java.io.File file;
        android.os.DropBoxManager.Entry entry;
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        java.util.Iterator<com.android.server.DropBoxManagerService.EntryFile> it = this.mAllFiles.contents.iterator();
        while (it.hasNext()) {
            com.android.server.DropBoxManagerService.EntryFile next = it.next();
            if (matchEntry(next, arrayList) && (file = next.getFile(this.mDropBoxDir)) != null && (next.flags & 1) == 0) {
                long start = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1112396529665L, next.timestampMillis);
                try {
                    entry = new android.os.DropBoxManager.Entry(next.tag, next.timestampMillis, file, next.flags);
                } catch (java.io.IOException e) {
                    android.util.Slog.e(TAG, "Can't read: " + file, e);
                }
                try {
                    java.io.InputStream inputStream = entry.getInputStream();
                    if (inputStream != null) {
                        try {
                            byte[] bArr = new byte[262144];
                            int i = 0;
                            int i2 = 0;
                            while (i >= 0) {
                                i2 += i;
                                if (i2 >= 262144) {
                                    break;
                                } else {
                                    i = inputStream.read(bArr, i2, 262144 - i2);
                                }
                            }
                            protoOutputStream.write(1151051235330L, java.util.Arrays.copyOf(bArr, i2));
                        } catch (java.lang.Throwable th) {
                            try {
                                inputStream.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entry.close();
                    protoOutputStream.end(start);
                } catch (java.lang.Throwable th3) {
                    try {
                        entry.close();
                    } catch (java.lang.Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                    throw th3;
                }
            }
        }
        protoOutputStream.flush();
    }

    private static final class FileList implements java.lang.Comparable<com.android.server.DropBoxManagerService.FileList> {
        public int blocks;
        public final java.util.TreeSet<com.android.server.DropBoxManagerService.EntryFile> contents;

        private FileList() {
            this.blocks = 0;
            this.contents = new java.util.TreeSet<>();
        }

        @Override // java.lang.Comparable
        public final int compareTo(com.android.server.DropBoxManagerService.FileList fileList) {
            if (this.blocks != fileList.blocks) {
                return fileList.blocks - this.blocks;
            }
            if (this == fileList) {
                return 0;
            }
            if (hashCode() < fileList.hashCode()) {
                return -1;
            }
            return hashCode() > fileList.hashCode() ? 1 : 0;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class EntryFile implements java.lang.Comparable<com.android.server.DropBoxManagerService.EntryFile> {
        public final int blocks;
        public final int flags;
        public final java.lang.String tag;
        public final long timestampMillis;

        @Override // java.lang.Comparable
        public final int compareTo(com.android.server.DropBoxManagerService.EntryFile entryFile) {
            int compare = java.lang.Long.compare(this.timestampMillis, entryFile.timestampMillis);
            if (compare != 0) {
                return compare;
            }
            int compare2 = com.android.internal.util.ObjectUtils.compare(this.tag, entryFile.tag);
            if (compare2 != 0) {
                return compare2;
            }
            int compare3 = java.lang.Integer.compare(this.flags, entryFile.flags);
            return compare3 != 0 ? compare3 : java.lang.Integer.compare(hashCode(), entryFile.hashCode());
        }

        public EntryFile(java.io.File file, java.io.File file2, java.lang.String str, long j, int i, int i2) throws java.io.IOException {
            if ((i & 1) != 0) {
                throw new java.lang.IllegalArgumentException();
            }
            this.tag = android.text.TextUtils.safeIntern(str);
            this.timestampMillis = j;
            this.flags = i;
            java.io.File file3 = getFile(file2);
            if (!file.renameTo(file3)) {
                throw new java.io.IOException("Can't rename " + file + " to " + file3);
            }
            long j2 = i2;
            this.blocks = (int) (((file3.length() + j2) - 1) / j2);
        }

        public EntryFile(java.io.File file, java.lang.String str, long j) throws java.io.IOException {
            this.tag = android.text.TextUtils.safeIntern(str);
            this.timestampMillis = j;
            this.flags = 1;
            this.blocks = 0;
            new java.io.FileOutputStream(getFile(file)).close();
        }

        public EntryFile(java.io.File file, int i) {
            java.lang.String decode;
            int i2;
            boolean z;
            long j;
            java.lang.String name = file.getName();
            int lastIndexOf = name.lastIndexOf(64);
            if (lastIndexOf < 0) {
                z = true;
                i2 = 0;
                decode = null;
                j = 0;
            } else {
                decode = android.net.Uri.decode(name.substring(0, lastIndexOf));
                if (!name.endsWith(com.android.server.pm.PackageManagerService.COMPRESSED_EXTENSION)) {
                    i2 = 0;
                } else {
                    name = name.substring(0, name.length() - 3);
                    i2 = 4;
                }
                if (name.endsWith(".lost")) {
                    i2 |= 1;
                    name = name.substring(lastIndexOf + 1, name.length() - 5);
                    z = false;
                } else if (name.endsWith(".txt")) {
                    i2 |= 2;
                    name = name.substring(lastIndexOf + 1, name.length() - 4);
                    z = false;
                } else if (name.endsWith(".dat")) {
                    name = name.substring(lastIndexOf + 1, name.length() - 4);
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    j = 0;
                } else {
                    try {
                        j = java.lang.Long.parseLong(name);
                    } catch (java.lang.NumberFormatException e) {
                        z = true;
                        j = 0;
                    }
                }
            }
            if (z) {
                android.util.Slog.wtf(com.android.server.DropBoxManagerService.TAG, "Invalid filename: " + file);
                file.delete();
                this.tag = null;
                this.flags = 1;
                this.timestampMillis = 0L;
                this.blocks = 0;
                return;
            }
            long length = file.length();
            long j2 = i;
            this.blocks = (int) (((length + j2) - 1) / j2);
            this.tag = android.text.TextUtils.safeIntern(decode);
            this.flags = i2;
            this.timestampMillis = j;
        }

        public EntryFile(long j) {
            this.tag = null;
            this.timestampMillis = j;
            this.flags = 1;
            this.blocks = 0;
        }

        public boolean hasFile() {
            return this.tag != null;
        }

        private java.lang.String getExtension() {
            if ((this.flags & 1) != 0) {
                return ".lost";
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append((this.flags & 2) != 0 ? ".txt" : ".dat");
            sb.append((this.flags & 4) != 0 ? com.android.server.pm.PackageManagerService.COMPRESSED_EXTENSION : "");
            return sb.toString();
        }

        public java.lang.String getFilename() {
            if (!hasFile()) {
                return null;
            }
            return android.net.Uri.encode(this.tag) + "@" + this.timestampMillis + getExtension();
        }

        public java.io.File getFile(java.io.File file) {
            if (hasFile()) {
                return new java.io.File(file, getFilename());
            }
            return null;
        }

        public void deleteFile(java.io.File file) {
            if (hasFile()) {
                getFile(file).delete();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void init() throws java.io.IOException {
        if (this.mStatFs == null) {
            if (!this.mDropBoxDir.isDirectory() && !this.mDropBoxDir.mkdirs()) {
                throw new java.io.IOException("Can't mkdir: " + this.mDropBoxDir);
            }
            try {
                this.mStatFs = new android.os.StatFs(this.mDropBoxDir.getPath());
                this.mBlockSize = this.mStatFs.getBlockSize();
            } catch (java.lang.IllegalArgumentException e) {
                throw new java.io.IOException("Can't statfs: " + this.mDropBoxDir);
            }
        }
        if (this.mAllFiles == null) {
            java.io.File[] listFiles = this.mDropBoxDir.listFiles();
            if (listFiles == null) {
                throw new java.io.IOException("Can't list files: " + this.mDropBoxDir);
            }
            this.mAllFiles = new com.android.server.DropBoxManagerService.FileList();
            this.mFilesByTag = new android.util.ArrayMap<>();
            for (java.io.File file : listFiles) {
                if (file.getName().endsWith(".tmp")) {
                    android.util.Slog.i(TAG, "Cleaning temp file: " + file);
                    file.delete();
                } else {
                    com.android.server.DropBoxManagerService.EntryFile entryFile = new com.android.server.DropBoxManagerService.EntryFile(file, this.mBlockSize);
                    if (entryFile.hasFile()) {
                        enrollEntry(entryFile);
                    }
                }
            }
        }
    }

    private synchronized void enrollEntry(com.android.server.DropBoxManagerService.EntryFile entryFile) {
        try {
            this.mAllFiles.contents.add(entryFile);
            this.mAllFiles.blocks += entryFile.blocks;
            if (entryFile.hasFile() && entryFile.blocks > 0) {
                com.android.server.DropBoxManagerService.FileList fileList = this.mFilesByTag.get(entryFile.tag);
                if (fileList == null) {
                    fileList = new com.android.server.DropBoxManagerService.FileList();
                    this.mFilesByTag.put(android.text.TextUtils.safeIntern(entryFile.tag), fileList);
                }
                fileList.contents.add(entryFile);
                fileList.blocks += entryFile.blocks;
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private synchronized long createEntry(java.io.File file, java.lang.String str, int i) throws java.io.IOException {
        long currentTimeMillis;
        com.android.server.DropBoxManagerService.EntryFile[] entryFileArr;
        try {
            currentTimeMillis = java.lang.System.currentTimeMillis();
            java.util.SortedSet<com.android.server.DropBoxManagerService.EntryFile> tailSet = this.mAllFiles.contents.tailSet(new com.android.server.DropBoxManagerService.EntryFile(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY + currentTimeMillis));
            if (tailSet.isEmpty()) {
                entryFileArr = null;
            } else {
                entryFileArr = (com.android.server.DropBoxManagerService.EntryFile[]) tailSet.toArray(new com.android.server.DropBoxManagerService.EntryFile[tailSet.size()]);
                tailSet.clear();
            }
            if (!this.mAllFiles.contents.isEmpty()) {
                currentTimeMillis = java.lang.Math.max(currentTimeMillis, this.mAllFiles.contents.last().timestampMillis + 1);
            }
            if (entryFileArr != null) {
                long j = currentTimeMillis;
                for (com.android.server.DropBoxManagerService.EntryFile entryFile : entryFileArr) {
                    this.mAllFiles.blocks -= entryFile.blocks;
                    com.android.server.DropBoxManagerService.FileList fileList = this.mFilesByTag.get(entryFile.tag);
                    if (fileList != null && fileList.contents.remove(entryFile)) {
                        fileList.blocks -= entryFile.blocks;
                    }
                    if ((entryFile.flags & 1) == 0) {
                        enrollEntry(new com.android.server.DropBoxManagerService.EntryFile(entryFile.getFile(this.mDropBoxDir), this.mDropBoxDir, entryFile.tag, j, entryFile.flags, this.mBlockSize));
                        j++;
                    } else {
                        enrollEntry(new com.android.server.DropBoxManagerService.EntryFile(this.mDropBoxDir, entryFile.tag, j));
                        j++;
                    }
                }
                currentTimeMillis = j;
            }
            if (file == null) {
                enrollEntry(new com.android.server.DropBoxManagerService.EntryFile(this.mDropBoxDir, str, currentTimeMillis));
            } else {
                enrollEntry(new com.android.server.DropBoxManagerService.EntryFile(file, this.mDropBoxDir, str, currentTimeMillis, i, this.mBlockSize));
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return currentTimeMillis;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized long trimToFit() throws java.io.IOException {
        try {
            int i = android.provider.Settings.Global.getInt(this.mContentResolver, "dropbox_age_seconds", DEFAULT_AGE_SECONDS);
            this.mMaxFiles = android.provider.Settings.Global.getInt(this.mContentResolver, "dropbox_max_files", android.app.ActivityManager.isLowRamDeviceStatic() ? 300 : 1000);
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            long j = currentTimeMillis - (i * 1000);
            while (!this.mAllFiles.contents.isEmpty()) {
                com.android.server.DropBoxManagerService.EntryFile first = this.mAllFiles.contents.first();
                if (first.timestampMillis > j && this.mAllFiles.contents.size() < this.mMaxFiles) {
                    break;
                }
                logDropboxDropped(4, first.tag, currentTimeMillis - first.timestampMillis);
                com.android.server.DropBoxManagerService.FileList fileList = this.mFilesByTag.get(first.tag);
                if (fileList != null && fileList.contents.remove(first)) {
                    fileList.blocks -= first.blocks;
                }
                if (this.mAllFiles.contents.remove(first)) {
                    this.mAllFiles.blocks -= first.blocks;
                }
                first.deleteFile(this.mDropBoxDir);
            }
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            int i2 = 0;
            if (uptimeMillis > this.mCachedQuotaUptimeMillis + 5000) {
                int i3 = android.provider.Settings.Global.getInt(this.mContentResolver, "dropbox_quota_percent", 10);
                int i4 = android.provider.Settings.Global.getInt(this.mContentResolver, "dropbox_reserve_percent", 0);
                int i5 = android.provider.Settings.Global.getInt(this.mContentResolver, "dropbox_quota_kb", DEFAULT_QUOTA_KB);
                try {
                    this.mStatFs.restat(this.mDropBoxDir.getPath());
                    this.mCachedQuotaBlocks = java.lang.Math.min((i5 * 1024) / this.mBlockSize, java.lang.Math.toIntExact(java.lang.Math.max(0L, java.lang.Math.min(((this.mStatFs.getAvailableBlocksLong() - ((this.mStatFs.getBlockCountLong() * i4) / 100)) * i3) / 100, 2147483647L))));
                    this.mCachedQuotaUptimeMillis = uptimeMillis;
                } catch (java.lang.IllegalArgumentException e) {
                    throw new java.io.IOException("Can't restat: " + this.mDropBoxDir);
                }
            }
            if (this.mAllFiles.blocks > this.mCachedQuotaBlocks) {
                int i6 = this.mAllFiles.blocks;
                java.util.TreeSet treeSet = new java.util.TreeSet(this.mFilesByTag.values());
                java.util.Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    com.android.server.DropBoxManagerService.FileList fileList2 = (com.android.server.DropBoxManagerService.FileList) it.next();
                    if (i2 > 0 && fileList2.blocks <= (this.mCachedQuotaBlocks - i6) / i2) {
                        break;
                    }
                    i6 -= fileList2.blocks;
                    i2++;
                }
                int i7 = (this.mCachedQuotaBlocks - i6) / i2;
                java.util.Iterator it2 = treeSet.iterator();
                while (it2.hasNext()) {
                    com.android.server.DropBoxManagerService.FileList fileList3 = (com.android.server.DropBoxManagerService.FileList) it2.next();
                    if (this.mAllFiles.blocks < this.mCachedQuotaBlocks) {
                        break;
                    }
                    while (fileList3.blocks > i7 && !fileList3.contents.isEmpty()) {
                        com.android.server.DropBoxManagerService.EntryFile first2 = fileList3.contents.first();
                        logDropboxDropped(3, first2.tag, currentTimeMillis - first2.timestampMillis);
                        if (fileList3.contents.remove(first2)) {
                            fileList3.blocks -= first2.blocks;
                        }
                        if (this.mAllFiles.contents.remove(first2)) {
                            this.mAllFiles.blocks -= first2.blocks;
                        }
                        try {
                            first2.deleteFile(this.mDropBoxDir);
                            enrollEntry(new com.android.server.DropBoxManagerService.EntryFile(this.mDropBoxDir, first2.tag, first2.timestampMillis));
                        } catch (java.io.IOException e2) {
                            android.util.Slog.e(TAG, "Can't write tombstone file", e2);
                        }
                    }
                }
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return this.mCachedQuotaBlocks * this.mBlockSize;
    }

    private void getLowPriorityResourceConfigs() {
        this.mLowPriorityRateLimitPeriod = android.content.res.Resources.getSystem().getInteger(android.R.integer.config_dreamOverlayReconnectTimeoutMs);
        java.lang.String[] stringArray = android.content.res.Resources.getSystem().getStringArray(android.R.array.config_doubleClickVibePattern);
        int length = stringArray.length;
        if (length == 0) {
            this.mLowPriorityTags = null;
            return;
        }
        this.mLowPriorityTags = new android.util.ArraySet<>(length);
        for (java.lang.String str : stringArray) {
            this.mLowPriorityTags.add(str);
        }
    }

    private final class DropBoxManagerInternalImpl extends com.android.server.DropBoxManagerInternal {
        private DropBoxManagerInternalImpl() {
        }

        @Override // com.android.server.DropBoxManagerInternal
        public void addEntry(java.lang.String str, com.android.server.DropBoxManagerInternal.EntrySource entrySource, int i) {
            com.android.server.DropBoxManagerService.this.addEntry(str, entrySource, i);
        }
    }
}
