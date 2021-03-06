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

import java.util.ArrayList;

import wblut.math.WB_Epsilon;
import wblut.math.WB_Math;

/**
 *
 */
public class WB_Line extends WB_Linear implements WB_Curve {
	/**
	 *
	 */
	private static final WB_GeometryFactory gf = WB_GeometryFactory.instance();

	/**
	 *
	 *
	 * @return
	 */
	public static final WB_Line X() {
		return new WB_Line(0, 0, 0, 1, 0, 0);
	}

	/**
	 *
	 *
	 * @return
	 */
	public static final WB_Line Y() {
		return new WB_Line(0, 0, 0, 0, 1, 0);
	}

	/**
	 *
	 *
	 * @return
	 */
	public static final WB_Line Z() {
		return new WB_Line(0, 0, 0, 0, 0, 1);
	}

	/**
	 *
	 */
	public WB_Line() {
		super();
	}

	/**
	 *
	 *
	 * @param o
	 * @param d
	 */
	public WB_Line(final WB_Coord o, final WB_Coord d) {
		super(o, d);
	}

	/**
	 *
	 *
	 * @param ox
	 * @param oy
	 * @param oz
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public WB_Line(final double ox, final double oy, final double oz, final double dx, final double dy,
			final double dz) {
		super(new WB_Point(ox, oy, oz), new WB_Vector(dx, dy, dz));
	}

	/**
	 * a.x+b.y+c=0
	 *
	 * @return a for a 2D line
	 */
	@Override
	public double a() {
		return -direction.yd();
	}

	/**
	 * a.x+b.y+c=0
	 *
	 * @return b for a 2D line
	 */
	@Override
	public double b() {
		return direction.xd();
	}

	/**
	 * a.x+b.y+c=0
	 *
	 * @return c for a 2D line
	 */
	@Override
	public double c() {
		return (origin.xd() * direction.yd()) - (origin.yd() * direction.xd());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Line: " + origin.toString() + " " + direction.toString();
	}

	/**
	 *
	 *
	 * @param p1
	 * @param p2
	 */
	public void setFromPoints(final WB_Coord p1, final WB_Coord p2) {
		super.set(p1, p2);
	}

	/**
	 *
	 *
	 * @param p
	 * @return
	 */
	public double getT(final WB_Coord p) {
		double t = Double.NaN;
		final WB_Coord proj = WB_GeometryOp.getClosestPoint2D(p, this);
		final double x = WB_Math.fastAbs(direction.xd());
		final double y = WB_Math.fastAbs(direction.yd());
		if (x >= y) {
			t = (proj.xd() - origin.xd()) / (direction.xd());
		} else {
			t = (proj.yd() - origin.yd()) / (direction.yd());
		}
		return t;
	}

	/**
	 *
	 *
	 * @param p
	 * @return
	 */
	public WB_Classification classifyPointToLine2D(final WB_Coord p) {
		final double dist = ((-direction.yd() * p.xd()) + (direction.xd() * p.yd()) + (origin.xd() * direction.yd()))
				- (origin.yd() * direction.xd());
		if (dist > WB_Epsilon.EPSILON) {
			return WB_Classification.FRONT;
		}
		if (dist < -WB_Epsilon.EPSILON) {
			return WB_Classification.BACK;
		}
		return WB_Classification.ON;
	}

	/**
	 *
	 *
	 * @param p
	 * @param L
	 * @return
	 */
	public static WB_Classification classifyPointToLine2D(final WB_Coord p, final WB_Line L) {
		final double dist = (L.a() * p.xd()) + (L.b() * p.yd()) + L.c();
		if (dist > WB_Epsilon.EPSILON) {
			return WB_Classification.FRONT;
		}
		if (dist < -WB_Epsilon.EPSILON) {
			return WB_Classification.BACK;
		}
		return WB_Classification.ON;
	}

