package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class ConcurrentCameraIdCombination implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.utils.ConcurrentCameraIdCombination> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.utils.ConcurrentCameraIdCombination>() { // from class: android.hardware.camera2.utils.ConcurrentCameraIdCombination.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.utils.ConcurrentCameraIdCombination createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.camera2.utils.ConcurrentCameraIdCombination(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.utils.ConcurrentCameraIdCombination[] newArray(int i) {
            return new android.hardware.camera2.utils.ConcurrentCameraIdCombination[i];
        }
    };
    private java.util.Set<java.lang.String> mConcurrentCameraIds;

    private ConcurrentCameraIdCombination(android.os.Parcel parcel) {
        this.mConcurrentCameraIds = new java.util.HashSet();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mConcurrentCameraIds.size());
        java.util.Iterator<java.lang.String> it = this.mConcurrentCameraIds.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mConcurrentCameraIds.clear();
        int readInt = parcel.readInt();
        if (readInt < 0) {
            throw new java.lang.RuntimeException("cameraCombinationSize " + readInt + " should not be negative");
        }
        for (int i = 0; i < readInt; i++) {
            java.lang.String readString = parcel.readString();
            if (readString == null) {
                throw new java.lang.RuntimeException("Failed to read camera id from Parcel");
            }
            this.mConcurrentCameraIds.add(readString);
        }
    }

    public java.util.Set<java.lang.String> getConcurrentCameraIdCombination() {
        return this.mConcurrentCameraIds;
    }
}
