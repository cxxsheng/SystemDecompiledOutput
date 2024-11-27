package android.service.attention;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class AttentionService extends android.app.Service {
    public static final int ATTENTION_FAILURE_CAMERA_PERMISSION_ABSENT = 6;
    public static final int ATTENTION_FAILURE_CANCELLED = 3;
    public static final int ATTENTION_FAILURE_PREEMPTED = 4;
    public static final int ATTENTION_FAILURE_TIMED_OUT = 5;
    public static final int ATTENTION_FAILURE_UNKNOWN = 2;
    public static final int ATTENTION_SUCCESS_ABSENT = 0;
    public static final int ATTENTION_SUCCESS_PRESENT = 1;
    private static final java.lang.String LOG_TAG = "AttentionService";
    public static final double PROXIMITY_UNKNOWN = -1.0d;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.attention.AttentionService";
    private final android.service.attention.IAttentionService.Stub mBinder = new android.service.attention.IAttentionService.Stub() { // from class: android.service.attention.AttentionService.1
        @Override // android.service.attention.IAttentionService
        public void checkAttention(android.service.attention.IAttentionCallback iAttentionCallback) {
            com.android.internal.util.Preconditions.checkNotNull(iAttentionCallback);
            android.service.attention.AttentionService.this.onCheckAttention(new android.service.attention.AttentionService.AttentionCallback(iAttentionCallback));
        }

        @Override // android.service.attention.IAttentionService
        public void cancelAttentionCheck(android.service.attention.IAttentionCallback iAttentionCallback) {
            com.android.internal.util.Preconditions.checkNotNull(iAttentionCallback);
            android.service.attention.AttentionService.this.onCancelAttentionCheck(new android.service.attention.AttentionService.AttentionCallback(iAttentionCallback));
        }

        @Override // android.service.attention.IAttentionService
        public void onStartProximityUpdates(android.service.attention.IProximityUpdateCallback iProximityUpdateCallback) {
            java.util.Objects.requireNonNull(iProximityUpdateCallback);
            android.service.attention.AttentionService.this.onStartProximityUpdates(new android.service.attention.AttentionService.ProximityUpdateCallback(iProximityUpdateCallback));
        }

        @Override // android.service.attention.IAttentionService
        public void onStopProximityUpdates() {
            android.service.attention.AttentionService.this.onStopProximityUpdates();
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttentionFailureCodes {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttentionSuccessCodes {
    }

    public abstract void onCancelAttentionCheck(android.service.attention.AttentionService.AttentionCallback attentionCallback);

    public abstract void onCheckAttention(android.service.attention.AttentionService.AttentionCallback attentionCallback);

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    public void onStartProximityUpdates(android.service.attention.AttentionService.ProximityUpdateCallback proximityUpdateCallback) {
        android.util.Slog.w(LOG_TAG, "Override this method.");
    }

    public void onStopProximityUpdates() {
        android.util.Slog.w(LOG_TAG, "Override this method.");
    }

    public static final class AttentionCallback {
        private final android.service.attention.IAttentionCallback mCallback;

        private AttentionCallback(android.service.attention.IAttentionCallback iAttentionCallback) {
            this.mCallback = iAttentionCallback;
        }

        public void onSuccess(int i, long j) {
            try {
                this.mCallback.onSuccess(i, j);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public void onFailure(int i) {
            try {
                this.mCallback.onFailure(i);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public static final class ProximityUpdateCallback {
        private final java.lang.ref.WeakReference<android.service.attention.IProximityUpdateCallback> mCallback;

        private ProximityUpdateCallback(android.service.attention.IProximityUpdateCallback iProximityUpdateCallback) {
            this.mCallback = new java.lang.ref.WeakReference<>(iProximityUpdateCallback);
        }

        public void onProximityUpdate(double d) {
            try {
                if (this.mCallback.get() != null) {
                    this.mCallback.get().onProximityUpdate(d);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }
}
