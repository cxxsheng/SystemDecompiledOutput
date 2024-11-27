package android.util.proto;

/* loaded from: classes3.dex */
public class ProtoUtils {
    public static void toAggStatsProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, long j3, long j4, int i, int i2) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, j2);
        protoOutputStream.write(1112396529666L, j3);
        protoOutputStream.write(1112396529667L, j4);
        protoOutputStream.write(1120986464260L, i);
        protoOutputStream.write(1120986464261L, i2);
        protoOutputStream.end(start);
    }

    public static void toAggStatsProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, long j3, long j4) {
        toAggStatsProto(protoOutputStream, j, j2, j3, j4, 0, 0);
    }

    public static void toDuration(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, long j3) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, j2);
        protoOutputStream.write(1112396529666L, j3);
        protoOutputStream.end(start);
    }

    public static void writeBitWiseFlagsToProtoEnum(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, int[] iArr, int[] iArr2) {
        if (iArr2.length != iArr.length) {
            throw new java.lang.IllegalArgumentException("The length of origEnums must match protoEnums");
        }
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (iArr[i] != 0 || j2 != 0) {
                if ((iArr[i] & j2) != 0) {
                    protoOutputStream.write(j, iArr2[i]);
                }
            } else {
                protoOutputStream.write(j, iArr2[i]);
                return;
            }
        }
    }

    public static java.lang.String currentFieldToString(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int fieldNumber = protoInputStream.getFieldNumber();
        int wireType = protoInputStream.getWireType();
        sb.append("Offset : 0x").append(java.lang.Integer.toHexString(protoInputStream.getOffset()));
        sb.append("\nField Number : 0x").append(java.lang.Integer.toHexString(protoInputStream.getFieldNumber()));
        sb.append("\nWire Type : ");
        switch (wireType) {
            case 0:
                long makeFieldId = android.util.proto.ProtoStream.makeFieldId(fieldNumber, 1112396529664L);
                sb.append("varint\nField Value : 0x");
                sb.append(java.lang.Long.toHexString(protoInputStream.readLong(makeFieldId)));
                break;
            case 1:
                long makeFieldId2 = android.util.proto.ProtoStream.makeFieldId(fieldNumber, 1125281431552L);
                sb.append("fixed64\nField Value : 0x");
                sb.append(java.lang.Long.toHexString(protoInputStream.readLong(makeFieldId2)));
                break;
            case 2:
                long makeFieldId3 = android.util.proto.ProtoStream.makeFieldId(fieldNumber, 1151051235328L);
                sb.append("length delimited\nField Bytes : ");
                sb.append(java.util.Arrays.toString(protoInputStream.readBytes(makeFieldId3)));
                break;
            case 3:
                sb.append("start group");
                break;
            case 4:
                sb.append("end group");
                break;
            case 5:
                long makeFieldId4 = android.util.proto.ProtoStream.makeFieldId(fieldNumber, 1129576398848L);
                sb.append("fixed32\nField Value : 0x");
                sb.append(java.lang.Integer.toHexString(protoInputStream.readInt(makeFieldId4)));
                break;
            default:
                sb.append("unknown(").append(protoInputStream.getWireType()).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                break;
        }
        return sb.toString();
    }
}
