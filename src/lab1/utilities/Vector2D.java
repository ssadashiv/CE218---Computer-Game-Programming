package lab1.utilities;

/**
 * Created by el16035 on 23/01/2018.
 */
public final class Vector2D {
    public double x, y;


    // constructor for zero vector
    public Vector2D() {
        x = y = 0;
    }

    // constructor for vector with given coordinates
    public Vector2D(double x, double y) {
        set(x, y);
    }

    // constructor that copies the argument vector
    public Vector2D(Vector2D v) {
        set(v);
    }

    // set coordinates
    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;

    }

    // set coordinates based on argument vector
    public Vector2D set(Vector2D v) {
        x = v.x;
        y = v.y;
        return this;
    }

    // compare for equality (note Object type argument)
    public boolean equals(Object o) {
        if (o instanceof Vector2D) {
            Vector2D v = (Vector2D) o;
            return x == v.x && y == v.y;
        } else {
            return false;
        }
    }

    // String for displaying vector as text
    public String toString() {
        return "(" +x+", "+y+")";
    }

    //  magnitude (= "length") of this vector
    public double mag() {
        return Math.hypot(x, y);
    }

    // angle between vector and horizontal axis in radians in range [-PI,PI]
    // can be calculated using Math.atan2
    public double angle() {
        return Math.atan2(y, x);
    }

    // angle between this vector and another vector in range [-PI,PI]
    public double angle(Vector2D other) {

        double result = other.angle() - angle();
        if (result < -Math.PI)
            result += 2 * Math.PI;
        if (result > Math.PI)
            result -= 2 * Math.PI;

        return result;

    }

    // add argument vector
    public Vector2D add(Vector2D v) {
        x += v.x;
        y += v.y;
        return this;
    }

    // add values to coordinates
    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    // weighted add - surprisingly useful
    public Vector2D addScaled(Vector2D v, double fac) {
        this.x += v.x * fac;
        this.y += v.y * fac;
        return this;
    }

    // subtract argument vector
    public Vector2D subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    // subtract values from coordinates
    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    // multiply with factor
    public Vector2D mult(double fac) {
        x *= fac;
        y *= fac;
        return this;
    }

    // rotate by angle given in radians
    public Vector2D rotate(double angle) {
        double nx = (this.x * Math.cos(angle)) - (this.y * Math.sin(angle));
        double ny = (this.x * Math.sin(angle)) + (this.y * Math.cos(angle));

        this.x = nx;
        this.y = ny;
        return this;
    }

    // "dot product" ("scalar product") with argument vector
    public double dot(Vector2D v) {
        System.out.println("og: "+ (this.x * v.x) + (v.y *this.y));
        return (this.x * v.x) + (v.y *this.y);
    }

    // distance to argument vector
    public double dist(Vector2D v) {
        Vector2D diffVector = new Vector2D(v.x - this.x, v.y - this.y);
        return diffVector.mag();
    }

    // normalise vector so that magnitude becomes 1
    public Vector2D normalise() {
        double m = mag();

        if (m == 0){
            x = y = 0;
        }else{
            x = x/m;
            y = y/m;
        }

        return this;
    }

    // wrap-around operation, assumes w> 0 and h>0
    // remember to manage negative values of the coordinates
    public Vector2D wrap(double w, double h) {

        x = (x+w) % w;
        y = (y+h) % h;

        return this;
    }

    // construct vector with given polar coordinates
    public static Vector2D polar(double angle, double mag) {

        return new Vector2D(mag* Math.cos(angle), mag*Math.sin(angle));
    }

}
