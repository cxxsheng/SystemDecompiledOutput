package android.app.usage;

/* loaded from: classes.dex */
public class EventList {
    private final java.util.ArrayList<android.app.usage.UsageEvents.Event> mEvents = new java.util.ArrayList<>();

    public int size() {
        return this.mEvents.size();
    }

    public void clear() {
        this.mEvents.clear();
    }

    public android.app.usage.UsageEvents.Event get(int i) {
        return this.mEvents.get(i);
    }

    public void insert(android.app.usage.UsageEvents.Event event) {
        int size = this.mEvents.size();
        if (size == 0 || event.mTimeStamp >= this.mEvents.get(size - 1).mTimeStamp) {
            this.mEvents.add(event);
        } else {
            this.mEvents.add(firstIndexOnOrAfter(event.mTimeStamp + 1), event);
        }
    }

    public android.app.usage.UsageEvents.Event remove(int i) {
        try {
            return this.mEvents.remove(i);
        } catch (java.lang.IndexOutOfBoundsException e) {
            return null;
        }
    }

    public int firstIndexOnOrAfter(long j) {
        int size = this.mEvents.size();
        int i = size - 1;
        int i2 = 0;
        while (i2 <= i) {
            int i3 = (i2 + i) >>> 1;
            if (this.mEvents.get(i3).mTimeStamp >= j) {
                i = i3 - 1;
                size = i3;
            } else {
                i2 = i3 + 1;
            }
        }
        return size;
    }

    public void merge(android.app.usage.EventList eventList) {
        int size = eventList.size();
        for (int i = 0; i < size; i++) {
            insert(eventList.get(i));
        }
    }
}
