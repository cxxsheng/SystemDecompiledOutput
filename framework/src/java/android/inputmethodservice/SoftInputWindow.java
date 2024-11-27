package android.inputmethodservice;

/* loaded from: classes2.dex */
final class SoftInputWindow extends android.app.Dialog {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "SoftInputWindow";
    private final android.view.KeyEvent.DispatcherState mDispatcherState;
    private final android.inputmethodservice.InputMethodService mService;
    private int mWindowState;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface WindowState {
        public static final int DESTROYED = 4;
        public static final int REJECTED_AT_LEAST_ONCE = 3;
        public static final int SHOWN_AT_LEAST_ONCE = 2;
        public static final int TOKEN_PENDING = 0;
        public static final int TOKEN_SET = 1;
    }

    @Override // android.app.Dialog
    protected boolean allowsRegisterDefaultOnBackInvokedCallback() {
        return false;
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

    SoftInputWindow(android.inputmethodservice.InputMethodService inputMethodService, int i, android.view.KeyEvent.DispatcherState dispatcherState) {
        super(inputMethodService, i);
        this.mWindowState = 0;
        this.mService = inputMethodService;
        this.mDispatcherState = dispatcherState;
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mDispatcherState.reset();
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
                } catch (android.view.WindowManager.BadTokenException | android.view.WindowManager.InvalidDisplayException e) {
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

    void dismissForDestroyIfNecessary() {
        switch (this.mWindowState) {
            case 0:
            case 1:
                updateWindowState(4);
                return;
            case 2:
                try {
                    getWindow().setWindowAnimations(0);
                    dismiss();
                } catch (android.view.WindowManager.BadTokenException e) {
                    android.util.Log.i(TAG, "Probably the IME window token is already invalidated. No need to dismiss it.");
                }
                updateWindowState(4);
                return;
            case 3:
                android.util.Log.i(TAG, "Not trying to dismiss the window because it is most likely unnecessary.");
                updateWindowState(4);
                return;
            case 4:
                throw new java.lang.IllegalStateException("dismissForDestroyIfNecessary can be called only once");
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

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464262L, this.mWindowState);
        protoOutputStream.end(start);
    }
}
