package com.android.server.biometrics.sensors.iris;

/* loaded from: classes.dex */
public class IrisService extends com.android.server.SystemService {
    private static final java.lang.String TAG = "IrisService";
    private final com.android.server.biometrics.sensors.iris.IrisService.IrisServiceWrapper mServiceWrapper;

    /* JADX INFO: Access modifiers changed from: private */
    final class IrisServiceWrapper extends android.hardware.iris.IIrisService.Stub {
        private IrisServiceWrapper() {
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void registerAuthenticators(@android.annotation.NonNull final java.util.List<android.hardware.biometrics.SensorPropertiesInternal> list) {
            super.registerAuthenticators_enforcePermission();
            com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(com.android.server.biometrics.sensors.iris.IrisService.TAG, 10, true);
            serviceThread.start();
            new android.os.Handler(serviceThread.getLooper()).post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.iris.IrisService$IrisServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.iris.IrisService.IrisServiceWrapper.this.lambda$registerAuthenticators$0(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registerAuthenticators$0(java.util.List list) {
            android.hardware.biometrics.IBiometricService asInterface = android.hardware.biometrics.IBiometricService.Stub.asInterface(android.os.ServiceManager.getService("biometric"));
            java.util.Iterator it = list.iterator();
            while (it.hasNext()) {
                android.hardware.biometrics.SensorPropertiesInternal sensorPropertiesInternal = (android.hardware.biometrics.SensorPropertiesInternal) it.next();
                int i = sensorPropertiesInternal.sensorId;
                try {
                    asInterface.registerAuthenticator(i, 4, com.android.server.biometrics.Utils.propertyStrengthToAuthenticatorStrength(sensorPropertiesInternal.sensorStrength), new com.android.server.biometrics.sensors.iris.IrisAuthenticator(com.android.server.biometrics.sensors.iris.IrisService.this.mServiceWrapper, i));
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.iris.IrisService.TAG, "Remote exception when registering sensorId: " + i);
                }
            }
        }
    }

    public IrisService(@android.annotation.NonNull android.content.Context context) {
        super(context);
        this.mServiceWrapper = new com.android.server.biometrics.sensors.iris.IrisService.IrisServiceWrapper();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("iris", this.mServiceWrapper);
    }
}
