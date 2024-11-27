package android.media.tv;

/* loaded from: classes2.dex */
public final class SignalingDataInfo implements android.os.Parcelable {
    public static final java.lang.String CONTENT_ENCODING_BASE64 = "Base64";
    public static final java.lang.String CONTENT_ENCODING_UTF_8 = "UTF-8";
    public static final android.os.Parcelable.Creator<android.media.tv.SignalingDataInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.SignalingDataInfo>() { // from class: android.media.tv.SignalingDataInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.SignalingDataInfo[] newArray(int i) {
            return new android.media.tv.SignalingDataInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.SignalingDataInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.SignalingDataInfo(parcel);
        }
    };
    public static final int LLS_NO_GROUP_ID = -1;
    private final java.lang.String mEncoding;
    private final int mGroup;
    private final java.lang.String mSignalingDataType;
    private final java.lang.String mTable;
    private final int mVersion;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ContentEncoding {
    }

    public SignalingDataInfo(java.lang.String str, java.lang.String str2, int i, int i2) {
        this.mTable = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTable);
        this.mSignalingDataType = str2;
        this.mVersion = i;
        this.mGroup = i2;
        this.mEncoding = CONTENT_ENCODING_UTF_8;
    }

    public SignalingDataInfo(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) {
        this.mTable = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTable);
        this.mSignalingDataType = str2;
        this.mVersion = i;
        this.mGroup = i2;
        this.mEncoding = str3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mEncoding);
    }

    public java.lang.String getTable() {
        return this.mTable;
    }

    public java.lang.String getSignalingDataType() {
        return this.mSignalingDataType;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public int getGroup() {
        return this.mGroup;
    }

    public java.lang.String getEncoding() {
        return this.mEncoding;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mTable);
        parcel.writeString(this.mSignalingDataType);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mGroup);
        parcel.writeString(this.mEncoding);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SignalingDataInfo(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        java.lang.String readString2 = parcel.readString();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        java.lang.String readString3 = parcel.readString();
        this.mTable = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTable);
        this.mSignalingDataType = readString2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSignalingDataType);
        this.mVersion = readInt;
        this.mGroup = readInt2;
        this.mEncoding = readString3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mEncoding);
    }
}
