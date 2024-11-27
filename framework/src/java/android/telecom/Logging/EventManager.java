package android.telecom.Logging;

/* loaded from: classes3.dex */
public class EventManager {
    public static final int DEFAULT_EVENTS_TO_CACHE = 10;
    public static final java.lang.String TAG = "Logging.Events";
    private android.telecom.Logging.SessionManager.ISessionIdQueryHandler mSessionIdHandler;
    public static final java.time.format.DateTimeFormatter DATE_TIME_FORMATTER = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static final java.lang.Object mSync = new java.lang.Object();
    private final java.util.Map<android.telecom.Logging.EventManager.Loggable, android.telecom.Logging.EventManager.EventRecord> mCallEventRecordMap = new java.util.HashMap();
    private java.util.concurrent.LinkedBlockingQueue<android.telecom.Logging.EventManager.EventRecord> mEventRecords = new java.util.concurrent.LinkedBlockingQueue<>(10);
    private java.util.List<android.telecom.Logging.EventManager.EventListener> mEventListeners = new java.util.ArrayList();
    private final java.util.Map<java.lang.String, java.util.List<android.telecom.Logging.EventManager.TimedEventPair>> requestResponsePairs = new java.util.HashMap();

    public interface EventListener {
        void eventRecordAdded(android.telecom.Logging.EventManager.EventRecord eventRecord);
    }

    public interface Loggable {
        java.lang.String getDescription();

        java.lang.String getId();
    }

    public static class TimedEventPair {
        private static final long DEFAULT_TIMEOUT = 3000;
        java.lang.String mName;
        java.lang.String mRequest;
        java.lang.String mResponse;
        long mTimeoutMillis;

        public TimedEventPair(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.mTimeoutMillis = 3000L;
            this.mRequest = str;
            this.mResponse = str2;
            this.mName = str3;
        }

        public TimedEventPair(java.lang.String str, java.lang.String str2, java.lang.String str3, long j) {
            this.mTimeoutMillis = 3000L;
            this.mRequest = str;
            this.mResponse = str2;
            this.mName = str3;
            this.mTimeoutMillis = j;
        }
    }

