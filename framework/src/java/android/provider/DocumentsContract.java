package android.provider;

/* loaded from: classes3.dex */
public final class DocumentsContract {

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_DOCUMENT_ROOT_SETTINGS = "android.provider.action.DOCUMENT_ROOT_SETTINGS";
    public static final java.lang.String ACTION_DOCUMENT_SETTINGS = "android.provider.action.DOCUMENT_SETTINGS";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_MANAGE_DOCUMENT = "android.provider.action.MANAGE_DOCUMENT";

    @android.annotation.SystemApi
    public static final java.lang.String DOWNLOADS_PROVIDER_AUTHORITY = "downloads";
    public static final java.lang.String EXTERNAL_STORAGE_PRIMARY_EMULATED_ROOT_ID = "primary";

    @android.annotation.SystemApi
    public static final java.lang.String EXTERNAL_STORAGE_PROVIDER_AUTHORITY = "com.android.externalstorage.documents";
    public static final java.lang.String EXTRA_ERROR = "error";
    public static final java.lang.String EXTRA_EXCLUDE_SELF = "android.provider.extra.EXCLUDE_SELF";
    public static final java.lang.String EXTRA_INFO = "info";
    public static final java.lang.String EXTRA_INITIAL_URI = "android.provider.extra.INITIAL_URI";
    public static final java.lang.String EXTRA_LOADING = "loading";
    public static final java.lang.String EXTRA_OPTIONS = "options";
    public static final java.lang.String EXTRA_ORIENTATION = "android.provider.extra.ORIENTATION";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PACKAGE_NAME = "android.intent.extra.PACKAGE_NAME";
    public static final java.lang.String EXTRA_PARENT_URI = "parentUri";
    public static final java.lang.String EXTRA_PROMPT = "android.provider.extra.PROMPT";
    public static final java.lang.String EXTRA_RESULT = "result";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_SHOW_ADVANCED = "android.provider.extra.SHOW_ADVANCED";
    public static final java.lang.String EXTRA_TARGET_URI = "android.content.extra.TARGET_URI";
    public static final java.lang.String EXTRA_URI = "uri";
    public static final java.lang.String EXTRA_URI_PERMISSIONS = "uriPermissions";
    public static final java.lang.String METADATA_EXIF = "android:documentExif";
    public static final java.lang.String METADATA_TREE_COUNT = "android:metadataTreeCount";
    public static final java.lang.String METADATA_TREE_SIZE = "android:metadataTreeSize";
    public static final java.lang.String METADATA_TYPES = "android:documentMetadataTypes";
    public static final java.lang.String METHOD_COPY_DOCUMENT = "android:copyDocument";
    public static final java.lang.String METHOD_CREATE_DOCUMENT = "android:createDocument";
    public static final java.lang.String METHOD_CREATE_WEB_LINK_INTENT = "android:createWebLinkIntent";
    public static final java.lang.String METHOD_DELETE_DOCUMENT = "android:deleteDocument";
    public static final java.lang.String METHOD_EJECT_ROOT = "android:ejectRoot";
    public static final java.lang.String METHOD_FIND_DOCUMENT_PATH = "android:findDocumentPath";
    public static final java.lang.String METHOD_GET_DOCUMENT_METADATA = "android:getDocumentMetadata";
    public static final java.lang.String METHOD_IS_CHILD_DOCUMENT = "android:isChildDocument";
    public static final java.lang.String METHOD_MOVE_DOCUMENT = "android:moveDocument";
    public static final java.lang.String METHOD_REMOVE_DOCUMENT = "android:removeDocument";
    public static final java.lang.String METHOD_RENAME_DOCUMENT = "android:renameDocument";
    public static final java.lang.String PACKAGE_DOCUMENTS_UI = "com.android.documentsui";
    private static final java.lang.String PARAM_MANAGE = "manage";
    private static final java.lang.String PARAM_QUERY = "query";
    private static final java.lang.String PATH_CHILDREN = "children";
    private static final java.lang.String PATH_DOCUMENT = "document";
    private static final java.lang.String PATH_RECENT = "recent";
    private static final java.lang.String PATH_ROOT = "root";
    private static final java.lang.String PATH_SEARCH = "search";
    private static final java.lang.String PATH_TREE = "tree";
    public static final java.lang.String PROVIDER_INTERFACE = "android.content.action.DOCUMENTS_PROVIDER";
    public static final java.lang.String QUERY_ARG_DISPLAY_NAME = "android:query-arg-display-name";
    public static final java.lang.String QUERY_ARG_EXCLUDE_MEDIA = "android:query-arg-exclude-media";
    public static final java.lang.String QUERY_ARG_FILE_SIZE_OVER = "android:query-arg-file-size-over";
    public static final java.lang.String QUERY_ARG_LAST_MODIFIED_AFTER = "android:query-arg-last-modified-after";
    public static final java.lang.String QUERY_ARG_MIME_TYPES = "android:query-arg-mime-types";
    private static final java.lang.String TAG = "DocumentsContract";

