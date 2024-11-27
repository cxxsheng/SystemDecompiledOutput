package android.service.resumeonreboot;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class ResumeOnRebootService extends android.app.Service {
    public static final java.lang.String EXCEPTION_KEY = "exception_key";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.resumeonreboot.ResumeOnRebootService";
    public static final java.lang.String UNWRAPPED_BLOB_KEY = "unrwapped_blob_key";
    public static final java.lang.String WRAPPED_BLOB_KEY = "wrapped_blob_key";
    private final android.os.Handler mHandler = com.android.internal.os.BackgroundThread.getHandler();
    private final android.service.resumeonreboot.IResumeOnRebootService mInterface = new android.service.resumeonreboot.ResumeOnRebootService.AnonymousClass1();

    public abstract byte[] onUnwrap(byte[] bArr) throws java.io.IOException;

    public abstract byte[] onWrap(byte[] bArr, long j) throws java.io.IOException;

    /* renamed from: android.service.resumeonreboot.ResumeOnRebootService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.resumeonreboot.IResumeOnRebootService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.resumeonreboot.IResumeOnRebootService
        public void wrapSecret(final byte[] bArr, final long j, final android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            android.service.resumeonreboot.ResumeOnRebootService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.resumeonreboot.ResumeOnRebootService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.resumeonreboot.ResumeOnRebootService.AnonymousClass1.this.lambda$wrapSecret$0(bArr, j, remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wrapSecret$0(byte[] bArr, long j, android.os.RemoteCallback remoteCallback) {
            try {
                byte[] onWrap = android.service.resumeonreboot.ResumeOnRebootService.this.onWrap(bArr, j);
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putByteArray(android.service.resumeonreboot.ResumeOnRebootService.WRAPPED_BLOB_KEY, onWrap);
                remoteCallback.sendResult(bundle);
            } catch (java.lang.Throwable th) {
                android.os.Bundle bundle2 = new android.os.Bundle();
                bundle2.putParcelable(android.service.resumeonreboot.ResumeOnRebootService.EXCEPTION_KEY, new android.os.ParcelableException(th));
                remoteCallback.sendResult(bundle2);
            }
        }

        @Override // android.service.resumeonreboot.IResumeOnRebootService
        public void unwrap(final byte[] bArr, final android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            android.service.resumeonreboot.ResumeOnRebootService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.resumeonreboot.ResumeOnRebootService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.resumeonreboot.ResumeOnRebootService.AnonymousClass1.this.lambda$unwrap$1(bArr, remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$unwrap$1(byte[] bArr, android.os.RemoteCallback remoteCallback) {
            try {
                byte[] onUnwrap = android.service.resumeonreboot.ResumeOnRebootService.this.onUnwrap(bArr);
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putByteArray(android.service.resumeonreboot.ResumeOnRebootService.UNWRAPPED_BLOB_KEY, onUnwrap);
                remoteCallback.sendResult(bundle);
            } catch (java.lang.Throwable th) {
                android.os.Bundle bundle2 = new android.os.Bundle();
                bundle2.putParcelable(android.service.resumeonreboot.ResumeOnRebootService.EXCEPTION_KEY, new android.os.ParcelableException(th));
                remoteCallback.sendResult(bundle2);
            }
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mInterface.asBinder();
    }
}