	/**
	 *
	 *
	 * @param seg
	 * @return
	 */
	public WB_Classification classifySegmentToLine2D(final WB_Segment seg) {
		final WB_Classification a = classifyPointToLine2D(seg.getOrigin());
		final WB_Classification b = classifyPointToLine2D(seg.getEndpoint());
		if (a == WB_Classification.ON) {
			if (b == WB_Classification.ON) {
				return WB_Classification.ON;
			} else if (b == WB_Classification.FRONT) {
				return WB_Classification.FRONT;
			} else {
				return WB_Classification.BACK;
			}
		}
		if (b == WB_Classification.ON) {
			if (a == WB_Classification.FRONT) {
				return WB_Classification.FRONT;
			} else {
				return WB_Classification.BACK;
			}
		}
		if ((a == WB_Classification.FRONT) && (b == WB_Classification.BACK)) {
			return WB_Classification.CROSSING;
		}
		if ((a == WB_Classification.BACK) && (b == WB_Classification.FRONT)) {
			return WB_Classification.CROSSING;
		}
		if (a == WB_Classification.FRONT) {
			return WB_Classification.FRONT;
		}
		return WB_Classification.BACK;
	}

	/**
	 *
	 *
	 * @param P
	 * @return
	 */
	public WB_Classification classifyPolygonToLine2D(final WB_Polygon P) {
		int numFront = 0;
		int numBack = 0;
		for (int i = 0; i < P.numberOfShellPoints; i++) {
			if (classifyPointToLine2D(P.getPoint(i)) == WB_Classification.FRONT) {
				numFront++;
			} else if (classifyPointToLine2D(P.getPoint(i)) == WB_Classification.BACK) {
				numBack++;
			}
			if ((numFront > 0) && (numBack > 0)) {
				return WB_Classification.CROSSING;
			}
		}
		if (numFront > 0) {
			return WB_Classification.FRONT;
		}
		if (numBack > 0) {
			return WB_Classification.BACK;
		}
		return null;
	}

	/**
	 *
	 *
	 * @param C
	 * @param p
	 * @return
	 */
	public static WB_Line getLineTangentToCircleAtPoint(final WB_Circle C, final WB_Coord p) {
		final WB_Vector v = new WB_Vector(C.getCenter(), p);
		return new WB_Line(p, new WB_Point(-v.yd(), v.xd()));
	}

	/**
	 *
	 *
	 * @param C
	 * @param p
	 * @return
	 */
	public static ArrayList<WB_Line> getLinesTangentToCircleThroughPoint(final WB_Circle C, final WB_Coord p) {
		final ArrayList<WB_Line> result = new ArrayList<WB_Line>(2);
		final double dcp = WB_GeometryOp.getDistance2D(C.getCenter(), p);
		if (WB_Epsilon.isZero(dcp - C.getRadius())) {
			final WB_Vector u = new WB_Vector(C.getCenter(), p);
			result.add(new WB_Line(p, new WB_Point(-u.yd(), u.xd())));
		} else if (dcp < C.getRadius()) {
			return result;
		} else {
			final WB_Vector u = new WB_Vector(C.getCenter(), p);
			final double ux2 = u.xd() * u.xd();
			final double ux4 = ux2 * ux2;
			final double uy2 = u.yd() * u.yd();
			final double r2 = C.getRadius() * C.getRadius();
			final double r4 = r2 * r2;
			final double num = r2 * uy2;
			final double denom = ux2 + uy2;
			final double rad = Math.sqrt((-r4 * ux2) + (r2 * ux4) + (r2 * ux2 * uy2));
			result.add(new WB_Line(p,
					new WB_Point(-((r2 * u.yd()) + rad) / denom, (r2 - ((num + (u.yd() * rad)) / denom)) / u.xd())));
			result.add(new WB_Line(p,
					new WB_Point(-((r2 * u.yd()) - rad) / denom, (r2 - ((num - (u.yd() * rad)) / denom)) / u.xd())));
		}
		return result;
	}

