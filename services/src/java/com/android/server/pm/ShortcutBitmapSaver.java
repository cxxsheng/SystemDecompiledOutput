package com.android.server.pm;

/* loaded from: classes2.dex */
public class ShortcutBitmapSaver {
    private static final boolean ADD_DELAY_BEFORE_SAVE_FOR_TEST = false;
    private static final boolean DEBUG = false;
    private static final long SAVE_DELAY_MS_FOR_TEST = 1000;
    private static final java.lang.String TAG = "ShortcutService";
    private final long SAVE_WAIT_TIMEOUT_MS = 5000;
    private final java.util.concurrent.Executor mExecutor = new java.util.concurrent.ThreadPoolExecutor(0, 1, 60, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.LinkedBlockingQueue());

    @com.android.internal.annotations.GuardedBy({"mPendingItems"})
    private final java.util.Deque<com.android.server.pm.ShortcutBitmapSaver.PendingItem> mPendingItems = new java.util.concurrent.LinkedBlockingDeque();
    private final java.lang.Runnable mRunnable = new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutBitmapSaver$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.pm.ShortcutBitmapSaver.this.lambda$new$1();
        }
    };
    private final com.android.server.pm.ShortcutService mService;

    private static class PendingItem {
        public final byte[] bytes;
        private final long mInstantiatedUptimeMillis;
        public final android.content.pm.ShortcutInfo shortcut;

        private PendingItem(android.content.pm.ShortcutInfo shortcutInfo, byte[] bArr) {
            this.shortcut = shortcutInfo;
            this.bytes = bArr;
            this.mInstantiatedUptimeMillis = android.os.SystemClock.uptimeMillis();
        }

        public java.lang.String toString() {
            return "PendingItem{size=" + this.bytes.length + " age=" + (android.os.SystemClock.uptimeMillis() - this.mInstantiatedUptimeMillis) + "ms shortcut=" + this.shortcut.toInsecureString() + "}";
        }
    }

    public ShortcutBitmapSaver(com.android.server.pm.ShortcutService shortcutService) {
        this.mService = shortcutService;
    }

    public boolean waitForAllSavesLocked() {
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutBitmapSaver$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                countDownLatch.countDown();
            }
        });
        try {
            if (countDownLatch.await(5000L, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                return true;
            }
            this.mService.wtf("Timed out waiting on saving bitmaps.");
            return false;
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.w(TAG, "interrupted");
            return false;
        }
    }

    @android.annotation.Nullable
    public java.lang.String getBitmapPathMayWaitLocked(android.content.pm.ShortcutInfo shortcutInfo) {
        if (waitForAllSavesLocked() && shortcutInfo.hasIconFile()) {
            return shortcutInfo.getBitmapPath();
        }
        return null;
    }

    public void removeIcon(android.content.pm.ShortcutInfo shortcutInfo) {
        shortcutInfo.setIconResourceId(0);
        shortcutInfo.setIconResName(null);
        shortcutInfo.setBitmapPath(null);
        shortcutInfo.setIconUri(null);
        shortcutInfo.clearFlags(35340);
    }

    public void saveBitmapLocked(android.content.pm.ShortcutInfo shortcutInfo, int i, android.graphics.Bitmap.CompressFormat compressFormat, int i2) {
        android.graphics.drawable.Icon icon = shortcutInfo.getIcon();
        java.util.Objects.requireNonNull(icon);
        android.graphics.Bitmap bitmap = icon.getBitmap();
        if (bitmap == null) {
            android.util.Log.e(TAG, "Missing icon: " + shortcutInfo);
            return;
        }
        android.os.StrictMode.ThreadPolicy threadPolicy = android.os.StrictMode.getThreadPolicy();
        try {
            try {
                android.os.StrictMode.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder(threadPolicy).permitCustomSlowCalls().build());
                android.graphics.Bitmap shrinkBitmap = com.android.server.pm.ShortcutService.shrinkBitmap(bitmap, i);
                try {
                    java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(65536);
                    try {
                        if (!shrinkBitmap.compress(compressFormat, i2, byteArrayOutputStream)) {
                            android.util.Slog.wtf(TAG, "Unable to compress bitmap");
                        }
                        byteArrayOutputStream.flush();
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                        byteArrayOutputStream.close();
                        android.os.StrictMode.setThreadPolicy(threadPolicy);
                        shortcutInfo.addFlags(2056);
                        if (icon.getType() == 5) {
                            shortcutInfo.addFlags(512);
                        }
                        com.android.server.pm.ShortcutBitmapSaver.PendingItem pendingItem = new com.android.server.pm.ShortcutBitmapSaver.PendingItem(shortcutInfo, byteArray);
                        synchronized (this.mPendingItems) {
                            this.mPendingItems.add(pendingItem);
                        }
                        this.mExecutor.execute(this.mRunnable);
                    } finally {
                    }
                } finally {
                    if (shrinkBitmap != bitmap) {
                        shrinkBitmap.recycle();
                    }
                }
            } catch (java.io.IOException | java.lang.OutOfMemoryError | java.lang.RuntimeException e) {
                android.util.Slog.wtf(TAG, "Unable to write bitmap to file", e);
                android.os.StrictMode.setThreadPolicy(threadPolicy);
            }
        } catch (java.lang.Throwable th) {
            android.os.StrictMode.setThreadPolicy(threadPolicy);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        while (processPendingItems()) {
        }
    }

    private boolean processPendingItems() {
        android.content.pm.ShortcutInfo shortcutInfo;
        java.lang.Throwable th;
        java.io.File file = null;
        try {
            synchronized (this.mPendingItems) {
                try {
                    if (this.mPendingItems.size() == 0) {
                        return false;
                    }
                    com.android.server.pm.ShortcutBitmapSaver.PendingItem pop = this.mPendingItems.pop();
                    shortcutInfo = pop.shortcut;
                    try {
                        if (!shortcutInfo.isIconPendingSave()) {
                            if (shortcutInfo.getBitmapPath() == null) {
                                removeIcon(shortcutInfo);
                            }
                            shortcutInfo.clearFlags(2048);
                            return true;
                        }
                        try {
                            com.android.server.pm.ShortcutService.FileOutputStreamWithPath openIconFileForWrite = this.mService.openIconFileForWrite(shortcutInfo.getUserId(), shortcutInfo);
                            java.io.File file2 = openIconFileForWrite.getFile();
                            try {
                                openIconFileForWrite.write(pop.bytes);
                                libcore.io.IoUtils.closeQuietly(openIconFileForWrite);
                                shortcutInfo.setBitmapPath(file2.getAbsolutePath());
                                if (shortcutInfo.getBitmapPath() == null) {
                                    removeIcon(shortcutInfo);
                                }
                                shortcutInfo.clearFlags(2048);
                                return true;
                            } catch (java.lang.Throwable th2) {
                                libcore.io.IoUtils.closeQuietly(openIconFileForWrite);
                                throw th2;
                            }
                        } catch (java.io.IOException | java.lang.RuntimeException e) {
                            android.util.Slog.e(TAG, "Unable to write bitmap to file", e);
                            if (0 != 0 && file.exists()) {
                                file.delete();
                            }
                            if (shortcutInfo.getBitmapPath() == null) {
                                removeIcon(shortcutInfo);
                            }
                            shortcutInfo.clearFlags(2048);
                            return true;
                        }
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        if (shortcutInfo != null) {
                            if (shortcutInfo.getBitmapPath() == null) {
                                removeIcon(shortcutInfo);
                            }
                            shortcutInfo.clearFlags(2048);
                        }
                        throw th;
                    }
                } catch (java.lang.Throwable th4) {
                    throw th4;
                }
            }
        } catch (java.lang.Throwable th5) {
            shortcutInfo = null;
            th = th5;
        }
    }

    public void dumpLocked(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mPendingItems) {
            try {
                int size = this.mPendingItems.size();
                printWriter.print(str);
                printWriter.println("Pending saves: Num=" + size + " Executor=" + this.mExecutor);
                for (com.android.server.pm.ShortcutBitmapSaver.PendingItem pendingItem : this.mPendingItems) {
                    printWriter.print(str);
                    printWriter.print("  ");
                    printWriter.println(pendingItem);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
