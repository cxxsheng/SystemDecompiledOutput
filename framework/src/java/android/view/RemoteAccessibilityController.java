package android.view;

/* loaded from: classes4.dex */
class RemoteAccessibilityController {
    private static final java.lang.String TAG = "RemoteAccessibilityController";
    private android.view.RemoteAccessibilityController.RemoteAccessibilityEmbeddedConnection mConnectionWrapper;
    private int mHostId;
    private android.view.View mHostView;
    private android.graphics.Matrix mWindowMatrixForEmbeddedHierarchy = new android.graphics.Matrix();
    private final float[] mMatrixValues = new float[9];

    RemoteAccessibilityController(android.view.View view) {
        this.mHostView = view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runOnUiThread(java.lang.Runnable runnable) {
        android.os.Handler handler = this.mHostView.getHandler();
        if (handler != null && handler.getLooper() != android.os.Looper.myLooper()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    void assosciateHierarchy(android.view.accessibility.IAccessibilityEmbeddedConnection iAccessibilityEmbeddedConnection, android.os.IBinder iBinder, int i) {
        this.mHostId = i;
        try {
            setRemoteAccessibilityEmbeddedConnection(iAccessibilityEmbeddedConnection, iAccessibilityEmbeddedConnection.associateEmbeddedHierarchy(iBinder, this.mHostId));
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Error in associateEmbeddedHierarchy " + e);
        }
    }

    void disassosciateHierarchy() {
        setRemoteAccessibilityEmbeddedConnection(null, null);
    }

    boolean alreadyAssociated(android.view.accessibility.IAccessibilityEmbeddedConnection iAccessibilityEmbeddedConnection) {
        if (this.mConnectionWrapper == null) {
            return false;
        }
        return this.mConnectionWrapper.mConnection.equals(iAccessibilityEmbeddedConnection);
    }

    boolean connected() {
        return this.mConnectionWrapper != null;
    }

    android.os.IBinder getLeashToken() {
        return this.mConnectionWrapper.getLeashToken();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RemoteAccessibilityEmbeddedConnection implements android.os.IBinder.DeathRecipient {
        private final android.view.accessibility.IAccessibilityEmbeddedConnection mConnection;
        private final java.lang.ref.WeakReference<android.view.RemoteAccessibilityController> mController;
        private final android.os.IBinder mLeashToken;

        RemoteAccessibilityEmbeddedConnection(android.view.RemoteAccessibilityController remoteAccessibilityController, android.view.accessibility.IAccessibilityEmbeddedConnection iAccessibilityEmbeddedConnection, android.os.IBinder iBinder) {
            this.mController = new java.lang.ref.WeakReference<>(remoteAccessibilityController);
            this.mConnection = iAccessibilityEmbeddedConnection;
            this.mLeashToken = iBinder;
        }

        android.view.accessibility.IAccessibilityEmbeddedConnection getConnection() {
            return this.mConnection;
        }

        android.os.IBinder getLeashToken() {
            return this.mLeashToken;
        }

        void linkToDeath() throws android.os.RemoteException {
            this.mConnection.asBinder().linkToDeath(this, 0);
        }

        void unlinkToDeath() {
            this.mConnection.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            unlinkToDeath();
            final android.view.RemoteAccessibilityController remoteAccessibilityController = this.mController.get();
            if (remoteAccessibilityController == null) {
                return;
            }
            remoteAccessibilityController.runOnUiThread(new java.lang.Runnable() { // from class: android.view.RemoteAccessibilityController$RemoteAccessibilityEmbeddedConnection$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.RemoteAccessibilityController.RemoteAccessibilityEmbeddedConnection.this.lambda$binderDied$0(remoteAccessibilityController);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$0(android.view.RemoteAccessibilityController remoteAccessibilityController) {
            if (remoteAccessibilityController.mConnectionWrapper == this) {
                remoteAccessibilityController.mConnectionWrapper = null;
            }
        }
    }

    private void setRemoteAccessibilityEmbeddedConnection(android.view.accessibility.IAccessibilityEmbeddedConnection iAccessibilityEmbeddedConnection, android.os.IBinder iBinder) {
        try {
            if (this.mConnectionWrapper != null) {
                this.mConnectionWrapper.getConnection().disassociateEmbeddedHierarchy();
                this.mConnectionWrapper.unlinkToDeath();
                this.mConnectionWrapper = null;
            }
            if (iAccessibilityEmbeddedConnection != null && iBinder != null) {
                this.mConnectionWrapper = new android.view.RemoteAccessibilityController.RemoteAccessibilityEmbeddedConnection(this, iAccessibilityEmbeddedConnection, iBinder);
                this.mConnectionWrapper.linkToDeath();
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Error while setRemoteEmbeddedConnection " + e);
        }
    }

    private android.view.RemoteAccessibilityController.RemoteAccessibilityEmbeddedConnection getRemoteAccessibilityEmbeddedConnection() {
        return this.mConnectionWrapper;
    }

    void setWindowMatrix(android.graphics.Matrix matrix, boolean z) {
        if (!z && matrix.equals(this.mWindowMatrixForEmbeddedHierarchy)) {
            return;
        }
        try {
            android.view.RemoteAccessibilityController.RemoteAccessibilityEmbeddedConnection remoteAccessibilityEmbeddedConnection = getRemoteAccessibilityEmbeddedConnection();
            if (remoteAccessibilityEmbeddedConnection == null) {
                return;
            }
            matrix.getValues(this.mMatrixValues);
            remoteAccessibilityEmbeddedConnection.getConnection().setWindowMatrix(this.mMatrixValues);
            this.mWindowMatrixForEmbeddedHierarchy.set(matrix);
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Error while setScreenMatrix " + e);
        }
    }
}
