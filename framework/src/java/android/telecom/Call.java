package android.telecom;

/* loaded from: classes3.dex */
public final class Call {

    @java.lang.Deprecated
    public static final java.lang.String AVAILABLE_PHONE_ACCOUNTS = "selectPhoneAccountAccounts";
    public static final java.lang.String EVENT_CLEAR_DIAGNOSTIC_MESSAGE = "android.telecom.event.CLEAR_DIAGNOSTIC_MESSAGE";
    public static final java.lang.String EVENT_DISPLAY_DIAGNOSTIC_MESSAGE = "android.telecom.event.DISPLAY_DIAGNOSTIC_MESSAGE";
    public static final java.lang.String EXTRA_ASSERTED_DISPLAY_NAME = "android.telecom.extra.ASSERTED_DISPLAY_NAME";
    public static final java.lang.String EXTRA_DIAGNOSTIC_MESSAGE = "android.telecom.extra.DIAGNOSTIC_MESSAGE";
    public static final java.lang.String EXTRA_DIAGNOSTIC_MESSAGE_ID = "android.telecom.extra.DIAGNOSTIC_MESSAGE_ID";
    public static final java.lang.String EXTRA_IS_BUSINESS_CALL = "android.telecom.extra.IS_BUSINESS_CALL";
    public static final java.lang.String EXTRA_IS_SUPPRESSED_BY_DO_NOT_DISTURB = "android.telecom.extra.IS_SUPPRESSED_BY_DO_NOT_DISTURB";
    public static final java.lang.String EXTRA_LAST_EMERGENCY_CALLBACK_TIME_MILLIS = "android.telecom.extra.LAST_EMERGENCY_CALLBACK_TIME_MILLIS";
    public static final java.lang.String EXTRA_SILENT_RINGING_REQUESTED = "android.telecom.extra.SILENT_RINGING_REQUESTED";
    public static final java.lang.String EXTRA_SUGGESTED_PHONE_ACCOUNTS = "android.telecom.extra.SUGGESTED_PHONE_ACCOUNTS";
    public static final int REJECT_REASON_DECLINED = 1;
    public static final int REJECT_REASON_UNWANTED = 2;
    public static final int STATE_ACTIVE = 4;
    public static final int STATE_AUDIO_PROCESSING = 12;
    public static final int STATE_CONNECTING = 9;
    public static final int STATE_DIALING = 1;
    public static final int STATE_DISCONNECTED = 7;
    public static final int STATE_DISCONNECTING = 10;
    public static final int STATE_HOLDING = 3;
    public static final int STATE_NEW = 0;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int STATE_PRE_DIAL_WAIT = 8;
    public static final int STATE_PULLING_CALL = 11;
    public static final int STATE_RINGING = 2;
    public static final int STATE_SELECT_PHONE_ACCOUNT = 8;
    public static final int STATE_SIMULATED_RINGING = 13;
    private java.lang.String mActiveGenericConferenceChild;
    private final java.util.List<android.telecom.CallbackRecord<android.telecom.Call.Callback>> mCallbackRecords;
    private java.lang.String mCallingPackage;
    private java.util.List<java.lang.String> mCannedTextResponses;
    private final java.util.List<android.telecom.Call> mChildren;
    private boolean mChildrenCached;
    private final java.util.List<java.lang.String> mChildrenIds;
    private final java.util.List<android.telecom.Call> mConferenceableCalls;
    private android.telecom.Call.Details mDetails;
    private android.os.Bundle mExtras;
    private final android.telecom.InCallAdapter mInCallAdapter;
    private java.lang.String mParentId;
    private final android.telecom.Phone mPhone;
    private java.lang.String mRemainingPostDialSequence;
    private android.telecom.Call.RttCall mRttCall;
    private int mState;
    private int mTargetSdkVersion;
    private final java.lang.String mTelecomCallId;
    private final java.util.List<android.telecom.Call> mUnmodifiableChildren;
    private final java.util.List<android.telecom.Call> mUnmodifiableConferenceableCalls;
    private android.telecom.VideoCallImpl mVideoCallImpl;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallState {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static abstract class Listener extends android.telecom.Call.Callback {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RejectReason {
    }

    public static class Details {
        public static final int CAPABILITY_ADD_PARTICIPANT = 33554432;
        public static final int CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO = 4194304;
        public static final int CAPABILITY_CAN_PAUSE_VIDEO = 1048576;
        public static final int CAPABILITY_CAN_PULL_CALL = 8388608;
        public static final int CAPABILITY_CAN_SEND_RESPONSE_VIA_CONNECTION = 2097152;
        public static final int CAPABILITY_CAN_UPGRADE_TO_VIDEO = 524288;
        public static final int CAPABILITY_DISCONNECT_FROM_CONFERENCE = 8192;
        public static final int CAPABILITY_HOLD = 1;
        public static final int CAPABILITY_MANAGE_CONFERENCE = 128;
        public static final int CAPABILITY_MERGE_CONFERENCE = 4;
        public static final int CAPABILITY_MUTE = 64;
        public static final int CAPABILITY_REMOTE_PARTY_SUPPORTS_RTT = 268435456;
        public static final int CAPABILITY_RESPOND_VIA_TEXT = 32;
        public static final int CAPABILITY_SEPARATE_FROM_CONFERENCE = 4096;
        public static final int CAPABILITY_SPEED_UP_MT_AUDIO = 262144;
        public static final int CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL = 768;
        public static final int CAPABILITY_SUPPORTS_VT_LOCAL_RX = 256;
        public static final int CAPABILITY_SUPPORTS_VT_LOCAL_TX = 512;
        public static final int CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL = 3072;
        public static final int CAPABILITY_SUPPORTS_VT_REMOTE_RX = 1024;
        public static final int CAPABILITY_SUPPORTS_VT_REMOTE_TX = 2048;
        public static final int CAPABILITY_SUPPORT_DEFLECT = 16777216;
        public static final int CAPABILITY_SUPPORT_HOLD = 2;
        public static final int CAPABILITY_SWAP_CONFERENCE = 8;
        public static final int CAPABILITY_TRANSFER = 67108864;
        public static final int CAPABILITY_TRANSFER_CONSULTATIVE = 134217728;
        public static final int CAPABILITY_UNUSED_1 = 16;
        public static final int DIRECTION_INCOMING = 0;
        public static final int DIRECTION_OUTGOING = 1;
        public static final int DIRECTION_UNKNOWN = -1;
        public static final int PROPERTY_ASSISTED_DIALING = 512;
        public static final int PROPERTY_CONFERENCE = 1;
        public static final int PROPERTY_CROSS_SIM = 16384;
        public static final int PROPERTY_EMERGENCY_CALLBACK_MODE = 4;
        public static final int PROPERTY_ENTERPRISE_CALL = 32;
        public static final int PROPERTY_GENERIC_CONFERENCE = 2;
        public static final int PROPERTY_HAS_CDMA_VOICE_PRIVACY = 128;
        public static final int PROPERTY_HIGH_DEF_AUDIO = 16;
        public static final int PROPERTY_IS_ADHOC_CONFERENCE = 8192;
        public static final int PROPERTY_IS_EXTERNAL_CALL = 64;
        public static final int PROPERTY_IS_TRANSACTIONAL = 32768;
        public static final int PROPERTY_NETWORK_IDENTIFIED_EMERGENCY_CALL = 2048;
        public static final int PROPERTY_RTT = 1024;
        public static final int PROPERTY_SELF_MANAGED = 256;
        public static final int PROPERTY_VOIP_AUDIO_MODE = 4096;
        public static final int PROPERTY_WIFI = 8;
        private final android.telecom.PhoneAccountHandle mAccountHandle;
        private final int mCallCapabilities;
        private final int mCallDirection;
        private final int mCallProperties;
        private final java.lang.String mCallerDisplayName;
        private final int mCallerDisplayNamePresentation;
        private final int mCallerNumberVerificationStatus;
        private final long mConnectTimeMillis;
        private final java.lang.String mContactDisplayName;
        private final android.net.Uri mContactPhotoUri;
        private final long mCreationTimeMillis;
        private final android.telecom.DisconnectCause mDisconnectCause;
        private final android.os.Bundle mExtras;
        private final android.telecom.GatewayInfo mGatewayInfo;
        private final android.net.Uri mHandle;
        private final int mHandlePresentation;
        private final android.os.Bundle mIntentExtras;
        private final int mState;
        private final android.telecom.StatusHints mStatusHints;
        private final int mSupportedAudioRoutes = 31;
        private final java.lang.String mTelecomCallId;
        private final int mVideoState;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface CallDirection {
        }

        public static boolean can(int i, int i2) {
            return (i & i2) == i2;
        }

        public boolean can(int i) {
            return can(this.mCallCapabilities, i);
        }

        public static java.lang.String capabilitiesToString(int i) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("[Capabilities:");
            if (can(i, 1)) {
                sb.append(" CAPABILITY_HOLD");
            }
            if (can(i, 2)) {
                sb.append(" CAPABILITY_SUPPORT_HOLD");
            }
            if (can(i, 4)) {
                sb.append(" CAPABILITY_MERGE_CONFERENCE");
            }
            if (can(i, 8)) {
                sb.append(" CAPABILITY_SWAP_CONFERENCE");
            }
            if (can(i, 32)) {
                sb.append(" CAPABILITY_RESPOND_VIA_TEXT");
            }
            if (can(i, 64)) {
                sb.append(" CAPABILITY_MUTE");
            }
            if (can(i, 128)) {
                sb.append(" CAPABILITY_MANAGE_CONFERENCE");
            }
            if (can(i, 256)) {
                sb.append(" CAPABILITY_SUPPORTS_VT_LOCAL_RX");
            }
            if (can(i, 512)) {
                sb.append(" CAPABILITY_SUPPORTS_VT_LOCAL_TX");
            }
            if (can(i, 768)) {
                sb.append(" CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL");
            }
            if (can(i, 1024)) {
                sb.append(" CAPABILITY_SUPPORTS_VT_REMOTE_RX");
            }
            if (can(i, 2048)) {
                sb.append(" CAPABILITY_SUPPORTS_VT_REMOTE_TX");
            }
            if (can(i, 4194304)) {
                sb.append(" CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO");
            }
            if (can(i, 3072)) {
                sb.append(" CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL");
            }
            if (can(i, 262144)) {
                sb.append(" CAPABILITY_SPEED_UP_MT_AUDIO");
            }
            if (can(i, 524288)) {
                sb.append(" CAPABILITY_CAN_UPGRADE_TO_VIDEO");
            }
            if (can(i, 1048576)) {
                sb.append(" CAPABILITY_CAN_PAUSE_VIDEO");
            }
            if (can(i, 8388608)) {
                sb.append(" CAPABILITY_CAN_PULL_CALL");
            }
            if (can(i, 16777216)) {
                sb.append(" CAPABILITY_SUPPORT_DEFLECT");
            }
            if (can(i, 33554432)) {
                sb.append(" CAPABILITY_ADD_PARTICIPANT");
            }
            if (can(i, 67108864)) {
                sb.append(" CAPABILITY_TRANSFER");
            }
            if (can(i, 134217728)) {
                sb.append(" CAPABILITY_TRANSFER_CONSULTATIVE");
            }
            if (can(i, 268435456)) {
                sb.append(" CAPABILITY_REMOTE_PARTY_SUPPORTS_RTT");
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            return sb.toString();
        }

        public static boolean hasProperty(int i, int i2) {
            return (i & i2) == i2;
        }

        public boolean hasProperty(int i) {
            return hasProperty(this.mCallProperties, i);
        }

        public static java.lang.String propertiesToString(int i) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("[Properties:");
            if (hasProperty(i, 1)) {
                sb.append(" PROPERTY_CONFERENCE");
            }
            if (hasProperty(i, 2)) {
                sb.append(" PROPERTY_GENERIC_CONFERENCE");
            }
            if (hasProperty(i, 8)) {
                sb.append(" PROPERTY_WIFI");
            }
            if (hasProperty(i, 16)) {
                sb.append(" PROPERTY_HIGH_DEF_AUDIO");
            }
            if (hasProperty(i, 4)) {
                sb.append(" PROPERTY_EMERGENCY_CALLBACK_MODE");
            }
            if (hasProperty(i, 64)) {
                sb.append(" PROPERTY_IS_EXTERNAL_CALL");
            }
            if (hasProperty(i, 128)) {
                sb.append(" PROPERTY_HAS_CDMA_VOICE_PRIVACY");
            }
            if (hasProperty(i, 512)) {
                sb.append(" PROPERTY_ASSISTED_DIALING_USED");
            }
            if (hasProperty(i, 2048)) {
                sb.append(" PROPERTY_NETWORK_IDENTIFIED_EMERGENCY_CALL");
            }
            if (hasProperty(i, 1024)) {
                sb.append(" PROPERTY_RTT");
            }
            if (hasProperty(i, 4096)) {
                sb.append(" PROPERTY_VOIP_AUDIO_MODE");
            }
            if (hasProperty(i, 8192)) {
                sb.append(" PROPERTY_IS_ADHOC_CONFERENCE");
            }
            if (hasProperty(i, 16384)) {
                sb.append(" PROPERTY_CROSS_SIM");
            }
            if (hasProperty(i, 32768)) {
                sb.append(" PROPERTY_IS_TRANSACTIONAL");
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            return sb.toString();
        }

        public final int getState() {
            return this.mState;
        }

        public java.lang.String getId() {
            return this.mTelecomCallId;
        }

        public java.lang.String getTelecomCallId() {
            return this.mTelecomCallId;
        }

        public android.net.Uri getHandle() {
            return this.mHandle;
        }

        public int getHandlePresentation() {
            return this.mHandlePresentation;
        }

        public android.net.Uri getContactPhotoUri() {
            return this.mContactPhotoUri;
        }

        public java.lang.String getCallerDisplayName() {
            return this.mCallerDisplayName;
        }

        public int getCallerDisplayNamePresentation() {
            return this.mCallerDisplayNamePresentation;
        }

        public android.telecom.PhoneAccountHandle getAccountHandle() {
            return this.mAccountHandle;
        }

        public int getCallCapabilities() {
            return this.mCallCapabilities;
        }

        public int getCallProperties() {
            return this.mCallProperties;
        }

        public int getSupportedAudioRoutes() {
            return 31;
        }

        public android.telecom.DisconnectCause getDisconnectCause() {
            return this.mDisconnectCause;
        }

        public final long getConnectTimeMillis() {
            return this.mConnectTimeMillis;
        }

        public android.telecom.GatewayInfo getGatewayInfo() {
            return this.mGatewayInfo;
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

        public android.os.Bundle getIntentExtras() {
            return this.mIntentExtras;
        }

        public long getCreationTimeMillis() {
            return this.mCreationTimeMillis;
        }

        public java.lang.String getContactDisplayName() {
            return this.mContactDisplayName;
        }

        public int getCallDirection() {
            return this.mCallDirection;
        }

        public int getCallerNumberVerificationStatus() {
            return this.mCallerNumberVerificationStatus;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.telecom.Call.Details)) {
                return false;
            }
            android.telecom.Call.Details details = (android.telecom.Call.Details) obj;
            return java.util.Objects.equals(java.lang.Integer.valueOf(this.mState), java.lang.Integer.valueOf(details.mState)) && java.util.Objects.equals(this.mHandle, details.mHandle) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mHandlePresentation), java.lang.Integer.valueOf(details.mHandlePresentation)) && java.util.Objects.equals(this.mCallerDisplayName, details.mCallerDisplayName) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mCallerDisplayNamePresentation), java.lang.Integer.valueOf(details.mCallerDisplayNamePresentation)) && java.util.Objects.equals(this.mAccountHandle, details.mAccountHandle) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mCallCapabilities), java.lang.Integer.valueOf(details.mCallCapabilities)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mCallProperties), java.lang.Integer.valueOf(details.mCallProperties)) && java.util.Objects.equals(this.mDisconnectCause, details.mDisconnectCause) && java.util.Objects.equals(java.lang.Long.valueOf(this.mConnectTimeMillis), java.lang.Long.valueOf(details.mConnectTimeMillis)) && java.util.Objects.equals(this.mGatewayInfo, details.mGatewayInfo) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mVideoState), java.lang.Integer.valueOf(details.mVideoState)) && java.util.Objects.equals(this.mStatusHints, details.mStatusHints) && android.telecom.Call.areBundlesEqual(this.mExtras, details.mExtras) && android.telecom.Call.areBundlesEqual(this.mIntentExtras, details.mIntentExtras) && java.util.Objects.equals(java.lang.Long.valueOf(this.mCreationTimeMillis), java.lang.Long.valueOf(details.mCreationTimeMillis)) && java.util.Objects.equals(this.mContactDisplayName, details.mContactDisplayName) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mCallDirection), java.lang.Integer.valueOf(details.mCallDirection)) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mCallerNumberVerificationStatus), java.lang.Integer.valueOf(details.mCallerNumberVerificationStatus)) && java.util.Objects.equals(this.mContactPhotoUri, details.mContactPhotoUri);
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mState), this.mHandle, java.lang.Integer.valueOf(this.mHandlePresentation), this.mCallerDisplayName, java.lang.Integer.valueOf(this.mCallerDisplayNamePresentation), this.mAccountHandle, java.lang.Integer.valueOf(this.mCallCapabilities), java.lang.Integer.valueOf(this.mCallProperties), this.mDisconnectCause, java.lang.Long.valueOf(this.mConnectTimeMillis), this.mGatewayInfo, java.lang.Integer.valueOf(this.mVideoState), this.mStatusHints, this.mExtras, this.mIntentExtras, java.lang.Long.valueOf(this.mCreationTimeMillis), this.mContactDisplayName, java.lang.Integer.valueOf(this.mCallDirection), java.lang.Integer.valueOf(this.mCallerNumberVerificationStatus), this.mContactPhotoUri);
        }

        public Details(int i, java.lang.String str, android.net.Uri uri, int i2, java.lang.String str2, int i3, android.telecom.PhoneAccountHandle phoneAccountHandle, int i4, int i5, android.telecom.DisconnectCause disconnectCause, long j, android.telecom.GatewayInfo gatewayInfo, int i6, android.telecom.StatusHints statusHints, android.os.Bundle bundle, android.os.Bundle bundle2, long j2, java.lang.String str3, int i7, int i8, android.net.Uri uri2) {
            this.mState = i;
            this.mTelecomCallId = str;
            this.mHandle = uri;
            this.mHandlePresentation = i2;
            this.mCallerDisplayName = str2;
            this.mCallerDisplayNamePresentation = i3;
            this.mAccountHandle = phoneAccountHandle;
            this.mCallCapabilities = i4;
            this.mCallProperties = i5;
            this.mDisconnectCause = disconnectCause;
            this.mConnectTimeMillis = j;
            this.mGatewayInfo = gatewayInfo;
            this.mVideoState = i6;
            this.mStatusHints = statusHints;
            this.mExtras = bundle;
            this.mIntentExtras = bundle2;
            this.mCreationTimeMillis = j2;
            this.mContactDisplayName = str3;
            this.mCallDirection = i7;
            this.mCallerNumberVerificationStatus = i8;
            this.mContactPhotoUri = uri2;
        }

        public static android.telecom.Call.Details createFromParcelableCall(android.telecom.ParcelableCall parcelableCall) {
            return new android.telecom.Call.Details(parcelableCall.getState(), parcelableCall.getId(), parcelableCall.getHandle(), parcelableCall.getHandlePresentation(), parcelableCall.getCallerDisplayName(), parcelableCall.getCallerDisplayNamePresentation(), parcelableCall.getAccountHandle(), parcelableCall.getCapabilities(), parcelableCall.getProperties(), parcelableCall.getDisconnectCause(), parcelableCall.getConnectTimeMillis(), parcelableCall.getGatewayInfo(), parcelableCall.getVideoState(), parcelableCall.getStatusHints(), parcelableCall.getExtras(), parcelableCall.getIntentExtras(), parcelableCall.getCreationTimeMillis(), parcelableCall.getContactDisplayName(), parcelableCall.getCallDirection(), parcelableCall.getCallerNumberVerificationStatus(), parcelableCall.getContactPhotoUri());
        }

        public java.lang.String toString() {
            return "[id: " + this.mTelecomCallId + ", state: " + android.telecom.Call.stateToString(this.mState) + ", pa: " + this.mAccountHandle + ", hdl: " + android.telecom.Log.piiHandle(this.mHandle) + ", hdlPres: " + this.mHandlePresentation + ", videoState: " + android.telecom.VideoProfile.videoStateToString(this.mVideoState) + ", caps: " + capabilitiesToString(this.mCallCapabilities) + ", props: " + propertiesToString(this.mCallProperties) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static abstract class Callback {
        public static final int HANDOVER_FAILURE_DEST_APP_REJECTED = 1;
        public static final int HANDOVER_FAILURE_NOT_SUPPORTED = 2;
        public static final int HANDOVER_FAILURE_ONGOING_EMERGENCY_CALL = 4;
        public static final int HANDOVER_FAILURE_UNKNOWN = 5;
        public static final int HANDOVER_FAILURE_USER_REJECTED = 3;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface HandoverFailureErrors {
        }

        public void onStateChanged(android.telecom.Call call, int i) {
        }

        public void onParentChanged(android.telecom.Call call, android.telecom.Call call2) {
        }

        public void onChildrenChanged(android.telecom.Call call, java.util.List<android.telecom.Call> list) {
        }

        public void onDetailsChanged(android.telecom.Call call, android.telecom.Call.Details details) {
        }

        public void onCannedTextResponsesLoaded(android.telecom.Call call, java.util.List<java.lang.String> list) {
        }

        public void onPostDialWait(android.telecom.Call call, java.lang.String str) {
        }

        public void onVideoCallChanged(android.telecom.Call call, android.telecom.InCallService.VideoCall videoCall) {
        }

        public void onCallDestroyed(android.telecom.Call call) {
        }

        public void onConferenceableCallsChanged(android.telecom.Call call, java.util.List<android.telecom.Call> list) {
        }

        public void onConnectionEvent(android.telecom.Call call, java.lang.String str, android.os.Bundle bundle) {
        }

        public void onRttModeChanged(android.telecom.Call call, int i) {
        }

        public void onRttStatusChanged(android.telecom.Call call, boolean z, android.telecom.Call.RttCall rttCall) {
        }

        public void onRttRequest(android.telecom.Call call, int i) {
        }

        public void onRttInitiationFailure(android.telecom.Call call, int i) {
        }

        public void onHandoverComplete(android.telecom.Call call) {
        }

        public void onHandoverFailed(android.telecom.Call call, int i) {
        }
    }

    public static final class RttCall {
        private static final int READ_BUFFER_SIZE = 1000;
        public static final int RTT_MODE_FULL = 1;
        public static final int RTT_MODE_HCO = 2;
        public static final int RTT_MODE_INVALID = 0;
        public static final int RTT_MODE_VCO = 3;
        private final android.telecom.InCallAdapter mInCallAdapter;
        private char[] mReadBuffer = new char[1000];
        private java.io.InputStreamReader mReceiveStream;
        private int mRttMode;
        private final java.lang.String mTelecomCallId;
        private java.io.OutputStreamWriter mTransmitStream;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface RttAudioMode {
        }

        public RttCall(java.lang.String str, java.io.InputStreamReader inputStreamReader, java.io.OutputStreamWriter outputStreamWriter, int i, android.telecom.InCallAdapter inCallAdapter) {
            this.mTelecomCallId = str;
            this.mReceiveStream = inputStreamReader;
            this.mTransmitStream = outputStreamWriter;
            this.mRttMode = i;
            this.mInCallAdapter = inCallAdapter;
        }

        public int getRttAudioMode() {
            return this.mRttMode;
        }

        public void setRttMode(int i) {
            this.mInCallAdapter.setRttMode(this.mTelecomCallId, i);
        }

        public void write(java.lang.String str) throws java.io.IOException {
            this.mTransmitStream.write(str);
            this.mTransmitStream.flush();
        }

        public java.lang.String read() {
            try {
                int read = this.mReceiveStream.read(this.mReadBuffer, 0, 1000);
                if (read < 0) {
                    return null;
                }
                return new java.lang.String(this.mReadBuffer, 0, read);
            } catch (java.io.IOException e) {
                android.telecom.Log.w(this, "Exception encountered when reading from InputStreamReader: %s", e);
                return null;
            }
        }

        public java.lang.String readImmediately() throws java.io.IOException {
            int read;
            if (!this.mReceiveStream.ready() || (read = this.mReceiveStream.read(this.mReadBuffer, 0, 1000)) < 0) {
                return null;
            }
            return new java.lang.String(this.mReadBuffer, 0, read);
        }

        public void close() {
            try {
                this.mReceiveStream.close();
            } catch (java.io.IOException e) {
            }
            try {
                this.mTransmitStream.close();
            } catch (java.io.IOException e2) {
            }
        }
    }

    public java.lang.String getRemainingPostDialSequence() {
        return this.mRemainingPostDialSequence;
    }

    public void answer(int i) {
        this.mInCallAdapter.answerCall(this.mTelecomCallId, i);
    }

    public void deflect(android.net.Uri uri) {
        this.mInCallAdapter.deflectCall(this.mTelecomCallId, uri);
    }

    public void reject(boolean z, java.lang.String str) {
        this.mInCallAdapter.rejectCall(this.mTelecomCallId, z, str);
    }

    public void reject(int i) {
        this.mInCallAdapter.rejectCall(this.mTelecomCallId, i);
    }

    public void transfer(android.net.Uri uri, boolean z) {
        this.mInCallAdapter.transferCall(this.mTelecomCallId, uri, z);
    }

    public void transfer(android.telecom.Call call) {
        this.mInCallAdapter.transferCall(this.mTelecomCallId, call.mTelecomCallId);
    }

    public void disconnect() {
        this.mInCallAdapter.disconnectCall(this.mTelecomCallId);
    }

    public void hold() {
        this.mInCallAdapter.holdCall(this.mTelecomCallId);
    }

    public void unhold() {
        this.mInCallAdapter.unholdCall(this.mTelecomCallId);
    }

    @android.annotation.SystemApi
    public void enterBackgroundAudioProcessing() {
        if (this.mState != 4 && this.mState != 2) {
            throw new java.lang.IllegalStateException("Call must be active or ringing");
        }
        this.mInCallAdapter.enterBackgroundAudioProcessing(this.mTelecomCallId);
    }

    @android.annotation.SystemApi
    public void exitBackgroundAudioProcessing(boolean z) {
        if (this.mState != 12) {
            throw new java.lang.IllegalStateException("Call must in the audio processing state");
        }
        this.mInCallAdapter.exitBackgroundAudioProcessing(this.mTelecomCallId, z);
    }

    public void playDtmfTone(char c) {
        this.mInCallAdapter.playDtmfTone(this.mTelecomCallId, c);
    }

    public void stopDtmfTone() {
        this.mInCallAdapter.stopDtmfTone(this.mTelecomCallId);
    }

    public void postDialContinue(boolean z) {
        this.mInCallAdapter.postDialContinue(this.mTelecomCallId, z);
    }

    public void phoneAccountSelected(android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) {
        this.mInCallAdapter.phoneAccountSelected(this.mTelecomCallId, phoneAccountHandle, z);
    }

    public void conference(android.telecom.Call call) {
        if (call != null) {
            this.mInCallAdapter.conference(this.mTelecomCallId, call.mTelecomCallId);
        }
    }

    public void splitFromConference() {
        this.mInCallAdapter.splitFromConference(this.mTelecomCallId);
    }

    public void mergeConference() {
        this.mInCallAdapter.mergeConference(this.mTelecomCallId);
    }

    public void swapConference() {
        this.mInCallAdapter.swapConference(this.mTelecomCallId);
    }

    public void addConferenceParticipants(java.util.List<android.net.Uri> list) {
        this.mInCallAdapter.addConferenceParticipants(this.mTelecomCallId, list);
    }

    public void pullExternalCall() {
        if (!this.mDetails.hasProperty(64)) {
            return;
        }
        this.mInCallAdapter.pullExternalCall(this.mTelecomCallId);
    }

    public void sendCallEvent(java.lang.String str, android.os.Bundle bundle) {
        this.mInCallAdapter.sendCallEvent(this.mTelecomCallId, str, this.mTargetSdkVersion, bundle);
    }

    public void sendRttRequest() {
        this.mInCallAdapter.sendRttRequest(this.mTelecomCallId);
    }

    public void respondToRttRequest(int i, boolean z) {
        this.mInCallAdapter.respondToRttRequest(this.mTelecomCallId, i, z);
    }

    public void handoverTo(android.telecom.PhoneAccountHandle phoneAccountHandle, int i, android.os.Bundle bundle) {
        this.mInCallAdapter.handoverTo(this.mTelecomCallId, phoneAccountHandle, i, bundle);
    }

    public void stopRtt() {
        this.mInCallAdapter.stopRtt(this.mTelecomCallId);
    }

    public final void putExtras(android.os.Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putAll(bundle);
        this.mInCallAdapter.putExtras(this.mTelecomCallId, bundle);
    }

    public final void putExtra(java.lang.String str, boolean z) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putBoolean(str, z);
        this.mInCallAdapter.putExtra(this.mTelecomCallId, str, z);
    }

    public final void putExtra(java.lang.String str, int i) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putInt(str, i);
        this.mInCallAdapter.putExtra(this.mTelecomCallId, str, i);
    }

    public final void putExtra(java.lang.String str, java.lang.String str2) {
        if (this.mExtras == null) {
            this.mExtras = new android.os.Bundle();
        }
        this.mExtras.putString(str, str2);
        this.mInCallAdapter.putExtra(this.mTelecomCallId, str, str2);
    }

    public final void removeExtras(java.util.List<java.lang.String> list) {
        if (this.mExtras != null) {
            java.util.Iterator<java.lang.String> it = list.iterator();
            while (it.hasNext()) {
                this.mExtras.remove(it.next());
            }
            if (this.mExtras.size() == 0) {
                this.mExtras = null;
            }
        }
        this.mInCallAdapter.removeExtras(this.mTelecomCallId, list);
    }

    public final void removeExtras(java.lang.String... strArr) {
        removeExtras(java.util.Arrays.asList(strArr));
    }

    public android.telecom.Call getParent() {
        if (this.mParentId != null) {
            return this.mPhone.internalGetCallByTelecomId(this.mParentId);
        }
        return null;
    }

    public java.util.List<android.telecom.Call> getChildren() {
        if (!this.mChildrenCached) {
            this.mChildrenCached = true;
            this.mChildren.clear();
            java.util.Iterator<java.lang.String> it = this.mChildrenIds.iterator();
            while (it.hasNext()) {
                android.telecom.Call internalGetCallByTelecomId = this.mPhone.internalGetCallByTelecomId(it.next());
                if (internalGetCallByTelecomId == null) {
                    this.mChildrenCached = false;
                } else {
                    this.mChildren.add(internalGetCallByTelecomId);
                }
            }
        }
        return this.mUnmodifiableChildren;
    }

    public java.util.List<android.telecom.Call> getConferenceableCalls() {
        return this.mUnmodifiableConferenceableCalls;
    }

    @java.lang.Deprecated
    public int getState() {
        return this.mState;
    }

    public android.telecom.Call getGenericConferenceActiveChildCall() {
        if (this.mActiveGenericConferenceChild != null) {
            return this.mPhone.internalGetCallByTelecomId(this.mActiveGenericConferenceChild);
        }
        return null;
    }

    public java.util.List<java.lang.String> getCannedTextResponses() {
        return this.mCannedTextResponses;
    }

    public android.telecom.InCallService.VideoCall getVideoCall() {
        return this.mVideoCallImpl;
    }

    public android.telecom.Call.Details getDetails() {
        return this.mDetails;
    }

    public android.telecom.Call.RttCall getRttCall() {
        return this.mRttCall;
    }

    public boolean isRttActive() {
        return this.mRttCall != null && this.mDetails.hasProperty(1024);
    }

    public void registerCallback(android.telecom.Call.Callback callback) {
        registerCallback(callback, new android.os.Handler());
    }

    public void registerCallback(android.telecom.Call.Callback callback, android.os.Handler handler) {
        unregisterCallback(callback);
        if (callback != null && handler != null && this.mState != 7) {
            this.mCallbackRecords.add(new android.telecom.CallbackRecord<>(callback, handler));
        }
    }

    public void unregisterCallback(android.telecom.Call.Callback callback) {
        if (callback != null && this.mState != 7) {
            for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
                if (callbackRecord.getCallback() == callback) {
                    this.mCallbackRecords.remove(callbackRecord);
                    return;
                }
            }
        }
    }

    public java.lang.String toString() {
        return "Call [id: " + this.mTelecomCallId + ", state: " + stateToString(this.mState) + ", details: " + this.mDetails + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "NEW";
            case 1:
                return "DIALING";
            case 2:
                return "RINGING";
            case 3:
                return "HOLDING";
            case 4:
                return "ACTIVE";
            case 5:
            case 6:
            case 11:
            default:
                android.telecom.Log.w(android.telecom.Call.class, "Unknown state %d", java.lang.Integer.valueOf(i));
                return "UNKNOWN";
            case 7:
                return "DISCONNECTED";
            case 8:
                return "SELECT_PHONE_ACCOUNT";
            case 9:
                return "CONNECTING";
            case 10:
                return "DISCONNECTING";
            case 12:
                return "AUDIO_PROCESSING";
            case 13:
                return "SIMULATED_RINGING";
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void addListener(android.telecom.Call.Listener listener) {
        registerCallback(listener);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void removeListener(android.telecom.Call.Listener listener) {
        unregisterCallback(listener);
    }

    Call(android.telecom.Phone phone, java.lang.String str, android.telecom.InCallAdapter inCallAdapter, java.lang.String str2, int i) {
        this.mChildrenIds = new java.util.ArrayList();
        this.mChildren = new java.util.ArrayList();
        this.mUnmodifiableChildren = java.util.Collections.unmodifiableList(this.mChildren);
        this.mCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList();
        this.mConferenceableCalls = new java.util.ArrayList();
        this.mUnmodifiableConferenceableCalls = java.util.Collections.unmodifiableList(this.mConferenceableCalls);
        this.mParentId = null;
        this.mActiveGenericConferenceChild = null;
        this.mCannedTextResponses = null;
        this.mPhone = phone;
        this.mTelecomCallId = str;
        this.mInCallAdapter = inCallAdapter;
        this.mState = 0;
        this.mCallingPackage = str2;
        this.mTargetSdkVersion = i;
    }

    Call(android.telecom.Phone phone, java.lang.String str, android.telecom.InCallAdapter inCallAdapter, int i, java.lang.String str2, int i2) {
        this.mChildrenIds = new java.util.ArrayList();
        this.mChildren = new java.util.ArrayList();
        this.mUnmodifiableChildren = java.util.Collections.unmodifiableList(this.mChildren);
        this.mCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList();
        this.mConferenceableCalls = new java.util.ArrayList();
        this.mUnmodifiableConferenceableCalls = java.util.Collections.unmodifiableList(this.mConferenceableCalls);
        this.mParentId = null;
        this.mActiveGenericConferenceChild = null;
        this.mCannedTextResponses = null;
        this.mPhone = phone;
        this.mTelecomCallId = str;
        this.mInCallAdapter = inCallAdapter;
        this.mState = i;
        this.mCallingPackage = str2;
        this.mTargetSdkVersion = i2;
    }

    final java.lang.String internalGetCallId() {
        return this.mTelecomCallId;
    }

    final void internalUpdate(android.telecom.ParcelableCall parcelableCall, java.util.Map<java.lang.String, android.telecom.Call> map) {
        boolean z;
        boolean z2;
        boolean z3;
        android.telecom.Call.Details createFromParcelableCall = android.telecom.Call.Details.createFromParcelableCall(parcelableCall);
        boolean z4 = !java.util.Objects.equals(this.mDetails, createFromParcelableCall);
        if (z4) {
            this.mDetails = createFromParcelableCall;
        }
        if (this.mCannedTextResponses == null && parcelableCall.getCannedSmsResponses() != null && !parcelableCall.getCannedSmsResponses().isEmpty()) {
            this.mCannedTextResponses = java.util.Collections.unmodifiableList(parcelableCall.getCannedSmsResponses());
            z = true;
        } else {
            z = false;
        }
        boolean z5 = parcelableCall.isVideoCallProviderChanged() && !java.util.Objects.equals(this.mVideoCallImpl == null ? null : this.mVideoCallImpl.getVideoProvider(), parcelableCall.getVideoProvider());
        if (z5) {
            if (this.mVideoCallImpl != null) {
                this.mVideoCallImpl.destroy();
            }
            this.mVideoCallImpl = parcelableCall.isVideoCallProviderChanged() ? parcelableCall.getVideoCallImpl(this.mCallingPackage, this.mTargetSdkVersion) : null;
        }
        if (this.mVideoCallImpl != null) {
            this.mVideoCallImpl.setVideoState(getDetails().getVideoState());
        }
        int state = parcelableCall.getState();
        if (this.mTargetSdkVersion < 30 && state == 13) {
            state = 2;
        }
        boolean z6 = this.mState != state;
        if (z6) {
            this.mState = state;
        }
        java.lang.String parentCallId = parcelableCall.getParentCallId();
        boolean z7 = !java.util.Objects.equals(this.mParentId, parentCallId);
        if (z7) {
            this.mParentId = parentCallId;
        }
        boolean z8 = !java.util.Objects.equals(parcelableCall.getChildCallIds(), this.mChildrenIds);
        if (z8) {
            this.mChildrenIds.clear();
            this.mChildrenIds.addAll(parcelableCall.getChildCallIds());
            this.mChildrenCached = false;
        }
        java.lang.String activeChildCallId = parcelableCall.getActiveChildCallId();
        boolean z9 = !java.util.Objects.equals(activeChildCallId, this.mActiveGenericConferenceChild);
        if (z9) {
            this.mActiveGenericConferenceChild = activeChildCallId;
        }
        java.util.List<java.lang.String> conferenceableCallIds = parcelableCall.getConferenceableCallIds();
        java.util.ArrayList arrayList = new java.util.ArrayList(conferenceableCallIds.size());
        for (java.lang.String str : conferenceableCallIds) {
            if (map.containsKey(str)) {
                arrayList.add(map.get(str));
            }
        }
        if (!java.util.Objects.equals(this.mConferenceableCalls, arrayList)) {
            this.mConferenceableCalls.clear();
            this.mConferenceableCalls.addAll(arrayList);
            fireConferenceableCallsChanged();
        }
        if (!parcelableCall.getIsRttCallChanged() || !this.mDetails.hasProperty(1024)) {
            if (this.mRttCall != null && parcelableCall.getParcelableRttCall() == null && parcelableCall.getIsRttCallChanged()) {
                this.mRttCall.close();
                this.mRttCall = null;
                z2 = true;
                z3 = false;
            } else {
                z2 = false;
                z3 = false;
            }
        } else {
            android.telecom.ParcelableRttCall parcelableRttCall = parcelableCall.getParcelableRttCall();
            android.telecom.Call.RttCall rttCall = new android.telecom.Call.RttCall(this.mTelecomCallId, new java.io.InputStreamReader(new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelableRttCall.getReceiveStream()), java.nio.charset.StandardCharsets.UTF_8), new java.io.OutputStreamWriter(new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelableRttCall.getTransmitStream()), java.nio.charset.StandardCharsets.UTF_8), parcelableRttCall.getRttMode(), this.mInCallAdapter);
            if (this.mRttCall == null) {
                z2 = true;
                z3 = false;
            } else if (this.mRttCall.getRttAudioMode() == rttCall.getRttAudioMode()) {
                z2 = false;
                z3 = false;
            } else {
                z3 = true;
                z2 = false;
            }
            this.mRttCall = rttCall;
        }
        if (z6) {
            fireStateChanged(this.mState);
        }
        if (z4) {
            fireDetailsChanged(this.mDetails);
        }
        if (z) {
            fireCannedTextResponsesLoaded(this.mCannedTextResponses);
        }
        if (z5) {
            fireVideoCallChanged(this.mVideoCallImpl);
        }
        if (z7) {
            fireParentChanged(getParent());
        }
        if (z8 || z9) {
            fireChildrenChanged(getChildren());
        }
        if (z2) {
            fireOnIsRttChanged(this.mRttCall != null, this.mRttCall);
        }
        if (z3) {
            fireOnRttModeChanged(this.mRttCall.getRttAudioMode());
        }
        if (this.mState == 7 && z6) {
            fireCallDestroyed();
        }
    }

    final void internalSetPostDialWait(java.lang.String str) {
        this.mRemainingPostDialSequence = str;
        firePostDialWait(this.mRemainingPostDialSequence);
    }

    final void internalSetDisconnected() {
        if (this.mState != 7) {
            this.mState = 7;
            if (this.mDetails != null) {
                this.mDetails = new android.telecom.Call.Details(this.mState, this.mDetails.getTelecomCallId(), this.mDetails.getHandle(), this.mDetails.getHandlePresentation(), this.mDetails.getCallerDisplayName(), this.mDetails.getCallerDisplayNamePresentation(), this.mDetails.getAccountHandle(), this.mDetails.getCallCapabilities(), this.mDetails.getCallProperties(), this.mDetails.getDisconnectCause(), this.mDetails.getConnectTimeMillis(), this.mDetails.getGatewayInfo(), this.mDetails.getVideoState(), this.mDetails.getStatusHints(), this.mDetails.getExtras(), this.mDetails.getIntentExtras(), this.mDetails.getCreationTimeMillis(), this.mDetails.getContactDisplayName(), this.mDetails.getCallDirection(), this.mDetails.getCallerNumberVerificationStatus(), this.mDetails.getContactPhotoUri());
                fireDetailsChanged(this.mDetails);
            }
            fireStateChanged(this.mState);
            fireCallDestroyed();
        }
    }

    final void internalOnConnectionEvent(java.lang.String str, android.os.Bundle bundle) {
        fireOnConnectionEvent(str, bundle);
    }

    final void internalOnRttUpgradeRequest(final int i) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.Call.Callback.this.onRttRequest(this, i);
                }
            });
        }
    }

    final void internalOnRttInitiationFailure(final int i) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.Call.Callback.this.onRttInitiationFailure(this, i);
                }
            });
        }
    }

    final void internalOnHandoverFailed(final int i) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.Call.Callback.this.onHandoverFailed(this, i);
                }
            });
        }
    }

    final void internalOnHandoverComplete() {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.Call.Callback.this.onHandoverComplete(this);
                }
            });
        }
    }

    private void fireStateChanged(final int i) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call.1
                @Override // java.lang.Runnable
                public void run() {
                    callback.onStateChanged(this, i);
                }
            });
        }
    }

    private void fireParentChanged(final android.telecom.Call call) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call.2
                @Override // java.lang.Runnable
                public void run() {
                    callback.onParentChanged(this, call);
                }
            });
        }
    }

    private void fireChildrenChanged(final java.util.List<android.telecom.Call> list) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call.3
                @Override // java.lang.Runnable
                public void run() {
                    callback.onChildrenChanged(this, list);
                }
            });
        }
    }

    private void fireDetailsChanged(final android.telecom.Call.Details details) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call.4
                @Override // java.lang.Runnable
                public void run() {
                    callback.onDetailsChanged(this, details);
                }
            });
        }
    }

    private void fireCannedTextResponsesLoaded(final java.util.List<java.lang.String> list) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call.5
                @Override // java.lang.Runnable
                public void run() {
                    callback.onCannedTextResponsesLoaded(this, list);
                }
            });
        }
    }

    private void fireVideoCallChanged(final android.telecom.InCallService.VideoCall videoCall) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call.6
                @Override // java.lang.Runnable
                public void run() {
                    callback.onVideoCallChanged(this, videoCall);
                }
            });
        }
    }

    private void firePostDialWait(final java.lang.String str) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call.7
                @Override // java.lang.Runnable
                public void run() {
                    callback.onPostDialWait(this, str);
                }
            });
        }
    }

    private void fireCallDestroyed() {
        if (this.mCallbackRecords.isEmpty()) {
            this.mPhone.internalRemoveCall(this);
        }
        for (final android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call.8
                @Override // java.lang.Runnable
                public void run() {
                    boolean z;
                    try {
                        callback.onCallDestroyed(this);
                        e = null;
                    } catch (java.lang.RuntimeException e) {
                        e = e;
                    }
                    synchronized (android.telecom.Call.this) {
                        android.telecom.Call.this.mCallbackRecords.remove(callbackRecord);
                        if (!android.telecom.Call.this.mCallbackRecords.isEmpty()) {
                            z = false;
                        } else {
                            z = true;
                        }
                    }
                    if (z) {
                        android.telecom.Call.this.mPhone.internalRemoveCall(this);
                    }
                    if (e != null) {
                        throw e;
                    }
                }
            });
        }
    }

    private void fireConferenceableCallsChanged() {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call.9
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConferenceableCallsChanged(this, android.telecom.Call.this.mUnmodifiableConferenceableCalls);
                }
            });
        }
    }

    private void fireOnConnectionEvent(final java.lang.String str, final android.os.Bundle bundle) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call.10
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConnectionEvent(this, str, bundle);
                }
            });
        }
    }

    private void fireOnIsRttChanged(final boolean z, final android.telecom.Call.RttCall rttCall) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.Call.Callback.this.onRttStatusChanged(this, z, rttCall);
                }
            });
        }
    }

    private void fireOnRttModeChanged(final int i) {
        for (android.telecom.CallbackRecord<android.telecom.Call.Callback> callbackRecord : this.mCallbackRecords) {
            final android.telecom.Call.Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new java.lang.Runnable() { // from class: android.telecom.Call$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.telecom.Call.Callback.this.onRttModeChanged(this, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean areBundlesEqual(android.os.Bundle bundle, android.os.Bundle bundle2) {
        if (bundle == null || bundle2 == null) {
            return bundle == bundle2;
        }
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (java.lang.String str : bundle.keySet()) {
            if (str != null) {
                if (!bundle2.containsKey(str)) {
                    return false;
                }
                try {
                    java.lang.Object obj = bundle.get(str);
                    java.lang.Object obj2 = bundle2.get(str);
                    if ((obj instanceof android.os.Bundle) && (obj2 instanceof android.os.Bundle) && !areBundlesEqual((android.os.Bundle) obj, (android.os.Bundle) obj2)) {
                        return false;
                    }
                    if ((obj instanceof byte[]) && (obj2 instanceof byte[])) {
                        if (!java.util.Arrays.equals((byte[]) obj, (byte[]) obj2)) {
                            return false;
                        }
                    } else if (!java.util.Objects.equals(obj, obj2)) {
                        return false;
                    }
                } catch (android.os.BadParcelableException e) {
                    return false;
                }
            }
        }
        return true;
    }
}
