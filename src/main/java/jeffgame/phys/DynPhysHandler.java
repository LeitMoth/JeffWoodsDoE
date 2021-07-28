package jeffgame.phys;

import org.joml.Vector2f;

public class DynPhysHandler {

    /*should be temp*/
    public boolean enableGravity = true;

    Rectangle bounds;
    Vector2f velocity = new Vector2f();
    Vector2f lastPos;

    public Vector2f getVelocity() {
        return velocity;
    }

    private final float SLOP = 0.001f;
    private final float gravity = 0.5f;

    public boolean inAir = true;

    public DynPhysHandler(Rectangle bounds)
    {
        this.bounds = bounds;
        this.lastPos = new Vector2f(bounds.center);
    }

    public void beginMove()
    {
        //Store our position before a move
        lastPos = new Vector2f(bounds.center);
    }

    public void endMove()
    {
        inAir = true;
        if(enableGravity)
        velocity.y -= gravity;

        //Move
        bounds.center.add(velocity);
    }

    public float getCenterDistanceSqrd(IPhysStatic objStatic) {
        Rectangle rect = objStatic.getRectangle();
        return rect.center.distanceSquared(bounds.center);
    }

    private void resolveX(Rectangle rect, Vector2f move)
    {
        if(Math.signum(move.x) < 0)
        {
            //Resolve right
            bounds.center.x = rect.center.x + rect.halfSize.x + bounds.halfSize.x + SLOP;
        }
        else
        {
            //Resolve left
            bounds.center.x = rect.center.x - rect.halfSize.x - bounds.halfSize.x - SLOP;
        }
        velocity.x = 0;
    }

    private void resolveY(Rectangle rect, Vector2f move)
    {
        if(Math.signum(move.y) < 0)
        {
            //Resolve up
            bounds.center.y = rect.center.y + rect.halfSize.y + bounds.halfSize.y + SLOP;
            inAir = false;
        }
        else
        {
            //Resolve left
            bounds.center.y = rect.center.y - rect.halfSize.y - bounds.halfSize.y - SLOP;
        }
        velocity.y = 0;
    }

    public boolean isColliding(IPhysStatic objStatic)
    {
        return objStatic.getRectangle().colliding(bounds);
    }

    public void handle(IPhysStatic objStatic) {
        if(!isColliding(objStatic)) return;

        /*
        basically this function looks at the slope of the Dynamics movement and compares it to the slope between the two overlapping corners
        with this comparison, we can deduce exactly which side of the rectangle we hit, and resolving is a breeze from there
        TODO: better explanation
         */

        Rectangle rect = objStatic.getRectangle();

        Vector2f move = new Vector2f();
        bounds.center.sub(lastPos, move);

        Vector2f directions = new Vector2f(Math.signum(move.x), Math.signum(move.y));

        Vector2f thisCorner = new Vector2f(bounds.halfSize);
        thisCorner.mul(directions).add(bounds.center);

        Vector2f otherCorner = new Vector2f(rect.halfSize);
        directions.mul(-1);
        otherCorner.mul(directions).add(rect.center);

        Vector2f delta = otherCorner.sub(thisCorner);

        if(delta.x == 0 || move.y == 0)
        {
            //If there is an infinite delta slope, or we have no vertical velocity: resolve horizontally
            resolveX(rect, move);
        }
        else if(delta.y == 0 || move.x == 0)
        {
            //If there is no delta slope, or we have no horizontal velocity: resolve vertically
            resolveY(rect, move);
        }
        else
        {
            float deltaSlope = Math.abs(delta.y/delta.x);
            float velSlope = Math.abs(move.y/move.x);
            if(velSlope < deltaSlope)
            {
                resolveX(rect, move);
            }
            else
            {
                resolveY(rect, move);
            }
        }
    }
}
