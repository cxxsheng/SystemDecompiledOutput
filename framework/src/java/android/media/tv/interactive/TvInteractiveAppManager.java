package android.media.tv.interactive;

/* loaded from: classes2.dex */
public final class TvInteractiveAppManager {
    public static final java.lang.String ACTION_APP_LINK_COMMAND = "android.media.tv.interactive.action.APP_LINK_COMMAND";
    public static final java.lang.String APP_LINK_KEY_BACK_URI = "back_uri";
    public static final java.lang.String APP_LINK_KEY_CLASS_NAME = "class_name";
    public static final java.lang.String APP_LINK_KEY_COMMAND_TYPE = "command_type";
    public static final java.lang.String APP_LINK_KEY_PACKAGE_NAME = "package_name";
    public static final java.lang.String APP_LINK_KEY_SERVICE_ID = "service_id";
    public static final int ERROR_BLOCKED = 5;
    public static final int ERROR_ENCRYPTED = 6;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_SUPPORTED = 2;
    public static final int ERROR_RESOURCE_UNAVAILABLE = 4;
    public static final int ERROR_UNKNOWN = 1;
    public static final int ERROR_UNKNOWN_CHANNEL = 7;
    public static final int ERROR_WEAK_SIGNAL = 3;
    public static final java.lang.String INTENT_KEY_BI_INTERACTIVE_APP_TYPE = "bi_interactive_app_type";
    public static final java.lang.String INTENT_KEY_BI_INTERACTIVE_APP_URI = "bi_interactive_app_uri";
    public static final java.lang.String INTENT_KEY_CHANNEL_URI = "channel_uri";
    public static final java.lang.String INTENT_KEY_COMMAND_TYPE = "command_type";
    public static final java.lang.String INTENT_KEY_INTERACTIVE_APP_SERVICE_ID = "interactive_app_id";
    public static final java.lang.String INTENT_KEY_TV_INPUT_ID = "tv_input_id";
    public static final int INTERACTIVE_APP_STATE_ERROR = 3;
    public static final int INTERACTIVE_APP_STATE_RUNNING = 2;
    public static final int INTERACTIVE_APP_STATE_STOPPED = 1;
    public static final int SERVICE_STATE_ERROR = 4;
    public static final int SERVICE_STATE_PREPARING = 2;
    public static final int SERVICE_STATE_READY = 3;
    public static final int SERVICE_STATE_UNREALIZED = 1;
    private static final java.lang.String TAG = "TvInteractiveAppManager";
    public static final int TELETEXT_APP_STATE_ERROR = 3;
    public static final int TELETEXT_APP_STATE_HIDE = 2;
    public static final int TELETEXT_APP_STATE_SHOW = 1;
    private int mNextSeq;
    private final android.media.tv.interactive.ITvInteractiveAppManager mService;
    private final int mUserId;
    private final android.util.SparseArray<android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord> mSessionCallbackRecordMap = new android.util.SparseArray<>();
    private final java.util.List<android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord> mCallbackRecords = new java.util.ArrayList();
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.media.tv.interactive.ITvInteractiveAppClient mClient = new android.media.tv.interactive.ITvInteractiveAppClient.Stub() { // from class: android.media.tv.interactive.TvInteractiveAppManager.1
        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) {
            android.media.tv.interactive.TvInteractiveAppManager.Session session;
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for " + iBinder);
                    return;
                }
                if (iBinder != null) {
                    session = new android.media.tv.interactive.TvInteractiveAppManager.Session(iBinder, inputChannel, android.media.tv.interactive.TvInteractiveAppManager.this.mService, android.media.tv.interactive.TvInteractiveAppManager.this.mUserId, i, android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap);
                } else {
                    android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.delete(i);
                    session = null;
                }
                sessionCallbackRecord.postSessionCreated(session);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionReleased(int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.delete(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq:" + i);
                } else {
                    sessionCallbackRecord.mSession.releaseInternal();
                    sessionCallbackRecord.postSessionReleased();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i5);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i5);
                } else {
                    sessionCallbackRecord.postLayoutSurface(i, i2, i3, i4);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBroadcastInfoRequest(android.media.tv.BroadcastInfoRequest broadcastInfoRequest, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postBroadcastInfoRequest(broadcastInfoRequest);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRemoveBroadcastInfo(int i, int i2) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postRemoveBroadcastInfo(i);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onCommandRequest(java.lang.String str, android.os.Bundle bundle, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postCommandRequest(str, bundle);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTimeShiftCommandRequest(java.lang.String str, android.os.Bundle bundle, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postTimeShiftCommandRequest(str, bundle);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetVideoBounds(android.graphics.Rect rect, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postSetVideoBounds(rect);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdRequest(android.media.tv.AdRequest adRequest, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postAdRequest(adRequest);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentVideoBounds(int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestCurrentVideoBounds();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelUri(int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestCurrentChannelUri();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelLcn(int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestCurrentChannelLcn();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStreamVolume(int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestStreamVolume();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTrackInfoList(int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestTrackInfoList();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSelectedTrackInfo(int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestSelectedTrackInfo();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentTvInputId(int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestCurrentTvInputId();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTimeShiftMode(int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestTimeShiftMode();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestAvailableSpeeds(int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestAvailableSpeeds();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStartRecording(java.lang.String str, android.net.Uri uri, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestStartRecording(str, uri);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStopRecording(java.lang.String str, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestStopRecording(str);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording(java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestScheduleRecording(str, str2, uri, uri2, bundle);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording2(java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle, int i2) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postRequestScheduleRecording(str, str2, uri, j, j2, i, bundle);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetTvRecordingInfo(java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postSetTvRecordingInfo(str, tvRecordingInfo);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfo(java.lang.String str, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestTvRecordingInfo(str);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfoList(int i, int i2) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postRequestTvRecordingInfoList(i);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRequestSigning(str, str2, str3, bArr);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning2(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, byte[] bArr, int i2) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postRequestSigning(str, str2, str3, i, bArr);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCertificate(java.lang.String str, int i, int i2) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postRequestCertificate(str, i);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionStateChanged(int i, int i2, int i3) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i3);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i3);
                } else {
                    sessionCallbackRecord.postSessionStateChanged(i, i2);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBiInteractiveAppCreated(android.net.Uri uri, java.lang.String str, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postBiInteractiveAppCreated(uri, str);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTeletextAppStateChanged(int i, int i2) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postTeletextAppStateChanged(i);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdBufferReady(android.media.tv.AdBuffer adBuffer, int i) {
            synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord) android.media.tv.interactive.TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postAdBufferReady(adBuffer);
                }
            }
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InteractiveAppState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TeletextAppState {
    }

    public TvInteractiveAppManager(android.media.tv.interactive.ITvInteractiveAppManager iTvInteractiveAppManager, int i) {
        this.mService = iTvInteractiveAppManager;
        this.mUserId = i;
        android.media.tv.interactive.ITvInteractiveAppManagerCallback.Stub stub = new android.media.tv.interactive.ITvInteractiveAppManagerCallback.Stub() { // from class: android.media.tv.interactive.TvInteractiveAppManager.2
            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceAdded(java.lang.String str) {
                synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.interactive.TvInteractiveAppManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord) it.next()).postInteractiveAppServiceAdded(str);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceRemoved(java.lang.String str) {
                synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.interactive.TvInteractiveAppManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord) it.next()).postInteractiveAppServiceRemoved(str);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceUpdated(java.lang.String str) {
                synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.interactive.TvInteractiveAppManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord) it.next()).postInteractiveAppServiceUpdated(str);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onTvInteractiveAppServiceInfoUpdated(android.media.tv.interactive.TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) {
                synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.interactive.TvInteractiveAppManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord) it.next()).postTvInteractiveAppServiceInfoUpdated(tvInteractiveAppServiceInfo);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onStateChanged(java.lang.String str, int i2, int i3, int i4) {
                synchronized (android.media.tv.interactive.TvInteractiveAppManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.interactive.TvInteractiveAppManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord) it.next()).postStateChanged(str, i2, i3, i4);
                    }
                }
            }
        };
        try {
            if (this.mService != null) {
                this.mService.registerCallback(stub, this.mUserId);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static abstract class TvInteractiveAppCallback {
        public void onInteractiveAppServiceAdded(java.lang.String str) {
        }

        public void onInteractiveAppServiceRemoved(java.lang.String str) {
        }

        public void onInteractiveAppServiceUpdated(java.lang.String str) {
        }

        public void onTvInteractiveAppServiceInfoUpdated(android.media.tv.interactive.TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) {
        }

        public void onTvInteractiveAppServiceStateChanged(java.lang.String str, int i, int i2, int i3) {
        }
    }

    private static final class TvInteractiveAppCallbackRecord {
        private final android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        TvInteractiveAppCallbackRecord(android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallback tvInteractiveAppCallback, java.util.concurrent.Executor executor) {
            this.mCallback = tvInteractiveAppCallback;
            this.mExecutor = executor;
        }

        public android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallback getCallback() {
            return this.mCallback;
        }

        public void postInteractiveAppServiceAdded(final java.lang.String str) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.this.mCallback.onInteractiveAppServiceAdded(str);
                }
            });
        }

        public void postInteractiveAppServiceRemoved(final java.lang.String str) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.this.mCallback.onInteractiveAppServiceRemoved(str);
                }
            });
        }

        public void postInteractiveAppServiceUpdated(final java.lang.String str) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.this.mCallback.onInteractiveAppServiceUpdated(str);
                }
            });
        }

        public void postTvInteractiveAppServiceInfoUpdated(final android.media.tv.interactive.TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.this.mCallback.onTvInteractiveAppServiceInfoUpdated(tvInteractiveAppServiceInfo);
                }
            });
        }

        public void postStateChanged(final java.lang.String str, final int i, final int i2, final int i3) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.this.mCallback.onTvInteractiveAppServiceStateChanged(str, i, i2, i3);
                }
            });
        }
    }

    public void createSession(java.lang.String str, int i, android.media.tv.interactive.TvInteractiveAppManager.SessionCallback sessionCallback, android.os.Handler handler) {
        createSessionInternal(str, i, sessionCallback, handler);
    }

    private void createSessionInternal(java.lang.String str, int i, android.media.tv.interactive.TvInteractiveAppManager.SessionCallback sessionCallback, android.os.Handler handler) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkNotNull(sessionCallback);
        com.android.internal.util.Preconditions.checkNotNull(handler);
        android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord sessionCallbackRecord = new android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord(sessionCallback, handler);
        synchronized (this.mSessionCallbackRecordMap) {
            int i2 = this.mNextSeq;
            this.mNextSeq = i2 + 1;
            this.mSessionCallbackRecordMap.put(i2, sessionCallbackRecord);
            try {
                this.mService.createSession(this.mClient, str, i, i2, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.util.List<android.media.tv.interactive.TvInteractiveAppServiceInfo> getTvInteractiveAppServiceList() {
        try {
            return this.mService.getTvInteractiveAppServiceList(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.media.tv.interactive.AppLinkInfo> getAppLinkInfoList() {
        try {
            return this.mService.getAppLinkInfoList(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerAppLinkInfo(java.lang.String str, android.media.tv.interactive.AppLinkInfo appLinkInfo) {
        try {
            this.mService.registerAppLinkInfo(str, appLinkInfo, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterAppLinkInfo(java.lang.String str, android.media.tv.interactive.AppLinkInfo appLinkInfo) {
        try {
            this.mService.unregisterAppLinkInfo(str, appLinkInfo, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendAppLinkCommand(java.lang.String str, android.os.Bundle bundle) {
        try {
            this.mService.sendAppLinkCommand(str, bundle, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(java.util.concurrent.Executor executor, android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallback tvInteractiveAppCallback) {
        com.android.internal.util.Preconditions.checkNotNull(tvInteractiveAppCallback);
        com.android.internal.util.Preconditions.checkNotNull(executor);
        synchronized (this.mLock) {
            this.mCallbackRecords.add(new android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord(tvInteractiveAppCallback, executor));
        }
    }

    public void unregisterCallback(android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallback tvInteractiveAppCallback) {
        com.android.internal.util.Preconditions.checkNotNull(tvInteractiveAppCallback);
        synchronized (this.mLock) {
            java.util.Iterator<android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord> it = this.mCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getCallback() == tvInteractiveAppCallback) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public static final class Session {
        static final int DISPATCH_HANDLED = 1;
        static final int DISPATCH_IN_PROGRESS = -1;
        static final int DISPATCH_NOT_HANDLED = 0;
        private static final long INPUT_SESSION_NOT_RESPONDING_TIMEOUT = 2500;
        private final android.media.tv.interactive.TvInteractiveAppManager.Session.InputEventHandler mHandler;
        private android.view.InputChannel mInputChannel;
        private android.media.tv.TvInputManager.Session mInputSession;
        private final android.util.Pools.Pool<android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent> mPendingEventPool;
        private final android.util.SparseArray<android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent> mPendingEvents;
        private android.media.tv.interactive.TvInteractiveAppManager.Session.TvInputEventSender mSender;
        private final int mSeq;
        private final android.media.tv.interactive.ITvInteractiveAppManager mService;
        private final android.util.SparseArray<android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord> mSessionCallbackRecordMap;
        private android.os.IBinder mToken;
        private final int mUserId;

        public interface FinishedInputEventCallback {
            void onFinishedInputEvent(java.lang.Object obj, boolean z);
        }

        private Session(android.os.IBinder iBinder, android.view.InputChannel inputChannel, android.media.tv.interactive.ITvInteractiveAppManager iTvInteractiveAppManager, int i, int i2, android.util.SparseArray<android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord> sparseArray) {
            this.mHandler = new android.media.tv.interactive.TvInteractiveAppManager.Session.InputEventHandler(android.os.Looper.getMainLooper());
            this.mPendingEventPool = new android.util.Pools.SimplePool(20);
            this.mPendingEvents = new android.util.SparseArray<>(20);
            this.mToken = iBinder;
            this.mInputChannel = inputChannel;
            this.mService = iTvInteractiveAppManager;
            this.mUserId = i;
            this.mSeq = i2;
            this.mSessionCallbackRecordMap = sparseArray;
        }

        public android.media.tv.TvInputManager.Session getInputSession() {
            return this.mInputSession;
        }

        public void setInputSession(android.media.tv.TvInputManager.Session session) {
            this.mInputSession = session;
        }

        void startInteractiveApp() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.startInteractiveApp(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void stopInteractiveApp() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopInteractiveApp(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void resetInteractiveApp() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.resetInteractiveApp(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void createBiInteractiveApp(android.net.Uri uri, android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.createBiInteractiveApp(this.mToken, uri, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void destroyBiInteractiveApp(java.lang.String str) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.destroyBiInteractiveApp(this.mToken, str, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void setTeletextAppEnabled(boolean z) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setTeletextAppEnabled(this.mToken, z, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentVideoBounds(android.graphics.Rect rect) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentVideoBounds(this.mToken, rect, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentChannelUri(android.net.Uri uri) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentChannelUri(this.mToken, uri, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentChannelLcn(int i) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentChannelLcn(this.mToken, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendStreamVolume(float f) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendStreamVolume(this.mToken, f, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTrackInfoList(this.mToken, list, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendSelectedTrackInfo(java.util.List<android.media.tv.TvTrackInfo> list) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendSelectedTrackInfo(this.mToken, list, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentTvInputId(java.lang.String str) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentTvInputId(this.mToken, str, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTimeShiftMode(int i) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTimeShiftMode(this.mToken, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendAvailableSpeeds(float[] fArr) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendAvailableSpeeds(this.mToken, fArr, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTvRecordingInfo(android.media.tv.TvRecordingInfo tvRecordingInfo) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTvRecordingInfo(this.mToken, tvRecordingInfo, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTvRecordingInfoList(java.util.List<android.media.tv.TvRecordingInfo> list) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTvRecordingInfoList(this.mToken, list, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingStarted(java.lang.String str, java.lang.String str2) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingStarted(this.mToken, str, str2, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingStopped(java.lang.String str) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingStopped(this.mToken, str, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendSigningResult(java.lang.String str, byte[] bArr) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendSigningResult(this.mToken, str, bArr, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCertificate(java.lang.String str, int i, android.net.http.SslCertificate sslCertificate) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCertificate(this.mToken, str, i, android.net.http.SslCertificate.saveState(sslCertificate), this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyError(java.lang.String str, android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyError(this.mToken, str, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftPlaybackParams(android.media.PlaybackParams playbackParams) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftPlaybackParams(this.mToken, playbackParams, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftStatusChanged(java.lang.String str, int i) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftStatusChanged(this.mToken, str, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftStartPositionChanged(java.lang.String str, long j) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftStartPositionChanged(this.mToken, str, j, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftCurrentPositionChanged(java.lang.String str, long j) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftCurrentPositionChanged(this.mToken, str, j, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingConnectionFailed(java.lang.String str, java.lang.String str2) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingConnectionFailed(this.mToken, str, str2, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingDisconnected(java.lang.String str, java.lang.String str2) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingDisconnected(this.mToken, str, str2, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingTuned(java.lang.String str, android.net.Uri uri) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingTuned(this.mToken, str, uri, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingError(java.lang.String str, int i) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingError(this.mToken, str, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingScheduled(java.lang.String str, java.lang.String str2) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingScheduled(this.mToken, str, str2, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSurface(android.view.Surface surface) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setSurface(this.mToken, surface, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void createMediaView(android.view.View view, android.graphics.Rect rect) {
            com.android.internal.util.Preconditions.checkNotNull(view);
            com.android.internal.util.Preconditions.checkNotNull(rect);
            if (view.getWindowToken() == null) {
                throw new java.lang.IllegalStateException("view must be attached to a window");
            }
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.createMediaView(this.mToken, view.getWindowToken(), rect, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void relayoutMediaView(android.graphics.Rect rect) {
            com.android.internal.util.Preconditions.checkNotNull(rect);
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.relayoutMediaView(this.mToken, rect, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void removeMediaView() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.removeMediaView(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void dispatchSurfaceChanged(int i, int i2, int i3) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.dispatchSurfaceChanged(this.mToken, i, i2, i3, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int dispatchInputEvent(android.view.InputEvent inputEvent, java.lang.Object obj, android.media.tv.interactive.TvInteractiveAppManager.Session.FinishedInputEventCallback finishedInputEventCallback, android.os.Handler handler) {
            com.android.internal.util.Preconditions.checkNotNull(inputEvent);
            com.android.internal.util.Preconditions.checkNotNull(finishedInputEventCallback);
            com.android.internal.util.Preconditions.checkNotNull(handler);
            synchronized (this.mHandler) {
                if (this.mInputChannel == null) {
                    return 0;
                }
                android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent obtainPendingEventLocked = obtainPendingEventLocked(inputEvent, obj, finishedInputEventCallback, handler);
                if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) {
                    return sendInputEventOnMainLooperLocked(obtainPendingEventLocked);
                }
                android.os.Message obtainMessage = this.mHandler.obtainMessage(1, obtainPendingEventLocked);
                obtainMessage.setAsynchronous(true);
                this.mHandler.sendMessage(obtainMessage);
                return -1;
            }
        }

        public void notifyBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyBroadcastInfoResponse(this.mToken, broadcastInfoResponse, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyAdResponse(android.media.tv.AdResponse adResponse) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyAdResponse(this.mToken, adResponse, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyAdBufferConsumed(android.media.tv.AdBuffer adBuffer) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                try {
                    this.mService.notifyAdBufferConsumed(this.mToken, adBuffer, this.mUserId);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                if (adBuffer != null) {
                    adBuffer.getSharedMemory().close();
                }
            }
        }

        public void release() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.releaseSession(this.mToken, this.mUserId);
                releaseInternal();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTuned(android.net.Uri uri) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTuned(this.mToken, uri, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTrackSelected(int i, java.lang.String str) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTrackSelected(this.mToken, i, str, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTracksChanged(this.mToken, list, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyVideoAvailable() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyVideoAvailable(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyVideoUnavailable(int i) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyVideoUnavailable(this.mToken, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyVideoFreezeUpdated(boolean z) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyVideoFreezeUpdated(this.mToken, z, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyContentAllowed() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyContentAllowed(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyContentBlocked(android.media.tv.TvContentRating tvContentRating) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyContentBlocked(this.mToken, tvContentRating.flattenToString(), this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifySignalStrength(int i) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifySignalStrength(this.mToken, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvMessage(int i, android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvMessage(this.mToken, i, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void flushPendingEventsLocked() {
            this.mHandler.removeMessages(3);
            int size = this.mPendingEvents.size();
            for (int i = 0; i < size; i++) {
                android.os.Message obtainMessage = this.mHandler.obtainMessage(3, this.mPendingEvents.keyAt(i), 0);
                obtainMessage.setAsynchronous(true);
                obtainMessage.sendToTarget();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void releaseInternal() {
            this.mToken = null;
            synchronized (this.mHandler) {
                if (this.mInputChannel != null) {
                    if (this.mSender != null) {
                        flushPendingEventsLocked();
                        this.mSender.dispose();
                        this.mSender = null;
                    }
                    this.mInputChannel.dispose();
                    this.mInputChannel = null;
                }
            }
            synchronized (this.mSessionCallbackRecordMap) {
                this.mSessionCallbackRecordMap.delete(this.mSeq);
            }
        }

        private android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent obtainPendingEventLocked(android.view.InputEvent inputEvent, java.lang.Object obj, android.media.tv.interactive.TvInteractiveAppManager.Session.FinishedInputEventCallback finishedInputEventCallback, android.os.Handler handler) {
            android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent acquire = this.mPendingEventPool.acquire();
            if (acquire == null) {
                acquire = new android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent();
            }
            acquire.mEvent = inputEvent;
            acquire.mEventToken = obj;
            acquire.mCallback = finishedInputEventCallback;
            acquire.mEventHandler = handler;
            return acquire;
        }

        void invokeFinishedInputEventCallback(android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent pendingEvent, boolean z) {
            pendingEvent.mHandled = z;
            if (pendingEvent.mEventHandler.getLooper().isCurrentThread()) {
                pendingEvent.run();
                return;
            }
            android.os.Message obtain = android.os.Message.obtain(pendingEvent.mEventHandler, pendingEvent);
            obtain.setAsynchronous(true);
            obtain.sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void sendInputEventAndReportResultOnMainLooper(android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent pendingEvent) {
            synchronized (this.mHandler) {
                if (sendInputEventOnMainLooperLocked(pendingEvent) == -1) {
                    return;
                }
                invokeFinishedInputEventCallback(pendingEvent, false);
            }
        }

        private int sendInputEventOnMainLooperLocked(android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent pendingEvent) {
            if (this.mInputChannel != null) {
                if (this.mSender == null) {
                    this.mSender = new android.media.tv.interactive.TvInteractiveAppManager.Session.TvInputEventSender(this.mInputChannel, this.mHandler.getLooper());
                }
                android.view.InputEvent inputEvent = pendingEvent.mEvent;
                int sequenceNumber = inputEvent.getSequenceNumber();
                if (this.mSender.sendInputEvent(sequenceNumber, inputEvent)) {
                    this.mPendingEvents.put(sequenceNumber, pendingEvent);
                    android.os.Message obtainMessage = this.mHandler.obtainMessage(2, pendingEvent);
                    obtainMessage.setAsynchronous(true);
                    this.mHandler.sendMessageDelayed(obtainMessage, INPUT_SESSION_NOT_RESPONDING_TIMEOUT);
                    return -1;
                }
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Unable to send input event to session: " + this.mToken + " dropping:" + inputEvent);
                return 0;
            }
            return 0;
        }

        void finishedInputEvent(int i, boolean z, boolean z2) {
            synchronized (this.mHandler) {
                int indexOfKey = this.mPendingEvents.indexOfKey(i);
                if (indexOfKey < 0) {
                    return;
                }
                android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent valueAt = this.mPendingEvents.valueAt(indexOfKey);
                this.mPendingEvents.removeAt(indexOfKey);
                if (z2) {
                    android.util.Log.w(android.media.tv.interactive.TvInteractiveAppManager.TAG, "Timeout waiting for session to handle input event after 2500 ms: " + this.mToken);
                } else {
                    this.mHandler.removeMessages(2, valueAt);
                }
                invokeFinishedInputEventCallback(valueAt, z);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void recyclePendingEventLocked(android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent pendingEvent) {
            pendingEvent.recycle();
            this.mPendingEventPool.release(pendingEvent);
        }

        private final class InputEventHandler extends android.os.Handler {
            public static final int MSG_FLUSH_INPUT_EVENT = 3;
            public static final int MSG_SEND_INPUT_EVENT = 1;
            public static final int MSG_TIMEOUT_INPUT_EVENT = 2;

            InputEventHandler(android.os.Looper looper) {
                super(looper, null, true);
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        android.media.tv.interactive.TvInteractiveAppManager.Session.this.sendInputEventAndReportResultOnMainLooper((android.media.tv.interactive.TvInteractiveAppManager.Session.PendingEvent) message.obj);
                        break;
                    case 2:
                        android.media.tv.interactive.TvInteractiveAppManager.Session.this.finishedInputEvent(message.arg1, false, true);
                        break;
                    case 3:
                        android.media.tv.interactive.TvInteractiveAppManager.Session.this.finishedInputEvent(message.arg1, false, false);
                        break;
                }
            }
        }

        private final class TvInputEventSender extends android.view.InputEventSender {
            TvInputEventSender(android.view.InputChannel inputChannel, android.os.Looper looper) {
                super(inputChannel, looper);
            }

            @Override // android.view.InputEventSender
            public void onInputEventFinished(int i, boolean z) {
                android.media.tv.interactive.TvInteractiveAppManager.Session.this.finishedInputEvent(i, z, false);
            }
        }

        private final class PendingEvent implements java.lang.Runnable {
            public android.media.tv.interactive.TvInteractiveAppManager.Session.FinishedInputEventCallback mCallback;
            public android.view.InputEvent mEvent;
            public android.os.Handler mEventHandler;
            public java.lang.Object mEventToken;
            public boolean mHandled;

            private PendingEvent() {
            }

            public void recycle() {
                this.mEvent = null;
                this.mEventToken = null;
                this.mCallback = null;
                this.mEventHandler = null;
                this.mHandled = false;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.mCallback.onFinishedInputEvent(this.mEventToken, this.mHandled);
                synchronized (this.mEventHandler) {
                    android.media.tv.interactive.TvInteractiveAppManager.Session.this.recyclePendingEventLocked(this);
                }
            }
        }
    }

    private static final class SessionCallbackRecord {
        private final android.os.Handler mHandler;
        private android.media.tv.interactive.TvInteractiveAppManager.Session mSession;
        private final android.media.tv.interactive.TvInteractiveAppManager.SessionCallback mSessionCallback;

        SessionCallbackRecord(android.media.tv.interactive.TvInteractiveAppManager.SessionCallback sessionCallback, android.os.Handler handler) {
            this.mSessionCallback = sessionCallback;
            this.mHandler = handler;
        }

        void postSessionCreated(final android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            this.mSession = session;
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onSessionCreated(session);
                }
            });
        }

        void postSessionReleased() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onSessionReleased(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postLayoutSurface(final int i, final int i2, final int i3, final int i4) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onLayoutSurface(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, i, i2, i3, i4);
                }
            });
        }

        void postBroadcastInfoRequest(final android.media.tv.BroadcastInfoRequest broadcastInfoRequest) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    if (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession.getInputSession().requestBroadcastInfo(broadcastInfoRequest);
                    }
                }
            });
        }

        void postRemoveBroadcastInfo(final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    if (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession.getInputSession().removeBroadcastInfo(i);
                    }
                }
            });
        }

        void postCommandRequest(final java.lang.String str, final android.os.Bundle bundle) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.6
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onCommandRequest(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str, bundle);
                }
            });
        }

        void postTimeShiftCommandRequest(final java.lang.String str, final android.os.Bundle bundle) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.7
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onTimeShiftCommandRequest(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str, bundle);
                }
            });
        }

        void postSetVideoBounds(final android.graphics.Rect rect) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.8
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onSetVideoBounds(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, rect);
                }
            });
        }

        void postRequestCurrentVideoBounds() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.9
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestCurrentVideoBounds(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentChannelUri() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.10
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestCurrentChannelUri(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentChannelLcn() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.11
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestCurrentChannelLcn(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestStreamVolume() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.12
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestStreamVolume(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestTrackInfoList() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.13
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestTrackInfoList(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestSelectedTrackInfo() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.14
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestSelectedTrackInfo(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentTvInputId() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.15
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestCurrentTvInputId(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestTimeShiftMode() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.16
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestTimeShiftMode(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestAvailableSpeeds() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.17
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestAvailableSpeeds(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestStartRecording(final java.lang.String str, final android.net.Uri uri) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.18
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestStartRecording(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str, uri);
                }
            });
        }

        void postRequestStopRecording(final java.lang.String str) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.19
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestStopRecording(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str);
                }
            });
        }

        void postRequestScheduleRecording(final java.lang.String str, final java.lang.String str2, final android.net.Uri uri, final android.net.Uri uri2, final android.os.Bundle bundle) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.20
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestScheduleRecording(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str, str2, uri, uri2, bundle);
                }
            });
        }

        void postRequestScheduleRecording(final java.lang.String str, final java.lang.String str2, final android.net.Uri uri, final long j, final long j2, final int i, final android.os.Bundle bundle) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.21
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestScheduleRecording(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str, str2, uri, j, j2, i, bundle);
                }
            });
        }

        void postRequestSigning(final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final byte[] bArr) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.22
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestSigning(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str, str2, str3, bArr);
                }
            });
        }

        void postRequestSigning(final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final int i, final byte[] bArr) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.23
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestSigning(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str, str2, str3, i, bArr);
                }
            });
        }

        void postRequestCertificate(final java.lang.String str, final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.24
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestCertificate(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str, i);
                }
            });
        }

        void postRequestTvRecordingInfo(final java.lang.String str) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.25
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestTvRecordingInfo(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str);
                }
            });
        }

        void postRequestTvRecordingInfoList(final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.26
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onRequestTvRecordingInfoList(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, i);
                }
            });
        }

        void postSetTvRecordingInfo(final java.lang.String str, final android.media.tv.TvRecordingInfo tvRecordingInfo) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.27
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onSetTvRecordingInfo(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, str, tvRecordingInfo);
                }
            });
        }

        void postAdRequest(final android.media.tv.AdRequest adRequest) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.28
                @Override // java.lang.Runnable
                public void run() {
                    if (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession.getInputSession().requestAd(adRequest);
                    }
                }
            });
        }

        void postSessionStateChanged(final int i, final int i2) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.29
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onSessionStateChanged(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, i, i2);
                }
            });
        }

        void postBiInteractiveAppCreated(final android.net.Uri uri, final java.lang.String str) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.30
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onBiInteractiveAppCreated(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, uri, str);
                }
            });
        }

        void postTeletextAppStateChanged(final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.31
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSessionCallback.onTeletextAppStateChanged(android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession, i);
                }
            });
        }

        void postAdBufferReady(final android.media.tv.AdBuffer adBuffer) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.32
                @Override // java.lang.Runnable
                public void run() {
                    if (android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.this.mSession.getInputSession().notifyAdBufferReady(adBuffer);
                    }
                }
            });
        }
    }

    public static abstract class SessionCallback {
        public void onSessionCreated(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onSessionReleased(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onLayoutSurface(android.media.tv.interactive.TvInteractiveAppManager.Session session, int i, int i2, int i3, int i4) {
        }

        public void onCommandRequest(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, android.os.Bundle bundle) {
        }

        public void onTimeShiftCommandRequest(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, android.os.Bundle bundle) {
        }

        public void onSetVideoBounds(android.media.tv.interactive.TvInteractiveAppManager.Session session, android.graphics.Rect rect) {
        }

        public void onRequestCurrentVideoBounds(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onRequestCurrentChannelUri(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onRequestCurrentChannelLcn(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onRequestStreamVolume(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onRequestTrackInfoList(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onRequestSelectedTrackInfo(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onRequestCurrentTvInputId(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onRequestTimeShiftMode(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onRequestAvailableSpeeds(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
        }

        public void onRequestStartRecording(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, android.net.Uri uri) {
        }

        public void onRequestStopRecording(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str) {
        }

        public void onRequestScheduleRecording(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle) {
        }

        public void onRequestScheduleRecording(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle) {
        }

        public void onSetTvRecordingInfo(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo) {
        }

        public void onRequestTvRecordingInfo(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str) {
        }

        public void onRequestTvRecordingInfoList(android.media.tv.interactive.TvInteractiveAppManager.Session session, int i) {
        }

        public void onRequestSigning(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) {
        }

        public void onRequestSigning(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, byte[] bArr) {
        }

        public void onRequestCertificate(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, int i) {
        }

        public void onSessionStateChanged(android.media.tv.interactive.TvInteractiveAppManager.Session session, int i, int i2) {
        }

        public void onBiInteractiveAppCreated(android.media.tv.interactive.TvInteractiveAppManager.Session session, android.net.Uri uri, java.lang.String str) {
        }

        public void onTeletextAppStateChanged(android.media.tv.interactive.TvInteractiveAppManager.Session session, int i) {
        }
    }
}
