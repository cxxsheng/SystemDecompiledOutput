package android.media.tv.interactive;

/* loaded from: classes2.dex */
public final class AppLinkInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.interactive.AppLinkInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.interactive.AppLinkInfo>() { // from class: android.media.tv.interactive.AppLinkInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.interactive.AppLinkInfo[] newArray(int i) {
            return new android.media.tv.interactive.AppLinkInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.interactive.AppLinkInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.interactive.AppLinkInfo(parcel);
        }
    };
    private android.content.ComponentName mComponentName;
    private android.net.Uri mUri;

    public AppLinkInfo(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) str);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) str2);
        this.mComponentName = new android.content.ComponentName(str, str2);
        this.mUri = android.net.Uri.parse(str3);
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    public java.lang.String toString() {
        return "AppLinkInfo { packageName = " + this.mComponentName.getPackageName() + ", className = " + this.mComponentName.getClassName() + ", uri = " + this.mUri.toString() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mComponentName.writeToParcel(parcel, i);
        parcel.writeString(this.mUri == null ? null : this.mUri.toString());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AppLinkInfo(android.os.Parcel parcel) {
        this.mComponentName = android.content.ComponentName.readFromParcel(parcel);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mComponentName.getPackageName());
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mComponentName.getClassName());
        java.lang.String readString = parcel.readString();
        this.mUri = readString != null ? android.net.Uri.parse(readString) : null;
    }
}
