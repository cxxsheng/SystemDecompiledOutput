package android.media.tv;

/* loaded from: classes2.dex */
public class ITvInputSessionWrapper extends android.media.tv.ITvInputSession.Stub implements com.android.internal.os.HandlerCaller.Callback {
    private static final int DO_APP_PRIVATE_COMMAND = 9;
    private static final int DO_CREATE_OVERLAY_VIEW = 10;
    private static final int DO_DISPATCH_SURFACE_CHANGED = 4;
    private static final int DO_NOTIFY_AD_BUFFER = 28;
    private static final int DO_NOTIFY_AD_SESSION_DATA = 36;
    private static final int DO_NOTIFY_TV_MESSAGE = 32;
    private static final int DO_PAUSE_RECORDING = 22;
    private static final int DO_RELAYOUT_OVERLAY_VIEW = 11;
    private static final int DO_RELEASE = 1;
    private static final int DO_REMOVE_BROADCAST_INFO = 25;
    private static final int DO_REMOVE_OVERLAY_VIEW = 12;
    private static final int DO_REQUEST_AD = 27;
    private static final int DO_REQUEST_BROADCAST_INFO = 24;
    private static final int DO_RESUME_PLAYBACK = 34;
    private static final int DO_RESUME_RECORDING = 23;
    private static final int DO_SELECT_AUDIO_PRESENTATION = 29;
    private static final int DO_SELECT_TRACK = 8;
    private static final int DO_SET_CAPTION_ENABLED = 7;
    private static final int DO_SET_IAPP_NOTIFICATION_ENABLED = 26;
    private static final int DO_SET_MAIN = 2;
    private static final int DO_SET_STREAM_VOLUME = 5;
    private static final int DO_SET_SURFACE = 3;
    private static final int DO_SET_TV_MESSAGE_ENABLED = 31;
    private static final int DO_SET_VIDEO_FROZEN = 35;
    private static final int DO_START_RECORDING = 20;
    private static final int DO_STOP_PLAYBACK = 33;
    private static final int DO_STOP_RECORDING = 21;
    private static final int DO_TIME_SHIFT_ENABLE_POSITION_TRACKING = 19;
    private static final int DO_TIME_SHIFT_PAUSE = 15;
    private static final int DO_TIME_SHIFT_PLAY = 14;
    private static final int DO_TIME_SHIFT_RESUME = 16;
    private static final int DO_TIME_SHIFT_SEEK_TO = 17;
    private static final int DO_TIME_SHIFT_SET_MODE = 30;
    private static final int DO_TIME_SHIFT_SET_PLAYBACK_PARAMS = 18;
    private static final int DO_TUNE = 6;
    private static final int DO_UNBLOCK_CONTENT = 13;
    private static final int EXECUTE_MESSAGE_TIMEOUT_LONG_MILLIS = 5000;
    private static final int EXECUTE_MESSAGE_TIMEOUT_SHORT_MILLIS = 50;
    private static final int EXECUTE_MESSAGE_TUNE_TIMEOUT_MILLIS = 2000;
    private static final java.lang.String TAG = "TvInputSessionWrapper";
    private final com.android.internal.os.HandlerCaller mCaller;
    private android.view.InputChannel mChannel;
    private final boolean mIsRecordingSession = true;
    private android.media.tv.ITvInputSessionWrapper.TvInputEventReceiver mReceiver;
    private android.media.tv.TvInputService.RecordingSession mTvInputRecordingSessionImpl;
    private android.media.tv.TvInputService.Session mTvInputSessionImpl;

    public ITvInputSessionWrapper(android.content.Context context, android.media.tv.TvInputService.Session session, android.view.InputChannel inputChannel) {
        this.mCaller = new com.android.internal.os.HandlerCaller(context, null, this, true);
        this.mTvInputSessionImpl = session;
        this.mChannel = inputChannel;
        if (inputChannel != null) {
            this.mReceiver = new android.media.tv.ITvInputSessionWrapper.TvInputEventReceiver(inputChannel, context.getMainLooper());
        }
    }

