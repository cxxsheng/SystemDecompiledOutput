package android.hardware.display;

/* loaded from: classes2.dex */
public final class DisplayedContentSample {
    private long mNumFrames;
    private long[] mSamplesComponent0;
    private long[] mSamplesComponent1;
    private long[] mSamplesComponent2;
    private long[] mSamplesComponent3;

    public enum ColorComponent {
        CHANNEL0,
        CHANNEL1,
        CHANNEL2,
        CHANNEL3
    }

    public DisplayedContentSample(long j, long[] jArr, long[] jArr2, long[] jArr3, long[] jArr4) {
        this.mNumFrames = j;
        this.mSamplesComponent0 = jArr;
        this.mSamplesComponent1 = jArr2;
        this.mSamplesComponent2 = jArr3;
        this.mSamplesComponent3 = jArr4;
    }

    public long[] getSampleComponent(android.hardware.display.DisplayedContentSample.ColorComponent colorComponent) {
        switch (colorComponent) {
            case CHANNEL0:
                return this.mSamplesComponent0;
            case CHANNEL1:
                return this.mSamplesComponent1;
            case CHANNEL2:
                return this.mSamplesComponent2;
            case CHANNEL3:
                return this.mSamplesComponent3;
            default:
                throw new java.lang.ArrayIndexOutOfBoundsException();
        }
    }

    public long getNumFrames() {
        return this.mNumFrames;
    }
}
