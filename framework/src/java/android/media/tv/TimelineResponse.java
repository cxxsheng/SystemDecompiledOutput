package android.media.tv;

/* loaded from: classes2.dex */
public final class TimelineResponse extends android.media.tv.BroadcastInfoResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.TimelineResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TimelineResponse>() { // from class: android.media.tv.TimelineResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TimelineResponse createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.TimelineResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TimelineResponse[] newArray(int i) {
            return new android.media.tv.TimelineResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 8;
    private final java.lang.String mSelector;
    private final long mTicks;
    private final int mUnitsPerSecond;
    private final int mUnitsPerTick;
    private final long mWallClock;

    static android.media.tv.TimelineResponse createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.TimelineResponse(parcel);
    }

    public TimelineResponse(int i, int i2, int i3, java.lang.String str, int i4, int i5, long j, long j2) {
        super(8, i, i2, i3);
        this.mSelector = str;
        this.mUnitsPerTick = i4;
        this.mUnitsPerSecond = i5;
        this.mWallClock = j;
        this.mTicks = j2;
    }

    TimelineResponse(android.os.Parcel parcel) {
        super(8, parcel);
        this.mSelector = parcel.readString();
        this.mUnitsPerTick = parcel.readInt();
        this.mUnitsPerSecond = parcel.readInt();
        this.mWallClock = parcel.readLong();
        this.mTicks = parcel.readLong();
    }

    public android.net.Uri getSelector() {
        return android.net.Uri.parse(this.mSelector);
    }

    public int getUnitsPerTick() {
        return this.mUnitsPerTick;
    }

    public int getUnitsPerSecond() {
        return this.mUnitsPerSecond;
    }

    public long getWallClock() {
        return this.mWallClock;
    }

    public long getTicks() {
        return this.mTicks;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mSelector);
        parcel.writeInt(this.mUnitsPerTick);
        parcel.writeInt(this.mUnitsPerSecond);
        parcel.writeLong(this.mWallClock);
        parcel.writeLong(this.mTicks);
    }
}
