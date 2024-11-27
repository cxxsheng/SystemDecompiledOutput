package android.view.accessibility;

/* loaded from: classes4.dex */
final class WeakSparseArray<E> {
    private final java.lang.ref.ReferenceQueue<E> mRefQueue = new java.lang.ref.ReferenceQueue<>();
    private final android.util.SparseArray<android.view.accessibility.WeakSparseArray.WeakReferenceWithId<E>> mSparseArray = new android.util.SparseArray<>();

    WeakSparseArray() {
    }

    public void append(int i, E e) {
        removeUnreachableValues();
        this.mSparseArray.append(i, new android.view.accessibility.WeakSparseArray.WeakReferenceWithId<>(e, this.mRefQueue, i));
    }

    public void remove(int i) {
        removeUnreachableValues();
        this.mSparseArray.remove(i);
    }

    public E get(int i) {
        removeUnreachableValues();
        android.view.accessibility.WeakSparseArray.WeakReferenceWithId<E> weakReferenceWithId = this.mSparseArray.get(i);
        if (weakReferenceWithId != null) {
            return (E) weakReferenceWithId.get();
        }
        return null;
    }

    private void removeUnreachableValues() {
        while (true) {
            java.lang.ref.Reference<? extends E> poll = this.mRefQueue.poll();
            if (poll != null) {
                this.mSparseArray.remove(((android.view.accessibility.WeakSparseArray.WeakReferenceWithId) poll).mId);
            } else {
                return;
            }
        }
    }

    private static class WeakReferenceWithId<E> extends java.lang.ref.WeakReference<E> {
        final int mId;

        WeakReferenceWithId(E e, java.lang.ref.ReferenceQueue<? super E> referenceQueue, int i) {
            super(e, referenceQueue);
            this.mId = i;
        }
    }
}
