import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public abstract class Detector{
	public int count = 0;
	
	/**
	 * To configure a new pattern: Relative pixels with y > 0 must match the color of the rest of the pixels with y > 0.
	 * Relative pixels with -(y) must be a color different to the pixels with y > 0
	 */
	public final int[][][] AMONGUS_TYPES = {
		// ▓▓▓
		//▓▓▒▒
		//▓▓▓▓
		//▓▓▓▓
		// ▓ ▓
		{
			   {1,0}, {2,0}, {3,0}, 
		{0,1}, {1,1},                         {2,-1},{3,-1}, 
		{0,2}, {1,2}, {2,2}, {3,2},
		{0,3}, {1,3}, {2,3}, {3,3},
			   {1,4}, 		 {3,4}
		},
		// ▓▓▓
		//▓▓▒▒
		//▓▓▓▓
		// ▓▓▓
		// ▓ ▓
		{
				   {1,0}, {2,0}, {3,0}, 
			{0,1}, {1,1},                              {2,-1},{3,-1},
			{0,2}, {1,2}, {2,2}, {3,2}, 
				   {1,3}, {2,3}, {3,3}, 
				   {1,4}, 		 {3,4}
		},
		// ▓▓▓
		//▓▓▒▒
		//▓▓▓▓
		// ▓ ▓
		{
				   {1,0}, {2,0}, {3,0}, 
			{0,1}, {1,1},                             {2,-1},{3,-1},
			{0,2}, {1,2}, {2,2}, {3,2}, 
				   {1,3}, 		 {3,3}
		},
		//▓▓▓
		//▒▒▓▓
		//▓▓▓▓
		//▓▓▓▓
		//▓ ▓
		{
			{0,0},{1,0},{2,0},              {0,-1},{1,-1},
						{2,1},{3,1},
			{0,2},{1,2},{2,2},{3,2},
			{0,3},{1,3},{2,3},{3,3},
			{0,4},		{2,4}
		},
		//▓▓▓
		//▒▒▓▓
		//▓▓▓▓
		//▓▓▓
		//▓ ▓
		{
			{0,0},{1,0},{2,0},                 {0,-1}, {1,-1},
						{2,1},{3,1},
			{0,2},{1,2},{2,2},{3,2},
			{0,3},{1,3},{2,3},
			{0,4},		{2,4}
		},
		//▓▓▓
		//▒▒▓▓
		//▓▓▓▓
		//▓ ▓
		{
			{0,0},{1,0},{2,0},               {0,-1}, {3,-1},
						{2,1},{3,1},
			{0,2},{1,2},{2,2},{3,2},
			{0,3},		{2,3}
		}
		
	};
	protected BufferedImage bi;
	/**
	 * This class detects amongus pixelsize in images and generates an image with the rest of pixels in almost pure black
	 * @param bi image in the format of {@link java.awt.image.BufferedImage BufferedImage}
	 */
	public Detector(BufferedImage bi) {
		this.bi = bi;
	}
	public static final Color detected = new Color(15, 15, 15);
	/**
	 * This function detects and genererates the image where the amongus are the only thing not turned into black
	 * @return the image with the amongus isolated
	 */
	protected BufferedImage detectAndGen() {
		count = 0;
		BufferedImage out = new BufferedImage(bi.getColorModel(), bi.copyData(null), bi.isAlphaPremultiplied(), null);	
		for (int y = 0; y < (bi.getHeight());y++)	
			for(int x = 0; x < bi.getWidth(); x++) {
				int amongus = -1;
				for(int i = 0; i < AMONGUS_TYPES.length; i++) {
					if(detect(x, y, AMONGUS_TYPES[i], bi)) {
						amongus = i;
						break;
					}
				}
				if (amongus == -1) continue;
				System.out.println(String.valueOf(x) + ":" + String.valueOf(y));
				count++;
				for(int[] i:AMONGUS_TYPES[amongus]) {
					try {
						out.setRGB(i[0]+x, Math.abs(i[1])+y, detected.getRGB());
					}catch(ArrayIndexOutOfBoundsException e1) {
					}
				}
					
				
			}
		for (int y = 0; y < (bi.getHeight());y++)	
			for(int x = 0; x < (bi.getWidth()); x++) {
				if(out.getRGB(x, y) != detected.getRGB()) {
					out.setRGB(x, y, new Color(15,15,15).getRGB());
				}else {
					out.setRGB(x, y, bi.getRGB(x, y));
				}
			}
		return out;
	}
	/**
	 * Detects the pattern in a patch of the image
	 * @param offsetX the x from where the algorithm starts
	 * @param offsetY the y from where the algorithm starts
	 * @param positions all the relative positions to check. Format: y>0 = must equal the rest; y<0 = -y must not equal the ones with y>0
	 * @param in image to check
	 * @return true if it equals the pattern
	 */
	protected boolean detect(int offsetX, int offsetY, int[][] positions, BufferedImage in) {
		return detect(offsetX, offsetY, positions, in, 0);
	}
	/**
	 * Detects the pattern in a patch of the image
	 * @param offsetX the x from where the algorithm starts
	 * @param offsetY the y from where the algorithm starts
	 * @param positions all the relative positions to check. Format: y>0 = must equal the rest; y<0 = -y must not equal the ones with y>0
	 * @param in image to check
	 * @param error quantity of allowed unmatching pixels
	 * @return true if it equals the pattern with less or equal the quantity of errors given
	 */
	protected boolean detect(int offsetX, int offsetY, int[][] positions, BufferedImage in, int error) {
		try {
			int color = in.getRGB(offsetX + positions[0][0], offsetY + positions[0][0]);
			if(new Color(color).equals(detected))return false;
			for(int[] i : positions)
				if(i[1] >= 0 && in.getRGB(i[0]+offsetX, i[1]+offsetY) != color) {
					error--;
				}else if(i[1] < 0 && in.getRGB(i[0]+offsetX, Math.abs(i[1])+offsetY) == color) {
					error --;
				}
					if(error == -1)return false;
			return true;
		}catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}