    private DocumentsContract() {
    }

    public static final class Document {
        public static final java.lang.String COLUMN_DISPLAY_NAME = "_display_name";
        public static final java.lang.String COLUMN_DOCUMENT_ID = "document_id";
        public static final java.lang.String COLUMN_FLAGS = "flags";
        public static final java.lang.String COLUMN_ICON = "icon";
        public static final java.lang.String COLUMN_LAST_MODIFIED = "last_modified";
        public static final java.lang.String COLUMN_MIME_TYPE = "mime_type";
        public static final java.lang.String COLUMN_SIZE = "_size";
        public static final java.lang.String COLUMN_SUMMARY = "summary";
        public static final int FLAG_DIR_BLOCKS_OPEN_DOCUMENT_TREE = 32768;
        public static final int FLAG_DIR_PREFERS_GRID = 16;
        public static final int FLAG_DIR_PREFERS_LAST_MODIFIED = 32;
        public static final int FLAG_DIR_SUPPORTS_CREATE = 8;
        public static final int FLAG_PARTIAL = 8192;
        public static final int FLAG_SUPPORTS_COPY = 128;
        public static final int FLAG_SUPPORTS_DELETE = 4;
        public static final int FLAG_SUPPORTS_METADATA = 16384;
        public static final int FLAG_SUPPORTS_MOVE = 256;
        public static final int FLAG_SUPPORTS_REMOVE = 1024;
        public static final int FLAG_SUPPORTS_RENAME = 64;
        public static final int FLAG_SUPPORTS_SETTINGS = 2048;
        public static final int FLAG_SUPPORTS_THUMBNAIL = 1;
        public static final int FLAG_SUPPORTS_WRITE = 2;
        public static final int FLAG_VIRTUAL_DOCUMENT = 512;
        public static final int FLAG_WEB_LINKABLE = 4096;
        public static final java.lang.String MIME_TYPE_DIR = "vnd.android.document/directory";

        private Document() {
        }
    }

    public static final class Root {
        public static final java.lang.String COLUMN_AVAILABLE_BYTES = "available_bytes";
        public static final java.lang.String COLUMN_CAPACITY_BYTES = "capacity_bytes";
        public static final java.lang.String COLUMN_DOCUMENT_ID = "document_id";
        public static final java.lang.String COLUMN_FLAGS = "flags";
        public static final java.lang.String COLUMN_ICON = "icon";
        public static final java.lang.String COLUMN_MIME_TYPES = "mime_types";
        public static final java.lang.String COLUMN_QUERY_ARGS = "query_args";
        public static final java.lang.String COLUMN_ROOT_ID = "root_id";
        public static final java.lang.String COLUMN_SUMMARY = "summary";
        public static final java.lang.String COLUMN_TITLE = "title";

        @android.annotation.SystemApi
        public static final int FLAG_ADVANCED = 65536;
        public static final int FLAG_EMPTY = 64;

        @android.annotation.SystemApi
        public static final int FLAG_HAS_SETTINGS = 131072;
        public static final int FLAG_LOCAL_ONLY = 2;

