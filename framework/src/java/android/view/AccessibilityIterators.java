package android.view;

/* loaded from: classes4.dex */
public final class AccessibilityIterators {

    public interface TextSegmentIterator {
        int[] following(int i);

        int[] preceding(int i);
    }

    public static abstract class AbstractTextSegmentIterator implements android.view.AccessibilityIterators.TextSegmentIterator {
        private final int[] mSegment = new int[2];
        protected java.lang.String mText;

        public void initialize(java.lang.String str) {
            this.mText = str;
        }

        protected int[] getRange(int i, int i2) {
            if (i < 0 || i2 < 0 || i == i2) {
                return null;
            }
            this.mSegment[0] = i;
            this.mSegment[1] = i2;
            return this.mSegment;
        }
    }

    static class CharacterTextSegmentIterator extends android.view.AccessibilityIterators.AbstractTextSegmentIterator implements android.view.ViewRootImpl.ConfigChangedCallback {
        private static android.view.AccessibilityIterators.CharacterTextSegmentIterator sInstance;
        protected java.text.BreakIterator mImpl;
        private java.util.Locale mLocale;

        public static android.view.AccessibilityIterators.CharacterTextSegmentIterator getInstance(java.util.Locale locale) {
            if (sInstance == null) {
                sInstance = new android.view.AccessibilityIterators.CharacterTextSegmentIterator(locale);
            }
            return sInstance;
        }

        private CharacterTextSegmentIterator(java.util.Locale locale) {
            this.mLocale = locale;
            onLocaleChanged(locale);
            android.view.ViewRootImpl.addConfigCallback(this);
        }

