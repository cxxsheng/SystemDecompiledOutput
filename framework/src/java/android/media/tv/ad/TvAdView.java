package android.media.tv.ad;

/* loaded from: classes2.dex */
public class TvAdView extends android.view.ViewGroup {
    private static final boolean DEBUG = false;
    public static final java.lang.String ERROR_KEY_ERROR_CODE = "error_code";
    public static final java.lang.String ERROR_KEY_METHOD_NAME = "method_name";
    private static final java.lang.String TAG = "TvAdView";
    private final android.util.AttributeSet mAttrs;
    private android.media.tv.ad.TvAdView.TvAdCallback mCallback;
    private java.util.concurrent.Executor mCallbackExecutor;
    private final java.lang.Object mCallbackLock;
    private final int mDefStyleAttr;
    private final android.media.tv.ad.TvAdManager.Session.FinishedInputEventCallback mFinishedInputEventCallback;
    private final android.os.Handler mHandler;
    private boolean mMediaViewCreated;
    private android.graphics.Rect mMediaViewFrame;
    private android.media.tv.ad.TvAdView.OnUnhandledInputEventListener mOnUnhandledInputEventListener;
    private final android.content.res.XmlResourceParser mParser;
    private android.media.tv.ad.TvAdManager.Session mSession;
    private android.media.tv.ad.TvAdView.MySessionCallback mSessionCallback;
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
    private final android.media.tv.ad.TvAdManager mTvAdManager;
    private boolean mUseRequestedSurfaceLayout;

    public interface OnUnhandledInputEventListener {
        boolean onUnhandledInputEvent(android.view.InputEvent inputEvent);
    }

    public TvAdView(android.content.Context context) {
        this(context, null, 0);
    }

