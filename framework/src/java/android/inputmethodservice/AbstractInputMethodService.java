package android.inputmethodservice;

/* loaded from: classes2.dex */
public abstract class AbstractInputMethodService extends android.window.WindowProviderService implements android.view.KeyEvent.Callback {
    final android.view.KeyEvent.DispatcherState mDispatcherState = new android.view.KeyEvent.DispatcherState();
    private android.view.inputmethod.InputMethod mInputMethod;
    private android.inputmethodservice.InputMethodServiceInternal mInputMethodServiceInternal;

    public abstract android.inputmethodservice.AbstractInputMethodService.AbstractInputMethodImpl onCreateInputMethodInterface();

    public abstract android.inputmethodservice.AbstractInputMethodService.AbstractInputMethodSessionImpl onCreateInputMethodSessionInterface();

    public final android.view.inputmethod.InputMethod getInputMethodInternal() {
        return this.mInputMethod;
    }

    public abstract class AbstractInputMethodImpl implements android.view.inputmethod.InputMethod {
        public AbstractInputMethodImpl() {
        }

        @Override // android.view.inputmethod.InputMethod
        public void createSession(android.view.inputmethod.InputMethod.SessionCallback sessionCallback) {
            sessionCallback.sessionCreated(android.inputmethodservice.AbstractInputMethodService.this.onCreateInputMethodSessionInterface());
        }

        @Override // android.view.inputmethod.InputMethod
        public void setSessionEnabled(android.view.inputmethod.InputMethodSession inputMethodSession, boolean z) {
            ((android.inputmethodservice.AbstractInputMethodService.AbstractInputMethodSessionImpl) inputMethodSession).setEnabled(z);
        }

        @Override // android.view.inputmethod.InputMethod
        public void revokeSession(android.view.inputmethod.InputMethodSession inputMethodSession) {
            ((android.inputmethodservice.AbstractInputMethodService.AbstractInputMethodSessionImpl) inputMethodSession).revokeSelf();
        }
    }

    public abstract class AbstractInputMethodSessionImpl implements android.view.inputmethod.InputMethodSession {
        boolean mEnabled = true;
        boolean mRevoked;

        public AbstractInputMethodSessionImpl() {
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public boolean isRevoked() {
            return this.mRevoked;
        }

        public void setEnabled(boolean z) {
            if (!this.mRevoked) {
                this.mEnabled = z;
            }
        }

        public void revokeSelf() {
            this.mRevoked = true;
            this.mEnabled = false;
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void dispatchKeyEvent(int i, android.view.KeyEvent keyEvent, android.view.inputmethod.InputMethodSession.EventCallback eventCallback) {
            boolean dispatch = keyEvent.dispatch(android.inputmethodservice.AbstractInputMethodService.this, android.inputmethodservice.AbstractInputMethodService.this.mDispatcherState, this);
            if (eventCallback != null) {
                eventCallback.finishedEvent(i, dispatch);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void dispatchTrackballEvent(int i, android.view.MotionEvent motionEvent, android.view.inputmethod.InputMethodSession.EventCallback eventCallback) {
            boolean onTrackballEvent = android.inputmethodservice.AbstractInputMethodService.this.onTrackballEvent(motionEvent);
            if (eventCallback != null) {
                eventCallback.finishedEvent(i, onTrackballEvent);
            }
        }

        @Override // android.view.inputmethod.InputMethodSession
        public void dispatchGenericMotionEvent(int i, android.view.MotionEvent motionEvent, android.view.inputmethod.InputMethodSession.EventCallback eventCallback) {
            boolean onGenericMotionEvent = android.inputmethodservice.AbstractInputMethodService.this.onGenericMotionEvent(motionEvent);
            if (eventCallback != null) {
                eventCallback.finishedEvent(i, onGenericMotionEvent);
            }
        }
    }

    public android.view.KeyEvent.DispatcherState getKeyDispatcherState() {
        return this.mDispatcherState;
    }

    @Override // android.app.Service
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (this.mInputMethod == null) {
            this.mInputMethod = onCreateInputMethodInterface();
        }
        if (this.mInputMethodServiceInternal == null) {
            this.mInputMethodServiceInternal = createInputMethodServiceInternal();
        }
        return new android.inputmethodservice.IInputMethodWrapper(this.mInputMethodServiceInternal, this.mInputMethod);
    }

    android.inputmethodservice.InputMethodServiceInternal createInputMethodServiceInternal() {
        return new android.inputmethodservice.InputMethodServiceInternal() { // from class: android.inputmethodservice.AbstractInputMethodService.1
            @Override // android.inputmethodservice.InputMethodServiceInternal
            public android.content.Context getContext() {
                return android.inputmethodservice.AbstractInputMethodService.this;
            }

            @Override // android.inputmethodservice.InputMethodServiceInternal
            public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
                android.inputmethodservice.AbstractInputMethodService.this.dump(fileDescriptor, printWriter, strArr);
            }
        };
    }

    public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    @Override // android.window.WindowProviderService, android.window.WindowProvider
    public final int getWindowType() {
        return 2011;
    }

    @Override // android.window.WindowProviderService, android.window.WindowProvider
    public final android.os.Bundle getWindowContextOptions() {
        return super.getWindowContextOptions();
    }

    @Override // android.window.WindowProviderService
    public final int getInitialDisplayId() {
        try {
            return android.view.WindowManagerGlobal.getWindowManagerService().getImeDisplayId();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
