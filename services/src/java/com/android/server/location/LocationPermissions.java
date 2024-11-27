package com.android.server.location;

/* loaded from: classes2.dex */
public final class LocationPermissions {
    public static final int PERMISSION_COARSE = 1;
    public static final int PERMISSION_FINE = 2;
    public static final int PERMISSION_NONE = 0;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionLevel {
    }

    private LocationPermissions() {
    }

    public static java.lang.String asPermission(int i) {
        switch (i) {
            case 1:
                return "android.permission.ACCESS_COARSE_LOCATION";
            case 2:
                return "android.permission.ACCESS_FINE_LOCATION";
            default:
                throw new java.lang.IllegalArgumentException();
        }
    }

    public static int asAppOp(int i) {
        switch (i) {
            case 1:
                return 0;
            case 2:
                return 1;
            default:
                throw new java.lang.IllegalArgumentException();
        }
    }

    public static void enforceCallingOrSelfLocationPermission(android.content.Context context, int i) {
        enforceLocationPermission(android.os.Binder.getCallingUid(), getPermissionLevel(context, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid()), i);
    }

    public static void enforceLocationPermission(android.content.Context context, int i, int i2, int i3) {
        enforceLocationPermission(i, getPermissionLevel(context, i, i2), i3);
    }

    public static void enforceLocationPermission(int i, int i2, int i3) {
        if (checkLocationPermission(i2, i3)) {
            return;
        }
        if (i3 == 1) {
            throw new java.lang.SecurityException("uid " + i + " does not have android.permission.ACCESS_COARSE_LOCATION or android.permission.ACCESS_FINE_LOCATION.");
        }
        if (i3 == 2) {
            throw new java.lang.SecurityException("uid " + i + " does not have android.permission.ACCESS_FINE_LOCATION.");
        }
    }

    public static void enforceCallingOrSelfBypassPermission(android.content.Context context) {
        enforceBypassPermission(context, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid());
    }

    public static void enforceBypassPermission(android.content.Context context, int i, int i2) {
        if (context.checkPermission("android.permission.LOCATION_BYPASS", i2, i) != 0) {
            throw new java.lang.SecurityException(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID + i + " does not have android.permission.LOCATION_BYPASS.");
        }
    }

    public static boolean checkCallingOrSelfLocationPermission(android.content.Context context, int i) {
        return checkLocationPermission(getCallingOrSelfPermissionLevel(context), i);
    }

    public static boolean checkLocationPermission(android.content.Context context, int i, int i2, int i3) {
        return checkLocationPermission(getPermissionLevel(context, i, i2), i3);
    }

    public static boolean checkLocationPermission(int i, int i2) {
        return i >= i2;
    }

    public static int getCallingOrSelfPermissionLevel(android.content.Context context) {
        return getPermissionLevel(context, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid());
    }

    public static int getPermissionLevel(android.content.Context context, int i, int i2) {
        if (context.checkPermission("android.permission.ACCESS_FINE_LOCATION", i2, i) == 0) {
            return 2;
        }
        if (context.checkPermission("android.permission.ACCESS_COARSE_LOCATION", i2, i) == 0) {
            return 1;
        }
        return 0;
    }
}
