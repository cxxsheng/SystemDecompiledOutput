package android.media.midi;

/* loaded from: classes2.dex */
public abstract class MidiReceiver {
    private final int mMaxMessageSize;

    public abstract void onSend(byte[] bArr, int i, int i2, long j) throws java.io.IOException;

    public MidiReceiver() {
        this.mMaxMessageSize = Integer.MAX_VALUE;
    }

    public MidiReceiver(int i) {
        this.mMaxMessageSize = i;
    }

    public void flush() throws java.io.IOException {
        onFlush();
    }

    public void onFlush() throws java.io.IOException {
    }

    public final int getMaxMessageSize() {
        return this.mMaxMessageSize;
    }

    public void send(byte[] bArr, int i, int i2) throws java.io.IOException {
        send(bArr, i, i2, 0L);
    }

    public void send(byte[] bArr, int i, int i2, long j) throws java.io.IOException {
        int maxMessageSize = getMaxMessageSize();
        while (i2 > 0) {
            int i3 = i2 > maxMessageSize ? maxMessageSize : i2;
            onSend(bArr, i, i3, j);
            i += i3;
            i2 -= i3;
        }
    }
}
