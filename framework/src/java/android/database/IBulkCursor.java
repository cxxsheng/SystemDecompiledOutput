package android.database;

/* loaded from: classes.dex */
public interface IBulkCursor extends android.os.IInterface {
    public static final int CLOSE_TRANSACTION = 7;
    public static final int DEACTIVATE_TRANSACTION = 2;
    public static final int GET_CURSOR_WINDOW_TRANSACTION = 1;
    public static final int GET_EXTRAS_TRANSACTION = 5;
    public static final int ON_MOVE_TRANSACTION = 4;
    public static final int REQUERY_TRANSACTION = 3;
    public static final int RESPOND_TRANSACTION = 6;
    public static final java.lang.String descriptor = "android.content.IBulkCursor";

    void close() throws android.os.RemoteException;

    void deactivate() throws android.os.RemoteException;

    android.os.Bundle getExtras() throws android.os.RemoteException;

    android.database.CursorWindow getWindow(int i) throws android.os.RemoteException;

    void onMove(int i) throws android.os.RemoteException;

    int requery(android.database.IContentObserver iContentObserver) throws android.os.RemoteException;

    android.os.Bundle respond(android.os.Bundle bundle) throws android.os.RemoteException;
}
