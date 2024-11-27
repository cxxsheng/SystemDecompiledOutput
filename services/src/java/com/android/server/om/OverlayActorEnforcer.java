package com.android.server.om;

/* loaded from: classes2.dex */
public class OverlayActorEnforcer {
    private final com.android.server.om.PackageManagerHelper mPackageManager;

    public enum ActorState {
        TARGET_NOT_FOUND,
        NO_PACKAGES_FOR_UID,
        MISSING_TARGET_OVERLAYABLE_NAME,
        MISSING_LEGACY_PERMISSION,
        ERROR_READING_OVERLAYABLE,
        UNABLE_TO_GET_TARGET_OVERLAYABLE,
        MISSING_OVERLAYABLE,
        INVALID_OVERLAYABLE_ACTOR_NAME,
        NO_NAMED_ACTORS,
        MISSING_NAMESPACE,
        MISSING_ACTOR_NAME,
        ACTOR_NOT_FOUND,
        ACTOR_NOT_PREINSTALLED,
        INVALID_ACTOR,
        ALLOWED
    }

    static android.util.Pair<java.lang.String, com.android.server.om.OverlayActorEnforcer.ActorState> getPackageNameForActor(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> map) {
        android.net.Uri parse = android.net.Uri.parse(str);
        java.lang.String scheme = parse.getScheme();
        java.util.List<java.lang.String> pathSegments = parse.getPathSegments();
        if (!"overlay".equals(scheme) || com.android.internal.util.CollectionUtils.size(pathSegments) != 1) {
            return android.util.Pair.create(null, com.android.server.om.OverlayActorEnforcer.ActorState.INVALID_OVERLAYABLE_ACTOR_NAME);
        }
        if (map.isEmpty()) {
            return android.util.Pair.create(null, com.android.server.om.OverlayActorEnforcer.ActorState.NO_NAMED_ACTORS);
        }
        java.util.Map<java.lang.String, java.lang.String> map2 = map.get(parse.getAuthority());
        if (com.android.internal.util.ArrayUtils.isEmpty(map2)) {
            return android.util.Pair.create(null, com.android.server.om.OverlayActorEnforcer.ActorState.MISSING_NAMESPACE);
        }
        java.lang.String str2 = map2.get(pathSegments.get(0));
        if (android.text.TextUtils.isEmpty(str2)) {
            return android.util.Pair.create(null, com.android.server.om.OverlayActorEnforcer.ActorState.MISSING_ACTOR_NAME);
        }
        return android.util.Pair.create(str2, com.android.server.om.OverlayActorEnforcer.ActorState.ALLOWED);
    }

    public OverlayActorEnforcer(@android.annotation.NonNull com.android.server.om.PackageManagerHelper packageManagerHelper) {
        this.mPackageManager = packageManagerHelper;
    }

