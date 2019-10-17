package com.mygdx.gerenciador;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 *
 * @author tetel
 */
public class GameInputProcessor extends InputAdapter {
    /*o evento ocorre quando a tecla é pressionada.*/
    @Override
    public boolean keyDown(int k){
        if(k == Keys.UP){
            GameTeclas.setKey(GameTeclas.UP, true);
        }
        if(k == Keys.LEFT){
            GameTeclas.setKey(GameTeclas.LEFT, true);
        }
        if(k == Keys.DOWN){
            GameTeclas.setKey(GameTeclas.DOWN, true);
        }
        if(k == Keys.RIGHT){
            GameTeclas.setKey(GameTeclas.RIGHT, true);
        }
        if(k == Keys.ENTER){
            GameTeclas.setKey(GameTeclas.ENTER, true);
        }
        if(k == Keys.ESCAPE){
            GameTeclas.setKey(GameTeclas.ESCAPE, true);
        }
        if(k == Keys.SPACE){
            GameTeclas.setKey(GameTeclas.SPACE, true);
        }
        if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            GameTeclas.setKey(GameTeclas.SHIFT, true);
	}
        return true;
    }
    /*o evento ocorre quando a tecla retorna a sua posição original no teclado (i.e. quando você solta a mesma)*/
    public boolean keyUp(int k){
        if(k == Keys.UP){
            GameTeclas.setKey(GameTeclas.UP, false);
        }
        if(k == Keys.LEFT){
            GameTeclas.setKey(GameTeclas.LEFT, false);
        }
        if(k == Keys.DOWN){
            GameTeclas.setKey(GameTeclas.DOWN, false);
        }
        if(k == Keys.RIGHT){
            GameTeclas.setKey(GameTeclas.RIGHT, false);
        }
        if(k == Keys.ENTER){
            GameTeclas.setKey(GameTeclas.ENTER, false);
        }
        if(k == Keys.ESCAPE){
            GameTeclas.setKey(GameTeclas.ESCAPE, false);
        }
        if(k == Keys.SPACE){
            GameTeclas.setKey(GameTeclas.SPACE, false);
        }
        if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT){
            GameTeclas.setKey(GameTeclas.SHIFT, false);
        }
        return true;
    }
   
    
}
    
