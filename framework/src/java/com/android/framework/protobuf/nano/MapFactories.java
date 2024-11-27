package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
public final class MapFactories {
    private static volatile com.android.framework.protobuf.nano.MapFactories.MapFactory mapFactory = new com.android.framework.protobuf.nano.MapFactories.DefaultMapFactory();

    public interface MapFactory {
        <K, V> java.util.Map<K, V> forMap(java.util.Map<K, V> map);
    }

    static void setMapFactory(com.android.framework.protobuf.nano.MapFactories.MapFactory mapFactory2) {
        mapFactory = mapFactory2;
    }

    public static com.android.framework.protobuf.nano.MapFactories.MapFactory getMapFactory() {
        return mapFactory;
    }

    private static class DefaultMapFactory implements com.android.framework.protobuf.nano.MapFactories.MapFactory {
        private DefaultMapFactory() {
        }

        @Override // com.android.framework.protobuf.nano.MapFactories.MapFactory
        public <K, V> java.util.Map<K, V> forMap(java.util.Map<K, V> map) {
            if (map == null) {
                return new java.util.HashMap();
            }
            return map;
        }
    }

    private MapFactories() {
    }
}
