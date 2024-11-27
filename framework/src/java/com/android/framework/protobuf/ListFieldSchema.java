package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
abstract class ListFieldSchema {
    private static final com.android.framework.protobuf.ListFieldSchema FULL_INSTANCE;
    private static final com.android.framework.protobuf.ListFieldSchema LITE_INSTANCE;

    abstract void makeImmutableListAt(java.lang.Object obj, long j);

    abstract <L> void mergeListsAt(java.lang.Object obj, java.lang.Object obj2, long j);

    abstract <L> java.util.List<L> mutableListAt(java.lang.Object obj, long j);

    private ListFieldSchema() {
    }

    static {
        FULL_INSTANCE = new com.android.framework.protobuf.ListFieldSchema.ListFieldSchemaFull();
        LITE_INSTANCE = new com.android.framework.protobuf.ListFieldSchema.ListFieldSchemaLite();
    }

    static com.android.framework.protobuf.ListFieldSchema full() {
        return FULL_INSTANCE;
    }

    static com.android.framework.protobuf.ListFieldSchema lite() {
        return LITE_INSTANCE;
    }

    private static final class ListFieldSchemaFull extends com.android.framework.protobuf.ListFieldSchema {
        private static final java.lang.Class<?> UNMODIFIABLE_LIST_CLASS = java.util.Collections.unmodifiableList(java.util.Collections.emptyList()).getClass();

        private ListFieldSchemaFull() {
            super();
        }

        @Override // com.android.framework.protobuf.ListFieldSchema
        <L> java.util.List<L> mutableListAt(java.lang.Object obj, long j) {
            return mutableListAt(obj, j, 10);
        }

        @Override // com.android.framework.protobuf.ListFieldSchema
        void makeImmutableListAt(java.lang.Object obj, long j) {
            java.lang.Object unmodifiableList;
            java.util.List list = (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(obj, j);
            if (list instanceof com.android.framework.protobuf.LazyStringList) {
                unmodifiableList = ((com.android.framework.protobuf.LazyStringList) list).getUnmodifiableView();
            } else {
                if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                    return;
                }
                if ((list instanceof com.android.framework.protobuf.PrimitiveNonBoxingCollection) && (list instanceof com.android.framework.protobuf.Internal.ProtobufList)) {
                    com.android.framework.protobuf.Internal.ProtobufList protobufList = (com.android.framework.protobuf.Internal.ProtobufList) list;
                    if (protobufList.isModifiable()) {
                        protobufList.makeImmutable();
                        return;
                    }
                    return;
                }
                unmodifiableList = java.util.Collections.unmodifiableList(list);
            }
            com.android.framework.protobuf.UnsafeUtil.putObject(obj, j, unmodifiableList);
        }

        private static <L> java.util.List<L> mutableListAt(java.lang.Object obj, long j, int i) {
            java.util.List<L> arrayList;
            java.util.List<L> list = getList(obj, j);
            if (list.isEmpty()) {
                if (list instanceof com.android.framework.protobuf.LazyStringList) {
                    arrayList = new com.android.framework.protobuf.LazyStringArrayList(i);
                } else if ((list instanceof com.android.framework.protobuf.PrimitiveNonBoxingCollection) && (list instanceof com.android.framework.protobuf.Internal.ProtobufList)) {
                    arrayList = ((com.android.framework.protobuf.Internal.ProtobufList) list).mutableCopyWithCapacity2(i);
                } else {
                    arrayList = new java.util.ArrayList<>(i);
                }
                com.android.framework.protobuf.UnsafeUtil.putObject(obj, j, arrayList);
                return arrayList;
            }
            if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                java.util.ArrayList arrayList2 = new java.util.ArrayList(list.size() + i);
                arrayList2.addAll(list);
                com.android.framework.protobuf.UnsafeUtil.putObject(obj, j, arrayList2);
                return arrayList2;
            }
            if (list instanceof com.android.framework.protobuf.UnmodifiableLazyStringList) {
                com.android.framework.protobuf.LazyStringArrayList lazyStringArrayList = new com.android.framework.protobuf.LazyStringArrayList(list.size() + i);
                lazyStringArrayList.addAll((com.android.framework.protobuf.UnmodifiableLazyStringList) list);
                com.android.framework.protobuf.UnsafeUtil.putObject(obj, j, lazyStringArrayList);
                return lazyStringArrayList;
            }
            if ((list instanceof com.android.framework.protobuf.PrimitiveNonBoxingCollection) && (list instanceof com.android.framework.protobuf.Internal.ProtobufList)) {
                com.android.framework.protobuf.Internal.ProtobufList protobufList = (com.android.framework.protobuf.Internal.ProtobufList) list;
                if (!protobufList.isModifiable()) {
                    com.android.framework.protobuf.Internal.ProtobufList mutableCopyWithCapacity2 = protobufList.mutableCopyWithCapacity2(list.size() + i);
                    com.android.framework.protobuf.UnsafeUtil.putObject(obj, j, mutableCopyWithCapacity2);
                    return mutableCopyWithCapacity2;
                }
                return list;
            }
            return list;
        }