    public TvAdView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TvAdView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHandler = new android.os.Handler();
        this.mCallbackLock = new java.lang.Object();
        this.mSurfaceHolderCallback = new android.view.SurfaceHolder.Callback() { // from class: android.media.tv.ad.TvAdView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(android.view.SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                android.media.tv.ad.TvAdView.this.mSurfaceFormat = i2;
                android.media.tv.ad.TvAdView.this.mSurfaceWidth = i3;
                android.media.tv.ad.TvAdView.this.mSurfaceHeight = i4;
                android.media.tv.ad.TvAdView.this.mSurfaceChanged = true;
                android.media.tv.ad.TvAdView.this.dispatchSurfaceChanged(android.media.tv.ad.TvAdView.this.mSurfaceFormat, android.media.tv.ad.TvAdView.this.mSurfaceWidth, android.media.tv.ad.TvAdView.this.mSurfaceHeight);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(android.view.SurfaceHolder surfaceHolder) {
                android.media.tv.ad.TvAdView.this.mSurface = surfaceHolder.getSurface();
                android.media.tv.ad.TvAdView.this.setSessionSurface(android.media.tv.ad.TvAdView.this.mSurface);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(android.view.SurfaceHolder surfaceHolder) {
                android.media.tv.ad.TvAdView.this.mSurface = null;
                android.media.tv.ad.TvAdView.this.mSurfaceChanged = false;
                android.media.tv.ad.TvAdView.this.setSessionSurface(null);
            }
        };
        this.mFinishedInputEventCallback = new android.media.tv.ad.TvAdManager.Session.FinishedInputEventCallback() { // from class: android.media.tv.ad.TvAdView.3
            @Override // android.media.tv.ad.TvAdManager.Session.FinishedInputEventCallback
            public void onFinishedInputEvent(java.lang.Object obj, boolean z) {
                android.view.ViewRootImpl viewRootImpl;
                if (z) {
                    return;
                }
                android.view.InputEvent inputEvent = (android.view.InputEvent) obj;
                if (!android.media.tv.ad.TvAdView.this.dispatchUnhandledInputEvent(inputEvent) && (viewRootImpl = android.media.tv.ad.TvAdView.this.getViewRootImpl()) != null) {
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
        this.mTvAdManager = (android.media.tv.ad.TvAdManager) getContext().getSystemService(android.content.Context.TV_AD_SERVICE);
    }

    public boolean setTvView(android.media.tv.TvView tvView) {
        if (tvView == null) {
            return unsetTvView();
        }
        android.media.tv.TvInputManager.Session inputSession = tvView.getInputSession();
        if (inputSession == null || this.mSession == null) {
            return false;
        }
        this.mSession.setInputSession(inputSession);
        inputSession.setAdSession(this.mSession);
        return true;
    }

    private boolean unsetTvView() {
        if (this.mSession == null || this.mSession.getInputSession() == null) {
            return false;
        }
        this.mSession.getInputSession().setAdSession(null);
        this.mSession.setInputSession(null);
        return true;
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
        this.mSurfaceView = new android.view.SurfaceView(getContext(), this.mAttrs, this.mDefStyleAttr) { // from class: android.media.tv.ad.TvAdView.2
            @Override // android.view.SurfaceView
            protected void updateSurface() {
                super.updateSurface();
                android.media.tv.ad.TvAdView.this.relayoutSessionMediaView();
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

    public void setOnUnhandledInputEventListener(java.util.concurrent.Executor executor, android.media.tv.ad.TvAdView.OnUnhandledInputEventListener onUnhandledInputEventListener) {
        this.mOnUnhandledInputEventListener = onUnhandledInputEventListener;
    }

    public android.media.tv.ad.TvAdView.OnUnhandledInputEventListener getOnUnhandledInputEventListener() {
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

    public void prepareAdService(java.lang.String str, java.lang.String str2) {
        this.mSessionCallback = new android.media.tv.ad.TvAdView.MySessionCallback(str);
        if (this.mTvAdManager != null) {
            this.mTvAdManager.createSession(str, str2, this.mSessionCallback, this.mHandler);
        }
    }

    public void startAdService() {
        if (this.mSession != null) {
            this.mSession.startAdService();
        }
    }

    public void stopAdService() {
        if (this.mSession != null) {
            this.mSession.stopAdService();
        }
    }

    public void resetAdService() {
        if (this.mSession != null) {
            this.mSession.resetAdService();
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

    public void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) {
        if (this.mSession != null) {
            this.mSession.sendTrackInfoList(list);
        }
    }

    public void sendCurrentTvInputId(java.lang.String str) {
        if (this.mSession != null) {
            this.mSession.sendCurrentTvInputId(str);
        }
    }

    public void sendSigningResult(java.lang.String str, byte[] bArr) {
        if (this.mSession != null) {
            this.mSession.sendSigningResult(str, bArr);
        }
    }

    public void notifyError(java.lang.String str, android.os.Bundle bundle) {
        if (this.mSession != null) {
            this.mSession.notifyError(str, bundle);
        }
    }

    public void notifyTvMessage(int i, android.os.Bundle bundle) {
        if (this.mSession != null) {
            this.mSession.notifyTvMessage(i, bundle);
        }
    }

    public void setCallback(java.util.concurrent.Executor executor, android.media.tv.ad.TvAdView.TvAdCallback tvAdCallback) {
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) tvAdCallback);
        synchronized (this.mCallbackLock) {
            this.mCallbackExecutor = executor;
            this.mCallback = tvAdCallback;
        }
    }

    public void clearCallback() {
        synchronized (this.mCallbackLock) {
            this.mCallback = null;
            this.mCallbackExecutor = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MySessionCallback extends android.media.tv.ad.TvAdManager.SessionCallback {
        final java.lang.String mServiceId;

        MySessionCallback(java.lang.String str) {
            this.mServiceId = str;
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onSessionCreated(android.media.tv.ad.TvAdManager.Session session) {
            if (this != android.media.tv.ad.TvAdView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.ad.TvAdView.TAG, "onSessionCreated - session already created");
                if (session != null) {
                    session.release();
                    return;
                }
                return;
            }
            android.media.tv.ad.TvAdView.this.mSession = session;
            if (session != null) {
                if (android.media.tv.ad.TvAdView.this.mSurface != null) {
                    android.media.tv.ad.TvAdView.this.setSessionSurface(android.media.tv.ad.TvAdView.this.mSurface);
                    if (android.media.tv.ad.TvAdView.this.mSurfaceChanged) {
                        android.media.tv.ad.TvAdView.this.dispatchSurfaceChanged(android.media.tv.ad.TvAdView.this.mSurfaceFormat, android.media.tv.ad.TvAdView.this.mSurfaceWidth, android.media.tv.ad.TvAdView.this.mSurfaceHeight);
                    }
                }
                android.media.tv.ad.TvAdView.this.createSessionMediaView();
                return;
            }
            android.media.tv.ad.TvAdView.this.mSessionCallback = null;
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onSessionReleased(android.media.tv.ad.TvAdManager.Session session) {
            if (this != android.media.tv.ad.TvAdView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.ad.TvAdView.TAG, "onSessionReleased - session not created");
                return;
            }
            android.media.tv.ad.TvAdView.this.mMediaViewCreated = false;
            android.media.tv.ad.TvAdView.this.mMediaViewFrame = null;
            android.media.tv.ad.TvAdView.this.mSessionCallback = null;
            android.media.tv.ad.TvAdView.this.mSession = null;
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onLayoutSurface(android.media.tv.ad.TvAdManager.Session session, int i, int i2, int i3, int i4) {
            if (this != android.media.tv.ad.TvAdView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.ad.TvAdView.TAG, "onLayoutSurface - session not created");
                return;
            }
            android.media.tv.ad.TvAdView.this.mSurfaceViewLeft = i;
            android.media.tv.ad.TvAdView.this.mSurfaceViewTop = i2;
            android.media.tv.ad.TvAdView.this.mSurfaceViewRight = i3;
            android.media.tv.ad.TvAdView.this.mSurfaceViewBottom = i4;
            android.media.tv.ad.TvAdView.this.mUseRequestedSurfaceLayout = true;
            android.media.tv.ad.TvAdView.this.requestLayout();
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onRequestCurrentVideoBounds(android.media.tv.ad.TvAdManager.Session session) {
            if (this != android.media.tv.ad.TvAdView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.ad.TvAdView.TAG, "onRequestCurrentVideoBounds - session not created");
                return;
            }
            synchronized (android.media.tv.ad.TvAdView.this.mCallbackLock) {
                if (android.media.tv.ad.TvAdView.this.mCallbackExecutor != null) {
                    android.media.tv.ad.TvAdView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.ad.TvAdView.MySessionCallback.this.lambda$onRequestCurrentVideoBounds$0();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentVideoBounds$0() {
            synchronized (android.media.tv.ad.TvAdView.this.mCallbackLock) {
                if (android.media.tv.ad.TvAdView.this.mCallback != null) {
                    android.media.tv.ad.TvAdView.this.mCallback.onRequestCurrentVideoBounds(this.mServiceId);
                }
            }
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onRequestCurrentChannelUri(android.media.tv.ad.TvAdManager.Session session) {
            if (this != android.media.tv.ad.TvAdView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.ad.TvAdView.TAG, "onRequestCurrentChannelUri - session not created");
                return;
            }
            synchronized (android.media.tv.ad.TvAdView.this.mCallbackLock) {
                if (android.media.tv.ad.TvAdView.this.mCallbackExecutor != null) {
                    android.media.tv.ad.TvAdView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.ad.TvAdView.MySessionCallback.this.lambda$onRequestCurrentChannelUri$1();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentChannelUri$1() {
            synchronized (android.media.tv.ad.TvAdView.this.mCallbackLock) {
                if (android.media.tv.ad.TvAdView.this.mCallback != null) {
                    android.media.tv.ad.TvAdView.this.mCallback.onRequestCurrentChannelUri(this.mServiceId);
                }
            }
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onRequestTrackInfoList(android.media.tv.ad.TvAdManager.Session session) {
            if (this != android.media.tv.ad.TvAdView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.ad.TvAdView.TAG, "onRequestTrackInfoList - session not created");
                return;
            }
            synchronized (android.media.tv.ad.TvAdView.this.mCallbackLock) {
                if (android.media.tv.ad.TvAdView.this.mCallbackExecutor != null) {
                    android.media.tv.ad.TvAdView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.ad.TvAdView.MySessionCallback.this.lambda$onRequestTrackInfoList$2();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestTrackInfoList$2() {
            synchronized (android.media.tv.ad.TvAdView.this.mCallbackLock) {
                if (android.media.tv.ad.TvAdView.this.mCallback != null) {
                    android.media.tv.ad.TvAdView.this.mCallback.onRequestTrackInfoList(this.mServiceId);
                }
            }
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onRequestCurrentTvInputId(android.media.tv.ad.TvAdManager.Session session) {
            if (this != android.media.tv.ad.TvAdView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.ad.TvAdView.TAG, "onRequestCurrentTvInputId - session not created");
                return;
            }
            synchronized (android.media.tv.ad.TvAdView.this.mCallbackLock) {
                if (android.media.tv.ad.TvAdView.this.mCallbackExecutor != null) {
                    android.media.tv.ad.TvAdView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.ad.TvAdView.MySessionCallback.this.lambda$onRequestCurrentTvInputId$3();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentTvInputId$3() {
            synchronized (android.media.tv.ad.TvAdView.this.mCallbackLock) {
                if (android.media.tv.ad.TvAdView.this.mCallback != null) {
                    android.media.tv.ad.TvAdView.this.mCallback.onRequestCurrentTvInputId(this.mServiceId);
                }
            }
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onRequestSigning(android.media.tv.ad.TvAdManager.Session session, final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final byte[] bArr) {
            if (this != android.media.tv.ad.TvAdView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.ad.TvAdView.TAG, "onRequestSigning - session not created");
                return;
            }
            synchronized (android.media.tv.ad.TvAdView.this.mCallbackLock) {
                if (android.media.tv.ad.TvAdView.this.mCallbackExecutor != null) {
                    android.media.tv.ad.TvAdView.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.tv.ad.TvAdView.MySessionCallback.this.lambda$onRequestSigning$4(str, str2, str3, bArr);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestSigning$4(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) {
            synchronized (android.media.tv.ad.TvAdView.this.mCallbackLock) {
                if (android.media.tv.ad.TvAdView.this.mCallback != null) {
                    android.media.tv.ad.TvAdView.this.mCallback.onRequestSigning(this.mServiceId, str, str2, str3, bArr);
                }
            }
        }
    }

    public static abstract class TvAdCallback {
        public void onRequestCurrentVideoBounds(java.lang.String str) {
        }

        public void onRequestCurrentChannelUri(java.lang.String str) {
        }

        public void onRequestTrackInfoList(java.lang.String str) {
        }

        public void onRequestCurrentTvInputId(java.lang.String str) {
        }

        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, byte[] bArr) {
        }

        public void onStateChanged(java.lang.String str, int i, int i2) {
        }
    }
}
