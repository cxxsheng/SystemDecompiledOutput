package android.app.wearable;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class WearableSensingManager {
    public static final java.lang.String EXTRA_WEARABLE_SENSING_DATA_REQUEST = "android.app.wearable.extra.WEARABLE_SENSING_DATA_REQUEST";
    public static final int STATUS_ACCESS_DENIED = 5;
    public static final int STATUS_CHANNEL_ERROR = 7;
    public static final java.lang.String STATUS_RESPONSE_BUNDLE_KEY = "android.app.wearable.WearableSensingStatusBundleKey";
    public static final int STATUS_SERVICE_UNAVAILABLE = 3;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_UNSUPPORTED = 2;
    public static final int STATUS_UNSUPPORTED_DATA_TYPE = 8;
    public static final int STATUS_UNSUPPORTED_OPERATION = 6;
    public static final int STATUS_WEARABLE_UNAVAILABLE = 4;
    private final android.content.Context mContext;
    private final android.app.wearable.IWearableSensingManager mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StatusCode {
    }

    public static android.app.wearable.WearableSensingDataRequest getDataRequestFromIntent(android.content.Intent intent) {
        return (android.app.wearable.WearableSensingDataRequest) intent.getParcelableExtra(EXTRA_WEARABLE_SENSING_DATA_REQUEST, android.app.wearable.WearableSensingDataRequest.class);
    }

    public WearableSensingManager(android.content.Context context, android.app.wearable.IWearableSensingManager iWearableSensingManager) {
        this.mContext = context;
        this.mService = iWearableSensingManager;
    }

    public void provideConnection(android.os.ParcelFileDescriptor parcelFileDescriptor, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        try {
            this.mService.provideConnection(parcelFileDescriptor, createStatusCallback(executor, consumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void provideDataStream(android.os.ParcelFileDescriptor parcelFileDescriptor, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        try {
            this.mService.provideDataStream(parcelFileDescriptor, createStatusCallback(executor, consumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void provideData(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        try {
            this.mService.provideData(persistableBundle, sharedMemory, createStatusCallback(executor, consumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerDataRequestObserver(int i, android.app.PendingIntent pendingIntent, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        try {
            this.mService.registerDataRequestObserver(i, pendingIntent, createStatusCallback(executor, consumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterDataRequestObserver(int i, android.app.PendingIntent pendingIntent, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        try {
            this.mService.unregisterDataRequestObserver(i, pendingIntent, createStatusCallback(executor, consumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startHotwordRecognition(android.content.ComponentName componentName, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        try {
            this.mService.startHotwordRecognition(componentName, createStatusCallback(executor, consumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopHotwordRecognition(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        try {
            this.mService.stopHotwordRecognition(createStatusCallback(executor, consumer));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static android.os.RemoteCallback createStatusCallback(final java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        return new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda0
            @Override // android.os.RemoteCallback.OnResultListener
            public final void onResult(android.os.Bundle bundle) {
                android.app.wearable.WearableSensingManager.lambda$createStatusCallback$1(executor, consumer, bundle);
            }
        });
    }

    static /* synthetic */ void lambda$createStatusCallback$1(java.util.concurrent.Executor executor, final java.util.function.Consumer consumer, android.os.Bundle bundle) {
        final int i = bundle.getInt("android.app.wearable.WearableSensingStatusBundleKey");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            executor.execute(new java.lang.Runnable() { // from class: android.app.wearable.WearableSensingManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(java.lang.Integer.valueOf(i));
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
