package com.android.server.am;

/* loaded from: classes.dex */
final class NativeCrashListener extends java.lang.Thread {
    static final boolean DEBUG = false;
    static final java.lang.String DEBUGGERD_SOCKET_PATH = "/data/system/ndebugsocket";
    static final boolean MORE_DEBUG = false;
    static final long SOCKET_TIMEOUT_MILLIS = 10000;
    static final java.lang.String TAG = "NativeCrashListener";
    final com.android.server.am.ActivityManagerService mAm;

    class NativeCrashReporter extends java.lang.Thread {
        com.android.server.am.ProcessRecord mApp;
        java.lang.String mCrashReport;
        boolean mGwpAsanRecoverableCrash;
        int mSignal;

        NativeCrashReporter(com.android.server.am.ProcessRecord processRecord, int i, boolean z, java.lang.String str) {
            super("NativeCrashReport");
            this.mApp = processRecord;
            this.mSignal = i;
            this.mGwpAsanRecoverableCrash = z;
            this.mCrashReport = str;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                android.app.ApplicationErrorReport.CrashInfo crashInfo = new android.app.ApplicationErrorReport.CrashInfo();
                crashInfo.exceptionClassName = "Native crash";
                crashInfo.exceptionMessage = android.system.Os.strsignal(this.mSignal);
                crashInfo.throwFileName = "unknown";
                crashInfo.throwClassName = "unknown";
                crashInfo.throwMethodName = "unknown";
                crashInfo.stackTrace = this.mCrashReport;
                com.android.server.am.NativeCrashListener.this.mAm.handleApplicationCrashInner(this.mGwpAsanRecoverableCrash ? "native_recoverable_crash" : "native_crash", this.mApp, this.mApp.processName, crashInfo);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.am.NativeCrashListener.TAG, "Unable to report native crash", e);
            }
        }
    }

    NativeCrashListener(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        byte[] bArr = new byte[1];
        java.io.File file = new java.io.File(DEBUGGERD_SOCKET_PATH);
        if (file.exists()) {
            file.delete();
        }
        try {
            java.io.FileDescriptor socket = android.system.Os.socket(android.system.OsConstants.AF_UNIX, android.system.OsConstants.SOCK_STREAM, 0);
            android.system.Os.bind(socket, android.system.UnixSocketAddress.createFileSystem(DEBUGGERD_SOCKET_PATH));
            android.system.Os.listen(socket, 1);
            android.system.Os.chmod(DEBUGGERD_SOCKET_PATH, 511);
            while (true) {
                java.io.FileDescriptor fileDescriptor = null;
                try {
                    try {
                        fileDescriptor = android.system.Os.accept(socket, null);
                        if (fileDescriptor != null) {
                            consumeNativeCrashData(fileDescriptor);
                        }
                        if (fileDescriptor != null) {
                            try {
                                android.system.Os.write(fileDescriptor, bArr, 0, 1);
                            } catch (java.lang.Exception e) {
                            }
                            try {
                                android.system.Os.close(fileDescriptor);
                            } catch (android.system.ErrnoException e2) {
                            }
                        }
                    } finally {
                    }
                } catch (java.lang.Exception e3) {
                    android.util.Slog.w(TAG, "Error handling connection", e3);
                    if (fileDescriptor != null) {
                        try {
                            android.system.Os.write(fileDescriptor, bArr, 0, 1);
                        } catch (java.lang.Exception e4) {
                        }
                        android.system.Os.close(fileDescriptor);
                    }
                }
            }
        } catch (java.lang.Exception e5) {
            android.util.Slog.e(TAG, "Unable to init native debug socket!", e5);
        }
    }

    static int unpackInt(byte[] bArr, int i) {
        return (bArr[i + 3] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) | ((bArr[i] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) << 24) | ((bArr[i + 1] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) << 16) | ((bArr[i + 2] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) << 8);
    }

    static int readExactly(java.io.FileDescriptor fileDescriptor, byte[] bArr, int i, int i2) throws android.system.ErrnoException, java.io.InterruptedIOException {
        int i3 = 0;
        while (i2 > 0) {
            int read = android.system.Os.read(fileDescriptor, bArr, i + i3, i2);
            if (read <= 0) {
                return -1;
            }
            i2 -= read;
            i3 += read;
        }
        return i3;
    }

    void consumeNativeCrashData(java.io.FileDescriptor fileDescriptor) {
        com.android.server.am.ProcessRecord processRecord;
        byte[] bArr = new byte[4096];
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(4096);
        try {
            android.system.StructTimeval fromMillis = android.system.StructTimeval.fromMillis(10000L);
            android.system.Os.setsockoptTimeval(fileDescriptor, android.system.OsConstants.SOL_SOCKET, android.system.OsConstants.SO_RCVTIMEO, fromMillis);
            android.system.Os.setsockoptTimeval(fileDescriptor, android.system.OsConstants.SOL_SOCKET, android.system.OsConstants.SO_SNDTIMEO, fromMillis);
            if (readExactly(fileDescriptor, bArr, 0, 9) != 9) {
                android.util.Slog.e(TAG, "Unable to read from debuggerd");
                return;
            }
            int unpackInt = unpackInt(bArr, 0);
            int unpackInt2 = unpackInt(bArr, 4);
            boolean z = bArr[8] != 0;
            if (unpackInt < 0) {
                android.util.Slog.e(TAG, "Bogus pid!");
                return;
            }
            synchronized (this.mAm.mPidsSelfLocked) {
                processRecord = this.mAm.mPidsSelfLocked.get(unpackInt);
            }
            if (processRecord == null) {
                android.util.Slog.w(TAG, "Couldn't find ProcessRecord for pid " + unpackInt);
                return;
            }
            if (processRecord.isPersistent()) {
                return;
            }
            while (true) {
                int read = android.system.Os.read(fileDescriptor, bArr, 0, 4096);
                if (read > 0) {
                    int i = read - 1;
                    if (bArr[i] == 0) {
                        byteArrayOutputStream.write(bArr, 0, i);
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                if (read <= 0) {
                    break;
                }
            }
            if (!z) {
                com.android.server.am.ActivityManagerService activityManagerService = this.mAm;
                com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mAm.mProcLock;
                        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                        synchronized (activityManagerGlobalLock) {
                            try {
                                processRecord.mErrorState.setCrashing(true);
                                processRecord.mErrorState.setForceCrashReport(true);
                            } catch (java.lang.Throwable th) {
                                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                                throw th;
                            }
                        }
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    } catch (java.lang.Throwable th2) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th2;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            }
            new com.android.server.am.NativeCrashListener.NativeCrashReporter(processRecord, unpackInt2, z, new java.lang.String(byteArrayOutputStream.toByteArray(), "UTF-8")).start();
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Exception dealing with report", e);
        }
    }
}
