package android.media.tv;

/* loaded from: classes2.dex */
public final class TvInputManager {
    public static final java.lang.String ACTION_BLOCKED_RATINGS_CHANGED = "android.media.tv.action.BLOCKED_RATINGS_CHANGED";
    public static final java.lang.String ACTION_PARENTAL_CONTROLS_ENABLED_CHANGED = "android.media.tv.action.PARENTAL_CONTROLS_ENABLED_CHANGED";
    public static final java.lang.String ACTION_QUERY_CONTENT_RATING_SYSTEMS = "android.media.tv.action.QUERY_CONTENT_RATING_SYSTEMS";
    public static final java.lang.String ACTION_SETUP_INPUTS = "android.media.tv.action.SETUP_INPUTS";
    public static final java.lang.String ACTION_VIEW_RECORDING_SCHEDULES = "android.media.tv.action.VIEW_RECORDING_SCHEDULES";
    public static final int BROADCAST_INFO_STREAM_EVENT = 5;
    public static final int BROADCAST_INFO_TYPE_COMMAND = 7;
    public static final int BROADCAST_INFO_TYPE_DSMCC = 6;
    public static final int BROADCAST_INFO_TYPE_PES = 4;
    public static final int BROADCAST_INFO_TYPE_SECTION = 3;
    public static final int BROADCAST_INFO_TYPE_SIGNALING_DATA = 9;
    public static final int BROADCAST_INFO_TYPE_TABLE = 2;
    public static final int BROADCAST_INFO_TYPE_TIMELINE = 8;
    public static final int BROADCAST_INFO_TYPE_TS = 1;
    public static final int DVB_DEVICE_DEMUX = 0;
    public static final int DVB_DEVICE_DVR = 1;
    static final int DVB_DEVICE_END = 2;
    public static final int DVB_DEVICE_FRONTEND = 2;
    static final int DVB_DEVICE_START = 0;
    public static final int INPUT_STATE_CONNECTED = 0;
    public static final int INPUT_STATE_CONNECTED_STANDBY = 1;
    public static final int INPUT_STATE_DISCONNECTED = 2;
    public static final java.lang.String META_DATA_CONTENT_RATING_SYSTEMS = "android.media.tv.metadata.CONTENT_RATING_SYSTEMS";
    static final int RECORDING_ERROR_END = 2;
    public static final int RECORDING_ERROR_INSUFFICIENT_SPACE = 1;
    public static final int RECORDING_ERROR_RESOURCE_BUSY = 2;
    static final int RECORDING_ERROR_START = 0;
    public static final int RECORDING_ERROR_UNKNOWN = 0;
    public static final java.lang.String SESSION_DATA_KEY_AD_BUFFER = "ad_buffer";
    public static final java.lang.String SESSION_DATA_KEY_AD_RESPONSE = "ad_response";
    public static final java.lang.String SESSION_DATA_KEY_BROADCAST_INFO_RESPONSE = "broadcast_info_response";
    public static final java.lang.String SESSION_DATA_KEY_CHANNEL_URI = "channel_uri";
    public static final java.lang.String SESSION_DATA_KEY_TRACKS = "tracks";
    public static final java.lang.String SESSION_DATA_KEY_TRACK_ID = "track_id";
    public static final java.lang.String SESSION_DATA_KEY_TRACK_TYPE = "track_type";
    public static final java.lang.String SESSION_DATA_KEY_TV_MESSAGE_TYPE = "tv_message_type";
    public static final java.lang.String SESSION_DATA_KEY_VIDEO_UNAVAILABLE_REASON = "video_unavailable_reason";
    public static final java.lang.String SESSION_DATA_TYPE_AD_BUFFER_CONSUMED = "ad_buffer_consumed";
    public static final java.lang.String SESSION_DATA_TYPE_AD_RESPONSE = "ad_response";
    public static final java.lang.String SESSION_DATA_TYPE_BROADCAST_INFO_RESPONSE = "broadcast_info_response";
    public static final java.lang.String SESSION_DATA_TYPE_TRACKS_CHANGED = "tracks_changed";
    public static final java.lang.String SESSION_DATA_TYPE_TRACK_SELECTED = "track_selected";
    public static final java.lang.String SESSION_DATA_TYPE_TUNED = "tuned";
    public static final java.lang.String SESSION_DATA_TYPE_TV_MESSAGE = "tv_message";
    public static final java.lang.String SESSION_DATA_TYPE_VIDEO_AVAILABLE = "video_available";
    public static final java.lang.String SESSION_DATA_TYPE_VIDEO_UNAVAILABLE = "video_unavailable";
    public static final int SIGNAL_STRENGTH_LOST = 1;
    public static final int SIGNAL_STRENGTH_STRONG = 3;
    public static final int SIGNAL_STRENGTH_WEAK = 2;
    private static final java.lang.String TAG = "TvInputManager";
    public static final long TIME_SHIFT_INVALID_TIME = Long.MIN_VALUE;
    public static final int TIME_SHIFT_MODE_AUTO = 4;
    public static final int TIME_SHIFT_MODE_LOCAL = 2;
    public static final int TIME_SHIFT_MODE_NETWORK = 3;
    public static final int TIME_SHIFT_MODE_OFF = 1;
    public static final int TIME_SHIFT_STATUS_AVAILABLE = 3;
    public static final int TIME_SHIFT_STATUS_UNAVAILABLE = 2;
    public static final int TIME_SHIFT_STATUS_UNKNOWN = 0;
    public static final int TIME_SHIFT_STATUS_UNSUPPORTED = 1;
    public static final long TV_MESSAGE_GROUP_ID_NONE = -1;
    public static final java.lang.String TV_MESSAGE_KEY_GROUP_ID = "android.media.tv.TvInputManager.group_id";
    public static final java.lang.String TV_MESSAGE_KEY_RAW_DATA = "android.media.tv.TvInputManager.raw_data";
    public static final java.lang.String TV_MESSAGE_KEY_STREAM_ID = "android.media.tv.TvInputManager.stream_id";
    public static final java.lang.String TV_MESSAGE_KEY_SUBTYPE = "android.media.tv.TvInputManager.subtype";
    public static final java.lang.String TV_MESSAGE_SUBTYPE_CC_608E = "CTA 608-E";
    public static final java.lang.String TV_MESSAGE_SUBTYPE_WATERMARKING_A335 = "ATSC A/335";
    public static final int TV_MESSAGE_TYPE_CLOSED_CAPTION = 2;
    public static final int TV_MESSAGE_TYPE_OTHER = 1000;
    public static final int TV_MESSAGE_TYPE_WATERMARK = 1;
    public static final int UNKNOWN_CLIENT_PID = -1;
    public static final int VIDEO_UNAVAILABLE_REASON_AUDIO_ONLY = 4;
    public static final int VIDEO_UNAVAILABLE_REASON_BUFFERING = 3;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_BLACKOUT = 16;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_CARD_INVALID = 15;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_CARD_MUTE = 14;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_INSUFFICIENT_OUTPUT_PROTECTION = 7;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_LICENSE_EXPIRED = 10;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_NEED_ACTIVATION = 11;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_NEED_PAIRING = 12;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_NO_CARD = 13;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_NO_LICENSE = 9;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_PVR_RECORDING_NOT_ALLOWED = 8;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_REBOOTING = 17;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_UNKNOWN = 18;
    static final int VIDEO_UNAVAILABLE_REASON_END = 18;
    public static final int VIDEO_UNAVAILABLE_REASON_INSUFFICIENT_RESOURCE = 6;
    public static final int VIDEO_UNAVAILABLE_REASON_NOT_CONNECTED = 5;
    static final int VIDEO_UNAVAILABLE_REASON_START = 0;
    public static final int VIDEO_UNAVAILABLE_REASON_STOPPED = 19;
    public static final int VIDEO_UNAVAILABLE_REASON_TUNING = 1;
    public static final int VIDEO_UNAVAILABLE_REASON_UNKNOWN = 0;
    public static final int VIDEO_UNAVAILABLE_REASON_WEAK_SIGNAL = 2;
    private int mNextSeq;
    private final android.media.tv.ITvInputManager mService;
    private final int mUserId;
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.List<android.media.tv.TvInputManager.TvInputCallbackRecord> mCallbackRecords = new java.util.ArrayList();
    private final java.util.Map<java.lang.String, java.lang.Integer> mStateMap = new android.util.ArrayMap();
    private final android.util.SparseArray<android.media.tv.TvInputManager.SessionCallbackRecord> mSessionCallbackRecordMap = new android.util.SparseArray<>();
    private final android.media.tv.ITvInputClient mClient = new android.media.tv.ITvInputClient.Stub() { // from class: android.media.tv.TvInputManager.1
        @Override // android.media.tv.ITvInputClient
        public void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) {
            android.media.tv.TvInputManager.Session session;
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for " + iBinder);
                    return;
                }
                if (iBinder != null) {
                    session = new android.media.tv.TvInputManager.Session(iBinder, inputChannel, android.media.tv.TvInputManager.this.mService, android.media.tv.TvInputManager.this.mUserId, i, android.media.tv.TvInputManager.this.mSessionCallbackRecordMap);
                } else {
                    android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.delete(i);
                    session = null;
                }
                sessionCallbackRecord.postSessionCreated(session);
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onSessionReleased(int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.delete(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq:" + i);
                } else {
                    sessionCallbackRecord.mSession.releaseInternal();
                    sessionCallbackRecord.postSessionReleased();
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onChannelRetuned(android.net.Uri uri, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postChannelRetuned(uri);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onAudioPresentationsChanged(java.util.List<android.media.AudioPresentation> list, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    if (sessionCallbackRecord.mSession.updateAudioPresentations(list)) {
                        sessionCallbackRecord.postAudioPresentationsChanged(list);
                    }
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onAudioPresentationSelected(int i, int i2, int i3) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i3);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i3);
                } else {
                    if (sessionCallbackRecord.mSession.updateAudioPresentationSelection(i, i2)) {
                        sessionCallbackRecord.postAudioPresentationSelected(i, i2);
                    }
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                if (sessionCallbackRecord.mSession.updateTracks(list)) {
                    sessionCallbackRecord.postTracksChanged(list);
                    postVideoSizeChangedIfNeededLocked(sessionCallbackRecord);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onTrackSelected(int i, java.lang.String str, int i2) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i2);
                    return;
                }
                if (sessionCallbackRecord.mSession.updateTrackSelection(i, str)) {
                    sessionCallbackRecord.postTrackSelected(i, str);
                    postVideoSizeChangedIfNeededLocked(sessionCallbackRecord);
                }
            }
        }

        private void postVideoSizeChangedIfNeededLocked(android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord) {
            android.media.tv.TvTrackInfo videoTrackToNotify = sessionCallbackRecord.mSession.getVideoTrackToNotify();
            if (videoTrackToNotify != null) {
                sessionCallbackRecord.postVideoSizeChanged(videoTrackToNotify.getVideoWidth(), videoTrackToNotify.getVideoHeight());
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoAvailable(int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postVideoAvailable();
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoUnavailable(int i, int i2) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postVideoUnavailable(i);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoFreezeUpdated(boolean z, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postVideoFreezeUpdated(z);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onContentAllowed(int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postContentAllowed();
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onContentBlocked(java.lang.String str, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postContentBlocked(android.media.tv.TvContentRating.unflattenFromString(str));
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i5);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i5);
                } else {
                    sessionCallbackRecord.postLayoutSurface(i, i2, i3, i4);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onSessionEvent(java.lang.String str, android.os.Bundle bundle, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postSessionEvent(str, bundle);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftStatusChanged(int i, int i2) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postTimeShiftStatusChanged(i);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftStartPositionChanged(long j, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postTimeShiftStartPositionChanged(j);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftCurrentPositionChanged(long j, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postTimeShiftCurrentPositionChanged(j);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onAitInfoUpdated(android.media.tv.AitInfo aitInfo, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postAitInfoUpdated(aitInfo);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onSignalStrength(int i, int i2) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postSignalStrength(i);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onCueingMessageAvailability(boolean z, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postCueingMessageAvailability(z);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftMode(int i, int i2) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postTimeShiftMode(i);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onAvailableSpeeds(float[] fArr, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postAvailableSpeeds(fArr);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onTuned(android.net.Uri uri, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postTuned(uri);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onTvMessage(int i, android.os.Bundle bundle, int i2) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postTvMessage(i, bundle);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onRecordingStopped(android.net.Uri uri, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postRecordingStopped(uri);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onError(int i, int i2) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i2);
                } else {
                    sessionCallbackRecord.postError(i);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postBroadcastInfoResponse(broadcastInfoResponse);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onAdResponse(android.media.tv.AdResponse adResponse, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postAdResponse(adResponse);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onAdBufferConsumed(android.media.tv.AdBuffer adBuffer, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postAdBufferConsumed(adBuffer);
                }
            }
        }

        @Override // android.media.tv.ITvInputClient
        public void onTvInputSessionData(java.lang.String str, android.os.Bundle bundle, int i) {
            synchronized (android.media.tv.TvInputManager.this.mSessionCallbackRecordMap) {
                android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = (android.media.tv.TvInputManager.SessionCallbackRecord) android.media.tv.TvInputManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    android.util.Log.e(android.media.tv.TvInputManager.TAG, "Callback not found for seq " + i);
                } else {
                    sessionCallbackRecord.postTvInputSessionData(str, bundle);
                }
            }
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BroadcastInfoType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DvbDeviceType {
    }

    @android.annotation.SystemApi
    public static abstract class HardwareCallback {
        public abstract void onReleased();

        public abstract void onStreamConfigChanged(android.media.tv.TvStreamConfig[] tvStreamConfigArr);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InputState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecordingError {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionDataKey {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionDataType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SignalStrength {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimeShiftMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimeShiftStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TvMessageType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VideoUnavailableReason {
    }

    public static abstract class SessionCallback {
        public void onSessionCreated(android.media.tv.TvInputManager.Session session) {
        }

        public void onSessionReleased(android.media.tv.TvInputManager.Session session) {
        }

        public void onChannelRetuned(android.media.tv.TvInputManager.Session session, android.net.Uri uri) {
        }

        public void onAudioPresentationsChanged(android.media.tv.TvInputManager.Session session, java.util.List<android.media.AudioPresentation> list) {
        }

        public void onAudioPresentationSelected(android.media.tv.TvInputManager.Session session, int i, int i2) {
        }

        public void onTracksChanged(android.media.tv.TvInputManager.Session session, java.util.List<android.media.tv.TvTrackInfo> list) {
        }

        public void onTrackSelected(android.media.tv.TvInputManager.Session session, int i, java.lang.String str) {
        }

        public void onVideoSizeChanged(android.media.tv.TvInputManager.Session session, int i, int i2) {
        }

        public void onVideoAvailable(android.media.tv.TvInputManager.Session session) {
        }

        public void onVideoUnavailable(android.media.tv.TvInputManager.Session session, int i) {
        }

        public void onVideoFreezeUpdated(android.media.tv.TvInputManager.Session session, boolean z) {
        }

        public void onContentAllowed(android.media.tv.TvInputManager.Session session) {
        }

        public void onContentBlocked(android.media.tv.TvInputManager.Session session, android.media.tv.TvContentRating tvContentRating) {
        }

        public void onLayoutSurface(android.media.tv.TvInputManager.Session session, int i, int i2, int i3, int i4) {
        }

        public void onSessionEvent(android.media.tv.TvInputManager.Session session, java.lang.String str, android.os.Bundle bundle) {
        }

        public void onTimeShiftStatusChanged(android.media.tv.TvInputManager.Session session, int i) {
        }

        public void onTimeShiftStartPositionChanged(android.media.tv.TvInputManager.Session session, long j) {
        }

        public void onTimeShiftCurrentPositionChanged(android.media.tv.TvInputManager.Session session, long j) {
        }

        public void onAitInfoUpdated(android.media.tv.TvInputManager.Session session, android.media.tv.AitInfo aitInfo) {
        }

        public void onSignalStrengthUpdated(android.media.tv.TvInputManager.Session session, int i) {
        }

        public void onCueingMessageAvailability(android.media.tv.TvInputManager.Session session, boolean z) {
        }

        public void onTimeShiftMode(android.media.tv.TvInputManager.Session session, int i) {
        }

        public void onAvailableSpeeds(android.media.tv.TvInputManager.Session session, float[] fArr) {
        }

        public void onTuned(android.media.tv.TvInputManager.Session session, android.net.Uri uri) {
        }

        public void onTvMessage(android.media.tv.TvInputManager.Session session, int i, android.os.Bundle bundle) {
        }

        void onRecordingStopped(android.media.tv.TvInputManager.Session session, android.net.Uri uri) {
        }

        void onError(android.media.tv.TvInputManager.Session session, int i) {
        }
    }

    private static final class SessionCallbackRecord {
        private final android.os.Handler mHandler;
        private android.media.tv.TvInputManager.Session mSession;
        private final android.media.tv.TvInputManager.SessionCallback mSessionCallback;

        SessionCallbackRecord(android.media.tv.TvInputManager.SessionCallback sessionCallback, android.os.Handler handler) {
            this.mSessionCallback = sessionCallback;
            this.mHandler = handler;
        }

        void postSessionCreated(final android.media.tv.TvInputManager.Session session) {
            this.mSession = session;
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onSessionCreated(session);
                }
            });
        }

        void postSessionReleased() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onSessionReleased(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postChannelRetuned(final android.net.Uri uri) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onChannelRetuned(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, uri);
                }
            });
        }

        void postAudioPresentationsChanged(final java.util.List<android.media.AudioPresentation> list) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onAudioPresentationsChanged(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, list);
                }
            });
        }

        void postAudioPresentationSelected(final int i, final int i2) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onAudioPresentationSelected(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, i, i2);
                }
            });
        }

        void postTracksChanged(final java.util.List<android.media.tv.TvTrackInfo> list) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.6
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onTracksChanged(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, list);
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.mIAppNotificationEnabled && android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyTracksChanged(list);
                    }
                }
            });
        }

        void postTrackSelected(final int i, final java.lang.String str) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.7
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onTrackSelected(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, i, str);
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.mIAppNotificationEnabled && android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyTrackSelected(i, str);
                    }
                }
            });
        }

        void postVideoSizeChanged(final int i, final int i2) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.8
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onVideoSizeChanged(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, i, i2);
                }
            });
        }

        void postVideoAvailable() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.9
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onVideoAvailable(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession);
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.mIAppNotificationEnabled && android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyVideoAvailable();
                    }
                }
            });
        }

        void postVideoUnavailable(final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.10
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onVideoUnavailable(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, i);
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.mIAppNotificationEnabled && android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyVideoUnavailable(i);
                    }
                }
            });
        }

        void postVideoFreezeUpdated(final boolean z) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.11
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onVideoFreezeUpdated(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, z);
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.mIAppNotificationEnabled && android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyVideoFreezeUpdated(z);
                    }
                }
            });
        }

        void postContentAllowed() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.12
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onContentAllowed(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession);
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.mIAppNotificationEnabled && android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyContentAllowed();
                    }
                }
            });
        }

        void postContentBlocked(final android.media.tv.TvContentRating tvContentRating) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.13
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onContentBlocked(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, tvContentRating);
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.mIAppNotificationEnabled && android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyContentBlocked(tvContentRating);
                    }
                }
            });
        }

        void postLayoutSurface(final int i, final int i2, final int i3, final int i4) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.14
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onLayoutSurface(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, i, i2, i3, i4);
                }
            });
        }

        void postSessionEvent(final java.lang.String str, final android.os.Bundle bundle) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.15
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onSessionEvent(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, str, bundle);
                }
            });
        }

        void postTimeShiftStatusChanged(final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.16
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onTimeShiftStatusChanged(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, i);
                }
            });
        }

        void postTimeShiftStartPositionChanged(final long j) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.17
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onTimeShiftStartPositionChanged(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, j);
                }
            });
        }

        void postTimeShiftCurrentPositionChanged(final long j) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.18
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onTimeShiftCurrentPositionChanged(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, j);
                }
            });
        }

        void postAitInfoUpdated(final android.media.tv.AitInfo aitInfo) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.19
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onAitInfoUpdated(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, aitInfo);
                }
            });
        }

        void postSignalStrength(final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.20
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onSignalStrengthUpdated(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, i);
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.mIAppNotificationEnabled && android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifySignalStrength(i);
                    }
                }
            });
        }

        void postCueingMessageAvailability(final boolean z) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.21
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onCueingMessageAvailability(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, z);
                }
            });
        }

        void postTimeShiftMode(final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.22
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onTimeShiftMode(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, i);
                }
            });
        }

        void postAvailableSpeeds(final float[] fArr) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.23
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onAvailableSpeeds(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, fArr);
                }
            });
        }

        void postTuned(final android.net.Uri uri) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.24
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onTuned(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, uri);
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.mIAppNotificationEnabled && android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyTuned(uri);
                    }
                }
            });
        }

        void postTvMessage(final int i, final android.os.Bundle bundle) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.25
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onTvMessage(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, i, bundle);
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.mIAppNotificationEnabled && android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyTvMessage(i, bundle);
                    }
                }
            });
        }

        void postRecordingStopped(final android.net.Uri uri) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.26
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onRecordingStopped(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, uri);
                }
            });
        }

        void postError(final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.27
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.SessionCallbackRecord.this.mSessionCallback.onError(android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession, i);
                }
            });
        }

        void postBroadcastInfoResponse(final android.media.tv.BroadcastInfoResponse broadcastInfoResponse) {
            if (this.mSession.mIAppNotificationEnabled) {
                this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.28
                    @Override // java.lang.Runnable
                    public void run() {
                        if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                            android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyBroadcastInfoResponse(broadcastInfoResponse);
                        }
                    }
                });
            }
        }

        void postAdResponse(final android.media.tv.AdResponse adResponse) {
            if (this.mSession.mIAppNotificationEnabled) {
                this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.29
                    @Override // java.lang.Runnable
                    public void run() {
                        if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                            android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyAdResponse(adResponse);
                        }
                    }
                });
            }
        }

        void postAdBufferConsumed(final android.media.tv.AdBuffer adBuffer) {
            if (this.mSession.mIAppNotificationEnabled) {
                this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.30
                    @Override // java.lang.Runnable
                    public void run() {
                        if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                            android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyAdBufferConsumed(adBuffer);
                        }
                    }
                });
            }
        }

        void postTvInputSessionData(final java.lang.String str, final android.os.Bundle bundle) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.31
                @Override // java.lang.Runnable
                public void run() {
                    if (android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getAdSession() != null) {
                        android.media.tv.TvInputManager.SessionCallbackRecord.this.mSession.getAdSession().notifyTvInputSessionData(str, bundle);
                    }
                }
            });
        }
    }

    public static abstract class TvInputCallback {
        public void onInputStateChanged(java.lang.String str, int i) {
        }

        public void onInputAdded(java.lang.String str) {
        }

        public void onInputRemoved(java.lang.String str) {
        }

        public void onInputUpdated(java.lang.String str) {
        }

        public void onTvInputInfoUpdated(android.media.tv.TvInputInfo tvInputInfo) {
        }

        @android.annotation.SystemApi
        public void onCurrentTunedInfosUpdated(java.util.List<android.media.tv.TunedInfo> list) {
        }
    }

    private static final class TvInputCallbackRecord {
        private final android.media.tv.TvInputManager.TvInputCallback mCallback;
        private final android.os.Handler mHandler;

        public TvInputCallbackRecord(android.media.tv.TvInputManager.TvInputCallback tvInputCallback, android.os.Handler handler) {
            this.mCallback = tvInputCallback;
            this.mHandler = handler;
        }

        public android.media.tv.TvInputManager.TvInputCallback getCallback() {
            return this.mCallback;
        }

        public void postInputAdded(final java.lang.String str) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.TvInputCallbackRecord.this.mCallback.onInputAdded(str);
                }
            });
        }

        public void postInputRemoved(final java.lang.String str) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.TvInputCallbackRecord.this.mCallback.onInputRemoved(str);
                }
            });
        }

        public void postInputUpdated(final java.lang.String str) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.TvInputCallbackRecord.this.mCallback.onInputUpdated(str);
                }
            });
        }

        public void postInputStateChanged(final java.lang.String str, final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.TvInputCallbackRecord.this.mCallback.onInputStateChanged(str, i);
                }
            });
        }

        public void postTvInputInfoUpdated(final android.media.tv.TvInputInfo tvInputInfo) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.TvInputCallbackRecord.this.mCallback.onTvInputInfoUpdated(tvInputInfo);
                }
            });
        }

        public void postCurrentTunedInfosUpdated(final java.util.List<android.media.tv.TunedInfo> list) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.6
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputManager.TvInputCallbackRecord.this.mCallback.onCurrentTunedInfosUpdated(list);
                }
            });
        }
    }

    public TvInputManager(android.media.tv.ITvInputManager iTvInputManager, int i) {
        this.mService = iTvInputManager;
        this.mUserId = i;
        android.media.tv.ITvInputManagerCallback.Stub stub = new android.media.tv.ITvInputManagerCallback.Stub() { // from class: android.media.tv.TvInputManager.2
            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputAdded(java.lang.String str) {
                synchronized (android.media.tv.TvInputManager.this.mLock) {
                    android.media.tv.TvInputManager.this.mStateMap.put(str, 0);
                    java.util.Iterator it = android.media.tv.TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.TvInputManager.TvInputCallbackRecord) it.next()).postInputAdded(str);
                    }
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputRemoved(java.lang.String str) {
                synchronized (android.media.tv.TvInputManager.this.mLock) {
                    android.media.tv.TvInputManager.this.mStateMap.remove(str);
                    java.util.Iterator it = android.media.tv.TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.TvInputManager.TvInputCallbackRecord) it.next()).postInputRemoved(str);
                    }
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputUpdated(java.lang.String str) {
                synchronized (android.media.tv.TvInputManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.TvInputManager.TvInputCallbackRecord) it.next()).postInputUpdated(str);
                    }
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputStateChanged(java.lang.String str, int i2) {
                synchronized (android.media.tv.TvInputManager.this.mLock) {
                    android.media.tv.TvInputManager.this.mStateMap.put(str, java.lang.Integer.valueOf(i2));
                    java.util.Iterator it = android.media.tv.TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.TvInputManager.TvInputCallbackRecord) it.next()).postInputStateChanged(str, i2);
                    }
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onTvInputInfoUpdated(android.media.tv.TvInputInfo tvInputInfo) {
                synchronized (android.media.tv.TvInputManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.TvInputManager.TvInputCallbackRecord) it.next()).postTvInputInfoUpdated(tvInputInfo);
                    }
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onCurrentTunedInfosUpdated(java.util.List<android.media.tv.TunedInfo> list) {
                synchronized (android.media.tv.TvInputManager.this.mLock) {
                    java.util.Iterator it = android.media.tv.TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((android.media.tv.TvInputManager.TvInputCallbackRecord) it.next()).postCurrentTunedInfosUpdated(list);
                    }
                }
            }
        };
        try {
            if (this.mService != null) {
                this.mService.registerCallback(stub, this.mUserId);
                java.util.List<android.media.tv.TvInputInfo> tvInputList = this.mService.getTvInputList(this.mUserId);
                synchronized (this.mLock) {
                    java.util.Iterator<android.media.tv.TvInputInfo> it = tvInputList.iterator();
                    while (it.hasNext()) {
                        java.lang.String id = it.next().getId();
                        this.mStateMap.put(id, java.lang.Integer.valueOf(this.mService.getTvInputState(id, this.mUserId)));
                    }
                }
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.media.tv.TvInputInfo> getTvInputList() {
        try {
            return this.mService.getTvInputList(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.tv.TvInputInfo getTvInputInfo(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        try {
            return this.mService.getTvInputInfo(str, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateTvInputInfo(android.media.tv.TvInputInfo tvInputInfo) {
        com.android.internal.util.Preconditions.checkNotNull(tvInputInfo);
        try {
            this.mService.updateTvInputInfo(tvInputInfo, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getInputState(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        synchronized (this.mLock) {
            java.lang.Integer num = this.mStateMap.get(str);
            if (num == null) {
                android.util.Log.w(TAG, "Unrecognized input ID: " + str);
                return 2;
            }
            return num.intValue();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getAvailableExtensionInterfaceNames(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        try {
            return this.mService.getAvailableExtensionInterfaceNames(str, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.IBinder getExtensionInterface(java.lang.String str, java.lang.String str2) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkNotNull(str2);
        try {
            return this.mService.getExtensionInterface(str, str2, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(android.media.tv.TvInputManager.TvInputCallback tvInputCallback, android.os.Handler handler) {
        com.android.internal.util.Preconditions.checkNotNull(tvInputCallback);
        com.android.internal.util.Preconditions.checkNotNull(handler);
        synchronized (this.mLock) {
            this.mCallbackRecords.add(new android.media.tv.TvInputManager.TvInputCallbackRecord(tvInputCallback, handler));
        }
    }

    public void unregisterCallback(android.media.tv.TvInputManager.TvInputCallback tvInputCallback) {
        com.android.internal.util.Preconditions.checkNotNull(tvInputCallback);
        synchronized (this.mLock) {
            java.util.Iterator<android.media.tv.TvInputManager.TvInputCallbackRecord> it = this.mCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getCallback() == tvInputCallback) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public boolean isParentalControlsEnabled() {
        try {
            return this.mService.isParentalControlsEnabled(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setParentalControlsEnabled(boolean z) {
        try {
            this.mService.setParentalControlsEnabled(z, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRatingBlocked(android.media.tv.TvContentRating tvContentRating) {
        com.android.internal.util.Preconditions.checkNotNull(tvContentRating);
        try {
            return this.mService.isRatingBlocked(tvContentRating.flattenToString(), this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.media.tv.TvContentRating> getBlockedRatings() {
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<java.lang.String> it = this.mService.getBlockedRatings(this.mUserId).iterator();
            while (it.hasNext()) {
                arrayList.add(android.media.tv.TvContentRating.unflattenFromString(it.next()));
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void addBlockedRating(android.media.tv.TvContentRating tvContentRating) {
        com.android.internal.util.Preconditions.checkNotNull(tvContentRating);
        try {
            this.mService.addBlockedRating(tvContentRating.flattenToString(), this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void removeBlockedRating(android.media.tv.TvContentRating tvContentRating) {
        com.android.internal.util.Preconditions.checkNotNull(tvContentRating);
        try {
            this.mService.removeBlockedRating(tvContentRating.flattenToString(), this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.tv.TvContentRatingSystemInfo> getTvContentRatingSystemList() {
        try {
            return this.mService.getTvContentRatingSystemList(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void notifyPreviewProgramBrowsableDisabled(java.lang.String str, long j) {
        android.content.Intent intent = new android.content.Intent();
        intent.setAction(android.media.tv.TvContract.ACTION_PREVIEW_PROGRAM_BROWSABLE_DISABLED);
        intent.putExtra(android.media.tv.TvContract.EXTRA_PREVIEW_PROGRAM_ID, j);
        intent.setPackage(str);
        try {
            this.mService.sendTvInputNotifyIntent(intent, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void notifyWatchNextProgramBrowsableDisabled(java.lang.String str, long j) {
        android.content.Intent intent = new android.content.Intent();
        intent.setAction(android.media.tv.TvContract.ACTION_WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED);
        intent.putExtra(android.media.tv.TvContract.EXTRA_WATCH_NEXT_PROGRAM_ID, j);
        intent.setPackage(str);
        try {
            this.mService.sendTvInputNotifyIntent(intent, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void notifyPreviewProgramAddedToWatchNext(java.lang.String str, long j, long j2) {
        android.content.Intent intent = new android.content.Intent();
        intent.setAction(android.media.tv.TvContract.ACTION_PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT);
        intent.putExtra(android.media.tv.TvContract.EXTRA_PREVIEW_PROGRAM_ID, j);
        intent.putExtra(android.media.tv.TvContract.EXTRA_WATCH_NEXT_PROGRAM_ID, j2);
        intent.setPackage(str);
        try {
            this.mService.sendTvInputNotifyIntent(intent, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createSession(java.lang.String str, android.content.AttributionSource attributionSource, android.media.tv.TvInputManager.SessionCallback sessionCallback, android.os.Handler handler) {
        createSessionInternal(str, attributionSource, false, sessionCallback, handler);
    }

    @android.annotation.SystemApi
    public int getClientPid(java.lang.String str) {
        return getClientPidInternal(str);
    }

    @android.annotation.SystemApi
    public int getClientPriority(int i, java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        if (!isValidUseCase(i)) {
            throw new java.lang.IllegalArgumentException("Invalid use case: " + i);
        }
        return getClientPriorityInternal(i, str);
    }

    @android.annotation.SystemApi
    public int getClientPriority(int i) {
        if (!isValidUseCase(i)) {
            throw new java.lang.IllegalArgumentException("Invalid use case: " + i);
        }
        return getClientPriorityInternal(i, null);
    }

    public void createRecordingSession(java.lang.String str, android.media.tv.TvInputManager.SessionCallback sessionCallback, android.os.Handler handler) {
        createSessionInternal(str, null, true, sessionCallback, handler);
    }

    private void createSessionInternal(java.lang.String str, android.content.AttributionSource attributionSource, boolean z, android.media.tv.TvInputManager.SessionCallback sessionCallback, android.os.Handler handler) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkNotNull(sessionCallback);
        com.android.internal.util.Preconditions.checkNotNull(handler);
        android.media.tv.TvInputManager.SessionCallbackRecord sessionCallbackRecord = new android.media.tv.TvInputManager.SessionCallbackRecord(sessionCallback, handler);
        synchronized (this.mSessionCallbackRecordMap) {
            int i = this.mNextSeq;
            this.mNextSeq = i + 1;
            this.mSessionCallbackRecordMap.put(i, sessionCallbackRecord);
            try {
                this.mService.createSession(this.mClient, str, attributionSource, z, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private int getClientPidInternal(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        try {
            return this.mService.getClientPid(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int getClientPriorityInternal(int i, java.lang.String str) {
        try {
            return this.mService.getClientPriority(i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean isValidUseCase(int i) {
        return i == 100 || i == 200 || i == 300 || i == 400 || i == 500;
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.tv.TvStreamConfig> getAvailableTvStreamConfigList(java.lang.String str) {
        try {
            return this.mService.getAvailableTvStreamConfigList(str, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean captureFrame(java.lang.String str, android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig) {
        try {
            return this.mService.captureFrame(str, surface, tvStreamConfig, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isSingleSessionActive() {
        try {
            return this.mService.isSingleSessionActive(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.tv.TvInputHardwareInfo> getHardwareList() {
        try {
            return this.mService.getHardwareList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.media.tv.TvInputManager.Hardware acquireTvInputHardware(int i, android.media.tv.TvInputManager.HardwareCallback hardwareCallback, android.media.tv.TvInputInfo tvInputInfo) {
        return acquireTvInputHardware(i, tvInputInfo, hardwareCallback);
    }

    @android.annotation.SystemApi
    public android.media.tv.TvInputManager.Hardware acquireTvInputHardware(int i, android.media.tv.TvInputInfo tvInputInfo, android.media.tv.TvInputManager.HardwareCallback hardwareCallback) {
        com.android.internal.util.Preconditions.checkNotNull(tvInputInfo);
        com.android.internal.util.Preconditions.checkNotNull(hardwareCallback);
        return acquireTvInputHardwareInternal(i, tvInputInfo, null, 400, new java.util.concurrent.Executor() { // from class: android.media.tv.TvInputManager.3
            @Override // java.util.concurrent.Executor
            public void execute(java.lang.Runnable runnable) {
                runnable.run();
            }
        }, hardwareCallback);
    }

    @android.annotation.SystemApi
    public android.media.tv.TvInputManager.Hardware acquireTvInputHardware(int i, android.media.tv.TvInputInfo tvInputInfo, java.lang.String str, int i2, java.util.concurrent.Executor executor, android.media.tv.TvInputManager.HardwareCallback hardwareCallback) {
        com.android.internal.util.Preconditions.checkNotNull(tvInputInfo);
        com.android.internal.util.Preconditions.checkNotNull(hardwareCallback);
        return acquireTvInputHardwareInternal(i, tvInputInfo, str, i2, executor, hardwareCallback);
    }

    public void addHardwareDevice(int i) {
        try {
            this.mService.addHardwareDevice(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeHardwareDevice(int i) {
        try {
            this.mService.removeHardwareDevice(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.media.tv.TvInputManager$4, reason: invalid class name */
    class AnonymousClass4 extends android.media.tv.ITvInputHardwareCallback.Stub {
        final /* synthetic */ android.media.tv.TvInputManager.HardwareCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass4(java.util.concurrent.Executor executor, android.media.tv.TvInputManager.HardwareCallback hardwareCallback) {
            this.val$executor = executor;
            this.val$callback = hardwareCallback;
        }

        @Override // android.media.tv.ITvInputHardwareCallback
        public void onReleased() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.media.tv.TvInputManager.HardwareCallback hardwareCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.TvInputManager.HardwareCallback.this.onReleased();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.media.tv.ITvInputHardwareCallback
        public void onStreamConfigChanged(final android.media.tv.TvStreamConfig[] tvStreamConfigArr) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.media.tv.TvInputManager.HardwareCallback hardwareCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.media.tv.TvInputManager$4$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.TvInputManager.HardwareCallback.this.onStreamConfigChanged(tvStreamConfigArr);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private android.media.tv.TvInputManager.Hardware acquireTvInputHardwareInternal(int i, android.media.tv.TvInputInfo tvInputInfo, java.lang.String str, int i2, java.util.concurrent.Executor executor, android.media.tv.TvInputManager.HardwareCallback hardwareCallback) {
        try {
            android.media.tv.ITvInputHardware acquireTvInputHardware = this.mService.acquireTvInputHardware(i, new android.media.tv.TvInputManager.AnonymousClass4(executor, hardwareCallback), tvInputInfo, this.mUserId, str, i2);
            if (acquireTvInputHardware == null) {
                return null;
            }
            return new android.media.tv.TvInputManager.Hardware(acquireTvInputHardware);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void releaseTvInputHardware(int i, android.media.tv.TvInputManager.Hardware hardware) {
        try {
            this.mService.releaseTvInputHardware(i, hardware.getInterface(), this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.tv.DvbDeviceInfo> getDvbDeviceList() {
        try {
            return this.mService.getDvbDeviceList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.os.ParcelFileDescriptor openDvbDevice(android.media.tv.DvbDeviceInfo dvbDeviceInfo, int i) {
        try {
            if (i < 0 || 2 < i) {
                throw new java.lang.IllegalArgumentException("Invalid DVB device: " + i);
            }
            return this.mService.openDvbDevice(dvbDeviceInfo, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestChannelBrowsable(android.net.Uri uri) {
        try {
            this.mService.requestChannelBrowsable(uri, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.tv.TunedInfo> getCurrentTunedInfos() {
        try {
            return this.mService.getCurrentTunedInfos(this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final class Session {
        static final int DISPATCH_HANDLED = 1;
        static final int DISPATCH_IN_PROGRESS = -1;
        static final int DISPATCH_NOT_HANDLED = 0;
        private static final long INPUT_SESSION_NOT_RESPONDING_TIMEOUT = 2500;
        private android.media.tv.ad.TvAdManager.Session mAdSession;
        private final java.util.List<android.media.AudioPresentation> mAudioPresentations;
        private final java.util.List<android.media.tv.TvTrackInfo> mAudioTracks;
        private android.view.InputChannel mChannel;
        private final android.media.tv.TvInputManager.Session.InputEventHandler mHandler;
        private boolean mIAppNotificationEnabled;
        private android.media.tv.interactive.TvInteractiveAppManager.Session mIAppSession;
        private final java.lang.Object mMetadataLock;
        private final android.util.Pools.Pool<android.media.tv.TvInputManager.Session.PendingEvent> mPendingEventPool;
        private final android.util.SparseArray<android.media.tv.TvInputManager.Session.PendingEvent> mPendingEvents;
        private int mSelectedAudioPresentationId;
        private int mSelectedAudioProgramId;
        private java.lang.String mSelectedAudioTrackId;
        private java.lang.String mSelectedSubtitleTrackId;
        private java.lang.String mSelectedVideoTrackId;
        private android.media.tv.TvInputManager.Session.TvInputEventSender mSender;
        private final int mSeq;
        private final android.media.tv.ITvInputManager mService;
        private final android.util.SparseArray<android.media.tv.TvInputManager.SessionCallbackRecord> mSessionCallbackRecordMap;
        private final java.util.List<android.media.tv.TvTrackInfo> mSubtitleTracks;
        private android.os.IBinder mToken;
        private final int mUserId;
        private int mVideoHeight;
        private final java.util.List<android.media.tv.TvTrackInfo> mVideoTracks;
        private int mVideoWidth;

        public interface FinishedInputEventCallback {
            void onFinishedInputEvent(java.lang.Object obj, boolean z);
        }

        private Session(android.os.IBinder iBinder, android.view.InputChannel inputChannel, android.media.tv.ITvInputManager iTvInputManager, int i, int i2, android.util.SparseArray<android.media.tv.TvInputManager.SessionCallbackRecord> sparseArray) {
            this.mHandler = new android.media.tv.TvInputManager.Session.InputEventHandler(android.os.Looper.getMainLooper());
            this.mPendingEventPool = new android.util.Pools.SimplePool(20);
            this.mPendingEvents = new android.util.SparseArray<>(20);
            this.mMetadataLock = new java.lang.Object();
            this.mAudioPresentations = new java.util.ArrayList();
            this.mAudioTracks = new java.util.ArrayList();
            this.mVideoTracks = new java.util.ArrayList();
            this.mSubtitleTracks = new java.util.ArrayList();
            this.mSelectedAudioProgramId = -1;
            this.mSelectedAudioPresentationId = -1;
            this.mIAppNotificationEnabled = false;
            this.mToken = iBinder;
            this.mChannel = inputChannel;
            this.mService = iTvInputManager;
            this.mUserId = i;
            this.mSeq = i2;
            this.mSessionCallbackRecordMap = sparseArray;
        }

        public android.media.tv.interactive.TvInteractiveAppManager.Session getInteractiveAppSession() {
            return this.mIAppSession;
        }

        public void setInteractiveAppSession(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            this.mIAppSession = session;
        }

        public android.media.tv.ad.TvAdManager.Session getAdSession() {
            return this.mAdSession;
        }

        public void setAdSession(android.media.tv.ad.TvAdManager.Session session) {
            this.mAdSession = session;
        }

        public void release() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.releaseSession(this.mToken, this.mUserId);
                releaseInternal();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void setMain() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setMainSession(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSurface(android.view.Surface surface) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setSurface(this.mToken, surface, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void dispatchSurfaceChanged(int i, int i2, int i3) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.dispatchSurfaceChanged(this.mToken, i, i2, i3, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setStreamVolume(float f) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                if (f < 0.0f || f > 1.0f) {
                    throw new java.lang.IllegalArgumentException("volume should be between 0.0f and 1.0f");
                }
                this.mService.setVolume(this.mToken, f, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void tune(android.net.Uri uri) {
            tune(uri, null);
        }

        public void tune(android.net.Uri uri, android.os.Bundle bundle) {
            com.android.internal.util.Preconditions.checkNotNull(uri);
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            synchronized (this.mMetadataLock) {
                this.mAudioPresentations.clear();
                this.mAudioTracks.clear();
                this.mVideoTracks.clear();
                this.mSubtitleTracks.clear();
                this.mSelectedAudioProgramId = -1;
                this.mSelectedAudioPresentationId = -1;
                this.mSelectedAudioTrackId = null;
                this.mSelectedVideoTrackId = null;
                this.mSelectedSubtitleTrackId = null;
                this.mVideoWidth = 0;
                this.mVideoHeight = 0;
            }
            try {
                this.mService.tune(this.mToken, uri, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setCaptionEnabled(boolean z) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setCaptionEnabled(this.mToken, z, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void selectAudioPresentation(int i, int i2) {
            synchronized (this.mMetadataLock) {
                if (i != -1) {
                    if (!containsAudioPresentation(this.mAudioPresentations, i)) {
                        android.util.Log.w(android.media.tv.TvInputManager.TAG, "Invalid audio presentation id: " + i);
                        return;
                    }
                }
                if (this.mToken == null) {
                    android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                    return;
                }
                try {
                    this.mService.selectAudioPresentation(this.mToken, i, i2, this.mUserId);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        private boolean containsAudioPresentation(java.util.List<android.media.AudioPresentation> list, int i) {
            synchronized (this.mMetadataLock) {
                java.util.Iterator<android.media.AudioPresentation> it = list.iterator();
                while (it.hasNext()) {
                    if (it.next().getPresentationId() == i) {
                        return true;
                    }
                }
                return false;
            }
        }

        public java.util.List<android.media.AudioPresentation> getAudioPresentations() {
            synchronized (this.mMetadataLock) {
                if (this.mAudioPresentations == null) {
                    return new java.util.ArrayList();
                }
                return new java.util.ArrayList(this.mAudioPresentations);
            }
        }

        public int getSelectedProgramId() {
            int i;
            synchronized (this.mMetadataLock) {
                i = this.mSelectedAudioProgramId;
            }
            return i;
        }

        public int getSelectedAudioPresentationId() {
            int i;
            synchronized (this.mMetadataLock) {
                i = this.mSelectedAudioPresentationId;
            }
            return i;
        }

        boolean updateAudioPresentations(java.util.List<android.media.AudioPresentation> list) {
            boolean z;
            synchronized (this.mMetadataLock) {
                this.mAudioPresentations.clear();
                java.util.Iterator<android.media.AudioPresentation> it = list.iterator();
                while (it.hasNext()) {
                    this.mAudioPresentations.add(it.next());
                }
                z = !this.mAudioPresentations.isEmpty();
            }
            return z;
        }

        boolean updateAudioPresentationSelection(int i, int i2) {
            synchronized (this.mMetadataLock) {
                if (i2 == this.mSelectedAudioProgramId && i == this.mSelectedAudioPresentationId) {
                    return false;
                }
                this.mSelectedAudioPresentationId = i;
                this.mSelectedAudioProgramId = i2;
                return true;
            }
        }

        public void selectTrack(int i, java.lang.String str) {
            synchronized (this.mMetadataLock) {
                if (i == 0) {
                    if (str != null && !containsTrack(this.mAudioTracks, str)) {
                        android.util.Log.w(android.media.tv.TvInputManager.TAG, "Invalid audio trackId: " + str);
                        return;
                    }
                } else if (i == 1) {
                    if (str != null && !containsTrack(this.mVideoTracks, str)) {
                        android.util.Log.w(android.media.tv.TvInputManager.TAG, "Invalid video trackId: " + str);
                        return;
                    }
                } else if (i == 2) {
                    if (str != null && !containsTrack(this.mSubtitleTracks, str)) {
                        android.util.Log.w(android.media.tv.TvInputManager.TAG, "Invalid subtitle trackId: " + str);
                        return;
                    }
                } else {
                    throw new java.lang.IllegalArgumentException("invalid type: " + i);
                }
                if (this.mToken == null) {
                    android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                    return;
                }
                try {
                    this.mService.selectTrack(this.mToken, i, str, this.mUserId);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        private boolean containsTrack(java.util.List<android.media.tv.TvTrackInfo> list, java.lang.String str) {
            java.util.Iterator<android.media.tv.TvTrackInfo> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().getId().equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public java.util.List<android.media.tv.TvTrackInfo> getTracks(int i) {
            synchronized (this.mMetadataLock) {
                try {
                    if (i == 0) {
                        if (this.mAudioTracks == null) {
                            return null;
                        }
                        return new java.util.ArrayList(this.mAudioTracks);
                    }
                    if (i == 1) {
                        if (this.mVideoTracks == null) {
                            return null;
                        }
                        return new java.util.ArrayList(this.mVideoTracks);
                    }
                    if (i == 2) {
                        if (this.mSubtitleTracks == null) {
                            return null;
                        }
                        return new java.util.ArrayList(this.mSubtitleTracks);
                    }
                    throw new java.lang.IllegalArgumentException("invalid type: " + i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public java.lang.String getSelectedTrack(int i) {
            synchronized (this.mMetadataLock) {
                try {
                    if (i == 0) {
                        return this.mSelectedAudioTrackId;
                    }
                    if (i == 1) {
                        return this.mSelectedVideoTrackId;
                    }
                    if (i == 2) {
                        return this.mSelectedSubtitleTrackId;
                    }
                    throw new java.lang.IllegalArgumentException("invalid type: " + i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setInteractiveAppNotificationEnabled(boolean z) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setInteractiveAppNotificationEnabled(this.mToken, z, this.mUserId);
                this.mIAppNotificationEnabled = z;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean updateTracks(java.util.List<android.media.tv.TvTrackInfo> list) {
            boolean z;
            synchronized (this.mMetadataLock) {
                this.mAudioTracks.clear();
                this.mVideoTracks.clear();
                this.mSubtitleTracks.clear();
                java.util.Iterator<android.media.tv.TvTrackInfo> it = list.iterator();
                while (true) {
                    z = true;
                    if (!it.hasNext()) {
                        break;
                    }
                    android.media.tv.TvTrackInfo next = it.next();
                    if (next.getType() == 0) {
                        this.mAudioTracks.add(next);
                    } else if (next.getType() == 1) {
                        this.mVideoTracks.add(next);
                    } else if (next.getType() == 2) {
                        this.mSubtitleTracks.add(next);
                    }
                }
                if (this.mAudioTracks.isEmpty() && this.mVideoTracks.isEmpty() && this.mSubtitleTracks.isEmpty()) {
                    z = false;
                }
            }
            return z;
        }

        boolean updateTrackSelection(int i, java.lang.String str) {
            synchronized (this.mMetadataLock) {
                if (i == 0) {
                    try {
                        if (!android.text.TextUtils.equals(str, this.mSelectedAudioTrackId)) {
                            this.mSelectedAudioTrackId = str;
                            return true;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (i == 1 && !android.text.TextUtils.equals(str, this.mSelectedVideoTrackId)) {
                    this.mSelectedVideoTrackId = str;
                    return true;
                }
                if (i == 2 && !android.text.TextUtils.equals(str, this.mSelectedSubtitleTrackId)) {
                    this.mSelectedSubtitleTrackId = str;
                    return true;
                }
                return false;
            }
        }

        android.media.tv.TvTrackInfo getVideoTrackToNotify() {
            synchronized (this.mMetadataLock) {
                if (!this.mVideoTracks.isEmpty() && this.mSelectedVideoTrackId != null) {
                    for (android.media.tv.TvTrackInfo tvTrackInfo : this.mVideoTracks) {
                        if (tvTrackInfo.getId().equals(this.mSelectedVideoTrackId)) {
                            int videoWidth = tvTrackInfo.getVideoWidth();
                            int videoHeight = tvTrackInfo.getVideoHeight();
                            if (this.mVideoWidth != videoWidth || this.mVideoHeight != videoHeight) {
                                this.mVideoWidth = videoWidth;
                                this.mVideoHeight = videoHeight;
                                return tvTrackInfo;
                            }
                        }
                    }
                }
                return null;
            }
        }

        void timeShiftPlay(android.net.Uri uri) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftPlay(this.mToken, uri, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftPause() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftPause(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftResume() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftResume(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftSeekTo(long j) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftSeekTo(this.mToken, j, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftSetPlaybackParams(android.media.PlaybackParams playbackParams) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftSetPlaybackParams(this.mToken, playbackParams, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftSetMode(int i) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftSetMode(this.mToken, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftEnablePositionTracking(boolean z) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftEnablePositionTracking(this.mToken, z, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void stopPlayback(int i) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopPlayback(this.mToken, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void resumePlayback() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.resumePlayback(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void setVideoFrozen(boolean z) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setVideoFrozen(this.mToken, z, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvMessage(int i, android.os.Bundle bundle) {
            try {
                this.mService.notifyTvMessage(this.mToken, i, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setTvMessageEnabled(int i, boolean z) {
            try {
                this.mService.setTvMessageEnabled(this.mToken, i, z, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void startRecording(android.net.Uri uri) {
            startRecording(uri, null);
        }

        void startRecording(android.net.Uri uri, android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.startRecording(this.mToken, uri, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void stopRecording() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopRecording(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void pauseRecording(android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.pauseRecording(this.mToken, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void resumeRecording(android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.resumeRecording(this.mToken, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void sendAppPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendAppPrivateCommand(this.mToken, str, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void createOverlayView(android.view.View view, android.graphics.Rect rect) {
            com.android.internal.util.Preconditions.checkNotNull(view);
            com.android.internal.util.Preconditions.checkNotNull(rect);
            if (view.getWindowToken() == null) {
                throw new java.lang.IllegalStateException("view must be attached to a window");
            }
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.createOverlayView(this.mToken, view.getWindowToken(), rect, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void relayoutOverlayView(android.graphics.Rect rect) {
            com.android.internal.util.Preconditions.checkNotNull(rect);
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.relayoutOverlayView(this.mToken, rect, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void removeOverlayView() {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.removeOverlayView(this.mToken, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void unblockContent(android.media.tv.TvContentRating tvContentRating) {
            com.android.internal.util.Preconditions.checkNotNull(tvContentRating);
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.unblockContent(this.mToken, tvContentRating.flattenToString(), this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int dispatchInputEvent(android.view.InputEvent inputEvent, java.lang.Object obj, android.media.tv.TvInputManager.Session.FinishedInputEventCallback finishedInputEventCallback, android.os.Handler handler) {
            com.android.internal.util.Preconditions.checkNotNull(inputEvent);
            com.android.internal.util.Preconditions.checkNotNull(finishedInputEventCallback);
            com.android.internal.util.Preconditions.checkNotNull(handler);
            synchronized (this.mHandler) {
                if (this.mChannel == null) {
                    return 0;
                }
                android.media.tv.TvInputManager.Session.PendingEvent obtainPendingEventLocked = obtainPendingEventLocked(inputEvent, obj, finishedInputEventCallback, handler);
                if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) {
                    return sendInputEventOnMainLooperLocked(obtainPendingEventLocked);
                }
                android.os.Message obtainMessage = this.mHandler.obtainMessage(1, obtainPendingEventLocked);
                obtainMessage.setAsynchronous(true);
                this.mHandler.sendMessage(obtainMessage);
                return -1;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void sendInputEventAndReportResultOnMainLooper(android.media.tv.TvInputManager.Session.PendingEvent pendingEvent) {
            synchronized (this.mHandler) {
                if (sendInputEventOnMainLooperLocked(pendingEvent) == -1) {
                    return;
                }
                invokeFinishedInputEventCallback(pendingEvent, false);
            }
        }

        private int sendInputEventOnMainLooperLocked(android.media.tv.TvInputManager.Session.PendingEvent pendingEvent) {
            if (this.mChannel != null) {
                if (this.mSender == null) {
                    this.mSender = new android.media.tv.TvInputManager.Session.TvInputEventSender(this.mChannel, this.mHandler.getLooper());
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
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "Unable to send input event to session: " + this.mToken + " dropping:" + inputEvent);
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
                android.media.tv.TvInputManager.Session.PendingEvent valueAt = this.mPendingEvents.valueAt(indexOfKey);
                this.mPendingEvents.removeAt(indexOfKey);
                if (z2) {
                    android.util.Log.w(android.media.tv.TvInputManager.TAG, "Timeout waiting for session to handle input event after 2500 ms: " + this.mToken);
                } else {
                    this.mHandler.removeMessages(2, valueAt);
                }
                invokeFinishedInputEventCallback(valueAt, z);
            }
        }

        void invokeFinishedInputEventCallback(android.media.tv.TvInputManager.Session.PendingEvent pendingEvent, boolean z) {
            pendingEvent.mHandled = z;
            if (pendingEvent.mEventHandler.getLooper().isCurrentThread()) {
                pendingEvent.run();
                return;
            }
            android.os.Message obtain = android.os.Message.obtain(pendingEvent.mEventHandler, pendingEvent);
            obtain.setAsynchronous(true);
            obtain.sendToTarget();
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

        private android.media.tv.TvInputManager.Session.PendingEvent obtainPendingEventLocked(android.view.InputEvent inputEvent, java.lang.Object obj, android.media.tv.TvInputManager.Session.FinishedInputEventCallback finishedInputEventCallback, android.os.Handler handler) {
            android.media.tv.TvInputManager.Session.PendingEvent acquire = this.mPendingEventPool.acquire();
            if (acquire == null) {
                acquire = new android.media.tv.TvInputManager.Session.PendingEvent();
            }
            acquire.mEvent = inputEvent;
            acquire.mEventToken = obj;
            acquire.mCallback = finishedInputEventCallback;
            acquire.mEventHandler = handler;
            return acquire;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void recyclePendingEventLocked(android.media.tv.TvInputManager.Session.PendingEvent pendingEvent) {
            pendingEvent.recycle();
            this.mPendingEventPool.release(pendingEvent);
        }

        android.os.IBinder getToken() {
            return this.mToken;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void releaseInternal() {
            this.mToken = null;
            synchronized (this.mHandler) {
                if (this.mChannel != null) {
                    if (this.mSender != null) {
                        flushPendingEventsLocked();
                        this.mSender.dispose();
                        this.mSender = null;
                    }
                    this.mChannel.dispose();
                    this.mChannel = null;
                }
            }
            synchronized (this.mSessionCallbackRecordMap) {
                this.mSessionCallbackRecordMap.delete(this.mSeq);
            }
        }

        public void requestBroadcastInfo(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.requestBroadcastInfo(this.mToken, broadcastInfoRequest, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void removeBroadcastInfo(int i) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.removeBroadcastInfo(this.mToken, i, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void requestAd(android.media.tv.AdRequest adRequest) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.requestAd(this.mToken, adRequest, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyAdBufferReady(android.media.tv.AdBuffer adBuffer) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                try {
                    this.mService.notifyAdBufferReady(this.mToken, adBuffer, this.mUserId);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                if (adBuffer != null) {
                    adBuffer.getSharedMemory().close();
                }
            }
        }

        public void notifyTvAdSessionData(java.lang.String str, android.os.Bundle bundle) {
            if (this.mToken == null) {
                android.util.Log.w(android.media.tv.TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvAdSessionData(this.mToken, str, bundle, this.mUserId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
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
                        android.media.tv.TvInputManager.Session.this.sendInputEventAndReportResultOnMainLooper((android.media.tv.TvInputManager.Session.PendingEvent) message.obj);
                        break;
                    case 2:
                        android.media.tv.TvInputManager.Session.this.finishedInputEvent(message.arg1, false, true);
                        break;
                    case 3:
                        android.media.tv.TvInputManager.Session.this.finishedInputEvent(message.arg1, false, false);
                        break;
                }
            }
        }

        private final class TvInputEventSender extends android.view.InputEventSender {
            public TvInputEventSender(android.view.InputChannel inputChannel, android.os.Looper looper) {
                super(inputChannel, looper);
            }

            @Override // android.view.InputEventSender
            public void onInputEventFinished(int i, boolean z) {
                android.media.tv.TvInputManager.Session.this.finishedInputEvent(i, z, false);
            }
        }

        private final class PendingEvent implements java.lang.Runnable {
            public android.media.tv.TvInputManager.Session.FinishedInputEventCallback mCallback;
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
                    android.media.tv.TvInputManager.Session.this.recyclePendingEventLocked(this);
                }
            }
        }
    }

    @android.annotation.SystemApi
    public static final class Hardware {
        private final android.media.tv.ITvInputHardware mInterface;

        private Hardware(android.media.tv.ITvInputHardware iTvInputHardware) {
            this.mInterface = iTvInputHardware;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.media.tv.ITvInputHardware getInterface() {
            return this.mInterface;
        }

        public boolean setSurface(android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig) {
            try {
                return this.mInterface.setSurface(surface, tvStreamConfig);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public void setStreamVolume(float f) {
            try {
                this.mInterface.setStreamVolume(f);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        @android.annotation.SystemApi
        public boolean dispatchKeyEventToHdmi(android.view.KeyEvent keyEvent) {
            return false;
        }

        public void overrideAudioSink(int i, java.lang.String str, int i2, int i3, int i4) {
            try {
                this.mInterface.overrideAudioSink(i, str, i2, i3, i4);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public void overrideAudioSink(android.media.AudioDeviceInfo audioDeviceInfo, int i, int i2, int i3) {
            java.util.Objects.requireNonNull(audioDeviceInfo);
            try {
                this.mInterface.overrideAudioSink(android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(audioDeviceInfo.getType()), audioDeviceInfo.getAddress(), i, i2, i3);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }
}
