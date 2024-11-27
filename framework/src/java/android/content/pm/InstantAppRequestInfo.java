package android.content.pm;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class InstantAppRequestInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.InstantAppRequestInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.InstantAppRequestInfo>() { // from class: android.content.pm.InstantAppRequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstantAppRequestInfo[] newArray(int i) {
            return new android.content.pm.InstantAppRequestInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstantAppRequestInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.InstantAppRequestInfo(parcel);
        }
    };
    private final int[] mHostDigestPrefix;
    private final android.content.Intent mIntent;
    private final boolean mRequesterInstantApp;
    private final java.lang.String mToken;
    private final android.os.UserHandle mUserHandle;

    public InstantAppRequestInfo(android.content.Intent intent, int[] iArr, android.os.UserHandle userHandle, boolean z, java.lang.String str) {
        this.mIntent = intent;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mIntent);
        this.mHostDigestPrefix = iArr;
        this.mUserHandle = userHandle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mUserHandle);
        this.mRequesterInstantApp = z;
        this.mToken = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mToken);
    }

    public android.content.Intent getIntent() {
        return this.mIntent;
    }

    public int[] getHostDigestPrefix() {
        return this.mHostDigestPrefix;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public boolean isRequesterInstantApp() {
        return this.mRequesterInstantApp;
    }

    public java.lang.String getToken() {
        return this.mToken;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mRequesterInstantApp ? (byte) 8 : (byte) 0;
        if (this.mHostDigestPrefix != null) {
            b = (byte) (b | 2);
        }
        parcel.writeByte(b);
        parcel.writeTypedObject(this.mIntent, i);
        if (this.mHostDigestPrefix != null) {
            parcel.writeIntArray(this.mHostDigestPrefix);
        }
        parcel.writeTypedObject(this.mUserHandle, i);
        parcel.writeString(this.mToken);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InstantAppRequestInfo(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        boolean z = (readByte & 8) != 0;
        android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
        int[] createIntArray = (readByte & 2) == 0 ? null : parcel.createIntArray();
        android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
        java.lang.String readString = parcel.readString();
        this.mIntent = intent;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mIntent);
        this.mHostDigestPrefix = createIntArray;
        this.mUserHandle = userHandle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mUserHandle);
        this.mRequesterInstantApp = z;
        this.mToken = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mToken);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
