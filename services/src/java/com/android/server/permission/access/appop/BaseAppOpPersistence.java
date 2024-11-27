package com.android.server.permission.access.appop;

/* compiled from: BaseAppOpPersistence.kt */
/* loaded from: classes2.dex */
public abstract class BaseAppOpPersistence {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.appop.BaseAppOpPersistence.Companion Companion = new com.android.server.permission.access.appop.BaseAppOpPersistence.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.appop.BaseAppOpPersistence.class.getSimpleName();

    public abstract void parseUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser binaryXmlPullParser, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState mutableAccessState, int i);

    public abstract void serializeUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer binaryXmlSerializer, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState accessState, int i);

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
    protected final void parseAppOps(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser r12, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer> r13) {
        /*
            r11 = this;
            r0 = r12
            r1 = 0
            int r2 = r0.getEventType()
            java.lang.String r3 = "Unexpected event type "
            switch(r2) {
                case 0: goto L20;
                case 1: goto Lb;
                case 2: goto L20;
                default: goto Lb;
            }
        Lb:
            org.xmlpull.v1.XmlPullParserException r4 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r2)
            java.lang.String r3 = r5.toString()
            r4.<init>(r3)
            throw r4
        L20:
            r4 = r0
            r5 = 0
        L22:
            int r6 = r4.next()
            switch(r6) {
                case 1: goto L2b;
                case 2: goto L2b;
                case 3: goto L2b;
                default: goto L2a;
            }
        L2a:
            goto L22
        L2b:
        L2c:
            int r2 = r0.getEventType()
            switch(r2) {
                case 1: goto Lf1;
                case 2: goto L49;
                case 3: goto Lf1;
                default: goto L34;
            }
        L34:
            org.xmlpull.v1.XmlPullParserException r4 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r2)
            java.lang.String r3 = r5.toString()
            r4.<init>(r3)
            throw r4
        L49:
            int r4 = r0.getDepth()
            r5 = r0
            r6 = 0
            r7 = r5
            r8 = 0
            java.lang.String r7 = r7.getName()
            java.lang.String r8 = "app-op"
            boolean r7 = com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(r7, r8)
            if (r7 == 0) goto L62
            r11.parseAppOp(r5, r13)
            goto L81
        L62:
            java.lang.String r7 = com.android.server.permission.access.appop.BaseAppOpPersistence.LOG_TAG
            java.lang.String r8 = r5.getName()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Ignoring unknown tag "
            r9.append(r10)
            r9.append(r8)
            java.lang.String r8 = " when parsing app-op state"
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            android.util.Slog.w(r7, r8)
        L81:
            int r5 = r0.getDepth()
            if (r5 != r4) goto Ld2
        L89:
        L8a:
            int r6 = r0.getEventType()
            switch(r6) {
                case 2: goto Lc6;
                case 3: goto La6;
                default: goto L91;
            }
        L91:
            org.xmlpull.v1.XmlPullParserException r7 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r3)
            r8.append(r6)
            java.lang.String r3 = r8.toString()
            r7.<init>(r3)
            throw r7
        La6:
            int r7 = r0.getDepth()
            if (r7 <= r4) goto Lb8
            r7 = r0
            r8 = 0
        Lae:
            int r9 = r7.next()
            switch(r9) {
                case 1: goto Lb7;
                case 2: goto Lb7;
                case 3: goto Lb7;
                default: goto Lb6;
            }
        Lb6:
            goto Lae
        Lb7:
            goto L89
        Lb8:
            r6 = r0
            r7 = 0
        Lbb:
            int r8 = r6.next()
            switch(r8) {
                case 1: goto Lc4;
                case 2: goto Lc4;
                case 3: goto Lc4;
                default: goto Lc3;
            }
        Lc3:
            goto Lbb
        Lc4:
            goto L2c
        Lc6:
            r7 = r0
            r8 = 0
        Lc8:
            int r9 = r7.next()
            switch(r9) {
                case 1: goto Ld1;
                case 2: goto Ld1;
                case 3: goto Ld1;
                default: goto Ld0;
            }
        Ld0:
            goto Lc8
        Ld1:
            goto L89
        Ld2:
            org.xmlpull.v1.XmlPullParserException r3 = new org.xmlpull.v1.XmlPullParserException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unexpected post-block depth "
            r6.append(r7)
            r6.append(r5)
            java.lang.String r7 = ", expected "
            r6.append(r7)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            r3.<init>(r6)
            throw r3
        Lf1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.appop.BaseAppOpPersistence.parseAppOps(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.immutable.MutableIndexedMap):void");
    }

    private final void parseAppOp(com.android.modules.utils.BinaryXmlPullParser $this$parseAppOp, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer> mutableIndexedMap) {
        java.lang.String name$iv = $this$parseAppOp.getAttributeValue($this$parseAppOp.getAttributeIndexOrThrow((java.lang.String) null, "name"));
        java.lang.String name = name$iv.intern();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(name, "intern(...)");
        int mode = $this$parseAppOp.getAttributeInt((java.lang.String) null, com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration.MODE_KEY);
        mutableIndexedMap.put(name, java.lang.Integer.valueOf(mode));
    }

    protected final void serializeAppOps(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeAppOps, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> indexedMap) {
        int size = indexedMap.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            java.lang.String keyAt = indexedMap.keyAt(index$iv);
            int mode = indexedMap.valueAt(index$iv).intValue();
            java.lang.String name = keyAt;
            serializeAppOp($this$serializeAppOps, name, mode);
        }
    }

    private final void serializeAppOp(com.android.modules.utils.BinaryXmlSerializer $this$serializeAppOp, java.lang.String name, int mode) {
        $this$serializeAppOp.startTag((java.lang.String) null, "app-op");
        $this$serializeAppOp.attributeInterned((java.lang.String) null, "name", name);
        $this$serializeAppOp.attributeInt((java.lang.String) null, com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration.MODE_KEY, mode);
        $this$serializeAppOp.endTag((java.lang.String) null, "app-op");
    }

    /* compiled from: BaseAppOpPersistence.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
