package android.app.backup;

/* loaded from: classes.dex */
public class WallpaperBackupHelper extends android.app.backup.FileBackupHelperBase implements android.app.backup.BackupHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String STAGE_FILE = new java.io.File(android.os.Environment.getUserSystemDirectory(0), "wallpaper-tmp").getAbsolutePath();
    private static final java.lang.String TAG = "WallpaperBackupHelper";
    public static final java.lang.String WALLPAPER_IMAGE_KEY = "/data/data/com.android.settings/files/wallpaper";
    public static final java.lang.String WALLPAPER_INFO_KEY = "/data/system/wallpaper_info.xml";
    private final java.lang.String[] mKeys;
    private final android.app.WallpaperManager mWpm;

    @Override // android.app.backup.FileBackupHelperBase, android.app.backup.BackupHelper
    public /* bridge */ /* synthetic */ void writeNewStateDescription(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        super.writeNewStateDescription(parcelFileDescriptor);
    }

    public WallpaperBackupHelper(android.content.Context context, java.lang.String[] strArr) {
        super(context);
        this.mContext = context;
        this.mKeys = strArr;
        this.mWpm = (android.app.WallpaperManager) context.getSystemService(android.content.Context.WALLPAPER_SERVICE);
    }

    @Override // android.app.backup.BackupHelper
    public void performBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
    }

    @Override // android.app.backup.BackupHelper
    public void restoreEntity(android.app.backup.BackupDataInputStream backupDataInputStream) {
        if (this.mWpm == null) {
            android.util.Slog.w(TAG, "restoreEntity(): no wallpaper service");
            return;
        }
        java.lang.String key = backupDataInputStream.getKey();
        if (isKeyInList(key, this.mKeys) && key.equals(WALLPAPER_IMAGE_KEY)) {
            java.io.File file = new java.io.File(STAGE_FILE);
            try {
                if (writeFile(file, backupDataInputStream)) {
                    try {
                        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                        try {
                            this.mWpm.setStream(fileInputStream);
                            fileInputStream.close();
                        } catch (java.lang.Throwable th) {
                            try {
                                fileInputStream.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "Unable to set restored wallpaper: " + e.getMessage());
                    }
                } else {
                    android.util.Slog.e(TAG, "Unable to save restored wallpaper");
                }
            } finally {
                file.delete();
            }
        }
    }
}
