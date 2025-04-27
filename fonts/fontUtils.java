package fonts;

import java.awt.*;
import java.io.File;

public class fontUtils{
    public  static Font loadFont(String fontPath, float size){
        Font font = null;
        try{
            File fontStyle = new File("fonts/Roboto-VariableFont_wdth,wght.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(size);
        }catch (Exception e){
            e.printStackTrace();
            font = new Font ("ARIAL", Font.PLAIN, (int) size);
        }
        return font;
    }
    public static Font loadFont(float size){
        return loadFont("fonts/Roboto-VariableFont_wdth,wght.ttf", size);
    }

}
