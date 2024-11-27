package com.android.internal.view;

/* loaded from: classes5.dex */
public class AppearanceRegion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.view.AppearanceRegion> CREATOR = new android.os.Parcelable.Creator<com.android.internal.view.AppearanceRegion>() { // from class: com.android.internal.view.AppearanceRegion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.view.AppearanceRegion[] newArray(int i) {
            return new com.android.internal.view.AppearanceRegion[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.view.AppearanceRegion createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.view.AppearanceRegion(parcel);
        }
    };
    private int mAppearance;
    private android.graphics.Rect mBounds;

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com.android.internal.view.AppearanceRegion appearanceRegion = (com.android.internal.view.AppearanceRegion) obj;
        if (this.mAppearance == appearanceRegion.mAppearance && this.mBounds.equals(appearanceRegion.mBounds)) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        return "AppearanceRegion{" + android.view.ViewDebug.flagsToString(android.view.InsetsFlags.class, "appearance", this.mAppearance) + " bounds=" + this.mBounds.toShortString() + "}";
    }

    public AppearanceRegion(int i, android.graphics.Rect rect) {
        this.mAppearance = i;
        this.mBounds = rect;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mBounds);
    }

    public int getAppearance() {
        return this.mAppearance;
    }

    public android.graphics.Rect getBounds() {
        return this.mBounds;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAppearance);
        parcel.writeTypedObject(this.mBounds, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected AppearanceRegion(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.mAppearance = readInt;
        this.mBounds = rect;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mBounds);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
