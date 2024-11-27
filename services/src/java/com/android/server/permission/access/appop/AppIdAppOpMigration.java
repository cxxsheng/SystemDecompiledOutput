package com.android.server.permission.access.appop;

/* compiled from: AppIdAppOpMigration.kt */
/* loaded from: classes2.dex */
public final class AppIdAppOpMigration {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.appop.AppIdAppOpMigration.Companion Companion = new com.android.server.permission.access.appop.AppIdAppOpMigration.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.appop.AppIdAppOpMigration.class.getSimpleName();

    public final void migrateUserState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        com.android.server.appop.AppOpMigrationHelper legacyAppOpsManager;
        java.util.Map legacyAppIdAppOpModes;
        com.android.server.permission.access.MutableUserState userState;
        java.lang.Object service = com.android.server.LocalServices.getService(com.android.server.appop.AppOpMigrationHelper.class);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(service);
        com.android.server.appop.AppOpMigrationHelper legacyAppOpsManager2 = (com.android.server.appop.AppOpMigrationHelper) service;
        if (legacyAppOpsManager2.hasLegacyAppOpState()) {
            java.util.Map legacyAppIdAppOpModes2 = legacyAppOpsManager2.getLegacyAppIdAppOpModes(userId);
            int version = com.android.server.permission.access.util.PackageVersionMigration.INSTANCE.getVersion$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(userId);
            com.android.server.permission.access.MutableUserState userState2 = com.android.server.permission.access.MutableAccessState.mutateUserState$default(state, userId, 0, 2, null);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState2);
            com.android.server.permission.access.immutable.MutableIntReferenceMap appIdAppOpModes = userState2.mutateAppIdAppOpModes();
            for (java.util.Map.Entry element$iv : legacyAppIdAppOpModes2.entrySet()) {
                java.lang.Integer appId = element$iv.getKey();
                java.util.Map<java.lang.String, java.lang.Integer> legacyAppOpModes = element$iv.getValue();
                com.android.server.permission.access.immutable.IndexedListSet packageNames = state.getExternalState().getAppIdPackageNames().get(appId.intValue());
                if (packageNames != null || appId.intValue() < 10000) {
                    legacyAppOpsManager = legacyAppOpsManager2;
                    com.android.server.permission.access.immutable.MutableIndexedMap appOpModes = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
                    com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.set(appIdAppOpModes, appId.intValue(), appOpModes);
                    java.util.Map $this$forEach$iv = legacyAppOpModes;
                    for (java.util.Map.Entry element$iv2 : $this$forEach$iv.entrySet()) {
                        java.lang.String appOpName = element$iv2.getKey();
                        java.util.Map $this$forEach$iv2 = $this$forEach$iv;
                        java.lang.Integer appOpMode = element$iv2.getValue();
                        appOpModes.put(appOpName, appOpMode);
                        $this$forEach$iv = $this$forEach$iv2;
                    }
                    if (packageNames != null) {
                        com.android.server.permission.access.immutable.MutableIndexedMap $this$set$iv = userState2.mutatePackageVersions();
                        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv = packageNames;
                        legacyAppIdAppOpModes = legacyAppIdAppOpModes2;
                        userState = userState2;
                        int index$iv = 0;
                        for (int size = $this$forEachIndexed$iv.getSize(); index$iv < size; size = size) {
                            com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
                            java.lang.String packageName = $this$forEachIndexed$iv.elementAt(index$iv);
                            $this$set$iv.put(packageName, java.lang.Integer.valueOf(version));
                            index$iv++;
                            $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                        }
                    } else {
                        legacyAppIdAppOpModes = legacyAppIdAppOpModes2;
                        userState = userState2;
                    }
                } else {
                    java.lang.String str = LOG_TAG;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    legacyAppOpsManager = legacyAppOpsManager2;
                    sb.append("Dropping unknown app ID ");
                    sb.append(appId);
                    sb.append(" when migrating app op state");
                    android.util.Slog.w(str, sb.toString());
                    legacyAppIdAppOpModes = legacyAppIdAppOpModes2;
                    userState = userState2;
                }
                legacyAppOpsManager2 = legacyAppOpsManager;
                legacyAppIdAppOpModes2 = legacyAppIdAppOpModes;
                userState2 = userState;
            }
        }
    }

    /* compiled from: AppIdAppOpMigration.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
