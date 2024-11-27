package android.app.admin;

/* loaded from: classes.dex */
public class DevicePolicyResourcesManager {
    private final android.content.Context mContext;
    private final android.app.admin.IDevicePolicyManager mService;
    private static java.lang.String TAG = "DevicePolicyResourcesManager";
    private static java.lang.String DISABLE_RESOURCES_UPDATABILITY_FLAG = "disable_resources_updatability";
    private static boolean DEFAULT_DISABLE_RESOURCES_UPDATABILITY = false;

    protected DevicePolicyResourcesManager(android.content.Context context, android.app.admin.IDevicePolicyManager iDevicePolicyManager) {
        this.mContext = context;
        this.mService = iDevicePolicyManager;
    }

    @android.annotation.SystemApi
    public void setDrawables(java.util.Set<android.app.admin.DevicePolicyDrawableResource> set) {
        if (this.mService != null) {
            try {
                this.mService.setDrawables(new java.util.ArrayList(set));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public void resetDrawables(java.util.Set<java.lang.String> set) {
        if (this.mService != null) {
            try {
                this.mService.resetDrawables(new java.util.ArrayList(set));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public android.graphics.drawable.Drawable getDrawable(java.lang.String str, java.lang.String str2, java.util.function.Supplier<android.graphics.drawable.Drawable> supplier) {
        return getDrawable(str, str2, android.app.admin.DevicePolicyResources.UNDEFINED, supplier);
    }

    public android.graphics.drawable.Drawable getDrawable(java.lang.String str, java.lang.String str2, java.lang.String str3, java.util.function.Supplier<android.graphics.drawable.Drawable> supplier) {
        java.util.Objects.requireNonNull(str, "drawableId can't be null");
        java.util.Objects.requireNonNull(str2, "drawableStyle can't be null");
        java.util.Objects.requireNonNull(str3, "drawableSource can't be null");
        java.util.Objects.requireNonNull(supplier, "defaultDrawableLoader can't be null");
        if (str.equals(android.app.admin.DevicePolicyResources.UNDEFINED) || android.provider.DeviceConfig.getBoolean("device_policy_manager", DISABLE_RESOURCES_UPDATABILITY_FLAG, DEFAULT_DISABLE_RESOURCES_UPDATABILITY)) {
            return android.app.admin.ParcelableResource.loadDefaultDrawable(supplier);
        }
        if (this.mService != null) {
            try {
                android.app.admin.ParcelableResource drawable = this.mService.getDrawable(str, str2, str3);
                if (drawable == null) {
                    return android.app.admin.ParcelableResource.loadDefaultDrawable(supplier);
                }
                return drawable.getDrawable(this.mContext, 0, supplier);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error getting the updated drawable from DevicePolicyManagerService.", e);
                return android.app.admin.ParcelableResource.loadDefaultDrawable(supplier);
            }
        }
        return android.app.admin.ParcelableResource.loadDefaultDrawable(supplier);
    }

    public android.graphics.drawable.Drawable getDrawableForDensity(java.lang.String str, java.lang.String str2, int i, java.util.function.Supplier<android.graphics.drawable.Drawable> supplier) {
        return getDrawableForDensity(str, str2, android.app.admin.DevicePolicyResources.UNDEFINED, i, supplier);
    }

    public android.graphics.drawable.Drawable getDrawableForDensity(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.util.function.Supplier<android.graphics.drawable.Drawable> supplier) {
        java.util.Objects.requireNonNull(str, "drawableId can't be null");
        java.util.Objects.requireNonNull(str2, "drawableStyle can't be null");
        java.util.Objects.requireNonNull(str3, "drawableSource can't be null");
        java.util.Objects.requireNonNull(supplier, "defaultDrawableLoader can't be null");
        if (str.equals(android.app.admin.DevicePolicyResources.UNDEFINED) || android.provider.DeviceConfig.getBoolean("device_policy_manager", DISABLE_RESOURCES_UPDATABILITY_FLAG, DEFAULT_DISABLE_RESOURCES_UPDATABILITY)) {
            return android.app.admin.ParcelableResource.loadDefaultDrawable(supplier);
        }
        if (this.mService != null) {
            try {
                android.app.admin.ParcelableResource drawable = this.mService.getDrawable(str, str2, str3);
                if (drawable == null) {
                    return android.app.admin.ParcelableResource.loadDefaultDrawable(supplier);
                }
                return drawable.getDrawable(this.mContext, i, supplier);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error getting the updated drawable from DevicePolicyManagerService.", e);
                return android.app.admin.ParcelableResource.loadDefaultDrawable(supplier);
            }
        }
        return android.app.admin.ParcelableResource.loadDefaultDrawable(supplier);
    }

    public android.graphics.drawable.Icon getDrawableAsIcon(java.lang.String str, java.lang.String str2, java.lang.String str3, android.graphics.drawable.Icon icon) {
        java.util.Objects.requireNonNull(str, "drawableId can't be null");
        java.util.Objects.requireNonNull(str2, "drawableStyle can't be null");
        java.util.Objects.requireNonNull(str3, "drawableSource can't be null");
        java.util.Objects.requireNonNull(icon, "defaultIcon can't be null");
        if (str.equals(android.app.admin.DevicePolicyResources.UNDEFINED) || android.provider.DeviceConfig.getBoolean("device_policy_manager", DISABLE_RESOURCES_UPDATABILITY_FLAG, DEFAULT_DISABLE_RESOURCES_UPDATABILITY)) {
            return icon;
        }
        if (this.mService != null) {
            try {
                android.app.admin.ParcelableResource drawable = this.mService.getDrawable(str, str2, str3);
                if (drawable == null) {
                    return icon;
                }
                return android.graphics.drawable.Icon.createWithResource(drawable.getPackageName(), drawable.getResourceId());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error getting the updated drawable from DevicePolicyManagerService.", e);
                return icon;
            }
        }
        return icon;
    }

    public android.graphics.drawable.Icon getDrawableAsIcon(java.lang.String str, java.lang.String str2, android.graphics.drawable.Icon icon) {
        return getDrawableAsIcon(str, str2, android.app.admin.DevicePolicyResources.UNDEFINED, icon);
    }

    @android.annotation.SystemApi
    public void setStrings(java.util.Set<android.app.admin.DevicePolicyStringResource> set) {
        if (this.mService != null) {
            try {
                this.mService.setStrings(new java.util.ArrayList(set));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public void resetStrings(java.util.Set<java.lang.String> set) {
        if (this.mService != null) {
            try {
                this.mService.resetStrings(new java.util.ArrayList(set));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.lang.String getString(java.lang.String str, java.util.function.Supplier<java.lang.String> supplier) {
        java.util.Objects.requireNonNull(str, "stringId can't be null");
        java.util.Objects.requireNonNull(supplier, "defaultStringLoader can't be null");
        if (str.equals(android.app.admin.DevicePolicyResources.UNDEFINED) || android.provider.DeviceConfig.getBoolean("device_policy_manager", DISABLE_RESOURCES_UPDATABILITY_FLAG, DEFAULT_DISABLE_RESOURCES_UPDATABILITY)) {
            return android.app.admin.ParcelableResource.loadDefaultString(supplier);
        }
        if (this.mService != null) {
            try {
                android.app.admin.ParcelableResource string = this.mService.getString(str);
                if (string == null) {
                    return android.app.admin.ParcelableResource.loadDefaultString(supplier);
                }
                return string.getString(this.mContext, supplier);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error getting the updated string from DevicePolicyManagerService.", e);
                return android.app.admin.ParcelableResource.loadDefaultString(supplier);
            }
        }
        return android.app.admin.ParcelableResource.loadDefaultString(supplier);
    }

    public java.lang.String getString(java.lang.String str, java.util.function.Supplier<java.lang.String> supplier, java.lang.Object... objArr) {
        java.util.Objects.requireNonNull(str, "stringId can't be null");
        java.util.Objects.requireNonNull(supplier, "defaultStringLoader can't be null");
        if (str.equals(android.app.admin.DevicePolicyResources.UNDEFINED) || android.provider.DeviceConfig.getBoolean("device_policy_manager", DISABLE_RESOURCES_UPDATABILITY_FLAG, DEFAULT_DISABLE_RESOURCES_UPDATABILITY)) {
            return android.app.admin.ParcelableResource.loadDefaultString(supplier);
        }
        if (this.mService != null) {
            try {
                android.app.admin.ParcelableResource string = this.mService.getString(str);
                if (string == null) {
                    return android.app.admin.ParcelableResource.loadDefaultString(supplier);
                }
                return string.getString(this.mContext, supplier, objArr);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error getting the updated string from DevicePolicyManagerService.", e);
                return android.app.admin.ParcelableResource.loadDefaultString(supplier);
            }
        }
        return android.app.admin.ParcelableResource.loadDefaultString(supplier);
    }
}