        @Override // android.view.AccessibilityIterators.AbstractTextSegmentIterator
        public void initialize(java.lang.String str) {
            super.initialize(str);
            this.mImpl.setText(str);
        }

        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] following(int i) {
            int length = this.mText.length();
            if (length <= 0 || i >= length) {
                return null;
            }
            if (i < 0) {
                i = 0;
            }
            while (!this.mImpl.isBoundary(i)) {
                i = this.mImpl.following(i);
                if (i == -1) {
                    return null;
                }
            }
            int following = this.mImpl.following(i);
            if (following == -1) {
                return null;
            }
            return getRange(i, following);
        }

        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] preceding(int i) {
            int length = this.mText.length();
            if (length <= 0 || i <= 0) {
                return null;
            }
            if (i > length) {
                i = length;
            }
            while (!this.mImpl.isBoundary(i)) {
                i = this.mImpl.preceding(i);
                if (i == -1) {
                    return null;
                }
            }
            int preceding = this.mImpl.preceding(i);
            if (preceding == -1) {
                return null;
            }
            return getRange(preceding, i);
        }

        @Override // android.view.ViewRootImpl.ConfigChangedCallback
        public void onConfigurationChanged(android.content.res.Configuration configuration) {
            java.util.Locale locale = configuration.getLocales().get(0);
            if (locale != null && !this.mLocale.equals(locale)) {
                this.mLocale = locale;
                onLocaleChanged(locale);
            }
        }

        protected void onLocaleChanged(java.util.Locale locale) {
            this.mImpl = java.text.BreakIterator.getCharacterInstance(locale);
        }
    }

    static class WordTextSegmentIterator extends android.view.AccessibilityIterators.CharacterTextSegmentIterator {
        private static android.view.AccessibilityIterators.WordTextSegmentIterator sInstance;

        public static android.view.AccessibilityIterators.WordTextSegmentIterator getInstance(java.util.Locale locale) {
            if (sInstance == null) {
                sInstance = new android.view.AccessibilityIterators.WordTextSegmentIterator(locale);
            }
            return sInstance;
        }

        private WordTextSegmentIterator(java.util.Locale locale) {
            super(locale);
        }

        @Override // android.view.AccessibilityIterators.CharacterTextSegmentIterator
        protected void onLocaleChanged(java.util.Locale locale) {
            this.mImpl = java.text.BreakIterator.getWordInstance(locale);
        }

        @Override // android.view.AccessibilityIterators.CharacterTextSegmentIterator, android.view.AccessibilityIterators.TextSegmentIterator
        public int[] following(int i) {
            if (this.mText.length() <= 0 || i >= this.mText.length()) {
                return null;
            }
            if (i < 0) {
                i = 0;
            }
            while (!isLetterOrDigit(i) && !isStartBoundary(i)) {
                i = this.mImpl.following(i);
                if (i == -1) {
                    return null;
                }
            }
            int following = this.mImpl.following(i);
            if (following == -1 || !isEndBoundary(following)) {
                return null;
            }
            return getRange(i, following);
        }

        @Override // android.view.AccessibilityIterators.CharacterTextSegmentIterator, android.view.AccessibilityIterators.TextSegmentIterator
        public int[] preceding(int i) {
            int length = this.mText.length();
            if (length <= 0 || i <= 0) {
                return null;
            }
            if (i > length) {
                i = length;
            }
            while (i > 0 && !isLetterOrDigit(i - 1) && !isEndBoundary(i)) {
                i = this.mImpl.preceding(i);
                if (i == -1) {
                    return null;
                }
            }
            int preceding = this.mImpl.preceding(i);
            if (preceding == -1 || !isStartBoundary(preceding)) {
                return null;
            }
            return getRange(preceding, i);
        }

        private boolean isStartBoundary(int i) {
            return isLetterOrDigit(i) && (i == 0 || !isLetterOrDigit(i - 1));
        }

        private boolean isEndBoundary(int i) {
            return i > 0 && isLetterOrDigit(i + (-1)) && (i == this.mText.length() || !isLetterOrDigit(i));
        }

        private boolean isLetterOrDigit(int i) {
            if (i >= 0 && i < this.mText.length()) {
                return java.lang.Character.isLetterOrDigit(this.mText.codePointAt(i));
            }
            return false;
        }
    }

    static class ParagraphTextSegmentIterator extends android.view.AccessibilityIterators.AbstractTextSegmentIterator {
        private static android.view.AccessibilityIterators.ParagraphTextSegmentIterator sInstance;

        ParagraphTextSegmentIterator() {
        }

        public static android.view.AccessibilityIterators.ParagraphTextSegmentIterator getInstance() {
            if (sInstance == null) {
                sInstance = new android.view.AccessibilityIterators.ParagraphTextSegmentIterator();
            }
            return sInstance;
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0028, code lost:
        
            return null;
         */
        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int[] following(int i) {
            int length = this.mText.length();
            if (length <= 0 || i >= length) {
                return null;
            }
            if (i < 0) {
                i = 0;
            }
            while (i < length && this.mText.charAt(i) == '\n' && !isStartBoundary(i)) {
                i++;
            }
            int i2 = i + 1;
            while (i2 < length && !isEndBoundary(i2)) {
                i2++;
            }
            return getRange(i, i2);
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x002a, code lost:
        
            return null;
         */
        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int[] preceding(int i) {
            int length = this.mText.length();
            if (length <= 0 || i <= 0) {
                return null;
            }
            if (i > length) {
                i = length;
            }
            while (i > 0 && this.mText.charAt(i - 1) == '\n' && !isEndBoundary(i)) {
                i--;
            }
            int i2 = i - 1;
            while (i2 > 0 && !isStartBoundary(i2)) {
                i2--;
            }
            return getRange(i2, i);
        }

        private boolean isStartBoundary(int i) {
            return this.mText.charAt(i) != '\n' && (i == 0 || this.mText.charAt(i - 1) == '\n');
        }

        private boolean isEndBoundary(int i) {
            return i > 0 && this.mText.charAt(i + (-1)) != '\n' && (i == this.mText.length() || this.mText.charAt(i) == '\n');
        }
    }
}
