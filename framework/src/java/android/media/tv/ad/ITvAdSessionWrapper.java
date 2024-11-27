package android.media.tv.ad;

/* loaded from: classes2.dex */
public class ITvAdSessionWrapper extends android.media.tv.ad.ITvAdSession.Stub implements com.android.internal.os.HandlerCaller.Callback {
    private static final int DO_CREATE_MEDIA_VIEW = 4;
    private static final int DO_DISPATCH_SURFACE_CHANGED = 3;
    private static final int DO_NOTIFY_ERROR = 15;
    private static final int DO_NOTIFY_INPUT_SESSION_DATA = 17;
    private static final int DO_NOTIFY_TV_MESSAGE = 16;
    private static final int DO_RELAYOUT_MEDIA_VIEW = 5;
    private static final int DO_RELEASE = 1;
    private static final int DO_REMOVE_MEDIA_VIEW = 6;
    private static final int DO_RESET_AD_SERVICE = 9;
    private static final int DO_SEND_CURRENT_CHANNEL_URI = 11;
    private static final int DO_SEND_CURRENT_TV_INPUT_ID = 13;
    private static final int DO_SEND_CURRENT_VIDEO_BOUNDS = 10;
    private static final int DO_SEND_SIGNING_RESULT = 14;
    private static final int DO_SEND_TRACK_INFO_LIST = 12;
    private static final int DO_SET_SURFACE = 2;
    private static final int DO_START_AD_SERVICE = 7;
    private static final int DO_STOP_AD_SERVICE = 8;
    private static final int EXECUTE_MESSAGE_TIMEOUT_LONG_MILLIS = 5000;
    private static final int EXECUTE_MESSAGE_TIMEOUT_SHORT_MILLIS = 1000;
    private static final java.lang.String TAG = "ITvAdSessionWrapper";
    private final com.android.internal.os.HandlerCaller mCaller;
    private android.view.InputChannel mChannel;
    private android.media.tv.ad.ITvAdSessionWrapper.TvAdEventReceiver mReceiver;
    private android.media.tv.ad.TvAdService.Session mSessionImpl;

    public ITvAdSessionWrapper(android.content.Context context, android.media.tv.ad.TvAdService.Session session, android.view.InputChannel inputChannel) {
        this.mSessionImpl = session;
        this.mCaller = new com.android.internal.os.HandlerCaller(context, null, this, true);
        this.mChannel = inputChannel;
        if (inputChannel != null) {
            this.mReceiver = new android.media.tv.ad.ITvAdSessionWrapper.TvAdEventReceiver(inputChannel, context.getMainLooper());
        }
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void release() {
        this.mSessionImpl.scheduleMediaViewCleanup();
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(1));
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
                this.mSessionImpl.setSurface((android.view.Surface) message.obj);
                break;
            case 3:
                com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.dispatchSurfaceChanged(java.lang.Integer.valueOf(someArgs.argi1).intValue(), java.lang.Integer.valueOf(someArgs.argi2).intValue(), java.lang.Integer.valueOf(someArgs.argi3).intValue());
                someArgs.recycle();
                break;
            case 4:
                com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.createMediaView((android.os.IBinder) someArgs2.arg1, (android.graphics.Rect) someArgs2.arg2);
                someArgs2.recycle();
                break;
            case 5:
                this.mSessionImpl.relayoutMediaView((android.graphics.Rect) message.obj);
                break;
            case 6:
                this.mSessionImpl.removeMediaView(true);
                break;
            case 7:
                this.mSessionImpl.startAdService();
                break;
            case 8:
                this.mSessionImpl.stopAdService();
                break;
            case 9:
                this.mSessionImpl.resetAdService();
                break;
            case 10:
                this.mSessionImpl.sendCurrentVideoBounds((android.graphics.Rect) message.obj);
                break;
            case 11:
                this.mSessionImpl.sendCurrentChannelUri((android.net.Uri) message.obj);
                break;
            case 12:
                this.mSessionImpl.sendTrackInfoList((java.util.List) message.obj);
                break;
            case 13:
                this.mSessionImpl.sendCurrentTvInputId((java.lang.String) message.obj);
                break;
            case 14:
                com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.sendSigningResult((java.lang.String) someArgs3.arg1, (byte[]) someArgs3.arg2);
                someArgs3.recycle();
                break;
            case 15:
                com.android.internal.os.SomeArgs someArgs4 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyError((java.lang.String) someArgs4.arg1, (android.os.Bundle) someArgs4.arg2);
                someArgs4.recycle();
                break;
            case 16:
                com.android.internal.os.SomeArgs someArgs5 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyTvMessage(((java.lang.Integer) someArgs5.arg1).intValue(), (android.os.Bundle) someArgs5.arg2);
                someArgs5.recycle();
                break;
            case 17:
                com.android.internal.os.SomeArgs someArgs6 = (com.android.internal.os.SomeArgs) message.obj;
                this.mSessionImpl.notifyTvInputSessionData((java.lang.String) someArgs6.arg1, (android.os.Bundle) someArgs6.arg2);
                someArgs6.recycle();
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

    @Override // android.media.tv.ad.ITvAdSession
    public void startAdService() throws android.os.RemoteException {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(7));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void stopAdService() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(8));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void resetAdService() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(9));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void setSurface(android.view.Surface surface) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(2, surface));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void dispatchSurfaceChanged(int i, int i2, int i3) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageIIII(3, i, i2, i3, 0));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendCurrentVideoBounds(android.graphics.Rect rect) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(10, rect));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendCurrentChannelUri(android.net.Uri uri) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(11, uri));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(12, list));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendCurrentTvInputId(java.lang.String str) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(13, str));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void sendSigningResult(java.lang.String str, byte[] bArr) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(14, str, bArr));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void notifyError(java.lang.String str, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(15, str, bundle));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void notifyTvMessage(int i, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(16, java.lang.Integer.valueOf(i), bundle));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void createMediaView(android.os.IBinder iBinder, android.graphics.Rect rect) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(4, iBinder, rect));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void relayoutMediaView(android.graphics.Rect rect) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(5, rect));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void removeMediaView() {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessage(6));
    }

    @Override // android.media.tv.ad.ITvAdSession
    public void notifyTvInputSessionData(java.lang.String str, android.os.Bundle bundle) {
        this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageOO(17, str, bundle));
    }

    private final class TvAdEventReceiver extends android.view.InputEventReceiver {
        TvAdEventReceiver(android.view.InputChannel inputChannel, android.os.Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(android.view.InputEvent inputEvent) {
            if (android.media.tv.ad.ITvAdSessionWrapper.this.mSessionImpl == null) {
                finishInputEvent(inputEvent, false);
                return;
            }
            int dispatchInputEvent = android.media.tv.ad.ITvAdSessionWrapper.this.mSessionImpl.dispatchInputEvent(inputEvent, this);
            if (dispatchInputEvent != -1) {
                finishInputEvent(inputEvent, dispatchInputEvent == 1);
            }
        }
    }
}
