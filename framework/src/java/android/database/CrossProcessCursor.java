package android.database;

/* loaded from: classes.dex */
public interface CrossProcessCursor extends android.database.Cursor {
    void fillWindow(int i, android.database.CursorWindow cursorWindow);

    android.database.CursorWindow getWindow();

    boolean onMove(int i, int i2);
}
