package org.apache.commons.math.geometry;

/* loaded from: classes3.dex */
public class Rotation implements java.io.Serializable {
    public static final org.apache.commons.math.geometry.Rotation IDENTITY = new org.apache.commons.math.geometry.Rotation(1.0d, 0.0d, 0.0d, 0.0d, false);
    private static final long serialVersionUID = -2153622329907944313L;
    private final double q0;
    private final double q1;
    private final double q2;
    private final double q3;

    public Rotation(double d, double d2, double d3, double d4, boolean z) {
        if (z) {
            double sqrt = 1.0d / org.apache.commons.math.util.FastMath.sqrt((((d * d) + (d2 * d2)) + (d3 * d3)) + (d4 * d4));
            d *= sqrt;
            d2 *= sqrt;
            d3 *= sqrt;
            d4 *= sqrt;
        }
        this.q0 = d;
        this.q1 = d2;
        this.q2 = d3;
        this.q3 = d4;
    }

    public Rotation(org.apache.commons.math.geometry.Vector3D vector3D, double d) {
        double norm = vector3D.getNorm();
        if (norm == 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_NORM_FOR_ROTATION_AXIS, new java.lang.Object[0]);
        }
        double d2 = d * (-0.5d);
        double sin = org.apache.commons.math.util.FastMath.sin(d2) / norm;
        this.q0 = org.apache.commons.math.util.FastMath.cos(d2);
        this.q1 = vector3D.getX() * sin;
        this.q2 = vector3D.getY() * sin;
        this.q3 = sin * vector3D.getZ();
    }

    public Rotation(double[][] dArr, double d) throws org.apache.commons.math.geometry.NotARotationMatrixException {
        if (dArr.length != 3 || dArr[0].length != 3 || dArr[1].length != 3 || dArr[2].length != 3) {
            throw new org.apache.commons.math.geometry.NotARotationMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.ROTATION_MATRIX_DIMENSIONS, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr[0].length));
        }
        double[][] orthogonalizeMatrix = orthogonalizeMatrix(dArr, d);
        double d2 = ((orthogonalizeMatrix[0][0] * ((orthogonalizeMatrix[1][1] * orthogonalizeMatrix[2][2]) - (orthogonalizeMatrix[2][1] * orthogonalizeMatrix[1][2]))) - (orthogonalizeMatrix[1][0] * ((orthogonalizeMatrix[0][1] * orthogonalizeMatrix[2][2]) - (orthogonalizeMatrix[2][1] * orthogonalizeMatrix[0][2])))) + (orthogonalizeMatrix[2][0] * ((orthogonalizeMatrix[0][1] * orthogonalizeMatrix[1][2]) - (orthogonalizeMatrix[1][1] * orthogonalizeMatrix[0][2])));
        if (d2 < 0.0d) {
            throw new org.apache.commons.math.geometry.NotARotationMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.CLOSEST_ORTHOGONAL_MATRIX_HAS_NEGATIVE_DETERMINANT, java.lang.Double.valueOf(d2));
        }
        double d3 = orthogonalizeMatrix[0][0] + orthogonalizeMatrix[1][1] + orthogonalizeMatrix[2][2];
        if (d3 > -0.19d) {
            this.q0 = org.apache.commons.math.util.FastMath.sqrt(d3 + 1.0d) * 0.5d;
            double d4 = 0.25d / this.q0;
            this.q1 = (orthogonalizeMatrix[1][2] - orthogonalizeMatrix[2][1]) * d4;
            this.q2 = (orthogonalizeMatrix[2][0] - orthogonalizeMatrix[0][2]) * d4;
            this.q3 = d4 * (orthogonalizeMatrix[0][1] - orthogonalizeMatrix[1][0]);
            return;
        }
        double d5 = (orthogonalizeMatrix[0][0] - orthogonalizeMatrix[1][1]) - orthogonalizeMatrix[2][2];
        if (d5 > -0.19d) {
            this.q1 = org.apache.commons.math.util.FastMath.sqrt(d5 + 1.0d) * 0.5d;
            double d6 = 0.25d / this.q1;
            this.q0 = (orthogonalizeMatrix[1][2] - orthogonalizeMatrix[2][1]) * d6;
            this.q2 = (orthogonalizeMatrix[0][1] + orthogonalizeMatrix[1][0]) * d6;
            this.q3 = d6 * (orthogonalizeMatrix[0][2] + orthogonalizeMatrix[2][0]);
            return;
        }
        double d7 = (orthogonalizeMatrix[1][1] - orthogonalizeMatrix[0][0]) - orthogonalizeMatrix[2][2];
        if (d7 > -0.19d) {
            this.q2 = org.apache.commons.math.util.FastMath.sqrt(d7 + 1.0d) * 0.5d;
            double d8 = 0.25d / this.q2;
            this.q0 = (orthogonalizeMatrix[2][0] - orthogonalizeMatrix[0][2]) * d8;
            this.q1 = (orthogonalizeMatrix[0][1] + orthogonalizeMatrix[1][0]) * d8;
            this.q3 = d8 * (orthogonalizeMatrix[2][1] + orthogonalizeMatrix[1][2]);
            return;
        }
        this.q3 = org.apache.commons.math.util.FastMath.sqrt(((orthogonalizeMatrix[2][2] - orthogonalizeMatrix[0][0]) - orthogonalizeMatrix[1][1]) + 1.0d) * 0.5d;
        double d9 = 0.25d / this.q3;
        this.q0 = (orthogonalizeMatrix[0][1] - orthogonalizeMatrix[1][0]) * d9;
        this.q1 = (orthogonalizeMatrix[0][2] + orthogonalizeMatrix[2][0]) * d9;
        this.q2 = d9 * (orthogonalizeMatrix[2][1] + orthogonalizeMatrix[1][2]);
    }

    public Rotation(org.apache.commons.math.geometry.Vector3D vector3D, org.apache.commons.math.geometry.Vector3D vector3D2, org.apache.commons.math.geometry.Vector3D vector3D3, org.apache.commons.math.geometry.Vector3D vector3D4) {
        org.apache.commons.math.geometry.Vector3D vector3D5 = vector3D;
        double dotProduct = org.apache.commons.math.geometry.Vector3D.dotProduct(vector3D5, vector3D5);
        double dotProduct2 = org.apache.commons.math.geometry.Vector3D.dotProduct(vector3D2, vector3D2);
        double dotProduct3 = org.apache.commons.math.geometry.Vector3D.dotProduct(vector3D3, vector3D3);
        double dotProduct4 = org.apache.commons.math.geometry.Vector3D.dotProduct(vector3D4, vector3D4);
        if (dotProduct == 0.0d || dotProduct2 == 0.0d || dotProduct3 == 0.0d || dotProduct4 == 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_NORM_FOR_ROTATION_DEFINING_VECTOR, new java.lang.Object[0]);
        }
        double x = vector3D.getX();
        double y = vector3D.getY();
        double z = vector3D.getZ();
        double x2 = vector3D2.getX();
        double y2 = vector3D2.getY();
        double z2 = vector3D2.getZ();
        double sqrt = org.apache.commons.math.util.FastMath.sqrt(dotProduct / dotProduct3);
        double x3 = vector3D3.getX() * sqrt;
        double y3 = sqrt * vector3D3.getY();
        double z3 = sqrt * vector3D3.getZ();
        org.apache.commons.math.geometry.Vector3D vector3D6 = new org.apache.commons.math.geometry.Vector3D(x3, y3, z3);
        double dotProduct5 = org.apache.commons.math.geometry.Vector3D.dotProduct(vector3D, vector3D2);
        double dotProduct6 = org.apache.commons.math.geometry.Vector3D.dotProduct(vector3D6, vector3D4);
        double d = dotProduct5 / dotProduct;
        double d2 = dotProduct6 / dotProduct;
        double sqrt2 = org.apache.commons.math.util.FastMath.sqrt((dotProduct2 - (dotProduct5 * d)) / (dotProduct4 - (dotProduct6 * d2)));
        double d3 = d - (d2 * sqrt2);
        double x4 = (d3 * x3) + (vector3D4.getX() * sqrt2);
        double y4 = (d3 * y3) + (vector3D4.getY() * sqrt2);
        double z4 = (d3 * z3) + (sqrt2 * vector3D4.getZ());
        org.apache.commons.math.geometry.Vector3D vector3D7 = new org.apache.commons.math.geometry.Vector3D(x4, y4, z4);
        double x5 = x3 - vector3D.getX();
        double y5 = y3 - vector3D.getY();
        double z5 = z3 - vector3D.getZ();
        double x6 = x4 - vector3D2.getX();
        double y6 = y4 - vector3D2.getY();
        double z6 = z4 - vector3D2.getZ();
        org.apache.commons.math.geometry.Vector3D vector3D8 = new org.apache.commons.math.geometry.Vector3D((y5 * z6) - (z5 * y6), (z5 * x6) - (x5 * z6), (x5 * y6) - (y5 * x6));
        double x7 = (vector3D8.getX() * ((y * z2) - (z * y2))) + (vector3D8.getY() * ((z * x2) - (x * z2))) + (vector3D8.getZ() * ((x * y2) - (y * x2)));
        if (x7 == 0.0d) {
            org.apache.commons.math.geometry.Vector3D crossProduct = org.apache.commons.math.geometry.Vector3D.crossProduct(vector3D, vector3D2);
            org.apache.commons.math.geometry.Vector3D crossProduct2 = org.apache.commons.math.geometry.Vector3D.crossProduct(vector3D6, vector3D7);
            double x8 = crossProduct.getX();
            double y7 = crossProduct.getY();
            double z7 = crossProduct.getZ();
            double x9 = crossProduct2.getX();
            double d4 = x9 - x8;
            double y8 = crossProduct2.getY() - y7;
            double z8 = crossProduct2.getZ() - z7;
            org.apache.commons.math.geometry.Vector3D vector3D9 = new org.apache.commons.math.geometry.Vector3D((y5 * z8) - (z5 * y8), (z5 * d4) - (x5 * z8), (x5 * y8) - (y5 * d4));
            double x10 = (vector3D9.getX() * ((y * z7) - (z * y7))) + (vector3D9.getY() * ((z * x8) - (x * z7))) + (vector3D9.getZ() * ((x * y7) - (y * x8)));
            if (x10 != 0.0d) {
                x7 = x10;
                vector3D8 = vector3D9;
            } else {
                org.apache.commons.math.geometry.Vector3D vector3D10 = new org.apache.commons.math.geometry.Vector3D((y6 * z8) - (z6 * y8), (z6 * d4) - (z8 * x6), (x6 * y8) - (y6 * d4));
                x7 = (vector3D10.getX() * ((y2 * z7) - (z2 * y7))) + (vector3D10.getY() * ((z2 * x8) - (z7 * x2))) + (vector3D10.getZ() * ((x2 * y7) - (y2 * x8)));
                if (x7 == 0.0d) {
                    this.q0 = 1.0d;
                    this.q1 = 0.0d;
                    this.q2 = 0.0d;
                    this.q3 = 0.0d;
                    return;
                }
                vector3D6 = vector3D7;
                vector3D8 = vector3D10;
                vector3D5 = vector3D2;
            }
        }
        double sqrt3 = org.apache.commons.math.util.FastMath.sqrt(x7);
        double d5 = 1.0d / (sqrt3 + sqrt3);
        this.q1 = vector3D8.getX() * d5;
        this.q2 = vector3D8.getY() * d5;
        this.q3 = d5 * vector3D8.getZ();
        org.apache.commons.math.geometry.Vector3D vector3D11 = new org.apache.commons.math.geometry.Vector3D((vector3D5.getY() * this.q3) - (vector3D5.getZ() * this.q2), (vector3D5.getZ() * this.q1) - (vector3D5.getX() * this.q3), (vector3D5.getX() * this.q2) - (vector3D5.getY() * this.q1));
        double dotProduct7 = org.apache.commons.math.geometry.Vector3D.dotProduct(vector3D11, vector3D11);
        this.q0 = org.apache.commons.math.geometry.Vector3D.dotProduct(vector3D6, vector3D11) / (dotProduct7 + dotProduct7);
    }

    public Rotation(org.apache.commons.math.geometry.Vector3D vector3D, org.apache.commons.math.geometry.Vector3D vector3D2) {
        double norm = vector3D.getNorm() * vector3D2.getNorm();
        if (norm == 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_NORM_FOR_ROTATION_DEFINING_VECTOR, new java.lang.Object[0]);
        }
        double dotProduct = org.apache.commons.math.geometry.Vector3D.dotProduct(vector3D, vector3D2);
        if (dotProduct < (-0.999999999999998d) * norm) {
            org.apache.commons.math.geometry.Vector3D orthogonal = vector3D.orthogonal();
            this.q0 = 0.0d;
            this.q1 = -orthogonal.getX();
            this.q2 = -orthogonal.getY();
            this.q3 = -orthogonal.getZ();
            return;
        }
        this.q0 = org.apache.commons.math.util.FastMath.sqrt(((dotProduct / norm) + 1.0d) * 0.5d);
        double d = 1.0d / ((this.q0 * 2.0d) * norm);
        this.q1 = ((vector3D2.getY() * vector3D.getZ()) - (vector3D2.getZ() * vector3D.getY())) * d;
        this.q2 = ((vector3D2.getZ() * vector3D.getX()) - (vector3D2.getX() * vector3D.getZ())) * d;
        this.q3 = d * ((vector3D2.getX() * vector3D.getY()) - (vector3D2.getY() * vector3D.getX()));
    }

    public Rotation(org.apache.commons.math.geometry.RotationOrder rotationOrder, double d, double d2, double d3) {
        org.apache.commons.math.geometry.Rotation applyTo = new org.apache.commons.math.geometry.Rotation(rotationOrder.getA1(), d).applyTo(new org.apache.commons.math.geometry.Rotation(rotationOrder.getA2(), d2).applyTo(new org.apache.commons.math.geometry.Rotation(rotationOrder.getA3(), d3)));
        this.q0 = applyTo.q0;
        this.q1 = applyTo.q1;
        this.q2 = applyTo.q2;
        this.q3 = applyTo.q3;
    }

    public org.apache.commons.math.geometry.Rotation revert() {
        return new org.apache.commons.math.geometry.Rotation(-this.q0, this.q1, this.q2, this.q3, false);
    }

    public double getQ0() {
        return this.q0;
    }

    public double getQ1() {
        return this.q1;
    }

    public double getQ2() {
        return this.q2;
    }

    public double getQ3() {
        return this.q3;
    }

    public org.apache.commons.math.geometry.Vector3D getAxis() {
        double d = (this.q1 * this.q1) + (this.q2 * this.q2) + (this.q3 * this.q3);
        if (d == 0.0d) {
            return new org.apache.commons.math.geometry.Vector3D(1.0d, 0.0d, 0.0d);
        }
        if (this.q0 < 0.0d) {
            double sqrt = 1.0d / org.apache.commons.math.util.FastMath.sqrt(d);
            return new org.apache.commons.math.geometry.Vector3D(this.q1 * sqrt, this.q2 * sqrt, this.q3 * sqrt);
        }
        double sqrt2 = (-1.0d) / org.apache.commons.math.util.FastMath.sqrt(d);
        return new org.apache.commons.math.geometry.Vector3D(this.q1 * sqrt2, this.q2 * sqrt2, this.q3 * sqrt2);
    }

    public double getAngle() {
        if (this.q0 < -0.1d || this.q0 > 0.1d) {
            return org.apache.commons.math.util.FastMath.asin(org.apache.commons.math.util.FastMath.sqrt((this.q1 * this.q1) + (this.q2 * this.q2) + (this.q3 * this.q3))) * 2.0d;
        }
        if (this.q0 < 0.0d) {
            return org.apache.commons.math.util.FastMath.acos(-this.q0) * 2.0d;
        }
        return org.apache.commons.math.util.FastMath.acos(this.q0) * 2.0d;
    }

    public double[] getAngles(org.apache.commons.math.geometry.RotationOrder rotationOrder) throws org.apache.commons.math.geometry.CardanEulerSingularityException {
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.XYZ) {
            org.apache.commons.math.geometry.Vector3D applyTo = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_K);
            org.apache.commons.math.geometry.Vector3D applyInverseTo = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_I);
            if (applyInverseTo.getZ() < -0.9999999999d || applyInverseTo.getZ() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(true);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(-applyTo.getY(), applyTo.getZ()), org.apache.commons.math.util.FastMath.asin(applyInverseTo.getZ()), org.apache.commons.math.util.FastMath.atan2(-applyInverseTo.getY(), applyInverseTo.getX())};
        }
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.XZY) {
            org.apache.commons.math.geometry.Vector3D applyTo2 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_J);
            org.apache.commons.math.geometry.Vector3D applyInverseTo2 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_I);
            if (applyInverseTo2.getY() < -0.9999999999d || applyInverseTo2.getY() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(true);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(applyTo2.getZ(), applyTo2.getY()), -org.apache.commons.math.util.FastMath.asin(applyInverseTo2.getY()), org.apache.commons.math.util.FastMath.atan2(applyInverseTo2.getZ(), applyInverseTo2.getX())};
        }
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.YXZ) {
            org.apache.commons.math.geometry.Vector3D applyTo3 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_K);
            org.apache.commons.math.geometry.Vector3D applyInverseTo3 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_J);
            if (applyInverseTo3.getZ() < -0.9999999999d || applyInverseTo3.getZ() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(true);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(applyTo3.getX(), applyTo3.getZ()), -org.apache.commons.math.util.FastMath.asin(applyInverseTo3.getZ()), org.apache.commons.math.util.FastMath.atan2(applyInverseTo3.getX(), applyInverseTo3.getY())};
        }
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.YZX) {
            org.apache.commons.math.geometry.Vector3D applyTo4 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_I);
            org.apache.commons.math.geometry.Vector3D applyInverseTo4 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_J);
            if (applyInverseTo4.getX() < -0.9999999999d || applyInverseTo4.getX() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(true);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(-applyTo4.getZ(), applyTo4.getX()), org.apache.commons.math.util.FastMath.asin(applyInverseTo4.getX()), org.apache.commons.math.util.FastMath.atan2(-applyInverseTo4.getZ(), applyInverseTo4.getY())};
        }
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.ZXY) {
            org.apache.commons.math.geometry.Vector3D applyTo5 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_J);
            org.apache.commons.math.geometry.Vector3D applyInverseTo5 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_K);
            if (applyInverseTo5.getY() < -0.9999999999d || applyInverseTo5.getY() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(true);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(-applyTo5.getX(), applyTo5.getY()), org.apache.commons.math.util.FastMath.asin(applyInverseTo5.getY()), org.apache.commons.math.util.FastMath.atan2(-applyInverseTo5.getX(), applyInverseTo5.getZ())};
        }
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.ZYX) {
            org.apache.commons.math.geometry.Vector3D applyTo6 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_I);
            org.apache.commons.math.geometry.Vector3D applyInverseTo6 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_K);
            if (applyInverseTo6.getX() < -0.9999999999d || applyInverseTo6.getX() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(true);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(applyTo6.getY(), applyTo6.getX()), -org.apache.commons.math.util.FastMath.asin(applyInverseTo6.getX()), org.apache.commons.math.util.FastMath.atan2(applyInverseTo6.getY(), applyInverseTo6.getZ())};
        }
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.XYX) {
            org.apache.commons.math.geometry.Vector3D applyTo7 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_I);
            org.apache.commons.math.geometry.Vector3D applyInverseTo7 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_I);
            if (applyInverseTo7.getX() < -0.9999999999d || applyInverseTo7.getX() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(false);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(applyTo7.getY(), -applyTo7.getZ()), org.apache.commons.math.util.FastMath.acos(applyInverseTo7.getX()), org.apache.commons.math.util.FastMath.atan2(applyInverseTo7.getY(), applyInverseTo7.getZ())};
        }
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.XZX) {
            org.apache.commons.math.geometry.Vector3D applyTo8 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_I);
            org.apache.commons.math.geometry.Vector3D applyInverseTo8 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_I);
            if (applyInverseTo8.getX() < -0.9999999999d || applyInverseTo8.getX() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(false);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(applyTo8.getZ(), applyTo8.getY()), org.apache.commons.math.util.FastMath.acos(applyInverseTo8.getX()), org.apache.commons.math.util.FastMath.atan2(applyInverseTo8.getZ(), -applyInverseTo8.getY())};
        }
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.YXY) {
            org.apache.commons.math.geometry.Vector3D applyTo9 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_J);
            org.apache.commons.math.geometry.Vector3D applyInverseTo9 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_J);
            if (applyInverseTo9.getY() < -0.9999999999d || applyInverseTo9.getY() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(false);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(applyTo9.getX(), applyTo9.getZ()), org.apache.commons.math.util.FastMath.acos(applyInverseTo9.getY()), org.apache.commons.math.util.FastMath.atan2(applyInverseTo9.getX(), -applyInverseTo9.getZ())};
        }
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.YZY) {
            org.apache.commons.math.geometry.Vector3D applyTo10 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_J);
            org.apache.commons.math.geometry.Vector3D applyInverseTo10 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_J);
            if (applyInverseTo10.getY() < -0.9999999999d || applyInverseTo10.getY() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(false);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(applyTo10.getZ(), -applyTo10.getX()), org.apache.commons.math.util.FastMath.acos(applyInverseTo10.getY()), org.apache.commons.math.util.FastMath.atan2(applyInverseTo10.getZ(), applyInverseTo10.getX())};
        }
        if (rotationOrder == org.apache.commons.math.geometry.RotationOrder.ZXZ) {
            org.apache.commons.math.geometry.Vector3D applyTo11 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_K);
            org.apache.commons.math.geometry.Vector3D applyInverseTo11 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_K);
            if (applyInverseTo11.getZ() < -0.9999999999d || applyInverseTo11.getZ() > 0.9999999999d) {
                throw new org.apache.commons.math.geometry.CardanEulerSingularityException(false);
            }
            return new double[]{org.apache.commons.math.util.FastMath.atan2(applyTo11.getX(), -applyTo11.getY()), org.apache.commons.math.util.FastMath.acos(applyInverseTo11.getZ()), org.apache.commons.math.util.FastMath.atan2(applyInverseTo11.getX(), applyInverseTo11.getY())};
        }
        org.apache.commons.math.geometry.Vector3D applyTo12 = applyTo(org.apache.commons.math.geometry.Vector3D.PLUS_K);
        org.apache.commons.math.geometry.Vector3D applyInverseTo12 = applyInverseTo(org.apache.commons.math.geometry.Vector3D.PLUS_K);
        if (applyInverseTo12.getZ() < -0.9999999999d || applyInverseTo12.getZ() > 0.9999999999d) {
            throw new org.apache.commons.math.geometry.CardanEulerSingularityException(false);
        }
        return new double[]{org.apache.commons.math.util.FastMath.atan2(applyTo12.getY(), applyTo12.getX()), org.apache.commons.math.util.FastMath.acos(applyInverseTo12.getZ()), org.apache.commons.math.util.FastMath.atan2(applyInverseTo12.getY(), -applyInverseTo12.getX())};
    }

    public double[][] getMatrix() {
        double d = this.q0 * this.q0;
        double d2 = this.q0 * this.q1;
        double d3 = this.q0 * this.q2;
        double d4 = this.q0 * this.q3;
        double d5 = this.q1 * this.q1;
        double d6 = this.q1 * this.q2;
        double d7 = this.q1 * this.q3;
        double d8 = this.q2 * this.q2;
        double d9 = this.q2 * this.q3;
        double d10 = this.q3 * this.q3;
        double[][] dArr = {new double[3], new double[3], new double[3]};
        dArr[0][0] = ((d5 + d) * 2.0d) - 1.0d;
        dArr[1][0] = (d6 - d4) * 2.0d;
        dArr[2][0] = (d7 + d3) * 2.0d;
        dArr[0][1] = (d6 + d4) * 2.0d;
        dArr[1][1] = ((d + d8) * 2.0d) - 1.0d;
        dArr[2][1] = (d9 - d2) * 2.0d;
        dArr[0][2] = (d7 - d3) * 2.0d;
        dArr[1][2] = (d9 + d2) * 2.0d;
        dArr[2][2] = ((d + d10) * 2.0d) - 1.0d;
        return dArr;
    }

    public org.apache.commons.math.geometry.Vector3D applyTo(org.apache.commons.math.geometry.Vector3D vector3D) {
        double x = vector3D.getX();
        double y = vector3D.getY();
        double z = vector3D.getZ();
        double d = (this.q1 * x) + (this.q2 * y) + (this.q3 * z);
        return new org.apache.commons.math.geometry.Vector3D((((this.q0 * ((this.q0 * x) - ((this.q2 * z) - (this.q3 * y)))) + (this.q1 * d)) * 2.0d) - x, (((this.q0 * ((this.q0 * y) - ((this.q3 * x) - (this.q1 * z)))) + (this.q2 * d)) * 2.0d) - y, (((this.q0 * ((this.q0 * z) - ((this.q1 * y) - (this.q2 * x)))) + (d * this.q3)) * 2.0d) - z);
    }

    public org.apache.commons.math.geometry.Vector3D applyInverseTo(org.apache.commons.math.geometry.Vector3D vector3D) {
        double x = vector3D.getX();
        double y = vector3D.getY();
        double z = vector3D.getZ();
        double d = (this.q1 * x) + (this.q2 * y) + (this.q3 * z);
        double d2 = -this.q0;
        return new org.apache.commons.math.geometry.Vector3D((((((x * d2) - ((this.q2 * z) - (this.q3 * y))) * d2) + (this.q1 * d)) * 2.0d) - x, (((((y * d2) - ((this.q3 * x) - (this.q1 * z))) * d2) + (this.q2 * d)) * 2.0d) - y, (((d2 * ((z * d2) - ((this.q1 * y) - (this.q2 * x)))) + (d * this.q3)) * 2.0d) - z);
    }

    public org.apache.commons.math.geometry.Rotation applyTo(org.apache.commons.math.geometry.Rotation rotation) {
        return new org.apache.commons.math.geometry.Rotation((rotation.q0 * this.q0) - (((rotation.q1 * this.q1) + (rotation.q2 * this.q2)) + (rotation.q3 * this.q3)), (rotation.q1 * this.q0) + (rotation.q0 * this.q1) + ((rotation.q2 * this.q3) - (rotation.q3 * this.q2)), (rotation.q2 * this.q0) + (rotation.q0 * this.q2) + ((rotation.q3 * this.q1) - (rotation.q1 * this.q3)), (rotation.q3 * this.q0) + (rotation.q0 * this.q3) + ((rotation.q1 * this.q2) - (rotation.q2 * this.q1)), false);
    }

    public org.apache.commons.math.geometry.Rotation applyInverseTo(org.apache.commons.math.geometry.Rotation rotation) {
        return new org.apache.commons.math.geometry.Rotation(((-rotation.q0) * this.q0) - (((rotation.q1 * this.q1) + (rotation.q2 * this.q2)) + (rotation.q3 * this.q3)), ((-rotation.q1) * this.q0) + (rotation.q0 * this.q1) + ((rotation.q2 * this.q3) - (rotation.q3 * this.q2)), ((-rotation.q2) * this.q0) + (rotation.q0 * this.q2) + ((rotation.q3 * this.q1) - (rotation.q1 * this.q3)), ((-rotation.q3) * this.q0) + (rotation.q0 * this.q3) + ((rotation.q1 * this.q2) - (rotation.q2 * this.q1)), false);
    }

    private double[][] orthogonalizeMatrix(double[][] dArr, double d) throws org.apache.commons.math.geometry.NotARotationMatrixException {
        double[] dArr2 = dArr[0];
        int i = 1;
        double[] dArr3 = dArr[1];
        double[] dArr4 = dArr[2];
        double d2 = dArr2[0];
        double d3 = dArr2[1];
        double d4 = dArr2[2];
        double d5 = dArr3[0];
        double d6 = dArr3[1];
        double d7 = dArr3[2];
        double d8 = dArr4[0];
        double d9 = dArr4[1];
        double d10 = dArr4[2];
        double[][] dArr5 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, 3, 3);
        double[] dArr6 = dArr5[0];
        double[] dArr7 = dArr5[1];
        double[] dArr8 = dArr5[2];
        double d11 = 0.0d;
        double d12 = d10;
        double d13 = d9;
        double d14 = d8;
        double d15 = d7;
        double d16 = d6;
        double d17 = d5;
        double d18 = d4;
        double d19 = d3;
        double d20 = d2;
        int i2 = 0;
        while (true) {
            i2 += i;
            if (i2 < 11) {
                double d21 = (dArr2[0] * d20) + (dArr3[0] * d17) + (dArr4[0] * d14);
                double d22 = (dArr2[1] * d20) + (dArr3[1] * d17) + (dArr4[1] * d14);
                double d23 = (dArr2[2] * d20) + (dArr3[2] * d17) + (dArr4[2] * d14);
                double d24 = (dArr2[0] * d19) + (dArr3[0] * d16) + (dArr4[0] * d13);
                double d25 = (dArr2[1] * d19) + (dArr3[1] * d16) + (dArr4[1] * d13);
                double d26 = (dArr2[2] * d19) + (dArr3[2] * d16) + (dArr4[2] * d13);
                double d27 = (dArr2[0] * d18) + (dArr3[0] * d15) + (dArr4[0] * d12);
                double d28 = (dArr2[1] * d18) + (dArr3[1] * d15) + (dArr4[1] * d12);
                double d29 = (dArr2[2] * d18) + (dArr3[2] * d15) + (dArr4[2] * d12);
                dArr6[0] = d20 - (((((d20 * d21) + (d19 * d22)) + (d18 * d23)) - dArr2[0]) * 0.5d);
                dArr6[1] = d19 - (((((d20 * d24) + (d19 * d25)) + (d18 * d26)) - dArr2[1]) * 0.5d);
                dArr6[2] = d18 - (((((d20 * d27) + (d19 * d28)) + (d18 * d29)) - dArr2[2]) * 0.5d);
                dArr7[0] = d17 - (((((d17 * d21) + (d16 * d22)) + (d15 * d23)) - dArr3[0]) * 0.5d);
                dArr7[1] = d16 - (((((d17 * d24) + (d16 * d25)) + (d15 * d26)) - dArr3[1]) * 0.5d);
                dArr7[2] = d15 - (((((d17 * d27) + (d16 * d28)) + (d15 * d29)) - dArr3[2]) * 0.5d);
                dArr8[0] = d14 - (((((d21 * d14) + (d22 * d13)) + (d23 * d12)) - dArr4[0]) * 0.5d);
                dArr8[1] = d13 - (((((d24 * d14) + (d25 * d13)) + (d26 * d12)) - dArr4[1]) * 0.5d);
                dArr8[2] = d12 - (((((d14 * d27) + (d13 * d28)) + (d29 * d12)) - dArr4[2]) * 0.5d);
                double d30 = dArr6[0] - dArr2[0];
                double d31 = dArr6[1] - dArr2[1];
                double d32 = dArr6[2] - dArr2[2];
                double d33 = dArr7[0] - dArr3[0];
                double d34 = dArr7[1] - dArr3[1];
                double d35 = dArr7[2] - dArr3[2];
                double d36 = dArr8[0] - dArr4[0];
                double d37 = dArr8[1] - dArr4[1];
                double d38 = dArr8[2] - dArr4[2];
                double d39 = (d30 * d30) + (d31 * d31) + (d32 * d32) + (d33 * d33) + (d34 * d34) + (d35 * d35) + (d36 * d36) + (d37 * d37) + (d38 * d38);
                if (org.apache.commons.math.util.FastMath.abs(d39 - d11) <= d) {
                    return dArr5;
                }
                d20 = dArr6[0];
                double d40 = dArr6[1];
                double d41 = dArr6[2];
                double d42 = dArr7[0];
                double d43 = dArr7[1];
                i = 1;
                d19 = d40;
                d18 = d41;
                d17 = d42;
                d16 = d43;
                d15 = dArr7[2];
                d14 = dArr8[0];
                d13 = dArr8[1];
                d12 = dArr8[2];
                d11 = d39;
            } else {
                throw new org.apache.commons.math.geometry.NotARotationMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.UNABLE_TO_ORTHOGONOLIZE_MATRIX, java.lang.Integer.valueOf(i2 - 1));
            }
        }
    }

    public static double distance(org.apache.commons.math.geometry.Rotation rotation, org.apache.commons.math.geometry.Rotation rotation2) {
        return rotation.applyInverseTo(rotation2).getAngle();
    }
}
