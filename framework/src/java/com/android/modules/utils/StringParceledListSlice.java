package com.android.modules.utils;

/* loaded from: classes5.dex */
public class StringParceledListSlice extends com.android.modules.utils.BaseParceledListSlice<java.lang.String> {
    public static final android.os.Parcelable.ClassLoaderCreator<com.android.modules.utils.StringParceledListSlice> CREATOR = new android.os.Parcelable.ClassLoaderCreator<com.android.modules.utils.StringParceledListSlice>() { // from class: com.android.modules.utils.StringParceledListSlice.1
        @Override // android.os.Parcelable.Creator
        public com.android.modules.utils.StringParceledListSlice createFromParcel(android.os.Parcel parcel) {
            return new com.android.modules.utils.StringParceledListSlice(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public com.android.modules.utils.StringParceledListSlice createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            return new com.android.modules.utils.StringParceledListSlice(parcel, classLoader);
        }

        @Override // android.os.Parcelable.Creator
        public com.android.modules.utils.StringParceledListSlice[] newArray(int i) {
            return new com.android.modules.utils.StringParceledListSlice[i];
        }
    };

    @Override // com.android.modules.utils.BaseParceledListSlice
    public /* bridge */ /* synthetic */ java.util.List<java.lang.String> getList() {
        return super.getList();
    }

    @Override // com.android.modules.utils.BaseParceledListSlice
    public /* bridge */ /* synthetic */ void setInlineCountLimit(int i) {
        super.setInlineCountLimit(i);
    }

    @Override // com.android.modules.utils.BaseParceledListSlice, android.os.Parcelable
    public /* bridge */ /* synthetic */ void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public StringParceledListSlice(java.util.List<java.lang.String> list) {
        super(list);
    }

    private StringParceledListSlice(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        super(parcel, classLoader);
    }

    public static com.android.modules.utils.StringParceledListSlice emptyList() {
        return new com.android.modules.utils.StringParceledListSlice(java.util.Collections.emptyList());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.modules.utils.BaseParceledListSlice
    public void writeElement(java.lang.String str, android.os.Parcel parcel, int i) {
        parcel.writeString(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.modules.utils.BaseParceledListSlice
    public void writeParcelableCreator(java.lang.String str, android.os.Parcel parcel) {
    }

    @Override // com.android.modules.utils.BaseParceledListSlice
    protected android.os.Parcelable.Creator<?> readParcelableCreator(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        return android.os.Parcel.STRING_CREATOR;
    }
}
