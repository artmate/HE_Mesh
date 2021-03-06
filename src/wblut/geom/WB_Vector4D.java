/*
 * This file is part of HE_Mesh, a library for creating and manipulating meshes.
 * It is dedicated to the public domain. To the extent possible under law,
 * I , Frederik Vanhoutte, have waived all copyright and related or neighboring
 * rights.
 *
 * This work is published from Belgium. (http://creativecommons.org/publicdomain/zero/1.0/)
 *
 */
package wblut.geom;

import wblut.math.WB_Epsilon;
import wblut.math.WB_M33;
import wblut.math.WB_Math;

/**
 *
 */
public class WB_Vector4D extends WB_SimpleVector4D implements WB_MutableCoordinateMath {
	/**
	 *
	 *
	 * @return
	 */
	public static WB_Coord X() {
		return new WB_Vector4D(1, 0, 0, 0);
	}

	/**
	 *
	 *
	 * @return
	 */
	public static WB_Coord Y() {
		return new WB_Vector4D(0, 1, 0, 0);
	}

	/**
	 *
	 *
	 * @return
	 */
	public static WB_Coord Z() {
		return new WB_Vector4D(0, 0, 1, 0);
	}

	/**
	 *
	 *
	 * @return
	 */
	public static WB_Coord ZERO() {
		return new WB_Vector4D(0, 0, 0, 0);
	}

