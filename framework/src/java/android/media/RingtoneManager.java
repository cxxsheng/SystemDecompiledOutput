package android.media;

/* loaded from: classes2.dex */
public class RingtoneManager {
    public static final java.lang.String ACTION_RINGTONE_PICKER = "android.intent.action.RINGTONE_PICKER";
    public static final java.lang.String EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS = "android.intent.extra.ringtone.AUDIO_ATTRIBUTES_FLAGS";
    public static final java.lang.String EXTRA_RINGTONE_DEFAULT_URI = "android.intent.extra.ringtone.DEFAULT_URI";
    public static final java.lang.String EXTRA_RINGTONE_EXISTING_URI = "android.intent.extra.ringtone.EXISTING_URI";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_RINGTONE_INCLUDE_DRM = "android.intent.extra.ringtone.INCLUDE_DRM";
    public static final java.lang.String EXTRA_RINGTONE_PICKED_URI = "android.intent.extra.ringtone.PICKED_URI";
    public static final java.lang.String EXTRA_RINGTONE_SHOW_DEFAULT = "android.intent.extra.ringtone.SHOW_DEFAULT";
    public static final java.lang.String EXTRA_RINGTONE_SHOW_SILENT = "android.intent.extra.ringtone.SHOW_SILENT";
    public static final java.lang.String EXTRA_RINGTONE_TITLE = "android.intent.extra.ringtone.TITLE";
    public static final java.lang.String EXTRA_RINGTONE_TYPE = "android.intent.extra.ringtone.TYPE";
    public static final int ID_COLUMN_INDEX = 0;
    private static final java.lang.String[] INTERNAL_COLUMNS = {"_id", "title", "title", "title_key"};
    private static final java.lang.String[] MEDIA_COLUMNS = {"_id", "title", "title", "title_key"};
    private static final java.lang.String TAG = "RingtoneManager";
    public static final int TITLE_COLUMN_INDEX = 1;
    public static final int TYPE_ALARM = 4;
    public static final int TYPE_ALL = 7;
    public static final int TYPE_NOTIFICATION = 2;
    public static final int TYPE_RINGTONE = 1;
    public static final int URI_COLUMN_INDEX = 2;
    private final android.app.Activity mActivity;
    private final android.content.Context mContext;
    private android.database.Cursor mCursor;
    private final java.util.List<java.lang.String> mFilterColumns;
    private boolean mIncludeParentRingtones;
    private android.media.Ringtone mPreviousRingtone;
    private boolean mStopPreviousRingtone;
    private int mType;

    public RingtoneManager(android.app.Activity activity) {
        this(activity, false);
    }

    public RingtoneManager(android.app.Activity activity, boolean z) {
        this.mType = 1;
        this.mFilterColumns = new java.util.ArrayList();
        this.mStopPreviousRingtone = true;
        this.mActivity = activity;
        this.mContext = activity;
        setType(this.mType);
        this.mIncludeParentRingtones = z;
    }

    public RingtoneManager(android.content.Context context) {
        this(context, false);
    }

    public RingtoneManager(android.content.Context context, boolean z) {
        this.mType = 1;
        this.mFilterColumns = new java.util.ArrayList();
        this.mStopPreviousRingtone = true;
        this.mActivity = null;
        this.mContext = context;
        setType(this.mType);
        this.mIncludeParentRingtones = z;
    }

    public void setType(int i) {
        if (this.mCursor != null) {
            throw new java.lang.IllegalStateException("Setting filter columns should be done before querying for ringtones.");
        }
        this.mType = i;
        setFilterColumnsList(i);
    }

    public int inferStreamType() {
        switch (this.mType) {
            case 2:
                return 5;
            case 3:
            default:
                return 2;
            case 4:
                return 4;
        }
    }

    public void setStopPreviousRingtone(boolean z) {
        this.mStopPreviousRingtone = z;
    }

    public boolean getStopPreviousRingtone() {
        return this.mStopPreviousRingtone;
    }

    public void stopPreviousRingtone() {
        if (this.mPreviousRingtone != null) {
            this.mPreviousRingtone.stop();
        }
    }

    @java.lang.Deprecated
    public boolean getIncludeDrm() {
        return false;
    }

