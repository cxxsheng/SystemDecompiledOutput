package com.android.server.appop;

/* loaded from: classes.dex */
final class AttributedOp {

    @android.annotation.Nullable
    private android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> mAccessEvents;

    @android.annotation.NonNull
    private final com.android.server.appop.AppOpsService mAppOpsService;

    @android.annotation.Nullable
    android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AttributedOp.InProgressStartOpEvent> mInProgressEvents;

    @android.annotation.Nullable
    android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AttributedOp.InProgressStartOpEvent> mPausedInProgressEvents;

    @android.annotation.Nullable
    private android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> mRejectEvents;

    @android.annotation.NonNull
    public final com.android.server.appop.AppOpsService.Op parent;

    @android.annotation.NonNull
    public final java.lang.String persistentDeviceId;

    @android.annotation.Nullable
    public final java.lang.String tag;

    AttributedOp(@android.annotation.NonNull com.android.server.appop.AppOpsService appOpsService, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull com.android.server.appop.AppOpsService.Op op) {
        this.mAppOpsService = appOpsService;
        this.tag = str;
        this.persistentDeviceId = str2;
        this.parent = op;
    }

    public void accessed(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, int i3) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        accessed(currentTimeMillis, -1L, i, str, str2, i2, i3);
        this.mAppOpsService.mHistoricalRegistry.incrementOpAccessedCount(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, i2, i3, currentTimeMillis, 0, -1);
    }

    public void accessed(long j, long j2, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, int i3) {
        android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo;
        long makeKey = android.app.AppOpsManager.makeKey(i2, i3);
        if (this.mAccessEvents == null) {
            this.mAccessEvents = new android.util.LongSparseArray<>(1);
        }
        if (i == -1) {
            opEventProxyInfo = null;
        } else {
            opEventProxyInfo = this.mAppOpsService.mOpEventProxyInfoPool.acquire(i, str, str2);
        }
        android.app.AppOpsManager.NoteOpEvent noteOpEvent = this.mAccessEvents.get(makeKey);
        if (noteOpEvent != null) {
            noteOpEvent.reinit(j, j2, opEventProxyInfo, this.mAppOpsService.mOpEventProxyInfoPool);
        } else {
            this.mAccessEvents.put(makeKey, new android.app.AppOpsManager.NoteOpEvent(j, j2, opEventProxyInfo));
        }
    }

    public void rejected(int i, int i2) {
        rejected(java.lang.System.currentTimeMillis(), i, i2);
        this.mAppOpsService.mHistoricalRegistry.incrementOpRejected(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, i, i2);
    }

    public void rejected(long j, int i, int i2) {
        long makeKey = android.app.AppOpsManager.makeKey(i, i2);
        if (this.mRejectEvents == null) {
            this.mRejectEvents = new android.util.LongSparseArray<>(1);
        }
        android.app.AppOpsManager.NoteOpEvent noteOpEvent = this.mRejectEvents.get(makeKey);
        if (noteOpEvent != null) {
            noteOpEvent.reinit(j, -1L, (android.app.AppOpsManager.OpEventProxyInfo) null, this.mAppOpsService.mOpEventProxyInfoPool);
        } else {
            this.mRejectEvents.put(makeKey, new android.app.AppOpsManager.NoteOpEvent(j, -1L, (android.app.AppOpsManager.OpEventProxyInfo) null));
        }
    }

    public void started(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
        startedOrPaused(iBinder, i, str, str2, i2, i3, i4, false, true, i5, i6);
    }

    private void startedOrPaused(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, int i3, int i4, boolean z, boolean z2, int i5, int i6) throws android.os.RemoteException {
        android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AttributedOp.InProgressStartOpEvent> arrayMap;
        com.android.server.appop.AttributedOp attributedOp;
        if (!z && !this.parent.isRunning() && z2) {
            this.mAppOpsService.scheduleOpActiveChangedIfNeededLocked(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, i2, true, i5, i6);
        }
        if (z2 && this.mInProgressEvents == null) {
            this.mInProgressEvents = new android.util.ArrayMap<>(1);
        } else if (!z2 && this.mPausedInProgressEvents == null) {
            this.mPausedInProgressEvents = new android.util.ArrayMap<>(1);
        }
        if (!z2) {
            arrayMap = this.mPausedInProgressEvents;
        } else {
            arrayMap = this.mInProgressEvents;
        }
        android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AttributedOp.InProgressStartOpEvent> arrayMap2 = arrayMap;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        com.android.server.appop.AttributedOp.InProgressStartOpEvent inProgressStartOpEvent = arrayMap2.get(iBinder);
        if (inProgressStartOpEvent == null) {
            inProgressStartOpEvent = this.mAppOpsService.mInProgressStartOpEventPool.acquire(currentTimeMillis, android.os.SystemClock.elapsedRealtime(), iBinder, this.tag, i2, com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.BiConsumer() { // from class: com.android.server.appop.AttributedOp$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.appop.AppOpsService.onClientDeath((com.android.server.appop.AttributedOp) obj, (android.os.IBinder) obj2);
                }
            }, this, iBinder), i, str, str2, i3, i4, i5, i6);
            arrayMap2.put(iBinder, inProgressStartOpEvent);
            attributedOp = this;
        } else if (i3 == inProgressStartOpEvent.getUidState()) {
            attributedOp = this;
        } else {
            attributedOp = this;
            attributedOp.onUidStateChanged(i3);
        }
        inProgressStartOpEvent.mNumUnfinishedStarts++;
        if (z2) {
            attributedOp.mAppOpsService.mHistoricalRegistry.incrementOpAccessedCount(attributedOp.parent.op, attributedOp.parent.uid, attributedOp.parent.packageName, attributedOp.tag, i3, i4, currentTimeMillis, i5, i6);
        }
    }

    public void finished(@android.annotation.NonNull android.os.IBinder iBinder) {
        finished(iBinder, false);
    }

    private void finished(@android.annotation.NonNull android.os.IBinder iBinder, boolean z) {
        finishOrPause(iBinder, z, false);
    }

    private void finishOrPause(@android.annotation.NonNull android.os.IBinder iBinder, boolean z, boolean z2) {
        int indexOfKey = isRunning() ? this.mInProgressEvents.indexOfKey(iBinder) : -1;
        if (indexOfKey < 0) {
            finishPossiblyPaused(iBinder, z2);
            return;
        }
        com.android.server.appop.AttributedOp.InProgressStartOpEvent valueAt = this.mInProgressEvents.valueAt(indexOfKey);
        if (!z2) {
            valueAt.mNumUnfinishedStarts--;
        }
        if (valueAt.mNumUnfinishedStarts == 0 || z2) {
            if (!z2) {
                valueAt.finish();
                this.mInProgressEvents.removeAt(indexOfKey);
            }
            if (this.mAccessEvents == null) {
                this.mAccessEvents = new android.util.LongSparseArray<>(1);
            }
            android.app.AppOpsManager.NoteOpEvent noteOpEvent = new android.app.AppOpsManager.NoteOpEvent(valueAt.getStartTime(), android.os.SystemClock.elapsedRealtime() - valueAt.getStartElapsedTime(), valueAt.getProxy() != null ? new android.app.AppOpsManager.OpEventProxyInfo(valueAt.getProxy()) : null);
            this.mAccessEvents.put(android.app.AppOpsManager.makeKey(valueAt.getUidState(), valueAt.getFlags()), noteOpEvent);
            this.mAppOpsService.mHistoricalRegistry.increaseOpAccessDuration(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, valueAt.getUidState(), valueAt.getFlags(), noteOpEvent.getNoteTime(), noteOpEvent.getDuration(), valueAt.getAttributionFlags(), valueAt.getAttributionChainId());
            if (!z2) {
                this.mAppOpsService.mInProgressStartOpEventPool.release(valueAt);
                if (this.mInProgressEvents.isEmpty()) {
                    this.mInProgressEvents = null;
                    if (!z && !this.parent.isRunning()) {
                        this.mAppOpsService.scheduleOpActiveChangedIfNeededLocked(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, valueAt.getVirtualDeviceId(), false, valueAt.getAttributionFlags(), valueAt.getAttributionChainId());
                    }
                }
            }
        }
    }

    private void finishPossiblyPaused(@android.annotation.NonNull android.os.IBinder iBinder, boolean z) {
        if (!isPaused()) {
            android.util.Slog.wtf("AppOps", "No ops running or paused");
            return;
        }
        int indexOfKey = this.mPausedInProgressEvents.indexOfKey(iBinder);
        if (indexOfKey < 0) {
            android.util.Slog.wtf("AppOps", "No op running or paused for the client");
            return;
        }
        if (z) {
            return;
        }
        com.android.server.appop.AttributedOp.InProgressStartOpEvent valueAt = this.mPausedInProgressEvents.valueAt(indexOfKey);
        valueAt.mNumUnfinishedStarts--;
        if (valueAt.mNumUnfinishedStarts == 0) {
            this.mPausedInProgressEvents.removeAt(indexOfKey);
            this.mAppOpsService.mInProgressStartOpEventPool.release(valueAt);
            if (this.mPausedInProgressEvents.isEmpty()) {
                this.mPausedInProgressEvents = null;
            }
        }
    }

    public void createPaused(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
        startedOrPaused(iBinder, i, str, str2, i2, i3, i4, false, false, i5, i6);
    }

    public void pause() {
        if (!isRunning()) {
            return;
        }
        if (this.mPausedInProgressEvents == null) {
            this.mPausedInProgressEvents = new android.util.ArrayMap<>(1);
        }
        for (int i = 0; i < this.mInProgressEvents.size(); i++) {
            com.android.server.appop.AttributedOp.InProgressStartOpEvent valueAt = this.mInProgressEvents.valueAt(i);
            this.mPausedInProgressEvents.put(valueAt.getClientId(), valueAt);
            finishOrPause(valueAt.getClientId(), false, true);
            this.mAppOpsService.scheduleOpActiveChangedIfNeededLocked(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, valueAt.getVirtualDeviceId(), false, valueAt.getAttributionFlags(), valueAt.getAttributionChainId());
        }
        this.mInProgressEvents = null;
    }

    public void resume() {
        if (!isPaused()) {
            return;
        }
        if (this.mInProgressEvents == null) {
            this.mInProgressEvents = new android.util.ArrayMap<>(this.mPausedInProgressEvents.size());
        }
        boolean z = !this.mPausedInProgressEvents.isEmpty() && this.mInProgressEvents.isEmpty();
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        for (int i = 0; i < this.mPausedInProgressEvents.size(); i++) {
            com.android.server.appop.AttributedOp.InProgressStartOpEvent valueAt = this.mPausedInProgressEvents.valueAt(i);
            this.mInProgressEvents.put(valueAt.getClientId(), valueAt);
            valueAt.setStartElapsedTime(android.os.SystemClock.elapsedRealtime());
            valueAt.setStartTime(currentTimeMillis);
            this.mAppOpsService.mHistoricalRegistry.incrementOpAccessedCount(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, valueAt.getUidState(), valueAt.getFlags(), currentTimeMillis, valueAt.getAttributionFlags(), valueAt.getAttributionChainId());
            if (z) {
                this.mAppOpsService.scheduleOpActiveChangedIfNeededLocked(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, valueAt.getVirtualDeviceId(), true, valueAt.getAttributionFlags(), valueAt.getAttributionChainId());
            }
            this.mAppOpsService.scheduleOpStartedIfNeededLocked(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, valueAt.getVirtualDeviceId(), valueAt.getFlags(), 0, 2, valueAt.getAttributionFlags(), valueAt.getAttributionChainId());
        }
        this.mPausedInProgressEvents = null;
    }

    void onClientDeath(@android.annotation.NonNull android.os.IBinder iBinder) {
        synchronized (this.mAppOpsService) {
            try {
                if (isPaused() || isRunning()) {
                    com.android.server.appop.AttributedOp.InProgressStartOpEvent inProgressStartOpEvent = (isPaused() ? this.mPausedInProgressEvents : this.mInProgressEvents).get(iBinder);
                    if (inProgressStartOpEvent != null) {
                        inProgressStartOpEvent.mNumUnfinishedStarts = 1;
                    }
                    finished(iBinder);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onUidStateChanged(int i) {
        int i2;
        int i3;
        java.util.ArrayList arrayList;
        com.android.server.appop.AttributedOp.InProgressStartOpEvent inProgressStartOpEvent;
        int i4;
        android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AttributedOp.InProgressStartOpEvent> arrayMap;
        android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AttributedOp.InProgressStartOpEvent> arrayMap2;
        if (!isPaused() && !isRunning()) {
            return;
        }
        boolean isRunning = isRunning();
        android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AttributedOp.InProgressStartOpEvent> arrayMap3 = isRunning ? this.mInProgressEvents : this.mPausedInProgressEvents;
        int size = arrayMap3.size();
        java.util.ArrayList arrayList2 = new java.util.ArrayList(arrayMap3.keySet());
        android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AttributedOp.InProgressStartOpEvent> arrayMap4 = arrayMap3;
        int i5 = 0;
        while (i5 < size) {
            com.android.server.appop.AttributedOp.InProgressStartOpEvent inProgressStartOpEvent2 = arrayMap4.get(arrayList2.get(i5));
            if (inProgressStartOpEvent2 == null || inProgressStartOpEvent2.getUidState() == i) {
                i2 = i5;
                i3 = size;
                arrayList = arrayList2;
                arrayMap4 = arrayMap4;
            } else {
                int attributionFlags = inProgressStartOpEvent2.getAttributionFlags();
                int attributionChainId = inProgressStartOpEvent2.getAttributionChainId();
                try {
                    int i6 = inProgressStartOpEvent2.mNumUnfinishedStarts;
                    inProgressStartOpEvent2.mNumUnfinishedStarts = 1;
                    android.app.AppOpsManager.OpEventProxyInfo proxy = inProgressStartOpEvent2.getProxy();
                    finished(inProgressStartOpEvent2.getClientId(), true);
                    if (proxy != null) {
                        try {
                            inProgressStartOpEvent = inProgressStartOpEvent2;
                            i4 = i5;
                            arrayMap = arrayMap4;
                            i3 = size;
                            arrayList = arrayList2;
                            try {
                                startedOrPaused(inProgressStartOpEvent2.getClientId(), proxy.getUid(), proxy.getPackageName(), proxy.getAttributionTag(), inProgressStartOpEvent2.getVirtualDeviceId(), i, inProgressStartOpEvent2.getFlags(), true, isRunning, inProgressStartOpEvent2.getAttributionFlags(), inProgressStartOpEvent2.getAttributionChainId());
                            } catch (android.os.RemoteException e) {
                                arrayMap4 = arrayMap;
                                i2 = i4;
                                this.mAppOpsService.scheduleOpActiveChangedIfNeededLocked(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, inProgressStartOpEvent.getVirtualDeviceId(), false, attributionFlags, attributionChainId);
                                i5 = i2 + 1;
                                arrayList2 = arrayList;
                                size = i3;
                            }
                        } catch (android.os.RemoteException e2) {
                            inProgressStartOpEvent = inProgressStartOpEvent2;
                            i4 = i5;
                            i3 = size;
                            arrayList = arrayList2;
                        }
                    } else {
                        inProgressStartOpEvent = inProgressStartOpEvent2;
                        i4 = i5;
                        arrayMap = arrayMap4;
                        i3 = size;
                        arrayList = arrayList2;
                        startedOrPaused(inProgressStartOpEvent.getClientId(), -1, null, null, inProgressStartOpEvent.getVirtualDeviceId(), i, inProgressStartOpEvent.getFlags(), true, isRunning, inProgressStartOpEvent.getAttributionFlags(), inProgressStartOpEvent.getAttributionChainId());
                    }
                    if (isRunning) {
                        arrayMap2 = this.mInProgressEvents;
                    } else {
                        try {
                            arrayMap2 = this.mPausedInProgressEvents;
                        } catch (android.os.RemoteException e3) {
                            i2 = i4;
                            arrayMap4 = arrayMap;
                            this.mAppOpsService.scheduleOpActiveChangedIfNeededLocked(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, inProgressStartOpEvent.getVirtualDeviceId(), false, attributionFlags, attributionChainId);
                            i5 = i2 + 1;
                            arrayList2 = arrayList;
                            size = i3;
                        }
                    }
                    arrayMap4 = arrayMap2;
                    i2 = i4;
                    try {
                        com.android.server.appop.AttributedOp.InProgressStartOpEvent inProgressStartOpEvent3 = arrayMap4.get(arrayList.get(i2));
                        if (inProgressStartOpEvent3 != null) {
                            inProgressStartOpEvent3.mNumUnfinishedStarts += i6 - 1;
                        }
                    } catch (android.os.RemoteException e4) {
                        this.mAppOpsService.scheduleOpActiveChangedIfNeededLocked(this.parent.op, this.parent.uid, this.parent.packageName, this.tag, inProgressStartOpEvent.getVirtualDeviceId(), false, attributionFlags, attributionChainId);
                        i5 = i2 + 1;
                        arrayList2 = arrayList;
                        size = i3;
                    }
                } catch (android.os.RemoteException e5) {
                    inProgressStartOpEvent = inProgressStartOpEvent2;
                    i2 = i5;
                    i3 = size;
                    arrayList = arrayList2;
                }
            }
            i5 = i2 + 1;
            arrayList2 = arrayList;
            size = i3;
        }
    }

    @android.annotation.Nullable
    private android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> add(@android.annotation.Nullable android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> longSparseArray, @android.annotation.Nullable android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> longSparseArray2) {
        if (longSparseArray == null) {
            return longSparseArray2;
        }
        if (longSparseArray2 == null) {
            return longSparseArray;
        }
        int size = longSparseArray2.size();
        for (int i = 0; i < size; i++) {
            long keyAt = longSparseArray2.keyAt(i);
            android.app.AppOpsManager.NoteOpEvent valueAt = longSparseArray2.valueAt(i);
            android.app.AppOpsManager.NoteOpEvent noteOpEvent = longSparseArray.get(keyAt);
            if (noteOpEvent == null || valueAt.getNoteTime() > noteOpEvent.getNoteTime()) {
                longSparseArray.put(keyAt, valueAt);
            }
        }
        return longSparseArray;
    }

    public void add(@android.annotation.NonNull com.android.server.appop.AttributedOp attributedOp) {
        if (attributedOp.isRunning() || attributedOp.isPaused()) {
            android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AttributedOp.InProgressStartOpEvent> arrayMap = attributedOp.isRunning() ? attributedOp.mInProgressEvents : attributedOp.mPausedInProgressEvents;
            android.util.Slog.w("AppOps", "Ignoring " + arrayMap.size() + " app-ops, running: " + attributedOp.isRunning());
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                com.android.server.appop.AttributedOp.InProgressStartOpEvent valueAt = arrayMap.valueAt(i);
                valueAt.finish();
                this.mAppOpsService.mInProgressStartOpEventPool.release(valueAt);
            }
        }
        this.mAccessEvents = add(this.mAccessEvents, attributedOp.mAccessEvents);
        this.mRejectEvents = add(this.mRejectEvents, attributedOp.mRejectEvents);
    }

    public boolean isRunning() {
        return (this.mInProgressEvents == null || this.mInProgressEvents.isEmpty()) ? false : true;
    }

    public boolean isPaused() {
        return (this.mPausedInProgressEvents == null || this.mPausedInProgressEvents.isEmpty()) ? false : true;
    }

    boolean hasAnyTime() {
        return (this.mAccessEvents != null && this.mAccessEvents.size() > 0) || (this.mRejectEvents != null && this.mRejectEvents.size() > 0);
    }

    @android.annotation.Nullable
    private android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> deepClone(@android.annotation.Nullable android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> longSparseArray) {
        if (longSparseArray == null) {
            return longSparseArray;
        }
        int size = longSparseArray.size();
        android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> longSparseArray2 = new android.util.LongSparseArray<>(size);
        for (int i = 0; i < size; i++) {
            longSparseArray2.put(longSparseArray.keyAt(i), new android.app.AppOpsManager.NoteOpEvent(longSparseArray.valueAt(i)));
        }
        return longSparseArray2;
    }

    @android.annotation.NonNull
    android.app.AppOpsManager.AttributedOpEntry createAttributedOpEntryLocked() {
        android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> deepClone = deepClone(this.mAccessEvents);
        if (isRunning()) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            int size = this.mInProgressEvents.size();
            if (deepClone == null) {
                deepClone = new android.util.LongSparseArray<>(size);
            }
            for (int i = 0; i < size; i++) {
                com.android.server.appop.AttributedOp.InProgressStartOpEvent valueAt = this.mInProgressEvents.valueAt(i);
                deepClone.append(android.app.AppOpsManager.makeKey(valueAt.getUidState(), valueAt.getFlags()), new android.app.AppOpsManager.NoteOpEvent(valueAt.getStartTime(), elapsedRealtime - valueAt.getStartElapsedTime(), valueAt.getProxy()));
            }
        }
        return new android.app.AppOpsManager.AttributedOpEntry(this.parent.op, isRunning(), deepClone, deepClone(this.mRejectEvents));
    }

    static final class InProgressStartOpEvent implements android.os.IBinder.DeathRecipient {
        private int mAttributionChainId;
        private int mAttributionFlags;

        @android.annotation.Nullable
        private java.lang.String mAttributionTag;

        @android.annotation.NonNull
        private android.os.IBinder mClientId;
        private int mFlags;
        int mNumUnfinishedStarts;

        @android.annotation.NonNull
        private java.lang.Runnable mOnDeath;

        @android.annotation.Nullable
        private android.app.AppOpsManager.OpEventProxyInfo mProxy;
        private long mStartElapsedTime;
        private long mStartTime;
        private int mUidState;
        private int mVirtualDeviceId;

        InProgressStartOpEvent(long j, long j2, @android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.Runnable runnable, int i2, @android.annotation.Nullable android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo, int i3, int i4, int i5) throws android.os.RemoteException {
            this.mStartTime = j;
            this.mStartElapsedTime = j2;
            this.mClientId = iBinder;
            this.mVirtualDeviceId = i;
            this.mAttributionTag = str;
            this.mOnDeath = runnable;
            this.mUidState = i2;
            this.mProxy = opEventProxyInfo;
            this.mFlags = i3;
            this.mAttributionFlags = i4;
            this.mAttributionChainId = i5;
            iBinder.linkToDeath(this, 0);
        }

        public void finish() {
            try {
                this.mClientId.unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mOnDeath.run();
        }

        public void reinit(long j, long j2, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.Nullable java.lang.String str, int i, @android.annotation.NonNull java.lang.Runnable runnable, int i2, int i3, @android.annotation.Nullable android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo, int i4, int i5, @android.annotation.NonNull android.util.Pools.Pool<android.app.AppOpsManager.OpEventProxyInfo> pool) throws android.os.RemoteException {
            this.mStartTime = j;
            this.mStartElapsedTime = j2;
            this.mClientId = iBinder;
            this.mAttributionTag = str;
            this.mOnDeath = runnable;
            this.mVirtualDeviceId = i;
            this.mUidState = i2;
            this.mFlags = i3;
            if (this.mProxy != null) {
                pool.release(this.mProxy);
            }
            this.mProxy = opEventProxyInfo;
            this.mAttributionFlags = i4;
            this.mAttributionChainId = i5;
            iBinder.linkToDeath(this, 0);
        }

        public long getStartTime() {
            return this.mStartTime;
        }

        public long getStartElapsedTime() {
            return this.mStartElapsedTime;
        }

        @android.annotation.NonNull
        public android.os.IBinder getClientId() {
            return this.mClientId;
        }

        public int getUidState() {
            return this.mUidState;
        }

        @android.annotation.Nullable
        public android.app.AppOpsManager.OpEventProxyInfo getProxy() {
            return this.mProxy;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public int getAttributionFlags() {
            return this.mAttributionFlags;
        }

        public int getAttributionChainId() {
            return this.mAttributionChainId;
        }

        public int getVirtualDeviceId() {
            return this.mVirtualDeviceId;
        }

        public void setStartTime(long j) {
            this.mStartTime = j;
        }

        public void setStartElapsedTime(long j) {
            this.mStartElapsedTime = j;
        }
    }

    static class InProgressStartOpEventPool extends android.util.Pools.SimplePool<com.android.server.appop.AttributedOp.InProgressStartOpEvent> {
        private com.android.server.appop.AttributedOp.OpEventProxyInfoPool mOpEventProxyInfoPool;

        InProgressStartOpEventPool(com.android.server.appop.AttributedOp.OpEventProxyInfoPool opEventProxyInfoPool, int i) {
            super(i);
            this.mOpEventProxyInfoPool = opEventProxyInfoPool;
        }

        com.android.server.appop.AttributedOp.InProgressStartOpEvent acquire(long j, long j2, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.Nullable java.lang.String str, int i, @android.annotation.NonNull java.lang.Runnable runnable, int i2, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
            android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo;
            com.android.server.appop.AttributedOp.InProgressStartOpEvent inProgressStartOpEvent = (com.android.server.appop.AttributedOp.InProgressStartOpEvent) acquire();
            if (i2 == -1) {
                opEventProxyInfo = null;
            } else {
                opEventProxyInfo = this.mOpEventProxyInfoPool.acquire(i2, str2, str3);
            }
            if (inProgressStartOpEvent != null) {
                inProgressStartOpEvent.reinit(j, j2, iBinder, str, i, runnable, i3, i4, opEventProxyInfo, i5, i6, this.mOpEventProxyInfoPool);
                return inProgressStartOpEvent;
            }
            return new com.android.server.appop.AttributedOp.InProgressStartOpEvent(j, j2, iBinder, i, str, runnable, i3, opEventProxyInfo, i4, i5, i6);
        }
    }

    static class OpEventProxyInfoPool extends android.util.Pools.SimplePool<android.app.AppOpsManager.OpEventProxyInfo> {
        OpEventProxyInfoPool(int i) {
            super(i);
        }

        android.app.AppOpsManager.OpEventProxyInfo acquire(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
            android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo = (android.app.AppOpsManager.OpEventProxyInfo) acquire();
            if (opEventProxyInfo != null) {
                opEventProxyInfo.reinit(i, str, str2);
                return opEventProxyInfo;
            }
            return new android.app.AppOpsManager.OpEventProxyInfo(i, str, str2);
        }
    }
}
