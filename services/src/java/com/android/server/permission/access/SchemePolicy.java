package com.android.server.permission.access;

/* compiled from: AccessPolicy.kt */
/* loaded from: classes2.dex */
public abstract class SchemePolicy {
    @org.jetbrains.annotations.NotNull
    public abstract java.lang.String getObjectScheme();

    @org.jetbrains.annotations.NotNull
    public abstract java.lang.String getSubjectScheme();

    public void onStateMutated(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$onStateMutated) {
    }

    public void onInitialized(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onInitialized) {
    }

    public void onUserAdded(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onUserAdded, int userId) {
    }

    public void onUserRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onUserRemoved, int userId) {
    }

    public void onAppIdAdded(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onAppIdAdded, int appId) {
    }

    public void onAppIdRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onAppIdRemoved, int appId) {
    }

    public void onStorageVolumeMounted(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onStorageVolumeMounted, @org.jetbrains.annotations.Nullable java.lang.String volumeUuid, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> list, boolean isSystemUpdated) {
    }

    public void onPackageAdded(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageAdded, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState) {
    }

    public void onPackageRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageRemoved, @org.jetbrains.annotations.NotNull java.lang.String packageName, int appId) {
    }

    public void onPackageInstalled(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageInstalled, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, int userId) {
    }

    public void onPackageUninstalled(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageUninstalled, @org.jetbrains.annotations.NotNull java.lang.String packageName, int appId, int userId) {
    }

    public void onSystemReady(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onSystemReady) {
    }

    public void migrateSystemState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state) {
    }

    public void migrateUserState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
    }

    public void upgradePackageState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$upgradePackageState, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, int userId, int version) {
    }

    public void parseSystemState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser $this$parseSystemState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state) {
    }

    public void serializeSystemState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeSystemState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state) {
    }

    public void parseUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser $this$parseUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
    }

    public void serializeUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state, int userId) {
    }
}
