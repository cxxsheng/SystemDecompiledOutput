package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
class AuthResultCoordinator {
    static final int AUTHENTICATOR_DEFAULT = 0;
    static final int AUTHENTICATOR_PERMANENT_LOCKED = 1;
    static final int AUTHENTICATOR_TIMED_LOCKED = 2;
    static final int AUTHENTICATOR_UNLOCKED = 4;
    private static final java.lang.String TAG = "AuthResultCoordinator";
    private final java.util.Map<java.lang.Integer, java.lang.Integer> mAuthenticatorState = new android.util.ArrayMap();

    AuthResultCoordinator() {
        this.mAuthenticatorState.put(15, 0);
        this.mAuthenticatorState.put(255, 0);
        this.mAuthenticatorState.put(4095, 0);
    }

    private void updateState(int i, java.util.function.IntFunction<java.lang.Integer> intFunction) {
        switch (i) {
            case 15:
                this.mAuthenticatorState.put(15, intFunction.apply(this.mAuthenticatorState.get(15).intValue()));
            case 255:
                this.mAuthenticatorState.put(255, intFunction.apply(this.mAuthenticatorState.get(255).intValue()));
            case 4095:
                this.mAuthenticatorState.put(4095, intFunction.apply(this.mAuthenticatorState.get(4095).intValue()));
                break;
        }
    }

    void authenticatedFor(int i) {
        if (i == 15) {
            updateState(i, new java.util.function.IntFunction() { // from class: com.android.server.biometrics.sensors.AuthResultCoordinator$$ExternalSyntheticLambda2
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i2) {
                    java.lang.Integer lambda$authenticatedFor$0;
                    lambda$authenticatedFor$0 = com.android.server.biometrics.sensors.AuthResultCoordinator.lambda$authenticatedFor$0(i2);
                    return lambda$authenticatedFor$0;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$authenticatedFor$0(int i) {
        return java.lang.Integer.valueOf(i | 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$lockedOutFor$1(int i) {
        return java.lang.Integer.valueOf(i | 1);
    }

    void lockedOutFor(int i) {
        updateState(i, new java.util.function.IntFunction() { // from class: com.android.server.biometrics.sensors.AuthResultCoordinator$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i2) {
                java.lang.Integer lambda$lockedOutFor$1;
                lambda$lockedOutFor$1 = com.android.server.biometrics.sensors.AuthResultCoordinator.lambda$lockedOutFor$1(i2);
                return lambda$lockedOutFor$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$lockOutTimed$2(int i) {
        return java.lang.Integer.valueOf(i | 2);
    }

    void lockOutTimed(int i) {
        updateState(i, new java.util.function.IntFunction() { // from class: com.android.server.biometrics.sensors.AuthResultCoordinator$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i2) {
                java.lang.Integer lambda$lockOutTimed$2;
                lambda$lockOutTimed$2 = com.android.server.biometrics.sensors.AuthResultCoordinator.lambda$lockOutTimed$2(i2);
                return lambda$lockOutTimed$2;
            }
        });
    }

    final java.util.Map<java.lang.Integer, java.lang.Integer> getResult() {
        return java.util.Collections.unmodifiableMap(this.mAuthenticatorState);
    }
}
