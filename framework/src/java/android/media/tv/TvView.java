package android.media.tv;

/* loaded from: classes2.dex */
public class TvView extends android.view.ViewGroup {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "TvView";
    private static final int ZORDER_MEDIA = 0;
    private static final int ZORDER_MEDIA_OVERLAY = 1;
    private static final int ZORDER_ON_TOP = 2;
    private final android.util.AttributeSet mAttrs;
    private android.media.tv.TvView.TvInputCallback mCallback;
    private java.lang.Boolean mCaptionEnabled;
    private final int mDefStyleAttr;
    private final android.media.tv.TvInputManager.Session.FinishedInputEventCallback mFinishedInputEventCallback;
    private final android.os.Handler mHandler;
    private android.media.tv.TvView.OnUnhandledInputEventListener mOnUnhandledInputEventListener;
    private boolean mOverlayViewCreated;
    private android.graphics.Rect mOverlayViewFrame;
    private final android.content.res.XmlResourceParser mParser;
    private final java.util.Queue<android.util.Pair<java.lang.String, android.os.Bundle>> mPendingAppPrivateCommands;
    private android.media.tv.TvInputManager.Session mSession;
    private android.media.tv.TvView.MySessionCallback mSessionCallback;
    private java.lang.Float mStreamVolume;
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
    private android.media.tv.TvView.TimeShiftPositionCallback mTimeShiftPositionCallback;
    private android.content.AttributionSource mTvAppAttributionSource;
    private final android.media.tv.TvInputManager mTvInputManager;
    private boolean mUseRequestedSurfaceLayout;
    private int mWindowZOrder;
    private static final java.lang.ref.WeakReference<android.media.tv.TvView> NULL_TV_VIEW = new java.lang.ref.WeakReference<>(null);
    private static final java.lang.Object sMainTvViewLock = new java.lang.Object();
    private static java.lang.ref.WeakReference<android.media.tv.TvView> sMainTvView = NULL_TV_VIEW;

    public interface OnUnhandledInputEventListener {
        boolean onUnhandledInputEvent(android.view.InputEvent inputEvent);
    }

    public TvView(android.content.Context context) {
        this(context, null, 0);
    }

