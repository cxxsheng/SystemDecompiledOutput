package com.android.internal.statusbar;

/* loaded from: classes5.dex */
public class LetterboxDetails implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.statusbar.LetterboxDetails> CREATOR = new android.os.Parcelable.Creator<com.android.internal.statusbar.LetterboxDetails>() { // from class: com.android.internal.statusbar.LetterboxDetails.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.statusbar.LetterboxDetails[] newArray(int i) {
            return new com.android.internal.statusbar.LetterboxDetails[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.statusbar.LetterboxDetails createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.statusbar.LetterboxDetails(parcel);
        }
    };
    private final int mAppAppearance;
    private final android.graphics.Rect mLetterboxFullBounds;
    private final android.graphics.Rect mLetterboxInnerBounds;

    public android.graphics.Rect getLetterboxInnerBounds() {
        return this.mLetterboxInnerBounds;
    }

    public android.graphics.Rect getLetterboxFullBounds() {
        return this.mLetterboxFullBounds;
    }

    public int getAppAppearance() {
        return this.mAppAppearance;
    }

    public java.lang.String appAppearanceToString() {
        return android.view.ViewDebug.flagsToString(android.view.InsetsFlags.class, "appearance", this.mAppAppearance);
    }

    public LetterboxDetails(android.graphics.Rect rect, android.graphics.Rect rect2, int i) {
        this.mLetterboxInnerBounds = rect;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mLetterboxInnerBounds);
        this.mLetterboxFullBounds = rect2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mLetterboxFullBounds);
        this.mAppAppearance = i;
    }

    public java.lang.String toString() {
        return "LetterboxDetails { letterboxInnerBounds = " + this.mLetterboxInnerBounds + ", letterboxFullBounds = " + this.mLetterboxFullBounds + ", appAppearance = " + appAppearanceToString() + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com.android.internal.statusbar.LetterboxDetails letterboxDetails = (com.android.internal.statusbar.LetterboxDetails) obj;
        if (java.util.Objects.equals(this.mLetterboxInnerBounds, letterboxDetails.mLetterboxInnerBounds) && java.util.Objects.equals(this.mLetterboxFullBounds, letterboxDetails.mLetterboxFullBounds) && this.mAppAppearance == letterboxDetails.mAppAppearance) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mLetterboxInnerBounds) + 31) * 31) + java.util.Objects.hashCode(this.mLetterboxFullBounds)) * 31) + this.mAppAppearance;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mLetterboxInnerBounds, i);
        parcel.writeTypedObject(this.mLetterboxFullBounds, i);
        parcel.writeInt(this.mAppAppearance);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected LetterboxDetails(android.os.Parcel parcel) {
        android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        int readInt = parcel.readInt();
        this.mLetterboxInnerBounds = rect;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mLetterboxInnerBounds);
        this.mLetterboxFullBounds = rect2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mLetterboxFullBounds);
        this.mAppAppearance = readInt;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
