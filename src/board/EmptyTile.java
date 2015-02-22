package board;

import java.awt.*;

/**
 *<h1>EmptyTile</h1><br>
 *     <p>The basic empty tile that don't do much.</p>
 */
public class EmptyTile extends AbstractTile{

    public EmptyTile(int x, int y){
        super(x, y);
    }

    public void draw(Graphics g){
        super.draw(g);
    }

    public void update() {
        super.update();
    }

}