    @java.lang.Deprecated
    public void setIncludeDrm(boolean z) {
        if (z) {
            android.util.Log.w(TAG, "setIncludeDrm no longer supported");
        }
    }

    public android.database.Cursor getCursor() {
        android.database.Cursor parentProfileRingtones;
        if (this.mCursor != null && this.mCursor.requery()) {
            return this.mCursor;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(getInternalRingtones());
        arrayList.add(getMediaRingtones());
        if (this.mIncludeParentRingtones && (parentProfileRingtones = getParentProfileRingtones()) != null) {
            arrayList.add(parentProfileRingtones);
        }
        com.android.internal.database.SortCursor sortCursor = new com.android.internal.database.SortCursor((android.database.Cursor[]) arrayList.toArray(new android.database.Cursor[arrayList.size()]), "title_key");
        this.mCursor = sortCursor;
        return sortCursor;
    }

    private android.database.Cursor getParentProfileRingtones() {
        android.content.Context createPackageContextAsUser;
        android.content.pm.UserInfo profileParent = android.os.UserManager.get(this.mContext).getProfileParent(this.mContext.getUserId());
        if (profileParent != null && profileParent.id != this.mContext.getUserId() && (createPackageContextAsUser = createPackageContextAsUser(this.mContext, profileParent.id)) != null) {
            return new android.media.ExternalRingtonesCursorWrapper(getMediaRingtones(createPackageContextAsUser), android.content.ContentProvider.maybeAddUserId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, profileParent.id));
        }
        return null;
    }

    public android.media.Ringtone getRingtone(int i) {
        if (this.mStopPreviousRingtone && this.mPreviousRingtone != null) {
            this.mPreviousRingtone.stop();
        }
        this.mPreviousRingtone = getRingtone(this.mContext, getRingtoneUri(i), inferStreamType(), true);
        return this.mPreviousRingtone;
    }

    public android.net.Uri getRingtoneUri(int i) {
        try {
            if (this.mCursor != null) {
                if (this.mCursor.moveToPosition(i)) {
                    return getUriFromCursor(this.mContext, this.mCursor);
                }
            }
            return null;
        } catch (android.database.StaleDataException | java.lang.IllegalStateException e) {
            android.util.Log.e(TAG, "Unexpected Exception has been catched.", e);
            return null;
        }
    }

    public static android.net.Uri getRingtoneUriForRestore(android.content.ContentResolver contentResolver, java.lang.String str, int i) throws java.io.FileNotFoundException, java.lang.IllegalArgumentException {
        java.lang.String str2;
        if (str == null) {
            return null;
        }
        android.net.Uri parse = android.net.Uri.parse(str);
        android.net.Uri uncanonicalize = contentResolver.uncanonicalize(parse);
        if (uncanonicalize != null) {
            return contentResolver.canonicalize(uncanonicalize);
        }
        java.lang.String queryParameter = parse.getQueryParameter("title");
        android.net.Uri build = android.content.ContentUris.removeId(parse).buildUpon().clearQuery().build();
        switch (i) {
            case 1:
                str2 = "is_ringtone";
                break;
            case 2:
                str2 = "is_notification";
                break;
            case 3:
            default:
                throw new java.lang.IllegalArgumentException("Unknown ringtone type: " + i);
            case 4:
                str2 = "is_alarm";
                break;
        }
        try {
            android.database.Cursor query = contentResolver.query(build, new java.lang.String[]{"_id"}, str2 + "=1 AND title=?", new java.lang.String[]{queryParameter}, null, null);
            if (query == null) {
                throw new java.io.FileNotFoundException("Missing cursor for " + build);
            }
            if (query.getCount() == 0) {
                android.os.FileUtils.closeQuietly(query);
                throw new java.io.FileNotFoundException("No item found for " + build);
            }
            if (query.getCount() > 1) {
                android.os.FileUtils.closeQuietly(query);
                throw new java.io.FileNotFoundException("Find multiple ringtone candidates by title+ringtone_type query: count: " + query.getCount());
            }
            if (query.moveToFirst()) {
                android.net.Uri withAppendedId = android.content.ContentUris.withAppendedId(build, query.getLong(0));
                android.os.FileUtils.closeQuietly(query);
                android.net.Uri canonicalize = contentResolver.canonicalize(withAppendedId);
                android.util.Log.v(TAG, "Find a valid result: " + canonicalize);
                return canonicalize;
            }
            android.os.FileUtils.closeQuietly(query);
            throw new java.io.FileNotFoundException("Failed to read row from the result.");
        } catch (java.lang.IllegalArgumentException e) {
            throw new java.io.FileNotFoundException("Volume not found for " + build);
        }
    }

