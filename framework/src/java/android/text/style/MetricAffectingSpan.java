package android.text.style;

/* loaded from: classes3.dex */
public abstract class MetricAffectingSpan extends android.text.style.CharacterStyle implements android.text.style.UpdateLayout {
    public abstract void updateMeasureState(android.text.TextPaint textPaint);

    @Override // android.text.style.CharacterStyle
    public android.text.style.MetricAffectingSpan getUnderlying() {
        return this;
    }

    static class Passthrough extends android.text.style.MetricAffectingSpan {
        private android.text.style.MetricAffectingSpan mStyle;

        Passthrough(android.text.style.MetricAffectingSpan metricAffectingSpan) {
            this.mStyle = metricAffectingSpan;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(android.text.TextPaint textPaint) {
            this.mStyle.updateDrawState(textPaint);
        }

        @Override // android.text.style.MetricAffectingSpan
        public void updateMeasureState(android.text.TextPaint textPaint) {
            this.mStyle.updateMeasureState(textPaint);
        }

        @Override // android.text.style.MetricAffectingSpan, android.text.style.CharacterStyle
        public android.text.style.MetricAffectingSpan getUnderlying() {
            return this.mStyle.getUnderlying();
        }
    }
}
