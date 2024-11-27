package com.android.server.biometrics.log;

/* loaded from: classes.dex */
public interface BiometricContext {
    com.android.server.biometrics.sensors.AuthSessionCoordinator getAuthSessionCoordinator();

    @android.annotation.Nullable
    com.android.server.biometrics.log.BiometricContextSessionInfo getBiometricPromptSessionInfo();

    int getCurrentRotation();

    int getDisplayState();

    int getDockedState();

    @android.hardware.biometrics.IBiometricContextListener.FoldState
    int getFoldState();

    @android.annotation.Nullable
    com.android.server.biometrics.log.BiometricContextSessionInfo getKeyguardEntrySessionInfo();

    boolean isAod();

    boolean isAwake();

    boolean isDisplayOn();

    boolean isHardwareIgnoringTouches();

    @java.lang.Deprecated
    void subscribe(@android.annotation.NonNull com.android.server.biometrics.log.OperationContextExt operationContextExt, @android.annotation.NonNull java.util.function.Consumer<android.hardware.biometrics.common.OperationContext> consumer);

    void subscribe(@android.annotation.NonNull com.android.server.biometrics.log.OperationContextExt operationContextExt, @android.annotation.NonNull java.util.function.Consumer<android.hardware.biometrics.common.OperationContext> consumer, @android.annotation.NonNull java.util.function.Consumer<android.hardware.biometrics.common.OperationContext> consumer2, @android.annotation.Nullable android.hardware.biometrics.AuthenticateOptions authenticateOptions);

    void unsubscribe(@android.annotation.NonNull com.android.server.biometrics.log.OperationContextExt operationContextExt);

    com.android.server.biometrics.log.OperationContextExt updateContext(@android.annotation.NonNull com.android.server.biometrics.log.OperationContextExt operationContextExt, boolean z);

    static com.android.server.biometrics.log.BiometricContext getInstance(@android.annotation.NonNull android.content.Context context) {
        return com.android.server.biometrics.log.BiometricContextProvider.defaultProvider(context);
    }
}
