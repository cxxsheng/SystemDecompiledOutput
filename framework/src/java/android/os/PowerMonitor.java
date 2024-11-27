package android.os;

/* loaded from: classes3.dex */
public final class PowerMonitor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.PowerMonitor> CREATOR = new android.os.Parcelable.Creator<android.os.PowerMonitor>() { // from class: android.os.PowerMonitor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.PowerMonitor createFromParcel(android.os.Parcel parcel) {
            return new android.os.PowerMonitor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.PowerMonitor[] newArray(int i) {
            return new android.os.PowerMonitor[i];
        }
    };
    public static final int POWER_MONITOR_TYPE_CONSUMER = 0;
    public static final int POWER_MONITOR_TYPE_MEASUREMENT = 1;
    public final int index;
    private final java.lang.String mName;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PowerMonitorType {
    }

    public PowerMonitor(int i, int i2, java.lang.String str) {
        this.index = i;
        this.mType = i2;
        this.mName = str;
    }

    public int getType() {
        return this.mType;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    private PowerMonitor(android.os.Parcel parcel) {
        this.index = parcel.readInt();
        this.mType = parcel.readInt();
        this.mName = parcel.readString8();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.index);
        parcel.writeInt(this.mType);
        parcel.writeString8(this.mName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