        @Override // com.android.framework.protobuf.ListFieldSchema
        <E> void mergeListsAt(java.lang.Object obj, java.lang.Object obj2, long j) {
            java.util.List list = getList(obj2, j);
            java.util.List mutableListAt = mutableListAt(obj, j, list.size());
            int size = mutableListAt.size();
            int size2 = list.size();
            if (size > 0 && size2 > 0) {
                mutableListAt.addAll(list);
            }
            if (size > 0) {
                list = mutableListAt;
            }
            com.android.framework.protobuf.UnsafeUtil.putObject(obj, j, list);
        }

        static <E> java.util.List<E> getList(java.lang.Object obj, long j) {
            return (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(obj, j);
        }
    }

    private static final class ListFieldSchemaLite extends com.android.framework.protobuf.ListFieldSchema {
        private ListFieldSchemaLite() {
            super();
        }

        @Override // com.android.framework.protobuf.ListFieldSchema
        <L> java.util.List<L> mutableListAt(java.lang.Object obj, long j) {
            com.android.framework.protobuf.Internal.ProtobufList protobufList = getProtobufList(obj, j);
            if (!protobufList.isModifiable()) {
                int size = protobufList.size();
                com.android.framework.protobuf.Internal.ProtobufList mutableCopyWithCapacity2 = protobufList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
                com.android.framework.protobuf.UnsafeUtil.putObject(obj, j, mutableCopyWithCapacity2);
                return mutableCopyWithCapacity2;
            }
            return protobufList;
        }

        @Override // com.android.framework.protobuf.ListFieldSchema
        void makeImmutableListAt(java.lang.Object obj, long j) {
            getProtobufList(obj, j).makeImmutable();
        }

        @Override // com.android.framework.protobuf.ListFieldSchema
        <E> void mergeListsAt(java.lang.Object obj, java.lang.Object obj2, long j) {
            com.android.framework.protobuf.Internal.ProtobufList protobufList = getProtobufList(obj, j);
            com.android.framework.protobuf.Internal.ProtobufList protobufList2 = getProtobufList(obj2, j);
            int size = protobufList.size();
            int size2 = protobufList2.size();
            if (size > 0 && size2 > 0) {
                if (!protobufList.isModifiable()) {
                    protobufList = protobufList.mutableCopyWithCapacity2(size2 + size);
                }
                protobufList.addAll(protobufList2);
            }
            if (size > 0) {
                protobufList2 = protobufList;
            }
            com.android.framework.protobuf.UnsafeUtil.putObject(obj, j, protobufList2);
        }

        static <E> com.android.framework.protobuf.Internal.ProtobufList<E> getProtobufList(java.lang.Object obj, long j) {
            return (com.android.framework.protobuf.Internal.ProtobufList) com.android.framework.protobuf.UnsafeUtil.getObject(obj, j);
        }
    }
}
