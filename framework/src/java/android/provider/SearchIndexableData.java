package android.provider;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class SearchIndexableData {
    public java.lang.String className;
    public android.content.Context context;
    public boolean enabled;
    public int iconResId;
    public java.lang.String intentAction;
    public java.lang.String intentTargetClass;
    public java.lang.String intentTargetPackage;
    public java.lang.String key;
    public java.util.Locale locale;
    public java.lang.String packageName;
    public int rank;
    public int userId;

    public SearchIndexableData() {
        this.userId = -1;
        this.locale = java.util.Locale.getDefault();
        this.enabled = true;
    }

    public SearchIndexableData(android.content.Context context) {
        this();
        this.context = context;
    }

    public java.lang.String toString() {
        return "SearchIndexableData[context: " + this.context + ", locale: " + this.locale + ", enabled: " + this.enabled + ", rank: " + this.rank + ", key: " + this.key + ", userId: " + this.userId + ", className: " + this.className + ", packageName: " + this.packageName + ", iconResId: " + this.iconResId + ", intentAction: " + this.intentAction + ", intentTargetPackage: " + this.intentTargetPackage + ", intentTargetClass: " + this.intentTargetClass + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
