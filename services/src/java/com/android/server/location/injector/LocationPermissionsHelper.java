package com.android.server.location.injector;

/* loaded from: classes2.dex */
public abstract class LocationPermissionsHelper {
    private final com.android.server.location.injector.AppOpsHelper mAppOps;
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener> mListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    public interface LocationPermissionsListener {
        void onLocationPermissionsChanged(int i);

        void onLocationPermissionsChanged(@android.annotation.Nullable java.lang.String str);
    }

    protected abstract boolean hasPermission(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity);

    public LocationPermissionsHelper(com.android.server.location.injector.AppOpsHelper appOpsHelper) {
        this.mAppOps = appOpsHelper;
        this.mAppOps.addListener(new com.android.server.location.injector.AppOpsHelper.LocationAppOpListener() { // from class: com.android.server.location.injector.LocationPermissionsHelper$$ExternalSyntheticLambda0
            @Override // com.android.server.location.injector.AppOpsHelper.LocationAppOpListener
            public final void onAppOpsChanged(java.lang.String str) {
                com.android.server.location.injector.LocationPermissionsHelper.this.onAppOpsChanged(str);
            }
        });
    }

    protected final void notifyLocationPermissionsChanged(java.lang.String str) {
        java.util.Iterator<com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onLocationPermissionsChanged(str);
        }
    }

    protected final void notifyLocationPermissionsChanged(int i) {
        java.util.Iterator<com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onLocationPermissionsChanged(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAppOpsChanged(java.lang.String str) {
        notifyLocationPermissionsChanged(str);
    }

    public final void addListener(com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener locationPermissionsListener) {
        this.mListeners.add(locationPermissionsListener);
    }

    public final void removeListener(com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener locationPermissionsListener) {
        this.mListeners.remove(locationPermissionsListener);
    }

    public final boolean hasLocationPermissions(int i, android.location.util.identity.CallerIdentity callerIdentity) {
        if (i == 0 || !hasPermission(com.android.server.location.LocationPermissions.asPermission(i), callerIdentity)) {
            return false;
        }
        return this.mAppOps.checkOpNoThrow(com.android.server.location.LocationPermissions.asAppOp(i), callerIdentity);
    }
}
