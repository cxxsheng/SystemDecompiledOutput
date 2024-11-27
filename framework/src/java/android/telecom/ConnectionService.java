package android.telecom;

/* loaded from: classes3.dex */
public abstract class ConnectionService extends android.app.Service {
    public static final java.lang.String EXTRA_IS_HANDOVER = "android.telecom.extra.IS_HANDOVER";
    private static final int MSG_ABORT = 3;
    private static final int MSG_ADD_CONNECTION_SERVICE_ADAPTER = 1;
    private static final int MSG_ADD_PARTICIPANT = 39;
    private static final int MSG_ANSWER = 4;
    private static final int MSG_ANSWER_VIDEO = 17;
    private static final int MSG_CONFERENCE = 12;
    private static final int MSG_CONNECTION_SERVICE_FOCUS_GAINED = 31;
    private static final int MSG_CONNECTION_SERVICE_FOCUS_LOST = 30;
    private static final int MSG_CREATE_CONFERENCE = 35;
    private static final int MSG_CREATE_CONFERENCE_COMPLETE = 36;
    private static final int MSG_CREATE_CONFERENCE_FAILED = 37;
    private static final int MSG_CREATE_CONNECTION = 2;
    private static final int MSG_CREATE_CONNECTION_COMPLETE = 29;
    private static final int MSG_CREATE_CONNECTION_FAILED = 25;
    private static final int MSG_DEFLECT = 34;
    private static final int MSG_DISCONNECT = 6;
    private static final int MSG_EXPLICIT_CALL_TRANSFER = 40;
    private static final int MSG_EXPLICIT_CALL_TRANSFER_CONSULTATIVE = 41;
    private static final int MSG_HANDOVER_COMPLETE = 33;
    private static final int MSG_HANDOVER_FAILED = 32;
    private static final int MSG_HOLD = 7;
    private static final int MSG_MERGE_CONFERENCE = 18;
    private static final int MSG_ON_AVAILABLE_CALL_ENDPOINTS_CHANGED = 46;
    private static final int MSG_ON_CALL_AUDIO_STATE_CHANGED = 9;
    private static final int MSG_ON_CALL_ENDPOINT_CHANGED = 45;
    private static final int MSG_ON_CALL_FILTERING_COMPLETED = 42;
    private static final int MSG_ON_EXTRAS_CHANGED = 24;
    private static final int MSG_ON_MUTE_STATE_CHANGED = 47;
    private static final int MSG_ON_POST_DIAL_CONTINUE = 14;
    private static final int MSG_ON_START_RTT = 26;
    private static final int MSG_ON_STOP_RTT = 27;
    private static final int MSG_ON_TRACKED_BY_NON_UI_SERVICE = 44;
    private static final int MSG_ON_USING_ALTERNATIVE_UI = 43;
    private static final int MSG_PLAY_DTMF_TONE = 10;
    private static final int MSG_PULL_EXTERNAL_CALL = 22;
    private static final int MSG_REJECT = 5;
    private static final int MSG_REJECT_WITH_MESSAGE = 20;
    private static final int MSG_REJECT_WITH_REASON = 38;
    private static final int MSG_REMOVE_CONNECTION_SERVICE_ADAPTER = 16;
    private static final int MSG_RTT_UPGRADE_RESPONSE = 28;
    private static final int MSG_SEND_CALL_EVENT = 23;
    private static final int MSG_SILENCE = 21;
    private static final int MSG_SPLIT_FROM_CONFERENCE = 13;
    private static final int MSG_STOP_DTMF_TONE = 11;
    private static final int MSG_SWAP_CONFERENCE = 19;
    private static final int MSG_UNHOLD = 8;
    private static final boolean PII_DEBUG = android.telecom.Log.isLoggable(3);
    public static final java.lang.String SERVICE_INTERFACE = "android.telecom.ConnectionService";
    private static final java.lang.String SESSION_ABORT = "CS.ab";
    private static final java.lang.String SESSION_ADD_CS_ADAPTER = "CS.aCSA";
    private static final java.lang.String SESSION_ADD_PARTICIPANT = "CS.aP";
    private static final java.lang.String SESSION_ANSWER = "CS.an";
    private static final java.lang.String SESSION_ANSWER_VIDEO = "CS.anV";
    private static final java.lang.String SESSION_AVAILABLE_CALL_ENDPOINTS_CHANGED = "CS.oACEC";
    private static final java.lang.String SESSION_CALL_AUDIO_SC = "CS.cASC";
    private static final java.lang.String SESSION_CALL_ENDPOINT_CHANGED = "CS.oCEC";
    private static final java.lang.String SESSION_CALL_FILTERING_COMPLETED = "CS.oCFC";
    private static final java.lang.String SESSION_CONFERENCE = "CS.c";
    private static final java.lang.String SESSION_CONNECTION_SERVICE_FOCUS_GAINED = "CS.cSFG";
    private static final java.lang.String SESSION_CONNECTION_SERVICE_FOCUS_LOST = "CS.cSFL";
    private static final java.lang.String SESSION_CONSULTATIVE_TRANSFER = "CS.cTrans";
    private static final java.lang.String SESSION_CREATE_CONF = "CS.crConf";
    private static final java.lang.String SESSION_CREATE_CONF_COMPLETE = "CS.crConfC";
    private static final java.lang.String SESSION_CREATE_CONF_FAILED = "CS.crConfF";
    private static final java.lang.String SESSION_CREATE_CONN = "CS.crCo";
    private static final java.lang.String SESSION_CREATE_CONN_COMPLETE = "CS.crCoC";
    private static final java.lang.String SESSION_CREATE_CONN_FAILED = "CS.crCoF";
    private static final java.lang.String SESSION_DEFLECT = "CS.def";
    private static final java.lang.String SESSION_DISCONNECT = "CS.d";
    private static final java.lang.String SESSION_EXTRAS_CHANGED = "CS.oEC";
    private static final java.lang.String SESSION_HANDLER = "H.";
    private static final java.lang.String SESSION_HANDOVER_COMPLETE = "CS.hC";
    private static final java.lang.String SESSION_HANDOVER_FAILED = "CS.haF";
    private static final java.lang.String SESSION_HOLD = "CS.h";
    private static final java.lang.String SESSION_MERGE_CONFERENCE = "CS.mC";
    private static final java.lang.String SESSION_MUTE_STATE_CHANGED = "CS.oMSC";
    private static final java.lang.String SESSION_PLAY_DTMF = "CS.pDT";
    private static final java.lang.String SESSION_POST_DIAL_CONT = "CS.oPDC";
    private static final java.lang.String SESSION_PULL_EXTERNAL_CALL = "CS.pEC";
    private static final java.lang.String SESSION_REJECT = "CS.r";
    private static final java.lang.String SESSION_REJECT_MESSAGE = "CS.rWM";
    private static final java.lang.String SESSION_REMOVE_CS_ADAPTER = "CS.rCSA";
    private static final java.lang.String SESSION_RTT_UPGRADE_RESPONSE = "CS.rTRUR";
    private static final java.lang.String SESSION_SEND_CALL_EVENT = "CS.sCE";
    private static final java.lang.String SESSION_SILENCE = "CS.s";
    private static final java.lang.String SESSION_SPLIT_CONFERENCE = "CS.sFC";
    private static final java.lang.String SESSION_START_RTT = "CS.+RTT";
    private static final java.lang.String SESSION_STOP_DTMF = "CS.sDT";
    private static final java.lang.String SESSION_STOP_RTT = "CS.-RTT";
    private static final java.lang.String SESSION_SWAP_CONFERENCE = "CS.sC";
    private static final java.lang.String SESSION_TRACKED_BY_NON_UI_SERVICE = "CS.tBNUS";
    private static final java.lang.String SESSION_TRANSFER = "CS.trans";
    private static final java.lang.String SESSION_UNHOLD = "CS.u";
    private static final java.lang.String SESSION_UPDATE_RTT_PIPES = "CS.uRTT";
    private static final java.lang.String SESSION_USING_ALTERNATIVE_UI = "CS.uAU";
    private static android.telecom.Connection sNullConnection;
    private android.telecom.Conference sNullConference;
    private final java.util.Map<java.lang.String, android.telecom.Connection> mConnectionById = new java.util.concurrent.ConcurrentHashMap();
    private final java.util.Map<android.telecom.Connection, java.lang.String> mIdByConnection = new java.util.concurrent.ConcurrentHashMap();
    private final java.util.Map<java.lang.String, android.telecom.Conference> mConferenceById = new java.util.concurrent.ConcurrentHashMap();
    private final java.util.Map<android.telecom.Conference, java.lang.String> mIdByConference = new java.util.concurrent.ConcurrentHashMap();
    private final android.telecom.RemoteConnectionManager mRemoteConnectionManager = new android.telecom.RemoteConnectionManager(this);
    private final java.util.List<java.lang.Runnable> mPreInitializationConnectionRequests = new java.util.ArrayList();
    private final android.telecom.ConnectionServiceAdapter mAdapter = new android.telecom.ConnectionServiceAdapter();
    private boolean mAreAccountsInitialized = false;
    private java.lang.Object mIdSyncRoot = new java.lang.Object();
    private int mId = 0;
    private final android.os.IBinder mBinder = new com.android.internal.telecom.IConnectionService.Stub() { // from class: android.telecom.ConnectionService.1
        @Override // com.android.internal.telecom.IConnectionService
        public void addConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_ADD_CS_ADAPTER);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = iConnectionServiceAdapter;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(1, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void removeConnectionServiceAdapter(com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_REMOVE_CS_ADAPTER);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = iConnectionServiceAdapter;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(16, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, boolean z2, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CREATE_CONN);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = phoneAccountHandle;
                obtain.arg2 = str;
                obtain.arg3 = connectionRequest;
                obtain.arg4 = android.telecom.Log.createSubsession();
                int i = 1;
                obtain.argi1 = z ? 1 : 0;
                if (!z2) {
                    i = 0;
                }
                obtain.argi2 = i;
                android.telecom.ConnectionService.this.mHandler.obtainMessage(2, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnectionComplete(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CREATE_CONN_COMPLETE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(29, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnectionFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CREATE_CONN_FAILED);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = connectionRequest;
                obtain.arg3 = android.telecom.Log.createSubsession();
                obtain.arg4 = phoneAccountHandle;
                obtain.argi1 = z ? 1 : 0;
                android.telecom.ConnectionService.this.mHandler.obtainMessage(25, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConference(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, boolean z2, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CREATE_CONF);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = phoneAccountHandle;
                obtain.arg2 = str;
                obtain.arg3 = connectionRequest;
                obtain.arg4 = android.telecom.Log.createSubsession();
                int i = 1;
                obtain.argi1 = z ? 1 : 0;
                if (!z2) {
                    i = 0;
                }
                obtain.argi2 = i;
                android.telecom.ConnectionService.this.mHandler.obtainMessage(35, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConferenceComplete(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CREATE_CONF_COMPLETE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(36, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConferenceFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CREATE_CONF_FAILED);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = connectionRequest;
                obtain.arg3 = android.telecom.Log.createSubsession();
                obtain.arg4 = phoneAccountHandle;
                obtain.argi1 = z ? 1 : 0;
                android.telecom.ConnectionService.this.mHandler.obtainMessage(37, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void handoverFailed(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, int i, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_HANDOVER_FAILED);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = connectionRequest;
                obtain.arg3 = android.telecom.Log.createSubsession();
                obtain.arg4 = java.lang.Integer.valueOf(i);
                android.telecom.ConnectionService.this.mHandler.obtainMessage(32, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void handoverComplete(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_HANDOVER_COMPLETE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(33, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void abort(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_ABORT);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(3, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void answerVideo(java.lang.String str, int i, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_ANSWER_VIDEO);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                obtain.argi1 = i;
                android.telecom.ConnectionService.this.mHandler.obtainMessage(17, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void answer(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_ANSWER);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(4, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void deflect(java.lang.String str, android.net.Uri uri, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_DEFLECT);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = uri;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(34, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void reject(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_REJECT);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(5, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void rejectWithReason(java.lang.String str, int i, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_REJECT);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.argi1 = i;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(38, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void rejectWithMessage(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_REJECT_MESSAGE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = str2;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(20, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void transfer(java.lang.String str, android.net.Uri uri, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_TRANSFER);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = uri;
                obtain.argi1 = z ? 1 : 0;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(40, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void consultativeTransfer(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CONSULTATIVE_TRANSFER);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = str2;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(41, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void silence(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_SILENCE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(21, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void disconnect(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_DISCONNECT);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(6, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void hold(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_HOLD);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(7, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void unhold(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_UNHOLD);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(8, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallAudioStateChanged(java.lang.String str, android.telecom.CallAudioState callAudioState, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CALL_AUDIO_SC);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = callAudioState;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(9, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallEndpointChanged(java.lang.String str, android.telecom.CallEndpoint callEndpoint, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CALL_ENDPOINT_CHANGED);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = callEndpoint;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(45, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onAvailableCallEndpointsChanged(java.lang.String str, java.util.List<android.telecom.CallEndpoint> list, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_AVAILABLE_CALL_ENDPOINTS_CHANGED);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = list;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(46, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onMuteStateChanged(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_MUTE_STATE_CHANGED);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = java.lang.Boolean.valueOf(z);
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(47, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onUsingAlternativeUi(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_USING_ALTERNATIVE_UI);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = java.lang.Boolean.valueOf(z);
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(43, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onTrackedByNonUiService(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_TRACKED_BY_NON_UI_SERVICE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = java.lang.Boolean.valueOf(z);
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(44, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void playDtmfTone(java.lang.String str, char c, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_PLAY_DTMF);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = java.lang.Character.valueOf(c);
                obtain.arg2 = str;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(10, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void stopDtmfTone(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_STOP_DTMF);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(11, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void conference(java.lang.String str, java.lang.String str2, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CONFERENCE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = str2;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(12, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void splitFromConference(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_SPLIT_CONFERENCE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(13, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void mergeConference(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_MERGE_CONFERENCE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(18, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void swapConference(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_SWAP_CONFERENCE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(19, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void addConferenceParticipants(java.lang.String str, java.util.List<android.net.Uri> list, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_ADD_PARTICIPANT);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = list;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(39, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onPostDialContinue(java.lang.String str, boolean z, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_POST_DIAL_CONT);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                obtain.argi1 = z ? 1 : 0;
                android.telecom.ConnectionService.this.mHandler.obtainMessage(14, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void pullExternalCall(java.lang.String str, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_PULL_EXTERNAL_CALL);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(22, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void sendCallEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_SEND_CALL_EVENT);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = str2;
                obtain.arg3 = bundle;
                obtain.arg4 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(23, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallFilteringCompleted(java.lang.String str, android.telecom.Connection.CallFilteringCompletionInfo callFilteringCompletionInfo, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CALL_FILTERING_COMPLETED);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = callFilteringCompletionInfo;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(42, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onExtrasChanged(java.lang.String str, android.os.Bundle bundle, android.telecom.Logging.Session.Info info) {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_EXTRAS_CHANGED);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = bundle;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(24, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void startRtt(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_START_RTT);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = new android.telecom.Connection.RttTextStream(parcelFileDescriptor2, parcelFileDescriptor);
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(26, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void stopRtt(java.lang.String str, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_STOP_RTT);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                obtain.arg2 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(27, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void respondToRttUpgradeRequest(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_RTT_UPGRADE_RESPONSE);
            try {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = str;
                if (parcelFileDescriptor2 != null && parcelFileDescriptor != null) {
                    obtain.arg2 = new android.telecom.Connection.RttTextStream(parcelFileDescriptor2, parcelFileDescriptor);
                    obtain.arg3 = android.telecom.Log.createSubsession();
                    android.telecom.ConnectionService.this.mHandler.obtainMessage(28, obtain).sendToTarget();
                }
                obtain.arg2 = null;
                obtain.arg3 = android.telecom.Log.createSubsession();
                android.telecom.ConnectionService.this.mHandler.obtainMessage(28, obtain).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void connectionServiceFocusLost(android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CONNECTION_SERVICE_FOCUS_LOST);
            try {
                android.telecom.ConnectionService.this.mHandler.obtainMessage(30).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void connectionServiceFocusGained(android.telecom.Logging.Session.Info info) throws android.os.RemoteException {
            android.telecom.Log.startSession(info, android.telecom.ConnectionService.SESSION_CONNECTION_SERVICE_FOCUS_GAINED);
            try {
                android.telecom.ConnectionService.this.mHandler.obtainMessage(31).sendToTarget();
            } finally {
                android.telecom.Log.endSession();
            }
        }
    };
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.telecom.ConnectionService.2
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.internal.os.SomeArgs someArgs;
            boolean z;
            java.lang.Object obj = null;
            switch (message.what) {
                case 1:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        com.android.internal.telecom.IConnectionServiceAdapter iConnectionServiceAdapter = (com.android.internal.telecom.IConnectionServiceAdapter) someArgs.arg1;
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.aCSA");
                        android.telecom.ConnectionService.this.mAdapter.addAdapter(iConnectionServiceAdapter);
                        android.telecom.ConnectionService.this.onAdapterAttached();
                        return;
                    } finally {
                    }
                case 2:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg4, "H.CS.crCo");
                    try {
                        final android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) someArgs.arg1;
                        final java.lang.String str = (java.lang.String) someArgs.arg2;
                        final android.telecom.ConnectionRequest connectionRequest = (android.telecom.ConnectionRequest) someArgs.arg3;
                        final boolean z2 = someArgs.argi1 == 1;
                        z = someArgs.argi2 == 1;
                        if (!android.telecom.ConnectionService.this.mAreAccountsInitialized) {
                            android.telecom.Log.d(this, "Enqueueing pre-init request %s", str);
                            final boolean z3 = z;
                            android.telecom.ConnectionService.this.mPreInitializationConnectionRequests.add(new android.telecom.Logging.Runnable("H.CS.crCo.pICR", null) { // from class: android.telecom.ConnectionService.2.1
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    android.telecom.ConnectionService.this.createConnection(phoneAccountHandle, str, connectionRequest, z2, z3);
                                }
                            }.prepare());
                        } else {
                            android.telecom.ConnectionService.this.createConnection(phoneAccountHandle, str, connectionRequest, z2, z);
                        }
                        return;
                    } finally {
                    }
                case 3:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.ab");
                    try {
                        android.telecom.ConnectionService.this.abort((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 4:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.an");
                    try {
                        android.telecom.ConnectionService.this.answer((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 5:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.r");
                    try {
                        android.telecom.ConnectionService.this.reject((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 6:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.d");
                    try {
                        android.telecom.ConnectionService.this.disconnect((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 7:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.r");
                    try {
                        android.telecom.ConnectionService.this.hold((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 8:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.u");
                    try {
                        android.telecom.ConnectionService.this.unhold((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 9:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.cASC");
                    try {
                        android.telecom.ConnectionService.this.onCallAudioStateChanged((java.lang.String) someArgs.arg1, new android.telecom.CallAudioState((android.telecom.CallAudioState) someArgs.arg2));
                        return;
                    } finally {
                    }
                case 10:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.pDT");
                        android.telecom.ConnectionService.this.playDtmfTone((java.lang.String) someArgs.arg2, ((java.lang.Character) someArgs.arg1).charValue());
                        return;
                    } finally {
                    }
                case 11:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.sDT");
                        android.telecom.ConnectionService.this.stopDtmfTone((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 12:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.c");
                        android.telecom.ConnectionService.this.conference((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 13:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.sFC");
                        android.telecom.ConnectionService.this.splitFromConference((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 14:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.oPDC");
                        android.telecom.ConnectionService.this.onPostDialContinue((java.lang.String) someArgs.arg1, someArgs.argi1 == 1);
                        return;
                    } finally {
                    }
                case 15:
                default:
                    return;
                case 16:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.rCSA");
                        android.telecom.ConnectionService.this.mAdapter.removeAdapter((com.android.internal.telecom.IConnectionServiceAdapter) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 17:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.anV");
                    try {
                        android.telecom.ConnectionService.this.answerVideo((java.lang.String) someArgs.arg1, someArgs.argi1);
                        return;
                    } finally {
                    }
                case 18:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.mC");
                        android.telecom.ConnectionService.this.mergeConference((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 19:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.sC");
                        android.telecom.ConnectionService.this.swapConference((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 20:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.rWM");
                    try {
                        android.telecom.ConnectionService.this.reject((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 21:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.s");
                    try {
                        android.telecom.ConnectionService.this.silence((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 22:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.pEC");
                        android.telecom.ConnectionService.this.pullExternalCall((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 23:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg4, "H.CS.sCE");
                        android.telecom.ConnectionService.this.sendCallEvent((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2, (android.os.Bundle) someArgs.arg3);
                        return;
                    } finally {
                    }
                case 24:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.oEC");
                        android.telecom.ConnectionService.this.handleExtrasChanged((java.lang.String) someArgs.arg1, (android.os.Bundle) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 25:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.crCoF");
                    try {
                        final java.lang.String str2 = (java.lang.String) someArgs.arg1;
                        final android.telecom.ConnectionRequest connectionRequest2 = (android.telecom.ConnectionRequest) someArgs.arg2;
                        final boolean z4 = someArgs.argi1 == 1;
                        final android.telecom.PhoneAccountHandle phoneAccountHandle2 = (android.telecom.PhoneAccountHandle) someArgs.arg4;
                        if (!android.telecom.ConnectionService.this.mAreAccountsInitialized) {
                            android.telecom.Log.d(this, "Enqueueing pre-init request %s", str2);
                            android.telecom.ConnectionService.this.mPreInitializationConnectionRequests.add(new android.telecom.Logging.Runnable("H.CS.crCoF.pICR", null) { // from class: android.telecom.ConnectionService.2.3
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    android.telecom.ConnectionService.this.createConnectionFailed(phoneAccountHandle2, str2, connectionRequest2, z4);
                                }
                            }.prepare());
                        } else {
                            android.telecom.Log.i(this, "createConnectionFailed %s", str2);
                            android.telecom.ConnectionService.this.createConnectionFailed(phoneAccountHandle2, str2, connectionRequest2, z4);
                        }
                        return;
                    } finally {
                    }
                case 26:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.+RTT");
                        android.telecom.ConnectionService.this.startRtt((java.lang.String) someArgs.arg1, (android.telecom.Connection.RttTextStream) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 27:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.-RTT");
                        android.telecom.ConnectionService.this.stopRtt((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 28:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.rTRUR");
                        android.telecom.ConnectionService.this.handleRttUpgradeResponse((java.lang.String) someArgs.arg1, (android.telecom.Connection.RttTextStream) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 29:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.crCoC");
                    try {
                        final java.lang.String str3 = (java.lang.String) someArgs.arg1;
                        if (!android.telecom.ConnectionService.this.mAreAccountsInitialized) {
                            android.telecom.Log.d(this, "Enqueueing pre-init request %s", str3);
                            android.telecom.ConnectionService.this.mPreInitializationConnectionRequests.add(new android.telecom.Logging.Runnable("H.CS.crCoC.pICR", obj) { // from class: android.telecom.ConnectionService.2.2
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    android.telecom.ConnectionService.this.notifyCreateConnectionComplete(str3);
                                }
                            }.prepare());
                        } else {
                            android.telecom.ConnectionService.this.notifyCreateConnectionComplete(str3);
                        }
                        return;
                    } finally {
                    }
                case 30:
                    android.telecom.ConnectionService.this.onConnectionServiceFocusLost();
                    return;
                case 31:
                    android.telecom.ConnectionService.this.onConnectionServiceFocusGained();
                    return;
                case 32:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.haF");
                    try {
                        final java.lang.String str4 = (java.lang.String) someArgs.arg1;
                        final android.telecom.ConnectionRequest connectionRequest3 = (android.telecom.ConnectionRequest) someArgs.arg2;
                        final int intValue = ((java.lang.Integer) someArgs.arg4).intValue();
                        if (!android.telecom.ConnectionService.this.mAreAccountsInitialized) {
                            android.telecom.Log.d(this, "Enqueueing pre-init request %s", str4);
                            android.telecom.ConnectionService.this.mPreInitializationConnectionRequests.add(new android.telecom.Logging.Runnable("H.CS.haF.pICR", null) { // from class: android.telecom.ConnectionService.2.7
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    android.telecom.ConnectionService.this.handoverFailed(str4, connectionRequest3, intValue);
                                }
                            }.prepare());
                        } else {
                            android.telecom.Log.i(this, "createConnectionFailed %s", str4);
                            android.telecom.ConnectionService.this.handoverFailed(str4, connectionRequest3, intValue);
                        }
                        return;
                    } finally {
                    }
                case 33:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.hC");
                        android.telecom.ConnectionService.this.notifyHandoverComplete((java.lang.String) someArgs.arg1);
                        return;
                    } finally {
                    }
                case 34:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.def");
                    try {
                        android.telecom.ConnectionService.this.deflect((java.lang.String) someArgs.arg1, (android.net.Uri) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 35:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg4, "H.CS.crCo");
                    try {
                        final android.telecom.PhoneAccountHandle phoneAccountHandle3 = (android.telecom.PhoneAccountHandle) someArgs.arg1;
                        final java.lang.String str5 = (java.lang.String) someArgs.arg2;
                        final android.telecom.ConnectionRequest connectionRequest4 = (android.telecom.ConnectionRequest) someArgs.arg3;
                        final boolean z5 = someArgs.argi1 == 1;
                        z = someArgs.argi2 == 1;
                        if (!android.telecom.ConnectionService.this.mAreAccountsInitialized) {
                            android.telecom.Log.d(this, "Enqueueing pre-initconference request %s", str5);
                            final boolean z6 = z;
                            android.telecom.ConnectionService.this.mPreInitializationConnectionRequests.add(new android.telecom.Logging.Runnable("H.CS.crConf.pIConfR", null) { // from class: android.telecom.ConnectionService.2.4
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    android.telecom.ConnectionService.this.createConference(phoneAccountHandle3, str5, connectionRequest4, z5, z6);
                                }
                            }.prepare());
                        } else {
                            android.telecom.ConnectionService.this.createConference(phoneAccountHandle3, str5, connectionRequest4, z5, z);
                        }
                        return;
                    } finally {
                    }
                case 36:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.crCoC");
                    try {
                        final java.lang.String str6 = (java.lang.String) someArgs.arg1;
                        if (!android.telecom.ConnectionService.this.mAreAccountsInitialized) {
                            android.telecom.Log.d(this, "Enqueueing pre-init conference request %s", str6);
                            android.telecom.ConnectionService.this.mPreInitializationConnectionRequests.add(new android.telecom.Logging.Runnable("H.CS.crConfC.pIConfR", obj) { // from class: android.telecom.ConnectionService.2.5
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    android.telecom.ConnectionService.this.notifyCreateConferenceComplete(str6);
                                }
                            }.prepare());
                        } else {
                            android.telecom.ConnectionService.this.notifyCreateConferenceComplete(str6);
                        }
                        return;
                    } finally {
                    }
                case 37:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.crCoF");
                    try {
                        final java.lang.String str7 = (java.lang.String) someArgs.arg1;
                        final android.telecom.ConnectionRequest connectionRequest5 = (android.telecom.ConnectionRequest) someArgs.arg2;
                        final boolean z7 = someArgs.argi1 == 1;
                        final android.telecom.PhoneAccountHandle phoneAccountHandle4 = (android.telecom.PhoneAccountHandle) someArgs.arg4;
                        if (!android.telecom.ConnectionService.this.mAreAccountsInitialized) {
                            android.telecom.Log.d(this, "Enqueueing pre-init conference request %s", str7);
                            android.telecom.ConnectionService.this.mPreInitializationConnectionRequests.add(new android.telecom.Logging.Runnable("H.CS.crConfF.pIConfR", null) { // from class: android.telecom.ConnectionService.2.6
                                @Override // android.telecom.Logging.Runnable
                                public void loggedRun() {
                                    android.telecom.ConnectionService.this.createConferenceFailed(phoneAccountHandle4, str7, connectionRequest5, z7);
                                }
                            }.prepare());
                        } else {
                            android.telecom.Log.i(this, "createConferenceFailed %s", str7);
                            android.telecom.ConnectionService.this.createConferenceFailed(phoneAccountHandle4, str7, connectionRequest5, z7);
                        }
                        return;
                    } finally {
                    }
                case 38:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg2, "H.CS.r");
                    try {
                        android.telecom.ConnectionService.this.reject((java.lang.String) someArgs.arg1, someArgs.argi1);
                        return;
                    } finally {
                    }
                case 39:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.aP");
                        android.telecom.ConnectionService.this.addConferenceParticipants((java.lang.String) someArgs.arg1, (java.util.List) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 40:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.trans");
                    try {
                        android.telecom.ConnectionService.this.transfer((java.lang.String) someArgs.arg1, (android.net.Uri) someArgs.arg2, someArgs.argi1 == 1);
                        return;
                    } finally {
                    }
                case 41:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.cTrans");
                    try {
                        android.telecom.ConnectionService.this.consultativeTransfer((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 42:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    try {
                        android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.oCFC");
                        android.telecom.ConnectionService.this.onCallFilteringCompleted((java.lang.String) someArgs.arg1, (android.telecom.Connection.CallFilteringCompletionInfo) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 43:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.uAU");
                    try {
                        android.telecom.ConnectionService.this.onUsingAlternativeUi((java.lang.String) someArgs.arg1, ((java.lang.Boolean) someArgs.arg2).booleanValue());
                        return;
                    } finally {
                    }
                case 44:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.tBNUS");
                    try {
                        android.telecom.ConnectionService.this.onTrackedByNonUiService((java.lang.String) someArgs.arg1, ((java.lang.Boolean) someArgs.arg2).booleanValue());
                        return;
                    } finally {
                    }
                case 45:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.cASC");
                    try {
                        android.telecom.ConnectionService.this.onCallEndpointChanged((java.lang.String) someArgs.arg1, (android.telecom.CallEndpoint) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 46:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.cASC");
                    try {
                        android.telecom.ConnectionService.this.onAvailableCallEndpointsChanged((java.lang.String) someArgs.arg1, (java.util.List) someArgs.arg2);
                        return;
                    } finally {
                    }
                case 47:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.telecom.Log.continueSession((android.telecom.Logging.Session) someArgs.arg3, "H.CS.cASC");
                    try {
                        android.telecom.ConnectionService.this.onMuteStateChanged((java.lang.String) someArgs.arg1, ((java.lang.Boolean) someArgs.arg2).booleanValue());
                        return;
                    } finally {
                    }
            }
        }
    };
    private final android.telecom.Conference.Listener mConferenceListener = new android.telecom.Conference.Listener() { // from class: android.telecom.ConnectionService.3
        @Override // android.telecom.Conference.Listener
        public void onStateChanged(android.telecom.Conference conference, int i, int i2) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            switch (i2) {
                case 2:
                    android.telecom.ConnectionService.this.mAdapter.setRinging(str);
                    break;
                case 3:
                    android.telecom.ConnectionService.this.mAdapter.setDialing(str);
                    break;
                case 4:
                    android.telecom.ConnectionService.this.mAdapter.setActive(str);
                    break;
                case 5:
                    android.telecom.ConnectionService.this.mAdapter.setOnHold(str);
                    break;
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onDisconnected(android.telecom.Conference conference, android.telecom.DisconnectCause disconnectCause) {
            android.telecom.ConnectionService.this.mAdapter.setDisconnected((java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference), disconnectCause);
        }

        @Override // android.telecom.Conference.Listener
        public void onConnectionAdded(android.telecom.Conference conference, android.telecom.Connection connection) {
        }

        @Override // android.telecom.Conference.Listener
        public void onConnectionRemoved(android.telecom.Conference conference, android.telecom.Connection connection) {
        }

        @Override // android.telecom.Conference.Listener
        public void onConferenceableConnectionsChanged(android.telecom.Conference conference, java.util.List<android.telecom.Connection> list) {
            android.telecom.ConnectionService.this.mAdapter.setConferenceableConnections((java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference), android.telecom.ConnectionService.this.createConnectionIdList(list));
        }

        @Override // android.telecom.Conference.Listener
        public void onDestroyed(android.telecom.Conference conference) {
            android.telecom.ConnectionService.this.removeConference(conference);
        }

        @Override // android.telecom.Conference.Listener
        public void onConnectionCapabilitiesChanged(android.telecom.Conference conference, int i) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            android.telecom.Log.d(this, "call capabilities: conference: %s", android.telecom.Connection.capabilitiesToString(i));
            android.telecom.ConnectionService.this.mAdapter.setConnectionCapabilities(str, i);
        }

        @Override // android.telecom.Conference.Listener
        public void onConnectionPropertiesChanged(android.telecom.Conference conference, int i) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            android.telecom.Log.d(this, "call capabilities: conference: %s", android.telecom.Connection.propertiesToString(i));
            android.telecom.ConnectionService.this.mAdapter.setConnectionProperties(str, i);
        }

        @Override // android.telecom.Conference.Listener
        public void onVideoStateChanged(android.telecom.Conference conference, int i) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            android.telecom.Log.d(this, "onVideoStateChanged set video state %d", java.lang.Integer.valueOf(i));
            android.telecom.ConnectionService.this.mAdapter.setVideoState(str, i);
        }

        @Override // android.telecom.Conference.Listener
        public void onVideoProviderChanged(android.telecom.Conference conference, android.telecom.Connection.VideoProvider videoProvider) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            android.telecom.Log.d(this, "onVideoProviderChanged: Connection: %s, VideoProvider: %s", conference, videoProvider);
            android.telecom.ConnectionService.this.mAdapter.setVideoProvider(str, videoProvider);
        }

        @Override // android.telecom.Conference.Listener
        public void onStatusHintsChanged(android.telecom.Conference conference, android.telecom.StatusHints statusHints) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.setStatusHints(str, statusHints);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onExtrasChanged(android.telecom.Conference conference, android.os.Bundle bundle) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.putExtras(str, bundle);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onExtrasRemoved(android.telecom.Conference conference, java.util.List<java.lang.String> list) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.removeExtras(str, list);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onConferenceStateChanged(android.telecom.Conference conference, boolean z) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.setConferenceState(str, z);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onCallDirectionChanged(android.telecom.Conference conference, int i) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.setCallDirection(str, i);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onAddressChanged(android.telecom.Conference conference, android.net.Uri uri, int i) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.setAddress(str, uri, i);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onCallerDisplayNameChanged(android.telecom.Conference conference, java.lang.String str, int i) {
            java.lang.String str2 = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            if (str2 != null) {
                android.telecom.ConnectionService.this.mAdapter.setCallerDisplayName(str2, str, i);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onConnectionEvent(android.telecom.Conference conference, java.lang.String str, android.os.Bundle bundle) {
            java.lang.String str2 = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            if (str2 != null) {
                android.telecom.ConnectionService.this.mAdapter.onConnectionEvent(str2, str, bundle);
            }
        }

        @Override // android.telecom.Conference.Listener
        public void onRingbackRequested(android.telecom.Conference conference, boolean z) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
            android.telecom.Log.d(this, "Adapter conference onRingback %b", java.lang.Boolean.valueOf(z));
            android.telecom.ConnectionService.this.mAdapter.setRingbackRequested(str, z);
        }
    };
    private final android.telecom.Connection.Listener mConnectionListener = new android.telecom.Connection.Listener() { // from class: android.telecom.ConnectionService.4
        @Override // android.telecom.Connection.Listener
        public void onStateChanged(android.telecom.Connection connection, int i) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            android.telecom.Log.d(this, "Adapter set state %s %s", str, android.telecom.Connection.stateToString(i));
            switch (i) {
                case 2:
                    android.telecom.ConnectionService.this.mAdapter.setRinging(str);
                    break;
                case 3:
                    android.telecom.ConnectionService.this.mAdapter.setDialing(str);
                    break;
                case 4:
                    android.telecom.ConnectionService.this.mAdapter.setActive(str);
                    break;
                case 5:
                    android.telecom.ConnectionService.this.mAdapter.setOnHold(str);
                    break;
                case 7:
                    android.telecom.ConnectionService.this.mAdapter.setPulling(str);
                    break;
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onDisconnected(android.telecom.Connection connection, android.telecom.DisconnectCause disconnectCause) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            android.telecom.Log.d(this, "Adapter set disconnected %s", disconnectCause);
            android.telecom.ConnectionService.this.mAdapter.setDisconnected(str, disconnectCause);
        }

        @Override // android.telecom.Connection.Listener
        public void onVideoStateChanged(android.telecom.Connection connection, int i) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            android.telecom.Log.d(this, "Adapter set video state %d", java.lang.Integer.valueOf(i));
            android.telecom.ConnectionService.this.mAdapter.setVideoState(str, i);
        }

        @Override // android.telecom.Connection.Listener
        public void onAddressChanged(android.telecom.Connection connection, android.net.Uri uri, int i) {
            android.telecom.ConnectionService.this.mAdapter.setAddress((java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection), uri, i);
        }

        @Override // android.telecom.Connection.Listener
        public void onCallerDisplayNameChanged(android.telecom.Connection connection, java.lang.String str, int i) {
            android.telecom.ConnectionService.this.mAdapter.setCallerDisplayName((java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection), str, i);
        }

        @Override // android.telecom.Connection.Listener
        public void onDestroyed(android.telecom.Connection connection) {
            android.telecom.ConnectionService.this.removeConnection(connection);
        }

        @Override // android.telecom.Connection.Listener
        public void onPostDialWait(android.telecom.Connection connection, java.lang.String str) {
            java.lang.String str2 = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            android.telecom.Log.d(this, "Adapter onPostDialWait %s, %s", connection, str);
            android.telecom.ConnectionService.this.mAdapter.onPostDialWait(str2, str);
        }

        @Override // android.telecom.Connection.Listener
        public void onPostDialChar(android.telecom.Connection connection, char c) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            android.telecom.Log.d(this, "Adapter onPostDialChar %s, %s", connection, java.lang.Character.valueOf(c));
            android.telecom.ConnectionService.this.mAdapter.onPostDialChar(str, c);
        }

        @Override // android.telecom.Connection.Listener
        public void onRingbackRequested(android.telecom.Connection connection, boolean z) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            android.telecom.Log.d(this, "Adapter onRingback %b", java.lang.Boolean.valueOf(z));
            android.telecom.ConnectionService.this.mAdapter.setRingbackRequested(str, z);
        }

        @Override // android.telecom.Connection.Listener
        public void onConnectionCapabilitiesChanged(android.telecom.Connection connection, int i) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            android.telecom.Log.d(this, "capabilities: parcelableconnection: %s", android.telecom.Connection.capabilitiesToString(i));
            android.telecom.ConnectionService.this.mAdapter.setConnectionCapabilities(str, i);
        }

        @Override // android.telecom.Connection.Listener
        public void onConnectionPropertiesChanged(android.telecom.Connection connection, int i) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            android.telecom.Log.d(this, "properties: parcelableconnection: %s", android.telecom.Connection.propertiesToString(i));
            android.telecom.ConnectionService.this.mAdapter.setConnectionProperties(str, i);
        }

        @Override // android.telecom.Connection.Listener
        public void onVideoProviderChanged(android.telecom.Connection connection, android.telecom.Connection.VideoProvider videoProvider) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            android.telecom.Log.d(this, "onVideoProviderChanged: Connection: %s, VideoProvider: %s", connection, videoProvider);
            android.telecom.ConnectionService.this.mAdapter.setVideoProvider(str, videoProvider);
        }

        @Override // android.telecom.Connection.Listener
        public void onAudioModeIsVoipChanged(android.telecom.Connection connection, boolean z) {
            android.telecom.ConnectionService.this.mAdapter.setIsVoipAudioMode((java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection), z);
        }

        @Override // android.telecom.Connection.Listener
        public void onStatusHintsChanged(android.telecom.Connection connection, android.telecom.StatusHints statusHints) {
            android.telecom.ConnectionService.this.mAdapter.setStatusHints((java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection), statusHints);
        }

        @Override // android.telecom.Connection.Listener
        public void onConferenceablesChanged(android.telecom.Connection connection, java.util.List<android.telecom.Conferenceable> list) {
            android.telecom.ConnectionService.this.mAdapter.setConferenceableConnections((java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection), android.telecom.ConnectionService.this.createIdList(list));
        }

        @Override // android.telecom.Connection.Listener
        public void onConferenceChanged(android.telecom.Connection connection, android.telecom.Conference conference) {
            java.lang.String str;
            java.lang.String str2 = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str2 != null) {
                if (conference == null) {
                    str = null;
                } else {
                    str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConference.get(conference);
                }
                android.telecom.ConnectionService.this.mAdapter.setIsConferenced(str2, str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onConferenceMergeFailed(android.telecom.Connection connection) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.onConferenceMergeFailed(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onExtrasChanged(android.telecom.Connection connection, android.os.Bundle bundle) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.putExtras(str, bundle);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onExtrasRemoved(android.telecom.Connection connection, java.util.List<java.lang.String> list) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.removeExtras(str, list);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onConnectionEvent(android.telecom.Connection connection, java.lang.String str, android.os.Bundle bundle) {
            java.lang.String str2 = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str2 != null) {
                android.telecom.ConnectionService.this.mAdapter.onConnectionEvent(str2, str, bundle);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onAudioRouteChanged(android.telecom.Connection connection, int i, java.lang.String str) {
            java.lang.String str2 = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str2 != null) {
                android.telecom.ConnectionService.this.mAdapter.setAudioRoute(str2, i, str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onRttInitiationSuccess(android.telecom.Connection connection) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.onRttInitiationSuccess(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onRttInitiationFailure(android.telecom.Connection connection, int i) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.onRttInitiationFailure(str, i);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onRttSessionRemotelyTerminated(android.telecom.Connection connection) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.onRttSessionRemotelyTerminated(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onRemoteRttRequest(android.telecom.Connection connection) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.onRemoteRttRequest(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onPhoneAccountChanged(android.telecom.Connection connection, android.telecom.PhoneAccountHandle phoneAccountHandle) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.onPhoneAccountChanged(str, phoneAccountHandle);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onConnectionTimeReset(android.telecom.Connection connection) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.resetConnectionTime(str);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onEndpointChanged(android.telecom.Connection connection, android.telecom.CallEndpoint callEndpoint, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallEndpointException> outcomeReceiver) {
            java.lang.String str = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str != null) {
                android.telecom.ConnectionService.this.mAdapter.requestCallEndpointChange(str, callEndpoint, executor, outcomeReceiver);
            }
        }

        @Override // android.telecom.Connection.Listener
        public void onQueryLocation(android.telecom.Connection connection, long j, java.lang.String str, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.location.Location, android.telecom.QueryLocationException> outcomeReceiver) {
            java.lang.String str2 = (java.lang.String) android.telecom.ConnectionService.this.mIdByConnection.get(connection);
            if (str2 != null) {
                android.telecom.ConnectionService.this.mAdapter.queryLocation(str2, j, str, executor, outcomeReceiver);
            }
        }
    };

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        onBindClient(intent);
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        endAllConnections();
        return super.onUnbind(intent);
    }

    public void onBindClient(android.content.Intent intent) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createConference(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, boolean z2) {
        android.telecom.Conference onCreateIncomingConference = z ? onCreateIncomingConference(phoneAccountHandle, connectionRequest) : onCreateOutgoingConference(phoneAccountHandle, connectionRequest);
        android.telecom.Log.d(this, "createConference, conference: %s", onCreateIncomingConference);
        if (onCreateIncomingConference == null) {
            android.telecom.Log.i(this, "createConference, implementation returned null conference.", new java.lang.Object[0]);
            onCreateIncomingConference = android.telecom.Conference.createFailedConference(new android.telecom.DisconnectCause(1, "IMPL_RETURNED_NULL_CONFERENCE"), connectionRequest.getAccountHandle());
        }
        android.os.Bundle extras = connectionRequest.getExtras();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(android.telecom.Connection.EXTRA_ORIGINAL_CONNECTION_ID, str);
        if (extras != null && extras.containsKey(android.telecom.Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME)) {
            bundle.putString(android.telecom.Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME, extras.getString(android.telecom.Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME));
            bundle.putParcelable(android.telecom.Connection.EXTRA_REMOTE_PHONE_ACCOUNT_HANDLE, connectionRequest.getAccountHandle());
        }
        onCreateIncomingConference.putExtras(bundle);
        this.mConferenceById.put(str, onCreateIncomingConference);
        this.mIdByConference.put(onCreateIncomingConference, str);
        onCreateIncomingConference.addListener(this.mConferenceListener);
        android.telecom.ParcelableConference build = new android.telecom.ParcelableConference.Builder(connectionRequest.getAccountHandle(), onCreateIncomingConference.getState()).setConnectionCapabilities(onCreateIncomingConference.getConnectionCapabilities()).setConnectionProperties(onCreateIncomingConference.getConnectionProperties()).setVideoAttributes(onCreateIncomingConference.getVideoProvider() == null ? null : onCreateIncomingConference.getVideoProvider().getInterface(), onCreateIncomingConference.getVideoState()).setConnectTimeMillis(onCreateIncomingConference.getConnectTimeMillis(), onCreateIncomingConference.getConnectionStartElapsedRealtimeMillis()).setStatusHints(onCreateIncomingConference.getStatusHints()).setExtras(onCreateIncomingConference.getExtras()).setAddress(onCreateIncomingConference.getAddress(), onCreateIncomingConference.getAddressPresentation()).setCallerDisplayName(onCreateIncomingConference.getCallerDisplayName(), onCreateIncomingConference.getCallerDisplayNamePresentation()).setDisconnectCause(onCreateIncomingConference.getDisconnectCause()).setRingbackRequested(onCreateIncomingConference.isRingbackRequested()).build();
        if (onCreateIncomingConference.getState() != 6) {
            onCreateIncomingConference.setTelecomCallId(str);
            this.mAdapter.setVideoProvider(str, onCreateIncomingConference.getVideoProvider());
            this.mAdapter.setVideoState(str, onCreateIncomingConference.getVideoState());
            onConferenceAdded(onCreateIncomingConference);
        }
        android.telecom.Log.d(this, "createConference, calling handleCreateConferenceSuccessful %s", str);
        this.mAdapter.handleCreateConferenceComplete(str, connectionRequest, build);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z, boolean z2) {
        android.telecom.Connection onCreateIncomingConnection;
        android.telecom.Connection connection;
        android.telecom.PhoneAccountHandle phoneAccountHandle2;
        android.telecom.Connection onCreateIncomingHandoverConnection;
        boolean z3 = connectionRequest.getExtras() != null && connectionRequest.getExtras().getBoolean("android.telecom.extra.IS_HANDOVER", false);
        boolean z4 = connectionRequest.getExtras() != null && connectionRequest.getExtras().getBoolean(android.telecom.TelecomManager.EXTRA_IS_HANDOVER_CONNECTION, false);
        android.telecom.Log.i(this, "createConnection, callManagerAccount: %s, callId: %s, request: %s, isIncoming: %b, isUnknown: %b, isLegacyHandover: %b, isHandover: %b,  addSelfManaged: %b", phoneAccountHandle, str, connectionRequest, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), java.lang.Boolean.valueOf(z3), java.lang.Boolean.valueOf(z4), java.lang.Boolean.valueOf(connectionRequest.getExtras() != null && connectionRequest.getExtras().getBoolean(android.telecom.PhoneAccount.EXTRA_ADD_SELF_MANAGED_CALLS_TO_INCALLSERVICE, true)));
        if (z4) {
            if (connectionRequest.getExtras() != null) {
                phoneAccountHandle2 = (android.telecom.PhoneAccountHandle) connectionRequest.getExtras().getParcelable(android.telecom.TelecomManager.EXTRA_HANDOVER_FROM_PHONE_ACCOUNT, android.telecom.PhoneAccountHandle.class);
            } else {
                phoneAccountHandle2 = null;
            }
            if (!z) {
                onCreateIncomingHandoverConnection = onCreateOutgoingHandoverConnection(phoneAccountHandle2, connectionRequest);
            } else {
                onCreateIncomingHandoverConnection = onCreateIncomingHandoverConnection(phoneAccountHandle2, connectionRequest);
            }
            connection = onCreateIncomingHandoverConnection;
        } else {
            if (z2) {
                onCreateIncomingConnection = onCreateUnknownConnection(phoneAccountHandle, connectionRequest);
            } else {
                onCreateIncomingConnection = z ? onCreateIncomingConnection(phoneAccountHandle, connectionRequest) : onCreateOutgoingConnection(phoneAccountHandle, connectionRequest);
            }
            connection = onCreateIncomingConnection;
        }
        android.telecom.Log.d(this, "createConnection, connection: %s", connection);
        if (connection == null) {
            android.telecom.Log.i(this, "createConnection, implementation returned null connection.", new java.lang.Object[0]);
            connection = android.telecom.Connection.createFailedConnection(new android.telecom.DisconnectCause(1, "IMPL_RETURNED_NULL_CONNECTION"));
        } else {
            try {
                android.os.Bundle extras = connectionRequest.getExtras();
                if (extras != null && extras.containsKey(android.telecom.Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME)) {
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putString(android.telecom.Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME, extras.getString(android.telecom.Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME));
                    bundle.putParcelable(android.telecom.Connection.EXTRA_REMOTE_PHONE_ACCOUNT_HANDLE, connectionRequest.getAccountHandle());
                    connection.putExtras(bundle);
                }
            } catch (java.lang.UnsupportedOperationException e) {
            }
        }
        boolean z5 = (connection.getConnectionProperties() & 128) == 128;
        if (z5) {
            connection.setAudioModeIsVoip(true);
        }
        connection.setTelecomCallId(str);
        android.telecom.PhoneAccountHandle accountHandle = connection.getPhoneAccountHandle() == null ? connectionRequest.getAccountHandle() : connection.getPhoneAccountHandle();
        if (connection.getState() != 6) {
            addConnection(accountHandle, str, connection);
        }
        android.net.Uri address = connection.getAddress();
        android.telecom.Log.v(this, "createConnection, number: %s, state: %s, capabilities: %s, properties: %s", android.telecom.Connection.toLogSafePhoneNumber(address == null ? "null" : address.getSchemeSpecificPart()), android.telecom.Connection.stateToString(connection.getState()), android.telecom.Connection.capabilitiesToString(connection.getConnectionCapabilities()), android.telecom.Connection.propertiesToString(connection.getConnectionProperties()));
        android.telecom.Log.d(this, "createConnection, calling handleCreateConnectionSuccessful %s", str);
        this.mAdapter.handleCreateConnectionComplete(str, connectionRequest, new android.telecom.ParcelableConnection(accountHandle, connection.getState(), connection.getConnectionCapabilities(), connection.getConnectionProperties(), connection.getSupportedAudioRoutes(), connection.getAddress(), connection.getAddressPresentation(), connection.getCallerDisplayName(), connection.getCallerDisplayNamePresentation(), connection.getVideoProvider() != null ? connection.getVideoProvider().getInterface() : null, connection.getVideoState(), connection.isRingbackRequested(), connection.getAudioModeIsVoip(), connection.getConnectTimeMillis(), connection.getConnectionStartElapsedRealtimeMillis(), connection.getStatusHints(), connection.getDisconnectCause(), createIdList(connection.getConferenceables()), connection.getExtras(), connection.getCallerNumberVerificationStatus()));
        if (z && connectionRequest.shouldShowIncomingCallUi() && z5) {
            connection.onShowIncomingCallUi();
        }
        if (z2) {
            triggerConferenceRecalculate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createConnectionFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z) {
        android.telecom.Log.i(this, "createConnectionFailed %s", str);
        if (z) {
            onCreateIncomingConnectionFailed(phoneAccountHandle, connectionRequest);
        } else {
            onCreateOutgoingConnectionFailed(phoneAccountHandle, connectionRequest);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createConferenceFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.ConnectionRequest connectionRequest, boolean z) {
        android.telecom.Log.i(this, "createConferenceFailed %s", str);
        if (z) {
            onCreateIncomingConferenceFailed(phoneAccountHandle, connectionRequest);
        } else {
            onCreateOutgoingConferenceFailed(phoneAccountHandle, connectionRequest);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handoverFailed(java.lang.String str, android.telecom.ConnectionRequest connectionRequest, int i) {
        android.telecom.Log.i(this, "handoverFailed %s", str);
        onHandoverFailed(connectionRequest, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCreateConnectionComplete(java.lang.String str) {
        android.telecom.Log.i(this, "notifyCreateConnectionComplete %s", str);
        if (str == null) {
            android.telecom.Log.w(this, "notifyCreateConnectionComplete: callId is null.", new java.lang.Object[0]);
        } else {
            onCreateConnectionComplete(findConnectionForAction(str, "notifyCreateConnectionComplete"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCreateConferenceComplete(java.lang.String str) {
        android.telecom.Log.i(this, "notifyCreateConferenceComplete %s", str);
        if (str == null) {
            android.telecom.Log.w(this, "notifyCreateConferenceComplete: callId is null.", new java.lang.Object[0]);
        } else {
            onCreateConferenceComplete(findConferenceForAction(str, "notifyCreateConferenceComplete"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abort(java.lang.String str) {
        android.telecom.Log.i(this, "abort %s", str);
        findConnectionForAction(str, "abort").onAbort();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void answerVideo(java.lang.String str, int i) {
        android.telecom.Log.i(this, "answerVideo %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "answer").onAnswer(i);
        } else {
            findConferenceForAction(str, "answer").onAnswer(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void answer(java.lang.String str) {
        android.telecom.Log.i(this, "answer %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "answer").onAnswer();
        } else {
            findConferenceForAction(str, "answer").onAnswer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deflect(java.lang.String str, android.net.Uri uri) {
        android.telecom.Log.i(this, "deflect %s", str);
        findConnectionForAction(str, "deflect").onDeflect(uri);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reject(java.lang.String str) {
        android.telecom.Log.i(this, "reject %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "reject").onReject();
        } else {
            findConferenceForAction(str, "reject").onReject();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reject(java.lang.String str, java.lang.String str2) {
        android.telecom.Log.i(this, "reject %s with message", str);
        findConnectionForAction(str, "reject").onReject(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reject(java.lang.String str, int i) {
        android.telecom.Log.i(this, "reject %s with reason %d", str, java.lang.Integer.valueOf(i));
        findConnectionForAction(str, "reject").onReject(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void transfer(java.lang.String str, android.net.Uri uri, boolean z) {
        android.telecom.Log.i(this, "transfer %s", str);
        findConnectionForAction(str, "transfer").onTransfer(uri, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void consultativeTransfer(java.lang.String str, java.lang.String str2) {
        android.telecom.Log.i(this, "consultativeTransfer %s", str);
        findConnectionForAction(str, "consultativeTransfer").onTransfer(findConnectionForAction(str2, " consultativeTransfer"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void silence(java.lang.String str) {
        android.telecom.Log.i(this, "silence %s", str);
        findConnectionForAction(str, "silence").onSilence();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnect(java.lang.String str) {
        android.telecom.Log.i(this, "disconnect %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, android.media.MediaMetrics.Value.DISCONNECT).onDisconnect();
        } else {
            findConferenceForAction(str, android.media.MediaMetrics.Value.DISCONNECT).onDisconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hold(java.lang.String str) {
        android.telecom.Log.i(this, "hold %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "hold").onHold();
        } else {
            findConferenceForAction(str, "hold").onHold();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unhold(java.lang.String str) {
        android.telecom.Log.i(this, "unhold %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "unhold").onUnhold();
        } else {
            findConferenceForAction(str, "unhold").onUnhold();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCallAudioStateChanged(java.lang.String str, android.telecom.CallAudioState callAudioState) {
        android.telecom.Log.i(this, "onAudioStateChanged %s %s", str, callAudioState);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onCallAudioStateChanged").setCallAudioState(callAudioState);
        } else {
            findConferenceForAction(str, "onCallAudioStateChanged").setCallAudioState(callAudioState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCallEndpointChanged(java.lang.String str, android.telecom.CallEndpoint callEndpoint) {
        android.telecom.Log.i(this, "onCallEndpointChanged %s %s", str, callEndpoint);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onCallEndpointChanged").setCallEndpoint(callEndpoint);
        } else {
            findConferenceForAction(str, "onCallEndpointChanged").setCallEndpoint(callEndpoint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAvailableCallEndpointsChanged(java.lang.String str, java.util.List<android.telecom.CallEndpoint> list) {
        android.telecom.Log.i(this, "onAvailableCallEndpointsChanged %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onAvailableCallEndpointsChanged").setAvailableCallEndpoints(list);
        } else {
            findConferenceForAction(str, "onAvailableCallEndpointsChanged").setAvailableCallEndpoints(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMuteStateChanged(java.lang.String str, boolean z) {
        android.telecom.Log.i(this, "onMuteStateChanged %s %s", str, java.lang.Boolean.valueOf(z));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onMuteStateChanged").setMuteState(z);
        } else {
            findConferenceForAction(str, "onMuteStateChanged").setMuteState(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUsingAlternativeUi(java.lang.String str, boolean z) {
        android.telecom.Log.i(this, "onUsingAlternativeUi %s %s", str, java.lang.Boolean.valueOf(z));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onUsingAlternativeUi").onUsingAlternativeUi(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTrackedByNonUiService(java.lang.String str, boolean z) {
        android.telecom.Log.i(this, "onTrackedByNonUiService %s %s", str, java.lang.Boolean.valueOf(z));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "onTrackedByNonUiService").onTrackedByNonUiService(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playDtmfTone(java.lang.String str, char c) {
        android.telecom.Log.i(this, "playDtmfTone %s %s", str, android.telecom.Log.pii(java.lang.Character.valueOf(c)));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "playDtmfTone").onPlayDtmfTone(c);
        } else {
            findConferenceForAction(str, "playDtmfTone").onPlayDtmfTone(c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopDtmfTone(java.lang.String str) {
        android.telecom.Log.i(this, "stopDtmfTone %s", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "stopDtmfTone").onStopDtmfTone();
        } else {
            findConferenceForAction(str, "stopDtmfTone").onStopDtmfTone();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void conference(java.lang.String str, java.lang.String str2) {
        android.telecom.Log.i(this, "conference %s, %s", str, str2);
        android.telecom.Connection findConnectionForAction = findConnectionForAction(str2, android.telephony.ims.ImsCallProfile.EXTRA_CONFERENCE_DEPRECATED);
        android.telecom.Conference nullConference = getNullConference();
        if (findConnectionForAction == getNullConnection() && (nullConference = findConferenceForAction(str2, android.telephony.ims.ImsCallProfile.EXTRA_CONFERENCE_DEPRECATED)) == getNullConference()) {
            android.telecom.Log.w(this, "Connection2 or Conference2 missing in conference request %s.", str2);
            return;
        }
        android.telecom.Connection findConnectionForAction2 = findConnectionForAction(str, android.telephony.ims.ImsCallProfile.EXTRA_CONFERENCE_DEPRECATED);
        if (findConnectionForAction2 == getNullConnection()) {
            android.telecom.Conference findConferenceForAction = findConferenceForAction(str, "addConnection");
            if (findConferenceForAction == getNullConference()) {
                android.telecom.Log.w(this, "Connection1 or Conference1 missing in conference request %s.", str);
                return;
            } else if (findConnectionForAction != getNullConnection()) {
                findConferenceForAction.onMerge(findConnectionForAction);
                return;
            } else {
                android.telecom.Log.wtf(this, "There can only be one conference and an attempt was made to merge two conferences.", new java.lang.Object[0]);
                return;
            }
        }
        if (nullConference != getNullConference()) {
            nullConference.onMerge(findConnectionForAction2);
        } else {
            onConference(findConnectionForAction2, findConnectionForAction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void splitFromConference(java.lang.String str) {
        android.telecom.Log.i(this, "splitFromConference(%s)", str);
        android.telecom.Connection findConnectionForAction = findConnectionForAction(str, "splitFromConference");
        if (findConnectionForAction == getNullConnection()) {
            android.telecom.Log.w(this, "Connection missing in conference request %s.", str);
            return;
        }
        android.telecom.Conference conference = findConnectionForAction.getConference();
        if (conference != null) {
            conference.onSeparate(findConnectionForAction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeConference(java.lang.String str) {
        android.telecom.Log.i(this, "mergeConference(%s)", str);
        android.telecom.Conference findConferenceForAction = findConferenceForAction(str, "mergeConference");
        if (findConferenceForAction != null) {
            findConferenceForAction.onMerge();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void swapConference(java.lang.String str) {
        android.telecom.Log.i(this, "swapConference(%s)", str);
        android.telecom.Conference findConferenceForAction = findConferenceForAction(str, "swapConference");
        if (findConferenceForAction != null) {
            findConferenceForAction.onSwap();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addConferenceParticipants(java.lang.String str, java.util.List<android.net.Uri> list) {
        android.telecom.Log.i(this, "addConferenceParticipants(%s)", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "addConferenceParticipants").onAddConferenceParticipants(list);
        } else {
            findConferenceForAction(str, "addConferenceParticipants").onAddConferenceParticipants(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pullExternalCall(java.lang.String str) {
        android.telecom.Log.i(this, "pullExternalCall(%s)", str);
        android.telecom.Connection findConnectionForAction = findConnectionForAction(str, "pullExternalCall");
        if (findConnectionForAction != null) {
            findConnectionForAction.onPullExternalCall();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCallEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        android.telecom.Log.i(this, "sendCallEvent(%s, %s)", str, str2);
        android.telecom.Connection findConnectionForAction = findConnectionForAction(str, "sendCallEvent");
        if (findConnectionForAction != null) {
            findConnectionForAction.onCallEvent(str2, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCallFilteringCompleted(java.lang.String str, android.telecom.Connection.CallFilteringCompletionInfo callFilteringCompletionInfo) {
        android.telecom.Log.i(this, "onCallFilteringCompleted(%s, %s)", str, callFilteringCompletionInfo);
        android.telecom.Connection findConnectionForAction = findConnectionForAction(str, "onCallFilteringCompleted");
        if (findConnectionForAction != null) {
            findConnectionForAction.onCallFilteringCompleted(callFilteringCompletionInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyHandoverComplete(java.lang.String str) {
        android.telecom.Log.i(this, "notifyHandoverComplete(%s)", str);
        android.telecom.Connection findConnectionForAction = findConnectionForAction(str, "notifyHandoverComplete");
        if (findConnectionForAction != null) {
            findConnectionForAction.onHandoverComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleExtrasChanged(java.lang.String str, android.os.Bundle bundle) {
        android.telecom.Log.i(this, "handleExtrasChanged(%s, %s)", str, bundle);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "handleExtrasChanged").handleExtrasChanged(bundle);
        } else if (this.mConferenceById.containsKey(str)) {
            findConferenceForAction(str, "handleExtrasChanged").handleExtrasChanged(bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRtt(java.lang.String str, android.telecom.Connection.RttTextStream rttTextStream) {
        android.telecom.Log.i(this, "startRtt(%s)", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "startRtt").onStartRtt(rttTextStream);
        } else if (this.mConferenceById.containsKey(str)) {
            android.telecom.Log.w(this, "startRtt called on a conference.", new java.lang.Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopRtt(java.lang.String str) {
        android.telecom.Log.i(this, "stopRtt(%s)", str);
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "stopRtt").onStopRtt();
        } else if (this.mConferenceById.containsKey(str)) {
            android.telecom.Log.w(this, "stopRtt called on a conference.", new java.lang.Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRttUpgradeResponse(java.lang.String str, android.telecom.Connection.RttTextStream rttTextStream) {
        android.telecom.Log.i(this, "handleRttUpgradeResponse(%s, %s)", str, java.lang.Boolean.valueOf(rttTextStream == null));
        if (this.mConnectionById.containsKey(str)) {
            findConnectionForAction(str, "handleRttUpgradeResponse").handleRttUpgradeResponse(rttTextStream);
        } else if (this.mConferenceById.containsKey(str)) {
            android.telecom.Log.w(this, "handleRttUpgradeResponse called on a conference.", new java.lang.Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPostDialContinue(java.lang.String str, boolean z) {
        android.telecom.Log.i(this, "onPostDialContinue(%s)", str);
        findConnectionForAction(str, "stopDtmfTone").onPostDialContinue(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAdapterAttached() {
        if (this.mAreAccountsInitialized) {
            return;
        }
        this.mAdapter.queryRemoteConnectionServices(new com.android.internal.telecom.RemoteServiceCallback.Stub() { // from class: android.telecom.ConnectionService.5
            @Override // com.android.internal.telecom.RemoteServiceCallback
            public void onResult(final java.util.List<android.content.ComponentName> list, final java.util.List<android.os.IBinder> list2) {
                android.telecom.ConnectionService.this.mHandler.post(new android.telecom.Logging.Runnable("oAA.qRCS.oR", null) { // from class: android.telecom.ConnectionService.5.1
                    @Override // android.telecom.Logging.Runnable
                    public void loggedRun() {
                        for (int i = 0; i < list.size() && i < list2.size(); i++) {
                            android.telecom.ConnectionService.this.mRemoteConnectionManager.addConnectionService((android.content.ComponentName) list.get(i), com.android.internal.telecom.IConnectionService.Stub.asInterface((android.os.IBinder) list2.get(i)));
                        }
                        android.telecom.ConnectionService.this.onAccountsInitialized();
                        android.telecom.Log.d(this, "remote connection services found: " + list2, new java.lang.Object[0]);
                    }
                }.prepare());
            }

            @Override // com.android.internal.telecom.RemoteServiceCallback
            public void onError() {
                android.telecom.ConnectionService.this.mHandler.post(new android.telecom.Logging.Runnable("oAA.qRCS.oE", null) { // from class: android.telecom.ConnectionService.5.2
                    @Override // android.telecom.Logging.Runnable
                    public void loggedRun() {
                        android.telecom.ConnectionService.this.mAreAccountsInitialized = true;
                    }
                }.prepare());
            }
        }, getOpPackageName());
    }

    public final android.telecom.RemoteConnection createRemoteIncomingConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return this.mRemoteConnectionManager.createRemoteConnection(phoneAccountHandle, connectionRequest, true);
    }

    public final android.telecom.RemoteConnection createRemoteOutgoingConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return this.mRemoteConnectionManager.createRemoteConnection(phoneAccountHandle, connectionRequest, false);
    }

    public final android.telecom.RemoteConference createRemoteIncomingConference(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return this.mRemoteConnectionManager.createRemoteConference(phoneAccountHandle, connectionRequest, true);
    }

    public final android.telecom.RemoteConference createRemoteOutgoingConference(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return this.mRemoteConnectionManager.createRemoteConference(phoneAccountHandle, connectionRequest, false);
    }

    public final void conferenceRemoteConnections(android.telecom.RemoteConnection remoteConnection, android.telecom.RemoteConnection remoteConnection2) {
        this.mRemoteConnectionManager.conferenceRemoteConnections(remoteConnection, remoteConnection2);
    }

    public final void addConference(android.telecom.Conference conference) {
        android.telecom.Log.d(this, "addConference: conference=%s", conference);
        java.lang.String addConferenceInternal = addConferenceInternal(conference);
        if (addConferenceInternal != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList(2);
            for (android.telecom.Connection connection : conference.getConnections()) {
                if (this.mIdByConnection.containsKey(connection)) {
                    arrayList.add(this.mIdByConnection.get(connection));
                }
            }
            conference.setTelecomCallId(addConferenceInternal);
            this.mAdapter.addConferenceCall(addConferenceInternal, new android.telecom.ParcelableConference.Builder(conference.getPhoneAccountHandle(), conference.getState()).setConnectionCapabilities(conference.getConnectionCapabilities()).setConnectionProperties(conference.getConnectionProperties()).setConnectionIds(arrayList).setVideoAttributes(conference.getVideoProvider() == null ? null : conference.getVideoProvider().getInterface(), conference.getVideoState()).setConnectTimeMillis(conference.getConnectTimeMillis(), conference.getConnectionStartElapsedRealtimeMillis()).setStatusHints(conference.getStatusHints()).setExtras(conference.getExtras()).setAddress(conference.getAddress(), conference.getAddressPresentation()).setCallerDisplayName(conference.getCallerDisplayName(), conference.getCallerDisplayNamePresentation()).setDisconnectCause(conference.getDisconnectCause()).setRingbackRequested(conference.isRingbackRequested()).setCallDirection(conference.getCallDirection()).build());
            this.mAdapter.setVideoProvider(addConferenceInternal, conference.getVideoProvider());
            this.mAdapter.setVideoState(addConferenceInternal, conference.getVideoState());
            if (!conference.isMultiparty()) {
                this.mAdapter.setConferenceState(addConferenceInternal, conference.isMultiparty());
            }
            java.util.Iterator<android.telecom.Connection> it = conference.getConnections().iterator();
            while (it.hasNext()) {
                java.lang.String str = this.mIdByConnection.get(it.next());
                if (str != null) {
                    this.mAdapter.setIsConferenced(str, addConferenceInternal);
                }
            }
            onConferenceAdded(conference);
        }
    }

    public final void addExistingConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.Connection connection) {
        addExistingConnection(phoneAccountHandle, connection, null);
    }

    public final void connectionServiceFocusReleased() {
        this.mAdapter.onConnectionServiceFocusReleased();
    }

    @android.annotation.SystemApi
    public final void addExistingConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.Connection connection, android.telecom.Conference conference) {
        java.lang.String str;
        java.lang.String addExistingConnectionInternal = addExistingConnectionInternal(phoneAccountHandle, connection);
        if (addExistingConnectionInternal != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList(0);
            if (conference == null) {
                str = null;
            } else {
                str = this.mIdByConference.get(conference);
            }
            this.mAdapter.addExistingConnection(addExistingConnectionInternal, new android.telecom.ParcelableConnection(phoneAccountHandle, connection.getState(), connection.getConnectionCapabilities(), connection.getConnectionProperties(), connection.getSupportedAudioRoutes(), connection.getAddress(), connection.getAddressPresentation(), connection.getCallerDisplayName(), connection.getCallerDisplayNamePresentation(), connection.getVideoProvider() != null ? connection.getVideoProvider().getInterface() : null, connection.getVideoState(), connection.isRingbackRequested(), connection.getAudioModeIsVoip(), connection.getConnectTimeMillis(), connection.getConnectionStartElapsedRealtimeMillis(), connection.getStatusHints(), connection.getDisconnectCause(), arrayList, connection.getExtras(), str, connection.getCallDirection(), 0));
        }
    }

    public final java.util.Collection<android.telecom.Connection> getAllConnections() {
        return this.mConnectionById.values();
    }

    public final java.util.Collection<android.telecom.Conference> getAllConferences() {
        return this.mConferenceById.values();
    }

    public android.telecom.Connection onCreateIncomingConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return null;
    }

    public android.telecom.Conference onCreateIncomingConference(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return null;
    }

    public void onCreateConnectionComplete(android.telecom.Connection connection) {
    }

    public void onCreateConferenceComplete(android.telecom.Conference conference) {
    }

    public void onCreateIncomingConnectionFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
    }

    public void onCreateOutgoingConnectionFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
    }

    public void onCreateIncomingConferenceFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
    }

    public void onCreateOutgoingConferenceFailed(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
    }

    public void triggerConferenceRecalculate() {
    }

    public android.telecom.Connection onCreateOutgoingConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return null;
    }

    public android.telecom.Conference onCreateOutgoingConference(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return null;
    }

    public android.telecom.Connection onCreateOutgoingHandoverConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return null;
    }

    public android.telecom.Connection onCreateIncomingHandoverConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return null;
    }

    public void onHandoverFailed(android.telecom.ConnectionRequest connectionRequest, int i) {
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public android.telecom.Connection onCreateUnknownConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest) {
        return null;
    }

    public void onConference(android.telecom.Connection connection, android.telecom.Connection connection2) {
    }

    public void onConnectionAdded(android.telecom.Connection connection) {
    }

    public void onConnectionRemoved(android.telecom.Connection connection) {
    }

    public void onConferenceAdded(android.telecom.Conference conference) {
    }

    public void onConferenceRemoved(android.telecom.Conference conference) {
    }

    public void onRemoteConferenceAdded(android.telecom.RemoteConference remoteConference) {
    }

    public void onRemoteExistingConnectionAdded(android.telecom.RemoteConnection remoteConnection) {
    }

    public void onConnectionServiceFocusLost() {
    }

    public void onConnectionServiceFocusGained() {
    }

    public boolean containsConference(android.telecom.Conference conference) {
        return this.mIdByConference.containsKey(conference);
    }

    void addRemoteConference(android.telecom.RemoteConference remoteConference) {
        onRemoteConferenceAdded(remoteConference);
    }

    void addRemoteExistingConnection(android.telecom.RemoteConnection remoteConnection) {
        onRemoteExistingConnectionAdded(remoteConnection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccountsInitialized() {
        this.mAreAccountsInitialized = true;
        java.util.Iterator<java.lang.Runnable> it = this.mPreInitializationConnectionRequests.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
        this.mPreInitializationConnectionRequests.clear();
    }

    private java.lang.String addExistingConnectionInternal(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.Connection connection) {
        java.lang.String str;
        if (connection.getExtras() != null && connection.getExtras().containsKey(android.telecom.Connection.EXTRA_ORIGINAL_CONNECTION_ID)) {
            str = connection.getExtras().getString(android.telecom.Connection.EXTRA_ORIGINAL_CONNECTION_ID);
            android.telecom.Log.d(this, "addExistingConnectionInternal - conn %s reusing original id %s", connection.getTelecomCallId(), str);
        } else if (phoneAccountHandle == null) {
            str = java.util.UUID.randomUUID().toString();
        } else {
            str = phoneAccountHandle.getComponentName().getClassName() + "@" + getNextCallId();
        }
        addConnection(phoneAccountHandle, str, connection);
        return str;
    }

    private void addConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, android.telecom.Connection connection) {
        connection.setTelecomCallId(str);
        this.mConnectionById.put(str, connection);
        this.mIdByConnection.put(connection, str);
        connection.addConnectionListener(this.mConnectionListener);
        connection.setConnectionService(this);
        connection.setPhoneAccountHandle(phoneAccountHandle);
        onConnectionAdded(connection);
    }

    protected void removeConnection(android.telecom.Connection connection) {
        connection.unsetConnectionService(this);
        connection.removeConnectionListener(this.mConnectionListener);
        java.lang.String str = this.mIdByConnection.get(connection);
        if (str != null) {
            this.mConnectionById.remove(str);
            this.mIdByConnection.remove(connection);
            this.mAdapter.removeCall(str);
            onConnectionRemoved(connection);
        }
    }

    private java.lang.String addConferenceInternal(android.telecom.Conference conference) {
        java.lang.String str;
        if (conference.getExtras() != null && conference.getExtras().containsKey(android.telecom.Connection.EXTRA_ORIGINAL_CONNECTION_ID)) {
            str = conference.getExtras().getString(android.telecom.Connection.EXTRA_ORIGINAL_CONNECTION_ID);
            android.telecom.Log.d(this, "addConferenceInternal: conf %s reusing original id %s", conference.getTelecomCallId(), str);
        } else {
            str = null;
        }
        if (this.mIdByConference.containsKey(conference)) {
            android.telecom.Log.w(this, "Re-adding an existing conference: %s.", conference);
        } else if (conference != null) {
            if (str == null) {
                str = java.util.UUID.randomUUID().toString();
            }
            this.mConferenceById.put(str, conference);
            this.mIdByConference.put(conference, str);
            conference.addListener(this.mConferenceListener);
            return str;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeConference(android.telecom.Conference conference) {
        if (this.mIdByConference.containsKey(conference)) {
            conference.removeListener(this.mConferenceListener);
            java.lang.String str = this.mIdByConference.get(conference);
            this.mConferenceById.remove(str);
            this.mIdByConference.remove(conference);
            this.mAdapter.removeCall(str);
            onConferenceRemoved(conference);
        }
    }

    private android.telecom.Connection findConnectionForAction(java.lang.String str, java.lang.String str2) {
        if (str != null && this.mConnectionById.containsKey(str)) {
            return this.mConnectionById.get(str);
        }
        android.telecom.Log.w(this, "%s - Cannot find Connection %s", str2, str);
        return getNullConnection();
    }

    static synchronized android.telecom.Connection getNullConnection() {
        android.telecom.Connection connection;
        synchronized (android.telecom.ConnectionService.class) {
            if (sNullConnection == null) {
                sNullConnection = new android.telecom.Connection() { // from class: android.telecom.ConnectionService.6
                };
            }
            connection = sNullConnection;
        }
        return connection;
    }

    private android.telecom.Conference findConferenceForAction(java.lang.String str, java.lang.String str2) {
        if (this.mConferenceById.containsKey(str)) {
            return this.mConferenceById.get(str);
        }
        android.telecom.Log.w(this, "%s - Cannot find conference %s", str2, str);
        return getNullConference();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<java.lang.String> createConnectionIdList(java.util.List<android.telecom.Connection> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.telecom.Connection connection : list) {
            if (this.mIdByConnection.containsKey(connection)) {
                arrayList.add(this.mIdByConnection.get(connection));
            }
        }
        java.util.Collections.sort(arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<java.lang.String> createIdList(java.util.List<android.telecom.Conferenceable> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.telecom.Conferenceable conferenceable : list) {
            if (conferenceable instanceof android.telecom.Connection) {
                android.telecom.Connection connection = (android.telecom.Connection) conferenceable;
                if (this.mIdByConnection.containsKey(connection)) {
                    arrayList.add(this.mIdByConnection.get(connection));
                }
            } else if (conferenceable instanceof android.telecom.Conference) {
                android.telecom.Conference conference = (android.telecom.Conference) conferenceable;
                if (this.mIdByConference.containsKey(conference)) {
                    arrayList.add(this.mIdByConference.get(conference));
                }
            }
        }
        java.util.Collections.sort(arrayList);
        return arrayList;
    }

    private android.telecom.Conference getNullConference() {
        if (this.sNullConference == null) {
            this.sNullConference = new android.telecom.Conference(null) { // from class: android.telecom.ConnectionService.7
            };
        }
        return this.sNullConference;
    }

    private void endAllConnections() {
        for (android.telecom.Connection connection : this.mIdByConnection.keySet()) {
            if (connection.getConference() == null) {
                connection.onDisconnect();
            }
        }
        java.util.Iterator<android.telecom.Conference> it = this.mIdByConference.keySet().iterator();
        while (it.hasNext()) {
            it.next().onDisconnect();
        }
    }

    private int getNextCallId() {
        int i;
        synchronized (this.mIdSyncRoot) {
            i = this.mId + 1;
            this.mId = i;
        }
        return i;
    }

    public android.os.Handler getHandler() {
        return this.mHandler;
    }

    public void setReadyForTest() {
        this.mAreAccountsInitialized = true;
    }
}
