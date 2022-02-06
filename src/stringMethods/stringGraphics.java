package stringMethods;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.lang.Math;
import java.util.ArrayList;

public class stringGraphics {
	

	public static void drawStringCentered(String input, Rectangle rect, Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector gv = g.getFont().createGlyphVector(frc, input);

        //Rectangle bounds =  gv.getPixelBounds(null, rect.x, rect.y);
        Rectangle2D bounds =  gv.getVisualBounds();
        //g.drawString(input, rect.x, (int)(rect.y + bounds.getHeight()));
        // g2d.draw(new Rectangle(rect.x, rect.y, (int)bounds.getWidth(), (int)bounds.getHeight()));

        g.drawString(input, (int)(rect.x + (rect.width-bounds.getWidth())/2), (int)(rect.y + bounds.getHeight() + (rect.height-bounds.getHeight())/2));

    }

    public static void drawStringsCentered(String[] input, Rectangle rect, Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        FontRenderContext frc = g2d.getFontRenderContext();
        double spacing = g.getFontMetrics().getHeight()/2.5;
        double totalHeight = 0;
        double currentHeight = 0;

        ArrayList<GlyphVector> glyphVectors = new ArrayList<GlyphVector>();
        ArrayList<Rectangle2D> bounds = new ArrayList<Rectangle2D>();
        for(int i = 0; i < input.length; i++){
            glyphVectors.add(g.getFont().createGlyphVector(frc, input[i]));
            bounds.add(glyphVectors.get(i).getVisualBounds());

            totalHeight += glyphVectors.get(i).getVisualBounds().getHeight();
        }

        totalHeight += spacing * (input.length-1);

        for(int i = 0; i < input.length; i++) {
            g.drawString(input[i], (int)(rect.x + (rect.width-bounds.get(i).getWidth())/2),
                    (int)(rect.y + bounds.get(0).getHeight() + currentHeight + (rect.height-totalHeight)/2));
            currentHeight+= spacing + bounds.get(i).getHeight();
        }

    }
    
    public static void drawStringsCentered(ArrayList<String> input, Rectangle rect, Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        FontRenderContext frc = g2d.getFontRenderContext();
        double spacing = g.getFontMetrics().getHeight()/2.5;
        double totalHeight = 0;
        double currentHeight = 0;

        ArrayList<GlyphVector> glyphVectors = new ArrayList<GlyphVector>();
        ArrayList<Rectangle2D> bounds = new ArrayList<Rectangle2D>();
        for(int i = 0; i < input.size(); i++){
            glyphVectors.add(g.getFont().createGlyphVector(frc, input.get(i)));
            bounds.add(glyphVectors.get(i).getVisualBounds());

            totalHeight += glyphVectors.get(i).getVisualBounds().getHeight();
        }

        totalHeight += spacing * (input.size()-1);

        for(int i = 0; i < input.size(); i++) {
            g.drawString(input.get(i), (int)(rect.x + (rect.width-bounds.get(i).getWidth())/2),
                    (int)(rect.y + bounds.get(0).getHeight() + currentHeight + (rect.height-totalHeight)/2));
            currentHeight+= spacing + bounds.get(i).getHeight();
        }

    }
    
    public static void drawStringCenteredFlow(String input, Rectangle rect, Graphics g) {
    	Graphics2D g2d = (Graphics2D)g;
        FontRenderContext frc = g2d.getFontRenderContext();
        
        //double totalWidth =  g.getFont().createGlyphVector(frc, input).getVisualBounds().getWidth();
        double totalWidth = g.getFontMetrics().stringWidth(input);

        String[] words = input.split(" "); //separate words
        for(int i = 0; i < words.length - 1; i++) {
        	words[i]+= " ";
        } //puts a space at the end of the last word, except for the last
        
        int numRows = (int)(Math.ceil(totalWidth/rect.width)); //minimum number of rows needed to fit text
        double rowWidth = totalWidth/numRows; //makes each row have similar width in text
        
        ArrayList<String> rows = new ArrayList<String>();
        String currentRow = "";
        double currentRowLength = 0;
        
        for(int i = 0; i < words.length; i++) {
			currentRow += words[i];
			currentRowLength += g.getFontMetrics().stringWidth(words[i]);
        	if(currentRowLength > rowWidth) {
        		rows.add(currentRow);
        		
        		currentRowLength = 0;
        		currentRow = "";
        	}
        }
       rows.add(currentRow);//one last time for the last row
        
        drawStringsCentered(rows, rect, g);

    }
    
    public static void drawRectToFitString(String input, Rectangle rect, Graphics g) {
    	
    	
    }
    
}
