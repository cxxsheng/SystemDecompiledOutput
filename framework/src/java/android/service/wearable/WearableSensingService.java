package android.service.wearable;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class WearableSensingService extends android.app.Service {
    public static final java.lang.String HOTWORD_AUDIO_STREAM_BUNDLE_KEY = "android.app.wearable.HotwordAudioStreamBundleKey";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.wearable.WearableSensingService";
    public static final java.lang.String STATUS_RESPONSE_BUNDLE_KEY = "android.app.wearable.WearableSensingStatusBundleKey";
    private static final java.lang.String TAG = android.service.wearable.WearableSensingService.class.getSimpleName();
    private final android.util.SparseArray<android.service.wearable.WearableSensingDataRequester> mDataRequestObserverIdToRequesterMap = new android.util.SparseArray<>();

    public abstract void onDataProvided(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, java.util.function.Consumer<java.lang.Integer> consumer);

    public abstract void onDataStreamProvided(android.os.ParcelFileDescriptor parcelFileDescriptor, java.util.function.Consumer<java.lang.Integer> consumer);

    public abstract void onQueryServiceStatus(java.util.Set<java.lang.Integer> set, java.lang.String str, java.util.function.Consumer<android.service.ambientcontext.AmbientContextDetectionServiceStatus> consumer);

    public abstract void onStartDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, java.util.function.Consumer<android.service.ambientcontext.AmbientContextDetectionServiceStatus> consumer, java.util.function.Consumer<android.service.ambientcontext.AmbientContextDetectionResult> consumer2);

    public abstract void onStopDetection(java.lang.String str);

    /* renamed from: android.service.wearable.WearableSensingService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.wearable.IWearableSensingService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideSecureConnection(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) {
            java.util.Objects.requireNonNull(parcelFileDescriptor);
            android.service.wearable.WearableSensingService.this.onSecureConnectionProvided(parcelFileDescriptor, android.service.wearable.WearableSensingService.createWearableStatusConsumer(remoteCallback));
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideDataStream(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) {
            java.util.Objects.requireNonNull(parcelFileDescriptor);
            android.service.wearable.WearableSensingService.this.onDataStreamProvided(parcelFileDescriptor, android.service.wearable.WearableSensingService.createWearableStatusConsumer(remoteCallback));
        }

        @Override // android.service.wearable.IWearableSensingService
        public void provideData(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.os.RemoteCallback remoteCallback) {
            java.util.Objects.requireNonNull(persistableBundle);
            android.service.wearable.WearableSensingService.this.onDataProvided(persistableBundle, sharedMemory, android.service.wearable.WearableSensingService.createWearableStatusConsumer(remoteCallback));
        }

        @Override // android.service.wearable.IWearableSensingService
        public void registerDataRequestObserver(int i, android.os.RemoteCallback remoteCallback, int i2, java.lang.String str, android.os.RemoteCallback remoteCallback2) {
            android.service.wearable.WearableSensingDataRequester wearableSensingDataRequester;
            java.util.Objects.requireNonNull(remoteCallback);
            java.util.Objects.requireNonNull(remoteCallback2);
            synchronized (android.service.wearable.WearableSensingService.this.mDataRequestObserverIdToRequesterMap) {
                wearableSensingDataRequester = (android.service.wearable.WearableSensingDataRequester) android.service.wearable.WearableSensingService.this.mDataRequestObserverIdToRequesterMap.get(i2);
                if (wearableSensingDataRequester == null) {
                    wearableSensingDataRequester = android.service.wearable.WearableSensingService.createDataRequester(remoteCallback);
                    android.service.wearable.WearableSensingService.this.mDataRequestObserverIdToRequesterMap.put(i2, wearableSensingDataRequester);
                }
            }
            android.service.wearable.WearableSensingService.this.onDataRequestObserverRegistered(i, str, wearableSensingDataRequester, android.service.wearable.WearableSensingService.createWearableStatusConsumer(remoteCallback2));
        }

        @Override // android.service.wearable.IWearableSensingService
        public void unregisterDataRequestObserver(int i, int i2, java.lang.String str, android.os.RemoteCallback remoteCallback) {
            synchronized (android.service.wearable.WearableSensingService.this.mDataRequestObserverIdToRequesterMap) {
                android.service.wearable.WearableSensingDataRequester wearableSensingDataRequester = (android.service.wearable.WearableSensingDataRequester) android.service.wearable.WearableSensingService.this.mDataRequestObserverIdToRequesterMap.get(i2);
                if (wearableSensingDataRequester == null) {
                    android.util.Slog.w(android.service.wearable.WearableSensingService.TAG, "dataRequestObserverId not found, cannot unregister data request observer.");
                    return;
                }
                android.service.wearable.WearableSensingService.this.mDataRequestObserverIdToRequesterMap.remove(i2);
                android.service.wearable.WearableSensingService.this.onDataRequestObserverUnregistered(i, str, wearableSensingDataRequester, android.service.wearable.WearableSensingService.createWearableStatusConsumer(remoteCallback));
            }
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startHotwordRecognition(final android.os.RemoteCallback remoteCallback, final android.os.RemoteCallback remoteCallback2) {
            android.service.wearable.WearableSensingService.this.onStartHotwordRecognition(new java.util.function.Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.wearable.WearableSensingService.AnonymousClass1.lambda$startHotwordRecognition$0(android.os.RemoteCallback.this, (android.service.voice.HotwordAudioStream) obj);
                }
            }, new java.util.function.Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.wearable.WearableSensingService.AnonymousClass1.lambda$startHotwordRecognition$1(android.os.RemoteCallback.this, (java.lang.Integer) obj);
                }
            });
        }

        static /* synthetic */ void lambda$startHotwordRecognition$0(android.os.RemoteCallback remoteCallback, android.service.voice.HotwordAudioStream hotwordAudioStream) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(android.service.wearable.WearableSensingService.HOTWORD_AUDIO_STREAM_BUNDLE_KEY, hotwordAudioStream);
            remoteCallback.sendResult(bundle);
        }

        static /* synthetic */ void lambda$startHotwordRecognition$1(android.os.RemoteCallback remoteCallback, java.lang.Integer num) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", num.intValue());
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopHotwordRecognition(final android.os.RemoteCallback remoteCallback) {
            android.service.wearable.WearableSensingService.this.onStopHotwordRecognition(new java.util.function.Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.wearable.WearableSensingService.AnonymousClass1.lambda$stopHotwordRecognition$2(android.os.RemoteCallback.this, (java.lang.Integer) obj);
                }
            });
        }

        static /* synthetic */ void lambda$stopHotwordRecognition$2(android.os.RemoteCallback remoteCallback, java.lang.Integer num) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", num.intValue());
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void onValidatedByHotwordDetectionService() {
            android.service.wearable.WearableSensingService.this.onValidatedByHotwordDetectionService();
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopActiveHotwordAudio() {
            android.service.wearable.WearableSensingService.this.onStopHotwordAudioStream();
        }

        @Override // android.service.wearable.IWearableSensingService
        public void startDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, final android.os.RemoteCallback remoteCallback, final android.os.RemoteCallback remoteCallback2) {
            java.util.Objects.requireNonNull(ambientContextEventRequest);
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(remoteCallback);
            java.util.Objects.requireNonNull(remoteCallback2);
            android.service.wearable.WearableSensingService.this.onStartDetection(ambientContextEventRequest, str, new java.util.function.Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.wearable.WearableSensingService.AnonymousClass1.lambda$startDetection$4(android.os.RemoteCallback.this, (android.service.ambientcontext.AmbientContextDetectionServiceStatus) obj);
                }
            }, new java.util.function.Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.wearable.WearableSensingService.AnonymousClass1.lambda$startDetection$3(android.os.RemoteCallback.this, (android.service.ambientcontext.AmbientContextDetectionResult) obj);
                }
            });
            android.util.Slog.d(android.service.wearable.WearableSensingService.TAG, "startDetection " + ambientContextEventRequest);
        }

        static /* synthetic */ void lambda$startDetection$3(android.os.RemoteCallback remoteCallback, android.service.ambientcontext.AmbientContextDetectionResult ambientContextDetectionResult) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(android.service.ambientcontext.AmbientContextDetectionResult.RESULT_RESPONSE_BUNDLE_KEY, ambientContextDetectionResult);
            remoteCallback.sendResult(bundle);
        }

        static /* synthetic */ void lambda$startDetection$4(android.os.RemoteCallback remoteCallback, android.service.ambientcontext.AmbientContextDetectionServiceStatus ambientContextDetectionServiceStatus) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(android.service.ambientcontext.AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, ambientContextDetectionServiceStatus);
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void stopDetection(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            android.service.wearable.WearableSensingService.this.onStopDetection(str);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void queryServiceStatus(int[] iArr, java.lang.String str, final android.os.RemoteCallback remoteCallback) {
            java.util.Objects.requireNonNull(iArr);
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(remoteCallback);
            android.service.wearable.WearableSensingService.this.onQueryServiceStatus(new java.util.HashSet(java.util.Arrays.asList(android.service.wearable.WearableSensingService.intArrayToIntegerArray(iArr))), str, new java.util.function.Consumer() { // from class: android.service.wearable.WearableSensingService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.wearable.WearableSensingService.AnonymousClass1.lambda$queryServiceStatus$5(android.os.RemoteCallback.this, (android.service.ambientcontext.AmbientContextDetectionServiceStatus) obj);
                }
            });
        }

        static /* synthetic */ void lambda$queryServiceStatus$5(android.os.RemoteCallback remoteCallback, android.service.ambientcontext.AmbientContextDetectionServiceStatus ambientContextDetectionServiceStatus) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(android.service.ambientcontext.AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, ambientContextDetectionServiceStatus);
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.wearable.IWearableSensingService
        public void killProcess() {
            android.util.Slog.d(android.service.wearable.WearableSensingService.TAG, "#killProcess");
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new android.service.wearable.WearableSensingService.AnonymousClass1();
        }
        android.util.Slog.w(TAG, "Incorrect service interface, returning null.");
        return null;
    }

    public void onSecureConnectionProvided(android.os.ParcelFileDescriptor parcelFileDescriptor, java.util.function.Consumer<java.lang.Integer> consumer) {
        consumer.accept(6);
    }

    public void onDataRequestObserverRegistered(int i, java.lang.String str, android.service.wearable.WearableSensingDataRequester wearableSensingDataRequester, java.util.function.Consumer<java.lang.Integer> consumer) {
        consumer.accept(6);
    }

    public void onDataRequestObserverUnregistered(int i, java.lang.String str, android.service.wearable.WearableSensingDataRequester wearableSensingDataRequester, java.util.function.Consumer<java.lang.Integer> consumer) {
        consumer.accept(6);
    }

    public void onStartHotwordRecognition(java.util.function.Consumer<android.service.voice.HotwordAudioStream> consumer, java.util.function.Consumer<java.lang.Integer> consumer2) {
        if (android.app.wearable.Flags.enableUnsupportedOperationStatusCode()) {
            consumer2.accept(6);
        }
    }

    public void onStopHotwordRecognition(java.util.function.Consumer<java.lang.Integer> consumer) {
        if (android.app.wearable.Flags.enableUnsupportedOperationStatusCode()) {
            consumer.accept(6);
        }
    }

    public void onValidatedByHotwordDetectionService() {
    }

    public void onStopHotwordAudioStream() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.Integer[] intArrayToIntegerArray(int[] iArr) {
        java.lang.Integer[] numArr = new java.lang.Integer[iArr.length];
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            numArr[i2] = java.lang.Integer.valueOf(iArr[i]);
            i++;
            i2++;
        }
        return numArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.service.wearable.WearableSensingDataRequester createDataRequester(final android.os.RemoteCallback remoteCallback) {
        return new android.service.wearable.WearableSensingDataRequester() { // from class: android.service.wearable.WearableSensingService$$ExternalSyntheticLambda2
            @Override // android.service.wearable.WearableSensingDataRequester
            public final void requestData(android.app.wearable.WearableSensingDataRequest wearableSensingDataRequest, java.util.function.Consumer consumer) {
                android.service.wearable.WearableSensingService.lambda$createDataRequester$1(android.os.RemoteCallback.this, wearableSensingDataRequest, consumer);
            }
        };
    }

    static /* synthetic */ void lambda$createDataRequester$1(android.os.RemoteCallback remoteCallback, android.app.wearable.WearableSensingDataRequest wearableSensingDataRequest, final java.util.function.Consumer consumer) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(android.app.wearable.WearableSensingDataRequest.REQUEST_BUNDLE_KEY, wearableSensingDataRequest);
        bundle.putParcelable(android.app.wearable.WearableSensingDataRequest.REQUEST_STATUS_CALLBACK_BUNDLE_KEY, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.service.wearable.WearableSensingService$$ExternalSyntheticLambda0
            @Override // android.os.RemoteCallback.OnResultListener
            public final void onResult(android.os.Bundle bundle2) {
                consumer.accept(java.lang.Integer.valueOf(bundle2.getInt("android.app.wearable.WearableSensingStatusBundleKey")));
            }
        }));
        remoteCallback.sendResult(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.function.Consumer<java.lang.Integer> createWearableStatusConsumer(final android.os.RemoteCallback remoteCallback) {
        return new java.util.function.Consumer() { // from class: android.service.wearable.WearableSensingService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.service.wearable.WearableSensingService.lambda$createWearableStatusConsumer$2(android.os.RemoteCallback.this, (java.lang.Integer) obj);
            }
        };
    }

    static /* synthetic */ void lambda$createWearableStatusConsumer$2(android.os.RemoteCallback remoteCallback, java.lang.Integer num) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("android.app.wearable.WearableSensingStatusBundleKey", num.intValue());
        remoteCallback.sendResult(bundle);
    }
}
