package android.service.dreams;

/* loaded from: classes3.dex */
public final class Sandman {
    private static final java.lang.String TAG = "Sandman";

    private Sandman() {
    }

    public static boolean shouldStartDockApp(android.content.Context context, android.content.Intent intent) {
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(context.getResources().getString(com.android.internal.R.string.config_somnambulatorComponent));
        android.content.ComponentName resolveActivity = intent.resolveActivity(context.getPackageManager());
        return (resolveActivity == null || resolveActivity.equals(unflattenFromString)) ? false : true;
    }

    public static void startDreamByUserRequest(android.content.Context context) {
        startDream(context, false);
    }

    public static void startDreamWhenDockedIfAppropriate(android.content.Context context) {
        if (!isScreenSaverEnabled(context) || !isScreenSaverActivatedOnDock(context)) {
            android.util.Slog.i(TAG, "Dreams currently disabled for docks.");
        } else {
            startDream(context, true);
        }
    }

    private static void startDream(android.content.Context context, boolean z) {
        try {
            android.service.dreams.IDreamManager asInterface = android.service.dreams.IDreamManager.Stub.asInterface(android.os.ServiceManager.getService(android.service.dreams.DreamService.DREAM_SERVICE));
            if (asInterface != null && !asInterface.isDreaming()) {
                if (z) {
                    android.util.Slog.i(TAG, "Activating dream while docked.");
                    ((android.os.PowerManager) context.getSystemService(android.os.PowerManager.class)).wakeUp(android.os.SystemClock.uptimeMillis(), 3, "android.service.dreams:DREAM");
                } else {
                    android.util.Slog.i(TAG, "Activating dream by user request.");
                }
                asInterface.dream();
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Could not start dream when docked.", e);
        }
    }

    private static boolean isScreenSaverEnabled(android.content.Context context) {
        return android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), android.provider.Settings.Secure.SCREENSAVER_ENABLED, context.getResources().getBoolean(com.android.internal.R.bool.config_dreamsEnabledByDefault) ? 1 : 0, -2) != 0;
    }

    private static boolean isScreenSaverActivatedOnDock(android.content.Context context) {
        return android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), android.provider.Settings.Secure.SCREENSAVER_ACTIVATE_ON_DOCK, context.getResources().getBoolean(com.android.internal.R.bool.config_dreamsActivatedOnDockByDefault) ? 1 : 0, -2) != 0;
    }
}
