package android.hardware.input;

/* loaded from: classes2.dex */
public class InputSensorInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.InputSensorInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.InputSensorInfo>() { // from class: android.hardware.input.InputSensorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.InputSensorInfo[] newArray(int i) {
            return new android.hardware.input.InputSensorInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.InputSensorInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.InputSensorInfo(parcel);
        }
    };
    private int mFifoMaxEventCount;
    private int mFifoReservedEventCount;
    private int mFlags;
    private int mHandle;
    private int mId;
    private int mMaxDelay;
    private float mMaxRange;
    private int mMinDelay;
    private java.lang.String mName;
    private float mPower;
    private java.lang.String mRequiredPermission;
    private float mResolution;
    private java.lang.String mStringType;
    private int mType;
    private java.lang.String mVendor;
    private int mVersion;

    public InputSensorInfo(java.lang.String str, java.lang.String str2, int i, int i2, int i3, float f, float f2, float f3, int i4, int i5, int i6, java.lang.String str3, java.lang.String str4, int i7, int i8, int i9) {
        this.mName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mName);
        this.mVendor = str2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mVendor);
        this.mVersion = i;
        this.mHandle = i2;
        this.mType = i3;
        this.mMaxRange = f;
        this.mResolution = f2;
        this.mPower = f3;
        this.mMinDelay = i4;
        this.mFifoReservedEventCount = i5;
        this.mFifoMaxEventCount = i6;
        this.mStringType = str3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mStringType);
        this.mRequiredPermission = str4;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRequiredPermission);
        this.mMaxDelay = i7;
        this.mFlags = i8;
        this.mId = i9;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getVendor() {
        return this.mVendor;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public int getHandle() {
        return this.mHandle;
    }

    public int getType() {
        return this.mType;
    }

    public float getMaxRange() {
        return this.mMaxRange;
    }

    public float getResolution() {
        return this.mResolution;
    }

    public float getPower() {
        return this.mPower;
    }

    public int getMinDelay() {
        return this.mMinDelay;
    }

    public int getFifoReservedEventCount() {
        return this.mFifoReservedEventCount;
    }

    public int getFifoMaxEventCount() {
        return this.mFifoMaxEventCount;
    }

    public java.lang.String getStringType() {
        return this.mStringType;
    }

    public java.lang.String getRequiredPermission() {
        return this.mRequiredPermission;
    }

    public int getMaxDelay() {
        return this.mMaxDelay;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getId() {
        return this.mId;
    }

    public java.lang.String toString() {
        return "InputSensorInfo { name = " + this.mName + ", vendor = " + this.mVendor + ", version = " + this.mVersion + ", handle = " + this.mHandle + ", type = " + this.mType + ", maxRange = " + this.mMaxRange + ", resolution = " + this.mResolution + ", power = " + this.mPower + ", minDelay = " + this.mMinDelay + ", fifoReservedEventCount = " + this.mFifoReservedEventCount + ", fifoMaxEventCount = " + this.mFifoMaxEventCount + ", stringType = " + this.mStringType + ", requiredPermission = " + this.mRequiredPermission + ", maxDelay = " + this.mMaxDelay + ", flags = " + this.mFlags + ", id = " + this.mId + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mVendor);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mHandle);
        parcel.writeInt(this.mType);
        parcel.writeFloat(this.mMaxRange);
        parcel.writeFloat(this.mResolution);
        parcel.writeFloat(this.mPower);
        parcel.writeInt(this.mMinDelay);
        parcel.writeInt(this.mFifoReservedEventCount);
        parcel.writeInt(this.mFifoMaxEventCount);
        parcel.writeString(this.mStringType);
        parcel.writeString(this.mRequiredPermission);
        parcel.writeInt(this.mMaxDelay);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected InputSensorInfo(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        java.lang.String readString2 = parcel.readString();
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        float readFloat = parcel.readFloat();
        float readFloat2 = parcel.readFloat();
        float readFloat3 = parcel.readFloat();
        int readInt4 = parcel.readInt();
        int readInt5 = parcel.readInt();
        int readInt6 = parcel.readInt();
        java.lang.String readString3 = parcel.readString();
        java.lang.String readString4 = parcel.readString();
        int readInt7 = parcel.readInt();
        int readInt8 = parcel.readInt();
        int readInt9 = parcel.readInt();
        this.mName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mName);
        this.mVendor = readString2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mVendor);
        this.mVersion = readInt;
        this.mHandle = readInt2;
        this.mType = readInt3;
        this.mMaxRange = readFloat;
        this.mResolution = readFloat2;
        this.mPower = readFloat3;
        this.mMinDelay = readInt4;
        this.mFifoReservedEventCount = readInt5;
        this.mFifoMaxEventCount = readInt6;
        this.mStringType = readString3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mStringType);
        this.mRequiredPermission = readString4;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mRequiredPermission);
        this.mMaxDelay = readInt7;
        this.mFlags = readInt8;
        this.mId = readInt9;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
