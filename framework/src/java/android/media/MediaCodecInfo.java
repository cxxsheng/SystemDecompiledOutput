package android.media;

/* loaded from: classes2.dex */
public final class MediaCodecInfo {
    private static final int DEFAULT_MAX_SUPPORTED_INSTANCES = 32;
    private static final int ERROR_NONE_SUPPORTED = 4;
    private static final int ERROR_UNRECOGNIZED = 1;
    private static final int ERROR_UNSUPPORTED = 2;
    private static final int FLAG_IS_ENCODER = 1;
    private static final int FLAG_IS_HARDWARE_ACCELERATED = 8;
    private static final int FLAG_IS_SOFTWARE_ONLY = 4;
    private static final int FLAG_IS_VENDOR = 2;
    private static final int MAX_SUPPORTED_INSTANCES_LIMIT = 256;
    public static final int SECURITY_MODEL_MEMORY_SAFE = 1;
    public static final int SECURITY_MODEL_SANDBOXED = 0;
    public static final int SECURITY_MODEL_TRUSTED_CONTENT_ONLY = 2;
    private static final java.lang.String TAG = "MediaCodecInfo";
    private java.lang.String mCanonicalName;
    private java.util.Map<java.lang.String, android.media.MediaCodecInfo.CodecCapabilities> mCaps = new java.util.HashMap();
    private int mFlags;
    private java.lang.String mName;
    private static final android.util.Range<java.lang.Integer> POSITIVE_INTEGERS = android.util.Range.create(1, Integer.MAX_VALUE);
    private static final android.util.Range<java.lang.Long> POSITIVE_LONGS = android.util.Range.create(1L, Long.MAX_VALUE);
    private static final android.util.Range<android.util.Rational> POSITIVE_RATIONALS = android.util.Range.create(new android.util.Rational(1, Integer.MAX_VALUE), new android.util.Rational(Integer.MAX_VALUE, 1));
    private static final android.util.Range<java.lang.Integer> FRAME_RATE_RANGE = android.util.Range.create(0, 960);
    private static final android.util.Range<java.lang.Integer> BITRATE_RANGE = android.util.Range.create(0, 500000000);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SecurityModel {
    }

    MediaCodecInfo(java.lang.String str, java.lang.String str2, int i, android.media.MediaCodecInfo.CodecCapabilities[] codecCapabilitiesArr) {
        this.mName = str;
        this.mCanonicalName = str2;
        this.mFlags = i;
        for (android.media.MediaCodecInfo.CodecCapabilities codecCapabilities : codecCapabilitiesArr) {
            this.mCaps.put(codecCapabilities.getMimeType(), codecCapabilities);
        }
    }

    public final java.lang.String getName() {
        return this.mName;
    }

    public final java.lang.String getCanonicalName() {
        return this.mCanonicalName;
    }

    public final boolean isAlias() {
        return !this.mName.equals(this.mCanonicalName);
    }

    public final boolean isEncoder() {
        return (this.mFlags & 1) != 0;
    }

    public final boolean isVendor() {
        return (this.mFlags & 2) != 0;
    }

    public final boolean isSoftwareOnly() {
        return (this.mFlags & 4) != 0;
    }

    public final boolean isHardwareAccelerated() {
        return (this.mFlags & 8) != 0;
    }

