package com.android.server.usage;

/* loaded from: classes2.dex */
public class AppTimeLimitController {
    private static final boolean DEBUG = false;
    private static final long MAX_OBSERVER_PER_UID = 1000;
    private static final java.lang.Integer ONE = new java.lang.Integer(1);
    private static final long ONE_MINUTE = 60000;
    private static final java.lang.String TAG = "AppTimeLimitController";
    private android.app.AlarmManager mAlarmManager;
    private final android.content.Context mContext;
    private final com.android.server.usage.AppTimeLimitController.MyHandler mHandler;
    private com.android.server.usage.AppTimeLimitController.TimeLimitCallbackListener mListener;
    private final com.android.server.usage.AppTimeLimitController.Lock mLock = new com.android.server.usage.AppTimeLimitController.Lock();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.usage.AppTimeLimitController.UserData> mUsers = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.usage.AppTimeLimitController.ObserverAppData> mObserverApps = new android.util.SparseArray<>();

    private static class Lock {
        private Lock() {
        }
    }

    public interface TimeLimitCallbackListener {
        void onLimitReached(int i, int i2, long j, long j2, android.app.PendingIntent pendingIntent);

        void onSessionEnd(int i, int i2, long j, android.app.PendingIntent pendingIntent);
    }

    private class UserData {
        public final android.util.ArrayMap<java.lang.String, java.lang.Integer> currentlyActive;
        public final android.util.ArrayMap<java.lang.String, java.util.ArrayList<com.android.server.usage.AppTimeLimitController.UsageGroup>> observedMap;
        private int userId;

