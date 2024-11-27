package android.content;

/* compiled from: ContentProviderNative.java */
/* loaded from: classes.dex */
final class ContentProviderProxy implements android.content.IContentProvider {
    private android.os.IBinder mRemote;

    public ContentProviderProxy(android.os.IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public android.os.IBinder asBinder() {
        return this.mRemote;
    }

    @Override // android.content.IContentProvider
    public android.database.Cursor query(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
        int i;
        android.database.BulkCursorToCursorAdaptor bulkCursorToCursorAdaptor = new android.database.BulkCursorToCursorAdaptor();
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            try {
                obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
                attributionSource.writeToParcel(obtain, 0);
                uri.writeToParcel(obtain, 0);
                if (strArr == null) {
                    i = 0;
                } else {
                    i = strArr.length;
                }
                obtain.writeInt(i);
                for (int i2 = 0; i2 < i; i2++) {
                    obtain.writeString(strArr[i2]);
                }
                obtain.writeBundle(bundle);
                obtain.writeStrongBinder(bulkCursorToCursorAdaptor.getObserver().asBinder());
                obtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
                this.mRemote.transact(1, obtain, obtain2, 0);
                android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
                if (obtain2.readInt() != 0) {
                    android.database.BulkCursorDescriptor createFromParcel = android.database.BulkCursorDescriptor.CREATOR.createFromParcel(obtain2);
                    android.os.Binder.copyAllowBlocking(this.mRemote, createFromParcel.cursor != null ? createFromParcel.cursor.asBinder() : null);
                    bulkCursorToCursorAdaptor.initialize(createFromParcel);
                } else {
                    bulkCursorToCursorAdaptor.close();
                    bulkCursorToCursorAdaptor = null;
                }
                return bulkCursorToCursorAdaptor;
            } catch (android.os.RemoteException e) {
                bulkCursorToCursorAdaptor.close();
                throw e;
            } catch (java.lang.RuntimeException e2) {
                bulkCursorToCursorAdaptor.close();
                throw e2;
            }
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public java.lang.String getType(android.content.AttributionSource attributionSource, android.net.Uri uri) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            this.mRemote.transact(2, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readString();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void getTypeAsync(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            remoteCallback.writeToParcel(obtain, 0);
            this.mRemote.transact(29, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void getTypeAnonymousAsync(android.net.Uri uri, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            uri.writeToParcel(obtain, 0);
            remoteCallback.writeToParcel(obtain, 0);
            this.mRemote.transact(32, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public android.net.Uri insert(android.content.AttributionSource attributionSource, android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            contentValues.writeToParcel(obtain, 0);
            obtain.writeBundle(bundle);
            this.mRemote.transact(3, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return android.net.Uri.CREATOR.createFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int bulkInsert(android.content.AttributionSource attributionSource, android.net.Uri uri, android.content.ContentValues[] contentValuesArr) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeTypedArray(contentValuesArr, 0);
            this.mRemote.transact(13, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public android.content.ContentProviderResult[] applyBatch(android.content.AttributionSource attributionSource, java.lang.String str, java.util.ArrayList<android.content.ContentProviderOperation> arrayList) throws android.os.RemoteException, android.content.OperationApplicationException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            obtain.writeString(str);
            obtain.writeInt(arrayList.size());
            java.util.Iterator<android.content.ContentProviderOperation> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(obtain, 0);
            }
            this.mRemote.transact(20, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionWithOperationApplicationExceptionFromParcel(obtain2);
            return (android.content.ContentProviderResult[]) obtain2.createTypedArray(android.content.ContentProviderResult.CREATOR);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int delete(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeBundle(bundle);
            this.mRemote.transact(4, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int update(android.content.AttributionSource attributionSource, android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            contentValues.writeToParcel(obtain, 0);
            obtain.writeBundle(bundle);
            this.mRemote.transact(10, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public android.os.ParcelFileDescriptor openFile(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeString(str);
            android.os.ParcelFileDescriptor parcelFileDescriptor = null;
            obtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(14, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(obtain2);
            if (obtain2.readInt() != 0) {
                parcelFileDescriptor = android.os.ParcelFileDescriptor.CREATOR.createFromParcel(obtain2);
            }
            return parcelFileDescriptor;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public android.content.res.AssetFileDescriptor openAssetFile(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeString(str);
            android.content.res.AssetFileDescriptor assetFileDescriptor = null;
            obtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(15, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(obtain2);
            if (obtain2.readInt() != 0) {
                assetFileDescriptor = android.content.res.AssetFileDescriptor.CREATOR.createFromParcel(obtain2);
            }
            return assetFileDescriptor;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public android.os.Bundle call(android.content.AttributionSource attributionSource, java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            obtain.writeString(str);
            obtain.writeString(str2);
            obtain.writeString(str3);
            obtain.writeBundle(bundle);
            this.mRemote.transact(21, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readBundle();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public java.lang.String[] getStreamTypes(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeString(str);
            this.mRemote.transact(22, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.createStringArray();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public android.content.res.AssetFileDescriptor openTypedAssetFile(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeString(str);
            obtain.writeBundle(bundle);
            android.content.res.AssetFileDescriptor assetFileDescriptor = null;
            obtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(23, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(obtain2);
            if (obtain2.readInt() != 0) {
                assetFileDescriptor = android.content.res.AssetFileDescriptor.CREATOR.createFromParcel(obtain2);
            }
            return assetFileDescriptor;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public android.os.ICancellationSignal createCancellationSignal() throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            this.mRemote.transact(24, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return android.os.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public android.net.Uri canonicalize(android.content.AttributionSource attributionSource, android.net.Uri uri) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            this.mRemote.transact(25, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return android.net.Uri.CREATOR.createFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void canonicalizeAsync(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            remoteCallback.writeToParcel(obtain, 0);
            this.mRemote.transact(30, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public android.net.Uri uncanonicalize(android.content.AttributionSource attributionSource, android.net.Uri uri) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            this.mRemote.transact(26, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return android.net.Uri.CREATOR.createFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void uncanonicalizeAsync(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            remoteCallback.writeToParcel(obtain, 0);
            this.mRemote.transact(31, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public boolean refresh(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.Bundle bundle, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeBundle(bundle);
            obtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(27, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt() == 0;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int checkUriPermission(android.content.AttributionSource attributionSource, android.net.Uri uri, int i, int i2) throws android.os.RemoteException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(android.content.IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeInt(i);
            obtain.writeInt(i2);
            this.mRemote.transact(28, obtain, obtain2, 0);
            android.database.DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }
}
