package com.mygdx.estadogame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.MyGdxGame;
import com.mygdx.gerenciador.GameControleEstado;
import com.mygdx.gerenciador.GameTeclas;

/**
 *
 * @author tetel
 */
public class MenuEstado extends GameEstado{

    private SpriteBatch sb;
    
    private BitmapFont tituloFonte;
    private BitmapFont fonte;
    
    private final String titulo = "Asteroids";
    private GlyphLayout layout; 
    
    private int currentItems;
    private String[] menuItems;
                
    
    //protected GameControleEstado gsm;

    public MenuEstado( GameControleEstado gce) {
        super(gce);
    }

    @Override
    public void init(){
    sb = new SpriteBatch();
    layout = new GlyphLayout();
    
    FreeTypeFontGenerator gen
            = new FreeTypeFontGenerator(
                    Gdx.files.internal("fonts/munro.ttf")
            );
    FreeTypeFontGenerator.FreeTypeFontParameter parms = new FreeTypeFontGenerator.FreeTypeFontParameter();
    FreeTypeFontGenerator.FreeTypeFontParameter parms2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
    
    parms.size = 56;
    parms.color = Color.WHITE;
    
    parms2.size = 20;
    
    tituloFonte = gen.generateFont(parms);
    fonte = gen.generateFont(parms2);
    
    menuItems = new String[]{
        "PLAY",
        "QUIT"
    };
    
    }

    public  void update(float dt){
        handleInput();
    }

    public void draw(){
        sb.setProjectionMatrix(MyGdxGame.cam.combined);
        //sr.setProjectionMatrix(Game.cam.combined);  
        
        sb.begin();
        
        layout.setText(tituloFonte, titulo);
        tituloFonte.draw(sb, titulo,
                (MyGdxGame.WIDTH - layout.width) / 2,
                300
        );
        //draw menu
        for (int i = 0; i < menuItems.length; i++) {
            layout.setText(fonte, menuItems[i]);
            if (currentItems == i) {
                fonte.setColor(Color.BLACK);
            } else {
                fonte.setColor(Color.WHITE);
            }
            fonte.draw(
                    sb,
                    menuItems[i],
                    (MyGdxGame.WIDTH - layout.width) / 2,
                    180 - 35 * i
            );
        }
        
        sb.end();
    }

    public void handleInput(){

        if (GameTeclas.isPressed(GameTeclas.UP)) {
            if (currentItems > 0) {
                currentItems--;
            }
        }
        if (GameTeclas.isPressed(GameTeclas.DOWN)) {
            if (currentItems < menuItems.length - 1) {
                currentItems++;
            }
        }
        if (GameTeclas.isPressed(GameTeclas.ENTER)) {
            select();
        }
    }

    private void select() {
        // play
        if (currentItems == 0) {
            gce.setEstado(GameControleEstado.PLAY);
        } // high scores
        else if (currentItems == 1) {
            //gsm.setState(GameControleEstado.HIGHSCORE);
        } else if (currentItems == 2) {
            Gdx.app.exit();
        }
    }
    
    public void dispose(){}

}
