package android.view.inputmethod;

/* loaded from: classes4.dex */
public abstract class CancellableHandwritingGesture extends android.view.inputmethod.HandwritingGesture {
    android.os.CancellationSignal mCancellationSignal;
    android.os.IBinder mCancellationSignalToken;

    public void setCancellationSignal(android.os.CancellationSignal cancellationSignal) {
        this.mCancellationSignal = cancellationSignal;
    }

    android.os.CancellationSignal getCancellationSignal() {
        return this.mCancellationSignal;
    }

    public void unbeamCancellationSignal(android.os.CancellationSignalBeamer.Receiver receiver) {
        if (this.mCancellationSignalToken != null) {
            this.mCancellationSignal = receiver.unbeam(this.mCancellationSignalToken);
            this.mCancellationSignalToken = null;
        }
    }
}
