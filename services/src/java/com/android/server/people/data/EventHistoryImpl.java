package com.android.server.people.data;

/* loaded from: classes2.dex */
class EventHistoryImpl implements com.android.server.people.data.EventHistory {
    private static final java.lang.String EVENTS_DIR = "events";
    private static final java.lang.String INDEXES_DIR = "indexes";
    private static final long MAX_EVENTS_AGE = 14400000;
    private static final long PRUNE_OLD_EVENTS_DELAY = 900000;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.SparseArray<com.android.server.people.data.EventIndex> mEventIndexArray;
    private final com.android.server.people.data.EventHistoryImpl.EventIndexesProtoDiskReadWriter mEventIndexesProtoDiskReadWriter;
    private final com.android.server.people.data.EventHistoryImpl.EventsProtoDiskReadWriter mEventsProtoDiskReadWriter;
    private final com.android.server.people.data.EventHistoryImpl.Injector mInjector;
    private long mLastPruneTime;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.people.data.EventList mRecentEvents;
    private final java.io.File mRootDir;
    private final java.util.concurrent.ScheduledExecutorService mScheduledExecutorService;

    EventHistoryImpl(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
        this(new com.android.server.people.data.EventHistoryImpl.Injector(), file, scheduledExecutorService);
    }

    @com.android.internal.annotations.VisibleForTesting
    EventHistoryImpl(@android.annotation.NonNull com.android.server.people.data.EventHistoryImpl.Injector injector, @android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
        this.mEventIndexArray = new android.util.SparseArray<>();
        this.mRecentEvents = new com.android.server.people.data.EventList();
        this.mInjector = injector;
        this.mScheduledExecutorService = scheduledExecutorService;
        this.mLastPruneTime = injector.currentTimeMillis();
        this.mRootDir = file;
        this.mEventsProtoDiskReadWriter = new com.android.server.people.data.EventHistoryImpl.EventsProtoDiskReadWriter(new java.io.File(this.mRootDir, EVENTS_DIR), this.mScheduledExecutorService);
        this.mEventIndexesProtoDiskReadWriter = new com.android.server.people.data.EventHistoryImpl.EventIndexesProtoDiskReadWriter(new java.io.File(this.mRootDir, INDEXES_DIR), scheduledExecutorService);
    }