    void enforceActor(@android.annotation.NonNull android.content.om.OverlayInfo overlayInfo, @android.annotation.NonNull java.lang.String str, int i, int i2) throws java.lang.SecurityException {
        java.lang.String str2;
        com.android.server.om.OverlayActorEnforcer.ActorState isAllowedActor = isAllowedActor(str, overlayInfo, i, i2);
        if (isAllowedActor == com.android.server.om.OverlayActorEnforcer.ActorState.ALLOWED) {
            return;
        }
        java.lang.String str3 = overlayInfo.targetOverlayableName;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("UID");
        sb.append(i);
        sb.append(" is not allowed to call ");
        sb.append(str);
        sb.append(" for ");
        if (android.text.TextUtils.isEmpty(str3)) {
            str2 = "";
        } else {
            str2 = str3 + " in ";
        }
        sb.append(str2);
        sb.append(overlayInfo.targetPackageName);
        sb.append(" for user ");
        sb.append(i2);
        java.lang.String sb2 = sb.toString();
        android.util.Slog.w("OverlayManager", sb2 + " because " + isAllowedActor);
        throw new java.lang.SecurityException(sb2);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public com.android.server.om.OverlayActorEnforcer.ActorState isAllowedActor(java.lang.String str, android.content.om.OverlayInfo overlayInfo, int i, int i2) {
        switch (i) {
            case 0:
            case 1000:
                return com.android.server.om.OverlayActorEnforcer.ActorState.ALLOWED;
            default:
                java.lang.String str2 = overlayInfo.targetPackageName;
                com.android.server.pm.pkg.PackageState packageStateForUser = this.mPackageManager.getPackageStateForUser(str2, i2);
                com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
                if (androidPackage == null) {
                    return com.android.server.om.OverlayActorEnforcer.ActorState.TARGET_NOT_FOUND;
                }
                if (androidPackage.isDebuggable()) {
                    return com.android.server.om.OverlayActorEnforcer.ActorState.ALLOWED;
                }
                java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
                if (com.android.internal.util.ArrayUtils.isEmpty(packagesForUid)) {
                    return com.android.server.om.OverlayActorEnforcer.ActorState.NO_PACKAGES_FOR_UID;
                }
                if (com.android.internal.util.ArrayUtils.contains(packagesForUid, str2)) {
                    return com.android.server.om.OverlayActorEnforcer.ActorState.ALLOWED;
                }
                java.lang.String str3 = overlayInfo.targetOverlayableName;
                if (android.text.TextUtils.isEmpty(str3)) {
                    try {
                        if (this.mPackageManager.doesTargetDefineOverlayable(str2, i2)) {
                            return com.android.server.om.OverlayActorEnforcer.ActorState.MISSING_TARGET_OVERLAYABLE_NAME;
                        }
                        try {
                            this.mPackageManager.enforcePermission("android.permission.CHANGE_OVERLAY_PACKAGES", str);
                            return com.android.server.om.OverlayActorEnforcer.ActorState.ALLOWED;
                        } catch (java.lang.SecurityException e) {
                            return com.android.server.om.OverlayActorEnforcer.ActorState.MISSING_LEGACY_PERMISSION;
                        }
                    } catch (java.io.IOException e2) {
                        return com.android.server.om.OverlayActorEnforcer.ActorState.ERROR_READING_OVERLAYABLE;
                    }
                }
                try {
                    android.content.om.OverlayableInfo overlayableForTarget = this.mPackageManager.getOverlayableForTarget(str2, str3, i2);
                    if (overlayableForTarget == null) {
                        return com.android.server.om.OverlayActorEnforcer.ActorState.MISSING_OVERLAYABLE;
                    }
                    java.lang.String str4 = overlayableForTarget.actor;
                    if (android.text.TextUtils.isEmpty(str4)) {
                        try {
                            this.mPackageManager.enforcePermission("android.permission.CHANGE_OVERLAY_PACKAGES", str);
                            return com.android.server.om.OverlayActorEnforcer.ActorState.ALLOWED;
                        } catch (java.lang.SecurityException e3) {
                            return com.android.server.om.OverlayActorEnforcer.ActorState.MISSING_LEGACY_PERMISSION;
                        }
                    }
                    android.util.Pair<java.lang.String, com.android.server.om.OverlayActorEnforcer.ActorState> packageNameForActor = getPackageNameForActor(str4, this.mPackageManager.getNamedActors());
                    com.android.server.om.OverlayActorEnforcer.ActorState actorState = (com.android.server.om.OverlayActorEnforcer.ActorState) packageNameForActor.second;
                    if (actorState != com.android.server.om.OverlayActorEnforcer.ActorState.ALLOWED) {
                        return actorState;
                    }
                    java.lang.String str5 = (java.lang.String) packageNameForActor.first;
                    com.android.server.pm.pkg.PackageState packageStateForUser2 = this.mPackageManager.getPackageStateForUser(str5, i2);
                    if (packageStateForUser2 == null || packageStateForUser2.getAndroidPackage() == null) {
                        return com.android.server.om.OverlayActorEnforcer.ActorState.ACTOR_NOT_FOUND;
                    }
                    if (!packageStateForUser2.isSystem()) {
                        return com.android.server.om.OverlayActorEnforcer.ActorState.ACTOR_NOT_PREINSTALLED;
                    }
                    if (com.android.internal.util.ArrayUtils.contains(packagesForUid, str5)) {
                        return com.android.server.om.OverlayActorEnforcer.ActorState.ALLOWED;
                    }
                    return com.android.server.om.OverlayActorEnforcer.ActorState.INVALID_ACTOR;
                } catch (java.io.IOException e4) {
                    return com.android.server.om.OverlayActorEnforcer.ActorState.UNABLE_TO_GET_TARGET_OVERLAYABLE;
                }
        }
    }
}
