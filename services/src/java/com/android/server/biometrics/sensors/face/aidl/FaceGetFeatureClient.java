package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class FaceGetFeatureClient extends com.android.server.biometrics.sensors.HalClientMonitor<com.android.server.biometrics.sensors.face.aidl.AidlSession> implements com.android.server.biometrics.sensors.ErrorConsumer {
    private static final java.lang.String TAG = "FaceGetFeatureClient";
    private final int mFeature;
    private final int mUserId;

    public FaceGetFeatureClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, int i3) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, 0, i2, biometricLogger, biometricContext);
        this.mUserId = i;
        this.mFeature = i3;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
        this.mCallback.onClientFinished(this, false);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            android.hardware.biometrics.face.ISession session = getFreshDaemon().getSession();
            if (session instanceof com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter) {
                ((com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter) session).setFeature(this.mFeature);
            }
            session.getFeatures();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to getFeature", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 9;
    }

    public void onFeatureGet(boolean z, byte[] bArr) {
        try {
            java.util.HashMap<java.lang.Integer, java.lang.Boolean> featureMap = getFeatureMap();
            int[] iArr = new int[featureMap.size()];
            boolean[] zArr = new boolean[featureMap.size()];
            for (byte b : bArr) {
                featureMap.put(java.lang.Integer.valueOf(com.android.server.biometrics.sensors.face.aidl.AidlConversionUtils.convertAidlToFrameworkFeature(b)), true);
            }
            int i = 0;
            for (java.util.Map.Entry<java.lang.Integer, java.lang.Boolean> entry : featureMap.entrySet()) {
                iArr[i] = entry.getKey().intValue();
                zArr[i] = entry.getValue().booleanValue();
                i++;
            }
            boolean booleanValue = featureMap.get(1).booleanValue();
            android.util.Slog.d(TAG, "Updating attention value for user: " + this.mUserId + " to value: " + booleanValue);
            android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "face_unlock_attention_required", booleanValue ? 1 : 0, this.mUserId);
            getListener().onFeatureGet(z, iArr, zArr);
            this.mCallback.onClientFinished(this, true);
        } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "exception", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @android.annotation.NonNull
    private java.util.HashMap<java.lang.Integer, java.lang.Boolean> getFeatureMap() {
        java.util.HashMap<java.lang.Integer, java.lang.Boolean> hashMap = new java.util.HashMap<>();
        hashMap.put(1, false);
        return hashMap;
    }

    @Override // com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        try {
            getListener().onFeatureGet(false, new int[0], new boolean[0]);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
        this.mCallback.onClientFinished(this, false);
    }
}
