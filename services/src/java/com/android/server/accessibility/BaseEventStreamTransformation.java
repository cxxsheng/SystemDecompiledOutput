package com.android.server.accessibility;

/* loaded from: classes.dex */
public abstract class BaseEventStreamTransformation implements com.android.server.accessibility.EventStreamTransformation {
    private com.android.server.accessibility.EventStreamTransformation mNext;

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void setNext(com.android.server.accessibility.EventStreamTransformation eventStreamTransformation) {
        this.mNext = eventStreamTransformation;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public com.android.server.accessibility.EventStreamTransformation getNext() {
        return this.mNext;
    }
}
