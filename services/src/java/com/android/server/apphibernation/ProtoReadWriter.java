package com.android.server.apphibernation;

/* loaded from: classes.dex */
interface ProtoReadWriter<T> {
    @android.annotation.Nullable
    T readFromProto(@android.annotation.NonNull android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException;

    void writeToProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, @android.annotation.NonNull T t);
}
