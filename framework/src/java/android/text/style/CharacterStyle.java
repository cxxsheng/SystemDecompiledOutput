package android.text.style;

/* loaded from: classes3.dex */
public abstract class CharacterStyle {
    public abstract void updateDrawState(android.text.TextPaint textPaint);

    public static android.text.style.CharacterStyle wrap(android.text.style.CharacterStyle characterStyle) {
        if (characterStyle instanceof android.text.style.MetricAffectingSpan) {
            return new android.text.style.MetricAffectingSpan.Passthrough((android.text.style.MetricAffectingSpan) characterStyle);
        }
        return new android.text.style.CharacterStyle.Passthrough(characterStyle);
    }

    public android.text.style.CharacterStyle getUnderlying() {
        return this;
    }

    private static class Passthrough extends android.text.style.CharacterStyle {
        private android.text.style.CharacterStyle mStyle;

        public Passthrough(android.text.style.CharacterStyle characterStyle) {
            this.mStyle = characterStyle;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(android.text.TextPaint textPaint) {
            this.mStyle.updateDrawState(textPaint);
        }

        @Override // android.text.style.CharacterStyle
        public android.text.style.CharacterStyle getUnderlying() {
            return this.mStyle.getUnderlying();
        }
    }
}
