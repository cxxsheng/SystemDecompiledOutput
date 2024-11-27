package com.android.server.pm;

/* loaded from: classes2.dex */
public final class UserVisibilityMediator implements android.util.Dumpable {
    public static final int ALWAYS_VISIBLE_PROFILE_GROUP_ID = -1;

    @com.android.internal.annotations.VisibleForTesting
    static final int INITIAL_CURRENT_USER_ID = 0;
    private static final java.lang.String PREFIX_SECONDARY_DISPLAY_MAPPING = "SECONDARY_DISPLAY_MAPPING_";
    public static final int SECONDARY_DISPLAY_MAPPING_FAILED = -1;
    public static final int SECONDARY_DISPLAY_MAPPING_NEEDED = 1;
    public static final int SECONDARY_DISPLAY_MAPPING_NOT_NEEDED = 2;
    private static final boolean VERBOSE = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mCurrentUserId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private final android.util.SparseIntArray mExtraDisplaysAssignedToUsers;
    private final android.os.Handler mHandler;
    final java.util.concurrent.CopyOnWriteArrayList<com.android.server.pm.UserManagerInternal.UserVisibilityListener> mListeners;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private final java.util.List<java.lang.Integer> mStartedInvisibleProfileUserIds;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mStartedVisibleProfileGroupIds;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private final android.util.SparseIntArray mUsersAssignedToDisplayOnStart;
    private final boolean mVisibleBackgroundUserOnDefaultDisplayEnabled;
    private final boolean mVisibleBackgroundUsersEnabled;
    private static final java.lang.String TAG = com.android.server.pm.UserVisibilityMediator.class.getSimpleName();
    private static final boolean DBG = android.util.Log.isLoggable(TAG, 3);

    public @interface SecondaryDisplayMappingStatus {
    }

    UserVisibilityMediator(android.os.Handler handler) {
        this(android.os.UserManager.isVisibleBackgroundUsersEnabled(), android.os.UserManager.isVisibleBackgroundUsersOnDefaultDisplayEnabled(), handler);
    }

