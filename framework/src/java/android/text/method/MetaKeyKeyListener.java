package android.text.method;

/* loaded from: classes3.dex */
public abstract class MetaKeyKeyListener {
    private static final int LOCKED = 67108881;
    private static final int LOCKED_RETURN_VALUE = 2;
    public static final int META_ALT_LOCKED = 512;
    private static final long META_ALT_MASK = 565157566611970L;
    public static final int META_ALT_ON = 2;
    private static final long META_ALT_PRESSED = 2199023255552L;
    private static final long META_ALT_RELEASED = 562949953421312L;
    private static final long META_ALT_USED = 8589934592L;
    public static final int META_CAP_LOCKED = 256;
    private static final long META_CAP_PRESSED = 1099511627776L;
    private static final long META_CAP_RELEASED = 281474976710656L;
    private static final long META_CAP_USED = 4294967296L;
    public static final int META_SELECTING = 2048;
    private static final long META_SHIFT_MASK = 282578783305985L;
    public static final int META_SHIFT_ON = 1;
    public static final int META_SYM_LOCKED = 1024;
    private static final long META_SYM_MASK = 1130315133223940L;
    public static final int META_SYM_ON = 4;
    private static final long META_SYM_PRESSED = 4398046511104L;
    private static final long META_SYM_RELEASED = 1125899906842624L;
    private static final long META_SYM_USED = 17179869184L;
    private static final int PRESSED = 16777233;
    private static final int PRESSED_RETURN_VALUE = 1;
    private static final int RELEASED = 33554449;
    private static final int USED = 50331665;
    private static final java.lang.Object CAP = new android.text.NoCopySpan.Concrete();
    private static final java.lang.Object ALT = new android.text.NoCopySpan.Concrete();
    private static final java.lang.Object SYM = new android.text.NoCopySpan.Concrete();
    private static final java.lang.Object SELECTING = new android.text.NoCopySpan.Concrete();

    public static void resetMetaState(android.text.Spannable spannable) {
        spannable.removeSpan(CAP);
        spannable.removeSpan(ALT);
        spannable.removeSpan(SYM);
        spannable.removeSpan(SELECTING);
    }

    public static final int getMetaState(java.lang.CharSequence charSequence) {
        return getActive(charSequence, SELECTING, 2048, 2048) | getActive(charSequence, CAP, 1, 256) | getActive(charSequence, ALT, 2, 512) | getActive(charSequence, SYM, 4, 1024);
    }

    public static final int getMetaState(java.lang.CharSequence charSequence, android.view.KeyEvent keyEvent) {
        int metaState = keyEvent.getMetaState();
        if (keyEvent.getKeyCharacterMap().getModifierBehavior() == 1) {
            return metaState | getMetaState(charSequence);
        }
        return metaState;
    }

    public static final int getMetaState(java.lang.CharSequence charSequence, int i) {
        switch (i) {
            case 1:
                return getActive(charSequence, CAP, 1, 2);
            case 2:
                return getActive(charSequence, ALT, 1, 2);
            case 4:
                return getActive(charSequence, SYM, 1, 2);
            case 2048:
                return getActive(charSequence, SELECTING, 1, 2);
            default:
                return 0;
        }
    }

    public static final int getMetaState(java.lang.CharSequence charSequence, int i, android.view.KeyEvent keyEvent) {
        int metaState = keyEvent.getMetaState();
        if (keyEvent.getKeyCharacterMap().getModifierBehavior() == 1) {
            metaState |= getMetaState(charSequence);
        }
        if (2048 == i) {
            return (2048 & metaState) != 0 ? 1 : 0;
        }
        return getMetaState(metaState, i);
    }

    private static int getActive(java.lang.CharSequence charSequence, java.lang.Object obj, int i, int i2) {
        if (!(charSequence instanceof android.text.Spanned)) {
            return 0;
        }
        int spanFlags = ((android.text.Spanned) charSequence).getSpanFlags(obj);
        if (spanFlags == LOCKED) {
            return i2;
        }
        if (spanFlags != 0) {
            return i;
        }
        return 0;
    }

