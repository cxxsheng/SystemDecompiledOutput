package com.android.server.pm;

/* loaded from: classes2.dex */
public final class ProcessLoggingHandler extends android.os.Handler {
    private static final int CHECKSUM_TYPE = 8;
    private static final java.lang.String TAG = "ProcessLoggingHandler";
    private final java.util.concurrent.Executor mExecutor;
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.ProcessLoggingHandler.LoggingInfo> mLoggingInfo;

    static class LoggingInfo {
        public java.lang.String apkHash = null;
        public java.util.List<android.os.Bundle> pendingLogEntries = new java.util.ArrayList();

        LoggingInfo() {
        }
    }

    ProcessLoggingHandler() {
        super(com.android.internal.os.BackgroundThread.getHandler().getLooper());
        this.mExecutor = new android.os.HandlerExecutor(this);
        this.mLoggingInfo = new android.util.ArrayMap<>();
    }

    void logAppProcessStart(android.content.Context context, android.content.pm.PackageManagerInternal packageManagerInternal, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4, int i2) {
        boolean z;
        final com.android.server.pm.ProcessLoggingHandler.LoggingInfo loggingInfo;
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putLong("startTimestamp", java.lang.System.currentTimeMillis());
        bundle.putString("processName", str3);
        bundle.putInt(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, i);
        bundle.putString("seinfo", str4);
        bundle.putInt("pid", i2);
        if (str == null) {
            enqueueSecurityLogEvent(bundle, "No APK");
            return;
        }
        synchronized (this.mLoggingInfo) {
            try {
                com.android.server.pm.ProcessLoggingHandler.LoggingInfo loggingInfo2 = this.mLoggingInfo.get(str);
                z = loggingInfo2 == null;
                if (!z) {
                    loggingInfo = loggingInfo2;
                } else {
                    com.android.server.pm.ProcessLoggingHandler.LoggingInfo loggingInfo3 = new com.android.server.pm.ProcessLoggingHandler.LoggingInfo();
                    this.mLoggingInfo.put(str, loggingInfo3);
                    loggingInfo = loggingInfo3;
                }
            } finally {
            }
        }
        synchronized (loggingInfo) {
            try {
                if (!android.text.TextUtils.isEmpty(loggingInfo.apkHash)) {
                    enqueueSecurityLogEvent(bundle, loggingInfo.apkHash);
                    return;
                }
                loggingInfo.pendingLogEntries.add(bundle);
                if (!z) {
                    return;
                }
                try {
                    packageManagerInternal.requestChecksums(str2, false, 0, 8, null, new android.content.pm.IOnChecksumsReadyListener.Stub() { // from class: com.android.server.pm.ProcessLoggingHandler.1
                        public void onChecksumsReady(java.util.List<android.content.pm.ApkChecksum> list) throws android.os.RemoteException {
                            com.android.server.pm.ProcessLoggingHandler.this.processChecksums(loggingInfo, list);
                        }
                    }, context.getUserId(), this.mExecutor, this);
                } catch (java.lang.Throwable th) {
                    android.util.Slog.e(TAG, "requestChecksums() failed", th);
                    enqueueProcessChecksum(loggingInfo, null);
                }
            } finally {
            }
        }
    }

    void processChecksums(com.android.server.pm.ProcessLoggingHandler.LoggingInfo loggingInfo, java.util.List<android.content.pm.ApkChecksum> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            android.content.pm.ApkChecksum apkChecksum = list.get(i);
            if (apkChecksum.getType() == 8) {
                processChecksum(loggingInfo, apkChecksum.getValue());
                return;
            }
        }
        android.util.Slog.e(TAG, "requestChecksums() failed to return SHA256, see logs for details.");
        processChecksum(loggingInfo, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enqueueProcessChecksum$0(com.android.server.pm.ProcessLoggingHandler.LoggingInfo loggingInfo) {
        processChecksum(loggingInfo, null);
    }

    void enqueueProcessChecksum(final com.android.server.pm.ProcessLoggingHandler.LoggingInfo loggingInfo, byte[] bArr) {
        post(new java.lang.Runnable() { // from class: com.android.server.pm.ProcessLoggingHandler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ProcessLoggingHandler.this.lambda$enqueueProcessChecksum$0(loggingInfo);
            }
        });
    }

    void processChecksum(com.android.server.pm.ProcessLoggingHandler.LoggingInfo loggingInfo, byte[] bArr) {
        java.lang.String str;
        if (bArr != null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (byte b : bArr) {
                sb.append(java.lang.String.format("%02x", java.lang.Byte.valueOf(b)));
            }
            str = sb.toString();
        } else {
            str = "Failed to count APK hash";
        }
        synchronized (loggingInfo) {
            try {
                if (android.text.TextUtils.isEmpty(loggingInfo.apkHash)) {
                    loggingInfo.apkHash = str;
                    java.util.List<android.os.Bundle> list = loggingInfo.pendingLogEntries;
                    loggingInfo.pendingLogEntries = null;
                    if (list != null) {
                        java.util.Iterator<android.os.Bundle> it = list.iterator();
                        while (it.hasNext()) {
                            lambda$enqueueSecurityLogEvent$1(it.next(), str);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void invalidateBaseApkHash(java.lang.String str) {
        synchronized (this.mLoggingInfo) {
            this.mLoggingInfo.remove(str);
        }
    }

    void enqueueSecurityLogEvent(final android.os.Bundle bundle, final java.lang.String str) {
        post(new java.lang.Runnable() { // from class: com.android.server.pm.ProcessLoggingHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ProcessLoggingHandler.this.lambda$enqueueSecurityLogEvent$1(bundle, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: logSecurityLogEvent, reason: merged with bridge method [inline-methods] */
    public void lambda$enqueueSecurityLogEvent$1(android.os.Bundle bundle, java.lang.String str) {
        long j = bundle.getLong("startTimestamp");
        java.lang.String string = bundle.getString("processName");
        int i = bundle.getInt(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID);
        android.app.admin.SecurityLog.writeEvent(210005, new java.lang.Object[]{string, java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(bundle.getInt("pid")), bundle.getString("seinfo"), str});
    }
}
