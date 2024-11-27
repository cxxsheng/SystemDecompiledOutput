package android.app;

/* loaded from: classes.dex */
public class BackgroundStartPrivileges {
    private final boolean mAllowsBackgroundActivityStarts;
    private final boolean mAllowsBackgroundForegroundServiceStarts;
    private final android.os.IBinder mOriginatingToken;
    public static final android.app.BackgroundStartPrivileges NONE = new android.app.BackgroundStartPrivileges(false, false, null);
    public static final android.app.BackgroundStartPrivileges ALLOW_BAL = new android.app.BackgroundStartPrivileges(true, true, null);
    public static final android.app.BackgroundStartPrivileges ALLOW_FGS = new android.app.BackgroundStartPrivileges(false, true, null);

    private BackgroundStartPrivileges(boolean z, boolean z2, android.os.IBinder iBinder) {
        com.android.internal.util.Preconditions.checkArgument(!z || z2, "backgroundActivityStarts implies bgFgServiceStarts");
        this.mAllowsBackgroundActivityStarts = z;
        this.mAllowsBackgroundForegroundServiceStarts = z2;
        this.mOriginatingToken = iBinder;
    }

    public static android.app.BackgroundStartPrivileges allowBackgroundActivityStarts(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return ALLOW_BAL;
        }
        return new android.app.BackgroundStartPrivileges(true, true, iBinder);
    }

    public android.app.BackgroundStartPrivileges merge(android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        if (backgroundStartPrivileges == NONE || backgroundStartPrivileges == null) {
            return this;
        }
        if (this == NONE) {
            return backgroundStartPrivileges;
        }
        boolean z = allowsBackgroundActivityStarts() || backgroundStartPrivileges.allowsBackgroundActivityStarts();
        boolean z2 = allowsBackgroundFgsStarts() || backgroundStartPrivileges.allowsBackgroundFgsStarts();
        if (this.mOriginatingToken == backgroundStartPrivileges.mOriginatingToken) {
            if (this.mAllowsBackgroundActivityStarts == z && this.mAllowsBackgroundForegroundServiceStarts == z2) {
                return this;
            }
            if (backgroundStartPrivileges.mAllowsBackgroundActivityStarts == z && backgroundStartPrivileges.mAllowsBackgroundForegroundServiceStarts == z2) {
                return backgroundStartPrivileges;
            }
            return new android.app.BackgroundStartPrivileges(z, z2, this.mOriginatingToken);
        }
        if (z) {
            return ALLOW_BAL;
        }
        if (z2) {
            return ALLOW_FGS;
        }
        return NONE;
    }

    public static android.app.BackgroundStartPrivileges merge(java.util.List<android.app.BackgroundStartPrivileges> list) {
        if (list == null || list.isEmpty()) {
            return NONE;
        }
        android.app.BackgroundStartPrivileges backgroundStartPrivileges = list.get(0);
        int size = list.size();
        while (true) {
            int i = size - 1;
            if (size > 1) {
                backgroundStartPrivileges = backgroundStartPrivileges.merge(list.get(i));
                size = i;
            } else {
                return backgroundStartPrivileges;
            }
        }
    }

    public boolean allowsBackgroundActivityStarts() {
        return this.mAllowsBackgroundActivityStarts;
    }

    public boolean allowsBackgroundFgsStarts() {
        return this.mAllowsBackgroundForegroundServiceStarts;
    }

    public boolean allowsAny() {
        return this.mAllowsBackgroundActivityStarts || this.mAllowsBackgroundForegroundServiceStarts;
    }

    public boolean allowsNothing() {
        return !allowsAny();
    }

    public android.os.IBinder getOriginatingToken() {
        return this.mOriginatingToken;
    }

    public java.lang.String toString() {
        if (this == ALLOW_BAL) {
            return "BSP.ALLOW_BAL";
        }
        if (this == ALLOW_FGS) {
            return "BSP.ALLOW_FGS";
        }
        if (this == NONE) {
            return "BSP.NONE";
        }
        return "BackgroundStartPrivileges[allowsBackgroundActivityStarts=" + this.mAllowsBackgroundActivityStarts + ", allowsBackgroundForegroundServiceStarts=" + this.mAllowsBackgroundForegroundServiceStarts + ", originatingToken=" + this.mOriginatingToken + ']';
    }
}
