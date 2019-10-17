package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gerenciador.GameControleEstado;
import com.mygdx.gerenciador.GameInputProcessor;
import com.mygdx.gerenciador.GameTeclas;

public class MyGdxGame extends ApplicationAdapter {

    public static int WIDTH;
    public static int HEIGHT;
    
    public static OrthographicCamera cam;
    
    private GameControleEstado gce;

    //SpriteBatch batch;
    //Texture img;

    @Override
    public void create () {
            WIDTH = Gdx.graphics.getWidth();
            HEIGHT = Gdx.graphics.getHeight();
            
            cam = new OrthographicCamera(WIDTH,HEIGHT);
            cam.translate(WIDTH/2, HEIGHT/2 );
            cam.update();
            //batch = new SpriteBatch();
            //img = new Texture("badlogic.jpg");
            Gdx.input.setInputProcessor(new GameInputProcessor());
            
            gce = new GameControleEstado();
    }

    @Override
    public void render () {
            //super.render();                              //Sem esta chamada, a tela que você definir no método create() não será processada.
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            //batch.begin();
            //batch.draw(img, 0, 0);
            //batch.end();
            
            //teste de teclas
            if(GameTeclas.isPressed(GameTeclas.SPACE)){
                System.out.println("SPACE");
            }
            
            gce.update(Gdx.graphics.getDeltaTime());
            gce.draw();
            GameTeclas.update();
            
            
    }

    @Override
    public void dispose () {
            //batch.dispose();
            //img.dispose();
    }
}
