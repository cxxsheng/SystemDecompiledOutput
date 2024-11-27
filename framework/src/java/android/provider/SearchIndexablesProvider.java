package android.provider;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class SearchIndexablesProvider extends android.content.ContentProvider {
    private static final int MATCH_DYNAMIC_RAW_CODE = 6;
    private static final int MATCH_NON_INDEXABLE_KEYS_CODE = 3;
    private static final int MATCH_RAW_CODE = 2;
    private static final int MATCH_RES_CODE = 1;
    private static final int MATCH_SITE_MAP_PAIRS_CODE = 4;
    private static final int MATCH_SLICE_URI_PAIRS_CODE = 5;
    private static final java.lang.String TAG = "IndexablesProvider";
    private java.lang.String mAuthority;
    private android.content.UriMatcher mMatcher;

    public abstract android.database.Cursor queryNonIndexableKeys(java.lang.String[] strArr);

    public abstract android.database.Cursor queryRawData(java.lang.String[] strArr);

    public abstract android.database.Cursor queryXmlResources(java.lang.String[] strArr);

    @Override // android.content.ContentProvider
    public void attachInfo(android.content.Context context, android.content.pm.ProviderInfo providerInfo) {
        this.mAuthority = providerInfo.authority;
        this.mMatcher = new android.content.UriMatcher(-1);
        this.mMatcher.addURI(this.mAuthority, android.provider.SearchIndexablesContract.INDEXABLES_XML_RES_PATH, 1);
        this.mMatcher.addURI(this.mAuthority, android.provider.SearchIndexablesContract.INDEXABLES_RAW_PATH, 2);
        this.mMatcher.addURI(this.mAuthority, android.provider.SearchIndexablesContract.NON_INDEXABLES_KEYS_PATH, 3);
        this.mMatcher.addURI(this.mAuthority, android.provider.SearchIndexablesContract.SITE_MAP_PAIRS_PATH, 4);
        this.mMatcher.addURI(this.mAuthority, android.provider.SearchIndexablesContract.SLICE_URI_PAIRS_PATH, 5);
        this.mMatcher.addURI(this.mAuthority, android.provider.SearchIndexablesContract.DYNAMIC_INDEXABLES_RAW_PATH, 6);
        if (!providerInfo.exported) {
            throw new java.lang.SecurityException("Provider must be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new java.lang.SecurityException("Provider must grantUriPermissions");
        }
        if (!android.Manifest.permission.READ_SEARCH_INDEXABLES.equals(providerInfo.readPermission)) {
            throw new java.lang.SecurityException("Provider must be protected by READ_SEARCH_INDEXABLES");
        }
        super.attachInfo(context, providerInfo);
    }

    @Override // android.content.ContentProvider
    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        try {
            switch (this.mMatcher.match(uri)) {
                case 1:
                    return queryXmlResources(null);
                case 2:
                    return queryRawData(null);
                case 3:
                    return queryNonIndexableKeys(null);
                case 4:
                    return querySiteMapPairs();
                case 5:
                    return querySliceUriPairs();
                case 6:
                    return queryDynamicRawData(null);
                default:
                    throw new java.lang.UnsupportedOperationException("Unknown Uri " + uri);
            }
        } catch (java.lang.UnsupportedOperationException e) {
            throw e;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Provider querying exception:", e2);
            return null;
        }
    }

    public android.database.Cursor querySiteMapPairs() {
        return null;
    }

    public android.database.Cursor querySliceUriPairs() {
        return null;
    }

    public android.database.Cursor queryDynamicRawData(java.lang.String[] strArr) {
        return null;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public java.lang.String getType(android.net.Uri uri) {
        switch (this.mMatcher.match(uri)) {
            case 1:
                return android.provider.SearchIndexablesContract.XmlResource.MIME_TYPE;
            case 2:
            case 6:
                return android.provider.SearchIndexablesContract.RawData.MIME_TYPE;
            case 3:
                return android.provider.SearchIndexablesContract.NonIndexableKey.MIME_TYPE;
            case 4:
            case 5:
            default:
                throw new java.lang.IllegalArgumentException("Unknown URI " + uri);
        }
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
}