        private UserData(int i) {
            this.currentlyActive = new android.util.ArrayMap<>();
            this.observedMap = new android.util.ArrayMap<>();
            this.userId = i;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean isActive(java.lang.String[] strArr) {
            for (java.lang.String str : strArr) {
                if (this.currentlyActive.containsKey(str)) {
                    return true;
                }
            }
            return false;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void addUsageGroup(com.android.server.usage.AppTimeLimitController.UsageGroup usageGroup) {
            int length = usageGroup.mObserved.length;
            for (int i = 0; i < length; i++) {
                java.util.ArrayList<com.android.server.usage.AppTimeLimitController.UsageGroup> arrayList = this.observedMap.get(usageGroup.mObserved[i]);
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList<>();
                    this.observedMap.put(usageGroup.mObserved[i], arrayList);
                }
                arrayList.add(usageGroup);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void removeUsageGroup(com.android.server.usage.AppTimeLimitController.UsageGroup usageGroup) {
            int length = usageGroup.mObserved.length;
            for (int i = 0; i < length; i++) {
                java.lang.String str = usageGroup.mObserved[i];
                java.util.ArrayList<com.android.server.usage.AppTimeLimitController.UsageGroup> arrayList = this.observedMap.get(str);
                if (arrayList != null) {
                    arrayList.remove(usageGroup);
                    if (arrayList.isEmpty()) {
                        this.observedMap.remove(str);
                    }
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void dump(java.io.PrintWriter printWriter) {
            printWriter.print(" userId=");
            printWriter.println(this.userId);
            printWriter.print(" Currently Active:");
            int size = this.currentlyActive.size();
            for (int i = 0; i < size; i++) {
                printWriter.print(this.currentlyActive.keyAt(i));
                printWriter.print(", ");
            }
            printWriter.println();
            printWriter.print(" Observed Entities:");
            int size2 = this.observedMap.size();
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print(this.observedMap.keyAt(i2));
                printWriter.print(", ");
            }
            printWriter.println();
        }
    }

    private class ObserverAppData {
        android.util.SparseArray<com.android.server.usage.AppTimeLimitController.AppUsageGroup> appUsageGroups;
        android.util.SparseArray<com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup> appUsageLimitGroups;
        android.util.SparseArray<com.android.server.usage.AppTimeLimitController.SessionUsageGroup> sessionUsageGroups;
        private int uid;

        private ObserverAppData(int i) {
            this.appUsageGroups = new android.util.SparseArray<>();
            this.sessionUsageGroups = new android.util.SparseArray<>();
            this.appUsageLimitGroups = new android.util.SparseArray<>();
            this.uid = i;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void removeAppUsageGroup(int i) {
            this.appUsageGroups.remove(i);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void removeSessionUsageGroup(int i) {
            this.sessionUsageGroups.remove(i);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void removeAppUsageLimitGroup(int i) {
            this.appUsageLimitGroups.remove(i);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void dump(java.io.PrintWriter printWriter) {
            printWriter.print(" uid=");
            printWriter.println(this.uid);
            printWriter.println("    App Usage Groups:");
            int size = this.appUsageGroups.size();
            for (int i = 0; i < size; i++) {
                this.appUsageGroups.valueAt(i).dump(printWriter);
                printWriter.println();
            }
            printWriter.println("    Session Usage Groups:");
            int size2 = this.sessionUsageGroups.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.sessionUsageGroups.valueAt(i2).dump(printWriter);
                printWriter.println();
            }
            printWriter.println("    App Usage Limit Groups:");
            int size3 = this.appUsageLimitGroups.size();
            for (int i3 = 0; i3 < size3; i3++) {
                this.appUsageLimitGroups.valueAt(i3).dump(printWriter);
                printWriter.println();
            }
        }
    }

    abstract class UsageGroup {
        protected int mActives;
        protected long mLastKnownUsageTimeMs;
        protected long mLastUsageEndTimeMs;
        protected android.app.PendingIntent mLimitReachedCallback;
        protected java.lang.String[] mObserved;
        protected java.lang.ref.WeakReference<com.android.server.usage.AppTimeLimitController.ObserverAppData> mObserverAppRef;
        protected int mObserverId;
        protected long mTimeLimitMs;
        protected long mUsageTimeMs;
        protected java.lang.ref.WeakReference<com.android.server.usage.AppTimeLimitController.UserData> mUserRef;

        UsageGroup(com.android.server.usage.AppTimeLimitController.UserData userData, com.android.server.usage.AppTimeLimitController.ObserverAppData observerAppData, int i, java.lang.String[] strArr, long j, android.app.PendingIntent pendingIntent) {
            this.mUserRef = new java.lang.ref.WeakReference<>(userData);
            this.mObserverAppRef = new java.lang.ref.WeakReference<>(observerAppData);
            this.mObserverId = i;
            this.mObserved = strArr;
            this.mTimeLimitMs = j;
            this.mLimitReachedCallback = pendingIntent;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public long getTimeLimitMs() {
            return this.mTimeLimitMs;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public long getUsageTimeMs() {
            return this.mUsageTimeMs;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void remove() {
            com.android.server.usage.AppTimeLimitController.UserData userData = this.mUserRef.get();
            if (userData != null) {
                userData.removeUsageGroup(this);
            }
            this.mLimitReachedCallback = null;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void noteUsageStart(long j) {
            noteUsageStart(j, j);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void noteUsageStart(long j, long j2) {
            int i = this.mActives;
            this.mActives = i + 1;
            if (i == 0) {
                if (this.mLastUsageEndTimeMs > j) {
                    j = this.mLastUsageEndTimeMs;
                }
                this.mLastKnownUsageTimeMs = j;
                long j3 = ((this.mTimeLimitMs - this.mUsageTimeMs) - j2) + j;
                if (j3 > 0) {
                    com.android.server.usage.AppTimeLimitController.this.postCheckTimeoutLocked(this, j3);
                    return;
                }
                return;
            }
            if (this.mActives > this.mObserved.length) {
                this.mActives = this.mObserved.length;
                com.android.server.usage.AppTimeLimitController.UserData userData = this.mUserRef.get();
                if (userData == null) {
                    return;
                }
                android.util.Slog.e(com.android.server.usage.AppTimeLimitController.TAG, "Too many noted usage starts! Observed entities: " + java.util.Arrays.toString(this.mObserved) + "   Active Entities: " + java.util.Arrays.toString(userData.currentlyActive.keySet().toArray()));
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void noteUsageStop(long j) {
            int i = this.mActives - 1;
            this.mActives = i;
            if (i == 0) {
                boolean z = this.mUsageTimeMs < this.mTimeLimitMs;
                this.mUsageTimeMs += j - this.mLastKnownUsageTimeMs;
                this.mLastUsageEndTimeMs = j;
                if (z && this.mUsageTimeMs >= this.mTimeLimitMs) {
                    com.android.server.usage.AppTimeLimitController.this.postInformLimitReachedListenerLocked(this);
                }
                com.android.server.usage.AppTimeLimitController.this.cancelCheckTimeoutLocked(this);
                return;
            }
            if (this.mActives < 0) {
                this.mActives = 0;
                com.android.server.usage.AppTimeLimitController.UserData userData = this.mUserRef.get();
                if (userData == null) {
                    return;
                }
                android.util.Slog.e(com.android.server.usage.AppTimeLimitController.TAG, "Too many noted usage stops! Observed entities: " + java.util.Arrays.toString(this.mObserved) + "   Active Entities: " + java.util.Arrays.toString(userData.currentlyActive.keySet().toArray()));
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void checkTimeout(long j) {
            com.android.server.usage.AppTimeLimitController.UserData userData = this.mUserRef.get();
            if (userData == null) {
                return;
            }
            long j2 = this.mTimeLimitMs - this.mUsageTimeMs;
            if (j2 > 0 && userData.isActive(this.mObserved)) {
                long j3 = j - this.mLastKnownUsageTimeMs;
                if (j2 <= j3) {
                    this.mUsageTimeMs += j3;
                    this.mLastKnownUsageTimeMs = j;
                    com.android.server.usage.AppTimeLimitController.this.postInformLimitReachedListenerLocked(this);
                    return;
                }
                com.android.server.usage.AppTimeLimitController.this.postCheckTimeoutLocked(this, j2 - j3);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void onLimitReached() {
            com.android.server.usage.AppTimeLimitController.UserData userData = this.mUserRef.get();
            if (userData != null && com.android.server.usage.AppTimeLimitController.this.mListener != null) {
                com.android.server.usage.AppTimeLimitController.this.mListener.onLimitReached(this.mObserverId, userData.userId, this.mTimeLimitMs, this.mUsageTimeMs, this.mLimitReachedCallback);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void dump(java.io.PrintWriter printWriter) {
            printWriter.print("        Group id=");
            printWriter.print(this.mObserverId);
            printWriter.print(" timeLimit=");
            printWriter.print(this.mTimeLimitMs);
            printWriter.print(" used=");
            printWriter.print(this.mUsageTimeMs);
            printWriter.print(" lastKnownUsage=");
            printWriter.print(this.mLastKnownUsageTimeMs);
            printWriter.print(" mActives=");
            printWriter.print(this.mActives);
            printWriter.print(" observed=");
            printWriter.print(java.util.Arrays.toString(this.mObserved));
        }
    }

    class AppUsageGroup extends com.android.server.usage.AppTimeLimitController.UsageGroup {
        public AppUsageGroup(com.android.server.usage.AppTimeLimitController.UserData userData, com.android.server.usage.AppTimeLimitController.ObserverAppData observerAppData, int i, java.lang.String[] strArr, long j, android.app.PendingIntent pendingIntent) {
            super(userData, observerAppData, i, strArr, j, pendingIntent);
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void remove() {
            super.remove();
            com.android.server.usage.AppTimeLimitController.ObserverAppData observerAppData = this.mObserverAppRef.get();
            if (observerAppData != null) {
                observerAppData.removeAppUsageGroup(this.mObserverId);
            }
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void onLimitReached() {
            super.onLimitReached();
            remove();
        }
    }

    class SessionUsageGroup extends com.android.server.usage.AppTimeLimitController.UsageGroup implements android.app.AlarmManager.OnAlarmListener {
        private long mNewSessionThresholdMs;
        private android.app.PendingIntent mSessionEndCallback;

        public SessionUsageGroup(com.android.server.usage.AppTimeLimitController.UserData userData, com.android.server.usage.AppTimeLimitController.ObserverAppData observerAppData, int i, java.lang.String[] strArr, long j, android.app.PendingIntent pendingIntent, long j2, android.app.PendingIntent pendingIntent2) {
            super(userData, observerAppData, i, strArr, j, pendingIntent);
            this.mNewSessionThresholdMs = j2;
            this.mSessionEndCallback = pendingIntent2;
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void remove() {
            super.remove();
            com.android.server.usage.AppTimeLimitController.ObserverAppData observerAppData = this.mObserverAppRef.get();
            if (observerAppData != null) {
                observerAppData.removeSessionUsageGroup(this.mObserverId);
            }
            this.mSessionEndCallback = null;
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void noteUsageStart(long j, long j2) {
            if (this.mActives == 0) {
                if (j - this.mLastUsageEndTimeMs > this.mNewSessionThresholdMs) {
                    this.mUsageTimeMs = 0L;
                }
                com.android.server.usage.AppTimeLimitController.this.getAlarmManager().cancel(this);
            }
            super.noteUsageStart(j, j2);
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void noteUsageStop(long j) {
            super.noteUsageStop(j);
            if (this.mActives == 0 && this.mUsageTimeMs >= this.mTimeLimitMs) {
                com.android.server.usage.AppTimeLimitController.this.getAlarmManager().setExact(3, com.android.server.usage.AppTimeLimitController.this.getElapsedRealtime() + this.mNewSessionThresholdMs, com.android.server.usage.AppTimeLimitController.TAG, this, com.android.server.usage.AppTimeLimitController.this.mHandler);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void onSessionEnd() {
            com.android.server.usage.AppTimeLimitController.UserData userData = this.mUserRef.get();
            if (userData != null && com.android.server.usage.AppTimeLimitController.this.mListener != null) {
                com.android.server.usage.AppTimeLimitController.this.mListener.onSessionEnd(this.mObserverId, userData.userId, this.mUsageTimeMs, this.mSessionEndCallback);
            }
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            synchronized (com.android.server.usage.AppTimeLimitController.this.mLock) {
                onSessionEnd();
            }
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        @com.android.internal.annotations.GuardedBy({"mLock"})
        void dump(java.io.PrintWriter printWriter) {
            super.dump(printWriter);
            printWriter.print(" lastUsageEndTime=");
            printWriter.print(this.mLastUsageEndTimeMs);
            printWriter.print(" newSessionThreshold=");
            printWriter.print(this.mNewSessionThresholdMs);
        }
    }

    class AppUsageLimitGroup extends com.android.server.usage.AppTimeLimitController.UsageGroup {
        public AppUsageLimitGroup(com.android.server.usage.AppTimeLimitController.UserData userData, com.android.server.usage.AppTimeLimitController.ObserverAppData observerAppData, int i, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent) {
            super(userData, observerAppData, i, strArr, j, pendingIntent);
            this.mUsageTimeMs = j2;
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void remove() {
            super.remove();
            com.android.server.usage.AppTimeLimitController.ObserverAppData observerAppData = this.mObserverAppRef.get();
            if (observerAppData != null) {
                observerAppData.removeAppUsageLimitGroup(this.mObserverId);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        long getTotaUsageLimit() {
            return this.mTimeLimitMs;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        long getUsageRemaining() {
            if (this.mActives > 0) {
                return (this.mTimeLimitMs - this.mUsageTimeMs) - (com.android.server.usage.AppTimeLimitController.this.getElapsedRealtime() - this.mLastKnownUsageTimeMs);
            }
            return this.mTimeLimitMs - this.mUsageTimeMs;
        }
    }

    private class MyHandler extends android.os.Handler {
        static final int MSG_CHECK_TIMEOUT = 1;
        static final int MSG_INFORM_LIMIT_REACHED_LISTENER = 2;

        MyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    synchronized (com.android.server.usage.AppTimeLimitController.this.mLock) {
                        ((com.android.server.usage.AppTimeLimitController.UsageGroup) message.obj).checkTimeout(com.android.server.usage.AppTimeLimitController.this.getElapsedRealtime());
                    }
                    return;
                case 2:
                    synchronized (com.android.server.usage.AppTimeLimitController.this.mLock) {
                        ((com.android.server.usage.AppTimeLimitController.UsageGroup) message.obj).onLimitReached();
                    }
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    }

    public AppTimeLimitController(android.content.Context context, com.android.server.usage.AppTimeLimitController.TimeLimitCallbackListener timeLimitCallbackListener, android.os.Looper looper) {
        this.mContext = context;
        this.mHandler = new com.android.server.usage.AppTimeLimitController.MyHandler(looper);
        this.mListener = timeLimitCallbackListener;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.app.AlarmManager getAlarmManager() {
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        }
        return this.mAlarmManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long getElapsedRealtime() {
        return android.os.SystemClock.elapsedRealtime();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long getAppUsageObserverPerUidLimit() {
        return 1000L;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long getUsageSessionObserverPerUidLimit() {
        return 1000L;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long getAppUsageLimitObserverPerUidLimit() {
        return 1000L;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long getMinTimeLimit() {
        return 60000L;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.usage.AppTimeLimitController.AppUsageGroup getAppUsageGroup(int i, int i2) {
        com.android.server.usage.AppTimeLimitController.AppUsageGroup appUsageGroup;
        synchronized (this.mLock) {
            appUsageGroup = getOrCreateObserverAppDataLocked(i).appUsageGroups.get(i2);
        }
        return appUsageGroup;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.usage.AppTimeLimitController.SessionUsageGroup getSessionUsageGroup(int i, int i2) {
        com.android.server.usage.AppTimeLimitController.SessionUsageGroup sessionUsageGroup;
        synchronized (this.mLock) {
            sessionUsageGroup = getOrCreateObserverAppDataLocked(i).sessionUsageGroups.get(i2);
        }
        return sessionUsageGroup;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup getAppUsageLimitGroup(int i, int i2) {
        com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup;
        synchronized (this.mLock) {
            appUsageLimitGroup = getOrCreateObserverAppDataLocked(i).appUsageLimitGroups.get(i2);
        }
        return appUsageLimitGroup;
    }

    public android.app.usage.UsageStatsManagerInternal.AppUsageLimitData getAppUsageLimit(java.lang.String str, android.os.UserHandle userHandle) {
        synchronized (this.mLock) {
            try {
                com.android.server.usage.AppTimeLimitController.UserData orCreateUserDataLocked = getOrCreateUserDataLocked(userHandle.getIdentifier());
                if (orCreateUserDataLocked == null) {
                    return null;
                }
                java.util.ArrayList<com.android.server.usage.AppTimeLimitController.UsageGroup> arrayList = orCreateUserDataLocked.observedMap.get(str);
                if (arrayList == null || arrayList.isEmpty()) {
                    return null;
                }
                android.util.ArraySet arraySet = new android.util.ArraySet();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i) instanceof com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup) {
                        com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup = (com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup) arrayList.get(i);
                        int i2 = 0;
                        while (true) {
                            if (i2 >= appUsageLimitGroup.mObserved.length) {
                                break;
                            }
                            if (!appUsageLimitGroup.mObserved[i2].equals(str)) {
                                i2++;
                            } else {
                                arraySet.add(appUsageLimitGroup);
                                break;
                            }
                        }
                    }
                }
                if (arraySet.isEmpty()) {
                    return null;
                }
                com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup2 = (com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup) arraySet.valueAt(0);
                for (int i3 = 1; i3 < arraySet.size(); i3++) {
                    com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup3 = (com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup) arraySet.valueAt(i3);
                    if (appUsageLimitGroup3.getUsageRemaining() < appUsageLimitGroup2.getUsageRemaining()) {
                        appUsageLimitGroup2 = appUsageLimitGroup3;
                    }
                }
                return new android.app.usage.UsageStatsManagerInternal.AppUsageLimitData(appUsageLimitGroup2.getTotaUsageLimit(), appUsageLimitGroup2.getUsageRemaining());
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.usage.AppTimeLimitController.UserData getOrCreateUserDataLocked(int i) {
        com.android.server.usage.AppTimeLimitController.UserData userData = this.mUsers.get(i);
        if (userData == null) {
            com.android.server.usage.AppTimeLimitController.UserData userData2 = new com.android.server.usage.AppTimeLimitController.UserData(i);
            this.mUsers.put(i, userData2);
            return userData2;
        }
        return userData;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.usage.AppTimeLimitController.ObserverAppData getOrCreateObserverAppDataLocked(int i) {
        com.android.server.usage.AppTimeLimitController.ObserverAppData observerAppData = this.mObserverApps.get(i);
        if (observerAppData == null) {
            com.android.server.usage.AppTimeLimitController.ObserverAppData observerAppData2 = new com.android.server.usage.AppTimeLimitController.ObserverAppData(i);
            this.mObserverApps.put(i, observerAppData2);
            return observerAppData2;
        }
        return observerAppData;
    }

    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
            this.mUsers.remove(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void noteActiveLocked(com.android.server.usage.AppTimeLimitController.UserData userData, com.android.server.usage.AppTimeLimitController.UsageGroup usageGroup, long j) {
        int length = usageGroup.mObserved.length;
        for (int i = 0; i < length; i++) {
            if (userData.currentlyActive.containsKey(usageGroup.mObserved[i])) {
                usageGroup.noteUsageStart(j);
            }
        }
    }

    public void addAppUsageObserver(int i, int i2, java.lang.String[] strArr, long j, android.app.PendingIntent pendingIntent, int i3) {
        if (j < getMinTimeLimit()) {
            throw new java.lang.IllegalArgumentException("Time limit must be >= " + getMinTimeLimit());
        }
        synchronized (this.mLock) {
            try {
                com.android.server.usage.AppTimeLimitController.UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i3);
                com.android.server.usage.AppTimeLimitController.ObserverAppData orCreateObserverAppDataLocked = getOrCreateObserverAppDataLocked(i);
                com.android.server.usage.AppTimeLimitController.AppUsageGroup appUsageGroup = orCreateObserverAppDataLocked.appUsageGroups.get(i2);
                if (appUsageGroup != null) {
                    appUsageGroup.remove();
                }
                if (orCreateObserverAppDataLocked.appUsageGroups.size() >= getAppUsageObserverPerUidLimit()) {
                    throw new java.lang.IllegalStateException("Too many app usage observers added by uid " + i);
                }
                com.android.server.usage.AppTimeLimitController.AppUsageGroup appUsageGroup2 = new com.android.server.usage.AppTimeLimitController.AppUsageGroup(orCreateUserDataLocked, orCreateObserverAppDataLocked, i2, strArr, j, pendingIntent);
                orCreateObserverAppDataLocked.appUsageGroups.append(i2, appUsageGroup2);
                orCreateUserDataLocked.addUsageGroup(appUsageGroup2);
                noteActiveLocked(orCreateUserDataLocked, appUsageGroup2, getElapsedRealtime());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeAppUsageObserver(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                com.android.server.usage.AppTimeLimitController.AppUsageGroup appUsageGroup = getOrCreateObserverAppDataLocked(i).appUsageGroups.get(i2);
                if (appUsageGroup != null) {
                    appUsageGroup.remove();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void addUsageSessionObserver(int i, int i2, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, int i3) {
        if (j < getMinTimeLimit()) {
            throw new java.lang.IllegalArgumentException("Time limit must be >= " + getMinTimeLimit());
        }
        synchronized (this.mLock) {
            try {
                com.android.server.usage.AppTimeLimitController.UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i3);
                com.android.server.usage.AppTimeLimitController.ObserverAppData orCreateObserverAppDataLocked = getOrCreateObserverAppDataLocked(i);
                com.android.server.usage.AppTimeLimitController.SessionUsageGroup sessionUsageGroup = orCreateObserverAppDataLocked.sessionUsageGroups.get(i2);
                if (sessionUsageGroup != null) {
                    sessionUsageGroup.remove();
                }
                if (orCreateObserverAppDataLocked.sessionUsageGroups.size() >= getUsageSessionObserverPerUidLimit()) {
                    throw new java.lang.IllegalStateException("Too many app usage observers added by uid " + i);
                }
                com.android.server.usage.AppTimeLimitController.SessionUsageGroup sessionUsageGroup2 = new com.android.server.usage.AppTimeLimitController.SessionUsageGroup(orCreateUserDataLocked, orCreateObserverAppDataLocked, i2, strArr, j, pendingIntent, j2, pendingIntent2);
                orCreateObserverAppDataLocked.sessionUsageGroups.append(i2, sessionUsageGroup2);
                orCreateUserDataLocked.addUsageGroup(sessionUsageGroup2);
                noteActiveLocked(orCreateUserDataLocked, sessionUsageGroup2, getElapsedRealtime());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeUsageSessionObserver(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                com.android.server.usage.AppTimeLimitController.SessionUsageGroup sessionUsageGroup = getOrCreateObserverAppDataLocked(i).sessionUsageGroups.get(i2);
                if (sessionUsageGroup != null) {
                    sessionUsageGroup.remove();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void addAppUsageLimitObserver(int i, int i2, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, int i3) {
        if (j < getMinTimeLimit()) {
            throw new java.lang.IllegalArgumentException("Time limit must be >= " + getMinTimeLimit());
        }
        synchronized (this.mLock) {
            try {
                com.android.server.usage.AppTimeLimitController.UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i3);
                com.android.server.usage.AppTimeLimitController.ObserverAppData orCreateObserverAppDataLocked = getOrCreateObserverAppDataLocked(i);
                com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup = orCreateObserverAppDataLocked.appUsageLimitGroups.get(i2);
                if (appUsageLimitGroup != null) {
                    appUsageLimitGroup.remove();
                }
                if (orCreateObserverAppDataLocked.appUsageLimitGroups.size() >= getAppUsageLimitObserverPerUidLimit()) {
                    throw new java.lang.IllegalStateException("Too many app usage observers added by uid " + i);
                }
                com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup2 = new com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup(orCreateUserDataLocked, orCreateObserverAppDataLocked, i2, strArr, j, j2, j2 >= j ? null : pendingIntent);
                orCreateObserverAppDataLocked.appUsageLimitGroups.append(i2, appUsageLimitGroup2);
                orCreateUserDataLocked.addUsageGroup(appUsageLimitGroup2);
                noteActiveLocked(orCreateUserDataLocked, appUsageLimitGroup2, getElapsedRealtime());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeAppUsageLimitObserver(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                com.android.server.usage.AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup = getOrCreateObserverAppDataLocked(i).appUsageLimitGroups.get(i2);
                if (appUsageLimitGroup != null) {
                    appUsageLimitGroup.remove();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void noteUsageStart(java.lang.String str, int i, long j) throws java.lang.IllegalArgumentException {
        java.lang.Integer valueAt;
        synchronized (this.mLock) {
            try {
                com.android.server.usage.AppTimeLimitController.UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i);
                int indexOfKey = orCreateUserDataLocked.currentlyActive.indexOfKey(str);
                if (indexOfKey >= 0 && (valueAt = orCreateUserDataLocked.currentlyActive.valueAt(indexOfKey)) != null) {
                    orCreateUserDataLocked.currentlyActive.setValueAt(indexOfKey, java.lang.Integer.valueOf(valueAt.intValue() + 1));
                    return;
                }
                long elapsedRealtime = getElapsedRealtime();
                orCreateUserDataLocked.currentlyActive.put(str, ONE);
                java.util.ArrayList<com.android.server.usage.AppTimeLimitController.UsageGroup> arrayList = orCreateUserDataLocked.observedMap.get(str);
                if (arrayList == null) {
                    return;
                }
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList.get(i2).noteUsageStart(elapsedRealtime - j, elapsedRealtime);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void noteUsageStart(java.lang.String str, int i) throws java.lang.IllegalArgumentException {
        noteUsageStart(str, i, 0L);
    }

    public void noteUsageStop(java.lang.String str, int i) throws java.lang.IllegalArgumentException {
        synchronized (this.mLock) {
            try {
                com.android.server.usage.AppTimeLimitController.UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i);
                int indexOfKey = orCreateUserDataLocked.currentlyActive.indexOfKey(str);
                if (indexOfKey < 0) {
                    throw new java.lang.IllegalArgumentException("Unable to stop usage for " + str + ", not in use");
                }
                if (!orCreateUserDataLocked.currentlyActive.valueAt(indexOfKey).equals(ONE)) {
                    orCreateUserDataLocked.currentlyActive.setValueAt(indexOfKey, java.lang.Integer.valueOf(r2.intValue() - 1));
                    return;
                }
                orCreateUserDataLocked.currentlyActive.removeAt(indexOfKey);
                long elapsedRealtime = getElapsedRealtime();
                java.util.ArrayList<com.android.server.usage.AppTimeLimitController.UsageGroup> arrayList = orCreateUserDataLocked.observedMap.get(str);
                if (arrayList == null) {
                    return;
                }
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList.get(i2).noteUsageStop(elapsedRealtime);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void postInformLimitReachedListenerLocked(com.android.server.usage.AppTimeLimitController.UsageGroup usageGroup) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, usageGroup));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void postCheckTimeoutLocked(com.android.server.usage.AppTimeLimitController.UsageGroup usageGroup, long j) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, usageGroup), j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void cancelCheckTimeoutLocked(com.android.server.usage.AppTimeLimitController.UsageGroup usageGroup) {
        this.mHandler.removeMessages(1, usageGroup);
    }

    void dump(java.lang.String[] strArr, java.io.PrintWriter printWriter) {
        if (strArr != null) {
            for (java.lang.String str : strArr) {
                if ("actives".equals(str)) {
                    synchronized (this.mLock) {
                        try {
                            int size = this.mUsers.size();
                            for (int i = 0; i < size; i++) {
                                android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap = this.mUsers.valueAt(i).currentlyActive;
                                int size2 = arrayMap.size();
                                for (int i2 = 0; i2 < size2; i2++) {
                                    printWriter.println(arrayMap.keyAt(i2));
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                }
            }
        }
        synchronized (this.mLock) {
            try {
                printWriter.println("\n  App Time Limits");
                int size3 = this.mUsers.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    printWriter.print("   User ");
                    this.mUsers.valueAt(i3).dump(printWriter);
                }
                printWriter.println();
                int size4 = this.mObserverApps.size();
                for (int i4 = 0; i4 < size4; i4++) {
                    printWriter.print("   Observer App ");
                    this.mObserverApps.valueAt(i4).dump(printWriter);
                }
            } finally {
            }
        }
    }
}
