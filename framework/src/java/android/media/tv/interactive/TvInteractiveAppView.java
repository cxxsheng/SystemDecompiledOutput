package android.media.tv.interactive;

/* loaded from: classes2.dex */
public class TvInteractiveAppView extends android.view.ViewGroup {
    public static final java.lang.String BI_INTERACTIVE_APP_KEY_ALIAS = "alias";
    public static final java.lang.String BI_INTERACTIVE_APP_KEY_CERTIFICATE = "certificate";
    public static final java.lang.String BI_INTERACTIVE_APP_KEY_HTTP_ADDITIONAL_HEADERS = "http_additional_headers";
    public static final java.lang.String BI_INTERACTIVE_APP_KEY_HTTP_USER_AGENT = "http_user_agent";
    public static final java.lang.String BI_INTERACTIVE_APP_KEY_PRIVATE_KEY = "private_key";
    private static final boolean DEBUG = false;
    public static final java.lang.String ERROR_KEY_METHOD_NAME = "method_name";
    private static final int SET_TVVIEW_FAIL = 2;
    private static final int SET_TVVIEW_SUCCESS = 1;
    private static final java.lang.String TAG = "TvInteractiveAppView";
    private static final int UNSET_TVVIEW_FAIL = 4;
    private static final int UNSET_TVVIEW_SUCCESS = 3;
    private final android.util.AttributeSet mAttrs;
    private android.media.tv.interactive.TvInteractiveAppView.TvInteractiveAppCallback mCallback;
    private java.util.concurrent.Executor mCallbackExecutor;
    private final java.lang.Object mCallbackLock;
    private final int mDefStyleAttr;
    private final android.media.tv.interactive.TvInteractiveAppManager.Session.FinishedInputEventCallback mFinishedInputEventCallback;
    private final android.os.Handler mHandler;
    private boolean mMediaViewCreated;
    private android.graphics.Rect mMediaViewFrame;
    private android.media.tv.interactive.TvInteractiveAppView.OnUnhandledInputEventListener mOnUnhandledInputEventListener;
    private final android.content.res.XmlResourceParser mParser;
    private android.media.tv.interactive.TvInteractiveAppManager.Session mSession;
    private android.media.tv.interactive.TvInteractiveAppView.MySessionCallback mSessionCallback;
    private android.view.Surface mSurface;
    private boolean mSurfaceChanged;
    private int mSurfaceFormat;
    private int mSurfaceHeight;
    private final android.view.SurfaceHolder.Callback mSurfaceHolderCallback;
    private android.view.SurfaceView mSurfaceView;
    private int mSurfaceViewBottom;
    private int mSurfaceViewLeft;
    private int mSurfaceViewRight;
    private int mSurfaceViewTop;
    private int mSurfaceWidth;
    private final android.media.tv.interactive.TvInteractiveAppManager mTvInteractiveAppManager;
    private boolean mUseRequestedSurfaceLayout;

    public interface OnUnhandledInputEventListener {
        boolean onUnhandledInputEvent(android.view.InputEvent inputEvent);
    }

    public TvInteractiveAppView(android.content.Context context) {
        this(context, null, 0);
    }

