package android.os;

/* loaded from: classes3.dex */
public class BatteryProperty implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.BatteryProperty> CREATOR = new android.os.Parcelable.Creator<android.os.BatteryProperty>() { // from class: android.os.BatteryProperty.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BatteryProperty createFromParcel(android.os.Parcel parcel) {
            return new android.os.BatteryProperty(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BatteryProperty[] newArray(int i) {
            return new android.os.BatteryProperty[i];
        }
    };
    private long mValueLong;
    private java.lang.String mValueString;

    public BatteryProperty() {
        this.mValueLong = Long.MIN_VALUE;
        this.mValueString = null;
    }

    public long getLong() {
        return this.mValueLong;
    }

    public java.lang.String getString() {
        return this.mValueString;
    }

    public void setLong(long j) {
        this.mValueLong = j;
    }

    public void setString(java.lang.String str) {
        this.mValueString = str;
    }

    private BatteryProperty(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mValueLong = parcel.readLong();
        this.mValueString = parcel.readString8();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mValueLong);
        parcel.writeString8(this.mValueString);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
