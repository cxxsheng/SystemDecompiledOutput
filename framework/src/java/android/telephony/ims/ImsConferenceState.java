package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ImsConferenceState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.ImsConferenceState> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.ImsConferenceState>() { // from class: android.telephony.ims.ImsConferenceState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsConferenceState createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.ImsConferenceState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsConferenceState[] newArray(int i) {
            return new android.telephony.ims.ImsConferenceState[i];
        }
    };
    public static final java.lang.String DISPLAY_TEXT = "display-text";
    public static final java.lang.String ENDPOINT = "endpoint";
    public static final java.lang.String SIP_STATUS_CODE = "sipstatuscode";
    public static final java.lang.String STATUS = "status";
    public static final java.lang.String STATUS_ALERTING = "alerting";
    public static final java.lang.String STATUS_CONNECTED = "connected";
    public static final java.lang.String STATUS_CONNECT_FAIL = "connect-fail";
    public static final java.lang.String STATUS_DIALING_IN = "dialing-in";
    public static final java.lang.String STATUS_DIALING_OUT = "dialing-out";
    public static final java.lang.String STATUS_DISCONNECTED = "disconnected";
    public static final java.lang.String STATUS_DISCONNECTING = "disconnecting";
    public static final java.lang.String STATUS_MUTED_VIA_FOCUS = "muted-via-focus";
    public static final java.lang.String STATUS_ON_HOLD = "on-hold";
    public static final java.lang.String STATUS_PENDING = "pending";
    public static final java.lang.String STATUS_SEND_ONLY = "sendonly";
    public static final java.lang.String STATUS_SEND_RECV = "sendrecv";
    private static final java.lang.String TAG = "ImsConferenceState";
    public static final java.lang.String USER = "user";
    public final java.util.HashMap<java.lang.String, android.os.Bundle> mParticipants;

    public ImsConferenceState() {
        this.mParticipants = new java.util.HashMap<>();
    }

    private ImsConferenceState(android.os.Parcel parcel) {
        this.mParticipants = new java.util.HashMap<>();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        java.util.Set<java.util.Map.Entry<java.lang.String, android.os.Bundle>> entrySet;
        parcel.writeInt(this.mParticipants.size());
        if (this.mParticipants.size() > 0 && (entrySet = this.mParticipants.entrySet()) != null) {
            for (java.util.Map.Entry<java.lang.String, android.os.Bundle> entry : entrySet) {
                parcel.writeString(entry.getKey());
                parcel.writeParcelable(entry.getValue(), 0);
            }
        }
    }

    private void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mParticipants.put(parcel.readString(), (android.os.Bundle) parcel.readParcelable(null, android.os.Bundle.class));
        }
    }

    public static int getConnectionStateForStatus(java.lang.String str) {
        if (str.equals(STATUS_PENDING)) {
            return 0;
        }
        if (str.equals(STATUS_DIALING_IN)) {
            return 2;
        }
        if (str.equals(STATUS_ALERTING) || str.equals(STATUS_DIALING_OUT)) {
            return 3;
        }
        if (str.equals(STATUS_ON_HOLD) || str.equals(STATUS_SEND_ONLY)) {
            return 5;
        }
        return (str.equals("connected") || str.equals(STATUS_MUTED_VIA_FOCUS) || str.equals(STATUS_DISCONNECTING) || str.equals(STATUS_SEND_RECV) || !str.equals("disconnected")) ? 4 : 6;
    }

    public java.lang.String toString() {
        java.util.Set<java.util.Map.Entry<java.lang.String, android.os.Bundle>> entrySet;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        sb.append(android.telephony.ims.ImsConferenceState.class.getSimpleName());
        sb.append(" ");
        if (this.mParticipants.size() > 0 && (entrySet = this.mParticipants.entrySet()) != null) {
            sb.append("<");
            for (java.util.Map.Entry<java.lang.String, android.os.Bundle> entry : entrySet) {
                sb.append(com.android.telephony.Rlog.pii(TAG, entry.getKey()));
                sb.append(": ");
                android.os.Bundle value = entry.getValue();
                for (java.lang.String str : value.keySet()) {
                    sb.append(str);
                    sb.append("=");
                    if ("status".equals(str)) {
                        sb.append(value.get(str));
                    } else {
                        sb.append(com.android.telephony.Rlog.pii(TAG, value.get(str)));
                    }
                    sb.append(", ");
                }
            }
            sb.append(">");
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
