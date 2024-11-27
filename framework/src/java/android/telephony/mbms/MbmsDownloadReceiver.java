package android.telephony.mbms;

/* loaded from: classes3.dex */
public class MbmsDownloadReceiver extends android.content.BroadcastReceiver {
    public static final java.lang.String DOWNLOAD_TOKEN_SUFFIX = ".download_token";
    private static final java.lang.String EMBMS_INTENT_PERMISSION = "android.permission.SEND_EMBMS_INTENTS";
    private static final java.lang.String LOG_TAG = "MbmsDownloadReceiver";
    private static final int MAX_TEMP_FILE_RETRIES = 5;
    public static final java.lang.String MBMS_FILE_PROVIDER_META_DATA_KEY = "mbms-file-provider-authority";

    @android.annotation.SystemApi
    public static final int RESULT_APP_NOTIFICATION_ERROR = 6;

    @android.annotation.SystemApi
    public static final int RESULT_BAD_TEMP_FILE_ROOT = 3;

    @android.annotation.SystemApi
    public static final int RESULT_DOWNLOAD_FINALIZATION_ERROR = 4;

    @android.annotation.SystemApi
    public static final int RESULT_INVALID_ACTION = 1;

    @android.annotation.SystemApi
    public static final int RESULT_MALFORMED_INTENT = 2;

    @android.annotation.SystemApi
    public static final int RESULT_OK = 0;

