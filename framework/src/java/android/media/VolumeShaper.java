package android.media;

/* loaded from: classes2.dex */
public final class VolumeShaper implements java.lang.AutoCloseable {
    private int mId;
    private final java.lang.ref.WeakReference<android.media.PlayerBase> mWeakPlayerBase;

    VolumeShaper(android.media.VolumeShaper.Configuration configuration, android.media.PlayerBase playerBase) {
        this.mWeakPlayerBase = new java.lang.ref.WeakReference<>(playerBase);
        this.mId = applyPlayer(configuration, new android.media.VolumeShaper.Operation.Builder().defer().build());
    }

    int getId() {
        return this.mId;
    }

    public void apply(android.media.VolumeShaper.Operation operation) {
        applyPlayer(new android.media.VolumeShaper.Configuration(this.mId), operation);
    }

    public void replace(android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation, boolean z) {
        this.mId = applyPlayer(configuration, new android.media.VolumeShaper.Operation.Builder(operation).replace(this.mId, z).build());
    }

    public float getVolume() {
        return getStatePlayer(this.mId).getVolume();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            applyPlayer(new android.media.VolumeShaper.Configuration(this.mId), new android.media.VolumeShaper.Operation.Builder().terminate().build());
        } catch (java.lang.IllegalStateException e) {
        }
        if (this.mWeakPlayerBase != null) {
            this.mWeakPlayerBase.clear();
        }
    }

    protected void finalize() {
        close();
    }

    private int applyPlayer(android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation) {
        if (this.mWeakPlayerBase != null) {
            android.media.PlayerBase playerBase = this.mWeakPlayerBase.get();
            if (playerBase == null) {
                throw new java.lang.IllegalStateException("player deallocated");
            }
            int playerApplyVolumeShaper = playerBase.playerApplyVolumeShaper(configuration, operation);
            if (playerApplyVolumeShaper < 0) {
                if (playerApplyVolumeShaper == -38) {
                    throw new java.lang.IllegalStateException("player or VolumeShaper deallocated");
                }
                throw new java.lang.IllegalArgumentException("invalid configuration or operation: " + playerApplyVolumeShaper);
            }
            return playerApplyVolumeShaper;
        }
        throw new java.lang.IllegalStateException("uninitialized shaper");
    }

    private android.media.VolumeShaper.State getStatePlayer(int i) {
        if (this.mWeakPlayerBase != null) {
            android.media.PlayerBase playerBase = this.mWeakPlayerBase.get();
            if (playerBase == null) {
                throw new java.lang.IllegalStateException("player deallocated");
            }
            android.media.VolumeShaper.State playerGetVolumeShaperState = playerBase.playerGetVolumeShaperState(i);
            if (playerGetVolumeShaperState == null) {
                throw new java.lang.IllegalStateException("shaper cannot be found");
            }
            return playerGetVolumeShaperState;
        }
        throw new java.lang.IllegalStateException("uninitialized shaper");
    }

    public static final class Configuration implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.VolumeShaper.Configuration> CREATOR;
        public static final int INTERPOLATOR_TYPE_CUBIC = 2;
        public static final int INTERPOLATOR_TYPE_CUBIC_MONOTONIC = 3;
        public static final int INTERPOLATOR_TYPE_LINEAR = 1;
        public static final int INTERPOLATOR_TYPE_STEP = 0;
        private static final int MAXIMUM_CURVE_POINTS = 16;
        public static final int OPTION_FLAG_CLOCK_TIME = 2;
        private static final int OPTION_FLAG_PUBLIC_ALL = 3;
        public static final int OPTION_FLAG_VOLUME_IN_DBFS = 1;
        public static final android.media.VolumeShaper.Configuration SCURVE_RAMP;
        public static final android.media.VolumeShaper.Configuration SINE_RAMP;
        static final int TYPE_ID = 0;
        static final int TYPE_SCALE = 1;
        private final double mDurationMs;
        private final int mId;
        private final int mInterpolatorType;
        private final int mOptionFlags;
        private final float[] mTimes;
        private final int mType;
        private final float[] mVolumes;
        public static final android.media.VolumeShaper.Configuration LINEAR_RAMP = new android.media.VolumeShaper.Configuration.Builder().setInterpolatorType(1).setCurve(new float[]{0.0f, 1.0f}, new float[]{0.0f, 1.0f}).setDuration(1000).build();
        public static final android.media.VolumeShaper.Configuration CUBIC_RAMP = new android.media.VolumeShaper.Configuration.Builder().setInterpolatorType(2).setCurve(new float[]{0.0f, 1.0f}, new float[]{0.0f, 1.0f}).setDuration(1000).build();

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface InterpolatorType {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface OptionFlag {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Type {
        }

        public static int getMaximumCurvePoints() {
            return 16;
        }

        static {
            float[] fArr = new float[16];
            float[] fArr2 = new float[16];
            float[] fArr3 = new float[16];
            for (int i = 0; i < 16; i++) {
                fArr[i] = i / 15.0f;
                float sin = (float) java.lang.Math.sin((fArr[i] * 3.141592653589793d) / 2.0d);
                fArr2[i] = sin;
                fArr3[i] = sin * sin;
            }
            SINE_RAMP = new android.media.VolumeShaper.Configuration.Builder().setInterpolatorType(2).setCurve(fArr, fArr2).setDuration(1000L).build();
            SCURVE_RAMP = new android.media.VolumeShaper.Configuration.Builder().setInterpolatorType(2).setCurve(fArr, fArr3).setDuration(1000L).build();
            CREATOR = new android.os.Parcelable.Creator<android.media.VolumeShaper.Configuration>() { // from class: android.media.VolumeShaper.Configuration.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.media.VolumeShaper.Configuration createFromParcel(android.os.Parcel parcel) {
                    return android.media.VolumeShaper.Configuration.fromParcelable(android.media.VolumeShaperConfiguration.CREATOR.createFromParcel(parcel));
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.media.VolumeShaper.Configuration[] newArray(int i2) {
                    return new android.media.VolumeShaper.Configuration[i2];
                }
            };
        }

        public java.lang.String toString() {
            return "VolumeShaper.Configuration{mType = " + this.mType + ", mId = " + this.mId + (this.mType != 0 ? ", mOptionFlags = 0x" + java.lang.Integer.toHexString(this.mOptionFlags).toUpperCase() + ", mDurationMs = " + this.mDurationMs + ", mInterpolatorType = " + this.mInterpolatorType + ", mTimes[] = " + java.util.Arrays.toString(this.mTimes) + ", mVolumes[] = " + java.util.Arrays.toString(this.mVolumes) + "}" : "}");
        }

        public int hashCode() {
            if (this.mType == 0) {
                return java.util.Objects.hash(java.lang.Integer.valueOf(this.mType), java.lang.Integer.valueOf(this.mId));
            }
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mType), java.lang.Integer.valueOf(this.mId), java.lang.Integer.valueOf(this.mOptionFlags), java.lang.Double.valueOf(this.mDurationMs), java.lang.Integer.valueOf(this.mInterpolatorType), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mTimes)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mVolumes)));
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.media.VolumeShaper.Configuration)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            android.media.VolumeShaper.Configuration configuration = (android.media.VolumeShaper.Configuration) obj;
            if (this.mType == configuration.mType && this.mId == configuration.mId) {
                return this.mType == 0 || (this.mOptionFlags == configuration.mOptionFlags && this.mDurationMs == configuration.mDurationMs && this.mInterpolatorType == configuration.mInterpolatorType && java.util.Arrays.equals(this.mTimes, configuration.mTimes) && java.util.Arrays.equals(this.mVolumes, configuration.mVolumes));
            }
            return false;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            toParcelable().writeToParcel(parcel, i);
        }

        public android.media.VolumeShaperConfiguration toParcelable() {
            android.media.VolumeShaperConfiguration volumeShaperConfiguration = new android.media.VolumeShaperConfiguration();
            volumeShaperConfiguration.type = typeToAidl(this.mType);
            volumeShaperConfiguration.id = this.mId;
            if (this.mType != 0) {
                volumeShaperConfiguration.optionFlags = optionFlagsToAidl(this.mOptionFlags);
                volumeShaperConfiguration.durationMs = this.mDurationMs;
                volumeShaperConfiguration.interpolatorConfig = toInterpolatorParcelable();
            }
            return volumeShaperConfiguration;
        }

        private android.media.InterpolatorConfig toInterpolatorParcelable() {
            android.media.InterpolatorConfig interpolatorConfig = new android.media.InterpolatorConfig();
            interpolatorConfig.type = interpolatorTypeToAidl(this.mInterpolatorType);
            interpolatorConfig.firstSlope = 0.0f;
            interpolatorConfig.lastSlope = 0.0f;
            interpolatorConfig.xy = new float[this.mTimes.length * 2];
            for (int i = 0; i < this.mTimes.length; i++) {
                int i2 = i * 2;
                interpolatorConfig.xy[i2] = this.mTimes[i];
                interpolatorConfig.xy[i2 + 1] = this.mVolumes[i];
            }
            return interpolatorConfig;
        }

        public static android.media.VolumeShaper.Configuration fromParcelable(android.media.VolumeShaperConfiguration volumeShaperConfiguration) {
            int typeFromAidl = typeFromAidl(volumeShaperConfiguration.type);
            int i = volumeShaperConfiguration.id;
            if (typeFromAidl == 0) {
                return new android.media.VolumeShaper.Configuration(i);
            }
            int optionFlagsFromAidl = optionFlagsFromAidl(volumeShaperConfiguration.optionFlags);
            double d = volumeShaperConfiguration.durationMs;
            int interpolatorTypeFromAidl = interpolatorTypeFromAidl(volumeShaperConfiguration.interpolatorConfig.type);
            int length = volumeShaperConfiguration.interpolatorConfig.xy.length;
            if (length % 2 != 0) {
                throw new android.os.BadParcelableException("xy length must be even");
            }
            int i2 = length / 2;
            float[] fArr = new float[i2];
            float[] fArr2 = new float[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                int i4 = i3 * 2;
                fArr[i3] = volumeShaperConfiguration.interpolatorConfig.xy[i4];
                fArr2[i3] = volumeShaperConfiguration.interpolatorConfig.xy[i4 + 1];
            }
            return new android.media.VolumeShaper.Configuration(typeFromAidl, i, optionFlagsFromAidl, d, interpolatorTypeFromAidl, fArr, fArr2);
        }

        private static int interpolatorTypeFromAidl(int i) {
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                default:
                    throw new android.os.BadParcelableException("Unknown interpolator type");
            }
        }

        private static int interpolatorTypeToAidl(int i) {
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                default:
                    throw new java.lang.RuntimeException("Unknown interpolator type");
            }
        }

        private static int typeFromAidl(int i) {
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                default:
                    throw new android.os.BadParcelableException("Unknown type");
            }
        }

        private static int typeToAidl(int i) {
            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                default:
                    throw new java.lang.RuntimeException("Unknown type");
            }
        }

        private static int optionFlagsFromAidl(int i) {
            int i2;
            if ((i & 1) == 0) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            if ((i & 2) != 0) {
                return i2 | 2;
            }
            return i2;
        }

        private static int optionFlagsToAidl(int i) {
            int i2;
            if ((i & 1) == 0) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            if ((i & 2) != 0) {
                return i2 | 2;
            }
            return i2;
        }

        public Configuration(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("negative id " + i);
            }
            this.mType = 0;
            this.mId = i;
            this.mInterpolatorType = 0;
            this.mOptionFlags = 0;
            this.mDurationMs = 0.0d;
            this.mTimes = null;
            this.mVolumes = null;
        }

        private Configuration(int i, int i2, int i3, double d, int i4, float[] fArr, float[] fArr2) {
            this.mType = i;
            this.mId = i2;
            this.mOptionFlags = i3;
            this.mDurationMs = d;
            this.mInterpolatorType = i4;
            this.mTimes = fArr;
            this.mVolumes = fArr2;
        }

        public int getType() {
            return this.mType;
        }

        public int getId() {
            return this.mId;
        }

        public int getInterpolatorType() {
            return this.mInterpolatorType;
        }

        public int getOptionFlags() {
            return this.mOptionFlags & 3;
        }

        int getAllOptionFlags() {
            return this.mOptionFlags;
        }

        public long getDuration() {
            return (long) this.mDurationMs;
        }

        public float[] getTimes() {
            return this.mTimes;
        }

        public float[] getVolumes() {
            return this.mVolumes;
        }

        private static java.lang.String checkCurveForErrors(float[] fArr, float[] fArr2, boolean z) {
            if (fArr == null) {
                return "times array must be non-null";
            }
            if (fArr2 == null) {
                return "volumes array must be non-null";
            }
            if (fArr.length != fArr2.length) {
                return "array length must match";
            }
            if (fArr.length < 2) {
                return "array length must be at least 2";
            }
            if (fArr.length > 16) {
                return "array length must be no larger than 16";
            }
            int i = 0;
            if (fArr[0] != 0.0f) {
                return "times must start at 0.f";
            }
            if (fArr[fArr.length - 1] != 1.0f) {
                return "times must end at 1.f";
            }
            for (int i2 = 1; i2 < fArr.length; i2++) {
                if (fArr[i2] <= fArr[i2 - 1]) {
                    return "times not monotonic increasing, check index " + i2;
                }
            }
            if (z) {
                while (i < fArr2.length) {
                    if (fArr2[i] <= 0.0f) {
                        i++;
                    } else {
                        return "volumes for log scale cannot be positive, check index " + i;
                    }
                }
                return null;
            }
            while (i < fArr2.length) {
                if (fArr2[i] >= 0.0f && fArr2[i] <= 1.0f) {
                    i++;
                } else {
                    return "volumes for linear scale must be between 0.f and 1.f, check index " + i;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void checkCurveForErrorsAndThrowException(float[] fArr, float[] fArr2, boolean z, boolean z2) {
            java.lang.String checkCurveForErrors = checkCurveForErrors(fArr, fArr2, z);
            if (checkCurveForErrors != null) {
                if (z2) {
                    throw new java.lang.IllegalStateException(checkCurveForErrors);
                }
                throw new java.lang.IllegalArgumentException(checkCurveForErrors);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void checkValidVolumeAndThrowException(float f, boolean z) {
            if (z) {
                if (f > 0.0f) {
                    throw new java.lang.IllegalArgumentException("dbfs volume must be 0.f or less");
                }
            } else if (f < 0.0f || f > 1.0f) {
                throw new java.lang.IllegalArgumentException("volume must be >= 0.f and <= 1.f");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void clampVolume(float[] fArr, boolean z) {
            int i = 0;
            if (z) {
                while (i < fArr.length) {
                    if (fArr[i] > 0.0f) {
                        fArr[i] = 0.0f;
                    }
                    i++;
                }
                return;
            }
            while (i < fArr.length) {
                if (fArr[i] < 0.0f) {
                    fArr[i] = 0.0f;
                } else if (fArr[i] > 1.0f) {
                    fArr[i] = 1.0f;
                }
                i++;
            }
        }

        public static final class Builder {
            private double mDurationMs;
            private int mId;
            private int mInterpolatorType;
            private int mOptionFlags;
            private float[] mTimes;
            private int mType;
            private float[] mVolumes;

            public Builder() {
                this.mType = 1;
                this.mId = -1;
                this.mInterpolatorType = 2;
                this.mOptionFlags = 2;
                this.mDurationMs = 1000.0d;
                this.mTimes = null;
                this.mVolumes = null;
            }

            public Builder(android.media.VolumeShaper.Configuration configuration) {
                this.mType = 1;
                this.mId = -1;
                this.mInterpolatorType = 2;
                this.mOptionFlags = 2;
                this.mDurationMs = 1000.0d;
                this.mTimes = null;
                this.mVolumes = null;
                this.mType = configuration.getType();
                this.mId = configuration.getId();
                this.mOptionFlags = configuration.getAllOptionFlags();
                this.mInterpolatorType = configuration.getInterpolatorType();
                this.mDurationMs = configuration.getDuration();
                this.mTimes = (float[]) configuration.getTimes().clone();
                this.mVolumes = (float[]) configuration.getVolumes().clone();
            }

            public android.media.VolumeShaper.Configuration.Builder setId(int i) {
                if (i < -1) {
                    throw new java.lang.IllegalArgumentException("invalid id: " + i);
                }
                this.mId = i;
                return this;
            }

            public android.media.VolumeShaper.Configuration.Builder setInterpolatorType(int i) {
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        this.mInterpolatorType = i;
                        return this;
                    default:
                        throw new java.lang.IllegalArgumentException("invalid interpolatorType: " + i);
                }
            }

            public android.media.VolumeShaper.Configuration.Builder setOptionFlags(int i) {
                if ((i & (-4)) != 0) {
                    throw new java.lang.IllegalArgumentException("invalid bits in flag: " + i);
                }
                this.mOptionFlags = i | (this.mOptionFlags & (-4));
                return this;
            }

            public android.media.VolumeShaper.Configuration.Builder setDuration(long j) {
                if (j <= 0) {
                    throw new java.lang.IllegalArgumentException("duration: " + j + " not positive");
                }
                this.mDurationMs = j;
                return this;
            }

            public android.media.VolumeShaper.Configuration.Builder setCurve(float[] fArr, float[] fArr2) {
                android.media.VolumeShaper.Configuration.checkCurveForErrorsAndThrowException(fArr, fArr2, (this.mOptionFlags & 1) != 0, false);
                this.mTimes = (float[]) fArr.clone();
                this.mVolumes = (float[]) fArr2.clone();
                return this;
            }

            public android.media.VolumeShaper.Configuration.Builder reflectTimes() {
                int i = 0;
                android.media.VolumeShaper.Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, (this.mOptionFlags & 1) != 0, true);
                while (i < this.mTimes.length / 2) {
                    float f = this.mTimes[i];
                    this.mTimes[i] = 1.0f - this.mTimes[(this.mTimes.length - 1) - i];
                    this.mTimes[(this.mTimes.length - 1) - i] = 1.0f - f;
                    float f2 = this.mVolumes[i];
                    this.mVolumes[i] = this.mVolumes[(this.mVolumes.length - 1) - i];
                    this.mVolumes[(this.mVolumes.length - 1) - i] = f2;
                    i++;
                }
                if ((this.mTimes.length & 1) != 0) {
                    this.mTimes[i] = 1.0f - this.mTimes[i];
                }
                return this;
            }

            public android.media.VolumeShaper.Configuration.Builder invertVolumes() {
                android.media.VolumeShaper.Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, (this.mOptionFlags & 1) != 0, true);
                float f = this.mVolumes[0];
                float f2 = this.mVolumes[0];
                for (int i = 1; i < this.mVolumes.length; i++) {
                    if (this.mVolumes[i] < f) {
                        f = this.mVolumes[i];
                    } else if (this.mVolumes[i] > f2) {
                        f2 = this.mVolumes[i];
                    }
                }
                float f3 = f2 + f;
                for (int i2 = 0; i2 < this.mVolumes.length; i2++) {
                    this.mVolumes[i2] = f3 - this.mVolumes[i2];
                }
                return this;
            }

            public android.media.VolumeShaper.Configuration.Builder scaleToEndVolume(float f) {
                int i = 0;
                boolean z = (this.mOptionFlags & 1) != 0;
                android.media.VolumeShaper.Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, z, true);
                android.media.VolumeShaper.Configuration.checkValidVolumeAndThrowException(f, z);
                float f2 = this.mVolumes[0];
                float f3 = this.mVolumes[this.mVolumes.length - 1];
                if (f3 == f2) {
                    float f4 = f - f2;
                    while (i < this.mVolumes.length) {
                        this.mVolumes[i] = this.mVolumes[i] + (this.mTimes[i] * f4);
                        i++;
                    }
                } else {
                    float f5 = (f - f2) / (f3 - f2);
                    while (i < this.mVolumes.length) {
                        this.mVolumes[i] = ((this.mVolumes[i] - f2) * f5) + f2;
                        i++;
                    }
                }
                android.media.VolumeShaper.Configuration.clampVolume(this.mVolumes, z);
                return this;
            }

            public android.media.VolumeShaper.Configuration.Builder scaleToStartVolume(float f) {
                int i = 0;
                boolean z = (this.mOptionFlags & 1) != 0;
                android.media.VolumeShaper.Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, z, true);
                android.media.VolumeShaper.Configuration.checkValidVolumeAndThrowException(f, z);
                float f2 = this.mVolumes[0];
                float f3 = this.mVolumes[this.mVolumes.length - 1];
                if (f3 == f2) {
                    float f4 = f - f2;
                    while (i < this.mVolumes.length) {
                        this.mVolumes[i] = this.mVolumes[i] + ((1.0f - this.mTimes[i]) * f4);
                        i++;
                    }
                } else {
                    float f5 = (f - f3) / (f2 - f3);
                    while (i < this.mVolumes.length) {
                        this.mVolumes[i] = ((this.mVolumes[i] - f3) * f5) + f3;
                        i++;
                    }
                }
                android.media.VolumeShaper.Configuration.clampVolume(this.mVolumes, z);
                return this;
            }

            public android.media.VolumeShaper.Configuration build() {
                android.media.VolumeShaper.Configuration.checkCurveForErrorsAndThrowException(this.mTimes, this.mVolumes, (this.mOptionFlags & 1) != 0, true);
                return new android.media.VolumeShaper.Configuration(this.mType, this.mId, this.mOptionFlags, this.mDurationMs, this.mInterpolatorType, this.mTimes, this.mVolumes);
            }
        }
    }

    public static final class Operation implements android.os.Parcelable {
        private static final int FLAG_CREATE_IF_NEEDED = 16;
        private static final int FLAG_DEFER = 8;
        private static final int FLAG_JOIN = 4;
        private static final int FLAG_NONE = 0;
        private static final int FLAG_PUBLIC_ALL = 3;
        private static final int FLAG_REVERSE = 1;
        private static final int FLAG_TERMINATE = 2;
        private final int mFlags;
        private final int mReplaceId;
        private final float mXOffset;
        public static final android.media.VolumeShaper.Operation PLAY = new android.media.VolumeShaper.Operation.Builder().build();
        public static final android.media.VolumeShaper.Operation REVERSE = new android.media.VolumeShaper.Operation.Builder().reverse().build();
        public static final android.os.Parcelable.Creator<android.media.VolumeShaper.Operation> CREATOR = new android.os.Parcelable.Creator<android.media.VolumeShaper.Operation>() { // from class: android.media.VolumeShaper.Operation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.VolumeShaper.Operation createFromParcel(android.os.Parcel parcel) {
                return android.media.VolumeShaper.Operation.fromParcelable(android.media.VolumeShaperOperation.CREATOR.createFromParcel(parcel));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.VolumeShaper.Operation[] newArray(int i) {
                return new android.media.VolumeShaper.Operation[i];
            }
        };

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Flag {
        }

        public java.lang.String toString() {
            return "VolumeShaper.Operation{mFlags = 0x" + java.lang.Integer.toHexString(this.mFlags).toUpperCase() + ", mReplaceId = " + this.mReplaceId + ", mXOffset = " + this.mXOffset + "}";
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mFlags), java.lang.Integer.valueOf(this.mReplaceId), java.lang.Float.valueOf(this.mXOffset));
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.media.VolumeShaper.Operation)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            android.media.VolumeShaper.Operation operation = (android.media.VolumeShaper.Operation) obj;
            return this.mFlags == operation.mFlags && this.mReplaceId == operation.mReplaceId && java.lang.Float.compare(this.mXOffset, operation.mXOffset) == 0;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            toParcelable().writeToParcel(parcel, i);
        }

        public android.media.VolumeShaperOperation toParcelable() {
            android.media.VolumeShaperOperation volumeShaperOperation = new android.media.VolumeShaperOperation();
            volumeShaperOperation.flags = flagsToAidl(this.mFlags);
            volumeShaperOperation.replaceId = this.mReplaceId;
            volumeShaperOperation.xOffset = this.mXOffset;
            return volumeShaperOperation;
        }

        public static android.media.VolumeShaper.Operation fromParcelable(android.media.VolumeShaperOperation volumeShaperOperation) {
            return new android.media.VolumeShaper.Operation(flagsFromAidl(volumeShaperOperation.flags), volumeShaperOperation.replaceId, volumeShaperOperation.xOffset);
        }

        private static int flagsFromAidl(int i) {
            int i2;
            if ((i & 1) == 0) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            if ((i & 2) != 0) {
                i2 |= 2;
            }
            if ((i & 4) != 0) {
                i2 |= 4;
            }
            if ((i & 8) != 0) {
                i2 |= 8;
            }
            if ((i & 16) != 0) {
                return i2 | 16;
            }
            return i2;
        }

        private static int flagsToAidl(int i) {
            int i2;
            if ((i & 1) == 0) {
                i2 = 0;
            } else {
                i2 = 1;
            }
            if ((i & 2) != 0) {
                i2 |= 2;
            }
            if ((i & 4) != 0) {
                i2 |= 4;
            }
            if ((i & 8) != 0) {
                i2 |= 8;
            }
            if ((i & 16) != 0) {
                return i2 | 16;
            }
            return i2;
        }

        private Operation(int i, int i2, float f) {
            this.mFlags = i;
            this.mReplaceId = i2;
            this.mXOffset = f;
        }

        public static final class Builder {
            int mFlags;
            int mReplaceId;
            float mXOffset;

            public Builder() {
                this.mFlags = 0;
                this.mReplaceId = -1;
                this.mXOffset = Float.NaN;
            }

            public Builder(android.media.VolumeShaper.Operation operation) {
                this.mReplaceId = operation.mReplaceId;
                this.mFlags = operation.mFlags;
                this.mXOffset = operation.mXOffset;
            }

            public android.media.VolumeShaper.Operation.Builder replace(int i, boolean z) {
                this.mReplaceId = i;
                if (z) {
                    this.mFlags |= 4;
                } else {
                    this.mFlags &= -5;
                }
                return this;
            }

            public android.media.VolumeShaper.Operation.Builder defer() {
                this.mFlags |= 8;
                return this;
            }

            public android.media.VolumeShaper.Operation.Builder terminate() {
                this.mFlags |= 2;
                return this;
            }

            public android.media.VolumeShaper.Operation.Builder reverse() {
                this.mFlags ^= 1;
                return this;
            }

            public android.media.VolumeShaper.Operation.Builder createIfNeeded() {
                this.mFlags |= 16;
                return this;
            }

            public android.media.VolumeShaper.Operation.Builder setXOffset(float f) {
                if (f < -0.0f) {
                    throw new java.lang.IllegalArgumentException("Negative xOffset not allowed");
                }
                if (f > 1.0f) {
                    throw new java.lang.IllegalArgumentException("xOffset > 1.f not allowed");
                }
                this.mXOffset = f;
                return this;
            }

            private android.media.VolumeShaper.Operation.Builder setFlags(int i) {
                if ((i & (-4)) != 0) {
                    throw new java.lang.IllegalArgumentException("flag has unknown bits set: " + i);
                }
                this.mFlags = i | (this.mFlags & (-4));
                return this;
            }

            public android.media.VolumeShaper.Operation build() {
                return new android.media.VolumeShaper.Operation(this.mFlags, this.mReplaceId, this.mXOffset);
            }
        }
    }

    public static final class State implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.VolumeShaper.State> CREATOR = new android.os.Parcelable.Creator<android.media.VolumeShaper.State>() { // from class: android.media.VolumeShaper.State.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.VolumeShaper.State createFromParcel(android.os.Parcel parcel) {
                return android.media.VolumeShaper.State.fromParcelable(android.media.VolumeShaperState.CREATOR.createFromParcel(parcel));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.VolumeShaper.State[] newArray(int i) {
                return new android.media.VolumeShaper.State[i];
            }
        };
        private float mVolume;
        private float mXOffset;

        public java.lang.String toString() {
            return "VolumeShaper.State{mVolume = " + this.mVolume + ", mXOffset = " + this.mXOffset + "}";
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Float.valueOf(this.mVolume), java.lang.Float.valueOf(this.mXOffset));
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.media.VolumeShaper.State)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            android.media.VolumeShaper.State state = (android.media.VolumeShaper.State) obj;
            return this.mVolume == state.mVolume && this.mXOffset == state.mXOffset;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            toParcelable().writeToParcel(parcel, i);
        }

        public android.media.VolumeShaperState toParcelable() {
            android.media.VolumeShaperState volumeShaperState = new android.media.VolumeShaperState();
            volumeShaperState.volume = this.mVolume;
            volumeShaperState.xOffset = this.mXOffset;
            return volumeShaperState;
        }

        public static android.media.VolumeShaper.State fromParcelable(android.media.VolumeShaperState volumeShaperState) {
            return new android.media.VolumeShaper.State(volumeShaperState.volume, volumeShaperState.xOffset);
        }

        State(float f, float f2) {
            this.mVolume = f;
            this.mXOffset = f2;
        }

        public float getVolume() {
            return this.mVolume;
        }

        public float getXOffset() {
            return this.mXOffset;
        }
    }
}