	/**
	 *
	 *
	 * @param C0
	 * @param C1
	 * @return
	 */
	public static ArrayList<WB_Line> getLinesTangentTo2Circles(final WB_Circle C0, final WB_Circle C1) {
		final ArrayList<WB_Line> result = new ArrayList<WB_Line>(4);
		final WB_Point w = WB_Point.sub(C1.getCenter(), C0.getCenter());
		final double wlensqr = w.getSqLength3D();
		final double rsum = C0.getRadius() + C1.getRadius();
		if (wlensqr <= ((rsum * rsum) + WB_Epsilon.SQEPSILON)) {
			return result;
		}
		final double rdiff = C1.getRadius() - C0.getRadius();
		if (!WB_Epsilon.isZero(rdiff)) {
			final double r0sqr = C0.getRadius() * C0.getRadius();
			final double r1sqr = C1.getRadius() * C1.getRadius();
			final double c0 = -r0sqr;
			final double c1 = 2 * r0sqr;
			final double c2 = (C1.getRadius() * C1.getRadius()) - r0sqr;
			final double invc2 = 1.0 / c2;
			final double discr = Math.sqrt(WB_Math.fastAbs((c1 * c1) - (4 * c0 * c2)));
			double s, oms, a;
			s = -0.5 * (c1 + discr) * invc2;
			if (s >= 0.5) {
				a = Math.sqrt(WB_Math.fastAbs(wlensqr - (r0sqr / (s * s))));
			} else {
				oms = 1.0 - s;
				a = Math.sqrt(WB_Math.fastAbs(wlensqr - (r1sqr / (oms * oms))));
			}
			WB_Point[] dir = getDirections(w, a);
			WB_Point org = new WB_Point(C0.getCenter().xd() + (s * w.xd()), C0.getCenter().yd() + (s * w.yd()));
			result.add(new WB_Line(org, dir[0]));
			result.add(new WB_Line(org, dir[1]));
			s = -0.5 * (c1 - discr) * invc2;
			if (s >= 0.5) {
				a = Math.sqrt(WB_Math.fastAbs(wlensqr - (r0sqr / (s * s))));
			} else {
				oms = 1.0 - s;
				a = Math.sqrt(WB_Math.fastAbs(wlensqr - (r1sqr / (oms * oms))));
			}
			dir = getDirections(w, a);
			org = new WB_Point(C0.getCenter().xd() + (s * w.xd()), C0.getCenter().yd() + (s * w.yd()));
			result.add(new WB_Line(org, dir[0]));
			result.add(new WB_Line(org, dir[1]));
		} else {
			final WB_Point mid = WB_Point.add(C0.getCenter(), C1.getCenter()).mulSelf(0.5);
			final double a = Math.sqrt(WB_Math.fastAbs(wlensqr - (4 * C0.getRadius() * C0.getRadius())));
			final WB_Point[] dir = getDirections(w, a);
			result.add(new WB_Line(mid, dir[0]));
			result.add(new WB_Line(mid, dir[1]));
			final double invwlen = 1.0 / Math.sqrt(wlensqr);
			w.mulSelf(invwlen);
			result.add(new WB_Line(
					new WB_Point(mid.xd() + (C0.getRadius() * w.yd()), mid.yd() - (C0.getRadius() * w.xd())), w));
			result.add(new WB_Line(
					new WB_Point(mid.xd() - (C0.getRadius() * w.yd()), mid.yd() + (C0.getRadius() * w.xd())), w));
		}
		return result;
	}

	/**
	 *
	 *
	 * @param w
	 * @param a
	 * @return
	 */
	private static WB_Point[] getDirections(final WB_Coord w, final double a) {
		final WB_Point[] dir = new WB_Point[2];
		final double asqr = a * a;
		final double wxsqr = w.xd() * w.xd();
		final double wysqr = w.yd() * w.yd();
		final double c2 = wxsqr + wysqr;
		final double invc2 = 1.0 / c2;
		double c0, c1, discr, invwx;
		final double invwy;
		if (WB_Math.fastAbs(w.xd()) >= WB_Math.fastAbs(w.yd())) {
			c0 = asqr - wxsqr;
			c1 = -2 * a * w.yd();
			discr = Math.sqrt(WB_Math.fastAbs((c1 * c1) - (4 * c0 * c2)));
			invwx = 1.0 / w.xd();
			final double dir0y = -0.5 * (c1 + discr) * invc2;
			dir[0] = new WB_Point((a - (w.yd() * dir0y)) * invwx, dir0y);
			final double dir1y = -0.5 * (c1 - discr) * invc2;
			dir[1] = new WB_Point((a - (w.yd() * dir1y)) * invwx, dir1y);
		} else {
			c0 = asqr - wysqr;
			c1 = -2 * a * w.xd();
			discr = Math.sqrt(WB_Math.fastAbs((c1 * c1) - (4 * c0 * c2)));
			invwy = 1.0 / w.yd();
			final double dir0x = -0.5 * (c1 + discr) * invc2;
			dir[0] = new WB_Point(dir0x, (a - (w.xd() * dir0x)) * invwy);
			final double dir1x = -0.5 * (c1 - discr) * invc2;
			dir[1] = new WB_Point(dir1x, (a - (w.xd() * dir1x)) * invwy);
		}
		return dir;
	}

