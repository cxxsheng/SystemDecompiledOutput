package com.android.internal.content;

/* loaded from: classes4.dex */
public abstract class FileSystemProvider extends android.provider.DocumentsProvider {
    private static final boolean LOG_INOTIFY = false;
    private static final int MAX_RESULTS_NUMBER = 23;
    protected static final java.lang.String SUPPORTED_QUERY_ARGS = joinNewline(android.provider.DocumentsContract.QUERY_ARG_DISPLAY_NAME, android.provider.DocumentsContract.QUERY_ARG_FILE_SIZE_OVER, android.provider.DocumentsContract.QUERY_ARG_LAST_MODIFIED_AFTER, android.provider.DocumentsContract.QUERY_ARG_MIME_TYPES);
    private static final java.lang.String TAG = "FileSystemProvider";
    private java.lang.String[] mDefaultProjection;
    private android.os.Handler mHandler;
    private final android.util.ArrayMap<java.io.File, com.android.internal.content.FileSystemProvider.DirectoryObserver> mObservers = new android.util.ArrayMap<>();

    protected abstract android.net.Uri buildNotificationUri(java.lang.String str);

    protected abstract java.lang.String getDocIdForFile(java.io.File file) throws java.io.FileNotFoundException;

    protected abstract java.io.File getFileForDocId(java.lang.String str, boolean z) throws java.io.FileNotFoundException;

    private static java.lang.String joinNewline(java.lang.String... strArr) {
        return android.text.TextUtils.join("\n", strArr);
    }

    protected void onDocIdChanged(java.lang.String str) {
    }

    protected void onDocIdDeleted(java.lang.String str) {
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        throw new java.lang.UnsupportedOperationException("Subclass should override this and call onCreate(defaultDocumentProjection)");
    }

    protected void onCreate(java.lang.String[] strArr) {
        this.mHandler = new android.os.Handler();
        this.mDefaultProjection = strArr;
    }

    @Override // android.provider.DocumentsProvider
    public boolean isChildDocument(java.lang.String str, java.lang.String str2) {
        try {
            return android.os.FileUtils.contains(getFileForDocId(str).getCanonicalFile(), getFileForDocId(str2).getCanonicalFile());
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("Failed to determine if " + str2 + " is child of " + str + ": " + e);
        }
    }

