package android.media.tv.interactive;

/* loaded from: classes2.dex */
public class ITvInteractiveAppSessionWrapper extends android.media.tv.interactive.ITvInteractiveAppSession.Stub implements com.android.internal.os.HandlerCaller.Callback {
    private static final int DO_CREATE_BI_INTERACTIVE_APP = 5;
    private static final int DO_CREATE_MEDIA_VIEW = 27;
    private static final int DO_DESTROY_BI_INTERACTIVE_APP = 6;
    private static final int DO_DISPATCH_SURFACE_CHANGED = 24;
    private static final int DO_NOTIFY_AD_BUFFER_CONSUMED = 32;
    private static final int DO_NOTIFY_AD_RESPONSE = 26;
    private static final int DO_NOTIFY_BROADCAST_INFO_RESPONSE = 25;
    private static final int DO_NOTIFY_CONTENT_ALLOWED = 20;
    private static final int DO_NOTIFY_CONTENT_BLOCKED = 21;
    private static final int DO_NOTIFY_ERROR = 14;
    private static final int DO_NOTIFY_RECORDING_CONNECTION_FAILED = 41;
    private static final int DO_NOTIFY_RECORDING_DISCONNECTED = 42;
    private static final int DO_NOTIFY_RECORDING_ERROR = 44;
    private static final int DO_NOTIFY_RECORDING_SCHEDULED = 45;
    private static final int DO_NOTIFY_RECORDING_STARTED = 30;
    private static final int DO_NOTIFY_RECORDING_STOPPED = 31;
    private static final int DO_NOTIFY_RECORDING_TUNED = 43;
    private static final int DO_NOTIFY_SIGNAL_STRENGTH = 22;
    private static final int DO_NOTIFY_TIME_SHIFT_CURRENT_POSITION_CHANGED = 39;
    private static final int DO_NOTIFY_TIME_SHIFT_PLAYBACK_PARAMS = 36;
    private static final int DO_NOTIFY_TIME_SHIFT_START_POSITION_CHANGED = 38;
    private static final int DO_NOTIFY_TIME_SHIFT_STATUS_CHANGED = 37;
    private static final int DO_NOTIFY_TRACKS_CHANGED = 17;
    private static final int DO_NOTIFY_TRACK_SELECTED = 16;
    private static final int DO_NOTIFY_TUNED = 15;
    private static final int DO_NOTIFY_TV_MESSAGE = 33;
    private static final int DO_NOTIFY_VIDEO_AVAILABLE = 18;
    private static final int DO_NOTIFY_VIDEO_FREEZE_UPDATED = 49;
    private static final int DO_NOTIFY_VIDEO_UNAVAILABLE = 19;
    private static final int DO_RELAYOUT_MEDIA_VIEW = 28;
    private static final int DO_RELEASE = 1;
    private static final int DO_REMOVE_MEDIA_VIEW = 29;
    private static final int DO_RESET_INTERACTIVE_APP = 4;
    private static final int DO_SEND_AVAILABLE_SPEEDS = 47;
    private static final int DO_SEND_CERTIFICATE = 50;
    private static final int DO_SEND_CURRENT_CHANNEL_LCN = 9;
    private static final int DO_SEND_CURRENT_CHANNEL_URI = 8;
    private static final int DO_SEND_CURRENT_TV_INPUT_ID = 12;
    private static final int DO_SEND_CURRENT_VIDEO_BOUNDS = 40;
    private static final int DO_SEND_RECORDING_INFO = 34;
    private static final int DO_SEND_RECORDING_INFO_LIST = 35;
    private static final int DO_SEND_SELECTED_TRACK_INFO = 48;
    private static final int DO_SEND_SIGNING_RESULT = 13;
    private static final int DO_SEND_STREAM_VOLUME = 10;
    private static final int DO_SEND_TIME_SHIFT_MODE = 46;
    private static final int DO_SEND_TRACK_INFO_LIST = 11;
    private static final int DO_SET_SURFACE = 23;
    private static final int DO_SET_TELETEXT_APP_ENABLED = 7;
    private static final int DO_START_INTERACTIVE_APP = 2;
    private static final int DO_STOP_INTERACTIVE_APP = 3;
    private static final int EXECUTE_MESSAGE_TIMEOUT_LONG_MILLIS = 5000;
    private static final int EXECUTE_MESSAGE_TIMEOUT_SHORT_MILLIS = 1000;
    private static final java.lang.String TAG = "ITvInteractiveAppSessionWrapper";
    private final com.android.internal.os.HandlerCaller mCaller;
    private android.view.InputChannel mChannel;
    private android.media.tv.interactive.ITvInteractiveAppSessionWrapper.TvInteractiveAppEventReceiver mReceiver;
    private android.media.tv.interactive.TvInteractiveAppService.Session mSessionImpl;

