package android.content.rollback;

/* loaded from: classes.dex */
public class RollbackManagerFrameworkInitializer {
    private RollbackManagerFrameworkInitializer() {
    }

    public static void initialize() {
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.ROLLBACK_SERVICE, android.content.rollback.RollbackManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder() { // from class: android.content.rollback.RollbackManagerFrameworkInitializer$$ExternalSyntheticLambda0
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder
            public final java.lang.Object createService(android.content.Context context, android.os.IBinder iBinder) {
                return android.content.rollback.RollbackManagerFrameworkInitializer.lambda$initialize$0(context, iBinder);
            }
        });
    }

    static /* synthetic */ android.content.rollback.RollbackManager lambda$initialize$0(android.content.Context context, android.os.IBinder iBinder) {
        return new android.content.rollback.RollbackManager(context, android.content.rollback.IRollbackManager.Stub.asInterface(iBinder));
    }
}
