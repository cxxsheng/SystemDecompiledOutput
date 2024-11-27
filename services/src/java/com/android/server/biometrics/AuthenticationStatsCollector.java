package com.android.server.biometrics;

/* loaded from: classes.dex */
public class AuthenticationStatsCollector {
    private static final int AUTHENTICATION_UPLOAD_INTERVAL = 50;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAXIMUM_ENROLLMENT_NOTIFICATIONS = 1;
    private static final int MINIMUM_ATTEMPTS = 150;
    private static final java.lang.String TAG = "AuthenticationStatsCollector";

    @android.annotation.NonNull
    private com.android.server.biometrics.AuthenticationStatsPersister mAuthenticationStatsPersister;

    @android.annotation.NonNull
    private com.android.server.biometrics.sensors.BiometricNotification mBiometricNotification;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private final boolean mEnabled;

    @android.annotation.Nullable
    private final android.hardware.face.FaceManager mFaceManager;

    @android.annotation.Nullable
    private final android.hardware.fingerprint.FingerprintManager mFingerprintManager;
    private final int mModality;

    @android.annotation.NonNull
    private final android.content.pm.PackageManager mPackageManager;
    private final float mThreshold;
    private final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.biometrics.AuthenticationStatsCollector.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
            if (intExtra != -10000 && intent.getAction().equals("android.intent.action.USER_REMOVED")) {
                android.util.Slog.d(com.android.server.biometrics.AuthenticationStatsCollector.TAG, "Removing data for user: " + intExtra);
                com.android.server.biometrics.AuthenticationStatsCollector.this.onUserRemoved(intExtra);
            }
        }
    };

    @android.annotation.NonNull
    private final java.util.Map<java.lang.Integer, com.android.server.biometrics.AuthenticationStats> mUserAuthenticationStatsMap = new java.util.HashMap();

    public AuthenticationStatsCollector(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricNotification biometricNotification) {
        this.mContext = context;
        this.mEnabled = context.getResources().getBoolean(android.R.bool.config_biometricFrrNotificationEnabled);
        this.mThreshold = context.getResources().getFraction(android.R.fraction.config_biometricNotificationFrrThreshold, 1, 1);
        this.mModality = i;
        this.mBiometricNotification = biometricNotification;
        this.mPackageManager = context.getPackageManager();
        this.mFaceManager = (android.hardware.face.FaceManager) this.mContext.getSystemService(android.hardware.face.FaceManager.class);
        this.mFingerprintManager = (android.hardware.fingerprint.FingerprintManager) this.mContext.getSystemService(android.hardware.fingerprint.FingerprintManager.class);
        this.mAuthenticationStatsPersister = new com.android.server.biometrics.AuthenticationStatsPersister(this.mContext);
        initializeUserAuthenticationStatsMap();
        this.mAuthenticationStatsPersister.persistFrrThreshold(this.mThreshold);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        context.registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void initializeUserAuthenticationStatsMap() {
        for (com.android.server.biometrics.AuthenticationStats authenticationStats : this.mAuthenticationStatsPersister.getAllFrrStats(this.mModality)) {
            this.mUserAuthenticationStatsMap.put(java.lang.Integer.valueOf(authenticationStats.getUserId()), authenticationStats);
        }
    }

    public void authenticate(int i, boolean z) {
        if (this.mEnabled && !isSingleModalityDevice()) {
            if (hasEnrolledFace(i) && hasEnrolledFingerprint(i)) {
                return;
            }
            if (this.mUserAuthenticationStatsMap.isEmpty()) {
                initializeUserAuthenticationStatsMap();
            }
            if (!this.mUserAuthenticationStatsMap.containsKey(java.lang.Integer.valueOf(i))) {
                this.mUserAuthenticationStatsMap.put(java.lang.Integer.valueOf(i), new com.android.server.biometrics.AuthenticationStats(i, this.mModality));
            }
            com.android.server.biometrics.AuthenticationStats authenticationStats = this.mUserAuthenticationStatsMap.get(java.lang.Integer.valueOf(i));
            if (authenticationStats.getEnrollmentNotifications() >= 1) {
                return;
            }
            authenticationStats.authenticate(z);
            sendNotificationIfNeeded(i);
            persistDataIfNeeded(i);
        }
    }

    private void sendNotificationIfNeeded(int i) {
        com.android.server.biometrics.AuthenticationStats authenticationStats = this.mUserAuthenticationStatsMap.get(java.lang.Integer.valueOf(i));
        if (authenticationStats.getTotalAttempts() < 150) {
            return;
        }
        if (authenticationStats.getEnrollmentNotifications() >= 1 || authenticationStats.getFrr() < this.mThreshold) {
            authenticationStats.resetData();
            return;
        }
        authenticationStats.resetData();
        boolean hasEnrolledFace = hasEnrolledFace(i);
        boolean hasEnrolledFingerprint = hasEnrolledFingerprint(i);
        if (hasEnrolledFace && !hasEnrolledFingerprint) {
            this.mBiometricNotification.sendFpEnrollNotification(this.mContext);
            authenticationStats.updateNotificationCounter();
        } else if (!hasEnrolledFace && hasEnrolledFingerprint) {
            this.mBiometricNotification.sendFaceEnrollNotification(this.mContext);
            authenticationStats.updateNotificationCounter();
        }
    }

    private void persistDataIfNeeded(int i) {
        com.android.server.biometrics.AuthenticationStats authenticationStats = this.mUserAuthenticationStatsMap.get(java.lang.Integer.valueOf(i));
        if (authenticationStats.getTotalAttempts() % 50 == 0) {
            this.mAuthenticationStatsPersister.persistFrrStats(authenticationStats.getUserId(), authenticationStats.getTotalAttempts(), authenticationStats.getRejectedAttempts(), authenticationStats.getEnrollmentNotifications(), authenticationStats.getModality());
        }
    }

    public void sendFaceReEnrollNotification() {
        this.mBiometricNotification.sendFaceEnrollNotification(this.mContext);
    }

    public void sendFingerprintReEnrollNotification() {
        this.mBiometricNotification.sendFpEnrollNotification(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserRemoved(int i) {
        this.mUserAuthenticationStatsMap.remove(java.lang.Integer.valueOf(i));
        this.mAuthenticationStatsPersister.removeFrrStats(i);
    }

    private boolean isSingleModalityDevice() {
        return (this.mPackageManager.hasSystemFeature("android.hardware.fingerprint") && this.mPackageManager.hasSystemFeature("android.hardware.biometrics.face")) ? false : true;
    }

    private boolean hasEnrolledFace(int i) {
        return this.mFaceManager != null && this.mFaceManager.hasEnrolledTemplates(i);
    }

    private boolean hasEnrolledFingerprint(int i) {
        return this.mFingerprintManager != null && this.mFingerprintManager.hasEnrolledTemplates(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.biometrics.AuthenticationStats getAuthenticationStatsForUser(int i) {
        return this.mUserAuthenticationStatsMap.getOrDefault(java.lang.Integer.valueOf(i), null);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAuthenticationStatsForUser(int i, com.android.server.biometrics.AuthenticationStats authenticationStats) {
        this.mUserAuthenticationStatsMap.put(java.lang.Integer.valueOf(i), authenticationStats);
    }
}
