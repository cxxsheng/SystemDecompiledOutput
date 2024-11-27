package android.app;

/* loaded from: classes.dex */
public class PackageInstallObserver {
    private final android.content.pm.IPackageInstallObserver2.Stub mBinder = new android.content.pm.IPackageInstallObserver2.Stub() { // from class: android.app.PackageInstallObserver.1
        @Override // android.content.pm.IPackageInstallObserver2
        public void onUserActionRequired(android.content.Intent intent) {
            android.app.PackageInstallObserver.this.onUserActionRequired(intent);
        }

        @Override // android.content.pm.IPackageInstallObserver2
        public void onPackageInstalled(java.lang.String str, int i, java.lang.String str2, android.os.Bundle bundle) {
            android.app.PackageInstallObserver.this.onPackageInstalled(str, i, str2, bundle);
        }
    };

    public android.content.pm.IPackageInstallObserver2 getBinder() {
        return this.mBinder;
    }

    public void onUserActionRequired(android.content.Intent intent) {
    }

    public void onPackageInstalled(java.lang.String str, int i, java.lang.String str2, android.os.Bundle bundle) {
    }
}
