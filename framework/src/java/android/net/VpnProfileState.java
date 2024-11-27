package android.net;

/* loaded from: classes2.dex */
public final class VpnProfileState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.VpnProfileState> CREATOR = new android.os.Parcelable.Creator<android.net.VpnProfileState>() { // from class: android.net.VpnProfileState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.VpnProfileState createFromParcel(android.os.Parcel parcel) {
            return new android.net.VpnProfileState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.VpnProfileState[] newArray(int i) {
            return new android.net.VpnProfileState[i];
        }
    };
    public static final int STATE_CONNECTED = 2;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_FAILED = 3;
    private final boolean mAlwaysOn;
    private final boolean mLockdown;
    private final java.lang.String mSessionKey;
    private final int mState;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    public VpnProfileState(int i, java.lang.String str, boolean z, boolean z2) {
        this.mState = i;
        this.mSessionKey = str;
        this.mAlwaysOn = z;
        this.mLockdown = z2;
    }

    public int getState() {
        return this.mState;
    }

    public java.lang.String getSessionId() {
        return this.mSessionKey;
    }

    public boolean isAlwaysOn() {
        return this.mAlwaysOn;
    }

    public boolean isLockdownEnabled() {
        return this.mLockdown;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mState);
        parcel.writeString(this.mSessionKey);
        parcel.writeBoolean(this.mAlwaysOn);
        parcel.writeBoolean(this.mLockdown);
    }

    private VpnProfileState(android.os.Parcel parcel) {
        this.mState = parcel.readInt();
        this.mSessionKey = parcel.readString();
        this.mAlwaysOn = parcel.readBoolean();
        this.mLockdown = parcel.readBoolean();
    }

    private java.lang.String convertStateToString(int i) {
        switch (i) {
            case 0:
                return "DISCONNECTED";
            case 1:
                return "CONNECTING";
            case 2:
                return "CONNECTED";
            case 3:
                return "FAILED";
            default:
                return "UNKNOWN";
        }
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("State: " + convertStateToString(getState()));
        stringJoiner.add("SessionId: " + getSessionId());
        stringJoiner.add("Always-on: " + isAlwaysOn());
        stringJoiner.add("Lockdown: " + isLockdownEnabled());
        return stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.VpnProfileState)) {
            return false;
        }
        android.net.VpnProfileState vpnProfileState = (android.net.VpnProfileState) obj;
        return getState() == vpnProfileState.getState() && java.util.Objects.equals(getSessionId(), vpnProfileState.getSessionId()) && isAlwaysOn() == vpnProfileState.isAlwaysOn() && isLockdownEnabled() == vpnProfileState.isLockdownEnabled();
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(getState()), getSessionId(), java.lang.Boolean.valueOf(isAlwaysOn()), java.lang.Boolean.valueOf(isLockdownEnabled()));
    }
}
