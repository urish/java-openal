package org.urish.openal;

/**
 * A tuple of 3 floats, used for specifying coordinates in the OpenAL System
 * 
 * @author Uri Shaked
 */
public class Tuple3F {
	public final float v1;
	public final float v2;
	public final float v3;

	public Tuple3F(float v1, float v2, float v3) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof Tuple3F)) {
			return false;
		}
		return (((Tuple3F) other).v1 == v1) && (((Tuple3F) other).v2 == v2) && (((Tuple3F) other).v3 == v3);
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + Float.floatToIntBits(v1);
		hash = hash * 31 + Float.floatToIntBits(v2);
		hash = hash * 13 + Float.floatToIntBits(v3);
		return hash;
	}

	@Override
	public String toString() {
		return "Tuple3F[" + v1 + "," + v2 + "," + v3 + "]";
	}
}
