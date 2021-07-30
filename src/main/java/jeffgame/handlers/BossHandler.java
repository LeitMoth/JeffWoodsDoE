package jeffgame.handlers;

import jeffgame.ResourceStore;
import jeffgame.gameobject.BossEntity;
import jeffgame.gameobject.Enemy;
import jeffgame.gameobject.Sprite;
import jeffgame.gfx.Texture;
/*
    Boss Handler class to handle all things bosses,
    A bit redundent for now because it only handles
    textures, but it should be expandable enough for the future of this project
    -Shadow

 */

public class BossHandler {

    public BossEntity boss;
    public Texture bossTex;


    public void BossGenerate(int bossID){

        //Asign the Boss ID's here; there are 7 bosses right now
        int Boss1 = 100;
        int Boss2 = 200;
        int Boss3 = 300;
        int Boss4 = 400;
        int Boss5 = 500;
        int CoronaBoss = 1111;
        int Anti_Jeff = 666;

        //Asign Boss Textures here
        Texture Boss1Tex = ResourceStore.getTexture("/texture/alexBoss.png");
        Texture Boss2Tex = ResourceStore.getTexture("/texture/warrenBoss.png");
        Texture Boss3Tex = ResourceStore.getTexture("/texture/jacobBoss.png");
        Texture Boss4Tex = ResourceStore.getTexture("/texture/johnBoss.png");
        Texture Boss5Tex = ResourceStore.getTexture("/texture/brettBoss.png");
        Texture CoronaBossTex = ResourceStore.getTexture("/texture/FinalBoss.png");
        Texture Anti_JeffTex = ResourceStore.getTexture("/texture/antiJeff.png");



        //Asign bossID to bossTexture

        if(bossID == Boss1){
            System.out.println("Loaded Boss1");
            bossTex = Boss1Tex;

        } else if(bossID == Boss2){
            System.out.println("Loaded Boss2");
            bossTex = Boss2Tex;

        } else if(bossID == Boss3){
            System.out.println("Loaded Boss3");
            bossTex = Boss3Tex;
        } else if(bossID == Boss4){
            System.out.println("Loaded Boss4");
            bossTex = Boss4Tex;
        } else if(bossID == Boss5){
            System.out.println("Loaded Boss5");
            bossTex = Boss5Tex;
        } else if(bossID == CoronaBoss){
            System.out.println("Loaded BossCorona");
            bossTex = CoronaBossTex;
        } else if(bossID == Anti_Jeff){
            System.out.println("Loaded BossAntiJeff");
            bossTex = Anti_JeffTex;
        } else {
            System.out.println("ERROR: Incorrect bossID entered, Loading Default Boss");
            bossTex = Boss1Tex;
        }

        /*
         * For right now, all bosses will have the same
         * AI, Attack, etc. I am building this class to
         * handle multiple music tracks and different attacks
         * Per boss in the future.
         * This class is built slightly inefficiently for ease of understanding.
         * -Shadow
         */




    }






}
