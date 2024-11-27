package android.database;

/* loaded from: classes.dex */
public abstract class AbstractWindowedCursor extends android.database.AbstractCursor {
    protected android.database.CursorWindow mWindow;

    @Override // android.database.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int i) {
        checkPosition();
        return this.mWindow.getBlob(this.mPos, i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public java.lang.String getString(int i) {
        checkPosition();
        return this.mWindow.getString(this.mPos, i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void copyStringToBuffer(int i, android.database.CharArrayBuffer charArrayBuffer) {
        checkPosition();
        this.mWindow.copyStringToBuffer(this.mPos, i, charArrayBuffer);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public short getShort(int i) {
        checkPosition();
        return this.mWindow.getShort(this.mPos, i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getInt(int i) {
        checkPosition();
        return this.mWindow.getInt(this.mPos, i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public long getLong(int i) {
        checkPosition();
        return this.mWindow.getLong(this.mPos, i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public float getFloat(int i) {
        checkPosition();
        return this.mWindow.getFloat(this.mPos, i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public double getDouble(int i) {
        checkPosition();
        return this.mWindow.getDouble(this.mPos, i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean isNull(int i) {
        checkPosition();
        return this.mWindow.getType(this.mPos, i) == 0;
    }

    @java.lang.Deprecated
    public boolean isBlob(int i) {
        return getType(i) == 4;
    }

    @java.lang.Deprecated
    public boolean isString(int i) {
        return getType(i) == 3;
    }

    @java.lang.Deprecated
    public boolean isLong(int i) {
        return getType(i) == 1;
    }

    @java.lang.Deprecated
    public boolean isFloat(int i) {
        return getType(i) == 2;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getType(int i) {
        checkPosition();
        return this.mWindow.getType(this.mPos, i);
    }

    @Override // android.database.AbstractCursor
    protected void checkPosition() {
        super.checkPosition();
        if (this.mWindow == null) {
            throw new android.database.StaleDataException("Attempting to access a closed CursorWindow.Most probable cause: cursor is deactivated prior to calling this method.");
        }
    }

    @Override // android.database.AbstractCursor, android.database.CrossProcessCursor
    public android.database.CursorWindow getWindow() {
        return this.mWindow;
    }

    public void setWindow(android.database.CursorWindow cursorWindow) {
        if (cursorWindow != this.mWindow) {
            closeWindow();
            this.mWindow = cursorWindow;
        }
    }

    public boolean hasWindow() {
        return this.mWindow != null;
    }

    protected void closeWindow() {
        if (this.mWindow != null) {
            this.mWindow.close();
            this.mWindow = null;
        }
    }

    protected void clearOrCreateWindow(java.lang.String str) {
        if (this.mWindow == null) {
            this.mWindow = new android.database.CursorWindow(str);
        } else {
            this.mWindow.clear();
        }
    }

    @Override // android.database.AbstractCursor
    protected void onDeactivateOrClose() {
        super.onDeactivateOrClose();
        closeWindow();
    }
}
