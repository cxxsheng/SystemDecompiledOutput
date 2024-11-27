package com.android.server.criticalevents.nano;

/* loaded from: classes5.dex */
public final class CriticalEventProto extends com.android.framework.protobuf.nano.MessageNano {
    public static final int ANR_FIELD_NUMBER = 4;
    public static final int DATA_APP = 1;
    public static final int EXCESSIVE_BINDER_CALLS_FIELD_NUMBER = 9;
    public static final int HALF_WATCHDOG_FIELD_NUMBER = 3;
    public static final int INSTALL_PACKAGES_FIELD_NUMBER = 8;
    public static final int JAVA_CRASH_FIELD_NUMBER = 5;
    public static final int NATIVE_CRASH_FIELD_NUMBER = 6;
    public static final int PROCESS_CLASS_UNKNOWN = 0;
    public static final int SYSTEM_APP = 2;
    public static final int SYSTEM_SERVER = 3;
    public static final int SYSTEM_SERVER_STARTED_FIELD_NUMBER = 7;
    public static final int WATCHDOG_FIELD_NUMBER = 2;
    private static volatile com.android.server.criticalevents.nano.CriticalEventProto[] _emptyArray;
    private int eventCase_ = 0;
    private java.lang.Object event_;
    public long timestampMs;

    public static final class ExcessiveBinderCalls extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls[] _emptyArray;
        public int uid;

        public static com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ExcessiveBinderCalls() {
            clear();
        }

