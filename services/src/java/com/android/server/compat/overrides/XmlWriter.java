package com.android.server.compat.overrides;

/* loaded from: classes.dex */
public class XmlWriter implements java.io.Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private java.io.PrintWriter out;
    private java.lang.StringBuilder outBuffer = new java.lang.StringBuilder();
    private int indent = 0;
    private boolean startLine = true;

    public XmlWriter(java.io.PrintWriter printWriter) {
        this.out = printWriter;
    }

    private void printIndent() {
        for (int i = 0; i < this.indent; i++) {
            this.outBuffer.append("    ");
        }
        this.startLine = false;
    }

    void print(java.lang.String str) {
        java.lang.String[] split = str.split("\n", -1);
        int i = 0;
        while (i < split.length) {
            if (this.startLine && !split[i].isEmpty()) {
                printIndent();
            }
            this.outBuffer.append(split[i]);
            i++;
            if (i < split.length) {
                this.outBuffer.append("\n");
                this.startLine = true;
            }
        }
    }

    void increaseIndent() {
        this.indent++;
    }

    void decreaseIndent() {
        this.indent--;
    }

    void printXml() {
        this.out.print(this.outBuffer.toString());
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.out != null) {
            this.out.close();
        }
    }

    public static void write(com.android.server.compat.overrides.XmlWriter xmlWriter, com.android.server.compat.overrides.Overrides overrides) throws java.io.IOException {
        xmlWriter.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        if (overrides != null) {
            overrides.write(xmlWriter, "overrides");
        }
        xmlWriter.printXml();
    }
}
