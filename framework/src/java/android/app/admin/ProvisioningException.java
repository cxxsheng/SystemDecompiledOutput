package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class ProvisioningException extends android.util.AndroidException {
    public static final int ERROR_ADMIN_PACKAGE_INSTALLATION_FAILED = 3;
    public static final int ERROR_PRE_CONDITION_FAILED = 1;
    public static final int ERROR_PROFILE_CREATION_FAILED = 2;
    public static final int ERROR_REMOVE_NON_REQUIRED_APPS_FAILED = 6;
    public static final int ERROR_SETTING_PROFILE_OWNER_FAILED = 4;
    public static final int ERROR_SET_DEVICE_OWNER_FAILED = 7;
    public static final int ERROR_STARTING_PROFILE_FAILED = 5;
    public static final int ERROR_UNKNOWN = 0;
    private final int mProvisioningError;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProvisioningError {
    }

    public ProvisioningException(java.lang.Exception exc, int i) {
        this(exc, i, null);
    }

    public ProvisioningException(java.lang.Exception exc, int i, java.lang.String str) {
        super(str, exc);
        this.mProvisioningError = i;
    }

    public int getProvisioningError() {
        return this.mProvisioningError;
    }
}