        @android.annotation.SystemApi
        public static final int FLAG_REMOVABLE_SD = 262144;

        @android.annotation.SystemApi
        public static final int FLAG_REMOVABLE_USB = 524288;
        public static final int FLAG_SUPPORTS_CREATE = 1;
        public static final int FLAG_SUPPORTS_EJECT = 32;
        public static final int FLAG_SUPPORTS_IS_CHILD = 16;
        public static final int FLAG_SUPPORTS_RECENTS = 4;
        public static final int FLAG_SUPPORTS_SEARCH = 8;
        public static final java.lang.String MIME_TYPE_ITEM = "vnd.android.document/root";

        private Root() {
        }
    }

    public static android.net.Uri buildRootsUri(java.lang.String str) {
        return new android.net.Uri.Builder().scheme("content").authority(str).appendPath(PATH_ROOT).build();
    }

    public static android.net.Uri buildRootUri(java.lang.String str, java.lang.String str2) {
        return new android.net.Uri.Builder().scheme("content").authority(str).appendPath(PATH_ROOT).appendPath(str2).build();
    }

    public static android.net.Uri buildRecentDocumentsUri(java.lang.String str, java.lang.String str2) {
        return new android.net.Uri.Builder().scheme("content").authority(str).appendPath(PATH_ROOT).appendPath(str2).appendPath("recent").build();
    }

    public static android.net.Uri buildTreeDocumentUri(java.lang.String str, java.lang.String str2) {
        return new android.net.Uri.Builder().scheme("content").authority(str).appendPath(PATH_TREE).appendPath(str2).build();
    }

    public static android.net.Uri buildDocumentUri(java.lang.String str, java.lang.String str2) {
        return getBaseDocumentUriBuilder(str).appendPath(str2).build();
    }

    @android.annotation.SystemApi
    public static android.net.Uri buildDocumentUriAsUser(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        return android.content.ContentProvider.maybeAddUserId(buildDocumentUri(str, str2), userHandle.getIdentifier());
    }

    public static android.net.Uri buildBaseDocumentUri(java.lang.String str) {
        return getBaseDocumentUriBuilder(str).build();
    }

    private static android.net.Uri.Builder getBaseDocumentUriBuilder(java.lang.String str) {
        return new android.net.Uri.Builder().scheme("content").authority(str).appendPath(PATH_DOCUMENT);
    }

    public static android.net.Uri buildDocumentUriUsingTree(android.net.Uri uri, java.lang.String str) {
        return new android.net.Uri.Builder().scheme("content").authority(uri.getAuthority()).appendPath(PATH_TREE).appendPath(getTreeDocumentId(uri)).appendPath(PATH_DOCUMENT).appendPath(str).build();
    }

    public static android.net.Uri buildDocumentUriMaybeUsingTree(android.net.Uri uri, java.lang.String str) {
        if (isTreeUri(uri)) {
            return buildDocumentUriUsingTree(uri, str);
        }
        return buildDocumentUri(uri.getAuthority(), str);
    }

    public static android.net.Uri buildChildDocumentsUri(java.lang.String str, java.lang.String str2) {
        return new android.net.Uri.Builder().scheme("content").authority(str).appendPath(PATH_DOCUMENT).appendPath(str2).appendPath(PATH_CHILDREN).build();
    }

    public static android.net.Uri buildChildDocumentsUriUsingTree(android.net.Uri uri, java.lang.String str) {
        return new android.net.Uri.Builder().scheme("content").authority(uri.getAuthority()).appendPath(PATH_TREE).appendPath(getTreeDocumentId(uri)).appendPath(PATH_DOCUMENT).appendPath(str).appendPath(PATH_CHILDREN).build();
    }

    public static android.net.Uri buildSearchDocumentsUri(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return new android.net.Uri.Builder().scheme("content").authority(str).appendPath(PATH_ROOT).appendPath(str2).appendPath("search").appendQueryParameter("query", str3).build();
    }

