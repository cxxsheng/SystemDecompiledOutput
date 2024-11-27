package android.text;

/* loaded from: classes3.dex */
public class TextDirectionHeuristics {
    public static final android.text.TextDirectionHeuristic ANYRTL_LTR;
    public static final android.text.TextDirectionHeuristic FIRSTSTRONG_LTR;
    public static final android.text.TextDirectionHeuristic FIRSTSTRONG_RTL;
    public static final android.text.TextDirectionHeuristic LOCALE = android.text.TextDirectionHeuristics.TextDirectionHeuristicLocale.INSTANCE;
    public static final android.text.TextDirectionHeuristic LTR;
    public static final android.text.TextDirectionHeuristic RTL;
    private static final int STATE_FALSE = 1;
    private static final int STATE_TRUE = 0;
    private static final int STATE_UNKNOWN = 2;

    private interface TextDirectionAlgorithm {
        int checkRtl(java.lang.CharSequence charSequence, int i, int i2);
    }

    static {
        boolean z = false;
        LTR = new android.text.TextDirectionHeuristics.TextDirectionHeuristicInternal(null, z);
        boolean z2 = true;
        RTL = new android.text.TextDirectionHeuristics.TextDirectionHeuristicInternal(0 == true ? 1 : 0, z2);
        FIRSTSTRONG_LTR = new android.text.TextDirectionHeuristics.TextDirectionHeuristicInternal(android.text.TextDirectionHeuristics.FirstStrong.INSTANCE, z);
        FIRSTSTRONG_RTL = new android.text.TextDirectionHeuristics.TextDirectionHeuristicInternal(android.text.TextDirectionHeuristics.FirstStrong.INSTANCE, z2);
        ANYRTL_LTR = new android.text.TextDirectionHeuristics.TextDirectionHeuristicInternal(android.text.TextDirectionHeuristics.AnyStrong.INSTANCE_RTL, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int isRtlCodePoint(int i) {
        switch (java.lang.Character.getDirectionality(i)) {
            case -1:
                if ((1424 > i || i > 2303) && ((64285 > i || i > 64975) && ((65008 > i || i > 65023) && ((65136 > i || i > 65279) && ((67584 > i || i > 69631) && (124928 > i || i > 126975)))))) {
                    return ((8293 > i || i > 8297) && (65520 > i || i > 65528) && ((917504 > i || i > 921599) && ((64976 > i || i > 65007) && (i & android.content.res.Configuration.DENSITY_DPI_ANY) != 65534 && ((8352 > i || i > 8399) && (55296 > i || i > 57343))))) ? 1 : 2;
                }
                return 0;
            case 0:
                return 1;
            case 1:
            case 2:
                return 0;
            default:
                return 2;
        }
    }

    private static abstract class TextDirectionHeuristicImpl implements android.text.TextDirectionHeuristic {
        private final android.text.TextDirectionHeuristics.TextDirectionAlgorithm mAlgorithm;

        protected abstract boolean defaultIsRtl();

        public TextDirectionHeuristicImpl(android.text.TextDirectionHeuristics.TextDirectionAlgorithm textDirectionAlgorithm) {
            this.mAlgorithm = textDirectionAlgorithm;
        }

        @Override // android.text.TextDirectionHeuristic
        public boolean isRtl(char[] cArr, int i, int i2) {
            return isRtl(java.nio.CharBuffer.wrap(cArr), i, i2);
        }

        @Override // android.text.TextDirectionHeuristic
        public boolean isRtl(java.lang.CharSequence charSequence, int i, int i2) {
            if (charSequence == null || i < 0 || i2 < 0 || charSequence.length() - i2 < i) {
                throw new java.lang.IllegalArgumentException();
            }
            if (this.mAlgorithm == null) {
                return defaultIsRtl();
            }
            return doCheck(charSequence, i, i2);
        }

        private boolean doCheck(java.lang.CharSequence charSequence, int i, int i2) {
            switch (this.mAlgorithm.checkRtl(charSequence, i, i2)) {
                case 0:
                    return true;
                case 1:
                    return false;
                default:
                    return defaultIsRtl();
            }
        }
    }

    private static class TextDirectionHeuristicInternal extends android.text.TextDirectionHeuristics.TextDirectionHeuristicImpl {
        private final boolean mDefaultIsRtl;

        private TextDirectionHeuristicInternal(android.text.TextDirectionHeuristics.TextDirectionAlgorithm textDirectionAlgorithm, boolean z) {
            super(textDirectionAlgorithm);
            this.mDefaultIsRtl = z;
        }

        @Override // android.text.TextDirectionHeuristics.TextDirectionHeuristicImpl
        protected boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    private static class FirstStrong implements android.text.TextDirectionHeuristics.TextDirectionAlgorithm {
        public static final android.text.TextDirectionHeuristics.FirstStrong INSTANCE = new android.text.TextDirectionHeuristics.FirstStrong();

        @Override // android.text.TextDirectionHeuristics.TextDirectionAlgorithm
        public int checkRtl(java.lang.CharSequence charSequence, int i, int i2) {
            int i3 = i2 + i;
            int i4 = 0;
            int i5 = 2;
            while (i < i3 && i5 == 2) {
                int codePointAt = java.lang.Character.codePointAt(charSequence, i);
                if (8294 <= codePointAt && codePointAt <= 8296) {
                    i4++;
                } else if (codePointAt == 8297) {
                    if (i4 > 0) {
                        i4--;
                    }
                } else if (i4 == 0) {
                    i5 = android.text.TextDirectionHeuristics.isRtlCodePoint(codePointAt);
                }
                i += java.lang.Character.charCount(codePointAt);
            }
            return i5;
        }

        private FirstStrong() {
        }
    }

    private static class AnyStrong implements android.text.TextDirectionHeuristics.TextDirectionAlgorithm {
        private final boolean mLookForRtl;
        public static final android.text.TextDirectionHeuristics.AnyStrong INSTANCE_RTL = new android.text.TextDirectionHeuristics.AnyStrong(true);
        public static final android.text.TextDirectionHeuristics.AnyStrong INSTANCE_LTR = new android.text.TextDirectionHeuristics.AnyStrong(false);

        /* JADX WARN: Code restructure failed: missing block: B:32:0x003a, code lost:
        
            continue;
         */
        @Override // android.text.TextDirectionHeuristics.TextDirectionAlgorithm
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int checkRtl(java.lang.CharSequence charSequence, int i, int i2) {
            int i3 = i2 + i;
            boolean z = false;
            int i4 = 0;
            while (i < i3) {
                int codePointAt = java.lang.Character.codePointAt(charSequence, i);
                if (8294 <= codePointAt && codePointAt <= 8296) {
                    i4++;
                } else if (codePointAt == 8297) {
                    if (i4 > 0) {
                        i4--;
                    }
                } else if (i4 == 0) {
                    switch (android.text.TextDirectionHeuristics.isRtlCodePoint(codePointAt)) {
                        case 0:
                            if (!this.mLookForRtl) {
                                z = true;
                                break;
                            } else {
                                return 0;
                            }
                        case 1:
                            if (!this.mLookForRtl) {
                                return 1;
                            }
                            z = true;
                            break;
                    }
                } else {
                    continue;
                }
                i += java.lang.Character.charCount(codePointAt);
                z = z;
            }
            if (z) {
                return this.mLookForRtl ? 1 : 0;
            }
            return 2;
        }

        private AnyStrong(boolean z) {
            this.mLookForRtl = z;
        }
    }

    private static class TextDirectionHeuristicLocale extends android.text.TextDirectionHeuristics.TextDirectionHeuristicImpl {
        public static final android.text.TextDirectionHeuristics.TextDirectionHeuristicLocale INSTANCE = new android.text.TextDirectionHeuristics.TextDirectionHeuristicLocale();

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        @Override // android.text.TextDirectionHeuristics.TextDirectionHeuristicImpl
        protected boolean defaultIsRtl() {
            return android.text.TextUtils.getLayoutDirectionFromLocale(java.util.Locale.getDefault()) == 1;
        }
    }
}
