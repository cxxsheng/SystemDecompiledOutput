package com.android.server.pm;

/* loaded from: classes2.dex */
public class DefaultAppProvider {

    @android.annotation.NonNull
    private final java.util.function.Supplier<android.app.role.RoleManager> mRoleManagerSupplier;

    @android.annotation.NonNull
    private final java.util.function.Supplier<com.android.server.pm.UserManagerInternal> mUserManagerInternalSupplier;

    public DefaultAppProvider(@android.annotation.NonNull java.util.function.Supplier<android.app.role.RoleManager> supplier, @android.annotation.NonNull java.util.function.Supplier<com.android.server.pm.UserManagerInternal> supplier2) {
        this.mRoleManagerSupplier = supplier;
        this.mUserManagerInternalSupplier = supplier2;
    }

    @android.annotation.Nullable
    public java.lang.String getDefaultBrowser(int i) {
        return getRoleHolder("android.app.role.BROWSER", i);
    }

    public void setDefaultBrowser(@android.annotation.Nullable final java.lang.String str, int i) {
        android.app.role.RoleManager roleManager = this.mRoleManagerSupplier.get();
        if (roleManager == null) {
            return;
        }
        android.os.UserHandle of = android.os.UserHandle.of(i);
        java.util.concurrent.Executor executor = com.android.server.FgThread.getExecutor();
        java.util.function.Consumer consumer = new java.util.function.Consumer() { // from class: com.android.server.pm.DefaultAppProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.DefaultAppProvider.lambda$setDefaultBrowser$0(str, (java.lang.Boolean) obj);
            }
        };
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (str != null) {
                roleManager.addRoleHolderAsUser("android.app.role.BROWSER", str, 0, of, executor, consumer);
            } else {
                roleManager.clearRoleHoldersAsUser("android.app.role.BROWSER", 0, of, executor, consumer);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setDefaultBrowser$0(java.lang.String str, java.lang.Boolean bool) {
        if (!bool.booleanValue()) {
            android.util.Slog.e("PackageManager", "Failed to set default browser to " + str);
        }
    }

    @android.annotation.Nullable
    public java.lang.String getDefaultDialer(@android.annotation.NonNull int i) {
        return getRoleHolder("android.app.role.DIALER", i);
    }

    @android.annotation.Nullable
    public java.lang.String getDefaultHome(@android.annotation.NonNull int i) {
        return getRoleHolder("android.app.role.HOME", this.mUserManagerInternalSupplier.get().getProfileParentId(i));
    }

    public boolean setDefaultHome(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull java.util.function.Consumer<java.lang.Boolean> consumer) {
        android.app.role.RoleManager roleManager = this.mRoleManagerSupplier.get();
        if (roleManager == null) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            roleManager.addRoleHolderAsUser("android.app.role.HOME", str, 0, android.os.UserHandle.of(i), executor, consumer);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @android.annotation.Nullable
    private java.lang.String getRoleHolder(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int i) {
        android.app.role.RoleManager roleManager = this.mRoleManagerSupplier.get();
        if (roleManager == null) {
            return null;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return (java.lang.String) com.android.internal.util.CollectionUtils.firstOrNull(roleManager.getRoleHoldersAsUser(str, android.os.UserHandle.of(i)));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
