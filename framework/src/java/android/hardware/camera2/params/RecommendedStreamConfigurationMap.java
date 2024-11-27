package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class RecommendedStreamConfigurationMap {
    public static final int MAX_USECASE_COUNT = 32;
    private static final java.lang.String TAG = "RecommendedStreamConfigurationMap";
    public static final int USECASE_10BIT_OUTPUT = 8;
    public static final int USECASE_LOW_LATENCY_SNAPSHOT = 6;
    public static final int USECASE_PREVIEW = 0;
    public static final int USECASE_RAW = 5;
    public static final int USECASE_RECORD = 1;
    public static final int USECASE_SNAPSHOT = 3;
    public static final int USECASE_VENDOR_START = 24;
    public static final int USECASE_VIDEO_SNAPSHOT = 2;
    public static final int USECASE_ZSL = 4;
    private android.hardware.camera2.params.StreamConfigurationMap mRecommendedMap;
    private boolean mSupportsPrivate;
    private int mUsecase;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecommendedUsecase {
    }

    public RecommendedStreamConfigurationMap(android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap, int i, boolean z) {
        this.mRecommendedMap = streamConfigurationMap;
        this.mUsecase = i;
        this.mSupportsPrivate = z;
    }

    public int getRecommendedUseCase() {
        return this.mUsecase;
    }

    private java.util.Set<java.lang.Integer> getUnmodifiableIntegerSet(int[] iArr) {
        if (iArr != null && iArr.length > 0) {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            arraySet.ensureCapacity(iArr.length);
            for (int i : iArr) {
                arraySet.add(java.lang.Integer.valueOf(i));
            }
            return java.util.Collections.unmodifiableSet(arraySet);
        }
        return null;
    }

    public java.util.Set<java.lang.Integer> getOutputFormats() {
        return getUnmodifiableIntegerSet(this.mRecommendedMap.getOutputFormats());
    }

    public java.util.Set<java.lang.Integer> getValidOutputFormatsForInput(int i) {
        return getUnmodifiableIntegerSet(this.mRecommendedMap.getValidOutputFormatsForInput(i));
    }

    public java.util.Set<java.lang.Integer> getInputFormats() {
        return getUnmodifiableIntegerSet(this.mRecommendedMap.getInputFormats());
    }

    private java.util.Set<android.util.Size> getUnmodifiableSizeSet(android.util.Size[] sizeArr) {
        if (sizeArr != null && sizeArr.length > 0) {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            arraySet.addAll(java.util.Arrays.asList(sizeArr));
            return java.util.Collections.unmodifiableSet(arraySet);
        }
        return null;
    }

    public java.util.Set<android.util.Size> getInputSizes(int i) {
        return getUnmodifiableSizeSet(this.mRecommendedMap.getInputSizes(i));
    }

    public boolean isOutputSupportedFor(int i) {
        return this.mRecommendedMap.isOutputSupportedFor(i);
    }

    public java.util.Set<android.util.Size> getOutputSizes(int i) {
        return getUnmodifiableSizeSet(this.mRecommendedMap.getOutputSizes(i));
    }

    public java.util.Set<android.util.Size> getHighSpeedVideoSizes() {
        return getUnmodifiableSizeSet(this.mRecommendedMap.getHighSpeedVideoSizes());
    }

    private java.util.Set<android.util.Range<java.lang.Integer>> getUnmodifiableRangeSet(android.util.Range<java.lang.Integer>[] rangeArr) {
        if (rangeArr != null && rangeArr.length > 0) {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            arraySet.addAll(java.util.Arrays.asList(rangeArr));
            return java.util.Collections.unmodifiableSet(arraySet);
        }
        return null;
    }

    public java.util.Set<android.util.Range<java.lang.Integer>> getHighSpeedVideoFpsRangesFor(android.util.Size size) {
        return getUnmodifiableRangeSet(this.mRecommendedMap.getHighSpeedVideoFpsRangesFor(size));
    }

    public java.util.Set<android.util.Range<java.lang.Integer>> getHighSpeedVideoFpsRanges() {
        return getUnmodifiableRangeSet(this.mRecommendedMap.getHighSpeedVideoFpsRanges());
    }

    public java.util.Set<android.util.Size> getHighSpeedVideoSizesFor(android.util.Range<java.lang.Integer> range) {
        return getUnmodifiableSizeSet(this.mRecommendedMap.getHighSpeedVideoSizesFor(range));
    }

    public java.util.Set<android.util.Size> getHighResolutionOutputSizes(int i) {
        return getUnmodifiableSizeSet(this.mRecommendedMap.getHighResolutionOutputSizes(i));
    }

    public long getOutputMinFrameDuration(int i, android.util.Size size) {
        return this.mRecommendedMap.getOutputMinFrameDuration(i, size);
    }

    public long getOutputStallDuration(int i, android.util.Size size) {
        return this.mRecommendedMap.getOutputStallDuration(i, size);
    }

    public <T> java.util.Set<android.util.Size> getOutputSizes(java.lang.Class<T> cls) {
        if (this.mSupportsPrivate) {
            return getUnmodifiableSizeSet(this.mRecommendedMap.getOutputSizes(cls));
        }
        return null;
    }

    public <T> long getOutputMinFrameDuration(java.lang.Class<T> cls, android.util.Size size) {
        if (this.mSupportsPrivate) {
            return this.mRecommendedMap.getOutputMinFrameDuration(cls, size);
        }
        return 0L;
    }

    public <T> long getOutputStallDuration(java.lang.Class<T> cls, android.util.Size size) {
        if (this.mSupportsPrivate) {
            return this.mRecommendedMap.getOutputStallDuration(cls, size);
        }
        return 0L;
    }

    public boolean isOutputSupportedFor(android.view.Surface surface) {
        return this.mRecommendedMap.isOutputSupportedFor(surface);
    }
}
