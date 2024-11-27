package com.android.internal.view;

/* loaded from: classes5.dex */
public final class RotationPolicy {
    private static final int CURRENT_ROTATION = -1;
    private static final java.lang.String TAG = "RotationPolicy";
    private static int sNaturalRotation = -1;

    public static abstract class RotationPolicyListener {
        final android.database.ContentObserver mObserver = new android.database.ContentObserver(new android.os.Handler()) { // from class: com.android.internal.view.RotationPolicy.RotationPolicyListener.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, android.net.Uri uri) {
                com.android.internal.view.RotationPolicy.RotationPolicyListener.this.onChange();
            }
        };

        public abstract void onChange();
    }

    private RotationPolicy() {
    }

    public static boolean isRotationSupported(android.content.Context context) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_SENSOR_ACCELEROMETER) && packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_SCREEN_PORTRAIT) && packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_SCREEN_LANDSCAPE) && context.getResources().getBoolean(com.android.internal.R.bool.config_supportAutoRotation);
    }

    public static int getRotationLockOrientation(android.content.Context context) {
        if (isCurrentRotationAllowed(context)) {
            return 0;
        }
        android.util.DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        boolean z = context.getResources().getConfiguration().windowConfiguration.getRotation() % 2 != 0;
        return (z ? displayMetrics.heightPixels : displayMetrics.widthPixels) < (z ? displayMetrics.widthPixels : displayMetrics.heightPixels) ? 1 : 2;
    }

    public static boolean isRotationLockToggleVisible(android.content.Context context) {
        return isRotationSupported(context) && android.provider.Settings.System.getIntForUser(context.getContentResolver(), android.provider.Settings.System.HIDE_ROTATION_LOCK_TOGGLE_FOR_ACCESSIBILITY, 0, -2) == 0;
    }

    public static boolean isRotationLocked(android.content.Context context) {
        return android.provider.Settings.System.getIntForUser(context.getContentResolver(), android.provider.Settings.System.ACCELEROMETER_ROTATION, 0, -2) == 0;
    }

    public static void setRotationLock(android.content.Context context, boolean z, java.lang.String str) {
        int i;
        if (isCurrentRotationAllowed(context) || useCurrentRotationOnRotationLockChange(context)) {
            i = -1;
        } else {
            i = getNaturalRotation();
        }
        setRotationLockAtAngle(context, z, i, str);
    }

    public static void setRotationLockAtAngle(android.content.Context context, boolean z, int i, java.lang.String str) {
        android.provider.Settings.System.putIntForUser(context.getContentResolver(), android.provider.Settings.System.HIDE_ROTATION_LOCK_TOGGLE_FOR_ACCESSIBILITY, 0, -2);
        setRotationLock(z, i, str);
    }

    public static void setRotationLockForAccessibility(android.content.Context context, boolean z, java.lang.String str) {
        android.provider.Settings.System.putIntForUser(context.getContentResolver(), android.provider.Settings.System.HIDE_ROTATION_LOCK_TOGGLE_FOR_ACCESSIBILITY, z ? 1 : 0, -2);
        setRotationLock(z, getNaturalRotation(), str);
    }

    public static boolean isRotationAllowed(int i, int i2, boolean z) {
        if (i2 < 0) {
            if (z) {
                i2 = 15;
            } else {
                i2 = 11;
            }
        }
        switch (i) {
            case 0:
                return (i2 & 1) != 0;
            case 1:
                return (i2 & 2) != 0;
            case 2:
                return (i2 & 4) != 0;
            case 3:
                return (i2 & 8) != 0;
            default:
                return false;
        }
    }

    private static boolean isCurrentRotationAllowed(android.content.Context context) {
        try {
            return isRotationAllowed(android.view.WindowManagerGlobal.getWindowManagerService().getDefaultDisplayRotation(), android.provider.Settings.System.getIntForUser(context.getContentResolver(), android.provider.Settings.System.ACCELEROMETER_ROTATION_ANGLES, -1, -2), context.getResources().getBoolean(com.android.internal.R.bool.config_allowAllRotations));
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Unable to getWindowManagerService.getDefaultDisplayRotation()");
            return false;
        }
    }

    private static boolean useCurrentRotationOnRotationLockChange(android.content.Context context) {
        return context.getResources().getBoolean(com.android.internal.R.bool.config_useCurrentRotationOnRotationLockChange);
    }

    private static void setRotationLock(final boolean z, final int i, final java.lang.String str) {
        android.os.AsyncTask.execute(new java.lang.Runnable() { // from class: com.android.internal.view.RotationPolicy.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    android.view.IWindowManager windowManagerService = android.view.WindowManagerGlobal.getWindowManagerService();
                    if (z) {
                        windowManagerService.freezeRotation(i, str);
                    } else {
                        windowManagerService.thawRotation(str);
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(com.android.internal.view.RotationPolicy.TAG, "Unable to save auto-rotate setting");
                }
            }
        });
    }

    public static void registerRotationPolicyListener(android.content.Context context, com.android.internal.view.RotationPolicy.RotationPolicyListener rotationPolicyListener) {
        registerRotationPolicyListener(context, rotationPolicyListener, android.os.UserHandle.getCallingUserId());
    }

    public static void registerRotationPolicyListener(android.content.Context context, com.android.internal.view.RotationPolicy.RotationPolicyListener rotationPolicyListener, int i) {
        context.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor(android.provider.Settings.System.ACCELEROMETER_ROTATION), false, rotationPolicyListener.mObserver, i);
        context.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor(android.provider.Settings.System.HIDE_ROTATION_LOCK_TOGGLE_FOR_ACCESSIBILITY), false, rotationPolicyListener.mObserver, i);
    }

    public static void unregisterRotationPolicyListener(android.content.Context context, com.android.internal.view.RotationPolicy.RotationPolicyListener rotationPolicyListener) {
        context.getContentResolver().unregisterContentObserver(rotationPolicyListener.mObserver);
    }

    public static int getNaturalRotation() {
        if (sNaturalRotation == -1) {
            sNaturalRotation = getNaturalRotationConfig();
        }
        return sNaturalRotation;
    }

    private static int getNaturalRotationConfig() {
        android.sysprop.SurfaceFlingerProperties.primary_display_orientation_values primary_display_orientation_valuesVar = android.sysprop.SurfaceFlingerProperties.primary_display_orientation_values.ORIENTATION_0;
        java.util.Optional<android.sysprop.SurfaceFlingerProperties.primary_display_orientation_values> primary_display_orientation = android.sysprop.SurfaceFlingerProperties.primary_display_orientation();
        if (primary_display_orientation.isPresent()) {
            primary_display_orientation_valuesVar = primary_display_orientation.get();
        }
        if (primary_display_orientation_valuesVar == android.sysprop.SurfaceFlingerProperties.primary_display_orientation_values.ORIENTATION_90) {
            return 1;
        }
        if (primary_display_orientation_valuesVar == android.sysprop.SurfaceFlingerProperties.primary_display_orientation_values.ORIENTATION_180) {
            return 2;
        }
        if (primary_display_orientation_valuesVar == android.sysprop.SurfaceFlingerProperties.primary_display_orientation_values.ORIENTATION_270) {
            return 3;
        }
        return 0;
    }
}
