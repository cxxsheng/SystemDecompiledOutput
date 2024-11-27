package android.media;

/* loaded from: classes2.dex */
public final class AudioTimestamp implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioTimestamp> CREATOR = new android.os.Parcelable.Creator<android.media.AudioTimestamp>() { // from class: android.media.AudioTimestamp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioTimestamp createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioTimestamp(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioTimestamp[] newArray(int i) {
            return new android.media.AudioTimestamp[i];
        }
    };
    public static final int TIMEBASE_BOOTTIME = 1;
    public static final int TIMEBASE_MONOTONIC = 0;
    public long framePosition;
    public long nanoTime;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Timebase {
    }

    public AudioTimestamp() {
    }

    private AudioTimestamp(android.os.Parcel parcel) {
        this.framePosition = parcel.readLong();
        this.nanoTime = parcel.readLong();
    }

    public java.lang.String toString() {
        return "AudioTimeStamp: framePos=" + this.framePosition + " nanoTime=" + this.nanoTime;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.framePosition);
        parcel.writeLong(this.nanoTime);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
