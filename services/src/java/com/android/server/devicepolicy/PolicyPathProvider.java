package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public interface PolicyPathProvider {
    default java.io.File getDataSystemDirectory() {
        return android.os.Environment.getDataSystemDirectory();
    }

    default java.io.File getUserSystemDirectory(int i) {
        return android.os.Environment.getUserSystemDirectory(i);
    }
}