    @android.annotation.SystemApi
    public static final int RESULT_TEMP_FILE_GENERATION_ERROR = 5;
    private static final java.lang.String TEMP_FILE_STAGING_LOCATION = "staged_completed_files";
    private static final java.lang.String TEMP_FILE_SUFFIX = ".embms.temp";
    private java.lang.String mFileProviderAuthorityCache = null;
    private java.lang.String mMiddlewarePackageNameCache = null;

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        verifyPermissionIntegrity(context);
        if (!verifyIntentContents(context, intent)) {
            setResultCode(2);
            return;
        }
        if (!java.util.Objects.equals(intent.getStringExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_TEMP_FILE_ROOT), android.telephony.mbms.MbmsTempFileProvider.getEmbmsTempFileDir(context).getPath())) {
            setResultCode(3);
            return;
        }
        if (android.telephony.mbms.vendor.VendorUtils.ACTION_DOWNLOAD_RESULT_INTERNAL.equals(intent.getAction())) {
            moveDownloadedFile(context, intent);
            cleanupPostMove(context, intent);
        } else if (android.telephony.mbms.vendor.VendorUtils.ACTION_FILE_DESCRIPTOR_REQUEST.equals(intent.getAction())) {
            generateTempFiles(context, intent);
        } else if (android.telephony.mbms.vendor.VendorUtils.ACTION_CLEANUP.equals(intent.getAction())) {
            cleanupTempFiles(context, intent);
        } else {
            setResultCode(1);
        }
    }

    private boolean verifyIntentContents(android.content.Context context, android.content.Intent intent) {
        if (android.telephony.mbms.vendor.VendorUtils.ACTION_DOWNLOAD_RESULT_INTERNAL.equals(intent.getAction())) {
            if (!intent.hasExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_RESULT)) {
                android.util.Log.w(LOG_TAG, "Download result did not include a result code. Ignoring.");
                return false;
            }
            if (intent.hasExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_REQUEST)) {
                if (1 != intent.getIntExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_RESULT, 2)) {
                    return true;
                }
                if (!intent.hasExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_TEMP_FILE_ROOT)) {
                    android.util.Log.w(LOG_TAG, "Download result did not include the temp file root. Ignoring.");
                    return false;
                }
                if (!intent.hasExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_FILE_INFO)) {
                    android.util.Log.w(LOG_TAG, "Download result did not include the associated file info. Ignoring.");
                    return false;
                }
                if (intent.hasExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_FINAL_URI)) {
                    android.telephony.mbms.DownloadRequest downloadRequest = (android.telephony.mbms.DownloadRequest) intent.getParcelableExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_REQUEST, android.telephony.mbms.DownloadRequest.class);
                    java.io.File file = new java.io.File(android.telephony.mbms.MbmsUtils.getEmbmsTempFileDirForService(context, downloadRequest.getFileServiceId()), downloadRequest.getHash() + DOWNLOAD_TOKEN_SUFFIX);
                    if (!file.exists()) {
                        android.util.Log.w(LOG_TAG, "Supplied download request does not match a token that we have. Expected " + file);
                        return false;
                    }
                } else {
                    android.util.Log.w(LOG_TAG, "Download result did not include the path to the final temp file. Ignoring.");
                    return false;
                }
            } else {
                android.util.Log.w(LOG_TAG, "Download result did not include the associated request. Ignoring.");
                return false;
            }
        } else if (android.telephony.mbms.vendor.VendorUtils.ACTION_FILE_DESCRIPTOR_REQUEST.equals(intent.getAction())) {
            if (!intent.hasExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_SERVICE_ID)) {
                android.util.Log.w(LOG_TAG, "Temp file request did not include the associated service id. Ignoring.");
                return false;
            }
            if (!intent.hasExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_TEMP_FILE_ROOT)) {
                android.util.Log.w(LOG_TAG, "Download result did not include the temp file root. Ignoring.");
                return false;
            }
        } else if (android.telephony.mbms.vendor.VendorUtils.ACTION_CLEANUP.equals(intent.getAction())) {
            if (!intent.hasExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_SERVICE_ID)) {
                android.util.Log.w(LOG_TAG, "Cleanup request did not include the associated service id. Ignoring.");
                return false;
            }
            if (!intent.hasExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_TEMP_FILE_ROOT)) {
                android.util.Log.w(LOG_TAG, "Cleanup request did not include the temp file root. Ignoring.");
                return false;
            }
            if (!intent.hasExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_TEMP_FILES_IN_USE)) {
                android.util.Log.w(LOG_TAG, "Cleanup request did not include the list of temp files in use. Ignoring.");
                return false;
            }
        }
        return true;
    }

    private void moveDownloadedFile(android.content.Context context, android.content.Intent intent) {
        android.telephony.mbms.DownloadRequest downloadRequest = (android.telephony.mbms.DownloadRequest) intent.getParcelableExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_REQUEST, android.telephony.mbms.DownloadRequest.class);
        android.content.Intent intentForApp = downloadRequest.getIntentForApp();
        if (intentForApp == null) {
            android.util.Log.i(LOG_TAG, "Malformed app notification intent");
            setResultCode(6);
            return;
        }
        int intExtra = intent.getIntExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_RESULT, 2);
        intentForApp.putExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_RESULT, intExtra);
        intentForApp.putExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_REQUEST, downloadRequest);
        if (intExtra != 1) {
            android.util.Log.i(LOG_TAG, "Download request indicated a failed download. Aborting.");
            context.sendBroadcast(intentForApp);
            setResultCode(0);
            return;
        }
        android.net.Uri uri = (android.net.Uri) intent.getParcelableExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_FINAL_URI, android.net.Uri.class);
        if (!verifyTempFilePath(context, downloadRequest.getFileServiceId(), uri)) {
            android.util.Log.w(LOG_TAG, "Download result specified an invalid temp file " + uri);
            setResultCode(4);
            return;
        }
        android.telephony.mbms.FileInfo fileInfo = (android.telephony.mbms.FileInfo) intent.getParcelableExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_FILE_INFO, android.telephony.mbms.FileInfo.class);
        try {
            intentForApp.putExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_COMPLETED_FILE_URI, moveToFinalLocation(uri, java.nio.file.FileSystems.getDefault().getPath(downloadRequest.getDestinationUri().getPath(), new java.lang.String[0]), getFileRelativePath(downloadRequest.getSourceUri().getPath(), fileInfo.getUri().getPath())));
            intentForApp.putExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_FILE_INFO, fileInfo);
            context.sendBroadcast(intentForApp);
            setResultCode(0);
        } catch (java.io.IOException e) {
            android.util.Log.w(LOG_TAG, "Failed to move temp file to final destination");
            setResultCode(4);
        }
    }

    private void cleanupPostMove(android.content.Context context, android.content.Intent intent) {
        android.telephony.mbms.DownloadRequest downloadRequest = (android.telephony.mbms.DownloadRequest) intent.getParcelableExtra(android.telephony.MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_REQUEST, android.telephony.mbms.DownloadRequest.class);
        if (downloadRequest == null) {
            android.util.Log.w(LOG_TAG, "Intent does not include a DownloadRequest. Ignoring.");
            return;
        }
        java.util.ArrayList<android.net.Uri> parcelableArrayListExtra = intent.getParcelableArrayListExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_TEMP_LIST, android.net.Uri.class);
        if (parcelableArrayListExtra == null) {
            return;
        }
        for (android.net.Uri uri : parcelableArrayListExtra) {
            if (verifyTempFilePath(context, downloadRequest.getFileServiceId(), uri)) {
                java.io.File file = new java.io.File(uri.getSchemeSpecificPart());
                if (!file.delete()) {
                    android.util.Log.w(LOG_TAG, "Failed to delete temp file at " + file.getPath());
                }
            }
        }
    }

    private void generateTempFiles(android.content.Context context, android.content.Intent intent) {
        java.lang.String stringExtra = intent.getStringExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_SERVICE_ID);
        if (stringExtra == null) {
            android.util.Log.w(LOG_TAG, "Temp file request did not include the associated service id. Ignoring.");
            setResultCode(2);
            return;
        }
        int intExtra = intent.getIntExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_FD_COUNT, 0);
        java.util.ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_PAUSED_LIST, android.net.Uri.class);
        if (intExtra == 0 && (parcelableArrayListExtra == null || parcelableArrayListExtra.size() == 0)) {
            android.util.Log.i(LOG_TAG, "No temp files actually requested. Ending.");
            setResultCode(0);
            setResultExtras(android.os.Bundle.EMPTY);
            return;
        }
        java.util.ArrayList<android.telephony.mbms.UriPathPair> generateFreshTempFiles = generateFreshTempFiles(context, stringExtra, intExtra);
        java.util.ArrayList<android.telephony.mbms.UriPathPair> generateUrisForPausedFiles = generateUrisForPausedFiles(context, stringExtra, parcelableArrayListExtra);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelableArrayList(android.telephony.mbms.vendor.VendorUtils.EXTRA_FREE_URI_LIST, generateFreshTempFiles);
        bundle.putParcelableArrayList(android.telephony.mbms.vendor.VendorUtils.EXTRA_PAUSED_URI_LIST, generateUrisForPausedFiles);
        setResultCode(0);
        setResultExtras(bundle);
    }

    private java.util.ArrayList<android.telephony.mbms.UriPathPair> generateFreshTempFiles(android.content.Context context, java.lang.String str, int i) {
        java.io.File embmsTempFileDirForService = android.telephony.mbms.MbmsUtils.getEmbmsTempFileDirForService(context, str);
        if (!embmsTempFileDirForService.exists()) {
            embmsTempFileDirForService.mkdirs();
        }
        java.util.ArrayList<android.telephony.mbms.UriPathPair> arrayList = new java.util.ArrayList<>(i);
        for (int i2 = 0; i2 < i; i2++) {
            java.io.File generateSingleTempFile = generateSingleTempFile(embmsTempFileDirForService);
            if (generateSingleTempFile == null) {
                setResultCode(5);
                android.util.Log.w(LOG_TAG, "Failed to generate a temp file. Moving on.");
            } else {
                android.net.Uri fromFile = android.net.Uri.fromFile(generateSingleTempFile);
                android.net.Uri uriForFile = android.telephony.mbms.MbmsTempFileProvider.getUriForFile(context, getFileProviderAuthorityCached(context), generateSingleTempFile);
                context.grantUriPermission(getMiddlewarePackageCached(context), uriForFile, 3);
                arrayList.add(new android.telephony.mbms.UriPathPair(fromFile, uriForFile));
            }
        }
        return arrayList;
    }

    private static java.io.File generateSingleTempFile(java.io.File file) {
        int i = 0;
        while (i < 5) {
            i++;
            java.io.File file2 = new java.io.File(file, java.util.UUID.randomUUID() + TEMP_FILE_SUFFIX);
            if (file2.createNewFile()) {
                return file2.getCanonicalFile();
            }
            continue;
        }
        return null;
    }

    private java.util.ArrayList<android.telephony.mbms.UriPathPair> generateUrisForPausedFiles(android.content.Context context, java.lang.String str, java.util.List<android.net.Uri> list) {
        if (list == null) {
            return new java.util.ArrayList<>(0);
        }
        java.util.ArrayList<android.telephony.mbms.UriPathPair> arrayList = new java.util.ArrayList<>(list.size());
        for (android.net.Uri uri : list) {
            if (!verifyTempFilePath(context, str, uri)) {
                android.util.Log.w(LOG_TAG, "Supplied file " + uri + " is not a valid temp file to resume");
                setResultCode(5);
            } else {
                java.io.File file = new java.io.File(uri.getSchemeSpecificPart());
                if (!file.exists()) {
                    android.util.Log.w(LOG_TAG, "Supplied file " + uri + " does not exist.");
                    setResultCode(5);
                } else {
                    android.net.Uri uriForFile = android.telephony.mbms.MbmsTempFileProvider.getUriForFile(context, getFileProviderAuthorityCached(context), file);
                    context.grantUriPermission(getMiddlewarePackageCached(context), uriForFile, 3);
                    arrayList.add(new android.telephony.mbms.UriPathPair(uri, uriForFile));
                }
            }
        }
        return arrayList;
    }

    private void cleanupTempFiles(android.content.Context context, android.content.Intent intent) {
        java.io.File embmsTempFileDirForService = android.telephony.mbms.MbmsUtils.getEmbmsTempFileDirForService(context, intent.getStringExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_SERVICE_ID));
        final java.util.ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(android.telephony.mbms.vendor.VendorUtils.EXTRA_TEMP_FILES_IN_USE, android.net.Uri.class);
        for (java.io.File file : embmsTempFileDirForService.listFiles(new java.io.FileFilter() { // from class: android.telephony.mbms.MbmsDownloadReceiver.1
            @Override // java.io.FileFilter
            public boolean accept(java.io.File file2) {
                try {
                    if (!file2.getCanonicalFile().getName().endsWith(android.telephony.mbms.MbmsDownloadReceiver.TEMP_FILE_SUFFIX)) {
                        return false;
                    }
                    return !parcelableArrayListExtra.contains(android.net.Uri.fromFile(r4));
                } catch (java.io.IOException e) {
                    android.util.Log.w(android.telephony.mbms.MbmsDownloadReceiver.LOG_TAG, "Got IOException canonicalizing " + file2 + ", not deleting.");
                    return false;
                }
            }
        })) {
            file.delete();
        }
    }

    private static android.net.Uri moveToFinalLocation(android.net.Uri uri, java.nio.file.Path path, java.lang.String str) throws java.io.IOException {
        if (!"file".equals(uri.getScheme())) {
            android.util.Log.w(LOG_TAG, "Downloaded file location uri " + uri + " does not have a file scheme");
            return null;
        }
        java.nio.file.Path path2 = java.nio.file.FileSystems.getDefault().getPath(uri.getPath(), new java.lang.String[0]);
        java.nio.file.Path resolve = path.resolve(str);
        if (!java.nio.file.Files.isDirectory(resolve.getParent(), new java.nio.file.LinkOption[0])) {
            java.nio.file.Files.createDirectories(resolve.getParent(), new java.nio.file.attribute.FileAttribute[0]);
        }
        return android.net.Uri.fromFile(java.nio.file.Files.move(path2, resolve, java.nio.file.StandardCopyOption.REPLACE_EXISTING, java.nio.file.StandardCopyOption.ATOMIC_MOVE).toFile());
    }

    public static java.lang.String getFileRelativePath(java.lang.String str, java.lang.String str2) {
        if (str.endsWith("*")) {
            str = str.substring(0, str.lastIndexOf(47));
        }
        if (!str2.startsWith(str)) {
            android.util.Log.e(LOG_TAG, "File location specified in FileInfo does not match the source URI. source: " + str + " fileinfo path: " + str2);
            return null;
        }
        if (str2.length() == str.length()) {
            return str.substring(str.lastIndexOf(47) + 1);
        }
        java.lang.String substring = str2.substring(str.length());
        if (substring.startsWith("/")) {
            return substring.substring(1);
        }
        return substring;
    }

    private static boolean verifyTempFilePath(android.content.Context context, java.lang.String str, android.net.Uri uri) {
        if (!"file".equals(uri.getScheme())) {
            android.util.Log.w(LOG_TAG, "Uri " + uri + " does not have a file scheme");
            return false;
        }
        java.lang.String schemeSpecificPart = uri.getSchemeSpecificPart();
        java.io.File file = new java.io.File(schemeSpecificPart);
        if (!file.exists()) {
            android.util.Log.w(LOG_TAG, "File at " + schemeSpecificPart + " does not exist.");
            return false;
        }
        if (!android.telephony.mbms.MbmsUtils.isContainedIn(android.telephony.mbms.MbmsUtils.getEmbmsTempFileDirForService(context, str), file)) {
            android.util.Log.w(LOG_TAG, "File at " + schemeSpecificPart + " is not contained in the temp file root, which is " + android.telephony.mbms.MbmsUtils.getEmbmsTempFileDirForService(context, str));
            return false;
        }
        return true;
    }

    private java.lang.String getFileProviderAuthorityCached(android.content.Context context) {
        if (this.mFileProviderAuthorityCache != null) {
            return this.mFileProviderAuthorityCache;
        }
        this.mFileProviderAuthorityCache = getFileProviderAuthority(context);
        return this.mFileProviderAuthorityCache;
    }

    private static java.lang.String getFileProviderAuthority(android.content.Context context) {
        try {
            android.content.pm.ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData == null) {
                throw new java.lang.RuntimeException("App must declare the file provider authority as metadata in the manifest.");
            }
            java.lang.String string = applicationInfo.metaData.getString(MBMS_FILE_PROVIDER_META_DATA_KEY);
            if (string == null) {
                throw new java.lang.RuntimeException("App must declare the file provider authority as metadata in the manifest.");
            }
            return string;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.RuntimeException("Package manager couldn't find " + context.getPackageName());
        }
    }

    private java.lang.String getMiddlewarePackageCached(android.content.Context context) {
        if (this.mMiddlewarePackageNameCache == null) {
            this.mMiddlewarePackageNameCache = android.telephony.mbms.MbmsUtils.getMiddlewareServiceInfo(context, android.telephony.MbmsDownloadSession.MBMS_DOWNLOAD_SERVICE_ACTION).packageName;
        }
        return this.mMiddlewarePackageNameCache;
    }

    private void verifyPermissionIntegrity(android.content.Context context) {
        java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(new android.content.Intent(context, (java.lang.Class<?>) android.telephony.mbms.MbmsDownloadReceiver.class), 0);
        if (queryBroadcastReceivers.size() != 1) {
            throw new java.lang.IllegalStateException("Non-unique download receiver in your app");
        }
        android.content.pm.ActivityInfo activityInfo = queryBroadcastReceivers.get(0).activityInfo;
        if (activityInfo == null) {
            throw new java.lang.IllegalStateException("Queried ResolveInfo does not contain a receiver");
        }
        if (android.telephony.mbms.MbmsUtils.getOverrideServiceName(context, android.telephony.MbmsDownloadSession.MBMS_DOWNLOAD_SERVICE_ACTION) != null) {
            if (activityInfo.permission == null) {
                throw new java.lang.IllegalStateException("MbmsDownloadReceiver must require some permission");
            }
        } else if (!java.util.Objects.equals("android.permission.SEND_EMBMS_INTENTS", activityInfo.permission)) {
            throw new java.lang.IllegalStateException("MbmsDownloadReceiver must require the SEND_EMBMS_INTENTS permission.");
        }
    }
}
