package com.android.server.people.data;

/* loaded from: classes2.dex */
class EventList {
    private final java.util.List<com.android.server.people.data.Event> mEvents = new java.util.ArrayList();

    EventList() {
    }

    void add(@android.annotation.NonNull com.android.server.people.data.Event event) {
        int firstIndexOnOrAfter = firstIndexOnOrAfter(event.getTimestamp());
        if (firstIndexOnOrAfter < this.mEvents.size() && this.mEvents.get(firstIndexOnOrAfter).getTimestamp() == event.getTimestamp() && isDuplicate(event, firstIndexOnOrAfter)) {
            return;
        }
        this.mEvents.add(firstIndexOnOrAfter, event);
    }

    void addAll(@android.annotation.NonNull java.util.List<com.android.server.people.data.Event> list) {
        java.util.Iterator<com.android.server.people.data.Event> it = list.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }

    @android.annotation.NonNull
    java.util.List<com.android.server.people.data.Event> queryEvents(@android.annotation.NonNull java.util.Set<java.lang.Integer> set, long j, long j2) {
        int firstIndexOnOrAfter = firstIndexOnOrAfter(j);
        if (firstIndexOnOrAfter == this.mEvents.size()) {
            return new java.util.ArrayList();
        }
        int firstIndexOnOrAfter2 = firstIndexOnOrAfter(j2);
        if (firstIndexOnOrAfter2 < firstIndexOnOrAfter) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (firstIndexOnOrAfter < firstIndexOnOrAfter2) {
            com.android.server.people.data.Event event = this.mEvents.get(firstIndexOnOrAfter);
            if (set.contains(java.lang.Integer.valueOf(event.getType()))) {
                arrayList.add(event);
            }
            firstIndexOnOrAfter++;
        }
        return arrayList;
    }

    void clear() {
        this.mEvents.clear();
    }

    @android.annotation.NonNull
    java.util.List<com.android.server.people.data.Event> getAllEvents() {
        return com.android.internal.util.CollectionUtils.copyOf(this.mEvents);
    }

    void removeOldEvents(long j) {
        int firstIndexOnOrAfter = firstIndexOnOrAfter(j);
        if (firstIndexOnOrAfter == 0) {
            return;
        }
        int size = this.mEvents.size();
        if (firstIndexOnOrAfter == size) {
            this.mEvents.clear();
            return;
        }
        int i = 0;
        while (firstIndexOnOrAfter < size) {
            this.mEvents.set(i, this.mEvents.get(firstIndexOnOrAfter));
            i++;
            firstIndexOnOrAfter++;
        }
        if (size > i) {
            this.mEvents.subList(i, size).clear();
        }
    }

    private int firstIndexOnOrAfter(long j) {
        int size = this.mEvents.size();
        int size2 = this.mEvents.size() - 1;
        int i = 0;
        while (i <= size2) {
            int i2 = (i + size2) >>> 1;
            if (this.mEvents.get(i2).getTimestamp() >= j) {
                size2 = i2 - 1;
                size = i2;
            } else {
                i = i2 + 1;
            }
        }
        return size;
    }

    private boolean isDuplicate(com.android.server.people.data.Event event, int i) {
        int size = this.mEvents.size();
        while (i < size && this.mEvents.get(i).getTimestamp() <= event.getTimestamp()) {
            int i2 = i + 1;
            if (this.mEvents.get(i).getType() != event.getType()) {
                i = i2;
            } else {
                return true;
            }
        }
        return false;
    }
}
