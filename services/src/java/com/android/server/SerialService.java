package com.android.server;

@android.ravenwood.annotation.RavenwoodKeepWholeClass
/* loaded from: classes.dex */
public class SerialService extends android.hardware.ISerialManager.Stub {
    private static final java.lang.String PREFIX_VIRTUAL = "virtual:";
    private final android.content.Context mContext;
    private final android.hardware.SerialManagerInternal mInternal;

    @com.android.internal.annotations.GuardedBy({"mSerialPorts"})
    private final java.util.LinkedHashMap<java.lang.String, java.util.function.Supplier<android.os.ParcelFileDescriptor>> mSerialPorts;

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: native_open, reason: merged with bridge method [inline-methods] */
    public native android.os.ParcelFileDescriptor lambda$new$0(java.lang.String str);

    public SerialService(android.content.Context context) {
        super(android.os.PermissionEnforcer.fromContext(context));
        this.mSerialPorts = new java.util.LinkedHashMap<>();
        this.mInternal = new android.hardware.SerialManagerInternal() { // from class: com.android.server.SerialService.1
            public void addVirtualSerialPortForTest(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.function.Supplier<android.os.ParcelFileDescriptor> supplier) {
                synchronized (com.android.server.SerialService.this.mSerialPorts) {
                    com.android.internal.util.Preconditions.checkState(!com.android.server.SerialService.this.mSerialPorts.containsKey(str), "Port " + str + " already defined");
                    com.android.internal.util.Preconditions.checkArgument(str.startsWith(com.android.server.SerialService.PREFIX_VIRTUAL), "Port " + str + " must be under " + com.android.server.SerialService.PREFIX_VIRTUAL);
                    com.android.server.SerialService.this.mSerialPorts.put(str, supplier);
                }
            }

            public void removeVirtualSerialPortForTest(@android.annotation.NonNull java.lang.String str) {
                synchronized (com.android.server.SerialService.this.mSerialPorts) {
                    com.android.internal.util.Preconditions.checkState(com.android.server.SerialService.this.mSerialPorts.containsKey(str), "Port " + str + " not yet defined");
                    com.android.internal.util.Preconditions.checkArgument(str.startsWith(com.android.server.SerialService.PREFIX_VIRTUAL), "Port " + str + " must be under " + com.android.server.SerialService.PREFIX_VIRTUAL);
                    com.android.server.SerialService.this.mSerialPorts.remove(str);
                }
            }
        };
        this.mContext = context;
        synchronized (this.mSerialPorts) {
            try {
                for (final java.lang.String str : getSerialPorts(context)) {
                    this.mSerialPorts.put(str, new java.util.function.Supplier() { // from class: com.android.server.SerialService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Supplier
                        public final java.lang.Object get() {
                            android.os.ParcelFileDescriptor lambda$new$0;
                            lambda$new$0 = com.android.server.SerialService.this.lambda$new$0(str);
                            return lambda$new$0;
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.ravenwood.annotation.RavenwoodReplace
    private static java.lang.String[] getSerialPorts(android.content.Context context) {
        return context.getResources().getStringArray(android.R.array.config_secondaryBuiltInDisplayCutoutSideOverride);
    }

    private static java.lang.String[] getSerialPorts$ravenwood(android.content.Context context) {
        return new java.lang.String[0];
    }

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.SerialService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mService = new com.android.server.SerialService(getContext());
            publishBinderService("serial", this.mService);
            publishLocalService(android.hardware.SerialManagerInternal.class, this.mService.mInternal);
        }
    }

    @android.annotation.EnforcePermission("android.permission.SERIAL_PORT")
    public java.lang.String[] getSerialPorts() {
        java.lang.String[] strArr;
        super.getSerialPorts_enforcePermission();
        synchronized (this.mSerialPorts) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (java.lang.String str : this.mSerialPorts.keySet()) {
                    if (str.startsWith(PREFIX_VIRTUAL) || new java.io.File(str).exists()) {
                        arrayList.add(str);
                    }
                }
                strArr = (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return strArr;
    }

    @android.annotation.EnforcePermission("android.permission.SERIAL_PORT")
    public android.os.ParcelFileDescriptor openSerialPort(java.lang.String str) {
        android.os.ParcelFileDescriptor parcelFileDescriptor;
        super.openSerialPort_enforcePermission();
        synchronized (this.mSerialPorts) {
            try {
                java.util.function.Supplier<android.os.ParcelFileDescriptor> supplier = this.mSerialPorts.get(str);
                if (supplier != null) {
                    parcelFileDescriptor = supplier.get();
                } else {
                    throw new java.lang.IllegalArgumentException("Invalid serial port " + str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return parcelFileDescriptor;
    }
}
