package android.filterfw.core;

/* loaded from: classes.dex */
public class MutableFrameFormat extends android.filterfw.core.FrameFormat {
    public MutableFrameFormat() {
    }

    public MutableFrameFormat(int i, int i2) {
        super(i, i2);
    }

    public void setBaseType(int i) {
        this.mBaseType = i;
        this.mBytesPerSample = bytesPerSampleOf(i);
    }

    public void setTarget(int i) {
        this.mTarget = i;
    }

    public void setBytesPerSample(int i) {
        this.mBytesPerSample = i;
        this.mSize = -1;
    }

    public void setDimensions(int[] iArr) {
        this.mDimensions = iArr == null ? null : java.util.Arrays.copyOf(iArr, iArr.length);
        this.mSize = -1;
    }

    public void setDimensions(int i) {
        this.mDimensions = new int[]{i};
        this.mSize = -1;
    }

    public void setDimensions(int i, int i2) {
        this.mDimensions = new int[]{i, i2};
        this.mSize = -1;
    }

    public void setDimensions(int i, int i2, int i3) {
        this.mDimensions = new int[]{i, i2, i3};
        this.mSize = -1;
    }

    public void setDimensionCount(int i) {
        this.mDimensions = new int[i];
    }

    public void setObjectClass(java.lang.Class cls) {
        this.mObjectClass = cls;
    }

    public void setMetaValue(java.lang.String str, java.lang.Object obj) {
        if (this.mMetaData == null) {
            this.mMetaData = new android.filterfw.core.KeyValueMap();
        }
        this.mMetaData.put(str, obj);
    }
}