    /* JADX WARN: Not initialized variable reg: 5, insn: 0x00ba: MOVE (r4 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]), block:B:46:0x00ba */
    @Override // android.provider.DocumentsProvider
    public android.os.Bundle getDocumentMetadata(java.lang.String str) throws java.io.FileNotFoundException {
        java.lang.AutoCloseable autoCloseable;
        java.io.FileInputStream fileInputStream;
        android.os.Bundle bundle;
        java.io.File fileForDocId = getFileForDocId(str);
        if (!fileForDocId.exists()) {
            throw new java.io.FileNotFoundException("Can't find the file for documentId: " + str);
        }
        java.lang.String documentType = getDocumentType(str);
        java.lang.AutoCloseable autoCloseable2 = null;
        if (android.provider.DocumentsContract.Document.MIME_TYPE_DIR.equals(documentType)) {
            final android.system.Int64Ref int64Ref = new android.system.Int64Ref(0L);
            final android.system.Int64Ref int64Ref2 = new android.system.Int64Ref(0L);
            try {
                java.nio.file.Files.walkFileTree(java.nio.file.FileSystems.getDefault().getPath(fileForDocId.getAbsolutePath(), new java.lang.String[0]), new java.nio.file.FileVisitor<java.nio.file.Path>() { // from class: com.android.internal.content.FileSystemProvider.1
                    @Override // java.nio.file.FileVisitor
                    public java.nio.file.FileVisitResult preVisitDirectory(java.nio.file.Path path, java.nio.file.attribute.BasicFileAttributes basicFileAttributes) {
                        return java.nio.file.FileVisitResult.CONTINUE;
                    }

                    @Override // java.nio.file.FileVisitor
                    public java.nio.file.FileVisitResult visitFile(java.nio.file.Path path, java.nio.file.attribute.BasicFileAttributes basicFileAttributes) {
                        int64Ref.value++;
                        int64Ref2.value += basicFileAttributes.size();
                        return java.nio.file.FileVisitResult.CONTINUE;
                    }

                    @Override // java.nio.file.FileVisitor
                    public java.nio.file.FileVisitResult visitFileFailed(java.nio.file.Path path, java.io.IOException iOException) {
                        return java.nio.file.FileVisitResult.CONTINUE;
                    }

                    @Override // java.nio.file.FileVisitor
                    public java.nio.file.FileVisitResult postVisitDirectory(java.nio.file.Path path, java.io.IOException iOException) {
                        return java.nio.file.FileVisitResult.CONTINUE;
                    }
                });
                android.os.Bundle bundle2 = new android.os.Bundle();
                bundle2.putLong(android.provider.DocumentsContract.METADATA_TREE_COUNT, int64Ref.value);
                bundle2.putLong(android.provider.DocumentsContract.METADATA_TREE_SIZE, int64Ref2.value);
                return bundle2;
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "An error occurred retrieving the metadata", e);
                return null;
            }
        }
        if (!fileForDocId.isFile()) {
            android.util.Log.w(TAG, "Can't stream non-regular file. Returning empty metadata.");
            return null;
        }
        if (!fileForDocId.canRead()) {
            android.util.Log.w(TAG, "Can't stream non-readable file. Returning empty metadata.");
            return null;
        }
        try {
            if (!android.provider.MetadataReader.isSupportedMimeType(documentType)) {
                android.util.Log.w(TAG, "Unsupported type " + documentType + ". Returning empty metadata.");
                return null;
            }
            try {
                bundle = new android.os.Bundle();
                fileInputStream = new java.io.FileInputStream(fileForDocId.getAbsolutePath());
            } catch (java.io.IOException e2) {
                e = e2;
                fileInputStream = null;
            } catch (java.lang.Throwable th) {
                th = th;
                libcore.io.IoUtils.closeQuietly(autoCloseable2);
                throw th;
            }
            try {
                android.provider.MetadataReader.getMetadata(bundle, fileInputStream, documentType, null);
                libcore.io.IoUtils.closeQuietly(fileInputStream);
                return bundle;
            } catch (java.io.IOException e3) {
                e = e3;
                android.util.Log.e(TAG, "An error occurred retrieving the metadata", e);
                libcore.io.IoUtils.closeQuietly(fileInputStream);
                return null;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            autoCloseable2 = autoCloseable;
        }
    }

