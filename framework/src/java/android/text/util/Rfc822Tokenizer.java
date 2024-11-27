package android.text.util;

/* loaded from: classes3.dex */
public class Rfc822Tokenizer implements android.widget.MultiAutoCompleteTextView.Tokenizer {
    public static void tokenize(java.lang.CharSequence charSequence, java.util.Collection<android.text.util.Rfc822Token> collection) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt == ',' || charAt == ';') {
                i++;
                while (i < length && charSequence.charAt(i) == ' ') {
                    i++;
                }
                crunch(sb);
                if (sb2.length() > 0) {
                    collection.add(new android.text.util.Rfc822Token(sb.toString(), sb2.toString(), sb3.toString()));
                } else if (sb.length() > 0) {
                    collection.add(new android.text.util.Rfc822Token(null, sb.toString(), sb3.toString()));
                }
                sb.setLength(0);
                sb2.setLength(0);
                sb3.setLength(0);
            } else if (charAt == '\"') {
                i++;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    char charAt2 = charSequence.charAt(i);
                    if (charAt2 == '\"') {
                        i++;
                        break;
                    } else if (charAt2 == '\\') {
                        int i2 = i + 1;
                        if (i2 < length) {
                            sb.append(charSequence.charAt(i2));
                        }
                        i += 2;
                    } else {
                        sb.append(charAt2);
                        i++;
                    }
                }
            } else if (charAt == '(') {
                i++;
                int i3 = 1;
                while (i < length && i3 > 0) {
                    char charAt3 = charSequence.charAt(i);
                    if (charAt3 == ')') {
                        if (i3 > 1) {
                            sb3.append(charAt3);
                        }
                        i3--;
                        i++;
                    } else if (charAt3 == '(') {
                        sb3.append(charAt3);
                        i3++;
                        i++;
                    } else if (charAt3 == '\\') {
                        int i4 = i + 1;
                        if (i4 < length) {
                            sb3.append(charSequence.charAt(i4));
                        }
                        i += 2;
                    } else {
                        sb3.append(charAt3);
                        i++;
                    }
                }
            } else if (charAt == '<') {
                i++;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    char charAt4 = charSequence.charAt(i);
                    if (charAt4 == '>') {
                        i++;
                        break;
                    } else {
                        sb2.append(charAt4);
                        i++;
                    }
                }
            } else if (charAt == ' ') {
                sb.append((char) 0);
                i++;
            } else {
                sb.append(charAt);
                i++;
            }
        }
        crunch(sb);
        if (sb2.length() > 0) {
            collection.add(new android.text.util.Rfc822Token(sb.toString(), sb2.toString(), sb3.toString()));
        } else if (sb.length() > 0) {
            collection.add(new android.text.util.Rfc822Token(null, sb.toString(), sb3.toString()));
        }
    }

    public static android.text.util.Rfc822Token[] tokenize(java.lang.CharSequence charSequence) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        tokenize(charSequence, arrayList);
        return (android.text.util.Rfc822Token[]) arrayList.toArray(new android.text.util.Rfc822Token[arrayList.size()]);
    }

    private static void crunch(java.lang.StringBuilder sb) {
        int length = sb.length();
        int i = 0;
        while (i < length) {
            if (sb.charAt(i) == 0) {
                if (i != 0 && i != length - 1) {
                    int i2 = i - 1;
                    if (sb.charAt(i2) != ' ' && sb.charAt(i2) != 0) {
                        int i3 = i + 1;
                        if (sb.charAt(i3) != ' ' && sb.charAt(i3) != 0) {
                            i = i3;
                        }
                    }
                }
                sb.deleteCharAt(i);
                length--;
            } else {
                i++;
            }
        }
        for (int i4 = 0; i4 < length; i4++) {
            if (sb.charAt(i4) == 0) {
                sb.setCharAt(i4, ' ');
            }
        }
    }

    @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
    public int findTokenStart(java.lang.CharSequence charSequence, int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            i2 = findTokenEnd(charSequence, i2);
            if (i2 < i) {
                i2++;
                while (i2 < i && charSequence.charAt(i2) == ' ') {
                    i2++;
                }
                if (i2 < i) {
                    i3 = i2;
                }
            }
        }
        return i3;
    }

    @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
    public int findTokenEnd(java.lang.CharSequence charSequence, int i) {
        int length = charSequence.length();
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt == ',' || charAt == ';') {
                return i;
            }
            if (charAt == '\"') {
                i++;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    char charAt2 = charSequence.charAt(i);
                    if (charAt2 == '\"') {
                        i++;
                        break;
                    }
                    if (charAt2 == '\\' && i + 1 < length) {
                        i += 2;
                    } else {
                        i++;
                    }
                }
            } else if (charAt == '(') {
                i++;
                int i2 = 1;
                while (i < length && i2 > 0) {
                    char charAt3 = charSequence.charAt(i);
                    if (charAt3 == ')') {
                        i2--;
                        i++;
                    } else if (charAt3 == '(') {
                        i2++;
                        i++;
                    } else if (charAt3 == '\\' && i + 1 < length) {
                        i += 2;
                    } else {
                        i++;
                    }
                }
            } else if (charAt == '<') {
                i++;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    if (charSequence.charAt(i) == '>') {
                        i++;
                        break;
                    }
                    i++;
                }
            } else {
                i++;
            }
        }
        return i;
    }

    @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
    public java.lang.CharSequence terminateToken(java.lang.CharSequence charSequence) {
        return ((java.lang.Object) charSequence) + ", ";
    }
}
