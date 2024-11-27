package com.android.server.permission.access.appop;

/* compiled from: PackageAppOpPersistence.kt */
/* loaded from: classes2.dex */
public final class PackageAppOpPersistence extends com.android.server.permission.access.appop.BaseAppOpPersistence {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.appop.PackageAppOpPersistence.Companion Companion = new com.android.server.permission.access.appop.PackageAppOpPersistence.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.appop.PackageAppOpPersistence.class.getSimpleName();

    @Override // com.android.server.permission.access.appop.BaseAppOpPersistence
    public void parseUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser $this$parseUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual($this$parseUserState.getName(), "package-app-ops")) {
            parsePackageAppOps($this$parseUserState, state, userId);
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
    private final void parsePackageAppOps(com.android.modules.utils.BinaryXmlPullParser r17, com.android.server.permission.access.MutableAccessState r18, int r19) {
        /*
            Method dump skipped, instructions count: 418
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.appop.PackageAppOpPersistence.parsePackageAppOps(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.MutableAccessState, int):void");
    }

    private final void parsePackage(com.android.modules.utils.BinaryXmlPullParser $this$parsePackage, com.android.server.permission.access.immutable.MutableIndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> mutableIndexedReferenceMap) {
        java.lang.String name$iv = $this$parsePackage.getAttributeValue($this$parsePackage.getAttributeIndexOrThrow((java.lang.String) null, "name"));
        java.lang.String packageName = name$iv.intern();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(packageName, "intern(...)");
        com.android.server.permission.access.immutable.MutableIndexedMap appOpModes = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
        mutableIndexedReferenceMap.put(packageName, appOpModes);
        parseAppOps($this$parsePackage, appOpModes);
    }

    @Override // com.android.server.permission.access.appop.BaseAppOpPersistence
    public void serializeUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state, int userId) {
        com.android.server.permission.access.UserState userState = state.getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        serializePackageAppOps($this$serializeUserState, userState.getPackageAppOpModes());
    }

    private final void serializePackageAppOps(com.android.modules.utils.BinaryXmlSerializer $this$serializePackageAppOps, com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> indexedReferenceMap) {
        $this$serializePackageAppOps.startTag((java.lang.String) null, "package-app-ops");
        int size = indexedReferenceMap.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            java.lang.String keyAt = indexedReferenceMap.keyAt(index$iv);
            com.android.server.permission.access.immutable.IndexedMap appOpModes = indexedReferenceMap.valueAt(index$iv);
            java.lang.String packageName = keyAt;
            serializePackage($this$serializePackageAppOps, packageName, appOpModes);
        }
        $this$serializePackageAppOps.endTag((java.lang.String) null, "package-app-ops");
    }

    private final void serializePackage(com.android.modules.utils.BinaryXmlSerializer $this$serializePackage, java.lang.String packageName, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> indexedMap) {
        $this$serializePackage.startTag((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE);
        $this$serializePackage.attributeInterned((java.lang.String) null, "name", packageName);
        serializeAppOps($this$serializePackage, indexedMap);
        $this$serializePackage.endTag((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE);
    }

    /* compiled from: PackageAppOpPersistence.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