    public static void adjustMetaAfterKeypress(android.text.Spannable spannable) {
        adjust(spannable, CAP);
        adjust(spannable, ALT);
        adjust(spannable, SYM);
    }

    public static boolean isMetaTracker(java.lang.CharSequence charSequence, java.lang.Object obj) {
        return obj == CAP || obj == ALT || obj == SYM || obj == SELECTING;
    }

    public static boolean isSelectingMetaTracker(java.lang.CharSequence charSequence, java.lang.Object obj) {
        return obj == SELECTING;
    }

    private static void adjust(android.text.Spannable spannable, java.lang.Object obj) {
        int spanFlags = spannable.getSpanFlags(obj);
        if (spanFlags == PRESSED) {
            spannable.setSpan(obj, 0, 0, USED);
        } else if (spanFlags == RELEASED) {
            spannable.removeSpan(obj);
        }
    }

    protected static void resetLockedMeta(android.text.Spannable spannable) {
        resetLock(spannable, CAP);
        resetLock(spannable, ALT);
        resetLock(spannable, SYM);
        resetLock(spannable, SELECTING);
    }

    private static void resetLock(android.text.Spannable spannable, java.lang.Object obj) {
        if (spannable.getSpanFlags(obj) == LOCKED) {
            spannable.removeSpan(obj);
        }
    }

    public boolean onKeyDown(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
        if (i == 59 || i == 60) {
            press(editable, CAP);
            return true;
        }
        if (i == 57 || i == 58 || i == 78) {
            press(editable, ALT);
            return true;
        }
        if (i == 63) {
            press(editable, SYM);
            return true;
        }
        return false;
    }

    private void press(android.text.Editable editable, java.lang.Object obj) {
        int spanFlags = editable.getSpanFlags(obj);
        if (spanFlags != PRESSED && spanFlags != USED) {
            if (spanFlags != LOCKED) {
                editable.setSpan(obj, 0, 0, PRESSED);
            } else {
                editable.removeSpan(obj);
            }
        }
    }

    public static void startSelecting(android.view.View view, android.text.Spannable spannable) {
        spannable.setSpan(SELECTING, 0, 0, PRESSED);
    }

    public static void stopSelecting(android.view.View view, android.text.Spannable spannable) {
        spannable.removeSpan(SELECTING);
    }

    public boolean onKeyUp(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
        if (i == 59 || i == 60) {
            release(editable, CAP, keyEvent);
            return true;
        }
        if (i == 57 || i == 58 || i == 78) {
            release(editable, ALT, keyEvent);
            return true;
        }
        if (i == 63) {
            release(editable, SYM, keyEvent);
            return true;
        }
        return false;
    }

    private void release(android.text.Editable editable, java.lang.Object obj, android.view.KeyEvent keyEvent) {
        int spanFlags = editable.getSpanFlags(obj);
        switch (keyEvent.getKeyCharacterMap().getModifierBehavior()) {
            case 1:
                if (spanFlags == USED) {
                    editable.removeSpan(obj);
                    break;
                } else if (spanFlags == PRESSED) {
                    editable.setSpan(obj, 0, 0, RELEASED);
                    break;
                }
                break;
            default:
                editable.removeSpan(obj);
                break;
        }
    }

    public void clearMetaKeyState(android.view.View view, android.text.Editable editable, int i) {
        clearMetaKeyState(editable, i);
    }

    public static void clearMetaKeyState(android.text.Editable editable, int i) {
        if ((i & 1) != 0) {
            editable.removeSpan(CAP);
        }
        if ((i & 2) != 0) {
            editable.removeSpan(ALT);
        }
        if ((i & 4) != 0) {
            editable.removeSpan(SYM);
        }
        if ((i & 2048) != 0) {
            editable.removeSpan(SELECTING);
        }
    }

    public static long resetLockedMeta(long j) {
        if ((256 & j) != 0) {
            j &= -282578783305986L;
        }
        if ((512 & j) != 0) {
            j &= -565157566611971L;
        }
        if ((1024 & j) != 0) {
            return j & (-1130315133223941L);
        }
        return j;
    }

