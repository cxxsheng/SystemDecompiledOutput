package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public abstract class FragmentContainer {
    public abstract <T extends android.view.View> T onFindViewById(int i);

    public abstract boolean onHasView();

    public android.app.Fragment instantiate(android.content.Context context, java.lang.String str, android.os.Bundle bundle) {
        return android.app.Fragment.instantiate(context, str, bundle);
    }
}
