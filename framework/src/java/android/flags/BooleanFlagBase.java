package android.flags;

/* loaded from: classes.dex */
abstract class BooleanFlagBase implements android.flags.Flag<java.lang.Boolean> {
    private java.lang.String mCategoryName;
    private java.lang.String mDescription;
    private java.lang.String mLabel;
    private final java.lang.String mName;
    private final java.lang.String mNamespace;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.flags.Flag
    public abstract java.lang.Boolean getDefault();

    BooleanFlagBase(java.lang.String str, java.lang.String str2) {
        this.mNamespace = str;
        this.mName = str2;
        this.mLabel = str2;
    }

    @Override // android.flags.Flag
    public java.lang.String getNamespace() {
        return this.mNamespace;
    }

    @Override // android.flags.Flag
    public java.lang.String getName() {
        return this.mName;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.flags.Flag
    public android.flags.Flag<java.lang.Boolean> defineMetaData(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mLabel = str;
        this.mDescription = str2;
        this.mCategoryName = str3;
        return this;
    }

    @Override // android.flags.Flag
    public java.lang.String getLabel() {
        return this.mLabel;
    }

    @Override // android.flags.Flag
    public java.lang.String getDescription() {
        return this.mDescription;
    }

    @Override // android.flags.Flag
    public java.lang.String getCategoryName() {
        return this.mCategoryName;
    }

    public java.lang.String toString() {
        return getNamespace() + android.media.MediaMetrics.SEPARATOR + getName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + getDefault() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