    public TvView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TvView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHandler = new android.os.Handler();
        this.mPendingAppPrivateCommands = new java.util.ArrayDeque();
        this.mSurfaceHolderCallback = new android.view.SurfaceHolder.Callback() { // from class: android.media.tv.TvView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(android.view.SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                android.media.tv.TvView.this.mSurfaceFormat = i2;
                android.media.tv.TvView.this.mSurfaceWidth = i3;
                android.media.tv.TvView.this.mSurfaceHeight = i4;
                android.media.tv.TvView.this.mSurfaceChanged = true;
                android.media.tv.TvView.this.dispatchSurfaceChanged(android.media.tv.TvView.this.mSurfaceFormat, android.media.tv.TvView.this.mSurfaceWidth, android.media.tv.TvView.this.mSurfaceHeight);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(android.view.SurfaceHolder surfaceHolder) {
                android.media.tv.TvView.this.mSurface = surfaceHolder.getSurface();
                android.media.tv.TvView.this.setSessionSurface(android.media.tv.TvView.this.mSurface);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(android.view.SurfaceHolder surfaceHolder) {
                android.media.tv.TvView.this.mSurface = null;
                android.media.tv.TvView.this.mSurfaceChanged = false;
                android.media.tv.TvView.this.setSessionSurface(null);
            }
        };
        this.mFinishedInputEventCallback = new android.media.tv.TvInputManager.Session.FinishedInputEventCallback() { // from class: android.media.tv.TvView.2
            @Override // android.media.tv.TvInputManager.Session.FinishedInputEventCallback
            public void onFinishedInputEvent(java.lang.Object obj, boolean z) {
                android.view.ViewRootImpl viewRootImpl;
                if (z) {
                    return;
                }
                android.view.InputEvent inputEvent = (android.view.InputEvent) obj;
                if (!android.media.tv.TvView.this.dispatchUnhandledInputEvent(inputEvent) && (viewRootImpl = android.media.tv.TvView.this.getViewRootImpl()) != null) {
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
        this.mTvInputManager = (android.media.tv.TvInputManager) getContext().getSystemService(android.content.Context.TV_INPUT_SERVICE);
        this.mTvAppAttributionSource = getContext().getAttributionSource();
    }

    public void setCallback(android.media.tv.TvView.TvInputCallback tvInputCallback) {
        this.mCallback = tvInputCallback;
    }

    public android.media.tv.TvInputManager.Session getInputSession() {
        return this.mSession;
    }

    @android.annotation.SystemApi
    public void setMain() {
        synchronized (sMainTvViewLock) {
            sMainTvView = new java.lang.ref.WeakReference<>(this);
            if (hasWindowFocus() && this.mSession != null) {
                this.mSession.setMain();
            }
        }
    }

    public void setZOrderMediaOverlay(boolean z) {
        if (z) {
            this.mWindowZOrder = 1;
            removeSessionOverlayView();
        } else {
            this.mWindowZOrder = 0;
            createSessionOverlayView();
        }
        if (this.mSurfaceView != null) {
            this.mSurfaceView.setZOrderOnTop(false);
            this.mSurfaceView.setZOrderMediaOverlay(z);
        }
    }

    public void setZOrderOnTop(boolean z) {
        if (z) {
            this.mWindowZOrder = 2;
            removeSessionOverlayView();
        } else {
            this.mWindowZOrder = 0;
            createSessionOverlayView();
        }
        if (this.mSurfaceView != null) {
            this.mSurfaceView.setZOrderMediaOverlay(false);
            this.mSurfaceView.setZOrderOnTop(z);
        }
    }

    public void setStreamVolume(float f) {
        this.mStreamVolume = java.lang.Float.valueOf(f);
        if (this.mSession == null) {
            return;
        }
        this.mSession.setStreamVolume(f);
    }

    public void overrideTvAppAttributionSource(android.content.AttributionSource attributionSource) {
        if (attributionSource != null) {
            this.mTvAppAttributionSource = attributionSource;
        }
    }

    public void tune(java.lang.String str, android.net.Uri uri) {
        tune(str, uri, null);
    }

    public void tune(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("inputId cannot be null or an empty string");
        }
        synchronized (sMainTvViewLock) {
            if (sMainTvView.get() == null) {
                sMainTvView = new java.lang.ref.WeakReference<>(this);
            }
        }
        if (this.mSessionCallback != null && android.text.TextUtils.equals(this.mSessionCallback.mInputId, str)) {
            if (this.mSession != null) {
                this.mSession.tune(uri, bundle);
                return;
            } else {
                this.mSessionCallback.mChannelUri = uri;
                this.mSessionCallback.mTuneParams = bundle;
                return;
            }
        }
        resetInternal();
        this.mSessionCallback = new android.media.tv.TvView.MySessionCallback(str, uri, bundle);
        if (this.mTvInputManager != null) {
            this.mTvInputManager.createSession(str, this.mTvAppAttributionSource, this.mSessionCallback, this.mHandler);
        }
    }

    public void reset() {
        synchronized (sMainTvViewLock) {
            if (this == sMainTvView.get()) {
                sMainTvView = NULL_TV_VIEW;
            }
        }
        resetInternal();
    }

    private void resetInternal() {
        this.mSessionCallback = null;
        synchronized (this.mPendingAppPrivateCommands) {
            this.mPendingAppPrivateCommands.clear();
        }
        if (this.mSession != null) {
            setSessionSurface(null);
            removeSessionOverlayView();
            this.mUseRequestedSurfaceLayout = false;
            this.mSession.release();
            this.mSession = null;
            resetSurfaceView();
        }
    }

    public void requestUnblockContent(android.media.tv.TvContentRating tvContentRating) {
        unblockContent(tvContentRating);
    }

    @android.annotation.SystemApi
    public void unblockContent(android.media.tv.TvContentRating tvContentRating) {
        if (this.mSession != null) {
            this.mSession.unblockContent(tvContentRating);
        }
    }

    public void setCaptionEnabled(boolean z) {
        this.mCaptionEnabled = java.lang.Boolean.valueOf(z);
        if (this.mSession != null) {
            this.mSession.setCaptionEnabled(z);
        }
    }

    public void selectAudioPresentation(int i, int i2) {
        if (this.mSession != null) {
            this.mSession.selectAudioPresentation(i, i2);
        }
    }

    public java.util.List<android.media.AudioPresentation> getAudioPresentations() {
        if (this.mSession == null) {
            return new java.util.ArrayList();
        }
        return this.mSession.getAudioPresentations();
    }

    public void selectTrack(int i, java.lang.String str) {
        if (this.mSession != null) {
            this.mSession.selectTrack(i, str);
        }
    }

    public java.util.List<android.media.tv.TvTrackInfo> getTracks(int i) {
        if (this.mSession == null) {
            return null;
        }
        return this.mSession.getTracks(i);
    }

    public java.lang.String getSelectedTrack(int i) {
        if (this.mSession == null) {
            return null;
        }
        return this.mSession.getSelectedTrack(i);
    }

    public void setInteractiveAppNotificationEnabled(boolean z) {
        if (this.mSession != null) {
            this.mSession.setInteractiveAppNotificationEnabled(z);
        }
    }

    public void timeShiftPlay(java.lang.String str, android.net.Uri uri) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("inputId cannot be null or an empty string");
        }
        synchronized (sMainTvViewLock) {
            if (sMainTvView.get() == null) {
                sMainTvView = new java.lang.ref.WeakReference<>(this);
            }
        }
        if (this.mSessionCallback != null && android.text.TextUtils.equals(this.mSessionCallback.mInputId, str)) {
            if (this.mSession != null) {
                this.mSession.timeShiftPlay(uri);
                return;
            } else {
                this.mSessionCallback.mRecordedProgramUri = uri;
                return;
            }
        }
        resetInternal();
        this.mSessionCallback = new android.media.tv.TvView.MySessionCallback(str, uri);
        if (this.mTvInputManager != null) {
            this.mTvInputManager.createSession(str, this.mTvAppAttributionSource, this.mSessionCallback, this.mHandler);
        }
    }

