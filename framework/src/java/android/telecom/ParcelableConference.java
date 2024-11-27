package android.telecom;

/* loaded from: classes3.dex */
public final class ParcelableConference implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.ParcelableConference> CREATOR = new android.os.Parcelable.Creator<android.telecom.ParcelableConference>() { // from class: android.telecom.ParcelableConference.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.ParcelableConference createFromParcel(android.os.Parcel parcel) {
            java.lang.ClassLoader classLoader = android.telecom.ParcelableConference.class.getClassLoader();
            android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readParcelable(classLoader, android.telecom.PhoneAccountHandle.class);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.util.ArrayList arrayList = new java.util.ArrayList(2);
            parcel.readList(arrayList, classLoader, java.lang.String.class);
            long readLong = parcel.readLong();
            return new android.telecom.ParcelableConference(phoneAccountHandle, readInt, readInt2, parcel.readInt(), arrayList, com.android.internal.telecom.IVideoProvider.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), readLong, parcel.readLong(), (android.telecom.StatusHints) parcel.readParcelable(classLoader, android.telecom.StatusHints.class), parcel.readBundle(classLoader), (android.net.Uri) parcel.readParcelable(classLoader, android.net.Uri.class), parcel.readInt(), parcel.readString(), parcel.readInt(), (android.telecom.DisconnectCause) parcel.readParcelable(classLoader, android.telecom.DisconnectCause.class), parcel.readInt() == 1, parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.ParcelableConference[] newArray(int i) {
            return new android.telecom.ParcelableConference[i];
        }
    };
    private final android.net.Uri mAddress;
    private final int mAddressPresentation;
    private final int mCallDirection;
    private final java.lang.String mCallerDisplayName;
    private final int mCallerDisplayNamePresentation;
    private final long mConnectElapsedTimeMillis;
    private final long mConnectTimeMillis;
    private final int mConnectionCapabilities;
    private final java.util.List<java.lang.String> mConnectionIds;
    private final int mConnectionProperties;
    private final android.telecom.DisconnectCause mDisconnectCause;
    private final android.os.Bundle mExtras;
    private final android.telecom.PhoneAccountHandle mPhoneAccount;
    private final boolean mRingbackRequested;
    private final int mState;
    private final android.telecom.StatusHints mStatusHints;
    private final com.android.internal.telecom.IVideoProvider mVideoProvider;
    private final int mVideoState;

    public static final class Builder {
        private android.net.Uri mAddress;
        private java.lang.String mCallerDisplayName;
        private int mConnectionCapabilities;
        private int mConnectionProperties;
        private android.telecom.DisconnectCause mDisconnectCause;
        private android.os.Bundle mExtras;
        private final android.telecom.PhoneAccountHandle mPhoneAccount;
        private boolean mRingbackRequested;
        private final int mState;
        private android.telecom.StatusHints mStatusHints;
        private com.android.internal.telecom.IVideoProvider mVideoProvider;
        private java.util.List<java.lang.String> mConnectionIds = java.util.Collections.emptyList();
        private long mConnectTimeMillis = 0;
        private int mVideoState = 0;
        private long mConnectElapsedTimeMillis = 0;
        private int mAddressPresentation = 3;
        private int mCallerDisplayNamePresentation = 3;
        private int mCallDirection = -1;

        public Builder(android.telecom.PhoneAccountHandle phoneAccountHandle, int i) {
            this.mPhoneAccount = phoneAccountHandle;
            this.mState = i;
        }

        public android.telecom.ParcelableConference.Builder setDisconnectCause(android.telecom.DisconnectCause disconnectCause) {
            this.mDisconnectCause = disconnectCause;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setRingbackRequested(boolean z) {
            this.mRingbackRequested = z;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setCallerDisplayName(java.lang.String str, int i) {
            this.mCallerDisplayName = str;
            this.mCallerDisplayNamePresentation = i;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setAddress(android.net.Uri uri, int i) {
            this.mAddress = uri;
            this.mAddressPresentation = i;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setStatusHints(android.telecom.StatusHints statusHints) {
            this.mStatusHints = statusHints;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setConnectTimeMillis(long j, long j2) {
            this.mConnectTimeMillis = j;
            this.mConnectElapsedTimeMillis = j2;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setVideoAttributes(com.android.internal.telecom.IVideoProvider iVideoProvider, int i) {
            this.mVideoProvider = iVideoProvider;
            this.mVideoState = i;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setConnectionIds(java.util.List<java.lang.String> list) {
            this.mConnectionIds = list;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setConnectionProperties(int i) {
            this.mConnectionProperties = i;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setConnectionCapabilities(int i) {
            this.mConnectionCapabilities = i;
            return this;
        }

        public android.telecom.ParcelableConference.Builder setCallDirection(int i) {
            this.mCallDirection = i;
            return this;
        }

        public android.telecom.ParcelableConference build() {
            return new android.telecom.ParcelableConference(this.mPhoneAccount, this.mState, this.mConnectionCapabilities, this.mConnectionProperties, this.mConnectionIds, this.mVideoProvider, this.mVideoState, this.mConnectTimeMillis, this.mConnectElapsedTimeMillis, this.mStatusHints, this.mExtras, this.mAddress, this.mAddressPresentation, this.mCallerDisplayName, this.mCallerDisplayNamePresentation, this.mDisconnectCause, this.mRingbackRequested, this.mCallDirection);
        }
    }

    private ParcelableConference(android.telecom.PhoneAccountHandle phoneAccountHandle, int i, int i2, int i3, java.util.List<java.lang.String> list, com.android.internal.telecom.IVideoProvider iVideoProvider, int i4, long j, long j2, android.telecom.StatusHints statusHints, android.os.Bundle bundle, android.net.Uri uri, int i5, java.lang.String str, int i6, android.telecom.DisconnectCause disconnectCause, boolean z, int i7) {
        this.mPhoneAccount = phoneAccountHandle;
        this.mState = i;
        this.mConnectionCapabilities = i2;
        this.mConnectionProperties = i3;
        this.mConnectionIds = list;
        this.mVideoProvider = iVideoProvider;
        this.mVideoState = i4;
        this.mConnectTimeMillis = j;
        this.mStatusHints = statusHints;
        this.mExtras = bundle;
        this.mConnectElapsedTimeMillis = j2;
        this.mAddress = uri;
        this.mAddressPresentation = i5;
        this.mCallerDisplayName = str;
        this.mCallerDisplayNamePresentation = i6;
        this.mDisconnectCause = disconnectCause;
        this.mRingbackRequested = z;
        this.mCallDirection = i7;
    }

    public java.lang.String toString() {
        return new java.lang.StringBuffer().append("account: ").append(this.mPhoneAccount).append(", state: ").append(android.telecom.Connection.stateToString(this.mState)).append(", capabilities: ").append(android.telecom.Connection.capabilitiesToString(this.mConnectionCapabilities)).append(", properties: ").append(android.telecom.Connection.propertiesToString(this.mConnectionProperties)).append(", connectTime: ").append(this.mConnectTimeMillis).append(", children: ").append(this.mConnectionIds).append(", VideoState: ").append(this.mVideoState).append(", VideoProvider: ").append(this.mVideoProvider).append(", isRingbackRequested: ").append(this.mRingbackRequested).append(", disconnectCause: ").append(this.mDisconnectCause).append(", callDirection: ").append(this.mCallDirection).toString();
    }

    public android.telecom.PhoneAccountHandle getPhoneAccount() {
        return this.mPhoneAccount;
    }

    public int getState() {
        return this.mState;
    }

    public int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public java.util.List<java.lang.String> getConnectionIds() {
        return this.mConnectionIds;
    }

    public long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    public long getConnectElapsedTimeMillis() {
        return this.mConnectElapsedTimeMillis;
    }

    public com.android.internal.telecom.IVideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public android.telecom.StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public android.net.Uri getHandle() {
        return this.mAddress;
    }

    public final android.telecom.DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public int getHandlePresentation() {
        return this.mAddressPresentation;
    }

    public int getCallDirection() {
        return this.mCallDirection;
    }

    public java.lang.String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mPhoneAccount, 0);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mConnectionCapabilities);
        parcel.writeList(this.mConnectionIds);
        parcel.writeLong(this.mConnectTimeMillis);
        parcel.writeStrongBinder(this.mVideoProvider != null ? this.mVideoProvider.asBinder() : null);
        parcel.writeInt(this.mVideoState);
        parcel.writeParcelable(this.mStatusHints, 0);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mConnectionProperties);
        parcel.writeLong(this.mConnectElapsedTimeMillis);
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeInt(this.mAddressPresentation);
        parcel.writeString(this.mCallerDisplayName);
        parcel.writeInt(this.mCallerDisplayNamePresentation);
        parcel.writeParcelable(this.mDisconnectCause, 0);
        parcel.writeInt(this.mRingbackRequested ? 1 : 0);
        parcel.writeInt(this.mCallDirection);
    }
}
