package android.app.ambientcontext;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AmbientContextManager {
    public static final java.lang.String EXTRA_AMBIENT_CONTEXT_EVENTS = "android.app.ambientcontext.extra.AMBIENT_CONTEXT_EVENTS";
    public static final int STATUS_ACCESS_DENIED = 5;
    public static final int STATUS_MICROPHONE_DISABLED = 4;
    public static final int STATUS_NOT_SUPPORTED = 2;
    public static final java.lang.String STATUS_RESPONSE_BUNDLE_KEY = "android.app.ambientcontext.AmbientContextStatusBundleKey";
    public static final int STATUS_SERVICE_UNAVAILABLE = 3;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN = 0;
    private final android.content.Context mContext;
    private final android.app.ambientcontext.IAmbientContextManager mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StatusCode {
    }

    public static java.util.List<android.app.ambientcontext.AmbientContextEvent> getEventsFromIntent(android.content.Intent intent) {
        if (intent.hasExtra(EXTRA_AMBIENT_CONTEXT_EVENTS)) {
            return intent.getParcelableArrayListExtra(EXTRA_AMBIENT_CONTEXT_EVENTS, android.app.ambientcontext.AmbientContextEvent.class);
        }
        return new java.util.ArrayList();
    }

    public AmbientContextManager(android.content.Context context, android.app.ambientcontext.IAmbientContextManager iAmbientContextManager) {
        this.mContext = context;
        this.mService = iAmbientContextManager;
    }

    public void queryAmbientContextServiceStatus(java.util.Set<java.lang.Integer> set, final java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        try {
            this.mService.queryServiceStatus(integerSetToIntArray(set), this.mContext.getOpPackageName(), new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.app.ambientcontext.AmbientContextManager$$ExternalSyntheticLambda3
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    android.app.ambientcontext.AmbientContextManager.lambda$queryAmbientContextServiceStatus$1(executor, consumer, bundle);
                }
            }));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$queryAmbientContextServiceStatus$1(java.util.concurrent.Executor executor, final java.util.function.Consumer consumer, android.os.Bundle bundle) {
        final int i = bundle.getInt(STATUS_RESPONSE_BUNDLE_KEY);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            executor.execute(new java.lang.Runnable() { // from class: android.app.ambientcontext.AmbientContextManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(java.lang.Integer.valueOf(i));
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void startConsentActivity(java.util.Set<java.lang.Integer> set) {
        try {
            this.mService.startConsentActivity(integerSetToIntArray(set), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static int[] integerSetToIntArray(java.util.Set<java.lang.Integer> set) {
        int[] iArr = new int[set.size()];
        java.util.Iterator<java.lang.Integer> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        return iArr;
    }

    public void registerObserver(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, android.app.PendingIntent pendingIntent, final java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        com.android.internal.util.Preconditions.checkArgument(!pendingIntent.isImmutable());
        try {
            this.mService.registerObserver(ambientContextEventRequest, pendingIntent, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.app.ambientcontext.AmbientContextManager$$ExternalSyntheticLambda2
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    android.app.ambientcontext.AmbientContextManager.lambda$registerObserver$3(executor, consumer, bundle);
                }
            }));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$registerObserver$3(java.util.concurrent.Executor executor, final java.util.function.Consumer consumer, android.os.Bundle bundle) {
        final int i = bundle.getInt(STATUS_RESPONSE_BUNDLE_KEY);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            executor.execute(new java.lang.Runnable() { // from class: android.app.ambientcontext.AmbientContextManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(java.lang.Integer.valueOf(i));
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* renamed from: android.app.ambientcontext.AmbientContextManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.app.ambientcontext.IAmbientContextObserver.Stub {
        final /* synthetic */ android.app.ambientcontext.AmbientContextCallback val$ambientContextCallback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass1(java.util.concurrent.Executor executor, android.app.ambientcontext.AmbientContextCallback ambientContextCallback) {
            this.val$executor = executor;
            this.val$ambientContextCallback = ambientContextCallback;
        }

        @Override // android.app.ambientcontext.IAmbientContextObserver
        public void onEvents(final java.util.List<android.app.ambientcontext.AmbientContextEvent> list) throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.app.ambientcontext.AmbientContextCallback ambientContextCallback = this.val$ambientContextCallback;
                executor.execute(new java.lang.Runnable() { // from class: android.app.ambientcontext.AmbientContextManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.ambientcontext.AmbientContextCallback.this.onEvents(list);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.app.ambientcontext.IAmbientContextObserver
        public void onRegistrationComplete(final int i) throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.app.ambientcontext.AmbientContextCallback ambientContextCallback = this.val$ambientContextCallback;
                executor.execute(new java.lang.Runnable() { // from class: android.app.ambientcontext.AmbientContextManager$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.ambientcontext.AmbientContextCallback.this.onRegistrationComplete(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void registerObserver(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.util.concurrent.Executor executor, android.app.ambientcontext.AmbientContextCallback ambientContextCallback) {
        try {
            this.mService.registerObserverWithCallback(ambientContextEventRequest, this.mContext.getPackageName(), new android.app.ambientcontext.AmbientContextManager.AnonymousClass1(executor, ambientContextCallback));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterObserver() {
        try {
            this.mService.unregisterObserver(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
