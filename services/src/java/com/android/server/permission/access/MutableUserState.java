package com.android.server.permission.access;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public final class MutableUserState extends com.android.server.permission.access.UserState implements com.android.server.permission.access.WritableState {
    private MutableUserState(com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> mutableReference, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>> mutableReference2, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>, com.android.server.permission.access.immutable.MutableIndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>, com.android.server.permission.access.immutable.MutableIndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>>> mutableReference3, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>> mutableReference4, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>, com.android.server.permission.access.immutable.MutableIndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>> mutableReference5, java.lang.String defaultPermissionGrantFingerprint, int writeMode) {
        super(mutableReference, mutableReference2, mutableReference3, mutableReference4, mutableReference5, defaultPermissionGrantFingerprint, writeMode, null);
    }

    public MutableUserState() {
        this(new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null)), new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIntReferenceMap(null, 1, null)), new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIntReferenceMap(null, 1, null)), new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIntReferenceMap(null, 1, null)), new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIndexedReferenceMap(null, 1, null)), null, 0);
    }

    public MutableUserState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.UserState userState) {
        this(userState.getPackageVersionsReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().toImmutable(), userState.getAppIdPermissionFlagsReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().toImmutable(), userState.getAppIdDevicePermissionFlagsReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().toImmutable(), userState.getAppIdAppOpModesReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().toImmutable(), userState.getPackageAppOpModesReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().toImmutable(), userState.getDefaultPermissionGrantFingerprint(), 0);
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer> mutatePackageVersions() {
        return (com.android.server.permission.access.immutable.MutableIndexedMap) getPackageVersionsReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().mutate();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> mutateAppIdPermissionFlags() {
        return (com.android.server.permission.access.immutable.MutableIntReferenceMap) getAppIdPermissionFlagsReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().mutate();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>, com.android.server.permission.access.immutable.MutableIndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>> mutateAppIdDevicePermissionFlags() {
        return (com.android.server.permission.access.immutable.MutableIntReferenceMap) getAppIdDevicePermissionFlagsReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().mutate();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> mutateAppIdAppOpModes() {
        return (com.android.server.permission.access.immutable.MutableIntReferenceMap) getAppIdAppOpModesReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().mutate();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> mutatePackageAppOpModes() {
        return (com.android.server.permission.access.immutable.MutableIndexedReferenceMap) getPackageAppOpModesReference$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().mutate();
    }

    public final void setDefaultPermissionGrantFingerprintPublic(@org.jetbrains.annotations.Nullable java.lang.String defaultPermissionGrantFingerprint) {
        setDefaultPermissionGrantFingerprint(defaultPermissionGrantFingerprint);
    }

    public void requestWriteMode(int writeMode) {
        setWriteMode(java.lang.Math.max(getWriteMode(), writeMode));
    }
}
