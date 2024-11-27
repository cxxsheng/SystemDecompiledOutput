package com.android.internal.util.dump;

/* loaded from: classes5.dex */
public class DualDumpOutputStream {
    private static final java.lang.String LOG_TAG = com.android.internal.util.dump.DualDumpOutputStream.class.getSimpleName();
    private final java.util.LinkedList<com.android.internal.util.dump.DualDumpOutputStream.DumpObject> mDumpObjects;
    private final android.util.IndentingPrintWriter mIpw;
    private final android.util.proto.ProtoOutputStream mProtoStream;

    private static abstract class Dumpable {
        final java.lang.String name;

        abstract void print(android.util.IndentingPrintWriter indentingPrintWriter, boolean z);

        private Dumpable(java.lang.String str) {
            this.name = str;
        }
    }

    private static class DumpObject extends com.android.internal.util.dump.DualDumpOutputStream.Dumpable {
        private final java.util.LinkedHashMap<java.lang.String, java.util.ArrayList<com.android.internal.util.dump.DualDumpOutputStream.Dumpable>> mSubObjects;

        private DumpObject(java.lang.String str) {
            super(str);
            this.mSubObjects = new java.util.LinkedHashMap<>();
        }

        @Override // com.android.internal.util.dump.DualDumpOutputStream.Dumpable
        void print(android.util.IndentingPrintWriter indentingPrintWriter, boolean z) {
            if (z) {
                indentingPrintWriter.println(this.name + "={");
            } else {
                indentingPrintWriter.println("{");
            }
            indentingPrintWriter.increaseIndent();
            for (java.util.ArrayList<com.android.internal.util.dump.DualDumpOutputStream.Dumpable> arrayList : this.mSubObjects.values()) {
                int size = arrayList.size();
                if (size == 1) {
                    arrayList.get(0).print(indentingPrintWriter, true);
                } else {
                    indentingPrintWriter.println(arrayList.get(0).name + "=[");
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < size; i++) {
                        arrayList.get(i).print(indentingPrintWriter, false);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
                }
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("}");
        }

        public void add(java.lang.String str, com.android.internal.util.dump.DualDumpOutputStream.Dumpable dumpable) {
            java.util.ArrayList<com.android.internal.util.dump.DualDumpOutputStream.Dumpable> arrayList = this.mSubObjects.get(str);
            if (arrayList == null) {
                arrayList = new java.util.ArrayList<>(1);
                this.mSubObjects.put(str, arrayList);
            }
            arrayList.add(dumpable);
        }
    }

    private static class DumpField extends com.android.internal.util.dump.DualDumpOutputStream.Dumpable {
        private final java.lang.String mValue;

        private DumpField(java.lang.String str, java.lang.String str2) {
            super(str);
            this.mValue = str2;
        }

        @Override // com.android.internal.util.dump.DualDumpOutputStream.Dumpable
        void print(android.util.IndentingPrintWriter indentingPrintWriter, boolean z) {
            if (z) {
                indentingPrintWriter.println(this.name + "=" + this.mValue);
            } else {
                indentingPrintWriter.println(this.mValue);
            }
        }
    }

    public DualDumpOutputStream(android.util.proto.ProtoOutputStream protoOutputStream) {
        this.mDumpObjects = new java.util.LinkedList<>();
        this.mProtoStream = protoOutputStream;
        this.mIpw = null;
    }

    public DualDumpOutputStream(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mDumpObjects = new java.util.LinkedList<>();
        this.mProtoStream = null;
        this.mIpw = indentingPrintWriter;
        this.mDumpObjects.add(new com.android.internal.util.dump.DualDumpOutputStream.DumpObject(null));
    }

    public void write(java.lang.String str, long j, double d) {
        if (this.mProtoStream != null) {
            this.mProtoStream.write(j, d);
        } else {
            this.mDumpObjects.getLast().add(str, new com.android.internal.util.dump.DualDumpOutputStream.DumpField(str, java.lang.String.valueOf(d)));
        }
    }

