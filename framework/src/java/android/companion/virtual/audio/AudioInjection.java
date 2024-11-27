package android.companion.virtual.audio;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AudioInjection {
    private static final java.lang.String TAG = "AudioInjection";
    private final android.media.AudioFormat mAudioFormat;
    private android.media.AudioTrack mAudioTrack;
    private boolean mIsSilent;
    private final java.lang.Object mLock = new java.lang.Object();
    private int mPlayState = 1;

    void setSilent(boolean z) {
        synchronized (this.mLock) {
            this.mIsSilent = z;
        }
    }

    void setAudioTrack(android.media.AudioTrack audioTrack) {
        android.util.Log.d(TAG, "set AudioTrack with " + audioTrack);
        synchronized (this.mLock) {
            if (audioTrack != null) {
                if (audioTrack.getState() != 1) {
                    throw new java.lang.IllegalStateException("set an uninitialized AudioTrack.");
                }
                if (this.mPlayState == 3 && audioTrack.getPlayState() != 3) {
                    audioTrack.play();
                }
                if (this.mPlayState == 1 && audioTrack.getPlayState() != 1) {
                    audioTrack.stop();
                }
            }
            if (this.mAudioTrack != null) {
                this.mAudioTrack.release();
            }
            this.mAudioTrack = audioTrack;
        }
    }

    AudioInjection(android.media.AudioFormat audioFormat) {
        this.mAudioFormat = audioFormat;
    }

    void close() {
        synchronized (this.mLock) {
            if (this.mAudioTrack != null) {
                this.mAudioTrack.release();
                this.mAudioTrack = null;
            }
        }
    }

    public android.media.AudioFormat getFormat() {
        return this.mAudioFormat;
    }

    public int write(byte[] bArr, int i, int i2) {
        return write(bArr, i, i2, 0);
    }

    public int write(byte[] bArr, int i, int i2, int i3) {
        int i4;
        synchronized (this.mLock) {
            if (this.mAudioTrack != null && !this.mIsSilent) {
                i4 = this.mAudioTrack.write(bArr, i, i2, i3);
            } else {
                i4 = 0;
            }
        }
        return i4;
    }

    public int write(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        int i3;
        synchronized (this.mLock) {
            if (this.mAudioTrack != null && !this.mIsSilent) {
                i3 = this.mAudioTrack.write(byteBuffer, i, i2);
            } else {
                i3 = 0;
            }
        }
        return i3;
    }

    public int write(java.nio.ByteBuffer byteBuffer, int i, int i2, long j) {
        int i3;
        synchronized (this.mLock) {
            if (this.mAudioTrack != null && !this.mIsSilent) {
                i3 = this.mAudioTrack.write(byteBuffer, i, i2, j);
            } else {
                i3 = 0;
            }
        }
        return i3;
    }

    public int write(float[] fArr, int i, int i2, int i3) {
        int i4;
        synchronized (this.mLock) {
            if (this.mAudioTrack != null && !this.mIsSilent) {
                i4 = this.mAudioTrack.write(fArr, i, i2, i3);
            } else {
                i4 = 0;
            }
        }
        return i4;
    }

    public int write(short[] sArr, int i, int i2) {
        return write(sArr, i, i2, 0);
    }

    public int write(short[] sArr, int i, int i2, int i3) {
        int i4;
        synchronized (this.mLock) {
            if (this.mAudioTrack != null && !this.mIsSilent) {
                i4 = this.mAudioTrack.write(sArr, i, i2, i3);
            } else {
                i4 = 0;
            }
        }
        return i4;
    }

    public void play() {
        synchronized (this.mLock) {
            this.mPlayState = 3;
            if (this.mAudioTrack != null && this.mAudioTrack.getPlayState() != 3) {
                this.mAudioTrack.play();
            }
        }
    }

    public void stop() {
        synchronized (this.mLock) {
            this.mPlayState = 1;
            if (this.mAudioTrack != null && this.mAudioTrack.getPlayState() != 1) {
                this.mAudioTrack.stop();
            }
        }
    }

    public int getPlayState() {
        int i;
        synchronized (this.mLock) {
            i = this.mPlayState;
        }
        return i;
    }
}
