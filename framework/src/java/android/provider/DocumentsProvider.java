package android.provider;

/* loaded from: classes3.dex */
public abstract class DocumentsProvider extends android.content.ContentProvider {
    private static final int MATCH_CHILDREN = 6;
    private static final int MATCH_CHILDREN_TREE = 8;
    private static final int MATCH_DOCUMENT = 5;
    private static final int MATCH_DOCUMENT_TREE = 7;
    private static final int MATCH_RECENT = 3;
    private static final int MATCH_ROOT = 2;
    private static final int MATCH_ROOTS = 1;
    private static final int MATCH_SEARCH = 4;
    private static final java.lang.String TAG = "DocumentsProvider";
    private java.lang.String mAuthority;
    private android.content.UriMatcher mMatcher;

    public abstract android.os.ParcelFileDescriptor openDocument(java.lang.String str, java.lang.String str2, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException;

    public abstract android.database.Cursor queryChildDocuments(java.lang.String str, java.lang.String[] strArr, java.lang.String str2) throws java.io.FileNotFoundException;

    public abstract android.database.Cursor queryDocument(java.lang.String str, java.lang.String[] strArr) throws java.io.FileNotFoundException;

    public abstract android.database.Cursor queryRoots(java.lang.String[] strArr) throws java.io.FileNotFoundException;

    @Override // android.content.ContentProvider
    public void attachInfo(android.content.Context context, android.content.pm.ProviderInfo providerInfo) {
        registerAuthority(providerInfo.authority);
        if (!providerInfo.exported) {
            throw new java.lang.SecurityException("Provider must be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new java.lang.SecurityException("Provider must grantUriPermissions");
        }
        if (!android.Manifest.permission.MANAGE_DOCUMENTS.equals(providerInfo.readPermission) || !android.Manifest.permission.MANAGE_DOCUMENTS.equals(providerInfo.writePermission)) {
            throw new java.lang.SecurityException("Provider must be protected by MANAGE_DOCUMENTS");
        }
        super.attachInfo(context, providerInfo);
    }

    @Override // android.content.ContentProvider
    public void attachInfoForTesting(android.content.Context context, android.content.pm.ProviderInfo providerInfo) {
        registerAuthority(providerInfo.authority);
        super.attachInfoForTesting(context, providerInfo);
    }

    private void registerAuthority(java.lang.String str) {
        this.mAuthority = str;
        this.mMatcher = new android.content.UriMatcher(-1);
        this.mMatcher.addURI(this.mAuthority, "root", 1);
        this.mMatcher.addURI(this.mAuthority, "root/*", 2);
        this.mMatcher.addURI(this.mAuthority, "root/*/recent", 3);
        this.mMatcher.addURI(this.mAuthority, "root/*/search", 4);
        this.mMatcher.addURI(this.mAuthority, "document/*", 5);
        this.mMatcher.addURI(this.mAuthority, "document/*/children", 6);
        this.mMatcher.addURI(this.mAuthority, "tree/*/document/*", 7);
        this.mMatcher.addURI(this.mAuthority, "tree/*/document/*/children", 8);
    }

    public boolean isChildDocument(java.lang.String str, java.lang.String str2) {
        return false;
    }

    private void enforceTreeForExtraUris(android.os.Bundle bundle) {
        enforceTree((android.net.Uri) bundle.getParcelable("uri", android.net.Uri.class));
        enforceTree((android.net.Uri) bundle.getParcelable(android.provider.DocumentsContract.EXTRA_PARENT_URI, android.net.Uri.class));
        enforceTree((android.net.Uri) bundle.getParcelable(android.provider.DocumentsContract.EXTRA_TARGET_URI, android.net.Uri.class));
    }

    private void enforceTree(android.net.Uri uri) {
        if (uri != null && android.provider.DocumentsContract.isTreeUri(uri)) {
            java.lang.String treeDocumentId = android.provider.DocumentsContract.getTreeDocumentId(uri);
            java.lang.String documentId = android.provider.DocumentsContract.getDocumentId(uri);
            if (!java.util.Objects.equals(treeDocumentId, documentId) && !isChildDocument(treeDocumentId, documentId)) {
                throw new java.lang.SecurityException("Document " + documentId + " is not a descendant of " + treeDocumentId);
            }
        }
    }

    private android.net.Uri validateIncomingNullableUri(android.net.Uri uri) {
        if (uri == null) {
            return null;
        }
        return validateIncomingUri(uri);
    }

    public java.lang.String createDocument(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Create not supported");
    }

    public java.lang.String renameDocument(java.lang.String str, java.lang.String str2) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Rename not supported");
    }

    public void deleteDocument(java.lang.String str) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Delete not supported");
    }

