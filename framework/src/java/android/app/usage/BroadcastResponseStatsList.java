package android.app.usage;

/* loaded from: classes.dex */
public final class BroadcastResponseStatsList implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.usage.BroadcastResponseStatsList> CREATOR = new android.os.Parcelable.Creator<android.app.usage.BroadcastResponseStatsList>() { // from class: android.app.usage.BroadcastResponseStatsList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.BroadcastResponseStatsList createFromParcel(android.os.Parcel parcel) {
            return new android.app.usage.BroadcastResponseStatsList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.BroadcastResponseStatsList[] newArray(int i) {
            return new android.app.usage.BroadcastResponseStatsList[i];
        }
    };
    private java.util.List<android.app.usage.BroadcastResponseStats> mBroadcastResponseStats;

    public BroadcastResponseStatsList(java.util.List<android.app.usage.BroadcastResponseStats> list) {
        this.mBroadcastResponseStats = list;
    }

    private BroadcastResponseStatsList(android.os.Parcel parcel) {
        this.mBroadcastResponseStats = new java.util.ArrayList();
        byte[] readBlob = parcel.readBlob();
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            obtain.unmarshall(readBlob, 0, readBlob.length);
            obtain.setDataPosition(0);
            obtain.readTypedList(this.mBroadcastResponseStats, android.app.usage.BroadcastResponseStats.CREATOR);
        } finally {
            obtain.recycle();
        }
    }

    public java.util.List<android.app.usage.BroadcastResponseStats> getList() {
        return this.mBroadcastResponseStats == null ? java.util.Collections.emptyList() : this.mBroadcastResponseStats;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            obtain.writeTypedList(this.mBroadcastResponseStats);
            parcel.writeBlob(obtain.marshall());
        } finally {
            obtain.recycle();
        }
    }
}
