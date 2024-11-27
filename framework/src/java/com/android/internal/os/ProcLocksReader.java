package com.android.internal.os;

/* loaded from: classes4.dex */
public class ProcLocksReader {
    private final java.lang.String mPath;
    private android.util.IntArray mPids;
    private com.android.internal.util.ProcFileReader mReader;

    public interface ProcLocksReaderCallback {
        void onBlockingFileLock(android.util.IntArray intArray);
    }

    public ProcLocksReader() {
        this.mReader = null;
        this.mPids = new android.util.IntArray();
        this.mPath = "/proc/locks";
    }

    public ProcLocksReader(java.lang.String str) {
        this.mReader = null;
        this.mPids = new android.util.IntArray();
        this.mPath = str;
    }

    public void handleBlockingFileLocks(com.android.internal.os.ProcLocksReader.ProcLocksReaderCallback procLocksReaderCallback) throws java.io.IOException {
        if (this.mReader == null) {
            this.mReader = new com.android.internal.util.ProcFileReader(new java.io.FileInputStream(this.mPath));
        } else {
            this.mReader.rewind();
        }
        this.mPids.clear();
        long j = -1;
        while (this.mReader.hasMoreData()) {
            long nextLong = this.mReader.nextLong(true);
            if (nextLong == j) {
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                int nextInt = this.mReader.nextInt();
                if (nextInt > 0) {
                    this.mPids.add(nextInt);
                }
                this.mReader.finishLine();
            } else {
                if (this.mPids.size() > 1) {
                    procLocksReaderCallback.onBlockingFileLock(this.mPids);
                    this.mPids.clear();
                }
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                int nextInt2 = this.mReader.nextInt();
                if (nextInt2 > 0) {
                    if (this.mPids.size() == 0) {
                        this.mPids.add(nextInt2);
                    } else {
                        this.mPids.set(0, nextInt2);
                    }
                }
                this.mReader.finishLine();
                j = nextLong;
            }
        }
        if (this.mPids.size() > 1) {
            procLocksReaderCallback.onBlockingFileLock(this.mPids);
        }
    }
}
