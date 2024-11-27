package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowList<E> extends java.util.ArrayList<E> {
    WindowList() {
    }

    public void addFirst(E e) {
        add(0, e);
    }

    E peekLast() {
        if (size() > 0) {
            return get(size() - 1);
        }
        return null;
    }

    E peekFirst() {
        if (size() > 0) {
            return get(0);
        }
        return null;
    }
}
