package android.text;

/* loaded from: classes3.dex */
public class Emoji {
    public static int COMBINING_ENCLOSING_KEYCAP = 8419;
    public static int ZERO_WIDTH_JOINER = 8205;
    public static int VARIATION_SELECTOR_16 = 65039;
    public static int CANCEL_TAG = 917631;

    public static boolean isRegionalIndicatorSymbol(int i) {
        return 127462 <= i && i <= 127487;
    }

    public static boolean isEmojiModifier(int i) {
        return android.icu.lang.UCharacter.hasBinaryProperty(i, 59);
    }

    public static boolean isEmojiModifierBase(int i) {
        if (i == 129309 || i == 129340) {
            return true;
        }
        return android.icu.lang.UCharacter.hasBinaryProperty(i, 60);
    }

    public static boolean isEmoji(int i) {
        return android.icu.lang.UCharacter.hasBinaryProperty(i, 57);
    }

    public static boolean isKeycapBase(int i) {
        return (48 <= i && i <= 57) || i == 35 || i == 42;
    }

    public static boolean isTagSpecChar(int i) {
        return 917536 <= i && i <= 917630;
    }
}
