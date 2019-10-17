package com.mygdx.gerenciador;

import com.mygdx.estadogame.GameEstado;
import com.mygdx.estadogame.MenuEstado;
import com.mygdx.estadogame.PlayEstado;

/**
 *
 * @author tetel
 */
public class GameControleEstado {
    /*atual estado do jogo*/
    private GameEstado gameEstado;
    
    public static final int MENU = 0;
    public static final int PLAY = 893746;
    
    /*construtor*/
    public GameControleEstado(){
        setEstado(MENU);
    }
    
    public void setEstado(int estado){
        if(gameEstado!= null) gameEstado.dispose();
        if (estado == MENU){
            /*troque para o menu*/
            gameEstado = new MenuEstado(this);
        }
        if (estado == PLAY){
            /*troque para play*/
            gameEstado = new PlayEstado(this);
        }
    }
    
    public void update(float dt){
        gameEstado.update(dt);
    }
    public void draw(){
        gameEstado.draw();
    }
}