    public ITvInteractiveAppSessionWrapper(android.content.Context context, android.media.tv.interactive.TvInteractiveAppService.Session session, android.view.InputChannel inputChannel) {
        this.mSessionImpl = session;
        this.mCaller = new com.android.internal.os.HandlerCaller(context, null, this, true);
        this.mChannel = inputChannel;
        if (inputChannel != null) {
            this.mReceiver = new android.media.tv.interactive.ITvInteractiveAppSessionWrapper.TvInteractiveAppEventReceiver(inputChannel, context.getMainLooper());
        }
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(android.os.Message message) {
        if (this.mSessionImpl == null) {
            return;
        }
        long nanoTime = java.lang.System.nanoTime();
        switch (message.what) {
            case 1:
                this.mSessionImpl.release();
                this.mSessionImpl = null;
                if (this.mReceiver != null) {
                    this.mReceiver.dispose();
                    this.mReceiver = null;
                }
                if (this.mChannel != null) {
                    this.mChannel.dispose();
                    this.mChannel = null;
                    break;
                }
                break;
            case 2:
                this.mSessionImpl.startInteractiveApp();
                break;
            case 3:
                this.mSessionImpl.stopInteractiveApp();
                break;
            case 4:
                this.mSessionImpl.resetInteractiveApp();
                break;
            case 5:
                com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.createBiInteractiveApp((android.net.Uri) someArgs.arg1, (android.os.Bundle) someArgs.arg2);
                someArgs.recycle();
                break;
            case 6:
                this.mSessionImpl.destroyBiInteractiveApp((java.lang.String) message.obj);
                break;
            case 7:
                this.mSessionImpl.setTeletextAppEnabled(((java.lang.Boolean) message.obj).booleanValue());
                break;
            case 8:
                this.mSessionImpl.sendCurrentChannelUri((android.net.Uri) message.obj);
                break;
            case 9:
                this.mSessionImpl.sendCurrentChannelLcn(((java.lang.Integer) message.obj).intValue());
                break;
            case 10:
                this.mSessionImpl.sendStreamVolume(((java.lang.Float) message.obj).floatValue());
                break;
            case 11:
                this.mSessionImpl.sendTrackInfoList((java.util.List) message.obj);
                break;
            case 12:
                this.mSessionImpl.sendCurrentTvInputId((java.lang.String) message.obj);
                break;
            case 13:
                com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.sendSigningResult((java.lang.String) someArgs2.arg1, (byte[]) someArgs2.arg2);
                someArgs2.recycle();
                break;
            case 14:
                com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyError((java.lang.String) someArgs3.arg1, (android.os.Bundle) someArgs3.arg2);
                someArgs3.recycle();
                break;
            case 15:
                this.mSessionImpl.notifyTuned((android.net.Uri) message.obj);
                break;
            case 16:
                com.android.internal.os.SomeArgs someArgs4 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyTrackSelected(((java.lang.Integer) someArgs4.arg1).intValue(), (java.lang.String) someArgs4.arg2);
                someArgs4.recycle();
                break;
            case 17:
                this.mSessionImpl.notifyTracksChanged((java.util.List) message.obj);
                break;
            case 18:
                this.mSessionImpl.notifyVideoAvailable();
                break;
            case 19:
                this.mSessionImpl.notifyVideoUnavailable(((java.lang.Integer) message.obj).intValue());
                break;
            case 20:
                this.mSessionImpl.notifyContentAllowed();
                break;
            case 21:
                this.mSessionImpl.notifyContentBlocked((android.media.tv.TvContentRating) message.obj);
                break;
            case 22:
                this.mSessionImpl.notifySignalStrength(((java.lang.Integer) message.obj).intValue());
                break;
            case 23:
                this.mSessionImpl.setSurface((android.view.Surface) message.obj);
                break;
            case 24:
                com.android.internal.os.SomeArgs someArgs5 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.dispatchSurfaceChanged(java.lang.Integer.valueOf(someArgs5.argi1).intValue(), java.lang.Integer.valueOf(someArgs5.argi2).intValue(), java.lang.Integer.valueOf(someArgs5.argi3).intValue());
                someArgs5.recycle();
                break;
            case 25:
                this.mSessionImpl.notifyBroadcastInfoResponse((android.media.tv.BroadcastInfoResponse) message.obj);
                break;
            case 26:
                this.mSessionImpl.notifyAdResponse((android.media.tv.AdResponse) message.obj);
                break;
            case 27:
                com.android.internal.os.SomeArgs someArgs6 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.createMediaView((android.os.IBinder) someArgs6.arg1, (android.graphics.Rect) someArgs6.arg2);
                someArgs6.recycle();
                break;
            case 28:
                this.mSessionImpl.relayoutMediaView((android.graphics.Rect) message.obj);
                break;
            case 29:
                this.mSessionImpl.removeMediaView(true);
                break;
            case 30:
                com.android.internal.os.SomeArgs someArgs7 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingStarted((java.lang.String) someArgs7.arg1, (java.lang.String) someArgs7.arg2);
                someArgs7.recycle();
                break;
            case 31:
                this.mSessionImpl.notifyRecordingStopped((java.lang.String) message.obj);
                break;
            case 32:
                this.mSessionImpl.notifyAdBufferConsumed((android.media.tv.AdBuffer) message.obj);
                break;
            case 33:
                com.android.internal.os.SomeArgs someArgs8 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyTvMessage(((java.lang.Integer) someArgs8.arg1).intValue(), (android.os.Bundle) someArgs8.arg2);
                someArgs8.recycle();
                break;
            case 34:
                this.mSessionImpl.sendTvRecordingInfo((android.media.tv.TvRecordingInfo) message.obj);
                break;
            case 35:
                this.mSessionImpl.sendTvRecordingInfoList((java.util.List) message.obj);
                break;
            case 36:
                this.mSessionImpl.notifyTimeShiftPlaybackParams((android.media.PlaybackParams) message.obj);
                break;
            case 37:
                com.android.internal.os.SomeArgs someArgs9 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyTimeShiftStatusChanged((java.lang.String) someArgs9.arg1, ((java.lang.Integer) someArgs9.arg2).intValue());
                someArgs9.recycle();
                break;
            case 38:
                com.android.internal.os.SomeArgs someArgs10 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyTimeShiftStartPositionChanged((java.lang.String) someArgs10.arg1, ((java.lang.Long) someArgs10.arg2).longValue());
                someArgs10.recycle();
                break;
            case 39:
                com.android.internal.os.SomeArgs someArgs11 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyTimeShiftCurrentPositionChanged((java.lang.String) someArgs11.arg1, ((java.lang.Long) someArgs11.arg2).longValue());
                someArgs11.recycle();
                break;
            case 40:
                this.mSessionImpl.sendCurrentVideoBounds((android.graphics.Rect) message.obj);
                break;
            case 41:
                com.android.internal.os.SomeArgs someArgs12 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingConnectionFailed((java.lang.String) someArgs12.arg1, (java.lang.String) someArgs12.arg2);
                someArgs12.recycle();
                break;
            case 42:
                com.android.internal.os.SomeArgs someArgs13 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingDisconnected((java.lang.String) someArgs13.arg1, (java.lang.String) someArgs13.arg2);
                someArgs13.recycle();
                break;
            case 43:
                com.android.internal.os.SomeArgs someArgs14 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingTuned((java.lang.String) someArgs14.arg1, (android.net.Uri) someArgs14.arg2);
                someArgs14.recycle();
                break;
            case 44:
                com.android.internal.os.SomeArgs someArgs15 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingError((java.lang.String) someArgs15.arg1, ((java.lang.Integer) someArgs15.arg2).intValue());
                someArgs15.recycle();
                break;
            case 45:
                com.android.internal.os.SomeArgs someArgs16 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyRecordingScheduled((java.lang.String) someArgs16.arg1, (java.lang.String) someArgs16.arg2);
                someArgs16.recycle();
                break;
            case 46:
                this.mSessionImpl.sendTimeShiftMode(((java.lang.Integer) message.obj).intValue());
                break;
            case 47:
                this.mSessionImpl.sendAvailableSpeeds((float[]) message.obj);
                break;
            case 48:
                this.mSessionImpl.sendSelectedTrackInfo((java.util.List) message.obj);
                break;
            case 49:
                this.mSessionImpl.notifyVideoFreezeUpdated(((java.lang.Boolean) message.obj).booleanValue());
                break;
            case 50:
                com.android.internal.os.SomeArgs someArgs17 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.sendCertificate((java.lang.String) someArgs17.arg1, ((java.lang.Integer) someArgs17.arg2).intValue(), (android.os.Bundle) someArgs17.arg3);
                someArgs17.recycle();
                break;
            default:
                android.util.Log.w(TAG, "Unhandled message code: " + message.what);
                break;
        }
        long nanoTime2 = (java.lang.System.nanoTime() - nanoTime) / 1000000;
        if (nanoTime2 > 1000) {
            android.util.Log.w(TAG, "Handling message (" + message.what + ") took too long time (duration=" + nanoTime2 + "ms)");
        }
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void startInteractiveApp() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void stopInteractiveApp() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(3));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void resetInteractiveApp() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(4));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void createBiInteractiveApp(android.net.Uri uri, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(5, uri, bundle));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void destroyBiInteractiveApp(java.lang.String str) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(6, str));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void setTeletextAppEnabled(boolean z) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(7, java.lang.Boolean.valueOf(z)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentVideoBounds(android.graphics.Rect rect) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(40, rect));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentChannelUri(android.net.Uri uri) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(8, uri));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentChannelLcn(int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(9, java.lang.Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendStreamVolume(float f) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(10, java.lang.Float.valueOf(f)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(11, list));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCurrentTvInputId(java.lang.String str) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(12, str));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTimeShiftMode(int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(46, java.lang.Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendAvailableSpeeds(float[] fArr) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(47, fArr));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTvRecordingInfo(android.media.tv.TvRecordingInfo tvRecordingInfo) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(34, tvRecordingInfo));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendTvRecordingInfoList(java.util.List<android.media.tv.TvRecordingInfo> list) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(35, list));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendSigningResult(java.lang.String str, byte[] bArr) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(13, str, bArr));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendCertificate(java.lang.String str, int i, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOOO(50, str, java.lang.Integer.valueOf(i), bundle));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyError(java.lang.String str, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(14, str, bundle));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftPlaybackParams(android.media.PlaybackParams playbackParams) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(36, playbackParams));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftStatusChanged(java.lang.String str, int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(37, str, java.lang.Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftStartPositionChanged(java.lang.String str, long j) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(38, str, java.lang.Long.valueOf(j)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTimeShiftCurrentPositionChanged(java.lang.String str, long j) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(39, str, java.lang.Long.valueOf(j)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void release() {
        this.mSessionImpl.scheduleMediaViewCleanup();
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(1));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTuned(android.net.Uri uri) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(15, uri));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTrackSelected(int i, java.lang.String str) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(16, java.lang.Integer.valueOf(i), str));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTvMessage(int i, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(33, java.lang.Integer.valueOf(i), bundle));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void sendSelectedTrackInfo(java.util.List<android.media.tv.TvTrackInfo> list) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(48, list));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(17, list));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyVideoAvailable() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(18));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyVideoUnavailable(int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(19, java.lang.Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyVideoFreezeUpdated(boolean z) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(49, java.lang.Boolean.valueOf(z)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyContentAllowed() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(20));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyContentBlocked(java.lang.String str) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(21, str));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifySignalStrength(int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(22, java.lang.Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingStarted(java.lang.String str, java.lang.String str2) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(30, str, str2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingStopped(java.lang.String str) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(31, str));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingConnectionFailed(java.lang.String str, java.lang.String str2) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(41, str, str2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingDisconnected(java.lang.String str, java.lang.String str2) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(42, str, str2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingTuned(java.lang.String str, android.net.Uri uri) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(43, str, uri));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingError(java.lang.String str, int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(44, str, java.lang.Integer.valueOf(i)));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyRecordingScheduled(java.lang.String str, java.lang.String str2) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(45, str, str2));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void setSurface(android.view.Surface surface) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(23, surface));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void dispatchSurfaceChanged(int i, int i2, int i3) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIIII(24, i, i2, i3, 0));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(25, broadcastInfoResponse));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyAdResponse(android.media.tv.AdResponse adResponse) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(26, adResponse));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void notifyAdBufferConsumed(android.media.tv.AdBuffer adBuffer) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(32, adBuffer));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void createMediaView(android.os.IBinder iBinder, android.graphics.Rect rect) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(27, iBinder, rect));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void relayoutMediaView(android.graphics.Rect rect) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(28, rect));
    }

    @Override // android.media.tv.interactive.ITvInteractiveAppSession
    public void removeMediaView() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(29));
    }

    private final class TvInteractiveAppEventReceiver extends android.view.InputEventReceiver {
        TvInteractiveAppEventReceiver(android.view.InputChannel inputChannel, android.os.Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(android.view.InputEvent inputEvent) {
            if (android.media.tv.interactive.ITvInteractiveAppSessionWrapper.this.mSessionImpl == null) {
                finishInputEvent(inputEvent, false);
                return;
            }
            int dispatchInputEvent = android.media.tv.interactive.ITvInteractiveAppSessionWrapper.this.mSessionImpl.dispatchInputEvent(inputEvent, this);
            if (dispatchInputEvent != -1) {
                finishInputEvent(inputEvent, dispatchInputEvent == 1);
            }
        }
    }
}
