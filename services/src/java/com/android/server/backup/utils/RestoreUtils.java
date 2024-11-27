package com.android.server.backup.utils;

/* loaded from: classes.dex */
public class RestoreUtils {
    public static boolean installApk(java.io.InputStream inputStream, android.content.Context context, com.android.server.backup.restore.RestoreDeleteObserver restoreDeleteObserver, java.util.HashMap<java.lang.String, android.content.pm.Signature[]> hashMap, java.util.HashMap<java.lang.String, com.android.server.backup.restore.RestorePolicy> hashMap2, com.android.server.backup.FileMetadata fileMetadata, java.lang.String str, com.android.server.backup.utils.BytesReadListener bytesReadListener, int i) {
        int i2;
        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Installing from backup: " + fileMetadata.packageName);
        try {
            new com.android.server.backup.utils.RestoreUtils.LocalIntentReceiver();
            android.content.pm.PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
            android.content.pm.PackageInstaller.SessionParams sessionParams = new android.content.pm.PackageInstaller.SessionParams(1);
            sessionParams.setInstallerPackageName(str);
            int createSession = packageInstaller.createSession(sessionParams);
            try {
                android.content.pm.PackageInstaller.Session openSession = packageInstaller.openSession(createSession);
                try {
                    java.io.OutputStream openWrite = openSession.openWrite(fileMetadata.packageName, 0L, fileMetadata.size);
                    try {
                        if (!com.android.server.backup.Flags.enableMaxSizeWritesToPipes()) {
                            i2 = 32768;
                        } else {
                            i2 = 65536;
                        }
                        byte[] bArr = new byte[i2];
                        long j = fileMetadata.size;
                        while (j > 0) {
                            long j2 = i2;
                            if (j2 >= j) {
                                j2 = j;
                            }
                            int read = inputStream.read(bArr, 0, (int) j2);
                            if (read >= 0) {
                                bytesReadListener.onBytesRead(read);
                            }
                            openWrite.write(bArr, 0, read);
                            j -= read;
                        }
                        if (openWrite != null) {
                            openWrite.close();
                        }
                        openSession.abandon();
                        openSession.close();
                        if (hashMap2.get(fileMetadata.packageName) != com.android.server.backup.restore.RestorePolicy.ACCEPT) {
                            return false;
                        }
                        return true;
                    } finally {
                    }
                } finally {
                }
            } catch (java.lang.Exception e) {
                packageInstaller.abandonSession(createSession);
                throw e;
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to transcribe restored apk for install");
            return false;
        }
    }

    private static class LocalIntentReceiver {
        private android.content.IIntentSender.Stub mLocalSender;
        private final java.lang.Object mLock;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.content.Intent mResult;

        private LocalIntentReceiver() {
            this.mLock = new java.lang.Object();
            this.mResult = null;
            this.mLocalSender = new android.content.IIntentSender.Stub() { // from class: com.android.server.backup.utils.RestoreUtils.LocalIntentReceiver.1
                public void send(int i, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle) {
                    synchronized (com.android.server.backup.utils.RestoreUtils.LocalIntentReceiver.this.mLock) {
                        com.android.server.backup.utils.RestoreUtils.LocalIntentReceiver.this.mResult = intent;
                        com.android.server.backup.utils.RestoreUtils.LocalIntentReceiver.this.mLock.notifyAll();
                    }
                }
            };
        }

        public android.content.IntentSender getIntentSender() {
            return new android.content.IntentSender(this.mLocalSender);
        }

        public android.content.Intent getResult() {
            android.content.Intent intent;
            synchronized (this.mLock) {
                while (this.mResult == null) {
                    try {
                        this.mLock.wait();
                    } catch (java.lang.InterruptedException e) {
                    }
                }
                intent = this.mResult;
            }
            return intent;
        }
    }
}
