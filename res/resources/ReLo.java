package resources;

import java.awt.Toolkit;
import java.awt.Image;

public class ReLo
{
    static ReLo rl;
    
    static {
        ReLo.rl = new ReLo();
    }
    
    public static Image loadImage(final String imageName) {
        return Toolkit.getDefaultToolkit().getImage(ReLo.rl.getClass().getResource("images/" + imageName));
    }
}
