package android.util;

/* loaded from: classes3.dex */
public final class JsonReader implements java.io.Closeable {
    private static final java.lang.String FALSE = "false";
    private static final java.lang.String TRUE = "true";
    private final java.io.Reader in;
    private java.lang.String name;
    private boolean skipping;
    private android.util.JsonToken token;
    private java.lang.String value;
    private int valueLength;
    private int valuePos;
    private final com.android.internal.util.StringPool stringPool = new com.android.internal.util.StringPool();
    private boolean lenient = false;
    private final char[] buffer = new char[1024];
    private int pos = 0;
    private int limit = 0;
    private int bufferStartLine = 1;
    private int bufferStartColumn = 1;
    private final java.util.List<android.util.JsonScope> stack = new java.util.ArrayList();

    public JsonReader(java.io.Reader reader) {
        push(android.util.JsonScope.EMPTY_DOCUMENT);
        this.skipping = false;
        if (reader == null) {
            throw new java.lang.NullPointerException("in == null");
        }
        this.in = reader;
    }

    public void setLenient(boolean z) {
        this.lenient = z;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public void beginArray() throws java.io.IOException {
        expect(android.util.JsonToken.BEGIN_ARRAY);
    }

    public void endArray() throws java.io.IOException {
        expect(android.util.JsonToken.END_ARRAY);
    }

    public void beginObject() throws java.io.IOException {
        expect(android.util.JsonToken.BEGIN_OBJECT);
    }

    public void endObject() throws java.io.IOException {
        expect(android.util.JsonToken.END_OBJECT);
    }

    private void expect(android.util.JsonToken jsonToken) throws java.io.IOException {
        peek();
        if (this.token != jsonToken) {
            throw new java.lang.IllegalStateException("Expected " + jsonToken + " but was " + peek());
        }
        advance();
    }

    public boolean hasNext() throws java.io.IOException {
        peek();
        return (this.token == android.util.JsonToken.END_OBJECT || this.token == android.util.JsonToken.END_ARRAY) ? false : true;
    }

    public android.util.JsonToken peek() throws java.io.IOException {
        if (this.token != null) {
            return this.token;
        }
        switch (peekStack()) {
            case EMPTY_DOCUMENT:
                replaceTop(android.util.JsonScope.NONEMPTY_DOCUMENT);
                android.util.JsonToken nextValue = nextValue();
                if (!this.lenient && this.token != android.util.JsonToken.BEGIN_ARRAY && this.token != android.util.JsonToken.BEGIN_OBJECT) {
                    throw new java.io.IOException("Expected JSON document to start with '[' or '{' but was " + this.token);
                }
                return nextValue;
            case EMPTY_ARRAY:
                return nextInArray(true);
            case NONEMPTY_ARRAY:
                return nextInArray(false);
            case EMPTY_OBJECT:
                return nextInObject(true);
            case DANGLING_NAME:
                return objectValue();
            case NONEMPTY_OBJECT:
                return nextInObject(false);
            case NONEMPTY_DOCUMENT:
                try {
                    android.util.JsonToken nextValue2 = nextValue();
                    if (this.lenient) {
                        return nextValue2;
                    }
                    throw syntaxError("Expected EOF");
                } catch (java.io.EOFException e) {
                    android.util.JsonToken jsonToken = android.util.JsonToken.END_DOCUMENT;
                    this.token = jsonToken;
                    return jsonToken;
                }
            case CLOSED:
                throw new java.lang.IllegalStateException("JsonReader is closed");
            default:
                throw new java.lang.AssertionError();
        }
    }

    private android.util.JsonToken advance() throws java.io.IOException {
        peek();
        android.util.JsonToken jsonToken = this.token;
        this.token = null;
        this.value = null;
        this.name = null;
        return jsonToken;
    }

    public java.lang.String nextName() throws java.io.IOException {
        peek();
        if (this.token != android.util.JsonToken.NAME) {
            throw new java.lang.IllegalStateException("Expected a name but was " + peek());
        }
        java.lang.String str = this.name;
        advance();
        return str;
    }

    public java.lang.String nextString() throws java.io.IOException {
        peek();
        if (this.token != android.util.JsonToken.STRING && this.token != android.util.JsonToken.NUMBER) {
            throw new java.lang.IllegalStateException("Expected a string but was " + peek());
        }
        java.lang.String str = this.value;
        advance();
        return str;
    }

    public boolean nextBoolean() throws java.io.IOException {
        peek();
        if (this.token != android.util.JsonToken.BOOLEAN) {
            throw new java.lang.IllegalStateException("Expected a boolean but was " + this.token);
        }
        boolean z = this.value == TRUE;
        advance();
        return z;
    }

    public void nextNull() throws java.io.IOException {
        peek();
        if (this.token != android.util.JsonToken.NULL) {
            throw new java.lang.IllegalStateException("Expected null but was " + this.token);
        }
        advance();
    }

    public double nextDouble() throws java.io.IOException {
        peek();
        if (this.token != android.util.JsonToken.STRING && this.token != android.util.JsonToken.NUMBER) {
            throw new java.lang.IllegalStateException("Expected a double but was " + this.token);
        }
        double parseDouble = java.lang.Double.parseDouble(this.value);
        advance();
        return parseDouble;
    }

    public long nextLong() throws java.io.IOException {
        long j;
        peek();
        if (this.token != android.util.JsonToken.STRING && this.token != android.util.JsonToken.NUMBER) {
            throw new java.lang.IllegalStateException("Expected a long but was " + this.token);
        }
        try {
            j = java.lang.Long.parseLong(this.value);
        } catch (java.lang.NumberFormatException e) {
            double parseDouble = java.lang.Double.parseDouble(this.value);
            long j2 = (long) parseDouble;
            if (j2 != parseDouble) {
                throw new java.lang.NumberFormatException(this.value);
            }
            j = j2;
        }
        advance();
        return j;
    }

    public int nextInt() throws java.io.IOException {
        int i;
        peek();
        if (this.token != android.util.JsonToken.STRING && this.token != android.util.JsonToken.NUMBER) {
            throw new java.lang.IllegalStateException("Expected an int but was " + this.token);
        }
        try {
            i = java.lang.Integer.parseInt(this.value);
        } catch (java.lang.NumberFormatException e) {
            double parseDouble = java.lang.Double.parseDouble(this.value);
            int i2 = (int) parseDouble;
            if (i2 != parseDouble) {
                throw new java.lang.NumberFormatException(this.value);
            }
            i = i2;
        }
        advance();
        return i;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.value = null;
        this.token = null;
        this.stack.clear();
        this.stack.add(android.util.JsonScope.CLOSED);
        this.in.close();
    }

    public void skipValue() throws java.io.IOException {
        this.skipping = true;
        try {
            if (!hasNext() || peek() == android.util.JsonToken.END_DOCUMENT) {
                throw new java.lang.IllegalStateException("No element left to skip");
            }
            int i = 0;
            do {
                android.util.JsonToken advance = advance();
                if (advance != android.util.JsonToken.BEGIN_ARRAY && advance != android.util.JsonToken.BEGIN_OBJECT) {
                    if (advance == android.util.JsonToken.END_ARRAY || advance == android.util.JsonToken.END_OBJECT) {
                        i--;
                    }
                }
                i++;
            } while (i != 0);
        } finally {
            this.skipping = false;
        }
    }

    private android.util.JsonScope peekStack() {
        return this.stack.get(this.stack.size() - 1);
    }

    private android.util.JsonScope pop() {
        return this.stack.remove(this.stack.size() - 1);
    }

    private void push(android.util.JsonScope jsonScope) {
        this.stack.add(jsonScope);
    }

    private void replaceTop(android.util.JsonScope jsonScope) {
        this.stack.set(this.stack.size() - 1, jsonScope);
    }

    private android.util.JsonToken nextInArray(boolean z) throws java.io.IOException {
        if (z) {
            replaceTop(android.util.JsonScope.NONEMPTY_ARRAY);
        } else {
            switch (nextNonWhitespace()) {
                case 44:
                    break;
                case 59:
                    checkLenient();
                    break;
                case 93:
                    pop();
                    android.util.JsonToken jsonToken = android.util.JsonToken.END_ARRAY;
                    this.token = jsonToken;
                    return jsonToken;
                default:
                    throw syntaxError("Unterminated array");
            }
        }
        switch (nextNonWhitespace()) {
            case 44:
            case 59:
                break;
            case 93:
                if (z) {
                    pop();
                    android.util.JsonToken jsonToken2 = android.util.JsonToken.END_ARRAY;
                    this.token = jsonToken2;
                    return jsonToken2;
                }
                break;
            default:
                this.pos--;
                return nextValue();
        }
        checkLenient();
        this.pos--;
        this.value = "null";
        android.util.JsonToken jsonToken3 = android.util.JsonToken.NULL;
        this.token = jsonToken3;
        return jsonToken3;
    }

    private android.util.JsonToken nextInObject(boolean z) throws java.io.IOException {
        if (z) {
            switch (nextNonWhitespace()) {
                case 125:
                    pop();
                    android.util.JsonToken jsonToken = android.util.JsonToken.END_OBJECT;
                    this.token = jsonToken;
                    return jsonToken;
                default:
                    this.pos--;
                    break;
            }
        } else {
            switch (nextNonWhitespace()) {
                case 44:
                case 59:
                    break;
                case 125:
                    pop();
                    android.util.JsonToken jsonToken2 = android.util.JsonToken.END_OBJECT;
                    this.token = jsonToken2;
                    return jsonToken2;
                default:
                    throw syntaxError("Unterminated object");
            }
        }
        int nextNonWhitespace = nextNonWhitespace();
        switch (nextNonWhitespace) {
            case 39:
                checkLenient();
            case 34:
                this.name = nextString((char) nextNonWhitespace);
                replaceTop(android.util.JsonScope.DANGLING_NAME);
                android.util.JsonToken jsonToken3 = android.util.JsonToken.NAME;
                this.token = jsonToken3;
                return jsonToken3;
            default:
                checkLenient();
                this.pos--;
                this.name = nextLiteral(false);
                if (this.name.isEmpty()) {
                    throw syntaxError("Expected name");
                }
                replaceTop(android.util.JsonScope.DANGLING_NAME);
                android.util.JsonToken jsonToken32 = android.util.JsonToken.NAME;
                this.token = jsonToken32;
                return jsonToken32;
        }
    }

    private android.util.JsonToken objectValue() throws java.io.IOException {
        switch (nextNonWhitespace()) {
            case 58:
                break;
            case 61:
                checkLenient();
                if ((this.pos < this.limit || fillBuffer(1)) && this.buffer[this.pos] == '>') {
                    this.pos++;
                    break;
                }
                break;
            default:
                throw syntaxError("Expected ':'");
        }
        replaceTop(android.util.JsonScope.NONEMPTY_OBJECT);
        return nextValue();
    }

    private android.util.JsonToken nextValue() throws java.io.IOException {
        int nextNonWhitespace = nextNonWhitespace();
        switch (nextNonWhitespace) {
            case 34:
                break;
            case 39:
                checkLenient();
                break;
            case 91:
                push(android.util.JsonScope.EMPTY_ARRAY);
                android.util.JsonToken jsonToken = android.util.JsonToken.BEGIN_ARRAY;
                this.token = jsonToken;
                return jsonToken;
            case 123:
                push(android.util.JsonScope.EMPTY_OBJECT);
                android.util.JsonToken jsonToken2 = android.util.JsonToken.BEGIN_OBJECT;
                this.token = jsonToken2;
                return jsonToken2;
            default:
                this.pos--;
                return readLiteral();
        }
        this.value = nextString((char) nextNonWhitespace);
        android.util.JsonToken jsonToken3 = android.util.JsonToken.STRING;
        this.token = jsonToken3;
        return jsonToken3;
    }

    private boolean fillBuffer(int i) throws java.io.IOException {
        for (int i2 = 0; i2 < this.pos; i2++) {
            if (this.buffer[i2] == '\n') {
                this.bufferStartLine++;
                this.bufferStartColumn = 1;
            } else {
                this.bufferStartColumn++;
            }
        }
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            java.lang.System.arraycopy(this.buffer, this.pos, this.buffer, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            int read = this.in.read(this.buffer, this.limit, this.buffer.length - this.limit);
            if (read == -1) {
                return false;
            }
            this.limit += read;
            if (this.bufferStartLine == 1 && this.bufferStartColumn == 1 && this.limit > 0 && this.buffer[0] == 65279) {
                this.pos++;
                this.bufferStartColumn--;
            }
        } while (this.limit < i);
        return true;
    }

    private int getLineNumber() {
        int i = this.bufferStartLine;
        for (int i2 = 0; i2 < this.pos; i2++) {
            if (this.buffer[i2] == '\n') {
                i++;
            }
        }
        return i;
    }

    private int getColumnNumber() {
        int i = this.bufferStartColumn;
        for (int i2 = 0; i2 < this.pos; i2++) {
            if (this.buffer[i2] == '\n') {
                i = 1;
            } else {
                i++;
            }
        }
        return i;
    }

    private int nextNonWhitespace() throws java.io.IOException {
        while (true) {
            if (this.pos < this.limit || fillBuffer(1)) {
                char[] cArr = this.buffer;
                int i = this.pos;
                this.pos = i + 1;
                char c = cArr[i];
                switch (c) {
                    case '\t':
                    case '\n':
                    case '\r':
                    case ' ':
                        break;
                    case '#':
                        checkLenient();
                        skipToEndOfLine();
                        break;
                    case '/':
                        if (this.pos == this.limit && !fillBuffer(1)) {
                            return c;
                        }
                        checkLenient();
                        switch (this.buffer[this.pos]) {
                            case '*':
                                this.pos++;
                                if (!skipTo("*/")) {
                                    throw syntaxError("Unterminated comment");
                                }
                                this.pos += 2;
                                break;
                            case '/':
                                this.pos++;
                                skipToEndOfLine();
                                break;
                            default:
                                return c;
                        }
                    default:
                        return c;
                }
            } else {
                throw new java.io.EOFException("End of input");
            }
        }
    }

    private void checkLenient() throws java.io.IOException {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void skipToEndOfLine() throws java.io.IOException {
        char c;
        do {
            if (this.pos < this.limit || fillBuffer(1)) {
                char[] cArr = this.buffer;
                int i = this.pos;
                this.pos = i + 1;
                c = cArr[i];
                if (c == '\r') {
                    return;
                }
            } else {
                return;
            }
        } while (c != '\n');
    }

    private boolean skipTo(java.lang.String str) throws java.io.IOException {
        while (true) {
            if (this.pos + str.length() > this.limit && !fillBuffer(str.length())) {
                return false;
            }
            for (int i = 0; i < str.length(); i++) {
                if (this.buffer[this.pos + i] != str.charAt(i)) {
                    break;
                }
            }
            return true;
            this.pos++;
        }
    }

    private java.lang.String nextString(char c) throws java.io.IOException {
        java.lang.StringBuilder sb = null;
        do {
            int i = this.pos;
            while (this.pos < this.limit) {
                char[] cArr = this.buffer;
                int i2 = this.pos;
                this.pos = i2 + 1;
                char c2 = cArr[i2];
                if (c2 == c) {
                    if (this.skipping) {
                        return "skipped!";
                    }
                    if (sb == null) {
                        return this.stringPool.get(this.buffer, i, (this.pos - i) - 1);
                    }
                    sb.append(this.buffer, i, (this.pos - i) - 1);
                    return sb.toString();
                }
                if (c2 == '\\') {
                    if (sb == null) {
                        sb = new java.lang.StringBuilder();
                    }
                    sb.append(this.buffer, i, (this.pos - i) - 1);
                    sb.append(readEscapeCharacter());
                    i = this.pos;
                }
            }
            if (sb == null) {
                sb = new java.lang.StringBuilder();
            }
            sb.append(this.buffer, i, this.pos - i);
        } while (fillBuffer(1));
        throw syntaxError("Unterminated string");
    }

    private java.lang.String nextLiteral(boolean z) throws java.io.IOException {
        this.valuePos = -1;
        int i = 0;
        this.valueLength = 0;
        java.lang.String str = null;
        int i2 = 0;
        java.lang.StringBuilder sb = null;
        while (true) {
            if (this.pos + i2 < this.limit) {
                switch (this.buffer[this.pos + i2]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        checkLenient();
                        break;
                    default:
                        i2++;
                        continue;
                }
            } else if (i2 < this.buffer.length) {
                if (!fillBuffer(i2 + 1)) {
                    this.buffer[this.limit] = 0;
                }
            } else {
                if (sb == null) {
                    sb = new java.lang.StringBuilder();
                }
                sb.append(this.buffer, this.pos, i2);
                this.valueLength += i2;
                this.pos += i2;
                if (fillBuffer(1)) {
                    i2 = 0;
                }
            }
        }
        i = i2;
        if (z && sb == null) {
            this.valuePos = this.pos;
        } else if (this.skipping) {
            str = "skipped!";
        } else if (sb == null) {
            str = this.stringPool.get(this.buffer, this.pos, i);
        } else {
            sb.append(this.buffer, this.pos, i);
            str = sb.toString();
        }
        this.valueLength += i;
        this.pos += i;
        return str;
    }

    public java.lang.String toString() {
        return getClass().getSimpleName() + " near " + ((java.lang.Object) getSnippet());
    }

    private char readEscapeCharacter() throws java.io.IOException {
        if (this.pos == this.limit && !fillBuffer(1)) {
            throw syntaxError("Unterminated escape sequence");
        }
        char[] cArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        char c = cArr[i];
        switch (c) {
            case 'b':
                return '\b';
            case 'f':
                return '\f';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            case 'u':
                if (this.pos + 4 > this.limit && !fillBuffer(4)) {
                    throw syntaxError("Unterminated escape sequence");
                }
                java.lang.String str = this.stringPool.get(this.buffer, this.pos, 4);
                this.pos += 4;
                return (char) java.lang.Integer.parseInt(str, 16);
            default:
                return c;
        }
    }

    private android.util.JsonToken readLiteral() throws java.io.IOException {
        this.value = nextLiteral(true);
        if (this.valueLength == 0) {
            throw syntaxError("Expected literal value");
        }
        this.token = decodeLiteral();
        if (this.token == android.util.JsonToken.STRING) {
            checkLenient();
        }
        return this.token;
    }

    private android.util.JsonToken decodeLiteral() throws java.io.IOException {
        if (this.valuePos == -1) {
            return android.util.JsonToken.STRING;
        }
        if (this.valueLength == 4 && (('n' == this.buffer[this.valuePos] || 'N' == this.buffer[this.valuePos]) && (('u' == this.buffer[this.valuePos + 1] || 'U' == this.buffer[this.valuePos + 1]) && (('l' == this.buffer[this.valuePos + 2] || 'L' == this.buffer[this.valuePos + 2]) && ('l' == this.buffer[this.valuePos + 3] || 'L' == this.buffer[this.valuePos + 3]))))) {
            this.value = "null";
            return android.util.JsonToken.NULL;
        }
        if (this.valueLength == 4 && (('t' == this.buffer[this.valuePos] || 'T' == this.buffer[this.valuePos]) && (('r' == this.buffer[this.valuePos + 1] || 'R' == this.buffer[this.valuePos + 1]) && (('u' == this.buffer[this.valuePos + 2] || 'U' == this.buffer[this.valuePos + 2]) && ('e' == this.buffer[this.valuePos + 3] || 'E' == this.buffer[this.valuePos + 3]))))) {
            this.value = TRUE;
            return android.util.JsonToken.BOOLEAN;
        }
        if (this.valueLength == 5 && (('f' == this.buffer[this.valuePos] || 'F' == this.buffer[this.valuePos]) && (('a' == this.buffer[this.valuePos + 1] || 'A' == this.buffer[this.valuePos + 1]) && (('l' == this.buffer[this.valuePos + 2] || 'L' == this.buffer[this.valuePos + 2]) && (('s' == this.buffer[this.valuePos + 3] || 'S' == this.buffer[this.valuePos + 3]) && ('e' == this.buffer[this.valuePos + 4] || 'E' == this.buffer[this.valuePos + 4])))))) {
            this.value = FALSE;
            return android.util.JsonToken.BOOLEAN;
        }
        this.value = this.stringPool.get(this.buffer, this.valuePos, this.valueLength);
        return decodeNumber(this.buffer, this.valuePos, this.valueLength);
    }

    private android.util.JsonToken decodeNumber(char[] cArr, int i, int i2) {
        int i3;
        int i4;
        char c;
        char c2 = cArr[i];
        if (c2 != '-') {
            i3 = i;
        } else {
            int i5 = i + 1;
            i3 = i5;
            c2 = cArr[i5];
        }
        if (c2 == '0') {
            i4 = i3 + 1;
            c = cArr[i4];
        } else if (c2 >= '1' && c2 <= '9') {
            i4 = i3 + 1;
            c = cArr[i4];
            while (c >= '0' && c <= '9') {
                i4++;
                c = cArr[i4];
            }
        } else {
            return android.util.JsonToken.STRING;
        }
        if (c == '.') {
            i4++;
            c = cArr[i4];
            while (c >= '0' && c <= '9') {
                i4++;
                c = cArr[i4];
            }
        }
        if (c == 'e' || c == 'E') {
            int i6 = i4 + 1;
            char c3 = cArr[i6];
            if (c3 == '+' || c3 == '-') {
                i6++;
                c3 = cArr[i6];
            }
            if (c3 >= '0' && c3 <= '9') {
                i4 = i6 + 1;
                char c4 = cArr[i4];
                while (c4 >= '0' && c4 <= '9') {
                    i4++;
                    c4 = cArr[i4];
                }
            } else {
                return android.util.JsonToken.STRING;
            }
        }
        if (i4 == i + i2) {
            return android.util.JsonToken.NUMBER;
        }
        return android.util.JsonToken.STRING;
    }

    private java.io.IOException syntaxError(java.lang.String str) throws java.io.IOException {
        throw new android.util.MalformedJsonException(str + " at line " + getLineNumber() + " column " + getColumnNumber());
    }

    private java.lang.CharSequence getSnippet() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int min = java.lang.Math.min(this.pos, 20);
        sb.append(this.buffer, this.pos - min, min);
        sb.append(this.buffer, this.pos, java.lang.Math.min(this.limit - this.pos, 20));
        return sb;
    }
}
