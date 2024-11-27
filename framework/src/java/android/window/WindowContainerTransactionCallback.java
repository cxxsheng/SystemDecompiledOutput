package android.window;

/* loaded from: classes4.dex */
public abstract class WindowContainerTransactionCallback {
    final android.window.IWindowContainerTransactionCallback mInterface = new android.window.IWindowContainerTransactionCallback.Stub() { // from class: android.window.WindowContainerTransactionCallback.1
        @Override // android.window.IWindowContainerTransactionCallback
        public void onTransactionReady(int i, android.view.SurfaceControl.Transaction transaction) {
            android.window.WindowContainerTransactionCallback.this.onTransactionReady(i, transaction);
        }
    };

    public abstract void onTransactionReady(int i, android.view.SurfaceControl.Transaction transaction);
}
