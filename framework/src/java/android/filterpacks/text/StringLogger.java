package android.filterpacks.text;

/* loaded from: classes.dex */
public class StringLogger extends android.filterfw.core.Filter {
    public StringLogger(java.lang.String str) {
        super(str);
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addMaskedInputPort("string", android.filterfw.format.ObjectFormat.fromClass(java.lang.Object.class, 1));
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.util.Log.i("StringLogger", pullInput("string").getObjectValue().toString());
    }
}
