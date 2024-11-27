package android.media.soundtrigger;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class SoundTriggerDetectionService extends android.app.Service {
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = android.media.soundtrigger.SoundTriggerDetectionService.class.getSimpleName();
    private android.os.Handler mHandler;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<java.util.UUID, android.media.soundtrigger.ISoundTriggerDetectionServiceClient> mClients = new android.util.ArrayMap<>();

    public abstract void onStopOperation(java.util.UUID uuid, android.os.Bundle bundle, int i);

    @Override // android.app.Service, android.content.ContextWrapper
    protected final void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
        this.mHandler = new android.os.Handler(context.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setClient(java.util.UUID uuid, android.os.Bundle bundle, android.media.soundtrigger.ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) {
        synchronized (this.mLock) {
            this.mClients.put(uuid, iSoundTriggerDetectionServiceClient);
        }
        onConnected(uuid, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeClient(java.util.UUID uuid, android.os.Bundle bundle) {
        synchronized (this.mLock) {
            this.mClients.remove(uuid);
        }
        onDisconnected(uuid, bundle);
    }

    public void onConnected(java.util.UUID uuid, android.os.Bundle bundle) {
    }

    public void onDisconnected(java.util.UUID uuid, android.os.Bundle bundle) {
    }

    public void onGenericRecognitionEvent(java.util.UUID uuid, android.os.Bundle bundle, int i, android.hardware.soundtrigger.SoundTrigger.RecognitionEvent recognitionEvent) {
        operationFinished(uuid, i);
    }

    public void onError(java.util.UUID uuid, android.os.Bundle bundle, int i, int i2) {
        operationFinished(uuid, i);
    }

    public final void operationFinished(java.util.UUID uuid, int i) {
        try {
            synchronized (this.mLock) {
                android.media.soundtrigger.ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient = this.mClients.get(uuid);
                if (iSoundTriggerDetectionServiceClient == null) {
                    android.util.Log.w(LOG_TAG, "operationFinished called, but no client for " + uuid + ". Was this called after onDisconnected?");
                } else {
                    iSoundTriggerDetectionServiceClient.onOpFinished(i);
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "operationFinished, remote exception for client " + uuid, e);
        }
    }

    /* renamed from: android.media.soundtrigger.SoundTriggerDetectionService$1, reason: invalid class name */
    class AnonymousClass1 extends android.media.soundtrigger.ISoundTriggerDetectionService.Stub {
        private final java.lang.Object mBinderLock = new java.lang.Object();
        public final android.util.ArrayMap<java.util.UUID, android.os.Bundle> mParams = new android.util.ArrayMap<>();

        AnonymousClass1() {
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void setClient(android.os.ParcelUuid parcelUuid, android.os.Bundle bundle, android.media.soundtrigger.ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) {
            java.util.UUID uuid = parcelUuid.getUuid();
            synchronized (this.mBinderLock) {
                this.mParams.put(uuid, bundle);
            }
            android.media.soundtrigger.SoundTriggerDetectionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.media.soundtrigger.SoundTriggerDetectionService$1$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.media.soundtrigger.SoundTriggerDetectionService) obj).setClient((java.util.UUID) obj2, (android.os.Bundle) obj3, (android.media.soundtrigger.ISoundTriggerDetectionServiceClient) obj4);
                }
            }, android.media.soundtrigger.SoundTriggerDetectionService.this, uuid, bundle, iSoundTriggerDetectionServiceClient));
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void removeClient(android.os.ParcelUuid parcelUuid) {
            android.os.Bundle remove;
            java.util.UUID uuid = parcelUuid.getUuid();
            synchronized (this.mBinderLock) {
                remove = this.mParams.remove(uuid);
            }
            android.media.soundtrigger.SoundTriggerDetectionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.soundtrigger.SoundTriggerDetectionService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.media.soundtrigger.SoundTriggerDetectionService) obj).removeClient((java.util.UUID) obj2, (android.os.Bundle) obj3);
                }
            }, android.media.soundtrigger.SoundTriggerDetectionService.this, uuid, remove));
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onGenericRecognitionEvent(android.os.ParcelUuid parcelUuid, int i, android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
            android.os.Bundle bundle;
            java.util.UUID uuid = parcelUuid.getUuid();
            synchronized (this.mBinderLock) {
                bundle = this.mParams.get(uuid);
            }
            android.media.soundtrigger.SoundTriggerDetectionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: android.media.soundtrigger.SoundTriggerDetectionService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((android.media.soundtrigger.SoundTriggerDetectionService) obj).onGenericRecognitionEvent((java.util.UUID) obj2, (android.os.Bundle) obj3, ((java.lang.Integer) obj4).intValue(), (android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent) obj5);
                }
            }, android.media.soundtrigger.SoundTriggerDetectionService.this, uuid, bundle, java.lang.Integer.valueOf(i), genericRecognitionEvent));
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onError(android.os.ParcelUuid parcelUuid, int i, int i2) {
            android.os.Bundle bundle;
            java.util.UUID uuid = parcelUuid.getUuid();
            synchronized (this.mBinderLock) {
                bundle = this.mParams.get(uuid);
            }
            android.media.soundtrigger.SoundTriggerDetectionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: android.media.soundtrigger.SoundTriggerDetectionService$1$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((android.media.soundtrigger.SoundTriggerDetectionService) obj).onError((java.util.UUID) obj2, (android.os.Bundle) obj3, ((java.lang.Integer) obj4).intValue(), ((java.lang.Integer) obj5).intValue());
                }
            }, android.media.soundtrigger.SoundTriggerDetectionService.this, uuid, bundle, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
        }

        @Override // android.media.soundtrigger.ISoundTriggerDetectionService
        public void onStopOperation(android.os.ParcelUuid parcelUuid, int i) {
            android.os.Bundle bundle;
            java.util.UUID uuid = parcelUuid.getUuid();
            synchronized (this.mBinderLock) {
                bundle = this.mParams.get(uuid);
            }
            android.media.soundtrigger.SoundTriggerDetectionService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.media.soundtrigger.SoundTriggerDetectionService$1$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.media.soundtrigger.SoundTriggerDetectionService) obj).onStopOperation((java.util.UUID) obj2, (android.os.Bundle) obj3, ((java.lang.Integer) obj4).intValue());
                }
            }, android.media.soundtrigger.SoundTriggerDetectionService.this, uuid, bundle, java.lang.Integer.valueOf(i)));
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.media.soundtrigger.SoundTriggerDetectionService.AnonymousClass1();
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        this.mClients.clear();
        return false;
    }
}
