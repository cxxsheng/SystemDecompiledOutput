package android.text.method;

/* loaded from: classes3.dex */
public class DialerKeyListener extends android.text.method.NumberKeyListener {
    public static final char[] CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '#', '*', '+', '-', '(', ')', ',', '/', android.telephony.PhoneNumberUtils.WILD, '.', ' ', ';'};
    private static android.text.method.DialerKeyListener sInstance;

    @Override // android.text.method.NumberKeyListener
    protected char[] getAcceptedChars() {
        return CHARACTERS;
    }

    public static android.text.method.DialerKeyListener getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        sInstance = new android.text.method.DialerKeyListener();
        return sInstance;
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return 3;
    }

    @Override // android.text.method.NumberKeyListener
    protected int lookup(android.view.KeyEvent keyEvent, android.text.Spannable spannable) {
        int metaState = getMetaState(spannable, keyEvent);
        char number = keyEvent.getNumber();
        if ((metaState & 3) == 0 && number != 0) {
            return number;
        }
        int lookup = super.lookup(keyEvent, spannable);
        if (lookup != 0) {
            return lookup;
        }
        if (metaState != 0) {
            android.view.KeyCharacterMap.KeyData keyData = new android.view.KeyCharacterMap.KeyData();
            char[] acceptedChars = getAcceptedChars();
            if (keyEvent.getKeyData(keyData)) {
                for (int i = 1; i < keyData.meta.length; i++) {
                    if (ok(acceptedChars, keyData.meta[i])) {
                        return keyData.meta[i];
                    }
                }
            }
        }
        return number;
    }
}
