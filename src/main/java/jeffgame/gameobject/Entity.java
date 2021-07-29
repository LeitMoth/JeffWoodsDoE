package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.gfx.Camera;
import jeffgame.phys.Rectangle;

public class Entity implements IGameObject {

    protected Rectangle bounds = new Rectangle();
    public Rectangle getBounds() {
        return bounds;
    }

    protected int health;
    protected int hitCooldown;
    protected int timer;

    protected int hitCooldownStart = 60;

    public int getHealth() {
        return health;
    }

    public float getX() { return bounds.center.x; }

    public float getY() { return bounds.center.y; }

    public void damage(int damage)
    {
        if(hitCooldown <= 0) {
            health -= damage;
            hitCooldown = hitCooldownStart;
        }
    }

    public boolean timer() {
        if (timer > 3600) {
            boolean creditsTime = true;
            return creditsTime;
        } else {
            timer += 10;
        }
        return false;
    }

    @Override
    public void draw(Camera c) {

    }

    @Override
    public void update(JeffWoods engine) {
        hitCooldown--;
    }

    @Override
    public void cleanup() {

    }
}
