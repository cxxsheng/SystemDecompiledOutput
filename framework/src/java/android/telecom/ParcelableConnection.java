package android.telecom;

/* loaded from: classes3.dex */
public final class ParcelableConnection implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.ParcelableConnection> CREATOR = new android.os.Parcelable.Creator<android.telecom.ParcelableConnection>() { // from class: android.telecom.ParcelableConnection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.ParcelableConnection createFromParcel(android.os.Parcel parcel) {
            java.lang.ClassLoader classLoader = android.telecom.ParcelableConnection.class.getClassLoader();
            android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readParcelable(classLoader, android.telecom.PhoneAccountHandle.class);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.net.Uri uri = (android.net.Uri) parcel.readParcelable(classLoader, android.net.Uri.class);
            int readInt3 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            int readInt4 = parcel.readInt();
            com.android.internal.telecom.IVideoProvider asInterface = com.android.internal.telecom.IVideoProvider.Stub.asInterface(parcel.readStrongBinder());
            int readInt5 = parcel.readInt();
            boolean z = parcel.readByte() == 1;
            boolean z2 = parcel.readByte() == 1;
            long readLong = parcel.readLong();
            android.telecom.StatusHints statusHints = (android.telecom.StatusHints) parcel.readParcelable(classLoader, android.telecom.StatusHints.class);
            android.telecom.DisconnectCause disconnectCause = (android.telecom.DisconnectCause) parcel.readParcelable(classLoader, android.telecom.DisconnectCause.class);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            parcel.readStringList(arrayList);
            return new android.telecom.ParcelableConnection(phoneAccountHandle, readInt, readInt2, parcel.readInt(), parcel.readInt(), uri, readInt3, readString, readInt4, asInterface, readInt5, z, z2, readLong, parcel.readLong(), statusHints, disconnectCause, arrayList, android.os.Bundle.setDefusable(parcel.readBundle(classLoader), true), parcel.readString(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.ParcelableConnection[] newArray(int i) {
            return new android.telecom.ParcelableConnection[i];
        }
    };
    private final android.net.Uri mAddress;
    private final int mAddressPresentation;
    private int mCallDirection;
    private final java.lang.String mCallerDisplayName;
    private final int mCallerDisplayNamePresentation;
    private int mCallerNumberVerificationStatus;
    private final java.util.List<java.lang.String> mConferenceableConnectionIds;
    private final long mConnectElapsedTimeMillis;
    private final long mConnectTimeMillis;
    private final int mConnectionCapabilities;
    private final int mConnectionProperties;
    private final android.telecom.DisconnectCause mDisconnectCause;
    private final android.os.Bundle mExtras;
    private final boolean mIsVoipAudioMode;
    private java.lang.String mParentCallId;
    private final android.telecom.PhoneAccountHandle mPhoneAccount;
    private final boolean mRingbackRequested;
    private final int mState;
    private final android.telecom.StatusHints mStatusHints;
    private final int mSupportedAudioRoutes;
    private final com.android.internal.telecom.IVideoProvider mVideoProvider;
    private final int mVideoState;

    public ParcelableConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, int i, int i2, int i3, int i4, android.net.Uri uri, int i5, java.lang.String str, int i6, com.android.internal.telecom.IVideoProvider iVideoProvider, int i7, boolean z, boolean z2, long j, long j2, android.telecom.StatusHints statusHints, android.telecom.DisconnectCause disconnectCause, java.util.List<java.lang.String> list, android.os.Bundle bundle, java.lang.String str2, int i8, int i9) {
        this(phoneAccountHandle, i, i2, i3, i4, uri, i5, str, i6, iVideoProvider, i7, z, z2, j, j2, statusHints, disconnectCause, list, bundle, i9);
        this.mParentCallId = str2;
        this.mCallDirection = i8;
    }

    public ParcelableConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, int i, int i2, int i3, int i4, android.net.Uri uri, int i5, java.lang.String str, int i6, com.android.internal.telecom.IVideoProvider iVideoProvider, int i7, boolean z, boolean z2, long j, long j2, android.telecom.StatusHints statusHints, android.telecom.DisconnectCause disconnectCause, java.util.List<java.lang.String> list, android.os.Bundle bundle, int i8) {
        this.mPhoneAccount = phoneAccountHandle;
        this.mState = i;
        this.mConnectionCapabilities = i2;
        this.mConnectionProperties = i3;
        this.mSupportedAudioRoutes = i4;
        this.mAddress = uri;
        this.mAddressPresentation = i5;
        this.mCallerDisplayName = str;
        this.mCallerDisplayNamePresentation = i6;
        this.mVideoProvider = iVideoProvider;
        this.mVideoState = i7;
        this.mRingbackRequested = z;
        this.mIsVoipAudioMode = z2;
        this.mConnectTimeMillis = j;
        this.mConnectElapsedTimeMillis = j2;
        this.mStatusHints = statusHints;
        this.mDisconnectCause = disconnectCause;
        this.mConferenceableConnectionIds = list;
        this.mExtras = bundle;
        this.mParentCallId = null;
        this.mCallDirection = -1;
        this.mCallerNumberVerificationStatus = i8;
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

    public int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public android.net.Uri getHandle() {
        return this.mAddress;
    }

    public int getHandlePresentation() {
        return this.mAddressPresentation;
    }

    public java.lang.String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public com.android.internal.telecom.IVideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public boolean getIsVoipAudioMode() {
        return this.mIsVoipAudioMode;
    }

    public long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    public long getConnectElapsedTimeMillis() {
        return this.mConnectElapsedTimeMillis;
    }

    public final android.telecom.StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public final android.telecom.DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public final java.util.List<java.lang.String> getConferenceableConnectionIds() {
        return this.mConferenceableConnectionIds;
    }

    public final android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public final java.lang.String getParentCallId() {
        return this.mParentCallId;
    }

    public int getCallDirection() {
        return this.mCallDirection;
    }

    public int getCallerNumberVerificationStatus() {
        return this.mCallerNumberVerificationStatus;
    }

    public java.lang.String toString() {
        return "ParcelableConnection [act:" + this.mPhoneAccount + "], state:" + this.mState + ", capabilities:" + android.telecom.Connection.capabilitiesToString(this.mConnectionCapabilities) + ", properties:" + android.telecom.Connection.propertiesToString(this.mConnectionProperties) + ", extras:" + this.mExtras + ", parent:" + this.mParentCallId + ", callDirection:" + this.mCallDirection;
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
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeInt(this.mAddressPresentation);
        parcel.writeString(this.mCallerDisplayName);
        parcel.writeInt(this.mCallerDisplayNamePresentation);
        parcel.writeStrongBinder(this.mVideoProvider != null ? this.mVideoProvider.asBinder() : null);
        parcel.writeInt(this.mVideoState);
        parcel.writeByte(this.mRingbackRequested ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mIsVoipAudioMode ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.mConnectTimeMillis);
        parcel.writeParcelable(this.mStatusHints, 0);
        parcel.writeParcelable(this.mDisconnectCause, 0);
        parcel.writeStringList(this.mConferenceableConnectionIds);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mConnectionProperties);
        parcel.writeInt(this.mSupportedAudioRoutes);
        parcel.writeString(this.mParentCallId);
        parcel.writeLong(this.mConnectElapsedTimeMillis);
        parcel.writeInt(this.mCallDirection);
        parcel.writeInt(this.mCallerNumberVerificationStatus);
    }
}
