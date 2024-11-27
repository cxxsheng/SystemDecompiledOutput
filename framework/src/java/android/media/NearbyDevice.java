package android.media;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class NearbyDevice implements android.os.Parcelable {
    public static final int RANGE_CLOSE = 3;
    public static final int RANGE_FAR = 1;
    public static final int RANGE_LONG = 2;
    public static final int RANGE_UNKNOWN = 0;
    public static final int RANGE_WITHIN_REACH = 4;
    private final java.lang.String mMediaRoute2Id;
    private final int mRangeZone;
    private static final java.util.List<java.lang.Integer> RANGE_WEIGHT_LIST = java.util.Arrays.asList(0, 1, 2, 3, 4);
    public static final android.os.Parcelable.Creator<android.media.NearbyDevice> CREATOR = new android.os.Parcelable.Creator<android.media.NearbyDevice>() { // from class: android.media.NearbyDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.NearbyDevice createFromParcel(android.os.Parcel parcel) {
            return new android.media.NearbyDevice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.NearbyDevice[] newArray(int i) {
            return new android.media.NearbyDevice[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RangeZone {
    }

    public static java.lang.String rangeZoneToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "FAR";
            case 2:
                return "LONG";
            case 3:
                return "CLOSE";
            case 4:
                return "WITHIN_REACH";
            default:
                return "Invalid";
        }
    }

    public NearbyDevice(java.lang.String str, int i) {
        this.mMediaRoute2Id = str;
        this.mRangeZone = i;
    }

    private NearbyDevice(android.os.Parcel parcel) {
        this.mMediaRoute2Id = parcel.readString8();
        this.mRangeZone = parcel.readInt();
    }

    public static int compareRangeZones(int i, int i2) {
        if (i == i2) {
            return 0;
        }
        return RANGE_WEIGHT_LIST.indexOf(java.lang.Integer.valueOf(i)) > RANGE_WEIGHT_LIST.indexOf(java.lang.Integer.valueOf(i2)) ? -1 : 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "NearbyDevice{mediaRoute2Id=" + this.mMediaRoute2Id + " rangeZone=" + rangeZoneToString(this.mRangeZone) + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mMediaRoute2Id);
        parcel.writeInt(this.mRangeZone);
    }

    public java.lang.String getMediaRoute2Id() {
        return this.mMediaRoute2Id;
    }

    public int getRangeZone() {
        return this.mRangeZone;
    }
}
