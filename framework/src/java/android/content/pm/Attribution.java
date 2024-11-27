package android.content.pm;

/* loaded from: classes.dex */
public final class Attribution implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.Attribution> CREATOR = new android.os.Parcelable.Creator<android.content.pm.Attribution>() { // from class: android.content.pm.Attribution.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.Attribution[] newArray(int i) {
            return new android.content.pm.Attribution[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.Attribution createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.Attribution(parcel);
        }
    };
    private final int mLabel;
    private final java.lang.String mTag;

    public Attribution(java.lang.String str, int i) {
        this.mTag = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTag);
        this.mLabel = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.IdRes.class, (java.lang.annotation.Annotation) null, this.mLabel);
    }

    public java.lang.String getTag() {
        return this.mTag;
    }

    public int getLabel() {
        return this.mLabel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mTag);
        parcel.writeInt(this.mLabel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Attribution(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        int readInt = parcel.readInt();
        this.mTag = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTag);
        this.mLabel = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.IdRes.class, (java.lang.annotation.Annotation) null, this.mLabel);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
