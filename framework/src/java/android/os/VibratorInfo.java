package android.os;

/* loaded from: classes3.dex */
public class VibratorInfo implements android.os.Parcelable {
    private static final java.lang.String TAG = "VibratorInfo";
    private final long mCapabilities;
    private final int mCompositionSizeMax;
    private final android.os.VibratorInfo.FrequencyProfile mFrequencyProfile;
    private final int mId;
    private final int mPrimitiveDelayMax;
    private final int mPwlePrimitiveDurationMax;
    private final int mPwleSizeMax;
    private final float mQFactor;
    private final android.util.SparseBooleanArray mSupportedBraking;
    private final android.util.SparseBooleanArray mSupportedEffects;
    private final android.util.SparseIntArray mSupportedPrimitives;
    public static final android.os.VibratorInfo EMPTY_VIBRATOR_INFO = new android.os.VibratorInfo.Builder(-1).build();
    public static final android.os.Parcelable.Creator<android.os.VibratorInfo> CREATOR = new android.os.Parcelable.Creator<android.os.VibratorInfo>() { // from class: android.os.VibratorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.VibratorInfo createFromParcel(android.os.Parcel parcel) {
            return new android.os.VibratorInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.VibratorInfo[] newArray(int i) {
            return new android.os.VibratorInfo[i];
        }
    };

    VibratorInfo(android.os.Parcel parcel) {
        this.mId = parcel.readInt();
        this.mCapabilities = parcel.readLong();
        this.mSupportedEffects = parcel.readSparseBooleanArray();
        this.mSupportedBraking = parcel.readSparseBooleanArray();
        this.mSupportedPrimitives = parcel.readSparseIntArray();
        this.mPrimitiveDelayMax = parcel.readInt();
        this.mCompositionSizeMax = parcel.readInt();
        this.mPwlePrimitiveDurationMax = parcel.readInt();
        this.mPwleSizeMax = parcel.readInt();
        this.mQFactor = parcel.readFloat();
        this.mFrequencyProfile = android.os.VibratorInfo.FrequencyProfile.CREATOR.createFromParcel(parcel);
    }

    public VibratorInfo(int i, android.os.VibratorInfo vibratorInfo) {
        this(i, vibratorInfo.mCapabilities, vibratorInfo.mSupportedEffects, vibratorInfo.mSupportedBraking, vibratorInfo.mSupportedPrimitives, vibratorInfo.mPrimitiveDelayMax, vibratorInfo.mCompositionSizeMax, vibratorInfo.mPwlePrimitiveDurationMax, vibratorInfo.mPwleSizeMax, vibratorInfo.mQFactor, vibratorInfo.mFrequencyProfile);
    }

