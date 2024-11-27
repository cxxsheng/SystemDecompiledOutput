package android.app.job;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class JobSchedulerFrameworkInitializer {
    private JobSchedulerFrameworkInitializer() {
    }

    public static void registerServiceWrappers() {
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.JOB_SCHEDULER_SERVICE, android.app.job.JobScheduler.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder() { // from class: android.app.job.JobSchedulerFrameworkInitializer$$ExternalSyntheticLambda0
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder
            public final java.lang.Object createService(android.content.Context context, android.os.IBinder iBinder) {
                return android.app.job.JobSchedulerFrameworkInitializer.lambda$registerServiceWrappers$0(context, iBinder);
            }
        });
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.DEVICE_IDLE_CONTROLLER, android.os.DeviceIdleManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder() { // from class: android.app.job.JobSchedulerFrameworkInitializer$$ExternalSyntheticLambda1
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithBinder
            public final java.lang.Object createService(android.content.Context context, android.os.IBinder iBinder) {
                return android.app.job.JobSchedulerFrameworkInitializer.lambda$registerServiceWrappers$1(context, iBinder);
            }
        });
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.POWER_WHITELIST_MANAGER, android.os.PowerWhitelistManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.app.job.JobSchedulerFrameworkInitializer$$ExternalSyntheticLambda2
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return new android.os.PowerWhitelistManager(context);
            }
        });
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.POWER_EXEMPTION_SERVICE, android.os.PowerExemptionManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.app.job.JobSchedulerFrameworkInitializer$$ExternalSyntheticLambda3
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return new android.os.PowerExemptionManager(context);
            }
        });
        android.app.SystemServiceRegistry.registerStaticService(android.content.Context.RESOURCE_ECONOMY_SERVICE, android.app.tare.EconomyManager.class, new android.app.SystemServiceRegistry.StaticServiceProducerWithBinder() { // from class: android.app.job.JobSchedulerFrameworkInitializer$$ExternalSyntheticLambda4
            @Override // android.app.SystemServiceRegistry.StaticServiceProducerWithBinder
            public final java.lang.Object createService(android.os.IBinder iBinder) {
                return android.app.job.JobSchedulerFrameworkInitializer.lambda$registerServiceWrappers$2(iBinder);
            }
        });
    }

    static /* synthetic */ android.app.job.JobScheduler lambda$registerServiceWrappers$0(android.content.Context context, android.os.IBinder iBinder) {
        return new android.app.JobSchedulerImpl(context, android.app.job.IJobScheduler.Stub.asInterface(iBinder));
    }

    static /* synthetic */ android.os.DeviceIdleManager lambda$registerServiceWrappers$1(android.content.Context context, android.os.IBinder iBinder) {
        return new android.os.DeviceIdleManager(context, android.os.IDeviceIdleController.Stub.asInterface(iBinder));
    }

    static /* synthetic */ android.app.tare.EconomyManager lambda$registerServiceWrappers$2(android.os.IBinder iBinder) {
        return new android.app.tare.EconomyManager(android.app.tare.IEconomyManager.Stub.asInterface(iBinder));
    }
}