    public ITvInputSessionWrapper(android.content.Context context, android.media.tv.TvInputService.RecordingSession recordingSession) {
        this.mCaller = new com.android.internal.os.HandlerCaller(context, null, this, true);
        this.mTvInputRecordingSessionImpl = recordingSession;
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(android.os.Message message) {
        if (this.mIsRecordingSession && this.mTvInputRecordingSessionImpl == null) {
            return;
        }
        if (!this.mIsRecordingSession && this.mTvInputSessionImpl == null) {
            return;
        }
        long nanoTime = java.lang.System.nanoTime();
        switch (message.what) {
            case 1:
                if (this.mIsRecordingSession) {
                    this.mTvInputRecordingSessionImpl.release();
                    this.mTvInputRecordingSessionImpl = null;
                    break;
                } else {
                    this.mTvInputSessionImpl.release();
                    this.mTvInputSessionImpl = null;
                    if (this.mReceiver != null) {
                        this.mReceiver.dispose();
                        this.mReceiver = null;
                    }
                    if (this.mChannel != null) {
                        this.mChannel.dispose();
                        this.mChannel = null;
                        break;
                    }
                }
                break;
            case 2:
                this.mTvInputSessionImpl.setMain(((java.lang.Boolean) message.obj).booleanValue());
                break;
            case 3:
                this.mTvInputSessionImpl.setSurface((android.view.Surface) message.obj);
                break;
            case 4:
                com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                this.mTvInputSessionImpl.dispatchSurfaceChanged(someArgs.argi1, someArgs.argi2, someArgs.argi3);
                someArgs.recycle();
                break;
            case 5:
                this.mTvInputSessionImpl.setStreamVolume(((java.lang.Float) message.obj).floatValue());
                break;
            case 6:
                com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                if (this.mIsRecordingSession) {
                    this.mTvInputRecordingSessionImpl.tune((android.net.Uri) someArgs2.arg1, (android.os.Bundle) someArgs2.arg2);
                } else {
                    this.mTvInputSessionImpl.tune((android.net.Uri) someArgs2.arg1, (android.os.Bundle) someArgs2.arg2);
                }
                someArgs2.recycle();
                break;
            case 7:
                this.mTvInputSessionImpl.setCaptionEnabled(((java.lang.Boolean) message.obj).booleanValue());
                break;
            case 8:
                com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                this.mTvInputSessionImpl.selectTrack(((java.lang.Integer) someArgs3.arg1).intValue(), (java.lang.String) someArgs3.arg2);
                someArgs3.recycle();
                break;
            case 9:
                com.android.internal.os.SomeArgs someArgs4 = (com.android.internal.os.SomeArgs) message.obj;
                if (this.mIsRecordingSession) {
                    this.mTvInputRecordingSessionImpl.appPrivateCommand((java.lang.String) someArgs4.arg1, (android.os.Bundle) someArgs4.arg2);
                } else {
                    this.mTvInputSessionImpl.appPrivateCommand((java.lang.String) someArgs4.arg1, (android.os.Bundle) someArgs4.arg2);
                }
                someArgs4.recycle();
                break;
            case 10:
                com.android.internal.os.SomeArgs someArgs5 = (com.android.internal.os.SomeArgs) message.obj;
                this.mTvInputSessionImpl.createOverlayView((android.os.IBinder) someArgs5.arg1, (android.graphics.Rect) someArgs5.arg2);
                someArgs5.recycle();
                break;
            case 11:
                this.mTvInputSessionImpl.relayoutOverlayView((android.graphics.Rect) message.obj);
                break;
            case 12:
                this.mTvInputSessionImpl.removeOverlayView(true);
                break;
            case 13:
                this.mTvInputSessionImpl.unblockContent((java.lang.String) message.obj);
                break;
            case 14:
                this.mTvInputSessionImpl.timeShiftPlay((android.net.Uri) message.obj);
                break;
            case 15:
                this.mTvInputSessionImpl.timeShiftPause();
                break;
            case 16:
                this.mTvInputSessionImpl.timeShiftResume();
                break;
            case 17:
                this.mTvInputSessionImpl.timeShiftSeekTo(((java.lang.Long) message.obj).longValue());
                break;
            case 18:
                this.mTvInputSessionImpl.timeShiftSetPlaybackParams((android.media.PlaybackParams) message.obj);
                break;
            case 19:
                this.mTvInputSessionImpl.timeShiftEnablePositionTracking(((java.lang.Boolean) message.obj).booleanValue());
                break;
            case 20:
                com.android.internal.os.SomeArgs someArgs6 = (com.android.internal.os.SomeArgs) message.obj;
                this.mTvInputRecordingSessionImpl.startRecording((android.net.Uri) someArgs6.arg1, (android.os.Bundle) someArgs6.arg2);
                someArgs6.recycle();
                break;
            case 21:
                this.mTvInputRecordingSessionImpl.stopRecording();
                break;
            case 22:
                this.mTvInputRecordingSessionImpl.pauseRecording((android.os.Bundle) message.obj);
                break;
            case 23:
                this.mTvInputRecordingSessionImpl.resumeRecording((android.os.Bundle) message.obj);
                break;
            case 24:
                this.mTvInputSessionImpl.requestBroadcastInfo((android.media.tv.BroadcastInfoRequest) message.obj);
                break;
            case 25:
                this.mTvInputSessionImpl.removeBroadcastInfo(message.arg1);
                break;
            case 26:
                this.mTvInputSessionImpl.setInteractiveAppNotificationEnabled(((java.lang.Boolean) message.obj).booleanValue());
                break;
            case 27:
                this.mTvInputSessionImpl.requestAd((android.media.tv.AdRequest) message.obj);
                break;
            case 28:
                this.mTvInputSessionImpl.notifyAdBufferReady((android.media.tv.AdBuffer) message.obj);
                break;
            case 29:
                com.android.internal.os.SomeArgs someArgs7 = (com.android.internal.os.SomeArgs) message.obj;
                this.mTvInputSessionImpl.selectAudioPresentation(((java.lang.Integer) someArgs7.arg1).intValue(), ((java.lang.Integer) someArgs7.arg2).intValue());
                someArgs7.recycle();
                break;
            case 30:
                this.mTvInputSessionImpl.timeShiftSetMode(message.arg1);
                break;
            case 31:
                com.android.internal.os.SomeArgs someArgs8 = (com.android.internal.os.SomeArgs) message.obj;
                this.mTvInputSessionImpl.setTvMessageEnabled(((java.lang.Integer) someArgs8.arg1).intValue(), ((java.lang.Boolean) someArgs8.arg2).booleanValue());
                someArgs8.recycle();
                break;
            case 32:
                com.android.internal.os.SomeArgs someArgs9 = (com.android.internal.os.SomeArgs) message.obj;
                this.mTvInputSessionImpl.onTvMessageReceived(((java.lang.Integer) someArgs9.arg1).intValue(), (android.os.Bundle) someArgs9.arg2);
                someArgs9.recycle();
                break;
            case 33:
                this.mTvInputSessionImpl.stopPlayback(message.arg1);
                break;
            case 34:
                this.mTvInputSessionImpl.resumePlayback();
                break;
            case 35:
                this.mTvInputSessionImpl.setVideoFrozen(((java.lang.Boolean) message.obj).booleanValue());
                break;
            case 36:
                com.android.internal.os.SomeArgs someArgs10 = (com.android.internal.os.SomeArgs) message.obj;
                this.mTvInputSessionImpl.notifyTvAdSessionData((java.lang.String) someArgs10.arg1, (android.os.Bundle) someArgs10.arg2);
                someArgs10.recycle();
                break;
            default:
                android.util.Log.w(TAG, "Unhandled message code: " + message.what);
                break;
        }
        long nanoTime2 = (java.lang.System.nanoTime() - nanoTime) / 1000000;
        if (nanoTime2 > 50) {
            android.util.Log.w(TAG, "Handling message (" + message.what + ") took too long time (duration=" + nanoTime2 + "ms)");
            if (message.what == 6 && nanoTime2 > 2000) {
                throw new java.lang.RuntimeException("Too much time to handle tune request. (" + nanoTime2 + "ms > 2000ms) Consider handling the tune request in a separate thread.");
            }
            if (nanoTime2 > 5000) {
                throw new java.lang.RuntimeException("Too much time to handle a request. (type=" + message.what + ", " + nanoTime2 + "ms > 5000ms).");
            }
        }
    }

    @Override // android.media.tv.ITvInputSession
    public void release() {
        if (!this.mIsRecordingSession) {
            this.mTvInputSessionImpl.scheduleOverlayViewCleanup();
        }
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(1));
    }

