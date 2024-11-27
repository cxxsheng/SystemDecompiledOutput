package android.content;

/* loaded from: classes.dex */
public class ContextWrapper extends android.content.Context {
    android.content.Context mBase;
    public java.util.List<android.content.ComponentCallbacks> mCallbacksRegisteredToSuper;
    private final java.lang.Object mLock = new java.lang.Object();

    public ContextWrapper(android.content.Context context) {
        this.mBase = context;
    }

    protected void attachBaseContext(android.content.Context context) {
        if (this.mBase != null) {
            throw new java.lang.IllegalStateException("Base context already set");
        }
        this.mBase = context;
    }

    public android.content.Context getBaseContext() {
        return this.mBase;
    }

    @Override // android.content.Context
    public android.content.res.AssetManager getAssets() {
        return this.mBase.getAssets();
    }

    @Override // android.content.Context
    public android.content.res.Resources getResources() {
        return this.mBase.getResources();
    }

    @Override // android.content.Context
    public android.content.pm.PackageManager getPackageManager() {
        return this.mBase.getPackageManager();
    }

    @Override // android.content.Context
    public android.content.ContentResolver getContentResolver() {
        return this.mBase.getContentResolver();
    }

    @Override // android.content.Context
    public android.os.Looper getMainLooper() {
        return this.mBase.getMainLooper();
    }

    @Override // android.content.Context
    public java.util.concurrent.Executor getMainExecutor() {
        return this.mBase.getMainExecutor();
    }

    @Override // android.content.Context
    public android.content.Context getApplicationContext() {
        return this.mBase.getApplicationContext();
    }

    @Override // android.content.Context
    public void setTheme(int i) {
        this.mBase.setTheme(i);
    }

    @Override // android.content.Context
    public int getThemeResId() {
        return this.mBase.getThemeResId();
    }

    @Override // android.content.Context
    public android.content.res.Resources.Theme getTheme() {
        return this.mBase.getTheme();
    }

    @Override // android.content.Context
    public java.lang.ClassLoader getClassLoader() {
        return this.mBase.getClassLoader();
    }

    @Override // android.content.Context
    public java.lang.String getPackageName() {
        return this.mBase.getPackageName();
    }

    @Override // android.content.Context
    public java.lang.String getBasePackageName() {
        return this.mBase.getBasePackageName();
    }

    @Override // android.content.Context
    public java.lang.String getOpPackageName() {
        return this.mBase.getOpPackageName();
    }

    @Override // android.content.Context
    public java.lang.String getAttributionTag() {
        return this.mBase.getAttributionTag();
    }

    @Override // android.content.Context
    public android.content.ContextParams getParams() {
        return this.mBase.getParams();
    }

    @Override // android.content.Context
    public android.content.pm.ApplicationInfo getApplicationInfo() {
        return this.mBase.getApplicationInfo();
    }

    @Override // android.content.Context
    public java.lang.String getPackageResourcePath() {
        return this.mBase.getPackageResourcePath();
    }

    @Override // android.content.Context
    public java.lang.String getPackageCodePath() {
        return this.mBase.getPackageCodePath();
    }

    @Override // android.content.Context
    public android.content.SharedPreferences getSharedPreferences(java.lang.String str, int i) {
        return this.mBase.getSharedPreferences(str, i);
    }

    @Override // android.content.Context
    public android.content.SharedPreferences getSharedPreferences(java.io.File file, int i) {
        return this.mBase.getSharedPreferences(file, i);
    }

    @Override // android.content.Context
    public void reloadSharedPreferences() {
        this.mBase.reloadSharedPreferences();
    }

    @Override // android.content.Context
    public boolean moveSharedPreferencesFrom(android.content.Context context, java.lang.String str) {
        return this.mBase.moveSharedPreferencesFrom(context, str);
    }

    @Override // android.content.Context
    public boolean deleteSharedPreferences(java.lang.String str) {
        return this.mBase.deleteSharedPreferences(str);
    }

    @Override // android.content.Context
    public java.io.FileInputStream openFileInput(java.lang.String str) throws java.io.FileNotFoundException {
        return this.mBase.openFileInput(str);
    }

