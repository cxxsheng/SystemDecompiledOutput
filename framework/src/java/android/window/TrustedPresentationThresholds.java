package android.window;

/* loaded from: classes4.dex */
public final class TrustedPresentationThresholds implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TrustedPresentationThresholds> CREATOR = new android.os.Parcelable.Creator<android.window.TrustedPresentationThresholds>() { // from class: android.window.TrustedPresentationThresholds.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TrustedPresentationThresholds[] newArray(int i) {
            return new android.window.TrustedPresentationThresholds[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TrustedPresentationThresholds createFromParcel(android.os.Parcel parcel) {
            return new android.window.TrustedPresentationThresholds(parcel);
        }
    };
    private final float mMinAlpha;
    private final float mMinFractionRendered;
    private final int mStabilityRequirementMs;

    public float getMinAlpha() {
        return this.mMinAlpha;
    }

    public float getMinFractionRendered() {
        return this.mMinFractionRendered;
    }

    public int getStabilityRequirementMillis() {
        return this.mStabilityRequirementMs;
    }

    private void checkValid() {
        if (this.mMinAlpha <= 0.0f || this.mMinFractionRendered <= 0.0f || this.mStabilityRequirementMs < 1) {
            throw new java.lang.IllegalArgumentException("TrustedPresentationThresholds values are invalid");
        }
    }

    public TrustedPresentationThresholds(float f, float f2, int i) {
        this.mMinAlpha = f;
        this.mMinFractionRendered = f2;
        this.mStabilityRequirementMs = i;
        checkValid();
    }

    public java.lang.String toString() {
        return "TrustedPresentationThresholds { minAlpha = " + this.mMinAlpha + ", minFractionRendered = " + this.mMinFractionRendered + ", stabilityRequirementMs = " + this.mStabilityRequirementMs + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mMinAlpha);
        parcel.writeFloat(this.mMinFractionRendered);
        parcel.writeInt(this.mStabilityRequirementMs);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mMinAlpha), java.lang.Float.valueOf(this.mMinFractionRendered), java.lang.Integer.valueOf(this.mStabilityRequirementMs));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.window.TrustedPresentationThresholds)) {
            return false;
        }
        android.window.TrustedPresentationThresholds trustedPresentationThresholds = (android.window.TrustedPresentationThresholds) obj;
        return this.mMinAlpha == trustedPresentationThresholds.mMinAlpha && this.mMinFractionRendered == trustedPresentationThresholds.mMinFractionRendered && this.mStabilityRequirementMs == trustedPresentationThresholds.mStabilityRequirementMs;
    }

    TrustedPresentationThresholds(android.os.Parcel parcel) {
        this.mMinAlpha = parcel.readFloat();
        this.mMinFractionRendered = parcel.readFloat();
        this.mStabilityRequirementMs = parcel.readInt();
        checkValid();
    }
}
