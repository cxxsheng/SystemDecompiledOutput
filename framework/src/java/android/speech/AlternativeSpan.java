package android.speech;

/* loaded from: classes3.dex */
public final class AlternativeSpan implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.speech.AlternativeSpan> CREATOR = new android.os.Parcelable.Creator<android.speech.AlternativeSpan>() { // from class: android.speech.AlternativeSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.speech.AlternativeSpan[] newArray(int i) {
            return new android.speech.AlternativeSpan[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.speech.AlternativeSpan createFromParcel(android.os.Parcel parcel) {
            return new android.speech.AlternativeSpan(parcel);
        }
    };
    private final java.util.List<java.lang.String> mAlternatives;
    private final int mEndPosition;
    private final int mStartPosition;

    private void onConstructed() {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(this.mStartPosition, "The range start must be non-negative.");
        com.android.internal.util.Preconditions.checkArgument(this.mStartPosition < this.mEndPosition, "Illegal range [%d, %d), must be start < end.", java.lang.Integer.valueOf(this.mStartPosition), java.lang.Integer.valueOf(this.mEndPosition));
        com.android.internal.util.Preconditions.checkCollectionNotEmpty(this.mAlternatives, "List of alternative strings must not be empty.");
    }

    public AlternativeSpan(int i, int i2, java.util.List<java.lang.String> list) {
        this.mStartPosition = i;
        this.mEndPosition = i2;
        this.mAlternatives = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAlternatives);
        onConstructed();
    }

    public int getStartPosition() {
        return this.mStartPosition;
    }

    public int getEndPosition() {
        return this.mEndPosition;
    }

    public java.util.List<java.lang.String> getAlternatives() {
        return this.mAlternatives;
    }

    public java.lang.String toString() {
        return "AlternativeSpan { startPosition = " + this.mStartPosition + ", endPosition = " + this.mEndPosition + ", alternatives = " + this.mAlternatives + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.speech.AlternativeSpan alternativeSpan = (android.speech.AlternativeSpan) obj;
        if (this.mStartPosition == alternativeSpan.mStartPosition && this.mEndPosition == alternativeSpan.mEndPosition && java.util.Objects.equals(this.mAlternatives, alternativeSpan.mAlternatives)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((this.mStartPosition + 31) * 31) + this.mEndPosition) * 31) + java.util.Objects.hashCode(this.mAlternatives);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStartPosition);
        parcel.writeInt(this.mEndPosition);
        parcel.writeStringList(this.mAlternatives);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AlternativeSpan(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        this.mStartPosition = readInt;
        this.mEndPosition = readInt2;
        this.mAlternatives = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAlternatives);
        onConstructed();
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
