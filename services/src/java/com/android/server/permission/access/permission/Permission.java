package com.android.server.permission.access.permission;

/* compiled from: Permission.kt */
/* loaded from: classes2.dex */
public final class Permission {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.permission.Permission.Companion Companion = new com.android.server.permission.access.permission.Permission.Companion(null);
    private final int appId;
    private final boolean areGidsPerUser;

    @org.jetbrains.annotations.NotNull
    private final int[] gids;
    private final boolean isReconciled;

    @org.jetbrains.annotations.NotNull
    private final android.content.pm.PermissionInfo permissionInfo;
    private final int type;

    public static /* synthetic */ com.android.server.permission.access.permission.Permission copy$default(com.android.server.permission.access.permission.Permission permission, android.content.pm.PermissionInfo permissionInfo, boolean z, int i, int i2, int[] iArr, boolean z2, int i3, java.lang.Object obj) {
        if ((i3 & 1) != 0) {
            permissionInfo = permission.permissionInfo;
        }
        if ((i3 & 2) != 0) {
            z = permission.isReconciled;
        }
        boolean z3 = z;
        if ((i3 & 4) != 0) {
            i = permission.type;
        }
        int i4 = i;
        if ((i3 & 8) != 0) {
            i2 = permission.appId;
        }
        int i5 = i2;
        if ((i3 & 16) != 0) {
            iArr = permission.gids;
        }
        int[] iArr2 = iArr;
        if ((i3 & 32) != 0) {
            z2 = permission.areGidsPerUser;
        }
        return permission.copy(permissionInfo, z3, i4, i5, iArr2, z2);
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.permission.Permission copy(@org.jetbrains.annotations.NotNull android.content.pm.PermissionInfo permissionInfo, boolean z, int i, int i2, @org.jetbrains.annotations.NotNull int[] iArr, boolean z2) {
        return new com.android.server.permission.access.permission.Permission(permissionInfo, z, i, i2, iArr, z2);
    }

    public boolean equals(@org.jetbrains.annotations.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.permission.access.permission.Permission)) {
            return false;
        }
        com.android.server.permission.access.permission.Permission permission = (com.android.server.permission.access.permission.Permission) obj;
        return com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(this.permissionInfo, permission.permissionInfo) && this.isReconciled == permission.isReconciled && this.type == permission.type && this.appId == permission.appId && com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(this.gids, permission.gids) && this.areGidsPerUser == permission.areGidsPerUser;
    }

    public int hashCode() {
        return (((((((((this.permissionInfo.hashCode() * 31) + java.lang.Boolean.hashCode(this.isReconciled)) * 31) + java.lang.Integer.hashCode(this.type)) * 31) + java.lang.Integer.hashCode(this.appId)) * 31) + java.util.Arrays.hashCode(this.gids)) * 31) + java.lang.Boolean.hashCode(this.areGidsPerUser);
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return "Permission(permissionInfo=" + this.permissionInfo + ", isReconciled=" + this.isReconciled + ", type=" + this.type + ", appId=" + this.appId + ", gids=" + java.util.Arrays.toString(this.gids) + ", areGidsPerUser=" + this.areGidsPerUser + ")";
    }

    public Permission(@org.jetbrains.annotations.NotNull android.content.pm.PermissionInfo permissionInfo, boolean isReconciled, int type, int appId, @org.jetbrains.annotations.NotNull int[] gids, boolean areGidsPerUser) {
        this.permissionInfo = permissionInfo;
        this.isReconciled = isReconciled;
        this.type = type;
        this.appId = appId;
        this.gids = gids;
        this.areGidsPerUser = areGidsPerUser;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ Permission(android.content.pm.PermissionInfo permissionInfo, boolean z, int i, int i2, int[] iArr, boolean z2, int i3, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(permissionInfo, z, i, i2, r5, r6);
        int[] iArr2;
        boolean z3;
        if ((i3 & 16) == 0) {
            iArr2 = iArr;
        } else {
            iArr2 = libcore.util.EmptyArray.INT;
        }
        if ((i3 & 32) == 0) {
            z3 = z2;
        } else {
            z3 = false;
        }
    }

    @org.jetbrains.annotations.NotNull
    public final android.content.pm.PermissionInfo getPermissionInfo() {
        return this.permissionInfo;
    }

    public final boolean isReconciled() {
        return this.isReconciled;
    }

    public final int getType() {
        return this.type;
    }

    public final int getAppId() {
        return this.appId;
    }

    @org.jetbrains.annotations.NotNull
    public final int[] getGids() {
        return this.gids;
    }

    public final boolean getAreGidsPerUser() {
        return this.areGidsPerUser;
    }

    @org.jetbrains.annotations.NotNull
    public final int[] getGidsForUser(int userId) {
        if (this.areGidsPerUser) {
            int length = this.gids.length;
            int[] iArr = new int[length];
            for (int i = 0; i < length; i++) {
                iArr[i] = android.os.UserHandle.getUid(userId, this.gids[i]);
            }
            return iArr;
        }
        int[] iArr2 = this.gids;
        int[] copyOf = java.util.Arrays.copyOf(iArr2, iArr2.length);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
        return copyOf;
    }

    /* compiled from: Permission.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @org.jetbrains.annotations.NotNull
        public final java.lang.String typeToString(int type) {
            switch (type) {
                case 0:
                    return "TYPE_MANIFEST";
                case 1:
                    return "TYPE_CONFIG";
                case 2:
                    return "TYPE_DYNAMIC";
                default:
                    return java.lang.String.valueOf(type);
            }
        }
    }
}
