package com.android.server.people.data;

/* loaded from: classes2.dex */
public class PackageData {

    @android.annotation.NonNull
    private final com.android.server.people.data.ConversationStore mConversationStore;

    @android.annotation.NonNull
    private final com.android.server.people.data.EventStore mEventStore;
    private final java.util.function.Predicate<java.lang.String> mIsDefaultDialerPredicate;
    private final java.util.function.Predicate<java.lang.String> mIsDefaultSmsAppPredicate;
    private final java.io.File mPackageDataDir;

    @android.annotation.NonNull
    private final java.lang.String mPackageName;
    private final int mUserId;

    PackageData(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.util.function.Predicate<java.lang.String> predicate, @android.annotation.NonNull java.util.function.Predicate<java.lang.String> predicate2, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService, @android.annotation.NonNull java.io.File file) {
        this.mPackageName = str;
        this.mUserId = i;
        this.mPackageDataDir = new java.io.File(file, this.mPackageName);
        this.mPackageDataDir.mkdirs();
        this.mConversationStore = new com.android.server.people.data.ConversationStore(this.mPackageDataDir, scheduledExecutorService);
        this.mEventStore = new com.android.server.people.data.EventStore(this.mPackageDataDir, scheduledExecutorService);
        this.mIsDefaultDialerPredicate = predicate;
        this.mIsDefaultSmsAppPredicate = predicate2;
    }

