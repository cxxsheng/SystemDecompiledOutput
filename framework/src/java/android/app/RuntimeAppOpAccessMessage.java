package android.app;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class RuntimeAppOpAccessMessage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.RuntimeAppOpAccessMessage> CREATOR = new android.os.Parcelable.Creator<android.app.RuntimeAppOpAccessMessage>() { // from class: android.app.RuntimeAppOpAccessMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RuntimeAppOpAccessMessage[] newArray(int i) {
            return new android.app.RuntimeAppOpAccessMessage[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RuntimeAppOpAccessMessage createFromParcel(android.os.Parcel parcel) {
            return new android.app.RuntimeAppOpAccessMessage(parcel);
        }
    };
    private final java.lang.String mAttributionTag;
    private final java.lang.String mMessage;
    private final int mOpCode;
    private final java.lang.String mPackageName;
    private final int mSamplingStrategy;
    private final int mUid;

    public java.lang.String getOp() {
        return android.app.AppOpsManager.opToPublicName(this.mOpCode);
    }

    public RuntimeAppOpAccessMessage(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, int i3) {
        this.mUid = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mUid, "from", 0L);
        this.mOpCode = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mOpCode, "from", 0L, "to", 147L);
        this.mPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mAttributionTag = str2;
        this.mMessage = str3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMessage);
        this.mSamplingStrategy = i3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.AppOpsManager.SamplingStrategy.class, (java.lang.annotation.Annotation) null, this.mSamplingStrategy);
    }

    public int getUid() {
        return this.mUid;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getAttributionTag() {
        return this.mAttributionTag;
    }

    public java.lang.String getMessage() {
        return this.mMessage;
    }

    public int getSamplingStrategy() {
        return this.mSamplingStrategy;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mAttributionTag != null ? (byte) 8 : (byte) 0);
        parcel.writeInt(this.mUid);
        parcel.writeInt(this.mOpCode);
        parcel.writeString(this.mPackageName);
        if (this.mAttributionTag != null) {
            parcel.writeString(this.mAttributionTag);
        }
        parcel.writeString(this.mMessage);
        parcel.writeInt(this.mSamplingStrategy);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RuntimeAppOpAccessMessage(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        java.lang.String readString = parcel.readString();
        java.lang.String readString2 = (readByte & 8) == 0 ? null : parcel.readString();
        java.lang.String readString3 = parcel.readString();
        int readInt3 = parcel.readInt();
        this.mUid = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mUid, "from", 0L);
        this.mOpCode = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mOpCode, "from", 0L, "to", 147L);
        this.mPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mAttributionTag = readString2;
        this.mMessage = readString3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMessage);
        this.mSamplingStrategy = readInt3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.AppOpsManager.SamplingStrategy.class, (java.lang.annotation.Annotation) null, this.mSamplingStrategy);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
