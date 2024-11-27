package android.util;

/* loaded from: classes3.dex */
public final class JsonWriter implements java.io.Closeable {
    private java.lang.String indent;
    private boolean lenient;
    private final java.io.Writer out;
    private java.lang.String separator;
    private final java.util.List<android.util.JsonScope> stack = new java.util.ArrayList();

    public JsonWriter(java.io.Writer writer) {
        this.stack.add(android.util.JsonScope.EMPTY_DOCUMENT);
        this.separator = ":";
        if (writer == null) {
            throw new java.lang.NullPointerException("out == null");
        }
        this.out = writer;
    }

    public void setIndent(java.lang.String str) {
        if (str.isEmpty()) {
            this.indent = null;
            this.separator = ":";
        } else {
            this.indent = str;
            this.separator = ": ";
        }
    }

    public void setLenient(boolean z) {
        this.lenient = z;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public android.util.JsonWriter beginArray() throws java.io.IOException {
        return open(android.util.JsonScope.EMPTY_ARRAY, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
    }

    public android.util.JsonWriter endArray() throws java.io.IOException {
        return close(android.util.JsonScope.EMPTY_ARRAY, android.util.JsonScope.NONEMPTY_ARRAY, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
    }

    public android.util.JsonWriter beginObject() throws java.io.IOException {
        return open(android.util.JsonScope.EMPTY_OBJECT, "{");
    }

    public android.util.JsonWriter endObject() throws java.io.IOException {
        return close(android.util.JsonScope.EMPTY_OBJECT, android.util.JsonScope.NONEMPTY_OBJECT, "}");
    }

    private android.util.JsonWriter open(android.util.JsonScope jsonScope, java.lang.String str) throws java.io.IOException {
        beforeValue(true);
        this.stack.add(jsonScope);
        this.out.write(str);
        return this;
    }

    private android.util.JsonWriter close(android.util.JsonScope jsonScope, android.util.JsonScope jsonScope2, java.lang.String str) throws java.io.IOException {
        android.util.JsonScope peek = peek();
        if (peek != jsonScope2 && peek != jsonScope) {
            throw new java.lang.IllegalStateException("Nesting problem: " + this.stack);
        }
        this.stack.remove(this.stack.size() - 1);
        if (peek == jsonScope2) {
            newline();
        }
        this.out.write(str);
        return this;
    }

    private android.util.JsonScope peek() {
        return this.stack.get(this.stack.size() - 1);
    }

    private void replaceTop(android.util.JsonScope jsonScope) {
        this.stack.set(this.stack.size() - 1, jsonScope);
    }

    public android.util.JsonWriter name(java.lang.String str) throws java.io.IOException {
        if (str == null) {
            throw new java.lang.NullPointerException("name == null");
        }
        beforeName();
        string(str);
        return this;
    }

    public android.util.JsonWriter value(java.lang.String str) throws java.io.IOException {
        if (str == null) {
            return nullValue();
        }
        beforeValue(false);
        string(str);
        return this;
    }

    public android.util.JsonWriter nullValue() throws java.io.IOException {
        beforeValue(false);
        this.out.write("null");
        return this;
    }

    public android.util.JsonWriter value(boolean z) throws java.io.IOException {
        beforeValue(false);
        this.out.write(z ? "true" : "false");
        return this;
    }

    public android.util.JsonWriter value(double d) throws java.io.IOException {
        if (!this.lenient && (java.lang.Double.isNaN(d) || java.lang.Double.isInfinite(d))) {
            throw new java.lang.IllegalArgumentException("Numeric values must be finite, but was " + d);
        }
        beforeValue(false);
        this.out.append((java.lang.CharSequence) java.lang.Double.toString(d));
        return this;
    }

    public android.util.JsonWriter value(long j) throws java.io.IOException {
        beforeValue(false);
        this.out.write(java.lang.Long.toString(j));
        return this;
    }

    public android.util.JsonWriter value(java.lang.Number number) throws java.io.IOException {
        if (number == null) {
            return nullValue();
        }
        java.lang.String obj = number.toString();
        if (!this.lenient && (obj.equals("-Infinity") || obj.equals("Infinity") || obj.equals("NaN"))) {
            throw new java.lang.IllegalArgumentException("Numeric values must be finite, but was " + number);
        }
        beforeValue(false);
        this.out.append((java.lang.CharSequence) obj);
        return this;
    }

    public void flush() throws java.io.IOException {
        this.out.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.out.close();
        if (peek() != android.util.JsonScope.NONEMPTY_DOCUMENT) {
            throw new java.io.IOException("Incomplete document");
        }
    }

    private void string(java.lang.String str) throws java.io.IOException {
        this.out.write("\"");
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            switch (charAt) {
                case '\b':
                    this.out.write("\\b");
                    break;
                case '\t':
                    this.out.write("\\t");
                    break;
                case '\n':
                    this.out.write("\\n");
                    break;
                case '\f':
                    this.out.write("\\f");
                    break;
                case '\r':
                    this.out.write("\\r");
                    break;
                case '\"':
                case '\\':
                    this.out.write(92);
                    this.out.write(charAt);
                    break;
                case android.bluetooth.hci.BluetoothHciProtoEnums.CMD_BLE_RM_DEV_RESOLVING_LIST /* 8232 */:
                case android.bluetooth.hci.BluetoothHciProtoEnums.CMD_BLE_CLEAR_RESOLVING_LIST /* 8233 */:
                    this.out.write(java.lang.String.format("\\u%04x", java.lang.Integer.valueOf(charAt)));
                    break;
                default:
                    if (charAt <= 31) {
                        this.out.write(java.lang.String.format("\\u%04x", java.lang.Integer.valueOf(charAt)));
                        break;
                    } else {
                        this.out.write(charAt);
                        break;
                    }
            }
        }
        this.out.write("\"");
    }

    private void newline() throws java.io.IOException {
        if (this.indent == null) {
            return;
        }
        this.out.write("\n");
        for (int i = 1; i < this.stack.size(); i++) {
            this.out.write(this.indent);
        }
    }

    private void beforeName() throws java.io.IOException {
        android.util.JsonScope peek = peek();
        if (peek == android.util.JsonScope.NONEMPTY_OBJECT) {
            this.out.write(44);
        } else if (peek != android.util.JsonScope.EMPTY_OBJECT) {
            throw new java.lang.IllegalStateException("Nesting problem: " + this.stack);
        }
        newline();
        replaceTop(android.util.JsonScope.DANGLING_NAME);
    }

    private void beforeValue(boolean z) throws java.io.IOException {
        switch (peek()) {
            case EMPTY_DOCUMENT:
                if (!this.lenient && !z) {
                    throw new java.lang.IllegalStateException("JSON must start with an array or an object.");
                }
                replaceTop(android.util.JsonScope.NONEMPTY_DOCUMENT);
                return;
            case EMPTY_ARRAY:
                replaceTop(android.util.JsonScope.NONEMPTY_ARRAY);
                newline();
                return;
            case NONEMPTY_ARRAY:
                this.out.append(',');
                newline();
                return;
            case DANGLING_NAME:
                this.out.append((java.lang.CharSequence) this.separator);
                replaceTop(android.util.JsonScope.NONEMPTY_OBJECT);
                return;
            case NONEMPTY_DOCUMENT:
                throw new java.lang.IllegalStateException("JSON must have only one top-level value.");
            default:
                throw new java.lang.IllegalStateException("Nesting problem: " + this.stack);
        }
    }
}
