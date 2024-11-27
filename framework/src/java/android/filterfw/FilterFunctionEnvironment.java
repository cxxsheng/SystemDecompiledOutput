package android.filterfw;

/* loaded from: classes.dex */
public class FilterFunctionEnvironment extends android.filterfw.MffEnvironment {
    public FilterFunctionEnvironment() {
        super(null);
    }

    public FilterFunctionEnvironment(android.filterfw.core.FrameManager frameManager) {
        super(frameManager);
    }

    public android.filterfw.core.FilterFunction createFunction(java.lang.Class cls, java.lang.Object... objArr) {
        android.filterfw.core.Filter createFilterByClass = android.filterfw.core.FilterFactory.sharedFactory().createFilterByClass(cls, "FilterFunction(" + cls.getSimpleName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        createFilterByClass.initWithAssignmentList(objArr);
        return new android.filterfw.core.FilterFunction(getContext(), createFilterByClass);
    }
}
