package android.service.displayhash;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class DisplayHashingService extends android.app.Service {
    public static final java.lang.String EXTRA_INTERVAL_BETWEEN_REQUESTS = "android.service.displayhash.extra.INTERVAL_BETWEEN_REQUESTS";
    public static final java.lang.String EXTRA_VERIFIED_DISPLAY_HASH = "android.service.displayhash.extra.VERIFIED_DISPLAY_HASH";

    @android.annotation.SystemApi
    public static final java.lang.String SERVICE_INTERFACE = "android.service.displayhash.DisplayHashingService";
    private android.os.Handler mHandler;
    private android.service.displayhash.DisplayHashingService.DisplayHashingServiceWrapper mWrapper;

    public abstract void onGenerateDisplayHash(byte[] bArr, android.hardware.HardwareBuffer hardwareBuffer, android.graphics.Rect rect, java.lang.String str, android.view.displayhash.DisplayHashResultCallback displayHashResultCallback);

    public abstract java.util.Map<java.lang.String, android.service.displayhash.DisplayHashParams> onGetDisplayHashAlgorithms();

    public abstract int onGetIntervalBetweenRequestsMillis();

    public abstract android.view.displayhash.VerifiedDisplayHash onVerifyDisplayHash(byte[] bArr, android.view.displayhash.DisplayHash displayHash);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mWrapper = new android.service.displayhash.DisplayHashingService.DisplayHashingServiceWrapper();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return this.mWrapper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyDisplayHash(byte[] bArr, android.view.displayhash.DisplayHash displayHash, android.os.RemoteCallback remoteCallback) {
        android.view.displayhash.VerifiedDisplayHash onVerifyDisplayHash = onVerifyDisplayHash(bArr, displayHash);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(EXTRA_VERIFIED_DISPLAY_HASH, onVerifyDisplayHash);
        remoteCallback.sendResult(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDisplayHashAlgorithms(android.os.RemoteCallback remoteCallback) {
        java.util.Map<java.lang.String, android.service.displayhash.DisplayHashParams> onGetDisplayHashAlgorithms = onGetDisplayHashAlgorithms();
        android.os.Bundle bundle = new android.os.Bundle();
        for (java.util.Map.Entry<java.lang.String, android.service.displayhash.DisplayHashParams> entry : onGetDisplayHashAlgorithms.entrySet()) {
            bundle.putParcelable(entry.getKey(), entry.getValue());
        }
        remoteCallback.sendResult(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDurationBetweenRequestsMillis(android.os.RemoteCallback remoteCallback) {
        int onGetIntervalBetweenRequestsMillis = onGetIntervalBetweenRequestsMillis();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt(EXTRA_INTERVAL_BETWEEN_REQUESTS, onGetIntervalBetweenRequestsMillis);
        remoteCallback.sendResult(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DisplayHashingServiceWrapper extends android.service.displayhash.IDisplayHashingService.Stub {
        private DisplayHashingServiceWrapper() {
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void generateDisplayHash(byte[] bArr, android.hardware.HardwareBuffer hardwareBuffer, android.graphics.Rect rect, java.lang.String str, final android.os.RemoteCallback remoteCallback) {
            android.service.displayhash.DisplayHashingService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: android.service.displayhash.DisplayHashingService$DisplayHashingServiceWrapper$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                    ((android.service.displayhash.DisplayHashingService) obj).onGenerateDisplayHash((byte[]) obj2, (android.hardware.HardwareBuffer) obj3, (android.graphics.Rect) obj4, (java.lang.String) obj5, (android.service.displayhash.DisplayHashingService.DisplayHashingServiceWrapper.AnonymousClass1) obj6);
                }
            }, android.service.displayhash.DisplayHashingService.this, bArr, hardwareBuffer, rect, str, new android.view.displayhash.DisplayHashResultCallback() { // from class: android.service.displayhash.DisplayHashingService.DisplayHashingServiceWrapper.1
                @Override // android.view.displayhash.DisplayHashResultCallback
                public void onDisplayHashResult(android.view.displayhash.DisplayHash displayHash) {
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putParcelable(android.view.displayhash.DisplayHashResultCallback.EXTRA_DISPLAY_HASH, displayHash);
                    remoteCallback.sendResult(bundle);
                }

                @Override // android.view.displayhash.DisplayHashResultCallback
                public void onDisplayHashError(int i) {
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putInt(android.view.displayhash.DisplayHashResultCallback.EXTRA_DISPLAY_HASH_ERROR_CODE, i);
                    remoteCallback.sendResult(bundle);
                }
            }));
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void verifyDisplayHash(byte[] bArr, android.view.displayhash.DisplayHash displayHash, android.os.RemoteCallback remoteCallback) {
            android.service.displayhash.DisplayHashingService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.displayhash.DisplayHashingService$DisplayHashingServiceWrapper$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.displayhash.DisplayHashingService) obj).verifyDisplayHash((byte[]) obj2, (android.view.displayhash.DisplayHash) obj3, (android.os.RemoteCallback) obj4);
                }
            }, android.service.displayhash.DisplayHashingService.this, bArr, displayHash, remoteCallback));
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void getDisplayHashAlgorithms(android.os.RemoteCallback remoteCallback) {
            android.service.displayhash.DisplayHashingService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.displayhash.DisplayHashingService$DisplayHashingServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.displayhash.DisplayHashingService) obj).getDisplayHashAlgorithms((android.os.RemoteCallback) obj2);
                }
            }, android.service.displayhash.DisplayHashingService.this, remoteCallback));
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void getIntervalBetweenRequestsMillis(android.os.RemoteCallback remoteCallback) {
            android.service.displayhash.DisplayHashingService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.displayhash.DisplayHashingService$DisplayHashingServiceWrapper$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.displayhash.DisplayHashingService) obj).getDurationBetweenRequestsMillis((android.os.RemoteCallback) obj2);
                }
            }, android.service.displayhash.DisplayHashingService.this, remoteCallback));
        }
    }
}
