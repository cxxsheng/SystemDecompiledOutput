package android.app;

/* loaded from: classes.dex */
public class PackageDeleteObserver {
    private final android.content.pm.IPackageDeleteObserver2.Stub mBinder = new android.content.pm.IPackageDeleteObserver2.Stub() { // from class: android.app.PackageDeleteObserver.1
        @Override // android.content.pm.IPackageDeleteObserver2
        public void onUserActionRequired(android.content.Intent intent) {
            android.app.PackageDeleteObserver.this.onUserActionRequired(intent);
        }

        @Override // android.content.pm.IPackageDeleteObserver2
        public void onPackageDeleted(java.lang.String str, int i, java.lang.String str2) {
            android.app.PackageDeleteObserver.this.onPackageDeleted(str, i, str2);
        }
    };

    public android.content.pm.IPackageDeleteObserver2 getBinder() {
        return this.mBinder;
    }

    public void onUserActionRequired(android.content.Intent intent) {
    }

    public void onPackageDeleted(java.lang.String str, int i, java.lang.String str2) {
    }
}
