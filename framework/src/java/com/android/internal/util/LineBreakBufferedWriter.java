package com.android.internal.util;

/* loaded from: classes5.dex */
public class LineBreakBufferedWriter extends java.io.PrintWriter {
    private char[] buffer;
    private int bufferIndex;
    private final int bufferSize;
    private int lastNewline;
    private final java.lang.String lineSeparator;

    public LineBreakBufferedWriter(java.io.Writer writer, int i) {
        this(writer, i, 16);
    }

    public LineBreakBufferedWriter(java.io.Writer writer, int i, int i2) {
        super(writer);
        this.lastNewline = -1;
        this.buffer = new char[java.lang.Math.min(i2, i)];
        this.bufferIndex = 0;
        this.bufferSize = i;
        this.lineSeparator = java.lang.System.getProperty("line.separator");
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.io.Flushable
    public void flush() {
        writeBuffer(this.bufferIndex);
        this.bufferIndex = 0;
        super.flush();
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(int i) {
        if (this.bufferIndex < this.buffer.length) {
            char c = (char) i;
            this.buffer[this.bufferIndex] = c;
            this.bufferIndex++;
            if (c == '\n') {
                this.lastNewline = this.bufferIndex;
                return;
            }
            return;
        }
        write(new char[]{(char) i}, 0, 1);
    }

    @Override // java.io.PrintWriter
    public void println() {
        write(this.lineSeparator);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        while (this.bufferIndex + i2 > this.bufferSize) {
            int i3 = this.bufferSize - this.bufferIndex;
            int i4 = -1;
            for (int i5 = 0; i5 < i3; i5++) {
                if (cArr[i + i5] == '\n') {
                    if (this.bufferIndex + i5 >= this.bufferSize) {
                        break;
                    } else {
                        i4 = i5;
                    }
                }
            }
            if (i4 != -1) {
                appendToBuffer(cArr, i, i4);
                writeBuffer(this.bufferIndex);
                this.bufferIndex = 0;
                this.lastNewline = -1;
                int i6 = i4 + 1;
                i += i6;
                i2 -= i6;
            } else if (this.lastNewline != -1) {
                writeBuffer(this.lastNewline);
                removeFromBuffer(this.lastNewline + 1);
                this.lastNewline = -1;
            } else {
                int i7 = this.bufferSize - this.bufferIndex;
                appendToBuffer(cArr, i, i7);
                writeBuffer(this.bufferIndex);
                this.bufferIndex = 0;
                i += i7;
                i2 -= i7;
            }
        }
        if (i2 > 0) {
            appendToBuffer(cArr, i, i2);
            for (int i8 = i2 - 1; i8 >= 0; i8--) {
                if (cArr[i + i8] == '\n') {
                    this.lastNewline = (this.bufferIndex - i2) + i8;
                    return;
                }
            }
        }
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(java.lang.String str, int i, int i2) {
        while (this.bufferIndex + i2 > this.bufferSize) {
            int i3 = this.bufferSize - this.bufferIndex;
            int i4 = -1;
            for (int i5 = 0; i5 < i3; i5++) {
                if (str.charAt(i + i5) == '\n') {
                    if (this.bufferIndex + i5 >= this.bufferSize) {
                        break;
                    } else {
                        i4 = i5;
                    }
                }
            }
            if (i4 != -1) {
                appendToBuffer(str, i, i4);
                writeBuffer(this.bufferIndex);
                this.bufferIndex = 0;
                this.lastNewline = -1;
                int i6 = i4 + 1;
                i += i6;
                i2 -= i6;
            } else if (this.lastNewline != -1) {
                writeBuffer(this.lastNewline);
                removeFromBuffer(this.lastNewline + 1);
                this.lastNewline = -1;
            } else {
                int i7 = this.bufferSize - this.bufferIndex;
                appendToBuffer(str, i, i7);
                writeBuffer(this.bufferIndex);
                this.bufferIndex = 0;
                i += i7;
                i2 -= i7;
            }
        }
        if (i2 > 0) {
            appendToBuffer(str, i, i2);
            for (int i8 = i2 - 1; i8 >= 0; i8--) {
                if (str.charAt(i + i8) == '\n') {
                    this.lastNewline = (this.bufferIndex - i2) + i8;
                    return;
                }
            }
        }
    }

    private void appendToBuffer(char[] cArr, int i, int i2) {
        if (this.bufferIndex + i2 > this.buffer.length) {
            ensureCapacity(this.bufferIndex + i2);
        }
        java.lang.System.arraycopy(cArr, i, this.buffer, this.bufferIndex, i2);
        this.bufferIndex += i2;
    }

    private void appendToBuffer(java.lang.String str, int i, int i2) {
        if (this.bufferIndex + i2 > this.buffer.length) {
            ensureCapacity(this.bufferIndex + i2);
        }
        str.getChars(i, i + i2, this.buffer, this.bufferIndex);
        this.bufferIndex += i2;
    }

    private void ensureCapacity(int i) {
        int length = (this.buffer.length * 2) + 2;
        if (length >= i) {
            i = length;
        }
        this.buffer = java.util.Arrays.copyOf(this.buffer, i);
    }

    private void removeFromBuffer(int i) {
        int i2 = this.bufferIndex - i;
        if (i2 > 0) {
            java.lang.System.arraycopy(this.buffer, this.bufferIndex - i2, this.buffer, 0, i2);
            this.bufferIndex = i2;
        } else {
            this.bufferIndex = 0;
        }
    }

    private void writeBuffer(int i) {
        if (i > 0) {
            super.write(this.buffer, 0, i);
        }
    }
}
