package android.hardware.face;

/* loaded from: classes2.dex */
public final class FaceDataFrame implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.face.FaceDataFrame> CREATOR = new android.os.Parcelable.Creator<android.hardware.face.FaceDataFrame>() { // from class: android.hardware.face.FaceDataFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceDataFrame createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.face.FaceDataFrame(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceDataFrame[] newArray(int i) {
            return new android.hardware.face.FaceDataFrame[i];
        }
    };
    private final int mAcquiredInfo;
    private final float mDistance;
    private final boolean mIsCancellable;
    private final float mPan;
    private final float mTilt;
    private final int mVendorCode;

    public FaceDataFrame(int i, int i2, float f, float f2, float f3, boolean z) {
        this.mAcquiredInfo = i;
        this.mVendorCode = i2;
        this.mPan = f;
        this.mTilt = f2;
        this.mDistance = f3;
        this.mIsCancellable = z;
    }

    public FaceDataFrame(int i, int i2) {
        this.mAcquiredInfo = i;
        this.mVendorCode = i2;
        this.mPan = 0.0f;
        this.mTilt = 0.0f;
        this.mDistance = 0.0f;
        this.mIsCancellable = false;
    }

    public int getAcquiredInfo() {
        return this.mAcquiredInfo;
    }

    public int getVendorCode() {
        return this.mVendorCode;
    }

    public float getPan() {
        return this.mPan;
    }

    public float getTilt() {
        return this.mTilt;
    }

    public float getDistance() {
        return this.mDistance;
    }

    public boolean isCancellable() {
        return this.mIsCancellable;
    }

    private FaceDataFrame(android.os.Parcel parcel) {
        this.mAcquiredInfo = parcel.readInt();
        this.mVendorCode = parcel.readInt();
        this.mPan = parcel.readFloat();
        this.mTilt = parcel.readFloat();
        this.mDistance = parcel.readFloat();
        this.mIsCancellable = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAcquiredInfo);
        parcel.writeInt(this.mVendorCode);
        parcel.writeFloat(this.mPan);
        parcel.writeFloat(this.mTilt);
        parcel.writeFloat(this.mDistance);
        parcel.writeBoolean(this.mIsCancellable);
    }
}
