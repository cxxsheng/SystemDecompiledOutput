package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
public class FileHashCache {
    private static final boolean DEBUG = false;
    private static final boolean VERIFY = false;
    private android.os.Handler mHandler;
    private static final java.lang.String TAG = com.android.server.net.watchlist.FileHashCache.class.getSimpleName();
    private static boolean sLoggedWtf = false;

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String sPersistFileName = "/data/system/file_hash_cache";
    static long sSaveDeferredDelayMillis = java.util.concurrent.TimeUnit.SECONDS.toMillis(5);
    private final java.util.Map<java.io.File, com.android.server.net.watchlist.FileHashCache.Entry> mEntries = new java.util.HashMap();
    private final java.lang.Runnable mLoadTask = new java.lang.Runnable() { // from class: com.android.server.net.watchlist.FileHashCache$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.net.watchlist.FileHashCache.this.lambda$new$0();
        }
    };
    private final java.lang.Runnable mSaveTask = new java.lang.Runnable() { // from class: com.android.server.net.watchlist.FileHashCache$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.net.watchlist.FileHashCache.this.lambda$new$1();
        }
    };

    private static class Entry {
        public final long mLastModified;
        public final byte[] mSha256Hash;

        Entry(long j, @android.annotation.NonNull byte[] bArr) {
            this.mLastModified = j;
            this.mSha256Hash = bArr;
        }
    }

    public FileHashCache(@android.annotation.NonNull android.os.Handler handler) {
        this.mHandler = handler;
        this.mHandler.post(this.mLoadTask);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    byte[] getSha256HashFromCache(@android.annotation.NonNull java.io.File file) {
        if (!this.mHandler.getLooper().isCurrentThread()) {
            android.util.Slog.wtf(TAG, "Request from invalid thread", new java.lang.Exception());
            return null;
        }
        com.android.server.net.watchlist.FileHashCache.Entry entry = this.mEntries.get(file);
        if (entry == null) {
            return null;
        }
        try {
            if (entry.mLastModified == android.system.Os.stat(file.getAbsolutePath()).st_ctime) {
                return entry.mSha256Hash;
            }
        } catch (android.system.ErrnoException e) {
        }
        this.mEntries.remove(file);
        return null;
    }

    @android.annotation.NonNull
    public byte[] getSha256Hash(@android.annotation.NonNull java.io.File file) throws java.security.NoSuchAlgorithmException, java.io.IOException {
        byte[] sha256HashFromCache = getSha256HashFromCache(file);
        if (sha256HashFromCache != null) {
            return sha256HashFromCache;
        }
        try {
            byte[] sha256Hash = com.android.server.net.watchlist.DigestUtils.getSha256Hash(file);
            this.mEntries.put(file, new com.android.server.net.watchlist.FileHashCache.Entry(android.system.Os.stat(file.getAbsolutePath()).st_ctime, sha256Hash));
            scheduleSave();
            return sha256Hash;
        } catch (android.system.ErrnoException e) {
            throw new java.io.IOException(e);
        }
    }

    private static void closeQuietly(@android.annotation.Nullable java.io.Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (java.io.IOException e) {
            }
        }
    }

    private static void logWtfOnce(@android.annotation.NonNull java.lang.String str, java.lang.Exception exc) {
        if (!sLoggedWtf) {
            android.util.Slog.wtf(TAG, str, exc);
            sLoggedWtf = true;
        } else {
            android.util.Slog.w(TAG, str, exc);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: load, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        this.mEntries.clear();
        android.os.SystemClock.currentTimeMicro();
        java.io.File file = new java.io.File(sPersistFileName);
        if (!file.exists()) {
            return;
        }
        java.io.BufferedReader bufferedReader = null;
        try {
            try {
                java.io.BufferedReader bufferedReader2 = new java.io.BufferedReader(new java.io.FileReader(file));
                try {
                    bufferedReader2.lines().forEach(new java.util.function.Consumer() { // from class: com.android.server.net.watchlist.FileHashCache$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.net.watchlist.FileHashCache.this.lambda$load$2((java.lang.String) obj);
                        }
                    });
                    closeQuietly(bufferedReader2);
                } catch (java.io.IOException | java.io.UncheckedIOException e) {
                    e = e;
                    bufferedReader = bufferedReader2;
                    android.util.Slog.e(TAG, "Failed to read storage file", e);
                    closeQuietly(bufferedReader);
                } catch (java.lang.Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    closeQuietly(bufferedReader);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.io.IOException | java.io.UncheckedIOException e2) {
            e = e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$load$2(java.lang.String str) {
        try {
            java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, ",");
            this.mEntries.put(new java.io.File(stringTokenizer.nextToken()), new com.android.server.net.watchlist.FileHashCache.Entry(java.lang.Long.parseLong(stringTokenizer.nextToken()), com.android.internal.util.HexDump.hexStringToByteArray(stringTokenizer.nextToken())));
        } catch (java.lang.RuntimeException e) {
            logWtfOnce("Invalid entry for " + str, e);
        }
    }

    private void scheduleSave() {
        this.mHandler.removeCallbacks(this.mSaveTask);
        this.mHandler.postDelayed(this.mSaveTask, sSaveDeferredDelayMillis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.util.Iterator] */
    /* renamed from: save, reason: merged with bridge method [inline-methods] */
    public void lambda$new$1() {
        android.os.SystemClock.currentTimeMicro();
        java.io.BufferedWriter bufferedWriter = null;
        java.io.BufferedWriter bufferedWriter2 = null;
        try {
            try {
                java.io.BufferedWriter bufferedWriter3 = new java.io.BufferedWriter(new java.io.FileWriter(sPersistFileName));
                try {
                    ?? it = this.mEntries.entrySet().iterator();
                    while (it.hasNext()) {
                        java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                        bufferedWriter3.write(entry.getKey() + "," + ((com.android.server.net.watchlist.FileHashCache.Entry) entry.getValue()).mLastModified + "," + com.android.internal.util.HexDump.toHexString(((com.android.server.net.watchlist.FileHashCache.Entry) entry.getValue()).mSha256Hash) + "\n");
                    }
                    closeQuietly(bufferedWriter3);
                    bufferedWriter = it;
                } catch (java.io.IOException e) {
                    e = e;
                    bufferedWriter2 = bufferedWriter3;
                    android.util.Slog.e(TAG, "Failed to save.", e);
                    closeQuietly(bufferedWriter2);
                    bufferedWriter = bufferedWriter2;
                } catch (java.lang.Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter3;
                    closeQuietly(bufferedWriter);
                    throw th;
                }
            } catch (java.io.IOException e2) {
                e = e2;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }
}
