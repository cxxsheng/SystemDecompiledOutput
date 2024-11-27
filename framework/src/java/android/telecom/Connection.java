package android.telecom;

/* loaded from: classes3.dex */
public abstract class Connection extends android.telecom.Conferenceable {
    public static final int AUDIO_CODEC_AMR = 1;
    public static final int AUDIO_CODEC_AMR_WB = 2;
    public static final int AUDIO_CODEC_EVRC = 4;
    public static final int AUDIO_CODEC_EVRC_B = 5;
    public static final int AUDIO_CODEC_EVRC_NW = 7;
    public static final int AUDIO_CODEC_EVRC_WB = 6;
    public static final int AUDIO_CODEC_EVS_FB = 20;
    public static final int AUDIO_CODEC_EVS_NB = 17;
    public static final int AUDIO_CODEC_EVS_SWB = 19;
    public static final int AUDIO_CODEC_EVS_WB = 18;
    public static final int AUDIO_CODEC_G711A = 13;
    public static final int AUDIO_CODEC_G711AB = 15;
    public static final int AUDIO_CODEC_G711U = 11;
    public static final int AUDIO_CODEC_G722 = 14;
    public static final int AUDIO_CODEC_G723 = 12;
    public static final int AUDIO_CODEC_G729 = 16;
    public static final int AUDIO_CODEC_GSM_EFR = 8;
    public static final int AUDIO_CODEC_GSM_FR = 9;
    public static final int AUDIO_CODEC_GSM_HR = 10;
    public static final int AUDIO_CODEC_NONE = 0;
    public static final int AUDIO_CODEC_QCELP13K = 3;
    public static final int CAPABILITY_ADD_PARTICIPANT = 67108864;
    public static final int CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO = 8388608;
    public static final int CAPABILITY_CAN_PAUSE_VIDEO = 1048576;
    public static final int CAPABILITY_CAN_PULL_CALL = 16777216;
    public static final int CAPABILITY_CAN_SEND_RESPONSE_VIA_CONNECTION = 4194304;
    public static final int CAPABILITY_CAN_UPGRADE_TO_VIDEO = 524288;

    @android.annotation.SystemApi
    public static final int CAPABILITY_CONFERENCE_HAS_NO_CHILDREN = 2097152;
    public static final int CAPABILITY_DISCONNECT_FROM_CONFERENCE = 8192;
    public static final int CAPABILITY_HOLD = 1;
    public static final int CAPABILITY_MANAGE_CONFERENCE = 128;
    public static final int CAPABILITY_MERGE_CONFERENCE = 4;
    public static final int CAPABILITY_MUTE = 64;
    public static final int CAPABILITY_REMOTE_PARTY_SUPPORTS_RTT = 536870912;
    public static final int CAPABILITY_RESPOND_VIA_TEXT = 32;
    public static final int CAPABILITY_SEPARATE_FROM_CONFERENCE = 4096;

    @android.annotation.SystemApi
    public static final int CAPABILITY_SPEED_UP_MT_AUDIO = 262144;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL = 768;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_RX = 256;
    public static final int CAPABILITY_SUPPORTS_VT_LOCAL_TX = 512;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL = 3072;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_RX = 1024;
    public static final int CAPABILITY_SUPPORTS_VT_REMOTE_TX = 2048;
    public static final int CAPABILITY_SUPPORT_DEFLECT = 33554432;
    public static final int CAPABILITY_SUPPORT_HOLD = 2;
    public static final int CAPABILITY_SWAP_CONFERENCE = 8;
    public static final int CAPABILITY_TRANSFER = 134217728;
    public static final int CAPABILITY_TRANSFER_CONSULTATIVE = 268435456;
    public static final int CAPABILITY_UNUSED = 16;
    public static final int CAPABILITY_UNUSED_2 = 16384;
    public static final int CAPABILITY_UNUSED_3 = 32768;
    public static final int CAPABILITY_UNUSED_4 = 65536;
    public static final int CAPABILITY_UNUSED_5 = 131072;
    public static final java.lang.String EVENT_CALL_HOLD_FAILED = "android.telecom.event.CALL_HOLD_FAILED";
    public static final java.lang.String EVENT_CALL_MERGE_FAILED = "android.telecom.event.CALL_MERGE_FAILED";
    public static final java.lang.String EVENT_CALL_PULL_FAILED = "android.telecom.event.CALL_PULL_FAILED";

    @android.annotation.SystemApi
    public static final java.lang.String EVENT_CALL_QUALITY_REPORT = "android.telecom.event.CALL_QUALITY_REPORT";
    public static final java.lang.String EVENT_CALL_REMOTELY_HELD = "android.telecom.event.CALL_REMOTELY_HELD";
    public static final java.lang.String EVENT_CALL_REMOTELY_UNHELD = "android.telecom.event.CALL_REMOTELY_UNHELD";
    public static final java.lang.String EVENT_CALL_SWITCH_FAILED = "android.telecom.event.CALL_SWITCH_FAILED";

    @android.annotation.SystemApi
    public static final java.lang.String EVENT_DEVICE_TO_DEVICE_MESSAGE = "android.telecom.event.DEVICE_TO_DEVICE_MESSAGE";
    public static final java.lang.String EVENT_MERGE_COMPLETE = "android.telecom.event.MERGE_COMPLETE";
    public static final java.lang.String EVENT_MERGE_START = "android.telecom.event.MERGE_START";
    public static final java.lang.String EVENT_ON_HOLD_TONE_END = "android.telecom.event.ON_HOLD_TONE_END";
    public static final java.lang.String EVENT_ON_HOLD_TONE_START = "android.telecom.event.ON_HOLD_TONE_START";
    public static final java.lang.String EVENT_RTT_AUDIO_INDICATION_CHANGED = "android.telecom.event.RTT_AUDIO_INDICATION_CHANGED";
    public static final java.lang.String EXTRA_ADD_TO_CONFERENCE_ID = "android.telecom.extra.ADD_TO_CONFERENCE_ID";
    public static final java.lang.String EXTRA_ANSWERING_DROPS_FG_CALL = "android.telecom.extra.ANSWERING_DROPS_FG_CALL";
    public static final java.lang.String EXTRA_ANSWERING_DROPS_FG_CALL_APP_NAME = "android.telecom.extra.ANSWERING_DROPS_FG_CALL_APP_NAME";
    public static final java.lang.String EXTRA_AUDIO_CODEC = "android.telecom.extra.AUDIO_CODEC";
    public static final java.lang.String EXTRA_AUDIO_CODEC_BANDWIDTH_KHZ = "android.telecom.extra.AUDIO_CODEC_BANDWIDTH_KHZ";
    public static final java.lang.String EXTRA_AUDIO_CODEC_BITRATE_KBPS = "android.telecom.extra.AUDIO_CODEC_BITRATE_KBPS";
    public static final java.lang.String EXTRA_CALLER_NUMBER_VERIFICATION_STATUS = "android.telecom.extra.CALLER_NUMBER_VERIFICATION_STATUS";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CALL_QUALITY_REPORT = "android.telecom.extra.CALL_QUALITY_REPORT";
    public static final java.lang.String EXTRA_CALL_SUBJECT = "android.telecom.extra.CALL_SUBJECT";
    public static final java.lang.String EXTRA_CHILD_ADDRESS = "android.telecom.extra.CHILD_ADDRESS";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_DEVICE_TO_DEVICE_MESSAGE_TYPE = "android.telecom.extra.DEVICE_TO_DEVICE_MESSAGE_TYPE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_DEVICE_TO_DEVICE_MESSAGE_VALUE = "android.telecom.extra.DEVICE_TO_DEVICE_MESSAGE_VALUE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_DISABLE_ADD_CALL = "android.telecom.extra.DISABLE_ADD_CALL";
    public static final java.lang.String EXTRA_IS_DEVICE_TO_DEVICE_COMMUNICATION_AVAILABLE = "android.telecom.extra.IS_DEVICE_TO_DEVICE_COMMUNICATION_AVAILABLE";
    public static final java.lang.String EXTRA_IS_RTT_AUDIO_PRESENT = "android.telecom.extra.IS_RTT_AUDIO_PRESENT";
    public static final java.lang.String EXTRA_KEY_QUERY_LOCATION = "android.telecom.extra.KEY_QUERY_LOCATION";
    public static final java.lang.String EXTRA_LAST_FORWARDED_NUMBER = "android.telecom.extra.LAST_FORWARDED_NUMBER";
    public static final java.lang.String EXTRA_LAST_KNOWN_CELL_IDENTITY = "android.telecom.extra.LAST_KNOWN_CELL_IDENTITY";
    public static final java.lang.String EXTRA_ORIGINAL_CONNECTION_ID = "android.telecom.extra.ORIGINAL_CONNECTION_ID";
    public static final java.lang.String EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME = "android.telecom.extra.REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME";
    public static final java.lang.String EXTRA_REMOTE_PHONE_ACCOUNT_HANDLE = "android.telecom.extra.REMOTE_PHONE_ACCOUNT_HANDLE";
    public static final java.lang.String EXTRA_SIP_INVITE = "android.telecom.extra.SIP_INVITE";
    private static final boolean PII_DEBUG = android.telecom.Log.isLoggable(3);
    public static final int PROPERTY_ASSISTED_DIALING = 512;
    public static final int PROPERTY_CROSS_SIM = 8192;

