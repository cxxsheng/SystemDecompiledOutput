package android.flags;

/* loaded from: classes.dex */
public final class FusedOffFlag extends android.flags.BooleanFlagBase {
    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public /* bridge */ /* synthetic */ java.lang.String getCategoryName() {
        return super.getCategoryName();
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public /* bridge */ /* synthetic */ java.lang.String getDescription() {
        return super.getDescription();
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public /* bridge */ /* synthetic */ java.lang.String getLabel() {
        return super.getLabel();
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public /* bridge */ /* synthetic */ java.lang.String getName() {
        return super.getName();
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public /* bridge */ /* synthetic */ java.lang.String getNamespace() {
        return super.getNamespace();
    }

    @Override // android.flags.BooleanFlagBase
    public /* bridge */ /* synthetic */ java.lang.String toString() {
        return super.toString();
    }

    FusedOffFlag(java.lang.String str, java.lang.String str2) {
        super(str, str2);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    public java.lang.Boolean getDefault() {
        return false;
    }

    @Override // android.flags.BooleanFlagBase, android.flags.Flag
    /* renamed from: defineMetaData */
    public android.flags.Flag<java.lang.Boolean> defineMetaData2(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        super.defineMetaData2(str, str2, str3);
        return this;
    }
}
