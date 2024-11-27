package android.media;

/* loaded from: classes2.dex */
public class MediaSyncEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.MediaSyncEvent> CREATOR = new android.os.Parcelable.Creator<android.media.MediaSyncEvent>() { // from class: android.media.MediaSyncEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MediaSyncEvent createFromParcel(android.os.Parcel parcel) {
            return new android.media.MediaSyncEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MediaSyncEvent[] newArray(int i) {
            return new android.media.MediaSyncEvent[i];
        }
    };
    public static final int SYNC_EVENT_NONE = 0;
    public static final int SYNC_EVENT_PRESENTATION_COMPLETE = 1;

    @android.annotation.SystemApi
    public static final int SYNC_EVENT_SHARE_AUDIO_HISTORY = 100;
    private int mAudioSession;
    private final int mType;

    public static android.media.MediaSyncEvent createEvent(int i) throws java.lang.IllegalArgumentException {
        if (!isValidType(i)) {
            throw new java.lang.IllegalArgumentException(i + "is not a valid MediaSyncEvent type.");
        }
        return new android.media.MediaSyncEvent(i);
    }

    private MediaSyncEvent(int i) {
        this.mAudioSession = 0;
        this.mType = i;
    }

    public android.media.MediaSyncEvent setAudioSessionId(int i) throws java.lang.IllegalArgumentException {
        if (i > 0) {
            this.mAudioSession = i;
            return this;
        }
        throw new java.lang.IllegalArgumentException(i + " is not a valid session ID.");
    }

    public int getType() {
        return this.mType;
    }

    public int getAudioSessionId() {
        return this.mAudioSession;
    }

    private static boolean isValidType(int i) {
        switch (i) {
            case 0:
            case 1:
            case 100:
                return true;
            default:
                return false;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        java.util.Objects.requireNonNull(parcel);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mAudioSession);
    }

    private MediaSyncEvent(android.os.Parcel parcel) {
        this.mAudioSession = 0;
        this.mType = parcel.readInt();
        this.mAudioSession = parcel.readInt();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.MediaSyncEvent mediaSyncEvent = (android.media.MediaSyncEvent) obj;
        if (this.mType == mediaSyncEvent.mType && this.mAudioSession == mediaSyncEvent.mAudioSession) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mType), java.lang.Integer.valueOf(this.mAudioSession));
    }

    public java.lang.String toString() {
        return new java.lang.String("MediaSyncEvent: type=" + typeToString(this.mType) + " session=" + this.mAudioSession);
    }

    public static java.lang.String typeToString(int i) {
        switch (i) {
            case 0:
                return "SYNC_EVENT_NONE";
            case 1:
                return "SYNC_EVENT_PRESENTATION_COMPLETE";
            case 100:
                return "SYNC_EVENT_SHARE_AUDIO_HISTORY";
            default:
                return "unknown event type " + i;
        }
    }
}
