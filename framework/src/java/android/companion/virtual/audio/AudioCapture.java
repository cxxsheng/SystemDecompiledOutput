package android.companion.virtual.audio;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AudioCapture {
    private static final java.lang.String TAG = "AudioCapture";
    private final android.media.AudioFormat mAudioFormat;
    private android.media.AudioRecord mAudioRecord;
    private final java.lang.Object mLock = new java.lang.Object();
    private int mRecordingState = 1;

    void setAudioRecord(android.media.AudioRecord audioRecord) {
        android.util.Log.d(TAG, "set AudioRecord with " + audioRecord);
        synchronized (this.mLock) {
            if (audioRecord != null) {
                if (audioRecord.getState() != 1) {
                    throw new java.lang.IllegalStateException("set an uninitialized AudioRecord.");
                }
                if (this.mRecordingState == 3 && audioRecord.getRecordingState() != 3) {
                    audioRecord.startRecording();
                }
                if (this.mRecordingState == 1 && audioRecord.getRecordingState() != 1) {
                    audioRecord.stop();
                }
            }
            if (this.mAudioRecord != null) {
                this.mAudioRecord.release();
            }
            this.mAudioRecord = audioRecord;
        }
    }

    AudioCapture(android.media.AudioFormat audioFormat) {
        this.mAudioFormat = audioFormat;
    }

    void close() {
        synchronized (this.mLock) {
            if (this.mAudioRecord != null) {
                this.mAudioRecord.release();
                this.mAudioRecord = null;
            }
        }
    }

    public android.media.AudioFormat getFormat() {
        return this.mAudioFormat;
    }

    public int read(byte[] bArr, int i, int i2) {
        return read(bArr, i, i2, 0);
    }

    public int read(byte[] bArr, int i, int i2, int i3) {
        int i4;
        synchronized (this.mLock) {
            if (this.mAudioRecord != null) {
                i4 = this.mAudioRecord.read(bArr, i, i2, i3);
            } else {
                i4 = 0;
            }
        }
        return i4;
    }

    public int read(java.nio.ByteBuffer byteBuffer, int i) {
        return read(byteBuffer, i, 0);
    }

    public int read(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        int i3;
        synchronized (this.mLock) {
            if (this.mAudioRecord != null) {
                i3 = this.mAudioRecord.read(byteBuffer, i, i2);
            } else {
                i3 = 0;
            }
        }
        return i3;
    }

    public int read(float[] fArr, int i, int i2, int i3) {
        int i4;
        synchronized (this.mLock) {
            if (this.mAudioRecord != null) {
                i4 = this.mAudioRecord.read(fArr, i, i2, i3);
            } else {
                i4 = 0;
            }
        }
        return i4;
    }

    public int read(short[] sArr, int i, int i2) {
        return read(sArr, i, i2, 0);
    }

    public int read(short[] sArr, int i, int i2, int i3) {
        int i4;
        synchronized (this.mLock) {
            if (this.mAudioRecord != null) {
                i4 = this.mAudioRecord.read(sArr, i, i2, i3);
            } else {
                i4 = 0;
            }
        }
        return i4;
    }

    public void startRecording() {
        synchronized (this.mLock) {
            this.mRecordingState = 3;
            if (this.mAudioRecord != null && this.mAudioRecord.getRecordingState() != 3) {
                this.mAudioRecord.startRecording();
            }
        }
    }

    public void stop() {
        synchronized (this.mLock) {
            this.mRecordingState = 1;
            if (this.mAudioRecord != null && this.mAudioRecord.getRecordingState() != 1) {
                this.mAudioRecord.stop();
            }
        }
    }

    public int getRecordingState() {
        int i;
        synchronized (this.mLock) {
            i = this.mRecordingState;
        }
        return i;
    }
}