    public VibratorInfo(int i, long j, android.util.SparseBooleanArray sparseBooleanArray, android.util.SparseBooleanArray sparseBooleanArray2, android.util.SparseIntArray sparseIntArray, int i2, int i3, int i4, int i5, float f, android.os.VibratorInfo.FrequencyProfile frequencyProfile) {
        com.android.internal.util.Preconditions.checkNotNull(sparseIntArray);
        com.android.internal.util.Preconditions.checkNotNull(frequencyProfile);
        this.mId = i;
        this.mCapabilities = j;
        this.mSupportedEffects = sparseBooleanArray == null ? null : sparseBooleanArray.m4836clone();
        this.mSupportedBraking = sparseBooleanArray2 != null ? sparseBooleanArray2.m4836clone() : null;
        this.mSupportedPrimitives = sparseIntArray.m4838clone();
        this.mPrimitiveDelayMax = i2;
        this.mCompositionSizeMax = i3;
        this.mPwlePrimitiveDurationMax = i4;
        this.mPwleSizeMax = i5;
        this.mQFactor = f;
        this.mFrequencyProfile = frequencyProfile;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeLong(this.mCapabilities);
        parcel.writeSparseBooleanArray(this.mSupportedEffects);
        parcel.writeSparseBooleanArray(this.mSupportedBraking);
        parcel.writeSparseIntArray(this.mSupportedPrimitives);
        parcel.writeInt(this.mPrimitiveDelayMax);
        parcel.writeInt(this.mCompositionSizeMax);
        parcel.writeInt(this.mPwlePrimitiveDurationMax);
        parcel.writeInt(this.mPwleSizeMax);
        parcel.writeFloat(this.mQFactor);
        this.mFrequencyProfile.writeToParcel(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.os.VibratorInfo)) {
            return false;
        }
        android.os.VibratorInfo vibratorInfo = (android.os.VibratorInfo) obj;
        return this.mId == vibratorInfo.mId && equalContent(vibratorInfo);
    }

    public boolean equalContent(android.os.VibratorInfo vibratorInfo) {
        int size = this.mSupportedPrimitives.size();
        if (size != vibratorInfo.mSupportedPrimitives.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.mSupportedPrimitives.keyAt(i) != vibratorInfo.mSupportedPrimitives.keyAt(i) || this.mSupportedPrimitives.valueAt(i) != vibratorInfo.mSupportedPrimitives.valueAt(i)) {
                return false;
            }
        }
        return this.mCapabilities == vibratorInfo.mCapabilities && this.mPrimitiveDelayMax == vibratorInfo.mPrimitiveDelayMax && this.mCompositionSizeMax == vibratorInfo.mCompositionSizeMax && this.mPwlePrimitiveDurationMax == vibratorInfo.mPwlePrimitiveDurationMax && this.mPwleSizeMax == vibratorInfo.mPwleSizeMax && java.util.Objects.equals(this.mSupportedEffects, vibratorInfo.mSupportedEffects) && java.util.Objects.equals(this.mSupportedBraking, vibratorInfo.mSupportedBraking) && java.util.Objects.equals(java.lang.Float.valueOf(this.mQFactor), java.lang.Float.valueOf(vibratorInfo.mQFactor)) && java.util.Objects.equals(this.mFrequencyProfile, vibratorInfo.mFrequencyProfile);
    }

    public int hashCode() {
        int hash = java.util.Objects.hash(java.lang.Integer.valueOf(this.mId), java.lang.Long.valueOf(this.mCapabilities), this.mSupportedEffects, this.mSupportedBraking, java.lang.Float.valueOf(this.mQFactor), this.mFrequencyProfile);
        for (int i = 0; i < this.mSupportedPrimitives.size(); i++) {
            hash = (((hash * 31) + this.mSupportedPrimitives.keyAt(i)) * 31) + this.mSupportedPrimitives.valueAt(i);
        }
        return hash;
    }

    public java.lang.String toString() {
        return "VibratorInfo{mId=" + this.mId + ", mCapabilities=" + java.util.Arrays.toString(getCapabilitiesNames()) + ", mCapabilities flags=" + java.lang.Long.toBinaryString(this.mCapabilities) + ", mSupportedEffects=" + java.util.Arrays.toString(getSupportedEffectsNames()) + ", mSupportedBraking=" + java.util.Arrays.toString(getSupportedBrakingNames()) + ", mSupportedPrimitives=" + java.util.Arrays.toString(getSupportedPrimitivesNames()) + ", mPrimitiveDelayMax=" + this.mPrimitiveDelayMax + ", mCompositionSizeMax=" + this.mCompositionSizeMax + ", mPwlePrimitiveDurationMax=" + this.mPwlePrimitiveDurationMax + ", mPwleSizeMax=" + this.mPwleSizeMax + ", mQFactor=" + this.mQFactor + ", mFrequencyProfile=" + this.mFrequencyProfile + '}';
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VibratorInfo:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("id = " + this.mId);
        indentingPrintWriter.println("capabilities = " + java.util.Arrays.toString(getCapabilitiesNames()));
        indentingPrintWriter.println("capabilitiesFlags = " + java.lang.Long.toBinaryString(this.mCapabilities));
        indentingPrintWriter.println("supportedEffects = " + java.util.Arrays.toString(getSupportedEffectsNames()));
        indentingPrintWriter.println("supportedPrimitives = " + java.util.Arrays.toString(getSupportedPrimitivesNames()));
        indentingPrintWriter.println("supportedBraking = " + java.util.Arrays.toString(getSupportedBrakingNames()));
        indentingPrintWriter.println("primitiveDelayMax = " + this.mPrimitiveDelayMax);
        indentingPrintWriter.println("compositionSizeMax = " + this.mCompositionSizeMax);
        indentingPrintWriter.println("pwlePrimitiveDurationMax = " + this.mPwlePrimitiveDurationMax);
        indentingPrintWriter.println("pwleSizeMax = " + this.mPwleSizeMax);
        indentingPrintWriter.println("q-factor = " + this.mQFactor);
        indentingPrintWriter.println("frequencyProfile = " + this.mFrequencyProfile);
        indentingPrintWriter.decreaseIndent();
    }

    public int getId() {
        return this.mId;
    }

    public boolean hasAmplitudeControl() {
        return hasCapability(4L);
    }

    public boolean hasFrequencyControl() {
        return hasCapability(1536L);
    }

    public int getDefaultBraking() {
        if (this.mSupportedBraking != null) {
            int size = this.mSupportedBraking.size();
            for (int i = 0; i < size; i++) {
                if (this.mSupportedBraking.keyAt(i) != 0) {
                    return this.mSupportedBraking.keyAt(i);
                }
            }
        }
        return 0;
    }

    public android.util.SparseBooleanArray getSupportedBraking() {
        if (this.mSupportedBraking == null) {
            return null;
        }
        return this.mSupportedBraking.m4836clone();
    }

    public boolean isBrakingSupportKnown() {
        return this.mSupportedBraking != null;
    }

    public boolean hasBrakingSupport(int i) {
        return this.mSupportedBraking != null && this.mSupportedBraking.get(i);
    }

    public boolean isEffectSupportKnown() {
        return this.mSupportedEffects != null;
    }

    public int isEffectSupported(int i) {
        if (this.mSupportedEffects == null) {
            return 0;
        }
        return this.mSupportedEffects.get(i) ? 1 : 2;
    }

    public android.util.SparseBooleanArray getSupportedEffects() {
        if (this.mSupportedEffects == null) {
            return null;
        }
        return this.mSupportedEffects.m4836clone();
    }

    public boolean isPrimitiveSupported(int i) {
        return hasCapability(32L) && this.mSupportedPrimitives.indexOfKey(i) >= 0;
    }

    public boolean areVibrationFeaturesSupported(android.os.VibrationEffect vibrationEffect) {
        return vibrationEffect.areVibrationFeaturesSupported(this);
    }

    public int getPrimitiveDuration(int i) {
        return this.mSupportedPrimitives.get(i);
    }

    public android.util.SparseIntArray getSupportedPrimitives() {
        return this.mSupportedPrimitives.m4838clone();
    }

    public int getPrimitiveDelayMax() {
        return this.mPrimitiveDelayMax;
    }

    public int getCompositionSizeMax() {
        return this.mCompositionSizeMax;
    }

    public int getPwlePrimitiveDurationMax() {
        return this.mPwlePrimitiveDurationMax;
    }

    public int getPwleSizeMax() {
        return this.mPwleSizeMax;
    }

    public boolean hasCapability(long j) {
        return (this.mCapabilities & j) == j;
    }

    public float getResonantFrequencyHz() {
        return this.mFrequencyProfile.mResonantFrequencyHz;
    }

    public float getQFactor() {
        return this.mQFactor;
    }

    public android.os.VibratorInfo.FrequencyProfile getFrequencyProfile() {
        return this.mFrequencyProfile;
    }

    public long getCapabilities() {
        return this.mCapabilities;
    }

    private java.lang.String[] getCapabilitiesNames() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (hasCapability(1L)) {
            arrayList.add("ON_CALLBACK");
        }
        if (hasCapability(2L)) {
            arrayList.add("PERFORM_CALLBACK");
        }
        if (hasCapability(32L)) {
            arrayList.add("COMPOSE_EFFECTS");
        }
        if (hasCapability(1024L)) {
            arrayList.add("COMPOSE_PWLE_EFFECTS");
        }
        if (hasCapability(64L)) {
            arrayList.add("ALWAYS_ON_CONTROL");
        }
        if (hasCapability(4L)) {
            arrayList.add("AMPLITUDE_CONTROL");
        }
        if (hasCapability(512L)) {
            arrayList.add("FREQUENCY_CONTROL");
        }
        if (hasCapability(8L)) {
            arrayList.add("EXTERNAL_CONTROL");
        }
        if (hasCapability(16L)) {
            arrayList.add("EXTERNAL_AMPLITUDE_CONTROL");
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
    }

    private java.lang.String[] getSupportedEffectsNames() {
        if (this.mSupportedEffects == null) {
            return new java.lang.String[0];
        }
        java.lang.String[] strArr = new java.lang.String[this.mSupportedEffects.size()];
        for (int i = 0; i < this.mSupportedEffects.size(); i++) {
            strArr[i] = android.os.VibrationEffect.effectIdToString(this.mSupportedEffects.keyAt(i));
        }
        return strArr;
    }

    private java.lang.String[] getSupportedBrakingNames() {
        if (this.mSupportedBraking == null) {
            return new java.lang.String[0];
        }
        java.lang.String[] strArr = new java.lang.String[this.mSupportedBraking.size()];
        for (int i = 0; i < this.mSupportedBraking.size(); i++) {
            switch (this.mSupportedBraking.keyAt(i)) {
                case 0:
                    strArr[i] = android.security.keystore.KeyProperties.DIGEST_NONE;
                    break;
                case 1:
                    strArr[i] = "CLAB";
                    break;
                default:
                    strArr[i] = java.lang.Integer.toString(this.mSupportedBraking.keyAt(i));
                    break;
            }
        }
        return strArr;
    }

    private java.lang.String[] getSupportedPrimitivesNames() {
        int size = this.mSupportedPrimitives.size();
        java.lang.String[] strArr = new java.lang.String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = android.os.VibrationEffect.Composition.primitiveToString(this.mSupportedPrimitives.keyAt(i)) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + this.mSupportedPrimitives.valueAt(i) + "ms)";
        }
        return strArr;
    }

    public static final class FrequencyProfile implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.os.VibratorInfo.FrequencyProfile> CREATOR = new android.os.Parcelable.Creator<android.os.VibratorInfo.FrequencyProfile>() { // from class: android.os.VibratorInfo.FrequencyProfile.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.VibratorInfo.FrequencyProfile createFromParcel(android.os.Parcel parcel) {
                return new android.os.VibratorInfo.FrequencyProfile(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.VibratorInfo.FrequencyProfile[] newArray(int i) {
                return new android.os.VibratorInfo.FrequencyProfile[i];
            }
        };
        private final android.util.Range<java.lang.Float> mFrequencyRangeHz;
        private final float mFrequencyResolutionHz;
        private final float[] mMaxAmplitudes;
        private final float mMinFrequencyHz;
        private final float mResonantFrequencyHz;

        FrequencyProfile(android.os.Parcel parcel) {
            this(parcel.readFloat(), parcel.readFloat(), parcel.readFloat(), parcel.createFloatArray());
        }

        public FrequencyProfile(float f, float f2, float f3, float[] fArr) {
            float f4;
            this.mMinFrequencyHz = f2;
            this.mResonantFrequencyHz = f;
            this.mFrequencyResolutionHz = f3;
            boolean z = false;
            this.mMaxAmplitudes = new float[fArr == null ? 0 : fArr.length];
            if (fArr != null) {
                java.lang.System.arraycopy(fArr, 0, this.mMaxAmplitudes, 0, fArr.length);
            }
            boolean z2 = !java.lang.Float.isNaN(f) && f > 0.0f && !java.lang.Float.isNaN(f2) && f2 > 0.0f && !java.lang.Float.isNaN(f3) && f3 > 0.0f && this.mMaxAmplitudes.length > 0;
            for (int i = 0; i < this.mMaxAmplitudes.length; i++) {
                z2 &= this.mMaxAmplitudes[i] >= 0.0f && this.mMaxAmplitudes[i] <= 1.0f;
            }
            if (z2) {
                f4 = (f3 * (this.mMaxAmplitudes.length - 1)) + f2;
            } else {
                f4 = Float.NaN;
            }
            if (!java.lang.Float.isNaN(f4) && f >= f2 && f <= f4 && f2 < f4) {
                z = true;
            }
            this.mFrequencyRangeHz = z2 & z ? android.util.Range.create(java.lang.Float.valueOf(f2), java.lang.Float.valueOf(f4)) : null;
        }

        public boolean isEmpty() {
            return this.mFrequencyRangeHz == null;
        }

        public android.util.Range<java.lang.Float> getFrequencyRangeHz() {
            return this.mFrequencyRangeHz;
        }

        public float getMaxAmplitude(float f) {
            if (isEmpty() || java.lang.Float.isNaN(f) || !this.mFrequencyRangeHz.contains((android.util.Range<java.lang.Float>) java.lang.Float.valueOf(f))) {
                return 0.0f;
            }
            float f2 = f - this.mMinFrequencyHz;
            int constrain = android.util.MathUtils.constrain((int) java.lang.Math.floor(f2 / this.mFrequencyResolutionHz), 0, this.mMaxAmplitudes.length - 1);
            int constrain2 = android.util.MathUtils.constrain(constrain + 1, 0, this.mMaxAmplitudes.length - 1);
            return android.util.MathUtils.constrainedMap(this.mMaxAmplitudes[constrain], this.mMaxAmplitudes[constrain2], constrain * this.mFrequencyResolutionHz, constrain2 * this.mFrequencyResolutionHz, f2);
        }

        public float[] getMaxAmplitudes() {
            return java.util.Arrays.copyOf(this.mMaxAmplitudes, this.mMaxAmplitudes.length);
        }

        public float getFrequencyResolutionHz() {
            return this.mFrequencyResolutionHz;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeFloat(this.mResonantFrequencyHz);
            parcel.writeFloat(this.mMinFrequencyHz);
            parcel.writeFloat(this.mFrequencyResolutionHz);
            parcel.writeFloatArray(this.mMaxAmplitudes);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.os.VibratorInfo.FrequencyProfile)) {
                return false;
            }
            android.os.VibratorInfo.FrequencyProfile frequencyProfile = (android.os.VibratorInfo.FrequencyProfile) obj;
            return java.lang.Float.compare(this.mMinFrequencyHz, frequencyProfile.mMinFrequencyHz) == 0 && java.lang.Float.compare(this.mResonantFrequencyHz, frequencyProfile.mResonantFrequencyHz) == 0 && java.lang.Float.compare(this.mFrequencyResolutionHz, frequencyProfile.mFrequencyResolutionHz) == 0 && java.util.Arrays.equals(this.mMaxAmplitudes, frequencyProfile.mMaxAmplitudes);
        }

        public int hashCode() {
            return (java.util.Objects.hash(java.lang.Float.valueOf(this.mMinFrequencyHz), java.lang.Float.valueOf(this.mFrequencyResolutionHz), java.lang.Float.valueOf(this.mFrequencyResolutionHz)) * 31) + java.util.Arrays.hashCode(this.mMaxAmplitudes);
        }

        public java.lang.String toString() {
            return "FrequencyProfile{mFrequencyRange=" + this.mFrequencyRangeHz + ", mMinFrequency=" + this.mMinFrequencyHz + ", mResonantFrequency=" + this.mResonantFrequencyHz + ", mFrequencyResolution=" + this.mFrequencyResolutionHz + ", mMaxAmplitudes count=" + this.mMaxAmplitudes.length + '}';
        }
    }

    public static final class Builder {
        private long mCapabilities;
        private int mCompositionSizeMax;
        private final int mId;
        private int mPrimitiveDelayMax;
        private int mPwlePrimitiveDurationMax;
        private int mPwleSizeMax;
        private android.util.SparseBooleanArray mSupportedBraking;
        private android.util.SparseBooleanArray mSupportedEffects;
        private android.util.SparseIntArray mSupportedPrimitives = new android.util.SparseIntArray();
        private float mQFactor = Float.NaN;
        private android.os.VibratorInfo.FrequencyProfile mFrequencyProfile = new android.os.VibratorInfo.FrequencyProfile(Float.NaN, Float.NaN, Float.NaN, null);

        public Builder(int i) {
            this.mId = i;
        }

        public android.os.VibratorInfo.Builder setCapabilities(long j) {
            this.mCapabilities = j;
            return this;
        }

        public android.os.VibratorInfo.Builder setSupportedEffects(int... iArr) {
            this.mSupportedEffects = toSparseBooleanArray(iArr);
            return this;
        }

        public android.os.VibratorInfo.Builder setSupportedBraking(int... iArr) {
            this.mSupportedBraking = toSparseBooleanArray(iArr);
            return this;
        }

        public android.os.VibratorInfo.Builder setPwlePrimitiveDurationMax(int i) {
            this.mPwlePrimitiveDurationMax = i;
            return this;
        }

        public android.os.VibratorInfo.Builder setPwleSizeMax(int i) {
            this.mPwleSizeMax = i;
            return this;
        }

        public android.os.VibratorInfo.Builder setSupportedPrimitive(int i, int i2) {
            this.mSupportedPrimitives.put(i, i2);
            return this;
        }

        public android.os.VibratorInfo.Builder setPrimitiveDelayMax(int i) {
            this.mPrimitiveDelayMax = i;
            return this;
        }

        public android.os.VibratorInfo.Builder setCompositionSizeMax(int i) {
            this.mCompositionSizeMax = i;
            return this;
        }

        public android.os.VibratorInfo.Builder setQFactor(float f) {
            this.mQFactor = f;
            return this;
        }

        public android.os.VibratorInfo.Builder setFrequencyProfile(android.os.VibratorInfo.FrequencyProfile frequencyProfile) {
            this.mFrequencyProfile = frequencyProfile;
            return this;
        }

        public android.os.VibratorInfo build() {
            return new android.os.VibratorInfo(this.mId, this.mCapabilities, this.mSupportedEffects, this.mSupportedBraking, this.mSupportedPrimitives, this.mPrimitiveDelayMax, this.mCompositionSizeMax, this.mPwlePrimitiveDurationMax, this.mPwleSizeMax, this.mQFactor, this.mFrequencyProfile);
        }

        private static android.util.SparseBooleanArray toSparseBooleanArray(int[] iArr) {
            if (iArr == null) {
                return null;
            }
            android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
            for (int i : iArr) {
                sparseBooleanArray.put(i, true);
            }
            return sparseBooleanArray;
        }
    }
}
