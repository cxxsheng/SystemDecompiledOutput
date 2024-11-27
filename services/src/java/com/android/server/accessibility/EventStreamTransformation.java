package com.android.server.accessibility;

/* loaded from: classes.dex */
public interface EventStreamTransformation {
    com.android.server.accessibility.EventStreamTransformation getNext();

    void setNext(com.android.server.accessibility.EventStreamTransformation eventStreamTransformation);

    default void onMotionEvent(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        com.android.server.accessibility.EventStreamTransformation next = getNext();
        if (next != null) {
            next.onMotionEvent(motionEvent, motionEvent2, i);
        }
    }

    default void onKeyEvent(android.view.KeyEvent keyEvent, int i) {
        com.android.server.accessibility.EventStreamTransformation next = getNext();
        if (next != null) {
            next.onKeyEvent(keyEvent, i);
        }
    }

    default void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        com.android.server.accessibility.EventStreamTransformation next = getNext();
        if (next != null) {
            next.onAccessibilityEvent(accessibilityEvent);
        }
    }

    default void clearEvents(int i) {
        com.android.server.accessibility.EventStreamTransformation next = getNext();
        if (next != null) {
            next.clearEvents(i);
        }
    }

    default void onDestroy() {
    }
}
