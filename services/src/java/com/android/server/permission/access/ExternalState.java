package com.android.server.permission.access;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public abstract class ExternalState implements com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.MutableExternalState> {

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>, com.android.server.permission.access.immutable.MutableIndexedListSet<java.lang.String>>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>, com.android.server.permission.access.immutable.MutableIndexedListSet<java.lang.String>>> appIdPackageNamesReference;

    @org.jetbrains.annotations.NotNull
    private java.util.Map<java.lang.String, com.android.server.SystemConfig.PermissionEntry> configPermissions;

    @org.jetbrains.annotations.NotNull
    private java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> disabledSystemPackageStates;

    @org.jetbrains.annotations.NotNull
    private com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>> implicitToSourcePermissions;
    private boolean isLeanback;
    private boolean isSystemReady;

    @org.jetbrains.annotations.NotNull
    private com.android.server.permission.access.immutable.IntMap<java.lang.String[]> knownPackages;

    @org.jetbrains.annotations.NotNull
    private java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> packageStates;

    @org.jetbrains.annotations.NotNull
    private com.android.server.pm.permission.PermissionAllowlist permissionAllowlist;

    @org.jetbrains.annotations.NotNull
    private com.android.server.permission.access.immutable.IndexedListSet<java.lang.String> privilegedPermissionAllowlistPackages;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntSet, com.android.server.permission.access.immutable.MutableIntSet> userIdsReference;

    public /* synthetic */ ExternalState(com.android.server.permission.access.immutable.MutableReference mutableReference, java.util.Map map, java.util.Map map2, com.android.server.permission.access.immutable.MutableReference mutableReference2, com.android.server.permission.access.immutable.IntMap intMap, boolean z, java.util.Map map3, com.android.server.permission.access.immutable.IndexedListSet indexedListSet, com.android.server.pm.permission.PermissionAllowlist permissionAllowlist, com.android.server.permission.access.immutable.IndexedMap indexedMap, boolean z2, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableReference, map, map2, mutableReference2, intMap, z, map3, indexedListSet, permissionAllowlist, indexedMap, z2);
    }

    private ExternalState(com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntSet, com.android.server.permission.access.immutable.MutableIntSet> mutableReference, java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map, java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map2, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>, com.android.server.permission.access.immutable.MutableIndexedListSet<java.lang.String>>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>, com.android.server.permission.access.immutable.MutableIndexedListSet<java.lang.String>>> mutableReference2, com.android.server.permission.access.immutable.IntMap<java.lang.String[]> intMap, boolean isLeanback, java.util.Map<java.lang.String, com.android.server.SystemConfig.PermissionEntry> map3, com.android.server.permission.access.immutable.IndexedListSet<java.lang.String> indexedListSet, com.android.server.pm.permission.PermissionAllowlist permissionAllowlist, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>> indexedMap, boolean isSystemReady) {
        this.userIdsReference = mutableReference;
        this.appIdPackageNamesReference = mutableReference2;
        this.packageStates = map;
        this.disabledSystemPackageStates = map2;
        this.knownPackages = intMap;
        this.isLeanback = isLeanback;
        this.configPermissions = map3;
        this.privilegedPermissionAllowlistPackages = indexedListSet;
        this.permissionAllowlist = permissionAllowlist;
        this.implicitToSourcePermissions = indexedMap;
        this.isSystemReady = isSystemReady;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntSet, com.android.server.permission.access.immutable.MutableIntSet> getUserIdsReference() {
        return this.userIdsReference;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>, com.android.server.permission.access.immutable.MutableIndexedListSet<java.lang.String>>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>, com.android.server.permission.access.immutable.MutableIndexedListSet<java.lang.String>>> getAppIdPackageNamesReference() {
        return this.appIdPackageNamesReference;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IntSet getUserIds() {
        return this.userIdsReference.get();
    }

    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> getPackageStates() {
        return this.packageStates;
    }

    protected final void setPackageStates(@org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map) {
        this.packageStates = map;
    }

    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> getDisabledSystemPackageStates() {
        return this.disabledSystemPackageStates;
    }

    protected final void setDisabledSystemPackageStates(@org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map) {
        this.disabledSystemPackageStates = map;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>, com.android.server.permission.access.immutable.MutableIndexedListSet<java.lang.String>> getAppIdPackageNames() {
        return this.appIdPackageNamesReference.get();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IntMap<java.lang.String[]> getKnownPackages() {
        return this.knownPackages;
    }

    protected final void setKnownPackages(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntMap<java.lang.String[]> intMap) {
        this.knownPackages = intMap;
    }

    public final boolean isLeanback() {
        return this.isLeanback;
    }

    protected final void setLeanback(boolean z) {
        this.isLeanback = z;
    }

    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, com.android.server.SystemConfig.PermissionEntry> getConfigPermissions() {
        return this.configPermissions;
    }

    protected final void setConfigPermissions(@org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, com.android.server.SystemConfig.PermissionEntry> map) {
        this.configPermissions = map;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IndexedListSet<java.lang.String> getPrivilegedPermissionAllowlistPackages() {
        return this.privilegedPermissionAllowlistPackages;
    }

    protected final void setPrivilegedPermissionAllowlistPackages(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedListSet<java.lang.String> indexedListSet) {
        this.privilegedPermissionAllowlistPackages = indexedListSet;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.pm.permission.PermissionAllowlist getPermissionAllowlist() {
        return this.permissionAllowlist;
    }

    protected final void setPermissionAllowlist(@org.jetbrains.annotations.NotNull com.android.server.pm.permission.PermissionAllowlist permissionAllowlist) {
        this.permissionAllowlist = permissionAllowlist;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>> getImplicitToSourcePermissions() {
        return this.implicitToSourcePermissions;
    }

    protected final void setImplicitToSourcePermissions(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>> indexedMap) {
        this.implicitToSourcePermissions = indexedMap;
    }

    public final boolean isSystemReady() {
        return this.isSystemReady;
    }

    protected final void setSystemReady(boolean z) {
        this.isSystemReady = z;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.MutableExternalState toMutable() {
        return new com.android.server.permission.access.MutableExternalState(this);
    }
}
