package android.media.tv;

/* loaded from: classes2.dex */
public final class TvRecordingInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.TvRecordingInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TvRecordingInfo>() { // from class: android.media.tv.TvRecordingInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvRecordingInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.TvRecordingInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TvRecordingInfo[] newArray(int i) {
            return new android.media.tv.TvRecordingInfo[i];
        }
    };
    public static final int FRIDAY = 32;
    public static final int MONDAY = 2;
    public static final int RECORDING_ALL = 3;
    public static final int RECORDING_IN_PROGRESS = 2;
    public static final int RECORDING_SCHEDULED = 1;
    public static final int SATURDAY = 64;
    public static final int SUNDAY = 1;
    public static final int THURSDAY = 16;
    public static final int TUESDAY = 4;
    public static final int WEDNESDAY = 8;
    private android.net.Uri mChannelUri;
    private java.util.List<android.media.tv.TvContentRating> mContentRatings;
    private java.lang.String mDescription;
    private long mEndPaddingMillis;
    private java.lang.String mName;
    private android.net.Uri mProgramUri;
    private long mRecordingDurationMillis;
    private java.lang.String mRecordingId;
    private long mRecordingStartTimeMillis;
    private android.net.Uri mRecordingUri;
    private int mRepeatDays;
    private long mScheduledDurationMillis;
    private long mScheduledStartTimeMillis;
    private long mStartPaddingMillis;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DaysOfWeek {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TvRecordingListType {
    }

    public TvRecordingInfo(java.lang.String str, long j, long j2, int i, java.lang.String str2, java.lang.String str3, long j3, long j4, android.net.Uri uri, android.net.Uri uri2, java.util.List<android.media.tv.TvContentRating> list, android.net.Uri uri3, long j5, long j6) {
        this.mRecordingId = str;
        this.mStartPaddingMillis = j;
        this.mEndPaddingMillis = j2;
        this.mRepeatDays = i;
        this.mName = str2;
        this.mDescription = str3;
        this.mScheduledStartTimeMillis = j3;
        this.mScheduledDurationMillis = j4;
        this.mChannelUri = uri;
        this.mProgramUri = uri2;
        this.mContentRatings = list;
        this.mRecordingUri = uri3;
        this.mRecordingStartTimeMillis = j5;
        this.mRecordingDurationMillis = j6;
    }

    public java.lang.String getRecordingId() {
        return this.mRecordingId;
    }

    public long getStartPaddingMillis() {
        return this.mStartPaddingMillis;
    }

    public long getEndPaddingMillis() {
        return this.mEndPaddingMillis;
    }

    public int getRepeatDays() {
        return this.mRepeatDays;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public void setName(java.lang.String str) {
        this.mName = str;
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public void setDescription(java.lang.String str) {
        this.mDescription = str;
    }

    public long getScheduledStartTimeMillis() {
        return this.mScheduledStartTimeMillis;
    }

    public long getScheduledDurationMillis() {
        return this.mScheduledDurationMillis;
    }

    public android.net.Uri getChannelUri() {
        return this.mChannelUri;
    }

    public android.net.Uri getProgramUri() {
        return this.mProgramUri;
    }

    public java.util.List<android.media.tv.TvContentRating> getContentRatings() {
        return this.mContentRatings;
    }

    public android.net.Uri getRecordingUri() {
        return this.mRecordingUri;
    }

    public long getRecordingStartTimeMillis() {
        return this.mRecordingStartTimeMillis;
    }

    public long getRecordingDurationMillis() {
        return this.mRecordingDurationMillis;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mRecordingId);
        parcel.writeLong(this.mStartPaddingMillis);
        parcel.writeLong(this.mEndPaddingMillis);
        parcel.writeInt(this.mRepeatDays);
        parcel.writeString(this.mName);
        parcel.writeString(this.mDescription);
        parcel.writeLong(this.mScheduledStartTimeMillis);
        parcel.writeLong(this.mScheduledDurationMillis);
        parcel.writeString(this.mChannelUri == null ? null : this.mChannelUri.toString());
        parcel.writeString(this.mProgramUri == null ? null : this.mProgramUri.toString());
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        this.mContentRatings.forEach(new java.util.function.Consumer() { // from class: android.media.tv.TvRecordingInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add(((android.media.tv.TvContentRating) obj).flattenToString());
            }
        });
        parcel.writeList(this.mContentRatings);
        parcel.writeString(this.mRecordingUri != null ? this.mProgramUri.toString() : null);
        parcel.writeLong(this.mRecordingDurationMillis);
        parcel.writeLong(this.mRecordingStartTimeMillis);
    }

    private TvRecordingInfo(android.os.Parcel parcel) {
        this.mRecordingId = parcel.readString();
        this.mStartPaddingMillis = parcel.readLong();
        this.mEndPaddingMillis = parcel.readLong();
        this.mRepeatDays = parcel.readInt();
        this.mName = parcel.readString();
        this.mDescription = parcel.readString();
        this.mScheduledStartTimeMillis = parcel.readLong();
        this.mScheduledDurationMillis = parcel.readLong();
        this.mChannelUri = android.net.Uri.parse(parcel.readString());
        this.mProgramUri = android.net.Uri.parse(parcel.readString());
        this.mContentRatings = new java.util.ArrayList();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        arrayList.forEach(new java.util.function.Consumer() { // from class: android.media.tv.TvRecordingInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.media.tv.TvRecordingInfo.this.lambda$new$1((java.lang.String) obj);
            }
        });
        this.mRecordingUri = android.net.Uri.parse(parcel.readString());
        this.mRecordingDurationMillis = parcel.readLong();
        this.mRecordingStartTimeMillis = parcel.readLong();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(java.lang.String str) {
        this.mContentRatings.add(android.media.tv.TvContentRating.unflattenFromString(str));
    }
}
