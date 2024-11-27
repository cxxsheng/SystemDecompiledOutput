package com.android.server.permission.access.appop;

/* compiled from: PackageAppOpMigration.kt */
/* loaded from: classes2.dex */
public final class PackageAppOpMigration {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.appop.PackageAppOpMigration.Companion Companion = new com.android.server.permission.access.appop.PackageAppOpMigration.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.appop.PackageAppOpMigration.class.getSimpleName();

    public final void migrateUserState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        com.android.server.appop.AppOpMigrationHelper legacyAppOpsManager;
        java.lang.Object service = com.android.server.LocalServices.getService(com.android.server.appop.AppOpMigrationHelper.class);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(service);
        com.android.server.appop.AppOpMigrationHelper legacyAppOpsManager2 = (com.android.server.appop.AppOpMigrationHelper) service;
        if (!legacyAppOpsManager2.hasLegacyAppOpState()) {
            return;
        }
        java.util.Map legacyPackageAppOpModes = legacyAppOpsManager2.getLegacyPackageAppOpModes(userId);
        int version = com.android.server.permission.access.util.PackageVersionMigration.INSTANCE.getVersion$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(userId);
        com.android.server.permission.access.MutableUserState userState = com.android.server.permission.access.MutableAccessState.mutateUserState$default(state, userId, 0, 2, null);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        com.android.server.permission.access.immutable.MutableIndexedReferenceMap packageAppOpModes = userState.mutatePackageAppOpModes();
        for (java.util.Map.Entry element$iv : legacyPackageAppOpModes.entrySet()) {
            java.lang.String packageName = element$iv.getKey();
            java.util.Map<java.lang.String, java.lang.Integer> legacyAppOpModes = element$iv.getValue();
            if (!state.getExternalState().getPackageStates().containsKey(packageName)) {
                android.util.Slog.w(LOG_TAG, "Dropping unknown package " + packageName + " when migrating app op state");
                legacyAppOpsManager = legacyAppOpsManager2;
            } else {
                com.android.server.permission.access.immutable.MutableIndexedMap appOpModes = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
                packageAppOpModes.put(packageName, appOpModes);
                for (java.util.Map.Entry element$iv2 : legacyAppOpModes.entrySet()) {
                    java.lang.String appOpName = element$iv2.getKey();
                    com.android.server.appop.AppOpMigrationHelper legacyAppOpsManager3 = legacyAppOpsManager2;
                    java.lang.Integer appOpMode = element$iv2.getValue();
                    appOpModes.put(appOpName, appOpMode);
                    legacyAppOpsManager2 = legacyAppOpsManager3;
                }
                legacyAppOpsManager = legacyAppOpsManager2;
                com.android.server.permission.access.immutable.MutableIndexedMap $this$set$iv = userState.mutatePackageVersions();
                $this$set$iv.put(packageName, java.lang.Integer.valueOf(version));
            }
            legacyAppOpsManager2 = legacyAppOpsManager;
        }
    }

    /* compiled from: PackageAppOpMigration.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
