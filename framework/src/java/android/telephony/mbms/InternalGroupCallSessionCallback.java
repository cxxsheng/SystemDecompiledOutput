package android.telephony.mbms;

/* loaded from: classes3.dex */
public class InternalGroupCallSessionCallback extends android.telephony.mbms.IMbmsGroupCallSessionCallback.Stub {
    private final android.telephony.mbms.MbmsGroupCallSessionCallback mAppCallback;
    private final java.util.concurrent.Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalGroupCallSessionCallback(android.telephony.mbms.MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback, java.util.concurrent.Executor executor) {
        this.mAppCallback = mbmsGroupCallSessionCallback;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
    public void onError(final int i, final java.lang.String str) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalGroupCallSessionCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalGroupCallSessionCallback.this.mAppCallback.onError(i, str);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
    public void onAvailableSaisUpdated(final java.util.List list, final java.util.List list2) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalGroupCallSessionCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalGroupCallSessionCallback.this.mAppCallback.onAvailableSaisUpdated(list, list2);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
    public void onServiceInterfaceAvailable(final java.lang.String str, final int i) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalGroupCallSessionCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalGroupCallSessionCallback.this.mAppCallback.onServiceInterfaceAvailable(str, i);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
    public void onMiddlewareReady() {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalGroupCallSessionCallback.4
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalGroupCallSessionCallback.this.mAppCallback.onMiddlewareReady();
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void stop() {
        this.mIsStopped = true;
    }
}
