package org.apache.http.conn.ssl;

@java.lang.Deprecated
/* loaded from: classes5.dex */
final class AndroidDistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;
    private final java.lang.String dn;
    private int end;
    private final int length;
    private int pos;

    public AndroidDistinguishedNameParser(javax.security.auth.x500.X500Principal x500Principal) {
        this.dn = x500Principal.getName("RFC2253");
        this.length = this.dn.length();
    }

    private java.lang.String nextAT() {
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            this.pos++;
        }
        if (this.pos == this.length) {
            return null;
        }
        this.beg = this.pos;
        this.pos++;
        while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] != ' ') {
            this.pos++;
        }
        if (this.pos >= this.length) {
            throw new java.lang.IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        this.end = this.pos;
        if (this.chars[this.pos] == ' ') {
            while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] == ' ') {
                this.pos++;
            }
            if (this.chars[this.pos] != '=' || this.pos == this.length) {
                throw new java.lang.IllegalStateException("Unexpected end of DN: " + this.dn);
            }
        }
        this.pos++;
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            this.pos++;
        }
        if (this.end - this.beg > 4 && this.chars[this.beg + 3] == '.' && ((this.chars[this.beg] == 'O' || this.chars[this.beg] == 'o') && ((this.chars[this.beg + 1] == 'I' || this.chars[this.beg + 1] == 'i') && (this.chars[this.beg + 2] == 'D' || this.chars[this.beg + 2] == 'd')))) {
            this.beg += 4;
        }
        return new java.lang.String(this.chars, this.beg, this.end - this.beg);
    }

    private java.lang.String quotedAV() {
        this.pos++;
        this.beg = this.pos;
        this.end = this.beg;
        while (this.pos != this.length) {
            if (this.chars[this.pos] == '\"') {
                this.pos++;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    this.pos++;
                }
                return new java.lang.String(this.chars, this.beg, this.end - this.beg);
            }
            if (this.chars[this.pos] == '\\') {
                this.chars[this.end] = getEscaped();
            } else {
                this.chars[this.end] = this.chars[this.pos];
            }
            this.pos++;
            this.end++;
        }
        throw new java.lang.IllegalStateException("Unexpected end of DN: " + this.dn);
    }

    private java.lang.String hexAV() {
        int i;
        if (this.pos + 4 >= this.length) {
            throw new java.lang.IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        this.beg = this.pos;
        this.pos++;
        while (this.pos != this.length && this.chars[this.pos] != '+' && this.chars[this.pos] != ',' && this.chars[this.pos] != ';') {
            if (this.chars[this.pos] == ' ') {
                this.end = this.pos;
                this.pos++;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    this.pos++;
                }
                i = this.end - this.beg;
                if (i >= 5 || (i & 1) == 0) {
                    throw new java.lang.IllegalStateException("Unexpected end of DN: " + this.dn);
                }
                int i2 = i / 2;
                byte[] bArr = new byte[i2];
                int i3 = this.beg + 1;
                for (int i4 = 0; i4 < i2; i4++) {
                    bArr[i4] = (byte) getByte(i3);
                    i3 += 2;
                }
                return new java.lang.String(this.chars, this.beg, i);
            }
            if (this.chars[this.pos] >= 'A' && this.chars[this.pos] <= 'F') {
                char[] cArr = this.chars;
                int i5 = this.pos;
                cArr[i5] = (char) (cArr[i5] + ' ');
            }
            this.pos++;
        }
        this.end = this.pos;
        i = this.end - this.beg;
        if (i >= 5) {
        }
        throw new java.lang.IllegalStateException("Unexpected end of DN: " + this.dn);
    }

    private java.lang.String escapedAV() {
        this.beg = this.pos;
        this.end = this.pos;
        while (this.pos < this.length) {
            switch (this.chars[this.pos]) {
                case ' ':
                    this.cur = this.end;
                    this.pos++;
                    char[] cArr = this.chars;
                    int i = this.end;
                    this.end = i + 1;
                    cArr[i] = ' ';
                    while (this.pos < this.length && this.chars[this.pos] == ' ') {
                        char[] cArr2 = this.chars;
                        int i2 = this.end;
                        this.end = i2 + 1;
                        cArr2[i2] = ' ';
                        this.pos++;
                    }
                    if (this.pos != this.length && this.chars[this.pos] != ',' && this.chars[this.pos] != '+' && this.chars[this.pos] != ';') {
                        break;
                    } else {
                        return new java.lang.String(this.chars, this.beg, this.cur - this.beg);
                    }
                case '+':
                case ',':
                case ';':
                    return new java.lang.String(this.chars, this.beg, this.end - this.beg);
                case '\\':
                    char[] cArr3 = this.chars;
                    int i3 = this.end;
                    this.end = i3 + 1;
                    cArr3[i3] = getEscaped();
                    this.pos++;
                    break;
                default:
                    char[] cArr4 = this.chars;
                    int i4 = this.end;
                    this.end = i4 + 1;
                    cArr4[i4] = this.chars[this.pos];
                    this.pos++;
                    break;
            }
        }
        return new java.lang.String(this.chars, this.beg, this.end - this.beg);
    }

    private char getEscaped() {
        this.pos++;
        if (this.pos == this.length) {
            throw new java.lang.IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        switch (this.chars[this.pos]) {
            case ' ':
            case '\"':
            case '#':
            case '%':
            case '*':
            case '+':
            case ',':
            case ';':
            case '<':
            case '=':
            case '>':
            case '\\':
            case '_':
                return this.chars[this.pos];
            default:
                return getUTF8();
        }
    }

    private char getUTF8() {
        int i;
        int i2;
        int i3 = getByte(this.pos);
        this.pos++;
        if (i3 < 128) {
            return (char) i3;
        }
        if (i3 < 192 || i3 > 247) {
            return '?';
        }
        if (i3 <= 223) {
            i = i3 & 31;
            i2 = 1;
        } else if (i3 <= 239) {
            i = i3 & 15;
            i2 = 2;
        } else {
            i = i3 & 7;
            i2 = 3;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            this.pos++;
            if (this.pos == this.length || this.chars[this.pos] != '\\') {
                return '?';
            }
            this.pos++;
            int i5 = getByte(this.pos);
            this.pos++;
            if ((i5 & 192) != 128) {
                return '?';
            }
            i = (i << 6) + (i5 & 63);
        }
        return (char) i;
    }

    private int getByte(int i) {
        int i2;
        int i3;
        int i4 = i + 1;
        if (i4 >= this.length) {
            throw new java.lang.IllegalStateException("Malformed DN: " + this.dn);
        }
        char c = this.chars[i];
        if (c >= '0' && c <= '9') {
            i2 = c - '0';
        } else if (c >= 'a' && c <= 'f') {
            i2 = c - 'W';
        } else if (c >= 'A' && c <= 'F') {
            i2 = c - '7';
        } else {
            throw new java.lang.IllegalStateException("Malformed DN: " + this.dn);
        }
        char c2 = this.chars[i4];
        if (c2 >= '0' && c2 <= '9') {
            i3 = c2 - '0';
        } else if (c2 >= 'a' && c2 <= 'f') {
            i3 = c2 - 'W';
        } else if (c2 >= 'A' && c2 <= 'F') {
            i3 = c2 - '7';
        } else {
            throw new java.lang.IllegalStateException("Malformed DN: " + this.dn);
        }
        return (i2 << 4) + i3;
    }

    public java.lang.String findMostSpecific(java.lang.String str) {
        java.lang.String quotedAV;
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.dn.toCharArray();
        java.lang.String nextAT = nextAT();
        if (nextAT == null) {
            return null;
        }
        while (this.pos != this.length) {
            switch (this.chars[this.pos]) {
                case '\"':
                    quotedAV = quotedAV();
                    break;
                case '#':
                    quotedAV = hexAV();
                    break;
                case '+':
                case ',':
                case ';':
                    quotedAV = "";
                    break;
                default:
                    quotedAV = escapedAV();
                    break;
            }
            if (str.equalsIgnoreCase(nextAT)) {
                return quotedAV;
            }
            if (this.pos >= this.length) {
                return null;
            }
            if (this.chars[this.pos] != ',' && this.chars[this.pos] != ';' && this.chars[this.pos] != '+') {
                throw new java.lang.IllegalStateException("Malformed DN: " + this.dn);
            }
            this.pos++;
            nextAT = nextAT();
            if (nextAT == null) {
                throw new java.lang.IllegalStateException("Malformed DN: " + this.dn);
            }
        }
        return null;
    }

    public java.util.List<java.lang.String> getAllMostSpecificFirst(java.lang.String str) {
        java.lang.String quotedAV;
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.dn.toCharArray();
        java.util.List<java.lang.String> emptyList = java.util.Collections.emptyList();
        java.lang.String nextAT = nextAT();
        if (nextAT == null) {
            return emptyList;
        }
        while (this.pos < this.length) {
            switch (this.chars[this.pos]) {
                case '\"':
                    quotedAV = quotedAV();
                    break;
                case '#':
                    quotedAV = hexAV();
                    break;
                case '+':
                case ',':
                case ';':
                    quotedAV = "";
                    break;
                default:
                    quotedAV = escapedAV();
                    break;
            }
            if (str.equalsIgnoreCase(nextAT)) {
                if (emptyList.isEmpty()) {
                    emptyList = new java.util.ArrayList<>();
                }
                emptyList.add(quotedAV);
            }
            if (this.pos < this.length) {
                if (this.chars[this.pos] != ',' && this.chars[this.pos] != ';' && this.chars[this.pos] != '+') {
                    throw new java.lang.IllegalStateException("Malformed DN: " + this.dn);
                }
                this.pos++;
                nextAT = nextAT();
                if (nextAT == null) {
                    throw new java.lang.IllegalStateException("Malformed DN: " + this.dn);
                }
            } else {
                return emptyList;
            }
        }
        return emptyList;
    }
}