    public static boolean matchSearchQueryArguments(android.os.Bundle bundle, java.lang.String str, java.lang.String str2, long j, long j2) {
        if (bundle == null) {
            return true;
        }
        java.lang.String string = bundle.getString(QUERY_ARG_DISPLAY_NAME, "");
        if (!string.isEmpty() && !str.toLowerCase().contains(string.toLowerCase())) {
            return false;
        }
        long j3 = bundle.getLong(QUERY_ARG_FILE_SIZE_OVER, -1L);
        if (j3 != -1 && j2 < j3) {
            return false;
        }
        long j4 = bundle.getLong(QUERY_ARG_LAST_MODIFIED_AFTER, -1L);
        if (j4 != -1 && j < j4) {
            return false;
        }
        java.lang.String[] stringArray = bundle.getStringArray(QUERY_ARG_MIME_TYPES);
        if (stringArray == null || stringArray.length <= 0) {
            return true;
        }
        java.lang.String normalizeMimeType = android.content.Intent.normalizeMimeType(str2);
        for (java.lang.String str3 : stringArray) {
            if (android.content.MimeTypeFilter.matches(normalizeMimeType, android.content.Intent.normalizeMimeType(str3))) {
                return true;
            }
        }
        return false;
    }

    public static java.lang.String[] getHandledQueryArguments(android.os.Bundle bundle) {
        if (bundle == null) {
            return new java.lang.String[0];
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (bundle.keySet().contains(QUERY_ARG_EXCLUDE_MEDIA)) {
            arrayList.add(QUERY_ARG_EXCLUDE_MEDIA);
        }
        if (bundle.keySet().contains(QUERY_ARG_DISPLAY_NAME)) {
            arrayList.add(QUERY_ARG_DISPLAY_NAME);
        }
        if (bundle.keySet().contains(QUERY_ARG_FILE_SIZE_OVER)) {
            arrayList.add(QUERY_ARG_FILE_SIZE_OVER);
        }
        if (bundle.keySet().contains(QUERY_ARG_LAST_MODIFIED_AFTER)) {
            arrayList.add(QUERY_ARG_LAST_MODIFIED_AFTER);
        }
        if (bundle.keySet().contains(QUERY_ARG_MIME_TYPES)) {
            arrayList.add(QUERY_ARG_MIME_TYPES);
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }

    public static boolean isDocumentUri(android.content.Context context, android.net.Uri uri) {
        if (isContentUri(uri) && isDocumentsProvider(context, uri.getAuthority())) {
            java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
            if (pathSegments.size() == 2) {
                return PATH_DOCUMENT.equals(pathSegments.get(0));
            }
            return pathSegments.size() == 4 && PATH_TREE.equals(pathSegments.get(0)) && PATH_DOCUMENT.equals(pathSegments.get(2));
        }
        return false;
    }

    public static boolean isRootsUri(android.content.Context context, android.net.Uri uri) {
        com.android.internal.util.Preconditions.checkNotNull(context, "context can not be null");
        return isRootUri(context, uri, 1);
    }

    public static boolean isRootUri(android.content.Context context, android.net.Uri uri) {
        com.android.internal.util.Preconditions.checkNotNull(context, "context can not be null");
        return isRootUri(context, uri, 2);
    }

    public static boolean isContentUri(android.net.Uri uri) {
        return uri != null && "content".equals(uri.getScheme());
    }

    public static boolean isTreeUri(android.net.Uri uri) {
        java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
        return pathSegments.size() >= 2 && PATH_TREE.equals(pathSegments.get(0));
    }

    private static boolean isRootUri(android.content.Context context, android.net.Uri uri, int i) {
        if (!isContentUri(uri) || !isDocumentsProvider(context, uri.getAuthority())) {
            return false;
        }
        java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
        return pathSegments.size() == i && PATH_ROOT.equals(pathSegments.get(0));
    }

    private static boolean isDocumentsProvider(android.content.Context context, java.lang.String str) {
        java.util.Iterator<android.content.pm.ResolveInfo> it = context.getPackageManager().queryIntentContentProviders(new android.content.Intent(PROVIDER_INTERFACE), 0).iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().providerInfo.authority)) {
                return true;
            }
        }
        return false;
    }

    public static java.lang.String getRootId(android.net.Uri uri) {
        java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() >= 2 && PATH_ROOT.equals(pathSegments.get(0))) {
            return pathSegments.get(1);
        }
        throw new java.lang.IllegalArgumentException("Invalid URI: " + uri);
    }

    public static java.lang.String getDocumentId(android.net.Uri uri) {
        java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() >= 2 && PATH_DOCUMENT.equals(pathSegments.get(0))) {
            return pathSegments.get(1);
        }
        if (pathSegments.size() >= 4 && PATH_TREE.equals(pathSegments.get(0)) && PATH_DOCUMENT.equals(pathSegments.get(2))) {
            return pathSegments.get(3);
        }
        throw new java.lang.IllegalArgumentException("Invalid URI: " + uri);
    }

    public static java.lang.String getTreeDocumentId(android.net.Uri uri) {
        java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() >= 2 && PATH_TREE.equals(pathSegments.get(0))) {
            return pathSegments.get(1);
        }
        throw new java.lang.IllegalArgumentException("Invalid URI: " + uri);
    }

    public static java.lang.String getSearchDocumentsQuery(android.net.Uri uri) {
        return uri.getQueryParameter("query");
    }

    public static java.lang.String getSearchDocumentsQuery(android.os.Bundle bundle) {
        com.android.internal.util.Preconditions.checkNotNull(bundle, "bundle can not be null");
        return bundle.getString(QUERY_ARG_DISPLAY_NAME, "");
    }

    @android.annotation.SystemApi
    public static android.net.Uri setManageMode(android.net.Uri uri) {
        com.android.internal.util.Preconditions.checkNotNull(uri, "uri can not be null");
        return uri.buildUpon().appendQueryParameter(PARAM_MANAGE, "true").build();
    }

    @android.annotation.SystemApi
    public static boolean isManageMode(android.net.Uri uri) {
        com.android.internal.util.Preconditions.checkNotNull(uri, "uri can not be null");
        return uri.getBooleanQueryParameter(PARAM_MANAGE, false);
    }

    public static android.graphics.Bitmap getDocumentThumbnail(android.content.ContentResolver contentResolver, android.net.Uri uri, android.graphics.Point point, android.os.CancellationSignal cancellationSignal) throws java.io.FileNotFoundException {
        try {
            return android.content.ContentResolver.loadThumbnail(contentResolver, uri, new android.util.Size(point.x, point.y), cancellationSignal, 1);
        } catch (java.lang.Exception e) {
            if (!(e instanceof android.os.OperationCanceledException)) {
                android.util.Log.w(TAG, "Failed to load thumbnail for " + uri + ": " + e);
            }
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static android.net.Uri createDocument(android.content.ContentResolver contentResolver, android.net.Uri uri, java.lang.String str, java.lang.String str2) throws java.io.FileNotFoundException {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putString("mime_type", str);
            bundle.putString("_display_name", str2);
            return (android.net.Uri) contentResolver.call(uri.getAuthority(), METHOD_CREATE_DOCUMENT, (java.lang.String) null, bundle).getParcelable("uri", android.net.Uri.class);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to create document", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static boolean isChildDocument(android.content.ContentResolver contentResolver, android.net.Uri uri, android.net.Uri uri2) throws java.io.FileNotFoundException {
        com.android.internal.util.Preconditions.checkNotNull(contentResolver, "content can not be null");
        com.android.internal.util.Preconditions.checkNotNull(uri, "parentDocumentUri can not be null");
        com.android.internal.util.Preconditions.checkNotNull(uri2, "childDocumentUri can not be null");
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putParcelable(EXTRA_TARGET_URI, uri2);
            android.os.Bundle call = contentResolver.call(uri.getAuthority(), METHOD_IS_CHILD_DOCUMENT, (java.lang.String) null, bundle);
            if (call == null) {
                throw new android.os.RemoteException("Failed to get a response from isChildDocument query.");
            }
            if (!call.containsKey("result")) {
                throw new android.os.RemoteException("Response did not include result field..");
            }
            return call.getBoolean("result");
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to create document", e);
            rethrowIfNecessary(e);
            return false;
        }
    }

    public static android.net.Uri renameDocument(android.content.ContentResolver contentResolver, android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putString("_display_name", str);
            android.net.Uri uri2 = (android.net.Uri) contentResolver.call(uri.getAuthority(), METHOD_RENAME_DOCUMENT, (java.lang.String) null, bundle).getParcelable("uri", android.net.Uri.class);
            return uri2 != null ? uri2 : uri;
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to rename document", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static boolean deleteDocument(android.content.ContentResolver contentResolver, android.net.Uri uri) throws java.io.FileNotFoundException {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("uri", uri);
            contentResolver.call(uri.getAuthority(), METHOD_DELETE_DOCUMENT, (java.lang.String) null, bundle);
            return true;
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to delete document", e);
            rethrowIfNecessary(e);
            return false;
        }
    }

    public static android.net.Uri copyDocument(android.content.ContentResolver contentResolver, android.net.Uri uri, android.net.Uri uri2) throws java.io.FileNotFoundException {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putParcelable(EXTRA_TARGET_URI, uri2);
            return (android.net.Uri) contentResolver.call(uri.getAuthority(), METHOD_COPY_DOCUMENT, (java.lang.String) null, bundle).getParcelable("uri", android.net.Uri.class);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to copy document", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static android.net.Uri moveDocument(android.content.ContentResolver contentResolver, android.net.Uri uri, android.net.Uri uri2, android.net.Uri uri3) throws java.io.FileNotFoundException {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putParcelable(EXTRA_PARENT_URI, uri2);
            bundle.putParcelable(EXTRA_TARGET_URI, uri3);
            return (android.net.Uri) contentResolver.call(uri.getAuthority(), METHOD_MOVE_DOCUMENT, (java.lang.String) null, bundle).getParcelable("uri", android.net.Uri.class);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to move document", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static boolean removeDocument(android.content.ContentResolver contentResolver, android.net.Uri uri, android.net.Uri uri2) throws java.io.FileNotFoundException {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putParcelable(EXTRA_PARENT_URI, uri2);
            contentResolver.call(uri.getAuthority(), METHOD_REMOVE_DOCUMENT, (java.lang.String) null, bundle);
            return true;
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to remove document", e);
            rethrowIfNecessary(e);
            return false;
        }
    }

    public static void ejectRoot(android.content.ContentResolver contentResolver, android.net.Uri uri) {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("uri", uri);
            contentResolver.call(uri.getAuthority(), METHOD_EJECT_ROOT, (java.lang.String) null, bundle);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to eject", e);
        }
    }

    public static android.os.Bundle getDocumentMetadata(android.content.ContentResolver contentResolver, android.net.Uri uri) throws java.io.FileNotFoundException {
        com.android.internal.util.Preconditions.checkNotNull(contentResolver, "content can not be null");
        com.android.internal.util.Preconditions.checkNotNull(uri, "documentUri can not be null");
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("uri", uri);
            return contentResolver.call(uri.getAuthority(), METHOD_GET_DOCUMENT_METADATA, (java.lang.String) null, bundle);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to get document metadata");
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static android.provider.DocumentsContract.Path findDocumentPath(android.content.ContentResolver contentResolver, android.net.Uri uri) throws java.io.FileNotFoundException {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("uri", uri);
            return (android.provider.DocumentsContract.Path) contentResolver.call(uri.getAuthority(), METHOD_FIND_DOCUMENT_PATH, (java.lang.String) null, bundle).getParcelable("result", android.provider.DocumentsContract.Path.class);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to find path", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static android.content.IntentSender createWebLinkIntent(android.content.ContentResolver contentResolver, android.net.Uri uri, android.os.Bundle bundle) throws java.io.FileNotFoundException {
        try {
            android.os.Bundle bundle2 = new android.os.Bundle();
            bundle2.putParcelable("uri", uri);
            if (bundle != null) {
                bundle2.putBundle(EXTRA_OPTIONS, bundle);
            }
            return (android.content.IntentSender) contentResolver.call(uri.getAuthority(), METHOD_CREATE_WEB_LINK_INTENT, (java.lang.String) null, bundle2).getParcelable("result", android.content.IntentSender.class);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Failed to create a web link intent", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static android.content.res.AssetFileDescriptor openImageThumbnail(java.io.File file) throws java.io.FileNotFoundException {
        android.os.Bundle bundle;
        android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, 268435456);
        try {
            android.media.ExifInterface exifInterface = new android.media.ExifInterface(file.getAbsolutePath());
            long[] thumbnailRange = exifInterface.getThumbnailRange();
            if (thumbnailRange != null) {
                switch (exifInterface.getAttributeInt(android.media.ExifInterface.TAG_ORIENTATION, -1)) {
                    case 3:
                        android.os.Bundle bundle2 = new android.os.Bundle(1);
                        bundle2.putInt(EXTRA_ORIENTATION, 180);
                        bundle = bundle2;
                        break;
                    case 6:
                        android.os.Bundle bundle3 = new android.os.Bundle(1);
                        bundle3.putInt(EXTRA_ORIENTATION, 90);
                        bundle = bundle3;
                        break;
                    case 8:
                        android.os.Bundle bundle4 = new android.os.Bundle(1);
                        bundle4.putInt(EXTRA_ORIENTATION, 270);
                        bundle = bundle4;
                        break;
                    default:
                        bundle = null;
                        break;
                }
                return new android.content.res.AssetFileDescriptor(open, thumbnailRange[0], thumbnailRange[1], bundle);
            }
        } catch (java.io.IOException e) {
        }
        return new android.content.res.AssetFileDescriptor(open, 0L, -1L, null);
    }

    private static void rethrowIfNecessary(java.lang.Exception exc) throws java.io.FileNotFoundException {
        if (dalvik.system.VMRuntime.getRuntime().getTargetSdkVersion() >= 26) {
            if (exc instanceof android.os.ParcelableException) {
                ((android.os.ParcelableException) exc).maybeRethrow(java.io.FileNotFoundException.class);
            } else if (exc instanceof android.os.RemoteException) {
                ((android.os.RemoteException) exc).rethrowAsRuntimeException();
            } else if (exc instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) exc);
            }
        }
    }

    public static final class Path implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.provider.DocumentsContract.Path> CREATOR = new android.os.Parcelable.Creator<android.provider.DocumentsContract.Path>() { // from class: android.provider.DocumentsContract.Path.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.provider.DocumentsContract.Path createFromParcel(android.os.Parcel parcel) {
                return new android.provider.DocumentsContract.Path(parcel.readString(), parcel.createStringArrayList());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.provider.DocumentsContract.Path[] newArray(int i) {
                return new android.provider.DocumentsContract.Path[i];
            }
        };
        private final java.util.List<java.lang.String> mPath;
        private final java.lang.String mRootId;

        public Path(java.lang.String str, java.util.List<java.lang.String> list) {
            com.android.internal.util.Preconditions.checkCollectionNotEmpty(list, "path");
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "path");
            this.mRootId = str;
            this.mPath = list;
        }

        public java.lang.String getRootId() {
            return this.mRootId;
        }

        public java.util.List<java.lang.String> getPath() {
            return this.mPath;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof android.provider.DocumentsContract.Path)) {
                return false;
            }
            android.provider.DocumentsContract.Path path = (android.provider.DocumentsContract.Path) obj;
            if (java.util.Objects.equals(this.mRootId, path.mRootId) && java.util.Objects.equals(this.mPath, path.mPath)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mRootId, this.mPath);
        }

        public java.lang.String toString() {
            return "DocumentsContract.Path{rootId=" + this.mRootId + ", path=" + this.mPath + "}";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mRootId);
            parcel.writeStringList(this.mPath);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
