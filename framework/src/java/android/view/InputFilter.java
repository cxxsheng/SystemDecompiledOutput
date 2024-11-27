package android.view;

/* loaded from: classes4.dex */
public abstract class InputFilter extends android.view.IInputFilter.Stub {
    private static final int MSG_INPUT_EVENT = 3;
    private static final int MSG_INSTALL = 1;
    private static final int MSG_UNINSTALL = 2;
    private final android.view.InputFilter.H mH;
    private android.view.IInputFilterHost mHost;
    private final android.view.InputEventConsistencyVerifier mInboundInputEventConsistencyVerifier;
    private final android.view.InputEventConsistencyVerifier mOutboundInputEventConsistencyVerifier;

    public InputFilter(android.os.Looper looper) {
        android.view.InputEventConsistencyVerifier inputEventConsistencyVerifier;
        if (android.view.InputEventConsistencyVerifier.isInstrumentationEnabled()) {
            inputEventConsistencyVerifier = new android.view.InputEventConsistencyVerifier(this, 1, "InputFilter#InboundInputEventConsistencyVerifier");
        } else {
            inputEventConsistencyVerifier = null;
        }
        this.mInboundInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mOutboundInputEventConsistencyVerifier = android.view.InputEventConsistencyVerifier.isInstrumentationEnabled() ? new android.view.InputEventConsistencyVerifier(this, 1, "InputFilter#OutboundInputEventConsistencyVerifier") : null;
        this.mH = new android.view.InputFilter.H(looper);
    }

    @Override // android.view.IInputFilter
    public final void install(android.view.IInputFilterHost iInputFilterHost) {
        this.mH.obtainMessage(1, iInputFilterHost).sendToTarget();
    }

    @Override // android.view.IInputFilter
    public final void uninstall() {
        this.mH.obtainMessage(2).sendToTarget();
    }

    @Override // android.view.IInputFilter
    public final void filterInputEvent(android.view.InputEvent inputEvent, int i) {
        this.mH.obtainMessage(3, i, 0, inputEvent).sendToTarget();
    }

    public void sendInputEvent(android.view.InputEvent inputEvent, int i) {
        if (inputEvent == null) {
            throw new java.lang.IllegalArgumentException("event must not be null");
        }
        if (this.mHost == null) {
            throw new java.lang.IllegalStateException("Cannot send input event because the input filter is not installed.");
        }
        if (this.mOutboundInputEventConsistencyVerifier != null) {
            this.mOutboundInputEventConsistencyVerifier.onInputEvent(inputEvent, 0);
        }
        try {
            this.mHost.sendInputEvent(inputEvent, i);
        } catch (android.os.RemoteException e) {
        }
    }

    public void onInputEvent(android.view.InputEvent inputEvent, int i) {
        sendInputEvent(inputEvent, i);
    }

    public void onInstalled() {
    }

    public void onUninstalled() {
    }

    private final class H extends android.os.Handler {
        public H(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.view.InputFilter.this.mHost = (android.view.IInputFilterHost) message.obj;
                    if (android.view.InputFilter.this.mInboundInputEventConsistencyVerifier != null) {
                        android.view.InputFilter.this.mInboundInputEventConsistencyVerifier.reset();
                    }
                    if (android.view.InputFilter.this.mOutboundInputEventConsistencyVerifier != null) {
                        android.view.InputFilter.this.mOutboundInputEventConsistencyVerifier.reset();
                    }
                    android.view.InputFilter.this.onInstalled();
                    return;
                case 2:
                    try {
                        android.view.InputFilter.this.onUninstalled();
                        return;
                    } finally {
                        android.view.InputFilter.this.mHost = null;
                    }
                case 3:
                    android.view.InputEvent inputEvent = (android.view.InputEvent) message.obj;
                    try {
                        if (android.view.InputFilter.this.mInboundInputEventConsistencyVerifier != null) {
                            android.view.InputFilter.this.mInboundInputEventConsistencyVerifier.onInputEvent(inputEvent, 0);
                        }
                        android.view.InputFilter.this.onInputEvent(inputEvent, message.arg1);
                        return;
                    } finally {
                        inputEvent.recycle();
                    }
                default:
                    return;
            }
        }
    }
}
