package android.se.omapi;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public class SeFrameworkInitializer {
    private static volatile android.se.omapi.SeServiceManager sSeServiceManager;

    private SeFrameworkInitializer() {
    }

    public static void setSeServiceManager(android.se.omapi.SeServiceManager seServiceManager) {
        if (sSeServiceManager != null) {
            throw new java.lang.IllegalStateException("setSeServiceManager called twice!");
        }
        if (seServiceManager == null) {
            throw new java.lang.IllegalArgumentException("seServiceManager must not be null");
        }
        sSeServiceManager = seServiceManager;
    }

    public static android.se.omapi.SeServiceManager getSeServiceManager() {
        return sSeServiceManager;
    }
}
