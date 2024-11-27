package android.media;

/* loaded from: classes2.dex */
public final class RemoteDisplayState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.RemoteDisplayState> CREATOR = new android.os.Parcelable.Creator<android.media.RemoteDisplayState>() { // from class: android.media.RemoteDisplayState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.RemoteDisplayState createFromParcel(android.os.Parcel parcel) {
            return new android.media.RemoteDisplayState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.RemoteDisplayState[] newArray(int i) {
            return new android.media.RemoteDisplayState[i];
        }
    };
    public static final int DISCOVERY_MODE_ACTIVE = 2;
    public static final int DISCOVERY_MODE_NONE = 0;
    public static final int DISCOVERY_MODE_PASSIVE = 1;
    public static final java.lang.String SERVICE_INTERFACE = "com.android.media.remotedisplay.RemoteDisplayProvider";
    public final java.util.ArrayList<android.media.RemoteDisplayState.RemoteDisplayInfo> displays;

    public RemoteDisplayState() {
        this.displays = new java.util.ArrayList<>();
    }

    RemoteDisplayState(android.os.Parcel parcel) {
        this.displays = parcel.createTypedArrayList(android.media.RemoteDisplayState.RemoteDisplayInfo.CREATOR);
    }

    public boolean isValid() {
        if (this.displays == null) {
            return false;
        }
        int size = this.displays.size();
        for (int i = 0; i < size; i++) {
            if (!this.displays.get(i).isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.displays);
    }

    public static final class RemoteDisplayInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.RemoteDisplayState.RemoteDisplayInfo> CREATOR = new android.os.Parcelable.Creator<android.media.RemoteDisplayState.RemoteDisplayInfo>() { // from class: android.media.RemoteDisplayState.RemoteDisplayInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.RemoteDisplayState.RemoteDisplayInfo createFromParcel(android.os.Parcel parcel) {
                return new android.media.RemoteDisplayState.RemoteDisplayInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.RemoteDisplayState.RemoteDisplayInfo[] newArray(int i) {
                return new android.media.RemoteDisplayState.RemoteDisplayInfo[i];
            }
        };
        public static final int PLAYBACK_VOLUME_FIXED = 0;
        public static final int PLAYBACK_VOLUME_VARIABLE = 1;
        public static final int STATUS_AVAILABLE = 2;
        public static final int STATUS_CONNECTED = 4;
        public static final int STATUS_CONNECTING = 3;
        public static final int STATUS_IN_USE = 1;
        public static final int STATUS_NOT_AVAILABLE = 0;
        public java.lang.String description;
        public java.lang.String id;
        public java.lang.String name;
        public int presentationDisplayId;
        public int status;
        public int volume;
        public int volumeHandling;
        public int volumeMax;

        public RemoteDisplayInfo(java.lang.String str) {
            this.id = str;
            this.status = 0;
            this.volumeHandling = 0;
            this.presentationDisplayId = -1;
        }

        public RemoteDisplayInfo(android.media.RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
            this.id = remoteDisplayInfo.id;
            this.name = remoteDisplayInfo.name;
            this.description = remoteDisplayInfo.description;
            this.status = remoteDisplayInfo.status;
            this.volume = remoteDisplayInfo.volume;
            this.volumeMax = remoteDisplayInfo.volumeMax;
            this.volumeHandling = remoteDisplayInfo.volumeHandling;
            this.presentationDisplayId = remoteDisplayInfo.presentationDisplayId;
        }

        RemoteDisplayInfo(android.os.Parcel parcel) {
            this.id = parcel.readString();
            this.name = parcel.readString();
            this.description = parcel.readString();
            this.status = parcel.readInt();
            this.volume = parcel.readInt();
            this.volumeMax = parcel.readInt();
            this.volumeHandling = parcel.readInt();
            this.presentationDisplayId = parcel.readInt();
        }

        public boolean isValid() {
            return (android.text.TextUtils.isEmpty(this.id) || android.text.TextUtils.isEmpty(this.name)) ? false : true;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.id);
            parcel.writeString(this.name);
            parcel.writeString(this.description);
            parcel.writeInt(this.status);
            parcel.writeInt(this.volume);
            parcel.writeInt(this.volumeMax);
            parcel.writeInt(this.volumeHandling);
            parcel.writeInt(this.presentationDisplayId);
        }

        public java.lang.String toString() {
            return "RemoteDisplayInfo{ id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", status=" + this.status + ", volume=" + this.volume + ", volumeMax=" + this.volumeMax + ", volumeHandling=" + this.volumeHandling + ", presentationDisplayId=" + this.presentationDisplayId + " }";
        }
    }
}