    @Override // android.content.Context
    public java.io.FileOutputStream openFileOutput(java.lang.String str, int i) throws java.io.FileNotFoundException {
        return this.mBase.openFileOutput(str, i);
    }

    @Override // android.content.Context
    public boolean deleteFile(java.lang.String str) {
        return this.mBase.deleteFile(str);
    }

    @Override // android.content.Context
    public java.io.File getFileStreamPath(java.lang.String str) {
        return this.mBase.getFileStreamPath(str);
    }

    @Override // android.content.Context
    public java.io.File getSharedPreferencesPath(java.lang.String str) {
        return this.mBase.getSharedPreferencesPath(str);
    }

    @Override // android.content.Context
    public java.lang.String[] fileList() {
        return this.mBase.fileList();
    }

    @Override // android.content.Context
    public java.io.File getDataDir() {
        return this.mBase.getDataDir();
    }

    @Override // android.content.Context
    public java.io.File getFilesDir() {
        return this.mBase.getFilesDir();
    }

    @Override // android.content.Context
    public java.io.File getCrateDir(java.lang.String str) {
        return this.mBase.getCrateDir(str);
    }

    @Override // android.content.Context
    public java.io.File getNoBackupFilesDir() {
        return this.mBase.getNoBackupFilesDir();
    }

    @Override // android.content.Context
    public java.io.File getExternalFilesDir(java.lang.String str) {
        return this.mBase.getExternalFilesDir(str);
    }

    @Override // android.content.Context
    public java.io.File[] getExternalFilesDirs(java.lang.String str) {
        return this.mBase.getExternalFilesDirs(str);
    }

    @Override // android.content.Context
    public java.io.File getObbDir() {
        return this.mBase.getObbDir();
    }

    @Override // android.content.Context
    public java.io.File[] getObbDirs() {
        return this.mBase.getObbDirs();
    }

    @Override // android.content.Context
    public java.io.File getCacheDir() {
        return this.mBase.getCacheDir();
    }

    @Override // android.content.Context
    public java.io.File getCodeCacheDir() {
        return this.mBase.getCodeCacheDir();
    }

    @Override // android.content.Context
    public java.io.File getExternalCacheDir() {
        return this.mBase.getExternalCacheDir();
    }

    @Override // android.content.Context
    public java.io.File[] getExternalCacheDirs() {
        return this.mBase.getExternalCacheDirs();
    }

    @Override // android.content.Context
    public java.io.File[] getExternalMediaDirs() {
        return this.mBase.getExternalMediaDirs();
    }

    @Override // android.content.Context
    public java.io.File getDir(java.lang.String str, int i) {
        return this.mBase.getDir(str, i);
    }

    @Override // android.content.Context
    public java.io.File getPreloadsFileCache() {
        return this.mBase.getPreloadsFileCache();
    }

    @Override // android.content.Context
    public android.database.sqlite.SQLiteDatabase openOrCreateDatabase(java.lang.String str, int i, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory) {
        return this.mBase.openOrCreateDatabase(str, i, cursorFactory);
    }

    @Override // android.content.Context
    public android.database.sqlite.SQLiteDatabase openOrCreateDatabase(java.lang.String str, int i, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, android.database.DatabaseErrorHandler databaseErrorHandler) {
        return this.mBase.openOrCreateDatabase(str, i, cursorFactory, databaseErrorHandler);
    }

    @Override // android.content.Context
    public boolean moveDatabaseFrom(android.content.Context context, java.lang.String str) {
        return this.mBase.moveDatabaseFrom(context, str);
    }

    @Override // android.content.Context
    public boolean deleteDatabase(java.lang.String str) {
        return this.mBase.deleteDatabase(str);
    }

    @Override // android.content.Context
    public java.io.File getDatabasePath(java.lang.String str) {
        return this.mBase.getDatabasePath(str);
    }

