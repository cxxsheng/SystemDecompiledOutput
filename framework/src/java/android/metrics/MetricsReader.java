package android.metrics;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class MetricsReader {
    private java.util.Queue<android.metrics.LogMaker> mPendingQueue = new java.util.LinkedList();
    private java.util.Queue<android.metrics.LogMaker> mSeenQueue = new java.util.LinkedList();
    private int[] LOGTAGS = {524292};
    private android.metrics.MetricsReader.LogReader mReader = new android.metrics.MetricsReader.LogReader();
    private int mCheckpointTag = -1;

    public void setLogReader(android.metrics.MetricsReader.LogReader logReader) {
        this.mReader = logReader;
    }

    public void read(long j) {
        java.lang.Object[] objArr;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            this.mReader.readEvents(this.LOGTAGS, j, arrayList);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        this.mPendingQueue.clear();
        this.mSeenQueue.clear();
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            android.metrics.MetricsReader.Event event = (android.metrics.MetricsReader.Event) it.next();
            long timeMillis = event.getTimeMillis();
            java.lang.Object data = event.getData();
            if (data instanceof java.lang.Object[]) {
                objArr = (java.lang.Object[]) data;
            } else {
                objArr = new java.lang.Object[]{data};
            }
            android.metrics.LogMaker processId = new android.metrics.LogMaker(objArr).setTimestamp(timeMillis).setUid(event.getUid()).setProcessId(event.getProcessId());
            if (processId.getCategory() == 920) {
                if (processId.getSubtype() == this.mCheckpointTag) {
                    this.mPendingQueue.clear();
                }
            } else {
                this.mPendingQueue.offer(processId);
            }
        }
    }

    public void checkpoint() {
        this.mCheckpointTag = (int) (java.lang.System.currentTimeMillis() % 2147483647L);
        this.mReader.writeCheckpoint(this.mCheckpointTag);
        this.mPendingQueue.clear();
        this.mSeenQueue.clear();
    }

    public void reset() {
        this.mSeenQueue.addAll(this.mPendingQueue);
        this.mPendingQueue.clear();
        this.mCheckpointTag = -1;
        java.util.Queue<android.metrics.LogMaker> queue = this.mPendingQueue;
        this.mPendingQueue = this.mSeenQueue;
        this.mSeenQueue = queue;
    }

    public boolean hasNext() {
        return !this.mPendingQueue.isEmpty();
    }

    public android.metrics.LogMaker next() {
        android.metrics.LogMaker poll = this.mPendingQueue.poll();
        if (poll != null) {
            this.mSeenQueue.offer(poll);
        }
        return poll;
    }

    public static class Event {
        java.lang.Object mData;
        int mPid;
        long mTimeMillis;
        int mUid;

        public Event(long j, int i, int i2, java.lang.Object obj) {
            this.mTimeMillis = j;
            this.mPid = i;
            this.mUid = i2;
            this.mData = obj;
        }

        Event(android.util.EventLog.Event event) {
            this.mTimeMillis = java.util.concurrent.TimeUnit.MILLISECONDS.convert(event.getTimeNanos(), java.util.concurrent.TimeUnit.NANOSECONDS);
            this.mPid = event.getProcessId();
            this.mUid = event.getUid();
            this.mData = event.getData();
        }

        public long getTimeMillis() {
            return this.mTimeMillis;
        }

        public int getProcessId() {
            return this.mPid;
        }

        public int getUid() {
            return this.mUid;
        }

        public java.lang.Object getData() {
            return this.mData;
        }

        public void setData(java.lang.Object obj) {
            this.mData = obj;
        }
    }

    public static class LogReader {
        public void readEvents(int[] iArr, long j, java.util.Collection<android.metrics.MetricsReader.Event> collection) throws java.io.IOException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.util.EventLog.readEventsOnWrapping(iArr, java.util.concurrent.TimeUnit.NANOSECONDS.convert(j, java.util.concurrent.TimeUnit.MILLISECONDS), arrayList);
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                collection.add(new android.metrics.MetricsReader.Event((android.util.EventLog.Event) it.next()));
            }
        }

        public void writeCheckpoint(int i) {
            new com.android.internal.logging.MetricsLogger().action(com.android.internal.logging.nano.MetricsProto.MetricsEvent.METRICS_CHECKPOINT, i);
        }
    }
}
