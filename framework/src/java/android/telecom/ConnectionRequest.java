package android.telecom;

/* loaded from: classes3.dex */
public final class ConnectionRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.ConnectionRequest> CREATOR = new android.os.Parcelable.Creator<android.telecom.ConnectionRequest>() { // from class: android.telecom.ConnectionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.ConnectionRequest createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.ConnectionRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.ConnectionRequest[] newArray(int i) {
            return new android.telecom.ConnectionRequest[i];
        }
    };
    private final android.telecom.PhoneAccountHandle mAccountHandle;
    private final android.net.Uri mAddress;
    private final android.os.Bundle mExtras;
    private final boolean mIsAdhocConference;
    private java.util.List<android.net.Uri> mParticipants;
    private final android.os.ParcelFileDescriptor mRttPipeFromInCall;
    private final android.os.ParcelFileDescriptor mRttPipeToInCall;
    private android.telecom.Connection.RttTextStream mRttTextStream;
    private final boolean mShouldShowIncomingCallUi;
    private final java.lang.String mTelecomCallId;
    private final int mVideoState;

    public static final class Builder {
        private android.telecom.PhoneAccountHandle mAccountHandle;
        private android.net.Uri mAddress;
        private android.os.Bundle mExtras;
        private java.util.List<android.net.Uri> mParticipants;
        private android.os.ParcelFileDescriptor mRttPipeFromInCall;
        private android.os.ParcelFileDescriptor mRttPipeToInCall;
        private java.lang.String mTelecomCallId;
        private int mVideoState = 0;
        private boolean mShouldShowIncomingCallUi = false;
        private boolean mIsAdhocConference = false;

        public android.telecom.ConnectionRequest.Builder setAccountHandle(android.telecom.PhoneAccountHandle phoneAccountHandle) {
            this.mAccountHandle = phoneAccountHandle;
            return this;
        }

        public android.telecom.ConnectionRequest.Builder setParticipants(java.util.List<android.net.Uri> list) {
            this.mParticipants = list;
            return this;
        }

        public android.telecom.ConnectionRequest.Builder setAddress(android.net.Uri uri) {
            this.mAddress = uri;
            return this;
        }

        public android.telecom.ConnectionRequest.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.telecom.ConnectionRequest.Builder setVideoState(int i) {
            this.mVideoState = i;
            return this;
        }

        public android.telecom.ConnectionRequest.Builder setTelecomCallId(java.lang.String str) {
            this.mTelecomCallId = str;
            return this;
        }

        public android.telecom.ConnectionRequest.Builder setShouldShowIncomingCallUi(boolean z) {
            this.mShouldShowIncomingCallUi = z;
            return this;
        }

        public android.telecom.ConnectionRequest.Builder setIsAdhocConferenceCall(boolean z) {
            this.mIsAdhocConference = z;
            return this;
        }

        public android.telecom.ConnectionRequest.Builder setRttPipeFromInCall(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            this.mRttPipeFromInCall = parcelFileDescriptor;
            return this;
        }

        public android.telecom.ConnectionRequest.Builder setRttPipeToInCall(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            this.mRttPipeToInCall = parcelFileDescriptor;
            return this;
        }

        public android.telecom.ConnectionRequest build() {
            return new android.telecom.ConnectionRequest(this.mAccountHandle, this.mAddress, this.mExtras, this.mVideoState, this.mTelecomCallId, this.mShouldShowIncomingCallUi, this.mRttPipeFromInCall, this.mRttPipeToInCall, this.mParticipants, this.mIsAdhocConference);
        }
    }

    public ConnectionRequest(android.telecom.PhoneAccountHandle phoneAccountHandle, android.net.Uri uri, android.os.Bundle bundle) {
        this(phoneAccountHandle, uri, bundle, 0, null, false, null, null);
    }

    public ConnectionRequest(android.telecom.PhoneAccountHandle phoneAccountHandle, android.net.Uri uri, android.os.Bundle bundle, int i) {
        this(phoneAccountHandle, uri, bundle, i, null, false, null, null);
    }

    public ConnectionRequest(android.telecom.PhoneAccountHandle phoneAccountHandle, android.net.Uri uri, android.os.Bundle bundle, int i, java.lang.String str, boolean z) {
        this(phoneAccountHandle, uri, bundle, i, str, z, null, null);
    }

    private ConnectionRequest(android.telecom.PhoneAccountHandle phoneAccountHandle, android.net.Uri uri, android.os.Bundle bundle, int i, java.lang.String str, boolean z, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
        this(phoneAccountHandle, uri, bundle, i, str, z, parcelFileDescriptor, parcelFileDescriptor2, null, false);
    }

    private ConnectionRequest(android.telecom.PhoneAccountHandle phoneAccountHandle, android.net.Uri uri, android.os.Bundle bundle, int i, java.lang.String str, boolean z, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, java.util.List<android.net.Uri> list, boolean z2) {
        this.mAccountHandle = phoneAccountHandle;
        this.mAddress = uri;
        this.mExtras = bundle;
        this.mVideoState = i;
        this.mTelecomCallId = str;
        this.mShouldShowIncomingCallUi = z;
        this.mRttPipeFromInCall = parcelFileDescriptor;
        this.mRttPipeToInCall = parcelFileDescriptor2;
        this.mParticipants = list;
        this.mIsAdhocConference = z2;
    }

    private ConnectionRequest(android.os.Parcel parcel) {
        this.mAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readParcelable(getClass().getClassLoader(), android.telecom.PhoneAccountHandle.class);
        this.mAddress = (android.net.Uri) parcel.readParcelable(getClass().getClassLoader(), android.net.Uri.class);
        this.mExtras = (android.os.Bundle) parcel.readParcelable(getClass().getClassLoader(), android.os.Bundle.class);
        this.mVideoState = parcel.readInt();
        this.mTelecomCallId = parcel.readString();
        this.mShouldShowIncomingCallUi = parcel.readInt() == 1;
        this.mRttPipeFromInCall = (android.os.ParcelFileDescriptor) parcel.readParcelable(getClass().getClassLoader(), android.os.ParcelFileDescriptor.class);
        this.mRttPipeToInCall = (android.os.ParcelFileDescriptor) parcel.readParcelable(getClass().getClassLoader(), android.os.ParcelFileDescriptor.class);
        this.mParticipants = new java.util.ArrayList();
        parcel.readList(this.mParticipants, getClass().getClassLoader(), android.net.Uri.class);
        this.mIsAdhocConference = parcel.readInt() == 1;
    }

    public android.telecom.PhoneAccountHandle getAccountHandle() {
        return this.mAccountHandle;
    }

    public android.net.Uri getAddress() {
        return this.mAddress;
    }

    public java.util.List<android.net.Uri> getParticipants() {
        return this.mParticipants;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    @android.annotation.SystemApi
    public java.lang.String getTelecomCallId() {
        return this.mTelecomCallId;
    }

    public boolean shouldShowIncomingCallUi() {
        return this.mShouldShowIncomingCallUi;
    }

    public boolean isAdhocConferenceCall() {
        return this.mIsAdhocConference;
    }

    public android.os.ParcelFileDescriptor getRttPipeToInCall() {
        return this.mRttPipeToInCall;
    }

    public android.os.ParcelFileDescriptor getRttPipeFromInCall() {
        return this.mRttPipeFromInCall;
    }

    public android.telecom.Connection.RttTextStream getRttTextStream() {
        if (isRequestingRtt()) {
            if (this.mRttTextStream == null) {
                this.mRttTextStream = new android.telecom.Connection.RttTextStream(this.mRttPipeToInCall, this.mRttPipeFromInCall);
            }
            return this.mRttTextStream;
        }
        return null;
    }

    public boolean isRequestingRtt() {
        return (this.mRttPipeFromInCall == null || this.mRttPipeToInCall == null) ? false : true;
    }

    public java.lang.String toString() {
        java.lang.Object logSafePhoneNumber;
        if (this.mAddress == null) {
            logSafePhoneNumber = android.net.Uri.EMPTY;
        } else {
            logSafePhoneNumber = android.telecom.Connection.toLogSafePhoneNumber(this.mAddress.toString());
        }
        return java.lang.String.format("ConnectionRequest %s %s isAdhocConf: %s", logSafePhoneNumber, bundleToString(this.mExtras), isAdhocConferenceCall() ? android.hardware.gnss.GnssSignalType.CODE_TYPE_Y : android.hardware.gnss.GnssSignalType.CODE_TYPE_N);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static java.lang.String bundleToString(android.os.Bundle bundle) {
        char c;
        if (bundle == null) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Bundle[");
        for (java.lang.String str : bundle.keySet()) {
            sb.append(str);
            sb.append("=");
            switch (str.hashCode()) {
                case -1582002592:
                    if (str.equals(android.telecom.TelecomManager.EXTRA_UNKNOWN_CALL_HANDLE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1513984200:
                    if (str.equals(android.telecom.TelecomManager.EXTRA_INCOMING_CALL_ADDRESS)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                    sb.append(android.telecom.Log.pii(bundle.get(str)));
                    break;
                default:
                    sb.append(bundle.get(str));
                    break;
            }
            sb.append(", ");
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mAccountHandle, 0);
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeParcelable(this.mExtras, 0);
        parcel.writeInt(this.mVideoState);
        parcel.writeString(this.mTelecomCallId);
        parcel.writeInt(this.mShouldShowIncomingCallUi ? 1 : 0);
        parcel.writeParcelable(this.mRttPipeFromInCall, 0);
        parcel.writeParcelable(this.mRttPipeToInCall, 0);
        parcel.writeList(this.mParticipants);
        parcel.writeInt(this.mIsAdhocConference ? 1 : 0);
    }
}