    public static final int getMetaState(long j) {
        int i;
        if ((256 & j) != 0) {
            i = 256;
        } else if ((1 & j) == 0) {
            i = 0;
        } else {
            i = 1;
        }
        if ((512 & j) != 0) {
            i |= 512;
        } else if ((2 & j) != 0) {
            i |= 2;
        }
        if ((1024 & j) != 0) {
            return i | 1024;
        }
        if ((j & 4) != 0) {
            return i | 4;
        }
        return i;
    }

    public static final int getMetaState(long j, int i) {
        switch (i) {
            case 1:
                if ((256 & j) != 0) {
                    return 2;
                }
                return (j & 1) != 0 ? 1 : 0;
            case 2:
                if ((512 & j) != 0) {
                    return 2;
                }
                return (j & 2) != 0 ? 1 : 0;
            case 3:
            default:
                return 0;
            case 4:
                if ((1024 & j) != 0) {
                    return 2;
                }
                return (j & 4) != 0 ? 1 : 0;
        }
    }

    public static long adjustMetaAfterKeypress(long j) {
        if ((1099511627776L & j) != 0) {
            j = (j & (-282578783305986L)) | 1 | 4294967296L;
        } else if ((281474976710656L & j) != 0) {
            j &= -282578783305986L;
        }
        if ((2199023255552L & j) != 0) {
            j = (j & (-565157566611971L)) | 2 | 8589934592L;
        } else if ((562949953421312L & j) != 0) {
            j &= -565157566611971L;
        }
        if ((4398046511104L & j) != 0) {
            return (j & (-1130315133223941L)) | 4 | 17179869184L;
        }
        if ((1125899906842624L & j) != 0) {
            return j & (-1130315133223941L);
        }
        return j;
    }

    public static long handleKeyDown(long j, int i, android.view.KeyEvent keyEvent) {
        if (i != 59 && i != 60) {
            if (i != 57 && i != 58 && i != 78) {
                if (i == 63) {
                    return press(j, 4, META_SYM_MASK, 1024L, 4398046511104L, 1125899906842624L, 17179869184L);
                }
                return j;
            }
            return press(j, 2, META_ALT_MASK, 512L, 2199023255552L, 562949953421312L, 8589934592L);
        }
        return press(j, 1, META_SHIFT_MASK, 256L, 1099511627776L, 281474976710656L, 4294967296L);
    }

    private static long press(long j, int i, long j2, long j3, long j4, long j5, long j6) {
        if ((j & j4) == 0) {
            if ((j & j5) != 0) {
                return ((~j2) & j) | i | j3;
            }
            if ((j & j6) == 0) {
                if ((j & j3) != 0) {
                    return (~j2) & j;
                }
                return i | j4 | j;
            }
        }
        return j;
    }

    public static long handleKeyUp(long j, int i, android.view.KeyEvent keyEvent) {
        if (i != 59 && i != 60) {
            if (i != 57 && i != 58 && i != 78) {
                if (i == 63) {
                    return release(j, 4, META_SYM_MASK, 4398046511104L, 1125899906842624L, 17179869184L, keyEvent);
                }
                return j;
            }
            return release(j, 2, META_ALT_MASK, 2199023255552L, 562949953421312L, 8589934592L, keyEvent);
        }
        return release(j, 1, META_SHIFT_MASK, 1099511627776L, 281474976710656L, 4294967296L, keyEvent);
    }

    private static long release(long j, int i, long j2, long j3, long j4, long j5, android.view.KeyEvent keyEvent) {
        switch (keyEvent.getKeyCharacterMap().getModifierBehavior()) {
            case 1:
                if ((j5 & j) != 0) {
                    return j & (~j2);
                }
                if ((j & j3) != 0) {
                    return j | i | j4;
                }
                return j;
            default:
                return j & (~j2);
        }
    }

    public long clearMetaKeyState(long j, int i) {
        if ((i & 1) != 0 && (256 & j) != 0) {
            j &= -282578783305986L;
        }
        if ((i & 2) != 0 && (512 & j) != 0) {
            j &= -565157566611971L;
        }
        if ((i & 4) != 0 && (1024 & j) != 0) {
            return j & (-1130315133223941L);
        }
        return j;
    }
}
