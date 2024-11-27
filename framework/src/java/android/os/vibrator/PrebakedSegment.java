package android.os.vibrator;

/* loaded from: classes3.dex */
public final class PrebakedSegment extends android.os.vibrator.VibrationEffectSegment {
    public static final android.os.Parcelable.Creator<android.os.vibrator.PrebakedSegment> CREATOR = new android.os.Parcelable.Creator<android.os.vibrator.PrebakedSegment>() { // from class: android.os.vibrator.PrebakedSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.vibrator.PrebakedSegment createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return new android.os.vibrator.PrebakedSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.vibrator.PrebakedSegment[] newArray(int i) {
            return new android.os.vibrator.PrebakedSegment[i];
        }
    };
    public static final boolean DEFAULT_SHOULD_FALLBACK = true;
    public static final int DEFAULT_STRENGTH = 1;
    private final int mEffectId;
    private final int mEffectStrength;
    private final boolean mFallback;

    PrebakedSegment(android.os.Parcel parcel) {
        this.mEffectId = parcel.readInt();
        this.mFallback = parcel.readByte() != 0;
        this.mEffectStrength = parcel.readInt();
    }

    public PrebakedSegment(int i, boolean z, int i2) {
        this.mEffectId = i;
        this.mFallback = z;
        this.mEffectStrength = i2;
    }

    public int getEffectId() {
        return this.mEffectId;
    }

    public int getEffectStrength() {
        return this.mEffectStrength;
    }

    public boolean shouldFallback() {
        return this.mFallback;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public long getDuration() {
        return -1L;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean areVibrationFeaturesSupported(android.os.VibratorInfo vibratorInfo) {
        if (vibratorInfo.isEffectSupported(this.mEffectId) == 1) {
            return true;
        }
        if (!this.mFallback) {
            return false;
        }
        switch (this.mEffectId) {
            case 0:
            case 1:
            case 2:
            case 5:
                return true;
            case 3:
            case 4:
            default:
                return false;
        }
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public boolean isHapticFeedbackCandidate() {
        switch (this.mEffectId) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 21:
                return true;
            default:
                return false;
        }
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.PrebakedSegment resolve(int i) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.PrebakedSegment scale(float f) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.PrebakedSegment scaleLinearly(float f) {
        return this;
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public android.os.vibrator.PrebakedSegment applyEffectStrength(int i) {
        if (i != this.mEffectStrength && isValidEffectStrength(i)) {
            return new android.os.vibrator.PrebakedSegment(this.mEffectId, this.mFallback, i);
        }
        return this;
    }

    private static boolean isValidEffectStrength(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public void validate() {
        switch (this.mEffectId) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 21:
                break;
            default:
                int[] iArr = android.os.VibrationEffect.RINGTONES;
                if (this.mEffectId < iArr[0] || this.mEffectId > iArr[iArr.length - 1]) {
                    throw new java.lang.IllegalArgumentException("Unknown prebaked effect type (value=" + this.mEffectId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
                break;
        }
        if (!isValidEffectStrength(this.mEffectStrength)) {
            throw new java.lang.IllegalArgumentException("Unknown prebaked effect strength (value=" + this.mEffectStrength + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.os.vibrator.PrebakedSegment)) {
            return false;
        }
        android.os.vibrator.PrebakedSegment prebakedSegment = (android.os.vibrator.PrebakedSegment) obj;
        return this.mEffectId == prebakedSegment.mEffectId && this.mFallback == prebakedSegment.mFallback && this.mEffectStrength == prebakedSegment.mEffectStrength;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mEffectId), java.lang.Boolean.valueOf(this.mFallback), java.lang.Integer.valueOf(this.mEffectStrength));
    }

    public java.lang.String toString() {
        return "Prebaked{effect=" + android.os.VibrationEffect.effectIdToString(this.mEffectId) + ", strength=" + android.os.VibrationEffect.effectStrengthToString(this.mEffectStrength) + ", fallback=" + this.mFallback + "}";
    }

    @Override // android.os.vibrator.VibrationEffectSegment
    public java.lang.String toDebugString() {
        return java.lang.String.format("Prebaked=%s(%s, %s fallback)", android.os.VibrationEffect.effectIdToString(this.mEffectId), android.os.VibrationEffect.effectStrengthToString(this.mEffectStrength), this.mFallback ? "with" : android.media.MediaMetrics.Value.NO);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(1);
        parcel.writeInt(this.mEffectId);
        parcel.writeByte(this.mFallback ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mEffectStrength);
    }
}
