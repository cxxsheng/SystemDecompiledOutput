package android.media;

/* loaded from: classes2.dex */
public final class MediaRouterClientState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.MediaRouterClientState> CREATOR = new android.os.Parcelable.Creator<android.media.MediaRouterClientState>() { // from class: android.media.MediaRouterClientState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MediaRouterClientState createFromParcel(android.os.Parcel parcel) {
            return new android.media.MediaRouterClientState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.MediaRouterClientState[] newArray(int i) {
            return new android.media.MediaRouterClientState[i];
        }
    };
    public final java.util.ArrayList<android.media.MediaRouterClientState.RouteInfo> routes;

    public MediaRouterClientState() {
        this.routes = new java.util.ArrayList<>();
    }

    MediaRouterClientState(android.os.Parcel parcel) {
        this.routes = parcel.createTypedArrayList(android.media.MediaRouterClientState.RouteInfo.CREATOR);
    }

    public android.media.MediaRouterClientState.RouteInfo getRoute(java.lang.String str) {
        int size = this.routes.size();
        for (int i = 0; i < size; i++) {
            android.media.MediaRouterClientState.RouteInfo routeInfo = this.routes.get(i);
            if (routeInfo.id.equals(str)) {
                return routeInfo;
            }
        }
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.routes);
    }

    public java.lang.String toString() {
        return "MediaRouterClientState{ routes=" + this.routes.toString() + " }";
    }

    public static final class RouteInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.MediaRouterClientState.RouteInfo> CREATOR = new android.os.Parcelable.Creator<android.media.MediaRouterClientState.RouteInfo>() { // from class: android.media.MediaRouterClientState.RouteInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.MediaRouterClientState.RouteInfo createFromParcel(android.os.Parcel parcel) {
                return new android.media.MediaRouterClientState.RouteInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.MediaRouterClientState.RouteInfo[] newArray(int i) {
                return new android.media.MediaRouterClientState.RouteInfo[i];
            }
        };
        public java.lang.String description;
        public int deviceType;
        public boolean enabled;
        public java.lang.String id;
        public java.lang.String name;
        public int playbackStream;
        public int playbackType;
        public int presentationDisplayId;
        public int statusCode;
        public int supportedTypes;
        public int volume;
        public int volumeHandling;
        public int volumeMax;

        public RouteInfo(java.lang.String str) {
            this.id = str;
            this.enabled = true;
            this.statusCode = 0;
            this.playbackType = 1;
            this.playbackStream = -1;
            this.volumeHandling = 0;
            this.presentationDisplayId = -1;
            this.deviceType = 0;
        }

        public RouteInfo(android.media.MediaRouterClientState.RouteInfo routeInfo) {
            this.id = routeInfo.id;
            this.name = routeInfo.name;
            this.description = routeInfo.description;
            this.supportedTypes = routeInfo.supportedTypes;
            this.enabled = routeInfo.enabled;
            this.statusCode = routeInfo.statusCode;
            this.playbackType = routeInfo.playbackType;
            this.playbackStream = routeInfo.playbackStream;
            this.volume = routeInfo.volume;
            this.volumeMax = routeInfo.volumeMax;
            this.volumeHandling = routeInfo.volumeHandling;
            this.presentationDisplayId = routeInfo.presentationDisplayId;
            this.deviceType = routeInfo.deviceType;
        }

        RouteInfo(android.os.Parcel parcel) {
            this.id = parcel.readString();
            this.name = parcel.readString();
            this.description = parcel.readString();
            this.supportedTypes = parcel.readInt();
            this.enabled = parcel.readInt() != 0;
            this.statusCode = parcel.readInt();
            this.playbackType = parcel.readInt();
            this.playbackStream = parcel.readInt();
            this.volume = parcel.readInt();
            this.volumeMax = parcel.readInt();
            this.volumeHandling = parcel.readInt();
            this.presentationDisplayId = parcel.readInt();
            this.deviceType = parcel.readInt();
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
            parcel.writeInt(this.supportedTypes);
            parcel.writeInt(this.enabled ? 1 : 0);
            parcel.writeInt(this.statusCode);
            parcel.writeInt(this.playbackType);
            parcel.writeInt(this.playbackStream);
            parcel.writeInt(this.volume);
            parcel.writeInt(this.volumeMax);
            parcel.writeInt(this.volumeHandling);
            parcel.writeInt(this.presentationDisplayId);
            parcel.writeInt(this.deviceType);
        }

        public java.lang.String toString() {
            return "RouteInfo{ id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", supportedTypes=0x" + java.lang.Integer.toHexString(this.supportedTypes) + ", enabled=" + this.enabled + ", statusCode=" + this.statusCode + ", playbackType=" + this.playbackType + ", playbackStream=" + this.playbackStream + ", volume=" + this.volume + ", volumeMax=" + this.volumeMax + ", volumeHandling=" + this.volumeHandling + ", presentationDisplayId=" + this.presentationDisplayId + ", deviceType=" + this.deviceType + " }";
        }
    }
}
