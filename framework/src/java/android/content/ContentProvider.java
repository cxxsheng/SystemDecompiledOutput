package android.content;

/* loaded from: classes.dex */
public abstract class ContentProvider implements android.content.ContentInterface, android.content.ComponentCallbacks2 {
    private static final java.lang.String TAG = "ContentProvider";
    private java.lang.String[] mAuthorities;
    private java.lang.String mAuthority;
    private java.lang.ThreadLocal<android.content.AttributionSource> mCallingAttributionSource;
    private android.content.Context mContext;
    private boolean mExported;
    private int mMyUid;
    private boolean mNoPerms;
    private android.content.pm.PathPermission[] mPathPermissions;
    private java.lang.String mReadPermission;
    private boolean mSingleUser;
    private boolean mSystemUserOnly;
    private android.content.ContentProvider.Transport mTransport;
    private android.util.SparseBooleanArray mUsersRedirectedToOwnerForMedia;
    private java.lang.String mWritePermission;

    public interface PipeDataWriter<T> {
        void writeDataToPipe(android.os.ParcelFileDescriptor parcelFileDescriptor, android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, T t);
    }

    public abstract int delete(android.net.Uri uri, java.lang.String str, java.lang.String[] strArr);

    @Override // android.content.ContentInterface
    public abstract java.lang.String getType(android.net.Uri uri);