    @android.annotation.NonNull
    static java.util.Map<java.lang.String, com.android.server.people.data.EventHistoryImpl> eventHistoriesImplFromDisk(java.io.File file, java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
        return eventHistoriesImplFromDisk(new com.android.server.people.data.EventHistoryImpl.Injector(), file, scheduledExecutorService);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    static java.util.Map<java.lang.String, com.android.server.people.data.EventHistoryImpl> eventHistoriesImplFromDisk(com.android.server.people.data.EventHistoryImpl.Injector injector, java.io.File file, java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        java.io.File[] listFiles = file.listFiles(new com.android.server.cpu.CpuInfoReader$$ExternalSyntheticLambda2());
        if (listFiles == null) {
            return arrayMap;
        }
        for (java.io.File file2 : listFiles) {
            java.io.File[] listFiles2 = file2.listFiles(new java.io.FilenameFilter() { // from class: com.android.server.people.data.EventHistoryImpl$$ExternalSyntheticLambda1
                @Override // java.io.FilenameFilter
                public final boolean accept(java.io.File file3, java.lang.String str) {
                    boolean lambda$eventHistoriesImplFromDisk$0;
                    lambda$eventHistoriesImplFromDisk$0 = com.android.server.people.data.EventHistoryImpl.lambda$eventHistoriesImplFromDisk$0(file3, str);
                    return lambda$eventHistoriesImplFromDisk$0;
                }
            });
            if (listFiles2 != null && listFiles2.length == 2) {
                com.android.server.people.data.EventHistoryImpl eventHistoryImpl = new com.android.server.people.data.EventHistoryImpl(injector, file2, scheduledExecutorService);
                eventHistoryImpl.loadFromDisk();
                arrayMap.put(android.net.Uri.decode(file2.getName()), eventHistoryImpl);
            }
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$eventHistoriesImplFromDisk$0(java.io.File file, java.lang.String str) {
        return EVENTS_DIR.equals(str) || INDEXES_DIR.equals(str);
    }

    @com.android.internal.annotations.VisibleForTesting
    synchronized void loadFromDisk() {
        this.mScheduledExecutorService.execute(new java.lang.Runnable() { // from class: com.android.server.people.data.EventHistoryImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.people.data.EventHistoryImpl.this.lambda$loadFromDisk$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadFromDisk$1() {
        synchronized (this) {
            try {
                com.android.server.people.data.EventList loadRecentEventsFromDisk = this.mEventsProtoDiskReadWriter.loadRecentEventsFromDisk();
                if (loadRecentEventsFromDisk != null) {
                    loadRecentEventsFromDisk.removeOldEvents(this.mInjector.currentTimeMillis() - 14400000);
                    this.mRecentEvents.addAll(loadRecentEventsFromDisk.getAllEvents());
                }
                android.util.SparseArray<com.android.server.people.data.EventIndex> loadIndexesFromDisk = this.mEventIndexesProtoDiskReadWriter.loadIndexesFromDisk();
                if (loadIndexesFromDisk != null) {
                    for (int i = 0; i < loadIndexesFromDisk.size(); i++) {
                        this.mEventIndexArray.put(loadIndexesFromDisk.keyAt(i), loadIndexesFromDisk.valueAt(i));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    synchronized void saveToDisk() {
        this.mEventsProtoDiskReadWriter.saveEventsImmediately(this.mRecentEvents);
        this.mEventIndexesProtoDiskReadWriter.saveIndexesImmediately(this.mEventIndexArray);
    }

    @Override // com.android.server.people.data.EventHistory
    @android.annotation.NonNull
    public synchronized com.android.server.people.data.EventIndex getEventIndex(int i) {
        com.android.server.people.data.EventIndex eventIndex;
        try {
            eventIndex = this.mEventIndexArray.get(i);
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return eventIndex != null ? new com.android.server.people.data.EventIndex(eventIndex) : this.mInjector.createEventIndex();
    }

    @Override // com.android.server.people.data.EventHistory
    @android.annotation.NonNull
    public synchronized com.android.server.people.data.EventIndex getEventIndex(java.util.Set<java.lang.Integer> set) {
        com.android.server.people.data.EventIndex createEventIndex;
        createEventIndex = this.mInjector.createEventIndex();
        java.util.Iterator<java.lang.Integer> it = set.iterator();
        while (it.hasNext()) {
            com.android.server.people.data.EventIndex eventIndex = this.mEventIndexArray.get(it.next().intValue());
            if (eventIndex != null) {
                createEventIndex = com.android.server.people.data.EventIndex.combine(createEventIndex, eventIndex);
            }
        }
        return createEventIndex;
    }

    @Override // com.android.server.people.data.EventHistory
    @android.annotation.NonNull
    public synchronized java.util.List<com.android.server.people.data.Event> queryEvents(java.util.Set<java.lang.Integer> set, long j, long j2) {
        return this.mRecentEvents.queryEvents(set, j, j2);
    }

    synchronized void addEvent(com.android.server.people.data.Event event) {
        pruneOldEvents();
        addEventInMemory(event);
        this.mEventsProtoDiskReadWriter.scheduleEventsSave(this.mRecentEvents);
        this.mEventIndexesProtoDiskReadWriter.scheduleIndexesSave(this.mEventIndexArray);
    }

    synchronized void onDestroy() {
        this.mEventIndexArray.clear();
        this.mRecentEvents.clear();
        this.mEventsProtoDiskReadWriter.deleteRecentEventsFile();
        this.mEventIndexesProtoDiskReadWriter.deleteIndexesFile();
        android.os.FileUtils.deleteContentsAndDir(this.mRootDir);
    }

    synchronized void pruneOldEvents() {
        long currentTimeMillis = this.mInjector.currentTimeMillis();
        if (currentTimeMillis - this.mLastPruneTime > PRUNE_OLD_EVENTS_DELAY) {
            this.mRecentEvents.removeOldEvents(currentTimeMillis - 14400000);
            this.mLastPruneTime = currentTimeMillis;
        }
    }

    private synchronized void addEventInMemory(com.android.server.people.data.Event event) {
        try {
            com.android.server.people.data.EventIndex eventIndex = this.mEventIndexArray.get(event.getType());
            if (eventIndex == null) {
                eventIndex = this.mInjector.createEventIndex();
                this.mEventIndexArray.put(event.getType(), eventIndex);
            }
            eventIndex.addEvent(event.getTimestamp());
            this.mRecentEvents.add(event);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.people.data.EventIndex createEventIndex() {
            return new com.android.server.people.data.EventIndex();
        }

        long currentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class EventsProtoDiskReadWriter extends com.android.server.people.data.AbstractProtoDiskReadWriter<com.android.server.people.data.EventList> {
        private static final java.lang.String RECENT_FILE = "recent";
        private static final java.lang.String TAG = com.android.server.people.data.EventHistoryImpl.EventsProtoDiskReadWriter.class.getSimpleName();

        EventsProtoDiskReadWriter(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
            super(file, scheduledExecutorService);
            file.mkdirs();
        }

        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
        com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter<com.android.server.people.data.EventList> protoStreamWriter() {
            return new com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter() { // from class: com.android.server.people.data.EventHistoryImpl$EventsProtoDiskReadWriter$$ExternalSyntheticLambda0
                @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter
                public final void write(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.Object obj) {
                    com.android.server.people.data.EventHistoryImpl.EventsProtoDiskReadWriter.lambda$protoStreamWriter$0(protoOutputStream, (com.android.server.people.data.EventList) obj);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$protoStreamWriter$0(android.util.proto.ProtoOutputStream protoOutputStream, com.android.server.people.data.EventList eventList) {
            for (com.android.server.people.data.Event event : eventList.getAllEvents()) {
                long start = protoOutputStream.start(2246267895809L);
                event.writeToProto(protoOutputStream);
                protoOutputStream.end(start);
            }
        }

        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
        com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader<com.android.server.people.data.EventList> protoStreamReader() {
            return new com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader() { // from class: com.android.server.people.data.EventHistoryImpl$EventsProtoDiskReadWriter$$ExternalSyntheticLambda1
                @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader
                public final java.lang.Object read(android.util.proto.ProtoInputStream protoInputStream) {
                    com.android.server.people.data.EventList lambda$protoStreamReader$1;
                    lambda$protoStreamReader$1 = com.android.server.people.data.EventHistoryImpl.EventsProtoDiskReadWriter.lambda$protoStreamReader$1(protoInputStream);
                    return lambda$protoStreamReader$1;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ com.android.server.people.data.EventList lambda$protoStreamReader$1(android.util.proto.ProtoInputStream protoInputStream) {
            java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
            while (protoInputStream.nextField() != -1) {
                try {
                    if (protoInputStream.getFieldNumber() == 1) {
                        long start = protoInputStream.start(2246267895809L);
                        com.android.server.people.data.Event readFromProto = com.android.server.people.data.Event.readFromProto(protoInputStream);
                        protoInputStream.end(start);
                        newArrayList.add(readFromProto);
                    }
                } catch (java.io.IOException e) {
                    android.util.Slog.e(TAG, "Failed to read protobuf input stream.", e);
                }
            }
            com.android.server.people.data.EventList eventList = new com.android.server.people.data.EventList();
            eventList.addAll(newArrayList);
            return eventList;
        }

        void scheduleEventsSave(com.android.server.people.data.EventList eventList) {
            scheduleSave(RECENT_FILE, eventList);
        }

        void saveEventsImmediately(com.android.server.people.data.EventList eventList) {
            saveImmediately(RECENT_FILE, eventList);
        }

        @android.annotation.Nullable
        com.android.server.people.data.EventList loadRecentEventsFromDisk() {
            return read(RECENT_FILE);
        }

        void deleteRecentEventsFile() {
            delete(RECENT_FILE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class EventIndexesProtoDiskReadWriter extends com.android.server.people.data.AbstractProtoDiskReadWriter<android.util.SparseArray<com.android.server.people.data.EventIndex>> {
        private static final java.lang.String INDEXES_FILE = "index";
        private static final java.lang.String TAG = com.android.server.people.data.EventHistoryImpl.EventIndexesProtoDiskReadWriter.class.getSimpleName();

        EventIndexesProtoDiskReadWriter(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService) {
            super(file, scheduledExecutorService);
            file.mkdirs();
        }

        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
        com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter<android.util.SparseArray<com.android.server.people.data.EventIndex>> protoStreamWriter() {
            return new com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter() { // from class: com.android.server.people.data.EventHistoryImpl$EventIndexesProtoDiskReadWriter$$ExternalSyntheticLambda1
                @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter
                public final void write(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.Object obj) {
                    com.android.server.people.data.EventHistoryImpl.EventIndexesProtoDiskReadWriter.lambda$protoStreamWriter$0(protoOutputStream, (android.util.SparseArray) obj);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$protoStreamWriter$0(android.util.proto.ProtoOutputStream protoOutputStream, android.util.SparseArray sparseArray) {
            for (int i = 0; i < sparseArray.size(); i++) {
                int keyAt = sparseArray.keyAt(i);
                com.android.server.people.data.EventIndex eventIndex = (com.android.server.people.data.EventIndex) sparseArray.valueAt(i);
                long start = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1120986464257L, keyAt);
                long start2 = protoOutputStream.start(1146756268034L);
                eventIndex.writeToProto(protoOutputStream);
                protoOutputStream.end(start2);
                protoOutputStream.end(start);
            }
        }

        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
        com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader<android.util.SparseArray<com.android.server.people.data.EventIndex>> protoStreamReader() {
            return new com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader() { // from class: com.android.server.people.data.EventHistoryImpl$EventIndexesProtoDiskReadWriter$$ExternalSyntheticLambda0
                @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader
                public final java.lang.Object read(android.util.proto.ProtoInputStream protoInputStream) {
                    android.util.SparseArray lambda$protoStreamReader$1;
                    lambda$protoStreamReader$1 = com.android.server.people.data.EventHistoryImpl.EventIndexesProtoDiskReadWriter.lambda$protoStreamReader$1(protoInputStream);
                    return lambda$protoStreamReader$1;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ android.util.SparseArray lambda$protoStreamReader$1(android.util.proto.ProtoInputStream protoInputStream) {
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            while (protoInputStream.nextField() != -1) {
                try {
                    if (protoInputStream.getFieldNumber() == 1) {
                        long start = protoInputStream.start(2246267895809L);
                        com.android.server.people.data.EventIndex eventIndex = com.android.server.people.data.EventIndex.EMPTY;
                        int i = 0;
                        while (protoInputStream.nextField() != -1) {
                            switch (protoInputStream.getFieldNumber()) {
                                case 1:
                                    i = protoInputStream.readInt(1120986464257L);
                                    break;
                                case 2:
                                    long start2 = protoInputStream.start(1146756268034L);
                                    eventIndex = com.android.server.people.data.EventIndex.readFromProto(protoInputStream);
                                    protoInputStream.end(start2);
                                    break;
                                default:
                                    android.util.Slog.w(TAG, "Could not read undefined field: " + protoInputStream.getFieldNumber());
                                    break;
                            }
                        }
                        sparseArray.append(i, eventIndex);
                        protoInputStream.end(start);
                    }
                } catch (java.io.IOException e) {
                    android.util.Slog.e(TAG, "Failed to read protobuf input stream.", e);
                }
            }
            return sparseArray;
        }

        void scheduleIndexesSave(android.util.SparseArray<com.android.server.people.data.EventIndex> sparseArray) {
            scheduleSave("index", sparseArray);
        }

        void saveIndexesImmediately(android.util.SparseArray<com.android.server.people.data.EventIndex> sparseArray) {
            saveImmediately("index", sparseArray);
        }

        @android.annotation.Nullable
        android.util.SparseArray<com.android.server.people.data.EventIndex> loadIndexesFromDisk() {
            return read("index");
        }

        void deleteIndexesFile() {
            delete("index");
        }
    }
}
