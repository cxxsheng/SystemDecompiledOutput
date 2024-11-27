package com.android.server;

/* loaded from: classes.dex */
public class SystemConfigService extends com.android.server.SystemService {
    private final android.content.Context mContext;
    private final android.os.ISystemConfig.Stub mInterface;

    /* renamed from: com.android.server.SystemConfigService$1, reason: invalid class name */
    class AnonymousClass1 extends android.os.ISystemConfig.Stub {
        AnonymousClass1() {
        }

        public java.util.List<java.lang.String> getDisabledUntilUsedPreinstalledCarrierApps() {
            com.android.server.SystemConfigService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_CARRIER_APP_INFO", "getDisabledUntilUsedPreInstalledCarrierApps requires READ_CARRIER_APP_INFO");
            return new java.util.ArrayList(com.android.server.SystemConfig.getInstance().getDisabledUntilUsedPreinstalledCarrierApps());
        }

        public java.util.Map getDisabledUntilUsedPreinstalledCarrierAssociatedApps() {
            com.android.server.SystemConfigService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_CARRIER_APP_INFO", "getDisabledUntilUsedPreInstalledCarrierAssociatedApps requires READ_CARRIER_APP_INFO");
            return (java.util.Map) com.android.server.SystemConfig.getInstance().getDisabledUntilUsedPreinstalledCarrierAssociatedApps().entrySet().stream().collect(java.util.stream.Collectors.toMap(new java.util.function.Function() { // from class: com.android.server.SystemConfigService$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return (java.lang.String) ((java.util.Map.Entry) obj).getKey();
                }
            }, new java.util.function.Function() { // from class: com.android.server.SystemConfigService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.util.List lambda$getDisabledUntilUsedPreinstalledCarrierAssociatedApps$1;
                    lambda$getDisabledUntilUsedPreinstalledCarrierAssociatedApps$1 = com.android.server.SystemConfigService.AnonymousClass1.lambda$getDisabledUntilUsedPreinstalledCarrierAssociatedApps$1((java.util.Map.Entry) obj);
                    return lambda$getDisabledUntilUsedPreinstalledCarrierAssociatedApps$1;
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.util.List lambda$getDisabledUntilUsedPreinstalledCarrierAssociatedApps$1(java.util.Map.Entry entry) {
            return (java.util.List) ((java.util.List) entry.getValue()).stream().map(new java.util.function.Function() { // from class: com.android.server.SystemConfigService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String str;
                    str = ((android.os.CarrierAssociatedAppEntry) obj).packageName;
                    return str;
                }
            }).collect(java.util.stream.Collectors.toList());
        }

        public java.util.Map getDisabledUntilUsedPreinstalledCarrierAssociatedAppEntries() {
            com.android.server.SystemConfigService.this.mContext.enforceCallingOrSelfPermission("android.permission.READ_CARRIER_APP_INFO", "getDisabledUntilUsedPreInstalledCarrierAssociatedAppEntries requires READ_CARRIER_APP_INFO");
            return com.android.server.SystemConfig.getInstance().getDisabledUntilUsedPreinstalledCarrierAssociatedApps();
        }

        public int[] getSystemPermissionUids(java.lang.String str) {
            com.android.server.SystemConfigService.this.mContext.enforceCallingOrSelfPermission("android.permission.GET_RUNTIME_PERMISSIONS", "getSystemPermissionUids requires GET_RUNTIME_PERMISSIONS");
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.util.SparseArray<android.util.ArraySet<java.lang.String>> systemPermissions = com.android.server.SystemConfig.getInstance().getSystemPermissions();
            for (int i = 0; i < systemPermissions.size(); i++) {
                android.util.ArraySet<java.lang.String> valueAt = systemPermissions.valueAt(i);
                if (valueAt != null && valueAt.contains(str)) {
                    arrayList.add(java.lang.Integer.valueOf(systemPermissions.keyAt(i)));
                }
            }
            return com.android.internal.util.ArrayUtils.convertToIntArray(arrayList);
        }

        public java.util.List<android.content.ComponentName> getEnabledComponentOverrides(java.lang.String str) {
            android.util.ArrayMap<java.lang.String, java.lang.Boolean> componentsEnabledStates = com.android.server.SystemConfig.getInstance().getComponentsEnabledStates(str);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (componentsEnabledStates != null) {
                for (java.util.Map.Entry<java.lang.String, java.lang.Boolean> entry : componentsEnabledStates.entrySet()) {
                    if (java.lang.Boolean.TRUE.equals(entry.getValue())) {
                        arrayList.add(new android.content.ComponentName(str, entry.getKey()));
                    }
                }
            }
            return arrayList;
        }

        public java.util.List<android.content.ComponentName> getDefaultVrComponents() {
            com.android.server.SystemConfigService.this.getContext().enforceCallingOrSelfPermission("android.permission.QUERY_ALL_PACKAGES", "Caller must hold android.permission.QUERY_ALL_PACKAGES");
            return new java.util.ArrayList(com.android.server.SystemConfig.getInstance().getDefaultVrComponents());
        }

        public java.util.List<java.lang.String> getPreventUserDisablePackages() {
            final android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            return (java.util.List) com.android.server.SystemConfig.getInstance().getPreventUserDisablePackages().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.SystemConfigService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getPreventUserDisablePackages$2;
                    lambda$getPreventUserDisablePackages$2 = com.android.server.SystemConfigService.AnonymousClass1.lambda$getPreventUserDisablePackages$2(android.content.pm.PackageManagerInternal.this, (java.lang.String) obj);
                    return lambda$getPreventUserDisablePackages$2;
                }
            }).collect(java.util.stream.Collectors.toList());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getPreventUserDisablePackages$2(android.content.pm.PackageManagerInternal packageManagerInternal, java.lang.String str) {
            return packageManagerInternal.canQueryPackage(android.os.Binder.getCallingUid(), str);
        }

        public java.util.List<android.content.pm.SignedPackageParcel> getEnhancedConfirmationTrustedPackages() {
            com.android.server.SystemConfigService.this.getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_ENHANCED_CONFIRMATION_STATES", "Caller must hold android.permission.MANAGE_ENHANCED_CONFIRMATION_STATES");
            return com.android.server.SystemConfig.getInstance().getEnhancedConfirmationTrustedPackages().stream().map(new com.android.server.SystemConfigService$1$$ExternalSyntheticLambda0()).toList();
        }

        public java.util.List<android.content.pm.SignedPackageParcel> getEnhancedConfirmationTrustedInstallers() {
            com.android.server.SystemConfigService.this.getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_ENHANCED_CONFIRMATION_STATES", "Caller must hold android.permission.MANAGE_ENHANCED_CONFIRMATION_STATES");
            return com.android.server.SystemConfig.getInstance().getEnhancedConfirmationTrustedInstallers().stream().map(new com.android.server.SystemConfigService$1$$ExternalSyntheticLambda0()).toList();
        }
    }

    public SystemConfigService(android.content.Context context) {
        super(context);
        this.mInterface = new com.android.server.SystemConfigService.AnonymousClass1();
        this.mContext = context;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("system_config", this.mInterface);
    }
}
