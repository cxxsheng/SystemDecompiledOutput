package android.service.ambientcontext;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class AmbientContextDetectionService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.ambientcontext.AmbientContextDetectionService";
    private static final java.lang.String TAG = android.service.ambientcontext.AmbientContextDetectionService.class.getSimpleName();

    public abstract void onQueryServiceStatus(int[] iArr, java.lang.String str, java.util.function.Consumer<android.service.ambientcontext.AmbientContextDetectionServiceStatus> consumer);

    public abstract void onStartDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, java.util.function.Consumer<android.service.ambientcontext.AmbientContextDetectionResult> consumer, java.util.function.Consumer<android.service.ambientcontext.AmbientContextDetectionServiceStatus> consumer2);

    public abstract void onStopDetection(java.lang.String str);

    /* renamed from: android.service.ambientcontext.AmbientContextDetectionService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.ambientcontext.IAmbientContextDetectionService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void startDetection(android.app.ambientcontext.AmbientContextEventRequest ambientContextEventRequest, java.lang.String str, final android.os.RemoteCallback remoteCallback, final android.os.RemoteCallback remoteCallback2) {
            java.util.Objects.requireNonNull(ambientContextEventRequest);
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(remoteCallback);
            java.util.Objects.requireNonNull(remoteCallback2);
            android.service.ambientcontext.AmbientContextDetectionService.this.onStartDetection(ambientContextEventRequest, str, new java.util.function.Consumer() { // from class: android.service.ambientcontext.AmbientContextDetectionService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.ambientcontext.AmbientContextDetectionService.AnonymousClass1.lambda$startDetection$0(android.os.RemoteCallback.this, (android.service.ambientcontext.AmbientContextDetectionResult) obj);
                }
            }, new java.util.function.Consumer() { // from class: android.service.ambientcontext.AmbientContextDetectionService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.ambientcontext.AmbientContextDetectionService.AnonymousClass1.lambda$startDetection$1(android.os.RemoteCallback.this, (android.service.ambientcontext.AmbientContextDetectionServiceStatus) obj);
                }
            });
            android.util.Slog.d(android.service.ambientcontext.AmbientContextDetectionService.TAG, "startDetection " + ambientContextEventRequest);
        }

        static /* synthetic */ void lambda$startDetection$0(android.os.RemoteCallback remoteCallback, android.service.ambientcontext.AmbientContextDetectionResult ambientContextDetectionResult) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(android.service.ambientcontext.AmbientContextDetectionResult.RESULT_RESPONSE_BUNDLE_KEY, ambientContextDetectionResult);
            remoteCallback.sendResult(bundle);
        }

        static /* synthetic */ void lambda$startDetection$1(android.os.RemoteCallback remoteCallback, android.service.ambientcontext.AmbientContextDetectionServiceStatus ambientContextDetectionServiceStatus) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(android.service.ambientcontext.AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, ambientContextDetectionServiceStatus);
            remoteCallback.sendResult(bundle);
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void stopDetection(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            android.service.ambientcontext.AmbientContextDetectionService.this.onStopDetection(str);
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void queryServiceStatus(int[] iArr, java.lang.String str, final android.os.RemoteCallback remoteCallback) {
            java.util.Objects.requireNonNull(iArr);
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(remoteCallback);
            android.service.ambientcontext.AmbientContextDetectionService.this.onQueryServiceStatus(iArr, str, new java.util.function.Consumer() { // from class: android.service.ambientcontext.AmbientContextDetectionService$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.ambientcontext.AmbientContextDetectionService.AnonymousClass1.lambda$queryServiceStatus$2(android.os.RemoteCallback.this, (android.service.ambientcontext.AmbientContextDetectionServiceStatus) obj);
                }
            });
        }

        static /* synthetic */ void lambda$queryServiceStatus$2(android.os.RemoteCallback remoteCallback, android.service.ambientcontext.AmbientContextDetectionServiceStatus ambientContextDetectionServiceStatus) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(android.service.ambientcontext.AmbientContextDetectionServiceStatus.STATUS_RESPONSE_BUNDLE_KEY, ambientContextDetectionServiceStatus);
            remoteCallback.sendResult(bundle);
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new android.service.ambientcontext.AmbientContextDetectionService.AnonymousClass1();
        }
        return null;
    }
}