    public final java.lang.String[] getSupportedTypes() {
        java.util.Set<java.lang.String> keySet = this.mCaps.keySet();
        java.lang.String[] strArr = (java.lang.String[]) keySet.toArray(new java.lang.String[keySet.size()]);
        java.util.Arrays.sort(strArr);
        return strArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkPowerOfTwo(int i, java.lang.String str) {
        if (((i - 1) & i) != 0) {
            throw new java.lang.IllegalArgumentException(str);
        }
        return i;
    }

    private static class Feature {
        public boolean mDefault;
        public boolean mInternal;
        public java.lang.String mName;
        public int mValue;

        public Feature(java.lang.String str, int i, boolean z) {
            this(str, i, z, false);
        }

        public Feature(java.lang.String str, int i, boolean z, boolean z2) {
            this.mName = str;
            this.mValue = i;
            this.mDefault = z;
            this.mInternal = z2;
        }
    }

    private static final class LazyHolder {
        private static final android.util.Range<java.lang.Integer> SIZE_RANGE;

        private LazyHolder() {
        }

        static {
            SIZE_RANGE = android.os.Process.is64Bit() ? android.util.Range.create(1, 32768) : android.util.Range.create(1, android.sysprop.MediaProperties.resolution_limit_32bit().orElse(4096));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.Range<java.lang.Integer> getSizeRange() {
        return android.media.MediaCodecInfo.LazyHolder.SIZE_RANGE;
    }

    public static final class CodecCapabilities {
        public static final int COLOR_Format12bitRGB444 = 3;
        public static final int COLOR_Format16bitARGB1555 = 5;
        public static final int COLOR_Format16bitARGB4444 = 4;
        public static final int COLOR_Format16bitBGR565 = 7;
        public static final int COLOR_Format16bitRGB565 = 6;
        public static final int COLOR_Format18BitBGR666 = 41;
        public static final int COLOR_Format18bitARGB1665 = 9;
        public static final int COLOR_Format18bitRGB666 = 8;
        public static final int COLOR_Format19bitARGB1666 = 10;
        public static final int COLOR_Format24BitABGR6666 = 43;
        public static final int COLOR_Format24BitARGB6666 = 42;
        public static final int COLOR_Format24bitARGB1887 = 13;
        public static final int COLOR_Format24bitBGR888 = 12;
        public static final int COLOR_Format24bitRGB888 = 11;
        public static final int COLOR_Format25bitARGB1888 = 14;
        public static final int COLOR_Format32bitABGR2101010 = 2130750114;
        public static final int COLOR_Format32bitABGR8888 = 2130747392;
        public static final int COLOR_Format32bitARGB8888 = 16;
        public static final int COLOR_Format32bitBGRA8888 = 15;
        public static final int COLOR_Format64bitABGRFloat = 2130710294;
        public static final int COLOR_Format8bitRGB332 = 2;
        public static final int COLOR_FormatCbYCrY = 27;
        public static final int COLOR_FormatCrYCbY = 28;
        public static final int COLOR_FormatL16 = 36;
        public static final int COLOR_FormatL2 = 33;
        public static final int COLOR_FormatL24 = 37;
        public static final int COLOR_FormatL32 = 38;
        public static final int COLOR_FormatL4 = 34;
        public static final int COLOR_FormatL8 = 35;
        public static final int COLOR_FormatMonochrome = 1;
        public static final int COLOR_FormatRGBAFlexible = 2134288520;
        public static final int COLOR_FormatRGBFlexible = 2134292616;
        public static final int COLOR_FormatRawBayer10bit = 31;
        public static final int COLOR_FormatRawBayer8bit = 30;
        public static final int COLOR_FormatRawBayer8bitcompressed = 32;
        public static final int COLOR_FormatSurface = 2130708361;
        public static final int COLOR_FormatYCbYCr = 25;
        public static final int COLOR_FormatYCrYCb = 26;
        public static final int COLOR_FormatYUV411PackedPlanar = 18;
        public static final int COLOR_FormatYUV411Planar = 17;
        public static final int COLOR_FormatYUV420Flexible = 2135033992;
        public static final int COLOR_FormatYUV420PackedPlanar = 20;
        public static final int COLOR_FormatYUV420PackedSemiPlanar = 39;
        public static final int COLOR_FormatYUV420Planar = 19;
        public static final int COLOR_FormatYUV420SemiPlanar = 21;
        public static final int COLOR_FormatYUV422Flexible = 2135042184;
        public static final int COLOR_FormatYUV422PackedPlanar = 23;
        public static final int COLOR_FormatYUV422PackedSemiPlanar = 40;
        public static final int COLOR_FormatYUV422Planar = 22;
        public static final int COLOR_FormatYUV422SemiPlanar = 24;
        public static final int COLOR_FormatYUV444Flexible = 2135181448;
        public static final int COLOR_FormatYUV444Interleaved = 29;
        public static final int COLOR_FormatYUVP010 = 54;
        public static final int COLOR_QCOM_FormatYUV420SemiPlanar = 2141391872;
        public static final int COLOR_TI_FormatYUV420PackedSemiPlanar = 2130706688;
        public static final java.lang.String FEATURE_AdaptivePlayback = "adaptive-playback";
        public static final java.lang.String FEATURE_DetachedSurface = "detached-surface";
        public static final java.lang.String FEATURE_DynamicColorAspects = "dynamic-color-aspects";
        public static final java.lang.String FEATURE_DynamicTimestamp = "dynamic-timestamp";
        public static final java.lang.String FEATURE_EncodingStatistics = "encoding-statistics";
        public static final java.lang.String FEATURE_FrameParsing = "frame-parsing";
        public static final java.lang.String FEATURE_HdrEditing = "hdr-editing";
        public static final java.lang.String FEATURE_HlgEditing = "hlg-editing";
        public static final java.lang.String FEATURE_IntraRefresh = "intra-refresh";
        public static final java.lang.String FEATURE_LowLatency = "low-latency";
        public static final java.lang.String FEATURE_MultipleFrames = "multiple-frames";
        public static final java.lang.String FEATURE_PartialFrame = "partial-frame";
        public static final java.lang.String FEATURE_QpBounds = "qp-bounds";
        public static final java.lang.String FEATURE_Roi = "region-of-interest";
        public static final java.lang.String FEATURE_SecurePlayback = "secure-playback";
        private static final java.lang.String FEATURE_SpecialCodec = "special-codec";
        public static final java.lang.String FEATURE_TunneledPlayback = "tunneled-playback";
        private static final java.lang.String TAG = "CodecCapabilities";
        public int[] colorFormats;
        private android.media.MediaCodecInfo.AudioCapabilities mAudioCaps;
        private android.media.MediaFormat mCapabilitiesInfo;
        private android.media.MediaFormat mDefaultFormat;
        private android.media.MediaCodecInfo.EncoderCapabilities mEncoderCaps;
        int mError;
        private int mFlagsRequired;
        private int mFlagsSupported;
        private int mFlagsVerified;
        private int mMaxSupportedInstances;
        private java.lang.String mMime;
        private android.media.MediaCodecInfo.VideoCapabilities mVideoCaps;
        public android.media.MediaCodecInfo.CodecProfileLevel[] profileLevels;

        public CodecCapabilities() {
        }

        public final boolean isFeatureSupported(java.lang.String str) {
            return checkFeature(str, this.mFlagsSupported);
        }

        public final boolean isFeatureRequired(java.lang.String str) {
            return checkFeature(str, this.mFlagsRequired);
        }

        private static class FeatureList {
            private static android.media.MediaCodecInfo.Feature[] decoderFeatures = getDecoderFeatures();
            private static android.media.MediaCodecInfo.Feature[] encoderFeatures = getEncoderFeatures();

            private FeatureList() {
            }

            private static android.media.MediaCodecInfo.Feature[] getDecoderFeatures() {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_AdaptivePlayback, 1, true));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_SecurePlayback, 2, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_TunneledPlayback, 4, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_PartialFrame, 8, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_FrameParsing, 16, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_MultipleFrames, 32, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_DynamicTimestamp, 64, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature("low-latency", 128, true));
                if (android.media.codec.Flags.dynamicColorAspects()) {
                    arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_DynamicColorAspects, 256, true));
                }
                if (android.media.codec.Flags.nullOutputSurface()) {
                    arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_DetachedSurface, 512, true));
                }
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_SpecialCodec, 1073741824, false, true));
                return (android.media.MediaCodecInfo.Feature[]) arrayList.toArray(new android.media.MediaCodecInfo.Feature[0]);
            }

            private static android.media.MediaCodecInfo.Feature[] getEncoderFeatures() {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_IntraRefresh, 1, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_MultipleFrames, 2, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_DynamicTimestamp, 4, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_QpBounds, 8, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_EncodingStatistics, 16, false));
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_HdrEditing, 32, false));
                if (android.media.codec.Flags.hlgEditing()) {
                    arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_HlgEditing, 64, true));
                }
                if (android.media.codec.Flags.regionOfInterest()) {
                    arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_Roi, 128, true));
                }
                arrayList.add(new android.media.MediaCodecInfo.Feature(android.media.MediaCodecInfo.CodecCapabilities.FEATURE_SpecialCodec, 1073741824, false, true));
                return (android.media.MediaCodecInfo.Feature[]) arrayList.toArray(new android.media.MediaCodecInfo.Feature[0]);
            }

            public static android.media.MediaCodecInfo.Feature[] getFeatures(boolean z) {
                if (z) {
                    return encoderFeatures;
                }
                return decoderFeatures;
            }
        }

        public java.lang.String[] validFeatures() {
            android.media.MediaCodecInfo.Feature[] validFeatures = getValidFeatures();
            int length = validFeatures.length;
            java.lang.String[] strArr = new java.lang.String[length];
            for (int i = 0; i < length; i++) {
                if (!validFeatures[i].mInternal) {
                    strArr[i] = validFeatures[i].mName;
                }
            }
            return strArr;
        }

        private android.media.MediaCodecInfo.Feature[] getValidFeatures() {
            return android.media.MediaCodecInfo.CodecCapabilities.FeatureList.getFeatures(isEncoder());
        }

        private boolean checkFeature(java.lang.String str, int i) {
            for (android.media.MediaCodecInfo.Feature feature : getValidFeatures()) {
                if (feature.mName.equals(str)) {
                    return (feature.mValue & i) != 0;
                }
            }
            return false;
        }

        public boolean isRegular() {
            for (android.media.MediaCodecInfo.Feature feature : getValidFeatures()) {
                if (!feature.mDefault && isFeatureRequired(feature.mName)) {
                    return false;
                }
            }
            return true;
        }

        public final boolean isFormatSupported(android.media.MediaFormat mediaFormat) {
            java.util.Set<java.lang.String> set;
            java.lang.Integer num;
            java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat.getMap();
            java.lang.String str = (java.lang.String) map.get(android.media.MediaFormat.KEY_MIME);
            if (str != null && !this.mMime.equalsIgnoreCase(str)) {
                return false;
            }
            for (android.media.MediaCodecInfo.Feature feature : getValidFeatures()) {
                if (!feature.mInternal && (num = (java.lang.Integer) map.get(android.media.MediaFormat.KEY_FEATURE_ + feature.mName)) != null && ((num.intValue() == 1 && !isFeatureSupported(feature.mName)) || (num.intValue() == 0 && isFeatureRequired(feature.mName)))) {
                    return false;
                }
            }
            java.lang.Integer num2 = (java.lang.Integer) map.get(android.media.MediaFormat.KEY_PROFILE);
            java.lang.Integer num3 = (java.lang.Integer) map.get("level");
            if (num2 != null) {
                if (!supportsProfileLevel(num2.intValue(), num3)) {
                    return false;
                }
                int i = 0;
                for (android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel : this.profileLevels) {
                    if (codecProfileLevel.profile == num2.intValue() && codecProfileLevel.level > i && (!this.mMime.equalsIgnoreCase("video/3gpp") || codecProfileLevel.level != 16 || i == 1)) {
                        i = codecProfileLevel.level;
                    }
                }
                android.media.MediaCodecInfo.CodecCapabilities createFromProfileLevel = createFromProfileLevel(this.mMime, num2.intValue(), i);
                java.util.HashMap hashMap = new java.util.HashMap(map);
                if (isVideo()) {
                    set = android.media.MediaCodecInfo.VideoCapabilities.VIDEO_LEVEL_CRITICAL_FORMAT_KEYS;
                } else {
                    set = isAudio() ? android.media.MediaCodecInfo.AudioCapabilities.AUDIO_LEVEL_CRITICAL_FORMAT_KEYS : null;
                }
                if (set != null && set.size() > 1 && createFromProfileLevel != null) {
                    hashMap.keySet().retainAll(set);
                    if (!createFromProfileLevel.isFormatSupported(new android.media.MediaFormat(hashMap))) {
                        return false;
                    }
                }
            }
            if (this.mAudioCaps != null && !this.mAudioCaps.supportsFormat(mediaFormat)) {
                return false;
            }
            if (this.mVideoCaps == null || this.mVideoCaps.supportsFormat(mediaFormat)) {
                return this.mEncoderCaps == null || this.mEncoderCaps.supportsFormat(mediaFormat);
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean supportsBitrate(android.util.Range<java.lang.Integer> range, android.media.MediaFormat mediaFormat) {
            java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat.getMap();
            java.lang.Integer num = (java.lang.Integer) map.get(android.media.MediaFormat.KEY_MAX_BIT_RATE);
            java.lang.Integer num2 = (java.lang.Integer) map.get(android.media.MediaFormat.KEY_BIT_RATE);
            if (num2 != null) {
                if (num == null) {
                    num = num2;
                } else {
                    num = java.lang.Integer.valueOf(java.lang.Math.max(num2.intValue(), num.intValue()));
                }
            }
            if (num != null && num.intValue() > 0) {
                return range.contains((android.util.Range<java.lang.Integer>) num);
            }
            return true;
        }

        private boolean supportsProfileLevel(int i, java.lang.Integer num) {
            for (android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel : this.profileLevels) {
                if (codecProfileLevel.profile == i) {
                    if (num == null || this.mMime.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_AAC) || this.mMime.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_DTS) || this.mMime.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_DTS_HD) || this.mMime.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_DTS_UHD)) {
                        return true;
                    }
                    if ((!this.mMime.equalsIgnoreCase("video/3gpp") || codecProfileLevel.level == num.intValue() || codecProfileLevel.level != 16 || num.intValue() <= 1) && (!this.mMime.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_MPEG4) || codecProfileLevel.level == num.intValue() || codecProfileLevel.level != 4 || num.intValue() <= 1)) {
                        if (this.mMime.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_HEVC)) {
                            boolean z = (codecProfileLevel.level & 44739242) != 0;
                            if (((44739242 & num.intValue()) != 0) && !z) {
                            }
                        }
                        if (codecProfileLevel.level >= num.intValue()) {
                            return createFromProfileLevel(this.mMime, i, codecProfileLevel.level) == null || createFromProfileLevel(this.mMime, i, num.intValue()) != null;
                        }
                    }
                }
            }
            return false;
        }

        public android.media.MediaFormat getDefaultFormat() {
            return this.mDefaultFormat;
        }

        public java.lang.String getMimeType() {
            return this.mMime;
        }

        public int getMaxSupportedInstances() {
            return this.mMaxSupportedInstances;
        }

        private boolean isAudio() {
            return this.mAudioCaps != null;
        }

        public android.media.MediaCodecInfo.AudioCapabilities getAudioCapabilities() {
            return this.mAudioCaps;
        }

        private boolean isEncoder() {
            return this.mEncoderCaps != null;
        }

        public android.media.MediaCodecInfo.EncoderCapabilities getEncoderCapabilities() {
            return this.mEncoderCaps;
        }

        private boolean isVideo() {
            return this.mVideoCaps != null;
        }

        public android.media.MediaCodecInfo.VideoCapabilities getVideoCapabilities() {
            return this.mVideoCaps;
        }

        public android.media.MediaCodecInfo.CodecCapabilities dup() {
            android.media.MediaCodecInfo.CodecCapabilities codecCapabilities = new android.media.MediaCodecInfo.CodecCapabilities();
            codecCapabilities.profileLevels = (android.media.MediaCodecInfo.CodecProfileLevel[]) java.util.Arrays.copyOf(this.profileLevels, this.profileLevels.length);
            codecCapabilities.colorFormats = java.util.Arrays.copyOf(this.colorFormats, this.colorFormats.length);
            codecCapabilities.mMime = this.mMime;
            codecCapabilities.mMaxSupportedInstances = this.mMaxSupportedInstances;
            codecCapabilities.mFlagsRequired = this.mFlagsRequired;
            codecCapabilities.mFlagsSupported = this.mFlagsSupported;
            codecCapabilities.mFlagsVerified = this.mFlagsVerified;
            codecCapabilities.mAudioCaps = this.mAudioCaps;
            codecCapabilities.mVideoCaps = this.mVideoCaps;
            codecCapabilities.mEncoderCaps = this.mEncoderCaps;
            codecCapabilities.mDefaultFormat = this.mDefaultFormat;
            codecCapabilities.mCapabilitiesInfo = this.mCapabilitiesInfo;
            return codecCapabilities;
        }

        public static android.media.MediaCodecInfo.CodecCapabilities createFromProfileLevel(java.lang.String str, int i, int i2) {
            android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel = new android.media.MediaCodecInfo.CodecProfileLevel();
            codecProfileLevel.profile = i;
            codecProfileLevel.level = i2;
            android.media.MediaFormat mediaFormat = new android.media.MediaFormat();
            mediaFormat.setString(android.media.MediaFormat.KEY_MIME, str);
            android.media.MediaCodecInfo.CodecCapabilities codecCapabilities = new android.media.MediaCodecInfo.CodecCapabilities(new android.media.MediaCodecInfo.CodecProfileLevel[]{codecProfileLevel}, new int[0], true, mediaFormat, new android.media.MediaFormat());
            if (codecCapabilities.mError != 0) {
                return null;
            }
            return codecCapabilities;
        }

        CodecCapabilities(android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr, int[] iArr, boolean z, java.util.Map<java.lang.String, java.lang.Object> map, java.util.Map<java.lang.String, java.lang.Object> map2) {
            this(codecProfileLevelArr, iArr, z, new android.media.MediaFormat(map), new android.media.MediaFormat(map2));
        }

        CodecCapabilities(android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr, int[] iArr, boolean z, android.media.MediaFormat mediaFormat, android.media.MediaFormat mediaFormat2) {
            java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat2.getMap();
            this.colorFormats = iArr;
            this.mFlagsVerified = 0;
            this.mDefaultFormat = mediaFormat;
            this.mCapabilitiesInfo = mediaFormat2;
            this.mMime = this.mDefaultFormat.getString(android.media.MediaFormat.KEY_MIME);
            if (codecProfileLevelArr.length == 0 && this.mMime.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_VP9)) {
                android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel = new android.media.MediaCodecInfo.CodecProfileLevel();
                codecProfileLevel.profile = 1;
                codecProfileLevel.level = android.media.MediaCodecInfo.VideoCapabilities.equivalentVP9Level(mediaFormat2);
                codecProfileLevelArr = new android.media.MediaCodecInfo.CodecProfileLevel[]{codecProfileLevel};
            }
            this.profileLevels = codecProfileLevelArr;
            if (this.mMime.toLowerCase().startsWith("audio/")) {
                this.mAudioCaps = android.media.MediaCodecInfo.AudioCapabilities.create(mediaFormat2, this);
                this.mAudioCaps.getDefaultFormat(this.mDefaultFormat);
            } else if (this.mMime.toLowerCase().startsWith("video/") || this.mMime.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_IMAGE_ANDROID_HEIC)) {
                this.mVideoCaps = android.media.MediaCodecInfo.VideoCapabilities.create(mediaFormat2, this);
            }
            if (z) {
                this.mEncoderCaps = android.media.MediaCodecInfo.EncoderCapabilities.create(mediaFormat2, this);
                this.mEncoderCaps.getDefaultFormat(this.mDefaultFormat);
            }
            this.mMaxSupportedInstances = android.media.Utils.parseIntSafely(android.media.MediaCodecList.getGlobalSettings().get("max-concurrent-instances"), 32);
            this.mMaxSupportedInstances = ((java.lang.Integer) android.util.Range.create(1, 256).clamp(java.lang.Integer.valueOf(android.media.Utils.parseIntSafely(map.get("max-concurrent-instances"), this.mMaxSupportedInstances)))).intValue();
            for (android.media.MediaCodecInfo.Feature feature : getValidFeatures()) {
                java.lang.String str = android.media.MediaFormat.KEY_FEATURE_ + feature.mName;
                java.lang.Integer num = (java.lang.Integer) map.get(str);
                if (num != null) {
                    if (num.intValue() > 0) {
                        this.mFlagsRequired |= feature.mValue;
                    }
                    this.mFlagsSupported |= feature.mValue;
                    if (!feature.mInternal) {
                        this.mDefaultFormat.setInteger(str, 1);
                    }
                }
            }
        }
    }

    public static final class AudioCapabilities {
        static final java.util.Set<java.lang.String> AUDIO_LEVEL_CRITICAL_FORMAT_KEYS = java.util.Set.of(android.media.MediaFormat.KEY_MIME);
        private static final int MAX_INPUT_CHANNEL_COUNT = 30;
        private static final java.lang.String TAG = "AudioCapabilities";
        private android.util.Range<java.lang.Integer> mBitrateRange;
        private android.util.Range<java.lang.Integer>[] mInputChannelRanges;
        private android.media.MediaCodecInfo.CodecCapabilities mParent;
        private android.util.Range<java.lang.Integer>[] mSampleRateRanges;
        private int[] mSampleRates;

        public android.util.Range<java.lang.Integer> getBitrateRange() {
            return this.mBitrateRange;
        }

        public int[] getSupportedSampleRates() {
            if (this.mSampleRates != null) {
                return java.util.Arrays.copyOf(this.mSampleRates, this.mSampleRates.length);
            }
            return null;
        }

        public android.util.Range<java.lang.Integer>[] getSupportedSampleRateRanges() {
            return (android.util.Range[]) java.util.Arrays.copyOf(this.mSampleRateRanges, this.mSampleRateRanges.length);
        }

        public int getMaxInputChannelCount() {
            int i = 0;
            for (int length = this.mInputChannelRanges.length - 1; length >= 0; length--) {
                int intValue = this.mInputChannelRanges[length].getUpper().intValue();
                if (intValue > i) {
                    i = intValue;
                }
            }
            return i;
        }

        public int getMinInputChannelCount() {
            int i = 30;
            for (int length = this.mInputChannelRanges.length - 1; length >= 0; length--) {
                int intValue = this.mInputChannelRanges[length].getLower().intValue();
                if (intValue < i) {
                    i = intValue;
                }
            }
            return i;
        }

        public android.util.Range<java.lang.Integer>[] getInputChannelCountRanges() {
            return (android.util.Range[]) java.util.Arrays.copyOf(this.mInputChannelRanges, this.mInputChannelRanges.length);
        }

        private AudioCapabilities() {
        }

        public static android.media.MediaCodecInfo.AudioCapabilities create(android.media.MediaFormat mediaFormat, android.media.MediaCodecInfo.CodecCapabilities codecCapabilities) {
            android.media.MediaCodecInfo.AudioCapabilities audioCapabilities = new android.media.MediaCodecInfo.AudioCapabilities();
            audioCapabilities.init(mediaFormat, codecCapabilities);
            return audioCapabilities;
        }

        private void init(android.media.MediaFormat mediaFormat, android.media.MediaCodecInfo.CodecCapabilities codecCapabilities) {
            this.mParent = codecCapabilities;
            initWithPlatformLimits();
            applyLevelLimits();
            parseFromInfo(mediaFormat);
        }

        private void initWithPlatformLimits() {
            this.mBitrateRange = android.util.Range.create(0, Integer.MAX_VALUE);
            this.mInputChannelRanges = new android.util.Range[]{android.util.Range.create(1, 30)};
            this.mSampleRateRanges = new android.util.Range[]{android.util.Range.create(java.lang.Integer.valueOf(android.os.SystemProperties.getInt("ro.mediacodec.min_sample_rate", 7350)), java.lang.Integer.valueOf(android.os.SystemProperties.getInt("ro.mediacodec.max_sample_rate", 192000)))};
            this.mSampleRates = null;
        }

        private boolean supports(java.lang.Integer num, java.lang.Integer num2) {
            if (num2 != null && android.media.Utils.binarySearchDistinctRanges(this.mInputChannelRanges, num2) < 0) {
                return false;
            }
            if (num != null && android.media.Utils.binarySearchDistinctRanges(this.mSampleRateRanges, num) < 0) {
                return false;
            }
            return true;
        }

        public boolean isSampleRateSupported(int i) {
            return supports(java.lang.Integer.valueOf(i), null);
        }

        private void limitSampleRates(int[] iArr) {
            java.util.Arrays.sort(iArr);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i : iArr) {
                if (supports(java.lang.Integer.valueOf(i), null)) {
                    arrayList.add(android.util.Range.create(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i)));
                }
            }
            this.mSampleRateRanges = (android.util.Range[]) arrayList.toArray(new android.util.Range[arrayList.size()]);
            createDiscreteSampleRates();
        }

        private void createDiscreteSampleRates() {
            this.mSampleRates = new int[this.mSampleRateRanges.length];
            for (int i = 0; i < this.mSampleRateRanges.length; i++) {
                this.mSampleRates[i] = this.mSampleRateRanges[i].getLower().intValue();
            }
        }

        private void limitSampleRates(android.util.Range<java.lang.Integer>[] rangeArr) {
            android.media.Utils.sortDistinctRanges(rangeArr);
            this.mSampleRateRanges = android.media.Utils.intersectSortedDistinctRanges(this.mSampleRateRanges, rangeArr);
            for (android.util.Range<java.lang.Integer> range : this.mSampleRateRanges) {
                if (!range.getLower().equals(range.getUpper())) {
                    this.mSampleRates = null;
                    return;
                }
            }
            createDiscreteSampleRates();
        }

        private void applyLevelLimits() {
            int i;
            int[] iArr;
            android.util.Range<java.lang.Integer> create;
            int[] iArr2;
            android.util.Range<java.lang.Integer> create2;
            android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr = this.mParent.profileLevels;
            java.lang.String mimeType = this.mParent.getMimeType();
            boolean equalsIgnoreCase = mimeType.equalsIgnoreCase("audio/mpeg");
            int i2 = 22050;
            int i3 = 24000;
            java.lang.Integer valueOf = java.lang.Integer.valueOf(com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION);
            android.util.Range<java.lang.Integer> range = null;
            if (equalsIgnoreCase) {
                iArr = new int[]{com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000};
                create = android.util.Range.create(valueOf, 320000);
                i = 2;
            } else if (mimeType.equalsIgnoreCase("audio/3gpp")) {
                iArr = new int[]{com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION};
                create = android.util.Range.create(4750, 12200);
                i = 1;
            } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_AMR_WB)) {
                iArr = new int[]{16000};
                create = android.util.Range.create(6600, 23850);
                i = 1;
            } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_AAC)) {
                iArr = new int[]{7350, com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000, 64000, 88200, 96000};
                create = android.util.Range.create(valueOf, 510000);
                i = 48;
            } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_VORBIS)) {
                android.util.Range<java.lang.Integer> create3 = android.util.Range.create(32000, 500000);
                android.util.Range<java.lang.Integer> create4 = android.util.Range.create(valueOf, 192000);
                i = 255;
                create = create3;
                iArr = null;
                range = create4;
            } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_OPUS)) {
                i = 255;
                create = android.util.Range.create(6000, 510000);
                iArr = new int[]{com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION, 12000, 16000, 24000, 48000};
            } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_RAW)) {
                android.util.Range<java.lang.Integer> create5 = android.util.Range.create(1, 192000);
                create = android.util.Range.create(1, 10000000);
                i = android.media.AudioSystem.OUT_CHANNEL_COUNT_MAX;
                range = create5;
                iArr = null;
            } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_FLAC)) {
                i = 255;
                create = null;
                range = android.util.Range.create(1, 655350);
                iArr = null;
            } else {
                i = 30;
                if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_G711_ALAW) || mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_G711_MLAW)) {
                    iArr = new int[]{com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION};
                    create = android.util.Range.create(64000, 64000);
                } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_MSGSM)) {
                    iArr = new int[]{com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION};
                    create = android.util.Range.create(13000, 13000);
                    i = 1;
                } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_AC3)) {
                    iArr = null;
                    create = null;
                    i = 6;
                } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_EAC3)) {
                    i = 16;
                    iArr = null;
                    create = null;
                } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_EAC3_JOC)) {
                    iArr = new int[]{48000};
                    create = android.util.Range.create(32000, 6144000);
                    i = 16;
                } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_AC4)) {
                    iArr = new int[]{44100, 48000, 96000, 192000};
                    create = android.util.Range.create(16000, 2688000);
                    i = 24;
                } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_DTS)) {
                    iArr = new int[]{44100, 48000};
                    create = android.util.Range.create(96000, 1524000);
                    i = 6;
                } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_DTS_HD)) {
                    int length = codecProfileLevelArr.length;
                    int i4 = 0;
                    int[] iArr3 = null;
                    android.util.Range<java.lang.Integer> range2 = null;
                    while (i4 < length) {
                        android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel = codecProfileLevelArr[i4];
                        switch (codecProfileLevel.profile) {
                            case 1:
                            case 4:
                                iArr2 = new int[]{44100, 48000, 88200, 96000, 176400, 192000};
                                create2 = android.util.Range.create(96000, 24500000);
                                break;
                            case 2:
                                iArr2 = new int[]{i2, i3, 44100, 48000};
                                create2 = android.util.Range.create(32000, 768000);
                                break;
                            case 3:
                            default:
                                android.util.Log.w(TAG, "Unrecognized profile " + codecProfileLevel.profile + " for " + mimeType);
                                this.mParent.mError |= 1;
                                iArr2 = new int[]{44100, 48000, 88200, 96000, 176400, 192000};
                                create2 = android.util.Range.create(96000, 24500000);
                                break;
                        }
                        range2 = create2;
                        iArr3 = iArr2;
                        i4++;
                        i2 = 22050;
                        i3 = 24000;
                    }
                    iArr = iArr3;
                    create = range2;
                    i = 8;
                } else if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_DTS_UHD)) {
                    int[] iArr4 = null;
                    android.util.Range<java.lang.Integer> range3 = null;
                    for (android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel2 : codecProfileLevelArr) {
                        switch (codecProfileLevel2.profile) {
                            case 1:
                                iArr4 = new int[]{44100, 48000, 88200, 96000, 176400, 192000};
                                range3 = android.util.Range.create(96000, 24500000);
                                i = 32;
                                break;
                            case 2:
                                iArr4 = new int[]{48000};
                                i = 10;
                                range3 = android.util.Range.create(96000, 768000);
                                break;
                            default:
                                android.util.Log.w(TAG, "Unrecognized profile " + codecProfileLevel2.profile + " for " + mimeType);
                                this.mParent.mError |= 1;
                                iArr4 = new int[]{44100, 48000, 88200, 96000, 176400, 192000};
                                i = 32;
                                range3 = android.util.Range.create(96000, 24500000);
                                break;
                        }
                    }
                    iArr = iArr4;
                    create = range3;
                } else {
                    android.util.Log.w(TAG, "Unsupported mime " + mimeType);
                    this.mParent.mError |= 2;
                    iArr = null;
                    create = null;
                }
            }
            if (iArr != null) {
                limitSampleRates(iArr);
            } else if (range != null) {
                limitSampleRates(new android.util.Range[]{range});
            }
            applyLimits(new android.util.Range[]{android.util.Range.create(1, java.lang.Integer.valueOf(i))}, create);
        }

        private void applyLimits(android.util.Range<java.lang.Integer>[] rangeArr, android.util.Range<java.lang.Integer> range) {
            android.util.Range[] rangeArr2 = new android.util.Range[rangeArr.length];
            for (int i = 0; i < rangeArr.length; i++) {
                rangeArr2[i] = android.util.Range.create(java.lang.Integer.valueOf(rangeArr[i].clamp(1).intValue()), java.lang.Integer.valueOf(rangeArr[i].clamp(30).intValue()));
            }
            android.media.Utils.sortDistinctRanges(rangeArr2);
            this.mInputChannelRanges = android.media.Utils.intersectSortedDistinctRanges(rangeArr2, this.mInputChannelRanges);
            if (range != null) {
                this.mBitrateRange = this.mBitrateRange.intersect(range);
            }
        }

        private void parseFromInfo(android.media.MediaFormat mediaFormat) {
            android.util.Range<java.lang.Integer>[] rangeArr = {android.util.Range.create(1, 30)};
            android.util.Range<java.lang.Integer> range = android.media.MediaCodecInfo.POSITIVE_INTEGERS;
            if (mediaFormat.containsKey("sample-rate-ranges")) {
                java.lang.String[] split = mediaFormat.getString("sample-rate-ranges").split(",");
                android.util.Range<java.lang.Integer>[] rangeArr2 = new android.util.Range[split.length];
                for (int i = 0; i < split.length; i++) {
                    rangeArr2[i] = android.media.Utils.parseIntRange(split[i], null);
                }
                limitSampleRates(rangeArr2);
            }
            if (mediaFormat.containsKey("channel-ranges")) {
                java.lang.String[] split2 = mediaFormat.getString("channel-ranges").split(",");
                rangeArr = new android.util.Range[split2.length];
                for (int i2 = 0; i2 < split2.length; i2++) {
                    rangeArr[i2] = android.media.Utils.parseIntRange(split2[i2], null);
                }
            } else if (mediaFormat.containsKey("channel-range")) {
                rangeArr = new android.util.Range[]{android.media.Utils.parseIntRange(mediaFormat.getString("channel-range"), null)};
            } else if (mediaFormat.containsKey("max-channel-count")) {
                int parseIntSafely = android.media.Utils.parseIntSafely(mediaFormat.getString("max-channel-count"), 30);
                if (parseIntSafely == 0) {
                    rangeArr = new android.util.Range[]{android.util.Range.create(0, 0)};
                } else {
                    rangeArr = new android.util.Range[]{android.util.Range.create(1, java.lang.Integer.valueOf(parseIntSafely))};
                }
            } else if ((this.mParent.mError & 2) != 0) {
                rangeArr = new android.util.Range[]{android.util.Range.create(0, 0)};
            }
            if (mediaFormat.containsKey("bitrate-range")) {
                range = range.intersect(android.media.Utils.parseIntRange(mediaFormat.getString("bitrate-range"), range));
            }
            applyLimits(rangeArr, range);
        }

        public void getDefaultFormat(android.media.MediaFormat mediaFormat) {
            if (this.mBitrateRange.getLower().equals(this.mBitrateRange.getUpper())) {
                mediaFormat.setInteger(android.media.MediaFormat.KEY_BIT_RATE, this.mBitrateRange.getLower().intValue());
            }
            if (getMaxInputChannelCount() == 1) {
                mediaFormat.setInteger(android.media.MediaFormat.KEY_CHANNEL_COUNT, 1);
            }
            if (this.mSampleRates != null && this.mSampleRates.length == 1) {
                mediaFormat.setInteger(android.media.MediaFormat.KEY_SAMPLE_RATE, this.mSampleRates[0]);
            }
        }

        public boolean supportsFormat(android.media.MediaFormat mediaFormat) {
            java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat.getMap();
            return supports((java.lang.Integer) map.get(android.media.MediaFormat.KEY_SAMPLE_RATE), (java.lang.Integer) map.get(android.media.MediaFormat.KEY_CHANNEL_COUNT)) && android.media.MediaCodecInfo.CodecCapabilities.supportsBitrate(this.mBitrateRange, mediaFormat);
        }
    }

    public int getSecurityModel() {
        return 0;
    }

    public static final class VideoCapabilities {
        private static final java.lang.String TAG = "VideoCapabilities";
        static final java.util.Set<java.lang.String> VIDEO_LEVEL_CRITICAL_FORMAT_KEYS = java.util.Set.of("width", "height", android.media.MediaFormat.KEY_FRAME_RATE, android.media.MediaFormat.KEY_BIT_RATE, android.media.MediaFormat.KEY_MIME);
        private boolean mAllowMbOverride;
        private android.util.Range<android.util.Rational> mAspectRatioRange;
        private android.util.Range<java.lang.Integer> mBitrateRange;
        private android.util.Range<android.util.Rational> mBlockAspectRatioRange;
        private android.util.Range<java.lang.Integer> mBlockCountRange;
        private int mBlockHeight;
        private int mBlockWidth;
        private android.util.Range<java.lang.Long> mBlocksPerSecondRange;
        private android.util.Range<java.lang.Integer> mFrameRateRange;
        private int mHeightAlignment;
        private android.util.Range<java.lang.Integer> mHeightRange;
        private android.util.Range<java.lang.Integer> mHorizontalBlockRange;
        private java.util.Map<android.util.Size, android.util.Range<java.lang.Long>> mMeasuredFrameRates;
        private android.media.MediaCodecInfo.CodecCapabilities mParent;
        private java.util.List<android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint> mPerformancePoints;
        private int mSmallerDimensionUpperLimit;
        private android.util.Range<java.lang.Integer> mVerticalBlockRange;
        private int mWidthAlignment;
        private android.util.Range<java.lang.Integer> mWidthRange;

        public android.util.Range<java.lang.Integer> getBitrateRange() {
            return this.mBitrateRange;
        }

        public android.util.Range<java.lang.Integer> getSupportedWidths() {
            return this.mWidthRange;
        }

        public android.util.Range<java.lang.Integer> getSupportedHeights() {
            return this.mHeightRange;
        }

        public int getWidthAlignment() {
            return this.mWidthAlignment;
        }

        public int getHeightAlignment() {
            return this.mHeightAlignment;
        }

        public int getSmallerDimensionUpperLimit() {
            return this.mSmallerDimensionUpperLimit;
        }

        public android.util.Range<java.lang.Integer> getSupportedFrameRates() {
            return this.mFrameRateRange;
        }

        public android.util.Range<java.lang.Integer> getSupportedWidthsFor(int i) {
            try {
                android.util.Range<java.lang.Integer> range = this.mWidthRange;
                if (!this.mHeightRange.contains((android.util.Range<java.lang.Integer>) java.lang.Integer.valueOf(i)) || i % this.mHeightAlignment != 0) {
                    throw new java.lang.IllegalArgumentException("unsupported height");
                }
                int divUp = android.media.Utils.divUp(i, this.mBlockHeight);
                double d = divUp;
                android.util.Range<java.lang.Integer> intersect = range.intersect(java.lang.Integer.valueOf(((java.lang.Math.max(android.media.Utils.divUp(this.mBlockCountRange.getLower().intValue(), divUp), (int) java.lang.Math.ceil(this.mBlockAspectRatioRange.getLower().doubleValue() * d)) - 1) * this.mBlockWidth) + this.mWidthAlignment), java.lang.Integer.valueOf(java.lang.Math.min(this.mBlockCountRange.getUpper().intValue() / divUp, (int) (this.mBlockAspectRatioRange.getUpper().doubleValue() * d)) * this.mBlockWidth));
                if (i > this.mSmallerDimensionUpperLimit) {
                    intersect = intersect.intersect(1, java.lang.Integer.valueOf(this.mSmallerDimensionUpperLimit));
                }
                double d2 = i;
                return intersect.intersect(java.lang.Integer.valueOf((int) java.lang.Math.ceil(this.mAspectRatioRange.getLower().doubleValue() * d2)), java.lang.Integer.valueOf((int) (this.mAspectRatioRange.getUpper().doubleValue() * d2)));
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.v(TAG, "could not get supported widths for " + i);
                throw new java.lang.IllegalArgumentException("unsupported height");
            }
        }

        public android.util.Range<java.lang.Integer> getSupportedHeightsFor(int i) {
            try {
                android.util.Range<java.lang.Integer> range = this.mHeightRange;
                if (!this.mWidthRange.contains((android.util.Range<java.lang.Integer>) java.lang.Integer.valueOf(i)) || i % this.mWidthAlignment != 0) {
                    throw new java.lang.IllegalArgumentException("unsupported width");
                }
                int divUp = android.media.Utils.divUp(i, this.mBlockWidth);
                double d = divUp;
                android.util.Range<java.lang.Integer> intersect = range.intersect(java.lang.Integer.valueOf(((java.lang.Math.max(android.media.Utils.divUp(this.mBlockCountRange.getLower().intValue(), divUp), (int) java.lang.Math.ceil(d / this.mBlockAspectRatioRange.getUpper().doubleValue())) - 1) * this.mBlockHeight) + this.mHeightAlignment), java.lang.Integer.valueOf(java.lang.Math.min(this.mBlockCountRange.getUpper().intValue() / divUp, (int) (d / this.mBlockAspectRatioRange.getLower().doubleValue())) * this.mBlockHeight));
                if (i > this.mSmallerDimensionUpperLimit) {
                    intersect = intersect.intersect(1, java.lang.Integer.valueOf(this.mSmallerDimensionUpperLimit));
                }
                double d2 = i;
                return intersect.intersect(java.lang.Integer.valueOf((int) java.lang.Math.ceil(d2 / this.mAspectRatioRange.getUpper().doubleValue())), java.lang.Integer.valueOf((int) (d2 / this.mAspectRatioRange.getLower().doubleValue())));
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.v(TAG, "could not get supported heights for " + i);
                throw new java.lang.IllegalArgumentException("unsupported width");
            }
        }

        public android.util.Range<java.lang.Double> getSupportedFrameRatesFor(int i, int i2) {
            if (!supports(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), null)) {
                throw new java.lang.IllegalArgumentException("unsupported size");
            }
            double divUp = android.media.Utils.divUp(i, this.mBlockWidth) * android.media.Utils.divUp(i2, this.mBlockHeight);
            return android.util.Range.create(java.lang.Double.valueOf(java.lang.Math.max(this.mBlocksPerSecondRange.getLower().longValue() / divUp, this.mFrameRateRange.getLower().intValue())), java.lang.Double.valueOf(java.lang.Math.min(this.mBlocksPerSecondRange.getUpper().longValue() / divUp, this.mFrameRateRange.getUpper().intValue())));
        }

        private int getBlockCount(int i, int i2) {
            return android.media.Utils.divUp(i, this.mBlockWidth) * android.media.Utils.divUp(i2, this.mBlockHeight);
        }

        private android.util.Size findClosestSize(int i, int i2) {
            int blockCount = getBlockCount(i, i2);
            android.util.Size size = null;
            int i3 = Integer.MAX_VALUE;
            for (android.util.Size size2 : this.mMeasuredFrameRates.keySet()) {
                int abs = java.lang.Math.abs(blockCount - getBlockCount(size2.getWidth(), size2.getHeight()));
                if (abs < i3) {
                    size = size2;
                    i3 = abs;
                }
            }
            return size;
        }

        private android.util.Range<java.lang.Double> estimateFrameRatesFor(int i, int i2) {
            android.util.Range<java.lang.Long> range = this.mMeasuredFrameRates.get(findClosestSize(i, i2));
            java.lang.Double valueOf = java.lang.Double.valueOf(getBlockCount(r0.getWidth(), r0.getHeight()) / java.lang.Math.max(getBlockCount(i, i2), 1));
            return android.util.Range.create(java.lang.Double.valueOf(range.getLower().longValue() * valueOf.doubleValue()), java.lang.Double.valueOf(range.getUpper().longValue() * valueOf.doubleValue()));
        }

        public android.util.Range<java.lang.Double> getAchievableFrameRatesFor(int i, int i2) {
            if (!supports(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), null)) {
                throw new java.lang.IllegalArgumentException("unsupported size");
            }
            if (this.mMeasuredFrameRates == null || this.mMeasuredFrameRates.size() <= 0) {
                android.util.Log.w(TAG, "Codec did not publish any measurement data.");
                return null;
            }
            return estimateFrameRatesFor(i, i2);
        }

        public static final class PerformancePoint {
            private android.util.Size mBlockSize;
            private int mHeight;
            private int mMaxFrameRate;
            private long mMaxMacroBlockRate;
            private int mWidth;
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint SD_24 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 24);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint SD_25 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 576, 25);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint SD_30 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 30);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint SD_48 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 48);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint SD_50 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 576, 50);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint SD_60 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 480, 60);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint HD_24 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 24);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint HD_25 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 25);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint HD_30 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 30);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint HD_50 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 50);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint HD_60 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 60);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint HD_100 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 100);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint HD_120 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 120);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint HD_200 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 200);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint HD_240 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH, 240);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint FHD_24 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_CREATE_LOGICAL_LINK, 24);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint FHD_25 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_CREATE_LOGICAL_LINK, 25);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint FHD_30 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_CREATE_LOGICAL_LINK, 30);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint FHD_50 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_CREATE_LOGICAL_LINK, 50);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint FHD_60 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_CREATE_LOGICAL_LINK, 60);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint FHD_100 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_CREATE_LOGICAL_LINK, 100);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint FHD_120 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_CREATE_LOGICAL_LINK, 120);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint FHD_200 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_CREATE_LOGICAL_LINK, 200);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint FHD_240 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_CREATE_LOGICAL_LINK, 240);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint UHD_24 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.net.NetworkPolicyManager.MASK_RESTRICTED_MODE_NETWORKS, 2160, 24);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint UHD_25 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.net.NetworkPolicyManager.MASK_RESTRICTED_MODE_NETWORKS, 2160, 25);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint UHD_30 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.net.NetworkPolicyManager.MASK_RESTRICTED_MODE_NETWORKS, 2160, 30);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint UHD_50 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.net.NetworkPolicyManager.MASK_RESTRICTED_MODE_NETWORKS, 2160, 50);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint UHD_60 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.net.NetworkPolicyManager.MASK_RESTRICTED_MODE_NETWORKS, 2160, 60);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint UHD_100 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.net.NetworkPolicyManager.MASK_RESTRICTED_MODE_NETWORKS, 2160, 100);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint UHD_120 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.net.NetworkPolicyManager.MASK_RESTRICTED_MODE_NETWORKS, 2160, 120);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint UHD_200 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.net.NetworkPolicyManager.MASK_RESTRICTED_MODE_NETWORKS, 2160, 200);
            public static final android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint UHD_240 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(android.net.NetworkPolicyManager.MASK_RESTRICTED_MODE_NETWORKS, 2160, 240);

            public int getMaxMacroBlocks() {
                return saturateLongToInt(this.mWidth * this.mHeight);
            }

            public int getMaxFrameRate() {
                return this.mMaxFrameRate;
            }

            public long getMaxMacroBlockRate() {
                return this.mMaxMacroBlockRate;
            }

            public java.lang.String toString() {
                int width = this.mBlockSize.getWidth() * 16;
                int height = this.mBlockSize.getHeight() * 16;
                int divUp = (int) android.media.Utils.divUp(this.mMaxMacroBlockRate, getMaxMacroBlocks());
                java.lang.String str = (this.mWidth * 16) + "x" + (this.mHeight * 16) + "@" + divUp;
                if (divUp < this.mMaxFrameRate) {
                    str = str + ", max " + this.mMaxFrameRate + "fps";
                }
                if (width > 16 || height > 16) {
                    str = str + ", " + width + "x" + height + " blocks";
                }
                return "PerformancePoint(" + str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            }

            public int hashCode() {
                return this.mMaxFrameRate;
            }

            public PerformancePoint(int i, int i2, int i3, int i4, android.util.Size size) {
                android.media.MediaCodecInfo.checkPowerOfTwo(size.getWidth(), "block width");
                android.media.MediaCodecInfo.checkPowerOfTwo(size.getHeight(), "block height");
                this.mBlockSize = new android.util.Size(android.media.Utils.divUp(size.getWidth(), 16), android.media.Utils.divUp(size.getHeight(), 16));
                this.mWidth = (int) (android.media.Utils.divUp(java.lang.Math.max(1L, i), java.lang.Math.max(size.getWidth(), 16)) * this.mBlockSize.getWidth());
                this.mHeight = (int) (android.media.Utils.divUp(java.lang.Math.max(1L, i2), java.lang.Math.max(size.getHeight(), 16)) * this.mBlockSize.getHeight());
                this.mMaxFrameRate = java.lang.Math.max(1, java.lang.Math.max(i3, i4));
                this.mMaxMacroBlockRate = java.lang.Math.max(1, i3) * getMaxMacroBlocks();
            }

            public PerformancePoint(android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint, android.util.Size size) {
                this(performancePoint.mWidth * 16, performancePoint.mHeight * 16, (int) android.media.Utils.divUp(performancePoint.mMaxMacroBlockRate, performancePoint.getMaxMacroBlocks()), performancePoint.mMaxFrameRate, new android.util.Size(java.lang.Math.max(size.getWidth(), performancePoint.mBlockSize.getWidth() * 16), java.lang.Math.max(size.getHeight(), performancePoint.mBlockSize.getHeight() * 16)));
            }

            public PerformancePoint(int i, int i2, int i3) {
                this(i, i2, i3, i3, new android.util.Size(16, 16));
            }

            private int saturateLongToInt(long j) {
                if (j < -2147483648L) {
                    return Integer.MIN_VALUE;
                }
                if (j > 2147483647L) {
                    return Integer.MAX_VALUE;
                }
                return (int) j;
            }

            private int align(int i, int i2) {
                return android.media.Utils.divUp(i, i2) * i2;
            }

            private void checkPowerOfTwo2(int i, java.lang.String str) {
                if (i == 0 || ((i - 1) & i) != 0) {
                    throw new java.lang.IllegalArgumentException(str + " (" + i + ") must be a power of 2");
                }
            }

            public boolean covers(android.media.MediaFormat mediaFormat) {
                return covers(new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(mediaFormat.getInteger("width", 0), mediaFormat.getInteger("height", 0), java.lang.Math.round((float) java.lang.Math.ceil(mediaFormat.getNumber(android.media.MediaFormat.KEY_FRAME_RATE, 0).doubleValue()))));
            }

            public boolean covers(android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint) {
                android.util.Size commonBlockSize = getCommonBlockSize(performancePoint);
                android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint2 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(this, commonBlockSize);
                android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint3 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(performancePoint, commonBlockSize);
                return performancePoint2.getMaxMacroBlocks() >= performancePoint3.getMaxMacroBlocks() && performancePoint2.mMaxFrameRate >= performancePoint3.mMaxFrameRate && performancePoint2.mMaxMacroBlockRate >= performancePoint3.mMaxMacroBlockRate;
            }

            private android.util.Size getCommonBlockSize(android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint) {
                return new android.util.Size(java.lang.Math.max(this.mBlockSize.getWidth(), performancePoint.mBlockSize.getWidth()) * 16, java.lang.Math.max(this.mBlockSize.getHeight(), performancePoint.mBlockSize.getHeight()) * 16);
            }

            public boolean equals(java.lang.Object obj) {
                if (!(obj instanceof android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint)) {
                    return false;
                }
                android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint = (android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint) obj;
                android.util.Size commonBlockSize = getCommonBlockSize(performancePoint);
                android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint2 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(this, commonBlockSize);
                android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint3 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(performancePoint, commonBlockSize);
                return performancePoint2.getMaxMacroBlocks() == performancePoint3.getMaxMacroBlocks() && performancePoint2.mMaxFrameRate == performancePoint3.mMaxFrameRate && performancePoint2.mMaxMacroBlockRate == performancePoint3.mMaxMacroBlockRate;
            }
        }

        public java.util.List<android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint> getSupportedPerformancePoints() {
            return this.mPerformancePoints;
        }

        public boolean areSizeAndRateSupported(int i, int i2, double d) {
            return supports(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Double.valueOf(d));
        }

        public boolean isSizeSupported(int i, int i2) {
            return supports(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), null);
        }

        private boolean supports(java.lang.Integer num, java.lang.Integer num2, java.lang.Number number) {
            boolean z;
            boolean z2 = false;
            if (num != null) {
                z = this.mWidthRange.contains((android.util.Range<java.lang.Integer>) num) && num.intValue() % this.mWidthAlignment == 0;
            } else {
                z = true;
            }
            if (z && num2 != null) {
                z = this.mHeightRange.contains((android.util.Range<java.lang.Integer>) num2) && num2.intValue() % this.mHeightAlignment == 0;
            }
            if (z && number != null) {
                z = this.mFrameRateRange.contains(android.media.Utils.intRangeFor(number.doubleValue()));
            }
            if (!z || num2 == null || num == null) {
                return z;
            }
            boolean z3 = java.lang.Math.min(num2.intValue(), num.intValue()) <= this.mSmallerDimensionUpperLimit;
            int divUp = android.media.Utils.divUp(num.intValue(), this.mBlockWidth);
            int divUp2 = android.media.Utils.divUp(num2.intValue(), this.mBlockHeight);
            int i = divUp * divUp2;
            if (z3 && this.mBlockCountRange.contains((android.util.Range<java.lang.Integer>) java.lang.Integer.valueOf(i)) && this.mBlockAspectRatioRange.contains((android.util.Range<android.util.Rational>) new android.util.Rational(divUp, divUp2)) && this.mAspectRatioRange.contains((android.util.Range<android.util.Rational>) new android.util.Rational(num.intValue(), num2.intValue()))) {
                z2 = true;
            }
            if (z2 && number != null) {
                return this.mBlocksPerSecondRange.contains(android.media.Utils.longRangeFor(i * number.doubleValue()));
            }
            return z2;
        }

        public boolean supportsFormat(android.media.MediaFormat mediaFormat) {
            java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat.getMap();
            return supports((java.lang.Integer) map.get("width"), (java.lang.Integer) map.get("height"), (java.lang.Number) map.get(android.media.MediaFormat.KEY_FRAME_RATE)) && android.media.MediaCodecInfo.CodecCapabilities.supportsBitrate(this.mBitrateRange, mediaFormat);
        }

        private VideoCapabilities() {
        }

        public static android.media.MediaCodecInfo.VideoCapabilities create(android.media.MediaFormat mediaFormat, android.media.MediaCodecInfo.CodecCapabilities codecCapabilities) {
            android.media.MediaCodecInfo.VideoCapabilities videoCapabilities = new android.media.MediaCodecInfo.VideoCapabilities();
            videoCapabilities.init(mediaFormat, codecCapabilities);
            return videoCapabilities;
        }

        private void init(android.media.MediaFormat mediaFormat, android.media.MediaCodecInfo.CodecCapabilities codecCapabilities) {
            this.mParent = codecCapabilities;
            initWithPlatformLimits();
            applyLevelLimits();
            parseFromInfo(mediaFormat);
            updateLimits();
        }

        public android.util.Size getBlockSize() {
            return new android.util.Size(this.mBlockWidth, this.mBlockHeight);
        }

        public android.util.Range<java.lang.Integer> getBlockCountRange() {
            return this.mBlockCountRange;
        }

        public android.util.Range<java.lang.Long> getBlocksPerSecondRange() {
            return this.mBlocksPerSecondRange;
        }

        public android.util.Range<android.util.Rational> getAspectRatioRange(boolean z) {
            return z ? this.mBlockAspectRatioRange : this.mAspectRatioRange;
        }

        private void initWithPlatformLimits() {
            this.mBitrateRange = android.media.MediaCodecInfo.BITRATE_RANGE;
            this.mWidthRange = android.media.MediaCodecInfo.getSizeRange();
            this.mHeightRange = android.media.MediaCodecInfo.getSizeRange();
            this.mFrameRateRange = android.media.MediaCodecInfo.FRAME_RATE_RANGE;
            this.mHorizontalBlockRange = android.media.MediaCodecInfo.getSizeRange();
            this.mVerticalBlockRange = android.media.MediaCodecInfo.getSizeRange();
            this.mBlockCountRange = android.media.MediaCodecInfo.POSITIVE_INTEGERS;
            this.mBlocksPerSecondRange = android.media.MediaCodecInfo.POSITIVE_LONGS;
            this.mBlockAspectRatioRange = android.media.MediaCodecInfo.POSITIVE_RATIONALS;
            this.mAspectRatioRange = android.media.MediaCodecInfo.POSITIVE_RATIONALS;
            this.mWidthAlignment = 2;
            this.mHeightAlignment = 2;
            this.mBlockWidth = 2;
            this.mBlockHeight = 2;
            this.mSmallerDimensionUpperLimit = ((java.lang.Integer) android.media.MediaCodecInfo.getSizeRange().getUpper()).intValue();
        }

        private java.util.List<android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint> getPerformancePoints(java.util.Map<java.lang.String, java.lang.Object> map) {
            android.util.Size parseSize;
            android.util.Range<java.lang.Long> parseLongRange;
            java.util.Vector vector = new java.util.Vector();
            for (java.lang.String str : map.keySet()) {
                if (str.startsWith("performance-point-")) {
                    if (str.substring("performance-point-".length()).equals("none") && vector.size() == 0) {
                        return java.util.Collections.unmodifiableList(vector);
                    }
                    java.lang.String[] split = str.split(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    if (split.length == 4 && (parseSize = android.media.Utils.parseSize(split[2], null)) != null && parseSize.getWidth() * parseSize.getHeight() > 0 && (parseLongRange = android.media.Utils.parseLongRange(map.get(str), null)) != null && parseLongRange.getLower().longValue() >= 0 && parseLongRange.getUpper().longValue() >= 0) {
                        android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(parseSize.getWidth(), parseSize.getHeight(), parseLongRange.getLower().intValue(), parseLongRange.getUpper().intValue(), new android.util.Size(this.mBlockWidth, this.mBlockHeight));
                        android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint2 = new android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint(parseSize.getHeight(), parseSize.getWidth(), parseLongRange.getLower().intValue(), parseLongRange.getUpper().intValue(), new android.util.Size(this.mBlockWidth, this.mBlockHeight));
                        vector.add(performancePoint);
                        if (!performancePoint.covers(performancePoint2)) {
                            vector.add(performancePoint2);
                        }
                    }
                }
            }
            if (vector.size() == 0) {
                return null;
            }
            vector.sort(new java.util.Comparator() { // from class: android.media.MediaCodecInfo$VideoCapabilities$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    return android.media.MediaCodecInfo.VideoCapabilities.lambda$getPerformancePoints$0((android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint) obj, (android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint) obj2);
                }
            });
            return java.util.Collections.unmodifiableList(vector);
        }

        static /* synthetic */ int lambda$getPerformancePoints$0(android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint, android.media.MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint2) {
            int i = -1;
            if (performancePoint.getMaxMacroBlocks() != performancePoint2.getMaxMacroBlocks()) {
                if (performancePoint.getMaxMacroBlocks() >= performancePoint2.getMaxMacroBlocks()) {
                    i = 1;
                }
            } else if (performancePoint.getMaxMacroBlockRate() != performancePoint2.getMaxMacroBlockRate()) {
                if (performancePoint.getMaxMacroBlockRate() >= performancePoint2.getMaxMacroBlockRate()) {
                    i = 1;
                }
            } else if (performancePoint.getMaxFrameRate() == performancePoint2.getMaxFrameRate()) {
                i = 0;
            } else if (performancePoint.getMaxFrameRate() >= performancePoint2.getMaxFrameRate()) {
                i = 1;
            }
            return -i;
        }

        private java.util.Map<android.util.Size, android.util.Range<java.lang.Long>> getMeasuredFrameRates(java.util.Map<java.lang.String, java.lang.Object> map) {
            android.util.Size parseSize;
            android.util.Range<java.lang.Long> parseLongRange;
            java.util.HashMap hashMap = new java.util.HashMap();
            for (java.lang.String str : map.keySet()) {
                if (str.startsWith("measured-frame-rate-")) {
                    str.substring("measured-frame-rate-".length());
                    java.lang.String[] split = str.split(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    if (split.length == 5 && (parseSize = android.media.Utils.parseSize(split[3], null)) != null && parseSize.getWidth() * parseSize.getHeight() > 0 && (parseLongRange = android.media.Utils.parseLongRange(map.get(str), null)) != null && parseLongRange.getLower().longValue() >= 0 && parseLongRange.getUpper().longValue() >= 0) {
                        hashMap.put(parseSize, parseLongRange);
                    }
                }
            }
            return hashMap;
        }

        private static android.util.Pair<android.util.Range<java.lang.Integer>, android.util.Range<java.lang.Integer>> parseWidthHeightRanges(java.lang.Object obj) {
            android.util.Pair<android.util.Size, android.util.Size> parseSizeRange = android.media.Utils.parseSizeRange(obj);
            if (parseSizeRange != null) {
                try {
                    return android.util.Pair.create(android.util.Range.create(java.lang.Integer.valueOf(parseSizeRange.first.getWidth()), java.lang.Integer.valueOf(parseSizeRange.second.getWidth())), android.util.Range.create(java.lang.Integer.valueOf(parseSizeRange.first.getHeight()), java.lang.Integer.valueOf(parseSizeRange.second.getHeight())));
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Log.w(TAG, "could not parse size range '" + obj + "'");
                    return null;
                }
            }
            return null;
        }

        public static int equivalentVP9Level(android.media.MediaFormat mediaFormat) {
            int max;
            java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat.getMap();
            android.util.Size parseSize = android.media.Utils.parseSize(map.get("block-size"), new android.util.Size(8, 8));
            int width = parseSize.getWidth() * parseSize.getHeight();
            android.util.Range<java.lang.Integer> parseIntRange = android.media.Utils.parseIntRange(map.get("block-count-range"), null);
            int intValue = parseIntRange == null ? 0 : parseIntRange.getUpper().intValue() * width;
            android.util.Range<java.lang.Long> parseLongRange = android.media.Utils.parseLongRange(map.get("blocks-per-second-range"), null);
            long longValue = parseLongRange == null ? 0L : width * parseLongRange.getUpper().longValue();
            android.util.Pair<android.util.Range<java.lang.Integer>, android.util.Range<java.lang.Integer>> parseWidthHeightRanges = parseWidthHeightRanges(map.get("size-range"));
            if (parseWidthHeightRanges == null) {
                max = 0;
            } else {
                max = java.lang.Math.max(parseWidthHeightRanges.first.getUpper().intValue(), parseWidthHeightRanges.second.getUpper().intValue());
            }
            android.util.Range<java.lang.Integer> parseIntRange2 = android.media.Utils.parseIntRange(map.get("bitrate-range"), null);
            int divUp = parseIntRange2 != null ? android.media.Utils.divUp(parseIntRange2.getUpper().intValue(), 1000) : 0;
            if (longValue <= 829440 && intValue <= 36864 && divUp <= 200 && max <= 512) {
                return 1;
            }
            if (longValue <= 2764800 && intValue <= 73728 && divUp <= 800 && max <= 768) {
                return 2;
            }
            if (longValue <= 4608000 && intValue <= 122880 && divUp <= 1800 && max <= 960) {
                return 4;
            }
            if (longValue <= 9216000 && intValue <= 245760 && divUp <= 3600 && max <= 1344) {
                return 8;
            }
            if (longValue <= 20736000 && intValue <= 552960 && divUp <= 7200 && max <= 2048) {
                return 16;
            }
            if (longValue <= 36864000 && intValue <= 983040 && divUp <= 12000 && max <= 2752) {
                return 32;
            }
            if (longValue <= 83558400 && intValue <= 2228224 && divUp <= 18000 && max <= 4160) {
                return 64;
            }
            if (longValue <= 160432128 && intValue <= 2228224 && divUp <= 30000 && max <= 4160) {
                return 128;
            }
            if (longValue <= 311951360 && intValue <= 8912896 && divUp <= 60000 && max <= 8384) {
                return 256;
            }
            if (longValue <= 588251136 && intValue <= 8912896 && divUp <= 120000 && max <= 8384) {
                return 512;
            }
            if (longValue <= 1176502272 && intValue <= 8912896 && divUp <= 180000 && max <= 8384) {
                return 1024;
            }
            if (longValue <= 1176502272 && intValue <= 35651584 && divUp <= 180000 && max <= 16832) {
                return 2048;
            }
            if (longValue <= 2353004544L && intValue <= 35651584 && divUp <= 240000 && max <= 16832) {
                return 4096;
            }
            return 8192;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x011c  */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0158  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x01be  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0269  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0275  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0281  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x02a0  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x02c0  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x02de  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x02ea  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x02f6  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x025f  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0129 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:67:0x00ed A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void parseFromInfo(android.media.MediaFormat mediaFormat) {
            android.util.Range<java.lang.Integer> range;
            android.util.Range<java.lang.Integer> range2;
            android.util.Range<java.lang.Integer> range3;
            android.util.Range<java.lang.Integer> range4;
            android.util.Range<java.lang.Integer> parseIntRange;
            android.util.Range<java.lang.Integer> range5;
            android.util.Range<java.lang.Integer> parseIntRange2;
            android.util.Range<java.lang.Integer> range6;
            android.util.Range<java.lang.Integer> range7;
            android.util.Range<java.lang.Long> range8;
            android.util.Range<java.lang.Integer> range9;
            android.util.Range<android.util.Rational> range10;
            android.util.Range<android.util.Rational> range11;
            java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat.getMap();
            android.util.Size size = new android.util.Size(this.mBlockWidth, this.mBlockHeight);
            android.util.Size size2 = new android.util.Size(this.mWidthAlignment, this.mHeightAlignment);
            android.util.Size parseSize = android.media.Utils.parseSize(map.get("block-size"), size);
            android.util.Size parseSize2 = android.media.Utils.parseSize(map.get("alignment"), size2);
            android.util.Range<java.lang.Integer> parseIntRange3 = android.media.Utils.parseIntRange(map.get("block-count-range"), null);
            android.util.Range<java.lang.Long> parseLongRange = android.media.Utils.parseLongRange(map.get("blocks-per-second-range"), null);
            this.mMeasuredFrameRates = getMeasuredFrameRates(map);
            this.mPerformancePoints = getPerformancePoints(map);
            android.util.Pair<android.util.Range<java.lang.Integer>, android.util.Range<java.lang.Integer>> parseWidthHeightRanges = parseWidthHeightRanges(map.get("size-range"));
            if (parseWidthHeightRanges == null) {
                range = null;
                range2 = null;
            } else {
                range2 = parseWidthHeightRanges.first;
                range = parseWidthHeightRanges.second;
            }
            if (map.containsKey("feature-can-swap-width-height")) {
                if (range2 != null) {
                    this.mSmallerDimensionUpperLimit = java.lang.Math.min(range2.getUpper().intValue(), range.getUpper().intValue());
                    range4 = range2.extend(range);
                    range3 = range4;
                    android.util.Range<android.util.Rational> parseRationalRange = android.media.Utils.parseRationalRange(map.get("block-aspect-ratio-range"), null);
                    android.util.Range<android.util.Rational> parseRationalRange2 = android.media.Utils.parseRationalRange(map.get("pixel-aspect-ratio-range"), null);
                    parseIntRange = android.media.Utils.parseIntRange(map.get("frame-rate-range"), null);
                    if (parseIntRange != null) {
                        range5 = parseIntRange;
                    } else {
                        try {
                            range5 = parseIntRange.intersect(android.media.MediaCodecInfo.FRAME_RATE_RANGE);
                        } catch (java.lang.IllegalArgumentException e) {
                            android.util.Log.w(TAG, "frame rate range (" + parseIntRange + ") is out of limits: " + android.media.MediaCodecInfo.FRAME_RATE_RANGE);
                            range5 = null;
                        }
                    }
                    parseIntRange2 = android.media.Utils.parseIntRange(map.get("bitrate-range"), null);
                    if (parseIntRange2 != null) {
                        range6 = parseIntRange2;
                    } else {
                        try {
                            range6 = parseIntRange2.intersect(android.media.MediaCodecInfo.BITRATE_RANGE);
                        } catch (java.lang.IllegalArgumentException e2) {
                            android.util.Log.w(TAG, "bitrate range (" + parseIntRange2 + ") is out of limits: " + android.media.MediaCodecInfo.BITRATE_RANGE);
                            range6 = null;
                        }
                    }
                    android.media.MediaCodecInfo.checkPowerOfTwo(parseSize.getWidth(), "block-size width must be power of two");
                    android.media.MediaCodecInfo.checkPowerOfTwo(parseSize.getHeight(), "block-size height must be power of two");
                    android.media.MediaCodecInfo.checkPowerOfTwo(parseSize2.getWidth(), "alignment width must be power of two");
                    android.media.MediaCodecInfo.checkPowerOfTwo(parseSize2.getHeight(), "alignment height must be power of two");
                    android.util.Range<java.lang.Integer> range12 = range5;
                    range7 = range4;
                    applyMacroBlockLimits(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, parseSize.getWidth(), parseSize.getHeight(), parseSize2.getWidth(), parseSize2.getHeight());
                    if ((this.mParent.mError & 2) != 0) {
                        if (!this.mAllowMbOverride) {
                            if (range7 != null) {
                                this.mWidthRange = this.mWidthRange.intersect(range7);
                            }
                            if (range3 != null) {
                                this.mHeightRange = this.mHeightRange.intersect(range3);
                            }
                            if (parseIntRange3 != null) {
                                this.mBlockCountRange = this.mBlockCountRange.intersect(android.media.Utils.factorRange(parseIntRange3, ((this.mBlockWidth * this.mBlockHeight) / parseSize.getWidth()) / parseSize.getHeight()));
                            }
                            if (parseLongRange != null) {
                                this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(android.media.Utils.factorRange(parseLongRange, ((this.mBlockWidth * this.mBlockHeight) / parseSize.getWidth()) / parseSize.getHeight()));
                            }
                            if (parseRationalRange2 != null) {
                                this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(android.media.Utils.scaleRange(parseRationalRange2, this.mBlockHeight / parseSize.getHeight(), this.mBlockWidth / parseSize.getWidth()));
                            }
                            if (parseRationalRange != null) {
                                this.mAspectRatioRange = this.mAspectRatioRange.intersect(parseRationalRange);
                            }
                            if (range12 != null) {
                                this.mFrameRateRange = this.mFrameRateRange.intersect(range12);
                            }
                            if (range6 != null) {
                                this.mBitrateRange = this.mBitrateRange.intersect(range6);
                            }
                            updateLimits();
                        }
                        range8 = parseLongRange;
                        range9 = range12;
                        range10 = parseRationalRange2;
                        range11 = parseRationalRange;
                    } else {
                        range8 = parseLongRange;
                        range9 = range12;
                        range10 = parseRationalRange2;
                        range11 = parseRationalRange;
                    }
                    if (range7 != null) {
                        this.mWidthRange = android.media.MediaCodecInfo.getSizeRange().intersect(range7);
                    }
                    if (range3 != null) {
                        this.mHeightRange = android.media.MediaCodecInfo.getSizeRange().intersect(range3);
                    }
                    if (parseIntRange3 != null) {
                        this.mBlockCountRange = android.media.MediaCodecInfo.POSITIVE_INTEGERS.intersect(android.media.Utils.factorRange(parseIntRange3, ((this.mBlockWidth * this.mBlockHeight) / parseSize.getWidth()) / parseSize.getHeight()));
                    }
                    if (range8 != null) {
                        this.mBlocksPerSecondRange = android.media.MediaCodecInfo.POSITIVE_LONGS.intersect(android.media.Utils.factorRange(range8, ((this.mBlockWidth * this.mBlockHeight) / parseSize.getWidth()) / parseSize.getHeight()));
                    }
                    if (range10 != null) {
                        this.mBlockAspectRatioRange = android.media.MediaCodecInfo.POSITIVE_RATIONALS.intersect(android.media.Utils.scaleRange(range10, this.mBlockHeight / parseSize.getHeight(), this.mBlockWidth / parseSize.getWidth()));
                    }
                    if (range11 != null) {
                        this.mAspectRatioRange = android.media.MediaCodecInfo.POSITIVE_RATIONALS.intersect(range11);
                    }
                    if (range9 != null) {
                        this.mFrameRateRange = android.media.MediaCodecInfo.FRAME_RATE_RANGE.intersect(range9);
                    }
                    if (range6 != null) {
                        if ((this.mParent.mError & 2) != 0) {
                            this.mBitrateRange = android.media.MediaCodecInfo.BITRATE_RANGE.intersect(range6);
                        } else {
                            this.mBitrateRange = this.mBitrateRange.intersect(range6);
                        }
                    }
                    updateLimits();
                }
                android.util.Log.w(TAG, "feature can-swap-width-height is best used with size-range");
                this.mSmallerDimensionUpperLimit = java.lang.Math.min(this.mWidthRange.getUpper().intValue(), this.mHeightRange.getUpper().intValue());
                android.util.Range<java.lang.Integer> extend = this.mWidthRange.extend(this.mHeightRange);
                this.mHeightRange = extend;
                this.mWidthRange = extend;
            }
            range3 = range;
            range4 = range2;
            android.util.Range<android.util.Rational> parseRationalRange3 = android.media.Utils.parseRationalRange(map.get("block-aspect-ratio-range"), null);
            android.util.Range<android.util.Rational> parseRationalRange22 = android.media.Utils.parseRationalRange(map.get("pixel-aspect-ratio-range"), null);
            parseIntRange = android.media.Utils.parseIntRange(map.get("frame-rate-range"), null);
            if (parseIntRange != null) {
            }
            parseIntRange2 = android.media.Utils.parseIntRange(map.get("bitrate-range"), null);
            if (parseIntRange2 != null) {
            }
            android.media.MediaCodecInfo.checkPowerOfTwo(parseSize.getWidth(), "block-size width must be power of two");
            android.media.MediaCodecInfo.checkPowerOfTwo(parseSize.getHeight(), "block-size height must be power of two");
            android.media.MediaCodecInfo.checkPowerOfTwo(parseSize2.getWidth(), "alignment width must be power of two");
            android.media.MediaCodecInfo.checkPowerOfTwo(parseSize2.getHeight(), "alignment height must be power of two");
            android.util.Range<java.lang.Integer> range122 = range5;
            range7 = range4;
            applyMacroBlockLimits(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, parseSize.getWidth(), parseSize.getHeight(), parseSize2.getWidth(), parseSize2.getHeight());
            if ((this.mParent.mError & 2) != 0) {
            }
            if (range7 != null) {
            }
            if (range3 != null) {
            }
            if (parseIntRange3 != null) {
            }
            if (range8 != null) {
            }
            if (range10 != null) {
            }
            if (range11 != null) {
            }
            if (range9 != null) {
            }
            if (range6 != null) {
            }
            updateLimits();
        }

        private void applyBlockLimits(int i, int i2, android.util.Range<java.lang.Integer> range, android.util.Range<java.lang.Long> range2, android.util.Range<android.util.Rational> range3) {
            android.media.MediaCodecInfo.checkPowerOfTwo(i, "blockWidth must be a power of two");
            android.media.MediaCodecInfo.checkPowerOfTwo(i2, "blockHeight must be a power of two");
            int max = java.lang.Math.max(i, this.mBlockWidth);
            int max2 = java.lang.Math.max(i2, this.mBlockHeight);
            int i3 = max * max2;
            int i4 = (i3 / this.mBlockWidth) / this.mBlockHeight;
            if (i4 != 1) {
                this.mBlockCountRange = android.media.Utils.factorRange(this.mBlockCountRange, i4);
                this.mBlocksPerSecondRange = android.media.Utils.factorRange(this.mBlocksPerSecondRange, i4);
                this.mBlockAspectRatioRange = android.media.Utils.scaleRange(this.mBlockAspectRatioRange, max2 / this.mBlockHeight, max / this.mBlockWidth);
                this.mHorizontalBlockRange = android.media.Utils.factorRange(this.mHorizontalBlockRange, max / this.mBlockWidth);
                this.mVerticalBlockRange = android.media.Utils.factorRange(this.mVerticalBlockRange, max2 / this.mBlockHeight);
            }
            int i5 = (i3 / i) / i2;
            if (i5 != 1) {
                range = android.media.Utils.factorRange(range, i5);
                range2 = android.media.Utils.factorRange(range2, i5);
                range3 = android.media.Utils.scaleRange(range3, max2 / i2, max / i);
            }
            this.mBlockCountRange = this.mBlockCountRange.intersect(range);
            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(range2);
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(range3);
            this.mBlockWidth = max;
            this.mBlockHeight = max2;
        }

        private void applyAlignment(int i, int i2) {
            android.media.MediaCodecInfo.checkPowerOfTwo(i, "widthAlignment must be a power of two");
            android.media.MediaCodecInfo.checkPowerOfTwo(i2, "heightAlignment must be a power of two");
            if (i > this.mBlockWidth || i2 > this.mBlockHeight) {
                applyBlockLimits(java.lang.Math.max(i, this.mBlockWidth), java.lang.Math.max(i2, this.mBlockHeight), android.media.MediaCodecInfo.POSITIVE_INTEGERS, android.media.MediaCodecInfo.POSITIVE_LONGS, android.media.MediaCodecInfo.POSITIVE_RATIONALS);
            }
            this.mWidthAlignment = java.lang.Math.max(i, this.mWidthAlignment);
            this.mHeightAlignment = java.lang.Math.max(i2, this.mHeightAlignment);
            this.mWidthRange = android.media.Utils.alignRange(this.mWidthRange, this.mWidthAlignment);
            this.mHeightRange = android.media.Utils.alignRange(this.mHeightRange, this.mHeightAlignment);
        }

        private void updateLimits() {
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(android.media.Utils.factorRange(this.mWidthRange, this.mBlockWidth));
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(android.util.Range.create(java.lang.Integer.valueOf(this.mBlockCountRange.getLower().intValue() / this.mVerticalBlockRange.getUpper().intValue()), java.lang.Integer.valueOf(this.mBlockCountRange.getUpper().intValue() / this.mVerticalBlockRange.getLower().intValue())));
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(android.media.Utils.factorRange(this.mHeightRange, this.mBlockHeight));
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(android.util.Range.create(java.lang.Integer.valueOf(this.mBlockCountRange.getLower().intValue() / this.mHorizontalBlockRange.getUpper().intValue()), java.lang.Integer.valueOf(this.mBlockCountRange.getUpper().intValue() / this.mHorizontalBlockRange.getLower().intValue())));
            this.mBlockCountRange = this.mBlockCountRange.intersect(android.util.Range.create(java.lang.Integer.valueOf(this.mHorizontalBlockRange.getLower().intValue() * this.mVerticalBlockRange.getLower().intValue()), java.lang.Integer.valueOf(this.mHorizontalBlockRange.getUpper().intValue() * this.mVerticalBlockRange.getUpper().intValue())));
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(new android.util.Rational(this.mHorizontalBlockRange.getLower().intValue(), this.mVerticalBlockRange.getUpper().intValue()), new android.util.Rational(this.mHorizontalBlockRange.getUpper().intValue(), this.mVerticalBlockRange.getLower().intValue()));
            this.mWidthRange = this.mWidthRange.intersect(java.lang.Integer.valueOf(((this.mHorizontalBlockRange.getLower().intValue() - 1) * this.mBlockWidth) + this.mWidthAlignment), java.lang.Integer.valueOf(this.mHorizontalBlockRange.getUpper().intValue() * this.mBlockWidth));
            this.mHeightRange = this.mHeightRange.intersect(java.lang.Integer.valueOf(((this.mVerticalBlockRange.getLower().intValue() - 1) * this.mBlockHeight) + this.mHeightAlignment), java.lang.Integer.valueOf(this.mVerticalBlockRange.getUpper().intValue() * this.mBlockHeight));
            this.mAspectRatioRange = this.mAspectRatioRange.intersect(new android.util.Rational(this.mWidthRange.getLower().intValue(), this.mHeightRange.getUpper().intValue()), new android.util.Rational(this.mWidthRange.getUpper().intValue(), this.mHeightRange.getLower().intValue()));
            this.mSmallerDimensionUpperLimit = java.lang.Math.min(this.mSmallerDimensionUpperLimit, java.lang.Math.min(this.mWidthRange.getUpper().intValue(), this.mHeightRange.getUpper().intValue()));
            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(java.lang.Long.valueOf(this.mBlockCountRange.getLower().intValue() * this.mFrameRateRange.getLower().intValue()), java.lang.Long.valueOf(this.mBlockCountRange.getUpper().intValue() * this.mFrameRateRange.getUpper().intValue()));
            this.mFrameRateRange = this.mFrameRateRange.intersect(java.lang.Integer.valueOf((int) (this.mBlocksPerSecondRange.getLower().longValue() / this.mBlockCountRange.getUpper().intValue())), java.lang.Integer.valueOf((int) (this.mBlocksPerSecondRange.getUpper().longValue() / this.mBlockCountRange.getLower().intValue())));
        }

        private void applyMacroBlockLimits(int i, int i2, int i3, long j, int i4, int i5, int i6, int i7) {
            applyMacroBlockLimits(1, 1, i, i2, i3, j, i4, i5, i6, i7);
        }

        private void applyMacroBlockLimits(int i, int i2, int i3, int i4, int i5, long j, int i6, int i7, int i8, int i9) {
            applyAlignment(i8, i9);
            applyBlockLimits(i6, i7, android.util.Range.create(1, java.lang.Integer.valueOf(i5)), android.util.Range.create(1L, java.lang.Long.valueOf(j)), android.util.Range.create(new android.util.Rational(1, i4), new android.util.Rational(i3, 1)));
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(java.lang.Integer.valueOf(android.media.Utils.divUp(i, this.mBlockWidth / i6)), java.lang.Integer.valueOf(i3 / (this.mBlockWidth / i6)));
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(java.lang.Integer.valueOf(android.media.Utils.divUp(i2, this.mBlockHeight / i7)), java.lang.Integer.valueOf(i4 / (this.mBlockHeight / i7)));
        }

        private void applyLevelLimits() {
            java.lang.Integer num;
            int i;
            int i2;
            android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr;
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            long j;
            java.lang.String str;
            int i8;
            double d;
            int i9;
            int i10;
            android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr2;
            java.lang.Integer num2;
            int i11;
            int i12;
            int i13;
            long j2;
            android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr3;
            int i14;
            int i15;
            int i16;
            int i17;
            int i18;
            java.lang.String str2;
            java.lang.String str3;
            boolean z;
            int i19;
            int i20;
            int i21;
            int i22;
            int i23;
            int i24;
            int i25;
            int i26;
            int i27;
            int i28;
            java.lang.String str4;
            int i29;
            int i30;
            int i31;
            android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr4;
            java.lang.String str5;
            java.lang.String str6;
            java.lang.String str7;
            int i32;
            int i33;
            int i34;
            boolean z2;
            boolean z3;
            int i35;
            int i36;
            int i37;
            int i38;
            int max;
            int i39;
            int i40;
            int i41;
            java.lang.String str8;
            java.lang.String str9;
            int i42;
            int i43;
            int i44;
            java.lang.String str10;
            int i45;
            boolean z4;
            int i46;
            int i47;
            int i48;
            int i49;
            java.lang.Integer num3;
            int i50;
            int i51;
            boolean z5;
            int i52;
            android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr5 = this.mParent.profileLevels;
            java.lang.String mimeType = this.mParent.getMimeType();
            boolean equalsIgnoreCase = mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_AVC);
            java.lang.String str11 = "Unrecognized profile ";
            java.lang.String str12 = " for ";
            java.lang.String str13 = TAG;
            java.lang.Integer num4 = 1;
            int i53 = 4;
            if (equalsIgnoreCase) {
                int length = codecProfileLevelArr5.length;
                long j3 = 1485;
                i = 64000;
                int i54 = 0;
                int i55 = 396;
                i2 = 4;
                int i56 = 99;
                while (i54 < length) {
                    android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel = codecProfileLevelArr5[i54];
                    switch (codecProfileLevel.level) {
                        case 1:
                            i46 = length;
                            i47 = 99;
                            i48 = 396;
                            i49 = 1485;
                            num3 = num4;
                            i50 = 64;
                            break;
                        case 2:
                            i46 = length;
                            i47 = 99;
                            i48 = 396;
                            i49 = 1485;
                            num3 = num4;
                            i50 = 128;
                            break;
                        case 4:
                            i49 = 3000;
                            i46 = length;
                            i48 = 900;
                            i47 = 396;
                            num3 = num4;
                            i50 = 192;
                            break;
                        case 8:
                            i49 = 6000;
                            i46 = length;
                            i48 = 2376;
                            i47 = 396;
                            num3 = num4;
                            i50 = 384;
                            break;
                        case 16:
                            i46 = length;
                            i48 = 2376;
                            i47 = 396;
                            i49 = 11880;
                            num3 = num4;
                            i50 = 768;
                            break;
                        case 32:
                            i46 = length;
                            i48 = 2376;
                            i47 = 396;
                            i49 = 11880;
                            num3 = num4;
                            i50 = 2000;
                            break;
                        case 64:
                            i49 = 19800;
                            i48 = 4752;
                            i46 = length;
                            i47 = 792;
                            num3 = num4;
                            i50 = 4000;
                            break;
                        case 128:
                            i49 = 20250;
                            i48 = 8100;
                            i46 = length;
                            i47 = 1620;
                            num3 = num4;
                            i50 = 4000;
                            break;
                        case 256:
                            i49 = 40500;
                            i48 = 8100;
                            i46 = length;
                            i47 = 1620;
                            num3 = num4;
                            i50 = 10000;
                            break;
                        case 512:
                            i49 = 108000;
                            i48 = 18000;
                            i46 = length;
                            i47 = 3600;
                            num3 = num4;
                            i50 = 14000;
                            break;
                        case 1024:
                            i49 = 216000;
                            i48 = 20480;
                            i46 = length;
                            i47 = 5120;
                            num3 = num4;
                            i50 = 20000;
                            break;
                        case 2048:
                            i49 = 245760;
                            i48 = 32768;
                            i46 = length;
                            i47 = 8192;
                            num3 = num4;
                            i50 = 20000;
                            break;
                        case 4096:
                            i49 = 245760;
                            i48 = 32768;
                            i46 = length;
                            i47 = 8192;
                            num3 = num4;
                            i50 = 50000;
                            break;
                        case 8192:
                            i49 = 522240;
                            i48 = 34816;
                            i46 = length;
                            i47 = 8704;
                            num3 = num4;
                            i50 = 50000;
                            break;
                        case 16384:
                            i49 = 589824;
                            i48 = 110400;
                            i46 = length;
                            i47 = 22080;
                            num3 = num4;
                            i50 = 135000;
                            break;
                        case 32768:
                            i49 = android.view.SurfaceControl.FX_SURFACE_MASK;
                            num3 = num4;
                            i48 = 184320;
                            i50 = 240000;
                            i46 = length;
                            i47 = 36864;
                            break;
                        case 65536:
                            i49 = 2073600;
                            num3 = num4;
                            i48 = 184320;
                            i50 = 240000;
                            i46 = length;
                            i47 = 36864;
                            break;
                        case 131072:
                            i49 = 4177920;
                            num3 = num4;
                            i48 = 696320;
                            i50 = 240000;
                            i46 = length;
                            i47 = 139264;
                            break;
                        case 262144:
                            i49 = 8355840;
                            i48 = 696320;
                            i46 = length;
                            i47 = 139264;
                            num3 = num4;
                            i50 = 480000;
                            break;
                        case 524288:
                            i49 = android.text.Spanned.SPAN_PRIORITY;
                            i48 = 696320;
                            i46 = length;
                            i47 = 139264;
                            num3 = num4;
                            i50 = 800000;
                            break;
                        default:
                            i46 = length;
                            android.util.Log.w(TAG, "Unrecognized level " + codecProfileLevel.level + str12 + mimeType);
                            i2 |= 1;
                            num3 = num4;
                            i47 = 0;
                            i48 = 0;
                            i49 = 0;
                            i50 = 0;
                            break;
                    }
                    android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr6 = codecProfileLevelArr5;
                    switch (codecProfileLevel.profile) {
                        case 1:
                        case 2:
                        case 65536:
                            i51 = i54;
                            z5 = true;
                            i52 = i50 * 1000;
                            break;
                        case 4:
                        case 32:
                        case 64:
                            i51 = i54;
                            android.util.Log.w(TAG, "Unsupported profile " + codecProfileLevel.profile + str12 + mimeType);
                            i2 |= 2;
                            z5 = false;
                            i52 = i50 * 1000;
                            break;
                        case 8:
                        case 524288:
                            i52 = i50 * com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_SELECTION_RANGE_START;
                            i51 = i54;
                            z5 = true;
                            break;
                        case 16:
                            i52 = i50 * 3000;
                            i51 = i54;
                            z5 = true;
                            break;
                        default:
                            i51 = i54;
                            android.util.Log.w(TAG, "Unrecognized profile " + codecProfileLevel.profile + str12 + mimeType);
                            i2 |= 1;
                            i52 = i50 * 1000;
                            z5 = true;
                            break;
                    }
                    if (z5) {
                        i2 &= -5;
                    }
                    j3 = java.lang.Math.max(i49, j3);
                    i56 = java.lang.Math.max(i47, i56);
                    i = java.lang.Math.max(i52, i);
                    i55 = java.lang.Math.max(i55, i48);
                    i54 = i51 + 1;
                    str12 = str12;
                    length = i46;
                    num4 = num3;
                    codecProfileLevelArr5 = codecProfileLevelArr6;
                }
                num = num4;
                int sqrt = (int) java.lang.Math.sqrt(i56 * 8);
                applyMacroBlockLimits(sqrt, sqrt, i56, j3, 16, 16, 1, 1);
            } else {
                java.lang.String str14 = " for ";
                num = num4;
                if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_MPEG2)) {
                    android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr7 = codecProfileLevelArr5;
                    int length2 = codecProfileLevelArr7.length;
                    int i57 = 64000;
                    int i58 = 4;
                    long j4 = 1485;
                    int i59 = 0;
                    int i60 = 99;
                    int i61 = 9;
                    int i62 = 11;
                    int i63 = 15;
                    while (i59 < length2) {
                        android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel2 = codecProfileLevelArr7[i59];
                        switch (codecProfileLevel2.profile) {
                            case 0:
                                i39 = length2;
                                switch (codecProfileLevel2.level) {
                                    case 1:
                                        i40 = 36;
                                        i41 = 40500;
                                        str8 = str14;
                                        str9 = str13;
                                        i42 = 15000;
                                        i43 = 30;
                                        i44 = 45;
                                        str10 = str11;
                                        i45 = 1620;
                                        z4 = true;
                                        break;
                                    default:
                                        android.util.Log.w(str13, "Unrecognized profile/level " + codecProfileLevel2.profile + "/" + codecProfileLevel2.level + str14 + mimeType);
                                        i58 |= 1;
                                        str8 = str14;
                                        str10 = str11;
                                        str9 = str13;
                                        i43 = 0;
                                        i44 = 0;
                                        i45 = 0;
                                        i40 = 0;
                                        i42 = 0;
                                        i41 = 0;
                                        z4 = true;
                                        break;
                                }
                            case 1:
                                i39 = length2;
                                switch (codecProfileLevel2.level) {
                                    case 0:
                                        str8 = str14;
                                        str9 = str13;
                                        i42 = 4000;
                                        i43 = 30;
                                        i44 = 22;
                                        i40 = 18;
                                        i41 = 11880;
                                        z4 = true;
                                        str10 = str11;
                                        i45 = 396;
                                        break;
                                    case 1:
                                        i40 = 36;
                                        i41 = 40500;
                                        str8 = str14;
                                        str9 = str13;
                                        i42 = 15000;
                                        i43 = 30;
                                        i44 = 45;
                                        str10 = str11;
                                        i45 = 1620;
                                        z4 = true;
                                        break;
                                    case 2:
                                        i44 = 90;
                                        i40 = 68;
                                        i41 = 183600;
                                        str8 = str14;
                                        str9 = str13;
                                        i42 = 60000;
                                        i43 = 60;
                                        str10 = str11;
                                        i45 = 6120;
                                        z4 = true;
                                        break;
                                    case 3:
                                        i44 = 120;
                                        i40 = 68;
                                        i41 = 244800;
                                        str8 = str14;
                                        str9 = str13;
                                        i42 = 80000;
                                        i43 = 60;
                                        str10 = str11;
                                        i45 = 8160;
                                        z4 = true;
                                        break;
                                    case 4:
                                        i44 = 120;
                                        i40 = 68;
                                        i41 = 489600;
                                        str8 = str14;
                                        str9 = str13;
                                        i42 = 80000;
                                        i43 = 60;
                                        str10 = str11;
                                        i45 = 8160;
                                        z4 = true;
                                        break;
                                    default:
                                        android.util.Log.w(str13, "Unrecognized profile/level " + codecProfileLevel2.profile + "/" + codecProfileLevel2.level + str14 + mimeType);
                                        i58 |= 1;
                                        str8 = str14;
                                        str10 = str11;
                                        str9 = str13;
                                        i43 = 0;
                                        i44 = 0;
                                        i45 = 0;
                                        i40 = 0;
                                        i42 = 0;
                                        i41 = 0;
                                        z4 = true;
                                        break;
                                }
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                                i39 = length2;
                                android.util.Log.i(str13, "Unsupported profile " + codecProfileLevel2.profile + str14 + mimeType);
                                i58 |= 2;
                                str8 = str14;
                                str10 = str11;
                                str9 = str13;
                                i43 = 0;
                                i44 = 0;
                                i45 = 0;
                                i40 = 0;
                                i42 = 0;
                                i41 = 0;
                                z4 = false;
                                break;
                            default:
                                i39 = length2;
                                android.util.Log.w(str13, str11 + codecProfileLevel2.profile + str14 + mimeType);
                                i58 |= 1;
                                str8 = str14;
                                str10 = str11;
                                str9 = str13;
                                i43 = 0;
                                i44 = 0;
                                i45 = 0;
                                i40 = 0;
                                i42 = 0;
                                i41 = 0;
                                z4 = true;
                                break;
                        }
                        if (z4) {
                            i58 &= -5;
                        }
                        j4 = java.lang.Math.max(i41, j4);
                        i60 = java.lang.Math.max(i45, i60);
                        i57 = java.lang.Math.max(i42 * 1000, i57);
                        i62 = java.lang.Math.max(i44, i62);
                        i61 = java.lang.Math.max(i40, i61);
                        i63 = java.lang.Math.max(i43, i63);
                        i59++;
                        length2 = i39;
                        str11 = str10;
                        str13 = str9;
                        str14 = str8;
                        mimeType = mimeType;
                        codecProfileLevelArr7 = codecProfileLevelArr7;
                    }
                    applyMacroBlockLimits(i62, i61, i60, j4, 16, 16, 1, 1);
                    this.mFrameRateRange = this.mFrameRateRange.intersect(12, java.lang.Integer.valueOf(i63));
                    i = i57;
                    i2 = i58;
                } else {
                    java.lang.String str15 = str14;
                    java.lang.String str16 = "Unrecognized profile ";
                    java.lang.String str17 = TAG;
                    if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_MPEG4)) {
                        android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr8 = codecProfileLevelArr5;
                        int length3 = codecProfileLevelArr8.length;
                        long j5 = 1485;
                        int i64 = 64000;
                        int i65 = 11;
                        int i66 = 9;
                        int i67 = 99;
                        int i68 = 0;
                        int i69 = 15;
                        while (i68 < length3) {
                            android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel3 = codecProfileLevelArr8[i68];
                            switch (codecProfileLevel3.profile) {
                                case 1:
                                    i31 = length3;
                                    java.lang.String str18 = str17;
                                    java.lang.String str19 = str15;
                                    codecProfileLevelArr4 = codecProfileLevelArr8;
                                    switch (codecProfileLevel3.level) {
                                        case 1:
                                            str5 = str19;
                                            str6 = str18;
                                            str7 = str16;
                                            i32 = 99;
                                            i33 = 11;
                                            i34 = 15;
                                            z2 = true;
                                            z3 = true;
                                            i35 = 64;
                                            i36 = i53;
                                            i37 = 9;
                                            i38 = 1485;
                                            break;
                                        case 2:
                                            str5 = str19;
                                            str6 = str18;
                                            str7 = str16;
                                            i32 = 99;
                                            i33 = 11;
                                            i34 = 15;
                                            z2 = true;
                                            z3 = true;
                                            i35 = 128;
                                            i36 = i53;
                                            i37 = 9;
                                            i38 = 1485;
                                            break;
                                        case 4:
                                            str5 = str19;
                                            str6 = str18;
                                            str7 = str16;
                                            i32 = 99;
                                            i33 = 11;
                                            i34 = 30;
                                            z2 = true;
                                            z3 = false;
                                            i35 = 64;
                                            i36 = i53;
                                            i37 = 9;
                                            i38 = 1485;
                                            break;
                                        case 8:
                                            str5 = str19;
                                            str6 = str18;
                                            str7 = str16;
                                            i33 = 22;
                                            i34 = 30;
                                            z2 = true;
                                            z3 = false;
                                            i35 = 128;
                                            i36 = i53;
                                            i32 = 396;
                                            i38 = 5940;
                                            i37 = 18;
                                            break;
                                        case 16:
                                            str5 = str19;
                                            str6 = str18;
                                            str7 = str16;
                                            i32 = 396;
                                            i33 = 22;
                                            i34 = 30;
                                            z2 = true;
                                            z3 = false;
                                            i35 = 384;
                                            i36 = i53;
                                            i37 = 18;
                                            i38 = 11880;
                                            break;
                                        case 64:
                                            i33 = 40;
                                            i32 = 1200;
                                            str5 = str19;
                                            str6 = str18;
                                            i35 = 4000;
                                            i34 = 30;
                                            z2 = true;
                                            z3 = false;
                                            str7 = str16;
                                            i36 = i53;
                                            i38 = 36000;
                                            i37 = 30;
                                            break;
                                        case 128:
                                            i32 = 1620;
                                            str5 = str19;
                                            str6 = str18;
                                            i35 = 8000;
                                            i34 = 30;
                                            z2 = true;
                                            z3 = false;
                                            str7 = str16;
                                            i36 = i53;
                                            i38 = 40500;
                                            i37 = 36;
                                            i33 = 45;
                                            break;
                                        case 256:
                                            i33 = 80;
                                            i32 = 3600;
                                            str5 = str19;
                                            str6 = str18;
                                            i35 = 12000;
                                            i34 = 30;
                                            z2 = true;
                                            z3 = false;
                                            str7 = str16;
                                            i36 = i53;
                                            i38 = 108000;
                                            i37 = 45;
                                            break;
                                        default:
                                            android.util.Log.w(str18, "Unrecognized profile/level " + codecProfileLevel3.profile + "/" + codecProfileLevel3.level + str19 + mimeType);
                                            str5 = str19;
                                            str6 = str18;
                                            str7 = str16;
                                            i37 = 0;
                                            i32 = 0;
                                            i33 = 0;
                                            i34 = 0;
                                            i35 = 0;
                                            z2 = true;
                                            z3 = false;
                                            i36 = i53 | 1;
                                            i38 = 0;
                                            break;
                                    }
                                case 2:
                                case 4:
                                case 8:
                                case 16:
                                case 32:
                                case 64:
                                case 128:
                                case 256:
                                case 512:
                                case 1024:
                                case 2048:
                                case 4096:
                                case 8192:
                                case 16384:
                                    java.lang.String str20 = str17;
                                    java.lang.String str21 = str15;
                                    i31 = length3;
                                    codecProfileLevelArr4 = codecProfileLevelArr8;
                                    android.util.Log.i(str20, "Unsupported profile " + codecProfileLevel3.profile + str21 + mimeType);
                                    str5 = str21;
                                    str6 = str20;
                                    str7 = str16;
                                    i37 = 0;
                                    i32 = 0;
                                    i33 = 0;
                                    i34 = 0;
                                    i35 = 0;
                                    z2 = false;
                                    z3 = false;
                                    i36 = i53 | 2;
                                    i38 = 0;
                                    break;
                                case 32768:
                                    switch (codecProfileLevel3.level) {
                                        case 1:
                                        case 4:
                                            i31 = length3;
                                            str6 = str17;
                                            str5 = str15;
                                            i35 = 128;
                                            i32 = 99;
                                            i33 = 11;
                                            z2 = true;
                                            z3 = false;
                                            codecProfileLevelArr4 = codecProfileLevelArr8;
                                            str7 = str16;
                                            i37 = 9;
                                            i36 = i53;
                                            i38 = 2970;
                                            i34 = 30;
                                            break;
                                        case 8:
                                            i31 = length3;
                                            str6 = str17;
                                            str5 = str15;
                                            i35 = 384;
                                            i32 = 396;
                                            i33 = 22;
                                            z2 = true;
                                            z3 = false;
                                            codecProfileLevelArr4 = codecProfileLevelArr8;
                                            str7 = str16;
                                            i37 = 18;
                                            i36 = i53;
                                            i38 = 5940;
                                            i34 = 30;
                                            break;
                                        case 16:
                                            i31 = length3;
                                            str6 = str17;
                                            str5 = str15;
                                            i35 = 768;
                                            i32 = 396;
                                            i33 = 22;
                                            i34 = 30;
                                            z2 = true;
                                            z3 = false;
                                            codecProfileLevelArr4 = codecProfileLevelArr8;
                                            str7 = str16;
                                            i37 = 18;
                                            i36 = i53;
                                            i38 = 11880;
                                            break;
                                        case 24:
                                            i31 = length3;
                                            str6 = str17;
                                            str5 = str15;
                                            i35 = 1500;
                                            i32 = 396;
                                            i33 = 22;
                                            i34 = 30;
                                            z2 = true;
                                            z3 = false;
                                            codecProfileLevelArr4 = codecProfileLevelArr8;
                                            str7 = str16;
                                            i37 = 18;
                                            i36 = i53;
                                            i38 = 11880;
                                            break;
                                        case 32:
                                            i33 = 44;
                                            str6 = str17;
                                            str5 = str15;
                                            i35 = 3000;
                                            z2 = true;
                                            z3 = false;
                                            codecProfileLevelArr4 = codecProfileLevelArr8;
                                            i37 = 36;
                                            str7 = str16;
                                            i34 = 30;
                                            i36 = i53;
                                            i38 = 23760;
                                            i31 = length3;
                                            i32 = 792;
                                            break;
                                        case 128:
                                            str6 = str17;
                                            str5 = str15;
                                            i35 = 8000;
                                            z2 = true;
                                            z3 = false;
                                            codecProfileLevelArr4 = codecProfileLevelArr8;
                                            i37 = 36;
                                            str7 = str16;
                                            i33 = 45;
                                            i36 = i53;
                                            i38 = 48600;
                                            i34 = 30;
                                            i31 = length3;
                                            i32 = 1620;
                                            break;
                                        default:
                                            java.lang.String str22 = str15;
                                            java.lang.String str23 = str17;
                                            android.util.Log.w(str23, "Unrecognized profile/level " + codecProfileLevel3.profile + "/" + codecProfileLevel3.level + str22 + mimeType);
                                            codecProfileLevelArr4 = codecProfileLevelArr8;
                                            i31 = length3;
                                            str5 = str22;
                                            str6 = str23;
                                            str7 = str16;
                                            i37 = 0;
                                            i32 = 0;
                                            i33 = 0;
                                            i34 = 0;
                                            i35 = 0;
                                            z2 = true;
                                            z3 = false;
                                            i36 = i53 | 1;
                                            i38 = 0;
                                            break;
                                    }
                                default:
                                    i31 = length3;
                                    java.lang.String str24 = str17;
                                    java.lang.String str25 = str15;
                                    codecProfileLevelArr4 = codecProfileLevelArr8;
                                    java.lang.String str26 = str16;
                                    android.util.Log.w(str24, str26 + codecProfileLevel3.profile + str25 + mimeType);
                                    str7 = str26;
                                    str5 = str25;
                                    str6 = str24;
                                    i36 = i53 | 1;
                                    i37 = 0;
                                    i32 = 0;
                                    i33 = 0;
                                    i34 = 0;
                                    i35 = 0;
                                    i38 = 0;
                                    z2 = true;
                                    z3 = false;
                                    break;
                            }
                            if (z2) {
                                i36 &= -5;
                            }
                            int i70 = i68;
                            j5 = java.lang.Math.max(i38, j5);
                            i67 = java.lang.Math.max(i32, i67);
                            i64 = java.lang.Math.max(i35 * 1000, i64);
                            if (z3) {
                                max = java.lang.Math.max(i33, i65);
                                i66 = java.lang.Math.max(i37, i66);
                                i69 = java.lang.Math.max(i34, i69);
                            } else {
                                int sqrt2 = (int) java.lang.Math.sqrt(i32 * 2);
                                max = java.lang.Math.max(sqrt2, i65);
                                i66 = java.lang.Math.max(sqrt2, i66);
                                i69 = java.lang.Math.max(java.lang.Math.max(i34, 60), i69);
                            }
                            i65 = max;
                            i68 = i70 + 1;
                            length3 = i31;
                            i53 = i36;
                            codecProfileLevelArr8 = codecProfileLevelArr4;
                            str16 = str7;
                            str17 = str6;
                            str15 = str5;
                        }
                        applyMacroBlockLimits(i65, i66, i67, j5, 16, 16, 1, 1);
                        this.mFrameRateRange = this.mFrameRateRange.intersect(12, java.lang.Integer.valueOf(i69));
                        i = i64;
                        i2 = i53;
                    } else {
                        java.lang.String str27 = str17;
                        java.lang.String str28 = str15;
                        java.lang.String str29 = str16;
                        if (mimeType.equalsIgnoreCase("video/3gpp")) {
                            android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr9 = codecProfileLevelArr5;
                            int length4 = codecProfileLevelArr9.length;
                            int i71 = 16;
                            long j6 = 1485;
                            int i72 = 64000;
                            int i73 = 11;
                            int i74 = 9;
                            int i75 = 11;
                            int i76 = 9;
                            int i77 = 99;
                            int i78 = 15;
                            int i79 = 0;
                            while (i79 < length4) {
                                android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel4 = codecProfileLevelArr9[i79];
                                int i80 = length4;
                                switch (codecProfileLevel4.level) {
                                    case 1:
                                        codecProfileLevelArr3 = codecProfileLevelArr9;
                                        i14 = i73;
                                        i15 = i14;
                                        i16 = i79;
                                        i17 = i53;
                                        i18 = i71;
                                        str2 = str27;
                                        str3 = str28;
                                        z = true;
                                        i19 = 11;
                                        i20 = 9;
                                        i21 = 1;
                                        i22 = 15;
                                        i23 = i74;
                                        i24 = i23;
                                        i25 = 1485;
                                        break;
                                    case 2:
                                        codecProfileLevelArr3 = codecProfileLevelArr9;
                                        i14 = i73;
                                        i15 = i14;
                                        i18 = i71;
                                        str3 = str28;
                                        z = true;
                                        i19 = 22;
                                        i22 = 30;
                                        i23 = i74;
                                        i24 = i23;
                                        i25 = 5940;
                                        i17 = i53;
                                        i20 = 18;
                                        i16 = i79;
                                        str2 = str27;
                                        i21 = 2;
                                        break;
                                    case 4:
                                        codecProfileLevelArr3 = codecProfileLevelArr9;
                                        i14 = i73;
                                        i15 = i14;
                                        i17 = i53;
                                        i18 = i71;
                                        str3 = str28;
                                        z = true;
                                        i19 = 22;
                                        i20 = 18;
                                        i22 = 30;
                                        i23 = i74;
                                        i24 = i23;
                                        i25 = 11880;
                                        i16 = i79;
                                        str2 = str27;
                                        i21 = 6;
                                        break;
                                    case 8:
                                        codecProfileLevelArr3 = codecProfileLevelArr9;
                                        i14 = i73;
                                        i15 = i14;
                                        i17 = i53;
                                        i18 = i71;
                                        str3 = str28;
                                        z = true;
                                        i19 = 22;
                                        i20 = 18;
                                        i22 = 30;
                                        i23 = i74;
                                        i24 = i23;
                                        i25 = 11880;
                                        i16 = i79;
                                        str2 = str27;
                                        i21 = 32;
                                        break;
                                    case 16:
                                        codecProfileLevelArr3 = codecProfileLevelArr9;
                                        z = codecProfileLevel4.profile == 1 || codecProfileLevel4.profile == 4;
                                        if (z) {
                                            i26 = i73;
                                            i27 = i71;
                                            i23 = i74;
                                        } else {
                                            i27 = 4;
                                            i26 = 1;
                                            i23 = 1;
                                        }
                                        i14 = i26;
                                        i15 = i73;
                                        i18 = i27;
                                        str3 = str28;
                                        i19 = 11;
                                        i22 = 15;
                                        i24 = i74;
                                        i16 = i79;
                                        str2 = str27;
                                        i25 = 1485;
                                        i21 = 2;
                                        i17 = i53;
                                        i20 = 9;
                                        break;
                                    case 32:
                                        codecProfileLevelArr3 = codecProfileLevelArr9;
                                        i18 = 4;
                                        i15 = i73;
                                        i16 = i79;
                                        str2 = str27;
                                        str3 = str28;
                                        z = false;
                                        i19 = 22;
                                        i14 = 1;
                                        i22 = 60;
                                        i24 = i74;
                                        i21 = 64;
                                        i25 = 19800;
                                        i23 = 1;
                                        i17 = i53;
                                        i20 = 18;
                                        break;
                                    case 64:
                                        codecProfileLevelArr3 = codecProfileLevelArr9;
                                        i18 = 4;
                                        i15 = i73;
                                        i16 = i79;
                                        str2 = str27;
                                        str3 = str28;
                                        z = false;
                                        i19 = 45;
                                        i14 = 1;
                                        i22 = 60;
                                        i24 = i74;
                                        i21 = 128;
                                        i25 = 40500;
                                        i23 = 1;
                                        i17 = i53;
                                        i20 = 18;
                                        break;
                                    case 128:
                                        codecProfileLevelArr3 = codecProfileLevelArr9;
                                        i18 = 4;
                                        i15 = i73;
                                        str3 = str28;
                                        z = false;
                                        i19 = 45;
                                        i14 = 1;
                                        i22 = 60;
                                        i24 = i74;
                                        i25 = 81000;
                                        i17 = i53;
                                        i20 = 36;
                                        i23 = 1;
                                        i16 = i79;
                                        str2 = str27;
                                        i21 = 256;
                                        break;
                                    default:
                                        codecProfileLevelArr3 = codecProfileLevelArr9;
                                        str3 = str28;
                                        i16 = i79;
                                        str2 = str27;
                                        android.util.Log.w(str2, "Unrecognized profile/level " + codecProfileLevel4.profile + "/" + codecProfileLevel4.level + str3 + mimeType);
                                        i14 = i73;
                                        i15 = i14;
                                        i24 = i74;
                                        i17 = i53 | 1;
                                        i18 = i71;
                                        z = false;
                                        i19 = 0;
                                        i20 = 0;
                                        i21 = 0;
                                        i22 = 0;
                                        i23 = i24;
                                        i25 = 0;
                                        break;
                                }
                                int i81 = i78;
                                switch (codecProfileLevel4.profile) {
                                    case 1:
                                    case 2:
                                    case 4:
                                    case 8:
                                    case 16:
                                    case 32:
                                    case 64:
                                    case 128:
                                    case 256:
                                        i28 = i76;
                                        str4 = str29;
                                        break;
                                    default:
                                        i28 = i76;
                                        str4 = str29;
                                        android.util.Log.w(str2, str4 + codecProfileLevel4.profile + str3 + mimeType);
                                        i17 |= 1;
                                        break;
                                }
                                if (z) {
                                    i30 = 11;
                                    i29 = 9;
                                } else {
                                    this.mAllowMbOverride = true;
                                    i29 = i23;
                                    i30 = i14;
                                }
                                java.lang.String str30 = mimeType;
                                j6 = java.lang.Math.max(i25, j6);
                                i77 = java.lang.Math.max(i19 * i20, i77);
                                i72 = java.lang.Math.max(64000 * i21, i72);
                                i75 = java.lang.Math.max(i19, i75);
                                int max2 = java.lang.Math.max(i20, i28);
                                int max3 = java.lang.Math.max(i22, i81);
                                i73 = java.lang.Math.min(i30, i15);
                                i74 = java.lang.Math.min(i29, i24);
                                i78 = max3;
                                str28 = str3;
                                str29 = str4;
                                str27 = str2;
                                i53 = i17 & (-5);
                                length4 = i80;
                                mimeType = str30;
                                i71 = i18;
                                i76 = max2;
                                i79 = i16 + 1;
                                codecProfileLevelArr9 = codecProfileLevelArr3;
                            }
                            int i82 = i73;
                            int i83 = i74;
                            int i84 = i76;
                            int i85 = i78;
                            if (!this.mAllowMbOverride) {
                                this.mBlockAspectRatioRange = android.util.Range.create(new android.util.Rational(11, 9), new android.util.Rational(11, 9));
                            }
                            applyMacroBlockLimits(i82, i83, i75, i84, i77, j6, 16, 16, i71, i71);
                            this.mFrameRateRange = android.util.Range.create(num, java.lang.Integer.valueOf(i85));
                            i = i72;
                            i2 = i53;
                        } else {
                            java.lang.Integer num5 = num;
                            java.lang.String str31 = str27;
                            if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_VP8)) {
                                for (android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel5 : codecProfileLevelArr5) {
                                    switch (codecProfileLevel5.level) {
                                        case 1:
                                        case 2:
                                        case 4:
                                        case 8:
                                            break;
                                        default:
                                            android.util.Log.w(str31, "Unrecognized level " + codecProfileLevel5.level + str28 + mimeType);
                                            i53 |= 1;
                                            break;
                                    }
                                    switch (codecProfileLevel5.profile) {
                                        case 1:
                                            break;
                                        default:
                                            android.util.Log.w(str31, str29 + codecProfileLevel5.profile + str28 + mimeType);
                                            i53 |= 1;
                                            break;
                                    }
                                    i53 &= -5;
                                }
                                applyMacroBlockLimits(32767, 32767, Integer.MAX_VALUE, 2147483647L, 16, 16, 1, 1);
                                i = 100000000;
                                num = num5;
                                i2 = i53;
                            } else {
                                android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr10 = codecProfileLevelArr5;
                                if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_VP9)) {
                                    int length5 = codecProfileLevelArr10.length;
                                    long j7 = 829440;
                                    int i86 = 36864;
                                    i = 200000;
                                    int i87 = 512;
                                    int i88 = 0;
                                    while (i88 < length5) {
                                        android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel6 = codecProfileLevelArr10[i88];
                                        int i89 = length5;
                                        switch (codecProfileLevel6.level) {
                                            case 1:
                                                i10 = 36864;
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                num2 = num5;
                                                i11 = 200;
                                                i12 = 512;
                                                i13 = i;
                                                j2 = 829440;
                                                break;
                                            case 2:
                                                i10 = 73728;
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                num2 = num5;
                                                i11 = 800;
                                                i12 = 768;
                                                i13 = i;
                                                j2 = 2764800;
                                                break;
                                            case 4:
                                                i10 = 122880;
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                num2 = num5;
                                                i11 = 1800;
                                                i12 = 960;
                                                i13 = i;
                                                j2 = 4608000;
                                                break;
                                            case 8:
                                                i10 = 245760;
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                num2 = num5;
                                                i11 = 3600;
                                                i12 = 1344;
                                                i13 = i;
                                                j2 = 9216000;
                                                break;
                                            case 16:
                                                i10 = 552960;
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                num2 = num5;
                                                i11 = 7200;
                                                i12 = 2048;
                                                i13 = i;
                                                j2 = 20736000;
                                                break;
                                            case 32:
                                                i10 = android.view.SurfaceControl.FX_SURFACE_MASK;
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                num2 = num5;
                                                i11 = 12000;
                                                i12 = 2752;
                                                i13 = i;
                                                j2 = 36864000;
                                                break;
                                            case 64:
                                                i10 = 2228224;
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                num2 = num5;
                                                i11 = 18000;
                                                i12 = 4160;
                                                i13 = i;
                                                j2 = 83558400;
                                                break;
                                            case 128:
                                                i10 = 2228224;
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                num2 = num5;
                                                i11 = 30000;
                                                i12 = 4160;
                                                i13 = i;
                                                j2 = 160432128;
                                                break;
                                            case 256:
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                i11 = 60000;
                                                num2 = num5;
                                                i12 = 8384;
                                                i10 = 8912896;
                                                i13 = i;
                                                j2 = 311951360;
                                                break;
                                            case 512:
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                i11 = 120000;
                                                num2 = num5;
                                                i12 = 8384;
                                                i10 = 8912896;
                                                i13 = i;
                                                j2 = 588251136;
                                                break;
                                            case 1024:
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                i11 = 180000;
                                                num2 = num5;
                                                i12 = 8384;
                                                i10 = 8912896;
                                                i13 = i;
                                                j2 = 1176502272;
                                                break;
                                            case 2048:
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                i11 = 180000;
                                                num2 = num5;
                                                i12 = 16832;
                                                i10 = 35651584;
                                                i13 = i;
                                                j2 = 1176502272;
                                                break;
                                            case 4096:
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                num2 = num5;
                                                i12 = 16832;
                                                i10 = 35651584;
                                                i11 = 240000;
                                                i13 = i;
                                                j2 = 2353004544L;
                                                break;
                                            case 8192:
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                i11 = 480000;
                                                num2 = num5;
                                                i12 = 16832;
                                                i10 = 35651584;
                                                i13 = i;
                                                j2 = 4706009088L;
                                                break;
                                            default:
                                                android.util.Log.w(str31, "Unrecognized level " + codecProfileLevel6.level + str28 + mimeType);
                                                i53 |= 1;
                                                codecProfileLevelArr2 = codecProfileLevelArr10;
                                                num2 = num5;
                                                i12 = 0;
                                                i10 = 0;
                                                i11 = 0;
                                                i13 = i;
                                                j2 = 0;
                                                break;
                                        }
                                        int i90 = i88;
                                        switch (codecProfileLevel6.profile) {
                                            case 1:
                                            case 2:
                                            case 4:
                                            case 8:
                                            case 4096:
                                            case 8192:
                                            case 16384:
                                            case 32768:
                                                break;
                                            default:
                                                android.util.Log.w(str31, str29 + codecProfileLevel6.profile + str28 + mimeType);
                                                i53 |= 1;
                                                break;
                                        }
                                        i53 &= -5;
                                        j7 = java.lang.Math.max(j2, j7);
                                        i86 = java.lang.Math.max(i10, i86);
                                        i = java.lang.Math.max(i11 * 1000, i13);
                                        i87 = java.lang.Math.max(i12, i87);
                                        i88 = i90 + 1;
                                        length5 = i89;
                                        num5 = num2;
                                        codecProfileLevelArr10 = codecProfileLevelArr2;
                                    }
                                    num = num5;
                                    int divUp = android.media.Utils.divUp(i87, 8);
                                    applyMacroBlockLimits(divUp, divUp, android.media.Utils.divUp(i86, 64), android.media.Utils.divUp(j7, 64L), 8, 8, 1, 1);
                                    i2 = i53;
                                } else {
                                    num = num5;
                                    if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_HEVC)) {
                                        int length6 = codecProfileLevelArr10.length;
                                        i = 128000;
                                        long j8 = 8640;
                                        int i91 = 576;
                                        int i92 = 0;
                                        while (i92 < length6) {
                                            android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel7 = codecProfileLevelArr10[i92];
                                            switch (codecProfileLevel7.level) {
                                                case 1:
                                                case 2:
                                                    i8 = 36864;
                                                    d = 15.0d;
                                                    i9 = 128;
                                                    break;
                                                case 4:
                                                case 8:
                                                    i8 = 122880;
                                                    d = 30.0d;
                                                    i9 = 1500;
                                                    break;
                                                case 16:
                                                case 32:
                                                    i8 = 245760;
                                                    d = 30.0d;
                                                    i9 = 3000;
                                                    break;
                                                case 64:
                                                case 128:
                                                    i8 = 552960;
                                                    d = 30.0d;
                                                    i9 = 6000;
                                                    break;
                                                case 256:
                                                case 512:
                                                    i8 = android.view.SurfaceControl.FX_SURFACE_MASK;
                                                    d = 33.75d;
                                                    i9 = 10000;
                                                    break;
                                                case 1024:
                                                    i8 = 2228224;
                                                    d = 30.0d;
                                                    i9 = 12000;
                                                    break;
                                                case 2048:
                                                    i8 = 2228224;
                                                    d = 30.0d;
                                                    i9 = 30000;
                                                    break;
                                                case 4096:
                                                    i8 = 2228224;
                                                    d = 60.0d;
                                                    i9 = 20000;
                                                    break;
                                                case 8192:
                                                    i8 = 2228224;
                                                    d = 60.0d;
                                                    i9 = 50000;
                                                    break;
                                                case 16384:
                                                    d = 30.0d;
                                                    i9 = 25000;
                                                    i8 = 8912896;
                                                    break;
                                                case 32768:
                                                    d = 30.0d;
                                                    i9 = 100000;
                                                    i8 = 8912896;
                                                    break;
                                                case 65536:
                                                    d = 60.0d;
                                                    i9 = 40000;
                                                    i8 = 8912896;
                                                    break;
                                                case 131072:
                                                    d = 60.0d;
                                                    i9 = 160000;
                                                    i8 = 8912896;
                                                    break;
                                                case 262144:
                                                    d = 120.0d;
                                                    i9 = 60000;
                                                    i8 = 8912896;
                                                    break;
                                                case 524288:
                                                    d = 120.0d;
                                                    i8 = 8912896;
                                                    i9 = 240000;
                                                    break;
                                                case 1048576:
                                                    d = 30.0d;
                                                    i9 = 60000;
                                                    i8 = 35651584;
                                                    break;
                                                case 2097152:
                                                    d = 30.0d;
                                                    i8 = 35651584;
                                                    i9 = 240000;
                                                    break;
                                                case 4194304:
                                                    d = 60.0d;
                                                    i9 = 120000;
                                                    i8 = 35651584;
                                                    break;
                                                case 8388608:
                                                    d = 60.0d;
                                                    i9 = 480000;
                                                    i8 = 35651584;
                                                    break;
                                                case 16777216:
                                                    d = 120.0d;
                                                    i8 = 35651584;
                                                    i9 = 240000;
                                                    break;
                                                case 33554432:
                                                    d = 120.0d;
                                                    i9 = 800000;
                                                    i8 = 35651584;
                                                    break;
                                                default:
                                                    android.util.Log.w(str31, "Unrecognized level " + codecProfileLevel7.level + str28 + mimeType);
                                                    i53 |= 1;
                                                    d = 0.0d;
                                                    i8 = 0;
                                                    i9 = 0;
                                                    break;
                                            }
                                            switch (codecProfileLevel7.profile) {
                                                case 1:
                                                case 2:
                                                case 4:
                                                case 4096:
                                                case 8192:
                                                    break;
                                                default:
                                                    android.util.Log.w(str31, str29 + codecProfileLevel7.profile + str28 + mimeType);
                                                    i53 |= 1;
                                                    break;
                                            }
                                            i53 &= -5;
                                            j8 = java.lang.Math.max((int) (r6 * d), j8);
                                            i91 = java.lang.Math.max(i8 >> 6, i91);
                                            i = java.lang.Math.max(i9 * 1000, i);
                                            i92++;
                                            str31 = str31;
                                        }
                                        int sqrt3 = (int) java.lang.Math.sqrt(i91 * 8);
                                        applyMacroBlockLimits(sqrt3, sqrt3, i91, j8, 8, 8, 1, 1);
                                        i2 = i53;
                                    } else {
                                        java.lang.String str32 = str31;
                                        android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr11 = codecProfileLevelArr10;
                                        if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_VIDEO_AV1)) {
                                            int length7 = codecProfileLevelArr11.length;
                                            long j9 = 829440;
                                            int i93 = 36864;
                                            int i94 = 512;
                                            i = 200000;
                                            int i95 = 0;
                                            while (i95 < length7) {
                                                android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel8 = codecProfileLevelArr11[i95];
                                                switch (codecProfileLevel8.level) {
                                                    case 1:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i3 = 1500;
                                                        i4 = i53;
                                                        i5 = length7;
                                                        i6 = 2048;
                                                        i7 = 147456;
                                                        j = 5529600;
                                                        str = str32;
                                                        break;
                                                    case 2:
                                                    case 4:
                                                    case 8:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i3 = 3000;
                                                        i4 = i53;
                                                        i5 = length7;
                                                        i6 = 2816;
                                                        i7 = 278784;
                                                        j = 10454400;
                                                        str = str32;
                                                        break;
                                                    case 16:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i3 = 6000;
                                                        i4 = i53;
                                                        i5 = length7;
                                                        i6 = 4352;
                                                        i7 = 665856;
                                                        j = 24969600;
                                                        str = str32;
                                                        break;
                                                    case 32:
                                                    case 64:
                                                    case 128:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i3 = 10000;
                                                        i4 = i53;
                                                        i5 = length7;
                                                        i6 = 5504;
                                                        i7 = 1065024;
                                                        j = 39938400;
                                                        str = str32;
                                                        break;
                                                    case 256:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i3 = 12000;
                                                        i4 = i53;
                                                        i5 = length7;
                                                        i6 = 6144;
                                                        i7 = 2359296;
                                                        j = 77856768;
                                                        str = str32;
                                                        break;
                                                    case 512:
                                                    case 1024:
                                                    case 2048:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i3 = 20000;
                                                        i4 = i53;
                                                        i5 = length7;
                                                        i6 = 6144;
                                                        i7 = 2359296;
                                                        j = 155713536;
                                                        str = str32;
                                                        break;
                                                    case 4096:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i5 = length7;
                                                        i3 = 30000;
                                                        i6 = 8192;
                                                        i4 = i53;
                                                        j = 273715200;
                                                        str = str32;
                                                        i7 = 8912896;
                                                        break;
                                                    case 8192:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i5 = length7;
                                                        i3 = 40000;
                                                        i6 = 8192;
                                                        i4 = i53;
                                                        j = 547430400;
                                                        str = str32;
                                                        i7 = 8912896;
                                                        break;
                                                    case 16384:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i5 = length7;
                                                        i3 = 60000;
                                                        i6 = 8192;
                                                        i4 = i53;
                                                        j = 1094860800;
                                                        str = str32;
                                                        i7 = 8912896;
                                                        break;
                                                    case 32768:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i5 = length7;
                                                        i3 = 60000;
                                                        i6 = 8192;
                                                        i4 = i53;
                                                        j = 1176502272;
                                                        str = str32;
                                                        i7 = 8912896;
                                                        break;
                                                    case 65536:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i5 = length7;
                                                        i3 = 60000;
                                                        i6 = 16384;
                                                        i4 = i53;
                                                        j = 1176502272;
                                                        str = str32;
                                                        i7 = 35651584;
                                                        break;
                                                    case 131072:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i5 = length7;
                                                        i3 = 100000;
                                                        i6 = 16384;
                                                        i4 = i53;
                                                        j = 2189721600L;
                                                        str = str32;
                                                        i7 = 35651584;
                                                        break;
                                                    case 262144:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i5 = length7;
                                                        i3 = 160000;
                                                        i6 = 16384;
                                                        i4 = i53;
                                                        j = 4379443200L;
                                                        str = str32;
                                                        i7 = 35651584;
                                                        break;
                                                    case 524288:
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i5 = length7;
                                                        i3 = 160000;
                                                        i6 = 16384;
                                                        i4 = i53;
                                                        j = 4706009088L;
                                                        str = str32;
                                                        i7 = 35651584;
                                                        break;
                                                    default:
                                                        str = str32;
                                                        android.util.Log.w(str, "Unrecognized level " + codecProfileLevel8.level + str28 + mimeType);
                                                        codecProfileLevelArr = codecProfileLevelArr11;
                                                        i4 = i53 | 1;
                                                        j = 0;
                                                        i3 = 0;
                                                        i7 = 0;
                                                        i5 = length7;
                                                        i6 = 0;
                                                        break;
                                                }
                                                int i96 = i95;
                                                switch (codecProfileLevel8.profile) {
                                                    case 1:
                                                    case 2:
                                                    case 4096:
                                                    case 8192:
                                                        break;
                                                    default:
                                                        android.util.Log.w(str, str29 + codecProfileLevel8.profile + str28 + mimeType);
                                                        i4 |= 1;
                                                        break;
                                                }
                                                j9 = java.lang.Math.max(j, j9);
                                                i93 = java.lang.Math.max(i7, i93);
                                                i = java.lang.Math.max(i3 * 1000, i);
                                                i94 = java.lang.Math.max(i6, i94);
                                                i53 = i4 & (-5);
                                                str32 = str;
                                                length7 = i5;
                                                i95 = i96 + 1;
                                                codecProfileLevelArr11 = codecProfileLevelArr;
                                            }
                                            int divUp2 = android.media.Utils.divUp(i94, 8);
                                            applyMacroBlockLimits(divUp2, divUp2, android.media.Utils.divUp(i93, 64), android.media.Utils.divUp(j9, 64L), 8, 8, 1, 1);
                                            i2 = i53;
                                        } else {
                                            android.util.Log.w(str32, "Unsupported mime " + mimeType);
                                            i = 64000;
                                            i2 = 6;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.mBitrateRange = android.util.Range.create(num, java.lang.Integer.valueOf(i));
            this.mParent.mError |= i2;
        }
    }

    public static final class EncoderCapabilities {
        public static final int BITRATE_MODE_CBR = 2;
        public static final int BITRATE_MODE_CBR_FD = 3;
        public static final int BITRATE_MODE_CQ = 0;
        public static final int BITRATE_MODE_VBR = 1;
        private static final android.media.MediaCodecInfo.Feature[] bitrates = {new android.media.MediaCodecInfo.Feature("VBR", 1, true), new android.media.MediaCodecInfo.Feature("CBR", 2, false), new android.media.MediaCodecInfo.Feature("CQ", 0, false), new android.media.MediaCodecInfo.Feature("CBR-FD", 3, false)};
        private int mBitControl;
        private android.util.Range<java.lang.Integer> mComplexityRange;
        private java.lang.Integer mDefaultComplexity;
        private java.lang.Integer mDefaultQuality;
        private android.media.MediaCodecInfo.CodecCapabilities mParent;
        private android.util.Range<java.lang.Integer> mQualityRange;
        private java.lang.String mQualityScale;

        public android.util.Range<java.lang.Integer> getQualityRange() {
            return this.mQualityRange;
        }

        public android.util.Range<java.lang.Integer> getComplexityRange() {
            return this.mComplexityRange;
        }

        private static int parseBitrateMode(java.lang.String str) {
            for (android.media.MediaCodecInfo.Feature feature : bitrates) {
                if (feature.mName.equalsIgnoreCase(str)) {
                    return feature.mValue;
                }
            }
            return 0;
        }

        public boolean isBitrateModeSupported(int i) {
            for (android.media.MediaCodecInfo.Feature feature : bitrates) {
                if (i == feature.mValue) {
                    return ((1 << i) & this.mBitControl) != 0;
                }
            }
            return false;
        }

        private EncoderCapabilities() {
        }

        public static android.media.MediaCodecInfo.EncoderCapabilities create(android.media.MediaFormat mediaFormat, android.media.MediaCodecInfo.CodecCapabilities codecCapabilities) {
            android.media.MediaCodecInfo.EncoderCapabilities encoderCapabilities = new android.media.MediaCodecInfo.EncoderCapabilities();
            encoderCapabilities.init(mediaFormat, codecCapabilities);
            return encoderCapabilities;
        }

        private void init(android.media.MediaFormat mediaFormat, android.media.MediaCodecInfo.CodecCapabilities codecCapabilities) {
            this.mParent = codecCapabilities;
            this.mComplexityRange = android.util.Range.create(0, 0);
            this.mQualityRange = android.util.Range.create(0, 0);
            this.mBitControl = 2;
            applyLevelLimits();
            parseFromInfo(mediaFormat);
        }

        private void applyLevelLimits() {
            java.lang.String mimeType = this.mParent.getMimeType();
            if (mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_FLAC)) {
                this.mComplexityRange = android.util.Range.create(0, 8);
                this.mBitControl = 1;
            } else if (mimeType.equalsIgnoreCase("audio/3gpp") || mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_AMR_WB) || mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_G711_ALAW) || mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_G711_MLAW) || mimeType.equalsIgnoreCase(android.media.MediaFormat.MIMETYPE_AUDIO_MSGSM)) {
                this.mBitControl = 4;
            }
        }

        private void parseFromInfo(android.media.MediaFormat mediaFormat) {
            java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat.getMap();
            if (mediaFormat.containsKey("complexity-range")) {
                this.mComplexityRange = android.media.Utils.parseIntRange(mediaFormat.getString("complexity-range"), this.mComplexityRange);
            }
            if (mediaFormat.containsKey("quality-range")) {
                this.mQualityRange = android.media.Utils.parseIntRange(mediaFormat.getString("quality-range"), this.mQualityRange);
            }
            if (mediaFormat.containsKey("feature-bitrate-modes")) {
                this.mBitControl = 0;
                for (java.lang.String str : mediaFormat.getString("feature-bitrate-modes").split(",")) {
                    this.mBitControl = (1 << parseBitrateMode(str)) | this.mBitControl;
                }
            }
            try {
                this.mDefaultComplexity = java.lang.Integer.valueOf(java.lang.Integer.parseInt((java.lang.String) map.get("complexity-default")));
            } catch (java.lang.NumberFormatException e) {
            }
            try {
                this.mDefaultQuality = java.lang.Integer.valueOf(java.lang.Integer.parseInt((java.lang.String) map.get("quality-default")));
            } catch (java.lang.NumberFormatException e2) {
            }
            this.mQualityScale = (java.lang.String) map.get("quality-scale");
        }

        private boolean supports(java.lang.Integer num, java.lang.Integer num2, java.lang.Integer num3) {
            boolean z;
            if (num != null) {
                z = this.mComplexityRange.contains((android.util.Range<java.lang.Integer>) num);
            } else {
                z = true;
            }
            if (z && num2 != null) {
                z = this.mQualityRange.contains((android.util.Range<java.lang.Integer>) num2);
            }
            if (z && num3 != null) {
                android.media.MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr = this.mParent.profileLevels;
                int length = codecProfileLevelArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    if (codecProfileLevelArr[i].profile != num3.intValue()) {
                        i++;
                    } else {
                        num3 = null;
                        break;
                    }
                }
                return num3 == null;
            }
            return z;
        }

        public void getDefaultFormat(android.media.MediaFormat mediaFormat) {
            if (!this.mQualityRange.getUpper().equals(this.mQualityRange.getLower()) && this.mDefaultQuality != null) {
                mediaFormat.setInteger("quality", this.mDefaultQuality.intValue());
            }
            if (!this.mComplexityRange.getUpper().equals(this.mComplexityRange.getLower()) && this.mDefaultComplexity != null) {
                mediaFormat.setInteger(android.media.MediaFormat.KEY_COMPLEXITY, this.mDefaultComplexity.intValue());
            }
            for (android.media.MediaCodecInfo.Feature feature : bitrates) {
                if ((this.mBitControl & (1 << feature.mValue)) != 0) {
                    mediaFormat.setInteger(android.media.MediaFormat.KEY_BITRATE_MODE, feature.mValue);
                    return;
                }
            }
        }

        public boolean supportsFormat(android.media.MediaFormat mediaFormat) {
            java.util.Map<java.lang.String, java.lang.Object> map = mediaFormat.getMap();
            java.lang.String mimeType = this.mParent.getMimeType();
            java.lang.Integer num = (java.lang.Integer) map.get(android.media.MediaFormat.KEY_BITRATE_MODE);
            if (num != null && !isBitrateModeSupported(num.intValue())) {
                return false;
            }
            java.lang.Integer num2 = (java.lang.Integer) map.get(android.media.MediaFormat.KEY_COMPLEXITY);
            if (android.media.MediaFormat.MIMETYPE_AUDIO_FLAC.equalsIgnoreCase(mimeType)) {
                java.lang.Integer num3 = (java.lang.Integer) map.get(android.media.MediaFormat.KEY_FLAC_COMPRESSION_LEVEL);
                if (num2 == null) {
                    num2 = num3;
                } else if (num3 != null && !num2.equals(num3)) {
                    throw new java.lang.IllegalArgumentException("conflicting values for complexity and flac-compression-level");
                }
            }
            java.lang.Integer num4 = (java.lang.Integer) map.get(android.media.MediaFormat.KEY_PROFILE);
            if (android.media.MediaFormat.MIMETYPE_AUDIO_AAC.equalsIgnoreCase(mimeType)) {
                java.lang.Integer num5 = (java.lang.Integer) map.get(android.media.MediaFormat.KEY_AAC_PROFILE);
                if (num4 == null) {
                    num4 = num5;
                } else if (num5 != null && !num5.equals(num4)) {
                    throw new java.lang.IllegalArgumentException("conflicting values for profile and aac-profile");
                }
            }
            return supports(num2, (java.lang.Integer) map.get("quality"), num4);
        }
    }

    public static final class CodecProfileLevel {
        public static final int AACObjectELD = 39;
        public static final int AACObjectERLC = 17;
        public static final int AACObjectERScalable = 20;
        public static final int AACObjectHE = 5;
        public static final int AACObjectHE_PS = 29;
        public static final int AACObjectLC = 2;
        public static final int AACObjectLD = 23;
        public static final int AACObjectLTP = 4;
        public static final int AACObjectMain = 1;
        public static final int AACObjectSSR = 3;
        public static final int AACObjectScalable = 6;
        public static final int AACObjectXHE = 42;
        private static final int AC4BitstreamVersion0 = 1;
        private static final int AC4BitstreamVersion1 = 2;
        private static final int AC4BitstreamVersion2 = 4;
        public static final int AC4Level0 = 1;
        public static final int AC4Level1 = 2;
        public static final int AC4Level2 = 4;
        public static final int AC4Level3 = 8;
        public static final int AC4Level4 = 16;
        private static final int AC4PresentationVersion0 = 1;
        private static final int AC4PresentationVersion1 = 2;
        private static final int AC4PresentationVersion2 = 4;
        public static final int AC4Profile00 = 257;
        public static final int AC4Profile10 = 513;
        public static final int AC4Profile11 = 514;
        public static final int AC4Profile21 = 1026;
        public static final int AC4Profile22 = 1028;
        public static final int AV1Level2 = 1;
        public static final int AV1Level21 = 2;
        public static final int AV1Level22 = 4;
        public static final int AV1Level23 = 8;
        public static final int AV1Level3 = 16;
        public static final int AV1Level31 = 32;
        public static final int AV1Level32 = 64;
        public static final int AV1Level33 = 128;
        public static final int AV1Level4 = 256;
        public static final int AV1Level41 = 512;
        public static final int AV1Level42 = 1024;
        public static final int AV1Level43 = 2048;
        public static final int AV1Level5 = 4096;
        public static final int AV1Level51 = 8192;
        public static final int AV1Level52 = 16384;
        public static final int AV1Level53 = 32768;
        public static final int AV1Level6 = 65536;
        public static final int AV1Level61 = 131072;
        public static final int AV1Level62 = 262144;
        public static final int AV1Level63 = 524288;
        public static final int AV1Level7 = 1048576;
        public static final int AV1Level71 = 2097152;
        public static final int AV1Level72 = 4194304;
        public static final int AV1Level73 = 8388608;
        public static final int AV1ProfileMain10 = 2;
        public static final int AV1ProfileMain10HDR10 = 4096;
        public static final int AV1ProfileMain10HDR10Plus = 8192;
        public static final int AV1ProfileMain8 = 1;
        public static final int AVCLevel1 = 1;
        public static final int AVCLevel11 = 4;
        public static final int AVCLevel12 = 8;
        public static final int AVCLevel13 = 16;
        public static final int AVCLevel1b = 2;
        public static final int AVCLevel2 = 32;
        public static final int AVCLevel21 = 64;
        public static final int AVCLevel22 = 128;
        public static final int AVCLevel3 = 256;
        public static final int AVCLevel31 = 512;
        public static final int AVCLevel32 = 1024;
        public static final int AVCLevel4 = 2048;
        public static final int AVCLevel41 = 4096;
        public static final int AVCLevel42 = 8192;
        public static final int AVCLevel5 = 16384;
        public static final int AVCLevel51 = 32768;
        public static final int AVCLevel52 = 65536;
        public static final int AVCLevel6 = 131072;
        public static final int AVCLevel61 = 262144;
        public static final int AVCLevel62 = 524288;
        public static final int AVCProfileBaseline = 1;
        public static final int AVCProfileConstrainedBaseline = 65536;
        public static final int AVCProfileConstrainedHigh = 524288;
        public static final int AVCProfileExtended = 4;
        public static final int AVCProfileHigh = 8;
        public static final int AVCProfileHigh10 = 16;
        public static final int AVCProfileHigh422 = 32;
        public static final int AVCProfileHigh444 = 64;
        public static final int AVCProfileMain = 2;
        public static final int DTS_HDProfileHRA = 1;
        public static final int DTS_HDProfileLBR = 2;
        public static final int DTS_HDProfileMA = 4;
        public static final int DTS_UHDProfileP1 = 1;
        public static final int DTS_UHDProfileP2 = 2;
        public static final int DolbyVisionLevel8k30 = 1024;
        public static final int DolbyVisionLevel8k60 = 2048;
        public static final int DolbyVisionLevelFhd24 = 4;
        public static final int DolbyVisionLevelFhd30 = 8;
        public static final int DolbyVisionLevelFhd60 = 16;
        public static final int DolbyVisionLevelHd24 = 1;
        public static final int DolbyVisionLevelHd30 = 2;
        public static final int DolbyVisionLevelUhd120 = 512;
        public static final int DolbyVisionLevelUhd24 = 32;
        public static final int DolbyVisionLevelUhd30 = 64;
        public static final int DolbyVisionLevelUhd48 = 128;
        public static final int DolbyVisionLevelUhd60 = 256;
        public static final int DolbyVisionProfileDvav110 = 1024;
        public static final int DolbyVisionProfileDvavPen = 2;
        public static final int DolbyVisionProfileDvavPer = 1;
        public static final int DolbyVisionProfileDvavSe = 512;
        public static final int DolbyVisionProfileDvheDen = 8;
        public static final int DolbyVisionProfileDvheDer = 4;
        public static final int DolbyVisionProfileDvheDtb = 128;
        public static final int DolbyVisionProfileDvheDth = 64;
        public static final int DolbyVisionProfileDvheDtr = 16;
        public static final int DolbyVisionProfileDvheSt = 256;
        public static final int DolbyVisionProfileDvheStn = 32;
        public static final int H263Level10 = 1;
        public static final int H263Level20 = 2;
        public static final int H263Level30 = 4;
        public static final int H263Level40 = 8;
        public static final int H263Level45 = 16;
        public static final int H263Level50 = 32;
        public static final int H263Level60 = 64;
        public static final int H263Level70 = 128;
        public static final int H263ProfileBackwardCompatible = 4;
        public static final int H263ProfileBaseline = 1;
        public static final int H263ProfileH320Coding = 2;
        public static final int H263ProfileHighCompression = 32;
        public static final int H263ProfileHighLatency = 256;
        public static final int H263ProfileISWV2 = 8;
        public static final int H263ProfileISWV3 = 16;
        public static final int H263ProfileInterlace = 128;
        public static final int H263ProfileInternet = 64;
        public static final int HEVCHighTierLevel1 = 2;
        public static final int HEVCHighTierLevel2 = 8;
        public static final int HEVCHighTierLevel21 = 32;
        public static final int HEVCHighTierLevel3 = 128;
        public static final int HEVCHighTierLevel31 = 512;
        public static final int HEVCHighTierLevel4 = 2048;
        public static final int HEVCHighTierLevel41 = 8192;
        public static final int HEVCHighTierLevel5 = 32768;
        public static final int HEVCHighTierLevel51 = 131072;
        public static final int HEVCHighTierLevel52 = 524288;
        public static final int HEVCHighTierLevel6 = 2097152;
        public static final int HEVCHighTierLevel61 = 8388608;
        public static final int HEVCHighTierLevel62 = 33554432;
        private static final int HEVCHighTierLevels = 44739242;
        public static final int HEVCMainTierLevel1 = 1;
        public static final int HEVCMainTierLevel2 = 4;
        public static final int HEVCMainTierLevel21 = 16;
        public static final int HEVCMainTierLevel3 = 64;
        public static final int HEVCMainTierLevel31 = 256;
        public static final int HEVCMainTierLevel4 = 1024;
        public static final int HEVCMainTierLevel41 = 4096;
        public static final int HEVCMainTierLevel5 = 16384;
        public static final int HEVCMainTierLevel51 = 65536;
        public static final int HEVCMainTierLevel52 = 262144;
        public static final int HEVCMainTierLevel6 = 1048576;
        public static final int HEVCMainTierLevel61 = 4194304;
        public static final int HEVCMainTierLevel62 = 16777216;
        public static final int HEVCProfileMain = 1;
        public static final int HEVCProfileMain10 = 2;
        public static final int HEVCProfileMain10HDR10 = 4096;
        public static final int HEVCProfileMain10HDR10Plus = 8192;
        public static final int HEVCProfileMainStill = 4;
        public static final int MPEG2LevelH14 = 2;
        public static final int MPEG2LevelHL = 3;
        public static final int MPEG2LevelHP = 4;
        public static final int MPEG2LevelLL = 0;
        public static final int MPEG2LevelML = 1;
        public static final int MPEG2Profile422 = 2;
        public static final int MPEG2ProfileHigh = 5;
        public static final int MPEG2ProfileMain = 1;
        public static final int MPEG2ProfileSNR = 3;
        public static final int MPEG2ProfileSimple = 0;
        public static final int MPEG2ProfileSpatial = 4;
        public static final int MPEG4Level0 = 1;
        public static final int MPEG4Level0b = 2;
        public static final int MPEG4Level1 = 4;
        public static final int MPEG4Level2 = 8;
        public static final int MPEG4Level3 = 16;
        public static final int MPEG4Level3b = 24;
        public static final int MPEG4Level4 = 32;
        public static final int MPEG4Level4a = 64;
        public static final int MPEG4Level5 = 128;
        public static final int MPEG4Level6 = 256;
        public static final int MPEG4ProfileAdvancedCoding = 4096;
        public static final int MPEG4ProfileAdvancedCore = 8192;
        public static final int MPEG4ProfileAdvancedRealTime = 1024;
        public static final int MPEG4ProfileAdvancedScalable = 16384;
        public static final int MPEG4ProfileAdvancedSimple = 32768;
        public static final int MPEG4ProfileBasicAnimated = 256;
        public static final int MPEG4ProfileCore = 4;
        public static final int MPEG4ProfileCoreScalable = 2048;
        public static final int MPEG4ProfileHybrid = 512;
        public static final int MPEG4ProfileMain = 8;
        public static final int MPEG4ProfileNbit = 16;
        public static final int MPEG4ProfileScalableTexture = 32;
        public static final int MPEG4ProfileSimple = 1;
        public static final int MPEG4ProfileSimpleFBA = 128;
        public static final int MPEG4ProfileSimpleFace = 64;
        public static final int MPEG4ProfileSimpleScalable = 2;
        public static final int VP8Level_Version0 = 1;
        public static final int VP8Level_Version1 = 2;
        public static final int VP8Level_Version2 = 4;
        public static final int VP8Level_Version3 = 8;
        public static final int VP8ProfileMain = 1;
        public static final int VP9Level1 = 1;
        public static final int VP9Level11 = 2;
        public static final int VP9Level2 = 4;
        public static final int VP9Level21 = 8;
        public static final int VP9Level3 = 16;
        public static final int VP9Level31 = 32;
        public static final int VP9Level4 = 64;
        public static final int VP9Level41 = 128;
        public static final int VP9Level5 = 256;
        public static final int VP9Level51 = 512;
        public static final int VP9Level52 = 1024;
        public static final int VP9Level6 = 2048;
        public static final int VP9Level61 = 4096;
        public static final int VP9Level62 = 8192;
        public static final int VP9Profile0 = 1;
        public static final int VP9Profile1 = 2;
        public static final int VP9Profile2 = 4;
        public static final int VP9Profile2HDR = 4096;
        public static final int VP9Profile2HDR10Plus = 16384;
        public static final int VP9Profile3 = 8;
        public static final int VP9Profile3HDR = 8192;
        public static final int VP9Profile3HDR10Plus = 32768;
        public int level;
        public int profile;

        public boolean equals(java.lang.Object obj) {
            if (obj == null || !(obj instanceof android.media.MediaCodecInfo.CodecProfileLevel)) {
                return false;
            }
            android.media.MediaCodecInfo.CodecProfileLevel codecProfileLevel = (android.media.MediaCodecInfo.CodecProfileLevel) obj;
            return codecProfileLevel.profile == this.profile && codecProfileLevel.level == this.level;
        }

        public int hashCode() {
            return java.lang.Long.hashCode((this.profile << 32) | this.level);
        }
    }

    public final android.media.MediaCodecInfo.CodecCapabilities getCapabilitiesForType(java.lang.String str) {
        android.media.MediaCodecInfo.CodecCapabilities codecCapabilities = this.mCaps.get(str);
        if (codecCapabilities == null) {
            throw new java.lang.IllegalArgumentException("codec does not support type");
        }
        return codecCapabilities.dup();
    }

    public android.media.MediaCodecInfo makeRegular() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.media.MediaCodecInfo.CodecCapabilities codecCapabilities : this.mCaps.values()) {
            if (codecCapabilities.isRegular()) {
                arrayList.add(codecCapabilities);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        if (arrayList.size() == this.mCaps.size()) {
            return this;
        }
        return new android.media.MediaCodecInfo(this.mName, this.mCanonicalName, this.mFlags, (android.media.MediaCodecInfo.CodecCapabilities[]) arrayList.toArray(new android.media.MediaCodecInfo.CodecCapabilities[arrayList.size()]));
    }
}
