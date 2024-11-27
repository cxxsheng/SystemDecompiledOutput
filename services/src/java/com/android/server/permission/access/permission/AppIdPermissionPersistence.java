package com.android.server.permission.access.permission;

/* compiled from: AppIdPermissionPersistence.kt */
/* loaded from: classes2.dex */
public final class AppIdPermissionPersistence {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.permission.AppIdPermissionPersistence.Companion Companion = new com.android.server.permission.access.permission.AppIdPermissionPersistence.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.permission.AppIdPermissionPersistence.class.getSimpleName();

    public final void parseSystemState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser $this$parseSystemState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state) {
        java.lang.String name = $this$parseSystemState.getName();
        if (!com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(name, "permission-trees")) {
            if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(name, "permissions")) {
                parsePermissions($this$parseSystemState, state, false);
                return;
            }
            return;
        }
        parsePermissions($this$parseSystemState, state, true);
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
    private final void parsePermissions(com.android.modules.utils.BinaryXmlPullParser r18, com.android.server.permission.access.MutableAccessState r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 460
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.permission.AppIdPermissionPersistence.parsePermissions(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.MutableAccessState, boolean):void");
    }

    private final void parsePermission(com.android.modules.utils.BinaryXmlPullParser $this$parsePermission, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission> mutableIndexedMap) {
        java.lang.String name$iv = $this$parsePermission.getAttributeValue($this$parsePermission.getAttributeIndexOrThrow((java.lang.String) null, "name"));
        java.lang.String name = name$iv.intern();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(name, "intern(...)");
        android.content.pm.PermissionInfo $this$parsePermission_u24lambda_u242 = new android.content.pm.PermissionInfo();
        $this$parsePermission_u24lambda_u242.name = name;
        java.lang.String name$iv2 = $this$parsePermission.getAttributeValue($this$parsePermission.getAttributeIndexOrThrow((java.lang.String) null, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME));
        java.lang.String name$iv3 = name$iv2.intern();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(name$iv3, "intern(...)");
        $this$parsePermission_u24lambda_u242.packageName = name$iv3;
        $this$parsePermission_u24lambda_u242.protectionLevel = $this$parsePermission.getAttributeIntHex((java.lang.String) null, "protectionLevel");
        int type = $this$parsePermission.getAttributeInt((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE);
        switch (type) {
            case 0:
                break;
            case 1:
                android.util.Slog.w(LOG_TAG, "Ignoring unexpected config permission " + name);
                return;
            case 2:
                $this$parsePermission_u24lambda_u242.icon = $this$parsePermission.getAttributeIntHex((java.lang.String) null, "icon", 0);
                java.lang.String name$iv4 = $this$parsePermission.getAttributeValue((java.lang.String) null, "label");
                $this$parsePermission_u24lambda_u242.nonLocalizedLabel = name$iv4;
                break;
            default:
                android.util.Slog.w(LOG_TAG, "Ignoring permission " + name + " with unknown type " + type);
                return;
        }
        com.android.server.permission.access.permission.Permission permission = new com.android.server.permission.access.permission.Permission($this$parsePermission_u24lambda_u242, false, type, 0, null, false, 48, null);
        mutableIndexedMap.put(name, permission);
    }

    public final void serializeSystemState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeSystemState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state) {
        com.android.server.permission.access.SystemState systemState = state.getSystemState();
        serializePermissions($this$serializeSystemState, "permission-trees", systemState.getPermissionTrees());
        serializePermissions($this$serializeSystemState, "permissions", systemState.getPermissions());
    }

    private final void serializePermissions(com.android.modules.utils.BinaryXmlSerializer $this$serializePermissions, java.lang.String tagName, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission> indexedMap) {
        $this$serializePermissions.startTag((java.lang.String) null, tagName);
        int size = indexedMap.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            indexedMap.keyAt(index$iv);
            com.android.server.permission.access.permission.Permission it = indexedMap.valueAt(index$iv);
            serializePermission($this$serializePermissions, it);
        }
        $this$serializePermissions.endTag((java.lang.String) null, tagName);
    }

    private final void serializePermission(com.android.modules.utils.BinaryXmlSerializer $this$serializePermission, com.android.server.permission.access.permission.Permission permission) {
        java.lang.String it;
        int type = permission.getType();
        switch (type) {
            case 0:
            case 2:
                $this$serializePermission.startTag((java.lang.String) null, "permission");
                java.lang.String value$iv = permission.getPermissionInfo().name;
                $this$serializePermission.attributeInterned((java.lang.String) null, "name", value$iv);
                java.lang.String value$iv2 = permission.getPermissionInfo().packageName;
                $this$serializePermission.attributeInterned((java.lang.String) null, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME, value$iv2);
                int value$iv3 = permission.getPermissionInfo().protectionLevel;
                $this$serializePermission.attributeIntHex((java.lang.String) null, "protectionLevel", value$iv3);
                $this$serializePermission.attributeInt((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, type);
                if (type == 2) {
                    android.content.pm.PermissionInfo permissionInfo = permission.getPermissionInfo();
                    int value$iv4 = permissionInfo.icon;
                    if (value$iv4 != 0) {
                        $this$serializePermission.attributeIntHex((java.lang.String) null, "icon", value$iv4);
                    }
                    java.lang.CharSequence charSequence = permissionInfo.nonLocalizedLabel;
                    if (charSequence != null && (it = charSequence.toString()) != null) {
                        $this$serializePermission.attribute((java.lang.String) null, "label", it);
                    }
                }
                $this$serializePermission.endTag((java.lang.String) null, "permission");
                break;
            case 1:
                break;
            default:
                android.util.Slog.w(LOG_TAG, "Skipping serializing permission " + $this$serializePermission.getName() + " with unknown type " + type);
                break;
        }
    }

    public final void parseUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser $this$parseUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual($this$parseUserState.getName(), "app-id-permissions")) {
            parseAppIdPermissions($this$parseUserState, state, userId);
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
    private final void parseAppIdPermissions(com.android.modules.utils.BinaryXmlPullParser r17, com.android.server.permission.access.MutableAccessState r18, int r19) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.permission.AppIdPermissionPersistence.parseAppIdPermissions(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.MutableAccessState, int):void");
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
    private final void parseAppId(com.android.modules.utils.BinaryXmlPullParser r14, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> r15) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.permission.AppIdPermissionPersistence.parseAppId(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.immutable.MutableIntReferenceMap):void");
    }

    private final void parseAppIdPermission(com.android.modules.utils.BinaryXmlPullParser $this$parseAppIdPermission, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer> mutableIndexedMap) {
        java.lang.String name$iv = $this$parseAppIdPermission.getAttributeValue($this$parseAppIdPermission.getAttributeIndexOrThrow((java.lang.String) null, "name"));
        java.lang.String name = name$iv.intern();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(name, "intern(...)");
        int flags = $this$parseAppIdPermission.getAttributeInt((java.lang.String) null, "flags");
        mutableIndexedMap.put(name, java.lang.Integer.valueOf(flags));
    }

    public final void serializeUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state, int userId) {
        com.android.server.permission.access.UserState userState = state.getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        serializeAppIdPermissions($this$serializeUserState, userState.getAppIdPermissionFlags());
    }

    private final void serializeAppIdPermissions(com.android.modules.utils.BinaryXmlSerializer $this$serializeAppIdPermissions, com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> intReferenceMap) {
        $this$serializeAppIdPermissions.startTag((java.lang.String) null, "app-id-permissions");
        int size = intReferenceMap.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            int appId = intReferenceMap.keyAt(index$iv);
            com.android.server.permission.access.immutable.IndexedMap permissionFlags = intReferenceMap.valueAt(index$iv);
            serializeAppId($this$serializeAppIdPermissions, appId, permissionFlags);
        }
        $this$serializeAppIdPermissions.endTag((java.lang.String) null, "app-id-permissions");
    }

    private final void serializeAppId(com.android.modules.utils.BinaryXmlSerializer $this$serializeAppId, int appId, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> indexedMap) {
        $this$serializeAppId.startTag((java.lang.String) null, "app-id");
        $this$serializeAppId.attributeInt((java.lang.String) null, "id", appId);
        int size = indexedMap.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            java.lang.String keyAt = indexedMap.keyAt(index$iv);
            int flags = indexedMap.valueAt(index$iv).intValue();
            java.lang.String name = keyAt;
            serializeAppIdPermission($this$serializeAppId, name, flags);
        }
        $this$serializeAppId.endTag((java.lang.String) null, "app-id");
    }

    private final void serializeAppIdPermission(com.android.modules.utils.BinaryXmlSerializer $this$serializeAppIdPermission, java.lang.String name, int flags) {
        int serializedFlags;
        $this$serializeAppIdPermission.startTag((java.lang.String) null, "permission");
        $this$serializeAppIdPermission.attributeInterned((java.lang.String) null, "name", name);
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 2097152)) {
            serializedFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(flags, 16);
        } else {
            serializedFlags = flags;
        }
        $this$serializeAppIdPermission.attributeInt((java.lang.String) null, "flags", serializedFlags);
        $this$serializeAppIdPermission.endTag((java.lang.String) null, "permission");
    }

    /* compiled from: AppIdPermissionPersistence.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
