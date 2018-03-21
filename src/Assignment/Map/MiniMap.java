package Assignment.Map;

import Assignment.MainGame.EastPanel;
import Assignment.MainGame.InfoPanel;
import Assignment.Utilities.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static Assignment.Map.MapConstants.BOSS_ROOM;
import static Assignment.Map.MapConstants.REGULAR_ROOM;
import static Assignment.Map.MapConstants.SHOP_ROOM;

/**
 * Created by el16035 on 15/03/2018.
 */
public class MiniMap {
    private static final Color NEIGHBOUR_ROOM_COLOR = Color.GRAY;
    private static final Color VISITED_ROOM_COLOR = Color.BLACK;
    private static final Color SQUARE_COLOR = Color.LIGHT_GRAY;

    private final Image PLAYER_SHIP_IMAGE = Sprite.PLAYER_SHIP;

    private EastPanel container;
    private MapHelper mapHelper;
    private int mapSquareSize;

    private Color[][] colorMap;
    private boolean[][] map;
    private int[][] exploredRooms;
    private int[] mapPos;

    private boolean testing = true;

    private int miniMapInitY;


    public MiniMap(EastPanel container, MapHelper mapHelper, int initY) {
        this.container = container;
        this.mapHelper = mapHelper;
        //updateMap();
       this.miniMapInitY = initY;
    }

    public void updateMap() {
        map = mapHelper.getWhichRoomsExist();
        colorMap = new Color[map.length][map[0].length];
        double width = container.getPreferredSize().width / map.length;

        mapSquareSize = (int) width;
        mapPos = mapHelper.getMapPos();
        exploredRooms = mapHelper.getExploredRooms();
    }


    //TODO: draw squares around all rooms
    public void draw(Graphics2D g) {
        for (int i = 0; i < colorMap.length; i++) {
            for (int j = 0; j < colorMap[i].length; j++) {
                if (map[i][j]) {
                    int expRoom = exploredRooms[i][j];

                    if (expRoom != 0 || testing) {
                        if (expRoom == 1) {
                            g.setColor(NEIGHBOUR_ROOM_COLOR);
                        } else {
                            g.setColor(VISITED_ROOM_COLOR);
                        }
                        g.fillRect(j * mapSquareSize, i * mapSquareSize + miniMapInitY, mapSquareSize, mapSquareSize);

                        g.setColor(SQUARE_COLOR);
                        g.drawRect(j * mapSquareSize, i * mapSquareSize + miniMapInitY, mapSquareSize, mapSquareSize);

                        Room room = mapHelper.getRoomAtPos(new int[]{i,j});
                        if (room != null){
                            String type = room.getRoomType();
                            if (!type.equals(REGULAR_ROOM)){
                                String letter = "";
                                if (type.equals(BOSS_ROOM)){
                                    letter = "B";
                                }else if (type.equals(SHOP_ROOM)){
                                    letter = "S";
                                }

                                g.setColor(Color.WHITE);
                                g.drawString(letter, (j+1) * mapSquareSize - (mapSquareSize/2), (i+1) *mapSquareSize + (mapSquareSize/2));
                            }
                        }
                    }
                }
            }
        }

        double imW = PLAYER_SHIP_IMAGE.getWidth(null);
        double imH = PLAYER_SHIP_IMAGE.getHeight(null);
        AffineTransform t = new AffineTransform();

        t.scale(mapSquareSize / imW / 2, mapSquareSize / imH / 2);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();

        int posX = mapPos[1] * mapSquareSize + (mapSquareSize / 2);
        int posY = mapPos[0] * mapSquareSize + (mapSquareSize / 2) + miniMapInitY;
        g.translate(posX, posY);
        g.drawImage(PLAYER_SHIP_IMAGE, t, null);
        g.setTransform(t0);

        int mapSize = mapSquareSize * map.length;

        g.setColor(Color.BLACK);
        g.drawRect(0, miniMapInitY, mapSize, mapSize);

    }
}