    public java.lang.String copyDocument(java.lang.String str, java.lang.String str2) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Copy not supported");
    }

    public java.lang.String moveDocument(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Move not supported");
    }

    public void removeDocument(java.lang.String str, java.lang.String str2) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Remove not supported");
    }

    public android.provider.DocumentsContract.Path findDocumentPath(java.lang.String str, java.lang.String str2) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("findDocumentPath not supported.");
    }

    public android.content.IntentSender createWebLinkIntent(java.lang.String str, android.os.Bundle bundle) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("createWebLink is not supported.");
    }

    public android.database.Cursor queryRecentDocuments(java.lang.String str, java.lang.String[] strArr) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Recent not supported");
    }

    public android.database.Cursor queryRecentDocuments(java.lang.String str, java.lang.String[] strArr, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        com.android.internal.util.Preconditions.checkNotNull(str, "rootId can not be null");
        android.database.Cursor queryRecentDocuments = queryRecentDocuments(str, strArr);
        android.os.Bundle bundle2 = new android.os.Bundle();
        queryRecentDocuments.setExtras(bundle2);
        bundle2.putStringArray(android.content.ContentResolver.EXTRA_HONORED_ARGS, new java.lang.String[0]);
        return queryRecentDocuments;
    }

    public android.database.Cursor queryChildDocuments(java.lang.String str, java.lang.String[] strArr, android.os.Bundle bundle) throws java.io.FileNotFoundException {
        return queryChildDocuments(str, strArr, getSortClause(bundle));
    }

    public android.database.Cursor queryChildDocumentsForManage(java.lang.String str, java.lang.String[] strArr, java.lang.String str2) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Manage not supported");
    }

    public android.database.Cursor querySearchDocuments(java.lang.String str, java.lang.String str2, java.lang.String[] strArr) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Search not supported");
    }

    public android.database.Cursor querySearchDocuments(java.lang.String str, java.lang.String[] strArr, android.os.Bundle bundle) throws java.io.FileNotFoundException {
        com.android.internal.util.Preconditions.checkNotNull(str, "rootId can not be null");
        com.android.internal.util.Preconditions.checkNotNull(bundle, "queryArgs can not be null");
        return querySearchDocuments(str, android.provider.DocumentsContract.getSearchDocumentsQuery(bundle), strArr);
    }

    public void ejectRoot(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("Eject not supported");
    }

    public android.os.Bundle getDocumentMetadata(java.lang.String str) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Metadata not supported");
    }

    public java.lang.String getDocumentType(java.lang.String str) throws java.io.FileNotFoundException {
        android.database.Cursor queryDocument = queryDocument(str, null);
        try {
            if (!queryDocument.moveToFirst()) {
                return null;
            }
            return queryDocument.getString(queryDocument.getColumnIndexOrThrow("mime_type"));
        } finally {
            libcore.io.IoUtils.closeQuietly(queryDocument);
        }
    }

    public android.content.res.AssetFileDescriptor openDocumentThumbnail(java.lang.String str, android.graphics.Point point, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        throw new java.lang.UnsupportedOperationException("Thumbnails not supported");
    }

    public android.content.res.AssetFileDescriptor openTypedDocument(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        throw new java.io.FileNotFoundException("The requested MIME type is not supported.");
    }

    @Override // android.content.ContentProvider
    public final android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        throw new java.lang.UnsupportedOperationException("Pre-Android-O query format not supported.");
    }

    @Override // android.content.ContentProvider
    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, android.os.CancellationSignal cancellationSignal) {
        throw new java.lang.UnsupportedOperationException("Pre-Android-O query format not supported.");
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) {
        try {
            switch (this.mMatcher.match(uri)) {
                case 1:
                    return queryRoots(strArr);
                case 2:
                default:
                    throw new java.lang.UnsupportedOperationException("Unsupported Uri " + uri);
                case 3:
                    return queryRecentDocuments(android.provider.DocumentsContract.getRootId(uri), strArr, bundle, cancellationSignal);
                case 4:
                    return querySearchDocuments(android.provider.DocumentsContract.getRootId(uri), strArr, bundle);
                case 5:
                case 7:
                    enforceTree(uri);
                    return queryDocument(android.provider.DocumentsContract.getDocumentId(uri), strArr);
                case 6:
                case 8:
                    enforceTree(uri);
                    if (android.provider.DocumentsContract.isManageMode(uri)) {
                        return queryChildDocumentsForManage(android.provider.DocumentsContract.getDocumentId(uri), strArr, getSortClause(bundle));
                    }
                    return queryChildDocuments(android.provider.DocumentsContract.getDocumentId(uri), strArr, bundle);
            }
        } catch (java.io.FileNotFoundException e) {
            android.util.Log.w(TAG, "Failed during query", e);
            return null;
        }
    }

    private static java.lang.String getSortClause(android.os.Bundle bundle) {
        if (bundle == null) {
            bundle = android.os.Bundle.EMPTY;
        }
        java.lang.String string = bundle.getString(android.content.ContentResolver.QUERY_ARG_SQL_SORT_ORDER);
        if (string == null && bundle.containsKey(android.content.ContentResolver.QUERY_ARG_SORT_COLUMNS)) {
            return android.content.ContentResolver.createSqlSortClause(bundle);
        }
        return string;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final java.lang.String getType(android.net.Uri uri) {
        try {
            switch (this.mMatcher.match(uri)) {
                case 2:
                    return android.provider.DocumentsContract.Root.MIME_TYPE_ITEM;
                case 5:
                case 7:
                    enforceTree(uri);
                    return getDocumentType(android.provider.DocumentsContract.getDocumentId(uri));
                default:
                    return null;
            }
        } catch (java.io.FileNotFoundException e) {
            android.util.Log.w(TAG, "Failed during getType", e);
            return null;
        }
        android.util.Log.w(TAG, "Failed during getType", e);
        return null;
    }

    @Override // android.content.ContentProvider
    public final java.lang.String getTypeAnonymous(android.net.Uri uri) {
        switch (this.mMatcher.match(uri)) {
            case 2:
                return android.provider.DocumentsContract.Root.MIME_TYPE_ITEM;
            default:
                return null;
        }
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public android.net.Uri canonicalize(android.net.Uri uri) {
        android.content.Context context = getContext();
        switch (this.mMatcher.match(uri)) {
            case 7:
                enforceTree(uri);
                android.net.Uri buildDocumentUri = android.provider.DocumentsContract.buildDocumentUri(uri.getAuthority(), android.provider.DocumentsContract.getDocumentId(uri));
                context.grantUriPermission(getCallingPackage(), buildDocumentUri, getCallingOrSelfUriPermissionModeFlags(context, uri));
                return buildDocumentUri;
            default:
                return null;
        }
    }

    private static int getCallingOrSelfUriPermissionModeFlags(android.content.Context context, android.net.Uri uri) {
        int i = context.checkCallingOrSelfUriPermission(uri, 1) != 0 ? 0 : 1;
        if (context.checkCallingOrSelfUriPermission(uri, 2) == 0) {
            i |= 2;
        }
        if (context.checkCallingOrSelfUriPermission(uri, 65) == 0) {
            return i | 64;
        }
        return i;
    }

    @Override // android.content.ContentProvider
    public final android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues) {
        throw new java.lang.UnsupportedOperationException("Insert not supported");
    }

    @Override // android.content.ContentProvider
    public final int delete(android.net.Uri uri, java.lang.String str, java.lang.String[] strArr) {
        throw new java.lang.UnsupportedOperationException("Delete not supported");
    }

    @Override // android.content.ContentProvider
    public final int update(android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) {
        throw new java.lang.UnsupportedOperationException("Update not supported");
    }

    @Override // android.content.ContentProvider
    public android.os.Bundle call(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        if (!str.startsWith("android:")) {
            return super.call(str, str2, bundle);
        }
        try {
            return callUnchecked(str, str2, bundle);
        } catch (java.io.FileNotFoundException e) {
            throw new android.os.ParcelableException(e);
        }
    }

    private android.os.Bundle callUnchecked(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws java.io.FileNotFoundException {
        java.lang.String str3;
        android.content.Context context = getContext();
        android.os.Bundle bundle2 = new android.os.Bundle();
        enforceTreeForExtraUris(bundle);
        android.net.Uri validateIncomingNullableUri = validateIncomingNullableUri((android.net.Uri) bundle.getParcelable("uri", android.net.Uri.class));
        android.net.Uri validateIncomingNullableUri2 = validateIncomingNullableUri((android.net.Uri) bundle.getParcelable(android.provider.DocumentsContract.EXTRA_TARGET_URI, android.net.Uri.class));
        android.net.Uri validateIncomingNullableUri3 = validateIncomingNullableUri((android.net.Uri) bundle.getParcelable(android.provider.DocumentsContract.EXTRA_PARENT_URI, android.net.Uri.class));
        if (android.provider.DocumentsContract.METHOD_EJECT_ROOT.equals(str)) {
            enforceWritePermissionInner(validateIncomingNullableUri, getCallingAttributionSource());
            ejectRoot(android.provider.DocumentsContract.getRootId(validateIncomingNullableUri));
            return bundle2;
        }
        java.lang.String authority = validateIncomingNullableUri.getAuthority();
        java.lang.String documentId = android.provider.DocumentsContract.getDocumentId(validateIncomingNullableUri);
        if (!this.mAuthority.equals(authority)) {
            throw new java.lang.SecurityException("Requested authority " + authority + " doesn't match provider " + this.mAuthority);
        }
        if (android.provider.DocumentsContract.METHOD_IS_CHILD_DOCUMENT.equals(str)) {
            enforceReadPermissionInner(validateIncomingNullableUri, getCallingAttributionSource());
            bundle2.putBoolean("result", this.mAuthority.equals(validateIncomingNullableUri2.getAuthority()) && isChildDocument(documentId, android.provider.DocumentsContract.getDocumentId(validateIncomingNullableUri2)));
        } else if (android.provider.DocumentsContract.METHOD_CREATE_DOCUMENT.equals(str)) {
            enforceWritePermissionInner(validateIncomingNullableUri, getCallingAttributionSource());
            bundle2.putParcelable("uri", android.provider.DocumentsContract.buildDocumentUriMaybeUsingTree(validateIncomingNullableUri, createDocument(documentId, bundle.getString("mime_type"), bundle.getString("_display_name"))));
        } else if (android.provider.DocumentsContract.METHOD_CREATE_WEB_LINK_INTENT.equals(str)) {
            enforceWritePermissionInner(validateIncomingNullableUri, getCallingAttributionSource());
            bundle2.putParcelable("result", createWebLinkIntent(documentId, bundle.getBundle(android.provider.DocumentsContract.EXTRA_OPTIONS)));
        } else if (android.provider.DocumentsContract.METHOD_RENAME_DOCUMENT.equals(str)) {
            enforceWritePermissionInner(validateIncomingNullableUri, getCallingAttributionSource());
            java.lang.String renameDocument = renameDocument(documentId, bundle.getString("_display_name"));
            if (renameDocument != null) {
                android.net.Uri buildDocumentUriMaybeUsingTree = android.provider.DocumentsContract.buildDocumentUriMaybeUsingTree(validateIncomingNullableUri, renameDocument);
                if (!android.provider.DocumentsContract.isTreeUri(buildDocumentUriMaybeUsingTree)) {
                    context.grantUriPermission(getCallingPackage(), buildDocumentUriMaybeUsingTree, getCallingOrSelfUriPermissionModeFlags(context, validateIncomingNullableUri));
                }
                bundle2.putParcelable("uri", buildDocumentUriMaybeUsingTree);
                revokeDocumentPermission(documentId);
            }
        } else if (android.provider.DocumentsContract.METHOD_DELETE_DOCUMENT.equals(str)) {
            enforceWritePermissionInner(validateIncomingNullableUri, getCallingAttributionSource());
            deleteDocument(documentId);
            revokeDocumentPermission(documentId);
        } else if (android.provider.DocumentsContract.METHOD_COPY_DOCUMENT.equals(str)) {
            java.lang.String documentId2 = android.provider.DocumentsContract.getDocumentId(validateIncomingNullableUri2);
            enforceReadPermissionInner(validateIncomingNullableUri, getCallingAttributionSource());
            enforceWritePermissionInner(validateIncomingNullableUri2, getCallingAttributionSource());
            java.lang.String copyDocument = copyDocument(documentId, documentId2);
            if (copyDocument != null) {
                android.net.Uri buildDocumentUriMaybeUsingTree2 = android.provider.DocumentsContract.buildDocumentUriMaybeUsingTree(validateIncomingNullableUri, copyDocument);
                if (!android.provider.DocumentsContract.isTreeUri(buildDocumentUriMaybeUsingTree2)) {
                    context.grantUriPermission(getCallingPackage(), buildDocumentUriMaybeUsingTree2, getCallingOrSelfUriPermissionModeFlags(context, validateIncomingNullableUri));
                }
                bundle2.putParcelable("uri", buildDocumentUriMaybeUsingTree2);
            }
        } else if (android.provider.DocumentsContract.METHOD_MOVE_DOCUMENT.equals(str)) {
            java.lang.String documentId3 = android.provider.DocumentsContract.getDocumentId(validateIncomingNullableUri3);
            java.lang.String documentId4 = android.provider.DocumentsContract.getDocumentId(validateIncomingNullableUri2);
            enforceWritePermissionInner(validateIncomingNullableUri, getCallingAttributionSource());
            enforceReadPermissionInner(validateIncomingNullableUri3, getCallingAttributionSource());
            enforceWritePermissionInner(validateIncomingNullableUri2, getCallingAttributionSource());
            java.lang.String moveDocument = moveDocument(documentId, documentId3, documentId4);
            if (moveDocument != null) {
                android.net.Uri buildDocumentUriMaybeUsingTree3 = android.provider.DocumentsContract.buildDocumentUriMaybeUsingTree(validateIncomingNullableUri, moveDocument);
                if (!android.provider.DocumentsContract.isTreeUri(buildDocumentUriMaybeUsingTree3)) {
                    context.grantUriPermission(getCallingPackage(), buildDocumentUriMaybeUsingTree3, getCallingOrSelfUriPermissionModeFlags(context, validateIncomingNullableUri));
                }
                bundle2.putParcelable("uri", buildDocumentUriMaybeUsingTree3);
            }
        } else if (android.provider.DocumentsContract.METHOD_REMOVE_DOCUMENT.equals(str)) {
            java.lang.String documentId5 = android.provider.DocumentsContract.getDocumentId(validateIncomingNullableUri3);
            enforceReadPermissionInner(validateIncomingNullableUri3, getCallingAttributionSource());
            enforceWritePermissionInner(validateIncomingNullableUri, getCallingAttributionSource());
            removeDocument(documentId, documentId5);
        } else if (android.provider.DocumentsContract.METHOD_FIND_DOCUMENT_PATH.equals(str)) {
            boolean isTreeUri = android.provider.DocumentsContract.isTreeUri(validateIncomingNullableUri);
            if (!isTreeUri) {
                getContext().enforceCallingPermission(android.Manifest.permission.MANAGE_DOCUMENTS, null);
            } else {
                enforceReadPermissionInner(validateIncomingNullableUri, getCallingAttributionSource());
            }
            if (isTreeUri) {
                str3 = android.provider.DocumentsContract.getTreeDocumentId(validateIncomingNullableUri);
            } else {
                str3 = null;
            }
            android.provider.DocumentsContract.Path findDocumentPath = findDocumentPath(str3, documentId);
            if (isTreeUri) {
                if (!java.util.Objects.equals(findDocumentPath.getPath().get(0), str3)) {
                    android.util.Log.wtf(TAG, "Provider doesn't return path from the tree root. Expected: " + str3 + " found: " + findDocumentPath.getPath().get(0));
                    java.util.LinkedList linkedList = new java.util.LinkedList(findDocumentPath.getPath());
                    while (linkedList.size() > 1 && !java.util.Objects.equals(linkedList.getFirst(), str3)) {
                        linkedList.removeFirst();
                    }
                    findDocumentPath = new android.provider.DocumentsContract.Path(null, linkedList);
                }
                if (findDocumentPath.getRootId() != null) {
                    android.util.Log.wtf(TAG, "Provider returns root id :" + findDocumentPath.getRootId() + " unexpectedly. Erase root id.");
                    findDocumentPath = new android.provider.DocumentsContract.Path(null, findDocumentPath.getPath());
                }
            }
            bundle2.putParcelable("result", findDocumentPath);
        } else {
            if (android.provider.DocumentsContract.METHOD_GET_DOCUMENT_METADATA.equals(str)) {
                return getDocumentMetadata(documentId);
            }
            throw new java.lang.UnsupportedOperationException("Method not supported " + str);
        }
        return bundle2;
    }

    public final void revokeDocumentPermission(java.lang.String str) {
        android.content.Context context = getContext();
        context.revokeUriPermission(android.provider.DocumentsContract.buildDocumentUri(this.mAuthority, str), -1);
        context.revokeUriPermission(android.provider.DocumentsContract.buildTreeDocumentUri(this.mAuthority, str), -1);
    }

    @Override // android.content.ContentProvider
    public final android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException {
        enforceTree(uri);
        return openDocument(android.provider.DocumentsContract.getDocumentId(uri), str, null);
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        enforceTree(uri);
        return openDocument(android.provider.DocumentsContract.getDocumentId(uri), str, cancellationSignal);
    }

    @Override // android.content.ContentProvider
    public final android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException {
        enforceTree(uri);
        android.os.ParcelFileDescriptor openDocument = openDocument(android.provider.DocumentsContract.getDocumentId(uri), str, null);
        if (openDocument != null) {
            return new android.content.res.AssetFileDescriptor(openDocument, 0L, -1L);
        }
        return null;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        enforceTree(uri);
        android.os.ParcelFileDescriptor openDocument = openDocument(android.provider.DocumentsContract.getDocumentId(uri), str, cancellationSignal);
        if (openDocument != null) {
            return new android.content.res.AssetFileDescriptor(openDocument, 0L, -1L);
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public final android.content.res.AssetFileDescriptor openTypedAssetFile(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle) throws java.io.FileNotFoundException {
        return openTypedAssetFileImpl(uri, str, bundle, null);
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final android.content.res.AssetFileDescriptor openTypedAssetFile(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        return openTypedAssetFileImpl(uri, str, bundle, cancellationSignal);
    }

    public java.lang.String[] getDocumentStreamTypes(java.lang.String str, java.lang.String str2) {
        android.database.Cursor cursor;
        android.database.Cursor cursor2 = null;
        try {
            cursor = queryDocument(str, null);
            try {
                if (cursor.moveToFirst()) {
                    java.lang.String string = cursor.getString(cursor.getColumnIndexOrThrow("mime_type"));
                    if ((cursor.getLong(cursor.getColumnIndexOrThrow("flags")) & 512) == 0 && string != null && android.content.MimeTypeFilter.matches(string, str2)) {
                        java.lang.String[] strArr = {string};
                        libcore.io.IoUtils.closeQuietly(cursor);
                        return strArr;
                    }
                }
                libcore.io.IoUtils.closeQuietly(cursor);
                return null;
            } catch (java.io.FileNotFoundException e) {
                libcore.io.IoUtils.closeQuietly(cursor);
                return null;
            } catch (java.lang.Throwable th) {
                th = th;
                cursor2 = cursor;
                libcore.io.IoUtils.closeQuietly(cursor2);
                throw th;
            }
        } catch (java.io.FileNotFoundException e2) {
            cursor = null;
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public java.lang.String[] getStreamTypes(android.net.Uri uri, java.lang.String str) {
        enforceTree(uri);
        return getDocumentStreamTypes(android.provider.DocumentsContract.getDocumentId(uri), str);
    }

    private final android.content.res.AssetFileDescriptor openTypedAssetFileImpl(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        enforceTree(uri);
        java.lang.String documentId = android.provider.DocumentsContract.getDocumentId(uri);
        if (bundle != null && bundle.containsKey(android.content.ContentResolver.EXTRA_SIZE)) {
            return openDocumentThumbnail(documentId, (android.graphics.Point) bundle.getParcelable(android.content.ContentResolver.EXTRA_SIZE, android.graphics.Point.class), cancellationSignal);
        }
        if ("*/*".equals(str)) {
            return openAssetFile(uri, "r");
        }
        java.lang.String type = getType(uri);
        if (type != null && android.content.ClipDescription.compareMimeTypes(type, str)) {
            return openAssetFile(uri, "r");
        }
        return openTypedDocument(documentId, str, bundle, cancellationSignal);
    }
}
