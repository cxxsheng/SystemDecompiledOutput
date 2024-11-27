package com.android.server.people.data;

/* loaded from: classes2.dex */
class AggregateEventHistoryImpl implements com.android.server.people.data.EventHistory {
    private final java.util.List<com.android.server.people.data.EventHistory> mEventHistoryList = new java.util.ArrayList();

    AggregateEventHistoryImpl() {
    }

    @Override // com.android.server.people.data.EventHistory
    @android.annotation.NonNull
    public com.android.server.people.data.EventIndex getEventIndex(int i) {
        java.util.Iterator<com.android.server.people.data.EventHistory> it = this.mEventHistoryList.iterator();
        while (it.hasNext()) {
            com.android.server.people.data.EventIndex eventIndex = it.next().getEventIndex(i);
            if (!eventIndex.isEmpty()) {
                return eventIndex;
            }
        }
        return com.android.server.people.data.EventIndex.EMPTY;
    }

    @Override // com.android.server.people.data.EventHistory
    @android.annotation.NonNull
    public com.android.server.people.data.EventIndex getEventIndex(java.util.Set<java.lang.Integer> set) {
        java.util.Iterator<com.android.server.people.data.EventHistory> it = this.mEventHistoryList.iterator();
        com.android.server.people.data.EventIndex eventIndex = null;
        while (it.hasNext()) {
            com.android.server.people.data.EventIndex eventIndex2 = it.next().getEventIndex(set);
            if (eventIndex == null) {
                eventIndex = eventIndex2;
            } else if (!eventIndex2.isEmpty()) {
                eventIndex = com.android.server.people.data.EventIndex.combine(eventIndex, eventIndex2);
            }
        }
        return eventIndex != null ? eventIndex : com.android.server.people.data.EventIndex.EMPTY;
    }

    @Override // com.android.server.people.data.EventHistory
    @android.annotation.NonNull
    public java.util.List<com.android.server.people.data.Event> queryEvents(java.util.Set<java.lang.Integer> set, long j, long j2) {
        java.util.List<com.android.server.people.data.Event> arrayList = new java.util.ArrayList<>();
        for (com.android.server.people.data.EventHistory eventHistory : this.mEventHistoryList) {
            if (!eventHistory.getEventIndex(set).isEmpty()) {
                arrayList = combineEventLists(arrayList, eventHistory.queryEvents(set, j, j2));
            }
        }
        return arrayList;
    }

    void addEventHistory(com.android.server.people.data.EventHistory eventHistory) {
        this.mEventHistoryList.add(eventHistory);
    }

    private java.util.List<com.android.server.people.data.Event> combineEventLists(java.util.List<com.android.server.people.data.Event> list, java.util.List<com.android.server.people.data.Event> list2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i = 0;
        int i2 = 0;
        while (i < list.size() && i2 < list2.size()) {
            if (list.get(i).getTimestamp() < list2.get(i2).getTimestamp()) {
                arrayList.add(list.get(i));
                i++;
            } else {
                arrayList.add(list2.get(i2));
                i2++;
            }
        }
        if (i < list.size()) {
            arrayList.addAll(list.subList(i, list.size()));
        } else if (i2 < list2.size()) {
            arrayList.addAll(list2.subList(i2, list2.size()));
        }
        return arrayList;
    }
}
