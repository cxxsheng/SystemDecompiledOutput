package android.service.voice;

/* loaded from: classes3.dex */
final class VoiceInteractionWindow extends android.app.Dialog {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "VoiceInteractionWindow";
    private final android.graphics.Rect mBounds;
    private final android.service.voice.VoiceInteractionWindow.Callback mCallback;
    private final android.view.KeyEvent.DispatcherState mDispatcherState;
    private final int mGravity;
    private final android.view.KeyEvent.Callback mKeyEventCallback;
    private final java.lang.String mName;
    private final boolean mTakesFocus;
    private int mWindowState;
    private final int mWindowType;

    interface Callback {
        void onBackPressed();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface WindowState {
        public static final int DESTROYED = 4;
        public static final int REJECTED_AT_LEAST_ONCE = 3;
        public static final int SHOWN_AT_LEAST_ONCE = 2;
        public static final int TOKEN_PENDING = 0;
        public static final int TOKEN_SET = 1;
    }

    void setToken(android.os.IBinder iBinder) {
        switch (this.mWindowState) {
            case 0:
                android.view.WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.token = iBinder;
                getWindow().setAttributes(attributes);
                updateWindowState(1);
                getWindow().getDecorView().setVisibility(4);
                show();
                return;
            case 1:
            case 2:
            case 3:
                throw new java.lang.IllegalStateException("setToken can be called only once");
            case 4:
                android.util.Log.i(TAG, "Ignoring setToken() because window is already destroyed.");
                return;
            default:
                throw new java.lang.IllegalStateException("Unexpected state=" + this.mWindowState);
        }
    }

    VoiceInteractionWindow(android.content.Context context, java.lang.String str, int i, android.service.voice.VoiceInteractionWindow.Callback callback, android.view.KeyEvent.Callback callback2, android.view.KeyEvent.DispatcherState dispatcherState, int i2, int i3, boolean z) {
        super(context, i);
        this.mBounds = new android.graphics.Rect();
        this.mWindowState = 0;
        this.mName = str;
        this.mCallback = callback;
        this.mKeyEventCallback = callback2;
        this.mDispatcherState = dispatcherState;
        this.mWindowType = i2;
        this.mGravity = i3;
        this.mTakesFocus = z;
        initDockWindow();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mDispatcherState.reset();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        getWindow().getDecorView().getHitRect(this.mBounds);
        if (motionEvent.isWithinBoundsNoHistory(this.mBounds.left, this.mBounds.top, this.mBounds.right - 1, this.mBounds.bottom - 1)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        android.view.MotionEvent clampNoHistory = motionEvent.clampNoHistory(this.mBounds.left, this.mBounds.top, this.mBounds.right - 1, this.mBounds.bottom - 1);
        boolean dispatchTouchEvent = super.dispatchTouchEvent(clampNoHistory);
        clampNoHistory.recycle();
        return dispatchTouchEvent;
    }

    private void updateWidthHeight(android.view.WindowManager.LayoutParams layoutParams) {
        if (layoutParams.gravity == 48 || layoutParams.gravity == 80) {
            layoutParams.width = -1;
            layoutParams.height = -2;
        } else {
            layoutParams.width = -2;
            layoutParams.height = -1;
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (this.mKeyEventCallback != null && this.mKeyEventCallback.onKeyDown(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, android.view.KeyEvent keyEvent) {
        if (this.mKeyEventCallback != null && this.mKeyEventCallback.onKeyLongPress(i, keyEvent)) {
            return true;
        }
        return super.onKeyLongPress(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (this.mKeyEventCallback != null && this.mKeyEventCallback.onKeyUp(i, keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
        if (this.mKeyEventCallback != null && this.mKeyEventCallback.onKeyMultiple(i, i2, keyEvent)) {
            return true;
        }
        return super.onKeyMultiple(i, i2, keyEvent);
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        if (this.mCallback != null) {
            this.mCallback.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    private void initDockWindow() {
        int i;
        int i2;
        android.view.WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.type = this.mWindowType;
        attributes.setTitle(this.mName);
        attributes.gravity = this.mGravity;
        updateWidthHeight(attributes);
        getWindow().setAttributes(attributes);
        if (!this.mTakesFocus) {
            i2 = 266;
            i = 264;
        } else {
            i = 288;
            i2 = 298;
        }
        getWindow().setFlags(i, i2);
    }

    @Override // android.app.Dialog
    public void show() {
        switch (this.mWindowState) {
            case 0:
                throw new java.lang.IllegalStateException("Window token is not set yet.");
            case 1:
            case 2:
                try {
                    super.show();
                    updateWindowState(2);
                    return;
                } catch (android.view.WindowManager.BadTokenException e) {
                    android.util.Log.i(TAG, "Probably the IME window token is already invalidated. show() does nothing.");
                    updateWindowState(3);
                    return;
                }
            case 3:
                android.util.Log.i(TAG, "Not trying to call show() because it was already rejected once.");
                return;
            case 4:
                android.util.Log.i(TAG, "Ignoring show() because the window is already destroyed.");
                return;
            default:
                throw new java.lang.IllegalStateException("Unexpected state=" + this.mWindowState);
        }
    }

    private void updateWindowState(int i) {
        this.mWindowState = i;
    }

    private static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "TOKEN_PENDING";
            case 1:
                return "TOKEN_SET";
            case 2:
                return "SHOWN_AT_LEAST_ONCE";
            case 3:
                return "REJECTED_AT_LEAST_ONCE";
            case 4:
                return "DESTROYED";
            default:
                throw new java.lang.IllegalStateException("Unknown state=" + i);
        }
    }
}
