package android.media.tv;

/* loaded from: classes2.dex */
public final class TimelineRequest extends android.media.tv.BroadcastInfoRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.TimelineRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TimelineRequest>() { // from class: android.media.tv.TimelineRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TimelineRequest createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.TimelineRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TimelineRequest[] newArray(int i) {
            return new android.media.tv.TimelineRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 8;
    private final int mIntervalMillis;
    private final java.lang.String mSelector;

    static android.media.tv.TimelineRequest createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.TimelineRequest(parcel);
    }

    public TimelineRequest(int i, int i2, int i3) {
        super(8, i, i2);
        this.mIntervalMillis = i3;
        this.mSelector = null;
    }

    public TimelineRequest(int i, int i2, int i3, java.lang.String str) {
        super(8, i, i2);
        this.mIntervalMillis = i3;
        this.mSelector = str;
    }

    TimelineRequest(android.os.Parcel parcel) {
        super(8, parcel);
        this.mIntervalMillis = parcel.readInt();
        this.mSelector = parcel.readString();
    }

    public int getIntervalMillis() {
        return this.mIntervalMillis;
    }

    public java.lang.String getSelector() {
        return this.mSelector;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mIntervalMillis);
        parcel.writeString(this.mSelector);
    }
}
