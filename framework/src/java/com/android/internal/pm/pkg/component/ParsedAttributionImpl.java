package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedAttributionImpl implements com.android.internal.pm.pkg.component.ParsedAttribution, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedAttributionImpl> CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedAttributionImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedAttributionImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedAttributionImpl[] newArray(int i) {
            return new com.android.internal.pm.pkg.component.ParsedAttributionImpl[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedAttributionImpl createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.pm.pkg.component.ParsedAttributionImpl(parcel);
        }
    };
    static final int MAX_NUM_ATTRIBUTIONS = 400;
    private java.util.List<java.lang.String> inheritFrom;
    private int label;
    private java.lang.String tag;

    public ParsedAttributionImpl() {
    }

    public ParsedAttributionImpl(java.lang.String str, int i, java.util.List<java.lang.String> list) {
        this.tag = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) str);
        this.label = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.StringRes.class, (java.lang.annotation.Annotation) null, i);
        this.inheritFrom = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) list);
    }

    @Override // com.android.internal.pm.pkg.component.ParsedAttribution
    public java.lang.String getTag() {
        return this.tag;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedAttribution
    public int getLabel() {
        return this.label;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedAttribution
    public java.util.List<java.lang.String> getInheritFrom() {
        return this.inheritFrom;
    }

    public com.android.internal.pm.pkg.component.ParsedAttributionImpl setTag(java.lang.String str) {
        this.tag = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.tag);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedAttributionImpl setLabel(int i) {
        this.label = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.StringRes.class, (java.lang.annotation.Annotation) null, this.label);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedAttributionImpl setInheritFrom(java.util.List<java.lang.String> list) {
        this.inheritFrom = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.inheritFrom);
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.tag);
        parcel.writeInt(this.label);
        parcel.writeStringList(this.inheritFrom);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParsedAttributionImpl(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        int readInt = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        this.tag = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.tag);
        this.label = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.StringRes.class, (java.lang.annotation.Annotation) null, this.label);
        this.inheritFrom = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.inheritFrom);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
