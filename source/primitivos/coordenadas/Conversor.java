package primitivos.coordenadas;

public class Conversor {

	public static int relativeToPixel(String relative, int screenSize) {
		
		return (int) (Float.parseFloat(relative) * screenSize);
		
	}
	
	public static Double pixelToRelative(double pixel, int screenSize) {
		
		return (pixel / screenSize);
		
	}
	
	public static String fileExt(String fileName) {
		String extension = "";

		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
		    extension = fileName.substring(i+1);
		}
		
		return extension;
	}
	
}
