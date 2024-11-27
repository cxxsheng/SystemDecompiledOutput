package android.mtp;

/* loaded from: classes2.dex */
public class MtpDatabase implements java.lang.AutoCloseable {
    private static final int[] AUDIO_PROPERTIES;
    private static final int[] DEVICE_PROPERTIES;
    private static final int[] FILE_PROPERTIES;
    private static final int[] IMAGE_PROPERTIES;
    private static final int MAX_THUMB_SIZE = 204800;
    private static final java.lang.String NO_MEDIA = ".nomedia";
    private static final java.lang.String PATH_WHERE = "_data=?";
    private static final int[] PLAYBACK_FORMATS;
    private static final java.lang.String TAG = android.mtp.MtpDatabase.class.getSimpleName();
    private static final int[] VIDEO_PROPERTIES;
    private int mBatteryLevel;
    private int mBatteryScale;
    private final android.content.Context mContext;
    private android.content.SharedPreferences mDeviceProperties;
    private int mDeviceType;
    private java.lang.String mHostType;
    private android.mtp.MtpStorageManager mManager;
    private final android.content.ContentProviderClient mMediaProvider;
    private long mNativeContext;
    private android.mtp.MtpServer mServer;
    private final java.util.concurrent.atomic.AtomicBoolean mClosed = new java.util.concurrent.atomic.AtomicBoolean();
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final java.util.HashMap<java.lang.String, android.mtp.MtpStorage> mStorageMap = new java.util.HashMap<>();
    private final android.util.SparseArray<android.mtp.MtpPropertyGroup> mPropertyGroupsByProperty = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.mtp.MtpPropertyGroup> mPropertyGroupsByFormat = new android.util.SparseArray<>();
    private boolean mSkipThumbForHost = false;
    private volatile boolean mHostIsWindows = false;
    private android.content.BroadcastReceiver mBatteryReceiver = new android.content.BroadcastReceiver() { // from class: android.mtp.MtpDatabase.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent.getAction().equals(android.content.Intent.ACTION_BATTERY_CHANGED)) {
                android.mtp.MtpDatabase.this.mBatteryScale = intent.getIntExtra("scale", 0);
                int intExtra = intent.getIntExtra("level", 0);
                if (intExtra != android.mtp.MtpDatabase.this.mBatteryLevel) {
                    android.mtp.MtpDatabase.this.mBatteryLevel = intExtra;
                    if (android.mtp.MtpDatabase.this.mServer != null) {
                        android.mtp.MtpDatabase.this.mServer.sendDevicePropertyChanged(android.mtp.MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL);
                    }
                }
            }
        }
    };

    private final native void native_finalize();

    private final native void native_setup();

    static {
        java.lang.System.loadLibrary("media_jni");
        PLAYBACK_FORMATS = new int[]{12288, 12289, 12292, 12293, 12296, 12297, 12299, android.mtp.MtpConstants.FORMAT_EXIF_JPEG, android.mtp.MtpConstants.FORMAT_TIFF_EP, android.mtp.MtpConstants.FORMAT_BMP, android.mtp.MtpConstants.FORMAT_GIF, android.mtp.MtpConstants.FORMAT_JFIF, android.mtp.MtpConstants.FORMAT_PNG, android.mtp.MtpConstants.FORMAT_TIFF, android.mtp.MtpConstants.FORMAT_WMA, android.mtp.MtpConstants.FORMAT_OGG, android.mtp.MtpConstants.FORMAT_AAC, android.mtp.MtpConstants.FORMAT_MP4_CONTAINER, android.mtp.MtpConstants.FORMAT_MP2, android.mtp.MtpConstants.FORMAT_3GP_CONTAINER, android.mtp.MtpConstants.FORMAT_ABSTRACT_AV_PLAYLIST, android.mtp.MtpConstants.FORMAT_WPL_PLAYLIST, android.mtp.MtpConstants.FORMAT_M3U_PLAYLIST, android.mtp.MtpConstants.FORMAT_PLS_PLAYLIST, android.mtp.MtpConstants.FORMAT_XML_DOCUMENT, android.mtp.MtpConstants.FORMAT_FLAC, android.mtp.MtpConstants.FORMAT_DNG, android.mtp.MtpConstants.FORMAT_HEIF};
        FILE_PROPERTIES = new int[]{android.mtp.MtpConstants.PROPERTY_STORAGE_ID, android.mtp.MtpConstants.PROPERTY_OBJECT_FORMAT, android.mtp.MtpConstants.PROPERTY_PROTECTION_STATUS, android.mtp.MtpConstants.PROPERTY_OBJECT_SIZE, android.mtp.MtpConstants.PROPERTY_OBJECT_FILE_NAME, android.mtp.MtpConstants.PROPERTY_DATE_MODIFIED, android.mtp.MtpConstants.PROPERTY_PERSISTENT_UID, android.mtp.MtpConstants.PROPERTY_PARENT_OBJECT, android.mtp.MtpConstants.PROPERTY_NAME, android.mtp.MtpConstants.PROPERTY_DISPLAY_NAME, android.mtp.MtpConstants.PROPERTY_DATE_ADDED};
        AUDIO_PROPERTIES = new int[]{android.mtp.MtpConstants.PROPERTY_ARTIST, android.mtp.MtpConstants.PROPERTY_ALBUM_NAME, android.mtp.MtpConstants.PROPERTY_ALBUM_ARTIST, android.mtp.MtpConstants.PROPERTY_TRACK, android.mtp.MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE, android.mtp.MtpConstants.PROPERTY_DURATION, android.mtp.MtpConstants.PROPERTY_GENRE, android.mtp.MtpConstants.PROPERTY_COMPOSER, android.mtp.MtpConstants.PROPERTY_AUDIO_WAVE_CODEC, android.mtp.MtpConstants.PROPERTY_BITRATE_TYPE, android.mtp.MtpConstants.PROPERTY_AUDIO_BITRATE, android.mtp.MtpConstants.PROPERTY_NUMBER_OF_CHANNELS, android.mtp.MtpConstants.PROPERTY_SAMPLE_RATE};
        VIDEO_PROPERTIES = new int[]{android.mtp.MtpConstants.PROPERTY_ARTIST, android.mtp.MtpConstants.PROPERTY_ALBUM_NAME, android.mtp.MtpConstants.PROPERTY_DURATION, android.mtp.MtpConstants.PROPERTY_DESCRIPTION};
        IMAGE_PROPERTIES = new int[]{android.mtp.MtpConstants.PROPERTY_DESCRIPTION};
        DEVICE_PROPERTIES = new int[]{android.mtp.MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER, android.mtp.MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME, android.mtp.MtpConstants.DEVICE_PROPERTY_IMAGE_SIZE, android.mtp.MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL, android.mtp.MtpConstants.DEVICE_PROPERTY_PERCEIVED_DEVICE_TYPE, android.mtp.MtpConstants.DEVICE_PROPERTY_SESSION_INITIATOR_VERSION_INFO};
    }

    private int[] getSupportedObjectProperties(int i) {
        switch (i) {
            case 12296:
            case 12297:
            case android.mtp.MtpConstants.FORMAT_WMA /* 47361 */:
            case android.mtp.MtpConstants.FORMAT_OGG /* 47362 */:
            case android.mtp.MtpConstants.FORMAT_AAC /* 47363 */:
                return java.util.stream.IntStream.concat(java.util.Arrays.stream(FILE_PROPERTIES), java.util.Arrays.stream(AUDIO_PROPERTIES)).toArray();
            case 12299:
            case android.mtp.MtpConstants.FORMAT_WMV /* 47489 */:
            case android.mtp.MtpConstants.FORMAT_3GP_CONTAINER /* 47492 */:
                return java.util.stream.IntStream.concat(java.util.Arrays.stream(FILE_PROPERTIES), java.util.Arrays.stream(VIDEO_PROPERTIES)).toArray();
            case android.mtp.MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
            case android.mtp.MtpConstants.FORMAT_BMP /* 14340 */:
            case android.mtp.MtpConstants.FORMAT_GIF /* 14343 */:
            case android.mtp.MtpConstants.FORMAT_PNG /* 14347 */:
            case android.mtp.MtpConstants.FORMAT_DNG /* 14353 */:
            case android.mtp.MtpConstants.FORMAT_HEIF /* 14354 */:
                return java.util.stream.IntStream.concat(java.util.Arrays.stream(FILE_PROPERTIES), java.util.Arrays.stream(IMAGE_PROPERTIES)).toArray();
            default:
                return FILE_PROPERTIES;
        }
    }

    public static android.net.Uri getObjectPropertiesUri(int i, java.lang.String str) {
        switch (i) {
            case 12296:
            case 12297:
            case android.mtp.MtpConstants.FORMAT_WMA /* 47361 */:
            case android.mtp.MtpConstants.FORMAT_OGG /* 47362 */:
            case android.mtp.MtpConstants.FORMAT_AAC /* 47363 */:
                return android.provider.MediaStore.Audio.Media.getContentUri(str);
            case 12299:
            case android.mtp.MtpConstants.FORMAT_WMV /* 47489 */:
            case android.mtp.MtpConstants.FORMAT_3GP_CONTAINER /* 47492 */:
                return android.provider.MediaStore.Video.Media.getContentUri(str);
            case android.mtp.MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
            case android.mtp.MtpConstants.FORMAT_BMP /* 14340 */:
            case android.mtp.MtpConstants.FORMAT_GIF /* 14343 */:
            case android.mtp.MtpConstants.FORMAT_PNG /* 14347 */:
            case android.mtp.MtpConstants.FORMAT_DNG /* 14353 */:
            case android.mtp.MtpConstants.FORMAT_HEIF /* 14354 */:
                return android.provider.MediaStore.Images.Media.getContentUri(str);
            default:
                return android.provider.MediaStore.Files.getContentUri(str);
        }
    }

    private int[] getSupportedDeviceProperties() {
        return DEVICE_PROPERTIES;
    }

    private int[] getSupportedPlaybackFormats() {
        return PLAYBACK_FORMATS;
    }

    private int[] getSupportedCaptureFormats() {
        return null;
    }

    public MtpDatabase(android.content.Context context, java.lang.String[] strArr) {
        native_setup();
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
        this.mMediaProvider = context.getContentResolver().acquireContentProviderClient("media");
        this.mManager = new android.mtp.MtpStorageManager(new android.mtp.MtpStorageManager.MtpNotifier() { // from class: android.mtp.MtpDatabase.2
            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectAdded(int i) {
                if (android.mtp.MtpDatabase.this.mServer != null) {
                    android.mtp.MtpDatabase.this.mServer.sendObjectAdded(i);
                }
            }

            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectRemoved(int i) {
                if (android.mtp.MtpDatabase.this.mServer != null) {
                    android.mtp.MtpDatabase.this.mServer.sendObjectRemoved(i);
                }
            }

            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectInfoChanged(int i) {
                if (android.mtp.MtpDatabase.this.mServer != null) {
                    android.mtp.MtpDatabase.this.mServer.sendObjectInfoChanged(i);
                }
            }
        }, strArr == null ? null : com.google.android.collect.Sets.newHashSet(strArr));
        initDeviceProperties(context);
        this.mDeviceType = android.os.SystemProperties.getInt("sys.usb.mtp.device_type", 0);
        this.mCloseGuard.open("close");
    }

    public void setServer(android.mtp.MtpServer mtpServer) {
        this.mServer = mtpServer;
        try {
            this.mContext.unregisterReceiver(this.mBatteryReceiver);
        } catch (java.lang.IllegalArgumentException e) {
        }
        if (mtpServer != null) {
            this.mContext.registerReceiver(this.mBatteryReceiver, new android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED));
        }
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mManager.close();
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            if (this.mMediaProvider != null) {
                this.mMediaProvider.close();
            }
            native_finalize();
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$addStorage$0() {
        return java.lang.Boolean.valueOf(this.mHostIsWindows);
    }

    public void addStorage(android.os.storage.StorageVolume storageVolume) {
        android.mtp.MtpStorage addMtpStorage = this.mManager.addMtpStorage(storageVolume, new java.util.function.Supplier() { // from class: android.mtp.MtpDatabase$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Boolean lambda$addStorage$0;
                lambda$addStorage$0 = android.mtp.MtpDatabase.this.lambda$addStorage$0();
                return lambda$addStorage$0;
            }
        });
        this.mStorageMap.put(storageVolume.getPath(), addMtpStorage);
        if (this.mServer != null) {
            this.mServer.addStorage(addMtpStorage);
        }
    }

    public void removeStorage(android.os.storage.StorageVolume storageVolume) {
        android.mtp.MtpStorage mtpStorage = this.mStorageMap.get(storageVolume.getPath());
        if (mtpStorage == null) {
            return;
        }
        if (this.mServer != null) {
            this.mServer.removeStorage(mtpStorage);
        }
        this.mManager.removeMtpStorage(mtpStorage);
        this.mStorageMap.remove(storageVolume.getPath());
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x005b, code lost:
    
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0073, code lost:
    
        r13.deleteDatabase("device-properties");
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0070, code lost:
    
        if (r11 != null) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0059, code lost:
    
        if (r11 != null) goto L20;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void initDeviceProperties(android.content.Context context) {
        android.database.sqlite.SQLiteDatabase sQLiteDatabase;
        this.mDeviceProperties = context.getSharedPreferences("device-properties", 0);
        if (context.getDatabasePath("device-properties").exists()) {
            android.database.Cursor cursor = null;
            try {
                try {
                    sQLiteDatabase = context.openOrCreateDatabase("device-properties", 0, null);
                    if (sQLiteDatabase != null) {
                        try {
                            cursor = sQLiteDatabase.query("properties", new java.lang.String[]{"_id", "code", "value"}, null, null, null, null, null);
                            if (cursor != null) {
                                android.content.SharedPreferences.Editor edit = this.mDeviceProperties.edit();
                                while (cursor.moveToNext()) {
                                    edit.putString(cursor.getString(1), cursor.getString(2));
                                }
                                edit.commit();
                            }
                        } catch (java.lang.Exception e) {
                            e = e;
                            android.util.Log.e(TAG, "failed to migrate device properties", e);
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (java.lang.Exception e2) {
                    e = e2;
                    sQLiteDatabase = null;
                } catch (java.lang.Throwable th) {
                    th = th;
                    if (0 != 0) {
                    }
                    if (0 != 0) {
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                if (0 != 0) {
                    cursor.close();
                }
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        }
        this.mHostType = "";
        this.mSkipThumbForHost = false;
        this.mHostIsWindows = false;
    }

    public int beginSendObject(java.lang.String str, int i, int i2, int i3) {
        android.mtp.MtpStorageManager.MtpObject storageRoot = i2 == 0 ? this.mManager.getStorageRoot(i3) : this.mManager.getObject(i2);
        if (storageRoot == null) {
            return -1;
        }
        return this.mManager.beginSendObject(storageRoot, java.nio.file.Paths.get(str, new java.lang.String[0]).getFileName().toString(), i);
    }

    private void endSendObject(int i, boolean z) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null || !this.mManager.endSendObject(object, z)) {
            android.util.Log.e(TAG, "Failed to successfully end send object");
        } else if (z) {
            updateMediaStore(this.mContext, object.getPath().toFile());
        }
    }

    private void rescanFile(java.lang.String str, int i, int i2) {
        android.provider.MediaStore.scanFile(this.mContext.getContentResolver(), new java.io.File(str));
    }

    private int[] getObjectList(int i, int i2, int i3) {
        java.util.List<android.mtp.MtpStorageManager.MtpObject> objects = this.mManager.getObjects(i3, i2, i);
        if (objects == null) {
            return null;
        }
        int[] iArr = new int[objects.size()];
        for (int i4 = 0; i4 < objects.size(); i4++) {
            iArr[i4] = objects.get(i4).getId();
        }
        return iArr;
    }

    public int getNumObjects(int i, int i2, int i3) {
        java.util.List<android.mtp.MtpStorageManager.MtpObject> objects = this.mManager.getObjects(i3, i2, i);
        if (objects == null) {
            return -1;
        }
        return objects.size();
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x005d, code lost:
    
        if (r9 != 0) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.mtp.MtpPropertyList getObjectPropertyList(int i, int i2, int i3, int i4, int i5) {
        android.mtp.MtpStorageManager.MtpObject mtpObject;
        android.mtp.MtpPropertyGroup mtpPropertyGroup;
        if (i3 == 0) {
            if (i4 == 0) {
                return new android.mtp.MtpPropertyList(8198);
            }
            return new android.mtp.MtpPropertyList(android.mtp.MtpConstants.RESPONSE_SPECIFICATION_BY_GROUP_UNSUPPORTED);
        }
        if (i5 == -1 && (i == 0 || i == -1)) {
            i5 = 0;
            i = -1;
        }
        if (i5 != 0 && i5 != 1) {
            return new android.mtp.MtpPropertyList(android.mtp.MtpConstants.RESPONSE_SPECIFICATION_BY_DEPTH_UNSUPPORTED);
        }
        java.util.List<android.mtp.MtpStorageManager.MtpObject> list = null;
        if (i == -1) {
            java.util.List<android.mtp.MtpStorageManager.MtpObject> objects = this.mManager.getObjects(0, i2, -1);
            if (objects != null) {
                list = objects;
                mtpObject = null;
            } else {
                return new android.mtp.MtpPropertyList(8201);
            }
        } else {
            if (i != 0) {
                mtpObject = this.mManager.getObject(i);
                if (mtpObject == null) {
                    return new android.mtp.MtpPropertyList(8201);
                }
                if (mtpObject.getFormat() != i2) {
                }
            }
            mtpObject = null;
        }
        if (i == 0 || i5 == 1) {
            if (i == 0) {
                i = -1;
            }
            list = this.mManager.getObjects(i, i2, -1);
            if (list == null) {
                return new android.mtp.MtpPropertyList(8201);
            }
        }
        if (list == null) {
            list = new java.util.ArrayList<>();
        }
        if (mtpObject != null) {
            list.add(mtpObject);
        }
        android.mtp.MtpPropertyList mtpPropertyList = new android.mtp.MtpPropertyList(8193);
        for (android.mtp.MtpStorageManager.MtpObject mtpObject2 : list) {
            if (i3 == -1) {
                if (i2 == 0 && i != 0 && i != -1) {
                    i2 = mtpObject2.getFormat();
                }
                mtpPropertyGroup = this.mPropertyGroupsByFormat.get(i2);
                if (mtpPropertyGroup == null) {
                    android.mtp.MtpPropertyGroup mtpPropertyGroup2 = new android.mtp.MtpPropertyGroup(getSupportedObjectProperties(i2));
                    this.mPropertyGroupsByFormat.put(i2, mtpPropertyGroup2);
                    mtpPropertyGroup = mtpPropertyGroup2;
                }
            } else {
                mtpPropertyGroup = this.mPropertyGroupsByProperty.get(i3);
                if (mtpPropertyGroup == null) {
                    android.mtp.MtpPropertyGroup mtpPropertyGroup3 = new android.mtp.MtpPropertyGroup(new int[]{i3});
                    this.mPropertyGroupsByProperty.put(i3, mtpPropertyGroup3);
                    mtpPropertyGroup = mtpPropertyGroup3;
                }
            }
            int propertyList = mtpPropertyGroup.getPropertyList(this.mMediaProvider, mtpObject2.getVolumeName(), mtpObject2, mtpPropertyList);
            if (propertyList != 8193) {
                return new android.mtp.MtpPropertyList(propertyList);
            }
        }
        return mtpPropertyList;
    }

    private int renameFile(int i, java.lang.String str) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return 8201;
        }
        java.nio.file.Path path = object.getPath();
        if (!this.mManager.beginRenameObject(object, str)) {
            return 8194;
        }
        java.nio.file.Path path2 = object.getPath();
        boolean renameTo = path.toFile().renameTo(path2.toFile());
        try {
            android.system.Os.access(path.toString(), android.system.OsConstants.F_OK);
            android.system.Os.access(path2.toString(), android.system.OsConstants.F_OK);
        } catch (android.system.ErrnoException e) {
        }
        if (!this.mManager.endRenameObject(object, path.getFileName().toString(), renameTo)) {
            android.util.Log.e(TAG, "Failed to end rename object");
        }
        if (!renameTo) {
            return 8194;
        }
        updateMediaStore(this.mContext, path.toFile());
        updateMediaStore(this.mContext, path2.toFile());
        return 8193;
    }

    private int beginMoveObject(int i, int i2, int i3) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        android.mtp.MtpStorageManager.MtpObject storageRoot = i2 == 0 ? this.mManager.getStorageRoot(i3) : this.mManager.getObject(i2);
        if (object == null || storageRoot == null) {
            return 8201;
        }
        return this.mManager.beginMoveObject(object, storageRoot) ? 8193 : 8194;
    }

    private void endMoveObject(int i, int i2, int i3, int i4, int i5, boolean z) {
        android.mtp.MtpStorageManager.MtpObject storageRoot = i == 0 ? this.mManager.getStorageRoot(i3) : this.mManager.getObject(i);
        android.mtp.MtpStorageManager.MtpObject storageRoot2 = i2 == 0 ? this.mManager.getStorageRoot(i4) : this.mManager.getObject(i2);
        java.lang.String name = this.mManager.getObject(i5).getName();
        if (storageRoot2 == null || storageRoot == null || !this.mManager.endMoveObject(storageRoot, storageRoot2, name, z)) {
            android.util.Log.e(TAG, "Failed to end move object");
            return;
        }
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i5);
        if (!z || object == null) {
            return;
        }
        java.nio.file.Path resolve = storageRoot2.getPath().resolve(name);
        updateMediaStore(this.mContext, storageRoot.getPath().resolve(name).toFile());
        updateMediaStore(this.mContext, resolve.toFile());
    }

    private int beginCopyObject(int i, int i2, int i3) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        android.mtp.MtpStorageManager.MtpObject storageRoot = i2 == 0 ? this.mManager.getStorageRoot(i3) : this.mManager.getObject(i2);
        if (object == null || storageRoot == null) {
            return 8201;
        }
        return this.mManager.beginCopyObject(object, storageRoot);
    }

    private void endCopyObject(int i, boolean z) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null || !this.mManager.endCopyObject(object, z)) {
            android.util.Log.e(TAG, "Failed to end copy object");
        } else {
            if (!z) {
                return;
            }
            updateMediaStore(this.mContext, object.getPath().toFile());
        }
    }

    private static void updateMediaStore(android.content.Context context, java.io.File file) {
        android.content.ContentResolver contentResolver = context.getContentResolver();
        if (!file.isDirectory() && file.getName().toLowerCase(java.util.Locale.ROOT).endsWith(NO_MEDIA)) {
            android.provider.MediaStore.scanFile(contentResolver, file.getParentFile());
        } else {
            android.provider.MediaStore.scanFile(contentResolver, file);
        }
    }

    private int setObjectProperty(int i, int i2, long j, java.lang.String str) {
        switch (i2) {
            case android.mtp.MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                return renameFile(i, str);
            default:
                return android.mtp.MtpConstants.RESPONSE_OBJECT_PROP_NOT_SUPPORTED;
        }
    }

    private int getDeviceProperty(int i, long[] jArr, char[] cArr) {
        int i2 = 255;
        switch (i) {
            case android.mtp.MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL /* 20481 */:
                jArr[0] = this.mBatteryLevel;
                jArr[1] = this.mBatteryScale;
                break;
            case android.mtp.MtpConstants.DEVICE_PROPERTY_IMAGE_SIZE /* 20483 */:
                android.view.Display defaultDisplay = ((android.view.WindowManager) this.mContext.getSystemService(android.content.Context.WINDOW_SERVICE)).getDefaultDisplay();
                java.lang.String str = java.lang.Integer.toString(defaultDisplay.getMaximumSizeDimension()) + "x" + java.lang.Integer.toString(defaultDisplay.getMaximumSizeDimension());
                str.getChars(0, str.length(), cArr, 0);
                cArr[str.length()] = 0;
                break;
            case android.mtp.MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER /* 54273 */:
            case android.mtp.MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME /* 54274 */:
                java.lang.String string = this.mDeviceProperties.getString(java.lang.Integer.toString(i), "");
                int length = string.length();
                if (length <= 255) {
                    i2 = length;
                }
                string.getChars(0, i2, cArr, 0);
                cArr[i2] = 0;
                break;
            case android.mtp.MtpConstants.DEVICE_PROPERTY_SESSION_INITIATOR_VERSION_INFO /* 54278 */:
                java.lang.String str2 = this.mHostType;
                int length2 = str2.length();
                if (length2 <= 255) {
                    i2 = length2;
                }
                str2.getChars(0, i2, cArr, 0);
                cArr[i2] = 0;
                break;
            case android.mtp.MtpConstants.DEVICE_PROPERTY_PERCEIVED_DEVICE_TYPE /* 54279 */:
                jArr[0] = this.mDeviceType;
                break;
        }
        return 8193;
    }

    private int setDeviceProperty(int i, long j, java.lang.String str) {
        switch (i) {
            case android.mtp.MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER /* 54273 */:
            case android.mtp.MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME /* 54274 */:
                android.content.SharedPreferences.Editor edit = this.mDeviceProperties.edit();
                edit.putString(java.lang.Integer.toString(i), str);
                if (edit.commit()) {
                    return 8193;
                }
                return 8194;
            case android.mtp.MtpConstants.DEVICE_PROPERTY_SESSION_INITIATOR_VERSION_INFO /* 54278 */:
                this.mHostType = str;
                android.util.Log.d(TAG, "setDeviceProperty." + java.lang.Integer.toHexString(i) + "=" + str);
                if (str.startsWith("Android/")) {
                    this.mSkipThumbForHost = true;
                } else if (str.startsWith("Windows/")) {
                    this.mHostIsWindows = true;
                }
                return 8193;
            default:
                return 8202;
        }
    }

    private boolean getObjectInfo(int i, int[] iArr, char[] cArr, long[] jArr) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return false;
        }
        iArr[0] = object.getStorageId();
        iArr[1] = object.getFormat();
        iArr[2] = object.getParent().isRoot() ? 0 : object.getParent().getId();
        int min = java.lang.Integer.min(object.getName().length(), 255);
        object.getName().getChars(0, min, cArr, 0);
        cArr[min] = 0;
        jArr[0] = object.getModifiedTime();
        jArr[1] = object.getModifiedTime();
        return true;
    }

    private int getObjectFilePath(int i, char[] cArr, long[] jArr) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return 8201;
        }
        java.lang.String path = object.getPath().toString();
        int min = java.lang.Integer.min(path.length(), 4096);
        path.getChars(0, min, cArr, 0);
        cArr[min] = 0;
        jArr[0] = object.getSize();
        jArr[1] = object.getFormat();
        return 8193;
    }

    private int openFilePath(java.lang.String str, boolean z) {
        android.net.Uri scanFile = android.provider.MediaStore.scanFile(this.mContext.getContentResolver(), new java.io.File(str));
        if (scanFile == null) {
            android.util.Log.i(TAG, "Failed to obtain URI for openFile with transcode support: " + str);
            return -1;
        }
        try {
            android.util.Log.i(TAG, "openFile with transcode support: " + str);
            android.os.Bundle bundle = new android.os.Bundle();
            if (z) {
                bundle.putParcelable("android.provider.extra.MEDIA_CAPABILITIES", new android.media.ApplicationMediaCapabilities.Builder().addUnsupportedVideoMimeType(android.media.MediaFormat.MIMETYPE_VIDEO_HEVC).build());
            } else {
                bundle.putParcelable("android.provider.extra.MEDIA_CAPABILITIES", new android.media.ApplicationMediaCapabilities.Builder().addSupportedVideoMimeType(android.media.MediaFormat.MIMETYPE_VIDEO_HEVC).build());
            }
            return this.mMediaProvider.openTypedAssetFileDescriptor(scanFile, "*/*", bundle).getParcelFileDescriptor().detachFd();
        } catch (android.os.RemoteException | java.io.FileNotFoundException e) {
            android.util.Log.w(TAG, "Failed to openFile with transcode support: " + str, e);
            return -1;
        }
    }

    private int getObjectFormat(int i) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return -1;
        }
        return object.getFormat();
    }

    private byte[] getThumbnailProcess(java.lang.String str, android.graphics.Bitmap bitmap) {
        try {
            if (bitmap == null) {
                android.util.Log.d(TAG, "getThumbnailProcess: Fail to generate thumbnail. Probably unsupported or corrupted image");
                return null;
            }
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            if (byteArrayOutputStream.size() > MAX_THUMB_SIZE) {
                android.util.Log.w(TAG, "getThumbnailProcess: size=" + byteArrayOutputStream.size());
                return null;
            }
            return byteArrayOutputStream.toByteArray();
        } catch (java.lang.OutOfMemoryError e) {
            android.util.Log.w(TAG, "OutOfMemoryError:" + e);
            return null;
        }
    }

    public boolean getThumbnailInfo(int i, long[] jArr) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return false;
        }
        java.lang.String path = object.getPath().toString();
        switch (object.getFormat()) {
            case android.mtp.MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
            case android.mtp.MtpConstants.FORMAT_JFIF /* 14344 */:
            case android.mtp.MtpConstants.FORMAT_HEIF /* 14354 */:
                try {
                    android.media.ExifInterface exifInterface = new android.media.ExifInterface(path);
                    long[] thumbnailRange = exifInterface.getThumbnailRange();
                    jArr[0] = thumbnailRange != null ? thumbnailRange[1] : 0L;
                    jArr[1] = exifInterface.getAttributeInt(android.media.ExifInterface.TAG_PIXEL_X_DIMENSION, 0);
                    jArr[2] = exifInterface.getAttributeInt(android.media.ExifInterface.TAG_PIXEL_Y_DIMENSION, 0);
                    if (this.mSkipThumbForHost) {
                        android.util.Log.d(TAG, "getThumbnailInfo: Skip runtime thumbnail.");
                        return true;
                    }
                    if (exifInterface.getThumbnailRange() != null) {
                        if (jArr[0] == 0 || jArr[1] == 0 || jArr[2] == 0) {
                            android.util.Log.d(TAG, "getThumbnailInfo: check thumb info:" + thumbnailRange[0] + "," + thumbnailRange[1] + "," + jArr[1] + "," + jArr[2]);
                        }
                        return true;
                    }
                } catch (java.io.IOException e) {
                    break;
                }
                break;
            case android.mtp.MtpConstants.FORMAT_BMP /* 14340 */:
            case android.mtp.MtpConstants.FORMAT_GIF /* 14343 */:
            case android.mtp.MtpConstants.FORMAT_PNG /* 14347 */:
                break;
            default:
                return false;
        }
        jArr[0] = 204800;
        jArr[1] = 320;
        jArr[2] = 240;
        return true;
    }

    public byte[] getThumbnailData(int i) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return null;
        }
        java.lang.String path = object.getPath().toString();
        switch (object.getFormat()) {
            case android.mtp.MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
            case android.mtp.MtpConstants.FORMAT_JFIF /* 14344 */:
            case android.mtp.MtpConstants.FORMAT_HEIF /* 14354 */:
                try {
                    android.media.ExifInterface exifInterface = new android.media.ExifInterface(path);
                    if (this.mSkipThumbForHost) {
                        android.util.Log.d(TAG, "getThumbnailData: Skip runtime thumbnail.");
                        return exifInterface.getThumbnail();
                    }
                    if (exifInterface.getThumbnailRange() != null) {
                        return exifInterface.getThumbnail();
                    }
                } catch (java.io.IOException e) {
                    break;
                }
                break;
            case android.mtp.MtpConstants.FORMAT_BMP /* 14340 */:
            case android.mtp.MtpConstants.FORMAT_GIF /* 14343 */:
            case android.mtp.MtpConstants.FORMAT_PNG /* 14347 */:
                break;
            default:
                return null;
        }
        return getThumbnailProcess(path, android.media.ThumbnailUtils.createImageThumbnail(path, 1));
    }

    private int beginDeleteObject(int i) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return 8201;
        }
        if (!this.mManager.beginRemoveObject(object)) {
            return 8194;
        }
        return 8193;
    }

    private void endDeleteObject(int i, boolean z) {
        android.mtp.MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return;
        }
        if (!this.mManager.endRemoveObject(object, z)) {
            android.util.Log.e(TAG, "Failed to end remove object");
        }
        if (z) {
            deleteFromMedia(object, object.getPath(), object.isDir());
        }
    }

    private void deleteFromMedia(android.mtp.MtpStorageManager.MtpObject mtpObject, java.nio.file.Path path, boolean z) {
        android.net.Uri contentUri = android.provider.MediaStore.Files.getContentUri(mtpObject.getVolumeName());
        if (z) {
            try {
                this.mMediaProvider.delete(contentUri, "_data LIKE ?1 AND lower(substr(_data,1,?2))=lower(?3)", new java.lang.String[]{path + "/%", java.lang.Integer.toString(path.toString().length() + 1), path.toString() + "/"});
            } catch (java.lang.Exception e) {
                android.util.Log.d(TAG, "Failed to delete " + path + " from MediaProvider");
                return;
            }
        }
        if (this.mMediaProvider.delete(contentUri, PATH_WHERE, new java.lang.String[]{path.toString()}) == 0) {
            android.util.Log.i(TAG, "MediaProvider didn't delete " + path);
        }
        updateMediaStore(this.mContext, path.toFile());
    }

    private int[] getObjectReferences(int i) {
        return null;
    }

    private int setObjectReferences(int i, int[] iArr) {
        return 8197;
    }
}
