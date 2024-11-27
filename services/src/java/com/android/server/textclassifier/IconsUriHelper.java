package com.android.server.textclassifier;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes2.dex */
public final class IconsUriHelper {
    public static final java.lang.String AUTHORITY = "com.android.textclassifier.icons";
    private static final java.lang.String TAG = "IconsUriHelper";
    private final java.util.function.Supplier<java.lang.String> mIdSupplier;

    @com.android.internal.annotations.GuardedBy({"mPackageIds"})
    private final java.util.Map<java.lang.String, java.lang.String> mPackageIds = new android.util.ArrayMap();
    private static final java.util.function.Supplier<java.lang.String> DEFAULT_ID_SUPPLIER = new java.util.function.Supplier() { // from class: com.android.server.textclassifier.IconsUriHelper$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            java.lang.String lambda$static$0;
            lambda$static$0 = com.android.server.textclassifier.IconsUriHelper.lambda$static$0();
            return lambda$static$0;
        }
    };
    private static final com.android.server.textclassifier.IconsUriHelper sSingleton = new com.android.server.textclassifier.IconsUriHelper(null);

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$static$0() {
        return java.util.UUID.randomUUID().toString();
    }

    private IconsUriHelper(@android.annotation.Nullable java.util.function.Supplier<java.lang.String> supplier) {
        this.mIdSupplier = supplier == null ? DEFAULT_ID_SUPPLIER : supplier;
        this.mPackageIds.put(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
    }

    public static com.android.server.textclassifier.IconsUriHelper newInstanceForTesting(@android.annotation.Nullable java.util.function.Supplier<java.lang.String> supplier) {
        return new com.android.server.textclassifier.IconsUriHelper(supplier);
    }

    static com.android.server.textclassifier.IconsUriHelper getInstance() {
        return sSingleton;
    }

    public android.net.Uri getContentUri(java.lang.String str, int i) {
        android.net.Uri build;
        java.util.Objects.requireNonNull(str);
        synchronized (this.mPackageIds) {
            try {
                if (!this.mPackageIds.containsKey(str)) {
                    this.mPackageIds.put(str, this.mIdSupplier.get());
                }
                build = new android.net.Uri.Builder().scheme(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT).authority(AUTHORITY).path(this.mPackageIds.get(str)).appendPath(java.lang.Integer.toString(i)).build();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return build;
    }

    @android.annotation.Nullable
    public com.android.server.textclassifier.IconsUriHelper.ResourceInfo getResourceInfo(android.net.Uri uri) {
        if (!com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme()) || !AUTHORITY.equals(uri.getAuthority())) {
            return null;
        }
        java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
        try {
        } catch (java.lang.Exception e) {
            android.util.Log.v(TAG, "Could not get resource info. Reason: " + e.getMessage());
        }
        synchronized (this.mPackageIds) {
            try {
                java.lang.String str = pathSegments.get(0);
                int parseInt = java.lang.Integer.parseInt(pathSegments.get(1));
                for (java.lang.String str2 : this.mPackageIds.keySet()) {
                    if (str.equals(this.mPackageIds.get(str2))) {
                        return new com.android.server.textclassifier.IconsUriHelper.ResourceInfo(str2, parseInt);
                    }
                }
                return null;
            } finally {
            }
        }
    }

    public static final class ResourceInfo {
        public final int id;
        public final java.lang.String packageName;

        private ResourceInfo(java.lang.String str, int i) {
            java.util.Objects.requireNonNull(str);
            this.packageName = str;
            this.id = i;
        }
    }
}