    @Override // android.content.Context
    public java.lang.String[] databaseList() {
        return this.mBase.databaseList();
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public android.graphics.drawable.Drawable getWallpaper() {
        return this.mBase.getWallpaper();
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public android.graphics.drawable.Drawable peekWallpaper() {
        return this.mBase.peekWallpaper();
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public int getWallpaperDesiredMinimumWidth() {
        return this.mBase.getWallpaperDesiredMinimumWidth();
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public int getWallpaperDesiredMinimumHeight() {
        return this.mBase.getWallpaperDesiredMinimumHeight();
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void setWallpaper(android.graphics.Bitmap bitmap) throws java.io.IOException {
        this.mBase.setWallpaper(bitmap);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void setWallpaper(java.io.InputStream inputStream) throws java.io.IOException {
        this.mBase.setWallpaper(inputStream);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void clearWallpaper() throws java.io.IOException {
        this.mBase.clearWallpaper();
    }

    @Override // android.content.Context
    public void startActivity(android.content.Intent intent) {
        this.mBase.startActivity(intent);
    }

    @Override // android.content.Context
    public void startActivityAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        this.mBase.startActivityAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public void startActivityForResult(java.lang.String str, android.content.Intent intent, int i, android.os.Bundle bundle) {
        this.mBase.startActivityForResult(str, intent, i, bundle);
    }

    @Override // android.content.Context
    public boolean canStartActivityForResult() {
        return this.mBase.canStartActivityForResult();
    }

    @Override // android.content.Context
    public void startActivity(android.content.Intent intent, android.os.Bundle bundle) {
        this.mBase.startActivity(intent, bundle);
    }

    @Override // android.content.Context
    public void startActivityAsUser(android.content.Intent intent, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        this.mBase.startActivityAsUser(intent, bundle, userHandle);
    }

    @Override // android.content.Context
    public void startActivities(android.content.Intent[] intentArr) {
        this.mBase.startActivities(intentArr);
    }

    @Override // android.content.Context
    public void startActivities(android.content.Intent[] intentArr, android.os.Bundle bundle) {
        this.mBase.startActivities(intentArr, bundle);
    }

    @Override // android.content.Context
    public int startActivitiesAsUser(android.content.Intent[] intentArr, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        return this.mBase.startActivitiesAsUser(intentArr, bundle, userHandle);
    }

    @Override // android.content.Context
    public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent, int i, int i2, int i3) throws android.content.IntentSender.SendIntentException {
        this.mBase.startIntentSender(intentSender, intent, i, i2, i3);
    }

    @Override // android.content.Context
    public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent, int i, int i2, int i3, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        this.mBase.startIntentSender(intentSender, intent, i, i2, i3, bundle);
    }

    @Override // android.content.Context
    public void sendBroadcast(android.content.Intent intent) {
        this.mBase.sendBroadcast(intent);
    }

    @Override // android.content.Context
    public void sendBroadcast(android.content.Intent intent, java.lang.String str) {
        this.mBase.sendBroadcast(intent, str);
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr) {
        this.mBase.sendBroadcastMultiplePermissions(intent, strArr);
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, android.app.BroadcastOptions broadcastOptions) {
        this.mBase.sendBroadcastMultiplePermissions(intent, strArr, strArr2, strArr3, broadcastOptions);
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr, android.os.Bundle bundle) {
        this.mBase.sendBroadcastMultiplePermissions(intent, strArr, bundle);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUserMultiplePermissions(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String[] strArr) {
        this.mBase.sendBroadcastAsUserMultiplePermissions(intent, userHandle, strArr);
    }

    @Override // android.content.Context
    public void sendBroadcast(android.content.Intent intent, java.lang.String str, android.os.Bundle bundle) {
        this.mBase.sendBroadcast(intent, str, bundle);
    }

    @Override // android.content.Context
    public void sendBroadcast(android.content.Intent intent, java.lang.String str, int i) {
        this.mBase.sendBroadcast(intent, str, i);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str) {
        this.mBase.sendOrderedBroadcast(intent, str);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, android.os.Bundle bundle) {
        this.mBase.sendOrderedBroadcast(intent, str, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str2, android.os.Bundle bundle) {
        this.mBase.sendOrderedBroadcast(intent, str, broadcastReceiver, handler, i, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, android.os.Bundle bundle, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str2, android.os.Bundle bundle2) {
        this.mBase.sendOrderedBroadcast(intent, str, bundle, broadcastReceiver, handler, i, str2, bundle2);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, int i, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i2, java.lang.String str2, android.os.Bundle bundle) {
        this.mBase.sendOrderedBroadcast(intent, str, i, broadcastReceiver, handler, i2, str2, bundle);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        this.mBase.sendBroadcastAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str) {
        this.mBase.sendBroadcastAsUser(intent, userHandle, str);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, android.os.Bundle bundle) {
        this.mBase.sendBroadcastAsUser(intent, userHandle, str, bundle);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, int i) {
        this.mBase.sendBroadcastAsUser(intent, userHandle, str, i);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str2, android.os.Bundle bundle) {
        this.mBase.sendOrderedBroadcastAsUser(intent, userHandle, str, broadcastReceiver, handler, i, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, int i, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i2, java.lang.String str2, android.os.Bundle bundle) {
        this.mBase.sendOrderedBroadcastAsUser(intent, userHandle, str, i, broadcastReceiver, handler, i2, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, int i, android.os.Bundle bundle, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i2, java.lang.String str2, android.os.Bundle bundle2) {
        this.mBase.sendOrderedBroadcastAsUser(intent, userHandle, str, i, bundle, broadcastReceiver, handler, i2, str2, bundle2);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, java.lang.String str2, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str3, android.os.Bundle bundle) {
        this.mBase.sendOrderedBroadcast(intent, str, str2, broadcastReceiver, handler, i, str3, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, int i, java.lang.String str, java.lang.String str2, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, java.lang.String str3, android.os.Bundle bundle, android.os.Bundle bundle2) {
        this.mBase.sendOrderedBroadcast(intent, i, str, str2, broadcastReceiver, handler, str3, bundle, bundle2);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyBroadcast(android.content.Intent intent) {
        this.mBase.sendStickyBroadcast(intent);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyBroadcast(android.content.Intent intent, android.os.Bundle bundle) {
        this.mBase.sendStickyBroadcast(intent, bundle);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyOrderedBroadcast(android.content.Intent intent, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str, android.os.Bundle bundle) {
        this.mBase.sendStickyOrderedBroadcast(intent, broadcastReceiver, handler, i, str, bundle);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void removeStickyBroadcast(android.content.Intent intent) {
        this.mBase.removeStickyBroadcast(intent);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        this.mBase.sendStickyBroadcastAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, android.os.Bundle bundle) {
        this.mBase.sendStickyBroadcastAsUser(intent, userHandle, bundle);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str, android.os.Bundle bundle) {
        this.mBase.sendStickyOrderedBroadcastAsUser(intent, userHandle, broadcastReceiver, handler, i, str, bundle);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void removeStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        this.mBase.removeStickyBroadcastAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, int i) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter, i);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter, str, handler);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler, int i) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter, str, handler, i);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiverForAllUsers(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler) {
        return this.mBase.registerReceiverForAllUsers(broadcastReceiver, intentFilter, str, handler);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiverForAllUsers(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler, int i) {
        return this.mBase.registerReceiverForAllUsers(broadcastReceiver, intentFilter, str, handler, i);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiverAsUser(android.content.BroadcastReceiver broadcastReceiver, android.os.UserHandle userHandle, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler) {
        return this.mBase.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, str, handler);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiverAsUser(android.content.BroadcastReceiver broadcastReceiver, android.os.UserHandle userHandle, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler, int i) {
        return this.mBase.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, str, handler, i);
    }

    @Override // android.content.Context
    public void unregisterReceiver(android.content.BroadcastReceiver broadcastReceiver) {
        this.mBase.unregisterReceiver(broadcastReceiver);
    }

    @Override // android.content.Context
    public android.content.ComponentName startService(android.content.Intent intent) {
        return this.mBase.startService(intent);
    }

    @Override // android.content.Context
    public android.content.ComponentName startForegroundService(android.content.Intent intent) {
        return this.mBase.startForegroundService(intent);
    }

    @Override // android.content.Context
    public boolean stopService(android.content.Intent intent) {
        return this.mBase.stopService(intent);
    }

    @Override // android.content.Context
    public android.content.ComponentName startServiceAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        return this.mBase.startServiceAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public android.content.ComponentName startForegroundServiceAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        return this.mBase.startForegroundServiceAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public boolean stopServiceAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        return this.mBase.stopServiceAsUser(intent, userHandle);
    }

    @Override // android.content.Context
    public boolean bindService(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i) {
        return this.mBase.bindService(intent, serviceConnection, i);
    }

    @Override // android.content.Context
    public boolean bindService(android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.content.Context.BindServiceFlags bindServiceFlags) {
        return this.mBase.bindService(intent, serviceConnection, bindServiceFlags);
    }

    @Override // android.content.Context
    public boolean bindService(android.content.Intent intent, int i, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        return this.mBase.bindService(intent, i, executor, serviceConnection);
    }

    @Override // android.content.Context
    public boolean bindService(android.content.Intent intent, android.content.Context.BindServiceFlags bindServiceFlags, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        return this.mBase.bindService(intent, bindServiceFlags, executor, serviceConnection);
    }

    @Override // android.content.Context
    public boolean bindIsolatedService(android.content.Intent intent, int i, java.lang.String str, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        return this.mBase.bindIsolatedService(intent, i, str, executor, serviceConnection);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i, android.os.UserHandle userHandle) {
        return this.mBase.bindServiceAsUser(intent, serviceConnection, i, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.content.Context.BindServiceFlags bindServiceFlags, android.os.UserHandle userHandle) {
        return this.mBase.bindServiceAsUser(intent, serviceConnection, bindServiceFlags, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i, android.os.Handler handler, android.os.UserHandle userHandle) {
        return this.mBase.bindServiceAsUser(intent, serviceConnection, i, handler, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.content.Context.BindServiceFlags bindServiceFlags, android.os.Handler handler, android.os.UserHandle userHandle) {
        return this.mBase.bindServiceAsUser(intent, serviceConnection, bindServiceFlags, handler, userHandle);
    }

    @Override // android.content.Context
    public void updateServiceGroup(android.content.ServiceConnection serviceConnection, int i, int i2) {
        this.mBase.updateServiceGroup(serviceConnection, i, i2);
    }

    @Override // android.content.Context
    public void unbindService(android.content.ServiceConnection serviceConnection) {
        this.mBase.unbindService(serviceConnection);
    }

    @Override // android.content.Context
    public boolean startInstrumentation(android.content.ComponentName componentName, java.lang.String str, android.os.Bundle bundle) {
        return this.mBase.startInstrumentation(componentName, str, bundle);
    }

    @Override // android.content.Context
    public java.lang.Object getSystemService(java.lang.String str) {
        return this.mBase.getSystemService(str);
    }

    @Override // android.content.Context
    public java.lang.String getSystemServiceName(java.lang.Class<?> cls) {
        return this.mBase.getSystemServiceName(cls);
    }

    @Override // android.content.Context
    public int checkPermission(java.lang.String str, int i, int i2) {
        return this.mBase.checkPermission(str, i, i2);
    }

    @Override // android.content.Context
    public int checkPermission(java.lang.String str, int i, int i2, android.os.IBinder iBinder) {
        return this.mBase.checkPermission(str, i, i2, iBinder);
    }

    @Override // android.content.Context
    public int checkCallingPermission(java.lang.String str) {
        return this.mBase.checkCallingPermission(str);
    }

    @Override // android.content.Context
    public int checkCallingOrSelfPermission(java.lang.String str) {
        return this.mBase.checkCallingOrSelfPermission(str);
    }

    @Override // android.content.Context
    public int checkSelfPermission(java.lang.String str) {
        return this.mBase.checkSelfPermission(str);
    }

    @Override // android.content.Context
    public void enforcePermission(java.lang.String str, int i, int i2, java.lang.String str2) {
        this.mBase.enforcePermission(str, i, i2, str2);
    }

    @Override // android.content.Context
    public void enforceCallingPermission(java.lang.String str, java.lang.String str2) {
        this.mBase.enforceCallingPermission(str, str2);
    }

    @Override // android.content.Context
    public void enforceCallingOrSelfPermission(java.lang.String str, java.lang.String str2) {
        this.mBase.enforceCallingOrSelfPermission(str, str2);
    }

    @Override // android.content.Context
    public void grantUriPermission(java.lang.String str, android.net.Uri uri, int i) {
        this.mBase.grantUriPermission(str, uri, i);
    }

    @Override // android.content.Context
    public void revokeUriPermission(android.net.Uri uri, int i) {
        this.mBase.revokeUriPermission(uri, i);
    }

    @Override // android.content.Context
    public void revokeUriPermission(java.lang.String str, android.net.Uri uri, int i) {
        this.mBase.revokeUriPermission(str, uri, i);
    }

    @Override // android.content.Context
    public int checkUriPermission(android.net.Uri uri, int i, int i2, int i3) {
        return this.mBase.checkUriPermission(uri, i, i2, i3);
    }

    @Override // android.content.Context
    public int checkContentUriPermissionFull(android.net.Uri uri, int i, int i2, int i3) {
        return this.mBase.checkContentUriPermissionFull(uri, i, i2, i3);
    }

    @Override // android.content.Context
    public int[] checkUriPermissions(java.util.List<android.net.Uri> list, int i, int i2, int i3) {
        return this.mBase.checkUriPermissions(list, i, i2, i3);
    }

    @Override // android.content.Context
    public int checkUriPermission(android.net.Uri uri, int i, int i2, int i3, android.os.IBinder iBinder) {
        return this.mBase.checkUriPermission(uri, i, i2, i3, iBinder);
    }

    @Override // android.content.Context
    public int checkCallingUriPermission(android.net.Uri uri, int i) {
        return this.mBase.checkCallingUriPermission(uri, i);
    }

    @Override // android.content.Context
    public int[] checkCallingUriPermissions(java.util.List<android.net.Uri> list, int i) {
        return this.mBase.checkCallingUriPermissions(list, i);
    }

    @Override // android.content.Context
    public int checkCallingOrSelfUriPermission(android.net.Uri uri, int i) {
        return this.mBase.checkCallingOrSelfUriPermission(uri, i);
    }

    @Override // android.content.Context
    public int[] checkCallingOrSelfUriPermissions(java.util.List<android.net.Uri> list, int i) {
        return this.mBase.checkCallingOrSelfUriPermissions(list, i);
    }

    @Override // android.content.Context
    public int checkUriPermission(android.net.Uri uri, java.lang.String str, java.lang.String str2, int i, int i2, int i3) {
        return this.mBase.checkUriPermission(uri, str, str2, i, i2, i3);
    }

    @Override // android.content.Context
    public void enforceUriPermission(android.net.Uri uri, int i, int i2, int i3, java.lang.String str) {
        this.mBase.enforceUriPermission(uri, i, i2, i3, str);
    }

    @Override // android.content.Context
    public void enforceCallingUriPermission(android.net.Uri uri, int i, java.lang.String str) {
        this.mBase.enforceCallingUriPermission(uri, i, str);
    }

    @Override // android.content.Context
    public void enforceCallingOrSelfUriPermission(android.net.Uri uri, int i, java.lang.String str) {
        this.mBase.enforceCallingOrSelfUriPermission(uri, i, str);
    }

    @Override // android.content.Context
    public void enforceUriPermission(android.net.Uri uri, java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3) {
        this.mBase.enforceUriPermission(uri, str, str2, i, i2, i3, str3);
    }

    @Override // android.content.Context
    public void revokeSelfPermissionsOnKill(java.util.Collection<java.lang.String> collection) {
        this.mBase.revokeSelfPermissionsOnKill(collection);
    }

    @Override // android.content.Context
    public android.content.Context createPackageContext(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return this.mBase.createPackageContext(str, i);
    }

    @Override // android.content.Context
    public android.content.Context createPackageContextAsUser(java.lang.String str, int i, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException {
        return this.mBase.createPackageContextAsUser(str, i, userHandle);
    }

    @Override // android.content.Context
    public android.content.Context createContextAsUser(android.os.UserHandle userHandle, int i) {
        return this.mBase.createContextAsUser(userHandle, i);
    }

    @Override // android.content.Context
    public android.content.Context createApplicationContext(android.content.pm.ApplicationInfo applicationInfo, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return this.mBase.createApplicationContext(applicationInfo, i);
    }

    @Override // android.content.Context
    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public android.content.Context createContextForSdkInSandbox(android.content.pm.ApplicationInfo applicationInfo, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return this.mBase.createContextForSdkInSandbox(applicationInfo, i);
    }

    @Override // android.content.Context
    public android.content.Context createContextForSplit(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        return this.mBase.createContextForSplit(str);
    }

    @Override // android.content.Context
    public int getUserId() {
        return this.mBase.getUserId();
    }

    @Override // android.content.Context
    public android.os.UserHandle getUser() {
        return this.mBase.getUser();
    }

    @Override // android.content.Context
    public android.content.Context createConfigurationContext(android.content.res.Configuration configuration) {
        return this.mBase.createConfigurationContext(configuration);
    }

    @Override // android.content.Context
    public android.content.Context createDisplayContext(android.view.Display display) {
        return this.mBase.createDisplayContext(display);
    }

    @Override // android.content.Context
    public android.content.Context createDeviceContext(int i) {
        return this.mBase.createDeviceContext(i);
    }

    @Override // android.content.Context
    public android.content.Context createWindowContext(int i, android.os.Bundle bundle) {
        return this.mBase.createWindowContext(i, bundle);
    }

    @Override // android.content.Context
    public android.content.Context createWindowContext(android.view.Display display, int i, android.os.Bundle bundle) {
        return this.mBase.createWindowContext(display, i, bundle);
    }

    @Override // android.content.Context
    public android.content.Context createContext(android.content.ContextParams contextParams) {
        return this.mBase.createContext(contextParams);
    }

    @Override // android.content.Context
    public android.content.Context createAttributionContext(java.lang.String str) {
        return this.mBase.createAttributionContext(str);
    }

    @Override // android.content.Context
    public android.content.AttributionSource getAttributionSource() {
        return this.mBase.getAttributionSource();
    }

    @Override // android.content.Context
    public boolean isRestricted() {
        return this.mBase.isRestricted();
    }

    @Override // android.content.Context
    public android.view.DisplayAdjustments getDisplayAdjustments(int i) {
        return this.mBase.getDisplayAdjustments(i);
    }

    @Override // android.content.Context
    public android.view.Display getDisplay() {
        return this.mBase.getDisplay();
    }

    @Override // android.content.Context
    public android.view.Display getDisplayNoVerify() {
        return this.mBase.getDisplayNoVerify();
    }

    @Override // android.content.Context
    public int getDisplayId() {
        return this.mBase.getDisplayId();
    }

    @Override // android.content.Context
    public int getAssociatedDisplayId() {
        return this.mBase.getAssociatedDisplayId();
    }

    @Override // android.content.Context
    public void updateDisplay(int i) {
        this.mBase.updateDisplay(i);
    }

    @Override // android.content.Context
    public void updateDeviceId(int i) {
        this.mBase.updateDeviceId(i);
    }

    @Override // android.content.Context
    public int getDeviceId() {
        return this.mBase.getDeviceId();
    }

    @Override // android.content.Context
    public void registerDeviceIdChangeListener(java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
        this.mBase.registerDeviceIdChangeListener(executor, intConsumer);
    }

    @Override // android.content.Context
    public void unregisterDeviceIdChangeListener(java.util.function.IntConsumer intConsumer) {
        this.mBase.unregisterDeviceIdChangeListener(intConsumer);
    }

    @Override // android.content.Context
    public android.content.Context createDeviceProtectedStorageContext() {
        return this.mBase.createDeviceProtectedStorageContext();
    }

    @Override // android.content.Context
    @android.annotation.SystemApi
    public android.content.Context createCredentialProtectedStorageContext() {
        return this.mBase.createCredentialProtectedStorageContext();
    }

    @Override // android.content.Context
    public android.content.Context createTokenContext(android.os.IBinder iBinder, android.view.Display display) {
        return this.mBase.createTokenContext(iBinder, display);
    }

    @Override // android.content.Context
    public boolean isDeviceProtectedStorage() {
        return this.mBase.isDeviceProtectedStorage();
    }

    @Override // android.content.Context
    @android.annotation.SystemApi
    public boolean isCredentialProtectedStorage() {
        return this.mBase.isCredentialProtectedStorage();
    }

    @Override // android.content.Context
    public boolean canLoadUnsafeResources() {
        return this.mBase.canLoadUnsafeResources();
    }

    @Override // android.content.Context
    public android.os.IBinder getActivityToken() {
        return this.mBase.getActivityToken();
    }

    @Override // android.content.Context
    public android.os.IBinder getWindowContextToken() {
        if (this.mBase != null) {
            return this.mBase.getWindowContextToken();
        }
        return null;
    }

    @Override // android.content.Context
    public android.app.IServiceConnection getServiceDispatcher(android.content.ServiceConnection serviceConnection, android.os.Handler handler, long j) {
        return this.mBase.getServiceDispatcher(serviceConnection, handler, j);
    }

    @Override // android.content.Context
    public android.app.IApplicationThread getIApplicationThread() {
        return this.mBase.getIApplicationThread();
    }

    @Override // android.content.Context
    public android.os.IBinder getProcessToken() {
        return this.mBase.getProcessToken();
    }

    @Override // android.content.Context
    public android.os.Handler getMainThreadHandler() {
        return this.mBase.getMainThreadHandler();
    }

    @Override // android.content.Context
    public int getNextAutofillId() {
        return this.mBase.getNextAutofillId();
    }

    @Override // android.content.Context
    public android.view.autofill.AutofillManager.AutofillClient getAutofillClient() {
        return this.mBase.getAutofillClient();
    }

    @Override // android.content.Context
    public void setAutofillClient(android.view.autofill.AutofillManager.AutofillClient autofillClient) {
        this.mBase.setAutofillClient(autofillClient);
    }

    @Override // android.content.Context
    public android.content.AutofillOptions getAutofillOptions() {
        if (this.mBase == null) {
            return null;
        }
        return this.mBase.getAutofillOptions();
    }

    @Override // android.content.Context
    public void setAutofillOptions(android.content.AutofillOptions autofillOptions) {
        if (this.mBase != null) {
            this.mBase.setAutofillOptions(autofillOptions);
        }
    }

    @Override // android.content.Context
    public android.content.ContentCaptureOptions getContentCaptureOptions() {
        if (this.mBase == null) {
            return null;
        }
        return this.mBase.getContentCaptureOptions();
    }

    @Override // android.content.Context
    public void setContentCaptureOptions(android.content.ContentCaptureOptions contentCaptureOptions) {
        if (this.mBase != null) {
            this.mBase.setContentCaptureOptions(contentCaptureOptions);
        }
    }

    @Override // android.content.Context
    public boolean isUiContext() {
        if (this.mBase == null) {
            return false;
        }
        return this.mBase.isUiContext();
    }

    @Override // android.content.Context
    public boolean isConfigurationContext() {
        if (this.mBase == null) {
            return false;
        }
        return this.mBase.isConfigurationContext();
    }

    @Override // android.content.Context
    public void registerComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        if (this.mBase != null) {
            this.mBase.registerComponentCallbacks(componentCallbacks);
            return;
        }
        if (!android.app.compat.CompatChanges.isChangeEnabled(android.content.Context.OVERRIDABLE_COMPONENT_CALLBACKS)) {
            super.registerComponentCallbacks(componentCallbacks);
            synchronized (this.mLock) {
                if (this.mCallbacksRegisteredToSuper == null) {
                    this.mCallbacksRegisteredToSuper = new java.util.ArrayList();
                }
                this.mCallbacksRegisteredToSuper.add(componentCallbacks);
            }
            return;
        }
        throw new java.lang.IllegalStateException("ComponentCallbacks must be registered after this ContextWrapper is attached to a base Context.");
    }

    @Override // android.content.Context
    public void unregisterComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        synchronized (this.mLock) {
            if (this.mCallbacksRegisteredToSuper != null && this.mCallbacksRegisteredToSuper.contains(componentCallbacks)) {
                super.unregisterComponentCallbacks(componentCallbacks);
                this.mCallbacksRegisteredToSuper.remove(componentCallbacks);
            } else if (this.mBase != null) {
                this.mBase.unregisterComponentCallbacks(componentCallbacks);
            } else if (android.app.compat.CompatChanges.isChangeEnabled(android.content.Context.OVERRIDABLE_COMPONENT_CALLBACKS)) {
                throw new java.lang.IllegalStateException("ComponentCallbacks must be unregistered after this ContextWrapper is attached to a base Context.");
            }
        }
    }

    @Override // android.content.Context
    public void closeSystemDialogs() {
        this.mBase.closeSystemDialogs();
    }
}
