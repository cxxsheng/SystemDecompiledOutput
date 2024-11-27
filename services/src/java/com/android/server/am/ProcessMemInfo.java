package com.android.server.am;

/* loaded from: classes.dex */
public class ProcessMemInfo {
    final java.lang.String adjReason;
    final java.lang.String adjType;
    long memtrack;
    final java.lang.String name;
    final int oomAdj;
    final int pid;
    final int procState;
    long pss;
    long swapPss;

    public ProcessMemInfo(java.lang.String str, int i, int i2, int i3, java.lang.String str2, java.lang.String str3) {
        this.name = str;
        this.pid = i;
        this.oomAdj = i2;
        this.procState = i3;
        this.adjType = str2;
        this.adjReason = str3;
    }
}
