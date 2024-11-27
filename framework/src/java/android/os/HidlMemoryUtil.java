package android.os;

/* loaded from: classes3.dex */
public final class HidlMemoryUtil {
    private static final java.lang.String TAG = "HidlMemoryUtil";

    private HidlMemoryUtil() {
    }

    public static android.os.HidlMemory byteArrayToHidlMemory(byte[] bArr) {
        return byteArrayToHidlMemory(bArr, null);
    }

    public static android.os.HidlMemory byteArrayToHidlMemory(byte[] bArr, java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(bArr);
        if (bArr.length == 0) {
            return new android.os.HidlMemory("ashmem", 0L, null);
        }
        if (str == null) {
            str = "";
        }
        try {
            android.os.SharedMemory create = android.os.SharedMemory.create(str, bArr.length);
            try {
                java.nio.ByteBuffer mapReadWrite = create.mapReadWrite();
                mapReadWrite.put(bArr);
                android.os.SharedMemory.unmap(mapReadWrite);
                android.os.HidlMemory sharedMemoryToHidlMemory = sharedMemoryToHidlMemory(create);
                if (create != null) {
                    create.close();
                }
                return sharedMemoryToHidlMemory;
            } finally {
            }
        } catch (android.system.ErrnoException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public static android.os.HidlMemory byteListToHidlMemory(java.util.List<java.lang.Byte> list) {
        return byteListToHidlMemory(list, null);
    }

    public static android.os.HidlMemory byteListToHidlMemory(java.util.List<java.lang.Byte> list, java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(list);
        if (list.isEmpty()) {
            return new android.os.HidlMemory("ashmem", 0L, null);
        }
        if (str == null) {
            str = "";
        }
        try {
            android.os.SharedMemory create = android.os.SharedMemory.create(str, list.size());
            try {
                java.nio.ByteBuffer mapReadWrite = create.mapReadWrite();
                java.util.Iterator<java.lang.Byte> it = list.iterator();
                while (it.hasNext()) {
                    mapReadWrite.put(it.next().byteValue());
                }
                android.os.SharedMemory.unmap(mapReadWrite);
                android.os.HidlMemory sharedMemoryToHidlMemory = sharedMemoryToHidlMemory(create);
                if (create != null) {
                    create.close();
                }
                return sharedMemoryToHidlMemory;
            } finally {
            }
        } catch (android.system.ErrnoException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public static byte[] hidlMemoryToByteArray(android.os.HidlMemory hidlMemory) {
        com.android.internal.util.Preconditions.checkNotNull(hidlMemory);
        com.android.internal.util.Preconditions.checkArgumentInRange(hidlMemory.getSize(), 0L, 2147483647L, "Memory size");
        com.android.internal.util.Preconditions.checkArgument(hidlMemory.getSize() == 0 || hidlMemory.getName().equals("ashmem"), "Unsupported memory type: %s", hidlMemory.getName());
        if (hidlMemory.getSize() == 0) {
            return new byte[0];
        }
        java.nio.ByteBuffer buffer = getBuffer(hidlMemory);
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        return bArr;
    }

    public static java.util.ArrayList<java.lang.Byte> hidlMemoryToByteList(android.os.HidlMemory hidlMemory) {
        com.android.internal.util.Preconditions.checkNotNull(hidlMemory);
        com.android.internal.util.Preconditions.checkArgumentInRange(hidlMemory.getSize(), 0L, 2147483647L, "Memory size");
        com.android.internal.util.Preconditions.checkArgument(hidlMemory.getSize() == 0 || hidlMemory.getName().equals("ashmem"), "Unsupported memory type: %s", hidlMemory.getName());
        if (hidlMemory.getSize() == 0) {
            return new java.util.ArrayList<>();
        }
        java.nio.ByteBuffer buffer = getBuffer(hidlMemory);
        java.util.ArrayList<java.lang.Byte> arrayList = new java.util.ArrayList<>(buffer.remaining());
        while (buffer.hasRemaining()) {
            arrayList.add(java.lang.Byte.valueOf(buffer.get()));
        }
        return arrayList;
    }

    public static android.os.HidlMemory sharedMemoryToHidlMemory(android.os.SharedMemory sharedMemory) {
        if (sharedMemory == null) {
            return new android.os.HidlMemory("ashmem", 0L, null);
        }
        return fileDescriptorToHidlMemory(sharedMemory.getFileDescriptor(), sharedMemory.getSize());
    }

    public static android.os.HidlMemory fileDescriptorToHidlMemory(java.io.FileDescriptor fileDescriptor, int i) {
        com.android.internal.util.Preconditions.checkArgument(fileDescriptor != null || i == 0);
        if (fileDescriptor == null) {
            return new android.os.HidlMemory("ashmem", 0L, null);
        }
        try {
            return new android.os.HidlMemory("ashmem", i, new android.os.NativeHandle(android.system.Os.dup(fileDescriptor), true));
        } catch (android.system.ErrnoException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private static java.nio.ByteBuffer getBuffer(android.os.HidlMemory hidlMemory) {
        try {
            final int size = (int) hidlMemory.getSize();
            if (size == 0) {
                return java.nio.ByteBuffer.wrap(new byte[0]);
            }
            android.os.NativeHandle handle = hidlMemory.getHandle();
            final long mmap = android.system.Os.mmap(0L, size, android.system.OsConstants.PROT_READ, android.system.OsConstants.MAP_SHARED, handle.getFileDescriptor(), 0L);
            return new java.nio.DirectByteBuffer(size, mmap, handle.getFileDescriptor(), new java.lang.Runnable() { // from class: android.os.HidlMemoryUtil$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.HidlMemoryUtil.lambda$getBuffer$0(mmap, size);
                }
            }, true);
        } catch (android.system.ErrnoException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    static /* synthetic */ void lambda$getBuffer$0(long j, int i) {
        try {
            android.system.Os.munmap(j, i);
        } catch (android.system.ErrnoException e) {
            android.util.Log.wtf(TAG, e);
        }
    }
}
