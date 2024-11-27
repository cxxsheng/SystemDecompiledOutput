package com.android.server.location.eventlog;

/* loaded from: classes2.dex */
public class LocationEventLog extends com.android.server.location.eventlog.LocalEventLog<java.lang.Object> {
    public static final com.android.server.location.eventlog.LocationEventLog EVENT_LOG = new com.android.server.location.eventlog.LocationEventLog();

    @com.android.internal.annotations.GuardedBy({"mAggregateStats"})
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<android.location.util.identity.CallerIdentity, com.android.server.location.eventlog.LocationEventLog.AggregateStats>> mAggregateStats;

    @com.android.internal.annotations.GuardedBy({"mGnssMeasAggregateStats"})
    private final android.util.ArrayMap<android.location.util.identity.CallerIdentity, com.android.server.location.eventlog.LocationEventLog.GnssMeasurementAggregateStats> mGnssMeasAggregateStats;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.location.eventlog.LocationEventLog.LocationsEventLog mLocationsLog;

    private static int getLogSize() {
        if (com.android.server.location.LocationManagerService.D) {
            return 600;
        }
        return 300;
    }

    private static int getLocationsLogSize() {
        if (com.android.server.location.LocationManagerService.D) {
            return 400;
        }
        return 200;
    }

    private LocationEventLog() {
        super(getLogSize(), java.lang.Object.class);
        this.mAggregateStats = new android.util.ArrayMap<>(4);
        this.mGnssMeasAggregateStats = new android.util.ArrayMap<>();
        this.mLocationsLog = new com.android.server.location.eventlog.LocationEventLog.LocationsEventLog(getLocationsLogSize());
    }

    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<android.location.util.identity.CallerIdentity, com.android.server.location.eventlog.LocationEventLog.AggregateStats>> copyAggregateStats() {
        android.util.ArrayMap<java.lang.String, android.util.ArrayMap<android.location.util.identity.CallerIdentity, com.android.server.location.eventlog.LocationEventLog.AggregateStats>> arrayMap;
        synchronized (this.mAggregateStats) {
            try {
                arrayMap = new android.util.ArrayMap<>(this.mAggregateStats);
                for (int i = 0; i < arrayMap.size(); i++) {
                    arrayMap.setValueAt(i, new android.util.ArrayMap<>(arrayMap.valueAt(i)));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayMap;
    }

    private com.android.server.location.eventlog.LocationEventLog.AggregateStats getAggregateStats(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity) {
        com.android.server.location.eventlog.LocationEventLog.AggregateStats aggregateStats;
        synchronized (this.mAggregateStats) {
            try {
                android.util.ArrayMap<android.location.util.identity.CallerIdentity, com.android.server.location.eventlog.LocationEventLog.AggregateStats> arrayMap = this.mAggregateStats.get(str);
                if (arrayMap == null) {
                    arrayMap = new android.util.ArrayMap<>(2);
                    this.mAggregateStats.put(str, arrayMap);
                }
                android.location.util.identity.CallerIdentity forAggregation = android.location.util.identity.CallerIdentity.forAggregation(callerIdentity);
                aggregateStats = arrayMap.get(forAggregation);
                if (aggregateStats == null) {
                    aggregateStats = new com.android.server.location.eventlog.LocationEventLog.AggregateStats();
                    arrayMap.put(forAggregation, aggregateStats);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return aggregateStats;
    }

    public android.util.ArrayMap<android.location.util.identity.CallerIdentity, com.android.server.location.eventlog.LocationEventLog.GnssMeasurementAggregateStats> copyGnssMeasurementAggregateStats() {
        android.util.ArrayMap<android.location.util.identity.CallerIdentity, com.android.server.location.eventlog.LocationEventLog.GnssMeasurementAggregateStats> arrayMap;
        synchronized (this.mGnssMeasAggregateStats) {
            arrayMap = new android.util.ArrayMap<>(this.mGnssMeasAggregateStats);
        }
        return arrayMap;
    }

    private com.android.server.location.eventlog.LocationEventLog.GnssMeasurementAggregateStats getGnssMeasurementAggregateStats(android.location.util.identity.CallerIdentity callerIdentity) {
        com.android.server.location.eventlog.LocationEventLog.GnssMeasurementAggregateStats gnssMeasurementAggregateStats;
        synchronized (this.mGnssMeasAggregateStats) {
            try {
                android.location.util.identity.CallerIdentity forAggregation = android.location.util.identity.CallerIdentity.forAggregation(callerIdentity);
                gnssMeasurementAggregateStats = this.mGnssMeasAggregateStats.get(forAggregation);
                if (gnssMeasurementAggregateStats == null) {
                    gnssMeasurementAggregateStats = new com.android.server.location.eventlog.LocationEventLog.GnssMeasurementAggregateStats();
                    this.mGnssMeasAggregateStats.put(forAggregation, gnssMeasurementAggregateStats);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return gnssMeasurementAggregateStats;
    }

    public void logUserSwitched(int i, int i2) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.UserSwitchedEvent(i, i2));
    }

    public void logUserVisibilityChanged(int i, boolean z) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.UserVisibilityChangedEvent(i, z));
    }

    public void logLocationEnabled(int i, boolean z) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.LocationEnabledEvent(i, z));
    }

    public void logAdasLocationEnabled(int i, boolean z) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.LocationAdasEnabledEvent(i, z));
    }

    public void logProviderEnabled(java.lang.String str, int i, boolean z) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderEnabledEvent(str, i, z));
    }

    public void logProviderMocked(java.lang.String str, boolean z) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderMockedEvent(str, z));
    }

    public void logProviderClientRegistered(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity, android.location.LocationRequest locationRequest) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderClientRegisterEvent(str, true, callerIdentity, locationRequest));
        getAggregateStats(str, callerIdentity).markRequestAdded(locationRequest.getIntervalMillis());
    }

    public void logProviderClientUnregistered(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderClientRegisterEvent(str, false, callerIdentity, null));
        getAggregateStats(str, callerIdentity).markRequestRemoved();
    }

    public void logProviderClientActive(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity) {
        getAggregateStats(str, callerIdentity).markRequestActive();
    }

    public void logProviderClientInactive(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity) {
        getAggregateStats(str, callerIdentity).markRequestInactive();
    }

    public void logProviderClientForeground(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity) {
        if (com.android.server.location.LocationManagerService.D) {
            addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderClientForegroundEvent(str, true, callerIdentity));
        }
        getAggregateStats(str, callerIdentity).markRequestForeground();
    }

    public void logProviderClientBackground(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity) {
        if (com.android.server.location.LocationManagerService.D) {
            addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderClientForegroundEvent(str, false, callerIdentity));
        }
        getAggregateStats(str, callerIdentity).markRequestBackground();
    }

    public void logProviderClientPermitted(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity) {
        if (com.android.server.location.LocationManagerService.D) {
            addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderClientPermittedEvent(str, true, callerIdentity));
        }
    }

    public void logProviderClientUnpermitted(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity) {
        if (com.android.server.location.LocationManagerService.D) {
            addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderClientPermittedEvent(str, false, callerIdentity));
        }
    }

    public void logProviderUpdateRequest(java.lang.String str, android.location.provider.ProviderRequest providerRequest) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderUpdateEvent(str, providerRequest));
    }

    public void logProviderReceivedLocations(java.lang.String str, int i) {
        synchronized (this) {
            this.mLocationsLog.logProviderReceivedLocations(str, i);
        }
    }

    public void logProviderDeliveredLocations(java.lang.String str, int i, android.location.util.identity.CallerIdentity callerIdentity) {
        synchronized (this) {
            this.mLocationsLog.logProviderDeliveredLocations(str, i, callerIdentity);
        }
        getAggregateStats(str, callerIdentity).markLocationDelivered();
    }

    public void logProviderStationaryThrottled(java.lang.String str, boolean z, android.location.provider.ProviderRequest providerRequest) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderStationaryThrottledEvent(str, z, providerRequest));
    }

    public void logLocationPowerSaveMode(int i) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.LocationPowerSaveModeEvent(i));
    }

    public void logGnssMeasurementClientRegistered(android.location.util.identity.CallerIdentity callerIdentity, android.location.GnssMeasurementRequest gnssMeasurementRequest) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.GnssMeasurementClientRegisterEvent(true, callerIdentity, gnssMeasurementRequest));
        getGnssMeasurementAggregateStats(callerIdentity).markRequestAdded(gnssMeasurementRequest.getIntervalMillis(), gnssMeasurementRequest.isFullTracking());
    }

    public void logGnssMeasurementClientUnregistered(android.location.util.identity.CallerIdentity callerIdentity) {
        addLog(new com.android.server.location.eventlog.LocationEventLog.GnssMeasurementClientRegisterEvent(false, callerIdentity, null));
        getGnssMeasurementAggregateStats(callerIdentity).markRequestRemoved();
    }

    public void logGnssMeasurementsDelivered(int i, android.location.util.identity.CallerIdentity callerIdentity) {
        synchronized (this) {
            this.mLocationsLog.logDeliveredGnssMeasurements(i, callerIdentity);
        }
        getGnssMeasurementAggregateStats(callerIdentity).markGnssMeasurementDelivered();
    }

    private void addLog(java.lang.Object obj) {
        addLog(android.os.SystemClock.elapsedRealtime(), obj);
    }

    @Override // com.android.server.location.eventlog.LocalEventLog
    public synchronized void iterate(com.android.server.location.eventlog.LocalEventLog.LogConsumer<? super java.lang.Object> logConsumer) {
        com.android.server.location.eventlog.LocalEventLog.iterate(logConsumer, this, this.mLocationsLog);
    }

    public void iterate(java.util.function.Consumer<java.lang.String> consumer) {
        iterate(consumer, (java.lang.String) null);
    }

    public void iterate(final java.util.function.Consumer<java.lang.String> consumer, @android.annotation.Nullable final java.lang.String str) {
        final long currentTimeMillis = java.lang.System.currentTimeMillis() - android.os.SystemClock.elapsedRealtime();
        final java.lang.StringBuilder sb = new java.lang.StringBuilder();
        iterate(new com.android.server.location.eventlog.LocalEventLog.LogConsumer() { // from class: com.android.server.location.eventlog.LocationEventLog$$ExternalSyntheticLambda0
            @Override // com.android.server.location.eventlog.LocalEventLog.LogConsumer
            public final void acceptLog(long j, java.lang.Object obj) {
                com.android.server.location.eventlog.LocationEventLog.lambda$iterate$0(str, sb, currentTimeMillis, consumer, j, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$iterate$0(java.lang.String str, java.lang.StringBuilder sb, long j, java.util.function.Consumer consumer, long j2, java.lang.Object obj) {
        if (str == null || ((obj instanceof com.android.server.location.eventlog.LocationEventLog.ProviderEvent) && str.equals(((com.android.server.location.eventlog.LocationEventLog.ProviderEvent) obj).mProvider))) {
            sb.setLength(0);
            sb.append(android.util.TimeUtils.logTimeOfDay(j2 + j));
            sb.append(": ");
            sb.append(obj);
            consumer.accept(sb.toString());
        }
    }

    private static abstract class ProviderEvent {
        protected final java.lang.String mProvider;

        ProviderEvent(java.lang.String str) {
            this.mProvider = str;
        }
    }

    private static final class ProviderEnabledEvent extends com.android.server.location.eventlog.LocationEventLog.ProviderEvent {
        private final boolean mEnabled;
        private final int mUserId;

        ProviderEnabledEvent(java.lang.String str, int i, boolean z) {
            super(str);
            this.mUserId = i;
            this.mEnabled = z;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.mProvider);
            sb.append(" provider [u");
            sb.append(this.mUserId);
            sb.append("] ");
            sb.append(this.mEnabled ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
            return sb.toString();
        }
    }

    private static final class ProviderMockedEvent extends com.android.server.location.eventlog.LocationEventLog.ProviderEvent {
        private final boolean mMocked;

        ProviderMockedEvent(java.lang.String str, boolean z) {
            super(str);
            this.mMocked = z;
        }

        public java.lang.String toString() {
            if (this.mMocked) {
                return this.mProvider + " provider added mock provider override";
            }
            return this.mProvider + " provider removed mock provider override";
        }
    }

    private static final class ProviderClientRegisterEvent extends com.android.server.location.eventlog.LocationEventLog.ProviderEvent {
        private final android.location.util.identity.CallerIdentity mIdentity;

        @android.annotation.Nullable
        private final android.location.LocationRequest mLocationRequest;
        private final boolean mRegistered;

        ProviderClientRegisterEvent(java.lang.String str, boolean z, android.location.util.identity.CallerIdentity callerIdentity, @android.annotation.Nullable android.location.LocationRequest locationRequest) {
            super(str);
            this.mRegistered = z;
            this.mIdentity = callerIdentity;
            this.mLocationRequest = locationRequest;
        }

        public java.lang.String toString() {
            if (this.mRegistered) {
                return this.mProvider + " provider +registration " + this.mIdentity + " -> " + this.mLocationRequest;
            }
            return this.mProvider + " provider -registration " + this.mIdentity;
        }
    }

    private static final class ProviderClientForegroundEvent extends com.android.server.location.eventlog.LocationEventLog.ProviderEvent {
        private final boolean mForeground;
        private final android.location.util.identity.CallerIdentity mIdentity;

        ProviderClientForegroundEvent(java.lang.String str, boolean z, android.location.util.identity.CallerIdentity callerIdentity) {
            super(str);
            this.mForeground = z;
            this.mIdentity = callerIdentity;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.mProvider);
            sb.append(" provider client ");
            sb.append(this.mIdentity);
            sb.append(" -> ");
            sb.append(this.mForeground ? "foreground" : "background");
            return sb.toString();
        }
    }

    private static final class ProviderClientPermittedEvent extends com.android.server.location.eventlog.LocationEventLog.ProviderEvent {
        private final android.location.util.identity.CallerIdentity mIdentity;
        private final boolean mPermitted;

        ProviderClientPermittedEvent(java.lang.String str, boolean z, android.location.util.identity.CallerIdentity callerIdentity) {
            super(str);
            this.mPermitted = z;
            this.mIdentity = callerIdentity;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.mProvider);
            sb.append(" provider client ");
            sb.append(this.mIdentity);
            sb.append(" -> ");
            sb.append(this.mPermitted ? "permitted" : "unpermitted");
            return sb.toString();
        }
    }

    private static final class ProviderUpdateEvent extends com.android.server.location.eventlog.LocationEventLog.ProviderEvent {
        private final android.location.provider.ProviderRequest mRequest;

        ProviderUpdateEvent(java.lang.String str, android.location.provider.ProviderRequest providerRequest) {
            super(str);
            this.mRequest = providerRequest;
        }

        public java.lang.String toString() {
            return this.mProvider + " provider request = " + this.mRequest;
        }
    }

    private static final class ProviderReceiveLocationEvent extends com.android.server.location.eventlog.LocationEventLog.ProviderEvent {
        private final int mNumLocations;

        ProviderReceiveLocationEvent(java.lang.String str, int i) {
            super(str);
            this.mNumLocations = i;
        }

        public java.lang.String toString() {
            return this.mProvider + " provider received location[" + this.mNumLocations + "]";
        }
    }

    private static final class ProviderDeliverLocationEvent extends com.android.server.location.eventlog.LocationEventLog.ProviderEvent {

        @android.annotation.Nullable
        private final android.location.util.identity.CallerIdentity mIdentity;
        private final int mNumLocations;

        ProviderDeliverLocationEvent(java.lang.String str, int i, @android.annotation.Nullable android.location.util.identity.CallerIdentity callerIdentity) {
            super(str);
            this.mNumLocations = i;
            this.mIdentity = callerIdentity;
        }

        public java.lang.String toString() {
            return this.mProvider + " provider delivered location[" + this.mNumLocations + "] to " + this.mIdentity;
        }
    }

    private static final class ProviderStationaryThrottledEvent extends com.android.server.location.eventlog.LocationEventLog.ProviderEvent {
        private final android.location.provider.ProviderRequest mRequest;
        private final boolean mStationaryThrottled;

        ProviderStationaryThrottledEvent(java.lang.String str, boolean z, android.location.provider.ProviderRequest providerRequest) {
            super(str);
            this.mStationaryThrottled = z;
            this.mRequest = providerRequest;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.mProvider);
            sb.append(" provider stationary/idle ");
            sb.append(this.mStationaryThrottled ? "throttled" : "unthrottled");
            sb.append(", request = ");
            sb.append(this.mRequest);
            return sb.toString();
        }
    }

    private static final class LocationPowerSaveModeEvent {
        private final int mLocationPowerSaveMode;

        LocationPowerSaveModeEvent(int i) {
            this.mLocationPowerSaveMode = i;
        }

        public java.lang.String toString() {
            java.lang.String str;
            switch (this.mLocationPowerSaveMode) {
                case 0:
                    str = "NO_CHANGE";
                    break;
                case 1:
                    str = "GPS_DISABLED_WHEN_SCREEN_OFF";
                    break;
                case 2:
                    str = "ALL_DISABLED_WHEN_SCREEN_OFF";
                    break;
                case 3:
                    str = "FOREGROUND_ONLY";
                    break;
                case 4:
                    str = "THROTTLE_REQUESTS_WHEN_SCREEN_OFF";
                    break;
                default:
                    str = "UNKNOWN";
                    break;
            }
            return "location power save mode changed to " + str;
        }
    }

    private static final class UserSwitchedEvent {
        private final int mUserIdFrom;
        private final int mUserIdTo;

        UserSwitchedEvent(int i, int i2) {
            this.mUserIdFrom = i;
            this.mUserIdTo = i2;
        }

        public java.lang.String toString() {
            return "current user switched from u" + this.mUserIdFrom + " to u" + this.mUserIdTo;
        }
    }

    private static final class UserVisibilityChangedEvent {
        private final int mUserId;
        private final boolean mVisible;

        UserVisibilityChangedEvent(int i, boolean z) {
            this.mUserId = i;
            this.mVisible = z;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("[u");
            sb.append(this.mUserId);
            sb.append("] ");
            sb.append(this.mVisible ? com.android.server.wm.ActivityTaskManagerService.DUMP_VISIBLE_ACTIVITIES : "invisible");
            return sb.toString();
        }
    }

    private static final class LocationEnabledEvent {
        private final boolean mEnabled;
        private final int mUserId;

        LocationEnabledEvent(int i, boolean z) {
            this.mUserId = i;
            this.mEnabled = z;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("location [u");
            sb.append(this.mUserId);
            sb.append("] ");
            sb.append(this.mEnabled ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
            return sb.toString();
        }
    }

    private static final class LocationAdasEnabledEvent {
        private final boolean mEnabled;
        private final int mUserId;

        LocationAdasEnabledEvent(int i, boolean z) {
            this.mUserId = i;
            this.mEnabled = z;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("adas location [u");
            sb.append(this.mUserId);
            sb.append("] ");
            sb.append(this.mEnabled ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
            return sb.toString();
        }
    }

    private static final class GnssMeasurementClientRegisterEvent {

        @android.annotation.Nullable
        private final android.location.GnssMeasurementRequest mGnssMeasurementRequest;
        private final android.location.util.identity.CallerIdentity mIdentity;
        private final boolean mRegistered;

        GnssMeasurementClientRegisterEvent(boolean z, android.location.util.identity.CallerIdentity callerIdentity, @android.annotation.Nullable android.location.GnssMeasurementRequest gnssMeasurementRequest) {
            this.mRegistered = z;
            this.mIdentity = callerIdentity;
            this.mGnssMeasurementRequest = gnssMeasurementRequest;
        }

        public java.lang.String toString() {
            if (this.mRegistered) {
                return "gnss measurements +registration " + this.mIdentity + " -> " + this.mGnssMeasurementRequest;
            }
            return "gnss measurements -registration " + this.mIdentity;
        }
    }

    private static final class GnssMeasurementDeliverEvent {

        @android.annotation.Nullable
        private final android.location.util.identity.CallerIdentity mIdentity;
        private final int mNumGnssMeasurements;

        GnssMeasurementDeliverEvent(int i, @android.annotation.Nullable android.location.util.identity.CallerIdentity callerIdentity) {
            this.mNumGnssMeasurements = i;
            this.mIdentity = callerIdentity;
        }

        public java.lang.String toString() {
            return "gnss measurements delivered GnssMeasurements[" + this.mNumGnssMeasurements + "] to " + this.mIdentity;
        }
    }

    private static final class LocationsEventLog extends com.android.server.location.eventlog.LocalEventLog<java.lang.Object> {
        LocationsEventLog(int i) {
            super(i, java.lang.Object.class);
        }

        public void logProviderReceivedLocations(java.lang.String str, int i) {
            addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderReceiveLocationEvent(str, i));
        }

        public void logDeliveredGnssMeasurements(int i, android.location.util.identity.CallerIdentity callerIdentity) {
            addLog(new com.android.server.location.eventlog.LocationEventLog.GnssMeasurementDeliverEvent(i, callerIdentity));
        }

        public void logProviderDeliveredLocations(java.lang.String str, int i, android.location.util.identity.CallerIdentity callerIdentity) {
            addLog(new com.android.server.location.eventlog.LocationEventLog.ProviderDeliverLocationEvent(str, i, callerIdentity));
        }

        private void addLog(java.lang.Object obj) {
            addLog(android.os.SystemClock.elapsedRealtime(), obj);
        }
    }

    public static final class AggregateStats {

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mActiveRequestCount;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mActiveTimeLastUpdateRealtimeMs;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mActiveTimeTotalMs;

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mAddedRequestCount;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mAddedTimeLastUpdateRealtimeMs;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mAddedTimeTotalMs;

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mDeliveredLocationCount;

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mForegroundRequestCount;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mForegroundTimeLastUpdateRealtimeMs;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mForegroundTimeTotalMs;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mFastestIntervalMs = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mSlowestIntervalMs = 0;

        AggregateStats() {
        }

        synchronized void markRequestAdded(long j) {
            try {
                int i = this.mAddedRequestCount;
                this.mAddedRequestCount = i + 1;
                if (i == 0) {
                    this.mAddedTimeLastUpdateRealtimeMs = android.os.SystemClock.elapsedRealtime();
                }
                this.mFastestIntervalMs = java.lang.Math.min(j, this.mFastestIntervalMs);
                this.mSlowestIntervalMs = java.lang.Math.max(j, this.mSlowestIntervalMs);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        synchronized void markRequestRemoved() {
            updateTotals();
            boolean z = true;
            this.mAddedRequestCount--;
            if (this.mAddedRequestCount < 0) {
                z = false;
            }
            com.android.internal.util.Preconditions.checkState(z);
            this.mActiveRequestCount = java.lang.Math.min(this.mAddedRequestCount, this.mActiveRequestCount);
            this.mForegroundRequestCount = java.lang.Math.min(this.mAddedRequestCount, this.mForegroundRequestCount);
        }

        synchronized void markRequestActive() {
            com.android.internal.util.Preconditions.checkState(this.mAddedRequestCount > 0);
            int i = this.mActiveRequestCount;
            this.mActiveRequestCount = i + 1;
            if (i == 0) {
                this.mActiveTimeLastUpdateRealtimeMs = android.os.SystemClock.elapsedRealtime();
            }
        }

        synchronized void markRequestInactive() {
            updateTotals();
            boolean z = true;
            this.mActiveRequestCount--;
            if (this.mActiveRequestCount < 0) {
                z = false;
            }
            com.android.internal.util.Preconditions.checkState(z);
        }

        synchronized void markRequestForeground() {
            com.android.internal.util.Preconditions.checkState(this.mAddedRequestCount > 0);
            int i = this.mForegroundRequestCount;
            this.mForegroundRequestCount = i + 1;
            if (i == 0) {
                this.mForegroundTimeLastUpdateRealtimeMs = android.os.SystemClock.elapsedRealtime();
            }
        }

        synchronized void markRequestBackground() {
            updateTotals();
            boolean z = true;
            this.mForegroundRequestCount--;
            if (this.mForegroundRequestCount < 0) {
                z = false;
            }
            com.android.internal.util.Preconditions.checkState(z);
        }

        synchronized void markLocationDelivered() {
            this.mDeliveredLocationCount++;
        }

        public synchronized void updateTotals() {
            try {
                if (this.mAddedRequestCount > 0) {
                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                    this.mAddedTimeTotalMs += elapsedRealtime - this.mAddedTimeLastUpdateRealtimeMs;
                    this.mAddedTimeLastUpdateRealtimeMs = elapsedRealtime;
                }
                if (this.mActiveRequestCount > 0) {
                    long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                    this.mActiveTimeTotalMs += elapsedRealtime2 - this.mActiveTimeLastUpdateRealtimeMs;
                    this.mActiveTimeLastUpdateRealtimeMs = elapsedRealtime2;
                }
                if (this.mForegroundRequestCount > 0) {
                    long elapsedRealtime3 = android.os.SystemClock.elapsedRealtime();
                    this.mForegroundTimeTotalMs += elapsedRealtime3 - this.mForegroundTimeLastUpdateRealtimeMs;
                    this.mForegroundTimeLastUpdateRealtimeMs = elapsedRealtime3;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        public synchronized java.lang.String toString() {
            return "min/max interval = " + intervalToString(this.mFastestIntervalMs) + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + intervalToString(this.mSlowestIntervalMs) + ", total/active/foreground duration = " + android.util.TimeUtils.formatDuration(this.mAddedTimeTotalMs) + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.util.TimeUtils.formatDuration(this.mActiveTimeTotalMs) + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.util.TimeUtils.formatDuration(this.mForegroundTimeTotalMs) + ", locations = " + this.mDeliveredLocationCount;
        }

        private static java.lang.String intervalToString(long j) {
            if (j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                return "passive";
            }
            return java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(j) + "s";
        }
    }

    public static final class GnssMeasurementAggregateStats {

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mAddedRequestCount;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mAddedTimeLastUpdateRealtimeMs;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mAddedTimeTotalMs;

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mHasDutyCycling;

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mHasFullTracking;

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mReceivedMeasurementEventCount;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mFastestIntervalMs = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;

        @com.android.internal.annotations.GuardedBy({"this"})
        private long mSlowestIntervalMs = 0;

        GnssMeasurementAggregateStats() {
        }

        synchronized void markRequestAdded(long j, boolean z) {
            try {
                int i = this.mAddedRequestCount;
                this.mAddedRequestCount = i + 1;
                if (i == 0) {
                    this.mAddedTimeLastUpdateRealtimeMs = android.os.SystemClock.elapsedRealtime();
                }
                if (z) {
                    this.mHasFullTracking = true;
                } else {
                    this.mHasDutyCycling = true;
                }
                this.mFastestIntervalMs = java.lang.Math.min(j, this.mFastestIntervalMs);
                this.mSlowestIntervalMs = java.lang.Math.max(j, this.mSlowestIntervalMs);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        synchronized void markRequestRemoved() {
            updateTotals();
            boolean z = true;
            this.mAddedRequestCount--;
            if (this.mAddedRequestCount < 0) {
                z = false;
            }
            com.android.internal.util.Preconditions.checkState(z);
        }

        synchronized void markGnssMeasurementDelivered() {
            this.mReceivedMeasurementEventCount++;
        }

        public synchronized void updateTotals() {
            if (this.mAddedRequestCount > 0) {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                this.mAddedTimeTotalMs += elapsedRealtime - this.mAddedTimeLastUpdateRealtimeMs;
                this.mAddedTimeLastUpdateRealtimeMs = elapsedRealtime;
            }
        }

        public synchronized java.lang.String toString() {
            return "min/max interval = " + intervalToString(this.mFastestIntervalMs) + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + intervalToString(this.mSlowestIntervalMs) + ", total duration = " + android.util.TimeUtils.formatDuration(this.mAddedTimeTotalMs) + ", tracking mode = " + trackingModeToString() + ", GNSS measurement events = " + this.mReceivedMeasurementEventCount;
        }

        private static java.lang.String intervalToString(long j) {
            if (j == 2147483647L) {
                return "passive";
            }
            return java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(j) + "s";
        }

        @com.android.internal.annotations.GuardedBy({"this"})
        private java.lang.String trackingModeToString() {
            if (this.mHasFullTracking && this.mHasDutyCycling) {
                return "mixed tracking mode";
            }
            if (this.mHasFullTracking) {
                return "always full-tracking";
            }
            return "always duty-cycling";
        }
    }
}
