package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class AuthenticationStateListeners implements android.os.IBinder.DeathRecipient {
    private static final java.lang.String TAG = "AuthenticationStateListeners";

    @android.annotation.NonNull
    private final java.util.concurrent.CopyOnWriteArrayList<android.hardware.biometrics.AuthenticationStateListener> mAuthenticationStateListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    public void registerAuthenticationStateListener(@android.annotation.NonNull android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) {
        this.mAuthenticationStateListeners.add(authenticationStateListener);
        try {
            authenticationStateListener.asBinder().linkToDeath(this, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to link to death", e);
        }
    }

    public void unregisterAuthenticationStateListener(@android.annotation.NonNull android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) {
        this.mAuthenticationStateListeners.remove(authenticationStateListener);
    }

    public void onAuthenticationStarted(int i) {
        java.util.Iterator<android.hardware.biometrics.AuthenticationStateListener> it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                it.next().onAuthenticationStarted(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception in notifying listener that authentication started", e);
            }
        }
    }

    public void onAuthenticationStopped() {
        java.util.Iterator<android.hardware.biometrics.AuthenticationStateListener> it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                it.next().onAuthenticationStopped();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception in notifying listener that authentication stopped", e);
            }
        }
    }

    public void onAuthenticationSucceeded(int i, int i2) {
        java.util.Iterator<android.hardware.biometrics.AuthenticationStateListener> it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                it.next().onAuthenticationSucceeded(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception in notifying listener that authentication succeeded", e);
            }
        }
    }

    public void onAuthenticationFailed(int i, int i2) {
        java.util.Iterator<android.hardware.biometrics.AuthenticationStateListener> it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                it.next().onAuthenticationFailed(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception in notifying listener that authentication failed", e);
            }
        }
    }

    public void onAuthenticationAcquired(android.hardware.biometrics.BiometricSourceType biometricSourceType, int i, int i2) {
        java.util.Iterator<android.hardware.biometrics.AuthenticationStateListener> it = this.mAuthenticationStateListeners.iterator();
        while (it.hasNext()) {
            try {
                it.next().onAuthenticationAcquired(biometricSourceType, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception in notifying listener that authentication stopped", e);
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied(final android.os.IBinder iBinder) {
        android.util.Slog.w(TAG, "Callback binder died: " + iBinder);
        if (this.mAuthenticationStateListeners.removeIf(new java.util.function.Predicate() { // from class: com.android.server.biometrics.sensors.AuthenticationStateListeners$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$binderDied$0;
                lambda$binderDied$0 = com.android.server.biometrics.sensors.AuthenticationStateListeners.lambda$binderDied$0(iBinder, (android.hardware.biometrics.AuthenticationStateListener) obj);
                return lambda$binderDied$0;
            }
        })) {
            android.util.Slog.w(TAG, "Removed dead listener for " + iBinder);
            return;
        }
        android.util.Slog.w(TAG, "No dead listeners found");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$binderDied$0(android.os.IBinder iBinder, android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) {
        return authenticationStateListener.asBinder().equals(iBinder);
    }
}
