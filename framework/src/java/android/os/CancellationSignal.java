package android.os;

/* loaded from: classes3.dex */
public final class CancellationSignal {
    private boolean mCancelInProgress;
    private boolean mIsCanceled;
    private android.os.CancellationSignal.OnCancelListener mOnCancelListener;
    private android.os.ICancellationSignal mRemote;

    public interface OnCancelListener {
        void onCancel();
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this) {
            z = this.mIsCanceled;
        }
        return z;
    }

    public void throwIfCanceled() {
        if (isCanceled()) {
            throw new android.os.OperationCanceledException();
        }
    }

    public void cancel() {
        synchronized (this) {
            if (this.mIsCanceled) {
                return;
            }
            this.mIsCanceled = true;
            this.mCancelInProgress = true;
            android.os.CancellationSignal.OnCancelListener onCancelListener = this.mOnCancelListener;
            android.os.ICancellationSignal iCancellationSignal = this.mRemote;
            if (onCancelListener != null) {
                try {
                    onCancelListener.onCancel();
                } catch (java.lang.Throwable th) {
                    synchronized (this) {
                        this.mCancelInProgress = false;
                        notifyAll();
                        throw th;
                    }
                }
            }
            if (iCancellationSignal != null) {
                try {
                    iCancellationSignal.cancel();
                } catch (android.os.RemoteException e) {
                }
            }
            synchronized (this) {
                this.mCancelInProgress = false;
                notifyAll();
            }
        }
    }

    public void setOnCancelListener(android.os.CancellationSignal.OnCancelListener onCancelListener) {
        synchronized (this) {
            waitForCancelFinishedLocked();
            if (this.mOnCancelListener == onCancelListener) {
                return;
            }
            this.mOnCancelListener = onCancelListener;
            if (this.mIsCanceled && onCancelListener != null) {
                onCancelListener.onCancel();
            }
        }
    }

    public void setRemote(android.os.ICancellationSignal iCancellationSignal) {
        synchronized (this) {
            waitForCancelFinishedLocked();
            if (this.mRemote == iCancellationSignal) {
                return;
            }
            this.mRemote = iCancellationSignal;
            if (this.mIsCanceled && iCancellationSignal != null) {
                try {
                    iCancellationSignal.cancel();
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    private void waitForCancelFinishedLocked() {
        while (this.mCancelInProgress) {
            try {
                wait();
            } catch (java.lang.InterruptedException e) {
            }
        }
    }

    public static android.os.ICancellationSignal createTransport() {
        return new android.os.CancellationSignal.Transport();
    }

    public static android.os.CancellationSignal fromTransport(android.os.ICancellationSignal iCancellationSignal) {
        if (iCancellationSignal instanceof android.os.CancellationSignal.Transport) {
            return ((android.os.CancellationSignal.Transport) iCancellationSignal).mCancellationSignal;
        }
        return null;
    }

    private static final class Transport extends android.os.ICancellationSignal.Stub {
        final android.os.CancellationSignal mCancellationSignal;

        private Transport() {
            this.mCancellationSignal = new android.os.CancellationSignal();
        }

        @Override // android.os.ICancellationSignal
        public void cancel() throws android.os.RemoteException {
            this.mCancellationSignal.cancel();
        }
    }
}
