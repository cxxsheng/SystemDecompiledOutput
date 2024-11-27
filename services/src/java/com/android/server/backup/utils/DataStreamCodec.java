package com.android.server.backup.utils;

/* loaded from: classes.dex */
public interface DataStreamCodec<T> {
    T deserialize(java.io.DataInputStream dataInputStream) throws java.io.IOException;

    void serialize(T t, java.io.DataOutputStream dataOutputStream) throws java.io.IOException;
}
