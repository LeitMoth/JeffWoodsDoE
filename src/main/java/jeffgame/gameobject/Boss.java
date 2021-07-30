package jeffgame.gameobject;

import jeffgame.JeffWoods;
import jeffgame.ResourceStore;
import jeffgame.gfx.Shader;
import jeffgame.gfx.Texture;
import jeffgame.phys.DynPhysHandler;
import jeffgame.phys.IPhysDyn;
import jeffgame.states.StatePlay;
import org.joml.Vector2f;

public class Boss extends Enemy {

    private int stillTimer = 0;

    public enum ID
    {
        Boss1,
        Boss2,
        Boss3,
        Boss4,
        Boss5,
        CoronaBoss,
        Anti_Jeff
    }
    public static Texture chooseTexture(ID id) {
        return switch (id) {
            default         -> ResourceStore.getTexture("/texture/alexBoss.png");
            case Boss2      -> ResourceStore.getTexture("/texture/warrenBoss.png");
            case Boss3      -> ResourceStore.getTexture("/texture/jacobBoss.png");
            case Boss4      -> ResourceStore.getTexture("/texture/johnBoss.png");
            case Boss5      -> ResourceStore.getTexture("/texture/brettBoss.png");
            case CoronaBoss -> ResourceStore.getTexture("/texture/FinalBoss.png");
            case Anti_Jeff  -> ResourceStore.getTexture("/texture/antiJeff.png");
        };
    }

    public Boss(Vector2f pos, Vector2f size, Texture t, Shader s) {
        super(pos, size, t, s);
        health = 25;
        attackDamage = 2;
    }

    public Boss(Vector2f pos, Vector2f size, ID id)
    {
        this(
                pos, size,
                chooseTexture(id),
                ResourceStore.getShader("/shader/tex.vs.glsl", "/shader/tex.fs.glsl")
        );
    }

    @Override
    public void update(JeffWoods engine) {
        if(engine.state instanceof StatePlay)
        {
            float velX = getHandler().getVelocity().x;
            if(Math.abs(velX) < 0.1)
            {
                stillTimer++;
                if(stillTimer >= 30 && !getHandler().inAir)
                {
                    getHandler().getVelocity().y = 15;
                }
            }
            else
            {
                stillTimer = 0;
            }

            Vector2f pcenter = ((StatePlay) engine.state).player.getBounds().center;
            getHandler().getVelocity().x += Math.signum(pcenter.x - bounds.center.x) * 0.02;
        }
        super.update(engine);
    }
}
