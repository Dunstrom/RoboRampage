package io;

import board.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The settings objects handles the reading and interpeting of the settings file. Returning usefull information to the game. Throws SettingsFailiureException if something is wrong with the settings file.
 */
public class Settings {

    private Map<String, String> settings;
    private String currentBoard;
    private Tile[][] tiles;

    public Settings(String fileName) throws SettingsFailiureException{
        assert(fileName.matches(".txt$")); // Asserts if the file isn't a text file.
        settings = new HashMap<>();
        URL url = Settings.class.getResource("../Resources/" + fileName);
        assert(url != null); //Asserts if the url is null.
        try(BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine;
            while((inputLine = in.readLine()) != null) {//Common practice...
                String[] strings = inputLine.split(";");
                settings.put(strings[0], strings[1]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        currentBoard = getCurrentBoard();
        readTiles();
    }

    public String getCurrentBoard() throws SettingsFailiureException{
        if(settings.containsKey("currentBoard")){
            return settings.get("currentBoard");
        }else {
            throw new SettingsFailiureException("currentBoard not defined in settings.");
        }
    }

    public int getBoardWidth() throws SettingsFailiureException {
        if(containsBoard()){
            return Integer.parseInt(settings.get(currentBoard + "Width"));
        }else {
            throw new SettingsFailiureException("Board name " + currentBoard + " not found");
        }
    }

    public int getBoardHeight() throws SettingsFailiureException {
        if(containsBoard()){
            return Integer.parseInt(settings.get(currentBoard + "Height"));
        }else {
            throw new SettingsFailiureException("Board name " + currentBoard + " not found");
        }
    }

    public int getTileSize() throws SettingsFailiureException{
        if(settings.containsKey("tileSize")){
            return Integer.parseInt(settings.get("tileSize"));
        } else {
            throw new SettingsFailiureException("tileSize not defined in settings.");
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Reads the settingsfile and saves the tiles and starting positions for both players and flags.
     * token - what it represents
     * '1' - Wall Tile
     * '2' - Pit Tile
     * '3' - Repair Tile
     * '4' - Fire Tile
     * '5' - Rotate Right Tile
     * '6' - Rotate Left Tile
     * 'F(1 to 10)' - Flag position for flag 1 to 10
     * 'P(1 to 10)' - Player position for player 1 to 10
     * @throws SettingsFailiureException
     */
    private void readTiles() throws SettingsFailiureException {
        if (containsBoard()){
            int width = getBoardWidth();
            int height = getBoardHeight();
            int tileSize = getTileSize();
            Tile[][] readTiles = new Tile[height][width];
            for(int row = 0; row < height; row++) {
                String[] tokens = settings.get("row" + (row+1) + currentBoard).split(":");
                for(int col = 0; col < width; col++) {
                    String token = tokens[col];
                    switch (token) {
                        case "1":
                            readTiles[row][col] = new WallTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "2":
                            readTiles[row][col] = new PitTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "3":
                            readTiles[row][col] = new RepairTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "4":
                            readTiles[row][col] = new FireTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "5":
                            readTiles[row][col] = new RotateRightTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "6":
                            readTiles[row][col] = new RotateLeftTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case"F1":case"F2":case"F3":case "F4":case"F5":case"F6":case"F7":case"F8":case "P1":case"P2":case"P3":case"P4":case"P5":case"P6":case"P7":case"P8"://Positions
                            readTiles[row][col] = new Tile(col * tileSize, row * tileSize, tileSize);
                            settings.put(token, Integer.toString(col * tileSize) + ";" + Integer.toString(row * tileSize));
                            break;
                        default:
                            readTiles[row][col] = new Tile(col * tileSize, row * tileSize, tileSize);
                    }
                }
            }
            tiles = readTiles;
        }else {
            throw new SettingsFailiureException("Board name " + currentBoard + " not found");
        }
    }

    public boolean containsBoard() {
        int i = 1;
        while(settings.containsKey("boardName"+i)) {
            if(settings.get("boardName"+i).equals(currentBoard)){
                return true;
            }
            i++;
        }
        return false;
    }


    public List<int[]> getFlagPositions() throws SettingsFailiureException {

        List<int[]> flagPositions = readPositions("F");
        if(flagPositions.isEmpty()){
            throw new SettingsFailiureException("No Flags on the map.");
        }

        return flagPositions;
    }

    public int getPlayerMax() throws SettingsFailiureException {

        List<int[]> playerPositions = readPositions("P");
        if(playerPositions.isEmpty()){
            throw new SettingsFailiureException("No players on the map.");
        }

        return playerPositions.size();

    }

    public List<int[]> getPlayerPositions() throws SettingsFailiureException {

            List<int[]> playerPositions = readPositions("P");
            if(playerPositions.isEmpty()){
                throw new SettingsFailiureException("No Players on the map.");
            }

            return playerPositions;
        }

    private List<int[]> readPositions(String id) {
        List<int[]> positions = new ArrayList<>();
        int i = 1;
        while(settings.containsKey(id + Integer.toString(i))) {
            String posString = settings.get(id + Integer.toString(i));
            String[] xYString = posString.split(";");
            int[] pos = new int[2];
            pos[0] = Integer.parseInt(xYString[0]);
            pos[1] = Integer.parseInt(xYString[1]);
            positions.add(pos);
            i++;
        }
        return positions;
    }

}