	/**
	 *
	 */
	public WB_Vector4D() {
		super();
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 */
	public WB_Vector4D(final double x, final double y) {
		super(x, y);
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public WB_Vector4D(final double x, final double y, final double z) {
		super(x, y, z);
	}

	/**
	 *
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public WB_Vector4D(final double x, final double y, final double z, final double w) {
		super(x, y, z, w);
	}

	/**
	 *
	 *
	 * @param x
	 */
	public WB_Vector4D(final double[] x) {
		super(x);
	}

	/**
	 *
	 *
	 * @param v
	 */
	public WB_Vector4D(final WB_Coord v) {
		super(v);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_MutableCoordinateMath#addMulSelf(double, double,
	 * double, double)
	 */
	@Override
	public WB_Vector4D addMulSelf(final double f, final double... x) {
		set(xd() + f * x[0], yd() + f * x[1], zd() + f * x[2], wd() + f * x[3]);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_MutableCoordinateMath#addMulSelf(double,
	 * wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_Vector4D addMulSelf(final double f, final WB_Coord p) {
		set(xd() + f * p.xd(), yd() + f * p.yd(), zd() + f * p.zd(), wd() + f * p.wd());
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_MutableCoordinateMath#addSelf(double, double, double)
	 */
	@Override
	public WB_Vector4D addSelf(final double... x) {
		set(xd() + x[0], yd() + x[1], zd() + x[2], wd() + x[3]);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * wblut.geom.WB_MutableCoordinateMath#addSelf(wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_Vector4D addSelf(final WB_Coord p) {
		set(xd() + p.xd(), yd() + p.yd(), zd() + p.zd(), wd() + p.wd());
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * wblut.geom.WB_MutableCoordinateMath#crossSelf(wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_Vector4D crossSelf(final WB_Coord p) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_MutableCoordinateMath#divSelf(double)
	 */
	@Override
	public WB_Vector4D divSelf(final double f) {
		return mulSelf(1.0 / f);
	}

	/**
	 *
	 */
	public void invert() {
		mulSelf(-1);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_MutableCoordinateMath#mulAddMulSelf(double, double,
	 * wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_Vector4D mulAddMulSelf(final double f, final double g, final WB_Coord p) {
		set(f * xd() + g * p.xd(), f * yd() + g * p.yd(), f * zd() + g * p.zd(), f * wd() + g * p.wd());
		return this;
	}

	@Override
	public WB_Vector4D mulAddMulSelf(final double f, final double g, final double... x) {
		set(f * this.xd() + g * x[0], f * this.yd() + g * x[1], f * this.zd() + g * x[2], f * this.wd() + g * x[3]);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_MutableCoordinateMath#mulSelf(double)
	 */
	@Override
	public WB_Vector4D mulSelf(final double f) {
		set(f * xd(), f * yd(), f * zd(), f * wd());
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_MutableCoordinateMath#normalizeSelf()
	 */
	@Override
	public double normalizeSelf() {
		final double d = getLength4D();
		if (WB_Epsilon.isZero(d)) {
			set(0, 0, 0, 0);
		} else {
			set(xd() / d, yd() / d, zd() / d, wd() / d);
		}
		return d;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_MutableCoordinateMath#subSelf(double, double, double)
	 */
	@Override
	public WB_Vector4D subSelf(final double... x) {
		set(xd() - x[0], yd() - x[1], zd() - x[2], wd() - x[3]);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * wblut.geom.WB_MutableCoordinateMath#subSelf(wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_Vector4D subSelf(final WB_Coord v) {
		set(xd() - v.xd(), yd() - v.yd(), zd() - v.zd(), wd() - v.wd());
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_MutableCoordinateMath#trimSelf(double)
	 */
	@Override
	public WB_Vector4D trimSelf(final double d) {
		if (getSqLength4D() > d * d) {
			normalizeSelf();
			mulSelf(d);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#absDot(wblut.geom.WB_Coordinate)
	 */
	@Override
	public double absDot(final WB_Coord p) {
		return WB_Math.fastAbs(WB_GeometryOp.dot4D(xd(), yd(), zd(), wd(), p.xd(), p.yd(), p.zd(), p.wd()));
	}

	/**
	 *
	 *
	 * @param p
	 * @param q
	 * @return
	 */
	public static double absDot(final WB_Coord p, final WB_Coord q) {
		return WB_Math.fastAbs(WB_GeometryOp.dot4D(p.xd(), p.yd(), p.zd(), p.wd(), q.xd(), q.yd(), q.zd(), q.wd()));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#absDot2D(wblut.geom.WB_Coordinate)
	 */
	@Override
	public double absDot2D(final WB_Coord p) {
		return WB_Math.fastAbs(WB_GeometryOp.dot2D(xd(), yd(), p.xd(), p.yd()));
	}

	/**
	 *
	 *
	 * @param p
	 * @param q
	 * @return
	 */
	public static double absDot2D(final WB_Coord p, final WB_Coord q) {
		return WB_Math.fastAbs(WB_GeometryOp.dot2D(p.xd(), p.yd(), q.xd(), q.yd()));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#add(double, double, double)
	 */
	@Override
	public WB_Vector4D add(final double... x) {
		return new WB_Vector4D(this.xd() + x[0], this.yd() + x[1], this.zd() + x[2], this.wd() + x[3]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#addInto(double, double, double,
	 * wblut.geom.WB_MutableCoordinate)
	 */
	@Override
	public void addInto(final WB_MutableCoord result, final double... x) {
		result.set(this.xd() + x[0], this.yd() + x[1], this.zd() + x[2], this.wd() + x[3]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#add(wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_Vector4D add(final WB_Coord p) {
		return new WB_Vector4D(xd() + p.xd(), yd() + p.yd(), zd() + p.zd(), wd() + p.wd());
	}

	/**
	 *
	 *
	 * @param p
	 * @param q
	 * @return
	 */
	public static WB_Vector4D add(final WB_Coord p, final WB_Coord q) {
		return new WB_Vector4D(q.xd() + p.xd(), q.yd() + p.yd(), q.zd() + p.zd(), q.wd() + p.wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#addInto(wblut.geom.WB_Coordinate,
	 * wblut.geom.WB_MutableCoordinate)
	 */
	@Override
	public void addInto(final WB_MutableCoord result, final WB_Coord p) {
		result.set(xd() + p.xd(), yd() + p.yd(), zd() + p.zd(), wd() + p.wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#addMul(double, double, double, double)
	 */
	@Override
	public WB_Vector4D addMul(final double f, final double... x) {
		return new WB_Vector4D(this.xd() + f * x[0], this.yd() + f * x[1], this.zd() + f * x[2], this.wd() + f * x[3]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#addMulInto(double, double, double,
	 * double, wblut.geom.WB_MutableCoordinate)
	 */
	@Override
	public void addMulInto(final WB_MutableCoord result, final double f, final double... x) {
		result.set(this.xd() + f * x[0], this.yd() + f * x[1], this.zd() + f * x[2], this.wd() + f * x[3]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#addMul(double,
	 * wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_Vector4D addMul(final double f, final WB_Coord p) {
		return new WB_Vector4D(xd() + f * p.xd(), yd() + f * p.yd(), zd() + f * p.zd(), wd() + f * p.wd());
	}

	/**
	 *
	 *
	 * @param p
	 * @param f
	 * @param q
	 * @return
	 */
	public static WB_Vector4D addMul(final WB_Coord p, final double f, final WB_Coord q) {
		return new WB_Vector4D(p.xd() + f * q.xd(), p.yd() + f * q.yd(), p.zd() + f * q.zd(), p.wd() + f * q.wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#addMulInto(double,
	 * wblut.geom.WB_Coordinate, wblut.geom.WB_MutableCoordinate)
	 */
	@Override
	public void addMulInto(final WB_MutableCoord result, final double f, final WB_Coord p) {
		result.set(xd() + f * p.xd(), yd() + f * p.yd(), zd() + f * p.zd(), wd() + f * p.wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_SimpleVector#compareTo(wblut.geom.WB_Coordinate)
	 */
	@Override
	public int compareTo(final WB_Coord p) {
		int cmp = Double.compare(xd(), p.xd());
		if (cmp != 0) {
			return cmp;
		}
		cmp = Double.compare(yd(), p.yd());
		if (cmp != 0) {
			return cmp;
		}
		cmp = Double.compare(zd(), p.zd());
		if (cmp != 0) {
			return cmp;
		}
		return Double.compare(wd(), p.wd());
	}

	/**
	 *
	 *
	 * @param p
	 * @return
	 */
	public int compareToY1st(final WB_Coord p) {
		int cmp = Double.compare(yd(), p.yd());
		if (cmp != 0) {
			return cmp;
		}
		cmp = Double.compare(xd(), p.xd());
		if (cmp != 0) {
			return cmp;
		}
		cmp = Double.compare(zd(), p.zd());
		if (cmp != 0) {
			return cmp;
		}
		return Double.compare(wd(), p.wd());
	}

	/**
	 *
	 *
	 * @return
	 */
	public double[] coords() {
		return new double[] { xd(), yd(), zd(), wd() };
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#cross(wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_Vector4D cross(final WB_Coord p) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#crossInto(wblut.geom.WB_Coordinate,
	 * wblut.geom.WB_MutableCoordinate)
	 */
	@Override
	public void crossInto(final WB_MutableCoord result, final WB_Coord p) {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#div(double)
	 */
	@Override
	public WB_Vector4D div(final double f) {
		return mul(1.0 / f);
	}

	/**
	 *
	 *
	 * @param p
	 * @param f
	 * @return
	 */
	public static WB_Vector4D div(final WB_Coord p, final double f) {
		return WB_Vector4D.mul(p, 1.0 / f);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#divInto(double,
	 * wblut.geom.WB_MutableCoordinate)
	 */
	@Override
	public void divInto(final WB_MutableCoord result, final double f) {
		mulInto(result, 1.0 / f);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#dot(wblut.geom.WB_Coordinate)
	 */
	@Override
	public double dot(final WB_Coord p) {
		return WB_GeometryOp.dot4D(xd(), yd(), zd(), wd(), p.xd(), p.yd(), p.zd(), p.wd());
	}

	/**
	 *
	 *
	 * @param p
	 * @param q
	 * @return
	 */
	public static double dot(final WB_Coord p, final WB_Coord q) {
		return WB_GeometryOp.dot4D(p.xd(), p.yd(), p.zd(), p.wd(), q.xd(), q.yd(), q.zd(), q.wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#dot2D(wblut.geom.WB_Coordinate)
	 */
	@Override
	public double dot2D(final WB_Coord p) {
		return WB_GeometryOp.dot2D(xd(), yd(), p.xd(), p.yd());
	}

	/**
	 *
	 *
	 * @param p
	 * @param q
	 * @return
	 */
	public static double dot2D(final WB_Coord p, final WB_Coord q) {
		return WB_GeometryOp.dot2D(p.xd(), p.yd(), q.xd(), q.yd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (!(o instanceof WB_Vector4D)) {
			return false;
		}
		final WB_Vector4D p = (WB_Vector4D) o;
		if (!WB_Epsilon.isEqualAbs(xd(), p.xd())) {
			return false;
		}
		if (!WB_Epsilon.isEqualAbs(yd(), p.yd())) {
			return false;
		}
		if (!WB_Epsilon.isEqualAbs(zd(), p.zd())) {
			return false;
		}
		if (!WB_Epsilon.isEqualAbs(wd(), p.wd())) {
			return false;
		}
		return true;
	}

	/**
	 *
	 *
	 * @return
	 */
	public WB_Vector4D get() {
		return new WB_Vector4D(xd(), yd(), zd(), wd());
	}

	/**
	 *
	 *
	 * @param p
	 * @return
	 */
	public double getDistance3D(final WB_Coord p) {
		return WB_GeometryOp.getDistance3D(xd(), yd(), zd(), p.xd(), p.yd(), p.zd());
	}

	/**
	 *
	 *
	 * @param q
	 * @param p
	 * @return
	 */
	public static double getDistance3D(final WB_Coord q, final WB_Coord p) {
		return WB_GeometryOp.getDistance3D(q.xd(), q.yd(), q.zd(), p.xd(), p.yd(), p.zd());
	}

	/**
	 *
	 *
	 * @return
	 */
	public double getLength3D() {
		return WB_GeometryOp.getLength3D(xd(), yd(), zd());
	}

	/**
	 *
	 *
	 * @param p
	 * @return
	 */
	public static double getLength3D(final WB_Coord p) {
		return WB_GeometryOp.getLength3D(p.xd(), p.yd(), p.zd());
	}

	/**
	 *
	 *
	 * @param p
	 * @return
	 */
	public double getSqDistance3D(final WB_Coord p) {
		return WB_GeometryOp.getSqDistance3D(xd(), yd(), zd(), p.xd(), p.yd(), p.zd());
	}

	/**
	 *
	 *
	 * @param q
	 * @param p
	 * @return
	 */
	public static double getSqDistance3D(final WB_Coord q, final WB_Coord p) {
		return WB_GeometryOp.getSqDistance3D(q.xd(), q.yd(), q.zd(), p.xd(), p.yd(), p.zd());
	}

	/**
	 *
	 *
	 * @return
	 */
	public double getSqLength3D() {
		return WB_GeometryOp.getSqLength3D(xd(), yd(), zd());
	}

	/**
	 *
	 *
	 * @param v
	 * @return
	 */
	public static double getSqLength3D(final WB_Coord v) {
		return WB_GeometryOp.getSqLength3D(v.xd(), v.yd(), v.zd());
	}

	/**
	 *
	 *
	 * @param p
	 * @return
	 */
	public double getDistance4D(final WB_Coord p) {
		return WB_GeometryOp.getDistance4D(xd(), yd(), zd(), wd(), p.xd(), p.yd(), p.zd(), p.wd());
	}

	/**
	 *
	 *
	 * @param q
	 * @param p
	 * @return
	 */
	public static double getDistance4D(final WB_Coord q, final WB_Coord p) {
		return WB_GeometryOp.getDistance4D(q.xd(), q.yd(), q.zd(), q.wd(), p.xd(), p.yd(), p.zd(), p.wd());
	}

	/**
	 *
	 *
	 * @return
	 */
	public double getLength4D() {
		return WB_GeometryOp.getLength4D(xd(), yd(), zd(), wd());
	}

	/**
	 *
	 *
	 * @param p
	 * @return
	 */
	public static double getLength4D(final WB_Coord p) {
		return WB_GeometryOp.getLength4D(p.xd(), p.yd(), p.zd(), p.wd());
	}

	/**
	 *
	 *
	 * @param p
	 * @return
	 */
	public double getSqDistance4D(final WB_Coord p) {
		return WB_GeometryOp.getSqDistance4D(xd(), yd(), zd(), wd(), p.xd(), p.yd(), p.zd(), p.wd());
	}

	/**
	 *
	 *
	 * @param q
	 * @param p
	 * @return
	 */
	public static double getSqDistance4D(final WB_Coord q, final WB_Coord p) {
		return WB_GeometryOp.getSqDistance4D(q.xd(), q.yd(), q.zd(), q.wd(), p.xd(), p.yd(), p.zd(), p.wd());
	}

	/**
	 *
	 *
	 * @return
	 */
	public double getSqLength4D() {
		return WB_GeometryOp.getSqLength4D(xd(), yd(), zd(), wd());
	}

	/**
	 *
	 *
	 * @param v
	 * @return
	 */
	public static double getSqLength4D(final WB_Coord v) {
		return WB_GeometryOp.getSqLength4D(v.xd(), v.yd(), v.zd(), v.wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return WB_HashCode.calculateHashCode(xd(), yd(), zd(), wd());
	}

	/**
	 *
	 *
	 * @return
	 */
	public boolean isZero() {
		return WB_GeometryOp.isZero4D(xd(), yd(), zd(), wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#mul(double)
	 */
	@Override
	public WB_Vector4D mul(final double f) {
		return new WB_Vector4D(xd() * f, yd() * f, zd() * f, wd() * f);
	}

	/**
	 *
	 *
	 * @param p
	 * @param f
	 * @return
	 */
	public static WB_Vector4D mul(final WB_Coord p, final double f) {
		return new WB_Vector4D(p.xd() * f, p.yd() * f, p.zd() * f, p.wd() * f);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#mulInto(double,
	 * wblut.geom.WB_MutableCoordinate)
	 */
	@Override
	public void mulInto(final WB_MutableCoord result, final double f) {
		result.set(f * xd(), f * yd(), f * zd(), f * wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#mulAddMul(double, double,
	 * wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_Vector4D mulAddMul(final double f, final double g, final WB_Coord p) {
		return new WB_Vector4D(f * xd() + g * p.xd(), f * yd() + g * p.yd(), f * zd() + g * p.zd(),
				f * wd() + g * p.wd());
	}

	/**
	 *
	 *
	 * @param f
	 * @param p
	 * @param g
	 * @param q
	 * @return
	 */
	public static WB_Vector4D mulAddMul(final double f, final WB_Coord p, final double g, final WB_Coord q) {
		return new WB_Vector4D(f * p.xd() + g * q.xd(), f * p.yd() + g * q.yd(), f * p.zd() + g * q.zd(),
				f * p.wd() + g * q.wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#mulAddMulInto(double, double,
	 * wblut.geom.WB_Coordinate, wblut.geom.WB_MutableCoordinate)
	 */
	@Override
	public void mulAddMulInto(final WB_MutableCoord result, final double f, final double g, final WB_Coord p) {
		result.set(f * xd() + g * p.xd(), f * yd() + g * p.yd(), f * zd() + g * p.zd(), f * wd() + g * p.wd());
	}

	/**
	 *
	 *
	 * @param otherXYZW
	 * @return
	 */
	public boolean smallerThan(final WB_Coord otherXYZW) {
		int _tmp = WB_Epsilon.compareAbs(xd(), otherXYZW.xd());
		if (_tmp != 0) {
			return _tmp < 0;
		}
		_tmp = WB_Epsilon.compareAbs(yd(), otherXYZW.yd());
		if (_tmp != 0) {
			return _tmp < 0;
		}
		_tmp = WB_Epsilon.compareAbs(zd(), otherXYZW.zd());
		if (_tmp != 0) {
			return _tmp < 0;
		}
		_tmp = WB_Epsilon.compareAbs(wd(), otherXYZW.wd());
		return _tmp < 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#sub(double[])
	 */
	@Override
	public WB_Vector4D sub(final double... x) {
		return new WB_Vector4D(this.xd() - x[0], this.yd() - x[1], this.zd() - x[2], this.wd() - x[3]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#subInto(wblut.geom.WB_MutableCoord,
	 * double[])
	 */
	@Override
	public void subInto(final WB_MutableCoord result, final double... x) {
		result.set(this.xd() - x[0], this.yd() - x[1], this.zd() - x[2], this.wd() - x[3]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#sub(wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_Vector4D sub(final WB_Coord p) {
		return new WB_Vector4D(this.xd() - p.xd(), this.yd() - p.yd(), this.zd() - p.zd(), this.wd() - p.wd());
	}

	/**
	 *
	 *
	 * @param p
	 * @param q
	 * @return
	 */
	public static WB_Vector4D sub(final WB_Coord p, final WB_Coord q) {
		return new WB_Vector4D(p.xd() - q.xd(), p.yd() - q.yd(), p.zd() - q.zd(), p.wd() - q.wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#subInto(wblut.geom.WB_Coordinate,
	 * wblut.geom.WB_MutableCoordinate)
	 */
	@Override
	public void subInto(final WB_MutableCoord result, final WB_Coord p) {
		result.set(this.xd() - p.xd(), this.yd() - p.yd(), this.zd() - p.zd(), this.wd() - p.wd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#tensor(wblut.geom.WB_Coordinate)
	 */
	@Override
	public WB_M33 tensor(final WB_Coord v) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WB_Vector4D [x=" + xd() + ", y=" + yd() + ", z=" + zd() + ", w=" + wd() + "]";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#mulAddMul(double, double, double[])
	 */
	@Override
	public WB_Vector4D mulAddMul(final double f, final double g, final double... x) {
		return new WB_Vector4D(f * this.xd() + g * x[0], f * this.yd() + g * x[1], f * this.zd() + g * x[2],
				f * this.wd() + g * x[3]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * wblut.geom.WB_CoordinateMath#mulAddMulInto(wblut.geom.WB_MutableCoord,
	 * double, double, double[])
	 */
	@Override
	public void mulAddMulInto(final WB_MutableCoord result, final double f, final double g, final double... x) {
		result.set(f * this.xd() + g * x[0], f * this.yd() + g * x[1], f * this.zd() + g * x[2],
				f * this.wd() + g * x[3]);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_CoordinateMath#scalarTriple(wblut.geom.WB_Coord,
	 * wblut.geom.WB_Coord)
	 */
	@Override
	public double scalarTriple(final WB_Coord v, final WB_Coord w) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 *
	 *
	 * @param theta
	 * @return
	 */
	public WB_Vector4D rotateXW(final double theta) {
		set(xd() * Math.cos(theta) + wd() * Math.sin(theta), yd(), zd(),
				xd() * -Math.sin(theta) + wd() * Math.cos(theta));
		return this;
	}

	/**
	 *
	 *
	 * @param theta
	 * @return
	 */
	public WB_Vector4D rotateXY(final double theta) {
		set(xd() * Math.cos(theta) + yd() * -Math.sin(theta), xd() * Math.sin(theta) + yd() * Math.cos(theta), zd(),
				wd());
		return this;
	}

	/**
	 *
	 *
	 * @param theta
	 * @return
	 */
	public WB_Vector4D rotateXZ(final double theta) {
		set(xd() * Math.cos(theta) + zd() * -Math.sin(theta), yd(), xd() * Math.sin(theta) + zd() * Math.cos(theta),
				wd());
		return this;
	}

	/**
	 *
	 *
	 * @param theta
	 * @return
	 */
	public WB_Vector4D rotateYW(final double theta) {
		set(xd(), yd() * Math.cos(theta) + wd() * -Math.sin(theta), zd(),
				yd() * Math.sin(theta) + wd() * Math.cos(theta));
		return this;
	}

	/**
	 *
	 *
	 * @param theta
	 * @return
	 */
	public WB_Vector4D rotateYZ(final double theta) {
		set(xd(), yd() * Math.cos(theta) + zd() * Math.sin(theta), yd() * -Math.sin(theta) + zd() * Math.cos(theta),
				wd());
		return this;
	}

	/**
	 *
	 *
	 * @param theta
	 * @return
	 */
	public WB_Vector4D rotateZW(final double theta) {
		set(xd(), yd(), zd() * Math.cos(theta) + wd() * -Math.sin(theta),
				zd() * Math.sin(theta) + wd() * Math.cos(theta));
		return this;
	}

	/**
	 *
	 *
	 * @param p0
	 * @param p1
	 * @param t
	 * @return
	 */
	public static WB_Vector4D interpolate(final WB_Coord p0, final WB_Coord p1, final double t) {
		return new WB_Vector4D(p0.xd() + t * (p1.xd() - p0.xd()), p0.yd() + t * (p1.yd() - p0.yd()),
				p0.zd() + t * (p1.zd() - p0.zd()), p0.wd() + t * (p1.wd() - p0.wd()));
	}
}
