package game;

import java.awt.event.*;

public class KeyboardListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char direction = e.getKeyChar();
        if (direction == 'w' || direction == 'a' || direction == 's' || direction == 'd') {
            if (Runner.multiplayer == true) {
                try {
                    net.Client.out.writeUTF(net.Client.id + ": " + direction);
                } catch (Exception e1) {

                }
            }
            else if (Runner.multiplayer == false) {
                Singleplayer.direction = direction;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}