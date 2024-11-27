package com.android.server.backup.restore;

/* loaded from: classes.dex */
class FullRestoreEngineThread implements java.lang.Runnable {
    com.android.server.backup.restore.FullRestoreEngine mEngine;
    java.io.InputStream mEngineStream;
    private final boolean mMustKillAgent;

    FullRestoreEngineThread(com.android.server.backup.restore.FullRestoreEngine fullRestoreEngine, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        this.mEngine = fullRestoreEngine;
        fullRestoreEngine.setRunning(true);
        this.mEngineStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
        this.mMustKillAgent = false;
    }

    FullRestoreEngineThread(com.android.server.backup.restore.FullRestoreEngine fullRestoreEngine, java.io.InputStream inputStream) {
        this.mEngine = fullRestoreEngine;
        fullRestoreEngine.setRunning(true);
        this.mEngineStream = inputStream;
        this.mMustKillAgent = true;
    }

    public boolean isRunning() {
        return this.mEngine.isRunning();
    }

    public int waitForResult() {
        return this.mEngine.waitForResult();
    }

    @Override // java.lang.Runnable
    public void run() {
        while (this.mEngine.isRunning()) {
            try {
                this.mEngine.restoreOneFile(this.mEngineStream, this.mMustKillAgent, this.mEngine.mBuffer, this.mEngine.mOnlyPackage, this.mEngine.mAllowApks, this.mEngine.mEphemeralOpToken, this.mEngine.mMonitor);
            } finally {
                libcore.io.IoUtils.closeQuietly(this.mEngineStream);
            }
        }
    }

    public void handleTimeout() {
        libcore.io.IoUtils.closeQuietly(this.mEngineStream);
        this.mEngine.handleTimeout();
    }
}