    @android.annotation.NonNull
    static java.util.Map<java.lang.String, com.android.server.people.data.PackageData> packagesDataFromDisk(int i, @android.annotation.NonNull java.util.function.Predicate<java.lang.String> predicate, @android.annotation.NonNull java.util.function.Predicate<java.lang.String> predicate2, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService, @android.annotation.NonNull java.io.File file) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        java.io.File[] listFiles = file.listFiles(new com.android.server.cpu.CpuInfoReader$$ExternalSyntheticLambda2());
        if (listFiles == null) {
            return arrayMap;
        }
        for (java.io.File file2 : listFiles) {
            com.android.server.people.data.PackageData packageData = new com.android.server.people.data.PackageData(file2.getName(), i, predicate, predicate2, scheduledExecutorService, file);
            packageData.loadFromDisk();
            arrayMap.put(file2.getName(), packageData);
        }
        return arrayMap;
    }

    private void loadFromDisk() {
        this.mConversationStore.loadConversationsFromDisk();
        this.mEventStore.loadFromDisk();
    }

    void saveToDisk() {
        this.mConversationStore.saveConversationsToDisk();
        this.mEventStore.saveToDisk();
    }

    @android.annotation.NonNull
    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public void forAllConversations(@android.annotation.NonNull java.util.function.Consumer<com.android.server.people.data.ConversationInfo> consumer) {
        this.mConversationStore.forAllConversations(consumer);
    }

    @android.annotation.Nullable
    public com.android.server.people.data.ConversationInfo getConversationInfo(@android.annotation.NonNull java.lang.String str) {
        return getConversationStore().getConversation(str);
    }

    @android.annotation.NonNull
    public com.android.server.people.data.EventHistory getEventHistory(@android.annotation.NonNull java.lang.String str) {
        com.android.server.people.data.EventHistory eventHistory;
        com.android.server.people.data.EventHistory eventHistory2;
        com.android.server.people.data.EventHistory eventHistory3;
        com.android.server.people.data.AggregateEventHistoryImpl aggregateEventHistoryImpl = new com.android.server.people.data.AggregateEventHistoryImpl();
        com.android.server.people.data.ConversationInfo conversation = this.mConversationStore.getConversation(str);
        if (conversation == null) {
            return aggregateEventHistoryImpl;
        }
        com.android.server.people.data.EventHistory eventHistory4 = getEventStore().getEventHistory(0, str);
        if (eventHistory4 != null) {
            aggregateEventHistoryImpl.addEventHistory(eventHistory4);
        }
        android.content.LocusId locusId = conversation.getLocusId();
        if (locusId != null && (eventHistory3 = getEventStore().getEventHistory(1, locusId.getId())) != null) {
            aggregateEventHistoryImpl.addEventHistory(eventHistory3);
        }
        java.lang.String contactPhoneNumber = conversation.getContactPhoneNumber();
        if (android.text.TextUtils.isEmpty(contactPhoneNumber)) {
            return aggregateEventHistoryImpl;
        }
        if (isDefaultDialer() && (eventHistory2 = getEventStore().getEventHistory(2, contactPhoneNumber)) != null) {
            aggregateEventHistoryImpl.addEventHistory(eventHistory2);
        }
        if (isDefaultSmsApp() && (eventHistory = getEventStore().getEventHistory(3, contactPhoneNumber)) != null) {
            aggregateEventHistoryImpl.addEventHistory(eventHistory);
        }
        return aggregateEventHistoryImpl;
    }

    @android.annotation.NonNull
    public com.android.server.people.data.EventHistory getClassLevelEventHistory(java.lang.String str) {
        com.android.server.people.data.EventHistory eventHistory = getEventStore().getEventHistory(4, str);
        return eventHistory != null ? eventHistory : new com.android.server.people.data.AggregateEventHistoryImpl();
    }

    public boolean isDefaultDialer() {
        return this.mIsDefaultDialerPredicate.test(this.mPackageName);
    }

    public boolean isDefaultSmsApp() {
        return this.mIsDefaultSmsAppPredicate.test(this.mPackageName);
    }

    @android.annotation.NonNull
    com.android.server.people.data.ConversationStore getConversationStore() {
        return this.mConversationStore;
    }

    @android.annotation.NonNull
    com.android.server.people.data.EventStore getEventStore() {
        return this.mEventStore;
    }

    void deleteDataForConversation(java.lang.String str) {
        com.android.server.people.data.ConversationInfo deleteConversation = this.mConversationStore.deleteConversation(str);
        if (deleteConversation == null) {
            return;
        }
        this.mEventStore.deleteEventHistory(0, str);
        if (deleteConversation.getLocusId() != null) {
            this.mEventStore.deleteEventHistory(1, deleteConversation.getLocusId().getId());
        }
        java.lang.String contactPhoneNumber = deleteConversation.getContactPhoneNumber();
        if (!android.text.TextUtils.isEmpty(contactPhoneNumber)) {
            if (isDefaultDialer()) {
                this.mEventStore.deleteEventHistory(2, contactPhoneNumber);
            }
            if (isDefaultSmsApp()) {
                this.mEventStore.deleteEventHistory(3, contactPhoneNumber);
            }
        }
    }

    void pruneOrphanEvents() {
        this.mEventStore.pruneOrphanEventHistories(0, new java.util.function.Predicate() { // from class: com.android.server.people.data.PackageData$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$pruneOrphanEvents$0;
                lambda$pruneOrphanEvents$0 = com.android.server.people.data.PackageData.this.lambda$pruneOrphanEvents$0((java.lang.String) obj);
                return lambda$pruneOrphanEvents$0;
            }
        });
        this.mEventStore.pruneOrphanEventHistories(1, new java.util.function.Predicate() { // from class: com.android.server.people.data.PackageData$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$pruneOrphanEvents$1;
                lambda$pruneOrphanEvents$1 = com.android.server.people.data.PackageData.this.lambda$pruneOrphanEvents$1((java.lang.String) obj);
                return lambda$pruneOrphanEvents$1;
            }
        });
        if (isDefaultDialer()) {
            this.mEventStore.pruneOrphanEventHistories(2, new java.util.function.Predicate() { // from class: com.android.server.people.data.PackageData$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$pruneOrphanEvents$2;
                    lambda$pruneOrphanEvents$2 = com.android.server.people.data.PackageData.this.lambda$pruneOrphanEvents$2((java.lang.String) obj);
                    return lambda$pruneOrphanEvents$2;
                }
            });
        }
        if (isDefaultSmsApp()) {
            this.mEventStore.pruneOrphanEventHistories(3, new java.util.function.Predicate() { // from class: com.android.server.people.data.PackageData$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$pruneOrphanEvents$3;
                    lambda$pruneOrphanEvents$3 = com.android.server.people.data.PackageData.this.lambda$pruneOrphanEvents$3((java.lang.String) obj);
                    return lambda$pruneOrphanEvents$3;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$pruneOrphanEvents$0(java.lang.String str) {
        return this.mConversationStore.getConversation(str) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$pruneOrphanEvents$1(java.lang.String str) {
        return this.mConversationStore.getConversationByLocusId(new android.content.LocusId(str)) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$pruneOrphanEvents$2(java.lang.String str) {
        return this.mConversationStore.getConversationByPhoneNumber(str) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$pruneOrphanEvents$3(java.lang.String str) {
        return this.mConversationStore.getConversationByPhoneNumber(str) != null;
    }

    void onDestroy() {
        this.mEventStore.onDestroy();
        this.mConversationStore.onDestroy();
        android.os.FileUtils.deleteContentsAndDir(this.mPackageDataDir);
    }
}
