package com.android.server.permission.access.permission;

/* compiled from: PermissionFlags.kt */
/* loaded from: classes2.dex */
public final class PermissionFlags {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.permission.PermissionFlags INSTANCE = new com.android.server.permission.access.permission.PermissionFlags();

    private PermissionFlags() {
    }

    public final boolean isPermissionGranted(int flags) {
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 1)) {
            return true;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 2)) {
            return false;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 4) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 1024) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 2048)) {
            return true;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 262144)) {
            return false;
        }
        return com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 16);
    }

    public final boolean isAppOpGranted(int flags) {
        return (!isPermissionGranted(flags) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 262144) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 1048576)) ? false : true;
    }

    public final int toApiFlags(int flags) {
        int apiFlags = 0;
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 32)) {
            apiFlags = 0 | 1;
        }
        int i = 64;
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 64)) {
            apiFlags |= 2;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 128)) {
            apiFlags |= 4;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 256)) {
            apiFlags |= 16;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 512)) {
            apiFlags |= 32;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 4096)) {
            if (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 1024)) {
                i = 128;
            }
            apiFlags = i | apiFlags;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 8192)) {
            apiFlags |= 256;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 16384)) {
            apiFlags |= 512;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 32768)) {
            apiFlags |= 2048;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 65536)) {
            apiFlags |= 4096;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 131072)) {
            apiFlags |= 8192;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 262144) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 524288)) {
            apiFlags |= 16384;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 8)) {
            apiFlags |= 32768;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 1048576)) {
            apiFlags |= 8;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 2097152)) {
            apiFlags |= 65536;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 4194304)) {
            apiFlags |= 131072;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 8388608)) {
            return apiFlags | 524288;
        }
        return apiFlags;
    }

    public final int updateRuntimePermissionGranted(int flags, boolean isGranted) {
        return isGranted ? flags | 16 : com.android.server.permission.access.util.IntExtensionsKt.andInv(flags, 16);
    }

    public final int updateFlags(@org.jetbrains.annotations.NotNull com.android.server.permission.access.permission.Permission permission, int flags, int apiFlagMask, int apiFlagValues) {
        int oldApiFlags = toApiFlags(flags);
        int newApiFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(oldApiFlags, apiFlagMask) | (apiFlagValues & apiFlagMask);
        return fromApiFlags(newApiFlags, permission, flags);
    }

    private final int fromApiFlags(int apiFlags, com.android.server.permission.access.permission.Permission permission, int oldFlags) {
        int flags = 0 | (oldFlags & 1) | (oldFlags & 2) | (oldFlags & 4);
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 32768)) {
            flags |= 8;
        }
        int flags2 = flags | (oldFlags & 16);
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 1)) {
            flags2 |= 32;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 2)) {
            flags2 |= 64;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 4)) {
            flags2 |= 128;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 16)) {
            flags2 |= 256;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 32)) {
            flags2 |= 512;
        }
        int flags3 = flags2 | (oldFlags & 1024) | (oldFlags & 2048);
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 64) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 128)) {
            flags3 |= 4096;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 256)) {
            flags3 |= 8192;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 512)) {
            flags3 |= 16384;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 2048)) {
            flags3 |= 32768;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 4096)) {
            flags3 |= 65536;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 8192)) {
            flags3 |= 131072;
        }
        if (!com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(flags3, 229376)) {
            if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 4)) {
                flags3 |= 262144;
            }
            if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 8)) {
                flags3 |= 524288;
            }
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 8)) {
            flags3 |= 1048576;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 65536)) {
            flags3 |= 2097152;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 131072)) {
            flags3 |= 4194304;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(apiFlags, 524288)) {
            return flags3 | 8388608;
        }
        return flags3;
    }

    @org.jetbrains.annotations.NotNull
    public final java.lang.String flagToString(int flag) {
        switch (flag) {
            case 1:
                return "INSTALL_GRANTED";
            case 2:
                return "INSTALL_REVOKED";
            case 4:
                return "PROTECTION_GRANTED";
            case 8:
                return "ROLE";
            case 16:
                return "RUNTIME_GRANTED";
            case 32:
                return "USER_SET";
            case 64:
                return "USER_FIXED";
            case 128:
                return "POLICY_FIXED";
            case 256:
                return "SYSTEM_FIXED";
            case 512:
                return "PREGRANT";
            case 1024:
                return "LEGACY_GRANTED";
            case 2048:
                return "IMPLICIT_GRANTED";
            case 4096:
                return "IMPLICIT";
            case 8192:
                return "USER_SENSITIVE_WHEN_GRANTED";
            case 16384:
                return "USER_SENSITIVE_WHEN_REVOKED";
            case 32768:
                return "INSTALLER_EXEMPT";
            case 65536:
                return "SYSTEM_EXEMPT";
            case 131072:
                return "UPGRADE_EXEMPT";
            case 262144:
                return "RESTRICTION_REVOKED";
            case 524288:
                return "SOFT_RESTRICTED";
            case 1048576:
                return "APP_OP_REVOKED";
            case 2097152:
                return "ONE_TIME";
            case 4194304:
                return "HIBERNATION";
            case 8388608:
                return "USER_SELECTED";
            default:
                java.lang.String upperCase = com.android.server.permission.jarjar.kotlin.text.UStringsKt.m5924toStringV7xB4Y4(com.android.server.permission.jarjar.kotlin.UInt.m5923constructorimpl(flag), 16).toUpperCase(java.util.Locale.ROOT);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                return "0x" + upperCase;
        }
    }

    @org.jetbrains.annotations.NotNull
    public final java.lang.String toString(int flags) {
        int flags$iv = flags;
        java.lang.StringBuilder $this$flagsToString_u24lambda_u240$iv = new java.lang.StringBuilder();
        $this$flagsToString_u24lambda_u240$iv.append("[");
        while (flags$iv != 0) {
            int flag$iv = 1 << java.lang.Integer.numberOfTrailingZeros(flags$iv);
            flags$iv = com.android.server.permission.access.util.IntExtensionsKt.andInv(flags$iv, flag$iv);
            $this$flagsToString_u24lambda_u240$iv.append(INSTANCE.flagToString(flag$iv));
            if (flags$iv != 0) {
                $this$flagsToString_u24lambda_u240$iv.append('|');
            }
        }
        $this$flagsToString_u24lambda_u240$iv.append("]");
        java.lang.String sb = $this$flagsToString_u24lambda_u240$iv.toString();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
        return sb;
    }
}
