package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class MediaEvent extends android.media.tv.tuner.filter.FilterEvent {
    private final java.util.List<android.media.AudioPresentation> mAudioPresentations;
    private final long mDataId;
    private final long mDataLength;
    private final long mDts;
    private final android.media.tv.tuner.filter.AudioDescriptor mExtraMetaData;
    private final boolean mIsDtsPresent;
    private final boolean mIsPrivateData;
    private final boolean mIsPtsPresent;
    private final boolean mIsSecureMemory;
    private android.media.MediaCodec.LinearBlock mLinearBlock;
    private final int mMpuSequenceNumber;
    private long mNativeContext;
    private final long mOffset;
    private final long mPts;
    private final int mScIndexMask;
    private final int mStreamId;
    private boolean mReleased = false;
    private final java.lang.Object mLock = new java.lang.Object();

    private native void nativeFinalize();

    private native java.lang.Long nativeGetAudioHandle();

    private native android.media.MediaCodec.LinearBlock nativeGetLinearBlock();

    private MediaEvent(int i, boolean z, long j, boolean z2, long j2, long j3, long j4, android.media.MediaCodec.LinearBlock linearBlock, boolean z3, long j5, int i2, boolean z4, int i3, android.media.tv.tuner.filter.AudioDescriptor audioDescriptor, java.util.List<android.media.AudioPresentation> list) {
        this.mStreamId = i;
        this.mIsPtsPresent = z;
        this.mPts = j;
        this.mIsDtsPresent = z2;
        this.mDts = j2;
        this.mDataLength = j3;
        this.mOffset = j4;
        this.mLinearBlock = linearBlock;
        this.mIsSecureMemory = z3;
        this.mDataId = j5;
        this.mMpuSequenceNumber = i2;
        this.mIsPrivateData = z4;
        this.mScIndexMask = i3;
        this.mExtraMetaData = audioDescriptor;
        this.mAudioPresentations = list;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    public boolean isPtsPresent() {
        return this.mIsPtsPresent;
    }

    public long getPts() {
        return this.mPts;
    }

    public boolean isDtsPresent() {
        return this.mIsDtsPresent;
    }

    public long getDts() {
        return this.mDts;
    }

    public long getDataLength() {
        return this.mDataLength;
    }

    public long getOffset() {
        return this.mOffset;
    }

    public android.media.MediaCodec.LinearBlock getLinearBlock() {
        android.media.MediaCodec.LinearBlock linearBlock;
        synchronized (this.mLock) {
            if (this.mLinearBlock == null) {
                this.mLinearBlock = nativeGetLinearBlock();
            }
            linearBlock = this.mLinearBlock;
        }
        return linearBlock;
    }

    public boolean isSecureMemory() {
        return this.mIsSecureMemory;
    }

    public long getAvDataId() {
        return this.mDataId;
    }

    public long getAudioHandle() {
        nativeGetAudioHandle();
        return this.mDataId;
    }

    public int getMpuSequenceNumber() {
        return this.mMpuSequenceNumber;
    }

    public boolean isPrivateData() {
        return this.mIsPrivateData;
    }

    public int getScIndexMask() {
        return this.mScIndexMask;
    }

    public android.media.tv.tuner.filter.AudioDescriptor getExtraMetaData() {
        return this.mExtraMetaData;
    }

    public java.util.List<android.media.AudioPresentation> getAudioPresentations() {
        return this.mAudioPresentations == null ? java.util.Collections.emptyList() : this.mAudioPresentations;
    }

    protected void finalize() {
        release();
    }

    public void release() {
        synchronized (this.mLock) {
            if (this.mReleased) {
                return;
            }
            nativeFinalize();
            this.mNativeContext = 0L;
            this.mReleased = true;
        }
    }
}
