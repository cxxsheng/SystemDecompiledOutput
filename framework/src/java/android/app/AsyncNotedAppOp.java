package android.app;

/* loaded from: classes.dex */
public final class AsyncNotedAppOp implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.AsyncNotedAppOp> CREATOR = new android.os.Parcelable.Creator<android.app.AsyncNotedAppOp>() { // from class: android.app.AsyncNotedAppOp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.AsyncNotedAppOp[] newArray(int i) {
            return new android.app.AsyncNotedAppOp[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.AsyncNotedAppOp createFromParcel(android.os.Parcel parcel) {
            return new android.app.AsyncNotedAppOp(parcel);
        }
    };
    private final java.lang.String mAttributionTag;
    private final java.lang.String mMessage;
    private final int mNotingUid;
    private final int mOpCode;
    private final long mTime;

    public java.lang.String getOp() {
        return android.app.AppOpsManager.opToPublicName(this.mOpCode);
    }

    private void onConstructed() {
        com.android.internal.util.Preconditions.checkArgumentInRange(this.mOpCode, 0, 147, "opCode");
    }

    private java.lang.String opCodeToString() {
        return getOp();
    }

    public AsyncNotedAppOp(int i, int i2, java.lang.String str, java.lang.String str2, long j) {
        this.mOpCode = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mOpCode, "from", 0L);
        this.mNotingUid = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mNotingUid, "from", 0L);
        this.mAttributionTag = str;
        this.mMessage = str2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMessage);
        this.mTime = j;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.CurrentTimeMillisLong.class, (java.lang.annotation.Annotation) null, this.mTime);
        onConstructed();
    }

    public int getNotingUid() {
        return this.mNotingUid;
    }

    public java.lang.String getAttributionTag() {
        return this.mAttributionTag;
    }

    public java.lang.String getMessage() {
        return this.mMessage;
    }

    public long getTime() {
        return this.mTime;
    }

    public java.lang.String toString() {
        return "AsyncNotedAppOp { opCode = " + opCodeToString() + ", notingUid = " + this.mNotingUid + ", attributionTag = " + this.mAttributionTag + ", message = " + this.mMessage + ", time = " + this.mTime + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.AsyncNotedAppOp asyncNotedAppOp = (android.app.AsyncNotedAppOp) obj;
        if (this.mOpCode == asyncNotedAppOp.mOpCode && this.mNotingUid == asyncNotedAppOp.mNotingUid && java.util.Objects.equals(this.mAttributionTag, asyncNotedAppOp.mAttributionTag) && java.util.Objects.equals(this.mMessage, asyncNotedAppOp.mMessage) && this.mTime == asyncNotedAppOp.mTime) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((this.mOpCode + 31) * 31) + this.mNotingUid) * 31) + java.util.Objects.hashCode(this.mAttributionTag)) * 31) + java.util.Objects.hashCode(this.mMessage)) * 31) + java.lang.Long.hashCode(this.mTime);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mAttributionTag != null ? (byte) 4 : (byte) 0);
        parcel.writeInt(this.mOpCode);
        parcel.writeInt(this.mNotingUid);
        if (this.mAttributionTag != null) {
            parcel.writeString(this.mAttributionTag);
        }
        parcel.writeString(this.mMessage);
        parcel.writeLong(this.mTime);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AsyncNotedAppOp(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        java.lang.String readString = (readByte & 4) == 0 ? null : parcel.readString();
        java.lang.String readString2 = parcel.readString();
        long readLong = parcel.readLong();
        this.mOpCode = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mOpCode, "from", 0L);
        this.mNotingUid = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mNotingUid, "from", 0L);
        this.mAttributionTag = readString;
        this.mMessage = readString2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMessage);
        this.mTime = readLong;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.CurrentTimeMillisLong.class, (java.lang.annotation.Annotation) null, this.mTime);
        onConstructed();
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
