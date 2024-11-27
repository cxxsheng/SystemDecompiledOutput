package android.content;

/* loaded from: classes.dex */
public class MutableContextWrapper extends android.content.ContextWrapper {
    public MutableContextWrapper(android.content.Context context) {
        super(context);
    }

    public void setBaseContext(android.content.Context context) {
        this.mBase = context;
    }
}
