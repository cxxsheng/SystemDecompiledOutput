package android.telecom.Logging;

/* loaded from: classes3.dex */
public abstract class Runnable {
    private final java.lang.Object mLock;
    private final java.lang.Runnable mRunnable = new java.lang.Runnable() { // from class: android.telecom.Logging.Runnable.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (android.telecom.Logging.Runnable.this.mLock) {
                try {
                    android.telecom.Log.continueSession(android.telecom.Logging.Runnable.this.mSubsession, android.telecom.Logging.Runnable.this.mSubsessionName);
                    android.telecom.Logging.Runnable.this.loggedRun();
                } finally {
                    if (android.telecom.Logging.Runnable.this.mSubsession != null) {
                        android.telecom.Log.endSession();
                        android.telecom.Logging.Runnable.this.mSubsession = null;
                    }
                }
            }
        }
    };
    private android.telecom.Logging.Session mSubsession;
    private final java.lang.String mSubsessionName;

    public abstract void loggedRun();

    public Runnable(java.lang.String str, java.lang.Object obj) {
        if (obj == null) {
            this.mLock = new java.lang.Object();
        } else {
            this.mLock = obj;
        }
        this.mSubsessionName = str;
    }

    public final java.lang.Runnable getRunnableToCancel() {
        return this.mRunnable;
    }

    public java.lang.Runnable prepare() {
        cancel();
        this.mSubsession = android.telecom.Log.createSubsession();
        return this.mRunnable;
    }

    public void cancel() {
        synchronized (this.mLock) {
            android.telecom.Log.cancelSubsession(this.mSubsession);
            this.mSubsession = null;
        }
    }
}
