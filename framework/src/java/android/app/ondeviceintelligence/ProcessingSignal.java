package android.app.ondeviceintelligence;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class ProcessingSignal {
    private static final int MAX_QUEUE_SIZE = 10;
    private java.util.concurrent.Executor mExecutor;
    private android.app.ondeviceintelligence.ProcessingSignal.OnProcessingSignalCallback mOnProcessingSignalCallback;
    private android.app.ondeviceintelligence.IProcessingSignal mRemote;
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.ArrayDeque<android.os.PersistableBundle> mActionParamsQueue = new java.util.ArrayDeque<>(10);

    public interface OnProcessingSignalCallback {
        void onSignalReceived(android.os.PersistableBundle persistableBundle);
    }

    public void sendSignal(android.os.PersistableBundle persistableBundle) {
        synchronized (this.mLock) {
            if (this.mActionParamsQueue.size() > 10) {
                throw new java.lang.RuntimeException("Maximum actions that can be queued are : 10");
            }
            this.mActionParamsQueue.add(persistableBundle);
            final android.app.ondeviceintelligence.ProcessingSignal.OnProcessingSignalCallback onProcessingSignalCallback = this.mOnProcessingSignalCallback;
            android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal = this.mRemote;
            if (onProcessingSignalCallback != null) {
                while (!this.mActionParamsQueue.isEmpty()) {
                    final android.os.PersistableBundle removeFirst = this.mActionParamsQueue.removeFirst();
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.ProcessingSignal$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.ondeviceintelligence.ProcessingSignal.OnProcessingSignalCallback.this.onSignalReceived(removeFirst);
                        }
                    });
                }
            }
            if (iProcessingSignal != null) {
                while (!this.mActionParamsQueue.isEmpty()) {
                    try {
                        iProcessingSignal.sendSignal(this.mActionParamsQueue.removeFirst());
                    } catch (android.os.RemoteException e) {
                        throw new java.lang.RuntimeException(e);
                    }
                }
            }
        }
    }

    public void setOnProcessingSignalCallback(java.util.concurrent.Executor executor, final android.app.ondeviceintelligence.ProcessingSignal.OnProcessingSignalCallback onProcessingSignalCallback) {
        java.util.Objects.requireNonNull(executor);
        synchronized (this.mLock) {
            if (this.mOnProcessingSignalCallback == onProcessingSignalCallback) {
                return;
            }
            this.mOnProcessingSignalCallback = onProcessingSignalCallback;
            this.mExecutor = executor;
            if (onProcessingSignalCallback != null && !this.mActionParamsQueue.isEmpty()) {
                while (!this.mActionParamsQueue.isEmpty()) {
                    final android.os.PersistableBundle removeFirst = this.mActionParamsQueue.removeFirst();
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.ProcessingSignal$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.ondeviceintelligence.ProcessingSignal.OnProcessingSignalCallback.this.onSignalReceived(removeFirst);
                        }
                    });
                }
            }
        }
    }

    void setRemote(android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal) {
        synchronized (this.mLock) {
            this.mRemote = iProcessingSignal;
            if (!this.mActionParamsQueue.isEmpty() && iProcessingSignal != null) {
                while (!this.mActionParamsQueue.isEmpty()) {
                    try {
                        iProcessingSignal.sendSignal(this.mActionParamsQueue.removeFirst());
                    } catch (android.os.RemoteException e) {
                        throw new java.lang.RuntimeException("Failed to send action to remote signal", e);
                    }
                }
            }
        }
    }

    public static android.app.ondeviceintelligence.IProcessingSignal createTransport() {
        return new android.app.ondeviceintelligence.ProcessingSignal.Transport();
    }

    public static android.app.ondeviceintelligence.ProcessingSignal fromTransport(android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal) {
        if (iProcessingSignal instanceof android.app.ondeviceintelligence.ProcessingSignal.Transport) {
            return ((android.app.ondeviceintelligence.ProcessingSignal.Transport) iProcessingSignal).mProcessingSignal;
        }
        return null;
    }

    private static final class Transport extends android.app.ondeviceintelligence.IProcessingSignal.Stub {
        final android.app.ondeviceintelligence.ProcessingSignal mProcessingSignal;

        private Transport() {
            this.mProcessingSignal = new android.app.ondeviceintelligence.ProcessingSignal();
        }

        @Override // android.app.ondeviceintelligence.IProcessingSignal
        public void sendSignal(android.os.PersistableBundle persistableBundle) {
            this.mProcessingSignal.sendSignal(persistableBundle);
        }
    }
}
