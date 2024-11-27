package android.content;

/* loaded from: classes.dex */
public interface IContentProvider extends android.os.IInterface {
    public static final int APPLY_BATCH_TRANSACTION = 20;
    public static final int BULK_INSERT_TRANSACTION = 13;
    public static final int CALL_TRANSACTION = 21;
    public static final int CANONICALIZE_ASYNC_TRANSACTION = 30;
    public static final int CANONICALIZE_TRANSACTION = 25;
    public static final int CHECK_URI_PERMISSION_TRANSACTION = 28;
    public static final int CREATE_CANCELATION_SIGNAL_TRANSACTION = 24;
    public static final int DELETE_TRANSACTION = 4;
    public static final int GET_STREAM_TYPES_TRANSACTION = 22;
    public static final int GET_TYPE_ANONYMOUS_ASYNC_TRANSACTION = 32;
    public static final int GET_TYPE_ASYNC_TRANSACTION = 29;
    public static final int GET_TYPE_TRANSACTION = 2;
    public static final int INSERT_TRANSACTION = 3;
    public static final int OPEN_ASSET_FILE_TRANSACTION = 15;
    public static final int OPEN_FILE_TRANSACTION = 14;
    public static final int OPEN_TYPED_ASSET_FILE_TRANSACTION = 23;
    public static final int QUERY_TRANSACTION = 1;
    public static final int REFRESH_TRANSACTION = 27;
    public static final int UNCANONICALIZE_ASYNC_TRANSACTION = 31;
    public static final int UNCANONICALIZE_TRANSACTION = 26;
    public static final int UPDATE_TRANSACTION = 10;
    public static final java.lang.String descriptor = "android.content.IContentProvider";

    android.content.ContentProviderResult[] applyBatch(android.content.AttributionSource attributionSource, java.lang.String str, java.util.ArrayList<android.content.ContentProviderOperation> arrayList) throws android.os.RemoteException, android.content.OperationApplicationException;

    int bulkInsert(android.content.AttributionSource attributionSource, android.net.Uri uri, android.content.ContentValues[] contentValuesArr) throws android.os.RemoteException;

    android.os.Bundle call(android.content.AttributionSource attributionSource, java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle) throws android.os.RemoteException;

    android.net.Uri canonicalize(android.content.AttributionSource attributionSource, android.net.Uri uri) throws android.os.RemoteException;

    void canonicalizeAsync(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    int checkUriPermission(android.content.AttributionSource attributionSource, android.net.Uri uri, int i, int i2) throws android.os.RemoteException;

    android.os.ICancellationSignal createCancellationSignal() throws android.os.RemoteException;

    int delete(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException;

    java.lang.String[] getStreamTypes(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str) throws android.os.RemoteException;

    java.lang.String getType(android.content.AttributionSource attributionSource, android.net.Uri uri) throws android.os.RemoteException;

    void getTypeAnonymousAsync(android.net.Uri uri, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void getTypeAsync(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    android.net.Uri insert(android.content.AttributionSource attributionSource, android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) throws android.os.RemoteException;

    android.content.res.AssetFileDescriptor openAssetFile(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException;

    android.os.ParcelFileDescriptor openFile(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException;

    android.content.res.AssetFileDescriptor openTypedAssetFile(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException;

    android.database.Cursor query(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException;

    boolean refresh(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.Bundle bundle, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException;

    android.net.Uri uncanonicalize(android.content.AttributionSource attributionSource, android.net.Uri uri) throws android.os.RemoteException;

    void uncanonicalizeAsync(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    int update(android.content.AttributionSource attributionSource, android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) throws android.os.RemoteException;

    @java.lang.Deprecated
    default java.lang.String getType(android.net.Uri uri) throws android.os.RemoteException {
        return getType(new android.content.AttributionSource(android.os.Binder.getCallingUid(), android.app.AppGlobals.getPackageManager().getPackagesForUid(android.os.Binder.getCallingUid())[0], null), uri);
    }

    @java.lang.Deprecated
    default void getTypeAsync(android.net.Uri uri, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        getTypeAsync(new android.content.AttributionSource(android.os.Binder.getCallingUid(), android.app.AppGlobals.getPackageManager().getPackagesForUid(android.os.Binder.getCallingUid())[0], null), uri, remoteCallback);
    }

    @java.lang.Deprecated
    default android.net.Uri insert(java.lang.String str, android.net.Uri uri, android.content.ContentValues contentValues) throws android.os.RemoteException {
        return insert(new android.content.AttributionSource(android.os.Binder.getCallingUid(), str, null), uri, contentValues, null);
    }

    @java.lang.Deprecated
    default int bulkInsert(java.lang.String str, android.net.Uri uri, android.content.ContentValues[] contentValuesArr) throws android.os.RemoteException {
        return bulkInsert(new android.content.AttributionSource(android.os.Binder.getCallingUid(), str, null), uri, contentValuesArr);
    }

    @java.lang.Deprecated
    default int delete(java.lang.String str, android.net.Uri uri, java.lang.String str2, java.lang.String[] strArr) throws android.os.RemoteException {
        return delete(new android.content.AttributionSource(android.os.Binder.getCallingUid(), str, null), uri, android.content.ContentResolver.createSqlQueryBundle(str2, strArr));
    }

    @java.lang.Deprecated
    default int update(java.lang.String str, android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str2, java.lang.String[] strArr) throws android.os.RemoteException {
        return update(new android.content.AttributionSource(android.os.Binder.getCallingUid(), str, null), uri, contentValues, android.content.ContentResolver.createSqlQueryBundle(str2, strArr));
    }

    @java.lang.Deprecated
    default android.os.Bundle call(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle) throws android.os.RemoteException {
        return call(new android.content.AttributionSource(android.os.Binder.getCallingUid(), str, null), "unknown", str2, str3, bundle);
    }

    @java.lang.Deprecated
    default java.lang.String[] getStreamTypes(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException {
        return getStreamTypes(new android.content.AttributionSource(android.os.Binder.getCallingUid(), android.app.AppGlobals.getPackageManager().getPackagesForUid(android.os.Binder.getCallingUid())[0], null), uri, str);
    }
}
