package android.content;

/* loaded from: classes.dex */
public abstract class ContentProviderNative extends android.os.Binder implements android.content.IContentProvider {
    public abstract java.lang.String getProviderName();

    public ContentProviderNative() {
        attachInterface(this, android.content.IContentProvider.descriptor);
    }

    public static android.content.IContentProvider asInterface(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        android.content.IContentProvider iContentProvider = (android.content.IContentProvider) iBinder.queryLocalInterface(android.content.IContentProvider.descriptor);
        if (iContentProvider != null) {
            return iContentProvider;
        }
        return new android.content.ContentProviderProxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        java.lang.String[] strArr;
        android.database.BulkCursorDescriptor bulkCursorDescriptor;
        int i3 = 0;
        try {
            switch (i) {
                case 1:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    android.content.AttributionSource createFromParcel = android.content.AttributionSource.CREATOR.createFromParcel(parcel);
                    android.net.Uri createFromParcel2 = android.net.Uri.CREATOR.createFromParcel(parcel);
                    int readInt = parcel.readInt();
                    android.database.CursorToBulkCursorAdaptor cursorToBulkCursorAdaptor = null;
                    if (readInt <= 0) {
                        strArr = null;
                    } else {
                        java.lang.String[] strArr2 = new java.lang.String[readInt];
                        for (int i4 = 0; i4 < readInt; i4++) {
                            strArr2[i4] = parcel.readString();
                        }
                        strArr = strArr2;
                    }
                    android.os.Bundle readBundle = parcel.readBundle();
                    android.database.IContentObserver asInterface = android.database.IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                    android.database.Cursor query = query(createFromParcel, createFromParcel2, strArr, readBundle, android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                    if (query != null) {
                        try {
                            android.database.CursorToBulkCursorAdaptor cursorToBulkCursorAdaptor2 = new android.database.CursorToBulkCursorAdaptor(query, asInterface, getProviderName());
                            try {
                                bulkCursorDescriptor = cursorToBulkCursorAdaptor2.getBulkCursorDescriptor();
                            } catch (java.lang.Throwable th) {
                                th = th;
                                query = null;
                                cursorToBulkCursorAdaptor = cursorToBulkCursorAdaptor2;
                            }
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                        }
                        try {
                            parcel2.writeNoException();
                            parcel2.writeInt(1);
                            bulkCursorDescriptor.writeToParcel(parcel2, 1);
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                            query = null;
                            if (cursorToBulkCursorAdaptor != null) {
                                cursorToBulkCursorAdaptor.close();
                            }
                            if (query != null) {
                                query.close();
                            }
                            throw th;
                        }
                    } else {
                        parcel2.writeNoException();
                        parcel2.writeInt(0);
                    }
                    return true;
                case 2:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    java.lang.String type = getType(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel));
                    parcel2.writeNoException();
                    parcel2.writeString(type);
                    return true;
                case 3:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    android.net.Uri insert = insert(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), android.content.ContentValues.CREATOR.createFromParcel(parcel), parcel.readBundle());
                    parcel2.writeNoException();
                    android.net.Uri.writeToParcel(parcel2, insert);
                    return true;
                case 4:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    int delete = delete(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), parcel.readBundle());
                    parcel2.writeNoException();
                    parcel2.writeInt(delete);
                    return true;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 11:
                case 12:
                case 16:
                case 17:
                case 18:
                case 19:
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
                case 10:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    int update = update(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), android.content.ContentValues.CREATOR.createFromParcel(parcel), parcel.readBundle());
                    parcel2.writeNoException();
                    parcel2.writeInt(update);
                    return true;
                case 13:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    int bulkInsert = bulkInsert(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), (android.content.ContentValues[]) parcel.createTypedArray(android.content.ContentValues.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(bulkInsert);
                    return true;
                case 14:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    android.os.ParcelFileDescriptor openFile = openFile(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), parcel.readString(), android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (openFile != null) {
                        parcel2.writeInt(1);
                        openFile.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 15:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    android.content.res.AssetFileDescriptor openAssetFile = openAssetFile(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), parcel.readString(), android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (openAssetFile != null) {
                        parcel2.writeInt(1);
                        openAssetFile.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 20:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    android.content.AttributionSource createFromParcel3 = android.content.AttributionSource.CREATOR.createFromParcel(parcel);
                    java.lang.String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    java.util.ArrayList<android.content.ContentProviderOperation> arrayList = new java.util.ArrayList<>(readInt2);
                    for (int i5 = 0; i5 < readInt2; i5++) {
                        arrayList.add(i5, android.content.ContentProviderOperation.CREATOR.createFromParcel(parcel));
                    }
                    android.content.ContentProviderResult[] applyBatch = applyBatch(createFromParcel3, readString, arrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(applyBatch, 0);
                    return true;
                case 21:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    android.os.Bundle call = call(android.content.AttributionSource.CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readBundle());
                    parcel2.writeNoException();
                    parcel2.writeBundle(call);
                    return true;
                case 22:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    java.lang.String[] streamTypes = getStreamTypes(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeStringArray(streamTypes);
                    return true;
                case 23:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    android.content.res.AssetFileDescriptor openTypedAssetFile = openTypedAssetFile(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readBundle(), android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (openTypedAssetFile != null) {
                        parcel2.writeInt(1);
                        openTypedAssetFile.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 24:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    android.os.ICancellationSignal createCancellationSignal = createCancellationSignal();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(createCancellationSignal.asBinder());
                    return true;
                case 25:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    android.net.Uri canonicalize = canonicalize(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel));
                    parcel2.writeNoException();
                    android.net.Uri.writeToParcel(parcel2, canonicalize);
                    return true;
                case 26:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    android.net.Uri uncanonicalize = uncanonicalize(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel));
                    parcel2.writeNoException();
                    android.net.Uri.writeToParcel(parcel2, uncanonicalize);
                    return true;
                case 27:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    boolean refresh = refresh(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), parcel.readBundle(), android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (!refresh) {
                        i3 = -1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 28:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    int checkUriPermission = checkUriPermission(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(checkUriPermission);
                    return true;
                case 29:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    getTypeAsync(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), android.os.RemoteCallback.CREATOR.createFromParcel(parcel));
                    return true;
                case 30:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    canonicalizeAsync(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), android.os.RemoteCallback.CREATOR.createFromParcel(parcel));
                    return true;
                case 31:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    uncanonicalizeAsync(android.content.AttributionSource.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel), android.os.RemoteCallback.CREATOR.createFromParcel(parcel));
                    return true;
                case 32:
                    parcel.enforceInterface(android.content.IContentProvider.descriptor);
                    getTypeAnonymousAsync(android.net.Uri.CREATOR.createFromParcel(parcel), android.os.RemoteCallback.CREATOR.createFromParcel(parcel));
                    return true;
            }
        } catch (java.lang.Exception e) {
            android.database.DatabaseUtils.writeExceptionToParcel(parcel2, e);
            return true;
        }
    }

    @Override // android.os.IInterface
    public android.os.IBinder asBinder() {
        return this;
    }
}
