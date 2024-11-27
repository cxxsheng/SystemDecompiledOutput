package com.android.server;

/* loaded from: classes.dex */
public class EntropyMixer extends android.os.Binder {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String DEVICE_SPECIFIC_INFO_HEADER = "Copyright (C) 2009 The Android Open Source Project\nAll Your Randomness Are Belong To Us\n";

    @com.android.internal.annotations.VisibleForTesting
    static final int SEED_FILE_SIZE = 512;
    private static final int SEED_UPDATE_PERIOD = 10800000;
    private static final java.lang.String TAG = "EntropyMixer";
    private static final int UPDATE_SEED_MSG = 1;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final android.os.Handler mHandler;
    private final java.io.File randomReadDevice;
    private final java.io.File randomWriteDevice;
    private final android.util.AtomicFile seedFile;
    private static final long START_TIME = java.lang.System.currentTimeMillis();
    private static final long START_NANOTIME = java.lang.System.nanoTime();

    public EntropyMixer(android.content.Context context) {
        this(context, new java.io.File(getSystemDir(), "entropy.dat"), new java.io.File("/dev/urandom"), new java.io.File("/dev/urandom"));
    }

    @com.android.internal.annotations.VisibleForTesting
    EntropyMixer(android.content.Context context, java.io.File file, java.io.File file2, java.io.File file3) {
        this.mHandler = new android.os.Handler(com.android.server.IoThread.getHandler().getLooper()) { // from class: com.android.server.EntropyMixer.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                if (message.what != 1) {
                    android.util.Slog.e(com.android.server.EntropyMixer.TAG, "Will not process invalid message");
                } else {
                    com.android.server.EntropyMixer.this.updateSeedFile();
                    com.android.server.EntropyMixer.this.scheduleSeedUpdater();
                }
            }
        };
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.EntropyMixer.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.EntropyMixer.this.updateSeedFile();
            }
        };
        this.seedFile = new android.util.AtomicFile((java.io.File) com.android.internal.util.Preconditions.checkNotNull(file));
        this.randomReadDevice = (java.io.File) com.android.internal.util.Preconditions.checkNotNull(file2);
        this.randomWriteDevice = (java.io.File) com.android.internal.util.Preconditions.checkNotNull(file3);
        loadInitialEntropy();
        updateSeedFile();
        scheduleSeedUpdater();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.REBOOT");
        context.registerReceiver(this.mBroadcastReceiver, intentFilter, null, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleSeedUpdater() {
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, 10800000L);
    }

    private void loadInitialEntropy() {
        byte[] readSeedFile = readSeedFile();
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(this.randomWriteDevice);
            try {
                if (readSeedFile.length != 0) {
                    fileOutputStream.write(readSeedFile);
                    android.util.Slog.i(TAG, "Loaded existing seed file");
                }
                fileOutputStream.write(getDeviceSpecificInformation());
                fileOutputStream.close();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error writing to " + this.randomWriteDevice, e);
        }
    }

    private byte[] readSeedFile() {
        try {
            return this.seedFile.readFully();
        } catch (java.io.FileNotFoundException e) {
            return new byte[0];
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Error reading " + this.seedFile.getBaseFile(), e2);
            return new byte[0];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSeedFile() {
        java.io.FileInputStream fileInputStream;
        byte[] readSeedFile = readSeedFile();
        byte[] bArr = new byte[512];
        try {
            fileInputStream = new java.io.FileInputStream(this.randomReadDevice);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error reading " + this.randomReadDevice + "; seed file won't be properly updated", e);
        }
        try {
            if (fileInputStream.read(bArr) != 512) {
                throw new java.io.IOException("unexpected EOF");
            }
            fileInputStream.close();
            try {
                java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
                messageDigest.update("Android EntropyMixer v1".getBytes());
                messageDigest.update(longToBytes(java.lang.System.currentTimeMillis()));
                messageDigest.update(longToBytes(java.lang.System.nanoTime()));
                messageDigest.update(longToBytes(readSeedFile.length));
                messageDigest.update(readSeedFile);
                messageDigest.update(longToBytes(512));
                messageDigest.update(bArr);
                byte[] digest = messageDigest.digest();
                java.lang.System.arraycopy(digest, 0, bArr, 512 - digest.length, digest.length);
                writeNewSeed(bArr);
                if (readSeedFile.length == 0) {
                    android.util.Slog.i(TAG, "Created seed file");
                } else {
                    android.util.Slog.i(TAG, "Updated seed file");
                }
            } catch (java.security.NoSuchAlgorithmException e2) {
                android.util.Slog.wtf(TAG, "SHA-256 algorithm not found; seed file won't be updated", e2);
            }
        } finally {
        }
    }

    private void writeNewSeed(byte[] bArr) {
        java.io.FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.seedFile.startWrite();
            fileOutputStream.write(bArr);
            this.seedFile.finishWrite(fileOutputStream);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error writing " + this.seedFile.getBaseFile(), e);
            this.seedFile.failWrite(fileOutputStream);
        }
    }

    private static byte[] longToBytes(long j) {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(8);
        allocate.putLong(j);
        return allocate.array();
    }

    private byte[] getDeviceSpecificInformation() {
        return (DEVICE_SPECIFIC_INFO_HEADER + START_TIME + '\n' + START_NANOTIME + '\n' + android.os.SystemProperties.get("ro.serialno") + '\n' + android.os.SystemProperties.get("ro.bootmode") + '\n' + android.os.SystemProperties.get("ro.baseband") + '\n' + android.os.SystemProperties.get("ro.carrier") + '\n' + android.os.SystemProperties.get("ro.bootloader") + '\n' + android.os.SystemProperties.get("ro.hardware") + '\n' + android.os.SystemProperties.get("ro.revision") + '\n' + android.os.SystemProperties.get("ro.build.fingerprint") + '\n' + new java.lang.Object().hashCode() + '\n' + java.lang.System.currentTimeMillis() + '\n' + java.lang.System.nanoTime() + '\n').getBytes();
    }

    private static java.io.File getSystemDir() {
        java.io.File file = new java.io.File(android.os.Environment.getDataDirectory(), "system");
        file.mkdirs();
        return file;
    }
}
