package android.graphics;

/* loaded from: classes.dex */
public final class GraphicsProtos {
    private GraphicsProtos() {
    }

    public static void dumpPointProto(android.graphics.Point point, android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, point.x);
        protoOutputStream.write(1120986464258L, point.y);
        protoOutputStream.end(start);
    }
}