    public TvInteractiveAppView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TvInteractiveAppView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHandler = new android.os.Handler();
        this.mCallbackLock = new java.lang.Object();
        this.mSurfaceHolderCallback = new android.view.SurfaceHolder.Callback() { // from class: android.media.tv.interactive.TvInteractiveAppView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(android.view.SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceFormat = i2;
                android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceWidth = i3;
                android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceHeight = i4;
                android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceChanged = true;
                android.media.tv.interactive.TvInteractiveAppView.this.dispatchSurfaceChanged(android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceFormat, android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceWidth, android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceHeight);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(android.view.SurfaceHolder surfaceHolder) {
                android.media.tv.interactive.TvInteractiveAppView.this.mSurface = surfaceHolder.getSurface();
                android.media.tv.interactive.TvInteractiveAppView.this.setSessionSurface(android.media.tv.interactive.TvInteractiveAppView.this.mSurface);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(android.view.SurfaceHolder surfaceHolder) {
                android.media.tv.interactive.TvInteractiveAppView.this.mSurface = null;
                android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceChanged = false;
                android.media.tv.interactive.TvInteractiveAppView.this.setSessionSurface(null);
            }
        };
        this.mFinishedInputEventCallback = new android.media.tv.interactive.TvInteractiveAppManager.Session.FinishedInputEventCallback() { // from class: android.media.tv.interactive.TvInteractiveAppView.3
            @Override // android.media.tv.interactive.TvInteractiveAppManager.Session.FinishedInputEventCallback
            public void onFinishedInputEvent(java.lang.Object obj, boolean z) {
                android.view.ViewRootImpl viewRootImpl;
                if (z) {
                    return;
                }
                android.view.InputEvent inputEvent = (android.view.InputEvent) obj;
                if (!android.media.tv.interactive.TvInteractiveAppView.this.dispatchUnhandledInputEvent(inputEvent) && (viewRootImpl = android.media.tv.interactive.TvInteractiveAppView.this.getViewRootImpl()) != null) {
                    viewRootImpl.dispatchUnhandledInputEvent(inputEvent);
                }
            }
        };
        int attributeSetSourceResId = android.content.res.Resources.getAttributeSetSourceResId(attributeSet);
        if (attributeSetSourceResId != 0) {
            android.util.Log.d(TAG, "Build local AttributeSet");
            this.mParser = context.getResources().getXml(attributeSetSourceResId);
            this.mAttrs = android.util.Xml.asAttributeSet(this.mParser);
        } else {
            android.util.Log.d(TAG, "Use passed in AttributeSet");
            this.mParser = null;
            this.mAttrs = attributeSet;
        }
        this.mDefStyleAttr = i;
        resetSurfaceView();
        this.mTvInteractiveAppManager = (android.media.tv.interactive.TvInteractiveAppManager) getContext().getSystemService(android.content.Context.TV_INTERACTIVE_APP_SERVICE);
    }