    public void write(java.lang.String str, long j, boolean z) {
        if (this.mProtoStream != null) {
            this.mProtoStream.write(j, z);
        } else {
            this.mDumpObjects.getLast().add(str, new com.android.internal.util.dump.DualDumpOutputStream.DumpField(str, java.lang.String.valueOf(z)));
        }
    }

    public void write(java.lang.String str, long j, int i) {
        if (this.mProtoStream != null) {
            this.mProtoStream.write(j, i);
        } else {
            this.mDumpObjects.getLast().add(str, new com.android.internal.util.dump.DualDumpOutputStream.DumpField(str, java.lang.String.valueOf(i)));
        }
    }

    public void write(java.lang.String str, long j, float f) {
        if (this.mProtoStream != null) {
            this.mProtoStream.write(j, f);
        } else {
            this.mDumpObjects.getLast().add(str, new com.android.internal.util.dump.DualDumpOutputStream.DumpField(str, java.lang.String.valueOf(f)));
        }
    }

    public void write(java.lang.String str, long j, byte[] bArr) {
        if (this.mProtoStream != null) {
            this.mProtoStream.write(j, bArr);
        } else {
            this.mDumpObjects.getLast().add(str, new com.android.internal.util.dump.DualDumpOutputStream.DumpField(str, java.util.Arrays.toString(bArr)));
        }
    }

    public void write(java.lang.String str, long j, long j2) {
        if (this.mProtoStream != null) {
            this.mProtoStream.write(j, j2);
        } else {
            this.mDumpObjects.getLast().add(str, new com.android.internal.util.dump.DualDumpOutputStream.DumpField(str, java.lang.String.valueOf(j2)));
        }
    }

    public void write(java.lang.String str, long j, java.lang.String str2) {
        if (this.mProtoStream != null) {
            this.mProtoStream.write(j, str2);
        } else {
            this.mDumpObjects.getLast().add(str, new com.android.internal.util.dump.DualDumpOutputStream.DumpField(str, java.lang.String.valueOf(str2)));
        }
    }

    public long start(java.lang.String str, long j) {
        if (this.mProtoStream != null) {
            return this.mProtoStream.start(j);
        }
        com.android.internal.util.dump.DualDumpOutputStream.DumpObject dumpObject = new com.android.internal.util.dump.DualDumpOutputStream.DumpObject(str);
        this.mDumpObjects.getLast().add(str, dumpObject);
        this.mDumpObjects.addLast(dumpObject);
        return java.lang.System.identityHashCode(dumpObject);
    }

    public void end(long j) {
        if (this.mProtoStream != null) {
            this.mProtoStream.end(j);
            return;
        }
        if (java.lang.System.identityHashCode(this.mDumpObjects.getLast()) != j) {
            android.util.Log.w(LOG_TAG, "Unexpected token for ending " + this.mDumpObjects.getLast().name + " at " + java.util.Arrays.toString(java.lang.Thread.currentThread().getStackTrace()));
        }
        this.mDumpObjects.removeLast();
    }

    public void flush() {
        if (this.mProtoStream != null) {
            this.mProtoStream.flush();
            return;
        }
        if (this.mDumpObjects.size() == 1) {
            this.mDumpObjects.getFirst().print(this.mIpw, false);
            this.mDumpObjects.clear();
            this.mDumpObjects.add(new com.android.internal.util.dump.DualDumpOutputStream.DumpObject(null));
        }
        this.mIpw.flush();
    }

    public void writeNested(java.lang.String str, byte[] bArr) {
        if (this.mIpw == null) {
            android.util.Log.w(LOG_TAG, "writeNested does not work for proto logging");
        } else {
            this.mDumpObjects.getLast().add(str, new com.android.internal.util.dump.DualDumpOutputStream.DumpField(str, new java.lang.String(bArr, java.nio.charset.StandardCharsets.UTF_8).trim()));
        }
    }

    public boolean isProto() {
        return this.mProtoStream != null;
    }
}
