package android.app;

/* loaded from: classes.dex */
public class ComponentOptions {
    public static final java.lang.String KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED = "android.pendingIntent.backgroundActivityAllowed";
    public static final java.lang.String KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED_BY_PERMISSION = "android.pendingIntent.backgroundActivityAllowedByPermission";
    private java.lang.Boolean mPendingIntentBalAllowed;
    private boolean mPendingIntentBalAllowedByPermission;

    ComponentOptions() {
        this.mPendingIntentBalAllowed = null;
        this.mPendingIntentBalAllowedByPermission = false;
    }

    ComponentOptions(android.os.Bundle bundle) {
        this.mPendingIntentBalAllowed = null;
        this.mPendingIntentBalAllowedByPermission = false;
        bundle.setDefusable(true);
        if (bundle.containsKey(KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED)) {
            this.mPendingIntentBalAllowed = java.lang.Boolean.valueOf(bundle.getBoolean(KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED));
        }
        setPendingIntentBackgroundActivityLaunchAllowedByPermission(bundle.getBoolean(KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED_BY_PERMISSION, false));
    }

    @java.lang.Deprecated
    public void setPendingIntentBackgroundActivityLaunchAllowed(boolean z) {
        this.mPendingIntentBalAllowed = java.lang.Boolean.valueOf(z);
    }

    @java.lang.Deprecated
    public boolean isPendingIntentBackgroundActivityLaunchAllowed() {
        if (this.mPendingIntentBalAllowed == null) {
            return true;
        }
        return this.mPendingIntentBalAllowed.booleanValue();
    }

    public android.app.ComponentOptions setPendingIntentBackgroundActivityStartMode(int i) {
        switch (i) {
            case 0:
                this.mPendingIntentBalAllowed = null;
                return this;
            case 1:
                this.mPendingIntentBalAllowed = true;
                return this;
            case 2:
                this.mPendingIntentBalAllowed = false;
                return this;
            default:
                throw new java.lang.IllegalArgumentException(i + " is not valid");
        }
    }

    public int getPendingIntentBackgroundActivityStartMode() {
        if (this.mPendingIntentBalAllowed == null) {
            return 0;
        }
        if (this.mPendingIntentBalAllowed.booleanValue()) {
            return 1;
        }
        return 2;
    }

    public void setPendingIntentBackgroundActivityLaunchAllowedByPermission(boolean z) {
        this.mPendingIntentBalAllowedByPermission = z;
    }

    public boolean isPendingIntentBackgroundActivityLaunchAllowedByPermission() {
        return this.mPendingIntentBalAllowedByPermission;
    }

    public android.os.Bundle toBundle() {
        android.os.Bundle bundle = new android.os.Bundle();
        if (this.mPendingIntentBalAllowed != null) {
            bundle.putBoolean(KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED, this.mPendingIntentBalAllowed.booleanValue());
        }
        if (this.mPendingIntentBalAllowedByPermission) {
            bundle.putBoolean(KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED_BY_PERMISSION, this.mPendingIntentBalAllowedByPermission);
        }
        return bundle;
    }

    public static android.app.ComponentOptions fromBundle(android.os.Bundle bundle) {
        if (bundle != null) {
            return new android.app.ComponentOptions(bundle);
        }
        return null;
    }
}
