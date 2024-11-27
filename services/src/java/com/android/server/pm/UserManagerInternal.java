package com.android.server.pm;

/* loaded from: classes2.dex */
public abstract class UserManagerInternal {
    public static final int OWNER_TYPE_DEVICE_OWNER = 0;
    public static final int OWNER_TYPE_NO_OWNER = 3;
    public static final int OWNER_TYPE_PROFILE_OWNER = 1;
    public static final int OWNER_TYPE_PROFILE_OWNER_OF_ORGANIZATION_OWNED_DEVICE = 2;
    private static final java.lang.String PREFIX_USER_ASSIGNMENT_RESULT = "USER_ASSIGNMENT_RESULT_";
    private static final java.lang.String PREFIX_USER_START_MODE = "USER_START_MODE_";

    @com.android.internal.annotations.Keep
    public static final int USER_ASSIGNMENT_RESULT_FAILURE = -1;

    @com.android.internal.annotations.Keep
    public static final int USER_ASSIGNMENT_RESULT_SUCCESS_ALREADY_VISIBLE = 3;

    @com.android.internal.annotations.Keep
    public static final int USER_ASSIGNMENT_RESULT_SUCCESS_INVISIBLE = 2;

    @com.android.internal.annotations.Keep
    public static final int USER_ASSIGNMENT_RESULT_SUCCESS_VISIBLE = 1;

    @com.android.internal.annotations.Keep
    public static final int USER_START_MODE_BACKGROUND = 2;

    @com.android.internal.annotations.Keep
    public static final int USER_START_MODE_BACKGROUND_VISIBLE = 3;

    @com.android.internal.annotations.Keep
    public static final int USER_START_MODE_FOREGROUND = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OwnerType {
    }

    public @interface UserAssignmentResult {
    }

    public interface UserRestrictionsListener {
        void onUserRestrictionsChanged(int i, android.os.Bundle bundle, android.os.Bundle bundle2);
    }

    public @interface UserStartMode {
    }

    public interface UserVisibilityListener {
        void onUserVisibilityChanged(int i, boolean z);
    }

    public abstract void addUserLifecycleListener(com.android.server.pm.UserManagerInternal.UserLifecycleListener userLifecycleListener);

    public abstract void addUserRestrictionsListener(com.android.server.pm.UserManagerInternal.UserRestrictionsListener userRestrictionsListener);

    public abstract void addUserVisibilityListener(com.android.server.pm.UserManagerInternal.UserVisibilityListener userVisibilityListener);

    @com.android.server.pm.UserManagerInternal.UserAssignmentResult
    public abstract int assignUserToDisplayOnStart(int i, int i2, @com.android.server.pm.UserManagerInternal.UserStartMode int i3, int i4);

    public abstract boolean assignUserToExtraDisplay(int i, int i2);

    @android.annotation.NonNull
    public abstract android.content.pm.UserInfo createUserEvenWhenDisallowed(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, @android.annotation.Nullable java.lang.String[] strArr, @android.annotation.Nullable java.lang.Object obj) throws android.os.UserManager.CheckedUserOperationException;

    public abstract boolean exists(int i);

    public abstract int getBootUser(boolean z) throws android.os.UserManager.CheckedUserOperationException;

    public abstract int getCommunalProfileId();

    @android.annotation.Nullable
    public abstract int[] getDisplaysAssignedToUser(int i);

    @android.annotation.Nullable
    public abstract android.content.pm.LauncherUserInfo getLauncherUserInfo(int i);

    public abstract int getMainDisplayAssignedToUser(int i);

    public abstract int getMainUserId();

    @android.annotation.NonNull
    public abstract int[] getProfileIds(int i, boolean z);

    public abstract int getProfileParentId(int i);

    public abstract int getUserAssignedToDisplay(int i);

    public abstract int[] getUserIds();

    @android.annotation.Nullable
    public abstract android.content.pm.UserInfo getUserInfo(int i);

