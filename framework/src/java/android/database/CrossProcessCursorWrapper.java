package android.database;

/* loaded from: classes.dex */
public class CrossProcessCursorWrapper extends android.database.CursorWrapper implements android.database.CrossProcessCursor {
    public CrossProcessCursorWrapper(android.database.Cursor cursor) {
        super(cursor);
    }

    @Override // android.database.CrossProcessCursor
    public void fillWindow(int i, android.database.CursorWindow cursorWindow) {
        if (this.mCursor instanceof android.database.CrossProcessCursor) {
            ((android.database.CrossProcessCursor) this.mCursor).fillWindow(i, cursorWindow);
        } else {
            android.database.DatabaseUtils.cursorFillWindow(this.mCursor, i, cursorWindow);
        }
    }

    @Override // android.database.CrossProcessCursor
    public android.database.CursorWindow getWindow() {
        if (this.mCursor instanceof android.database.CrossProcessCursor) {
            return ((android.database.CrossProcessCursor) this.mCursor).getWindow();
        }
        return null;
    }

    @Override // android.database.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        if (this.mCursor instanceof android.database.CrossProcessCursor) {
            return ((android.database.CrossProcessCursor) this.mCursor).onMove(i, i2);
        }
        return true;
    }
}
