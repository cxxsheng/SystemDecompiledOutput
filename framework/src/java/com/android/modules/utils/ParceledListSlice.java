package com.android.modules.utils;

/* loaded from: classes5.dex */
public class ParceledListSlice<T extends android.os.Parcelable> extends com.android.modules.utils.BaseParceledListSlice<T> {
    public static final android.os.Parcelable.ClassLoaderCreator<com.android.modules.utils.ParceledListSlice> CREATOR = new android.os.Parcelable.ClassLoaderCreator<com.android.modules.utils.ParceledListSlice>() { // from class: com.android.modules.utils.ParceledListSlice.1
        @Override // android.os.Parcelable.Creator
        public com.android.modules.utils.ParceledListSlice createFromParcel(android.os.Parcel parcel) {
            return new com.android.modules.utils.ParceledListSlice(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public com.android.modules.utils.ParceledListSlice createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            return new com.android.modules.utils.ParceledListSlice(parcel, classLoader);
        }

        @Override // android.os.Parcelable.Creator
        public com.android.modules.utils.ParceledListSlice[] newArray(int i) {
            return new com.android.modules.utils.ParceledListSlice[i];
        }
    };

    @Override // com.android.modules.utils.BaseParceledListSlice
    public /* bridge */ /* synthetic */ java.util.List getList() {
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

    public ParceledListSlice(java.util.List<T> list) {
        super(list);
    }

    private ParceledListSlice(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        super(parcel, classLoader);
    }

    public static <T extends android.os.Parcelable> com.android.modules.utils.ParceledListSlice<T> emptyList() {
        return new com.android.modules.utils.ParceledListSlice<>(java.util.Collections.emptyList());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        java.util.List list = getList();
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            i |= ((android.os.Parcelable) list.get(i2)).describeContents();
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.modules.utils.BaseParceledListSlice
    public void writeElement(T t, android.os.Parcel parcel, int i) {
        t.writeToParcel(parcel, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.modules.utils.BaseParceledListSlice
    public void writeParcelableCreator(T t, android.os.Parcel parcel) {
        parcel.writeParcelableCreator(t);
    }

    @Override // com.android.modules.utils.BaseParceledListSlice
    protected android.os.Parcelable.Creator<?> readParcelableCreator(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        return parcel.readParcelableCreator(classLoader);
    }
}
