package android.telecom;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes3.dex */
public class AudioState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.AudioState> CREATOR = new android.os.Parcelable.Creator<android.telecom.AudioState>() { // from class: android.telecom.AudioState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.AudioState createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.AudioState(parcel.readByte() != 0, parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.AudioState[] newArray(int i) {
            return new android.telecom.AudioState[i];
        }
    };
    private static final int ROUTE_ALL = 15;
    public static final int ROUTE_BLUETOOTH = 2;
    public static final int ROUTE_EARPIECE = 1;
    public static final int ROUTE_SPEAKER = 8;
    public static final int ROUTE_WIRED_HEADSET = 4;
    public static final int ROUTE_WIRED_OR_EARPIECE = 5;
    private final boolean isMuted;
    private final int route;
    private final int supportedRouteMask;

    public AudioState(boolean z, int i, int i2) {
        this.isMuted = z;
        this.route = i;
        this.supportedRouteMask = i2;
    }

    public AudioState(android.telecom.AudioState audioState) {
        this.isMuted = audioState.isMuted();
        this.route = audioState.getRoute();
        this.supportedRouteMask = audioState.getSupportedRouteMask();
    }

    public AudioState(android.telecom.CallAudioState callAudioState) {
        this.isMuted = callAudioState.isMuted();
        this.route = callAudioState.getRoute();
        this.supportedRouteMask = callAudioState.getSupportedRouteMask();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telecom.AudioState)) {
            return false;
        }
        android.telecom.AudioState audioState = (android.telecom.AudioState) obj;
        return isMuted() == audioState.isMuted() && getRoute() == audioState.getRoute() && getSupportedRouteMask() == audioState.getSupportedRouteMask();
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "[AudioState isMuted: %b, route: %s, supportedRouteMask: %s]", java.lang.Boolean.valueOf(this.isMuted), audioRouteToString(this.route), audioRouteToString(this.supportedRouteMask));
    }

    public static java.lang.String audioRouteToString(int i) {
        if (i == 0 || (i & (-16)) != 0) {
            return "UNKNOWN";
        }
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        if ((i & 1) == 1) {
            listAppend(stringBuffer, "EARPIECE");
        }
        if ((i & 2) == 2) {
            listAppend(stringBuffer, "BLUETOOTH");
        }
        if ((i & 4) == 4) {
            listAppend(stringBuffer, "WIRED_HEADSET");
        }
        if ((i & 8) == 8) {
            listAppend(stringBuffer, "SPEAKER");
        }
        return stringBuffer.toString();
    }

    private static void listAppend(java.lang.StringBuffer stringBuffer, java.lang.String str) {
        if (stringBuffer.length() > 0) {
            stringBuffer.append(", ");
        }
        stringBuffer.append(str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.isMuted ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.route);
        parcel.writeInt(this.supportedRouteMask);
    }

    public boolean isMuted() {
        return this.isMuted;
    }

    public int getRoute() {
        return this.route;
    }

    public int getSupportedRouteMask() {
        return this.supportedRouteMask;
    }
}
