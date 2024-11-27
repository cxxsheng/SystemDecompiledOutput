package com.android.internal.util.dump;

/* loaded from: classes5.dex */
public final class DumpableContainerImpl implements android.util.DumpableContainer {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = com.android.internal.util.dump.DumpableContainerImpl.class.getSimpleName();
    private final android.util.ArrayMap<java.lang.String, android.util.Dumpable> mDumpables = new android.util.ArrayMap<>();

    @Override // android.util.DumpableContainer
    public boolean addDumpable(final android.util.Dumpable dumpable) {
        java.util.Objects.requireNonNull(dumpable, "dumpable");
        java.lang.String dumpableName = dumpable.getDumpableName();
        java.util.Objects.requireNonNull(dumpableName, (java.util.function.Supplier<java.lang.String>) new java.util.function.Supplier() { // from class: com.android.internal.util.dump.DumpableContainerImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return com.android.internal.util.dump.DumpableContainerImpl.lambda$addDumpable$0(android.util.Dumpable.this);
            }
        });
        if (this.mDumpables.containsKey(dumpableName)) {
            return false;
        }
        this.mDumpables.put(dumpableName, dumpable);
        return true;
    }

    static /* synthetic */ java.lang.String lambda$addDumpable$0(android.util.Dumpable dumpable) {
        return "name of" + dumpable;
    }

    @Override // android.util.DumpableContainer
    public boolean removeDumpable(android.util.Dumpable dumpable) {
        android.util.Dumpable dumpable2;
        java.util.Objects.requireNonNull(dumpable, "dumpable");
        java.lang.String dumpableName = dumpable.getDumpableName();
        if (dumpableName == null || (dumpable2 = this.mDumpables.get(dumpableName)) == null) {
            return false;
        }
        if (dumpable2 != dumpable) {
            android.util.Log.w(TAG, "removeDumpable(): passed dumpable (" + dumpable + ") named " + dumpableName + ", but internal dumpable with that name is " + dumpable2);
            return false;
        }
        this.mDumpables.remove(dumpableName);
        return true;
    }

    private int dumpNumberDumpables(android.util.IndentingPrintWriter indentingPrintWriter) {
        int size = this.mDumpables.size();
        if (size == 0) {
            indentingPrintWriter.print("No dumpables");
        } else {
            indentingPrintWriter.print(size);
            indentingPrintWriter.print(" dumpables");
        }
        return size;
    }

    public void listDumpables(java.lang.String str, java.io.PrintWriter printWriter) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, str, str);
        int dumpNumberDumpables = dumpNumberDumpables(indentingPrintWriter);
        if (dumpNumberDumpables == 0) {
            indentingPrintWriter.println();
            return;
        }
        indentingPrintWriter.print(": ");
        for (int i = 0; i < dumpNumberDumpables; i++) {
            indentingPrintWriter.print(this.mDumpables.keyAt(i));
            if (i < dumpNumberDumpables - 1) {
                indentingPrintWriter.print(' ');
            }
        }
        indentingPrintWriter.println();
    }

    public void dumpAllDumpables(java.lang.String str, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, str, str);
        int dumpNumberDumpables = dumpNumberDumpables(indentingPrintWriter);
        if (dumpNumberDumpables == 0) {
            indentingPrintWriter.println();
            return;
        }
        indentingPrintWriter.println(":");
        for (int i = 0; i < dumpNumberDumpables; i++) {
            java.lang.String keyAt = this.mDumpables.keyAt(i);
            indentingPrintWriter.print('#');
            indentingPrintWriter.print(i);
            indentingPrintWriter.print(": ");
            indentingPrintWriter.println(keyAt);
            indentAndDump(indentingPrintWriter, this.mDumpables.valueAt(i), strArr);
        }
    }

    private void indentAndDump(android.util.IndentingPrintWriter indentingPrintWriter, android.util.Dumpable dumpable, java.lang.String[] strArr) {
        indentingPrintWriter.increaseIndent();
        try {
            dumpable.dump(indentingPrintWriter, strArr);
        } finally {
            indentingPrintWriter.decreaseIndent();
        }
    }

    public void dumpOneDumpable(java.lang.String str, java.io.PrintWriter printWriter, java.lang.String str2, java.lang.String[] strArr) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, str, str);
        android.util.Dumpable dumpable = this.mDumpables.get(str2);
        if (dumpable == null) {
            indentingPrintWriter.print("No ");
            indentingPrintWriter.println(str2);
        } else {
            indentingPrintWriter.print(str2);
            indentingPrintWriter.println(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
            indentAndDump(indentingPrintWriter, dumpable, strArr);
        }
    }
}