        public com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls clear() {
            this.uid = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (this.uid != 0) {
                codedOutputByteBufferNano.writeInt32(1, this.uid);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (this.uid != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(1, this.uid);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 8:
                        this.uid = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls(), bArr);
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class InstallPackages extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages[] _emptyArray;

        public static com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages[0];
                    }
                }
            }
            return _emptyArray;
        }

        public InstallPackages() {
            clear();
        }

        public com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages clear() {
            this.cachedSize = -1;
            return this;
        }

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
            	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
            	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:103)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
            */
        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano r2) throws java.io.IOException {
            /*
                r1 = this;
            L1:
                int r0 = r2.readTag()
                switch(r0) {
                    case 0: goto Lf;
                    default: goto L8;
                }
            L8:
                boolean r0 = com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(r2, r0)
                if (r0 != 0) goto L10
                return r1
            Lf:
                return r1
            L10:
                goto L1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages.mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano):com.android.server.criticalevents.nano.CriticalEventProto$InstallPackages");
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages(), bArr);
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class SystemServerStarted extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted[] _emptyArray;

        public static com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted[0];
                    }
                }
            }
            return _emptyArray;
        }

        public SystemServerStarted() {
            clear();
        }

        public com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted clear() {
            this.cachedSize = -1;
            return this;
        }

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
            	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
            	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:103)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
            */
        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano r2) throws java.io.IOException {
            /*
                r1 = this;
            L1:
                int r0 = r2.readTag()
                switch(r0) {
                    case 0: goto Lf;
                    default: goto L8;
                }
            L8:
                boolean r0 = com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(r2, r0)
                if (r0 != 0) goto L10
                return r1
            Lf:
                return r1
            L10:
                goto L1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted.mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano):com.android.server.criticalevents.nano.CriticalEventProto$SystemServerStarted");
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted(), bArr);
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class Watchdog extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.criticalevents.nano.CriticalEventProto.Watchdog[] _emptyArray;
        public java.lang.String subject;
        public java.lang.String uuid;

        public static com.android.server.criticalevents.nano.CriticalEventProto.Watchdog[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.criticalevents.nano.CriticalEventProto.Watchdog[0];
                    }
                }
            }
            return _emptyArray;
        }

        public Watchdog() {
            clear();
        }

        public com.android.server.criticalevents.nano.CriticalEventProto.Watchdog clear() {
            this.subject = "";
            this.uuid = "";
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (!this.subject.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.subject);
            }
            if (!this.uuid.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.uuid);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.subject.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.subject);
            }
            if (!this.uuid.equals("")) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(2, this.uuid);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.criticalevents.nano.CriticalEventProto.Watchdog mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.subject = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        this.uuid = codedInputByteBufferNano.readString();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.Watchdog parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.criticalevents.nano.CriticalEventProto.Watchdog) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventProto.Watchdog(), bArr);
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.Watchdog parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.criticalevents.nano.CriticalEventProto.Watchdog().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class HalfWatchdog extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog[] _emptyArray;
        public java.lang.String subject;

        public static com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog[0];
                    }
                }
            }
            return _emptyArray;
        }

        public HalfWatchdog() {
            clear();
        }

        public com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog clear() {
            this.subject = "";
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (!this.subject.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.subject);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.subject.equals("")) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.subject);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.subject = codedInputByteBufferNano.readString();
                        break;
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog(), bArr);
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class AppNotResponding extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding[] _emptyArray;
        public int pid;
        public java.lang.String process;
        public int processClass;
        public java.lang.String subject;
        public int uid;

        public static com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding[0];
                    }
                }
            }
            return _emptyArray;
        }

        public AppNotResponding() {
            clear();
        }

        public com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding clear() {
            this.subject = "";
            this.process = "";
            this.pid = 0;
            this.uid = 0;
            this.processClass = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (!this.subject.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.subject);
            }
            if (!this.process.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.process);
            }
            if (this.pid != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.pid);
            }
            if (this.uid != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.uid);
            }
            if (this.processClass != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.processClass);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.subject.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.subject);
            }
            if (!this.process.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(2, this.process);
            }
            if (this.pid != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.pid);
            }
            if (this.uid != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.uid);
            }
            if (this.processClass != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(5, this.processClass);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.subject = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        this.process = codedInputByteBufferNano.readString();
                        break;
                    case 24:
                        this.pid = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.uid = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        switch (readInt32) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.processClass = readInt32;
                                break;
                        }
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding(), bArr);
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class JavaCrash extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash[] _emptyArray;
        public java.lang.String exceptionClass;
        public int pid;
        public java.lang.String process;
        public int processClass;
        public int uid;

        public static com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash[0];
                    }
                }
            }
            return _emptyArray;
        }

        public JavaCrash() {
            clear();
        }

        public com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash clear() {
            this.exceptionClass = "";
            this.process = "";
            this.pid = 0;
            this.uid = 0;
            this.processClass = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (!this.exceptionClass.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.exceptionClass);
            }
            if (!this.process.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.process);
            }
            if (this.pid != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.pid);
            }
            if (this.uid != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.uid);
            }
            if (this.processClass != 0) {
                codedOutputByteBufferNano.writeInt32(5, this.processClass);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.exceptionClass.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.exceptionClass);
            }
            if (!this.process.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(2, this.process);
            }
            if (this.pid != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.pid);
            }
            if (this.uid != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.uid);
            }
            if (this.processClass != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(5, this.processClass);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.exceptionClass = codedInputByteBufferNano.readString();
                        break;
                    case 18:
                        this.process = codedInputByteBufferNano.readString();
                        break;
                    case 24:
                        this.pid = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.uid = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        switch (readInt32) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.processClass = readInt32;
                                break;
                        }
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash(), bArr);
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class NativeCrash extends com.android.framework.protobuf.nano.MessageNano {
        private static volatile com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash[] _emptyArray;
        public int pid;
        public java.lang.String process;
        public int processClass;
        public int uid;

        public static com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash[0];
                    }
                }
            }
            return _emptyArray;
        }

        public NativeCrash() {
            clear();
        }

        public com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash clear() {
            this.process = "";
            this.pid = 0;
            this.uid = 0;
            this.processClass = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
            if (!this.process.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.process);
            }
            if (this.pid != 0) {
                codedOutputByteBufferNano.writeInt32(2, this.pid);
            }
            if (this.uid != 0) {
                codedOutputByteBufferNano.writeInt32(3, this.uid);
            }
            if (this.processClass != 0) {
                codedOutputByteBufferNano.writeInt32(4, this.processClass);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            if (!this.process.equals("")) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.process);
            }
            if (this.pid != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.pid);
            }
            if (this.uid != 0) {
                computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.uid);
            }
            if (this.processClass != 0) {
                return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(4, this.processClass);
            }
            return computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        return this;
                    case 10:
                        this.process = codedInputByteBufferNano.readString();
                        break;
                    case 16:
                        this.pid = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.uid = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        int readInt32 = codedInputByteBufferNano.readInt32();
                        switch (readInt32) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.processClass = readInt32;
                                break;
                        }
                    default:
                        if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
            return (com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash(), bArr);
        }

        public static com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
            return new com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash().mergeFrom(codedInputByteBufferNano);
        }
    }

    public int getEventCase() {
        return this.eventCase_;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto clearEvent() {
        this.eventCase_ = 0;
        this.event_ = null;
        return this;
    }

    public static com.android.server.criticalevents.nano.CriticalEventProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new com.android.server.criticalevents.nano.CriticalEventProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public boolean hasWatchdog() {
        return this.eventCase_ == 2;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto.Watchdog getWatchdog() {
        if (this.eventCase_ == 2) {
            return (com.android.server.criticalevents.nano.CriticalEventProto.Watchdog) this.event_;
        }
        return null;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto setWatchdog(com.android.server.criticalevents.nano.CriticalEventProto.Watchdog watchdog) {
        if (watchdog == null) {
            throw new java.lang.NullPointerException();
        }
        this.eventCase_ = 2;
        this.event_ = watchdog;
        return this;
    }

    public boolean hasHalfWatchdog() {
        return this.eventCase_ == 3;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog getHalfWatchdog() {
        if (this.eventCase_ == 3) {
            return (com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog) this.event_;
        }
        return null;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto setHalfWatchdog(com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog halfWatchdog) {
        if (halfWatchdog == null) {
            throw new java.lang.NullPointerException();
        }
        this.eventCase_ = 3;
        this.event_ = halfWatchdog;
        return this;
    }

    public boolean hasAnr() {
        return this.eventCase_ == 4;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding getAnr() {
        if (this.eventCase_ == 4) {
            return (com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding) this.event_;
        }
        return null;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto setAnr(com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding appNotResponding) {
        if (appNotResponding == null) {
            throw new java.lang.NullPointerException();
        }
        this.eventCase_ = 4;
        this.event_ = appNotResponding;
        return this;
    }

    public boolean hasJavaCrash() {
        return this.eventCase_ == 5;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash getJavaCrash() {
        if (this.eventCase_ == 5) {
            return (com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash) this.event_;
        }
        return null;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto setJavaCrash(com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash javaCrash) {
        if (javaCrash == null) {
            throw new java.lang.NullPointerException();
        }
        this.eventCase_ = 5;
        this.event_ = javaCrash;
        return this;
    }

    public boolean hasNativeCrash() {
        return this.eventCase_ == 6;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash getNativeCrash() {
        if (this.eventCase_ == 6) {
            return (com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash) this.event_;
        }
        return null;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto setNativeCrash(com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash nativeCrash) {
        if (nativeCrash == null) {
            throw new java.lang.NullPointerException();
        }
        this.eventCase_ = 6;
        this.event_ = nativeCrash;
        return this;
    }

    public boolean hasSystemServerStarted() {
        return this.eventCase_ == 7;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted getSystemServerStarted() {
        if (this.eventCase_ == 7) {
            return (com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted) this.event_;
        }
        return null;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto setSystemServerStarted(com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted systemServerStarted) {
        if (systemServerStarted == null) {
            throw new java.lang.NullPointerException();
        }
        this.eventCase_ = 7;
        this.event_ = systemServerStarted;
        return this;
    }

    public boolean hasInstallPackages() {
        return this.eventCase_ == 8;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages getInstallPackages() {
        if (this.eventCase_ == 8) {
            return (com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages) this.event_;
        }
        return null;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto setInstallPackages(com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages installPackages) {
        if (installPackages == null) {
            throw new java.lang.NullPointerException();
        }
        this.eventCase_ = 8;
        this.event_ = installPackages;
        return this;
    }

    public boolean hasExcessiveBinderCalls() {
        return this.eventCase_ == 9;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls getExcessiveBinderCalls() {
        if (this.eventCase_ == 9) {
            return (com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls) this.event_;
        }
        return null;
    }

    public com.android.server.criticalevents.nano.CriticalEventProto setExcessiveBinderCalls(com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls excessiveBinderCalls) {
        if (excessiveBinderCalls == null) {
            throw new java.lang.NullPointerException();
        }
        this.eventCase_ = 9;
        this.event_ = excessiveBinderCalls;
        return this;
    }

    public CriticalEventProto() {
        clear();
    }

    public com.android.server.criticalevents.nano.CriticalEventProto clear() {
        this.timestampMs = 0L;
        clearEvent();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (this.timestampMs != 0) {
            codedOutputByteBufferNano.writeInt64(1, this.timestampMs);
        }
        if (this.eventCase_ == 2) {
            codedOutputByteBufferNano.writeMessage(2, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 3) {
            codedOutputByteBufferNano.writeMessage(3, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 4) {
            codedOutputByteBufferNano.writeMessage(4, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 5) {
            codedOutputByteBufferNano.writeMessage(5, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 6) {
            codedOutputByteBufferNano.writeMessage(6, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 7) {
            codedOutputByteBufferNano.writeMessage(7, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 8) {
            codedOutputByteBufferNano.writeMessage(8, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 9) {
            codedOutputByteBufferNano.writeMessage(9, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (this.timestampMs != 0) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(1, this.timestampMs);
        }
        if (this.eventCase_ == 2) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(2, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 3) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(3, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 4) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(4, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 5) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(5, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 6) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(6, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 7) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(7, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 8) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(8, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        if (this.eventCase_ == 9) {
            return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(9, (com.android.framework.protobuf.nano.MessageNano) this.event_);
        }
        return computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public com.android.server.criticalevents.nano.CriticalEventProto mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            switch (readTag) {
                case 0:
                    return this;
                case 8:
                    this.timestampMs = codedInputByteBufferNano.readInt64();
                    break;
                case 18:
                    if (this.eventCase_ != 2) {
                        this.event_ = new com.android.server.criticalevents.nano.CriticalEventProto.Watchdog();
                    }
                    codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                    this.eventCase_ = 2;
                    break;
                case 26:
                    if (this.eventCase_ != 3) {
                        this.event_ = new com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog();
                    }
                    codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                    this.eventCase_ = 3;
                    break;
                case 34:
                    if (this.eventCase_ != 4) {
                        this.event_ = new com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding();
                    }
                    codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                    this.eventCase_ = 4;
                    break;
                case 42:
                    if (this.eventCase_ != 5) {
                        this.event_ = new com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash();
                    }
                    codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                    this.eventCase_ = 5;
                    break;
                case 50:
                    if (this.eventCase_ != 6) {
                        this.event_ = new com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash();
                    }
                    codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                    this.eventCase_ = 6;
                    break;
                case 58:
                    if (this.eventCase_ != 7) {
                        this.event_ = new com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted();
                    }
                    codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                    this.eventCase_ = 7;
                    break;
                case 66:
                    if (this.eventCase_ != 8) {
                        this.event_ = new com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages();
                    }
                    codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                    this.eventCase_ = 8;
                    break;
                case 74:
                    if (this.eventCase_ != 9) {
                        this.event_ = new com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls();
                    }
                    codedInputByteBufferNano.readMessage((com.android.framework.protobuf.nano.MessageNano) this.event_);
                    this.eventCase_ = 9;
                    break;
                default:
                    if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static com.android.server.criticalevents.nano.CriticalEventProto parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        return (com.android.server.criticalevents.nano.CriticalEventProto) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventProto(), bArr);
    }

    public static com.android.server.criticalevents.nano.CriticalEventProto parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        return new com.android.server.criticalevents.nano.CriticalEventProto().mergeFrom(codedInputByteBufferNano);
    }
}
