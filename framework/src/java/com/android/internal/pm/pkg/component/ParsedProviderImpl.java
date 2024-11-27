package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedProviderImpl extends com.android.internal.pm.pkg.component.ParsedMainComponentImpl implements com.android.internal.pm.pkg.component.ParsedProvider, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedProviderImpl> CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedProviderImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedProviderImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedProviderImpl createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.pm.pkg.component.ParsedProviderImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedProviderImpl[] newArray(int i) {
            return new com.android.internal.pm.pkg.component.ParsedProviderImpl[i];
        }
    };
    private java.lang.String authority;
    private boolean forceUriPermissions;
    private boolean grantUriPermissions;
    private int initOrder;
    private boolean multiProcess;
    private java.util.List<android.content.pm.PathPermission> pathPermissions;
    private java.lang.String readPermission;
    private boolean syncable;
    private java.util.List<android.os.PatternMatcher> uriPermissionPatterns;
    private java.lang.String writePermission;

    public ParsedProviderImpl(com.android.internal.pm.pkg.component.ParsedProvider parsedProvider) {
        super(parsedProvider);
        this.uriPermissionPatterns = java.util.Collections.emptyList();
        this.pathPermissions = java.util.Collections.emptyList();
        this.authority = parsedProvider.getAuthority();
        this.syncable = parsedProvider.isSyncable();
        this.readPermission = parsedProvider.getReadPermission();
        this.writePermission = parsedProvider.getWritePermission();
        this.grantUriPermissions = parsedProvider.isGrantUriPermissions();
        this.forceUriPermissions = parsedProvider.isForceUriPermissions();
        this.multiProcess = parsedProvider.isMultiProcess();
        this.initOrder = parsedProvider.getInitOrder();
        this.uriPermissionPatterns = new java.util.ArrayList(parsedProvider.getUriPermissionPatterns());
        this.pathPermissions = new java.util.ArrayList(parsedProvider.getPathPermissions());
    }

    public com.android.internal.pm.pkg.component.ParsedProviderImpl setReadPermission(java.lang.String str) {
        this.readPermission = android.text.TextUtils.isEmpty(str) ? null : str.intern();
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProviderImpl setWritePermission(java.lang.String str) {
        this.writePermission = android.text.TextUtils.isEmpty(str) ? null : str.intern();
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProviderImpl addUriPermissionPattern(android.os.PatternMatcher patternMatcher) {
        this.uriPermissionPatterns = com.android.internal.util.CollectionUtils.add(this.uriPermissionPatterns, patternMatcher);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProviderImpl addPathPermission(android.content.pm.PathPermission pathPermission) {
        this.pathPermissions = com.android.internal.util.CollectionUtils.add(this.pathPermissions, pathPermission);
        return this;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("Provider{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        android.content.ComponentName.appendShortString(sb, getPackageName(), getName());
        sb.append('}');
        return sb.toString();
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl, com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl, com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.authority);
        parcel.writeBoolean(this.syncable);
        com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.parcel(this.readPermission, parcel, i);
        com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.parcel(this.writePermission, parcel, i);
        parcel.writeBoolean(this.grantUriPermissions);
        parcel.writeBoolean(this.forceUriPermissions);
        parcel.writeBoolean(this.multiProcess);
        parcel.writeInt(this.initOrder);
        parcel.writeTypedList(this.uriPermissionPatterns, i);
        parcel.writeTypedList(this.pathPermissions, i);
    }

    public ParsedProviderImpl() {
        this.uriPermissionPatterns = java.util.Collections.emptyList();
        this.pathPermissions = java.util.Collections.emptyList();
    }

    protected ParsedProviderImpl(android.os.Parcel parcel) {
        super(parcel);
        this.uriPermissionPatterns = java.util.Collections.emptyList();
        this.pathPermissions = java.util.Collections.emptyList();
        this.authority = parcel.readString();
        this.syncable = parcel.readBoolean();
        this.readPermission = com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.unparcel(parcel);
        this.writePermission = com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.unparcel(parcel);
        this.grantUriPermissions = parcel.readBoolean();
        this.forceUriPermissions = parcel.readBoolean();
        this.multiProcess = parcel.readBoolean();
        this.initOrder = parcel.readInt();
        this.uriPermissionPatterns = parcel.createTypedArrayList(android.os.PatternMatcher.CREATOR);
        this.pathPermissions = parcel.createTypedArrayList(android.content.pm.PathPermission.CREATOR);
    }

    public ParsedProviderImpl(java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3, boolean z2, boolean z3, boolean z4, int i, java.util.List<android.os.PatternMatcher> list, java.util.List<android.content.pm.PathPermission> list2) {
        this.uriPermissionPatterns = java.util.Collections.emptyList();
        this.pathPermissions = java.util.Collections.emptyList();
        this.authority = str;
        this.syncable = z;
        this.readPermission = str2;
        this.writePermission = str3;
        this.grantUriPermissions = z2;
        this.forceUriPermissions = z3;
        this.multiProcess = z4;
        this.initOrder = i;
        this.uriPermissionPatterns = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) list);
        this.pathPermissions = list2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) list2);
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public java.lang.String getAuthority() {
        return this.authority;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public boolean isSyncable() {
        return this.syncable;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public java.lang.String getReadPermission() {
        return this.readPermission;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public java.lang.String getWritePermission() {
        return this.writePermission;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public boolean isGrantUriPermissions() {
        return this.grantUriPermissions;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public boolean isForceUriPermissions() {
        return this.forceUriPermissions;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public boolean isMultiProcess() {
        return this.multiProcess;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public int getInitOrder() {
        return this.initOrder;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public java.util.List<android.os.PatternMatcher> getUriPermissionPatterns() {
        return this.uriPermissionPatterns;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProvider
    public java.util.List<android.content.pm.PathPermission> getPathPermissions() {
        return this.pathPermissions;
    }

    public com.android.internal.pm.pkg.component.ParsedProviderImpl setAuthority(java.lang.String str) {
        this.authority = str;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProviderImpl setSyncable(boolean z) {
        this.syncable = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProviderImpl setGrantUriPermissions(boolean z) {
        this.grantUriPermissions = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProviderImpl setForceUriPermissions(boolean z) {
        this.forceUriPermissions = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProviderImpl setMultiProcess(boolean z) {
        this.multiProcess = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProviderImpl setInitOrder(int i) {
        this.initOrder = i;
        return this;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
