package com.android.server.permission.access.permission;

/* compiled from: DevicePermissionPersistence.kt */
/* loaded from: classes2.dex */
public final class DevicePermissionPersistence {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.permission.DevicePermissionPersistence.Companion Companion = new com.android.server.permission.access.permission.DevicePermissionPersistence.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.permission.DevicePermissionPersistence.class.getSimpleName();

    public final void parseUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser $this$parseUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual($this$parseUserState.getName(), "app-id-device-permissions")) {
            parseAppIdDevicePermissions($this$parseUserState, state, userId);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private final void parseAppIdDevicePermissions(com.android.modules.utils.BinaryXmlPullParser r17, com.android.server.permission.access.MutableAccessState r18, int r19) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.permission.DevicePermissionPersistence.parseAppIdDevicePermissions(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.MutableAccessState, int):void");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private final void parseAppId(com.android.modules.utils.BinaryXmlPullParser r14, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>, com.android.server.permission.access.immutable.MutableIndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>> r15) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.permission.DevicePermissionPersistence.parseAppId(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.immutable.MutableIntReferenceMap):void");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private final void parseDevice(com.android.modules.utils.BinaryXmlPullParser r14, com.android.server.permission.access.immutable.MutableIndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> r15) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.permission.DevicePermissionPersistence.parseDevice(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.immutable.MutableIndexedReferenceMap):void");
    }

    private final void parsePermission(com.android.modules.utils.BinaryXmlPullParser $this$parsePermission, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer> mutableIndexedMap) {
        java.lang.String name$iv = $this$parsePermission.getAttributeValue($this$parsePermission.getAttributeIndexOrThrow((java.lang.String) null, "name"));
        java.lang.String name = name$iv.intern();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(name, "intern(...)");
        int flags = $this$parsePermission.getAttributeInt((java.lang.String) null, "flags");
        mutableIndexedMap.put(name, java.lang.Integer.valueOf(flags));
    }

    public final void serializeUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state, int userId) {
        com.android.server.permission.access.UserState userState = state.getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        com.android.server.permission.access.immutable.IntReferenceMap appIdDevicePermissionFlags = userState.getAppIdDevicePermissionFlags();
        $this$serializeUserState.startTag((java.lang.String) null, "app-id-device-permissions");
        int size = appIdDevicePermissionFlags.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            int appId = appIdDevicePermissionFlags.keyAt(index$iv);
            com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> devicePermissionFlags = appIdDevicePermissionFlags.valueAt(index$iv);
            serializeAppId($this$serializeUserState, appId, devicePermissionFlags);
        }
        $this$serializeUserState.endTag((java.lang.String) null, "app-id-device-permissions");
    }

    private final void serializeAppId(com.android.modules.utils.BinaryXmlSerializer $this$serializeAppId, int appId, com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> indexedReferenceMap) {
        $this$serializeAppId.startTag((java.lang.String) null, "app-id");
        $this$serializeAppId.attributeInt((java.lang.String) null, "id", appId);
        int size = indexedReferenceMap.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            java.lang.String keyAt = indexedReferenceMap.keyAt(index$iv);
            com.android.server.permission.access.immutable.IndexedMap permissionFlags = indexedReferenceMap.valueAt(index$iv);
            java.lang.String deviceId = keyAt;
            serializeDevice($this$serializeAppId, deviceId, permissionFlags);
        }
        $this$serializeAppId.endTag((java.lang.String) null, "app-id");
    }

    private final void serializeDevice(com.android.modules.utils.BinaryXmlSerializer $this$serializeDevice, java.lang.String deviceId, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> indexedMap) {
        $this$serializeDevice.startTag((java.lang.String) null, "device");
        $this$serializeDevice.attributeInterned((java.lang.String) null, "id", deviceId);
        int size = indexedMap.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            java.lang.String keyAt = indexedMap.keyAt(index$iv);
            int flags = indexedMap.valueAt(index$iv).intValue();
            java.lang.String name = keyAt;
            serializePermission($this$serializeDevice, name, flags);
        }
        $this$serializeDevice.endTag((java.lang.String) null, "device");
    }

    private final void serializePermission(com.android.modules.utils.BinaryXmlSerializer $this$serializePermission, java.lang.String name, int flags) {
        int serializedFlags;
        $this$serializePermission.startTag((java.lang.String) null, "permission");
        $this$serializePermission.attributeInterned((java.lang.String) null, "name", name);
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 2097152)) {
            serializedFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(flags, 16);
        } else {
            serializedFlags = flags;
        }
        $this$serializePermission.attributeInt((java.lang.String) null, "flags", serializedFlags);
        $this$serializePermission.endTag((java.lang.String) null, "permission");
    }

    /* compiled from: DevicePermissionPersistence.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
