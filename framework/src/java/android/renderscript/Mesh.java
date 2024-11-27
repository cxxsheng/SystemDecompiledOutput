package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Mesh extends android.renderscript.BaseObj {
    android.renderscript.Allocation[] mIndexBuffers;
    android.renderscript.Mesh.Primitive[] mPrimitives;
    android.renderscript.Allocation[] mVertexBuffers;

    public enum Primitive {
        POINT(0),
        LINE(1),
        LINE_STRIP(2),
        TRIANGLE(3),
        TRIANGLE_STRIP(4),
        TRIANGLE_FAN(5);

        int mID;

        Primitive(int i) {
            this.mID = i;
        }
    }

    Mesh(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.guard.open("destroy");
    }

    public int getVertexAllocationCount() {
        if (this.mVertexBuffers == null) {
            return 0;
        }
        return this.mVertexBuffers.length;
    }

    public android.renderscript.Allocation getVertexAllocation(int i) {
        return this.mVertexBuffers[i];
    }

    public int getPrimitiveCount() {
        if (this.mIndexBuffers == null) {
            return 0;
        }
        return this.mIndexBuffers.length;
    }

    public android.renderscript.Allocation getIndexSetAllocation(int i) {
        return this.mIndexBuffers[i];
    }

    public android.renderscript.Mesh.Primitive getPrimitive(int i) {
        return this.mPrimitives[i];
    }

    @Override // android.renderscript.BaseObj
    void updateFromNative() {
        super.updateFromNative();
        int nMeshGetVertexBufferCount = this.mRS.nMeshGetVertexBufferCount(getID(this.mRS));
        int nMeshGetIndexCount = this.mRS.nMeshGetIndexCount(getID(this.mRS));
        long[] jArr = new long[nMeshGetVertexBufferCount];
        long[] jArr2 = new long[nMeshGetIndexCount];
        int[] iArr = new int[nMeshGetIndexCount];
        this.mRS.nMeshGetVertices(getID(this.mRS), jArr, nMeshGetVertexBufferCount);
        this.mRS.nMeshGetIndices(getID(this.mRS), jArr2, iArr, nMeshGetIndexCount);
        this.mVertexBuffers = new android.renderscript.Allocation[nMeshGetVertexBufferCount];
        this.mIndexBuffers = new android.renderscript.Allocation[nMeshGetIndexCount];
        this.mPrimitives = new android.renderscript.Mesh.Primitive[nMeshGetIndexCount];
        for (int i = 0; i < nMeshGetVertexBufferCount; i++) {
            if (jArr[i] != 0) {
                this.mVertexBuffers[i] = new android.renderscript.Allocation(jArr[i], this.mRS, null, 1);
                this.mVertexBuffers[i].updateFromNative();
            }
        }
        for (int i2 = 0; i2 < nMeshGetIndexCount; i2++) {
            if (jArr2[i2] != 0) {
                this.mIndexBuffers[i2] = new android.renderscript.Allocation(jArr2[i2], this.mRS, null, 1);
                this.mIndexBuffers[i2].updateFromNative();
            }
            this.mPrimitives[i2] = android.renderscript.Mesh.Primitive.values()[iArr[i2]];
        }
    }

    public static class Builder {
        android.renderscript.RenderScript mRS;
        int mUsage;
        int mVertexTypeCount = 0;
        android.renderscript.Mesh.Builder.Entry[] mVertexTypes = new android.renderscript.Mesh.Builder.Entry[16];
        java.util.Vector mIndexTypes = new java.util.Vector();

        class Entry {
            android.renderscript.Element e;
            android.renderscript.Mesh.Primitive prim;
            int size;
            android.renderscript.Type t;
            int usage;

            Entry() {
            }
        }

        public Builder(android.renderscript.RenderScript renderScript, int i) {
            this.mRS = renderScript;
            this.mUsage = i;
        }

        public int getCurrentVertexTypeIndex() {
            return this.mVertexTypeCount - 1;
        }

        public int getCurrentIndexSetIndex() {
            return this.mIndexTypes.size() - 1;
        }

        public android.renderscript.Mesh.Builder addVertexType(android.renderscript.Type type) throws java.lang.IllegalStateException {
            if (this.mVertexTypeCount >= this.mVertexTypes.length) {
                throw new java.lang.IllegalStateException("Max vertex types exceeded.");
            }
            this.mVertexTypes[this.mVertexTypeCount] = new android.renderscript.Mesh.Builder.Entry();
            this.mVertexTypes[this.mVertexTypeCount].t = type;
            this.mVertexTypes[this.mVertexTypeCount].e = null;
            this.mVertexTypeCount++;
            return this;
        }

        public android.renderscript.Mesh.Builder addVertexType(android.renderscript.Element element, int i) throws java.lang.IllegalStateException {
            if (this.mVertexTypeCount >= this.mVertexTypes.length) {
                throw new java.lang.IllegalStateException("Max vertex types exceeded.");
            }
            this.mVertexTypes[this.mVertexTypeCount] = new android.renderscript.Mesh.Builder.Entry();
            this.mVertexTypes[this.mVertexTypeCount].t = null;
            this.mVertexTypes[this.mVertexTypeCount].e = element;
            this.mVertexTypes[this.mVertexTypeCount].size = i;
            this.mVertexTypeCount++;
            return this;
        }

        public android.renderscript.Mesh.Builder addIndexSetType(android.renderscript.Type type, android.renderscript.Mesh.Primitive primitive) {
            android.renderscript.Mesh.Builder.Entry entry = new android.renderscript.Mesh.Builder.Entry();
            entry.t = type;
            entry.e = null;
            entry.size = 0;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public android.renderscript.Mesh.Builder addIndexSetType(android.renderscript.Mesh.Primitive primitive) {
            android.renderscript.Mesh.Builder.Entry entry = new android.renderscript.Mesh.Builder.Entry();
            entry.t = null;
            entry.e = null;
            entry.size = 0;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public android.renderscript.Mesh.Builder addIndexSetType(android.renderscript.Element element, int i, android.renderscript.Mesh.Primitive primitive) {
            android.renderscript.Mesh.Builder.Entry entry = new android.renderscript.Mesh.Builder.Entry();
            entry.t = null;
            entry.e = element;
            entry.size = i;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        android.renderscript.Type newType(android.renderscript.Element element, int i) {
            android.renderscript.Type.Builder builder = new android.renderscript.Type.Builder(this.mRS, element);
            builder.setX(i);
            return builder.create();
        }

        public android.renderscript.Mesh create() {
            android.renderscript.Allocation createSized;
            android.renderscript.Allocation createSized2;
            this.mRS.validate();
            long[] jArr = new long[this.mVertexTypeCount];
            long[] jArr2 = new long[this.mIndexTypes.size()];
            int[] iArr = new int[this.mIndexTypes.size()];
            android.renderscript.Allocation[] allocationArr = new android.renderscript.Allocation[this.mVertexTypeCount];
            android.renderscript.Allocation[] allocationArr2 = new android.renderscript.Allocation[this.mIndexTypes.size()];
            android.renderscript.Mesh.Primitive[] primitiveArr = new android.renderscript.Mesh.Primitive[this.mIndexTypes.size()];
            for (int i = 0; i < this.mVertexTypeCount; i++) {
                android.renderscript.Mesh.Builder.Entry entry = this.mVertexTypes[i];
                if (entry.t != null) {
                    createSized2 = android.renderscript.Allocation.createTyped(this.mRS, entry.t, this.mUsage);
                } else if (entry.e != null) {
                    createSized2 = android.renderscript.Allocation.createSized(this.mRS, entry.e, entry.size, this.mUsage);
                } else {
                    throw new java.lang.IllegalStateException("Builder corrupt, no valid element in entry.");
                }
                allocationArr[i] = createSized2;
                jArr[i] = createSized2.getID(this.mRS);
            }
            for (int i2 = 0; i2 < this.mIndexTypes.size(); i2++) {
                android.renderscript.Mesh.Builder.Entry entry2 = (android.renderscript.Mesh.Builder.Entry) this.mIndexTypes.elementAt(i2);
                if (entry2.t != null) {
                    createSized = android.renderscript.Allocation.createTyped(this.mRS, entry2.t, this.mUsage);
                } else if (entry2.e != null) {
                    createSized = android.renderscript.Allocation.createSized(this.mRS, entry2.e, entry2.size, this.mUsage);
                } else {
                    throw new java.lang.IllegalStateException("Builder corrupt, no valid element in entry.");
                }
                long id = createSized == null ? 0L : createSized.getID(this.mRS);
                allocationArr2[i2] = createSized;
                primitiveArr[i2] = entry2.prim;
                jArr2[i2] = id;
                iArr[i2] = entry2.prim.mID;
            }
            android.renderscript.Mesh mesh = new android.renderscript.Mesh(this.mRS.nMeshCreate(jArr, jArr2, iArr), this.mRS);
            mesh.mVertexBuffers = allocationArr;
            mesh.mIndexBuffers = allocationArr2;
            mesh.mPrimitives = primitiveArr;
            return mesh;
        }
    }

    public static class AllocationBuilder {
        android.renderscript.RenderScript mRS;
        int mVertexTypeCount = 0;
        android.renderscript.Mesh.AllocationBuilder.Entry[] mVertexTypes = new android.renderscript.Mesh.AllocationBuilder.Entry[16];
        java.util.Vector mIndexTypes = new java.util.Vector();

        class Entry {
            android.renderscript.Allocation a;
            android.renderscript.Mesh.Primitive prim;

            Entry() {
            }
        }

        public AllocationBuilder(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public int getCurrentVertexTypeIndex() {
            return this.mVertexTypeCount - 1;
        }

        public int getCurrentIndexSetIndex() {
            return this.mIndexTypes.size() - 1;
        }

        public android.renderscript.Mesh.AllocationBuilder addVertexAllocation(android.renderscript.Allocation allocation) throws java.lang.IllegalStateException {
            if (this.mVertexTypeCount >= this.mVertexTypes.length) {
                throw new java.lang.IllegalStateException("Max vertex types exceeded.");
            }
            this.mVertexTypes[this.mVertexTypeCount] = new android.renderscript.Mesh.AllocationBuilder.Entry();
            this.mVertexTypes[this.mVertexTypeCount].a = allocation;
            this.mVertexTypeCount++;
            return this;
        }

        public android.renderscript.Mesh.AllocationBuilder addIndexSetAllocation(android.renderscript.Allocation allocation, android.renderscript.Mesh.Primitive primitive) {
            android.renderscript.Mesh.AllocationBuilder.Entry entry = new android.renderscript.Mesh.AllocationBuilder.Entry();
            entry.a = allocation;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public android.renderscript.Mesh.AllocationBuilder addIndexSetType(android.renderscript.Mesh.Primitive primitive) {
            android.renderscript.Mesh.AllocationBuilder.Entry entry = new android.renderscript.Mesh.AllocationBuilder.Entry();
            entry.a = null;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public android.renderscript.Mesh create() {
            this.mRS.validate();
            long[] jArr = new long[this.mVertexTypeCount];
            long[] jArr2 = new long[this.mIndexTypes.size()];
            int[] iArr = new int[this.mIndexTypes.size()];
            android.renderscript.Allocation[] allocationArr = new android.renderscript.Allocation[this.mIndexTypes.size()];
            android.renderscript.Mesh.Primitive[] primitiveArr = new android.renderscript.Mesh.Primitive[this.mIndexTypes.size()];
            android.renderscript.Allocation[] allocationArr2 = new android.renderscript.Allocation[this.mVertexTypeCount];
            for (int i = 0; i < this.mVertexTypeCount; i++) {
                android.renderscript.Mesh.AllocationBuilder.Entry entry = this.mVertexTypes[i];
                allocationArr2[i] = entry.a;
                jArr[i] = entry.a.getID(this.mRS);
            }
            for (int i2 = 0; i2 < this.mIndexTypes.size(); i2++) {
                android.renderscript.Mesh.AllocationBuilder.Entry entry2 = (android.renderscript.Mesh.AllocationBuilder.Entry) this.mIndexTypes.elementAt(i2);
                long id = entry2.a == null ? 0L : entry2.a.getID(this.mRS);
                allocationArr[i2] = entry2.a;
                primitiveArr[i2] = entry2.prim;
                jArr2[i2] = id;
                iArr[i2] = entry2.prim.mID;
            }
            android.renderscript.Mesh mesh = new android.renderscript.Mesh(this.mRS.nMeshCreate(jArr, jArr2, iArr), this.mRS);
            mesh.mVertexBuffers = allocationArr2;
            mesh.mIndexBuffers = allocationArr;
            mesh.mPrimitives = primitiveArr;
            return mesh;
        }
    }

    public static class TriangleMeshBuilder {
        public static final int COLOR = 1;
        public static final int NORMAL = 2;
        public static final int TEXTURE_0 = 256;
        android.renderscript.Element mElement;
        int mFlags;
        android.renderscript.RenderScript mRS;
        int mVtxSize;
        float mNX = 0.0f;
        float mNY = 0.0f;
        float mNZ = -1.0f;
        float mS0 = 0.0f;
        float mT0 = 0.0f;
        float mR = 1.0f;
        float mG = 1.0f;
        float mB = 1.0f;
        float mA = 1.0f;
        int mVtxCount = 0;
        int mMaxIndex = 0;
        int mIndexCount = 0;
        float[] mVtxData = new float[128];
        short[] mIndexData = new short[128];

        public TriangleMeshBuilder(android.renderscript.RenderScript renderScript, int i, int i2) {
            this.mRS = renderScript;
            this.mVtxSize = i;
            this.mFlags = i2;
            if (i < 2 || i > 3) {
                throw new java.lang.IllegalArgumentException("Vertex size out of range.");
            }
        }

        private void makeSpace(int i) {
            if (this.mVtxCount + i >= this.mVtxData.length) {
                float[] fArr = new float[this.mVtxData.length * 2];
                java.lang.System.arraycopy(this.mVtxData, 0, fArr, 0, this.mVtxData.length);
                this.mVtxData = fArr;
            }
        }

        private void latch() {
            if ((this.mFlags & 1) != 0) {
                makeSpace(4);
                float[] fArr = this.mVtxData;
                int i = this.mVtxCount;
                this.mVtxCount = i + 1;
                fArr[i] = this.mR;
                float[] fArr2 = this.mVtxData;
                int i2 = this.mVtxCount;
                this.mVtxCount = i2 + 1;
                fArr2[i2] = this.mG;
                float[] fArr3 = this.mVtxData;
                int i3 = this.mVtxCount;
                this.mVtxCount = i3 + 1;
                fArr3[i3] = this.mB;
                float[] fArr4 = this.mVtxData;
                int i4 = this.mVtxCount;
                this.mVtxCount = i4 + 1;
                fArr4[i4] = this.mA;
            }
            if ((this.mFlags & 256) != 0) {
                makeSpace(2);
                float[] fArr5 = this.mVtxData;
                int i5 = this.mVtxCount;
                this.mVtxCount = i5 + 1;
                fArr5[i5] = this.mS0;
                float[] fArr6 = this.mVtxData;
                int i6 = this.mVtxCount;
                this.mVtxCount = i6 + 1;
                fArr6[i6] = this.mT0;
            }
            if ((this.mFlags & 2) != 0) {
                makeSpace(4);
                float[] fArr7 = this.mVtxData;
                int i7 = this.mVtxCount;
                this.mVtxCount = i7 + 1;
                fArr7[i7] = this.mNX;
                float[] fArr8 = this.mVtxData;
                int i8 = this.mVtxCount;
                this.mVtxCount = i8 + 1;
                fArr8[i8] = this.mNY;
                float[] fArr9 = this.mVtxData;
                int i9 = this.mVtxCount;
                this.mVtxCount = i9 + 1;
                fArr9[i9] = this.mNZ;
                float[] fArr10 = this.mVtxData;
                int i10 = this.mVtxCount;
                this.mVtxCount = i10 + 1;
                fArr10[i10] = 0.0f;
            }
            this.mMaxIndex++;
        }

        public android.renderscript.Mesh.TriangleMeshBuilder addVertex(float f, float f2) {
            if (this.mVtxSize != 2) {
                throw new java.lang.IllegalStateException("add mistmatch with declared components.");
            }
            makeSpace(2);
            float[] fArr = this.mVtxData;
            int i = this.mVtxCount;
            this.mVtxCount = i + 1;
            fArr[i] = f;
            float[] fArr2 = this.mVtxData;
            int i2 = this.mVtxCount;
            this.mVtxCount = i2 + 1;
            fArr2[i2] = f2;
            latch();
            return this;
        }

        public android.renderscript.Mesh.TriangleMeshBuilder addVertex(float f, float f2, float f3) {
            if (this.mVtxSize != 3) {
                throw new java.lang.IllegalStateException("add mistmatch with declared components.");
            }
            makeSpace(4);
            float[] fArr = this.mVtxData;
            int i = this.mVtxCount;
            this.mVtxCount = i + 1;
            fArr[i] = f;
            float[] fArr2 = this.mVtxData;
            int i2 = this.mVtxCount;
            this.mVtxCount = i2 + 1;
            fArr2[i2] = f2;
            float[] fArr3 = this.mVtxData;
            int i3 = this.mVtxCount;
            this.mVtxCount = i3 + 1;
            fArr3[i3] = f3;
            float[] fArr4 = this.mVtxData;
            int i4 = this.mVtxCount;
            this.mVtxCount = i4 + 1;
            fArr4[i4] = 1.0f;
            latch();
            return this;
        }

        public android.renderscript.Mesh.TriangleMeshBuilder setTexture(float f, float f2) {
            if ((this.mFlags & 256) == 0) {
                throw new java.lang.IllegalStateException("add mistmatch with declared components.");
            }
            this.mS0 = f;
            this.mT0 = f2;
            return this;
        }

        public android.renderscript.Mesh.TriangleMeshBuilder setNormal(float f, float f2, float f3) {
            if ((this.mFlags & 2) == 0) {
                throw new java.lang.IllegalStateException("add mistmatch with declared components.");
            }
            this.mNX = f;
            this.mNY = f2;
            this.mNZ = f3;
            return this;
        }

        public android.renderscript.Mesh.TriangleMeshBuilder setColor(float f, float f2, float f3, float f4) {
            if ((this.mFlags & 1) == 0) {
                throw new java.lang.IllegalStateException("add mistmatch with declared components.");
            }
            this.mR = f;
            this.mG = f2;
            this.mB = f3;
            this.mA = f4;
            return this;
        }

        public android.renderscript.Mesh.TriangleMeshBuilder addTriangle(int i, int i2, int i3) {
            if (i >= this.mMaxIndex || i < 0 || i2 >= this.mMaxIndex || i2 < 0 || i3 >= this.mMaxIndex || i3 < 0) {
                throw new java.lang.IllegalStateException("Index provided greater than vertex count.");
            }
            if (this.mIndexCount + 3 >= this.mIndexData.length) {
                short[] sArr = new short[this.mIndexData.length * 2];
                java.lang.System.arraycopy(this.mIndexData, 0, sArr, 0, this.mIndexData.length);
                this.mIndexData = sArr;
            }
            short[] sArr2 = this.mIndexData;
            int i4 = this.mIndexCount;
            this.mIndexCount = i4 + 1;
            sArr2[i4] = (short) i;
            short[] sArr3 = this.mIndexData;
            int i5 = this.mIndexCount;
            this.mIndexCount = i5 + 1;
            sArr3[i5] = (short) i2;
            short[] sArr4 = this.mIndexData;
            int i6 = this.mIndexCount;
            this.mIndexCount = i6 + 1;
            sArr4[i6] = (short) i3;
            return this;
        }

        public android.renderscript.Mesh create(boolean z) {
            int i;
            android.renderscript.Element.Builder builder = new android.renderscript.Element.Builder(this.mRS);
            builder.add(android.renderscript.Element.createVector(this.mRS, android.renderscript.Element.DataType.FLOAT_32, this.mVtxSize), "position");
            if ((this.mFlags & 1) != 0) {
                builder.add(android.renderscript.Element.F32_4(this.mRS), "color");
            }
            if ((this.mFlags & 256) != 0) {
                builder.add(android.renderscript.Element.F32_2(this.mRS), "texture0");
            }
            if ((this.mFlags & 2) != 0) {
                builder.add(android.renderscript.Element.F32_3(this.mRS), android.graphics.FontListParser.STYLE_NORMAL);
            }
            this.mElement = builder.create();
            if (!z) {
                i = 1;
            } else {
                i = 5;
            }
            android.renderscript.Mesh.Builder builder2 = new android.renderscript.Mesh.Builder(this.mRS, i);
            builder2.addVertexType(this.mElement, this.mMaxIndex);
            builder2.addIndexSetType(android.renderscript.Element.U16(this.mRS), this.mIndexCount, android.renderscript.Mesh.Primitive.TRIANGLE);
            android.renderscript.Mesh create = builder2.create();
            create.getVertexAllocation(0).copy1DRangeFromUnchecked(0, this.mMaxIndex, this.mVtxData);
            if (z) {
                create.getVertexAllocation(0).syncAll(1);
            }
            create.getIndexSetAllocation(0).copy1DRangeFromUnchecked(0, this.mIndexCount, this.mIndexData);
            if (z) {
                create.getIndexSetAllocation(0).syncAll(1);
            }
            return create;
        }
    }
}
