package android.text;

/* loaded from: classes3.dex */
public class AutoText {
    private static final int DEFAULT = 14337;
    private static final int INCREMENT = 1024;
    private static final int RIGHT = 9300;
    private static final int TRIE_C = 0;
    private static final int TRIE_CHILD = 2;
    private static final int TRIE_NEXT = 3;
    private static final char TRIE_NULL = 65535;
    private static final int TRIE_OFF = 1;
    private static final int TRIE_ROOT = 0;
    private static final int TRIE_SIZEOF = 4;
    private static android.text.AutoText sInstance = new android.text.AutoText(android.content.res.Resources.getSystem());
    private static java.lang.Object sLock = new java.lang.Object();
    private java.util.Locale mLocale;
    private int mSize;
    private java.lang.String mText;
    private char[] mTrie;
    private char mTrieUsed;

    private AutoText(android.content.res.Resources resources) {
        this.mLocale = resources.getConfiguration().locale;
        init(resources);
    }

    private static android.text.AutoText getInstance(android.view.View view) {
        android.text.AutoText autoText;
        android.content.res.Resources resources = view.getContext().getResources();
        java.util.Locale locale = resources.getConfiguration().locale;
        synchronized (sLock) {
            autoText = sInstance;
            if (!locale.equals(autoText.mLocale)) {
                autoText = new android.text.AutoText(resources);
                sInstance = autoText;
            }
        }
        return autoText;
    }

    public static java.lang.String get(java.lang.CharSequence charSequence, int i, int i2, android.view.View view) {
        return getInstance(view).lookup(charSequence, i, i2);
    }

    public static int getSize(android.view.View view) {
        return getInstance(view).getSize();
    }

    private int getSize() {
        return this.mSize;
    }

    private java.lang.String lookup(java.lang.CharSequence charSequence, int i, int i2) {
        char c = this.mTrie[0];
        while (i < i2) {
            char charAt = charSequence.charAt(i);
            while (true) {
                if (c == 65535) {
                    break;
                }
                if (charAt != this.mTrie[c + 0]) {
                    c = this.mTrie[c + 3];
                } else {
                    if (i == i2 - 1) {
                        int i3 = c + 1;
                        if (this.mTrie[i3] != 65535) {
                            char c2 = this.mTrie[i3];
                            char charAt2 = this.mText.charAt(c2);
                            int i4 = c2 + 1;
                            return this.mText.substring(i4, charAt2 + i4);
                        }
                    }
                    c = this.mTrie[c + 2];
                }
            }
            if (c == 65535) {
                return null;
            }
            i++;
        }
        return null;
    }

    private void init(android.content.res.Resources resources) {
        char length;
        android.content.res.XmlResourceParser xml = resources.getXml(com.android.internal.R.xml.autotext);
        java.lang.StringBuilder sb = new java.lang.StringBuilder(RIGHT);
        this.mTrie = new char[14337];
        this.mTrie[0] = TRIE_NULL;
        this.mTrieUsed = (char) 1;
        try {
            try {
                com.android.internal.util.XmlUtils.beginDocument(xml, "words");
                while (true) {
                    com.android.internal.util.XmlUtils.nextElement(xml);
                    java.lang.String name = xml.getName();
                    if (name == null || !name.equals(android.provider.UserDictionary.Words.WORD)) {
                        break;
                    }
                    java.lang.String attributeValue = xml.getAttributeValue(null, "src");
                    if (xml.next() == 4) {
                        java.lang.String text = xml.getText();
                        if (text.equals("")) {
                            length = 0;
                        } else {
                            length = (char) sb.length();
                            sb.append((char) text.length());
                            sb.append(text);
                        }
                        add(attributeValue, length);
                    }
                }
                resources.flushLayoutCache();
                xml.close();
                this.mText = sb.toString();
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                throw new java.lang.RuntimeException(e2);
            }
        } catch (java.lang.Throwable th) {
            xml.close();
            throw th;
        }
    }

    private void add(java.lang.String str, char c) {
        boolean z;
        int length = str.length();
        this.mSize++;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            while (true) {
                if (this.mTrie[i] != 65535) {
                    if (charAt != this.mTrie[this.mTrie[i] + 0]) {
                        i = this.mTrie[i] + 3;
                    } else if (i2 == length - 1) {
                        this.mTrie[this.mTrie[i] + 1] = c;
                        return;
                    } else {
                        i = this.mTrie[i] + 2;
                        z = true;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (!z) {
                this.mTrie[i] = newTrieNode();
                this.mTrie[this.mTrie[i] + 0] = charAt;
                this.mTrie[this.mTrie[i] + 1] = TRIE_NULL;
                this.mTrie[this.mTrie[i] + 3] = TRIE_NULL;
                this.mTrie[this.mTrie[i] + 2] = TRIE_NULL;
                if (i2 == length - 1) {
                    this.mTrie[this.mTrie[i] + 1] = c;
                    return;
                }
                i = this.mTrie[i] + 2;
            }
        }
    }

    private char newTrieNode() {
        if (this.mTrieUsed + 4 > this.mTrie.length) {
            char[] cArr = new char[this.mTrie.length + 1024];
            java.lang.System.arraycopy(this.mTrie, 0, cArr, 0, this.mTrie.length);
            this.mTrie = cArr;
        }
        char c = this.mTrieUsed;
        this.mTrieUsed = (char) (this.mTrieUsed + 4);
        return c;
    }
}
