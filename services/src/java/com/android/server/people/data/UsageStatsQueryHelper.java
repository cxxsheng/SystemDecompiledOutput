package com.android.server.people.data;

/* loaded from: classes2.dex */
class UsageStatsQueryHelper {
    private final com.android.server.people.data.UsageStatsQueryHelper.EventListener mEventListener;
    private long mLastEventTimestamp;
    private final java.util.function.Function<java.lang.String, com.android.server.people.data.PackageData> mPackageDataGetter;
    private final int mUserId;
    private final java.util.Map<android.content.ComponentName, android.app.usage.UsageEvents.Event> mConvoStartEvents = new android.util.ArrayMap();
    private final android.app.usage.UsageStatsManagerInternal mUsageStatsManagerInternal = getUsageStatsManagerInternal();

    interface EventListener {
        void onEvent(com.android.server.people.data.PackageData packageData, com.android.server.people.data.ConversationInfo conversationInfo, com.android.server.people.data.Event event);
    }

    UsageStatsQueryHelper(int i, java.util.function.Function<java.lang.String, com.android.server.people.data.PackageData> function, com.android.server.people.data.UsageStatsQueryHelper.EventListener eventListener) {
        this.mUserId = i;
        this.mPackageDataGetter = function;
        this.mEventListener = eventListener;
    }

    boolean querySince(long j) {
        android.app.usage.UsageEvents queryEventsForUser = this.mUsageStatsManagerInternal.queryEventsForUser(this.mUserId, j, java.lang.System.currentTimeMillis(), 0);
        boolean z = false;
        if (queryEventsForUser == null) {
            return false;
        }
        while (queryEventsForUser.hasNextEvent()) {
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event();
            queryEventsForUser.getNextEvent(event);
            this.mLastEventTimestamp = java.lang.Math.max(this.mLastEventTimestamp, event.getTimeStamp());
            java.lang.String packageName = event.getPackageName();
            com.android.server.people.data.PackageData apply = this.mPackageDataGetter.apply(packageName);
            if (apply != null) {
                switch (event.getEventType()) {
                    case 2:
                    case 23:
                    case 24:
                        onInAppConversationEnded(apply, event);
                        break;
                    case 8:
                        addEventByShortcutId(apply, event.getShortcutId(), new com.android.server.people.data.Event(event.getTimeStamp(), 1));
                        break;
                    case 30:
                        onInAppConversationEnded(apply, event);
                        android.content.LocusId locusId = event.getLocusId() != null ? new android.content.LocusId(event.getLocusId()) : null;
                        if (locusId != null && apply.getConversationStore().getConversationByLocusId(locusId) != null) {
                            this.mConvoStartEvents.put(new android.content.ComponentName(packageName, event.getClassName()), event);
                            break;
                        }
                        break;
                }
            }
            z = true;
        }
        return z;
    }

    long getLastEventTimestamp() {
        return this.mLastEventTimestamp;
    }

    static java.util.List<android.app.usage.UsageEvents.Event> queryAppMovingToForegroundEvents(int i, long j, long j2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.app.usage.UsageEvents queryEventsForUser = getUsageStatsManagerInternal().queryEventsForUser(i, j, j2, 10);
        if (queryEventsForUser == null) {
            return arrayList;
        }
        while (queryEventsForUser.hasNextEvent()) {
            android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event();
            queryEventsForUser.getNextEvent(event);
            if (event.getEventType() == 1) {
                arrayList.add(event);
            }
        }
        return arrayList;
    }

    static java.util.Map<java.lang.String, com.android.server.people.data.AppUsageStatsData> queryAppUsageStats(int i, long j, long j2, java.util.Set<java.lang.String> set) {
        java.util.List<android.app.usage.UsageStats> queryUsageStatsForUser = getUsageStatsManagerInternal().queryUsageStatsForUser(i, 4, j, j2, false);
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        if (queryUsageStatsForUser == null) {
            return arrayMap;
        }
        for (android.app.usage.UsageStats usageStats : queryUsageStatsForUser) {
            java.lang.String packageName = usageStats.getPackageName();
            if (set.contains(packageName)) {
                com.android.server.people.data.AppUsageStatsData appUsageStatsData = (com.android.server.people.data.AppUsageStatsData) arrayMap.computeIfAbsent(packageName, new java.util.function.Function() { // from class: com.android.server.people.data.UsageStatsQueryHelper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        com.android.server.people.data.AppUsageStatsData lambda$queryAppUsageStats$0;
                        lambda$queryAppUsageStats$0 = com.android.server.people.data.UsageStatsQueryHelper.lambda$queryAppUsageStats$0((java.lang.String) obj);
                        return lambda$queryAppUsageStats$0;
                    }
                });
                appUsageStatsData.incrementChosenCountBy(sumChooserCounts(usageStats.mChooserCounts));
                appUsageStatsData.incrementLaunchCountBy(usageStats.getAppLaunchCount());
            }
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.people.data.AppUsageStatsData lambda$queryAppUsageStats$0(java.lang.String str) {
        return new com.android.server.people.data.AppUsageStatsData();
    }

    private static int sumChooserCounts(android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Integer>> arrayMap) {
        if (arrayMap == null) {
            return 0;
        }
        int size = arrayMap.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            android.util.ArrayMap<java.lang.String, java.lang.Integer> valueAt = arrayMap.valueAt(i2);
            if (valueAt != null) {
                int size2 = valueAt.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    i += valueAt.valueAt(i3).intValue();
                }
            }
        }
        return i;
    }

    private void onInAppConversationEnded(@android.annotation.NonNull com.android.server.people.data.PackageData packageData, @android.annotation.NonNull android.app.usage.UsageEvents.Event event) {
        android.app.usage.UsageEvents.Event remove = this.mConvoStartEvents.remove(new android.content.ComponentName(event.getPackageName(), event.getClassName()));
        if (remove == null || remove.getTimeStamp() >= event.getTimeStamp()) {
            return;
        }
        addEventByLocusId(packageData, new android.content.LocusId(remove.getLocusId()), new com.android.server.people.data.Event.Builder(remove.getTimeStamp(), 13).setDurationSeconds((int) ((event.getTimeStamp() - remove.getTimeStamp()) / 1000)).build());
    }

    private void addEventByShortcutId(com.android.server.people.data.PackageData packageData, java.lang.String str, com.android.server.people.data.Event event) {
        com.android.server.people.data.ConversationInfo conversation = packageData.getConversationStore().getConversation(str);
        if (conversation == null) {
            return;
        }
        packageData.getEventStore().getOrCreateEventHistory(0, str).addEvent(event);
        this.mEventListener.onEvent(packageData, conversation, event);
    }

    private void addEventByLocusId(com.android.server.people.data.PackageData packageData, android.content.LocusId locusId, com.android.server.people.data.Event event) {
        com.android.server.people.data.ConversationInfo conversationByLocusId = packageData.getConversationStore().getConversationByLocusId(locusId);
        if (conversationByLocusId == null) {
            return;
        }
        packageData.getEventStore().getOrCreateEventHistory(1, locusId.getId()).addEvent(event);
        this.mEventListener.onEvent(packageData, conversationByLocusId, event);
    }

    private static android.app.usage.UsageStatsManagerInternal getUsageStatsManagerInternal() {
        return (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
    }
}
