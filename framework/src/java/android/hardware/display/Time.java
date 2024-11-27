package android.hardware.display;

/* loaded from: classes2.dex */
public final class Time implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.display.Time> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.Time>() { // from class: android.hardware.display.Time.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.Time createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.display.Time(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.Time[] newArray(int i) {
            return new android.hardware.display.Time[i];
        }
    };
    private final int mHour;
    private final int mMinute;
    private final int mNano;
    private final int mSecond;

    public Time(java.time.LocalTime localTime) {
        this.mHour = localTime.getHour();
        this.mMinute = localTime.getMinute();
        this.mSecond = localTime.getSecond();
        this.mNano = localTime.getNano();
    }

    public Time(android.os.Parcel parcel) {
        this.mHour = parcel.readInt();
        this.mMinute = parcel.readInt();
        this.mSecond = parcel.readInt();
        this.mNano = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mHour);
        parcel.writeInt(this.mMinute);
        parcel.writeInt(this.mSecond);
        parcel.writeInt(this.mNano);
    }

    public java.time.LocalTime getLocalTime() {
        return java.time.LocalTime.of(this.mHour, this.mMinute, this.mSecond, this.mNano);
    }
}
