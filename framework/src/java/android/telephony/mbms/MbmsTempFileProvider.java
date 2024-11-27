package android.telephony.mbms;

/* loaded from: classes3.dex */
public class MbmsTempFileProvider extends android.content.ContentProvider {
    public static final java.lang.String TEMP_FILE_ROOT_PREF_FILE_NAME = "MbmsTempFileRootPrefs";
    public static final java.lang.String TEMP_FILE_ROOT_PREF_NAME = "mbms_temp_file_root";
    private java.lang.String mAuthority;
    private android.content.Context mContext;

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        throw new java.lang.UnsupportedOperationException("No querying supported");
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public java.lang.String getType(android.net.Uri uri) {
        return "application/octet-stream";
    }

    @Override // android.content.ContentProvider
    public android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues) {
        throw new java.lang.UnsupportedOperationException("No inserting supported");
    }

    @Override // android.content.ContentProvider
    public int delete(android.net.Uri uri, java.lang.String str, java.lang.String[] strArr) {
        throw new java.lang.UnsupportedOperationException("No deleting supported");
    }

    @Override // android.content.ContentProvider
    public int update(android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) {
        throw new java.lang.UnsupportedOperationException("No updating supported");
    }

    @Override // android.content.ContentProvider
    public android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str) throws java.io.FileNotFoundException {
        return android.os.ParcelFileDescriptor.open(getFileForUri(this.mContext, this.mAuthority, uri), android.os.ParcelFileDescriptor.parseMode(str));
    }

    @Override // android.content.ContentProvider
    public void attachInfo(android.content.Context context, android.content.pm.ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) {
            throw new java.lang.SecurityException("Provider must not be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new java.lang.SecurityException("Provider must grant uri permissions");
        }
        this.mAuthority = providerInfo.authority;
        this.mContext = context;
    }

    public static android.net.Uri getUriForFile(android.content.Context context, java.lang.String str, java.io.File file) {
        java.lang.String substring;
        try {
            java.lang.String canonicalPath = file.getCanonicalPath();
            java.io.File embmsTempFileDir = getEmbmsTempFileDir(context);
            if (!android.telephony.mbms.MbmsUtils.isContainedIn(embmsTempFileDir, file)) {
                throw new java.lang.IllegalArgumentException("File " + file + " is not contained in the temp file directory, which is " + embmsTempFileDir);
            }
            try {
                java.lang.String canonicalPath2 = embmsTempFileDir.getCanonicalPath();
                if (canonicalPath2.endsWith("/")) {
                    substring = canonicalPath.substring(canonicalPath2.length());
                } else {
                    substring = canonicalPath.substring(canonicalPath2.length() + 1);
                }
                return new android.net.Uri.Builder().scheme("content").authority(str).encodedPath(android.net.Uri.encode(substring)).build();
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException("Could not get canonical path for temp file root dir " + embmsTempFileDir);
            }
        } catch (java.io.IOException e2) {
            throw new java.lang.IllegalArgumentException("Could not get canonical path for file " + file);
        }
    }

    public static java.io.File getFileForUri(android.content.Context context, java.lang.String str, android.net.Uri uri) throws java.io.FileNotFoundException {
        if (!"content".equals(uri.getScheme())) {
            throw new java.lang.IllegalArgumentException("Uri must have scheme content");
        }
        if (!java.util.Objects.equals(str, uri.getAuthority())) {
            throw new java.lang.IllegalArgumentException("Uri does not have a matching authority: " + str + ", " + uri.getAuthority());
        }
        java.lang.String decode = android.net.Uri.decode(uri.getEncodedPath());
        try {
            java.io.File canonicalFile = getEmbmsTempFileDir(context).getCanonicalFile();
            java.io.File canonicalFile2 = new java.io.File(canonicalFile, decode).getCanonicalFile();
            if (!canonicalFile2.getPath().startsWith(canonicalFile.getPath())) {
                throw new java.lang.SecurityException("Resolved path jumped beyond configured root");
            }
            return canonicalFile2;
        } catch (java.io.IOException e) {
            throw new java.io.FileNotFoundException("Could not resolve paths");
        }
    }

    public static java.io.File getEmbmsTempFileDir(android.content.Context context) {
        java.lang.String string = context.getSharedPreferences(TEMP_FILE_ROOT_PREF_FILE_NAME, 0).getString(TEMP_FILE_ROOT_PREF_NAME, null);
        try {
            if (string != null) {
                return new java.io.File(string).getCanonicalFile();
            }
            return new java.io.File(context.getFilesDir(), android.telephony.MbmsDownloadSession.DEFAULT_TOP_LEVEL_TEMP_DIRECTORY).getCanonicalFile();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Unable to canonicalize temp file root path " + e);
        }
    }
}
