package com.android.internal.util;

@java.lang.Deprecated
/* loaded from: classes5.dex */
public class JournaledFile {
    java.io.File mReal;
    java.io.File mTemp;
    boolean mWriting;

    public JournaledFile(java.io.File file, java.io.File file2) {
        this.mReal = file;
        this.mTemp = file2;
    }

    public java.io.File chooseForRead() {
        if (this.mReal.exists()) {
            java.io.File file = this.mReal;
            if (this.mTemp.exists()) {
                this.mTemp.delete();
                return file;
            }
            return file;
        }
        if (this.mTemp.exists()) {
            java.io.File file2 = this.mTemp;
            this.mTemp.renameTo(this.mReal);
            return file2;
        }
        return this.mReal;
    }

    public java.io.File chooseForWrite() {
        if (this.mWriting) {
            throw new java.lang.IllegalStateException("uncommitted write already in progress");
        }
        if (!this.mReal.exists()) {
            try {
                this.mReal.createNewFile();
            } catch (java.io.IOException e) {
            }
        }
        if (this.mTemp.exists()) {
            this.mTemp.delete();
        }
        this.mWriting = true;
        return this.mTemp;
    }

    public void commit() {
        if (!this.mWriting) {
            throw new java.lang.IllegalStateException("no file to commit");
        }
        this.mWriting = false;
        this.mTemp.renameTo(this.mReal);
    }

    public void rollback() {
        if (!this.mWriting) {
            throw new java.lang.IllegalStateException("no file to roll back");
        }
        this.mWriting = false;
        this.mTemp.delete();
    }
}
