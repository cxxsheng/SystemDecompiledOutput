package com.android.server.apphibernation;

/* loaded from: classes.dex */
final class GlobalLevelHibernationProto implements com.android.server.apphibernation.ProtoReadWriter<java.util.List<com.android.server.apphibernation.GlobalLevelState>> {
    private static final java.lang.String TAG = "GlobalLevelHibernationProtoReadWriter";

    GlobalLevelHibernationProto() {
    }

    @Override // com.android.server.apphibernation.ProtoReadWriter
    public void writeToProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, @android.annotation.NonNull java.util.List<com.android.server.apphibernation.GlobalLevelState> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            long start = protoOutputStream.start(2246267895809L);
            com.android.server.apphibernation.GlobalLevelState globalLevelState = list.get(i);
            protoOutputStream.write(1138166333441L, globalLevelState.packageName);
            protoOutputStream.write(1133871366146L, globalLevelState.hibernated);
            protoOutputStream.write(1112396529667L, globalLevelState.savedByte);
            protoOutputStream.end(start);
        }
    }

    @Override // com.android.server.apphibernation.ProtoReadWriter
    @android.annotation.Nullable
    public java.util.List<com.android.server.apphibernation.GlobalLevelState> readFromProto(@android.annotation.NonNull android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                com.android.server.apphibernation.GlobalLevelState globalLevelState = new com.android.server.apphibernation.GlobalLevelState();
                long start = protoInputStream.start(2246267895809L);
                while (protoInputStream.nextField() != -1) {
                    switch (protoInputStream.getFieldNumber()) {
                        case 1:
                            globalLevelState.packageName = protoInputStream.readString(1138166333441L);
                            break;
                        case 2:
                            globalLevelState.hibernated = protoInputStream.readBoolean(1133871366146L);
                            break;
                        case 3:
                            globalLevelState.savedByte = protoInputStream.readLong(1112396529667L);
                            break;
                        default:
                            android.util.Slog.w(TAG, "Undefined field in proto: " + protoInputStream.getFieldNumber());
                            break;
                    }
                }
                protoInputStream.end(start);
                arrayList.add(globalLevelState);
            }
        }
        return arrayList;
    }
}
