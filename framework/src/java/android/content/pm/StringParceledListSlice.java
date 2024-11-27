package android.content.pm;

/* loaded from: classes.dex */
public class StringParceledListSlice extends android.content.pm.BaseParceledListSlice<java.lang.String> {
    public static final android.os.Parcelable.ClassLoaderCreator<android.content.pm.StringParceledListSlice> CREATOR = new android.os.Parcelable.ClassLoaderCreator<android.content.pm.StringParceledListSlice>() { // from class: android.content.pm.StringParceledListSlice.1
        @Override // android.os.Parcelable.Creator
        public android.content.pm.StringParceledListSlice createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.StringParceledListSlice(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public android.content.pm.StringParceledListSlice createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            return new android.content.pm.StringParceledListSlice(parcel, classLoader);
        }

        @Override // android.os.Parcelable.Creator
        public android.content.pm.StringParceledListSlice[] newArray(int i) {
            return new android.content.pm.StringParceledListSlice[i];
        }
    };

    @Override // android.content.pm.BaseParceledListSlice
    public /* bridge */ /* synthetic */ java.util.List<java.lang.String> getList() {
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

    public StringParceledListSlice(java.util.List<java.lang.String> list) {
        super(list);
    }

    private StringParceledListSlice(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        super(parcel, classLoader);
    }

    public static android.content.pm.StringParceledListSlice emptyList() {
        return new android.content.pm.StringParceledListSlice(java.util.Collections.emptyList());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.content.pm.BaseParceledListSlice
    public void writeElement(java.lang.String str, android.os.Parcel parcel, int i) {
        parcel.writeString(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.content.pm.BaseParceledListSlice
    public void writeParcelableCreator(java.lang.String str, android.os.Parcel parcel) {
    }

    @Override // android.content.pm.BaseParceledListSlice
    protected android.os.Parcelable.Creator<?> readParcelableCreator(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        return android.os.Parcel.STRING_CREATOR;
    }
}
