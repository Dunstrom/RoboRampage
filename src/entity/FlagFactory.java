package entity;

import java.util.LinkedList;
import java.awt.Color;

public class FlagFactory
{

    private static final FlagFactory INSTANCE = new FlagFactory();

    private LinkedList<Color> flagColors;

    private FlagFactory() {
	flagColors = new LinkedList<>();
	flagColors.add(Color.GREEN);
	flagColors.add(Color.YELLOW);
	flagColors.add(Color.BLUE);
	flagColors.add(Color.PINK);
	flagColors.add(Color.RED);
	flagColors.add(Color.WHITE);
	flagColors.add(Color.BLACK);
	flagColors.add(Color.CYAN);
	flagColors.add(Color.ORANGE);
    }

    public static FlagFactory getInstance() {
	return INSTANCE;
    }

    public Flag createFlag(int x, int y) {
	if(flagColors.isEmpty()){
	    throw new IndexOutOfBoundsException("Out of new colors");
	} else {
	    return new Flag(x, y, flagColors.poll());
	}

    }

}