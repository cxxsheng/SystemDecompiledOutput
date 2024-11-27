package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class StreamConfigurationMap {
    private static final long DURATION_20FPS_NS = 50000000;
    private static final int DURATION_MIN_FRAME = 0;
    private static final int DURATION_STALL = 1;
    public static final int HAL_DATASPACE_DEPTH = 4096;
    public static final int HAL_DATASPACE_DYNAMIC_DEPTH = 4098;
    public static final int HAL_DATASPACE_HEIF = 4100;
    public static final int HAL_DATASPACE_JPEG_R = 4101;
    private static final int HAL_DATASPACE_RANGE_SHIFT = 27;
    private static final int HAL_DATASPACE_STANDARD_SHIFT = 16;
    private static final int HAL_DATASPACE_TRANSFER_SHIFT = 22;
    private static final int HAL_DATASPACE_UNKNOWN = 0;
    public static final int HAL_DATASPACE_V0_JFIF = 146931712;
    public static final int HAL_PIXEL_FORMAT_BLOB = 33;
    private static final int HAL_PIXEL_FORMAT_IMPLEMENTATION_DEFINED = 34;
    private static final int HAL_PIXEL_FORMAT_RAW10 = 37;
    private static final int HAL_PIXEL_FORMAT_RAW12 = 38;
    private static final int HAL_PIXEL_FORMAT_RAW16 = 32;
    private static final int HAL_PIXEL_FORMAT_RAW_OPAQUE = 36;
    private static final int HAL_PIXEL_FORMAT_Y16 = 540422489;
    private static final int HAL_PIXEL_FORMAT_YCbCr_420_888 = 35;
    private static final int MAX_DIMEN_FOR_ROUNDING = 1920;
    private static final java.lang.String TAG = "StreamConfigurationMap";
    private final android.util.SparseIntArray mAllOutputFormats;
    private final android.hardware.camera2.params.StreamConfiguration[] mConfigurations;
    private final android.hardware.camera2.params.StreamConfiguration[] mDepthConfigurations;
    private final android.hardware.camera2.params.StreamConfigurationDuration[] mDepthMinFrameDurations;
    private final android.util.SparseIntArray mDepthOutputFormats;
    private final android.hardware.camera2.params.StreamConfigurationDuration[] mDepthStallDurations;
    private final android.hardware.camera2.params.StreamConfiguration[] mDynamicDepthConfigurations;
    private final android.hardware.camera2.params.StreamConfigurationDuration[] mDynamicDepthMinFrameDurations;
    private final android.util.SparseIntArray mDynamicDepthOutputFormats;
    private final android.hardware.camera2.params.StreamConfigurationDuration[] mDynamicDepthStallDurations;
    private final android.hardware.camera2.params.StreamConfiguration[] mHeicConfigurations;
    private final android.hardware.camera2.params.StreamConfigurationDuration[] mHeicMinFrameDurations;
    private final android.util.SparseIntArray mHeicOutputFormats;
    private final android.hardware.camera2.params.StreamConfigurationDuration[] mHeicStallDurations;
    private final android.util.SparseIntArray mHighResOutputFormats;
    private final android.hardware.camera2.params.HighSpeedVideoConfiguration[] mHighSpeedVideoConfigurations;
    private final java.util.HashMap<android.util.Range<java.lang.Integer>, java.lang.Integer> mHighSpeedVideoFpsRangeMap;
    private final java.util.HashMap<android.util.Size, java.lang.Integer> mHighSpeedVideoSizeMap;
    private final android.util.SparseIntArray mInputFormats;
    private final android.hardware.camera2.params.ReprocessFormatsMap mInputOutputFormatsMap;
    private final android.hardware.camera2.params.StreamConfiguration[] mJpegRConfigurations;
    private final android.hardware.camera2.params.StreamConfigurationDuration[] mJpegRMinFrameDurations;
    private final android.util.SparseIntArray mJpegROutputFormats;
    private final android.hardware.camera2.params.StreamConfigurationDuration[] mJpegRStallDurations;
    private final boolean mListHighResolution;
    private final android.hardware.camera2.params.StreamConfigurationDuration[] mMinFrameDurations;
    private final android.util.SparseIntArray mOutputFormats;
    private final android.hardware.camera2.params.StreamConfigurationDuration[] mStallDurations;

    public StreamConfigurationMap(android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr2, android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr2, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr3, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr4, android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr3, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr5, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr6, android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr4, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr7, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr8, android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr5, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr9, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr10, android.hardware.camera2.params.HighSpeedVideoConfiguration[] highSpeedVideoConfigurationArr, android.hardware.camera2.params.ReprocessFormatsMap reprocessFormatsMap, boolean z) {
        this(streamConfigurationArr, streamConfigurationDurationArr, streamConfigurationDurationArr2, streamConfigurationArr2, streamConfigurationDurationArr3, streamConfigurationDurationArr4, streamConfigurationArr3, streamConfigurationDurationArr5, streamConfigurationDurationArr6, streamConfigurationArr4, streamConfigurationDurationArr7, streamConfigurationDurationArr8, streamConfigurationArr5, streamConfigurationDurationArr9, streamConfigurationDurationArr10, highSpeedVideoConfigurationArr, reprocessFormatsMap, z, true);
    }

    public StreamConfigurationMap(android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr2, android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr2, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr3, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr4, android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr3, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr5, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr6, android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr4, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr7, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr8, android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr5, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr9, android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr10, android.hardware.camera2.params.HighSpeedVideoConfiguration[] highSpeedVideoConfigurationArr, android.hardware.camera2.params.ReprocessFormatsMap reprocessFormatsMap, boolean z, boolean z2) {
        android.util.SparseIntArray sparseIntArray;
        long j;
        this.mOutputFormats = new android.util.SparseIntArray();
        this.mHighResOutputFormats = new android.util.SparseIntArray();
        this.mAllOutputFormats = new android.util.SparseIntArray();
        this.mInputFormats = new android.util.SparseIntArray();
        this.mDepthOutputFormats = new android.util.SparseIntArray();
        this.mDynamicDepthOutputFormats = new android.util.SparseIntArray();
        this.mHeicOutputFormats = new android.util.SparseIntArray();
        this.mJpegROutputFormats = new android.util.SparseIntArray();
        this.mHighSpeedVideoSizeMap = new java.util.HashMap<>();
        this.mHighSpeedVideoFpsRangeMap = new java.util.HashMap<>();
        if (streamConfigurationArr != null || streamConfigurationArr2 != null || streamConfigurationArr4 != null) {
            if (streamConfigurationArr == null) {
                this.mConfigurations = new android.hardware.camera2.params.StreamConfiguration[0];
                this.mMinFrameDurations = new android.hardware.camera2.params.StreamConfigurationDuration[0];
                this.mStallDurations = new android.hardware.camera2.params.StreamConfigurationDuration[0];
            } else {
                this.mConfigurations = (android.hardware.camera2.params.StreamConfiguration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationArr, "configurations");
                this.mMinFrameDurations = (android.hardware.camera2.params.StreamConfigurationDuration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr, "minFrameDurations");
                this.mStallDurations = (android.hardware.camera2.params.StreamConfigurationDuration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr2, "stallDurations");
            }
            this.mListHighResolution = z;
            if (streamConfigurationArr2 == null) {
                this.mDepthConfigurations = new android.hardware.camera2.params.StreamConfiguration[0];
                this.mDepthMinFrameDurations = new android.hardware.camera2.params.StreamConfigurationDuration[0];
                this.mDepthStallDurations = new android.hardware.camera2.params.StreamConfigurationDuration[0];
            } else {
                this.mDepthConfigurations = (android.hardware.camera2.params.StreamConfiguration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationArr2, "depthConfigurations");
                this.mDepthMinFrameDurations = (android.hardware.camera2.params.StreamConfigurationDuration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr3, "depthMinFrameDurations");
                this.mDepthStallDurations = (android.hardware.camera2.params.StreamConfigurationDuration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr4, "depthStallDurations");
            }
            if (streamConfigurationArr3 == null) {
                this.mDynamicDepthConfigurations = new android.hardware.camera2.params.StreamConfiguration[0];
                this.mDynamicDepthMinFrameDurations = new android.hardware.camera2.params.StreamConfigurationDuration[0];
                this.mDynamicDepthStallDurations = new android.hardware.camera2.params.StreamConfigurationDuration[0];
            } else {
                this.mDynamicDepthConfigurations = (android.hardware.camera2.params.StreamConfiguration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationArr3, "dynamicDepthConfigurations");
                this.mDynamicDepthMinFrameDurations = (android.hardware.camera2.params.StreamConfigurationDuration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr5, "dynamicDepthMinFrameDurations");
                this.mDynamicDepthStallDurations = (android.hardware.camera2.params.StreamConfigurationDuration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr6, "dynamicDepthStallDurations");
            }
            if (streamConfigurationArr4 == null) {
                this.mHeicConfigurations = new android.hardware.camera2.params.StreamConfiguration[0];
                this.mHeicMinFrameDurations = new android.hardware.camera2.params.StreamConfigurationDuration[0];
                this.mHeicStallDurations = new android.hardware.camera2.params.StreamConfigurationDuration[0];
            } else {
                this.mHeicConfigurations = (android.hardware.camera2.params.StreamConfiguration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationArr4, "heicConfigurations");
                this.mHeicMinFrameDurations = (android.hardware.camera2.params.StreamConfigurationDuration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr7, "heicMinFrameDurations");
                this.mHeicStallDurations = (android.hardware.camera2.params.StreamConfigurationDuration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr8, "heicStallDurations");
            }
            if (streamConfigurationArr5 == null) {
                this.mJpegRConfigurations = new android.hardware.camera2.params.StreamConfiguration[0];
                this.mJpegRMinFrameDurations = new android.hardware.camera2.params.StreamConfigurationDuration[0];
                this.mJpegRStallDurations = new android.hardware.camera2.params.StreamConfigurationDuration[0];
            } else {
                this.mJpegRConfigurations = (android.hardware.camera2.params.StreamConfiguration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationArr5, "jpegRConfigurations");
                this.mJpegRMinFrameDurations = (android.hardware.camera2.params.StreamConfigurationDuration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr9, "jpegRFrameDurations");
                this.mJpegRStallDurations = (android.hardware.camera2.params.StreamConfigurationDuration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(streamConfigurationDurationArr10, "jpegRStallDurations");
            }
            if (highSpeedVideoConfigurationArr == null) {
                this.mHighSpeedVideoConfigurations = new android.hardware.camera2.params.HighSpeedVideoConfiguration[0];
            } else {
                this.mHighSpeedVideoConfigurations = (android.hardware.camera2.params.HighSpeedVideoConfiguration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(highSpeedVideoConfigurationArr, "highSpeedVideoConfigurations");
            }
            for (android.hardware.camera2.params.StreamConfiguration streamConfiguration : this.mConfigurations) {
                int format = streamConfiguration.getFormat();
                if (streamConfiguration.isOutput()) {
                    this.mAllOutputFormats.put(format, this.mAllOutputFormats.get(format) + 1);
                    if (this.mListHighResolution) {
                        for (android.hardware.camera2.params.StreamConfigurationDuration streamConfigurationDuration : this.mMinFrameDurations) {
                            if (streamConfigurationDuration.getFormat() == format && streamConfigurationDuration.getWidth() == streamConfiguration.getSize().getWidth() && streamConfigurationDuration.getHeight() == streamConfiguration.getSize().getHeight()) {
                                j = streamConfigurationDuration.getDuration();
                                break;
                            }
                        }
                    }
                    j = 0;
                    if (j > DURATION_20FPS_NS) {
                        sparseIntArray = this.mHighResOutputFormats;
                    } else {
                        sparseIntArray = this.mOutputFormats;
                    }
                } else {
                    sparseIntArray = this.mInputFormats;
                }
                sparseIntArray.put(format, sparseIntArray.get(format) + 1);
            }
            for (android.hardware.camera2.params.StreamConfiguration streamConfiguration2 : this.mDepthConfigurations) {
                if (streamConfiguration2.isOutput()) {
                    this.mDepthOutputFormats.put(streamConfiguration2.getFormat(), this.mDepthOutputFormats.get(streamConfiguration2.getFormat()) + 1);
                }
            }
            for (android.hardware.camera2.params.StreamConfiguration streamConfiguration3 : this.mDynamicDepthConfigurations) {
                if (streamConfiguration3.isOutput()) {
                    this.mDynamicDepthOutputFormats.put(streamConfiguration3.getFormat(), this.mDynamicDepthOutputFormats.get(streamConfiguration3.getFormat()) + 1);
                }
            }
            for (android.hardware.camera2.params.StreamConfiguration streamConfiguration4 : this.mHeicConfigurations) {
                if (streamConfiguration4.isOutput()) {
                    this.mHeicOutputFormats.put(streamConfiguration4.getFormat(), this.mHeicOutputFormats.get(streamConfiguration4.getFormat()) + 1);
                }
            }
            for (android.hardware.camera2.params.StreamConfiguration streamConfiguration5 : this.mJpegRConfigurations) {
                if (streamConfiguration5.isOutput()) {
                    this.mJpegROutputFormats.put(streamConfiguration5.getFormat(), this.mJpegROutputFormats.get(streamConfiguration5.getFormat()) + 1);
                }
            }
            if (streamConfigurationArr != null && z2 && this.mOutputFormats.indexOfKey(34) < 0) {
                throw new java.lang.AssertionError("At least one stream configuration for IMPLEMENTATION_DEFINED must exist");
            }
            for (android.hardware.camera2.params.HighSpeedVideoConfiguration highSpeedVideoConfiguration : this.mHighSpeedVideoConfigurations) {
                android.util.Size size = highSpeedVideoConfiguration.getSize();
                android.util.Range<java.lang.Integer> fpsRange = highSpeedVideoConfiguration.getFpsRange();
                java.lang.Integer num = this.mHighSpeedVideoSizeMap.get(size);
                this.mHighSpeedVideoSizeMap.put(size, java.lang.Integer.valueOf((num == null ? 0 : num).intValue() + 1));
                java.lang.Integer num2 = this.mHighSpeedVideoFpsRangeMap.get(fpsRange);
                if (num2 == null) {
                    num2 = 0;
                }
                this.mHighSpeedVideoFpsRangeMap.put(fpsRange, java.lang.Integer.valueOf(num2.intValue() + 1));
            }
            this.mInputOutputFormatsMap = reprocessFormatsMap;
            return;
        }
        throw new java.lang.NullPointerException("At least one of color/depth/heic configurations must not be null");
    }

    public int[] getOutputFormats() {
        return getPublicFormats(true);
    }

    public int[] getValidOutputFormatsForInput(int i) {
        if (this.mInputOutputFormatsMap == null) {
            return new int[0];
        }
        int[] outputs = this.mInputOutputFormatsMap.getOutputs(i);
        if (this.mHeicOutputFormats.size() > 0) {
            int[] copyOf = java.util.Arrays.copyOf(outputs, outputs.length + 1);
            copyOf[outputs.length] = 1212500294;
            return copyOf;
        }
        return outputs;
    }

    public int[] getInputFormats() {
        return getPublicFormats(false);
    }

    public android.util.Size[] getInputSizes(int i) {
        return getPublicFormatSizes(i, false, false);
    }

    public boolean isOutputSupportedFor(int i) {
        checkArgumentFormat(i);
        int imageFormatToInternal = imageFormatToInternal(i);
        int imageFormatToDataspace = imageFormatToDataspace(i);
        return imageFormatToDataspace == 4096 ? this.mDepthOutputFormats.indexOfKey(imageFormatToInternal) >= 0 : imageFormatToDataspace == 4098 ? this.mDynamicDepthOutputFormats.indexOfKey(imageFormatToInternal) >= 0 : imageFormatToDataspace == 4100 ? this.mHeicOutputFormats.indexOfKey(imageFormatToInternal) >= 0 : imageFormatToDataspace == 4101 ? this.mJpegROutputFormats.indexOfKey(imageFormatToInternal) >= 0 : getFormatsMap(true).indexOfKey(imageFormatToInternal) >= 0;
    }

    public static <T> boolean isOutputSupportedFor(java.lang.Class<T> cls) {
        java.util.Objects.requireNonNull(cls, "klass must not be null");
        return cls == android.media.ImageReader.class || cls == android.media.MediaRecorder.class || cls == android.media.MediaCodec.class || cls == android.renderscript.Allocation.class || cls == android.view.SurfaceHolder.class || cls == android.graphics.SurfaceTexture.class;
    }

    public boolean isOutputSupportedFor(android.view.Surface surface) {
        android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr;
        java.util.Objects.requireNonNull(surface, "surface must not be null");
        android.util.Size surfaceSize = android.hardware.camera2.utils.SurfaceUtils.getSurfaceSize(surface);
        int surfaceFormat = android.hardware.camera2.utils.SurfaceUtils.getSurfaceFormat(surface);
        int surfaceDataspace = android.hardware.camera2.utils.SurfaceUtils.getSurfaceDataspace(surface);
        boolean isFlexibleConsumer = android.hardware.camera2.utils.SurfaceUtils.isFlexibleConsumer(surface);
        if (surfaceDataspace == 4096) {
            streamConfigurationArr = this.mDepthConfigurations;
        } else if (surfaceDataspace == 4098) {
            streamConfigurationArr = this.mDynamicDepthConfigurations;
        } else if (surfaceDataspace == 4100) {
            streamConfigurationArr = this.mHeicConfigurations;
        } else {
            streamConfigurationArr = surfaceDataspace == 4101 ? this.mJpegRConfigurations : this.mConfigurations;
        }
        for (android.hardware.camera2.params.StreamConfiguration streamConfiguration : streamConfigurationArr) {
            if (streamConfiguration.getFormat() == surfaceFormat && streamConfiguration.isOutput()) {
                if (streamConfiguration.getSize().equals(surfaceSize)) {
                    return true;
                }
                if (isFlexibleConsumer && streamConfiguration.getSize().getWidth() <= 1920) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOutputSupportedFor(android.util.Size size, int i) {
        android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr;
        int imageFormatToInternal = imageFormatToInternal(i);
        int imageFormatToDataspace = imageFormatToDataspace(i);
        if (imageFormatToDataspace == 4096) {
            streamConfigurationArr = this.mDepthConfigurations;
        } else if (imageFormatToDataspace == 4098) {
            streamConfigurationArr = this.mDynamicDepthConfigurations;
        } else if (imageFormatToDataspace == 4100) {
            streamConfigurationArr = this.mHeicConfigurations;
        } else {
            streamConfigurationArr = imageFormatToDataspace == 4101 ? this.mJpegRConfigurations : this.mConfigurations;
        }
        for (android.hardware.camera2.params.StreamConfiguration streamConfiguration : streamConfigurationArr) {
            if (streamConfiguration.getFormat() == imageFormatToInternal && streamConfiguration.isOutput() && streamConfiguration.getSize().equals(size)) {
                return true;
            }
        }
        return false;
    }

    public <T> android.util.Size[] getOutputSizes(java.lang.Class<T> cls) {
        if (!isOutputSupportedFor(cls)) {
            return null;
        }
        return getInternalFormatSizes(34, 0, true, false);
    }

    public android.util.Size[] getOutputSizes(int i) {
        return getPublicFormatSizes(i, true, false);
    }

    public android.util.Size[] getHighSpeedVideoSizes() {
        java.util.Set<android.util.Size> keySet = this.mHighSpeedVideoSizeMap.keySet();
        return (android.util.Size[]) keySet.toArray(new android.util.Size[keySet.size()]);
    }

    public android.util.Range<java.lang.Integer>[] getHighSpeedVideoFpsRangesFor(android.util.Size size) {
        java.lang.Integer num = this.mHighSpeedVideoSizeMap.get(size);
        if (num == null || num.intValue() == 0) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Size %s does not support high speed video recording", size));
        }
        android.util.Range<java.lang.Integer>[] rangeArr = new android.util.Range[num.intValue()];
        int i = 0;
        for (android.hardware.camera2.params.HighSpeedVideoConfiguration highSpeedVideoConfiguration : this.mHighSpeedVideoConfigurations) {
            if (size.equals(highSpeedVideoConfiguration.getSize())) {
                rangeArr[i] = highSpeedVideoConfiguration.getFpsRange();
                i++;
            }
        }
        return rangeArr;
    }

    public android.util.Range<java.lang.Integer>[] getHighSpeedVideoFpsRanges() {
        java.util.Set<android.util.Range<java.lang.Integer>> keySet = this.mHighSpeedVideoFpsRangeMap.keySet();
        return (android.util.Range[]) keySet.toArray(new android.util.Range[keySet.size()]);
    }

    public android.util.Size[] getHighSpeedVideoSizesFor(android.util.Range<java.lang.Integer> range) {
        java.lang.Integer num = this.mHighSpeedVideoFpsRangeMap.get(range);
        if (num == null || num.intValue() == 0) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("FpsRange %s does not support high speed video recording", range));
        }
        android.util.Size[] sizeArr = new android.util.Size[num.intValue()];
        int i = 0;
        for (android.hardware.camera2.params.HighSpeedVideoConfiguration highSpeedVideoConfiguration : this.mHighSpeedVideoConfigurations) {
            if (range.equals(highSpeedVideoConfiguration.getFpsRange())) {
                sizeArr[i] = highSpeedVideoConfiguration.getSize();
                i++;
            }
        }
        return sizeArr;
    }

    public android.util.Size[] getHighResolutionOutputSizes(int i) {
        if (this.mListHighResolution) {
            return getPublicFormatSizes(i, true, true);
        }
        return null;
    }

    public long getOutputMinFrameDuration(int i, android.util.Size size) {
        java.util.Objects.requireNonNull(size, "size must not be null");
        checkArgumentFormatSupported(i, true);
        return getInternalFormatDuration(imageFormatToInternal(i), imageFormatToDataspace(i), size, 0);
    }

    public <T> long getOutputMinFrameDuration(java.lang.Class<T> cls, android.util.Size size) {
        if (!isOutputSupportedFor(cls)) {
            throw new java.lang.IllegalArgumentException("klass was not supported");
        }
        return getInternalFormatDuration(34, 0, size, 0);
    }

    public long getOutputStallDuration(int i, android.util.Size size) {
        checkArgumentFormatSupported(i, true);
        return getInternalFormatDuration(imageFormatToInternal(i), imageFormatToDataspace(i), size, 1);
    }

    public <T> long getOutputStallDuration(java.lang.Class<T> cls, android.util.Size size) {
        if (!isOutputSupportedFor(cls)) {
            throw new java.lang.IllegalArgumentException("klass was not supported");
        }
        return getInternalFormatDuration(34, 0, size, 1);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.StreamConfigurationMap)) {
            return false;
        }
        android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap = (android.hardware.camera2.params.StreamConfigurationMap) obj;
        if (!java.util.Arrays.equals(this.mConfigurations, streamConfigurationMap.mConfigurations) || !java.util.Arrays.equals(this.mMinFrameDurations, streamConfigurationMap.mMinFrameDurations) || !java.util.Arrays.equals(this.mStallDurations, streamConfigurationMap.mStallDurations) || !java.util.Arrays.equals(this.mDepthConfigurations, streamConfigurationMap.mDepthConfigurations) || !java.util.Arrays.equals(this.mDepthMinFrameDurations, streamConfigurationMap.mDepthMinFrameDurations) || !java.util.Arrays.equals(this.mDepthStallDurations, streamConfigurationMap.mDepthStallDurations) || !java.util.Arrays.equals(this.mDynamicDepthConfigurations, streamConfigurationMap.mDynamicDepthConfigurations) || !java.util.Arrays.equals(this.mDynamicDepthMinFrameDurations, streamConfigurationMap.mDynamicDepthMinFrameDurations) || !java.util.Arrays.equals(this.mDynamicDepthStallDurations, streamConfigurationMap.mDynamicDepthStallDurations) || !java.util.Arrays.equals(this.mHeicConfigurations, streamConfigurationMap.mHeicConfigurations) || !java.util.Arrays.equals(this.mHeicMinFrameDurations, streamConfigurationMap.mHeicMinFrameDurations) || !java.util.Arrays.equals(this.mHeicStallDurations, streamConfigurationMap.mHeicStallDurations) || !java.util.Arrays.equals(this.mJpegRConfigurations, streamConfigurationMap.mJpegRConfigurations) || !java.util.Arrays.equals(this.mJpegRMinFrameDurations, streamConfigurationMap.mJpegRMinFrameDurations) || !java.util.Arrays.equals(this.mJpegRStallDurations, streamConfigurationMap.mJpegRStallDurations) || !java.util.Arrays.equals(this.mHighSpeedVideoConfigurations, streamConfigurationMap.mHighSpeedVideoConfigurations)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCodeGeneric(this.mConfigurations, this.mMinFrameDurations, this.mStallDurations, this.mDepthConfigurations, this.mDepthMinFrameDurations, this.mDepthStallDurations, this.mDynamicDepthConfigurations, this.mDynamicDepthMinFrameDurations, this.mDynamicDepthStallDurations, this.mHeicConfigurations, this.mHeicMinFrameDurations, this.mHeicStallDurations, this.mJpegRConfigurations, this.mJpegRMinFrameDurations, this.mJpegRStallDurations, this.mHighSpeedVideoConfigurations);
    }

    private int checkArgumentFormatSupported(int i, boolean z) {
        checkArgumentFormat(i);
        int imageFormatToInternal = imageFormatToInternal(i);
        int imageFormatToDataspace = imageFormatToDataspace(i);
        if (z) {
            if (imageFormatToDataspace == 4096) {
                if (this.mDepthOutputFormats.indexOfKey(imageFormatToInternal) >= 0) {
                    return i;
                }
            } else if (imageFormatToDataspace == 4098) {
                if (this.mDynamicDepthOutputFormats.indexOfKey(imageFormatToInternal) >= 0) {
                    return i;
                }
            } else if (imageFormatToDataspace == 4100) {
                if (this.mHeicOutputFormats.indexOfKey(imageFormatToInternal) >= 0) {
                    return i;
                }
            } else if (imageFormatToDataspace == 4101) {
                if (this.mJpegROutputFormats.indexOfKey(imageFormatToInternal) >= 0) {
                    return i;
                }
            } else if (this.mAllOutputFormats.indexOfKey(imageFormatToInternal) >= 0) {
                return i;
            }
        } else if (this.mInputFormats.indexOfKey(imageFormatToInternal) >= 0) {
            return i;
        }
        throw new java.lang.IllegalArgumentException(java.lang.String.format("format %x is not supported by this stream configuration map", java.lang.Integer.valueOf(i)));
    }

    static int checkArgumentFormatInternal(int i) {
        switch (i) {
            case 33:
            case 34:
            case 36:
            case 540422489:
                return i;
            case 256:
            case android.graphics.ImageFormat.HEIC /* 1212500294 */:
                throw new java.lang.IllegalArgumentException("An unknown internal format: " + i);
            default:
                return checkArgumentFormat(i);
        }
    }

    static int checkArgumentFormat(int i) {
        if (!android.graphics.ImageFormat.isPublicFormat(i) && !android.graphics.PixelFormat.isPublicFormat(i)) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("format 0x%x was not defined in either ImageFormat or PixelFormat", java.lang.Integer.valueOf(i)));
        }
        return i;
    }

    public static int imageFormatToPublic(int i) {
        switch (i) {
            case 33:
                return 256;
            case 256:
                throw new java.lang.IllegalArgumentException("ImageFormat.JPEG is an unknown internal format");
            default:
                return i;
        }
    }

    public static int depthFormatToPublic(int i) {
        switch (i) {
            case 32:
                return 4098;
            case 33:
                return 257;
            case 34:
                throw new java.lang.IllegalArgumentException("IMPLEMENTATION_DEFINED must not leak to public API");
            case 37:
                return 4099;
            case 256:
                throw new java.lang.IllegalArgumentException("ImageFormat.JPEG is an unknown internal format");
            case 540422489:
                return android.graphics.ImageFormat.DEPTH16;
            default:
                throw new java.lang.IllegalArgumentException("Unknown DATASPACE_DEPTH format " + i);
        }
    }

    static int[] imageFormatToPublic(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = imageFormatToPublic(iArr[i]);
        }
        return iArr;
    }

    static int imageFormatToInternal(int i) {
        switch (i) {
            case 256:
            case 257:
            case 4101:
            case android.graphics.ImageFormat.HEIC /* 1212500294 */:
            case android.graphics.ImageFormat.DEPTH_JPEG /* 1768253795 */:
                return 33;
            case 4098:
                return 32;
            case 4099:
                return 37;
            case android.graphics.ImageFormat.DEPTH16 /* 1144402265 */:
                return 540422489;
            default:
                return i;
        }
    }

    static int imageFormatToDataspace(int i) {
        switch (i) {
            case 256:
                return 146931712;
            case 257:
            case 4098:
            case 4099:
            case android.graphics.ImageFormat.DEPTH16 /* 1144402265 */:
                return 4096;
            case 4101:
                return 4101;
            case android.graphics.ImageFormat.HEIC /* 1212500294 */:
                return 4100;
            case android.graphics.ImageFormat.DEPTH_JPEG /* 1768253795 */:
                return 4098;
            default:
                return 0;
        }
    }

    public static int[] imageFormatToInternal(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = imageFormatToInternal(iArr[i]);
        }
        return iArr;
    }

    private android.util.Size[] getPublicFormatSizes(int i, boolean z, boolean z2) {
        try {
            checkArgumentFormatSupported(i, z);
            return getInternalFormatSizes(imageFormatToInternal(i), imageFormatToDataspace(i), z, z2);
        } catch (java.lang.IllegalArgumentException e) {
            return null;
        }
    }

    private android.util.Size[] getInternalFormatSizes(int i, int i2, boolean z, boolean z2) {
        android.util.SparseIntArray sparseIntArray;
        android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr;
        android.hardware.camera2.params.StreamConfigurationDuration[] streamConfigurationDurationArr;
        char c;
        long j;
        android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap = this;
        int i3 = 0;
        char c2 = 4096;
        if (i2 == 4096 && z2) {
            return new android.util.Size[0];
        }
        if (!z) {
            sparseIntArray = streamConfigurationMap.mInputFormats;
        } else if (i2 == 4096) {
            sparseIntArray = streamConfigurationMap.mDepthOutputFormats;
        } else if (i2 == 4098) {
            sparseIntArray = streamConfigurationMap.mDynamicDepthOutputFormats;
        } else if (i2 == 4100) {
            sparseIntArray = streamConfigurationMap.mHeicOutputFormats;
        } else if (i2 == 4101) {
            sparseIntArray = streamConfigurationMap.mJpegROutputFormats;
        } else {
            sparseIntArray = z2 ? streamConfigurationMap.mHighResOutputFormats : streamConfigurationMap.mOutputFormats;
        }
        int i4 = sparseIntArray.get(i);
        if ((z && i2 != 4096 && i2 != 4101 && i2 != 4098 && i2 != 4100) || i4 != 0) {
            if (z && i2 != 4096 && i2 != 4101 && i2 != 4098 && i2 != 4100 && streamConfigurationMap.mAllOutputFormats.get(i) == 0) {
                return null;
            }
            android.util.Size[] sizeArr = new android.util.Size[i4];
            if (i2 == 4096) {
                streamConfigurationArr = streamConfigurationMap.mDepthConfigurations;
            } else if (i2 == 4098) {
                streamConfigurationArr = streamConfigurationMap.mDynamicDepthConfigurations;
            } else if (i2 == 4100) {
                streamConfigurationArr = streamConfigurationMap.mHeicConfigurations;
            } else {
                streamConfigurationArr = i2 == 4101 ? streamConfigurationMap.mJpegRConfigurations : streamConfigurationMap.mConfigurations;
            }
            if (i2 == 4096) {
                streamConfigurationDurationArr = streamConfigurationMap.mDepthMinFrameDurations;
            } else if (i2 == 4098) {
                streamConfigurationDurationArr = streamConfigurationMap.mDynamicDepthMinFrameDurations;
            } else if (i2 == 4100) {
                streamConfigurationDurationArr = streamConfigurationMap.mHeicMinFrameDurations;
            } else {
                streamConfigurationDurationArr = i2 == 4101 ? streamConfigurationMap.mJpegRMinFrameDurations : streamConfigurationMap.mMinFrameDurations;
            }
            int length = streamConfigurationArr.length;
            int i5 = 0;
            while (i5 < length) {
                android.hardware.camera2.params.StreamConfiguration streamConfiguration = streamConfigurationArr[i5];
                int format = streamConfiguration.getFormat();
                if (format != i || streamConfiguration.isOutput() != z) {
                    c = c2;
                } else {
                    if (!z || !streamConfigurationMap.mListHighResolution) {
                        c = c2;
                    } else {
                        int i6 = 0;
                        while (true) {
                            if (i6 >= streamConfigurationDurationArr.length) {
                                j = 0;
                                break;
                            }
                            android.hardware.camera2.params.StreamConfigurationDuration streamConfigurationDuration = streamConfigurationDurationArr[i6];
                            if (streamConfigurationDuration.getFormat() != format || streamConfigurationDuration.getWidth() != streamConfiguration.getSize().getWidth() || streamConfigurationDuration.getHeight() != streamConfiguration.getSize().getHeight()) {
                                i6++;
                            } else {
                                j = streamConfigurationDuration.getDuration();
                                break;
                            }
                        }
                        c = 4096;
                        if (i2 != 4096) {
                            if (z2 != (j > DURATION_20FPS_NS)) {
                            }
                        }
                    }
                    sizeArr[i3] = streamConfiguration.getSize();
                    i3++;
                }
                i5++;
                c2 = c;
                streamConfigurationMap = this;
            }
            if ((i3 != i4 && (i2 == 4098 || i2 == 4100)) || i2 == 4101) {
                if (i3 > i4) {
                    throw new java.lang.AssertionError("Too many dynamic depth sizes (expected " + i4 + ", actual " + i3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
                if (i3 <= 0) {
                    return new android.util.Size[0];
                }
                return (android.util.Size[]) java.util.Arrays.copyOf(sizeArr, i3);
            }
            if (i3 != i4) {
                throw new java.lang.AssertionError("Too few sizes (expected " + i4 + ", actual " + i3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            return sizeArr;
        }
        return null;
    }

    private int[] getPublicFormats(boolean z) {
        int publicFormatCount = getPublicFormatCount(z);
        int[] iArr = new int[publicFormatCount];
        android.util.SparseIntArray formatsMap = getFormatsMap(z);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < formatsMap.size()) {
            iArr[i3] = imageFormatToPublic(formatsMap.keyAt(i2));
            i2++;
            i3++;
        }
        if (z) {
            while (i < this.mDepthOutputFormats.size()) {
                iArr[i3] = depthFormatToPublic(this.mDepthOutputFormats.keyAt(i));
                i++;
                i3++;
            }
            if (this.mDynamicDepthOutputFormats.size() > 0) {
                iArr[i3] = 1768253795;
                i3++;
            }
            if (this.mHeicOutputFormats.size() > 0) {
                iArr[i3] = 1212500294;
                i3++;
            }
            if (this.mJpegROutputFormats.size() > 0) {
                iArr[i3] = 4101;
                i3++;
            }
        }
        if (publicFormatCount != i3) {
            throw new java.lang.AssertionError("Too few formats " + i3 + ", expected " + publicFormatCount);
        }
        return iArr;
    }

    private android.util.SparseIntArray getFormatsMap(boolean z) {
        return z ? this.mAllOutputFormats : this.mInputFormats;
    }

    private long getInternalFormatDuration(int i, int i2, android.util.Size size, int i3) {
        if (!isSupportedInternalConfiguration(i, i2, size)) {
            throw new java.lang.IllegalArgumentException("size was not supported");
        }
        for (android.hardware.camera2.params.StreamConfigurationDuration streamConfigurationDuration : getDurations(i3, i2)) {
            if (streamConfigurationDuration.getFormat() == i && streamConfigurationDuration.getWidth() == size.getWidth() && streamConfigurationDuration.getHeight() == size.getHeight()) {
                return streamConfigurationDuration.getDuration();
            }
        }
        return 0L;
    }

    private android.hardware.camera2.params.StreamConfigurationDuration[] getDurations(int i, int i2) {
        switch (i) {
            case 0:
                if (i2 == 4096) {
                    return this.mDepthMinFrameDurations;
                }
                if (i2 == 4098) {
                    return this.mDynamicDepthMinFrameDurations;
                }
                return i2 == 4100 ? this.mHeicMinFrameDurations : i2 == 4101 ? this.mJpegRMinFrameDurations : this.mMinFrameDurations;
            case 1:
                return i2 == 4096 ? this.mDepthStallDurations : i2 == 4098 ? this.mDynamicDepthStallDurations : i2 == 4100 ? this.mHeicStallDurations : i2 == 4101 ? this.mJpegRStallDurations : this.mStallDurations;
            default:
                throw new java.lang.IllegalArgumentException("duration was invalid");
        }
    }

    private int getPublicFormatCount(boolean z) {
        int size = getFormatsMap(z).size();
        if (z) {
            return size + this.mDepthOutputFormats.size() + this.mDynamicDepthOutputFormats.size() + this.mHeicOutputFormats.size() + this.mJpegROutputFormats.size();
        }
        return size;
    }

    private static <T> boolean arrayContains(T[] tArr, T t) {
        if (tArr == null) {
            return false;
        }
        for (T t2 : tArr) {
            if (java.util.Objects.equals(t2, t)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSupportedInternalConfiguration(int i, int i2, android.util.Size size) {
        android.hardware.camera2.params.StreamConfiguration[] streamConfigurationArr;
        if (i2 == 4096) {
            streamConfigurationArr = this.mDepthConfigurations;
        } else if (i2 == 4098) {
            streamConfigurationArr = this.mDynamicDepthConfigurations;
        } else if (i2 == 4100) {
            streamConfigurationArr = this.mHeicConfigurations;
        } else {
            streamConfigurationArr = i2 == 4101 ? this.mJpegRConfigurations : this.mConfigurations;
        }
        for (int i3 = 0; i3 < streamConfigurationArr.length; i3++) {
            if (streamConfigurationArr[i3].getFormat() == i && streamConfigurationArr[i3].getSize().equals(size)) {
                return true;
            }
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("StreamConfiguration(");
        appendOutputsString(sb);
        sb.append(", ");
        appendHighResOutputsString(sb);
        sb.append(", ");
        appendInputsString(sb);
        sb.append(", ");
        appendValidOutputFormatsForInputString(sb);
        sb.append(", ");
        appendHighSpeedVideoConfigurationsString(sb);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public static int compareSizes(int i, int i2, int i3, int i4) {
        long j = i;
        long j2 = i2 * j;
        long j3 = i3;
        long j4 = i4 * j3;
        if (j2 != j4) {
            j = j2;
            j3 = j4;
        }
        if (j < j3) {
            return -1;
        }
        return j > j3 ? 1 : 0;
    }

    private void appendOutputsString(java.lang.StringBuilder sb) {
        sb.append("Outputs(");
        for (int i : getOutputFormats()) {
            for (android.util.Size size : getOutputSizes(i)) {
                sb.append(java.lang.String.format("[w:%d, h:%d, format:%s(%d), min_duration:%d, stall:%d], ", java.lang.Integer.valueOf(size.getWidth()), java.lang.Integer.valueOf(size.getHeight()), formatToString(i), java.lang.Integer.valueOf(i), java.lang.Long.valueOf(getOutputMinFrameDuration(i, size)), java.lang.Long.valueOf(getOutputStallDuration(i, size))));
            }
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    private void appendHighResOutputsString(java.lang.StringBuilder sb) {
        sb.append("HighResolutionOutputs(");
        for (int i : getOutputFormats()) {
            android.util.Size[] highResolutionOutputSizes = getHighResolutionOutputSizes(i);
            if (highResolutionOutputSizes != null) {
                for (android.util.Size size : highResolutionOutputSizes) {
                    sb.append(java.lang.String.format("[w:%d, h:%d, format:%s(%d), min_duration:%d, stall:%d], ", java.lang.Integer.valueOf(size.getWidth()), java.lang.Integer.valueOf(size.getHeight()), formatToString(i), java.lang.Integer.valueOf(i), java.lang.Long.valueOf(getOutputMinFrameDuration(i, size)), java.lang.Long.valueOf(getOutputStallDuration(i, size))));
                }
            }
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    private void appendInputsString(java.lang.StringBuilder sb) {
        sb.append("Inputs(");
        for (int i : getInputFormats()) {
            for (android.util.Size size : getInputSizes(i)) {
                sb.append(java.lang.String.format("[w:%d, h:%d, format:%s(%d)], ", java.lang.Integer.valueOf(size.getWidth()), java.lang.Integer.valueOf(size.getHeight()), formatToString(i), java.lang.Integer.valueOf(i)));
            }
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    private void appendValidOutputFormatsForInputString(java.lang.StringBuilder sb) {
        sb.append("ValidOutputFormatsForInput(");
        for (int i : getInputFormats()) {
            sb.append(java.lang.String.format("[in:%s(%d), out:", formatToString(i), java.lang.Integer.valueOf(i)));
            int[] validOutputFormatsForInput = getValidOutputFormatsForInput(i);
            for (int i2 = 0; i2 < validOutputFormatsForInput.length; i2++) {
                sb.append(java.lang.String.format("%s(%d)", formatToString(validOutputFormatsForInput[i2]), java.lang.Integer.valueOf(validOutputFormatsForInput[i2])));
                if (i2 < validOutputFormatsForInput.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("], ");
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    private void appendHighSpeedVideoConfigurationsString(java.lang.StringBuilder sb) {
        sb.append("HighSpeedVideoConfigurations(");
        for (android.util.Size size : getHighSpeedVideoSizes()) {
            for (android.util.Range<java.lang.Integer> range : getHighSpeedVideoFpsRangesFor(size)) {
                sb.append(java.lang.String.format("[w:%d, h:%d, min_fps:%d, max_fps:%d], ", java.lang.Integer.valueOf(size.getWidth()), java.lang.Integer.valueOf(size.getHeight()), range.getLower(), range.getUpper()));
            }
        }
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    public static java.lang.String formatToString(int i) {
        switch (i) {
            case 1:
                return "RGBA_8888";
            case 2:
                return "RGBX_8888";
            case 3:
                return "RGB_888";
            case 4:
                return "RGB_565";
            case 16:
                return "NV16";
            case 17:
                return "NV21";
            case 20:
                return "YUY2";
            case 32:
                return "RAW_SENSOR";
            case 34:
                return "PRIVATE";
            case 35:
                return "YUV_420_888";
            case 36:
                return "RAW_PRIVATE";
            case 37:
                return "RAW10";
            case 256:
                return "JPEG";
            case 257:
                return "DEPTH_POINT_CLOUD";
            case 4098:
                return "RAW_DEPTH";
            case 4099:
                return "RAW_DEPTH10";
            case 4101:
                return "JPEG/R";
            case 538982489:
                return "Y8";
            case 540422489:
                return "Y16";
            case 842094169:
                return "YV12";
            case android.graphics.ImageFormat.DEPTH16 /* 1144402265 */:
                return "DEPTH16";
            case android.graphics.ImageFormat.HEIC /* 1212500294 */:
                return "HEIC";
            case android.graphics.ImageFormat.DEPTH_JPEG /* 1768253795 */:
                return "DEPTH_JPEG";
            default:
                return "UNKNOWN";
        }
    }
}
