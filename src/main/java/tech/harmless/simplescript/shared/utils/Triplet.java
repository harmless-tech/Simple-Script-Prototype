package tech.harmless.simplescript.shared.utils;

//TODO Move to shared??
/**
 * A triplet type that allows for the holding of three data types.
 *
 * @param <X>
 * @param <Y>
 * @param <Z>
 */
public class Triplet<X, Y, Z> {

    public X x;
    public Y y;
    public Z z;

    public Triplet(X x, Y y, Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //TODO More functions??
}