    protected final java.util.List<java.lang.String> findDocumentPath(java.io.File file, java.io.File file2) throws java.io.FileNotFoundException {
        if (!file2.exists()) {
            throw new java.io.FileNotFoundException(file2 + " is not found.");
        }
        if (!android.os.FileUtils.contains(file, file2)) {
            throw new java.io.FileNotFoundException(file2 + " is not found under " + file);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (file2 != null && android.os.FileUtils.contains(file, file2)) {
            arrayList.add(0, getDocIdForFile(file2));
            file2 = file2.getParentFile();
        }
        return arrayList;
    }

    @Override // android.provider.DocumentsProvider
    public java.lang.String createDocument(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.FileNotFoundException {
        java.lang.String docIdForFile;
        java.lang.String buildValidFatFilename = android.os.FileUtils.buildValidFatFilename(str3);
        java.io.File fileForDocId = getFileForDocId(str);
        if (!fileForDocId.isDirectory()) {
            throw new java.lang.IllegalArgumentException("Parent document isn't a directory");
        }
        java.io.File buildUniqueFile = android.os.FileUtils.buildUniqueFile(fileForDocId, str2, buildValidFatFilename);
        if (android.provider.DocumentsContract.Document.MIME_TYPE_DIR.equals(str2)) {
            if (!buildUniqueFile.mkdir()) {
                throw new java.lang.IllegalStateException("Failed to mkdir " + buildUniqueFile);
            }
            docIdForFile = getDocIdForFile(buildUniqueFile);
            onDocIdChanged(docIdForFile);
        } else {
            try {
                if (!buildUniqueFile.createNewFile()) {
                    throw new java.lang.IllegalStateException("Failed to touch " + buildUniqueFile);
                }
                docIdForFile = getDocIdForFile(buildUniqueFile);
                onDocIdChanged(docIdForFile);
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalStateException("Failed to touch " + buildUniqueFile + ": " + e);
            }
        }
        updateMediaStore(getContext(), buildUniqueFile);
        return docIdForFile;
    }

    @Override // android.provider.DocumentsProvider
    public java.lang.String renameDocument(java.lang.String str, java.lang.String str2) throws java.io.FileNotFoundException {
        java.lang.String buildValidFatFilename = android.os.FileUtils.buildValidFatFilename(str2);
        java.io.File fileForDocId = getFileForDocId(str);
        java.io.File fileForDocId2 = getFileForDocId(str, true);
        java.io.File buildUniqueFile = android.os.FileUtils.buildUniqueFile(fileForDocId.getParentFile(), buildValidFatFilename);
        if (!fileForDocId.renameTo(buildUniqueFile)) {
            throw new java.lang.IllegalStateException("Failed to rename to " + buildUniqueFile);
        }
        java.lang.String docIdForFile = getDocIdForFile(buildUniqueFile);
        onDocIdChanged(str);
        onDocIdDeleted(str);
        onDocIdChanged(docIdForFile);
        java.io.File fileForDocId3 = getFileForDocId(docIdForFile, true);
        updateMediaStore(getContext(), fileForDocId2);
        updateMediaStore(getContext(), fileForDocId3);
        if (!android.text.TextUtils.equals(str, docIdForFile)) {
            return docIdForFile;
        }
        return null;
    }

    @Override // android.provider.DocumentsProvider
    public java.lang.String moveDocument(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.FileNotFoundException {
        java.io.File fileForDocId = getFileForDocId(str);
        java.io.File file = new java.io.File(getFileForDocId(str3), fileForDocId.getName());
        java.io.File fileForDocId2 = getFileForDocId(str, true);
        if (file.exists()) {
            throw new java.lang.IllegalStateException("Already exists " + file);
        }
        if (!fileForDocId.renameTo(file)) {
            throw new java.lang.IllegalStateException("Failed to move to " + file);
        }
        java.lang.String docIdForFile = getDocIdForFile(file);
        onDocIdChanged(str);
        onDocIdDeleted(str);
        onDocIdChanged(docIdForFile);
        updateMediaStore(getContext(), fileForDocId2);
        updateMediaStore(getContext(), getFileForDocId(docIdForFile, true));
        return docIdForFile;
    }

    private static void updateMediaStore(android.content.Context context, java.io.File file) {
        if (file != null) {
            android.content.ContentResolver contentResolver = context.getContentResolver();
            if (!file.isDirectory() && file.getName().toLowerCase(java.util.Locale.ROOT).endsWith(".nomedia")) {
                android.provider.MediaStore.scanFile(contentResolver, file.getParentFile());
            } else {
                android.provider.MediaStore.scanFile(contentResolver, file);
            }
        }
    }

    @Override // android.provider.DocumentsProvider
    public void deleteDocument(java.lang.String str) throws java.io.FileNotFoundException {
        java.io.File fileForDocId = getFileForDocId(str);
        java.io.File fileForDocId2 = getFileForDocId(str, true);
        if (fileForDocId.isDirectory()) {
            android.os.FileUtils.deleteContents(fileForDocId);
        }
        if (fileForDocId.exists() && !fileForDocId.delete()) {
            throw new java.lang.IllegalStateException("Failed to delete " + fileForDocId);
        }
        onDocIdChanged(str);
        onDocIdDeleted(str);
        updateMediaStore(getContext(), fileForDocId2);
    }

    @Override // android.provider.DocumentsProvider
    public android.database.Cursor queryDocument(java.lang.String str, java.lang.String[] strArr) throws java.io.FileNotFoundException {
        android.database.MatrixCursor matrixCursor = new android.database.MatrixCursor(resolveProjection(strArr));
        includeFile(matrixCursor, str, null);
        return matrixCursor;
    }

    @Override // android.provider.DocumentsProvider
    public android.database.Cursor queryChildDocuments(java.lang.String str, java.lang.String[] strArr, java.lang.String str2) throws java.io.FileNotFoundException {
        return queryChildDocuments(str, strArr, str2, false);
    }

    @Override // android.provider.DocumentsProvider
    public final android.database.Cursor queryChildDocumentsForManage(java.lang.String str, java.lang.String[] strArr, java.lang.String str2) throws java.io.FileNotFoundException {
        return queryChildDocuments(str, strArr, str2, true);
    }

    protected android.database.Cursor queryChildDocuments(java.lang.String str, java.lang.String[] strArr, java.lang.String str2, boolean z) throws java.io.FileNotFoundException {
        java.io.File fileForDocId = getFileForDocId(str);
        com.android.internal.content.FileSystemProvider.DirectoryCursor directoryCursor = new com.android.internal.content.FileSystemProvider.DirectoryCursor(resolveProjection(strArr), str, fileForDocId);
        if (!fileForDocId.isDirectory()) {
            android.util.Log.w(TAG, '\"' + str + "\" is not a directory");
            return directoryCursor;
        }
        if (!z && shouldHideDocument(str)) {
            android.util.Log.w(TAG, "Queried directory \"" + str + "\" is hidden");
            return directoryCursor;
        }
        java.io.File[] listFilesOrEmpty = android.os.FileUtils.listFilesOrEmpty(fileForDocId);
        for (java.io.File file : listFilesOrEmpty) {
            if (z || !shouldHideDocument(file)) {
                includeFile(directoryCursor, null, file);
            }
        }
        return directoryCursor;
    }

    protected final android.database.Cursor querySearchDocuments(java.io.File file, java.lang.String[] strArr, java.util.Set<java.lang.String> set, android.os.Bundle bundle) throws java.io.FileNotFoundException {
        android.database.MatrixCursor matrixCursor = new android.database.MatrixCursor(resolveProjection(strArr));
        java.util.ArrayDeque arrayDeque = new java.util.ArrayDeque();
        arrayDeque.offer(file);
        while (!arrayDeque.isEmpty() && matrixCursor.getCount() < 23) {
            java.io.File file2 = (java.io.File) arrayDeque.poll();
            if (!shouldHideDocument(file2)) {
                if (file2.isDirectory()) {
                    for (java.io.File file3 : android.os.FileUtils.listFilesOrEmpty(file2)) {
                        arrayDeque.offer(file3);
                    }
                }
                if (!set.contains(file2.getAbsolutePath()) && matchSearchQueryArguments(file2, bundle)) {
                    includeFile(matrixCursor, null, file2);
                }
            }
        }
        java.lang.String[] handledQueryArguments = android.provider.DocumentsContract.getHandledQueryArguments(bundle);
        if (handledQueryArguments.length > 0) {
            android.os.Bundle bundle2 = new android.os.Bundle();
            bundle2.putStringArray(android.content.ContentResolver.EXTRA_HONORED_ARGS, handledQueryArguments);
            matrixCursor.setExtras(bundle2);
        }
        return matrixCursor;
    }

    @Override // android.provider.DocumentsProvider
    public java.lang.String getDocumentType(java.lang.String str) throws java.io.FileNotFoundException {
        return getDocumentType(str, getFileForDocId(str));
    }

    private java.lang.String getDocumentType(java.lang.String str, java.io.File file) throws java.io.FileNotFoundException {
        if (file.isDirectory()) {
            return android.provider.DocumentsContract.Document.MIME_TYPE_DIR;
        }
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            java.lang.String mimeTypeFromExtension = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(lastIndexOf + 1).toLowerCase());
            if (mimeTypeFromExtension != null) {
                return mimeTypeFromExtension;
            }
            return "application/octet-stream";
        }
        return "application/octet-stream";
    }

    @Override // android.provider.DocumentsProvider
    public android.os.ParcelFileDescriptor openDocument(final java.lang.String str, java.lang.String str2, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        java.io.File fileForDocId = getFileForDocId(str);
        final java.io.File fileForDocId2 = getFileForDocId(str, true);
        int parseMode = android.os.ParcelFileDescriptor.parseMode(str2);
        if (fileForDocId2 == null) {
            return android.os.ParcelFileDescriptor.open(fileForDocId, parseMode);
        }
        if (parseMode == 268435456) {
            return openFileForRead(fileForDocId2);
        }
        try {
            return android.os.ParcelFileDescriptor.open(fileForDocId, parseMode, this.mHandler, new android.os.ParcelFileDescriptor.OnCloseListener() { // from class: com.android.internal.content.FileSystemProvider$$ExternalSyntheticLambda0
                @Override // android.os.ParcelFileDescriptor.OnCloseListener
                public final void onClose(java.io.IOException iOException) {
                    com.android.internal.content.FileSystemProvider.this.lambda$openDocument$0(str, fileForDocId2, iOException);
                }
            });
        } catch (java.io.IOException e) {
            throw new java.io.FileNotFoundException("Failed to open for writing: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDocument$0(java.lang.String str, java.io.File file, java.io.IOException iOException) {
        onDocIdChanged(str);
        scanFile(file);
    }

    private android.os.ParcelFileDescriptor openFileForRead(java.io.File file) throws java.io.FileNotFoundException {
        android.net.Uri scanFile = android.provider.MediaStore.scanFile(getContext().getContentResolver(), file);
        if (scanFile == null) {
            android.util.Log.w(TAG, "Failed to retrieve media store URI for: " + file);
            return android.os.ParcelFileDescriptor.open(file, 268435456);
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("android.provider.extra.MEDIA_CAPABILITIES_UID", android.os.Binder.getCallingUid());
        android.content.res.AssetFileDescriptor openTypedAssetFileDescriptor = getContext().getContentResolver().openTypedAssetFileDescriptor(scanFile, "*/*", bundle);
        if (openTypedAssetFileDescriptor == null) {
            android.util.Log.w(TAG, "Failed to open with media_capabilities uid for URI: " + scanFile);
            return android.os.ParcelFileDescriptor.open(file, 268435456);
        }
        return openTypedAssetFileDescriptor.getParcelFileDescriptor();
    }

    private boolean matchSearchQueryArguments(java.io.File file, android.os.Bundle bundle) {
        java.lang.String mimeTypeFromExtension;
        if (file == null) {
            return false;
        }
        java.lang.String name = file.getName();
        if (file.isDirectory()) {
            mimeTypeFromExtension = android.provider.DocumentsContract.Document.MIME_TYPE_DIR;
        } else {
            int lastIndexOf = name.lastIndexOf(46);
            if (lastIndexOf < 0) {
                return false;
            }
            mimeTypeFromExtension = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(name.substring(lastIndexOf + 1));
        }
        return android.provider.DocumentsContract.matchSearchQueryArguments(bundle, name, mimeTypeFromExtension, file.lastModified(), file.length());
    }

    private void scanFile(java.io.File file) {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(android.net.Uri.fromFile(file));
        getContext().sendBroadcast(intent);
    }

    @Override // android.provider.DocumentsProvider
    public android.content.res.AssetFileDescriptor openDocumentThumbnail(java.lang.String str, android.graphics.Point point, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        return android.provider.DocumentsContract.openImageThumbnail(getFileForDocId(str));
    }

    protected android.database.MatrixCursor.RowBuilder includeFile(android.database.MatrixCursor matrixCursor, java.lang.String str, java.io.File file) throws java.io.FileNotFoundException {
        int i;
        java.lang.String[] columnNames = matrixCursor.getColumnNames();
        android.database.MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
        if (str == null) {
            str = getDocIdForFile(file);
        } else {
            file = getFileForDocId(str);
        }
        java.lang.String documentType = getDocumentType(str, file);
        newRow.add("document_id", str);
        newRow.add("mime_type", documentType);
        int indexOf = com.android.internal.util.ArrayUtils.indexOf(columnNames, "flags");
        if (indexOf != -1) {
            boolean equals = documentType.equals(android.provider.DocumentsContract.Document.MIME_TYPE_DIR);
            if (!file.canWrite()) {
                i = 0;
            } else if (equals) {
                i = 332;
            } else {
                i = 326;
            }
            if (equals && shouldBlockDirectoryFromTree(str)) {
                i |= 32768;
            }
            if (documentType.startsWith(com.android.internal.widget.MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
                i |= 1;
            }
            if (typeSupportsMetadata(documentType)) {
                i |= 16384;
            }
            newRow.add(indexOf, java.lang.Integer.valueOf(i));
        }
        int indexOf2 = com.android.internal.util.ArrayUtils.indexOf(columnNames, "_display_name");
        if (indexOf2 != -1) {
            newRow.add(indexOf2, file.getName());
        }
        int indexOf3 = com.android.internal.util.ArrayUtils.indexOf(columnNames, "last_modified");
        if (indexOf3 != -1) {
            long lastModified = file.lastModified();
            if (lastModified > 31536000000L) {
                newRow.add(indexOf3, java.lang.Long.valueOf(lastModified));
            }
        }
        int indexOf4 = com.android.internal.util.ArrayUtils.indexOf(columnNames, "_size");
        if (indexOf4 != -1) {
            newRow.add(indexOf4, java.lang.Long.valueOf(file.length()));
        }
        return newRow;
    }

    protected boolean shouldHideDocument(java.lang.String str) throws java.io.FileNotFoundException {
        return false;
    }

    protected final boolean shouldHideDocument(java.io.File file) throws java.io.FileNotFoundException {
        return shouldHideDocument(getDocIdForFile(file));
    }

    protected boolean shouldBlockDirectoryFromTree(java.lang.String str) throws java.io.FileNotFoundException {
        return false;
    }

    protected boolean typeSupportsMetadata(java.lang.String str) {
        return android.provider.MetadataReader.isSupportedMimeType(str) || android.provider.DocumentsContract.Document.MIME_TYPE_DIR.equals(str);
    }

    protected final java.io.File getFileForDocId(java.lang.String str) throws java.io.FileNotFoundException {
        return getFileForDocId(str, false);
    }

    private java.lang.String[] resolveProjection(java.lang.String[] strArr) {
        return strArr == null ? this.mDefaultProjection : strArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startObserving(java.io.File file, android.net.Uri uri, com.android.internal.content.FileSystemProvider.DirectoryCursor directoryCursor) {
        synchronized (this.mObservers) {
            com.android.internal.content.FileSystemProvider.DirectoryObserver directoryObserver = this.mObservers.get(file);
            if (directoryObserver == null) {
                directoryObserver = new com.android.internal.content.FileSystemProvider.DirectoryObserver(file, getContext().getContentResolver(), uri);
                directoryObserver.startWatching();
                this.mObservers.put(file, directoryObserver);
            }
            directoryObserver.mCursors.add(directoryCursor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopObserving(java.io.File file, com.android.internal.content.FileSystemProvider.DirectoryCursor directoryCursor) {
        synchronized (this.mObservers) {
            com.android.internal.content.FileSystemProvider.DirectoryObserver directoryObserver = this.mObservers.get(file);
            if (directoryObserver == null) {
                return;
            }
            directoryObserver.mCursors.remove(directoryCursor);
            if (directoryObserver.mCursors.size() == 0) {
                this.mObservers.remove(file);
                directoryObserver.stopWatching();
            }
        }
    }

    private static class DirectoryObserver extends android.os.FileObserver {
        private static final int NOTIFY_EVENTS = 4044;
        private final java.util.concurrent.CopyOnWriteArrayList<com.android.internal.content.FileSystemProvider.DirectoryCursor> mCursors;
        private final java.io.File mFile;
        private final android.net.Uri mNotifyUri;
        private final android.content.ContentResolver mResolver;

        DirectoryObserver(java.io.File file, android.content.ContentResolver contentResolver, android.net.Uri uri) {
            super(file.getAbsolutePath(), NOTIFY_EVENTS);
            this.mFile = file;
            this.mResolver = contentResolver;
            this.mNotifyUri = uri;
            this.mCursors = new java.util.concurrent.CopyOnWriteArrayList<>();
        }

        @Override // android.os.FileObserver
        public void onEvent(int i, java.lang.String str) {
            if ((i & NOTIFY_EVENTS) != 0) {
                java.util.Iterator<com.android.internal.content.FileSystemProvider.DirectoryCursor> it = this.mCursors.iterator();
                while (it.hasNext()) {
                    it.next().notifyChanged();
                }
                this.mResolver.notifyChange(this.mNotifyUri, (android.database.ContentObserver) null, false);
            }
        }

        public java.lang.String toString() {
            return "DirectoryObserver{file=" + this.mFile.getAbsolutePath() + ", ref=" + this.mCursors.size() + "}";
        }
    }

    private class DirectoryCursor extends android.database.MatrixCursor {
        private final java.io.File mFile;

        public DirectoryCursor(java.lang.String[] strArr, java.lang.String str, java.io.File file) {
            super(strArr);
            android.net.Uri buildNotificationUri = com.android.internal.content.FileSystemProvider.this.buildNotificationUri(str);
            setNotificationUris(com.android.internal.content.FileSystemProvider.this.getContext().getContentResolver(), java.util.Arrays.asList(buildNotificationUri), com.android.internal.content.FileSystemProvider.this.getContext().getContentResolver().getUserId(), false);
            this.mFile = file;
            com.android.internal.content.FileSystemProvider.this.startObserving(this.mFile, buildNotificationUri, this);
        }

        public void notifyChanged() {
            onChange(false);
        }

        @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            super.close();
            com.android.internal.content.FileSystemProvider.this.stopObserving(this.mFile, this);
        }
    }
}
