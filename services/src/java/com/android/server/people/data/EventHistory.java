package com.android.server.people.data;

/* loaded from: classes2.dex */
public interface EventHistory {
    @android.annotation.NonNull
    com.android.server.people.data.EventIndex getEventIndex(int i);

    @android.annotation.NonNull
    com.android.server.people.data.EventIndex getEventIndex(java.util.Set<java.lang.Integer> set);

    @android.annotation.NonNull
    java.util.List<com.android.server.people.data.Event> queryEvents(java.util.Set<java.lang.Integer> set, long j, long j2);
}