    public void timeShiftPause() {
        if (this.mSession != null) {
            this.mSession.timeShiftPause();
        }
    }

    public void timeShiftResume() {
        if (this.mSession != null) {
            this.mSession.timeShiftResume();
        }
    }

    public void timeShiftSeekTo(long j) {
        if (this.mSession != null) {
            this.mSession.timeShiftSeekTo(j);
        }
    }

    public void timeShiftSetPlaybackParams(android.media.PlaybackParams playbackParams) {
        if (this.mSession != null) {
            this.mSession.timeShiftSetPlaybackParams(playbackParams);
        }
    }

    public void timeShiftSetMode(int i) {
        if (this.mSession != null) {
            this.mSession.timeShiftSetMode(i);
        }
    }

    public void stopPlayback(int i) {
        if (this.mSession != null) {
            this.mSession.stopPlayback(i);
        }
    }

    public void resumePlayback() {
        if (this.mSession != null) {
            this.mSession.resumePlayback();
        }
    }

    public void setVideoFrozen(boolean z) {
        if (this.mSession != null) {
            this.mSession.setVideoFrozen(z);
        }
    }

    public void notifyTvMessage(int i, android.os.Bundle bundle) {
        if (this.mSession != null) {
            this.mSession.notifyTvMessage(i, bundle);
        }
    }

    public void setTimeShiftPositionCallback(android.media.tv.TvView.TimeShiftPositionCallback timeShiftPositionCallback) {
        this.mTimeShiftPositionCallback = timeShiftPositionCallback;
        ensurePositionTracking();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensurePositionTracking() {
        if (this.mSession == null) {
            return;
        }
        this.mSession.timeShiftEnablePositionTracking(this.mTimeShiftPositionCallback != null);
    }

    public void sendAppPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("action cannot be null or an empty string");
        }
        if (this.mSession != null) {
            this.mSession.sendAppPrivateCommand(str, bundle);
            return;
        }
        android.util.Log.w(TAG, "sendAppPrivateCommand - session not yet created (action \"" + str + "\" pending)");
        synchronized (this.mPendingAppPrivateCommands) {
            this.mPendingAppPrivateCommands.add(android.util.Pair.create(str, bundle));
        }
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