    @com.android.internal.annotations.VisibleForTesting
    UserVisibilityMediator(boolean z, boolean z2, android.os.Handler handler) {
        this.mLock = new java.lang.Object();
        this.mCurrentUserId = 0;
        this.mStartedVisibleProfileGroupIds = new android.util.SparseIntArray();
        this.mListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mVisibleBackgroundUsersEnabled = z;
        if (z2 && !z) {
            throw new java.lang.IllegalArgumentException("Cannot have visibleBackgroundUserOnDefaultDisplayEnabled without visibleBackgroundUsersOnDisplaysEnabled");
        }
        this.mVisibleBackgroundUserOnDefaultDisplayEnabled = z2;
        if (this.mVisibleBackgroundUsersEnabled) {
            this.mUsersAssignedToDisplayOnStart = new android.util.SparseIntArray();
            this.mExtraDisplaysAssignedToUsers = new android.util.SparseIntArray();
        } else {
            this.mUsersAssignedToDisplayOnStart = null;
            this.mExtraDisplaysAssignedToUsers = null;
        }
        this.mStartedInvisibleProfileUserIds = DBG ? new java.util.ArrayList(4) : null;
        this.mHandler = handler;
        this.mStartedVisibleProfileGroupIds.put(0, 0);
        if (DBG) {
            com.android.server.utils.Slogf.i(TAG, "UserVisibilityMediator created with DBG on");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c2 A[Catch: all -> 0x005b, TryCatch #0 {all -> 0x005b, blocks: (B:7:0x0042, B:9:0x004a, B:14:0x0066, B:16:0x006e, B:18:0x0080, B:21:0x0082, B:22:0x0086, B:23:0x0089, B:24:0x00db, B:25:0x00de, B:26:0x0117, B:27:0x011b, B:32:0x00ee, B:34:0x00f2, B:35:0x00fa, B:37:0x00fe, B:38:0x0111, B:39:0x0099, B:41:0x009d, B:43:0x00a3, B:44:0x00bc, B:45:0x00be, B:47:0x00c2, B:48:0x00d5, B:50:0x0134), top: B:6:0x0042 }] */
    @com.android.server.pm.UserManagerInternal.UserAssignmentResult
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int assignUserToDisplayOnStart(int i, int i2, @com.android.server.pm.UserManagerInternal.UserStartMode int i3, int i4, boolean z) {
        com.android.internal.util.Preconditions.checkArgument(!isSpecialUserId(i), "user id cannot be generic: %d", new java.lang.Object[]{java.lang.Integer.valueOf(i)});
        validateUserStartMode(i3);
        int resolveProfileGroupId = resolveProfileGroupId(i, i2, z);
        if (DBG) {
            com.android.server.utils.Slogf.d(TAG, "assignUserToDisplayOnStart(%d, %d, %s, %d): actualProfileGroupId=%d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), com.android.server.pm.UserManagerInternal.userStartModeToString(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(resolveProfileGroupId));
        }
        synchronized (this.mLock) {
            try {
                int userVisibilityOnStartLocked = getUserVisibilityOnStartLocked(i, resolveProfileGroupId, i3, i4);
                if (DBG) {
                    com.android.server.utils.Slogf.d(TAG, "result of getUserVisibilityOnStartLocked(%s)", com.android.server.pm.UserManagerInternal.userAssignmentResultToString(userVisibilityOnStartLocked));
                }
                if (userVisibilityOnStartLocked == -1 || userVisibilityOnStartLocked == 3) {
                    return userVisibilityOnStartLocked;
                }
                int canAssignUserToDisplayLocked = canAssignUserToDisplayLocked(i, resolveProfileGroupId, i3, i4);
                if (DBG) {
                    com.android.server.utils.Slogf.d(TAG, "mapping result: %s", secondaryDisplayMappingStatusToString(canAssignUserToDisplayLocked));
                }
                if (canAssignUserToDisplayLocked == -1) {
                    return -1;
                }
                android.util.IntArray visibleUsers = getVisibleUsers();
                switch (i3) {
                    case 1:
                        this.mCurrentUserId = i;
                        if (DBG) {
                            com.android.server.utils.Slogf.d(TAG, "adding visible user / profile group id mapping (%d -> %d)", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(resolveProfileGroupId));
                        }
                        this.mStartedVisibleProfileGroupIds.put(i, resolveProfileGroupId);
                        break;
                    case 2:
                        if (this.mStartedInvisibleProfileUserIds != null && isProfile(i, resolveProfileGroupId)) {
                            com.android.server.utils.Slogf.d(TAG, "adding user %d to list of invisible profiles", java.lang.Integer.valueOf(i));
                            this.mStartedInvisibleProfileUserIds.add(java.lang.Integer.valueOf(i));
                            break;
                        }
                        break;
                    case 3:
                        if (DBG) {
                        }
                        this.mStartedVisibleProfileGroupIds.put(i, resolveProfileGroupId);
                        break;
                    default:
                        com.android.server.utils.Slogf.wtf(TAG, "invalid userStartMode passed to assignUserToDisplayOnStart: %d", java.lang.Integer.valueOf(i3));
                        break;
                }
                switch (canAssignUserToDisplayLocked) {
                    case 1:
                        if (DBG) {
                            com.android.server.utils.Slogf.d(TAG, "adding user / display mapping (%d -> %d)", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i4));
                        }
                        this.mUsersAssignedToDisplayOnStart.put(i, i4);
                        break;
                    case 2:
                        if (DBG) {
                            com.android.server.utils.Slogf.d(TAG, "don't need to update mUsersOnSecondaryDisplays");
                            break;
                        }
                        break;
                    default:
                        com.android.server.utils.Slogf.wtf(TAG, "invalid resut from canAssignUserToDisplayLocked: %d", java.lang.Integer.valueOf(canAssignUserToDisplayLocked));
                        break;
                }
                dispatchVisibilityChanged(visibleUsers, getVisibleUsers());
                if (DBG) {
                    com.android.server.utils.Slogf.d(TAG, "returning %s", com.android.server.pm.UserManagerInternal.userAssignmentResultToString(userVisibilityOnStartLocked));
                }
                return userVisibilityOnStartLocked;
            } finally {
            }
        }
    }

    private int resolveProfileGroupId(int i, int i2, boolean z) {
        if (z) {
            return -1;
        }
        if (i2 == -10000) {
            return i;
        }
        return i2;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.server.pm.UserManagerInternal.UserAssignmentResult
    private int getUserVisibilityOnStartLocked(int i, int i2, @com.android.server.pm.UserManagerInternal.UserStartMode int i3, int i4) {
        if (i3 == 2 && i4 != 0) {
            com.android.server.utils.Slogf.wtf(TAG, "cannot start user (%d) as BACKGROUND_USER on secondary display (%d) (it should be BACKGROUND_USER_VISIBLE", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i4));
            return -1;
        }
        boolean z = i3 == 3;
        if (i4 == 0 && z) {
            if (this.mVisibleBackgroundUserOnDefaultDisplayEnabled && isCurrentUserLocked(i)) {
                com.android.server.utils.Slogf.wtf(TAG, "trying to start current user (%d) visible in background on default display", java.lang.Integer.valueOf(i));
                return 3;
            }
            if (!this.mVisibleBackgroundUserOnDefaultDisplayEnabled && !isProfile(i, i2)) {
                com.android.server.utils.Slogf.wtf(TAG, "cannot start full user (%d) visible on default display", java.lang.Integer.valueOf(i));
                return -1;
            }
        }
        boolean z2 = i3 == 1;
        if (i4 != 0) {
            if (z2) {
                com.android.server.utils.Slogf.w(TAG, "getUserVisibilityOnStartLocked(%d, %d, %s, %d) failed: cannot start foreground user on secondary display", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), com.android.server.pm.UserManagerInternal.userStartModeToString(i3), java.lang.Integer.valueOf(i4));
                return -1;
            }
            if (!this.mVisibleBackgroundUsersEnabled) {
                com.android.server.utils.Slogf.w(TAG, "getUserVisibilityOnStartLocked(%d, %d, %s, %d) failed: called on device that doesn't support multiple users on multiple displays", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), com.android.server.pm.UserManagerInternal.userStartModeToString(i3), java.lang.Integer.valueOf(i4));
                return -1;
            }
        }
        if (isProfile(i, i2)) {
            if (i4 != 0) {
                com.android.server.utils.Slogf.w(TAG, "canStartUserLocked(%d, %d, %s, %d) failed: cannot start profile user on secondary display", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), com.android.server.pm.UserManagerInternal.userStartModeToString(i3), java.lang.Integer.valueOf(i4));
                return -1;
            }
            switch (i3) {
                case 1:
                    com.android.server.utils.Slogf.w(TAG, "startUser(%d, %d, %s, %d) failed: cannot start profile user in foreground", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), com.android.server.pm.UserManagerInternal.userStartModeToString(i3), java.lang.Integer.valueOf(i4));
                    return -1;
                case 2:
                    return 2;
                case 3:
                    if (isParentVisibleOnDisplay(i2, i4)) {
                        return 1;
                    }
                    com.android.server.utils.Slogf.w(TAG, "getUserVisibilityOnStartLocked(%d, %d, %s, %d) failed: cannot start profile user visible when its parent is not visible in that display", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), com.android.server.pm.UserManagerInternal.userStartModeToString(i3), java.lang.Integer.valueOf(i4));
                    return -1;
            }
        }
        if (this.mUsersAssignedToDisplayOnStart != null && isUserAssignedToDisplayOnStartLocked(i, i4)) {
            if (DBG) {
                com.android.server.utils.Slogf.d(TAG, "full user %d is already visible on display %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i4));
            }
            return 3;
        }
        return (z2 || i4 != 0 || (z && this.mVisibleBackgroundUserOnDefaultDisplayEnabled)) ? 1 : 2;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.server.pm.UserVisibilityMediator.SecondaryDisplayMappingStatus
    private int canAssignUserToDisplayLocked(int i, int i2, @com.android.server.pm.UserManagerInternal.UserStartMode int i3, int i4) {
        boolean z;
        if (i4 == 0) {
            if (this.mVisibleBackgroundUserOnDefaultDisplayEnabled && i3 == 3) {
                int userStartedOnDisplay = getUserStartedOnDisplay(0);
                if (userStartedOnDisplay != -10000 && userStartedOnDisplay != i2) {
                    com.android.server.utils.Slogf.w(TAG, "canAssignUserToDisplayLocked(): cannot start user %d visible on default display because user %d already did so", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(userStartedOnDisplay));
                    return -1;
                }
                z = true;
            } else {
                z = false;
            }
            if (!z && this.mVisibleBackgroundUsersEnabled && isProfile(i, i2)) {
                z = true;
            }
            if (!z) {
                if (DBG) {
                    com.android.server.utils.Slogf.d(TAG, "Ignoring mapping for default display for user %d starting as %s", java.lang.Integer.valueOf(i), com.android.server.pm.UserManagerInternal.userStartModeToString(i3));
                }
                return 2;
            }
        }
        if (i == 0) {
            com.android.server.utils.Slogf.w(TAG, "Cannot assign system user to secondary display (%d)", java.lang.Integer.valueOf(i4));
            return -1;
        }
        if (i4 == -1) {
            com.android.server.utils.Slogf.w(TAG, "Cannot assign to INVALID_DISPLAY (%d)", java.lang.Integer.valueOf(i4));
            return -1;
        }
        if (i == this.mCurrentUserId) {
            com.android.server.utils.Slogf.w(TAG, "Cannot assign current user (%d) to other displays", java.lang.Integer.valueOf(i));
            return -1;
        }
        if (isProfile(i, i2)) {
            if (i4 != 0) {
                com.android.server.utils.Slogf.w(TAG, "Profile user can only be started in the default display");
                return -1;
            }
            if (DBG) {
                com.android.server.utils.Slogf.d(TAG, "Don't need to map profile user %d to default display", java.lang.Integer.valueOf(i));
            }
            return 2;
        }
        if (this.mUsersAssignedToDisplayOnStart == null) {
            com.android.server.utils.Slogf.wtf(TAG, "canAssignUserToDisplayLocked(%d, %d, %d, %d) is trying to check mUsersAssignedToDisplayOnStart when it's not set", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4));
            return -1;
        }
        for (int i5 = 0; i5 < this.mUsersAssignedToDisplayOnStart.size(); i5++) {
            int keyAt = this.mUsersAssignedToDisplayOnStart.keyAt(i5);
            int valueAt = this.mUsersAssignedToDisplayOnStart.valueAt(i5);
            if (DBG) {
                com.android.server.utils.Slogf.d(TAG, "%d: assignedUserId=%d, assignedDisplayId=%d", java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(keyAt), java.lang.Integer.valueOf(valueAt));
            }
            if (i4 == valueAt) {
                com.android.server.utils.Slogf.w(TAG, "Cannot assign user %d to display %d because such display is already assigned to user %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(keyAt));
                return -1;
            }
            if (i == keyAt) {
                com.android.server.utils.Slogf.w(TAG, "Cannot assign user %d to display %d because such user is as already assigned to display %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(keyAt));
                return -1;
            }
        }
        return 1;
    }

    public boolean assignUserToExtraDisplay(int i, int i2) {
        if (DBG) {
            com.android.server.utils.Slogf.d(TAG, "assignUserToExtraDisplay(%d, %d)", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        if (!this.mVisibleBackgroundUsersEnabled) {
            com.android.server.utils.Slogf.w(TAG, "assignUserToExtraDisplay(%d, %d): called when not supported", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            return false;
        }
        if (i2 == -1) {
            com.android.server.utils.Slogf.w(TAG, "assignUserToExtraDisplay(%d, %d): called with INVALID_DISPLAY", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            return false;
        }
        if (i2 == 0) {
            com.android.server.utils.Slogf.w(TAG, "assignUserToExtraDisplay(%d, %d): DEFAULT_DISPLAY is automatically assigned to current user", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (!isUserVisible(i)) {
                    com.android.server.utils.Slogf.w(TAG, "assignUserToExtraDisplay(%d, %d): failed because user is not visible", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                    return false;
                }
                if (isStartedVisibleProfileLocked(i)) {
                    com.android.server.utils.Slogf.w(TAG, "assignUserToExtraDisplay(%d, %d): failed because user is a profile", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                    return false;
                }
                if (this.mExtraDisplaysAssignedToUsers.get(i2, com.android.server.am.ProcessList.INVALID_ADJ) == i) {
                    com.android.server.utils.Slogf.w(TAG, "assignUserToExtraDisplay(%d, %d): failed because user is already assigned to that display", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                    return false;
                }
                int userStartedOnDisplay = getUserStartedOnDisplay(i2);
                if (userStartedOnDisplay != -10000) {
                    com.android.server.utils.Slogf.w(TAG, "assignUserToExtraDisplay(%d, %d): failed because display was assigned to user %d on start", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(userStartedOnDisplay));
                    return false;
                }
                int i3 = this.mExtraDisplaysAssignedToUsers.get(i, com.android.server.am.ProcessList.INVALID_ADJ);
                if (i3 != -10000) {
                    com.android.server.utils.Slogf.w(TAG, "assignUserToExtraDisplay(%d, %d): failed because user %d was already assigned that extra display", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3));
                    return false;
                }
                if (DBG) {
                    com.android.server.utils.Slogf.d(TAG, "addding %d -> %d to mExtraDisplaysAssignedToUsers", java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i));
                }
                this.mExtraDisplaysAssignedToUsers.put(i2, i);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean unassignUserFromExtraDisplay(int i, int i2) {
        if (DBG) {
            com.android.server.utils.Slogf.d(TAG, "unassignUserFromExtraDisplay(%d, %d)", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        if (!this.mVisibleBackgroundUsersEnabled) {
            com.android.server.utils.Slogf.w(TAG, "unassignUserFromExtraDisplay(%d, %d): called when not supported", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            return false;
        }
        synchronized (this.mLock) {
            try {
                int i3 = this.mExtraDisplaysAssignedToUsers.get(i2, com.android.server.am.ProcessList.INVALID_ADJ);
                if (i3 == -10000) {
                    com.android.server.utils.Slogf.w(TAG, "unassignUserFromExtraDisplay(%d, %d): not assigned to any user", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                    return false;
                }
                if (i3 != i) {
                    com.android.server.utils.Slogf.w(TAG, "unassignUserFromExtraDisplay(%d, %d): was assigned to user %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3));
                    return false;
                }
                if (DBG) {
                    com.android.server.utils.Slogf.d(TAG, "removing %d from map", java.lang.Integer.valueOf(i2));
                }
                this.mExtraDisplaysAssignedToUsers.delete(i2);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unassignUserFromDisplayOnStop(int i) {
        android.util.IntArray visibleUsers;
        android.util.IntArray visibleUsers2;
        if (DBG) {
            com.android.server.utils.Slogf.d(TAG, "unassignUserFromDisplayOnStop(%d)", java.lang.Integer.valueOf(i));
        }
        synchronized (this.mLock) {
            visibleUsers = getVisibleUsers();
            unassignUserFromAllDisplaysOnStopLocked(i);
            visibleUsers2 = getVisibleUsers();
        }
        dispatchVisibilityChanged(visibleUsers, visibleUsers2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unassignUserFromAllDisplaysOnStopLocked(int i) {
        if (DBG) {
            com.android.server.utils.Slogf.d(TAG, "Removing %d from mStartedVisibleProfileGroupIds (%s)", java.lang.Integer.valueOf(i), this.mStartedVisibleProfileGroupIds);
        }
        this.mStartedVisibleProfileGroupIds.delete(i);
        if (this.mStartedInvisibleProfileUserIds != null) {
            com.android.server.utils.Slogf.d(TAG, "Removing %d from list of invisible profiles", java.lang.Integer.valueOf(i));
            this.mStartedInvisibleProfileUserIds.remove(java.lang.Integer.valueOf(i));
        }
        if (!this.mVisibleBackgroundUsersEnabled) {
            return;
        }
        if (DBG) {
            com.android.server.utils.Slogf.d(TAG, "Removing user %d from mUsersOnDisplaysMap (%s)", java.lang.Integer.valueOf(i), this.mUsersAssignedToDisplayOnStart);
        }
        this.mUsersAssignedToDisplayOnStart.delete(i);
        for (int size = this.mExtraDisplaysAssignedToUsers.size() - 1; size >= 0; size--) {
            if (this.mExtraDisplaysAssignedToUsers.valueAt(size) == i) {
                if (DBG) {
                    com.android.server.utils.Slogf.d(TAG, "Removing display %d from mExtraDisplaysAssignedToUsers (%s)", java.lang.Integer.valueOf(this.mExtraDisplaysAssignedToUsers.keyAt(size)), this.mExtraDisplaysAssignedToUsers);
                }
                this.mExtraDisplaysAssignedToUsers.removeAt(size);
            }
        }
    }

    public boolean isUserVisible(int i) {
        int i2;
        if (isCurrentUserOrRunningProfileOfCurrentUser(i)) {
            return true;
        }
        if (!this.mVisibleBackgroundUsersEnabled) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                synchronized (this.mLock) {
                    i2 = this.mStartedVisibleProfileGroupIds.get(i, com.android.server.am.ProcessList.INVALID_ADJ);
                }
                if (isProfile(i, i2)) {
                    return isUserAssignedToDisplayOnStartLocked(i2);
                }
                return isUserAssignedToDisplayOnStartLocked(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isUserAssignedToDisplayOnStartLocked(int i) {
        return this.mUsersAssignedToDisplayOnStart.indexOfKey(i) >= 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isUserAssignedToDisplayOnStartLocked(int i, int i2) {
        if (this.mUsersAssignedToDisplayOnStart != null) {
            return i2 != -1 && this.mUsersAssignedToDisplayOnStart.get(i, -1) == i2;
        }
        com.android.server.utils.Slogf.wtf(TAG, "isUserAssignedToDisplayOnStartLocked(%d, %d): called when mUsersAssignedToDisplayOnStart is null", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        return false;
    }

    private boolean isParentVisibleOnDisplay(int i, int i2) {
        if (i == -1) {
            return true;
        }
        return isUserVisible(i, i2);
    }

    public boolean isUserVisible(int i, int i2) {
        int i3;
        if (i2 == -1) {
            return false;
        }
        if (isCurrentUserOrRunningProfileOfCurrentUser(i) && (i2 == 0 || !this.mVisibleBackgroundUsersEnabled)) {
            return true;
        }
        if (!this.mVisibleBackgroundUsersEnabled) {
            if (DBG) {
                com.android.server.utils.Slogf.d(TAG, "isUserVisible(%d, %d): returning false as device does not support visible background users", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            }
            return false;
        }
        synchronized (this.mLock) {
            try {
                synchronized (this.mLock) {
                    i3 = this.mStartedVisibleProfileGroupIds.get(i, com.android.server.am.ProcessList.INVALID_ADJ);
                }
                if (isProfile(i, i3)) {
                    return isFullUserVisibleOnBackgroundLocked(i3, i2);
                }
                return isFullUserVisibleOnBackgroundLocked(i, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isFullUserVisibleOnBackgroundLocked(int i, int i2) {
        return this.mUsersAssignedToDisplayOnStart.get(i, -1) == i2 || this.mExtraDisplaysAssignedToUsers.get(i2, com.android.server.am.ProcessList.INVALID_ADJ) == i;
    }

    public int getMainDisplayAssignedToUser(int i) {
        int i2;
        int userStartedOnDisplay;
        if (isCurrentUserOrRunningProfileOfCurrentUser(i)) {
            if (this.mVisibleBackgroundUserOnDefaultDisplayEnabled) {
                synchronized (this.mLock) {
                    userStartedOnDisplay = getUserStartedOnDisplay(0);
                }
                if (userStartedOnDisplay != -10000) {
                    if (DBG) {
                        com.android.server.utils.Slogf.d(TAG, "getMainDisplayAssignedToUser(%d): returning INVALID_DISPLAY for current user user %d was started on DEFAULT_DISPLAY", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(userStartedOnDisplay));
                    }
                    return -1;
                }
            }
            return 0;
        }
        if (!this.mVisibleBackgroundUsersEnabled) {
            return -1;
        }
        synchronized (this.mLock) {
            i2 = this.mUsersAssignedToDisplayOnStart.get(i, -1);
        }
        return i2;
    }

    @android.annotation.Nullable
    public int[] getDisplaysAssignedToUser(int i) {
        int mainDisplayAssignedToUser = getMainDisplayAssignedToUser(i);
        if (mainDisplayAssignedToUser == -1) {
            if (DBG) {
                com.android.server.utils.Slogf.d(TAG, "getDisplaysAssignedToUser(): returning null because there is no display assigned to user %d", java.lang.Integer.valueOf(i));
                return null;
            }
            return null;
        }
        synchronized (this.mLock) {
            try {
                if (this.mExtraDisplaysAssignedToUsers != null && this.mExtraDisplaysAssignedToUsers.size() != 0) {
                    int i2 = 1;
                    int size = this.mExtraDisplaysAssignedToUsers.size() + 1;
                    int[] iArr = new int[size];
                    iArr[0] = mainDisplayAssignedToUser;
                    for (int i3 = 0; i3 < this.mExtraDisplaysAssignedToUsers.size(); i3++) {
                        if (this.mExtraDisplaysAssignedToUsers.valueAt(i3) == i) {
                            iArr[i2] = this.mExtraDisplaysAssignedToUsers.keyAt(i3);
                            i2++;
                        }
                    }
                    if (size == i2) {
                        return iArr;
                    }
                    int[] iArr2 = new int[i2];
                    java.lang.System.arraycopy(iArr, 0, iArr2, 0, i2);
                    return iArr2;
                }
                return new int[]{mainDisplayAssignedToUser};
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getUserAssignedToDisplay(int i) {
        return getUserAssignedToDisplay(i, true);
    }

    private int getUserStartedOnDisplay(int i) {
        return getUserAssignedToDisplay(i, false);
    }

    private int getUserAssignedToDisplay(int i, boolean z) {
        if (z && ((i == 0 && !this.mVisibleBackgroundUserOnDefaultDisplayEnabled) || !this.mVisibleBackgroundUsersEnabled)) {
            return getCurrentUserId();
        }
        synchronized (this.mLock) {
            for (int i2 = 0; i2 < this.mUsersAssignedToDisplayOnStart.size(); i2++) {
                try {
                    if (this.mUsersAssignedToDisplayOnStart.valueAt(i2) == i) {
                        int keyAt = this.mUsersAssignedToDisplayOnStart.keyAt(i2);
                        if (!isStartedVisibleProfileLocked(keyAt)) {
                            return keyAt;
                        }
                        if (DBG) {
                            com.android.server.utils.Slogf.d(TAG, "getUserAssignedToDisplay(%d): skipping user %d because it's a profile", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(keyAt));
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (!z) {
                if (DBG) {
                    com.android.server.utils.Slogf.d(TAG, "getUserAssignedToDisplay(%d): no user assigned to display, returning USER_NULL instead", java.lang.Integer.valueOf(i));
                    return com.android.server.am.ProcessList.INVALID_ADJ;
                }
                return com.android.server.am.ProcessList.INVALID_ADJ;
            }
            int currentUserId = getCurrentUserId();
            if (DBG) {
                com.android.server.utils.Slogf.d(TAG, "getUserAssignedToDisplay(%d): no user assigned to display, returning current user (%d) instead", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(currentUserId));
            }
            return currentUserId;
        }
    }

    public android.util.IntArray getVisibleUsers() {
        android.util.IntArray intArray = new android.util.IntArray();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mStartedVisibleProfileGroupIds.size(); i++) {
                try {
                    int keyAt = this.mStartedVisibleProfileGroupIds.keyAt(i);
                    if (isUserVisible(keyAt)) {
                        intArray.add(keyAt);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return intArray;
    }

    public void addListener(com.android.server.pm.UserManagerInternal.UserVisibilityListener userVisibilityListener) {
        if (DBG) {
            com.android.server.utils.Slogf.d(TAG, "adding listener %s", userVisibilityListener);
        }
        synchronized (this.mLock) {
            this.mListeners.add(userVisibilityListener);
        }
    }

    public void removeListener(com.android.server.pm.UserManagerInternal.UserVisibilityListener userVisibilityListener) {
        if (DBG) {
            com.android.server.utils.Slogf.d(TAG, "removing listener %s", userVisibilityListener);
        }
        synchronized (this.mLock) {
            this.mListeners.remove(userVisibilityListener);
        }
    }

    void onSystemUserVisibilityChanged(boolean z) {
        dispatchVisibilityChanged(this.mListeners, 0, z);
    }

    private void dispatchVisibilityChanged(android.util.IntArray intArray, android.util.IntArray intArray2) {
        if (intArray == null) {
            if (DBG) {
                com.android.server.utils.Slogf.d(TAG, "dispatchVisibilityChanged(): ignoring, no listeners");
                return;
            }
            return;
        }
        java.util.concurrent.CopyOnWriteArrayList<com.android.server.pm.UserManagerInternal.UserVisibilityListener> copyOnWriteArrayList = this.mListeners;
        if (DBG) {
            com.android.server.utils.Slogf.d(TAG, "dispatchVisibilityChanged(): visibleUsersBefore=%s, visibleUsersAfter=%s, %d listeners (%s)", intArray, intArray2, java.lang.Integer.valueOf(copyOnWriteArrayList.size()), copyOnWriteArrayList);
        }
        for (int i = 0; i < intArray.size(); i++) {
            int i2 = intArray.get(i);
            if (intArray2.indexOf(i2) == -1) {
                dispatchVisibilityChanged(copyOnWriteArrayList, i2, false);
            }
        }
        for (int i3 = 0; i3 < intArray2.size(); i3++) {
            int i4 = intArray2.get(i3);
            if (intArray.indexOf(i4) == -1) {
                dispatchVisibilityChanged(copyOnWriteArrayList, i4, true);
            }
        }
    }

    private void dispatchVisibilityChanged(java.util.concurrent.CopyOnWriteArrayList<com.android.server.pm.UserManagerInternal.UserVisibilityListener> copyOnWriteArrayList, final int i, final boolean z) {
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UM_USER_VISIBILITY_CHANGED, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(z ? 1 : 0));
        if (DBG) {
            com.android.server.utils.Slogf.d(TAG, "dispatchVisibilityChanged(%d -> %b): sending to %d listeners", java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z), java.lang.Integer.valueOf(copyOnWriteArrayList.size()));
        }
        for (int i2 = 0; i2 < this.mListeners.size(); i2++) {
            final com.android.server.pm.UserManagerInternal.UserVisibilityListener userVisibilityListener = this.mListeners.get(i2);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.UserVisibilityMediator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.UserManagerInternal.UserVisibilityListener.this.onUserVisibilityChanged(i, z);
                }
            });
        }
    }

    private void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("UserVisibilityMediator");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("DBG: ");
        indentingPrintWriter.println(DBG);
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.print("Current user id: ");
                indentingPrintWriter.println(this.mCurrentUserId);
                indentingPrintWriter.print("Visible users: ");
                indentingPrintWriter.println(getVisibleUsers());
                dumpSparseIntArray(indentingPrintWriter, this.mStartedVisibleProfileGroupIds, "started visible user / profile group", "u", "pg");
                if (this.mStartedInvisibleProfileUserIds != null) {
                    indentingPrintWriter.print("Profiles started invisible: ");
                    indentingPrintWriter.println(this.mStartedInvisibleProfileUserIds);
                }
                indentingPrintWriter.print("Supports visible background users on displays: ");
                indentingPrintWriter.println(this.mVisibleBackgroundUsersEnabled);
                indentingPrintWriter.print("Supports visible background users on default display: ");
                indentingPrintWriter.println(this.mVisibleBackgroundUserOnDefaultDisplayEnabled);
                dumpSparseIntArray(indentingPrintWriter, this.mUsersAssignedToDisplayOnStart, "user / display", "u", "d");
                dumpSparseIntArray(indentingPrintWriter, this.mExtraDisplaysAssignedToUsers, "extra display / user", "d", "u");
                int size = this.mListeners.size();
                indentingPrintWriter.print("Number of listeners: ");
                indentingPrintWriter.println(size);
                if (size > 0) {
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < size; i++) {
                        indentingPrintWriter.print(i);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mListeners.get(i));
                    }
                    indentingPrintWriter.decreaseIndent();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    private static void dumpSparseIntArray(android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable android.util.SparseIntArray sparseIntArray, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (sparseIntArray == null) {
            indentingPrintWriter.print("No ");
            indentingPrintWriter.print(str);
            indentingPrintWriter.println(" mappings");
            return;
        }
        indentingPrintWriter.print("Number of ");
        indentingPrintWriter.print(str);
        indentingPrintWriter.print(" mappings: ");
        indentingPrintWriter.println(sparseIntArray.size());
        if (sparseIntArray.size() <= 0) {
            return;
        }
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < sparseIntArray.size(); i++) {
            indentingPrintWriter.print(str2);
            indentingPrintWriter.print(':');
            indentingPrintWriter.print(sparseIntArray.keyAt(i));
            indentingPrintWriter.print(" -> ");
            indentingPrintWriter.print(str3);
            indentingPrintWriter.print(':');
            indentingPrintWriter.println(sparseIntArray.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
    }

    @Override // android.util.Dumpable
    public void dump(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (printWriter instanceof android.util.IndentingPrintWriter) {
            dump((android.util.IndentingPrintWriter) printWriter);
        } else {
            dump(new android.util.IndentingPrintWriter(printWriter));
        }
    }

    private static boolean isSpecialUserId(int i) {
        switch (i) {
            case com.android.server.am.ProcessList.INVALID_ADJ /* -10000 */:
            case -3:
            case -2:
            case -1:
                return true;
            default:
                return false;
        }
    }

    private static boolean isProfile(int i, int i2) {
        return (i2 == -10000 || i2 == i) ? false : true;
    }

    private int getCurrentUserId() {
        int i;
        synchronized (this.mLock) {
            i = this.mCurrentUserId;
        }
        return i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isCurrentUserLocked(int i) {
        return (i == -10000 || this.mCurrentUserId == -10000 || this.mCurrentUserId != i) ? false : true;
    }

    private boolean isCurrentUserOrRunningProfileOfCurrentUser(int i) {
        synchronized (this.mLock) {
            if (i != -10000) {
                try {
                    if (this.mCurrentUserId != -10000) {
                        if (this.mCurrentUserId == i) {
                            return true;
                        }
                        int i2 = this.mStartedVisibleProfileGroupIds.get(i, com.android.server.am.ProcessList.INVALID_ADJ);
                        return i2 == this.mCurrentUserId || i2 == -1;
                    }
                } finally {
                }
            }
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isStartedVisibleProfileLocked(int i) {
        return isProfile(i, this.mStartedVisibleProfileGroupIds.get(i, com.android.server.am.ProcessList.INVALID_ADJ));
    }

    private void validateUserStartMode(@com.android.server.pm.UserManagerInternal.UserStartMode int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
                return;
            default:
                throw new java.lang.IllegalArgumentException("Invalid user start mode: " + i);
        }
    }

    private static java.lang.String secondaryDisplayMappingStatusToString(@com.android.server.pm.UserVisibilityMediator.SecondaryDisplayMappingStatus int i) {
        return android.util.DebugUtils.constantToString(com.android.server.pm.UserVisibilityMediator.class, PREFIX_SECONDARY_DISPLAY_MAPPING, i);
    }
}
