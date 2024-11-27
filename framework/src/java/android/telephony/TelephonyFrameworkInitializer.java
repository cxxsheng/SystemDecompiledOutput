package android.telephony;

/* loaded from: classes3.dex */
public class TelephonyFrameworkInitializer {
    private static volatile android.os.TelephonyServiceManager sTelephonyServiceManager;

    private TelephonyFrameworkInitializer() {
    }

    public static void setTelephonyServiceManager(android.os.TelephonyServiceManager telephonyServiceManager) {
        com.android.internal.util.Preconditions.checkState(sTelephonyServiceManager == null, "setTelephonyServiceManager called twice!");
        sTelephonyServiceManager = (android.os.TelephonyServiceManager) com.android.internal.util.Preconditions.checkNotNull(telephonyServiceManager);
    }

    private static boolean hasSystemFeature(android.content.Context context, java.lang.String str) {
        if (com.android.internal.telephony.flags.Flags.minimalTelephonyManagersConditionalOnFeatures()) {
            return context.getPackageManager().hasSystemFeature(str);
        }
        return true;
    }

    public static void registerServiceWrappers() {
        android.app.SystemServiceRegistry.registerContextAwareService("phone", android.telephony.TelephonyManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda0
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return android.telephony.TelephonyFrameworkInitializer.lambda$registerServiceWrappers$0(context);
            }
        });
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.TELEPHONY_SUBSCRIPTION_SERVICE, android.telephony.SubscriptionManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda1
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return android.telephony.TelephonyFrameworkInitializer.lambda$registerServiceWrappers$1(context);
            }
        });
        android.app.SystemServiceRegistry.registerContextAwareService("carrier_config", android.telephony.CarrierConfigManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda2
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return android.telephony.TelephonyFrameworkInitializer.lambda$registerServiceWrappers$2(context);
            }
        });
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.EUICC_SERVICE, android.telephony.euicc.EuiccManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda3
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return android.telephony.TelephonyFrameworkInitializer.lambda$registerServiceWrappers$3(context);
            }
        });
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.EUICC_CARD_SERVICE, android.telephony.euicc.EuiccCardManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda4
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return android.telephony.TelephonyFrameworkInitializer.lambda$registerServiceWrappers$4(context);
            }
        });
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.TELEPHONY_IMS_SERVICE, android.telephony.ims.ImsManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda5
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return android.telephony.TelephonyFrameworkInitializer.lambda$registerServiceWrappers$5(context);
            }
        });
        android.app.SystemServiceRegistry.registerContextAwareService(android.content.Context.SMS_SERVICE, android.telephony.SmsManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda6
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return android.telephony.TelephonyFrameworkInitializer.lambda$registerServiceWrappers$6(context);
            }
        });
        android.app.SystemServiceRegistry.registerContextAwareService("satellite", android.telephony.satellite.SatelliteManager.class, new android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder() { // from class: android.telephony.TelephonyFrameworkInitializer$$ExternalSyntheticLambda7
            @Override // android.app.SystemServiceRegistry.ContextAwareServiceProducerWithoutBinder
            public final java.lang.Object createService(android.content.Context context) {
                return android.telephony.TelephonyFrameworkInitializer.lambda$registerServiceWrappers$7(context);
            }
        });
    }

    static /* synthetic */ android.telephony.TelephonyManager lambda$registerServiceWrappers$0(android.content.Context context) {
        return new android.telephony.TelephonyManager(context);
    }

    static /* synthetic */ android.telephony.SubscriptionManager lambda$registerServiceWrappers$1(android.content.Context context) {
        return new android.telephony.SubscriptionManager(context);
    }

    static /* synthetic */ android.telephony.CarrierConfigManager lambda$registerServiceWrappers$2(android.content.Context context) {
        if (hasSystemFeature(context, android.content.pm.PackageManager.FEATURE_TELEPHONY_SUBSCRIPTION)) {
            return new android.telephony.CarrierConfigManager(context);
        }
        return null;
    }

    static /* synthetic */ android.telephony.euicc.EuiccManager lambda$registerServiceWrappers$3(android.content.Context context) {
        if (hasSystemFeature(context, android.content.pm.PackageManager.FEATURE_TELEPHONY_EUICC)) {
            return new android.telephony.euicc.EuiccManager(context);
        }
        return null;
    }

    static /* synthetic */ android.telephony.euicc.EuiccCardManager lambda$registerServiceWrappers$4(android.content.Context context) {
        if (hasSystemFeature(context, android.content.pm.PackageManager.FEATURE_TELEPHONY_EUICC)) {
            return new android.telephony.euicc.EuiccCardManager(context);
        }
        return null;
    }

    static /* synthetic */ android.telephony.ims.ImsManager lambda$registerServiceWrappers$5(android.content.Context context) {
        if (hasSystemFeature(context, android.content.pm.PackageManager.FEATURE_TELEPHONY_IMS)) {
            return new android.telephony.ims.ImsManager(context);
        }
        return null;
    }

    static /* synthetic */ android.telephony.SmsManager lambda$registerServiceWrappers$6(android.content.Context context) {
        if (hasSystemFeature(context, android.content.pm.PackageManager.FEATURE_TELEPHONY_MESSAGING)) {
            return android.telephony.SmsManager.getSmsManagerForContextAndSubscriptionId(context, Integer.MAX_VALUE);
        }
        return null;
    }

    static /* synthetic */ android.telephony.satellite.SatelliteManager lambda$registerServiceWrappers$7(android.content.Context context) {
        if (hasSystemFeature(context, android.content.pm.PackageManager.FEATURE_TELEPHONY_SATELLITE)) {
            return new android.telephony.satellite.SatelliteManager(context);
        }
        return null;
    }

    public static android.os.TelephonyServiceManager getTelephonyServiceManager() {
        return sTelephonyServiceManager;
    }
}