    public void setOnUnhandledInputEventListener(android.media.tv.TvView.OnUnhandledInputEventListener onUnhandledInputEventListener) {
        this.mOnUnhandledInputEventListener = onUnhandledInputEventListener;
    }

    public void setTvMessageEnabled(int i, boolean z) {
        if (this.mSession != null) {
            this.mSession.setTvMessageEnabled(i, z);
        }
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

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        if (super.dispatchTouchEvent(motionEvent)) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        android.view.MotionEvent copy = motionEvent.copy();
        return this.mSession.dispatchInputEvent(copy, copy, this.mFinishedInputEventCallback, this.mHandler) != 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(android.view.MotionEvent motionEvent) {
        if (super.dispatchTrackballEvent(motionEvent)) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        android.view.MotionEvent copy = motionEvent.copy();
        return this.mSession.dispatchInputEvent(copy, copy, this.mFinishedInputEventCallback, this.mHandler) != 0;
    }

    @Override // android.view.View
    public boolean dispatchGenericMotionEvent(android.view.MotionEvent motionEvent) {
        if (super.dispatchGenericMotionEvent(motionEvent)) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        android.view.MotionEvent copy = motionEvent.copy();
        return this.mSession.dispatchInputEvent(copy, copy, this.mFinishedInputEventCallback, this.mHandler) != 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchWindowFocusChanged(boolean z) {
        super.dispatchWindowFocusChanged(z);
        synchronized (sMainTvViewLock) {
            if (z) {
                if (this == sMainTvView.get() && this.mSession != null && checkChangeHdmiCecActiveSourcePermission()) {
                    this.mSession.setMain();
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        createSessionOverlayView();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        removeSessionOverlayView();
        super.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mUseRequestedSurfaceLayout) {
            this.mSurfaceView.layout(this.mSurfaceViewLeft, this.mSurfaceViewTop, this.mSurfaceViewRight, this.mSurfaceViewBottom);
        } else {
            this.mSurfaceView.layout(0, 0, i3 - i, i4 - i2);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        this.mSurfaceView.measure(i, i2);
        int measuredWidth = this.mSurfaceView.getMeasuredWidth();
        int measuredHeight = this.mSurfaceView.getMeasuredHeight();
        int measuredState = this.mSurfaceView.getMeasuredState();
        setMeasuredDimension(resolveSizeAndState(measuredWidth, i, measuredState), resolveSizeAndState(measuredHeight, i2, measuredState << 16));
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean gatherTransparentRegion(android.graphics.Region region) {
        if (this.mWindowZOrder != 2 && region != null) {
            int width = getWidth();
            int height = getHeight();
            if (width > 0 && height > 0) {
                int[] iArr = new int[2];
                getLocationInWindow(iArr);
                int i = iArr[0];
                int i2 = iArr[1];
                region.op(i, i2, i + width, i2 + height, android.graphics.Region.Op.UNION);
            }
        }
        return super.gatherTransparentRegion(region);
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        if (this.mWindowZOrder != 2) {
            canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
        }
        super.draw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        if (this.mWindowZOrder != 2) {
            canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(android.view.View view, int i) {
        super.onVisibilityChanged(view, i);
        this.mSurfaceView.setVisibility(i);
        if (i == 0) {
            createSessionOverlayView();
        } else {
            removeSessionOverlayView();
        }
    }

    private void resetSurfaceView() {
        if (this.mSurfaceView != null) {
            this.mSurfaceView.getHolder().removeCallback(this.mSurfaceHolderCallback);
            removeView(this.mSurfaceView);
        }
        this.mSurface = null;
        this.mSurfaceView = new android.view.SurfaceView(getContext(), this.mAttrs, this.mDefStyleAttr) { // from class: android.media.tv.TvView.3
            @Override // android.view.SurfaceView
            protected void updateSurface() {
                super.updateSurface();
                android.media.tv.TvView.this.relayoutSessionOverlayView();
            }
        };
        this.mSurfaceView.setSecure(true);
        this.mSurfaceView.getHolder().addCallback(this.mSurfaceHolderCallback);
        if (this.mWindowZOrder == 1) {
            this.mSurfaceView.setZOrderMediaOverlay(true);
        } else if (this.mWindowZOrder == 2) {
            this.mSurfaceView.setZOrderOnTop(true);
        }
        addView(this.mSurfaceView);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void createSessionOverlayView() {
        if (this.mSession == null || !isAttachedToWindow() || this.mOverlayViewCreated || this.mWindowZOrder != 0) {
            return;
        }
        this.mOverlayViewFrame = getViewFrameOnScreen();
        this.mSession.createOverlayView(this, this.mOverlayViewFrame);
        this.mOverlayViewCreated = true;
    }

    private void removeSessionOverlayView() {
        if (this.mSession == null || !this.mOverlayViewCreated) {
            return;
        }
        this.mSession.removeOverlayView();
        this.mOverlayViewCreated = false;
        this.mOverlayViewFrame = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void relayoutSessionOverlayView() {
        if (this.mSession == null || !isAttachedToWindow() || !this.mOverlayViewCreated || this.mWindowZOrder != 0) {
            return;
        }
        android.graphics.Rect viewFrameOnScreen = getViewFrameOnScreen();
        if (viewFrameOnScreen.equals(this.mOverlayViewFrame)) {
            return;
        }
        this.mSession.relayoutOverlayView(viewFrameOnScreen);
        this.mOverlayViewFrame = viewFrameOnScreen;
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
    public boolean checkChangeHdmiCecActiveSourcePermission() {
        return getContext().checkSelfPermission(android.Manifest.permission.CHANGE_HDMI_CEC_ACTIVE_SOURCE) == 0;
    }

    public static abstract class TimeShiftPositionCallback {
        public void onTimeShiftStartPositionChanged(java.lang.String str, long j) {
        }

        public void onTimeShiftCurrentPositionChanged(java.lang.String str, long j) {
        }
    }

    public static abstract class TvInputCallback {
        public void onConnectionFailed(java.lang.String str) {
        }

        public void onDisconnected(java.lang.String str) {
        }

        public void onChannelRetuned(java.lang.String str, android.net.Uri uri) {
        }

        public void onAudioPresentationsChanged(java.lang.String str, java.util.List<android.media.AudioPresentation> list) {
        }

        public void onAudioPresentationSelected(java.lang.String str, int i, int i2) {
        }

        public void onTracksChanged(java.lang.String str, java.util.List<android.media.tv.TvTrackInfo> list) {
        }

        public void onTrackSelected(java.lang.String str, int i, java.lang.String str2) {
        }

        public void onVideoSizeChanged(java.lang.String str, int i, int i2) {
        }

        public void onVideoAvailable(java.lang.String str) {
        }

        public void onVideoUnavailable(java.lang.String str, int i) {
        }

        public void onContentAllowed(java.lang.String str) {
        }

        public void onContentBlocked(java.lang.String str, android.media.tv.TvContentRating tvContentRating) {
        }

        @android.annotation.SystemApi
        public void onEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        }

        public void onTimeShiftStatusChanged(java.lang.String str, int i) {
        }

        public void onAitInfoUpdated(java.lang.String str, android.media.tv.AitInfo aitInfo) {
        }

        public void onSignalStrengthUpdated(java.lang.String str, int i) {
        }

        public void onCueingMessageAvailability(java.lang.String str, boolean z) {
        }

        public void onTimeShiftMode(java.lang.String str, int i) {
        }

        public void onAvailableSpeeds(java.lang.String str, float[] fArr) {
        }

        public void onTuned(java.lang.String str, android.net.Uri uri) {
        }

        public void onTvMessage(java.lang.String str, int i, android.os.Bundle bundle) {
        }

        public void onVideoFreezeUpdated(java.lang.String str, boolean z) {
        }
    }

    private class MySessionCallback extends android.media.tv.TvInputManager.SessionCallback {
        android.net.Uri mChannelUri;
        final java.lang.String mInputId;
        android.net.Uri mRecordedProgramUri;
        android.os.Bundle mTuneParams;

        MySessionCallback(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) {
            this.mInputId = str;
            this.mChannelUri = uri;
            this.mTuneParams = bundle;
        }

        MySessionCallback(java.lang.String str, android.net.Uri uri) {
            this.mInputId = str;
            this.mRecordedProgramUri = uri;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionCreated(android.media.tv.TvInputManager.Session session) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onSessionCreated - session already created");
                if (session != null) {
                    session.release();
                    return;
                }
                return;
            }
            android.media.tv.TvView.this.mSession = session;
            if (session != null) {
                synchronized (android.media.tv.TvView.this.mPendingAppPrivateCommands) {
                    for (android.util.Pair pair : android.media.tv.TvView.this.mPendingAppPrivateCommands) {
                        android.media.tv.TvView.this.mSession.sendAppPrivateCommand((java.lang.String) pair.first, (android.os.Bundle) pair.second);
                    }
                    android.media.tv.TvView.this.mPendingAppPrivateCommands.clear();
                }
                synchronized (android.media.tv.TvView.sMainTvViewLock) {
                    if (android.media.tv.TvView.this.hasWindowFocus() && android.media.tv.TvView.this == android.media.tv.TvView.sMainTvView.get() && android.media.tv.TvView.this.checkChangeHdmiCecActiveSourcePermission()) {
                        android.media.tv.TvView.this.mSession.setMain();
                    }
                }
                if (android.media.tv.TvView.this.mSurface != null) {
                    android.media.tv.TvView.this.setSessionSurface(android.media.tv.TvView.this.mSurface);
                    if (android.media.tv.TvView.this.mSurfaceChanged) {
                        android.media.tv.TvView.this.dispatchSurfaceChanged(android.media.tv.TvView.this.mSurfaceFormat, android.media.tv.TvView.this.mSurfaceWidth, android.media.tv.TvView.this.mSurfaceHeight);
                    }
                }
                android.media.tv.TvView.this.createSessionOverlayView();
                if (android.media.tv.TvView.this.mStreamVolume != null) {
                    android.media.tv.TvView.this.mSession.setStreamVolume(android.media.tv.TvView.this.mStreamVolume.floatValue());
                }
                if (android.media.tv.TvView.this.mCaptionEnabled != null) {
                    android.media.tv.TvView.this.mSession.setCaptionEnabled(android.media.tv.TvView.this.mCaptionEnabled.booleanValue());
                }
                if (this.mChannelUri != null) {
                    android.media.tv.TvView.this.mSession.tune(this.mChannelUri, this.mTuneParams);
                } else {
                    android.media.tv.TvView.this.mSession.timeShiftPlay(this.mRecordedProgramUri);
                }
                android.media.tv.TvView.this.ensurePositionTracking();
                return;
            }
            android.media.tv.TvView.this.mSessionCallback = null;
            if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onConnectionFailed(this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionReleased(android.media.tv.TvInputManager.Session session) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onSessionReleased - session not created");
                return;
            }
            android.media.tv.TvView.this.mOverlayViewCreated = false;
            android.media.tv.TvView.this.mOverlayViewFrame = null;
            android.media.tv.TvView.this.mSessionCallback = null;
            android.media.tv.TvView.this.mSession = null;
            if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onDisconnected(this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onChannelRetuned(android.media.tv.TvInputManager.Session session, android.net.Uri uri) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onChannelRetuned - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onChannelRetuned(this.mInputId, uri);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onAudioPresentationsChanged(android.media.tv.TvInputManager.Session session, java.util.List<android.media.AudioPresentation> list) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onAudioPresentationsChanged - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onAudioPresentationsChanged(this.mInputId, list);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onAudioPresentationSelected(android.media.tv.TvInputManager.Session session, int i, int i2) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onAudioPresentationSelected - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onAudioPresentationSelected(this.mInputId, i, i2);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTracksChanged(android.media.tv.TvInputManager.Session session, java.util.List<android.media.tv.TvTrackInfo> list) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onTracksChanged - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onTracksChanged(this.mInputId, list);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTrackSelected(android.media.tv.TvInputManager.Session session, int i, java.lang.String str) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onTrackSelected - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onTrackSelected(this.mInputId, i, str);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onVideoSizeChanged(android.media.tv.TvInputManager.Session session, int i, int i2) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onVideoSizeChanged - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onVideoSizeChanged(this.mInputId, i, i2);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onVideoAvailable(android.media.tv.TvInputManager.Session session) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onVideoAvailable - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onVideoAvailable(this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onVideoUnavailable(android.media.tv.TvInputManager.Session session, int i) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onVideoUnavailable - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onVideoUnavailable(this.mInputId, i);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onContentAllowed(android.media.tv.TvInputManager.Session session) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onContentAllowed - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onContentAllowed(this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onContentBlocked(android.media.tv.TvInputManager.Session session, android.media.tv.TvContentRating tvContentRating) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onContentBlocked - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onContentBlocked(this.mInputId, tvContentRating);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onLayoutSurface(android.media.tv.TvInputManager.Session session, int i, int i2, int i3, int i4) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onLayoutSurface - session not created");
                return;
            }
            android.media.tv.TvView.this.mSurfaceViewLeft = i;
            android.media.tv.TvView.this.mSurfaceViewTop = i2;
            android.media.tv.TvView.this.mSurfaceViewRight = i3;
            android.media.tv.TvView.this.mSurfaceViewBottom = i4;
            android.media.tv.TvView.this.mUseRequestedSurfaceLayout = true;
            android.media.tv.TvView.this.requestLayout();
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionEvent(android.media.tv.TvInputManager.Session session, java.lang.String str, android.os.Bundle bundle) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onSessionEvent - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onEvent(this.mInputId, str, bundle);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTimeShiftStatusChanged(android.media.tv.TvInputManager.Session session, int i) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onTimeShiftStatusChanged - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onTimeShiftStatusChanged(this.mInputId, i);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTimeShiftStartPositionChanged(android.media.tv.TvInputManager.Session session, long j) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onTimeShiftStartPositionChanged - session not created");
            } else if (android.media.tv.TvView.this.mTimeShiftPositionCallback != null) {
                android.media.tv.TvView.this.mTimeShiftPositionCallback.onTimeShiftStartPositionChanged(this.mInputId, j);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTimeShiftCurrentPositionChanged(android.media.tv.TvInputManager.Session session, long j) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onTimeShiftCurrentPositionChanged - session not created");
            } else if (android.media.tv.TvView.this.mTimeShiftPositionCallback != null) {
                android.media.tv.TvView.this.mTimeShiftPositionCallback.onTimeShiftCurrentPositionChanged(this.mInputId, j);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onAitInfoUpdated(android.media.tv.TvInputManager.Session session, android.media.tv.AitInfo aitInfo) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onAitInfoUpdated - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onAitInfoUpdated(this.mInputId, aitInfo);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSignalStrengthUpdated(android.media.tv.TvInputManager.Session session, int i) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onSignalStrengthUpdated - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onSignalStrengthUpdated(this.mInputId, i);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onCueingMessageAvailability(android.media.tv.TvInputManager.Session session, boolean z) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onCueingMessageAvailability - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onCueingMessageAvailability(this.mInputId, z);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTimeShiftMode(android.media.tv.TvInputManager.Session session, int i) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onTimeShiftMode - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onTimeShiftMode(this.mInputId, i);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onAvailableSpeeds(android.media.tv.TvInputManager.Session session, float[] fArr) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onAvailableSpeeds - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onAvailableSpeeds(this.mInputId, fArr);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTuned(android.media.tv.TvInputManager.Session session, android.net.Uri uri) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onTuned - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onTuned(this.mInputId, uri);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTvMessage(android.media.tv.TvInputManager.Session session, int i, android.os.Bundle bundle) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onTvMessage - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onTvMessage(this.mInputId, i, bundle);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onVideoFreezeUpdated(android.media.tv.TvInputManager.Session session, boolean z) {
            if (this != android.media.tv.TvView.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvView.TAG, "onVideoFreezeUpdated - session not created");
            } else if (android.media.tv.TvView.this.mCallback != null) {
                android.media.tv.TvView.this.mCallback.onVideoFreezeUpdated(this.mInputId, z);
            }
        }
    }
}
