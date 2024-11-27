package android.service.watchdog;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class ExplicitHealthCheckService extends android.app.Service {
    public static final java.lang.String BIND_PERMISSION = "android.permission.BIND_EXPLICIT_HEALTH_CHECK_SERVICE";
    public static final java.lang.String EXTRA_HEALTH_CHECK_PASSED_PACKAGE = "android.service.watchdog.extra.health_check_passed_package";
    public static final java.lang.String EXTRA_REQUESTED_PACKAGES = "android.service.watchdog.extra.requested_packages";
    public static final java.lang.String EXTRA_SUPPORTED_PACKAGES = "android.service.watchdog.extra.supported_packages";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.watchdog.ExplicitHealthCheckService";
    private static final java.lang.String TAG = "ExplicitHealthCheckService";
    private android.os.RemoteCallback mCallback;
    private final android.service.watchdog.ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper mWrapper = new android.service.watchdog.ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper();
    private final android.os.Handler mHandler = android.os.Handler.createAsync(android.os.Looper.getMainLooper());

    public abstract void onCancelHealthCheck(java.lang.String str);

    public abstract java.util.List<java.lang.String> onGetRequestedPackages();

    public abstract java.util.List<android.service.watchdog.ExplicitHealthCheckService.PackageConfig> onGetSupportedPackages();

    public abstract void onRequestHealthCheck(java.lang.String str);

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return this.mWrapper;
    }

    public void setCallback(android.os.RemoteCallback remoteCallback) {
        this.mCallback = remoteCallback;
    }

    public final void notifyHealthCheckPassed(final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.watchdog.ExplicitHealthCheckService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.service.watchdog.ExplicitHealthCheckService.this.lambda$notifyHealthCheckPassed$0(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyHealthCheckPassed$0(java.lang.String str) {
        if (this.mCallback != null) {
            java.util.Objects.requireNonNull(str, "Package passing explicit health check must be non-null");
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(EXTRA_HEALTH_CHECK_PASSED_PACKAGE, str);
            this.mCallback.sendResult(bundle);
            return;
        }
        android.util.Log.wtf(TAG, "System missed explicit health check result for " + str);
    }

    @android.annotation.SystemApi
    public static final class PackageConfig implements android.os.Parcelable {
        private final long mHealthCheckTimeoutMillis;
        private final java.lang.String mPackageName;
        private static final long DEFAULT_HEALTH_CHECK_TIMEOUT_MILLIS = java.util.concurrent.TimeUnit.HOURS.toMillis(1);
        public static final android.os.Parcelable.Creator<android.service.watchdog.ExplicitHealthCheckService.PackageConfig> CREATOR = new android.os.Parcelable.Creator<android.service.watchdog.ExplicitHealthCheckService.PackageConfig>() { // from class: android.service.watchdog.ExplicitHealthCheckService.PackageConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.service.watchdog.ExplicitHealthCheckService.PackageConfig createFromParcel(android.os.Parcel parcel) {
                return new android.service.watchdog.ExplicitHealthCheckService.PackageConfig(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.service.watchdog.ExplicitHealthCheckService.PackageConfig[] newArray(int i) {
                return new android.service.watchdog.ExplicitHealthCheckService.PackageConfig[i];
            }
        };

        public PackageConfig(java.lang.String str, long j) {
            this.mPackageName = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str);
            if (j == 0) {
                this.mHealthCheckTimeoutMillis = DEFAULT_HEALTH_CHECK_TIMEOUT_MILLIS;
            } else {
                this.mHealthCheckTimeoutMillis = com.android.internal.util.Preconditions.checkArgumentNonnegative(j);
            }
        }

        private PackageConfig(android.os.Parcel parcel) {
            this.mPackageName = parcel.readString();
            this.mHealthCheckTimeoutMillis = parcel.readLong();
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public long getHealthCheckTimeoutMillis() {
            return this.mHealthCheckTimeoutMillis;
        }

        public java.lang.String toString() {
            return "PackageConfig{" + this.mPackageName + ", " + this.mHealthCheckTimeoutMillis + "}";
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.service.watchdog.ExplicitHealthCheckService.PackageConfig)) {
                return false;
            }
            android.service.watchdog.ExplicitHealthCheckService.PackageConfig packageConfig = (android.service.watchdog.ExplicitHealthCheckService.PackageConfig) obj;
            return java.util.Objects.equals(java.lang.Long.valueOf(packageConfig.getHealthCheckTimeoutMillis()), java.lang.Long.valueOf(this.mHealthCheckTimeoutMillis)) && java.util.Objects.equals(packageConfig.getPackageName(), this.mPackageName);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mPackageName, java.lang.Long.valueOf(this.mHealthCheckTimeoutMillis));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mPackageName);
            parcel.writeLong(this.mHealthCheckTimeoutMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ExplicitHealthCheckServiceWrapper extends android.service.watchdog.IExplicitHealthCheckService.Stub {
        private ExplicitHealthCheckServiceWrapper() {
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void setCallback(final android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            android.service.watchdog.ExplicitHealthCheckService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.watchdog.ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.watchdog.ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper.this.lambda$setCallback$0(remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setCallback$0(android.os.RemoteCallback remoteCallback) {
            android.service.watchdog.ExplicitHealthCheckService.this.mCallback = remoteCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$request$1(java.lang.String str) {
            android.service.watchdog.ExplicitHealthCheckService.this.onRequestHealthCheck(str);
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void request(final java.lang.String str) throws android.os.RemoteException {
            android.service.watchdog.ExplicitHealthCheckService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.watchdog.ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.watchdog.ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper.this.lambda$request$1(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$cancel$2(java.lang.String str) {
            android.service.watchdog.ExplicitHealthCheckService.this.onCancelHealthCheck(str);
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void cancel(final java.lang.String str) throws android.os.RemoteException {
            android.service.watchdog.ExplicitHealthCheckService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.watchdog.ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.watchdog.ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper.this.lambda$cancel$2(str);
                }
            });
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void getSupportedPackages(final android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            android.service.watchdog.ExplicitHealthCheckService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.watchdog.ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.watchdog.ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper.this.lambda$getSupportedPackages$3(remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getSupportedPackages$3(android.os.RemoteCallback remoteCallback) {
            java.util.List<android.service.watchdog.ExplicitHealthCheckService.PackageConfig> onGetSupportedPackages = android.service.watchdog.ExplicitHealthCheckService.this.onGetSupportedPackages();
            java.util.Objects.requireNonNull(onGetSupportedPackages, "Supported package list must be non-null");
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelableArrayList(android.service.watchdog.ExplicitHealthCheckService.EXTRA_SUPPORTED_PACKAGES, new java.util.ArrayList<>(onGetSupportedPackages));
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.watchdog.IExplicitHealthCheckService
        public void getRequestedPackages(final android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            android.service.watchdog.ExplicitHealthCheckService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.watchdog.ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.watchdog.ExplicitHealthCheckService.ExplicitHealthCheckServiceWrapper.this.lambda$getRequestedPackages$4(remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getRequestedPackages$4(android.os.RemoteCallback remoteCallback) {
            java.util.List<java.lang.String> onGetRequestedPackages = android.service.watchdog.ExplicitHealthCheckService.this.onGetRequestedPackages();
            java.util.Objects.requireNonNull(onGetRequestedPackages, "Requested  package list must be non-null");
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putStringArrayList(android.service.watchdog.ExplicitHealthCheckService.EXTRA_REQUESTED_PACKAGES, new java.util.ArrayList<>(onGetRequestedPackages));
            remoteCallback.sendResult(bundle);
        }
    }
}
