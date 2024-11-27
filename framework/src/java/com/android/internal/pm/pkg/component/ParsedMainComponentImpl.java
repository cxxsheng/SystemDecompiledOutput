package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedMainComponentImpl extends com.android.internal.pm.pkg.component.ParsedComponentImpl implements com.android.internal.pm.pkg.component.ParsedMainComponent, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedMainComponentImpl> CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedMainComponentImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedMainComponentImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedMainComponentImpl createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.pm.pkg.component.ParsedMainComponentImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedMainComponentImpl[] newArray(int i) {
            return new com.android.internal.pm.pkg.component.ParsedMainComponentImpl[i];
        }
    };
    private java.lang.String[] attributionTags;
    private boolean directBootAware;
    private boolean enabled;
    private boolean exported;
    private int order;
    private java.lang.String processName;
    private java.lang.String splitName;

    public ParsedMainComponentImpl() {
        this.enabled = true;
    }

    public ParsedMainComponentImpl(com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent) {
        super(parsedMainComponent);
        this.enabled = true;
        this.processName = parsedMainComponent.getProcessName();
        this.directBootAware = parsedMainComponent.isDirectBootAware();
        this.enabled = parsedMainComponent.isEnabled();
        this.exported = parsedMainComponent.isExported();
        this.order = parsedMainComponent.getOrder();
        this.splitName = parsedMainComponent.getSplitName();
        this.attributionTags = parsedMainComponent.getAttributionTags();
    }

    public com.android.internal.pm.pkg.component.ParsedMainComponentImpl setProcessName(java.lang.String str) {
        this.processName = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public java.lang.String getClassName() {
        return getName();
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public java.lang.String[] getAttributionTags() {
        return this.attributionTags == null ? libcore.util.EmptyArray.STRING : this.attributionTags;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.parcel(this.processName, parcel, i);
        parcel.writeBoolean(this.directBootAware);
        parcel.writeBoolean(this.enabled);
        parcel.writeBoolean(this.exported);
        parcel.writeInt(this.order);
        parcel.writeString(this.splitName);
        parcel.writeString8Array(this.attributionTags);
    }

    protected ParsedMainComponentImpl(android.os.Parcel parcel) {
        super(parcel);
        this.enabled = true;
        this.processName = com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.unparcel(parcel);
        this.directBootAware = parcel.readBoolean();
        this.enabled = parcel.readBoolean();
        this.exported = parcel.readBoolean();
        this.order = parcel.readInt();
        this.splitName = parcel.readString();
        this.attributionTags = parcel.createString8Array();
    }

    public ParsedMainComponentImpl(java.lang.String str, boolean z, boolean z2, boolean z3, int i, java.lang.String str2, java.lang.String[] strArr) {
        this.enabled = true;
        this.processName = str;
        this.directBootAware = z;
        this.enabled = z2;
        this.exported = z3;
        this.order = i;
        this.splitName = str2;
        this.attributionTags = strArr;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public java.lang.String getProcessName() {
        return this.processName;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public boolean isDirectBootAware() {
        return this.directBootAware;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public boolean isExported() {
        return this.exported;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public int getOrder() {
        return this.order;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponent
    public java.lang.String getSplitName() {
        return this.splitName;
    }

    public com.android.internal.pm.pkg.component.ParsedMainComponentImpl setDirectBootAware(boolean z) {
        this.directBootAware = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedMainComponentImpl setEnabled(boolean z) {
        this.enabled = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedMainComponentImpl setExported(boolean z) {
        this.exported = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedMainComponentImpl setOrder(int i) {
        this.order = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedMainComponentImpl setSplitName(java.lang.String str) {
        this.splitName = str;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedMainComponentImpl setAttributionTags(java.lang.String... strArr) {
        this.attributionTags = strArr;
        return this;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
