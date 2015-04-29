package io;

import board.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The settings objects handles the reading and interpeting of the settings file. Returning usefull information to the game. Throws SettingsFailiureException if something is wrong with the settings file.
 */
public class Settings {

    private Map<String, String> settings;
    private String currentBoard;

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

    public Tile[][] getTiles() throws SettingsFailiureException {
        if (containsBoard()){
            int width = getBoardWidth();
            int height = getBoardHeight();
            int tileSize = getTileSize();
            Tile[][] tiles = new Tile[height][width];
            for(int row = 0; row < height; row++) {
                String[] tokens = settings.get("row" + (row+1) + currentBoard).split(":");
                for(int col = 0; col < width; col++) {
                    String token = tokens[col];
                    switch (token) {
                        case "1":
                            tiles[row][col] = new WallTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "2":
                            tiles[row][col] = new PitTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "3":
                            tiles[row][col] = new RepairTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "4":
                            tiles[row][col] = new FireTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "5":
                            tiles[row][col] = new RotateRightTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "6":
                            tiles[row][col] = new RotateLeftTile(col * tileSize, row * tileSize, tileSize);
                            break;
                        case "F1":
                            tiles[row][col] = new Tile(col * tileSize, row * tileSize, tileSize);
                            settings.put(token, Integer.toString(col * tileSize) + ";" + Integer.toString(row * tileSize));
                            break;
                        case "F2":
                            tiles[row][col] = new Tile(col * tileSize, row * tileSize, tileSize);
                            settings.put(token, Integer.toString(col * tileSize) + ";" + Integer.toString(row * tileSize));
                            break;
                        case "F3":
                            tiles[row][col] = new Tile(col * tileSize, row * tileSize, tileSize);
                            settings.put(token, Integer.toString(col * tileSize) + ";" + Integer.toString(row * tileSize));
                            break;
                        default:
                            tiles[row][col] = new Tile(col * tileSize, row * tileSize, tileSize);
                    }
                }
            }
            return tiles;
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
        }
        return false;
    }


    public ArrayList<int[]> getFlagPositions() {

        ArrayList<int[]> flagPositions = new ArrayList<>();
        int i = 1;
        while(settings.containsKey("F" + Integer.toString(i))) {
            String flagpos = settings.get("F" + Integer.toString(i));
            String[] flagXY = flagpos.split(";");
            int[] pos = new int[2];
            pos[0] = Integer.parseInt(flagXY[0]);
            pos[1] = Integer.parseInt(flagXY[1]);
            flagPositions.add(pos);
            i++;
        }
        return flagPositions;
    }



}