    @android.annotation.SystemApi
    public static final int PROPERTY_EMERGENCY_CALLBACK_MODE = 1;

    @android.annotation.SystemApi
    public static final int PROPERTY_GENERIC_CONFERENCE = 2;
    public static final int PROPERTY_HAS_CDMA_VOICE_PRIVACY = 32;
    public static final int PROPERTY_HIGH_DEF_AUDIO = 4;
    public static final int PROPERTY_IS_ADHOC_CONFERENCE = 4096;

    @android.annotation.SystemApi
    public static final int PROPERTY_IS_DOWNGRADED_CONFERENCE = 64;
    public static final int PROPERTY_IS_EXTERNAL_CALL = 16;
    public static final int PROPERTY_IS_RTT = 256;
    public static final int PROPERTY_NETWORK_IDENTIFIED_EMERGENCY_CALL = 1024;

    @android.annotation.SystemApi
    public static final int PROPERTY_REMOTELY_HOSTED = 2048;
    public static final int PROPERTY_SELF_MANAGED = 128;
    public static final int PROPERTY_WIFI = 8;
    public static final int STATE_ACTIVE = 4;
    public static final int STATE_DIALING = 3;
    public static final int STATE_DISCONNECTED = 6;
    public static final int STATE_HOLDING = 5;
    public static final int STATE_INITIALIZING = 0;
    public static final int STATE_NEW = 1;
    public static final int STATE_PULLING_CALL = 7;
    public static final int STATE_RINGING = 2;
    public static final int VERIFICATION_STATUS_FAILED = 2;
    public static final int VERIFICATION_STATUS_NOT_VERIFIED = 0;
    public static final int VERIFICATION_STATUS_PASSED = 1;
    private android.net.Uri mAddress;
    private int mAddressPresentation;
    private boolean mAudioModeIsVoip;
    private android.telecom.CallAudioState mCallAudioState;
    private android.telecom.CallEndpoint mCallEndpoint;
    private java.lang.String mCallerDisplayName;
    private int mCallerDisplayNamePresentation;
    private int mCallerNumberVerificationStatus;
    private android.telecom.Conference mConference;
    private int mConnectionCapabilities;
    private int mConnectionProperties;
    private android.telecom.ConnectionService mConnectionService;
    private android.telecom.DisconnectCause mDisconnectCause;
    private android.os.Bundle mExtras;
    private android.telecom.PhoneAccountHandle mPhoneAccountHandle;
    private java.util.Set<java.lang.String> mPreviousExtraKeys;
    private android.telecom.StatusHints mStatusHints;
    private java.lang.String mTelecomCallId;
    private android.telecom.Connection.VideoProvider mVideoProvider;
    private int mVideoState;
    private final android.telecom.Connection.Listener mConnectionDeathListener = new android.telecom.Connection.Listener() { // from class: android.telecom.Connection.1
        @Override // android.telecom.Connection.Listener
        public void onDestroyed(android.telecom.Connection connection) {
            if (android.telecom.Connection.this.mConferenceables.remove(connection)) {
                android.telecom.Connection.this.fireOnConferenceableConnectionsChanged();
            }
        }
    };
    private final android.telecom.Conference.Listener mConferenceDeathListener = new android.telecom.Conference.Listener() { // from class: android.telecom.Connection.2
        @Override // android.telecom.Conference.Listener
        public void onDestroyed(android.telecom.Conference conference) {
            if (android.telecom.Connection.this.mConferenceables.remove(conference)) {
                android.telecom.Connection.this.fireOnConferenceableConnectionsChanged();
            }
        }
    };
    private final java.util.Set<android.telecom.Connection.Listener> mListeners = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap(8, 0.9f, 1));
    private final java.util.List<android.telecom.Conferenceable> mConferenceables = new java.util.ArrayList();
    private final java.util.List<android.telecom.Conferenceable> mUnmodifiableConferenceables = java.util.Collections.unmodifiableList(this.mConferenceables);
    private int mState = 1;
    private boolean mRingbackRequested = false;
    private int mSupportedAudioRoutes = 31;
    private long mConnectTimeMillis = 0;
    private long mConnectElapsedTimeMillis = 0;
    private final java.lang.Object mExtrasLock = new java.lang.Object();
    private int mCallDirection = -1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioCodec {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConnectionState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VerificationStatus {
    }

    public static java.lang.String capabilitiesToString(int i) {
        return capabilitiesToStringInternal(i, true);
    }

    public static java.lang.String capabilitiesToStringShort(int i) {
        return capabilitiesToStringInternal(i, false);
    }

    private static java.lang.String capabilitiesToStringInternal(int i, boolean z) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        if (z) {
            sb.append("Capabilities:");
        }
        if ((i & 1) == 1) {
            sb.append(z ? " CAPABILITY_HOLD" : " hld");
        }
        if ((i & 2) == 2) {
            sb.append(z ? " CAPABILITY_SUPPORT_HOLD" : " sup_hld");
        }
        if ((i & 4) == 4) {
            sb.append(z ? " CAPABILITY_MERGE_CONFERENCE" : " mrg_cnf");
        }
        if ((i & 8) == 8) {
            sb.append(z ? " CAPABILITY_SWAP_CONFERENCE" : " swp_cnf");
        }
        if ((i & 32) == 32) {
            sb.append(z ? " CAPABILITY_RESPOND_VIA_TEXT" : " txt");
        }
        if ((i & 64) == 64) {
            sb.append(z ? " CAPABILITY_MUTE" : " mut");
        }
        if ((i & 128) == 128) {
            sb.append(z ? " CAPABILITY_MANAGE_CONFERENCE" : " mng_cnf");
        }
        if ((i & 256) == 256) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_LOCAL_RX" : " VTlrx");
        }
        if ((i & 512) == 512) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_LOCAL_TX" : " VTltx");
        }
        if ((i & 768) == 768) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL" : " VTlbi");
        }
        if ((i & 1024) == 1024) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_REMOTE_RX" : " VTrrx");
        }
        if ((i & 2048) == 2048) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_REMOTE_TX" : " VTrtx");
        }
        if ((i & 3072) == 3072) {
            sb.append(z ? " CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL" : " VTrbi");
        }
        if ((i & 8388608) == 8388608) {
            sb.append(z ? " CAPABILITY_CANNOT_DOWNGRADE_VIDEO_TO_AUDIO" : " !v2a");
        }
        if ((i & 262144) == 262144) {
            sb.append(z ? " CAPABILITY_SPEED_UP_MT_AUDIO" : " spd_aud");
        }
        if ((i & 524288) == 524288) {
            sb.append(z ? " CAPABILITY_CAN_UPGRADE_TO_VIDEO" : " a2v");
        }
        if ((i & 1048576) == 1048576) {
            sb.append(z ? " CAPABILITY_CAN_PAUSE_VIDEO" : " paus_VT");
        }
        if ((i & 2097152) == 2097152) {
            sb.append(z ? " CAPABILITY_SINGLE_PARTY_CONFERENCE" : " 1p_cnf");
        }
        if ((i & 4194304) == 4194304) {
            sb.append(z ? " CAPABILITY_CAN_SEND_RESPONSE_VIA_CONNECTION" : " rsp_by_con");
        }
        if ((i & 16777216) == 16777216) {
            sb.append(z ? " CAPABILITY_CAN_PULL_CALL" : " pull");
        }
        if ((i & 33554432) == 33554432) {
            sb.append(z ? " CAPABILITY_SUPPORT_DEFLECT" : " sup_def");
        }
        if ((i & 67108864) == 67108864) {
            sb.append(z ? " CAPABILITY_ADD_PARTICIPANT" : " add_participant");
        }
        if ((134217728 & i) == 134217728) {
            sb.append(z ? " CAPABILITY_TRANSFER" : " sup_trans");
        }
        if ((268435456 & i) == 268435456) {
            sb.append(z ? " CAPABILITY_TRANSFER_CONSULTATIVE" : " sup_cTrans");
        }
        if ((i & 536870912) == 536870912) {
            sb.append(z ? " CAPABILITY_REMOTE_PARTY_SUPPORTS_RTT" : " sup_rtt");
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public static java.lang.String propertiesToString(int i) {
        return propertiesToStringInternal(i, true);
    }

    public static java.lang.String propertiesToStringShort(int i) {
        return propertiesToStringInternal(i, false);
    }

    private static java.lang.String propertiesToStringInternal(int i, boolean z) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        if (z) {
            sb.append("Properties:");
        }
        if ((i & 128) == 128) {
            sb.append(z ? " PROPERTY_SELF_MANAGED" : " self_mng");
        }
        if ((i & 1) == 1) {
            sb.append(z ? " PROPERTY_EMERGENCY_CALLBACK_MODE" : " ecbm");
        }
        if ((i & 4) == 4) {
            sb.append(z ? " PROPERTY_HIGH_DEF_AUDIO" : " HD");
        }
        if ((i & 8) == 8) {
            sb.append(z ? " PROPERTY_WIFI" : " wifi");
        }
        if ((i & 2) == 2) {
            sb.append(z ? " PROPERTY_GENERIC_CONFERENCE" : " gen_conf");
        }
        if ((i & 16) == 16) {
            sb.append(z ? " PROPERTY_IS_EXTERNAL_CALL" : " xtrnl");
        }
        if ((i & 32) == 32) {
            sb.append(z ? " PROPERTY_HAS_CDMA_VOICE_PRIVACY" : " priv");
        }
        if ((i & 256) == 256) {
            sb.append(z ? " PROPERTY_IS_RTT" : " rtt");
        }
        if ((i & 1024) == 1024) {
            sb.append(z ? " PROPERTY_NETWORK_IDENTIFIED_EMERGENCY_CALL" : " ecall");
        }
        if ((i & 2048) == 2048) {
            sb.append(z ? " PROPERTY_REMOTELY_HOSTED" : " remote_hst");
        }
        if ((i & 4096) == 4096) {
            sb.append(z ? " PROPERTY_IS_ADHOC_CONFERENCE" : " adhoc_conf");
        }
        if ((i & 64) == 64) {
            sb.append(z ? " PROPERTY_IS_DOWNGRADED_CONFERENCE" : " dngrd_conf");
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    static abstract class Listener {
        Listener() {
        }

        public void onStateChanged(android.telecom.Connection connection, int i) {
        }

        public void onAddressChanged(android.telecom.Connection connection, android.net.Uri uri, int i) {
        }

        public void onCallerDisplayNameChanged(android.telecom.Connection connection, java.lang.String str, int i) {
        }

        public void onVideoStateChanged(android.telecom.Connection connection, int i) {
        }

        public void onDisconnected(android.telecom.Connection connection, android.telecom.DisconnectCause disconnectCause) {
        }

        public void onPostDialWait(android.telecom.Connection connection, java.lang.String str) {
        }

        public void onPostDialChar(android.telecom.Connection connection, char c) {
        }

        public void onRingbackRequested(android.telecom.Connection connection, boolean z) {
        }

        public void onDestroyed(android.telecom.Connection connection) {
        }

        public void onConnectionCapabilitiesChanged(android.telecom.Connection connection, int i) {
        }

        public void onConnectionPropertiesChanged(android.telecom.Connection connection, int i) {
        }

        public void onSupportedAudioRoutesChanged(android.telecom.Connection connection, int i) {
        }

        public void onVideoProviderChanged(android.telecom.Connection connection, android.telecom.Connection.VideoProvider videoProvider) {
        }

        public void onAudioModeIsVoipChanged(android.telecom.Connection connection, boolean z) {
        }

        public void onStatusHintsChanged(android.telecom.Connection connection, android.telecom.StatusHints statusHints) {
        }

        public void onConferenceablesChanged(android.telecom.Connection connection, java.util.List<android.telecom.Conferenceable> list) {
        }

        public void onConferenceChanged(android.telecom.Connection connection, android.telecom.Conference conference) {
        }

        public void onConferenceMergeFailed(android.telecom.Connection connection) {
        }

        public void onExtrasChanged(android.telecom.Connection connection, android.os.Bundle bundle) {
        }

        public void onExtrasRemoved(android.telecom.Connection connection, java.util.List<java.lang.String> list) {
        }

        public void onConnectionEvent(android.telecom.Connection connection, java.lang.String str, android.os.Bundle bundle) {
        }

        public void onAudioRouteChanged(android.telecom.Connection connection, int i, java.lang.String str) {
        }

        public void onRttInitiationSuccess(android.telecom.Connection connection) {
        }

        public void onRttInitiationFailure(android.telecom.Connection connection, int i) {
        }

        public void onRttSessionRemotelyTerminated(android.telecom.Connection connection) {
        }

        public void onRemoteRttRequest(android.telecom.Connection connection) {
        }

        public void onPhoneAccountChanged(android.telecom.Connection connection, android.telecom.PhoneAccountHandle phoneAccountHandle) {
        }

        public void onConnectionTimeReset(android.telecom.Connection connection) {
        }

        public void onEndpointChanged(android.telecom.Connection connection, android.telecom.CallEndpoint callEndpoint, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallEndpointException> outcomeReceiver) {
        }

        public void onQueryLocation(android.telecom.Connection connection, long j, java.lang.String str, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.location.Location, android.telecom.QueryLocationException> outcomeReceiver) {
        }
    }

    public static final class RttTextStream {
        private static final int READ_BUFFER_SIZE = 1000;
        private final android.os.ParcelFileDescriptor mFdFromInCall;
        private final android.os.ParcelFileDescriptor mFdToInCall;
        private final java.io.FileInputStream mFromInCallFileInputStream;
        private final java.io.InputStreamReader mPipeFromInCall;
        private final java.io.OutputStreamWriter mPipeToInCall;
        private char[] mReadBuffer = new char[1000];

        public RttTextStream(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
            this.mFdFromInCall = parcelFileDescriptor2;
            this.mFdToInCall = parcelFileDescriptor;
            this.mFromInCallFileInputStream = new java.io.FileInputStream(parcelFileDescriptor2.getFileDescriptor());
            this.mPipeFromInCall = new java.io.InputStreamReader(java.nio.channels.Channels.newInputStream(java.nio.channels.Channels.newChannel(this.mFromInCallFileInputStream)));
            this.mPipeToInCall = new java.io.OutputStreamWriter(new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        }

        public void write(java.lang.String str) throws java.io.IOException {
            this.mPipeToInCall.write(str);
            this.mPipeToInCall.flush();
        }

        public java.lang.String read() throws java.io.IOException {
            int read = this.mPipeFromInCall.read(this.mReadBuffer, 0, 1000);
            if (read < 0) {
                return null;
            }
            return new java.lang.String(this.mReadBuffer, 0, read);
        }

        public java.lang.String readImmediately() throws java.io.IOException {
            if (this.mFromInCallFileInputStream.available() > 0) {
                return read();
            }
            return null;
        }

        public android.os.ParcelFileDescriptor getFdFromInCall() {
            return this.mFdFromInCall;
        }

        public android.os.ParcelFileDescriptor getFdToInCall() {
            return this.mFdToInCall;
        }
    }

    public static final class RttModifyStatus {
        public static final int SESSION_MODIFY_REQUEST_FAIL = 2;
        public static final int SESSION_MODIFY_REQUEST_INVALID = 3;
        public static final int SESSION_MODIFY_REQUEST_REJECTED_BY_REMOTE = 5;
        public static final int SESSION_MODIFY_REQUEST_SUCCESS = 1;
        public static final int SESSION_MODIFY_REQUEST_TIMED_OUT = 4;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface RttSessionModifyStatus {
        }

        private RttModifyStatus() {
        }
    }

    public static abstract class VideoProvider {
        private static final int MSG_ADD_VIDEO_CALLBACK = 1;
        private static final int MSG_REMOVE_VIDEO_CALLBACK = 12;
        private static final int MSG_REQUEST_CAMERA_CAPABILITIES = 9;
        private static final int MSG_REQUEST_CONNECTION_DATA_USAGE = 10;
        private static final int MSG_SEND_SESSION_MODIFY_REQUEST = 7;
        private static final int MSG_SEND_SESSION_MODIFY_RESPONSE = 8;
        private static final int MSG_SET_CAMERA = 2;
        private static final int MSG_SET_DEVICE_ORIENTATION = 5;
        private static final int MSG_SET_DISPLAY_SURFACE = 4;
        private static final int MSG_SET_PAUSE_IMAGE = 11;
        private static final int MSG_SET_PREVIEW_SURFACE = 3;
        private static final int MSG_SET_ZOOM = 6;
        public static final int SESSION_EVENT_CAMERA_FAILURE = 5;
        private static final java.lang.String SESSION_EVENT_CAMERA_FAILURE_STR = "CAMERA_FAIL";
        public static final int SESSION_EVENT_CAMERA_PERMISSION_ERROR = 7;
        private static final java.lang.String SESSION_EVENT_CAMERA_PERMISSION_ERROR_STR = "CAMERA_PERMISSION_ERROR";
        public static final int SESSION_EVENT_CAMERA_READY = 6;
        private static final java.lang.String SESSION_EVENT_CAMERA_READY_STR = "CAMERA_READY";
        public static final int SESSION_EVENT_RX_PAUSE = 1;
        private static final java.lang.String SESSION_EVENT_RX_PAUSE_STR = "RX_PAUSE";
        public static final int SESSION_EVENT_RX_RESUME = 2;
        private static final java.lang.String SESSION_EVENT_RX_RESUME_STR = "RX_RESUME";
        public static final int SESSION_EVENT_TX_START = 3;
        private static final java.lang.String SESSION_EVENT_TX_START_STR = "TX_START";
        public static final int SESSION_EVENT_TX_STOP = 4;
        private static final java.lang.String SESSION_EVENT_TX_STOP_STR = "TX_STOP";
        private static final java.lang.String SESSION_EVENT_UNKNOWN_STR = "UNKNOWN";
        public static final int SESSION_MODIFY_REQUEST_FAIL = 2;
        public static final int SESSION_MODIFY_REQUEST_INVALID = 3;
        public static final int SESSION_MODIFY_REQUEST_REJECTED_BY_REMOTE = 5;
        public static final int SESSION_MODIFY_REQUEST_SUCCESS = 1;
        public static final int SESSION_MODIFY_REQUEST_TIMED_OUT = 4;
        private final android.telecom.Connection.VideoProvider.VideoProviderBinder mBinder;
        private android.telecom.Connection.VideoProvider.VideoProviderHandler mMessageHandler;
        private java.util.concurrent.ConcurrentHashMap<android.os.IBinder, com.android.internal.telecom.IVideoCallback> mVideoCallbacks;

        public abstract void onRequestCameraCapabilities();

        public abstract void onRequestConnectionDataUsage();

        public abstract void onSendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2);

        public abstract void onSendSessionModifyResponse(android.telecom.VideoProfile videoProfile);

        public abstract void onSetCamera(java.lang.String str);

        public abstract void onSetDeviceOrientation(int i);

        public abstract void onSetDisplaySurface(android.view.Surface surface);

        public abstract void onSetPauseImage(android.net.Uri uri);

        public abstract void onSetPreviewSurface(android.view.Surface surface);

        public abstract void onSetZoom(float f);

        private final class VideoProviderHandler extends android.os.Handler {
            public VideoProviderHandler() {
            }

            public VideoProviderHandler(android.os.Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                com.android.internal.os.SomeArgs someArgs;
                switch (message.what) {
                    case 1:
                        android.os.IBinder iBinder = (android.os.IBinder) message.obj;
                        com.android.internal.telecom.IVideoCallback asInterface = com.android.internal.telecom.IVideoCallback.Stub.asInterface((android.os.IBinder) message.obj);
                        if (asInterface == null) {
                            android.telecom.Log.w(this, "addVideoProvider - skipped; callback is null.", new java.lang.Object[0]);
                            return;
                        } else if (android.telecom.Connection.VideoProvider.this.mVideoCallbacks.containsKey(iBinder)) {
                            android.telecom.Log.i(this, "addVideoProvider - skipped; already present.", new java.lang.Object[0]);
                            return;
                        } else {
                            android.telecom.Connection.VideoProvider.this.mVideoCallbacks.put(iBinder, asInterface);
                            return;
                        }
                    case 2:
                        someArgs = (com.android.internal.os.SomeArgs) message.obj;
                        try {
                            android.telecom.Connection.VideoProvider.this.onSetCamera((java.lang.String) someArgs.arg1);
                            android.telecom.Connection.VideoProvider.this.onSetCamera((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2, someArgs.argi1, someArgs.argi2, someArgs.argi3);
                            return;
                        } finally {
                        }
                    case 3:
                        android.telecom.Connection.VideoProvider.this.onSetPreviewSurface((android.view.Surface) message.obj);
                        return;
                    case 4:
                        android.telecom.Connection.VideoProvider.this.onSetDisplaySurface((android.view.Surface) message.obj);
                        return;
                    case 5:
                        android.telecom.Connection.VideoProvider.this.onSetDeviceOrientation(message.arg1);
                        return;
                    case 6:
                        android.telecom.Connection.VideoProvider.this.onSetZoom(((java.lang.Float) message.obj).floatValue());
                        return;
                    case 7:
                        someArgs = (com.android.internal.os.SomeArgs) message.obj;
                        try {
                            android.telecom.Connection.VideoProvider.this.onSendSessionModifyRequest((android.telecom.VideoProfile) someArgs.arg1, (android.telecom.VideoProfile) someArgs.arg2);
                            return;
                        } finally {
                        }
                    case 8:
                        android.telecom.Connection.VideoProvider.this.onSendSessionModifyResponse((android.telecom.VideoProfile) message.obj);
                        return;
                    case 9:
                        android.telecom.Connection.VideoProvider.this.onRequestCameraCapabilities();
                        return;
                    case 10:
                        android.telecom.Connection.VideoProvider.this.onRequestConnectionDataUsage();
                        return;
                    case 11:
                        android.telecom.Connection.VideoProvider.this.onSetPauseImage((android.net.Uri) message.obj);
                        return;
                    case 12:
                        android.os.IBinder iBinder2 = (android.os.IBinder) message.obj;
                        com.android.internal.telecom.IVideoCallback.Stub.asInterface((android.os.IBinder) message.obj);
                        if (!android.telecom.Connection.VideoProvider.this.mVideoCallbacks.containsKey(iBinder2)) {
                            android.telecom.Log.i(this, "removeVideoProvider - skipped; not present.", new java.lang.Object[0]);
                            return;
                        } else {
                            android.telecom.Connection.VideoProvider.this.mVideoCallbacks.remove(iBinder2);
                            return;
                        }
                    default:
                        return;
                }
            }
        }

        private final class VideoProviderBinder extends com.android.internal.telecom.IVideoProvider.Stub {
            private VideoProviderBinder() {
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void addVideoCallback(android.os.IBinder iBinder) {
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(1, iBinder).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void removeVideoCallback(android.os.IBinder iBinder) {
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(12, iBinder).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setCamera(java.lang.String str, java.lang.String str2, int i) {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = str2;
                obtain.argi1 = android.os.Binder.getCallingUid();
                obtain.argi2 = android.os.Binder.getCallingPid();
                obtain.argi3 = i;
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(2, obtain).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setPreviewSurface(android.view.Surface surface) {
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(3, surface).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setDisplaySurface(android.view.Surface surface) {
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(4, surface).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setDeviceOrientation(int i) {
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(5, i, 0).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setZoom(float f) {
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(6, java.lang.Float.valueOf(f)).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = videoProfile;
                obtain.arg2 = videoProfile2;
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(7, obtain).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile) {
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(8, videoProfile).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void requestCameraCapabilities() {
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(9).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void requestCallDataUsage() {
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(10).sendToTarget();
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setPauseImage(android.net.Uri uri) {
                android.telecom.Connection.VideoProvider.this.mMessageHandler.obtainMessage(11, uri).sendToTarget();
            }
        }

        public VideoProvider() {
            this.mVideoCallbacks = new java.util.concurrent.ConcurrentHashMap<>(8, 0.9f, 1);
            this.mBinder = new android.telecom.Connection.VideoProvider.VideoProviderBinder();
            this.mMessageHandler = new android.telecom.Connection.VideoProvider.VideoProviderHandler(android.os.Looper.getMainLooper());
        }

        public VideoProvider(android.os.Looper looper) {
            this.mVideoCallbacks = new java.util.concurrent.ConcurrentHashMap<>(8, 0.9f, 1);
            this.mBinder = new android.telecom.Connection.VideoProvider.VideoProviderBinder();
            this.mMessageHandler = new android.telecom.Connection.VideoProvider.VideoProviderHandler(looper);
        }

        public final com.android.internal.telecom.IVideoProvider getInterface() {
            return this.mBinder;
        }

        public void onSetCamera(java.lang.String str, java.lang.String str2, int i, int i2, int i3) {
        }

        public void receiveSessionModifyRequest(android.telecom.VideoProfile videoProfile) {
            if (this.mVideoCallbacks != null) {
                java.util.Iterator<com.android.internal.telecom.IVideoCallback> it = this.mVideoCallbacks.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().receiveSessionModifyRequest(videoProfile);
                    } catch (android.os.RemoteException e) {
                        android.telecom.Log.w(this, "receiveSessionModifyRequest callback failed", e);
                    }
                }
            }
        }

        public void receiveSessionModifyResponse(int i, android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) {
            if (this.mVideoCallbacks != null) {
                java.util.Iterator<com.android.internal.telecom.IVideoCallback> it = this.mVideoCallbacks.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().receiveSessionModifyResponse(i, videoProfile, videoProfile2);
                    } catch (android.os.RemoteException e) {
                        android.telecom.Log.w(this, "receiveSessionModifyResponse callback failed", e);
                    }
                }
            }
        }

        public void handleCallSessionEvent(int i) {
            if (this.mVideoCallbacks != null) {
                java.util.Iterator<com.android.internal.telecom.IVideoCallback> it = this.mVideoCallbacks.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().handleCallSessionEvent(i);
                    } catch (android.os.RemoteException e) {
                        android.telecom.Log.w(this, "handleCallSessionEvent callback failed", e);
                    }
                }
            }
        }

        public void changePeerDimensions(int i, int i2) {
            if (this.mVideoCallbacks != null) {
                java.util.Iterator<com.android.internal.telecom.IVideoCallback> it = this.mVideoCallbacks.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().changePeerDimensions(i, i2);
                    } catch (android.os.RemoteException e) {
                        android.telecom.Log.w(this, "changePeerDimensions callback failed", e);
                    }
                }
            }
        }

        public void setCallDataUsage(long j) {
            if (this.mVideoCallbacks != null) {
                java.util.Iterator<com.android.internal.telecom.IVideoCallback> it = this.mVideoCallbacks.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().changeCallDataUsage(j);
                    } catch (android.os.RemoteException e) {
                        android.telecom.Log.w(this, "setCallDataUsage callback failed", e);
                    }
                }
            }
        }

        public void changeCallDataUsage(long j) {
            setCallDataUsage(j);
        }

        public void changeCameraCapabilities(android.telecom.VideoProfile.CameraCapabilities cameraCapabilities) {
            if (this.mVideoCallbacks != null) {
                java.util.Iterator<com.android.internal.telecom.IVideoCallback> it = this.mVideoCallbacks.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().changeCameraCapabilities(cameraCapabilities);
                    } catch (android.os.RemoteException e) {
                        android.telecom.Log.w(this, "changeCameraCapabilities callback failed", e);
                    }
                }
            }
        }

        public void changeVideoQuality(int i) {
            if (this.mVideoCallbacks != null) {
                java.util.Iterator<com.android.internal.telecom.IVideoCallback> it = this.mVideoCallbacks.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().changeVideoQuality(i);
                    } catch (android.os.RemoteException e) {
                        android.telecom.Log.w(this, "changeVideoQuality callback failed", e);
                    }
                }
            }
        }

        public static java.lang.String sessionEventToString(int i) {
            switch (i) {
                case 1:
                    return SESSION_EVENT_RX_PAUSE_STR;
                case 2:
                    return SESSION_EVENT_RX_RESUME_STR;
                case 3:
                    return SESSION_EVENT_TX_START_STR;
                case 4:
                    return SESSION_EVENT_TX_STOP_STR;
                case 5:
                    return SESSION_EVENT_CAMERA_FAILURE_STR;
                case 6:
                    return SESSION_EVENT_CAMERA_READY_STR;
                case 7:
                    return SESSION_EVENT_CAMERA_PERMISSION_ERROR_STR;
                default:
                    return "UNKNOWN " + i;
            }
        }
    }

    @android.annotation.SystemApi
    public final java.lang.String getTelecomCallId() {
        return this.mTelecomCallId;
    }

    public final android.net.Uri getAddress() {
        return this.mAddress;
    }

    public final int getAddressPresentation() {
        return this.mAddressPresentation;
    }

    public final java.lang.String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public final int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public final int getState() {
        return this.mState;
    }

    public final int getVideoState() {
        return this.mVideoState;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public final android.telecom.AudioState getAudioState() {
        if (this.mCallAudioState == null) {
            return null;
        }
        return new android.telecom.AudioState(this.mCallAudioState);
    }

    @java.lang.Deprecated
    public final android.telecom.CallAudioState getCallAudioState() {
        return this.mCallAudioState;
    }

    public final android.telecom.Conference getConference() {
        return this.mConference;
    }

    public final boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public final boolean getAudioModeIsVoip() {
        return this.mAudioModeIsVoip;
    }

    @android.annotation.SystemApi
    public final long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    @android.annotation.SystemApi
    public final long getConnectionStartElapsedRealtimeMillis() {
        return this.mConnectElapsedTimeMillis;
    }

    public final android.telecom.StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public final android.os.Bundle getExtras() {
        android.os.Bundle bundle;
        synchronized (this.mExtrasLock) {
            if (this.mExtras == null) {
                bundle = null;
            } else {
                bundle = new android.os.Bundle(this.mExtras);
            }
        }
        return bundle;
    }

    final android.telecom.Connection addConnectionListener(android.telecom.Connection.Listener listener) {
        this.mListeners.add(listener);
        return this;
    }

    final android.telecom.Connection removeConnectionListener(android.telecom.Connection.Listener listener) {
        if (listener != null) {
            this.mListeners.remove(listener);
        }
        return this;
    }

    public final android.telecom.DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    @android.annotation.SystemApi
    public void setTelecomCallId(java.lang.String str) {
        this.mTelecomCallId = str;
    }

    final void setCallAudioState(android.telecom.CallAudioState callAudioState) {
        checkImmutable();
        android.telecom.Log.d(this, "setAudioState %s", callAudioState);
        this.mCallAudioState = callAudioState;
        onAudioStateChanged(getAudioState());
        onCallAudioStateChanged(callAudioState);
    }

    final void setCallEndpoint(android.telecom.CallEndpoint callEndpoint) {
        checkImmutable();
        android.telecom.Log.d(this, "setCallEndpoint %s", callEndpoint);
        this.mCallEndpoint = callEndpoint;
        onCallEndpointChanged(callEndpoint);
    }

    final void setAvailableCallEndpoints(java.util.List<android.telecom.CallEndpoint> list) {
        checkImmutable();
        android.telecom.Log.d(this, "setAvailableCallEndpoints", new java.lang.Object[0]);
        onAvailableCallEndpointsChanged(list);
    }

    final void setMuteState(boolean z) {
        checkImmutable();
        android.telecom.Log.d(this, "setMuteState %s", java.lang.Boolean.valueOf(z));
        onMuteStateChanged(z);
    }

    public static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "INITIALIZING";
            case 1:
                return "NEW";
            case 2:
                return "RINGING";
            case 3:
                return "DIALING";
            case 4:
                return "ACTIVE";
            case 5:
                return "HOLDING";
            case 6:
                return "DISCONNECTED";
            case 7:
                return "PULLING_CALL";
            default:
                android.telecom.Log.wtf(android.telecom.Connection.class, "Unknown state %d", java.lang.Integer.valueOf(i));
                return "UNKNOWN";
        }
    }

    public final int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public final int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public final int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public final void setAddress(android.net.Uri uri, int i) {
        android.telecom.Log.d(this, "setAddress %s", uri);
        this.mAddress = uri;
        this.mAddressPresentation = i;
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAddressChanged(this, uri, i);
        }
    }

    public final void setCallerDisplayName(java.lang.String str, int i) {
        checkImmutable();
        boolean z = !java.util.Objects.equals(this.mCallerDisplayName, str);
        boolean z2 = this.mCallerDisplayNamePresentation != i;
        if (z) {
            this.mCallerDisplayName = str;
        }
        if (z2) {
            this.mCallerDisplayNamePresentation = i;
        }
        if (z || z2) {
            java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onCallerDisplayNameChanged(this, this.mCallerDisplayName, this.mCallerDisplayNamePresentation);
            }
        }
    }

    public final void setVideoState(int i) {
        checkImmutable();
        android.telecom.Log.d(this, "setVideoState %d", java.lang.Integer.valueOf(i));
        this.mVideoState = i;
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onVideoStateChanged(this, this.mVideoState);
        }
    }

    public final void setActive() {
        checkImmutable();
        setRingbackRequested(false);
        setState(4);
    }

    public final void setRinging() {
        checkImmutable();
        setState(2);
    }

    public final void setInitializing() {
        checkImmutable();
        setState(0);
    }

    public final void setInitialized() {
        checkImmutable();
        setState(1);
    }

    public final void setDialing() {
        checkImmutable();
        setState(3);
    }

    public final void setPulling() {
        checkImmutable();
        setState(7);
    }

    public final void setOnHold() {
        checkImmutable();
        setState(5);
    }

    public final void setVideoProvider(android.telecom.Connection.VideoProvider videoProvider) {
        checkImmutable();
        this.mVideoProvider = videoProvider;
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onVideoProviderChanged(this, videoProvider);
        }
    }

    public final android.telecom.Connection.VideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public final void setDisconnected(android.telecom.DisconnectCause disconnectCause) {
        checkImmutable();
        this.mDisconnectCause = disconnectCause;
        setState(6);
        android.telecom.Log.d(this, "Disconnected with cause %s", disconnectCause);
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onDisconnected(this, disconnectCause);
        }
    }

    public final void setPostDialWait(java.lang.String str) {
        checkImmutable();
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onPostDialWait(this, str);
        }
    }

    public final void setNextPostDialChar(char c) {
        checkImmutable();
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onPostDialChar(this, c);
        }
    }

    public final void setRingbackRequested(boolean z) {
        checkImmutable();
        if (this.mRingbackRequested != z) {
            this.mRingbackRequested = z;
            java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onRingbackRequested(this, z);
            }
        }
    }

    public final void setConnectionCapabilities(int i) {
        checkImmutable();
        if (this.mConnectionCapabilities != i) {
            this.mConnectionCapabilities = i;
            java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionCapabilitiesChanged(this, this.mConnectionCapabilities);
            }
        }
    }

    public final void setConnectionProperties(int i) {
        checkImmutable();
        if (this.mConnectionProperties != i) {
            this.mConnectionProperties = i;
            java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionPropertiesChanged(this, this.mConnectionProperties);
            }
        }
    }

    public final void setSupportedAudioRoutes(int i) {
        if ((i & 9) == 0) {
            throw new java.lang.IllegalArgumentException("supported audio routes must include either speaker or earpiece");
        }
        if (this.mSupportedAudioRoutes != i) {
            this.mSupportedAudioRoutes = i;
            java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onSupportedAudioRoutesChanged(this, this.mSupportedAudioRoutes);
            }
        }
    }

    public final void destroy() {
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onDestroyed(this);
        }
    }

    public final void setAudioModeIsVoip(boolean z) {
        if (!z && (this.mConnectionProperties & 128) == 128) {
            android.telecom.Log.i(this, "setAudioModeIsVoip: Ignored request to set a self-managed connection's audioModeIsVoip to false. Doing so can cause unwanted behavior.", new java.lang.Object[0]);
            return;
        }
        checkImmutable();
        this.mAudioModeIsVoip = z;
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioModeIsVoipChanged(this, z);
        }
    }

    @android.annotation.SystemApi
    public final void setConnectTimeMillis(long j) {
        this.mConnectTimeMillis = j;
    }

    @android.annotation.SystemApi
    public final void setConnectionStartElapsedRealtimeMillis(long j) {
        this.mConnectElapsedTimeMillis = j;
    }

    public final void setStatusHints(android.telecom.StatusHints statusHints) {
        checkImmutable();
        this.mStatusHints = statusHints;
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onStatusHintsChanged(this, statusHints);
        }
    }

    public final void setConferenceableConnections(java.util.List<android.telecom.Connection> list) {
        checkImmutable();
        clearConferenceableList();
        for (android.telecom.Connection connection : list) {
            if (!this.mConferenceables.contains(connection)) {
                connection.addConnectionListener(this.mConnectionDeathListener);
                this.mConferenceables.add(connection);
            }
        }
        fireOnConferenceableConnectionsChanged();
    }

    public final void setConferenceables(java.util.List<android.telecom.Conferenceable> list) {
        clearConferenceableList();
        for (android.telecom.Conferenceable conferenceable : list) {
            if (!this.mConferenceables.contains(conferenceable)) {
                if (conferenceable instanceof android.telecom.Connection) {
                    ((android.telecom.Connection) conferenceable).addConnectionListener(this.mConnectionDeathListener);
                } else if (conferenceable instanceof android.telecom.Conference) {
                    ((android.telecom.Conference) conferenceable).addListener(this.mConferenceDeathListener);
                }
                this.mConferenceables.add(conferenceable);
            }
        }
        fireOnConferenceableConnectionsChanged();
    }

    @android.annotation.SystemApi
    public final void resetConnectionTime() {
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConnectionTimeReset(this);
        }
    }

    public final java.util.List<android.telecom.Conferenceable> getConferenceables() {
        return this.mUnmodifiableConferenceables;
    }

    public final void setConnectionService(android.telecom.ConnectionService connectionService) {
        checkImmutable();
        if (this.mConnectionService != null) {
            android.telecom.Log.e(this, new java.lang.Exception(), "Trying to set ConnectionService on a connection which is already associated with another ConnectionService.", new java.lang.Object[0]);
        } else {
            this.mConnectionService = connectionService;
        }
    }

    public final void unsetConnectionService(android.telecom.ConnectionService connectionService) {
        if (this.mConnectionService != connectionService) {
            android.telecom.Log.e(this, new java.lang.Exception(), "Trying to remove ConnectionService from a Connection that does not belong to the ConnectionService.", new java.lang.Object[0]);
        } else {
            this.mConnectionService = null;
        }
    }

    public final boolean setConference(android.telecom.Conference conference) {
        checkImmutable();
        if (this.mConference == null) {
            this.mConference = conference;
            if (this.mConnectionService != null && this.mConnectionService.containsConference(conference)) {
                fireConferenceChanged();
                return true;
            }
            return true;
        }
        return false;
    }

    public final void resetConference() {
        if (this.mConference != null) {
            android.telecom.Log.d(this, "Conference reset", new java.lang.Object[0]);
            this.mConference = null;
            fireConferenceChanged();
        }
    }

    public final void setExtras(android.os.Bundle bundle) {
        checkImmutable();
        putExtras(bundle);
        if (this.mPreviousExtraKeys != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str : this.mPreviousExtraKeys) {
                if (bundle == null || !bundle.containsKey(str)) {
                    arrayList.add(str);
                }
            }
            if (!arrayList.isEmpty()) {
                removeExtras(arrayList);
            }
        }
        if (this.mPreviousExtraKeys == null) {
            this.mPreviousExtraKeys = new android.util.ArraySet();
        }
        this.mPreviousExtraKeys.clear();
        if (bundle != null) {
            this.mPreviousExtraKeys.addAll(bundle.keySet());
        }
    }

    public final void putExtras(android.os.Bundle bundle) {
        android.os.Bundle bundle2;
        checkImmutable();
        if (bundle == null) {
            return;
        }
        synchronized (this.mExtrasLock) {
            if (this.mExtras == null) {
                this.mExtras = new android.os.Bundle();
            }
            this.mExtras.putAll(bundle);
            bundle2 = new android.os.Bundle(this.mExtras);
        }
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onExtrasChanged(this, new android.os.Bundle(bundle2));
        }
    }

    public final void removeExtras(java.util.List<java.lang.String> list) {
        synchronized (this.mExtrasLock) {
            if (this.mExtras != null) {
                java.util.Iterator<java.lang.String> it = list.iterator();
                while (it.hasNext()) {
                    this.mExtras.remove(it.next());
                }
            }
        }
        java.util.List<java.lang.String> unmodifiableList = java.util.Collections.unmodifiableList(list);
        java.util.Iterator<android.telecom.Connection.Listener> it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
            it2.next().onExtrasRemoved(this, unmodifiableList);
        }
    }

    public final void removeExtras(java.lang.String... strArr) {
        removeExtras(java.util.Arrays.asList(strArr));
    }

    @java.lang.Deprecated
    public final void setAudioRoute(int i) {
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioRouteChanged(this, i, null);
        }
    }

    @java.lang.Deprecated
    public void requestBluetoothAudio(android.bluetooth.BluetoothDevice bluetoothDevice) {
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioRouteChanged(this, 2, bluetoothDevice.getAddress());
        }
    }

    public final void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallEndpointException> outcomeReceiver) {
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onEndpointChanged(this, callEndpoint, executor, outcomeReceiver);
        }
    }

    public final android.telecom.CallEndpoint getCurrentCallEndpoint() {
        return this.mCallEndpoint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendRttInitiationSuccess$0(android.telecom.Connection.Listener listener) {
        listener.onRttInitiationSuccess(this);
    }

    public final void sendRttInitiationSuccess() {
        this.mListeners.forEach(new java.util.function.Consumer() { // from class: android.telecom.Connection$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telecom.Connection.this.lambda$sendRttInitiationSuccess$0((android.telecom.Connection.Listener) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendRttInitiationFailure$1(int i, android.telecom.Connection.Listener listener) {
        listener.onRttInitiationFailure(this, i);
    }

    public final void sendRttInitiationFailure(final int i) {
        this.mListeners.forEach(new java.util.function.Consumer() { // from class: android.telecom.Connection$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telecom.Connection.this.lambda$sendRttInitiationFailure$1(i, (android.telecom.Connection.Listener) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendRttSessionRemotelyTerminated$2(android.telecom.Connection.Listener listener) {
        listener.onRttSessionRemotelyTerminated(this);
    }

    public final void sendRttSessionRemotelyTerminated() {
        this.mListeners.forEach(new java.util.function.Consumer() { // from class: android.telecom.Connection$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telecom.Connection.this.lambda$sendRttSessionRemotelyTerminated$2((android.telecom.Connection.Listener) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendRemoteRttRequest$3(android.telecom.Connection.Listener listener) {
        listener.onRemoteRttRequest(this);
    }

    public final void sendRemoteRttRequest() {
        this.mListeners.forEach(new java.util.function.Consumer() { // from class: android.telecom.Connection$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telecom.Connection.this.lambda$sendRemoteRttRequest$3((android.telecom.Connection.Listener) obj);
            }
        });
    }

    public final void queryLocationForEmergency(final long j, final java.lang.String str, final java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<android.location.Location, android.telecom.QueryLocationException> outcomeReceiver) {
        if (str == null || executor == null || outcomeReceiver == null) {
            throw new java.lang.IllegalArgumentException("There are arguments that must not be null");
        }
        if (j < 100 || j > 5000) {
            throw new java.lang.IllegalArgumentException("The timeoutMillis should be min 100, max 5000");
        }
        this.mListeners.forEach(new java.util.function.Consumer() { // from class: android.telecom.Connection$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telecom.Connection.this.lambda$queryLocationForEmergency$4(j, str, executor, outcomeReceiver, (android.telecom.Connection.Listener) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLocationForEmergency$4(long j, java.lang.String str, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver, android.telecom.Connection.Listener listener) {
        listener.onQueryLocation(this, j, str, executor, outcomeReceiver);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onAudioStateChanged(android.telecom.AudioState audioState) {
    }

    @java.lang.Deprecated
    public void onCallAudioStateChanged(android.telecom.CallAudioState callAudioState) {
    }

    public void onCallEndpointChanged(android.telecom.CallEndpoint callEndpoint) {
    }

    public void onAvailableCallEndpointsChanged(java.util.List<android.telecom.CallEndpoint> list) {
    }

    public void onMuteStateChanged(boolean z) {
    }

    public void onUsingAlternativeUi(boolean z) {
    }

    public void onTrackedByNonUiService(boolean z) {
    }

    public void onStateChanged(int i) {
    }

    public void onPlayDtmfTone(char c) {
    }

    public void onStopDtmfTone() {
    }

    public void onDisconnect() {
    }

    public void onSeparate() {
    }

    public void onAddConferenceParticipants(java.util.List<android.net.Uri> list) {
    }

    public void onAbort() {
    }

    public void onHold() {
    }

    public void onUnhold() {
    }

    public void onAnswer(int i) {
    }

    public void onAnswer() {
        onAnswer(0);
    }

    public void onDeflect(android.net.Uri uri) {
    }

    public void onReject() {
    }

    public void onReject(int i) {
    }

    public void onReject(java.lang.String str) {
    }

    public void onTransfer(android.net.Uri uri, boolean z) {
    }

    public void onTransfer(android.telecom.Connection connection) {
    }

    public void onSilence() {
    }

    public void onPostDialContinue(boolean z) {
    }

    public void onPullExternalCall() {
    }

    public void onCallEvent(java.lang.String str, android.os.Bundle bundle) {
    }

    public void onHandoverComplete() {
    }

    public void onExtrasChanged(android.os.Bundle bundle) {
    }

    public void onShowIncomingCallUi() {
    }

    public void onStartRtt(android.telecom.Connection.RttTextStream rttTextStream) {
    }

    public void onStopRtt() {
    }

    public void handleRttUpgradeResponse(android.telecom.Connection.RttTextStream rttTextStream) {
    }

    @android.annotation.SystemApi
    public static final class CallFilteringCompletionInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.telecom.Connection.CallFilteringCompletionInfo> CREATOR = new android.os.Parcelable.Creator<android.telecom.Connection.CallFilteringCompletionInfo>() { // from class: android.telecom.Connection.CallFilteringCompletionInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telecom.Connection.CallFilteringCompletionInfo createFromParcel(android.os.Parcel parcel) {
                return new android.telecom.Connection.CallFilteringCompletionInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telecom.Connection.CallFilteringCompletionInfo[] newArray(int i) {
                return new android.telecom.Connection.CallFilteringCompletionInfo[i];
            }
        };
        private final android.telecom.CallScreeningService.CallResponse mCallResponse;
        private final android.content.ComponentName mCallScreeningComponent;
        private final boolean mIsBlocked;
        private final boolean mIsInContacts;

        public CallFilteringCompletionInfo(boolean z, boolean z2, android.telecom.CallScreeningService.CallResponse callResponse, android.content.ComponentName componentName) {
            this.mIsBlocked = z;
            this.mIsInContacts = z2;
            this.mCallResponse = callResponse;
            this.mCallScreeningComponent = componentName;
        }

        protected CallFilteringCompletionInfo(android.os.Parcel parcel) {
            this.mIsBlocked = parcel.readByte() != 0;
            this.mIsInContacts = parcel.readByte() != 0;
            android.telecom.CallScreeningService.ParcelableCallResponse parcelableCallResponse = (android.telecom.CallScreeningService.ParcelableCallResponse) parcel.readParcelable(android.telecom.CallScreeningService.class.getClassLoader(), android.telecom.CallScreeningService.ParcelableCallResponse.class);
            this.mCallResponse = parcelableCallResponse == null ? null : parcelableCallResponse.toCallResponse();
            this.mCallScreeningComponent = (android.content.ComponentName) parcel.readParcelable(android.content.ComponentName.class.getClassLoader(), android.content.ComponentName.class);
        }

        public boolean isBlocked() {
            return this.mIsBlocked;
        }

        public boolean isInContacts() {
            return this.mIsInContacts;
        }

        public android.telecom.CallScreeningService.CallResponse getCallResponse() {
            return this.mCallResponse;
        }

        public android.content.ComponentName getCallScreeningComponent() {
            return this.mCallScreeningComponent;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            return "CallFilteringCompletionInfo{mIsBlocked=" + this.mIsBlocked + ", mIsInContacts=" + this.mIsInContacts + ", mCallResponse=" + this.mCallResponse + ", mCallScreeningPackageName='" + this.mCallScreeningComponent + android.text.format.DateFormat.QUOTE + '}';
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeByte(this.mIsBlocked ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.mIsInContacts ? (byte) 1 : (byte) 0);
            parcel.writeParcelable(this.mCallResponse == null ? null : this.mCallResponse.toParcelable(), 0);
            parcel.writeParcelable(this.mCallScreeningComponent, 0);
        }
    }

    @android.annotation.SystemApi
    public void onCallFilteringCompleted(android.telecom.Connection.CallFilteringCompletionInfo callFilteringCompletionInfo) {
    }

    static java.lang.String toLogSafePhoneNumber(java.lang.String str) {
        if (str == null) {
            return "";
        }
        if (PII_DEBUG) {
            return str;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '-' || charAt == '@' || charAt == '.') {
                sb.append(charAt);
            } else {
                sb.append(com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty.TARGET_X);
            }
        }
        return sb.toString();
    }

    private void setState(int i) {
        checkImmutable();
        if (this.mState == 6 && this.mState != i) {
            android.telecom.Log.d(this, "Connection already DISCONNECTED; cannot transition out of this state.", new java.lang.Object[0]);
            return;
        }
        if (this.mState != i) {
            android.telecom.Log.d(this, "setState: %s", stateToString(i));
            this.mState = i;
            onStateChanged(i);
            java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onStateChanged(this, i);
            }
        }
    }

    private static class FailureSignalingConnection extends android.telecom.Connection {
        private boolean mImmutable;

        public FailureSignalingConnection(android.telecom.DisconnectCause disconnectCause) {
            this.mImmutable = false;
            setDisconnected(disconnectCause);
            this.mImmutable = true;
        }

        @Override // android.telecom.Connection
        public void checkImmutable() {
            if (this.mImmutable) {
                throw new java.lang.UnsupportedOperationException("Connection is immutable");
            }
        }
    }

    public static android.telecom.Connection createFailedConnection(android.telecom.DisconnectCause disconnectCause) {
        return new android.telecom.Connection.FailureSignalingConnection(disconnectCause);
    }

    public void checkImmutable() {
    }

    public static android.telecom.Connection createCanceledConnection() {
        return new android.telecom.Connection.FailureSignalingConnection(new android.telecom.DisconnectCause(4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fireOnConferenceableConnectionsChanged() {
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConferenceablesChanged(this, getConferenceables());
        }
    }

    private final void fireConferenceChanged() {
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConferenceChanged(this, this.mConference);
        }
    }

    private final void clearConferenceableList() {
        for (android.telecom.Conferenceable conferenceable : this.mConferenceables) {
            if (conferenceable instanceof android.telecom.Connection) {
                ((android.telecom.Connection) conferenceable).removeConnectionListener(this.mConnectionDeathListener);
            } else if (conferenceable instanceof android.telecom.Conference) {
                ((android.telecom.Conference) conferenceable).removeListener(this.mConferenceDeathListener);
            }
        }
        this.mConferenceables.clear();
    }

    final void handleExtrasChanged(android.os.Bundle bundle) {
        android.os.Bundle bundle2;
        synchronized (this.mExtrasLock) {
            this.mExtras = bundle;
            if (this.mExtras == null) {
                bundle2 = null;
            } else {
                bundle2 = new android.os.Bundle(this.mExtras);
            }
        }
        onExtrasChanged(bundle2);
    }

    public final void notifyConferenceMergeFailed() {
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConferenceMergeFailed(this);
        }
    }

    public void notifyPhoneAccountChanged(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onPhoneAccountChanged(this, phoneAccountHandle);
        }
    }

    @android.annotation.SystemApi
    public void setPhoneAccountHandle(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        if (this.mPhoneAccountHandle != phoneAccountHandle) {
            this.mPhoneAccountHandle = phoneAccountHandle;
            notifyPhoneAccountChanged(phoneAccountHandle);
        }
    }

    @android.annotation.SystemApi
    public android.telecom.PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccountHandle;
    }

    public void sendConnectionEvent(java.lang.String str, android.os.Bundle bundle) {
        java.util.Iterator<android.telecom.Connection.Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConnectionEvent(this, str, bundle);
        }
    }

    @android.annotation.SystemApi
    public final int getCallDirection() {
        return this.mCallDirection;
    }

    @android.annotation.SystemApi
    public void setCallDirection(int i) {
        this.mCallDirection = i;
    }

    public final int getCallerNumberVerificationStatus() {
        return this.mCallerNumberVerificationStatus;
    }

    public final void setCallerNumberVerificationStatus(int i) {
        this.mCallerNumberVerificationStatus = i;
    }
}