    @Override // android.media.tv.ITvInputSession
    public void setMain(boolean z) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(2, java.lang.Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void setSurface(android.view.Surface surface) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(3, surface));
    }

    @Override // android.media.tv.ITvInputSession
    public void dispatchSurfaceChanged(int i, int i2, int i3) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIIII(4, i, i2, i3, 0));
    }

    @Override // android.media.tv.ITvInputSession
    public final void setVolume(float f) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(5, java.lang.Float.valueOf(f)));
    }

    @Override // android.media.tv.ITvInputSession
    public void tune(android.net.Uri uri, android.os.Bundle bundle) {
        this.mCaller.removeMessages(6);
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(6, uri, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void setCaptionEnabled(boolean z) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(7, java.lang.Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void selectAudioPresentation(int i, int i2) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(29, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
    }

    @Override // android.media.tv.ITvInputSession
    public void selectTrack(int i, java.lang.String str) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(8, java.lang.Integer.valueOf(i), str));
    }

    @Override // android.media.tv.ITvInputSession
    public void setInteractiveAppNotificationEnabled(boolean z) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(26, java.lang.Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(9, str, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void createOverlayView(android.os.IBinder iBinder, android.graphics.Rect rect) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(10, iBinder, rect));
    }

    @Override // android.media.tv.ITvInputSession
    public void relayoutOverlayView(android.graphics.Rect rect) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(11, rect));
    }

    @Override // android.media.tv.ITvInputSession
    public void removeOverlayView() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(12));
    }

    @Override // android.media.tv.ITvInputSession
    public void unblockContent(java.lang.String str) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(13, str));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftPlay(android.net.Uri uri) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(14, uri));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftPause() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(15));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftResume() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(16));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftSeekTo(long j) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(17, java.lang.Long.valueOf(j)));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftSetPlaybackParams(android.media.PlaybackParams playbackParams) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(18, playbackParams));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftSetMode(int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(30, i));
    }

    @Override // android.media.tv.ITvInputSession
    public void timeShiftEnablePositionTracking(boolean z) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(19, java.lang.Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void startRecording(android.net.Uri uri, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(20, uri, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void stopRecording() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(21));
    }

    @Override // android.media.tv.ITvInputSession
    public void pauseRecording(android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(22, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void resumeRecording(android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(23, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void requestBroadcastInfo(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(24, broadcastInfoRequest));
    }

    @Override // android.media.tv.ITvInputSession
    public void removeBroadcastInfo(int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(25, i));
    }

    @Override // android.media.tv.ITvInputSession
    public void requestAd(android.media.tv.AdRequest adRequest) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(27, adRequest));
    }

    @Override // android.media.tv.ITvInputSession
    public void notifyAdBufferReady(android.media.tv.AdBuffer adBuffer) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(28, adBuffer));
    }

    @Override // android.media.tv.ITvInputSession
    public void notifyTvAdSessionData(java.lang.String str, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(36, str, bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void setVideoFrozen(boolean z) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(35, java.lang.Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void notifyTvMessage(int i, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(32, java.lang.Integer.valueOf(i), bundle));
    }

    @Override // android.media.tv.ITvInputSession
    public void setTvMessageEnabled(int i, boolean z) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(31, java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z)));
    }

    @Override // android.media.tv.ITvInputSession
    public void stopPlayback(int i) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageI(33, i));
    }

    @Override // android.media.tv.ITvInputSession
    public void resumePlayback() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(34));
    }

    private final class TvInputEventReceiver extends android.view.InputEventReceiver {
        TvInputEventReceiver(android.view.InputChannel inputChannel, android.os.Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(android.view.InputEvent inputEvent) {
            if (android.media.tv.ITvInputSessionWrapper.this.mTvInputSessionImpl == null) {
                finishInputEvent(inputEvent, false);
                return;
            }
            int dispatchInputEvent = android.media.tv.ITvInputSessionWrapper.this.mTvInputSessionImpl.dispatchInputEvent(inputEvent, this);
            if (dispatchInputEvent != -1) {
                finishInputEvent(inputEvent, dispatchInputEvent == 1);
            }
        }
    }
}
