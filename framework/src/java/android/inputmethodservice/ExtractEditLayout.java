package android.inputmethodservice;

/* loaded from: classes2.dex */
public class ExtractEditLayout extends android.widget.LinearLayout {
    android.widget.Button mExtractActionButton;

    public ExtractEditLayout(android.content.Context context) {
        super(context);
    }

    public ExtractEditLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mExtractActionButton = (android.widget.Button) findViewById(16908377);
    }
}
