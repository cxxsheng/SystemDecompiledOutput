package android.app.backup;

/* loaded from: classes.dex */
public class BackupUtils {
    private BackupUtils() {
    }

    public static boolean isFileSpecifiedInPathList(java.io.File file, java.util.Collection<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> collection) throws java.io.IOException {
        java.util.Iterator<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> it = collection.iterator();
        while (it.hasNext()) {
            java.lang.String path = it.next().getPath();
            java.io.File file2 = new java.io.File(path);
            if (file2.isDirectory()) {
                if (file.isDirectory()) {
                    if (file.equals(file2)) {
                        return true;
                    }
                } else if (file.toPath().startsWith(path)) {
                    return true;
                }
            } else if (file.equals(file2)) {
                return true;
            }
        }
        return false;
    }
}