	/**
	 *
	 *
	 * @param L
	 * @param p
	 * @return
	 */
	public static WB_Line getPerpendicularLineThroughPoint(final WB_Line L, final WB_Coord p) {
		return new WB_Line(p, new WB_Point(-L.getDirection().yd(), L.getDirection().xd()));
	}

	/**
	 *
	 *
	 * @param L
	 * @param p
	 * @return
	 */
	public static WB_Line getParallelLineThroughPoint(final WB_Line L, final WB_Coord p) {
		return new WB_Line(p, L.getDirection());
	}

	/**
	 *
	 *
	 * @param p
	 * @param q
	 * @return
	 */
	public static WB_Line getBisector(final WB_Coord p, final WB_Coord q) {
		return new WB_Line(gf.createInterpolatedPoint(p, q, 0.5), new WB_Point(p.yd() - q.yd(), q.xd() - p.xd()));
	}

	/**
	 *
	 *
	 * @param L
	 * @param d
	 * @return
	 */
	public static WB_Line[] getParallelLines(final WB_Line L, final double d) {
		final WB_Line[] result = new WB_Line[2];
		result[0] = new WB_Line(new WB_Point(L.getOrigin().xd() - (d * L.getDirection().yd()),
				L.getOrigin().yd() + (d * L.getDirection().xd())), L.getDirection());
		result[1] = new WB_Line(new WB_Point(L.getOrigin().xd() + (d * L.getDirection().yd()),
				L.getOrigin().yd() - (d * L.getDirection().xd())), L.getDirection());
		return result;
	}

	/**
	 *
	 *
	 * @param L
	 * @param C
	 * @return
	 */
	public static WB_Line[] getPerpendicularLinesTangentToCircle(final WB_Line L, final WB_Circle C) {
		final WB_Line[] result = new WB_Line[2];
		result[0] = new WB_Line(
				new WB_Point(C.getCenter().xd() + (C.getRadius() * L.getDirection().xd()),
						C.getCenter().yd() + (C.getRadius() * L.getDirection().yd())),
				new WB_Point(-L.getDirection().yd(), L.getDirection().xd()));
		result[1] = new WB_Line(
				new WB_Point(C.getCenter().xd() - (C.getRadius() * L.getDirection().xd()),
						C.getCenter().yd() - (C.getRadius() * L.getDirection().yd())),
				new WB_Point(-L.getDirection().yd(), L.getDirection().xd()));
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_Curve#curvePoint(double)
	 */
	@Override
	public WB_Point curvePoint(final double u) {
		return this.getPointOnLine(u);
	}

	/* (non-Javadoc)
	 * @see wblut.geom.WB_Curve#curveDirection(double)
	 */
	@Override
	public WB_Vector curveDirection(final double u) {

		return new WB_Vector(direction);
	}

	/* (non-Javadoc)
	 * @see wblut.geom.WB_Curve#curveDerivative(double)
	 */
	@Override
	public WB_Vector curveDerivative(final double u) {
		return new WB_Vector(direction);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_Curve#loweru()
	 */
	@Override
	public double getLowerU() {
		return Double.NEGATIVE_INFINITY;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wblut.geom.WB_Curve#upperu()
	 */
	@Override
	public double getUpperU() {
		return Double.POSITIVE_INFINITY;
	}
}
