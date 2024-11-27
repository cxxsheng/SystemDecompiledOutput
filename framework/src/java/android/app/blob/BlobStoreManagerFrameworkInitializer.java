package android.app.blob;

/* loaded from: classes.dex */
public class BlobStoreManagerFrameworkInitializer {
    public static void initialize() {
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.BLOB_STORE_SERVICE, android.app.blob.BlobStoreManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder() { // from class: android.app.blob.BlobStoreManagerFrameworkInitializer$$ExternalSyntheticLambda0
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder
            public final java.lang.Object createService(android.content.Context context, android.os.IBinder iBinder) {
                return android.app.blob.BlobStoreManagerFrameworkInitializer.lambda$initialize$0(context, iBinder);
            }
        });
    }

    static /* synthetic */ android.app.blob.BlobStoreManager lambda$initialize$0(android.content.Context context, android.os.IBinder iBinder) {
        return new android.app.blob.BlobStoreManager(context, android.app.blob.IBlobStoreManager.Stub.asInterface(iBinder));
    }
}