    @android.annotation.NonNull
    public abstract android.content.pm.UserInfo[] getUserInfos();

    @android.annotation.Nullable
    public abstract android.content.pm.UserProperties getUserProperties(int i);

    public abstract boolean getUserRestriction(int i, java.lang.String str);

    public abstract int[] getUserTypesForStatsd(int[] iArr);

    @android.annotation.NonNull
    public abstract java.util.List<android.content.pm.UserInfo> getUsers(boolean z);

    @android.annotation.NonNull
    public abstract java.util.List<android.content.pm.UserInfo> getUsers(boolean z, boolean z2, boolean z3);

    public abstract boolean hasUserRestriction(java.lang.String str, int i);

    @java.lang.Deprecated
    public abstract boolean isDeviceManaged();

    public abstract boolean isProfileAccessible(int i, int i2, java.lang.String str, boolean z);

    public abstract boolean isSettingRestrictedForUser(java.lang.String str, int i, java.lang.String str2, int i2);

    public abstract boolean isUserInitialized(int i);

    @java.lang.Deprecated
    public abstract boolean isUserManaged(int i);

    public abstract boolean isUserRunning(int i);

    public abstract boolean isUserUnlocked(int i);

    public abstract boolean isUserUnlockingOrUnlocked(int i);

    public abstract boolean isUserVisible(int i);

    public abstract boolean isUserVisible(int i, int i2);

    public abstract void onEphemeralUserStop(int i);

    public abstract void onSystemUserVisibilityChanged(boolean z);

    public abstract void removeAllUsers();

    public abstract boolean removeUserEvenWhenDisallowed(int i);

    public abstract void removeUserLifecycleListener(com.android.server.pm.UserManagerInternal.UserLifecycleListener userLifecycleListener);

    public abstract void removeUserRestrictionsListener(com.android.server.pm.UserManagerInternal.UserRestrictionsListener userRestrictionsListener);

    public abstract void removeUserState(int i);

    public abstract void removeUserVisibilityListener(com.android.server.pm.UserManagerInternal.UserVisibilityListener userVisibilityListener);

    public abstract void setDefaultCrossProfileIntentFilters(int i, int i2);

    @java.lang.Deprecated
    public abstract void setDeviceManaged(boolean z);

    public abstract void setDevicePolicyUserRestrictions(int i, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.Nullable com.android.server.pm.RestrictionsSet restrictionsSet, boolean z);

    public abstract void setForceEphemeralUsers(boolean z);

    public abstract void setUserIcon(int i, android.graphics.Bitmap bitmap);

    @java.lang.Deprecated
    public abstract void setUserManaged(int i, boolean z);

    public abstract void setUserRestriction(int i, @android.annotation.NonNull java.lang.String str, boolean z);

    public abstract void setUserState(int i, int i2);

    public abstract boolean shouldIgnorePrepareStorageErrors(int i);

    public abstract void unassignUserFromDisplayOnStop(int i);

    public abstract boolean unassignUserFromExtraDisplay(int i, int i2);

    public interface UserLifecycleListener {
        default void onUserCreated(android.content.pm.UserInfo userInfo, @android.annotation.Nullable java.lang.Object obj) {
        }

        default void onUserRemoved(android.content.pm.UserInfo userInfo) {
        }
    }

    public static java.lang.String userAssignmentResultToString(@com.android.server.pm.UserManagerInternal.UserAssignmentResult int i) {
        return android.util.DebugUtils.constantToString(com.android.server.pm.UserManagerInternal.class, PREFIX_USER_ASSIGNMENT_RESULT, i);
    }

    public static java.lang.String userStartModeToString(@com.android.server.pm.UserManagerInternal.UserStartMode int i) {
        return android.util.DebugUtils.constantToString(com.android.server.pm.UserManagerInternal.class, PREFIX_USER_START_MODE, i);
    }
}
