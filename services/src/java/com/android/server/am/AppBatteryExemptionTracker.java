package com.android.server.am;

/* loaded from: classes.dex */
final class AppBatteryExemptionTracker extends com.android.server.am.BaseAppStateDurationsTracker<com.android.server.am.AppBatteryExemptionTracker.AppBatteryExemptionPolicy, com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates> implements com.android.server.am.BaseAppStateEvents.Factory<com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates>, com.android.server.am.BaseAppStateTracker.StateListener {
    private static final boolean DEBUG_BACKGROUND_BATTERY_EXEMPTION_TRACKER = false;
    static final java.lang.String DEFAULT_NAME = "";
    private static final java.lang.String TAG = "ActivityManager";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.am.UidProcessMap<java.lang.Integer> mUidPackageStates;

    AppBatteryExemptionTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController) {
        this(context, appRestrictionController, null, null);
    }

    AppBatteryExemptionTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController, java.lang.reflect.Constructor<? extends com.android.server.am.BaseAppStateTracker.Injector<com.android.server.am.AppBatteryExemptionTracker.AppBatteryExemptionPolicy>> constructor, java.lang.Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mUidPackageStates = new com.android.server.am.UidProcessMap<>();
        this.mInjector.setPolicy(new com.android.server.am.AppBatteryExemptionTracker.AppBatteryExemptionPolicy(this.mInjector, this));
    }

    @Override // com.android.server.am.BaseAppStateTracker
    @com.android.server.am.AppRestrictionController.TrackerType
    int getType() {
        return 2;
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onSystemReady() {
        super.onSystemReady();
        this.mAppRestrictionController.forEachTracker(new java.util.function.Consumer() { // from class: com.android.server.am.AppBatteryExemptionTracker$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.am.AppBatteryExemptionTracker.this.lambda$onSystemReady$0((com.android.server.am.BaseAppStateTracker) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$0(com.android.server.am.BaseAppStateTracker baseAppStateTracker) {
        baseAppStateTracker.registerStateListener(this);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates createAppStateEvents(int i, java.lang.String str) {
        return new com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates(i, TAG, (com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig) this.mInjector.getPolicy());
    }

    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates createAppStateEvents(com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates uidBatteryStates) {
        return new com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates(uidBatteryStates);
    }

    @Override // com.android.server.am.BaseAppStateTracker.StateListener
    public void onStateChange(int i, java.lang.String str, boolean z, long j, int i2) {
        int i3;
        boolean z2;
        com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates uidBatteryStates;
        boolean z3;
        if (!((com.android.server.am.AppBatteryExemptionTracker.AppBatteryExemptionPolicy) this.mInjector.getPolicy()).isEnabled()) {
            return;
        }
        com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage uidBatteryUsage = this.mAppRestrictionController.getUidBatteryUsage(i);
        int stateTypeToIndex = com.android.server.am.BaseAppStateTracker.stateTypeToIndex(i2);
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<android.util.ArrayMap<java.lang.String, java.lang.Integer>> map = this.mUidPackageStates.getMap();
                android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap = map.get(i);
                if (arrayMap == null) {
                    arrayMap = new android.util.ArrayMap<>();
                    map.put(i, arrayMap);
                }
                int indexOfKey = arrayMap.indexOfKey(str);
                boolean z4 = false;
                if (indexOfKey >= 0) {
                    i3 = arrayMap.valueAt(indexOfKey).intValue();
                } else {
                    arrayMap.put(str, 0);
                    indexOfKey = arrayMap.indexOfKey(str);
                    i3 = 0;
                }
                if (z) {
                    int size = arrayMap.size() - 1;
                    while (true) {
                        if (size < 0) {
                            z3 = false;
                            break;
                        } else if ((arrayMap.valueAt(size).intValue() & i2) == 0) {
                            size--;
                        } else {
                            z3 = true;
                            break;
                        }
                    }
                    arrayMap.setValueAt(indexOfKey, java.lang.Integer.valueOf(i3 | i2));
                    if (!z3) {
                        z4 = true;
                    }
                } else {
                    int i4 = i3 & (~i2);
                    arrayMap.setValueAt(indexOfKey, java.lang.Integer.valueOf(i4));
                    int size2 = arrayMap.size() - 1;
                    while (true) {
                        if (size2 < 0) {
                            z2 = true;
                            break;
                        } else if ((arrayMap.valueAt(size2).intValue() & i2) == 0) {
                            size2--;
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                    if (z2) {
                        z4 = true;
                    }
                    if (i4 == 0) {
                        arrayMap.removeAt(indexOfKey);
                        if (arrayMap.size() == 0) {
                            map.remove(i);
                        }
                    }
                }
                if (z4) {
                    com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates uidBatteryStates2 = (com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates) this.mPkgEvents.get(i, "");
                    if (uidBatteryStates2 != null) {
                        uidBatteryStates = uidBatteryStates2;
                    } else {
                        com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates createAppStateEvents = createAppStateEvents(i, "");
                        this.mPkgEvents.put(i, "", createAppStateEvents);
                        uidBatteryStates = createAppStateEvents;
                    }
                    uidBatteryStates.addEvent(z, j, uidBatteryUsage, stateTypeToIndex);
                }
            } finally {
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateDurationsTracker, com.android.server.am.BaseAppStateEventsTracker
    @com.android.internal.annotations.VisibleForTesting
    void reset() {
        super.reset();
        synchronized (this.mLock) {
            this.mUidPackageStates.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTrackerEnabled(boolean z) {
        if (!z) {
            synchronized (this.mLock) {
                this.mPkgEvents.clear();
                this.mUidPackageStates.clear();
            }
        }
    }

    com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage getUidBatteryExemptedUsageSince(int i, long j, long j2, int i2) {
        if (!((com.android.server.am.AppBatteryExemptionTracker.AppBatteryExemptionPolicy) this.mInjector.getPolicy()).isEnabled()) {
            return com.android.server.am.AppBatteryTracker.BATTERY_USAGE_NONE;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates uidBatteryStates = (com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates) this.mPkgEvents.get(i, "");
                if (uidBatteryStates == null) {
                    return com.android.server.am.AppBatteryTracker.BATTERY_USAGE_NONE;
                }
                android.util.Pair<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage, com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> batteryUsageSince = uidBatteryStates.getBatteryUsageSince(j, j2, i2);
                if (!((com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage) batteryUsageSince.second).isEmpty()) {
                    return ((com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage) batteryUsageSince.first).mutate().add(this.mAppRestrictionController.getUidBatteryUsage(i)).subtract((com.android.server.am.AppBatteryTracker.BatteryUsage) batteryUsageSince.second).unmutate();
                }
                return (com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage) batteryUsageSince.first;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static final class UidBatteryStates extends com.android.server.am.BaseAppStateDurations<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> {
        UidBatteryStates(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
            super(i, "", 5, str, maxTrackingDurationConfig);
        }

        UidBatteryStates(@android.annotation.NonNull com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates uidBatteryStates) {
            super(uidBatteryStates);
        }

        void addEvent(boolean z, long j, com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage, int i) {
            if (z) {
                addEvent(z, (boolean) new com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery(z, j, immutableBatteryUsage, null), i);
                return;
            }
            com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery lastEvent = getLastEvent(i);
            if (lastEvent == null || !lastEvent.isStart()) {
                return;
            }
            addEvent(z, (boolean) new com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery(z, j, immutableBatteryUsage.mutate().subtract(lastEvent.getBatteryUsage()).unmutate(), lastEvent), i);
        }

        com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery getLastEvent(int i) {
            if (this.mEvents[i] != null) {
                return (com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery) this.mEvents[i].peekLast();
            }
            return null;
        }

        private android.util.Pair<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage, com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> getBatteryUsageSince(long j, long j2, java.util.LinkedList<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> linkedList) {
            if (linkedList == null || linkedList.size() == 0) {
                return android.util.Pair.create(com.android.server.am.AppBatteryTracker.BATTERY_USAGE_NONE, com.android.server.am.AppBatteryTracker.BATTERY_USAGE_NONE);
            }
            com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage = new com.android.server.am.AppBatteryTracker.BatteryUsage();
            java.util.Iterator<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> it = linkedList.iterator();
            com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery uidStateEventWithBattery = null;
            while (it.hasNext()) {
                uidStateEventWithBattery = it.next();
                if (uidStateEventWithBattery.getTimestamp() >= j && !uidStateEventWithBattery.isStart()) {
                    batteryUsage.add(uidStateEventWithBattery.getBatteryUsage(j, java.lang.Math.min(j2, uidStateEventWithBattery.getTimestamp())));
                    if (j2 <= uidStateEventWithBattery.getTimestamp()) {
                        break;
                    }
                }
            }
            return android.util.Pair.create(batteryUsage.unmutate(), uidStateEventWithBattery.isStart() ? uidStateEventWithBattery.getBatteryUsage() : com.android.server.am.AppBatteryTracker.BATTERY_USAGE_NONE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        android.util.Pair<com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage, com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage> getBatteryUsageSince(long j, long j2, int i) {
            java.util.LinkedList<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> linkedList = new java.util.LinkedList<>();
            for (int i2 = 0; i2 < this.mEvents.length; i2++) {
                if ((com.android.server.am.BaseAppStateTracker.stateIndexToType(i2) & i) != 0) {
                    linkedList = add(linkedList, this.mEvents[i2]);
                }
            }
            return getBatteryUsageSince(j, j2, linkedList);
        }

        @Override // com.android.server.am.BaseAppStateDurations, com.android.server.am.BaseAppStateTimeEvents, com.android.server.am.BaseAppStateEvents
        @com.android.internal.annotations.VisibleForTesting
        java.util.LinkedList<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> add(java.util.LinkedList<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> linkedList, java.util.LinkedList<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> linkedList2) {
            com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery uidStateEventWithBattery;
            com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery uidStateEventWithBattery2;
            java.util.Iterator<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> it;
            java.util.Iterator<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> it2;
            long j;
            if (linkedList2 == null || linkedList2.size() == 0) {
                return linkedList;
            }
            if (linkedList == null || linkedList.size() == 0) {
                return (java.util.LinkedList) linkedList2.clone();
            }
            java.util.Iterator<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> it3 = linkedList.iterator();
            java.util.Iterator<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> it4 = linkedList2.iterator();
            com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery next = it3.next();
            com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery next2 = it4.next();
            java.util.LinkedList<com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery> linkedList3 = new java.util.LinkedList<>();
            com.android.server.am.AppBatteryTracker.BatteryUsage batteryUsage = new com.android.server.am.AppBatteryTracker.BatteryUsage();
            long timestamp = next.getTimestamp();
            long timestamp2 = next2.getTimestamp();
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            long j2 = 0;
            long j3 = 0;
            while (true) {
                long j4 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                if (timestamp != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME || timestamp2 != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                    boolean z4 = z || z2;
                    if (timestamp == timestamp2) {
                        if (z) {
                            batteryUsage.add(next.getBatteryUsage());
                        }
                        if (z2) {
                            batteryUsage.add(next2.getBatteryUsage());
                        }
                        j2 += (z3 && (z || z2)) ? timestamp - j3 : 0L;
                        z = !z;
                        z2 = !z2;
                        if (it3.hasNext()) {
                            uidStateEventWithBattery2 = it3.next();
                            j = uidStateEventWithBattery2.getTimestamp();
                        } else {
                            uidStateEventWithBattery2 = next;
                            j = Long.MAX_VALUE;
                        }
                        if (it4.hasNext()) {
                            next2 = it4.next();
                            j4 = next2.getTimestamp();
                        }
                        timestamp2 = j4;
                        j4 = j;
                    } else if (timestamp < timestamp2) {
                        if (z) {
                            batteryUsage.add(next.getBatteryUsage());
                        }
                        j2 += (z3 && z) ? timestamp - j3 : 0L;
                        z = !z;
                        if (it3.hasNext()) {
                            uidStateEventWithBattery2 = it3.next();
                            j4 = uidStateEventWithBattery2.getTimestamp();
                        } else {
                            uidStateEventWithBattery2 = next;
                        }
                    } else {
                        if (z2) {
                            batteryUsage.add(next2.getBatteryUsage());
                        }
                        j2 += (z3 && z2) ? timestamp2 - j3 : 0L;
                        z2 = !z2;
                        if (it4.hasNext()) {
                            uidStateEventWithBattery = it4.next();
                            j4 = uidStateEventWithBattery.getTimestamp();
                        } else {
                            uidStateEventWithBattery = next2;
                        }
                        long j5 = timestamp;
                        uidStateEventWithBattery2 = next;
                        next = next2;
                        next2 = uidStateEventWithBattery;
                        timestamp2 = j4;
                        j4 = j5;
                    }
                    z3 = z && z2;
                    if (z || z2) {
                        j3 = next.getTimestamp();
                    }
                    if (z4 == (z || z2)) {
                        it = it3;
                        it2 = it4;
                    } else {
                        com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery uidStateEventWithBattery3 = (com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery) next.clone();
                        if (!z4) {
                            it = it3;
                            it2 = it4;
                        } else {
                            com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery peekLast = linkedList3.peekLast();
                            long timestamp3 = uidStateEventWithBattery3.getTimestamp() - peekLast.getTimestamp();
                            it = it3;
                            it2 = it4;
                            long j6 = timestamp3 + j2;
                            if (j6 != 0) {
                                batteryUsage.scale((timestamp3 * 1.0d) / j6);
                                uidStateEventWithBattery3.update(peekLast, new com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage(batteryUsage));
                            } else {
                                uidStateEventWithBattery3.update(peekLast, com.android.server.am.AppBatteryTracker.BATTERY_USAGE_NONE);
                            }
                            batteryUsage.setTo(com.android.server.am.AppBatteryTracker.BATTERY_USAGE_NONE);
                            j2 = 0;
                        }
                        linkedList3.add(uidStateEventWithBattery3);
                    }
                    it3 = it;
                    next = uidStateEventWithBattery2;
                    it4 = it2;
                    timestamp = j4;
                } else {
                    return linkedList3;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trimDurations() {
        trim(java.lang.Math.max(0L, android.os.SystemClock.elapsedRealtime() - ((com.android.server.am.AppBatteryExemptionTracker.AppBatteryExemptionPolicy) this.mInjector.getPolicy()).getMaxTrackingDuration()));
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        ((com.android.server.am.AppBatteryExemptionTracker.AppBatteryExemptionPolicy) this.mInjector.getPolicy()).dump(printWriter, str);
    }

    static final class UidStateEventWithBattery extends com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent {

        @android.annotation.NonNull
        private com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage mBatteryUsage;
        private boolean mIsStart;

        @android.annotation.Nullable
        private com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery mPeer;

        UidStateEventWithBattery(boolean z, long j, @android.annotation.NonNull com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage, @android.annotation.Nullable com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery uidStateEventWithBattery) {
            super(j);
            this.mIsStart = z;
            this.mBatteryUsage = immutableBatteryUsage;
            this.mPeer = uidStateEventWithBattery;
            if (uidStateEventWithBattery != null) {
                uidStateEventWithBattery.mPeer = this;
            }
        }

        UidStateEventWithBattery(com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery uidStateEventWithBattery) {
            super(uidStateEventWithBattery);
            this.mIsStart = uidStateEventWithBattery.mIsStart;
            this.mBatteryUsage = uidStateEventWithBattery.mBatteryUsage;
        }

        @Override // com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent
        void trimTo(long j) {
            if (!this.mIsStart || j < this.mTimestamp) {
                return;
            }
            if (this.mPeer != null) {
                com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage batteryUsage = this.mPeer.getBatteryUsage();
                this.mPeer.mBatteryUsage = this.mPeer.getBatteryUsage(j, this.mPeer.mTimestamp);
                this.mBatteryUsage = this.mBatteryUsage.mutate().add(batteryUsage).subtract(this.mPeer.mBatteryUsage).unmutate();
            }
            this.mTimestamp = j;
        }

        void update(@android.annotation.NonNull com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery uidStateEventWithBattery, @android.annotation.NonNull com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage) {
            this.mPeer = uidStateEventWithBattery;
            uidStateEventWithBattery.mPeer = this;
            this.mBatteryUsage = immutableBatteryUsage;
        }

        boolean isStart() {
            return this.mIsStart;
        }

        @android.annotation.NonNull
        com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage getBatteryUsage(long j, long j2) {
            if (this.mIsStart || j >= this.mTimestamp || j2 <= j) {
                return com.android.server.am.AppBatteryTracker.BATTERY_USAGE_NONE;
            }
            long max = java.lang.Math.max(j, this.mPeer.mTimestamp);
            long min = java.lang.Math.min(j2, this.mTimestamp);
            long j3 = this.mTimestamp - this.mPeer.mTimestamp;
            long j4 = min - max;
            return j3 != 0 ? j3 == j4 ? this.mBatteryUsage : this.mBatteryUsage.mutate().scale((j4 * 1.0d) / j3).unmutate() : com.android.server.am.AppBatteryTracker.BATTERY_USAGE_NONE;
        }

        @android.annotation.NonNull
        com.android.server.am.AppBatteryTracker.ImmutableBatteryUsage getBatteryUsage() {
            return this.mBatteryUsage;
        }

        @Override // com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent
        public java.lang.Object clone() {
            return new com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery(this);
        }

        @Override // com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent
        public boolean equals(java.lang.Object obj) {
            if (obj == null || obj.getClass() != com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery.class) {
                return false;
            }
            com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery uidStateEventWithBattery = (com.android.server.am.AppBatteryExemptionTracker.UidStateEventWithBattery) obj;
            return uidStateEventWithBattery.mIsStart == this.mIsStart && uidStateEventWithBattery.mTimestamp == this.mTimestamp && this.mBatteryUsage.equals(uidStateEventWithBattery.mBatteryUsage);
        }

        public java.lang.String toString() {
            return "UidStateEventWithBattery(" + this.mIsStart + ", " + this.mTimestamp + ", " + this.mBatteryUsage + ")";
        }

        @Override // com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent
        public int hashCode() {
            return (((java.lang.Boolean.hashCode(this.mIsStart) * 31) + java.lang.Long.hashCode(this.mTimestamp)) * 31) + this.mBatteryUsage.hashCode();
        }
    }

    static final class AppBatteryExemptionPolicy extends com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy<com.android.server.am.AppBatteryExemptionTracker> {
        static final boolean DEFAULT_BG_BATTERY_EXEMPTION_ENABLED = false;
        static final java.lang.String KEY_BG_BATTERY_EXEMPTION_ENABLED = "bg_battery_exemption_enabled";

        AppBatteryExemptionPolicy(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker.Injector injector, @android.annotation.NonNull com.android.server.am.AppBatteryExemptionTracker appBatteryExemptionTracker) {
            super(injector, appBatteryExemptionTracker, KEY_BG_BATTERY_EXEMPTION_ENABLED, false, "bg_current_drain_window", appBatteryExemptionTracker.mContext.getResources().getInteger(android.R.integer.config_bg_current_drain_types_to_restricted_bucket));
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public void onMaxTrackingDurationChanged(long j) {
            android.os.Handler handler = ((com.android.server.am.AppBatteryExemptionTracker) this.mTracker).mBgHandler;
            final com.android.server.am.AppBatteryExemptionTracker appBatteryExemptionTracker = (com.android.server.am.AppBatteryExemptionTracker) this.mTracker;
            java.util.Objects.requireNonNull(appBatteryExemptionTracker);
            handler.post(new java.lang.Runnable() { // from class: com.android.server.am.AppBatteryExemptionTracker$AppBatteryExemptionPolicy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.AppBatteryExemptionTracker.this.trimDurations();
                }
            });
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onTrackerEnabled(boolean z) {
            ((com.android.server.am.AppBatteryExemptionTracker) this.mTracker).onTrackerEnabled(z);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.println("APP BATTERY EXEMPTION TRACKER POLICY SETTINGS:");
            super.dump(printWriter, "  " + str);
        }
    }
}
