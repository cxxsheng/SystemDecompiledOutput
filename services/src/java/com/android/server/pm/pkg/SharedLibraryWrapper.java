package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public class SharedLibraryWrapper implements com.android.server.pm.pkg.SharedLibrary {

    @android.annotation.Nullable
    private java.util.List<com.android.server.pm.pkg.SharedLibrary> cachedDependenciesList;
    private final android.content.pm.SharedLibraryInfo mInfo;

    public SharedLibraryWrapper(@android.annotation.NonNull android.content.pm.SharedLibraryInfo sharedLibraryInfo) {
        this.mInfo = sharedLibraryInfo;
    }

    @android.annotation.NonNull
    public android.content.pm.SharedLibraryInfo getInfo() {
        return this.mInfo;
    }

    @Override // com.android.server.pm.pkg.SharedLibrary
    public java.lang.String getPath() {
        return this.mInfo.getPath();
    }

    @Override // com.android.server.pm.pkg.SharedLibrary
    public java.lang.String getPackageName() {
        return this.mInfo.getPackageName();
    }

    @Override // com.android.server.pm.pkg.SharedLibrary
    public java.lang.String getName() {
        return this.mInfo.getName();
    }

    @Override // com.android.server.pm.pkg.SharedLibrary
    public java.util.List<java.lang.String> getAllCodePaths() {
        return java.util.Collections.unmodifiableList(this.mInfo.getAllCodePaths());
    }

    @Override // com.android.server.pm.pkg.SharedLibrary
    public long getVersion() {
        return this.mInfo.getLongVersion();
    }

    @Override // com.android.server.pm.pkg.SharedLibrary
    public int getType() {
        return this.mInfo.getType();
    }

    @Override // com.android.server.pm.pkg.SharedLibrary
    public boolean isNative() {
        return this.mInfo.isNative();
    }

    @Override // com.android.server.pm.pkg.SharedLibrary
    @android.annotation.NonNull
    public android.content.pm.VersionedPackage getDeclaringPackage() {
        return this.mInfo.getDeclaringPackage();
    }

    @Override // com.android.server.pm.pkg.SharedLibrary
    @android.annotation.NonNull
    public java.util.List<android.content.pm.VersionedPackage> getDependentPackages() {
        return java.util.Collections.unmodifiableList(this.mInfo.getDependentPackages());
    }

    @Override // com.android.server.pm.pkg.SharedLibrary
    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.pkg.SharedLibrary> getDependencies() {
        if (this.cachedDependenciesList == null) {
            java.util.List dependencies = this.mInfo.getDependencies();
            if (dependencies == null) {
                this.cachedDependenciesList = java.util.Collections.emptyList();
            } else {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i = 0; i < dependencies.size(); i++) {
                    arrayList.add(new com.android.server.pm.pkg.SharedLibraryWrapper((android.content.pm.SharedLibraryInfo) dependencies.get(i)));
                }
                this.cachedDependenciesList = java.util.Collections.unmodifiableList(arrayList);
            }
        }
        return this.cachedDependenciesList;
    }
}
