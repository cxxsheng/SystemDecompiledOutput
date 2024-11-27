package com.android.internal.pm.pkg;

/* loaded from: classes5.dex */
public class AndroidPackageSplitImpl implements com.android.server.pm.pkg.AndroidPackageSplit {
    private final java.lang.String mClassLoaderName;
    private java.util.List<com.android.server.pm.pkg.AndroidPackageSplit> mDependencies = java.util.Collections.emptyList();
    private final int mFlags;
    private final java.lang.String mName;
    private final java.lang.String mPath;
    private final int mRevisionCode;

    public AndroidPackageSplitImpl(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) {
        this.mName = str;
        this.mPath = str2;
        this.mRevisionCode = i;
        this.mFlags = i2;
        this.mClassLoaderName = str3;
    }

    public void fillDependencies(java.util.List<com.android.server.pm.pkg.AndroidPackageSplit> list) {
        if (!this.mDependencies.isEmpty()) {
            throw new java.lang.IllegalStateException("Cannot fill split dependencies more than once");
        }
        this.mDependencies = list;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public java.lang.String getName() {
        return this.mName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public java.lang.String getPath() {
        return this.mPath;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public int getRevisionCode() {
        return this.mRevisionCode;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public boolean isHasCode() {
        return (this.mFlags & 4) != 0;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public java.lang.String getClassLoaderName() {
        return this.mClassLoaderName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackageSplit
    public java.util.List<com.android.server.pm.pkg.AndroidPackageSplit> getDependencies() {
        return this.mDependencies;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.internal.pm.pkg.AndroidPackageSplitImpl)) {
            return false;
        }
        com.android.internal.pm.pkg.AndroidPackageSplitImpl androidPackageSplitImpl = (com.android.internal.pm.pkg.AndroidPackageSplitImpl) obj;
        if (!(this.mRevisionCode == androidPackageSplitImpl.mRevisionCode && this.mFlags == androidPackageSplitImpl.mFlags && java.util.Objects.equals(this.mName, androidPackageSplitImpl.mName) && java.util.Objects.equals(this.mPath, androidPackageSplitImpl.mPath) && java.util.Objects.equals(this.mClassLoaderName, androidPackageSplitImpl.mClassLoaderName)) || this.mDependencies.size() != androidPackageSplitImpl.mDependencies.size()) {
            return false;
        }
        for (int i = 0; i < this.mDependencies.size(); i++) {
            if (!java.util.Objects.equals(this.mDependencies.get(i).getName(), androidPackageSplitImpl.mDependencies.get(i).getName())) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = java.util.Objects.hash(this.mName, this.mPath, java.lang.Integer.valueOf(this.mRevisionCode), java.lang.Integer.valueOf(this.mFlags), this.mClassLoaderName);
        for (int i = 0; i < this.mDependencies.size(); i++) {
            java.lang.String name = this.mDependencies.get(i).getName();
            hash = (hash * 31) + (name == null ? 0 : name.hashCode());
        }
        return hash;
    }
}
