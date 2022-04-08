import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public abstract class Detector{
	public int count = 0;
	
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
	public Detector(BufferedImage bi) {
		this.bi = bi;
	}
	Color detected = new Color(15, 15, 15);
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
						out.setRGB(i[0]+x, i[1]+y, detected.getRGB());
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
	protected boolean detect(int offsetX, int offsetY, int[][] positions, BufferedImage in) {
		try {
			int color = in.getRGB(offsetX + positions[0][0], offsetY + positions[0][0]);
			if(new Color(color).equals(detected))return false;
			for(int[] i : positions)
				if(i[1] >= 0 && in.getRGB(i[0]+offsetX, i[1]+offsetY) != color) {
					return false;
				}else if(i[1] < 0 && in.getRGB(i[0]+offsetX, Math.abs(i[1])+offsetY) == color)
					return false;
			return true;
		}catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}