    private static android.net.Uri getUriFromCursor(android.content.Context context, android.database.Cursor cursor) {
        return context.getContentResolver().canonicalizeOrElse(android.content.ContentUris.withAppendedId(android.net.Uri.parse(cursor.getString(2)), cursor.getLong(0)));
    }

    public int getRingtonePosition(android.net.Uri uri) {
        if (uri == null) {
            return -1;
        }
        try {
            android.database.Cursor cursor = getCursor();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                if (uri.equals(getUriFromCursor(this.mContext, cursor))) {
                    return cursor.getPosition();
                }
            }
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.e(TAG, "NumberFormatException while getting ringtone position, returning -1", e);
        }
        return -1;
    }

    public static android.net.Uri getValidRingtoneUri(android.content.Context context) {
        android.media.RingtoneManager ringtoneManager = new android.media.RingtoneManager(context);
        android.net.Uri validRingtoneUriFromCursorAndClose = getValidRingtoneUriFromCursorAndClose(context, ringtoneManager.getInternalRingtones());
        if (validRingtoneUriFromCursorAndClose == null) {
            return getValidRingtoneUriFromCursorAndClose(context, ringtoneManager.getMediaRingtones());
        }
        return validRingtoneUriFromCursorAndClose;
    }

    private static android.net.Uri getValidRingtoneUriFromCursorAndClose(android.content.Context context, android.database.Cursor cursor) {
        android.net.Uri uri = null;
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            uri = getUriFromCursor(context, cursor);
        }
        cursor.close();
        return uri;
    }

    private android.database.Cursor getInternalRingtones() {
        return new android.media.ExternalRingtonesCursorWrapper(query(android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI, INTERNAL_COLUMNS, constructBooleanTrueWhereClause(this.mFilterColumns), null, "title_key"), android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
    }

    private android.database.Cursor getMediaRingtones() {
        return new android.media.ExternalRingtonesCursorWrapper(getMediaRingtones(this.mContext), android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    private android.database.Cursor getMediaRingtones(android.content.Context context) {
        return query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MEDIA_COLUMNS, constructBooleanTrueWhereClause(this.mFilterColumns), null, "title_key", context);
    }

    private void setFilterColumnsList(int i) {
        java.util.List<java.lang.String> list = this.mFilterColumns;
        list.clear();
        if ((i & 1) != 0) {
            list.add("is_ringtone");
        }
        if ((i & 2) != 0) {
            list.add("is_notification");
        }
        if ((i & 4) != 0) {
            list.add("is_alarm");
        }
    }

    private static java.lang.String constructBooleanTrueWhereClause(java.util.List<java.lang.String> list) {
        if (list == null) {
            return null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        for (int size = list.size() - 1; size >= 0; size--) {
            sb.append(list.get(size)).append("=1 or ");
        }
        if (list.size() > 0) {
            sb.setLength(sb.length() - 4);
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    private android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        return query(uri, strArr, str, strArr2, str2, this.mContext);
    }

    private android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2, android.content.Context context) {
        if (this.mActivity != null) {
            return this.mActivity.managedQuery(uri, strArr, str, strArr2, str2);
        }
        return context.getContentResolver().query(uri, strArr, str, strArr2, str2);
    }

    public static android.media.Ringtone getRingtone(android.content.Context context, android.net.Uri uri) {
        return getRingtone(context, uri, -1, true);
    }

    public static android.media.Ringtone getRingtone(android.content.Context context, android.net.Uri uri, android.media.VolumeShaper.Configuration configuration) {
        return getRingtone(context, uri, -1, configuration, true);
    }

    public static android.media.Ringtone getRingtone(android.content.Context context, android.net.Uri uri, android.media.VolumeShaper.Configuration configuration, boolean z) {
        return getRingtone(context, uri, -1, configuration, z);
    }

    public static android.media.Ringtone getRingtone(android.content.Context context, android.net.Uri uri, android.media.VolumeShaper.Configuration configuration, android.media.AudioAttributes audioAttributes) {
        android.media.Ringtone ringtone = getRingtone(context, uri, -1, configuration, false);
        if (ringtone != null) {
            ringtone.setAudioAttributesField(audioAttributes);
            if (!ringtone.createLocalMediaPlayer()) {
                android.util.Log.e(TAG, "Failed to open ringtone " + uri);
                return null;
            }
        }
        return ringtone;
    }

    private static android.media.Ringtone getRingtone(android.content.Context context, android.net.Uri uri, int i, boolean z) {
        return getRingtone(context, uri, i, null, z);
    }

    private static android.media.Ringtone getRingtone(android.content.Context context, android.net.Uri uri, int i, android.media.VolumeShaper.Configuration configuration, boolean z) {
        try {
            android.media.Ringtone ringtone = new android.media.Ringtone(context, true);
            if (i >= 0) {
                ringtone.setStreamType(i);
            }
            ringtone.setVolumeShaperConfig(configuration);
            ringtone.setUri(uri, configuration);
            if (z && !ringtone.createLocalMediaPlayer()) {
                android.util.Log.e(TAG, "Failed to open ringtone " + uri);
                return null;
            }
            return ringtone;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failed to open ringtone " + uri + ": " + e);
            return null;
        }
    }

    public static android.net.Uri getActualDefaultRingtoneUri(android.content.Context context, int i) {
        java.lang.String settingForType = getSettingForType(i);
        if (settingForType == null) {
            return null;
        }
        java.lang.String stringForUser = android.provider.Settings.System.getStringForUser(context.getContentResolver(), settingForType, context.getUserId());
        android.net.Uri parse = stringForUser != null ? android.net.Uri.parse(stringForUser) : null;
        if (parse != null && android.content.ContentProvider.getUserIdFromUri(parse) == context.getUserId()) {
            return android.content.ContentProvider.getUriWithoutUserId(parse);
        }
        return parse;
    }

    public static void setActualDefaultRingtoneUri(android.content.Context context, int i, android.net.Uri uri) {
        java.lang.String settingForType = getSettingForType(i);
        if (settingForType == null) {
            return;
        }
        android.content.ContentResolver contentResolver = context.getContentResolver();
        if (!isInternalRingtoneUri(uri)) {
            uri = android.content.ContentProvider.maybeAddUserId(uri, context.getUserId());
        }
        if (uri != null) {
            java.lang.String type = contentResolver.getType(uri);
            if (type == null) {
                android.util.Log.e(TAG, "setActualDefaultRingtoneUri for URI:" + uri + " ignored: failure to find mimeType (no access from this context?)");
                return;
            } else if (!type.startsWith("audio/") && !type.equals(com.google.android.mms.ContentType.AUDIO_OGG)) {
                android.util.Log.e(TAG, "setActualDefaultRingtoneUri for URI:" + uri + " ignored: associated mimeType:" + type + " is not an audio type");
                return;
            }
        }
        android.provider.Settings.System.putStringForUser(contentResolver, settingForType, uri != null ? uri.toString() : null, context.getUserId());
    }

    private static boolean isInternalRingtoneUri(android.net.Uri uri) {
        return isRingtoneUriInStorage(uri, android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
    }

    private static boolean isExternalRingtoneUri(android.net.Uri uri) {
        return isRingtoneUriInStorage(uri, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    private static boolean isRingtoneUriInStorage(android.net.Uri uri, android.net.Uri uri2) {
        android.net.Uri uriWithoutUserId = android.content.ContentProvider.getUriWithoutUserId(uri);
        if (uriWithoutUserId == null) {
            return false;
        }
        return uriWithoutUserId.toString().startsWith(uri2.toString());
    }

    public android.net.Uri addCustomExternalRingtone(android.net.Uri uri, int i) throws java.io.FileNotFoundException, java.lang.IllegalArgumentException, java.io.IOException {
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            throw new java.io.IOException("External storage is not mounted. Unable to install ringtones.");
        }
        java.lang.String type = this.mContext.getContentResolver().getType(uri);
        if (type == null || (!type.startsWith("audio/") && !type.equals(com.google.android.mms.ContentType.AUDIO_OGG))) {
            throw new java.lang.IllegalArgumentException("Ringtone file must have MIME type \"audio/*\". Given file has MIME type \"" + type + "\"");
        }
        java.io.File uniqueExternalFile = android.media.Utils.getUniqueExternalFile(this.mContext, getExternalDirectoryForType(i), android.os.FileUtils.buildValidFatFilename(android.media.Utils.getFileDisplayNameFromUri(this.mContext, uri)), type);
        java.io.InputStream openInputStream = this.mContext.getContentResolver().openInputStream(uri);
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(uniqueExternalFile);
            try {
                android.os.FileUtils.copy(openInputStream, fileOutputStream);
                fileOutputStream.close();
                if (openInputStream != null) {
                    openInputStream.close();
                }
                return android.provider.MediaStore.scanFile(this.mContext.getContentResolver(), uniqueExternalFile);
            } finally {
            }
        } catch (java.lang.Throwable th) {
            if (openInputStream != null) {
                try {
                    openInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static final java.lang.String getExternalDirectoryForType(int i) {
        switch (i) {
            case 1:
                return android.os.Environment.DIRECTORY_RINGTONES;
            case 2:
                return android.os.Environment.DIRECTORY_NOTIFICATIONS;
            case 3:
            default:
                throw new java.lang.IllegalArgumentException("Unsupported ringtone type: " + i);
            case 4:
                return android.os.Environment.DIRECTORY_ALARMS;
        }
    }

    private static java.lang.String getSettingForType(int i) {
        if ((i & 1) != 0) {
            return android.provider.Settings.System.RINGTONE;
        }
        if ((i & 2) != 0) {
            return android.provider.Settings.System.NOTIFICATION_SOUND;
        }
        if ((i & 4) != 0) {
            return android.provider.Settings.System.ALARM_ALERT;
        }
        return null;
    }

    public static android.net.Uri getCacheForType(int i) {
        return getCacheForType(i, android.os.UserHandle.getCallingUserId());
    }

    public static android.net.Uri getCacheForType(int i, int i2) {
        if ((i & 1) != 0) {
            return android.content.ContentProvider.maybeAddUserId(android.provider.Settings.System.RINGTONE_CACHE_URI, i2);
        }
        if ((i & 2) != 0) {
            return android.content.ContentProvider.maybeAddUserId(android.provider.Settings.System.NOTIFICATION_SOUND_CACHE_URI, i2);
        }
        if ((i & 4) != 0) {
            return android.content.ContentProvider.maybeAddUserId(android.provider.Settings.System.ALARM_ALERT_CACHE_URI, i2);
        }
        return null;
    }

    public static boolean isDefault(android.net.Uri uri) {
        return getDefaultType(uri) != -1;
    }

    public static int getDefaultType(android.net.Uri uri) {
        android.net.Uri uriWithoutUserId = android.content.ContentProvider.getUriWithoutUserId(uri);
        if (uriWithoutUserId == null) {
            return -1;
        }
        if (uriWithoutUserId.equals(android.provider.Settings.System.DEFAULT_RINGTONE_URI)) {
            return 1;
        }
        if (uriWithoutUserId.equals(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)) {
            return 2;
        }
        if (!uriWithoutUserId.equals(android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI)) {
            return -1;
        }
        return 4;
    }

    public static android.net.Uri getDefaultUri(int i) {
        if ((i & 1) != 0) {
            return android.provider.Settings.System.DEFAULT_RINGTONE_URI;
        }
        if ((i & 2) != 0) {
            return android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
        }
        if ((i & 4) != 0) {
            return android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI;
        }
        return null;
    }

    public static android.content.res.AssetFileDescriptor openDefaultRingtoneUri(android.content.Context context, android.net.Uri uri) throws java.io.FileNotFoundException {
        android.content.res.AssetFileDescriptor assetFileDescriptor;
        int defaultType = getDefaultType(uri);
        android.net.Uri cacheForType = getCacheForType(defaultType, context.getUserId());
        android.net.Uri actualDefaultRingtoneUri = getActualDefaultRingtoneUri(context, defaultType);
        android.content.ContentResolver contentResolver = context.getContentResolver();
        if (cacheForType == null) {
            assetFileDescriptor = null;
        } else {
            assetFileDescriptor = contentResolver.openAssetFileDescriptor(cacheForType, "r");
            if (assetFileDescriptor != null) {
                return assetFileDescriptor;
            }
        }
        if (actualDefaultRingtoneUri != null) {
            return contentResolver.openAssetFileDescriptor(actualDefaultRingtoneUri, "r");
        }
        return assetFileDescriptor;
    }

    public boolean hasHapticChannels(int i) {
        return android.media.AudioManager.hasHapticChannels(this.mContext, getRingtoneUri(i));
    }

    public static boolean hasHapticChannels(android.net.Uri uri) {
        return android.media.AudioManager.hasHapticChannels(null, uri);
    }

    public static boolean hasHapticChannels(android.content.Context context, android.net.Uri uri) {
        return android.media.AudioManager.hasHapticChannels(context, uri);
    }

    private static android.content.Context createPackageContextAsUser(android.content.Context context, int i) {
        try {
            return context.createPackageContextAsUser(context.getPackageName(), 0, android.os.UserHandle.of(i));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "Unable to create package context", e);
            return null;
        }
    }

    @android.annotation.SystemApi
    public static void ensureDefaultRingtones(android.content.Context context) {
        android.net.Uri computeDefaultRingtoneUri;
        int[] iArr = {1, 2, 4};
        for (int i = 0; i < 3; i++) {
            int i2 = iArr[i];
            java.lang.String defaultRingtoneSetting = getDefaultRingtoneSetting(i2);
            if (android.provider.Settings.System.getInt(context.getContentResolver(), defaultRingtoneSetting, 0) == 0 && (computeDefaultRingtoneUri = computeDefaultRingtoneUri(context, i2)) != null) {
                setActualDefaultRingtoneUri(context, i2, computeDefaultRingtoneUri);
                android.provider.Settings.System.putInt(context.getContentResolver(), defaultRingtoneSetting, 1);
            }
        }
    }

    private static android.net.Uri computeDefaultRingtoneUri(android.content.Context context, int i) {
        java.lang.String defaultRingtoneFilename = getDefaultRingtoneFilename(i);
        java.lang.String str = "_display_name=? AND " + getQueryStringForType(i) + "=?";
        android.net.Uri uri = android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        android.database.Cursor query = context.getContentResolver().query(uri, new java.lang.String[]{"_id"}, str, new java.lang.String[]{defaultRingtoneFilename, "1"}, null);
        try {
            if (query.moveToFirst()) {
                android.net.Uri canonicalizeOrElse = context.getContentResolver().canonicalizeOrElse(android.content.ContentUris.withAppendedId(uri, query.getLong(0)));
                if (query != null) {
                    query.close();
                }
                return canonicalizeOrElse;
            }
            if (query != null) {
                query.close();
                return null;
            }
            return null;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static java.lang.String getDefaultRingtoneSetting(int i) {
        switch (i) {
            case 1:
                return "ringtone_set";
            case 2:
                return "notification_sound_set";
            case 3:
            default:
                throw new java.lang.IllegalArgumentException();
            case 4:
                return "alarm_alert_set";
        }
    }

    private static java.lang.String getDefaultRingtoneFilename(int i) {
        switch (i) {
            case 1:
                return android.os.SystemProperties.get("ro.config.ringtone");
            case 2:
                return android.os.SystemProperties.get("ro.config.notification_sound");
            case 3:
            default:
                throw new java.lang.IllegalArgumentException();
            case 4:
                return android.os.SystemProperties.get("ro.config.alarm_alert");
        }
    }

    private static java.lang.String getQueryStringForType(int i) {
        switch (i) {
            case 1:
                return "is_ringtone";
            case 2:
                return "is_notification";
            case 3:
            default:
                throw new java.lang.IllegalArgumentException();
            case 4:
                return "is_alarm";
        }
    }
}
