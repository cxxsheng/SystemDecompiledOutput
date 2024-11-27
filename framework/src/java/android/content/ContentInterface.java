package android.content;

/* loaded from: classes.dex */
public interface ContentInterface {
    android.content.ContentProviderResult[] applyBatch(java.lang.String str, java.util.ArrayList<android.content.ContentProviderOperation> arrayList) throws android.os.RemoteException, android.content.OperationApplicationException;

    int bulkInsert(android.net.Uri uri, android.content.ContentValues[] contentValuesArr) throws android.os.RemoteException;

    android.os.Bundle call(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle) throws android.os.RemoteException;

    android.net.Uri canonicalize(android.net.Uri uri) throws android.os.RemoteException;

    int checkUriPermission(android.net.Uri uri, int i, int i2) throws android.os.RemoteException;

    int delete(android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException;

    java.lang.String[] getStreamTypes(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException;

    java.lang.String getType(android.net.Uri uri) throws android.os.RemoteException;

    android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) throws android.os.RemoteException;

    android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException;

    android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException;

    android.content.res.AssetFileDescriptor openTypedAssetFile(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException;

    android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException;

    boolean refresh(android.net.Uri uri, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException;

    android.net.Uri uncanonicalize(android.net.Uri uri) throws android.os.RemoteException;

    int update(android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) throws android.os.RemoteException;
}
