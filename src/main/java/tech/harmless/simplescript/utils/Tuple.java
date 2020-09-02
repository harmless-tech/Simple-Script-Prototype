package tech.harmless.simplescript.utils;

/**
 * A tuple type that allows for the holding of two data types.
 *
 * @param <X>
 * @param <Y>
 */
public class Tuple<X, Y> {

    public X x;
    public Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    //TODO More functions??
}
