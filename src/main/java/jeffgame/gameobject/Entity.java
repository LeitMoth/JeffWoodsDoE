package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.gfx.Camera;
import jeffgame.phys.Rectangle;
import jeffgame.states.StatePlay;

public class Entity implements IGameObject {

    protected Rectangle bounds = new Rectangle();
    public Rectangle getBounds() {
        return bounds;
    }

    protected int health;
    protected int hitCooldown;

    protected int hitCooldownStart = 30;

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

    public void heal(int health)
    {
        this.health += health;
    }

    @Override
    public void draw(Camera c) {

    }

    @Override
    public void update(JeffWoods engine) {
        hitCooldown--;

        if(health <= 0)
        {
            cleanup();
            if(engine.state instanceof StatePlay)
                ((StatePlay) engine.state).queueRemoveGameObject(this);
        }
    }

    @Override
    public void cleanup() {

    }
}
