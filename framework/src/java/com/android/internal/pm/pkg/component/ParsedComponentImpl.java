package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public abstract class ParsedComponentImpl implements com.android.internal.pm.pkg.component.ParsedComponent, android.os.Parcelable {
    private int banner;
    private android.content.ComponentName componentName;
    private int descriptionRes;
    private int flags;
    private int icon;
    private java.util.List<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> intents;
    private int labelRes;
    private int logo;
    private java.util.Map<java.lang.String, android.content.pm.PackageManager.Property> mProperties;
    private android.os.Bundle metaData;
    private java.lang.String name;
    private java.lang.CharSequence nonLocalizedLabel;
    private java.lang.String packageName;

    public ParsedComponentImpl() {
        this.intents = java.util.Collections.emptyList();
        this.mProperties = java.util.Collections.emptyMap();
    }

    protected ParsedComponentImpl(com.android.internal.pm.pkg.component.ParsedComponent parsedComponent) {
        this.intents = java.util.Collections.emptyList();
        this.mProperties = java.util.Collections.emptyMap();
        this.metaData = parsedComponent.getMetaData();
        this.name = parsedComponent.getName();
        this.icon = parsedComponent.getIcon();
        this.labelRes = parsedComponent.getLabelRes();
        this.nonLocalizedLabel = parsedComponent.getNonLocalizedLabel();
        this.logo = parsedComponent.getLogo();
        this.banner = parsedComponent.getBanner();
        this.descriptionRes = parsedComponent.getDescriptionRes();
        this.flags = parsedComponent.getFlags();
        this.packageName = parsedComponent.getPackageName();
        this.componentName = parsedComponent.getComponentName();
        this.intents = new java.util.ArrayList(((com.android.internal.pm.pkg.component.ParsedComponentImpl) parsedComponent).intents);
        this.mProperties = new android.util.ArrayMap();
        this.mProperties.putAll(parsedComponent.getProperties());
    }

    public void addIntent(com.android.internal.pm.pkg.component.ParsedIntentInfoImpl parsedIntentInfoImpl) {
        this.intents = com.android.internal.util.CollectionUtils.add(this.intents, parsedIntentInfoImpl);
    }

    public void addProperty(android.content.pm.PackageManager.Property property) {
        this.mProperties = com.android.internal.util.CollectionUtils.add(this.mProperties, property.getName(), property);
    }

    public com.android.internal.pm.pkg.component.ParsedComponentImpl setName(java.lang.String str) {
        this.name = android.text.TextUtils.safeIntern(str);
        return this;
    }

    public void setPackageName(java.lang.String str) {
        this.packageName = android.text.TextUtils.safeIntern(str);
        this.componentName = null;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public android.content.ComponentName getComponentName() {
        if (this.componentName == null) {
            this.componentName = new android.content.ComponentName(getPackageName(), getName());
        }
        return this.componentName;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public android.os.Bundle getMetaData() {
        return this.metaData == null ? android.os.Bundle.EMPTY : this.metaData;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public java.util.List<com.android.internal.pm.pkg.component.ParsedIntentInfo> getIntents() {
        return new java.util.ArrayList(this.intents);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeInt(getIcon());
        parcel.writeInt(getLabelRes());
        parcel.writeCharSequence(getNonLocalizedLabel());
        parcel.writeInt(getLogo());
        parcel.writeInt(getBanner());
        parcel.writeInt(getDescriptionRes());
        parcel.writeInt(getFlags());
        com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.parcel(this.packageName, parcel, i);
        parcel.writeTypedList(this.intents);
        parcel.writeBundle(this.metaData);
        parcel.writeMap(this.mProperties);
    }

    protected ParsedComponentImpl(android.os.Parcel parcel) {
        this.intents = java.util.Collections.emptyList();
        this.mProperties = java.util.Collections.emptyMap();
        java.lang.ClassLoader classLoader = java.lang.Object.class.getClassLoader();
        this.name = parcel.readString();
        this.icon = parcel.readInt();
        this.labelRes = parcel.readInt();
        this.nonLocalizedLabel = parcel.readCharSequence();
        this.logo = parcel.readInt();
        this.banner = parcel.readInt();
        this.descriptionRes = parcel.readInt();
        this.flags = parcel.readInt();
        this.packageName = com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.unparcel(parcel);
        this.intents = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedIntentInfoImpl.CREATOR);
        this.metaData = parcel.readBundle(classLoader);
        this.mProperties = parcel.readHashMap(classLoader);
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public java.lang.String getName() {
        return this.name;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public int getIcon() {
        return this.icon;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public int getLabelRes() {
        return this.labelRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public java.lang.CharSequence getNonLocalizedLabel() {
        return this.nonLocalizedLabel;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public int getLogo() {
        return this.logo;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public int getBanner() {
        return this.banner;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public int getDescriptionRes() {
        return this.descriptionRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public int getFlags() {
        return this.flags;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public java.lang.String getPackageName() {
        return this.packageName;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponent
    public java.util.Map<java.lang.String, android.content.pm.PackageManager.Property> getProperties() {
        return this.mProperties;
    }

    public com.android.internal.pm.pkg.component.ParsedComponentImpl setIcon(int i) {
        this.icon = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedComponentImpl setLabelRes(int i) {
        this.labelRes = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedComponentImpl setNonLocalizedLabel(java.lang.CharSequence charSequence) {
        this.nonLocalizedLabel = charSequence;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedComponentImpl setLogo(int i) {
        this.logo = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedComponentImpl setBanner(int i) {
        this.banner = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedComponentImpl setDescriptionRes(int i) {
        this.descriptionRes = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedComponentImpl setFlags(int i) {
        this.flags = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedComponentImpl setMetaData(android.os.Bundle bundle) {
        this.metaData = bundle;
        return this;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
