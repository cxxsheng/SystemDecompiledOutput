package android.app.ondeviceintelligence;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class OnDeviceIntelligenceManager {
    public static final java.lang.String API_VERSION_BUNDLE_KEY = "ApiVersionBundleKey";
    public static final int REQUEST_TYPE_EMBEDDINGS = 2;
    public static final int REQUEST_TYPE_INFERENCE = 0;
    public static final int REQUEST_TYPE_PREPARE = 1;
    private final android.content.Context mContext;
    private final android.app.ondeviceintelligence.IOnDeviceIntelligenceManager mService;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestType {
    }

    public OnDeviceIntelligenceManager(android.content.Context context, android.app.ondeviceintelligence.IOnDeviceIntelligenceManager iOnDeviceIntelligenceManager) {
        this.mContext = context;
        this.mService = iOnDeviceIntelligenceManager;
    }

    public void getVersion(final java.util.concurrent.Executor executor, final java.util.function.LongConsumer longConsumer) {
        try {
            this.mService.getVersion(new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda0
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    android.app.ondeviceintelligence.OnDeviceIntelligenceManager.lambda$getVersion$4(executor, longConsumer, bundle);
                }
            }));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$getVersion$4(final java.util.concurrent.Executor executor, final java.util.function.LongConsumer longConsumer, android.os.Bundle bundle) {
        if (bundle == null) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            r1.accept(0L);
                        }
                    });
                }
            });
        }
        final long j = bundle.getLong(API_VERSION_BUNDLE_KEY);
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda4
            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
            public final void runOrThrow() {
                executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        r1.accept(r2);
                    }
                });
            }
        });
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.app.ondeviceintelligence.IFeatureCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$callbackExecutor;
        final /* synthetic */ android.os.OutcomeReceiver val$featureReceiver;

        AnonymousClass1(java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$featureReceiver = outcomeReceiver;
        }

        @Override // android.app.ondeviceintelligence.IFeatureCallback
        public void onSuccess(final android.app.ondeviceintelligence.Feature feature) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$featureReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onResult(r2);
                        }
                    });
                }
            });
        }

        @Override // android.app.ondeviceintelligence.IFeatureCallback
        public void onFailure(final int i, final java.lang.String str, final android.os.PersistableBundle persistableBundle) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$featureReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onError(new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException(r2, r3, r4));
                        }
                    });
                }
            });
        }
    }

    public void getFeature(int i, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.app.ondeviceintelligence.Feature, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException> outcomeReceiver) {
        try {
            this.mService.getFeature(i, new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass1(executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$2, reason: invalid class name */
    class AnonymousClass2 extends android.app.ondeviceintelligence.IListFeaturesCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$callbackExecutor;
        final /* synthetic */ android.os.OutcomeReceiver val$featureListReceiver;

        AnonymousClass2(java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$featureListReceiver = outcomeReceiver;
        }

        @Override // android.app.ondeviceintelligence.IListFeaturesCallback
        public void onSuccess(final java.util.List<android.app.ondeviceintelligence.Feature> list) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$featureListReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$2$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$2$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onResult(r2);
                        }
                    });
                }
            });
        }

        @Override // android.app.ondeviceintelligence.IListFeaturesCallback
        public void onFailure(final int i, final java.lang.String str, final android.os.PersistableBundle persistableBundle) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$featureListReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$2$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$2$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onError(new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException(r2, r3, r4));
                        }
                    });
                }
            });
        }
    }

    public void listFeatures(java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.util.List<android.app.ondeviceintelligence.Feature>, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException> outcomeReceiver) {
        try {
            this.mService.listFeatures(new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass2(executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$3, reason: invalid class name */
    class AnonymousClass3 extends android.app.ondeviceintelligence.IFeatureDetailsCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$callbackExecutor;
        final /* synthetic */ android.os.OutcomeReceiver val$featureDetailsReceiver;

        AnonymousClass3(java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$featureDetailsReceiver = outcomeReceiver;
        }

        @Override // android.app.ondeviceintelligence.IFeatureDetailsCallback
        public void onSuccess(final android.app.ondeviceintelligence.FeatureDetails featureDetails) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$featureDetailsReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$3$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$3$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onResult(r2);
                        }
                    });
                }
            });
        }

        @Override // android.app.ondeviceintelligence.IFeatureDetailsCallback
        public void onFailure(final int i, final java.lang.String str, final android.os.PersistableBundle persistableBundle) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$featureDetailsReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$3$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$3$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onError(new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException(r2, r3, r4));
                        }
                    });
                }
            });
        }
    }

    public void getFeatureDetails(android.app.ondeviceintelligence.Feature feature, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.app.ondeviceintelligence.FeatureDetails, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException> outcomeReceiver) {
        try {
            this.mService.getFeatureDetails(feature, new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass3(executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4, reason: invalid class name */
    class AnonymousClass4 extends android.app.ondeviceintelligence.IDownloadCallback.Stub {
        final /* synthetic */ android.app.ondeviceintelligence.DownloadCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$callbackExecutor;

        AnonymousClass4(java.util.concurrent.Executor executor, android.app.ondeviceintelligence.DownloadCallback downloadCallback) {
            this.val$callbackExecutor = executor;
            this.val$callback = downloadCallback;
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadStarted(final long j) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.app.ondeviceintelligence.DownloadCallback downloadCallback = this.val$callback;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda5
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.ondeviceintelligence.DownloadCallback.this.onDownloadStarted(r2);
                        }
                    });
                }
            });
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadProgress(final long j) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.app.ondeviceintelligence.DownloadCallback downloadCallback = this.val$callback;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.ondeviceintelligence.DownloadCallback.this.onDownloadProgress(r2);
                        }
                    });
                }
            });
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadFailed(final int i, final java.lang.String str, final android.os.PersistableBundle persistableBundle) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.app.ondeviceintelligence.DownloadCallback downloadCallback = this.val$callback;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.ondeviceintelligence.DownloadCallback.this.onDownloadFailed(r2, r3, r4);
                        }
                    });
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDownloadCompleted$7(java.util.concurrent.Executor executor, final android.os.PersistableBundle persistableBundle) throws java.lang.Exception {
            executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass4.this.lambda$onDownloadCompleted$6(persistableBundle);
                }
            });
        }

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        /* renamed from: onDownloadCompleted, reason: merged with bridge method [inline-methods] */
        public void lambda$onDownloadCompleted$6(final android.os.PersistableBundle persistableBundle) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$4$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass4.this.lambda$onDownloadCompleted$7(executor, persistableBundle);
                }
            });
        }
    }

    public void requestFeatureDownload(android.app.ondeviceintelligence.Feature feature, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.app.ondeviceintelligence.DownloadCallback downloadCallback) {
        android.os.ICancellationSignal iCancellationSignal;
        try {
            android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass4 anonymousClass4 = new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass4(executor, downloadCallback);
            if (cancellationSignal == null) {
                iCancellationSignal = null;
            } else {
                iCancellationSignal = android.os.CancellationSignal.createTransport();
                cancellationSignal.setRemote(iCancellationSignal);
            }
            this.mService.requestFeatureDownload(feature, iCancellationSignal, anonymousClass4);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$5, reason: invalid class name */
    class AnonymousClass5 extends android.app.ondeviceintelligence.ITokenCountCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$callbackExecutor;
        final /* synthetic */ android.os.OutcomeReceiver val$outcomeReceiver;

        AnonymousClass5(java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$outcomeReceiver = outcomeReceiver;
        }

        @Override // android.app.ondeviceintelligence.ITokenCountCallback
        public void onSuccess(final long j) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$outcomeReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$5$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$5$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onResult(java.lang.Long.valueOf(r2));
                        }
                    });
                }
            });
        }

        @Override // android.app.ondeviceintelligence.ITokenCountCallback
        public void onFailure(final int i, final java.lang.String str, final android.os.PersistableBundle persistableBundle) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$outcomeReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$5$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$5$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onError(new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException(r2, r3, r4));
                        }
                    });
                }
            });
        }
    }

    public void requestTokenCount(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Long, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException> outcomeReceiver) {
        android.os.ICancellationSignal iCancellationSignal;
        try {
            android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass5 anonymousClass5 = new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass5(executor, outcomeReceiver);
            if (cancellationSignal == null) {
                iCancellationSignal = null;
            } else {
                iCancellationSignal = android.os.CancellationSignal.createTransport();
                cancellationSignal.setRemote(iCancellationSignal);
            }
            this.mService.requestTokenCount(feature, content, iCancellationSignal, anonymousClass5);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6, reason: invalid class name */
    class AnonymousClass6 extends android.app.ondeviceintelligence.IResponseCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$callbackExecutor;
        final /* synthetic */ android.os.OutcomeReceiver val$responseOutcomeReceiver;

        AnonymousClass6(java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            this.val$callbackExecutor = executor;
            this.val$responseOutcomeReceiver = outcomeReceiver;
        }

        @Override // android.app.ondeviceintelligence.IResponseCallback
        public void onSuccess(final android.app.ondeviceintelligence.Content content) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$responseOutcomeReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onResult(r2);
                        }
                    });
                }
            });
        }

        @Override // android.app.ondeviceintelligence.IResponseCallback
        public void onFailure(final int i, final java.lang.String str, final android.os.PersistableBundle persistableBundle) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$responseOutcomeReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$6$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onError(new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException(r2, r3, r4));
                        }
                    });
                }
            });
        }
    }

    public void processRequest(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.CancellationSignal cancellationSignal, android.app.ondeviceintelligence.ProcessingSignal processingSignal, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException> outcomeReceiver) {
        android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal;
        android.os.ICancellationSignal iCancellationSignal;
        try {
            android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass6 anonymousClass6 = new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass6(executor, outcomeReceiver);
            if (processingSignal == null) {
                iProcessingSignal = null;
            } else {
                android.app.ondeviceintelligence.IProcessingSignal createTransport = android.app.ondeviceintelligence.ProcessingSignal.createTransport();
                processingSignal.setRemote(createTransport);
                iProcessingSignal = createTransport;
            }
            if (cancellationSignal == null) {
                iCancellationSignal = null;
            } else {
                android.os.ICancellationSignal createTransport2 = android.os.CancellationSignal.createTransport();
                cancellationSignal.setRemote(createTransport2);
                iCancellationSignal = createTransport2;
            }
            this.mService.processRequest(feature, content, i, iCancellationSignal, iProcessingSignal, anonymousClass6);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7, reason: invalid class name */
    class AnonymousClass7 extends android.app.ondeviceintelligence.IStreamingResponseCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$callbackExecutor;
        final /* synthetic */ android.app.ondeviceintelligence.StreamingResponseReceiver val$streamingResponseReceiver;

        AnonymousClass7(java.util.concurrent.Executor executor, android.app.ondeviceintelligence.StreamingResponseReceiver streamingResponseReceiver) {
            this.val$callbackExecutor = executor;
            this.val$streamingResponseReceiver = streamingResponseReceiver;
        }

        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onNewContent(final android.app.ondeviceintelligence.Content content) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.app.ondeviceintelligence.StreamingResponseReceiver streamingResponseReceiver = this.val$streamingResponseReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.ondeviceintelligence.StreamingResponseReceiver.this.onNewContent(r2);
                        }
                    });
                }
            });
        }

        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onSuccess(final android.app.ondeviceintelligence.Content content) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.app.ondeviceintelligence.StreamingResponseReceiver streamingResponseReceiver = this.val$streamingResponseReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.ondeviceintelligence.StreamingResponseReceiver.this.onResult(r2);
                        }
                    });
                }
            });
        }

        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onFailure(final int i, final java.lang.String str, final android.os.PersistableBundle persistableBundle) {
            final java.util.concurrent.Executor executor = this.val$callbackExecutor;
            final android.app.ondeviceintelligence.StreamingResponseReceiver streamingResponseReceiver = this.val$streamingResponseReceiver;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.app.ondeviceintelligence.OnDeviceIntelligenceManager$7$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.ondeviceintelligence.StreamingResponseReceiver.this.onError(new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException(r2, r3, r4));
                        }
                    });
                }
            });
        }
    }

    public void processRequestStreaming(android.app.ondeviceintelligence.Feature feature, android.app.ondeviceintelligence.Content content, int i, android.os.CancellationSignal cancellationSignal, android.app.ondeviceintelligence.ProcessingSignal processingSignal, java.util.concurrent.Executor executor, android.app.ondeviceintelligence.StreamingResponseReceiver<android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.Content, android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerProcessingException> streamingResponseReceiver) {
        android.app.ondeviceintelligence.IProcessingSignal iProcessingSignal;
        android.os.ICancellationSignal iCancellationSignal;
        try {
            android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass7 anonymousClass7 = new android.app.ondeviceintelligence.OnDeviceIntelligenceManager.AnonymousClass7(executor, streamingResponseReceiver);
            if (processingSignal == null) {
                iProcessingSignal = null;
            } else {
                android.app.ondeviceintelligence.IProcessingSignal createTransport = android.app.ondeviceintelligence.ProcessingSignal.createTransport();
                processingSignal.setRemote(createTransport);
                iProcessingSignal = createTransport;
            }
            if (cancellationSignal == null) {
                iCancellationSignal = null;
            } else {
                android.os.ICancellationSignal createTransport2 = android.os.CancellationSignal.createTransport();
                cancellationSignal.setRemote(createTransport2);
                iCancellationSignal = createTransport2;
            }
            this.mService.processRequestStreaming(feature, content, i, iCancellationSignal, iProcessingSignal, anonymousClass7);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class OnDeviceIntelligenceManagerException extends java.lang.Exception {
        public static final int ON_DEVICE_INTELLIGENCE_SERVICE_UNAVAILABLE = 1000;
        private final android.os.PersistableBundle errorParams;
        private final int mErrorCode;

        public OnDeviceIntelligenceManagerException(int i, java.lang.String str, android.os.PersistableBundle persistableBundle) {
            super(str);
            this.mErrorCode = i;
            this.errorParams = persistableBundle;
        }

        public OnDeviceIntelligenceManagerException(int i, android.os.PersistableBundle persistableBundle) {
            this.mErrorCode = i;
            this.errorParams = persistableBundle;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public android.os.PersistableBundle getErrorParams() {
            return this.errorParams;
        }
    }

    public static class OnDeviceIntelligenceManagerProcessingException extends android.app.ondeviceintelligence.OnDeviceIntelligenceManager.OnDeviceIntelligenceManagerException {
        public static final int PROCESSING_ERROR_BAD_DATA = 2;
        public static final int PROCESSING_ERROR_BAD_REQUEST = 3;
        public static final int PROCESSING_ERROR_BUSY = 9;
        public static final int PROCESSING_ERROR_CANCELLED = 7;
        public static final int PROCESSING_ERROR_COMPUTE_ERROR = 5;
        public static final int PROCESSING_ERROR_INTERNAL = 14;
        public static final int PROCESSING_ERROR_IPC_ERROR = 6;
        public static final int PROCESSING_ERROR_NOT_AVAILABLE = 8;
        public static final int PROCESSING_ERROR_REQUEST_NOT_SAFE = 4;
        public static final int PROCESSING_ERROR_REQUEST_TOO_LARGE = 12;
        public static final int PROCESSING_ERROR_RESPONSE_NOT_SAFE = 11;
        public static final int PROCESSING_ERROR_SAFETY_ERROR = 10;
        public static final int PROCESSING_ERROR_SERVICE_UNAVAILABLE = 15;
        public static final int PROCESSING_ERROR_SUSPENDED = 13;
        public static final int PROCESSING_ERROR_UNKNOWN = 1;

        @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
        @interface ProcessingError {
        }

        public OnDeviceIntelligenceManagerProcessingException(int i, java.lang.String str, android.os.PersistableBundle persistableBundle) {
            super(i, str, persistableBundle);
        }

        public OnDeviceIntelligenceManagerProcessingException(int i, android.os.PersistableBundle persistableBundle) {
            super(i, persistableBundle);
        }
    }
}
