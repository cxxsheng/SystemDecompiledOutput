package com.android.framework.protobuf.nano.android;

/* loaded from: classes4.dex */
public final class ParcelableMessageNanoCreator<T extends com.android.framework.protobuf.nano.MessageNano> implements android.os.Parcelable.Creator<T> {
    private static final java.lang.String TAG = "PMNCreator";
    private final java.lang.Class<T> mClazz;

    public ParcelableMessageNanoCreator(java.lang.Class<T> cls) {
        this.mClazz = cls;
    }

    @Override // android.os.Parcelable.Creator
    public T createFromParcel(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        byte[] createByteArray = parcel.createByteArray();
        T t = null;
        try {
            T t2 = (T) java.lang.Class.forName(readString, false, getClass().getClassLoader()).asSubclass(com.android.framework.protobuf.nano.MessageNano.class).getConstructor(new java.lang.Class[0]).newInstance(new java.lang.Object[0]);
            try {
                com.android.framework.protobuf.nano.MessageNano.mergeFrom(t2, createByteArray);
                return t2;
            } catch (com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException e) {
                e = e;
                t = t2;
                android.util.Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            } catch (java.lang.ClassNotFoundException e2) {
                e = e2;
                t = t2;
                android.util.Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            } catch (java.lang.IllegalAccessException e3) {
                e = e3;
                t = t2;
                android.util.Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            } catch (java.lang.InstantiationException e4) {
                e = e4;
                t = t2;
                android.util.Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            } catch (java.lang.NoSuchMethodException e5) {
                e = e5;
                t = t2;
                android.util.Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            } catch (java.lang.reflect.InvocationTargetException e6) {
                e = e6;
                t = t2;
                android.util.Log.e(TAG, "Exception trying to create proto from parcel", e);
                return t;
            }
        } catch (com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException e7) {
            e = e7;
        } catch (java.lang.ClassNotFoundException e8) {
            e = e8;
        } catch (java.lang.IllegalAccessException e9) {
            e = e9;
        } catch (java.lang.InstantiationException e10) {
            e = e10;
        } catch (java.lang.NoSuchMethodException e11) {
            e = e11;
        } catch (java.lang.reflect.InvocationTargetException e12) {
            e = e12;
        }
    }

    @Override // android.os.Parcelable.Creator
    public T[] newArray(int i) {
        return (T[]) ((com.android.framework.protobuf.nano.MessageNano[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) this.mClazz, i));
    }

    static <T extends com.android.framework.protobuf.nano.MessageNano> void writeToParcel(java.lang.Class<T> cls, com.android.framework.protobuf.nano.MessageNano messageNano, android.os.Parcel parcel) {
        parcel.writeString(cls.getName());
        parcel.writeByteArray(com.android.framework.protobuf.nano.MessageNano.toByteArray(messageNano));
    }
}
