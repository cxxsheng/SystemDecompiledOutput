package android.view;

/* loaded from: classes4.dex */
class ViewTraversalTracingStrings {
    public final java.lang.String classSimpleName;
    public final java.lang.String onLayout;
    public final java.lang.String onMeasure;
    public final java.lang.String onMeasureBeforeLayout;
    public final java.lang.String requestLayoutStacktracePrefix;

    ViewTraversalTracingStrings(android.view.View view) {
        java.lang.String simpleName = view.getClass().getSimpleName();
        this.classSimpleName = simpleName;
        this.onMeasureBeforeLayout = getTraceName("onMeasureBeforeLayout", simpleName, view);
        this.onMeasure = getTraceName("onMeasure", simpleName, view);
        this.onLayout = getTraceName("onLayout", simpleName, view);
        this.requestLayoutStacktracePrefix = "requestLayout " + simpleName;
    }

    private java.lang.String getTraceName(java.lang.String str, java.lang.String str2, android.view.View view) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        view.appendId(sb);
        return sb.substring(0, java.lang.Math.min(sb.length() - 1, 126));
    }
}
