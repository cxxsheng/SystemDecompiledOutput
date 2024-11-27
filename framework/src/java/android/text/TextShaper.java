package android.text;

/* loaded from: classes3.dex */
public class TextShaper {

    public interface GlyphsConsumer {
        void accept(int i, int i2, android.graphics.text.PositionedGlyphs positionedGlyphs, android.text.TextPaint textPaint);
    }

    private TextShaper() {
    }

    public static void shapeText(java.lang.CharSequence charSequence, int i, int i2, android.text.TextDirectionHeuristic textDirectionHeuristic, android.text.TextPaint textPaint, android.text.TextShaper.GlyphsConsumer glyphsConsumer) {
        int i3 = i + i2;
        android.text.MeasuredParagraph buildForBidi = android.text.MeasuredParagraph.buildForBidi(charSequence, i, i3, textDirectionHeuristic, null);
        android.text.TextLine obtain = android.text.TextLine.obtain();
        try {
            obtain.set(textPaint, charSequence, i, i3, buildForBidi.getParagraphDir(), buildForBidi.getDirections(0, i2), false, null, -1, -1, false);
            obtain.shape(glyphsConsumer);
        } finally {
            android.text.TextLine.recycle(obtain);
        }
    }
}
