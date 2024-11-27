package android.app;

/* loaded from: classes.dex */
public final class SyncNotedAppOp implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.SyncNotedAppOp> CREATOR = new android.os.Parcelable.Creator<android.app.SyncNotedAppOp>() { // from class: android.app.SyncNotedAppOp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.SyncNotedAppOp[] newArray(int i) {
            return new android.app.SyncNotedAppOp[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.SyncNotedAppOp createFromParcel(android.os.Parcel parcel) {
            return new android.app.SyncNotedAppOp(parcel);
        }
    };
    private final java.lang.String mAttributionTag;
    private final int mOpCode;
    private final int mOpMode;
    private final java.lang.String mPackageName;

    public SyncNotedAppOp(int i, int i2, java.lang.String str, java.lang.String str2) {
        this.mOpCode = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mOpCode, "from", 0L, "to", 147L);
        this.mAttributionTag = str;
        this.mOpMode = i;
        this.mPackageName = str2;
    }

    public SyncNotedAppOp(int i, java.lang.String str) {
        this(1, i, str, android.app.ActivityThread.currentPackageName());
    }

    public SyncNotedAppOp(int i, java.lang.String str, java.lang.String str2) {
        this(1, i, str, str2);
    }

    public java.lang.String getOp() {
        return android.app.AppOpsManager.opToPublicName(this.mOpCode);
    }

    public int getOpMode() {
        return this.mOpMode;
    }

    private java.lang.String opCodeToString() {
        return getOp();
    }

    public java.lang.String getAttributionTag() {
        return this.mAttributionTag;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String toString() {
        return "SyncNotedAppOp { opMode = " + this.mOpMode + ", opCode = " + opCodeToString() + ", attributionTag = " + this.mAttributionTag + ", packageName = " + this.mPackageName + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.SyncNotedAppOp syncNotedAppOp = (android.app.SyncNotedAppOp) obj;
        if (this.mOpMode == syncNotedAppOp.mOpMode && this.mOpCode == syncNotedAppOp.mOpCode && java.util.Objects.equals(this.mAttributionTag, syncNotedAppOp.mAttributionTag) && java.util.Objects.equals(this.mPackageName, syncNotedAppOp.mPackageName)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.mOpMode + 31) * 31) + this.mOpCode) * 31) + java.util.Objects.hashCode(this.mAttributionTag)) * 31) + java.util.Objects.hashCode(this.mPackageName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mAttributionTag != null ? (byte) 4 : (byte) 0;
        if (this.mPackageName != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mOpMode);
        parcel.writeInt(this.mOpCode);
        if (this.mAttributionTag != null) {
            parcel.writeString(this.mAttributionTag);
        }
        if (this.mPackageName != null) {
            parcel.writeString(this.mPackageName);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SyncNotedAppOp(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        java.lang.String readString = (readByte & 4) == 0 ? null : parcel.readString();
        java.lang.String readString2 = (readByte & 8) != 0 ? parcel.readString() : null;
        this.mOpMode = readInt;
        this.mOpCode = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mOpCode, "from", 0L, "to", 147L);
        this.mAttributionTag = readString;
        this.mPackageName = readString2;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
