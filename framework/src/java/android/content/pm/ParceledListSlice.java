package android.content.pm;

/* loaded from: classes.dex */
public class ParceledListSlice<T extends android.os.Parcelable> extends android.content.pm.BaseParceledListSlice<T> {
    public static final android.os.Parcelable.ClassLoaderCreator<android.content.pm.ParceledListSlice> CREATOR = new android.os.Parcelable.ClassLoaderCreator<android.content.pm.ParceledListSlice>() { // from class: android.content.pm.ParceledListSlice.1
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ParceledListSlice createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ParceledListSlice(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public android.content.pm.ParceledListSlice createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            return new android.content.pm.ParceledListSlice(parcel, classLoader);
        }

        @Override // android.os.Parcelable.Creator
        public android.content.pm.ParceledListSlice[] newArray(int i) {
            return new android.content.pm.ParceledListSlice[i];
        }
    };

    @Override // android.content.pm.BaseParceledListSlice
    public /* bridge */ /* synthetic */ java.util.List getList() {
        return super.getList();
    }

    @Override // android.content.pm.BaseParceledListSlice
    public /* bridge */ /* synthetic */ void setInlineCountLimit(int i) {
        super.setInlineCountLimit(i);
    }

    @Override // android.content.pm.BaseParceledListSlice, android.os.Parcelable
    public /* bridge */ /* synthetic */ void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public ParceledListSlice(java.util.List<T> list) {
        super(list);
    }

    private ParceledListSlice(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        super(parcel, classLoader);
    }

    public static <T extends android.os.Parcelable> android.content.pm.ParceledListSlice<T> emptyList() {
        return new android.content.pm.ParceledListSlice<>(java.util.Collections.emptyList());
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
    @Override // android.content.pm.BaseParceledListSlice
    public void writeElement(T t, android.os.Parcel parcel, int i) {
        t.writeToParcel(parcel, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.content.pm.BaseParceledListSlice
    public void writeParcelableCreator(T t, android.os.Parcel parcel) {
        parcel.writeParcelableCreator(t);
    }

    @Override // android.content.pm.BaseParceledListSlice
    protected android.os.Parcelable.Creator<?> readParcelableCreator(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        return parcel.readParcelableCreator(classLoader);
    }
}
