import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class Detector{
	public int count = 0;
	
	/**
	 * To configure a new pattern: Relative pixels with both axis positive must match the color of the rest of the pixels with both axis positive.
	 * Negative axis work like this: axis = -(value + 1) and mean that that pixel must not be the same color as the rest.
	 * If only one axis is negative that's primary pixel, so if the pixel does not comply the condition, the pattern it's discarded
	 * If both axis are negative, the non complyant pixel will add to the error count 
	 * 
	 */
	public final int[][][] AMONGUS_TYPES = {
		// ▓▓▓
		//▓▓▒▒
		//▓▓▓▓
		//▓▓▓▓
		// ▓ ▓
		{
			   {1,0}, {2,0}, {3,0}, 		  {-1,-1},
		{0,1}, {1,1},                         {2,-2},{3,-2}, 
		{0,2}, {1,2}, {2,2}, {3,2},
		{0,3}, {1,3}, {2,3}, {3,3},
			   {1,4}, 		 {3,4},			  {-1,-5},{-3,-5}
		},
		// ▓▓▓
		//▓▓▒▒
		//▓▓▓▓
		// ▓▓▓
		// ▓ ▓
		{
				   {1,0}, {2,0}, {3,0}, 			   {-1,-1},                
			{0,1}, {1,1},                              {2,-2},{3,-2},
			{0,2}, {1,2}, {2,2}, {3,2}, 
				   {1,3}, {2,3}, {3,3}, 			   {-1,-4},
				   {1,4}, 		 {3,4},				   {-1,-5},{-3,-5}
		},
		// ▓▓▓
		//▓▓▒▒
		//▓▓▓▓
		// ▓ ▓
		{
				   {1,0}, {2,0}, {3,0}, 			  {-1,-1},
			{0,1}, {1,1},                             {2,-2},{3,-2},
			{0,2}, {1,2}, {2,2}, {3,2}, 
				   {1,3}, 		 {3,3},               {-1,-4},{-3,-4}
		},
		//▓▓▓
		//▒▒▓▓
		//▓▓▓▓
		//▓▓▓▓
		//▓ ▓
		{
			{0,0},{1,0},{2,0},							{-4,-1},
						{2,1},{3,1},					{0,-2},{1,-2},
			{0,2},{1,2},{2,2},{3,2},
			{0,3},{1,3},{2,3},{3,3},
			{0,4},		{2,4},							{-2,-4},{-4,-5}
		},
		//▓▓▓
		//▒▒▓▓
		//▓▓▓▓
		//▓▓▓
		//▓ ▓
		{
			{0,0},{1,0},{2,0},					{-4,-1},
						{2,1},{3,1},			{0,-2}, {1,-2},
			{0,2},{1,2},{2,2},{3,2},
			{0,3},{1,3},{2,3},					{-4,-4},
			{0,4},		{2,4},					{-2,-5},{-4,-5}
		},
		//▓▓▓
		//▒▒▓▓
		//▓▓▓▓
		//▓ ▓
		{
			{0,0},{1,0},{2,0},               	{-4,-1},
						{2,1},{3,1},			{0,-2}, {1,-2},
			{0,2},{1,2},{2,2},{3,2},
			{0,3},		{2,3},					{-2,-4},{-4,-4}
		},
		// ▓▓▓
		//▓▓▓▒
		//▓▓▓▓
		//▓▓▓▓
		// ▓ ▓
		{
			   {1,0}, {2,0}, {3,0}, 		  {-1,-1},
		{0,1}, {1,1}, {2,1},				  {3,-2}, 
		{0,2}, {1,2}, {2,2}, {3,2},
		{0,3}, {1,3}, {2,3}, {3,3},
			   {1,4}, 		 {3,4},			  {-1,-5},{-3,-5}
		},
		// ▓▓▓
		//▓▓▓▒
		//▓▓▓▓
		// ▓▓▓
		// ▓ ▓
		{
				   {1,0}, {2,0}, {3,0}, 			   {-1,-1},                
			{0,1}, {1,1}, {2,1},                       {3,-2},
			{0,2}, {1,2}, {2,2}, {3,2}, 
				   {1,3}, {2,3}, {3,3}, 			   {-1,-4},
				   {1,4}, 		 {3,4},				   {-1,-5},{-3,-5}
		},
		// ▓▓▓
		//▓▓▓▒
		//▓▓▓▓
		// ▓ ▓
		{
				   {1,0}, {2,0}, {3,0}, 			  {-1,-1},
			{0,1}, {1,1}, {2,1},                      {3,-2},
			{0,2}, {1,2}, {2,2}, {3,2}, 
				   {1,3}, 		 {3,3},               {-1,-4},{-3,-4}
		},
		//▓▓▓
		//▒▓▓▓
		//▓▓▓▓
		//▓▓▓▓
		//▓ ▓
		{
			{0,0}, {1,0}, {2,0},							{-4,-1},
				   {1,1}, {2,1}, {3,1},						{0,-2},
			{0,2}, {1,2}, {2,2}, {3,2},
			{0,3}, {1,3}, {2,3}, {3,3},
			{0,4},		  {2,4},							{-2,-4},{-4,-5}
		},
		//▓▓▓
		//▒▓▓▓
		//▓▓▓▓
		//▓▓▓
		//▓ ▓
		{
			{0,0}, {1,0}, {2,0},					{-4,-1},
				   {1,1}, {2,1}, {3,1},				{0,-2}, {1,-2},
			{0,2}, {1,2}, {2,2}, {3,2},
			{0,3}, {1,3}, {2,3},					{-4,-4},
			{0,4},		  {2,4},					{-2,-5},{-4,-5}
		},
		//▓▓▓
		//▒▓▓▓
		//▓▓▓▓
		//▓ ▓
		{
			{0,0}, {1,0}, {2,0},               	{-4,-1},
				   {1,1}, {2,1}, {3,1},			{0,-2}, {1,-2},
			{0,2}, {1,2}, {2,2}, {3,2},
			{0,3},		  {2,3},				{-2,-4},{-4,-4}
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
				System.out.println(String.valueOf(x) + ":" + String.valueOf(y) + " -> " + amongus);
				count++;
				for(int[] i:AMONGUS_TYPES[amongus]) {
					try {
						if(i[0] >= 0 || i[1] >= 0)
							out.setRGB(i[0] < 0?-i[0] - 1 + x: i[0] + x, i[1] < 0? -i[1] - 1 + y : i[1] + y, detected.getRGB());
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
		return detect(offsetX, offsetY, positions, in, 1);
	}
	/**
	 * Detects the pattern in a patch of the image
	 * @param offsetX the x from where the algorithm starts
	 * @param offsetY the y from where the algorithm starts
	 * @param positions all the relative positions to check. Format: y>0 = must equal the rest; y<0 = -y must not equal the ones with y>0
	 * @param in image to check
	 * @param error quantity of allowed unmatching pixels (pixels that should not match automatically disqualify the amongus)
	 * @return true if it equals the pattern with less or equal the quantity of errors given
	 */
	protected boolean detect(int offsetX, int offsetY, int[][] positions, BufferedImage in, int error) {
		try {
			for(int _offset = 0; _offset <= error; _offset++) {
				int color;
				int offset = _offset;
				getcolor: {
					for(int[] pos : positions)
						if(pos[0] < 0 && pos[1] < 0) {
							offset++;
							break getcolor;
						}
					return false;
				}
				color = in.getRGB(offsetX + positions[offset][0], offsetY + positions[offset][1]);
				if(new Color(color).equals(detected))return false;
//				if(offsetX == 2400 && offsetY == 416)
//					System.out.println("enter");
				checkLoop: {
					for(int[] i : positions) {
						if((i[0] >= 0 && i[1] >= 0 && in.getRGB(i[0]+offsetX, i[1]+offsetY) != color) ||
						   (i[0] <  0 && i[1] <  0 && in.getRGB(-i[0] - 1 + offsetX, -i[1] - 1 +offsetY) == color)
						) {
//							if(offsetX == 2400 && offsetY == 416)
//								System.err.println("e " + i[0] + " " + i[1]);
							error--;
						}else if(
							(i[0] <  0 && i[1] >= 0 && in.getRGB(-i[0] - 1 + offsetX, i[1] + offsetY) == color) ||
							(i[1] <  0 && i[0] >= 0 && in.getRGB(i[0] + offsetX, -i[1] - 1 + offsetY) == color)) {
//							if(offsetX == 2400 && offsetY == 416)
//								System.err.println("d " + i[0] + " " + i[1]);
							break checkLoop;
						}
						if(error == -1)break checkLoop;
					}
					return true;
				}
				
			}
			return false;
		}catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}
