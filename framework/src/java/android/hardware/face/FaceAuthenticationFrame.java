package android.hardware.face;

/* loaded from: classes2.dex */
public final class FaceAuthenticationFrame implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.face.FaceAuthenticationFrame> CREATOR = new android.os.Parcelable.Creator<android.hardware.face.FaceAuthenticationFrame>() { // from class: android.hardware.face.FaceAuthenticationFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceAuthenticationFrame createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.face.FaceAuthenticationFrame(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.FaceAuthenticationFrame[] newArray(int i) {
            return new android.hardware.face.FaceAuthenticationFrame[i];
        }
    };
    private final android.hardware.face.FaceDataFrame mData;

    public FaceAuthenticationFrame(android.hardware.face.FaceDataFrame faceDataFrame) {
        this.mData = faceDataFrame;
    }

    public android.hardware.face.FaceDataFrame getData() {
        return this.mData;
    }

    private FaceAuthenticationFrame(android.os.Parcel parcel) {
        this.mData = (android.hardware.face.FaceDataFrame) parcel.readParcelable(android.hardware.face.FaceDataFrame.class.getClassLoader(), android.hardware.face.FaceDataFrame.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mData, i);
    }
}