    public void setCallback(java.util.concurrent.Executor executor, android.media.tv.interactive.TvInteractiveAppView.TvInteractiveAppCallback tvInteractiveAppCallback) {
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) tvInteractiveAppCallback);
        synchronized (this.mCallbackLock) {
            this.mCallbackExecutor = executor;
            this.mCallback = tvInteractiveAppCallback;
        }
    }

    public void clearCallback() {
        synchronized (this.mCallbackLock) {
            this.mCallback = null;
            this.mCallbackExecutor = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        createSessionMediaView();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeSessionMediaView();
        super.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mUseRequestedSurfaceLayout) {
            this.mSurfaceView.layout(this.mSurfaceViewLeft, this.mSurfaceViewTop, this.mSurfaceViewRight, this.mSurfaceViewBottom);
        } else {
            this.mSurfaceView.layout(0, 0, i3 - i, i4 - i2);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        this.mSurfaceView.measure(i, i2);
        int measuredWidth = this.mSurfaceView.getMeasuredWidth();
        int measuredHeight = this.mSurfaceView.getMeasuredHeight();
        int measuredState = this.mSurfaceView.getMeasuredState();
        setMeasuredDimension(resolveSizeAndState(measuredWidth, i, measuredState), resolveSizeAndState(measuredHeight, i2, measuredState << 16));
    }

    @Override // android.view.View
    public void onVisibilityChanged(android.view.View view, int i) {
        super.onVisibilityChanged(view, i);
        this.mSurfaceView.setVisibility(i);
        if (i == 0) {
            createSessionMediaView();
        } else {
            removeSessionMediaView();
        }
    }

    private void resetSurfaceView() {
        if (this.mSurfaceView != null) {
            this.mSurfaceView.getHolder().removeCallback(this.mSurfaceHolderCallback);
            removeView(this.mSurfaceView);
        }
        this.mSurface = null;
        this.mSurfaceView = new android.view.SurfaceView(getContext(), this.mAttrs, this.mDefStyleAttr) { // from class: android.media.tv.interactive.TvInteractiveAppView.2
            @Override // android.view.SurfaceView
            protected void updateSurface() {
                super.updateSurface();
                android.media.tv.interactive.TvInteractiveAppView.this.relayoutSessionMediaView();
            }
        };
        this.mSurfaceView.setSecure(true);
        this.mSurfaceView.getHolder().addCallback(this.mSurfaceHolderCallback);
        this.mSurfaceView.getHolder().setFormat(-3);
        this.mSurfaceView.setZOrderOnTop(false);
        this.mSurfaceView.setZOrderMediaOverlay(true);
        addView(this.mSurfaceView);
    }

    public void reset() {
        resetInternal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createSessionMediaView() {
        if (this.mSession == null || !isAttachedToWindow() || this.mMediaViewCreated) {
            return;
        }
        this.mMediaViewFrame = getViewFrameOnScreen();
        this.mSession.createMediaView(this, this.mMediaViewFrame);
        this.mMediaViewCreated = true;
    }

    private void removeSessionMediaView() {
        if (this.mSession == null || !this.mMediaViewCreated) {
            return;
        }
        this.mSession.removeMediaView();
        this.mMediaViewCreated = false;
        this.mMediaViewFrame = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void relayoutSessionMediaView() {
        if (this.mSession == null || !isAttachedToWindow() || !this.mMediaViewCreated) {
            return;
        }
        android.graphics.Rect viewFrameOnScreen = getViewFrameOnScreen();
        if (viewFrameOnScreen.equals(this.mMediaViewFrame)) {
            return;
        }
        this.mSession.relayoutMediaView(viewFrameOnScreen);
        this.mMediaViewFrame = viewFrameOnScreen;
    }

    private android.graphics.Rect getViewFrameOnScreen() {
        android.graphics.Rect rect = new android.graphics.Rect();
        getGlobalVisibleRect(rect);
        android.graphics.RectF rectF = new android.graphics.RectF(rect);
        getMatrix().mapRect(rectF);
        rectF.round(rect);
        return rect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSessionSurface(android.view.Surface surface) {
        if (this.mSession == null) {
            return;
        }
        this.mSession.setSurface(surface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSurfaceChanged(int i, int i2, int i3) {
        if (this.mSession == null) {
            return;
        }
        this.mSession.dispatchSurfaceChanged(i, i2, i3);
    }

    public boolean dispatchUnhandledInputEvent(android.view.InputEvent inputEvent) {
        if (this.mOnUnhandledInputEventListener != null && this.mOnUnhandledInputEventListener.onUnhandledInputEvent(inputEvent)) {
            return true;
        }
        return onUnhandledInputEvent(inputEvent);
    }

    public boolean onUnhandledInputEvent(android.view.InputEvent inputEvent) {
        return false;
    }

    public void setOnUnhandledInputEventListener(java.util.concurrent.Executor executor, android.media.tv.interactive.TvInteractiveAppView.OnUnhandledInputEventListener onUnhandledInputEventListener) {
        this.mOnUnhandledInputEventListener = onUnhandledInputEventListener;
    }

    public android.media.tv.interactive.TvInteractiveAppView.OnUnhandledInputEventListener getOnUnhandledInputEventListener() {
        return this.mOnUnhandledInputEventListener;
    }

    public void clearOnUnhandledInputEventListener() {
        this.mOnUnhandledInputEventListener = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        if (super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        android.view.KeyEvent copy = keyEvent.copy();
        return this.mSession.dispatchInputEvent(copy, copy, this.mFinishedInputEventCallback, this.mHandler) != 0;
    }

    public void prepareInteractiveApp(java.lang.String str, int i) {
        this.mSessionCallback = new android.media.tv.interactive.TvInteractiveAppView.MySessionCallback(str, i);
        if (this.mTvInteractiveAppManager != null) {
            this.mTvInteractiveAppManager.createSession(str, i, this.mSessionCallback, this.mHandler);
        }
    }

    public void startInteractiveApp() {
        if (this.mSession != null) {
            this.mSession.startInteractiveApp();
        }
    }

    public void stopInteractiveApp() {
        if (this.mSession != null) {
            this.mSession.stopInteractiveApp();
        }
    }

    public void resetInteractiveApp() {
        if (this.mSession != null) {
            this.mSession.resetInteractiveApp();
        }
    }

    public void sendCurrentVideoBounds(android.graphics.Rect rect) {
        if (this.mSession != null) {
            this.mSession.sendCurrentVideoBounds(rect);
        }
    }

    public void sendCurrentChannelUri(android.net.Uri uri) {
        if (this.mSession != null) {
            this.mSession.sendCurrentChannelUri(uri);
        }
    }

    public void sendCurrentChannelLcn(int i) {
        if (this.mSession != null) {
            this.mSession.sendCurrentChannelLcn(i);
        }
    }

    public void sendStreamVolume(float f) {
        if (this.mSession != null) {
            this.mSession.sendStreamVolume(f);
        }
    }

    public void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) {
        if (this.mSession != null) {
            this.mSession.sendTrackInfoList(list);
        }
    }

    public void sendSelectedTrackInfo(java.util.List<android.media.tv.TvTrackInfo> list) {
        if (this.mSession != null) {
            this.mSession.sendSelectedTrackInfo(list);
        }
    }

    public void sendCurrentTvInputId(java.lang.String str) {
        if (this.mSession != null) {
            this.mSession.sendCurrentTvInputId(str);
        }
    }

    public void sendTimeShiftMode(int i) {
        if (this.mSession != null) {
            this.mSession.sendTimeShiftMode(i);
        }
    }

    public void sendAvailableSpeeds(float[] fArr) {
        if (this.mSession != null) {
            java.util.Arrays.sort(fArr);
            this.mSession.sendAvailableSpeeds(fArr);
        }
    }

    public void sendTvRecordingInfo(android.media.tv.TvRecordingInfo tvRecordingInfo) {
        if (this.mSession != null) {
            this.mSession.sendTvRecordingInfo(tvRecordingInfo);
        }
    }

    public void sendTvRecordingInfoList(java.util.List<android.media.tv.TvRecordingInfo> list) {
        if (this.mSession != null) {
            this.mSession.sendTvRecordingInfoList(list);
        }
    }

    public void notifyRecordingStarted(java.lang.String str, java.lang.String str2) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingStarted(str, str2);
        }
    }

    public void notifyRecordingStopped(java.lang.String str) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingStopped(str);
        }
    }

    public void notifyVideoFreezeUpdated(boolean z) {
        if (this.mSession != null) {
            this.mSession.notifyVideoFreezeUpdated(z);
        }
    }

    public void sendSigningResult(java.lang.String str, byte[] bArr) {
        if (this.mSession != null) {
            this.mSession.sendSigningResult(str, bArr);
        }
    }

    public void sendCertificate(java.lang.String str, int i, android.net.http.SslCertificate sslCertificate) {
        if (this.mSession != null) {
            this.mSession.sendCertificate(str, i, sslCertificate);
        }
    }

    public void notifyError(java.lang.String str, android.os.Bundle bundle) {
        if (this.mSession != null) {
            this.mSession.notifyError(str, bundle);
        }
    }

    public void notifyTimeShiftPlaybackParams(android.media.PlaybackParams playbackParams) {
        if (this.mSession != null) {
            this.mSession.notifyTimeShiftPlaybackParams(playbackParams);
        }
    }

    public void notifyTimeShiftStatusChanged(java.lang.String str, int i) {
        if (this.mSession != null) {
            this.mSession.notifyTimeShiftStatusChanged(str, i);
        }
    }

    public void notifyTimeShiftStartPositionChanged(java.lang.String str, long j) {
        if (this.mSession != null) {
            this.mSession.notifyTimeShiftStartPositionChanged(str, j);
        }
    }

    public void notifyTimeShiftCurrentPositionChanged(java.lang.String str, long j) {
        if (this.mSession != null) {
            this.mSession.notifyTimeShiftCurrentPositionChanged(str, j);
        }
    }

    public void notifyRecordingConnectionFailed(java.lang.String str, java.lang.String str2) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingConnectionFailed(str, str2);
        }
    }

    public void notifyRecordingDisconnected(java.lang.String str, java.lang.String str2) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingDisconnected(str, str2);
        }
    }

    public void notifyRecordingTuned(java.lang.String str, android.net.Uri uri) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingTuned(str, uri);
        }
    }

    public void notifyRecordingError(java.lang.String str, int i) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingError(str, i);
        }
    }

    public void notifyRecordingScheduled(java.lang.String str, java.lang.String str2) {
        if (this.mSession != null) {
            this.mSession.notifyRecordingScheduled(str, str2);
        }
    }

    public void notifyTvMessage(int i, android.os.Bundle bundle) {
        if (this.mSession != null) {
            this.mSession.notifyTvMessage(i, bundle);
        }
    }

    private void resetInternal() {
        this.mSessionCallback = null;
        if (this.mSession != null) {
            setSessionSurface(null);
            removeSessionMediaView();
            this.mUseRequestedSurfaceLayout = false;
            this.mSession.release();
            this.mSession = null;
            resetSurfaceView();
        }
    }

    public void createBiInteractiveApp(android.net.Uri uri, android.os.Bundle bundle) {
        if (this.mSession != null) {
            this.mSession.createBiInteractiveApp(uri, bundle);
        }
    }

    public void destroyBiInteractiveApp(java.lang.String str) {
        if (this.mSession != null) {
            this.mSession.destroyBiInteractiveApp(str);
        }
    }

    public android.media.tv.interactive.TvInteractiveAppManager.Session getInteractiveAppSession() {
        return this.mSession;
    }

    public int setTvView(android.media.tv.TvView tvView) {
        if (tvView == null) {
            return unsetTvView();
        }
        android.media.tv.TvInputManager.Session inputSession = tvView.getInputSession();
        if (inputSession == null || this.mSession == null) {
            return 2;
        }
        this.mSession.setInputSession(inputSession);
        inputSession.setInteractiveAppSession(this.mSession);
        return 1;
    }

    private int unsetTvView() {
        if (this.mSession == null || this.mSession.getInputSession() == null) {
            return 4;
        }
        this.mSession.getInputSession().setInteractiveAppSession(null);
        this.mSession.setInputSession(null);
        return 3;
    }

    public void setTeletextAppEnabled(boolean z) {
        if (this.mSession != null) {
            this.mSession.setTeletextAppEnabled(z);
        }
    }

    public static abstract class TvInteractiveAppCallback {
        public void onPlaybackCommandRequest(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        }

        public void onTimeShiftCommandRequest(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        }

        public void onStateChanged(java.lang.String str, int i, int i2) {
        }

        public void onBiInteractiveAppCreated(java.lang.String str, android.net.Uri uri, java.lang.String str2) {
        }

        public void onTeletextAppStateChanged(java.lang.String str, int i) {
        }

        public void onSetVideoBounds(java.lang.String str, android.graphics.Rect rect) {
        }

        public void onRequestCurrentVideoBounds(java.lang.String str) {
        }

        public void onRequestCurrentChannelUri(java.lang.String str) {
        }

        public void onRequestCurrentChannelLcn(java.lang.String str) {
        }

        public void onRequestStreamVolume(java.lang.String str) {
        }

        public void onRequestTrackInfoList(java.lang.String str) {
        }

        public void onRequestSelectedTrackInfo(java.lang.String str) {
        }

        public void onRequestCurrentTvInputId(java.lang.String str) {
        }

        public void onRequestTimeShiftMode(java.lang.String str) {
        }

        public void onRequestAvailableSpeeds(java.lang.String str) {
        }

        public void onRequestStartRecording(java.lang.String str, java.lang.String str2, android.net.Uri uri) {
        }

        public void onRequestStopRecording(java.lang.String str, java.lang.String str2) {
        }

        public void onRequestScheduleRecording(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle) {
        }

        public void onRequestScheduleRecording(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle) {
        }

        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, byte[] bArr) {
        }

        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i, byte[] bArr) {
        }

        public void onRequestCertificate(java.lang.String str, java.lang.String str2, int i) {
        }

        public void onSetTvRecordingInfo(java.lang.String str, java.lang.String str2, android.media.tv.TvRecordingInfo tvRecordingInfo) {
        }

        public void onRequestTvRecordingInfo(java.lang.String str, java.lang.String str2) {
        }

        public void onRequestTvRecordingInfoList(java.lang.String str, int i) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MySessionCallback extends android.media.tv.interactive.TvInteractiveAppManager.SessionCallback {
        final java.lang.String mIAppServiceId;
        int mType;

        MySessionCallback(java.lang.String str, int i) {
            this.mIAppServiceId = str;
            this.mType = i;
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSessionCreated(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onSessionCreated - session already created");
                if (session != null) {
                    session.release();
                    return;
                }
                return;
            }
            android.media.tv.interactive.TvInteractiveAppView.this.mSession = session;
            if (session != null) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mSurface != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.setSessionSurface(android.media.tv.interactive.TvInteractiveAppView.this.mSurface);
                    if (android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceChanged) {
                        android.media.tv.interactive.TvInteractiveAppView.this.dispatchSurfaceChanged(android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceFormat, android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceWidth, android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceHeight);
                    }
                }
                android.media.tv.interactive.TvInteractiveAppView.this.createSessionMediaView();
                return;
            }
            android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback = null;
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSessionReleased(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onSessionReleased - session not created");
                return;
            }
            android.media.tv.interactive.TvInteractiveAppView.this.mMediaViewCreated = false;
            android.media.tv.interactive.TvInteractiveAppView.this.mMediaViewFrame = null;
            android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback = null;
            android.media.tv.interactive.TvInteractiveAppView.this.mSession = null;
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onLayoutSurface(android.media.tv.interactive.TvInteractiveAppManager.Session session, int i, int i2, int i3, int i4) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onLayoutSurface - session not created");
                return;
            }
            android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceViewLeft = i;
            android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceViewTop = i2;
            android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceViewRight = i3;
            android.media.tv.interactive.TvInteractiveAppView.this.mSurfaceViewBottom = i4;
            android.media.tv.interactive.TvInteractiveAppView.this.mUseRequestedSurfaceLayout = true;
            android.media.tv.interactive.TvInteractiveAppView.this.requestLayout();
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onCommandRequest(android.media.tv.interactive.TvInteractiveAppManager.Session session, final java.lang.String str, final android.os.Bundle bundle) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onCommandRequest - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onCommandRequest$0(str, bundle);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCommandRequest$0(java.lang.String str, android.os.Bundle bundle) {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onPlaybackCommandRequest(this.mIAppServiceId, str, bundle);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onTimeShiftCommandRequest(android.media.tv.interactive.TvInteractiveAppManager.Session session, final java.lang.String str, final android.os.Bundle bundle) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onTimeShiftCommandRequest - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onTimeShiftCommandRequest$1(str, bundle);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTimeShiftCommandRequest$1(java.lang.String str, android.os.Bundle bundle) {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onTimeShiftCommandRequest(this.mIAppServiceId, str, bundle);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSessionStateChanged(android.media.tv.interactive.TvInteractiveAppManager.Session session, final int i, final int i2) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onSessionStateChanged - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onSessionStateChanged$2(i, i2);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSessionStateChanged$2(int i, int i2) {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onStateChanged(this.mIAppServiceId, i, i2);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onBiInteractiveAppCreated(android.media.tv.interactive.TvInteractiveAppManager.Session session, final android.net.Uri uri, final java.lang.String str) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onBiInteractiveAppCreated - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onBiInteractiveAppCreated$3(uri, str);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBiInteractiveAppCreated$3(android.net.Uri uri, java.lang.String str) {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onBiInteractiveAppCreated(this.mIAppServiceId, uri, str);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onTeletextAppStateChanged(android.media.tv.interactive.TvInteractiveAppManager.Session session, int i) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onTeletextAppStateChanged - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onTeletextAppStateChanged(this.mIAppServiceId, i);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSetVideoBounds(android.media.tv.interactive.TvInteractiveAppManager.Session session, final android.graphics.Rect rect) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onSetVideoBounds - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onSetVideoBounds$4(rect);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSetVideoBounds$4(android.graphics.Rect rect) {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onSetVideoBounds(this.mIAppServiceId, rect);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentVideoBounds(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestCurrentVideoBounds - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onRequestCurrentVideoBounds$5();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentVideoBounds$5() {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestCurrentVideoBounds(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentChannelUri(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestCurrentChannelUri - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onRequestCurrentChannelUri$6();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentChannelUri$6() {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestCurrentChannelUri(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentChannelLcn(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestCurrentChannelLcn - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onRequestCurrentChannelLcn$7();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentChannelLcn$7() {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestCurrentChannelLcn(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestStreamVolume(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestStreamVolume - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onRequestStreamVolume$8();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestStreamVolume$8() {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestStreamVolume(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTrackInfoList(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestTrackInfoList - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onRequestTrackInfoList$9();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestTrackInfoList$9() {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestTrackInfoList(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestSelectedTrackInfo(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestSelectedTrackInfo - session not created");
                return;
            }
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.interactive.TvInteractiveAppView.MySessionCallback.this.lambda$onRequestSelectedTrackInfo$10();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestSelectedTrackInfo$10() {
            synchronized (android.media.tv.interactive.TvInteractiveAppView.this.mCallbackLock) {
                if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestSelectedTrackInfo(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentTvInputId(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestCurrentTvInputId - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestCurrentTvInputId(this.mIAppServiceId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTimeShiftMode(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestTimeShiftMode - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestTimeShiftMode(this.mIAppServiceId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestAvailableSpeeds(android.media.tv.interactive.TvInteractiveAppManager.Session session) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestAvailableSpeeds - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestAvailableSpeeds(this.mIAppServiceId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestStartRecording(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, android.net.Uri uri) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestStartRecording - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestStartRecording(this.mIAppServiceId, str, uri);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestStopRecording(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestStopRecording - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestStopRecording(this.mIAppServiceId, str);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSetTvRecordingInfo(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onSetRecordingInfo - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onSetTvRecordingInfo(this.mIAppServiceId, str, tvRecordingInfo);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestScheduleRecording(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestScheduleRecording - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestScheduleRecording(this.mIAppServiceId, str, str2, uri, uri2, bundle);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestScheduleRecording(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestScheduleRecording - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestScheduleRecording(this.mIAppServiceId, str, str2, uri, j, j2, i, bundle);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTvRecordingInfo(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestRecordingInfo - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestTvRecordingInfo(this.mIAppServiceId, str);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTvRecordingInfoList(android.media.tv.interactive.TvInteractiveAppManager.Session session, int i) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestRecordingInfoList - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestTvRecordingInfoList(this.mIAppServiceId, i);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestSigning(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestSigning - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestSigning(this.mIAppServiceId, str, str2, str3, bArr);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestSigning(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, byte[] bArr) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestSigning - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null && android.media.tv.flags.Flags.tiafVApis()) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestSigning(this.mIAppServiceId, str, str2, str3, i, bArr);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCertificate(android.media.tv.interactive.TvInteractiveAppManager.Session session, java.lang.String str, int i) {
            if (this != android.media.tv.interactive.TvInteractiveAppView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppView.TAG, "onRequestCertificate - session not created");
            } else if (android.media.tv.interactive.TvInteractiveAppView.this.mCallback != null && android.media.tv.flags.Flags.tiafVApis()) {
                android.media.tv.interactive.TvInteractiveAppView.this.mCallback.onRequestCertificate(this.mIAppServiceId, str, i);
            }
        }
    }
}
