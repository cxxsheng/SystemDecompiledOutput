package android.hardware.face;

/* loaded from: classes2.dex */
public final class FaceEnrollCell implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.face.FaceEnrollCell> CREATOR = new android.os.Parcelable.Creator<android.hardware.face.FaceEnrollCell>() { // from class: android.hardware.face.FaceEnrollCell.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceEnrollCell createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.face.FaceEnrollCell(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceEnrollCell[] newArray(int i) {
            return new android.hardware.face.FaceEnrollCell[i];
        }
    };
    private final int mX;
    private final int mY;
    private final int mZ;

    public FaceEnrollCell(int i, int i2, int i3) {
        this.mX = i;
        this.mY = i2;
        this.mZ = i3;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getZ() {
        return this.mZ;
    }

    private FaceEnrollCell(android.os.Parcel parcel) {
        this.mX = parcel.readInt();
        this.mY = parcel.readInt();
        this.mZ = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mX);
        parcel.writeInt(this.mY);
        parcel.writeInt(this.mZ);
    }
}
