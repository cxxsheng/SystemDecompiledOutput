package com.android.server.powerstats;

/* loaded from: classes2.dex */
public class PowerStatsDataStorage {
    private static final long DELETE_AGE_MILLIS = 172800000;
    private static final long MILLISECONDS_PER_HOUR = 3600000;
    private static final long ROTATE_AGE_MILLIS = 14400000;
    private static final java.lang.String TAG = com.android.server.powerstats.PowerStatsDataStorage.class.getSimpleName();
    private final java.io.File mDataStorageDir;
    private final java.lang.String mDataStorageFilename;
    private final com.android.internal.util.FileRotator mFileRotator;
    private final java.util.concurrent.locks.ReentrantLock mLock = new java.util.concurrent.locks.ReentrantLock();

    public interface DataElementReadCallback {
        void onReadDataElement(byte[] bArr);
    }

    private static class DataElement {
        private static final int LENGTH_FIELD_WIDTH = 4;
        private static final int MAX_DATA_ELEMENT_SIZE = 32768;
        private byte[] mData;

        /* JADX INFO: Access modifiers changed from: private */
        public byte[] toByteArray() throws java.io.IOException {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            byteArrayOutputStream.write(java.nio.ByteBuffer.allocate(4).putInt(this.mData.length).array());
            byteArrayOutputStream.write(this.mData);
            return byteArrayOutputStream.toByteArray();
        }

        protected byte[] getData() {
            return this.mData;
        }

        private DataElement(byte[] bArr) {
            this.mData = bArr;
        }

        private DataElement(java.io.InputStream inputStream) throws java.io.IOException {
            byte[] bArr = new byte[4];
            int read = inputStream.read(bArr);
            this.mData = new byte[0];
            if (read == 4) {
                int i = java.nio.ByteBuffer.wrap(bArr).getInt();
                if (i > 0 && i < 32768) {
                    this.mData = new byte[i];
                    int read2 = inputStream.read(this.mData);
                    if (read2 != i) {
                        throw new java.io.IOException("Invalid bytes read, expected: " + i + ", actual: " + read2);
                    }
                    return;
                }
                throw new java.io.IOException("DataElement size is invalid: " + i);
            }
            throw new java.io.IOException("Did not read 4 bytes (" + read + ")");
        }
    }

    private static class DataReader implements com.android.internal.util.FileRotator.Reader {
        private com.android.server.powerstats.PowerStatsDataStorage.DataElementReadCallback mCallback;

        DataReader(com.android.server.powerstats.PowerStatsDataStorage.DataElementReadCallback dataElementReadCallback) {
            this.mCallback = dataElementReadCallback;
        }

        public void read(java.io.InputStream inputStream) throws java.io.IOException {
            while (inputStream.available() > 0) {
                this.mCallback.onReadDataElement(new com.android.server.powerstats.PowerStatsDataStorage.DataElement(inputStream).getData());
            }
        }
    }

    private static class DataRewriter implements com.android.internal.util.FileRotator.Rewriter {
        byte[] mActiveFileData = new byte[0];
        byte[] mNewData;

        DataRewriter(byte[] bArr) {
            this.mNewData = bArr;
        }

        public void reset() {
        }

        public void read(java.io.InputStream inputStream) throws java.io.IOException {
            this.mActiveFileData = new byte[inputStream.available()];
            inputStream.read(this.mActiveFileData);
        }

        public boolean shouldWrite() {
            return true;
        }

        public void write(java.io.OutputStream outputStream) throws java.io.IOException {
            outputStream.write(this.mActiveFileData);
            outputStream.write(this.mNewData);
        }
    }

    public PowerStatsDataStorage(android.content.Context context, java.io.File file, java.lang.String str) {
        this.mDataStorageDir = file;
        this.mDataStorageFilename = str;
        if (!this.mDataStorageDir.exists() && !this.mDataStorageDir.mkdirs()) {
            android.util.Slog.wtf(TAG, "mDataStorageDir does not exist: " + this.mDataStorageDir.getPath());
            this.mFileRotator = null;
            return;
        }
        java.io.File[] listFiles = this.mDataStorageDir.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].getName().startsWith(this.mDataStorageFilename.substring(0, this.mDataStorageFilename.lastIndexOf(46))) && !listFiles[i].getName().startsWith(this.mDataStorageFilename)) {
                listFiles[i].delete();
            }
        }
        this.mFileRotator = new com.android.internal.util.FileRotator(this.mDataStorageDir, this.mDataStorageFilename, 14400000L, DELETE_AGE_MILLIS);
    }

    public void write(byte[] bArr) {
        if (bArr != null && bArr.length > 0) {
            this.mLock.lock();
            try {
                try {
                    long currentTimeMillis = java.lang.System.currentTimeMillis();
                    this.mFileRotator.rewriteActive(new com.android.server.powerstats.PowerStatsDataStorage.DataRewriter(new com.android.server.powerstats.PowerStatsDataStorage.DataElement(bArr).toByteArray()), currentTimeMillis);
                    this.mFileRotator.maybeRotate(currentTimeMillis);
                } catch (java.io.IOException e) {
                    android.util.Slog.e(TAG, "Failed to write to on-device storage: " + e);
                }
            } finally {
                this.mLock.unlock();
            }
        }
    }

    public void read(com.android.server.powerstats.PowerStatsDataStorage.DataElementReadCallback dataElementReadCallback) throws java.io.IOException {
        this.mLock.lock();
        try {
            this.mFileRotator.readMatching(new com.android.server.powerstats.PowerStatsDataStorage.DataReader(dataElementReadCallback), Long.MIN_VALUE, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
        } finally {
            this.mLock.unlock();
        }
    }

    public void deleteLogs() {
        this.mLock.lock();
        try {
            java.io.File[] listFiles = this.mDataStorageDir.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].getName().startsWith(this.mDataStorageFilename.substring(0, this.mDataStorageFilename.lastIndexOf(46)))) {
                    listFiles[i].delete();
                }
            }
        } finally {
            this.mLock.unlock();
        }
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mLock.lock();
        try {
            java.lang.String substring = this.mDataStorageFilename.substring(0, this.mDataStorageFilename.lastIndexOf(46));
            java.io.File[] listFiles = this.mDataStorageDir.listFiles();
            int i = 0;
            int i2 = 0;
            long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            for (int i3 = 0; i3 < listFiles.length; i3++) {
                java.io.File file = listFiles[i3];
                java.lang.String name = file.getName();
                if (listFiles[i3].getName().startsWith(substring)) {
                    int i4 = i + 1;
                    int length = (int) (i2 + file.length());
                    try {
                        java.lang.Long valueOf = java.lang.Long.valueOf(java.lang.Long.parseLong(name.substring(name.lastIndexOf(46) + 1, name.lastIndexOf(45))));
                        if (valueOf.longValue() < j) {
                            j = valueOf.longValue();
                        }
                        i2 = length;
                        i = i4;
                    } catch (java.lang.NumberFormatException e) {
                        android.util.Slog.e(TAG, "Failed to extract start time from file : " + name, e);
                        i2 = length;
                        i = i4;
                    }
                }
            }
            if (j != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                indentingPrintWriter.println("Earliest data time : " + new java.util.Date(j));
            } else {
                indentingPrintWriter.println("Failed to parse earliest data time!!!");
            }
            indentingPrintWriter.println("# files : " + i);
            indentingPrintWriter.println("Total data size (B) : " + i2);
        } finally {
            this.mLock.unlock();
        }
    }
}
