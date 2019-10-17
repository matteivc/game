package com.mygdc.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author tetel
 */
public class Player extends SpaceObject {
    
    private final int MAX_PROJETEIS = 4;
    private ArrayList<Projeteis> projeteis;
    
    /**/
    private float[] fogox;
    private float[]fogoy;
    /*movimentos*/ 
    private boolean left;
    private boolean right;
    private boolean up;
    
    private float velocidadeMax;
    private float acelera;
    private float desacelera;
    private float acceleratingTimer;
    
    private boolean chocar;
    private boolean morto;
    
    private float chocarTempo;
    private float chocarCronometro;
    private Line2D.Float[] hitLines;
    private Point2D.Float[] hitLinesVector;
    
    private long score;
    private int vidaextra;
    private long requiredScore;
    
    public Player(ArrayList<Projeteis> projeteis){
        /*dependencia parra classe Player ter acesso a Lista de projeteis */
        this.projeteis =  projeteis;
        
        x = MyGdxGame.WIDTH/ 2;
        y = MyGdxGame.HEIGHT / 2;
        
        velocidadeMax = 300;
        acelera = 200;
        desacelera = 10;
        
        formax = new float[4];
        formay = new float[4];
        fogox = new float[3];
        fogoy = new float[3];
        
        radians = (float) ( Math.PI / 2 );
        
        rotationSpeed = 3;
        
        chocar = false;
        chocarCronometro = 0;
        chocarTempo = 2;
        
        score = 0;
        vidaextra = 3;
        requiredScore = 10000;
        
    }
     /*forma da nave*/
    private void setForma(){
        formax[0]= x + MathUtils.cos(radians)*8;
        formay[0]= y + MathUtils.sin(radians)*8;
        
        formax[1]= x + MathUtils.cos(radians - 4 * 3.1415f / 5)*8;
        formay[1]= y + MathUtils.sin(radians - 4 * 3.1415f / 5)*8;
        
        formax[2]= x + MathUtils.cos(radians + 3.1415f)*5;
        formay[2]= y + MathUtils.sin(radians + 3.1415f)*5;
        
        formax[3]= x + MathUtils.cos(radians + 4 * 3.1415f / 5)*8;
        formay[3]= y + MathUtils.sin(radians + 4 * 3.1415f / 5)*8;
        
    }
    
    private void setFogo(){
        fogox[0]= x + MathUtils.cos(radians - 5 * 3.1415f / 6)*5;
        fogoy[0]= y + MathUtils.sin(radians - 5 * 3.1415f / 6)*5;
        
        fogox[1]= x + MathUtils.cos(radians - 3.1415f)*(6 + acceleratingTimer * 50);
        fogoy[1]= y + MathUtils.sin(radians - 3.1415f)*(6 + acceleratingTimer * 50);
        
        fogox[2]= x + MathUtils.cos(radians + 5 * 3.1415f / 6)*5;
        fogoy[2]= y + MathUtils.sin(radians + 5 * 3.1415f / 6)*5;
       
    }
    
