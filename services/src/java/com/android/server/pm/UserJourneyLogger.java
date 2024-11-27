package com.android.server.pm;

/* loaded from: classes2.dex */
public class UserJourneyLogger {
    public static final int ERROR_CODE_ABORTED = 3;
    public static final int ERROR_CODE_INCOMPLETE_OR_TIMEOUT = 2;
    public static final int ERROR_CODE_INVALID_SESSION_ID = 0;
    public static final int ERROR_CODE_NULL_USER_INFO = 4;
    public static final int ERROR_CODE_UNSPECIFIED = -1;
    public static final int ERROR_CODE_USER_ALREADY_AN_ADMIN = 5;
    public static final int ERROR_CODE_USER_IS_NOT_AN_ADMIN = 6;
    public static final int EVENT_STATE_BEGIN = 1;
    public static final int EVENT_STATE_CANCEL = 3;
    public static final int EVENT_STATE_ERROR = 4;
    public static final int EVENT_STATE_FINISH = 2;
    public static final int EVENT_STATE_NONE = 0;
    private static final int USER_ID_KEY_MULTIPLICATION = 100;
    public static final int USER_JOURNEY_GRANT_ADMIN = 7;
    public static final int USER_JOURNEY_REVOKE_ADMIN = 8;
    public static final int USER_JOURNEY_UNKNOWN = 0;
    public static final int USER_JOURNEY_USER_CREATE = 4;
    public static final int USER_JOURNEY_USER_LIFECYCLE = 9;
    public static final int USER_JOURNEY_USER_REMOVE = 6;
    public static final int USER_JOURNEY_USER_START = 3;
    public static final int USER_JOURNEY_USER_STOP = 5;
    public static final int USER_JOURNEY_USER_SWITCH_FG = 2;
    public static final int USER_JOURNEY_USER_SWITCH_UI = 1;
    public static final int USER_LIFECYCLE_EVENT_CREATE_USER = 3;
    public static final int USER_LIFECYCLE_EVENT_GRANT_ADMIN = 9;
    public static final int USER_LIFECYCLE_EVENT_REMOVE_USER = 8;
    public static final int USER_LIFECYCLE_EVENT_REVOKE_ADMIN = 10;
    public static final int USER_LIFECYCLE_EVENT_START_USER = 2;
    public static final int USER_LIFECYCLE_EVENT_STOP_USER = 7;
    public static final int USER_LIFECYCLE_EVENT_SWITCH_USER = 1;
    public static final int USER_LIFECYCLE_EVENT_UNKNOWN = 0;
    public static final int USER_LIFECYCLE_EVENT_UNLOCKED_USER = 6;
    public static final int USER_LIFECYCLE_EVENT_UNLOCKING_USER = 5;
    public static final int USER_LIFECYCLE_EVENT_USER_RUNNING_LOCKED = 4;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.pm.UserJourneyLogger.UserJourneySession> mUserIdToUserJourneyMap = new android.util.SparseArray<>();

    public @interface UserJourney {
    }

    public @interface UserJourneyErrorCode {
    }

    public @interface UserLifecycleEvent {
    }

    public @interface UserLifecycleEventState {
    }

