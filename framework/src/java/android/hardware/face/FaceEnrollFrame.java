package android.hardware.face;

/* loaded from: classes2.dex */
public final class FaceEnrollFrame implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.face.FaceEnrollFrame> CREATOR = new android.os.Parcelable.Creator<android.hardware.face.FaceEnrollFrame>() { // from class: android.hardware.face.FaceEnrollFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceEnrollFrame createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.face.FaceEnrollFrame(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceEnrollFrame[] newArray(int i) {
            return new android.hardware.face.FaceEnrollFrame[i];
        }
    };
    private final android.hardware.face.FaceEnrollCell mCell;
    private final android.hardware.face.FaceDataFrame mData;
    private final int mStage;

    public FaceEnrollFrame(android.hardware.face.FaceEnrollCell faceEnrollCell, int i, android.hardware.face.FaceDataFrame faceDataFrame) {
        this.mCell = faceEnrollCell;
        this.mStage = i;
        this.mData = faceDataFrame;
    }

    public android.hardware.face.FaceEnrollCell getCell() {
        return this.mCell;
    }

    public int getStage() {
        return this.mStage;
    }

    public android.hardware.face.FaceDataFrame getData() {
        return this.mData;
    }

    private FaceEnrollFrame(android.os.Parcel parcel) {
        this.mCell = (android.hardware.face.FaceEnrollCell) parcel.readParcelable(android.hardware.face.FaceEnrollCell.class.getClassLoader(), android.hardware.face.FaceEnrollCell.class);
        this.mStage = parcel.readInt();
        this.mData = (android.hardware.face.FaceDataFrame) parcel.readParcelable(android.hardware.face.FaceDataFrame.class.getClassLoader(), android.hardware.face.FaceDataFrame.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mCell, i);
        parcel.writeInt(this.mStage);
        parcel.writeParcelable(this.mData, i);
    }
}
