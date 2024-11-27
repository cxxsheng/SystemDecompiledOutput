package android.media.tv;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class TunedInfo implements android.os.Parcelable {
    public static final int APP_TAG_SELF = 0;
    public static final int APP_TYPE_NON_SYSTEM = 3;
    public static final int APP_TYPE_SELF = 1;
    public static final int APP_TYPE_SYSTEM = 2;
    public static final android.os.Parcelable.Creator<android.media.tv.TunedInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TunedInfo>() { // from class: android.media.tv.TunedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TunedInfo createFromParcel(android.os.Parcel parcel) {
            try {
                return new android.media.tv.TunedInfo(parcel);
            } catch (java.lang.Exception e) {
                android.util.Log.e(android.media.tv.TunedInfo.TAG, "Exception creating TunedInfo from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TunedInfo[] newArray(int i) {
            return new android.media.tv.TunedInfo[i];
        }
    };
    static final java.lang.String TAG = "TunedInfo";
    private final int mAppTag;
    private final int mAppType;
    private final android.net.Uri mChannelUri;
    private final java.lang.String mInputId;
    private final boolean mIsMainSession;
    private final boolean mIsRecordingSession;
    private final boolean mIsVisible;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AppType {
    }

    public TunedInfo(java.lang.String str, android.net.Uri uri, boolean z, boolean z2, boolean z3, int i, int i2) {
        this.mInputId = str;
        this.mChannelUri = uri;
        this.mIsRecordingSession = z;
        this.mIsVisible = z2;
        this.mIsMainSession = z3;
        this.mAppType = i;
        this.mAppTag = i2;
    }

    private TunedInfo(android.os.Parcel parcel) {
        this.mInputId = parcel.readString();
        java.lang.String readString = parcel.readString();
        this.mChannelUri = readString == null ? null : android.net.Uri.parse(readString);
        this.mIsRecordingSession = parcel.readInt() == 1;
        this.mIsVisible = parcel.readInt() == 1;
        this.mIsMainSession = parcel.readInt() == 1;
        this.mAppType = parcel.readInt();
        this.mAppTag = parcel.readInt();
    }

    public java.lang.String getInputId() {
        return this.mInputId;
    }

    public android.net.Uri getChannelUri() {
        return this.mChannelUri;
    }

    public boolean isRecordingSession() {
        return this.mIsRecordingSession;
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    public boolean isMainSession() {
        return this.mIsMainSession;
    }

    public int getAppTag() {
        return this.mAppTag;
    }

    public int getAppType() {
        return this.mAppType;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mInputId);
        parcel.writeString(this.mChannelUri == null ? null : this.mChannelUri.toString());
        parcel.writeInt(this.mIsRecordingSession ? 1 : 0);
        parcel.writeInt(this.mIsVisible ? 1 : 0);
        parcel.writeInt(this.mIsMainSession ? 1 : 0);
        parcel.writeInt(this.mAppType);
        parcel.writeInt(this.mAppTag);
    }

    public java.lang.String toString() {
        return "inputID=" + this.mInputId + ";channelUri=" + this.mChannelUri + ";isRecording=" + this.mIsRecordingSession + ";isVisible=" + this.mIsVisible + ";isMainSession=" + this.mIsMainSession + ";appType=" + this.mAppType + ";appTag=" + this.mAppTag;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.media.tv.TunedInfo)) {
            return false;
        }
        android.media.tv.TunedInfo tunedInfo = (android.media.tv.TunedInfo) obj;
        return android.text.TextUtils.equals(this.mInputId, tunedInfo.getInputId()) && java.util.Objects.equals(this.mChannelUri, tunedInfo.mChannelUri) && this.mIsRecordingSession == tunedInfo.mIsRecordingSession && this.mIsVisible == tunedInfo.mIsVisible && this.mIsMainSession == tunedInfo.mIsMainSession && this.mAppType == tunedInfo.mAppType && this.mAppTag == tunedInfo.mAppTag;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mInputId, this.mChannelUri, java.lang.Boolean.valueOf(this.mIsRecordingSession), java.lang.Boolean.valueOf(this.mIsVisible), java.lang.Boolean.valueOf(this.mIsMainSession), java.lang.Integer.valueOf(this.mAppType), java.lang.Integer.valueOf(this.mAppTag));
    }
}
