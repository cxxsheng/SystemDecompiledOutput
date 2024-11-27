package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class InternalCleanupClient<S extends android.hardware.biometrics.BiometricAuthenticator.Identifier, T> extends com.android.server.biometrics.sensors.HalClientMonitor<T> implements com.android.server.biometrics.sensors.EnumerateConsumer, com.android.server.biometrics.sensors.RemovalConsumer, com.android.server.biometrics.sensors.EnrollmentModifier {
    private static final java.lang.String TAG = "Biometrics/InternalCleanupClient";
    private final java.util.Map<java.lang.Integer, java.lang.Long> mAuthenticatorIds;
    private final com.android.server.biometrics.sensors.BiometricUtils<S> mBiometricUtils;
    private com.android.server.biometrics.sensors.BaseClientMonitor mCurrentTask;
    private final com.android.server.biometrics.sensors.ClientMonitorCallback mEnumerateCallback;
    private boolean mFavorHalEnrollments;
    private final boolean mHasEnrollmentsBeforeStarting;
    private final com.android.server.biometrics.sensors.ClientMonitorCallback mRemoveCallback;
    private final java.util.ArrayList<com.android.server.biometrics.sensors.InternalCleanupClient.UserTemplate> mUnknownHALTemplates;

    protected abstract com.android.server.biometrics.sensors.InternalEnumerateClient<T> getEnumerateClient(android.content.Context context, java.util.function.Supplier<T> supplier, android.os.IBinder iBinder, int i, java.lang.String str, java.util.List<S> list, com.android.server.biometrics.sensors.BiometricUtils<S> biometricUtils, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext);

    protected abstract com.android.server.biometrics.sensors.RemovalClient<S, T> getRemovalClient(android.content.Context context, java.util.function.Supplier<T> supplier, android.os.IBinder iBinder, int i, int i2, java.lang.String str, com.android.server.biometrics.sensors.BiometricUtils<S> biometricUtils, int i3, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, java.util.Map<java.lang.Integer, java.lang.Long> map);

    private static final class UserTemplate {
        final android.hardware.biometrics.BiometricAuthenticator.Identifier mIdentifier;
        final int mUserId;

        UserTemplate(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, int i) {
            this.mIdentifier = identifier;
            this.mUserId = i;
        }
    }

    protected InternalCleanupClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<T> supplier, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<S> biometricUtils, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map) {
        super(context, supplier, null, null, i, str, 0, i2, biometricLogger, biometricContext);
        this.mUnknownHALTemplates = new java.util.ArrayList<>();
        this.mFavorHalEnrollments = false;
        this.mEnumerateCallback = new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.InternalCleanupClient.1
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                java.util.List<android.hardware.biometrics.BiometricAuthenticator.Identifier> unknownHALTemplates = ((com.android.server.biometrics.sensors.InternalEnumerateClient) com.android.server.biometrics.sensors.InternalCleanupClient.this.mCurrentTask).getUnknownHALTemplates();
                android.util.Slog.d(com.android.server.biometrics.sensors.InternalCleanupClient.TAG, "Enumerate onClientFinished: " + baseClientMonitor + ", success: " + z);
                if (!unknownHALTemplates.isEmpty()) {
                    android.util.Slog.w(com.android.server.biometrics.sensors.InternalCleanupClient.TAG, "Adding " + unknownHALTemplates.size() + " templates for deletion");
                }
                java.util.Iterator<android.hardware.biometrics.BiometricAuthenticator.Identifier> it = unknownHALTemplates.iterator();
                while (it.hasNext()) {
                    com.android.server.biometrics.sensors.InternalCleanupClient.this.mUnknownHALTemplates.add(new com.android.server.biometrics.sensors.InternalCleanupClient.UserTemplate(it.next(), com.android.server.biometrics.sensors.InternalCleanupClient.this.mCurrentTask.getTargetUserId()));
                }
                if (!com.android.server.biometrics.sensors.InternalCleanupClient.this.mUnknownHALTemplates.isEmpty()) {
                    if (com.android.server.biometrics.sensors.InternalCleanupClient.this.mFavorHalEnrollments && android.os.Build.isDebuggable()) {
                        try {
                            java.util.Iterator it2 = com.android.server.biometrics.sensors.InternalCleanupClient.this.mUnknownHALTemplates.iterator();
                            while (it2.hasNext()) {
                                com.android.server.biometrics.sensors.InternalCleanupClient.UserTemplate userTemplate = (com.android.server.biometrics.sensors.InternalCleanupClient.UserTemplate) it2.next();
                                android.util.Slog.i(com.android.server.biometrics.sensors.InternalCleanupClient.TAG, "Adding unknown HAL template: " + userTemplate.mIdentifier.getBiometricId());
                                com.android.server.biometrics.sensors.InternalCleanupClient.this.onAddUnknownTemplate(userTemplate.mUserId, userTemplate.mIdentifier);
                            }
                            return;
                        } finally {
                            com.android.server.biometrics.sensors.InternalCleanupClient.this.mCallback.onClientFinished(com.android.server.biometrics.sensors.InternalCleanupClient.this, z);
                        }
                    }
                    com.android.server.biometrics.sensors.InternalCleanupClient.this.startCleanupUnknownHalTemplates();
                }
            }
        };
        this.mRemoveCallback = new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.InternalCleanupClient.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                android.util.Slog.d(com.android.server.biometrics.sensors.InternalCleanupClient.TAG, "Remove onClientFinished: " + baseClientMonitor + ", success: " + z);
                if (com.android.server.biometrics.sensors.InternalCleanupClient.this.mUnknownHALTemplates.isEmpty()) {
                    com.android.server.biometrics.sensors.InternalCleanupClient.this.mCallback.onClientFinished(com.android.server.biometrics.sensors.InternalCleanupClient.this, z);
                } else {
                    com.android.server.biometrics.sensors.InternalCleanupClient.this.startCleanupUnknownHalTemplates();
                }
            }
        };
        this.mBiometricUtils = biometricUtils;
        this.mAuthenticatorIds = map;
        this.mHasEnrollmentsBeforeStarting = !biometricUtils.getBiometricsForUser(context, i).isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCleanupUnknownHalTemplates() {
        android.util.Slog.d(TAG, "startCleanupUnknownHalTemplates, size: " + this.mUnknownHALTemplates.size());
        com.android.server.biometrics.sensors.InternalCleanupClient.UserTemplate userTemplate = this.mUnknownHALTemplates.get(0);
        this.mUnknownHALTemplates.remove(userTemplate);
        this.mCurrentTask = getRemovalClient(getContext(), this.mLazyDaemon, getToken(), userTemplate.mIdentifier.getBiometricId(), userTemplate.mUserId, getContext().getPackageName(), this.mBiometricUtils, getSensorId(), getLogger(), getBiometricContext(), this.mAuthenticatorIds);
        getLogger().logUnknownEnrollmentInHal();
        this.mCurrentTask.start(this.mRemoveCallback);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        java.util.List<S> biometricsForUser = this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId());
        this.mCurrentTask = getEnumerateClient(getContext(), this.mLazyDaemon, getToken(), getTargetUserId(), getOwnerString(), biometricsForUser, this.mBiometricUtils, getSensorId(), getLogger(), getBiometricContext());
        android.util.Slog.d(TAG, "Starting enumerate: " + this.mCurrentTask + " enrolledList size:" + biometricsForUser.size());
        this.mCurrentTask.start(this.mEnumerateCallback);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
    }

    @Override // com.android.server.biometrics.sensors.RemovalConsumer
    public void onRemoved(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, int i) {
        if (!(this.mCurrentTask instanceof com.android.server.biometrics.sensors.RemovalClient)) {
            android.util.Slog.e(TAG, "onRemoved received during client: " + this.mCurrentTask.getClass().getSimpleName());
            return;
        }
        ((com.android.server.biometrics.sensors.RemovalClient) this.mCurrentTask).onRemoved(identifier, i);
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public boolean hasEnrollmentStateChanged() {
        return (this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).isEmpty() ^ true) != this.mHasEnrollmentsBeforeStarting;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public boolean hasEnrollments() {
        return !this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).isEmpty();
    }

    @Override // com.android.server.biometrics.sensors.EnumerateConsumer
    public void onEnumerationResult(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, int i) {
        if (!(this.mCurrentTask instanceof com.android.server.biometrics.sensors.InternalEnumerateClient)) {
            android.util.Slog.e(TAG, "onEnumerationResult received during client: " + this.mCurrentTask.getClass().getSimpleName());
            return;
        }
        android.util.Slog.d(TAG, "onEnumerated, remaining: " + i);
        ((com.android.server.biometrics.sensors.EnumerateConsumer) this.mCurrentTask).onEnumerationResult(identifier, i);
    }

    public void setFavorHalEnrollments() {
        this.mFavorHalEnrollments = true;
    }

    protected void onAddUnknownTemplate(int i, @android.annotation.NonNull android.hardware.biometrics.BiometricAuthenticator.Identifier identifier) {
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 7;
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.biometrics.sensors.InternalEnumerateClient<T> getCurrentEnumerateClient() {
        return (com.android.server.biometrics.sensors.InternalEnumerateClient) this.mCurrentTask;
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.biometrics.sensors.RemovalClient<S, T> getCurrentRemoveClient() {
        return (com.android.server.biometrics.sensors.RemovalClient) this.mCurrentTask;
    }

    @com.android.internal.annotations.VisibleForTesting
    public java.util.ArrayList<com.android.server.biometrics.sensors.InternalCleanupClient.UserTemplate> getUnknownHALTemplates() {
        return this.mUnknownHALTemplates;
    }
}