    public void addRequestResponsePair(android.telecom.Logging.EventManager.TimedEventPair timedEventPair) {
        if (this.requestResponsePairs.containsKey(timedEventPair.mRequest)) {
            this.requestResponsePairs.get(timedEventPair.mRequest).add(timedEventPair);
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(timedEventPair);
        this.requestResponsePairs.put(timedEventPair.mRequest, arrayList);
    }

    public static class Event {
        public java.lang.Object data;
        public java.lang.String eventId;
        public java.lang.String sessionId;
        public long time;
        public final java.lang.String timestampString;

        public Event(java.lang.String str, java.lang.String str2, long j, java.lang.Object obj) {
            this.eventId = str;
            this.sessionId = str2;
            this.time = j;
            this.timestampString = java.time.ZonedDateTime.ofInstant(java.time.Instant.ofEpochMilli(j), java.time.ZoneId.systemDefault()).format(android.telecom.Logging.EventManager.DATE_TIME_FORMATTER);
            this.data = obj;
        }
    }

    public class EventRecord {
        private final java.util.List<android.telecom.Logging.EventManager.Event> mEvents = java.util.Collections.synchronizedList(new java.util.ArrayList());
        private final android.telecom.Logging.EventManager.Loggable mRecordEntry;

        public class EventTiming extends android.telecom.Logging.TimedEvent<java.lang.String> {
            public java.lang.String name;
            public long time;

            public EventTiming(java.lang.String str, long j) {
                this.name = str;
                this.time = j;
            }

            @Override // android.telecom.Logging.TimedEvent
            public java.lang.String getKey() {
                return this.name;
            }

            @Override // android.telecom.Logging.TimedEvent
            public long getTime() {
                return this.time;
            }
        }

        private class PendingResponse {
            java.lang.String name;
            java.lang.String requestEventId;
            long requestEventTimeMillis;
            long timeoutMillis;

            public PendingResponse(java.lang.String str, long j, long j2, java.lang.String str2) {
                this.requestEventId = str;
                this.requestEventTimeMillis = j;
                this.timeoutMillis = j2;
                this.name = str2;
            }
        }

        public EventRecord(android.telecom.Logging.EventManager.Loggable loggable) {
            this.mRecordEntry = loggable;
        }

        public android.telecom.Logging.EventManager.Loggable getRecordEntry() {
            return this.mRecordEntry;
        }

        public void addEvent(java.lang.String str, java.lang.String str2, java.lang.Object obj) {
            this.mEvents.add(new android.telecom.Logging.EventManager.Event(str, str2, java.lang.System.currentTimeMillis(), obj));
            android.telecom.Log.i("Event", "RecordEntry %s: %s, %s", this.mRecordEntry.getId(), str, obj);
        }

        public java.util.List<android.telecom.Logging.EventManager.Event> getEvents() {
            return new java.util.ArrayList(this.mEvents);
        }

        public java.util.List<android.telecom.Logging.EventManager.EventRecord.EventTiming> extractEventTimings() {
            java.util.Iterator<android.telecom.Logging.EventManager.Event> it;
            if (this.mEvents == null) {
                return java.util.Collections.emptyList();
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.HashMap hashMap = new java.util.HashMap();
            synchronized (this.mEvents) {
                java.util.Iterator<android.telecom.Logging.EventManager.Event> it2 = this.mEvents.iterator();
                while (it2.hasNext()) {
                    android.telecom.Logging.EventManager.Event next = it2.next();
                    if (!android.telecom.Logging.EventManager.this.requestResponsePairs.containsKey(next.eventId)) {
                        it = it2;
                    } else {
                        for (android.telecom.Logging.EventManager.TimedEventPair timedEventPair : (java.util.List) android.telecom.Logging.EventManager.this.requestResponsePairs.get(next.eventId)) {
                            hashMap.put(timedEventPair.mResponse, new android.telecom.Logging.EventManager.EventRecord.PendingResponse(next.eventId, next.time, timedEventPair.mTimeoutMillis, timedEventPair.mName));
                            it2 = it2;
                        }
                        it = it2;
                    }
                    android.telecom.Logging.EventManager.EventRecord.PendingResponse pendingResponse = (android.telecom.Logging.EventManager.EventRecord.PendingResponse) hashMap.remove(next.eventId);
                    if (pendingResponse != null) {
                        long j = next.time - pendingResponse.requestEventTimeMillis;
                        if (j < pendingResponse.timeoutMillis) {
                            arrayList.add(new android.telecom.Logging.EventManager.EventRecord.EventTiming(pendingResponse.name, j));
                        }
                    }
                    it2 = it;
                }
            }
            return arrayList;
        }

        public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            android.telecom.Logging.EventManager.EventRecord eventRecord;
            indentingPrintWriter.print(this.mRecordEntry.getDescription());
            indentingPrintWriter.increaseIndent();
            for (android.telecom.Logging.EventManager.Event event : getEvents()) {
                indentingPrintWriter.print(event.timestampString);
                indentingPrintWriter.print(" - ");
                indentingPrintWriter.print(event.eventId);
                if (event.data != null) {
                    indentingPrintWriter.print(" (");
                    java.lang.Object obj = event.data;
                    if ((obj instanceof android.telecom.Logging.EventManager.Loggable) && (eventRecord = (android.telecom.Logging.EventManager.EventRecord) android.telecom.Logging.EventManager.this.mCallEventRecordMap.get(obj)) != null) {
                        obj = "RecordEntry " + eventRecord.mRecordEntry.getId();
                    }
                    indentingPrintWriter.print(obj);
                    indentingPrintWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
                if (!android.text.TextUtils.isEmpty(event.sessionId)) {
                    indentingPrintWriter.print(":");
                    indentingPrintWriter.print(event.sessionId);
                }
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("Timings (average for this call, milliseconds):");
            indentingPrintWriter.increaseIndent();
            java.util.Map averageTimings = android.telecom.Logging.EventManager.EventRecord.EventTiming.averageTimings(extractEventTimings());
            java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList(averageTimings.keySet());
            java.util.Collections.sort(arrayList);
            for (java.lang.String str : arrayList) {
                indentingPrintWriter.printf("%s: %.2f\n", new java.lang.Object[]{str, averageTimings.get(str)});
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
        }
    }

    public EventManager(android.telecom.Logging.SessionManager.ISessionIdQueryHandler iSessionIdQueryHandler) {
        this.mSessionIdHandler = iSessionIdQueryHandler;
    }

    public void event(android.telecom.Logging.EventManager.Loggable loggable, java.lang.String str, java.lang.Object obj) {
        java.lang.String sessionId = this.mSessionIdHandler.getSessionId();
        if (loggable == null) {
            android.telecom.Log.i(TAG, "Non-call EVENT: %s, %s", str, obj);
            return;
        }
        synchronized (this.mEventRecords) {
            if (!this.mCallEventRecordMap.containsKey(loggable)) {
                addEventRecord(new android.telecom.Logging.EventManager.EventRecord(loggable));
            }
            this.mCallEventRecordMap.get(loggable).addEvent(str, sessionId, obj);
        }
    }

    public void event(android.telecom.Logging.EventManager.Loggable loggable, java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        if (objArr != null) {
            try {
                if (objArr.length != 0) {
                    str2 = java.lang.String.format(java.util.Locale.US, str2, objArr);
                }
            } catch (java.util.IllegalFormatException e) {
                android.telecom.Log.e(this, e, "IllegalFormatException: formatString='%s' numArgs=%d", str2, java.lang.Integer.valueOf(objArr.length));
                str2 = str2 + " (An error occurred while formatting the message.)";
            }
        }
        event(loggable, str, str2);
    }

    public void dumpEvents(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Historical Events:");
        indentingPrintWriter.increaseIndent();
        java.util.Iterator<android.telecom.Logging.EventManager.EventRecord> it = this.mEventRecords.iterator();
        while (it.hasNext()) {
            it.next().dump(indentingPrintWriter);
        }
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void dumpEventsTimeline(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Historical Events (sorted by time):");
        java.util.ArrayList<android.util.Pair> arrayList = new java.util.ArrayList();
        java.util.Iterator<android.telecom.Logging.EventManager.EventRecord> it = this.mEventRecords.iterator();
        while (it.hasNext()) {
            android.telecom.Logging.EventManager.EventRecord next = it.next();
            java.util.Iterator<android.telecom.Logging.EventManager.Event> it2 = next.getEvents().iterator();
            while (it2.hasNext()) {
                arrayList.add(new android.util.Pair(next.getRecordEntry(), it2.next()));
            }
        }
        arrayList.sort(java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: android.telecom.Logging.EventManager$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(java.lang.Object obj) {
                long j;
                j = ((android.telecom.Logging.EventManager.Event) ((android.util.Pair) obj).second).time;
                return j;
            }
        }));
        indentingPrintWriter.increaseIndent();
        for (android.util.Pair pair : arrayList) {
            indentingPrintWriter.print(((android.telecom.Logging.EventManager.Event) pair.second).timestampString);
            indentingPrintWriter.print(",");
            indentingPrintWriter.print(((android.telecom.Logging.EventManager.Loggable) pair.first).getId());
            indentingPrintWriter.print(",");
            indentingPrintWriter.print(((android.telecom.Logging.EventManager.Event) pair.second).eventId);
            indentingPrintWriter.print(",");
            indentingPrintWriter.println(((android.telecom.Logging.EventManager.Event) pair.second).data);
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void changeEventCacheSize(int i) {
        java.util.concurrent.LinkedBlockingQueue<android.telecom.Logging.EventManager.EventRecord> linkedBlockingQueue = this.mEventRecords;
        this.mEventRecords = new java.util.concurrent.LinkedBlockingQueue<>(i);
        this.mCallEventRecordMap.clear();
        linkedBlockingQueue.forEach(new java.util.function.Consumer() { // from class: android.telecom.Logging.EventManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.telecom.Logging.EventManager.this.lambda$changeEventCacheSize$1((android.telecom.Logging.EventManager.EventRecord) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeEventCacheSize$1(android.telecom.Logging.EventManager.EventRecord eventRecord) {
        android.telecom.Logging.EventManager.EventRecord poll;
        android.telecom.Logging.EventManager.Loggable recordEntry = eventRecord.getRecordEntry();
        if (this.mEventRecords.remainingCapacity() == 0 && (poll = this.mEventRecords.poll()) != null) {
            this.mCallEventRecordMap.remove(poll.getRecordEntry());
        }
        this.mEventRecords.add(eventRecord);
        this.mCallEventRecordMap.put(recordEntry, eventRecord);
    }

    public void registerEventListener(android.telecom.Logging.EventManager.EventListener eventListener) {
        if (eventListener != null) {
            synchronized (mSync) {
                this.mEventListeners.add(eventListener);
            }
        }
    }

    public java.util.concurrent.LinkedBlockingQueue<android.telecom.Logging.EventManager.EventRecord> getEventRecords() {
        return this.mEventRecords;
    }

    public java.util.Map<android.telecom.Logging.EventManager.Loggable, android.telecom.Logging.EventManager.EventRecord> getCallEventRecordMap() {
        return this.mCallEventRecordMap;
    }

    private void addEventRecord(android.telecom.Logging.EventManager.EventRecord eventRecord) {
        android.telecom.Logging.EventManager.EventRecord poll;
        android.telecom.Logging.EventManager.Loggable recordEntry = eventRecord.getRecordEntry();
        if (this.mEventRecords.remainingCapacity() == 0 && (poll = this.mEventRecords.poll()) != null) {
            this.mCallEventRecordMap.remove(poll.getRecordEntry());
        }
        this.mEventRecords.add(eventRecord);
        this.mCallEventRecordMap.put(recordEntry, eventRecord);
        synchronized (mSync) {
            java.util.Iterator<android.telecom.Logging.EventManager.EventListener> it = this.mEventListeners.iterator();
            while (it.hasNext()) {
                it.next().eventRecordAdded(eventRecord);
            }
        }
    }
}
