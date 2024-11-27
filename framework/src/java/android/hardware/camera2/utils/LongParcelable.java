package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class LongParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.utils.LongParcelable> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.utils.LongParcelable>() { // from class: android.hardware.camera2.utils.LongParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.utils.LongParcelable createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.camera2.utils.LongParcelable(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.utils.LongParcelable[] newArray(int i) {
            return new android.hardware.camera2.utils.LongParcelable[i];
        }
    };
    private long number;

    public LongParcelable() {
        this.number = 0L;
    }

    public LongParcelable(long j) {
        this.number = j;
    }

    private LongParcelable(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.number);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.number = parcel.readLong();
    }

    public long getNumber() {
        return this.number;
    }

    public void setNumber(long j) {
        this.number = j;
    }
}
