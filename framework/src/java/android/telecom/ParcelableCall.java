package android.telecom;

/* loaded from: classes3.dex */
public final class ParcelableCall implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.ParcelableCall> CREATOR = new android.os.Parcelable.Creator<android.telecom.ParcelableCall>() { // from class: android.telecom.ParcelableCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.ParcelableCall createFromParcel(android.os.Parcel parcel) {
            java.lang.ClassLoader classLoader = android.telecom.ParcelableCall.class.getClassLoader();
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            android.telecom.DisconnectCause disconnectCause = (android.telecom.DisconnectCause) parcel.readParcelable(classLoader, android.telecom.DisconnectCause.class);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            parcel.readList(arrayList, classLoader, java.lang.String.class);
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            long readLong = parcel.readLong();
            android.net.Uri createFromParcel = android.net.Uri.CREATOR.createFromParcel(parcel);
            int readInt4 = parcel.readInt();
            java.lang.String readString2 = parcel.readString();
            int readInt5 = parcel.readInt();
            android.telecom.GatewayInfo gatewayInfo = (android.telecom.GatewayInfo) parcel.readParcelable(classLoader, android.telecom.GatewayInfo.class);
            android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readParcelable(classLoader, android.telecom.PhoneAccountHandle.class);
            boolean z = parcel.readByte() == 1;
            com.android.internal.telecom.IVideoProvider asInterface = com.android.internal.telecom.IVideoProvider.Stub.asInterface(parcel.readStrongBinder());
            java.lang.String readString3 = parcel.readString();
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            boolean z2 = z;
            parcel.readList(arrayList2, classLoader, java.lang.String.class);
            android.telecom.StatusHints statusHints = (android.telecom.StatusHints) parcel.readParcelable(classLoader, android.telecom.StatusHints.class);
            int readInt6 = parcel.readInt();
            java.util.ArrayList arrayList3 = new java.util.ArrayList();
            parcel.readList(arrayList3, classLoader, java.lang.String.class);
            android.os.Bundle readBundle = parcel.readBundle(classLoader);
            android.os.Bundle readBundle2 = parcel.readBundle(classLoader);
            int readInt7 = parcel.readInt();
            boolean z3 = parcel.readByte() == 1;
            android.telecom.ParcelableRttCall parcelableRttCall = (android.telecom.ParcelableRttCall) parcel.readParcelable(classLoader, android.telecom.ParcelableRttCall.class);
            boolean z4 = z3;
            long readLong2 = parcel.readLong();
            int readInt8 = parcel.readInt();
            int readInt9 = parcel.readInt();
            java.lang.String readString4 = parcel.readString();
            return new android.telecom.ParcelableCall.ParcelableCallBuilder().setId(readString).setState(readInt).setDisconnectCause(disconnectCause).setCannedSmsResponses(arrayList).setCapabilities(readInt2).setProperties(readInt3).setSupportedAudioRoutes(readInt7).setConnectTimeMillis(readLong).setHandle(createFromParcel).setHandlePresentation(readInt4).setCallerDisplayName(readString2).setCallerDisplayNamePresentation(readInt5).setGatewayInfo(gatewayInfo).setAccountHandle(phoneAccountHandle).setIsVideoCallProviderChanged(z2).setVideoCallProvider(asInterface).setIsRttCallChanged(z4).setRttCall(parcelableRttCall).setParentCallId(readString3).setChildCallIds(arrayList2).setStatusHints(statusHints).setVideoState(readInt6).setConferenceableCallIds(arrayList3).setIntentExtras(readBundle).setExtras(readBundle2).setCreationTimeMillis(readLong2).setCallDirection(readInt8).setCallerNumberVerificationStatus(readInt9).setContactDisplayName(readString4).setActiveChildCallId(parcel.readString()).setContactPhotoUri((android.net.Uri) parcel.readParcelable(classLoader, android.net.Uri.class)).createParcelableCall();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.ParcelableCall[] newArray(int i) {
            return new android.telecom.ParcelableCall[i];
        }
    };
    private final android.telecom.PhoneAccountHandle mAccountHandle;
    private final java.lang.String mActiveChildCallId;
    private final int mCallDirection;
    private final java.lang.String mCallerDisplayName;
    private final int mCallerDisplayNamePresentation;
    private final int mCallerNumberVerificationStatus;
    private final java.util.List<java.lang.String> mCannedSmsResponses;
    private final int mCapabilities;
    private final java.util.List<java.lang.String> mChildCallIds;
    private final java.util.List<java.lang.String> mConferenceableCallIds;
    private final long mConnectTimeMillis;
    private final java.lang.String mContactDisplayName;
    private final android.net.Uri mContactPhotoUri;
    private final long mCreationTimeMillis;
    private final android.telecom.DisconnectCause mDisconnectCause;
    private final android.os.Bundle mExtras;
    private final android.telecom.GatewayInfo mGatewayInfo;
    private final android.net.Uri mHandle;
    private final int mHandlePresentation;
    private final java.lang.String mId;
    private final android.os.Bundle mIntentExtras;
    private final boolean mIsRttCallChanged;
    private final boolean mIsVideoCallProviderChanged;
    private final java.lang.String mParentCallId;
    private final int mProperties;
    private final android.telecom.ParcelableRttCall mRttCall;
    private final int mState;
    private final android.telecom.StatusHints mStatusHints;
    private final int mSupportedAudioRoutes;
    private android.telecom.VideoCallImpl mVideoCall;
    private final com.android.internal.telecom.IVideoProvider mVideoCallProvider;
    private final int mVideoState;

    public static class ParcelableCallBuilder {
        private android.telecom.PhoneAccountHandle mAccountHandle;
        private java.lang.String mActiveChildCallId;
        private int mCallDirection;
        private java.lang.String mCallerDisplayName;
        private int mCallerDisplayNamePresentation;
        private int mCallerNumberVerificationStatus;
        private java.util.List<java.lang.String> mCannedSmsResponses;
        private int mCapabilities;
        private java.util.List<java.lang.String> mChildCallIds;
        private java.util.List<java.lang.String> mConferenceableCallIds;
        private long mConnectTimeMillis;
        private java.lang.String mContactDisplayName;
        private android.net.Uri mContactPhotoUri;
        private long mCreationTimeMillis;
        private android.telecom.DisconnectCause mDisconnectCause;
        private android.os.Bundle mExtras;
        private android.telecom.GatewayInfo mGatewayInfo;
        private android.net.Uri mHandle;
        private int mHandlePresentation;
        private java.lang.String mId;
        private android.os.Bundle mIntentExtras;
        private boolean mIsRttCallChanged;
        private boolean mIsVideoCallProviderChanged;
        private java.lang.String mParentCallId;
        private int mProperties;
        private android.telecom.ParcelableRttCall mRttCall;
        private int mState;
        private android.telecom.StatusHints mStatusHints;
        private int mSupportedAudioRoutes;
        private com.android.internal.telecom.IVideoProvider mVideoCallProvider;
        private int mVideoState;

        public android.telecom.ParcelableCall.ParcelableCallBuilder setId(java.lang.String str) {
            this.mId = str;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setState(int i) {
            this.mState = i;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setDisconnectCause(android.telecom.DisconnectCause disconnectCause) {
            this.mDisconnectCause = disconnectCause;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setCannedSmsResponses(java.util.List<java.lang.String> list) {
            this.mCannedSmsResponses = list;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setCapabilities(int i) {
            this.mCapabilities = i;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setProperties(int i) {
            this.mProperties = i;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setSupportedAudioRoutes(int i) {
            this.mSupportedAudioRoutes = i;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setConnectTimeMillis(long j) {
            this.mConnectTimeMillis = j;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setHandle(android.net.Uri uri) {
            this.mHandle = uri;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setHandlePresentation(int i) {
            this.mHandlePresentation = i;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setCallerDisplayName(java.lang.String str) {
            this.mCallerDisplayName = str;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setCallerDisplayNamePresentation(int i) {
            this.mCallerDisplayNamePresentation = i;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setGatewayInfo(android.telecom.GatewayInfo gatewayInfo) {
            this.mGatewayInfo = gatewayInfo;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setAccountHandle(android.telecom.PhoneAccountHandle phoneAccountHandle) {
            this.mAccountHandle = phoneAccountHandle;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setIsVideoCallProviderChanged(boolean z) {
            this.mIsVideoCallProviderChanged = z;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setVideoCallProvider(com.android.internal.telecom.IVideoProvider iVideoProvider) {
            this.mVideoCallProvider = iVideoProvider;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setIsRttCallChanged(boolean z) {
            this.mIsRttCallChanged = z;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setRttCall(android.telecom.ParcelableRttCall parcelableRttCall) {
            this.mRttCall = parcelableRttCall;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setParentCallId(java.lang.String str) {
            this.mParentCallId = str;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setChildCallIds(java.util.List<java.lang.String> list) {
            this.mChildCallIds = list;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setStatusHints(android.telecom.StatusHints statusHints) {
            this.mStatusHints = statusHints;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setVideoState(int i) {
            this.mVideoState = i;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setConferenceableCallIds(java.util.List<java.lang.String> list) {
            this.mConferenceableCallIds = list;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setIntentExtras(android.os.Bundle bundle) {
            this.mIntentExtras = bundle;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setCreationTimeMillis(long j) {
            this.mCreationTimeMillis = j;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setCallDirection(int i) {
            this.mCallDirection = i;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setCallerNumberVerificationStatus(int i) {
            this.mCallerNumberVerificationStatus = i;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setContactDisplayName(java.lang.String str) {
            this.mContactDisplayName = str;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setActiveChildCallId(java.lang.String str) {
            this.mActiveChildCallId = str;
            return this;
        }

        public android.telecom.ParcelableCall.ParcelableCallBuilder setContactPhotoUri(android.net.Uri uri) {
            this.mContactPhotoUri = uri;
            return this;
        }

        public android.telecom.ParcelableCall createParcelableCall() {
            return new android.telecom.ParcelableCall(this.mId, this.mState, this.mDisconnectCause, this.mCannedSmsResponses, this.mCapabilities, this.mProperties, this.mSupportedAudioRoutes, this.mConnectTimeMillis, this.mHandle, this.mHandlePresentation, this.mCallerDisplayName, this.mCallerDisplayNamePresentation, this.mGatewayInfo, this.mAccountHandle, this.mIsVideoCallProviderChanged, this.mVideoCallProvider, this.mIsRttCallChanged, this.mRttCall, this.mParentCallId, this.mChildCallIds, this.mStatusHints, this.mVideoState, this.mConferenceableCallIds, this.mIntentExtras, this.mExtras, this.mCreationTimeMillis, this.mCallDirection, this.mCallerNumberVerificationStatus, this.mContactDisplayName, this.mActiveChildCallId, this.mContactPhotoUri);
        }

        public static android.telecom.ParcelableCall.ParcelableCallBuilder fromParcelableCall(android.telecom.ParcelableCall parcelableCall) {
            android.telecom.ParcelableCall.ParcelableCallBuilder parcelableCallBuilder = new android.telecom.ParcelableCall.ParcelableCallBuilder();
            parcelableCallBuilder.mId = parcelableCall.mId;
            parcelableCallBuilder.mState = parcelableCall.mState;
            parcelableCallBuilder.mDisconnectCause = parcelableCall.mDisconnectCause;
            parcelableCallBuilder.mCannedSmsResponses = parcelableCall.mCannedSmsResponses;
            parcelableCallBuilder.mCapabilities = parcelableCall.mCapabilities;
            parcelableCallBuilder.mProperties = parcelableCall.mProperties;
            parcelableCallBuilder.mSupportedAudioRoutes = parcelableCall.mSupportedAudioRoutes;
            parcelableCallBuilder.mConnectTimeMillis = parcelableCall.mConnectTimeMillis;
            parcelableCallBuilder.mHandle = parcelableCall.mHandle;
            parcelableCallBuilder.mHandlePresentation = parcelableCall.mHandlePresentation;
            parcelableCallBuilder.mCallerDisplayName = parcelableCall.mCallerDisplayName;
            parcelableCallBuilder.mCallerDisplayNamePresentation = parcelableCall.mCallerDisplayNamePresentation;
            parcelableCallBuilder.mGatewayInfo = parcelableCall.mGatewayInfo;
            parcelableCallBuilder.mAccountHandle = parcelableCall.mAccountHandle;
            parcelableCallBuilder.mIsVideoCallProviderChanged = parcelableCall.mIsVideoCallProviderChanged;
            parcelableCallBuilder.mVideoCallProvider = parcelableCall.mVideoCallProvider;
            parcelableCallBuilder.mIsRttCallChanged = parcelableCall.mIsRttCallChanged;
            parcelableCallBuilder.mRttCall = parcelableCall.mRttCall;
            parcelableCallBuilder.mParentCallId = parcelableCall.mParentCallId;
            parcelableCallBuilder.mChildCallIds = parcelableCall.mChildCallIds;
            parcelableCallBuilder.mStatusHints = parcelableCall.mStatusHints;
            parcelableCallBuilder.mVideoState = parcelableCall.mVideoState;
            parcelableCallBuilder.mConferenceableCallIds = parcelableCall.mConferenceableCallIds;
            parcelableCallBuilder.mIntentExtras = parcelableCall.mIntentExtras;
            parcelableCallBuilder.mExtras = parcelableCall.mExtras;
            parcelableCallBuilder.mCreationTimeMillis = parcelableCall.mCreationTimeMillis;
            parcelableCallBuilder.mCallDirection = parcelableCall.mCallDirection;
            parcelableCallBuilder.mCallerNumberVerificationStatus = parcelableCall.mCallerNumberVerificationStatus;
            parcelableCallBuilder.mContactDisplayName = parcelableCall.mContactDisplayName;
            parcelableCallBuilder.mActiveChildCallId = parcelableCall.mActiveChildCallId;
            parcelableCallBuilder.mContactPhotoUri = parcelableCall.mContactPhotoUri;
            return parcelableCallBuilder;
        }
    }

    public ParcelableCall(java.lang.String str, int i, android.telecom.DisconnectCause disconnectCause, java.util.List<java.lang.String> list, int i2, int i3, int i4, long j, android.net.Uri uri, int i5, java.lang.String str2, int i6, android.telecom.GatewayInfo gatewayInfo, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z, com.android.internal.telecom.IVideoProvider iVideoProvider, boolean z2, android.telecom.ParcelableRttCall parcelableRttCall, java.lang.String str3, java.util.List<java.lang.String> list2, android.telecom.StatusHints statusHints, int i7, java.util.List<java.lang.String> list3, android.os.Bundle bundle, android.os.Bundle bundle2, long j2, int i8, int i9, java.lang.String str4, java.lang.String str5, android.net.Uri uri2) {
        this.mId = str;
        this.mState = i;
        this.mDisconnectCause = disconnectCause;
        this.mCannedSmsResponses = list;
        this.mCapabilities = i2;
        this.mProperties = i3;
        this.mSupportedAudioRoutes = i4;
        this.mConnectTimeMillis = j;
        this.mHandle = uri;
        this.mHandlePresentation = i5;
        this.mCallerDisplayName = str2;
        this.mCallerDisplayNamePresentation = i6;
        this.mGatewayInfo = gatewayInfo;
        this.mAccountHandle = phoneAccountHandle;
        this.mIsVideoCallProviderChanged = z;
        this.mVideoCallProvider = iVideoProvider;
        this.mIsRttCallChanged = z2;
        this.mRttCall = parcelableRttCall;
        this.mParentCallId = str3;
        this.mChildCallIds = list2;
        this.mStatusHints = statusHints;
        this.mVideoState = i7;
        this.mConferenceableCallIds = java.util.Collections.unmodifiableList(list3);
        this.mIntentExtras = bundle;
        this.mExtras = bundle2;
        this.mCreationTimeMillis = j2;
        this.mCallDirection = i8;
        this.mCallerNumberVerificationStatus = i9;
        this.mContactDisplayName = str4;
        this.mActiveChildCallId = str5;
        this.mContactPhotoUri = uri2;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public int getState() {
        return this.mState;
    }

    public android.telecom.DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public java.util.List<java.lang.String> getCannedSmsResponses() {
        return this.mCannedSmsResponses;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public int getProperties() {
        return this.mProperties;
    }

    public int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    public android.net.Uri getHandle() {
        return this.mHandle;
    }

    public int getHandlePresentation() {
        return this.mHandlePresentation;
    }

    public java.lang.String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public android.telecom.GatewayInfo getGatewayInfo() {
        return this.mGatewayInfo;
    }

    public android.telecom.PhoneAccountHandle getAccountHandle() {
        return this.mAccountHandle;
    }

    public android.telecom.VideoCallImpl getVideoCallImpl(java.lang.String str, int i) {
        if (this.mVideoCall == null && this.mVideoCallProvider != null) {
            try {
                this.mVideoCall = new android.telecom.VideoCallImpl(this.mVideoCallProvider, str, i);
            } catch (android.os.RemoteException e) {
            }
        }
        return this.mVideoCall;
    }

    public com.android.internal.telecom.IVideoProvider getVideoProvider() {
        return this.mVideoCallProvider;
    }

    public boolean getIsRttCallChanged() {
        return this.mIsRttCallChanged;
    }

    public android.telecom.ParcelableRttCall getParcelableRttCall() {
        return this.mRttCall;
    }

    public java.lang.String getParentCallId() {
        return this.mParentCallId;
    }

    public java.util.List<java.lang.String> getChildCallIds() {
        return this.mChildCallIds;
    }

    public java.util.List<java.lang.String> getConferenceableCallIds() {
        return this.mConferenceableCallIds;
    }

    public android.telecom.StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public android.os.Bundle getIntentExtras() {
        return this.mIntentExtras;
    }

    public boolean isVideoCallProviderChanged() {
        return this.mIsVideoCallProviderChanged;
    }

    public long getCreationTimeMillis() {
        return this.mCreationTimeMillis;
    }

    public int getCallDirection() {
        return this.mCallDirection;
    }

    public int getCallerNumberVerificationStatus() {
        return this.mCallerNumberVerificationStatus;
    }

    public java.lang.String getContactDisplayName() {
        return this.mContactDisplayName;
    }

    public android.net.Uri getContactPhotoUri() {
        return this.mContactPhotoUri;
    }

    public java.lang.String getActiveChildCallId() {
        return this.mActiveChildCallId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeInt(this.mState);
        parcel.writeParcelable(this.mDisconnectCause, 0);
        parcel.writeList(this.mCannedSmsResponses);
        parcel.writeInt(this.mCapabilities);
        parcel.writeInt(this.mProperties);
        parcel.writeLong(this.mConnectTimeMillis);
        android.net.Uri.writeToParcel(parcel, this.mHandle);
        parcel.writeInt(this.mHandlePresentation);
        parcel.writeString(this.mCallerDisplayName);
        parcel.writeInt(this.mCallerDisplayNamePresentation);
        parcel.writeParcelable(this.mGatewayInfo, 0);
        parcel.writeParcelable(this.mAccountHandle, 0);
        parcel.writeByte(this.mIsVideoCallProviderChanged ? (byte) 1 : (byte) 0);
        parcel.writeStrongBinder(this.mVideoCallProvider != null ? this.mVideoCallProvider.asBinder() : null);
        parcel.writeString(this.mParentCallId);
        parcel.writeList(this.mChildCallIds);
        parcel.writeParcelable(this.mStatusHints, 0);
        parcel.writeInt(this.mVideoState);
        parcel.writeList(this.mConferenceableCallIds);
        parcel.writeBundle(this.mIntentExtras);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mSupportedAudioRoutes);
        parcel.writeByte(this.mIsRttCallChanged ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.mRttCall, 0);
        parcel.writeLong(this.mCreationTimeMillis);
        parcel.writeInt(this.mCallDirection);
        parcel.writeInt(this.mCallerNumberVerificationStatus);
        parcel.writeString(this.mContactDisplayName);
        parcel.writeString(this.mActiveChildCallId);
        parcel.writeParcelable(this.mContactPhotoUri, 0);
    }

    public java.lang.String toString() {
        return java.lang.String.format("[%s, parent:%s, children:%s]", this.mId, this.mParentCallId, this.mChildCallIds);
    }
}
