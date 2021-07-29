package jeffgame.phys;

import org.joml.Vector2f;

public class Rectangle {
    public Vector2f center, halfSize;

    public Rectangle(Vector2f c, Vector2f h)
    {
        center = c;
        halfSize = h;
    }

    public Rectangle() {
        center = new Vector2f();
        halfSize = new Vector2f();
    }
    public boolean colliding(Rectangle other)
    {
        if (Math.abs(center.x - other.center.x) > halfSize.x + other.halfSize.x) return false;
        if (Math.abs(center.y - other.center.y) > halfSize.y + other.halfSize.y) return false;
        return true;
    }
}
