package android.text.style;

/* loaded from: classes3.dex */
public interface TabStopSpan extends android.text.style.ParagraphStyle {
    int getTabStop();

    public static class Standard implements android.text.style.TabStopSpan {
        private int mTabOffset;

        public Standard(int i) {
            this.mTabOffset = i;
        }

        @Override // android.text.style.TabStopSpan
        public int getTabStop() {
            return this.mTabOffset;
        }

        public java.lang.String toString() {
            return "TabStopSpan.Standard{tabOffset=" + getTabStop() + '}';
        }
    }
}
