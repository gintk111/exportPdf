package demo.service;

public final class Constant {
	/** user space units per inch */
	public static final float POINTS_PER_INCH = 72;

	/** user space units per millimeter */
	public static final float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;
	
	// A4 SIZE
	public static final float A4_HEIGHT = 297;
	public static final float A4_WIDTH = 210;
	
	// LINE CUT
	public static final float LINE_VERTICAL = 2;
	public static final float LINE_HORIZONTAL = 6;

	// CARD
	public static final float CARD_HEIGHT = 55;
	public static final float CARD_WIDTH = 91;
	public static final float SPACE_CARD = 3;

	// SQUARE
	public static final float SQUARE = 6;
	public static final float SPACE_SQUARE = 0.5f;

	// SVG
	public static final float SVG_WIDTH = 340.28f;
	public static final float SVG_HEIGHT = 209.96f;

	// Avatar
	public static final float AVT_WIDTH = 89;
	public static final float AVT_HEIGHT = 104;
	public static final float AVT_X = 98;
	public static final float AVT_Y = 146;
	public static final float AVT_ROTATE = 90;

	// Company name
	public static final float CNAME_X = 12;
	public static final float CNAME_Y = 150;
	public static final float CNAME_ROTATE = 90;
	public static final String CNAME_FONT = "Font";
	public static final float CNAME_FONTSIZE = 12;

	// Logo
	public static final float LOGO_WIDTH = 30;
	public static final float LOGO_HEIGHT = 27;
	public static final float LOGO_X = 10;
	public static final float LOGO_Y = 190;
	public static final float LOGO_ROTATE = 90;

	// Full name
	public static final float FNAME_X = 224;
	public static final float FNAME_Y = 135;
	public static final float FNAME_ROTATE = 90;
	public static final String FNAME_FONT = "Font Bold";
	public static final float FNAME_FONTSIZE = 18;
}
