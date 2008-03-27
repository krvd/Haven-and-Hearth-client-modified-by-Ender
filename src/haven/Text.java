package haven;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Text {
	private static Foundry std;
	private BufferedImage img;
	public final String text;
	private FontMetrics m;
	private Tex tex;
	
	static {
		std = new Foundry(new Font("SansSerif", Font.PLAIN, 10));
	}
	
	public static class Foundry {
		private static FontMetrics m;
		Font font;
		
		public Foundry(Font f) {
			font = f;
			BufferedImage junk = Tex.mkbuf(new Coord(10, 10));
			Graphics g = junk.getGraphics();
			g.setFont(f);
			m = g.getFontMetrics();
		}
		
		public Text render(String text) {
			Text t = new Text(text);
			Rectangle2D b = font.getStringBounds(text, m.getFontRenderContext());
			t.img = Tex.mkbuf(new Coord((int)b.getWidth(), m.getHeight()));
			Graphics g = t.img.createGraphics();
			g.setFont(font);
			t.m = g.getFontMetrics();
			g.drawString(text, 0, t.m.getAscent());
			g.dispose();
			return(t);
		}
	}
	
	private Text(String text) {
		this.text = text;
	}
	
	public Coord sz() {
		return(Utils.imgsz(img));
	}
	
	public Coord base() {
		return(new Coord(0, m.getAscent()));
	}
	
	public static Text render(String text) {
		return(std.render(text));
	}
	
	public Tex tex() {
		if(tex == null)
			tex = new Tex(img);
		return(tex);
	}
}