    /*movimento metodo*/
    public void setLeft(boolean b){ left = b;}
    public void setRight(boolean b){ right = b;}
    public void setUp(boolean b){ up = b;}
    
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setForma();
    }
    
    public boolean estaChocado(){return chocar;}
    public boolean estaMorto(){return morto;}
    public void reset(){
        x = MyGdxGame.WIDTH /2;
        y = MyGdxGame.HEIGHT/2;
        setForma();
        chocar = morto = false;
    }
    
    
    public long getScore() {
        return score;
    }

    public int getLives() {
        return vidaextra;
    }

    public void loseLife() {
        vidaextra--;
    }

    public void incrementScore(long l) {
        score += l;
    }
    
    
    public void tiro(){
        if(projeteis.size() ==  MAX_PROJETEIS)return;
        projeteis.add(new Projeteis(x, y, radians));
    }
    
    public void chocar(){
        if (chocar) {
            return;
        }

        chocar = true;
        dx = dy = 0;
        left = right = up = false;
        //Jukebox.stop("thruster");

        hitLines = new Line2D.Float[4];
        for (int i = 0, j = hitLines.length - 1;
                i < hitLines.length;
                j = i++) {
            hitLines[i] = new Line2D.Float(
                    formax[i], formay[i], formax[j], formay[j]
            );
        }

        hitLinesVector = new Point2D.Float[4];
        hitLinesVector[0] = new Point2D.Float(
                MathUtils.cos(radians + 1.5f),
                MathUtils.sin(radians + 1.5f)
        );
        hitLinesVector[1] = new Point2D.Float(
                MathUtils.cos(radians - 1.5f),
                MathUtils.sin(radians - 1.5f)
        );
        hitLinesVector[2] = new Point2D.Float(
                MathUtils.cos(radians - 2.8f),
                MathUtils.sin(radians - 2.8f)
        );
        hitLinesVector[3] = new Point2D.Float(
                MathUtils.cos(radians + 2.8f),
                MathUtils.sin(radians + 2.8f)
        );

    }
    
    public void update(float dt){
        
        /*checar se chocar*/
        if(chocar){
            chocarCronometro += dt;
            if(chocarCronometro > chocarTempo){
                morto = true;
                chocarCronometro = 0;
            }
            for (int i = 0; i < hitLines.length; i++) {
                hitLines[i].setLine(
                        hitLines[i].x1 + hitLinesVector[i].x * 10 * dt,
                        hitLines[i].y1 + hitLinesVector[i].y * 10 * dt,
                        hitLines[i].x2 + hitLinesVector[i].x * 10 * dt,
                        hitLines[i].y2 + hitLinesVector[i].y * 10 * dt
                );
            }
            return;
        }
        
        /*checar vida extra*/
        if (score >= requiredScore) {
            vidaextra++;
            requiredScore += 10000;
            //Jukebox.play("vida extra");
        }
        
        
        /*rotação*/
        if (left){
            radians += rotationSpeed * dt;
        }
        else if(right){
            radians -= rotationSpeed * dt;
        }
        
        /*aceleração*/
        if(up){
            dx += MathUtils.cos(radians) * acelera * dt;
            dy += MathUtils.sin(radians) * acelera * dt;
            acceleratingTimer += dt;
            if(acceleratingTimer > 0.1f){
                acceleratingTimer = 0;
                
            }
        }else{
            acceleratingTimer = 0;
        }
        /*desacelera*/
        float vec = (float) Math.sqrt(dx * dx + dy * dy );
        if (vec > 0){
            dx -= (dx / vec) * desacelera * dt;
            dy -= (dy / vec) * desacelera * dt;          
        }
        if(vec > velocidadeMax){
            dx = (dx / vec) * velocidadeMax;
            dy = (dy / vec) * velocidadeMax;
        }
        
        /*posição*/
        x+= dx * dt;
        y+= dy * dt;
        
        /*setar forma*/
         setForma();
         
         /*setar fogo*/
         if(up){
             setFogo();
         }
         
         /*limite da tela*/
         limiteTela();
        
    }
    
    public void draw(ShapeRenderer sr){
        
        sr.setColor(1,1,1,1);
        
        /*todo o drewing ocorroe entre o começo e o fim*/
        sr.begin(ShapeType.Line);
        
        /*draw os chocars*/
        if (chocar) {
            for(int i = 0; i < hitLines.length; i++) {
                sr.line(
                        hitLines[i].x1,
                        hitLines[i].y1,
                        hitLines[i].x2,
                        hitLines[i].y2
                );
            }
            sr.end();
            return;
        }
        
        /*draw nave*/
        for(int i = 0, j = formax.length - 1; i < formax.length; j = i++){
            /*desenhando a linha da nave*/
            sr.line(formax[i], formay[i],formax[j],formay[j]);
        }
        
        /*draw fogo*/
        if(up){
            for(int i = 0, j = fogox.length - 1; i < fogox.length; j = i++){
                /*desenhando a linha da nave*/
                sr.line(fogox[i], fogoy[i],fogox[j],fogoy[j]);
        }
        }
        
        sr.end();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