    public abstract android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues);

    public abstract boolean onCreate();

    public abstract android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2);

    public abstract int update(android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr);

    public static boolean isAuthorityRedirectedForCloneProfile(java.lang.String str) {
        return "media".equals(str);
    }

    public ContentProvider() {
        this.mContext = null;
        this.mUsersRedirectedToOwnerForMedia = new android.util.SparseBooleanArray();
        this.mTransport = new android.content.ContentProvider.Transport();
    }

    public ContentProvider(android.content.Context context, java.lang.String str, java.lang.String str2, android.content.pm.PathPermission[] pathPermissionArr) {
        this.mContext = null;
        this.mUsersRedirectedToOwnerForMedia = new android.util.SparseBooleanArray();
        this.mTransport = new android.content.ContentProvider.Transport();
        this.mContext = context;
        this.mReadPermission = str;
        this.mWritePermission = str2;
        this.mPathPermissions = pathPermissionArr;
    }

    public static android.content.ContentProvider coerceToLocalContentProvider(android.content.IContentProvider iContentProvider) {
        if (iContentProvider instanceof android.content.ContentProvider.Transport) {
            return ((android.content.ContentProvider.Transport) iContentProvider).getContentProvider();
        }
        return null;
    }

    class Transport extends android.content.ContentProviderNative {
        volatile android.content.ContentInterface mInterface;
        volatile android.app.AppOpsManager mAppOpsManager = null;
        volatile int mReadOp = -1;
        volatile int mWriteOp = -1;

        Transport() {
            this.mInterface = android.content.ContentProvider.this;
        }

        android.content.ContentProvider getContentProvider() {
            return android.content.ContentProvider.this;
        }

        @Override // android.content.ContentProviderNative
        public java.lang.String getProviderName() {
            return getContentProvider().getClass().getName();
        }

        @Override // android.content.IContentProvider
        public android.database.Cursor query(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle, android.os.ICancellationSignal iCancellationSignal) {
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            if (enforceReadPermission(attributionSource, maybeGetUriWithoutUserId) == 0) {
                android.content.ContentProvider.traceBegin(64L, "query: ", maybeGetUriWithoutUserId.getAuthority());
                android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
                try {
                    try {
                        return this.mInterface.query(maybeGetUriWithoutUserId, strArr, bundle, android.os.CancellationSignal.fromTransport(iCancellationSignal));
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowAsRuntimeException();
                    }
                } finally {
                    android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                    android.os.Trace.traceEnd(64L);
                }
            }
            if (strArr != null) {
                return new android.database.MatrixCursor(strArr, 0);
            }
            android.content.AttributionSource callingAttributionSource2 = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    android.database.Cursor query = this.mInterface.query(maybeGetUriWithoutUserId, strArr, bundle, android.os.CancellationSignal.fromTransport(iCancellationSignal));
                    if (query == null) {
                        return null;
                    }
                    return new android.database.MatrixCursor(query.getColumnNames(), 0);
                } finally {
                    android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource2);
                }
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowAsRuntimeException();
            }
        }

        @Override // android.content.IContentProvider
        public java.lang.String getType(android.content.AttributionSource attributionSource, android.net.Uri uri) {
            android.content.ContentProvider.CallingIdentity clearCallingIdentity;
            java.lang.String type;
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            android.content.ContentProvider.traceBegin(64L, "getType: ", maybeGetUriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    if (checkGetTypePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                        int callingUid = android.os.Binder.getCallingUid();
                        clearCallingIdentity = getContentProvider().clearCallingIdentity();
                        try {
                            java.lang.String typeAnonymous = android.content.ContentProvider.this.getTypeAnonymous(maybeGetUriWithoutUserId);
                            if (typeAnonymous != null) {
                                logGetTypeData(callingUid, maybeGetUriWithoutUserId, typeAnonymous, false);
                            }
                            return typeAnonymous;
                        } finally {
                        }
                    }
                    if (android.content.ContentProvider.this.checkPermission(android.Manifest.permission.GET_ANY_PROVIDER_TYPE, attributionSource) == 0) {
                        clearCallingIdentity = getContentProvider().clearCallingIdentity();
                        try {
                            type = this.mInterface.getType(maybeGetUriWithoutUserId);
                            getContentProvider().restoreCallingIdentity(clearCallingIdentity);
                        } finally {
                        }
                    } else {
                        type = this.mInterface.getType(maybeGetUriWithoutUserId);
                    }
                    if (type != null) {
                        logGetTypeData(android.os.Binder.getCallingUid(), maybeGetUriWithoutUserId, type, true);
                    }
                    return type;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        private void logGetTypeData(int i, android.net.Uri uri, java.lang.String str, boolean z) {
            if (!z) {
                com.android.internal.util.FrameworkStatsLog.write(564, 4, i, uri.getAuthority(), str);
                return;
            }
            try {
                android.content.pm.ProviderInfo resolveContentProvider = android.content.ContentProvider.this.mContext.getPackageManager().resolveContentProvider(uri.getAuthority(), android.content.pm.PackageManager.ComponentInfoFlags.of(128L));
                int userId = android.os.UserHandle.getUserId(i);
                android.net.Uri maybeAddUserId = (!android.content.ContentProvider.this.mSingleUser || android.os.UserHandle.isSameUser(android.content.ContentProvider.this.mMyUid, i)) ? uri : android.content.ContentProvider.maybeAddUserId(uri, userId);
                if (resolveContentProvider.forceUriPermissions && this.mInterface.checkUriPermission(uri, i, 1) != 0 && android.content.ContentProvider.this.getContext().checkUriPermission(maybeAddUserId, android.os.Binder.getCallingPid(), i, 1) != 0 && !android.content.ContentProvider.deniedAccessSystemUserOnlyProvider(userId, android.content.ContentProvider.this.mSystemUserOnly)) {
                    com.android.internal.util.FrameworkStatsLog.write(564, 5, i, uri.getAuthority(), str);
                }
            } catch (java.lang.Exception e) {
            }
        }

        @Override // android.content.IContentProvider
        public void getTypeAsync(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.RemoteCallback remoteCallback) {
            android.os.Bundle bundle = new android.os.Bundle();
            try {
                bundle.putString("result", getType(attributionSource, uri));
            } catch (java.lang.Exception e) {
                bundle.putParcelable("error", new android.os.ParcelableException(e));
            }
            remoteCallback.sendResult(bundle);
        }

        @Override // android.content.IContentProvider
        public void getTypeAnonymousAsync(android.net.Uri uri, android.os.RemoteCallback remoteCallback) {
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            android.content.ContentProvider.traceBegin(64L, "getTypeAnonymous: ", maybeGetUriWithoutUserId.getAuthority());
            android.os.Bundle bundle = new android.os.Bundle();
            try {
                try {
                    bundle.putString("result", android.content.ContentProvider.this.getTypeAnonymous(maybeGetUriWithoutUserId));
                } catch (java.lang.Exception e) {
                    bundle.putParcelable("error", new android.os.ParcelableException(e));
                }
            } finally {
                remoteCallback.sendResult(bundle);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public android.net.Uri insert(android.content.AttributionSource attributionSource, android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) {
            android.net.Uri validateIncomingUri = android.content.ContentProvider.this.validateIncomingUri(uri);
            int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(validateIncomingUri);
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(validateIncomingUri);
            if (enforceWritePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
                try {
                    return android.content.ContentProvider.this.rejectInsert(maybeGetUriWithoutUserId, contentValues);
                } finally {
                    android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                }
            }
            android.content.ContentProvider.traceBegin(64L, "insert: ", maybeGetUriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource2 = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return android.content.ContentProvider.maybeAddUserId(this.mInterface.insert(maybeGetUriWithoutUserId, contentValues, bundle), userIdFromUri);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource2);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public int bulkInsert(android.content.AttributionSource attributionSource, android.net.Uri uri, android.content.ContentValues[] contentValuesArr) {
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            if (enforceWritePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                return 0;
            }
            android.content.ContentProvider.traceBegin(64L, "bulkInsert: ", maybeGetUriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.bulkInsert(maybeGetUriWithoutUserId, contentValuesArr);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public android.content.ContentProviderResult[] applyBatch(android.content.AttributionSource attributionSource, java.lang.String str, java.util.ArrayList<android.content.ContentProviderOperation> arrayList) throws android.content.OperationApplicationException {
            android.content.ContentProvider.this.validateIncomingAuthority(str);
            int size = arrayList.size();
            int[] iArr = new int[size];
            for (int i = 0; i < size; i++) {
                android.content.ContentProviderOperation contentProviderOperation = arrayList.get(i);
                android.net.Uri uri = contentProviderOperation.getUri();
                iArr[i] = android.content.ContentProvider.getUserIdFromUri(uri);
                android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
                if (!java.util.Objects.equals(contentProviderOperation.getUri(), maybeGetUriWithoutUserId)) {
                    android.content.ContentProviderOperation contentProviderOperation2 = new android.content.ContentProviderOperation(contentProviderOperation, maybeGetUriWithoutUserId);
                    arrayList.set(i, contentProviderOperation2);
                    contentProviderOperation = contentProviderOperation2;
                }
                if (contentProviderOperation.isReadOperation() && enforceReadPermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                    throw new android.content.OperationApplicationException("App op not allowed", 0);
                }
                if (contentProviderOperation.isWriteOperation() && enforceWritePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                    throw new android.content.OperationApplicationException("App op not allowed", 0);
                }
            }
            android.content.ContentProvider.traceBegin(64L, "applyBatch: ", str);
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    android.content.ContentProviderResult[] applyBatch = this.mInterface.applyBatch(str, arrayList);
                    if (applyBatch != null) {
                        for (int i2 = 0; i2 < applyBatch.length; i2++) {
                            if (iArr[i2] != -2) {
                                applyBatch[i2] = new android.content.ContentProviderResult(applyBatch[i2], iArr[i2]);
                            }
                        }
                    }
                    return applyBatch;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public int delete(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.Bundle bundle) {
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            if (enforceWritePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                return 0;
            }
            android.content.ContentProvider.traceBegin(64L, "delete: ", maybeGetUriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.delete(maybeGetUriWithoutUserId, bundle);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public int update(android.content.AttributionSource attributionSource, android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) {
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            if (enforceWritePermission(attributionSource, maybeGetUriWithoutUserId) != 0) {
                return 0;
            }
            android.content.ContentProvider.traceBegin(64L, "update: ", maybeGetUriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.update(maybeGetUriWithoutUserId, contentValues, bundle);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public android.os.ParcelFileDescriptor openFile(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str, android.os.ICancellationSignal iCancellationSignal) throws java.io.FileNotFoundException {
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            enforceFilePermission(attributionSource, maybeGetUriWithoutUserId, str);
            android.content.ContentProvider.traceBegin(64L, "openFile: ", maybeGetUriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.openFile(maybeGetUriWithoutUserId, str, android.os.CancellationSignal.fromTransport(iCancellationSignal));
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public android.content.res.AssetFileDescriptor openAssetFile(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str, android.os.ICancellationSignal iCancellationSignal) throws java.io.FileNotFoundException {
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            enforceFilePermission(attributionSource, maybeGetUriWithoutUserId, str);
            android.content.ContentProvider.traceBegin(64L, "openAssetFile: ", maybeGetUriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.openAssetFile(maybeGetUriWithoutUserId, str, android.os.CancellationSignal.fromTransport(iCancellationSignal));
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public android.os.Bundle call(android.content.AttributionSource attributionSource, java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle) {
            android.content.ContentProvider.this.validateIncomingAuthority(str);
            android.os.Bundle.setDefusable(bundle, true);
            android.content.ContentProvider.traceBegin(64L, "call: ", str);
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.call(str, str2, str3, bundle);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public java.lang.String[] getStreamTypes(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str) {
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            android.content.ContentProvider.traceBegin(64L, "getStreamTypes: ", maybeGetUriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.getStreamTypes(maybeGetUriWithoutUserId, str);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public android.content.res.AssetFileDescriptor openTypedAssetFile(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.ICancellationSignal iCancellationSignal) throws java.io.FileNotFoundException {
            android.os.Bundle.setDefusable(bundle, true);
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            enforceFilePermission(attributionSource, maybeGetUriWithoutUserId, "r");
            android.content.ContentProvider.traceBegin(64L, "openTypedAssetFile: ", maybeGetUriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.openTypedAssetFile(maybeGetUriWithoutUserId, str, bundle, android.os.CancellationSignal.fromTransport(iCancellationSignal));
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public android.os.ICancellationSignal createCancellationSignal() {
            return android.os.CancellationSignal.createTransport();
        }

        @Override // android.content.IContentProvider
        public android.net.Uri canonicalize(android.content.AttributionSource attributionSource, android.net.Uri uri) {
            android.net.Uri validateIncomingUri = android.content.ContentProvider.this.validateIncomingUri(uri);
            int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(validateIncomingUri);
            android.net.Uri uriWithoutUserId = android.content.ContentProvider.getUriWithoutUserId(validateIncomingUri);
            if (enforceReadPermission(attributionSource, uriWithoutUserId) != 0) {
                return null;
            }
            android.content.ContentProvider.traceBegin(64L, "canonicalize: ", uriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return android.content.ContentProvider.maybeAddUserId(this.mInterface.canonicalize(uriWithoutUserId), userIdFromUri);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public void canonicalizeAsync(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.RemoteCallback remoteCallback) {
            android.os.Bundle bundle = new android.os.Bundle();
            try {
                bundle.putParcelable("result", canonicalize(attributionSource, uri));
            } catch (java.lang.Exception e) {
                bundle.putParcelable("error", new android.os.ParcelableException(e));
            }
            remoteCallback.sendResult(bundle);
        }

        @Override // android.content.IContentProvider
        public android.net.Uri uncanonicalize(android.content.AttributionSource attributionSource, android.net.Uri uri) {
            android.net.Uri validateIncomingUri = android.content.ContentProvider.this.validateIncomingUri(uri);
            int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(validateIncomingUri);
            android.net.Uri uriWithoutUserId = android.content.ContentProvider.getUriWithoutUserId(validateIncomingUri);
            if (enforceReadPermission(attributionSource, uriWithoutUserId) != 0) {
                return null;
            }
            android.content.ContentProvider.traceBegin(64L, "uncanonicalize: ", uriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return android.content.ContentProvider.maybeAddUserId(this.mInterface.uncanonicalize(uriWithoutUserId), userIdFromUri);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public void uncanonicalizeAsync(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.RemoteCallback remoteCallback) {
            android.os.Bundle bundle = new android.os.Bundle();
            try {
                bundle.putParcelable("result", uncanonicalize(attributionSource, uri));
            } catch (java.lang.Exception e) {
                bundle.putParcelable("error", new android.os.ParcelableException(e));
            }
            remoteCallback.sendResult(bundle);
        }

        @Override // android.content.IContentProvider
        public boolean refresh(android.content.AttributionSource attributionSource, android.net.Uri uri, android.os.Bundle bundle, android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
            android.net.Uri uriWithoutUserId = android.content.ContentProvider.getUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            if (enforceReadPermission(attributionSource, uriWithoutUserId) != 0) {
                return false;
            }
            android.content.ContentProvider.traceBegin(64L, "refresh: ", uriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                return this.mInterface.refresh(uriWithoutUserId, bundle, android.os.CancellationSignal.fromTransport(iCancellationSignal));
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        @Override // android.content.IContentProvider
        public int checkUriPermission(android.content.AttributionSource attributionSource, android.net.Uri uri, int i, int i2) {
            android.net.Uri maybeGetUriWithoutUserId = android.content.ContentProvider.this.maybeGetUriWithoutUserId(android.content.ContentProvider.this.validateIncomingUri(uri));
            android.content.ContentProvider.traceBegin(64L, "checkUriPermission: ", maybeGetUriWithoutUserId.getAuthority());
            android.content.AttributionSource callingAttributionSource = android.content.ContentProvider.this.setCallingAttributionSource(attributionSource);
            try {
                try {
                    return this.mInterface.checkUriPermission(maybeGetUriWithoutUserId, i, i2);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } finally {
                android.content.ContentProvider.this.setCallingAttributionSource(callingAttributionSource);
                android.os.Trace.traceEnd(64L);
            }
        }

        private void enforceFilePermission(android.content.AttributionSource attributionSource, android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException, java.lang.SecurityException {
            if (str != null && str.indexOf(119) != -1) {
                if (enforceWritePermission(attributionSource, uri) != 0) {
                    throw new java.io.FileNotFoundException("App op not allowed");
                }
            } else if (enforceReadPermission(attributionSource, uri) != 0) {
                throw new java.io.FileNotFoundException("App op not allowed");
            }
        }

        private int enforceReadPermission(android.content.AttributionSource attributionSource, android.net.Uri uri) throws java.lang.SecurityException {
            int enforceReadPermissionInner = android.content.ContentProvider.this.enforceReadPermissionInner(uri, attributionSource);
            if (enforceReadPermissionInner != 0) {
                return enforceReadPermissionInner;
            }
            if (android.content.ContentProvider.this.mTransport.mReadOp != -1 && android.content.ContentProvider.this.mTransport.mReadOp != android.app.AppOpsManager.permissionToOpCode(android.content.ContentProvider.this.mReadPermission)) {
                return android.content.PermissionChecker.checkOpForDataDelivery(android.content.ContentProvider.this.getContext(), android.app.AppOpsManager.opToPublicName(android.content.ContentProvider.this.mTransport.mReadOp), attributionSource, null);
            }
            return 0;
        }

        private int enforceWritePermission(android.content.AttributionSource attributionSource, android.net.Uri uri) throws java.lang.SecurityException {
            int enforceWritePermissionInner = android.content.ContentProvider.this.enforceWritePermissionInner(uri, attributionSource);
            if (enforceWritePermissionInner != 0) {
                return enforceWritePermissionInner;
            }
            if (android.content.ContentProvider.this.mTransport.mWriteOp != -1 && android.content.ContentProvider.this.mTransport.mWriteOp != android.app.AppOpsManager.permissionToOpCode(android.content.ContentProvider.this.mWritePermission)) {
                return android.content.PermissionChecker.checkOpForDataDelivery(android.content.ContentProvider.this.getContext(), android.app.AppOpsManager.opToPublicName(android.content.ContentProvider.this.mTransport.mWriteOp), attributionSource, null);
            }
            return 0;
        }

        private int checkGetTypePermission(android.content.AttributionSource attributionSource, android.net.Uri uri) {
            if (android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) == 1000 || android.content.ContentProvider.this.checkPermission(android.Manifest.permission.GET_ANY_PROVIDER_TYPE, attributionSource) == 0) {
                return 0;
            }
            try {
                return enforceReadPermission(attributionSource, uri);
            } catch (java.lang.SecurityException e) {
                return 2;
            }
        }
    }

    boolean checkUser(int i, int i2, android.content.Context context) {
        int userId = android.os.UserHandle.getUserId(i2);
        if (deniedAccessSystemUserOnlyProvider(userId, this.mSystemUserOnly)) {
            return false;
        }
        if (userId == context.getUserId() || this.mSingleUser || context.checkPermission(android.Manifest.permission.INTERACT_ACROSS_USERS, i, i2) == 0 || context.checkPermission(android.Manifest.permission.INTERACT_ACROSS_USERS_FULL, i, i2) == 0) {
            return true;
        }
        return isContentRedirectionAllowedForUser(userId);
    }

    private boolean isContentRedirectionAllowedForUser(int i) {
        android.os.UserHandle profileParent;
        if (!"media".equals(this.mAuthority)) {
            return false;
        }
        int indexOfKey = this.mUsersRedirectedToOwnerForMedia.indexOfKey(i);
        if (indexOfKey >= 0) {
            return this.mUsersRedirectedToOwnerForMedia.valueAt(indexOfKey);
        }
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        if (userManager != null && userManager.getUserProperties(android.os.UserHandle.of(i)).isMediaSharedWithParent() && (profileParent = userManager.getProfileParent(android.os.UserHandle.of(i))) != null && profileParent.equals(android.os.Process.myUserHandle())) {
            this.mUsersRedirectedToOwnerForMedia.put(i, true);
            return true;
        }
        this.mUsersRedirectedToOwnerForMedia.put(i, false);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int checkPermission(java.lang.String str, android.content.AttributionSource attributionSource) {
        if (android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
            return 0;
        }
        return android.content.PermissionChecker.checkPermissionForDataDeliveryFromDataSource(getContext(), str, -1, new android.content.AttributionSource(getContext().getAttributionSource(), attributionSource), null);
    }

    protected int enforceReadPermissionInner(android.net.Uri uri, android.content.AttributionSource attributionSource) throws java.lang.SecurityException {
        int i;
        java.lang.String str;
        android.content.Context context = getContext();
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        if (android.os.UserHandle.isSameApp(callingUid, this.mMyUid)) {
            return 0;
        }
        java.lang.String str2 = null;
        if (this.mExported && checkUser(callingPid, callingUid, context)) {
            java.lang.String readPermission = getReadPermission();
            if (readPermission == null) {
                i = 0;
            } else {
                int checkPermission = checkPermission(readPermission, attributionSource);
                if (checkPermission == 0) {
                    return 0;
                }
                i = java.lang.Math.max(0, checkPermission);
                str2 = readPermission;
            }
            boolean z = readPermission == null;
            android.content.pm.PathPermission[] pathPermissions = getPathPermissions();
            if (pathPermissions != null) {
                java.lang.String path = uri.getPath();
                for (android.content.pm.PathPermission pathPermission : pathPermissions) {
                    java.lang.String readPermission2 = pathPermission.getReadPermission();
                    if (readPermission2 != null && pathPermission.match(path)) {
                        int checkPermission2 = checkPermission(readPermission2, attributionSource);
                        if (checkPermission2 == 0) {
                            return 0;
                        }
                        i = java.lang.Math.max(i, checkPermission2);
                        z = false;
                        str2 = readPermission2;
                    }
                }
            }
            if (z) {
                return 0;
            }
        } else {
            i = 0;
        }
        int userId = android.os.UserHandle.getUserId(callingUid);
        if (deniedAccessSystemUserOnlyProvider(userId, this.mSystemUserOnly)) {
            return 2;
        }
        if (context.checkUriPermission((!this.mSingleUser || android.os.UserHandle.isSameUser(this.mMyUid, callingUid)) ? uri : maybeAddUserId(uri, userId), callingPid, callingUid, 1) == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (android.Manifest.permission.MANAGE_DOCUMENTS.equals(this.mReadPermission)) {
            str = " requires that you obtain access using ACTION_OPEN_DOCUMENT or related APIs";
        } else if (this.mExported) {
            str = " requires " + str2 + ", or grantUriPermission()";
        } else {
            str = " requires the provider be exported, or grantUriPermission()";
        }
        throw new java.lang.SecurityException("Permission Denial: reading " + getClass().getName() + " uri " + uri + " from pid=" + callingPid + ", uid=" + callingUid + str);
    }

    protected int enforceWritePermissionInner(android.net.Uri uri, android.content.AttributionSource attributionSource) throws java.lang.SecurityException {
        int i;
        java.lang.String str;
        android.content.Context context = getContext();
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        if (android.os.UserHandle.isSameApp(callingUid, this.mMyUid)) {
            return 0;
        }
        java.lang.String str2 = null;
        if (this.mExported && checkUser(callingPid, callingUid, context)) {
            java.lang.String writePermission = getWritePermission();
            if (writePermission == null) {
                i = 0;
            } else {
                int checkPermission = checkPermission(writePermission, attributionSource);
                if (checkPermission == 0) {
                    return 0;
                }
                i = java.lang.Math.max(0, checkPermission);
                str2 = writePermission;
            }
            boolean z = writePermission == null;
            android.content.pm.PathPermission[] pathPermissions = getPathPermissions();
            if (pathPermissions != null) {
                java.lang.String path = uri.getPath();
                for (android.content.pm.PathPermission pathPermission : pathPermissions) {
                    java.lang.String writePermission2 = pathPermission.getWritePermission();
                    if (writePermission2 != null && pathPermission.match(path)) {
                        int checkPermission2 = checkPermission(writePermission2, attributionSource);
                        if (checkPermission2 == 0) {
                            return 0;
                        }
                        i = java.lang.Math.max(i, checkPermission2);
                        z = false;
                        str2 = writePermission2;
                    }
                }
            }
            if (z) {
                return 0;
            }
        } else {
            i = 0;
        }
        if (context.checkUriPermission(uri, callingPid, callingUid, 2) == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (this.mExported) {
            str = " requires " + str2 + ", or grantUriPermission()";
        } else {
            str = " requires the provider be exported, or grantUriPermission()";
        }
        throw new java.lang.SecurityException("Permission Denial: writing " + getClass().getName() + " uri " + uri + " from pid=" + callingPid + ", uid=" + callingUid + str);
    }

    public final android.content.Context getContext() {
        return this.mContext;
    }

    public final android.content.Context requireContext() {
        android.content.Context context = getContext();
        if (context == null) {
            throw new java.lang.IllegalStateException("Cannot find context from the provider.");
        }
        return context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.AttributionSource setCallingAttributionSource(android.content.AttributionSource attributionSource) {
        android.content.AttributionSource attributionSource2 = this.mCallingAttributionSource.get();
        this.mCallingAttributionSource.set(attributionSource);
        onCallingPackageChanged();
        return attributionSource2;
    }

    public final java.lang.String getCallingPackage() {
        android.content.AttributionSource callingAttributionSource = getCallingAttributionSource();
        if (callingAttributionSource != null) {
            return callingAttributionSource.getPackageName();
        }
        return null;
    }

    public final android.content.AttributionSource getCallingAttributionSource() {
        android.content.AttributionSource attributionSource = this.mCallingAttributionSource.get();
        if (attributionSource != null) {
            this.mTransport.mAppOpsManager.checkPackage(android.os.Binder.getCallingUid(), attributionSource.getPackageName());
        }
        return attributionSource;
    }

    public final java.lang.String getCallingAttributionTag() {
        android.content.AttributionSource attributionSource = this.mCallingAttributionSource.get();
        if (attributionSource != null) {
            return attributionSource.getAttributionTag();
        }
        return null;
    }

    @java.lang.Deprecated
    public final java.lang.String getCallingFeatureId() {
        return getCallingAttributionTag();
    }

    public final java.lang.String getCallingPackageUnchecked() {
        android.content.AttributionSource attributionSource = this.mCallingAttributionSource.get();
        if (attributionSource != null) {
            return attributionSource.getPackageName();
        }
        return null;
    }

    public void onCallingPackageChanged() {
    }

    public final class CallingIdentity {
        public final long binderToken;
        public final android.content.AttributionSource callingAttributionSource;

        public CallingIdentity(long j, android.content.AttributionSource attributionSource) {
            this.binderToken = j;
            this.callingAttributionSource = attributionSource;
        }
    }

    public final android.content.ContentProvider.CallingIdentity clearCallingIdentity() {
        return new android.content.ContentProvider.CallingIdentity(android.os.Binder.clearCallingIdentity(), setCallingAttributionSource(null));
    }

    public final void restoreCallingIdentity(android.content.ContentProvider.CallingIdentity callingIdentity) {
        android.os.Binder.restoreCallingIdentity(callingIdentity.binderToken);
        this.mCallingAttributionSource.set(callingIdentity.callingAttributionSource);
    }

    protected final void setAuthorities(java.lang.String str) {
        if (str != null) {
            if (str.indexOf(59) == -1) {
                this.mAuthority = str;
                this.mAuthorities = null;
            } else {
                this.mAuthority = null;
                this.mAuthorities = str.split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
            }
        }
    }

    protected final boolean matchesOurAuthorities(java.lang.String str) {
        if (this.mAuthority != null) {
            return this.mAuthority.equals(str);
        }
        if (this.mAuthorities != null) {
            int length = this.mAuthorities.length;
            for (int i = 0; i < length; i++) {
                if (this.mAuthorities[i].equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected final void setReadPermission(java.lang.String str) {
        this.mReadPermission = str;
    }

    public final java.lang.String getReadPermission() {
        return this.mReadPermission;
    }

    protected final void setWritePermission(java.lang.String str) {
        this.mWritePermission = str;
    }

    public final java.lang.String getWritePermission() {
        return this.mWritePermission;
    }

    protected final void setPathPermissions(android.content.pm.PathPermission[] pathPermissionArr) {
        this.mPathPermissions = pathPermissionArr;
    }

    public final android.content.pm.PathPermission[] getPathPermissions() {
        return this.mPathPermissions;
    }

    public final void setAppOps(int i, int i2) {
        if (!this.mNoPerms) {
            this.mTransport.mReadOp = i;
            this.mTransport.mWriteOp = i2;
        }
    }

    public android.app.AppOpsManager getAppOpsManager() {
        return this.mTransport.mAppOpsManager;
    }

    public final void setTransportLoggingEnabled(boolean z) {
        if (this.mTransport == null) {
            return;
        }
        if (z) {
            this.mTransport.mInterface = new android.content.LoggingContentInterface(getClass().getSimpleName(), this);
        } else {
            this.mTransport.mInterface = this;
        }
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
    }

    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, android.os.CancellationSignal cancellationSignal) {
        return query(uri, strArr, str, strArr2, str2);
    }

    @Override // android.content.ContentInterface
    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) {
        java.lang.String str;
        if (bundle == null) {
            bundle = android.os.Bundle.EMPTY;
        }
        java.lang.String string = bundle.getString(android.content.ContentResolver.QUERY_ARG_SQL_SORT_ORDER);
        if (string == null && bundle.containsKey(android.content.ContentResolver.QUERY_ARG_SORT_COLUMNS)) {
            str = android.content.ContentResolver.createSqlSortClause(bundle);
        } else {
            str = string;
        }
        return query(uri, strArr, bundle.getString(android.content.ContentResolver.QUERY_ARG_SQL_SELECTION), bundle.getStringArray(android.content.ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS), str, cancellationSignal);
    }

    public java.lang.String getTypeAnonymous(android.net.Uri uri) {
        return getType(uri);
    }

    @Override // android.content.ContentInterface
    public android.net.Uri canonicalize(android.net.Uri uri) {
        return null;
    }

    @Override // android.content.ContentInterface
    public android.net.Uri uncanonicalize(android.net.Uri uri) {
        return uri;
    }

    @Override // android.content.ContentInterface
    public boolean refresh(android.net.Uri uri, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) {
        return false;
    }

    @Override // android.content.ContentInterface
    @android.annotation.SystemApi
    public int checkUriPermission(android.net.Uri uri, int i, int i2) {
        return -1;
    }

    public android.net.Uri rejectInsert(android.net.Uri uri, android.content.ContentValues contentValues) {
        return uri.buildUpon().appendPath(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS).build();
    }

    @Override // android.content.ContentInterface
    public android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) {
        return insert(uri, contentValues);
    }

    @Override // android.content.ContentInterface
    public int bulkInsert(android.net.Uri uri, android.content.ContentValues[] contentValuesArr) {
        int length = contentValuesArr.length;
        for (android.content.ContentValues contentValues : contentValuesArr) {
            insert(uri, contentValues);
        }
        return length;
    }

    @Override // android.content.ContentInterface
    public int delete(android.net.Uri uri, android.os.Bundle bundle) {
        if (bundle == null) {
            bundle = android.os.Bundle.EMPTY;
        }
        return delete(uri, bundle.getString(android.content.ContentResolver.QUERY_ARG_SQL_SELECTION), bundle.getStringArray(android.content.ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS));
    }

    @Override // android.content.ContentInterface
    public int update(android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) {
        if (bundle == null) {
            bundle = android.os.Bundle.EMPTY;
        }
        return update(uri, contentValues, bundle.getString(android.content.ContentResolver.QUERY_ARG_SQL_SELECTION), bundle.getStringArray(android.content.ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS));
    }

    public android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException {
        throw new java.io.FileNotFoundException("No files supported by provider at " + uri);
    }

    @Override // android.content.ContentInterface
    public android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        return openFile(uri, str);
    }

    public android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException {
        android.os.ParcelFileDescriptor openFile = openFile(uri, str);
        if (openFile != null) {
            return new android.content.res.AssetFileDescriptor(openFile, 0L, -1L);
        }
        return null;
    }

    @Override // android.content.ContentInterface
    public android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        return openAssetFile(uri, str);
    }

    protected final android.os.ParcelFileDescriptor openFileHelper(android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException {
        android.database.Cursor query = query(uri, new java.lang.String[]{"_data"}, null, null, null);
        int count = query != null ? query.getCount() : 0;
        if (count != 1) {
            if (query != null) {
                query.close();
            }
            if (count == 0) {
                throw new java.io.FileNotFoundException("No entry for " + uri);
            }
            throw new java.io.FileNotFoundException("Multiple items at " + uri);
        }
        query.moveToFirst();
        int columnIndex = query.getColumnIndex("_data");
        java.lang.String string = columnIndex >= 0 ? query.getString(columnIndex) : null;
        query.close();
        if (string == null) {
            throw new java.io.FileNotFoundException("Column _data not found.");
        }
        return android.os.ParcelFileDescriptor.open(new java.io.File(string), android.os.ParcelFileDescriptor.parseMode(str));
    }

    @Override // android.content.ContentInterface
    public java.lang.String[] getStreamTypes(android.net.Uri uri, java.lang.String str) {
        return null;
    }

    public android.content.res.AssetFileDescriptor openTypedAssetFile(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle) throws java.io.FileNotFoundException {
        if ("*/*".equals(str)) {
            return openAssetFile(uri, "r");
        }
        java.lang.String type = getType(uri);
        if (type != null && android.content.ClipDescription.compareMimeTypes(type, str)) {
            return openAssetFile(uri, "r");
        }
        throw new java.io.FileNotFoundException("Can't open " + uri + " as type " + str);
    }

    @Override // android.content.ContentInterface
    public android.content.res.AssetFileDescriptor openTypedAssetFile(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        return openTypedAssetFile(uri, str, bundle);
    }

    public <T> android.os.ParcelFileDescriptor openPipeHelper(final android.net.Uri uri, final java.lang.String str, final android.os.Bundle bundle, final T t, final android.content.ContentProvider.PipeDataWriter<T> pipeDataWriter) throws java.io.FileNotFoundException {
        try {
            final android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
            new android.os.AsyncTask<java.lang.Object, java.lang.Object, java.lang.Object>() { // from class: android.content.ContentProvider.1
                @Override // android.os.AsyncTask
                protected java.lang.Object doInBackground(java.lang.Object... objArr) {
                    pipeDataWriter.writeDataToPipe(createPipe[1], uri, str, bundle, t);
                    try {
                        createPipe[1].close();
                        return null;
                    } catch (java.io.IOException e) {
                        android.util.Log.w(android.content.ContentProvider.TAG, "Failure closing pipe", e);
                        return null;
                    }
                }
            }.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, null);
            return createPipe[0];
        } catch (java.io.IOException e) {
            throw new java.io.FileNotFoundException("failure making pipe");
        }
    }

    protected boolean isTemporary() {
        return false;
    }

    public android.content.IContentProvider getIContentProvider() {
        return this.mTransport;
    }

    public void attachInfoForTesting(android.content.Context context, android.content.pm.ProviderInfo providerInfo) {
        attachInfo(context, providerInfo, true);
    }

    public void attachInfo(android.content.Context context, android.content.pm.ProviderInfo providerInfo) {
        attachInfo(context, providerInfo, false);
    }

    private void attachInfo(android.content.Context context, android.content.pm.ProviderInfo providerInfo, boolean z) {
        this.mNoPerms = z;
        this.mCallingAttributionSource = new java.lang.ThreadLocal<>();
        if (this.mContext == null) {
            this.mContext = context;
            if (context != null && this.mTransport != null) {
                this.mTransport.mAppOpsManager = (android.app.AppOpsManager) context.getSystemService(android.content.Context.APP_OPS_SERVICE);
            }
            this.mMyUid = android.os.Process.myUid();
            if (providerInfo != null) {
                setReadPermission(providerInfo.readPermission);
                setWritePermission(providerInfo.writePermission);
                setPathPermissions(providerInfo.pathPermissions);
                this.mExported = providerInfo.exported;
                this.mSingleUser = (providerInfo.flags & 1073741824) != 0;
                this.mSystemUserOnly = (providerInfo.flags & 536870912) != 0;
                setAuthorities(providerInfo.authority);
            }
            if (android.os.Build.IS_DEBUGGABLE) {
                setTransportLoggingEnabled(android.util.Log.isLoggable(getClass().getSimpleName(), 2));
            }
            onCreate();
        }
    }

    @Override // android.content.ContentInterface
    public android.content.ContentProviderResult[] applyBatch(java.lang.String str, java.util.ArrayList<android.content.ContentProviderOperation> arrayList) throws android.content.OperationApplicationException {
        return applyBatch(arrayList);
    }

    public android.content.ContentProviderResult[] applyBatch(java.util.ArrayList<android.content.ContentProviderOperation> arrayList) throws android.content.OperationApplicationException {
        int size = arrayList.size();
        android.content.ContentProviderResult[] contentProviderResultArr = new android.content.ContentProviderResult[size];
        for (int i = 0; i < size; i++) {
            contentProviderResultArr[i] = arrayList.get(i).apply(this, contentProviderResultArr, i);
        }
        return contentProviderResultArr;
    }

    @Override // android.content.ContentInterface
    public android.os.Bundle call(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle) {
        return call(str2, str3, bundle);
    }

    public android.os.Bundle call(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        return null;
    }

    public void shutdown() {
        android.util.Log.w(TAG, "implement ContentProvider shutdown() to make sure all database connections are gracefully shutdown");
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println("nothing to dump");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateIncomingAuthority(java.lang.String str) throws java.lang.SecurityException {
        java.lang.String str2;
        if (!matchesOurAuthorities(getAuthorityWithoutUserId(str))) {
            java.lang.String str3 = "The authority " + str + " does not match the one of the contentProvider: ";
            if (this.mAuthority != null) {
                str2 = str3 + this.mAuthority;
            } else {
                str2 = str3 + java.util.Arrays.toString(this.mAuthorities);
            }
            throw new java.lang.SecurityException(str2);
        }
    }

    public android.net.Uri validateIncomingUri(android.net.Uri uri) throws java.lang.SecurityException {
        java.lang.String authority = uri.getAuthority();
        if (!this.mSingleUser) {
            int userIdFromAuthority = getUserIdFromAuthority(authority, -2);
            if (deniedAccessSystemUserOnlyProvider(this.mContext.getUserId(), this.mSystemUserOnly)) {
                throw new java.lang.SecurityException("Trying to query a SYSTEM user only content provider from user:" + this.mContext.getUserId());
            }
            if (userIdFromAuthority != -2 && userIdFromAuthority != this.mContext.getUserId() && !isContentRedirectionAllowedForUser(userIdFromAuthority)) {
                throw new java.lang.SecurityException("trying to query a ContentProvider in user " + this.mContext.getUserId() + " with a uri belonging to user " + userIdFromAuthority);
            }
        }
        validateIncomingAuthority(authority);
        java.lang.String encodedPath = uri.getEncodedPath();
        if (encodedPath != null && encodedPath.indexOf("//") != -1) {
            android.net.Uri build = uri.buildUpon().encodedPath(encodedPath.replaceAll("//+", "/")).build();
            android.util.Log.w(TAG, "Normalized " + uri + " to " + build + " to avoid possible security issues");
            return build;
        }
        return uri;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.net.Uri maybeGetUriWithoutUserId(android.net.Uri uri) {
        if (this.mSingleUser) {
            return uri;
        }
        return getUriWithoutUserId(uri);
    }

    public static int getUserIdFromAuthority(java.lang.String str, int i) {
        int lastIndexOf;
        if (str == null || (lastIndexOf = str.lastIndexOf(64)) == -1) {
            return i;
        }
        try {
            return java.lang.Integer.parseInt(str.substring(0, lastIndexOf));
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.w(TAG, "Error parsing userId.", e);
            return -10000;
        }
    }

    public static int getUserIdFromAuthority(java.lang.String str) {
        return getUserIdFromAuthority(str, -2);
    }

    public static int getUserIdFromUri(android.net.Uri uri, int i) {
        return uri == null ? i : getUserIdFromAuthority(uri.getAuthority(), i);
    }

    public static int getUserIdFromUri(android.net.Uri uri) {
        return getUserIdFromUri(uri, -2);
    }

    public static android.os.UserHandle getUserHandleFromUri(android.net.Uri uri) {
        return android.os.UserHandle.of(getUserIdFromUri(uri, android.os.Process.myUserHandle().getIdentifier()));
    }

    public static java.lang.String getAuthorityWithoutUserId(java.lang.String str) {
        if (str == null) {
            return null;
        }
        return str.substring(str.lastIndexOf(64) + 1);
    }

    public static android.net.Uri getUriWithoutUserId(android.net.Uri uri) {
        if (uri == null) {
            return null;
        }
        android.net.Uri.Builder buildUpon = uri.buildUpon();
        buildUpon.authority(getAuthorityWithoutUserId(uri.getAuthority()));
        return buildUpon.build();
    }

    public static boolean uriHasUserId(android.net.Uri uri) {
        if (uri == null) {
            return false;
        }
        return !android.text.TextUtils.isEmpty(uri.getUserInfo());
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static android.net.Uri createContentUriForUser(android.net.Uri uri, android.os.UserHandle userHandle) {
        if (!"content".equals(uri.getScheme())) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Given URI [%s] is not a content URI: ", uri));
        }
        int identifier = userHandle.getIdentifier();
        if (uriHasUserId(uri)) {
            if (java.lang.String.valueOf(identifier).equals(uri.getUserInfo())) {
                return uri;
            }
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Given URI [%s] already has a user ID, different from given user handle [%s]", uri, java.lang.Integer.valueOf(identifier)));
        }
        android.net.Uri.Builder buildUpon = uri.buildUpon();
        buildUpon.encodedAuthority("" + userHandle.getIdentifier() + "@" + uri.getEncodedAuthority());
        return buildUpon.build();
    }

    public static android.net.Uri maybeAddUserId(android.net.Uri uri, int i) {
        if (uri == null) {
            return null;
        }
        if (i != -2 && "content".equals(uri.getScheme()) && !uriHasUserId(uri)) {
            android.net.Uri.Builder buildUpon = uri.buildUpon();
            buildUpon.encodedAuthority("" + i + "@" + uri.getEncodedAuthority());
            return buildUpon.build();
        }
        return uri;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void traceBegin(long j, java.lang.String str, java.lang.String str2) {
        if (android.os.Trace.isTagEnabled(j)) {
            android.os.Trace.traceBegin(j, str + str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean deniedAccessSystemUserOnlyProvider(int i, boolean z) {
        return android.multiuser.Flags.enableSystemUserOnlyForServicesAndProviders() && i != 0 && z;
    }
}