    @com.android.server.pm.UserJourneyLogger.UserLifecycleEvent
    private static int journeyToEvent(@com.android.server.pm.UserJourneyLogger.UserJourney int i) {
        switch (i) {
            case 1:
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 7;
            case 6:
                return 8;
            case 7:
                return 9;
            case 8:
                return 10;
            default:
                return 0;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getUserTypeForStatsd(@android.annotation.NonNull java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1309576832:
                if (str.equals("android.os.usertype.profile.PRIVATE")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1103927049:
                if (str.equals("android.os.usertype.full.GUEST")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -159818852:
                if (str.equals("android.os.usertype.profile.MANAGED")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 34001850:
                if (str.equals("android.os.usertype.system.HEADLESS")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 485661392:
                if (str.equals("android.os.usertype.full.SYSTEM")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 942013715:
                if (str.equals("android.os.usertype.full.SECONDARY")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1711075452:
                if (str.equals("android.os.usertype.full.RESTRICTED")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1765400260:
                if (str.equals("android.os.usertype.full.DEMO")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1966344346:
                if (str.equals("android.os.usertype.profile.CLONE")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 8;
            case '\b':
                return 9;
            default:
                return 0;
        }
    }

    @com.android.server.pm.UserJourneyLogger.UserLifecycleEventState
    private static int errorToFinishState(@com.android.server.pm.UserJourneyLogger.UserJourneyErrorCode int i) {
        switch (i) {
            case -1:
                return 2;
            case 3:
                return 3;
            default:
                return 4;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void logUserLifecycleJourneyReported(@android.annotation.Nullable com.android.server.pm.UserJourneyLogger.UserJourneySession userJourneySession, @com.android.server.pm.UserJourneyLogger.UserJourney int i, int i2, int i3, int i4, int i5, @com.android.server.pm.UserJourneyLogger.UserJourneyErrorCode int i6) {
        if (userJourneySession != null) {
            writeUserLifecycleJourneyReported(userJourneySession.mSessionId, i, i2, i3, i4, i5, i6, java.lang.System.currentTimeMillis() - userJourneySession.mStartTimeInMills);
        } else {
            writeUserLifecycleJourneyReported(-1L, i, i2, i3, i4, i5, 0, -1L);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void writeUserLifecycleJourneyReported(long j, int i, int i2, int i3, int i4, int i5, int i6, long j2) {
        com.android.internal.util.FrameworkStatsLog.write(264, j, i, i2, i3, i4, i5, i6, j2);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void logUserLifecycleEventOccurred(com.android.server.pm.UserJourneyLogger.UserJourneySession userJourneySession, int i, @com.android.server.pm.UserJourneyLogger.UserLifecycleEvent int i2, @com.android.server.pm.UserJourneyLogger.UserLifecycleEventState int i3, @com.android.server.pm.UserJourneyLogger.UserJourneyErrorCode int i4) {
        if (userJourneySession == null) {
            writeUserLifecycleEventOccurred(-1L, i, i2, 4, 0);
        } else {
            writeUserLifecycleEventOccurred(userJourneySession.mSessionId, i, i2, i3, i4);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void writeUserLifecycleEventOccurred(long j, int i, int i2, int i3, int i4) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED, j, i, i2, i3, i4);
    }

    public void logUserLifecycleEvent(int i, @com.android.server.pm.UserJourneyLogger.UserLifecycleEvent int i2, @com.android.server.pm.UserJourneyLogger.UserLifecycleEventState int i3) {
        logUserLifecycleEventOccurred(findUserJourneySession(i), i, i2, i3, -1);
    }

    @android.annotation.Nullable
    private com.android.server.pm.UserJourneyLogger.UserJourneySession findUserJourneySession(int i) {
        synchronized (this.mLock) {
            try {
                int size = this.mUserIdToUserJourneyMap.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int keyAt = this.mUserIdToUserJourneyMap.keyAt(i2);
                    if (keyAt / 100 == i) {
                        return this.mUserIdToUserJourneyMap.get(keyAt);
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int getUserJourneyKey(int i, @com.android.server.pm.UserJourneyLogger.UserJourney int i2) {
        return (i * 100) + i2;
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.pm.UserJourneyLogger.UserJourneySession finishAndClearIncompleteUserJourney(int i, @com.android.server.pm.UserJourneyLogger.UserJourney int i2) {
        synchronized (this.mLock) {
            try {
                int userJourneyKey = getUserJourneyKey(i, i2);
                com.android.server.pm.UserJourneyLogger.UserJourneySession userJourneySession = this.mUserIdToUserJourneyMap.get(userJourneyKey);
                if (userJourneySession != null) {
                    logUserLifecycleEventOccurred(userJourneySession, i, journeyToEvent(userJourneySession.mJourney), 4, 2);
                    logUserLifecycleJourneyReported(userJourneySession, i2, -1, i, getUserTypeForStatsd(""), -1, 2);
                    this.mUserIdToUserJourneyMap.remove(userJourneyKey);
                    return userJourneySession;
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public com.android.server.pm.UserJourneyLogger.UserJourneySession logUserJourneyFinish(int i, android.content.pm.UserInfo userInfo, @com.android.server.pm.UserJourneyLogger.UserJourney int i2) {
        return logUserJourneyFinishWithError(i, userInfo, i2, -1);
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.pm.UserJourneyLogger.UserJourneySession logUserSwitchJourneyFinish(int i, android.content.pm.UserInfo userInfo) {
        synchronized (this.mLock) {
            try {
                int userJourneyKey = getUserJourneyKey(userInfo.id, 2);
                int userJourneyKey2 = getUserJourneyKey(userInfo.id, 1);
                if (this.mUserIdToUserJourneyMap.contains(userJourneyKey)) {
                    return logUserJourneyFinish(i, userInfo, 2);
                }
                if (!this.mUserIdToUserJourneyMap.contains(userJourneyKey2)) {
                    return null;
                }
                return logUserJourneyFinish(i, userInfo, 1);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public com.android.server.pm.UserJourneyLogger.UserJourneySession logUserJourneyFinishWithError(int i, android.content.pm.UserInfo userInfo, @com.android.server.pm.UserJourneyLogger.UserJourney int i2, @com.android.server.pm.UserJourneyLogger.UserJourneyErrorCode int i3) {
        synchronized (this.mLock) {
            try {
                int errorToFinishState = errorToFinishState(i3);
                int userJourneyKey = getUserJourneyKey(userInfo.id, i2);
                com.android.server.pm.UserJourneyLogger.UserJourneySession userJourneySession = this.mUserIdToUserJourneyMap.get(userJourneyKey);
                if (userJourneySession != null) {
                    logUserLifecycleEventOccurred(userJourneySession, userInfo.id, journeyToEvent(userJourneySession.mJourney), errorToFinishState, i3);
                    logUserLifecycleJourneyReported(userJourneySession, i2, i, userInfo.id, getUserTypeForStatsd(userInfo.userType), userInfo.flags, i3);
                    this.mUserIdToUserJourneyMap.remove(userJourneyKey);
                    return userJourneySession;
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public com.android.server.pm.UserJourneyLogger.UserJourneySession logDelayedUserJourneyFinishWithError(int i, android.content.pm.UserInfo userInfo, @com.android.server.pm.UserJourneyLogger.UserJourney int i2, @com.android.server.pm.UserJourneyLogger.UserJourneyErrorCode int i3) {
        synchronized (this.mLock) {
            try {
                int userJourneyKey = getUserJourneyKey(userInfo.id, i2);
                com.android.server.pm.UserJourneyLogger.UserJourneySession userJourneySession = this.mUserIdToUserJourneyMap.get(userJourneyKey);
                if (userJourneySession != null) {
                    logUserLifecycleJourneyReported(userJourneySession, i2, i, userInfo.id, getUserTypeForStatsd(userInfo.userType), userInfo.flags, i3);
                    this.mUserIdToUserJourneyMap.remove(userJourneyKey);
                    return userJourneySession;
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public com.android.server.pm.UserJourneyLogger.UserJourneySession logNullUserJourneyError(@com.android.server.pm.UserJourneyLogger.UserJourney int i, int i2, int i3, java.lang.String str, int i4) {
        com.android.server.pm.UserJourneyLogger.UserJourneySession userJourneySession;
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(i3, i);
            userJourneySession = this.mUserIdToUserJourneyMap.get(userJourneyKey);
            logUserLifecycleEventOccurred(userJourneySession, i3, journeyToEvent(i), 4, 4);
            logUserLifecycleJourneyReported(userJourneySession, i, i2, i3, getUserTypeForStatsd(str), i4, 4);
            this.mUserIdToUserJourneyMap.remove(userJourneyKey);
        }
        return userJourneySession;
    }

    public com.android.server.pm.UserJourneyLogger.UserJourneySession logUserCreateJourneyFinish(int i, android.content.pm.UserInfo userInfo) {
        synchronized (this.mLock) {
            try {
                int userJourneyKey = getUserJourneyKey(-1, 4);
                com.android.server.pm.UserJourneyLogger.UserJourneySession userJourneySession = this.mUserIdToUserJourneyMap.get(userJourneyKey);
                if (userJourneySession != null) {
                    logUserLifecycleEventOccurred(userJourneySession, userInfo.id, 3, 2, -1);
                    logUserLifecycleJourneyReported(userJourneySession, 4, i, userInfo.id, getUserTypeForStatsd(userInfo.userType), userInfo.flags, -1);
                    this.mUserIdToUserJourneyMap.remove(userJourneyKey);
                    return userJourneySession;
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public com.android.server.pm.UserJourneyLogger.UserJourneySession logUserJourneyBegin(int i, @com.android.server.pm.UserJourneyLogger.UserJourney int i2) {
        com.android.server.pm.UserJourneyLogger.UserJourneySession userJourneySession;
        long nextLong = java.util.concurrent.ThreadLocalRandom.current().nextLong(1L, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(i, i2);
            userJourneySession = new com.android.server.pm.UserJourneyLogger.UserJourneySession(nextLong, i2);
            this.mUserIdToUserJourneyMap.append(userJourneyKey, userJourneySession);
            logUserLifecycleEventOccurred(userJourneySession, i, journeyToEvent(userJourneySession.mJourney), 1, -1);
        }
        return userJourneySession;
    }

    public com.android.server.pm.UserJourneyLogger.UserJourneySession startSessionForDelayedJourney(int i, @com.android.server.pm.UserJourneyLogger.UserJourney int i2, long j) {
        com.android.server.pm.UserJourneyLogger.UserJourneySession userJourneySession;
        long nextLong = java.util.concurrent.ThreadLocalRandom.current().nextLong(1L, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
        synchronized (this.mLock) {
            int userJourneyKey = getUserJourneyKey(i, i2);
            userJourneySession = new com.android.server.pm.UserJourneyLogger.UserJourneySession(nextLong, i2, j);
            this.mUserIdToUserJourneyMap.append(userJourneyKey, userJourneySession);
        }
        return userJourneySession;
    }

    public static class UserJourneySession {

        @com.android.server.pm.UserJourneyLogger.UserJourney
        public final int mJourney;
        public final long mSessionId;
        public final long mStartTimeInMills;

        @com.android.internal.annotations.VisibleForTesting
        public UserJourneySession(long j, @com.android.server.pm.UserJourneyLogger.UserJourney int i) {
            this.mJourney = i;
            this.mSessionId = j;
            this.mStartTimeInMills = java.lang.System.currentTimeMillis();
        }

        @com.android.internal.annotations.VisibleForTesting
        public UserJourneySession(long j, @com.android.server.pm.UserJourneyLogger.UserJourney int i, long j2) {
            this.mJourney = i;
            this.mSessionId = j;
            this.mStartTimeInMills = j2;
        }
    }
}
