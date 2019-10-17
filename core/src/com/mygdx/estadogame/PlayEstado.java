package com.mygdx.estadogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdc.entities.Projeteis;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdc.entities.Asteroides;
import com.mygdc.entities.Particulas;
import com.mygdc.entities.Player;
import com.mygdx.game.MyGdxGame;
import com.mygdx.gerenciador.GameControleEstado;
import com.mygdx.gerenciador.GameTeclas;
import java.util.ArrayList;

/**
 *
 * @author tetel
 */
public class PlayEstado extends GameEstado {
    private SpriteBatch sb;
    private ShapeRenderer sr;
    
    private BitmapFont font;
    private Player hudPlayer;
    
    private Player player;
    
    public ArrayList<Projeteis> projeteis;
    public ArrayList<Asteroides> asteroides;
    
    private ArrayList<Particulas> particulas;
    
    private int level;
    private int totalAsteroids;
    private int numAsteroidsLeft;
    
    
    public PlayEstado(GameControleEstado gce){
        super(gce);
    }
    
    @Override
    public void init(){
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        
        /*set fonte*/
        FreeTypeFontGenerator gen
                = new FreeTypeFontGenerator(
                        Gdx.files.internal("fonts/munro.ttf")
                );
        FreeTypeFontGenerator.FreeTypeFontParameter parms = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parms.size = 24;
        parms.color = Color.BLACK;
        font = gen.generateFont(parms);
        
        
        projeteis = new ArrayList<Projeteis>();
        
        player = new Player(projeteis);
        
        asteroides = new ArrayList<Asteroides>();
        
        particulas = new ArrayList<Particulas>();
        
        level = 1;
        spawAsteroids();
        
        hudPlayer = new Player(null);
        
    }
    
    private void criarParticulas(float x, float y) {
        for (int i = 0; i < 6; i++) {
            particulas.add(new Particulas(x, y));
        }
    }
    
    private void splitAsteroids(Asteroides a) {
        criarParticulas(a.getx(), a.gety());
        numAsteroidsLeft--;
        /*currentDelay = ((maxDelay - minDelay)
                * numAsteroidsLeft / totalAsteroids)
                + minDelay;*/
        if (a.getType() == Asteroides.GRANDE) {
            asteroides.add(
                    new Asteroides(a.getx(), a.gety(), Asteroides.MEDIO));
            asteroides.add(
                    new Asteroides(a.getx(), a.gety(), Asteroides.MEDIO));
        }
        if (a.getType() == Asteroides.MEDIO) {
            asteroides.add(
                    new Asteroides(a.getx(), a.gety(), Asteroides.PEQUENO));
            asteroides.add(
                    new Asteroides(a.getx(), a.gety(), Asteroides.PEQUENO));
        }
    }
    
    private void spawAsteroids(){
        asteroides.clear();
       
        int numToSpawn = 4 + level - 1;
        totalAsteroids = numToSpawn * 7;
        numAsteroidsLeft = totalAsteroids;
       
        for(int i = 0; i < numToSpawn; i++) {
			
            float x = MathUtils.random(MyGdxGame.WIDTH);
            float y = MathUtils.random(MyGdxGame.HEIGHT);
			
            float dx = x - player.getx();
            float dy = y - player.gety();
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
			
            while(dist < 100) {
		x = MathUtils.random(MyGdxGame.WIDTH);
		y = MathUtils.random(MyGdxGame.HEIGHT);
		dx = x - player.getx();
		dy = y - player.gety();
		dist = (float) Math.sqrt(dx * dx + dy * dy);
            }
            
            asteroides.add(new Asteroides(x, y, Asteroides.GRANDE));
			
	}
      
    }
    
    
    @Override
    public void update(float dt){
        /*pegar a entrada do usuario*/
        handleInput();
        
        /*proximo level*/
        if (asteroides.size() == 0) {
            level++;
            spawAsteroids();
        }
        
        /*update player*/
        player.update(dt);
        if(player.estaMorto()){
            if(player.getLives() == 0){
                gce.setEstado(GameControleEstado.MENU);
            }
            player.reset();
            player.loseLife();
            return;
        }
        
        /*update player tiros*/
        for(int i = 0; i< projeteis.size();i++){
            projeteis.get(i).update(dt);
            if(projeteis.get(i).deveRemover()){
                projeteis.remove(i);
                i--;
            }
        }
        
        /*update asteroides*/
        for(int i = 0; i< asteroides.size();i++){
            asteroides.get(i).update(dt);
            if(asteroides.get(i).deveRemover()){
                asteroides.remove(i);
                i--;
            }
        }
        
        /*update particulas*/
        for (int i = 0; i < particulas.size(); i++) {
            particulas.get(i).update(dt);
            if (particulas.get(i).remove()) {
                particulas.remove(i);
                i--;
            }
        }
        
        /*checar colisões*/
        checkColisoes();
    }
    
    private void checkColisoes() {
        
        /*colisão jogador-asteroid*/
        if (!player.estaChocado()) {
            for (int i = 0; i < asteroides.size(); i++) {
                Asteroides a = asteroides.get(i);
                if (a.cruzar(player)) {
                    player.chocar();
                    asteroides.remove(i);
                    i--;
                    splitAsteroids(a);
                    /*Jukebox.play("explode");*/
                    break;
                }
            }
        }
        
        /*colisão de projeteis-asteroids*/
        for (int i = 0; i < projeteis.size(); i++) {
            Projeteis b = projeteis.get(i);
            for (int j = 0; j < asteroides.size(); j++) {
                Asteroides a = asteroides.get(j);
                if (a.contains(b.getx(), b.gety())) {
                    projeteis.remove(i);
                    i--;
                    asteroides.remove(j);
                    j--;
                    splitAsteroids(a);
                    player.incrementScore(a.getScore());
                    /**Jukebox.play("explode");
                    **/
                    break;
                }
            }
        }
    }
    
    
    
    @Override
    public void draw(){
        // draw player
        player.draw(sr);
        
        //draw projeteis
        for(int i = 0; i < projeteis.size(); i++){
            projeteis.get(i).draw(sr);
        }
        
        /*draw asteroides*/
        for(int i = 0; i < asteroides.size(); i++){
            asteroides.get(i).draw(sr);
        }
        
        /*draw particulas*/
        for (int i = 0; i < particulas.size(); i++) {
            particulas.get(i).draw(sr);
        }
        
        /*draw score*/
        sb.setColor(1, 1, 1, 1);
        sb.begin();
        font.draw(sb, Long.toString(player.getScore()), 40, 390);
        sb.end();
        
        /*draw vidas*/
        for (int i = 0; i < player.getLives(); i++) {
            hudPlayer.setPosition(40 + i * 10, 360);
            hudPlayer.draw(sr);
        }
    }
    
    @Override
    public void handleInput(){
        player.setLeft(GameTeclas.isDown(GameTeclas.LEFT));
        player.setRight(GameTeclas.isDown(GameTeclas.RIGHT));
        player.setUp(GameTeclas.isDown(GameTeclas.UP));
        if(GameTeclas.isPressed(GameTeclas.SPACE)){
            player.tiro();
        }
        
        
    }
    
    @Override
    public void dispose(){}

    
        
}
