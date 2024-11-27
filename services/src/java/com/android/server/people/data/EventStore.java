package com.android.server.people.data;

/* loaded from: classes2.dex */
class EventStore {
    static final int CATEGORY_CALL = 2;
    static final int CATEGORY_CLASS_BASED = 4;
    static final int CATEGORY_LOCUS_ID_BASED = 1;
    static final int CATEGORY_SHORTCUT_BASED = 0;
    static final int CATEGORY_SMS = 3;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.List<java.util.Map<java.lang.String, com.android.server.people.data.EventHistoryImpl>> mEventHistoryMaps = new java.util.ArrayList();
    private final java.util.List<java.io.File> mEventsCategoryDirs = new java.util.ArrayList();
    private final java.util.concurrent.ScheduledExecutorService mScheduledExecutorService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface EventCategory {
    }

    EventStore(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
        this.mEventHistoryMaps.add(0, new android.util.ArrayMap());
        this.mEventHistoryMaps.add(1, new android.util.ArrayMap());
        this.mEventHistoryMaps.add(2, new android.util.ArrayMap());
        this.mEventHistoryMaps.add(3, new android.util.ArrayMap());
        this.mEventHistoryMaps.add(4, new android.util.ArrayMap());
        java.io.File file2 = new java.io.File(file, "event");
        this.mEventsCategoryDirs.add(0, new java.io.File(file2, "shortcut"));
        this.mEventsCategoryDirs.add(1, new java.io.File(file2, "locus"));
        this.mEventsCategoryDirs.add(2, new java.io.File(file2, "call"));
        this.mEventsCategoryDirs.add(3, new java.io.File(file2, "sms"));
        this.mEventsCategoryDirs.add(4, new java.io.File(file2, "class"));
        this.mScheduledExecutorService = scheduledExecutorService;
    }

    synchronized void loadFromDisk() {
        for (int i = 0; i < this.mEventsCategoryDirs.size(); i++) {
            this.mEventHistoryMaps.get(i).putAll(com.android.server.people.data.EventHistoryImpl.eventHistoriesImplFromDisk(this.mEventsCategoryDirs.get(i), this.mScheduledExecutorService));
        }
    }

    synchronized void saveToDisk() {
        java.util.Iterator<java.util.Map<java.lang.String, com.android.server.people.data.EventHistoryImpl>> it = this.mEventHistoryMaps.iterator();
        while (it.hasNext()) {
            java.util.Iterator<com.android.server.people.data.EventHistoryImpl> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                it2.next().saveToDisk();
            }
        }
    }

    @android.annotation.Nullable
    synchronized com.android.server.people.data.EventHistory getEventHistory(int i, java.lang.String str) {
        return this.mEventHistoryMaps.get(i).get(str);
    }

    @android.annotation.NonNull
    synchronized com.android.server.people.data.EventHistoryImpl getOrCreateEventHistory(final int i, final java.lang.String str) {
        return this.mEventHistoryMaps.get(i).computeIfAbsent(str, new java.util.function.Function() { // from class: com.android.server.people.data.EventStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.people.data.EventHistoryImpl lambda$getOrCreateEventHistory$0;
                lambda$getOrCreateEventHistory$0 = com.android.server.people.data.EventStore.this.lambda$getOrCreateEventHistory$0(i, str, (java.lang.String) obj);
                return lambda$getOrCreateEventHistory$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.people.data.EventHistoryImpl lambda$getOrCreateEventHistory$0(int i, java.lang.String str, java.lang.String str2) {
        return new com.android.server.people.data.EventHistoryImpl(new java.io.File(this.mEventsCategoryDirs.get(i), android.net.Uri.encode(str)), this.mScheduledExecutorService);
    }

    synchronized void deleteEventHistory(int i, java.lang.String str) {
        com.android.server.people.data.EventHistoryImpl remove = this.mEventHistoryMaps.get(i).remove(str);
        if (remove != null) {
            remove.onDestroy();
        }
    }

    synchronized void deleteEventHistories(int i) {
        try {
            java.util.Iterator<com.android.server.people.data.EventHistoryImpl> it = this.mEventHistoryMaps.get(i).values().iterator();
            while (it.hasNext()) {
                it.next().onDestroy();
            }
            this.mEventHistoryMaps.get(i).clear();
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized void pruneOldEvents() {
        java.util.Iterator<java.util.Map<java.lang.String, com.android.server.people.data.EventHistoryImpl>> it = this.mEventHistoryMaps.iterator();
        while (it.hasNext()) {
            java.util.Iterator<com.android.server.people.data.EventHistoryImpl> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                it2.next().pruneOldEvents();
            }
        }
    }

    synchronized void pruneOrphanEventHistories(int i, java.util.function.Predicate<java.lang.String> predicate) {
        try {
            java.util.Set<java.lang.String> keySet = this.mEventHistoryMaps.get(i).keySet();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str : keySet) {
                if (!predicate.test(str)) {
                    arrayList.add(str);
                }
            }
            java.util.Map<java.lang.String, com.android.server.people.data.EventHistoryImpl> map = this.mEventHistoryMaps.get(i);
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                com.android.server.people.data.EventHistoryImpl remove = map.remove((java.lang.String) it.next());
                if (remove != null) {
                    remove.onDestroy();
                }
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized void onDestroy() {
        java.util.Iterator<java.util.Map<java.lang.String, com.android.server.people.data.EventHistoryImpl>> it = this.mEventHistoryMaps.iterator();
        while (it.hasNext()) {
            java.util.Iterator<com.android.server.people.data.EventHistoryImpl> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                it2.next().onDestroy();
            }
        }
    }
}
