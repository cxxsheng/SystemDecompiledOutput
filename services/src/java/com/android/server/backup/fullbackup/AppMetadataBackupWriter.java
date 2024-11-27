package com.android.server.backup.fullbackup;

/* loaded from: classes.dex */
public class AppMetadataBackupWriter {
    private final android.app.backup.FullBackupDataOutput mOutput;
    private final android.content.pm.PackageManager mPackageManager;

    public AppMetadataBackupWriter(android.app.backup.FullBackupDataOutput fullBackupDataOutput, android.content.pm.PackageManager packageManager) {
        this.mOutput = fullBackupDataOutput;
        this.mPackageManager = packageManager;
    }

    public void backupManifest(android.content.pm.PackageInfo packageInfo, java.io.File file, java.io.File file2, boolean z) throws java.io.IOException {
        backupManifest(packageInfo, file, file2, null, null, z);
    }

    public void backupManifest(android.content.pm.PackageInfo packageInfo, java.io.File file, java.io.File file2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, boolean z) throws java.io.IOException {
        byte[] manifestBytes = getManifestBytes(packageInfo, z);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
        fileOutputStream.write(manifestBytes);
        fileOutputStream.close();
        file.setLastModified(0L);
        android.app.backup.FullBackup.backupToTar(packageInfo.packageName, str, str2, file2.getAbsolutePath(), file.getAbsolutePath(), this.mOutput);
    }

    private byte[] getManifestBytes(android.content.pm.PackageInfo packageInfo, boolean z) {
        java.lang.String str = packageInfo.packageName;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(4096);
        android.util.StringBuilderPrinter stringBuilderPrinter = new android.util.StringBuilderPrinter(sb);
        stringBuilderPrinter.println(java.lang.Integer.toString(1));
        stringBuilderPrinter.println(str);
        stringBuilderPrinter.println(java.lang.Long.toString(packageInfo.getLongVersionCode()));
        stringBuilderPrinter.println(java.lang.Integer.toString(android.os.Build.VERSION.SDK_INT));
        java.lang.String installerPackageName = this.mPackageManager.getInstallerPackageName(str);
        if (installerPackageName == null) {
            installerPackageName = "";
        }
        stringBuilderPrinter.println(installerPackageName);
        stringBuilderPrinter.println(z ? "1" : "0");
        android.content.pm.SigningInfo signingInfo = packageInfo.signingInfo;
        if (signingInfo == null) {
            stringBuilderPrinter.println("0");
        } else {
            android.content.pm.Signature[] apkContentsSigners = signingInfo.getApkContentsSigners();
            stringBuilderPrinter.println(java.lang.Integer.toString(apkContentsSigners.length));
            for (android.content.pm.Signature signature : apkContentsSigners) {
                stringBuilderPrinter.println(signature.toCharsString());
            }
        }
        return sb.toString().getBytes();
    }

    public void backupWidget(android.content.pm.PackageInfo packageInfo, java.io.File file, java.io.File file2, byte[] bArr) throws java.io.IOException {
        com.android.internal.util.Preconditions.checkArgument(bArr.length > 0, "Can't backup widget with no data.");
        java.lang.String str = packageInfo.packageName;
        java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream(file));
        java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(bufferedOutputStream);
        bufferedOutputStream.write(getMetadataBytes(str));
        writeWidgetData(dataOutputStream, bArr);
        bufferedOutputStream.flush();
        dataOutputStream.close();
        file.setLastModified(0L);
        android.app.backup.FullBackup.backupToTar(str, (java.lang.String) null, (java.lang.String) null, file2.getAbsolutePath(), file.getAbsolutePath(), this.mOutput);
    }

    private byte[] getMetadataBytes(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(512);
        android.util.StringBuilderPrinter stringBuilderPrinter = new android.util.StringBuilderPrinter(sb);
        stringBuilderPrinter.println(java.lang.Integer.toString(1));
        stringBuilderPrinter.println(str);
        return sb.toString().getBytes();
    }

    private void writeWidgetData(java.io.DataOutputStream dataOutputStream, byte[] bArr) throws java.io.IOException {
        dataOutputStream.writeInt(com.android.server.backup.UserBackupManagerService.BACKUP_WIDGET_METADATA_TOKEN);
        dataOutputStream.writeInt(bArr.length);
        dataOutputStream.write(bArr);
    }

    public void backupApk(android.content.pm.PackageInfo packageInfo) {
        java.lang.String baseCodePath = packageInfo.applicationInfo.getBaseCodePath();
        android.app.backup.FullBackup.backupToTar(packageInfo.packageName, com.android.server.wm.ActivityTaskManagerService.DUMP_ACTIVITIES_SHORT_CMD, (java.lang.String) null, new java.io.File(baseCodePath).getParent(), baseCodePath, this.mOutput);
    }

    public void backupObb(int i, android.content.pm.PackageInfo packageInfo) {
        java.io.File[] listFiles;
        java.io.File file = new android.os.Environment.UserEnvironment(i).buildExternalStorageAppObbDirs(packageInfo.packageName)[0];
        if (file != null && (listFiles = file.listFiles()) != null) {
            java.lang.String absolutePath = file.getAbsolutePath();
            for (java.io.File file2 : listFiles) {
                android.app.backup.FullBackup.backupToTar(packageInfo.packageName, "obb", (java.lang.String) null, absolutePath, file2.getAbsolutePath(), this.mOutput);
            }
        }
    }
}
