package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class PreciseCallState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.PreciseCallState> CREATOR = new android.os.Parcelable.Creator<android.telephony.PreciseCallState>() { // from class: android.telephony.PreciseCallState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.PreciseCallState createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.PreciseCallState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.PreciseCallState[] newArray(int i) {
            return new android.telephony.PreciseCallState[i];
        }
    };
    public static final int PRECISE_CALL_STATE_ACTIVE = 1;
    public static final int PRECISE_CALL_STATE_ALERTING = 4;
    public static final int PRECISE_CALL_STATE_DIALING = 3;
    public static final int PRECISE_CALL_STATE_DISCONNECTED = 7;
    public static final int PRECISE_CALL_STATE_DISCONNECTING = 8;
    public static final int PRECISE_CALL_STATE_HOLDING = 2;
    public static final int PRECISE_CALL_STATE_IDLE = 0;
    public static final int PRECISE_CALL_STATE_INCOMING = 5;
    public static final int PRECISE_CALL_STATE_INCOMING_SETUP = 9;
    public static final int PRECISE_CALL_STATE_NOT_VALID = -1;
    public static final int PRECISE_CALL_STATE_WAITING = 6;
    private int mBackgroundCallState;
    private int mDisconnectCause;
    private int mForegroundCallState;
    private int mPreciseDisconnectCause;
    private int mRingingCallState;

    @android.annotation.SystemApi
    public PreciseCallState(int i, int i2, int i3, int i4, int i5) {
        this.mRingingCallState = -1;
        this.mForegroundCallState = -1;
        this.mBackgroundCallState = -1;
        this.mDisconnectCause = -1;
        this.mPreciseDisconnectCause = -1;
        this.mRingingCallState = i;
        this.mForegroundCallState = i2;
        this.mBackgroundCallState = i3;
        this.mDisconnectCause = i4;
        this.mPreciseDisconnectCause = i5;
    }

    public PreciseCallState() {
        this.mRingingCallState = -1;
        this.mForegroundCallState = -1;
        this.mBackgroundCallState = -1;
        this.mDisconnectCause = -1;
        this.mPreciseDisconnectCause = -1;
    }

    private PreciseCallState(android.os.Parcel parcel) {
        this.mRingingCallState = -1;
        this.mForegroundCallState = -1;
        this.mBackgroundCallState = -1;
        this.mDisconnectCause = -1;
        this.mPreciseDisconnectCause = -1;
        this.mRingingCallState = parcel.readInt();
        this.mForegroundCallState = parcel.readInt();
        this.mBackgroundCallState = parcel.readInt();
        this.mDisconnectCause = parcel.readInt();
        this.mPreciseDisconnectCause = parcel.readInt();
    }

    public int getRingingCallState() {
        return this.mRingingCallState;
    }

    public int getForegroundCallState() {
        return this.mForegroundCallState;
    }

    public int getBackgroundCallState() {
        return this.mBackgroundCallState;
    }

    public int getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public int getPreciseDisconnectCause() {
        return this.mPreciseDisconnectCause;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRingingCallState);
        parcel.writeInt(this.mForegroundCallState);
        parcel.writeInt(this.mBackgroundCallState);
        parcel.writeInt(this.mDisconnectCause);
        parcel.writeInt(this.mPreciseDisconnectCause);
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRingingCallState), java.lang.Integer.valueOf(this.mForegroundCallState), java.lang.Integer.valueOf(this.mForegroundCallState), java.lang.Integer.valueOf(this.mDisconnectCause), java.lang.Integer.valueOf(this.mPreciseDisconnectCause));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.PreciseCallState preciseCallState = (android.telephony.PreciseCallState) obj;
        if (this.mRingingCallState == preciseCallState.mRingingCallState && this.mForegroundCallState == preciseCallState.mForegroundCallState && this.mBackgroundCallState == preciseCallState.mBackgroundCallState && this.mDisconnectCause == preciseCallState.mDisconnectCause && this.mPreciseDisconnectCause == preciseCallState.mPreciseDisconnectCause) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("Ringing call state: " + this.mRingingCallState);
        stringBuffer.append(", Foreground call state: " + this.mForegroundCallState);
        stringBuffer.append(", Background call state: " + this.mBackgroundCallState);
        stringBuffer.append(", Disconnect cause: " + this.mDisconnectCause);
        stringBuffer.append(", Precise disconnect cause: " + this.mPreciseDisconnectCause);
        return stringBuffer.toString();
    }
}
