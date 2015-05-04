package entity;

import board.SettingsFailiureException;
import io.Settings;

import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Color;
import java.util.Collection;
import java.util.List;


/** A class that creates flags and makes sure that they don't get the same color in one particular game. Flags should only be created through this factory. */
public final class FlagFactory {

    private static final FlagFactory INSTANCE = new FlagFactory();

    private LinkedList<Color> flagColors;

    private FlagFactory() {
		flagColors = new LinkedList<>();
		addColors();
    }

    private void addColors() {
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

    public Collection<BoardEntity> createFlags(Settings settings) throws SettingsFailiureException {
	List<int[]> flagPositions = settings.getFlagPositions();
	Collection<BoardEntity> flags = new ArrayList<>();
	for(int[] pos : flagPositions) {
	    flags.add(new Flag(pos[0], pos[1], flagColors.poll()));
	    if(flagColors.isEmpty()) {
		addColors();
	    }
	}

	return flags;

    }

}
