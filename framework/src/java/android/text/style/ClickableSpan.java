package android.text.style;

/* loaded from: classes3.dex */
public abstract class ClickableSpan extends android.text.style.CharacterStyle implements android.text.style.UpdateAppearance {
    private static int sIdCounter = 0;
    private int mId;

    public abstract void onClick(android.view.View view);

    public ClickableSpan() {
        int i = sIdCounter;
        sIdCounter = i + 1;
        this.mId = i;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        textPaint.setColor(textPaint.linkColor);
        textPaint.setUnderlineText(true);
    }

    public int getId() {
        return this.mId;
    }

    public java.lang.String toString() {
        return "ClickableSpan{}";
    }
}
