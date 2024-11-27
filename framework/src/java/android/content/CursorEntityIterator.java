package android.content;

/* loaded from: classes.dex */
public abstract class CursorEntityIterator implements android.content.EntityIterator {
    private final android.database.Cursor mCursor;
    private boolean mIsClosed = false;

    public abstract android.content.Entity getEntityAndIncrementCursor(android.database.Cursor cursor) throws android.os.RemoteException;

    public CursorEntityIterator(android.database.Cursor cursor) {
        this.mCursor = cursor;
        this.mCursor.moveToFirst();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.mIsClosed) {
            throw new java.lang.IllegalStateException("calling hasNext() when the iterator is closed");
        }
        return !this.mCursor.isAfterLast();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public android.content.Entity next() {
        if (this.mIsClosed) {
            throw new java.lang.IllegalStateException("calling next() when the iterator is closed");
        }
        if (!hasNext()) {
            throw new java.lang.IllegalStateException("you may only call next() if hasNext() is true");
        }
        try {
            return getEntityAndIncrementCursor(this.mCursor);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("caught a remote exception, this process will die soon", e);
        }
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new java.lang.UnsupportedOperationException("remove not supported by EntityIterators");
    }

    @Override // android.content.EntityIterator
    public final void reset() {
        if (this.mIsClosed) {
            throw new java.lang.IllegalStateException("calling reset() when the iterator is closed");
        }
        this.mCursor.moveToFirst();
    }

    @Override // android.content.EntityIterator
    public final void close() {
        if (this.mIsClosed) {
            throw new java.lang.IllegalStateException("closing when already closed");
        }
        this.mIsClosed = true;
        this.mCursor.close();
    }
}
