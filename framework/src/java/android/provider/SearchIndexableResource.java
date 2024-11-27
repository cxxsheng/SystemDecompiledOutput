package android.provider;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class SearchIndexableResource extends android.provider.SearchIndexableData {
    public int xmlResId;

    public SearchIndexableResource(int i, int i2, java.lang.String str, int i3) {
        this.rank = i;
        this.xmlResId = i2;
        this.className = str;
        this.iconResId = i3;
    }

    public SearchIndexableResource(android.content.Context context) {
        super(context);
    }

    @Override // android.provider.SearchIndexableData
    public java.lang.String toString() {
        return "SearchIndexableResource[" + super.toString() + ", xmlResId: " + this.xmlResId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
