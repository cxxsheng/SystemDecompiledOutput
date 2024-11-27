package android.app.admin;

/* loaded from: classes.dex */
public abstract class NetworkEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.NetworkEvent> CREATOR = new android.os.Parcelable.Creator<android.app.admin.NetworkEvent>() { // from class: android.app.admin.NetworkEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.NetworkEvent createFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            parcel.setDataPosition(dataPosition);
            switch (readInt) {
                case 1:
                    return android.app.admin.DnsEvent.CREATOR.createFromParcel(parcel);
                case 2:
                    return android.app.admin.ConnectEvent.CREATOR.createFromParcel(parcel);
                default:
                    throw new android.os.ParcelFormatException("Unexpected NetworkEvent token in parcel: " + readInt);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.NetworkEvent[] newArray(int i) {
            return new android.app.admin.NetworkEvent[i];
        }
    };
    static final int PARCEL_TOKEN_CONNECT_EVENT = 2;
    static final int PARCEL_TOKEN_DNS_EVENT = 1;
    long mId;
    java.lang.String mPackageName;
    long mTimestamp;

    @Override // android.os.Parcelable
    public abstract void writeToParcel(android.os.Parcel parcel, int i);

    NetworkEvent() {
    }

    NetworkEvent(java.lang.String str, long j) {
        this.mPackageName = str;
        this.mTimestamp = j;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setId(long j) {
        this.mId = j;
    }

    public long getId() {
        return this.mId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
