package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public final class LegacyPermission {
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_PACKAGE = "package";
    private static final java.lang.String TAG_ITEM = "item";
    public static final int TYPE_CONFIG = 1;
    public static final int TYPE_DYNAMIC = 2;
    public static final int TYPE_MANIFEST = 0;

    @android.annotation.NonNull
    private final int[] mGids;

    @android.annotation.NonNull
    private final android.content.pm.PermissionInfo mPermissionInfo;
    private final int mType;
    private final int mUid;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionType {
    }

    public LegacyPermission(@android.annotation.NonNull android.content.pm.PermissionInfo permissionInfo, int i, int i2, @android.annotation.NonNull int[] iArr) {
        this.mPermissionInfo = permissionInfo;
        this.mType = i;
        this.mUid = i2;
        this.mGids = iArr;
    }

    private LegacyPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        this.mPermissionInfo = new android.content.pm.PermissionInfo();
        this.mPermissionInfo.name = str;
        this.mPermissionInfo.packageName = str2;
        this.mPermissionInfo.protectionLevel = 2;
        this.mType = i;
        this.mUid = 0;
        this.mGids = libcore.util.EmptyArray.INT;
    }

    @android.annotation.NonNull
    public android.content.pm.PermissionInfo getPermissionInfo() {
        return this.mPermissionInfo;
    }

    public int getType() {
        return this.mType;
    }

    public static boolean read(@android.annotation.NonNull java.util.Map<java.lang.String, com.android.server.pm.permission.LegacyPermission> map, @android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        if (!typedXmlPullParser.getName().equals("item")) {
            return false;
        }
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "package");
        java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE);
        if (attributeValue == null || attributeValue2 == null) {
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: permissions has no name at " + typedXmlPullParser.getPositionDescription());
            return false;
        }
        boolean equals = "dynamic".equals(attributeValue3);
        com.android.server.pm.permission.LegacyPermission legacyPermission = map.get(attributeValue);
        if (legacyPermission == null || legacyPermission.mType != 1) {
            legacyPermission = new com.android.server.pm.permission.LegacyPermission(attributeValue.intern(), attributeValue2, equals ? 2 : 0);
        }
        legacyPermission.mPermissionInfo.protectionLevel = readInt(typedXmlPullParser, null, "protection", 0);
        legacyPermission.mPermissionInfo.protectionLevel = android.content.pm.PermissionInfo.fixProtectionLevel(legacyPermission.mPermissionInfo.protectionLevel);
        if (equals) {
            legacyPermission.mPermissionInfo.icon = readInt(typedXmlPullParser, null, "icon", 0);
            legacyPermission.mPermissionInfo.nonLocalizedLabel = typedXmlPullParser.getAttributeValue((java.lang.String) null, "label");
        }
        map.put(legacyPermission.mPermissionInfo.name, legacyPermission);
        return true;
    }

    private static int readInt(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        return typedXmlPullParser.getAttributeInt(str, str2, i);
    }

    public void write(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        if (this.mPermissionInfo.packageName == null) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, "item");
        typedXmlSerializer.attribute((java.lang.String) null, "name", this.mPermissionInfo.name);
        typedXmlSerializer.attribute((java.lang.String) null, "package", this.mPermissionInfo.packageName);
        if (this.mPermissionInfo.protectionLevel != 0) {
            typedXmlSerializer.attributeInt((java.lang.String) null, "protection", this.mPermissionInfo.protectionLevel);
        }
        if (this.mType == 2) {
            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, "dynamic");
            if (this.mPermissionInfo.icon != 0) {
                typedXmlSerializer.attributeInt((java.lang.String) null, "icon", this.mPermissionInfo.icon);
            }
            if (this.mPermissionInfo.nonLocalizedLabel != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "label", this.mPermissionInfo.nonLocalizedLabel.toString());
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, "item");
    }

    public boolean dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.Set<java.lang.String> set, boolean z, boolean z2, @android.annotation.NonNull com.android.server.pm.DumpState dumpState) {
        if (str != null && !str.equals(this.mPermissionInfo.packageName)) {
            return false;
        }
        if (set != null && !set.contains(this.mPermissionInfo.name)) {
            return false;
        }
        if (!z2) {
            if (dumpState.onTitlePrinted()) {
                printWriter.println();
            }
            printWriter.println("Permissions:");
        }
        printWriter.print("  Permission [");
        printWriter.print(this.mPermissionInfo.name);
        printWriter.print("] (");
        printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        printWriter.println("):");
        printWriter.print("    sourcePackage=");
        printWriter.println(this.mPermissionInfo.packageName);
        printWriter.print("    uid=");
        printWriter.print(this.mUid);
        printWriter.print(" gids=");
        printWriter.print(java.util.Arrays.toString(this.mGids));
        printWriter.print(" type=");
        printWriter.print(this.mType);
        printWriter.print(" prot=");
        printWriter.println(android.content.pm.PermissionInfo.protectionToString(this.mPermissionInfo.protectionLevel));
        if (this.mPermissionInfo != null) {
            printWriter.print("    perm=");
            printWriter.println(this.mPermissionInfo);
            if ((this.mPermissionInfo.flags & 1073741824) == 0 || (this.mPermissionInfo.flags & 2) != 0) {
                printWriter.print("    flags=0x");
                printWriter.println(java.lang.Integer.toHexString(this.mPermissionInfo.flags));
            }
        }
        if (java.util.Objects.equals(this.mPermissionInfo.name, "android.permission.READ_EXTERNAL_STORAGE")) {
            printWriter.print("    enforced=");
            printWriter.println(z);
            return true;
        }
        return true;
    }
}
