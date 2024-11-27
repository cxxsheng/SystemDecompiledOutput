package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class Tokenizer {
    private static final java.lang.String TAG = "Tokenizer";
    private int mHandledLen;
    private java.lang.String mLine;
    private android.media.Tokenizer.OnTokenListener mListener;
    private android.media.Tokenizer.TokenizerPhase mPhase;
    private android.media.Tokenizer.TokenizerPhase mDataTokenizer = new android.media.Tokenizer.DataTokenizer();
    private android.media.Tokenizer.TokenizerPhase mTagTokenizer = new android.media.Tokenizer.TagTokenizer();

    /* compiled from: WebVttRenderer.java */
    interface OnTokenListener {
        void onData(java.lang.String str);

        void onEnd(java.lang.String str);

        void onLineEnd();

        void onStart(java.lang.String str, java.lang.String[] strArr, java.lang.String str2);

        void onTimeStamp(long j);
    }

    /* compiled from: WebVttRenderer.java */
    interface TokenizerPhase {
        android.media.Tokenizer.TokenizerPhase start();

        void tokenize();
    }

    /* compiled from: WebVttRenderer.java */
    class DataTokenizer implements android.media.Tokenizer.TokenizerPhase {
        private java.lang.StringBuilder mData;

        DataTokenizer() {
        }

        @Override // android.media.Tokenizer.TokenizerPhase
        public android.media.Tokenizer.TokenizerPhase start() {
            this.mData = new java.lang.StringBuilder();
            return this;
        }

        private boolean replaceEscape(java.lang.String str, java.lang.String str2, int i) {
            if (android.media.Tokenizer.this.mLine.startsWith(str, i)) {
                this.mData.append(android.media.Tokenizer.this.mLine.substring(android.media.Tokenizer.this.mHandledLen, i));
                this.mData.append(str2);
                android.media.Tokenizer.this.mHandledLen = i + str.length();
                int unused = android.media.Tokenizer.this.mHandledLen;
                return true;
            }
            return false;
        }

        @Override // android.media.Tokenizer.TokenizerPhase
        public void tokenize() {
            int length = android.media.Tokenizer.this.mLine.length();
            int i = android.media.Tokenizer.this.mHandledLen;
            while (true) {
                if (i >= android.media.Tokenizer.this.mLine.length()) {
                    break;
                }
                if (android.media.Tokenizer.this.mLine.charAt(i) == '&') {
                    if (!replaceEscape("&amp;", "&", i) && !replaceEscape("&lt;", "<", i) && !replaceEscape("&gt;", ">", i) && !replaceEscape("&lrm;", "\u200e", i) && !replaceEscape("&rlm;", "\u200f", i)) {
                        replaceEscape("&nbsp;", " ", i);
                    }
                } else if (android.media.Tokenizer.this.mLine.charAt(i) == '<') {
                    android.media.Tokenizer.this.mPhase = android.media.Tokenizer.this.mTagTokenizer.start();
                    length = i;
                    break;
                }
                i++;
            }
            this.mData.append(android.media.Tokenizer.this.mLine.substring(android.media.Tokenizer.this.mHandledLen, length));
            android.media.Tokenizer.this.mListener.onData(this.mData.toString());
            this.mData.delete(0, this.mData.length());
            android.media.Tokenizer.this.mHandledLen = length;
        }
    }

    /* compiled from: WebVttRenderer.java */
    class TagTokenizer implements android.media.Tokenizer.TokenizerPhase {
        private java.lang.String mAnnotation;
        private boolean mAtAnnotation;
        private java.lang.String mName;

        TagTokenizer() {
        }

        @Override // android.media.Tokenizer.TokenizerPhase
        public android.media.Tokenizer.TokenizerPhase start() {
            this.mAnnotation = "";
            this.mName = "";
            this.mAtAnnotation = false;
            return this;
        }

        @Override // android.media.Tokenizer.TokenizerPhase
        public void tokenize() {
            java.lang.String[] split;
            if (!this.mAtAnnotation) {
                android.media.Tokenizer.this.mHandledLen++;
            }
            if (android.media.Tokenizer.this.mHandledLen < android.media.Tokenizer.this.mLine.length()) {
                if (this.mAtAnnotation || android.media.Tokenizer.this.mLine.charAt(android.media.Tokenizer.this.mHandledLen) == '/') {
                    split = android.media.Tokenizer.this.mLine.substring(android.media.Tokenizer.this.mHandledLen).split(">");
                } else {
                    split = android.media.Tokenizer.this.mLine.substring(android.media.Tokenizer.this.mHandledLen).split("[\t\f >]");
                }
                java.lang.String substring = android.media.Tokenizer.this.mLine.substring(android.media.Tokenizer.this.mHandledLen, android.media.Tokenizer.this.mHandledLen + split[0].length());
                android.media.Tokenizer.this.mHandledLen += split[0].length();
                if (this.mAtAnnotation) {
                    this.mAnnotation += " " + substring;
                } else {
                    this.mName = substring;
                }
            }
            this.mAtAnnotation = true;
            if (android.media.Tokenizer.this.mHandledLen < android.media.Tokenizer.this.mLine.length() && android.media.Tokenizer.this.mLine.charAt(android.media.Tokenizer.this.mHandledLen) == '>') {
                yield_tag();
                android.media.Tokenizer.this.mPhase = android.media.Tokenizer.this.mDataTokenizer.start();
                android.media.Tokenizer.this.mHandledLen++;
            }
        }

        private void yield_tag() {
            java.lang.String[] strArr;
            if (this.mName.startsWith("/")) {
                android.media.Tokenizer.this.mListener.onEnd(this.mName.substring(1));
                return;
            }
            if (this.mName.length() > 0 && java.lang.Character.isDigit(this.mName.charAt(0))) {
                try {
                    android.media.Tokenizer.this.mListener.onTimeStamp(android.media.WebVttParser.parseTimestampMs(this.mName));
                    return;
                } catch (java.lang.NumberFormatException e) {
                    android.util.Log.d(android.media.Tokenizer.TAG, "invalid timestamp tag: <" + this.mName + ">");
                    return;
                }
            }
            this.mAnnotation = this.mAnnotation.replaceAll("\\s+", " ");
            if (this.mAnnotation.startsWith(" ")) {
                this.mAnnotation = this.mAnnotation.substring(1);
            }
            if (this.mAnnotation.endsWith(" ")) {
                this.mAnnotation = this.mAnnotation.substring(0, this.mAnnotation.length() - 1);
            }
            int indexOf = this.mName.indexOf(46);
            if (indexOf < 0) {
                strArr = null;
            } else {
                strArr = this.mName.substring(indexOf + 1).split("\\.");
                this.mName = this.mName.substring(0, indexOf);
            }
            android.media.Tokenizer.this.mListener.onStart(this.mName, strArr, this.mAnnotation);
        }
    }

    Tokenizer(android.media.Tokenizer.OnTokenListener onTokenListener) {
        reset();
        this.mListener = onTokenListener;
    }

    void reset() {
        this.mPhase = this.mDataTokenizer.start();
    }

    void tokenize(java.lang.String str) {
        this.mHandledLen = 0;
        this.mLine = str;
        while (this.mHandledLen < this.mLine.length()) {
            this.mPhase.tokenize();
        }
        if (!(this.mPhase instanceof android.media.Tokenizer.TagTokenizer)) {
            this.mListener.onLineEnd();
        }
    }
}
