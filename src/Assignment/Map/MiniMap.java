package Assignment.Map;

import Assignment.MainGame.EastPanel;
import Assignment.Utilities.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by el16035 on 15/03/2018.
 */
public class MiniMap {
    private static final Color NEIGHBOUR_ROOM_COLOR = Color.GRAY;
    private static final Color ROOM_MAP_SQUARE = Color.BLACK;

    private final Image PLAYER_SHIP_IMAGE = Sprite.PLAYER_SHIP;

    private EastPanel container;
    private MapHelper mapHelper;
    private int mapSquareSize;
    private Color[][] currentMap;
    private boolean[][] doesMapExist;
    private int[][] exploredRooms;
    private int[] mapPos;

    private boolean testing = true;


    public MiniMap(EastPanel container, MapHelper mapHelper) {
        this.container = container;
        this.mapHelper = mapHelper;
        //updateMap();
    }

    public void updateMap() {
        currentMap = mapHelper.getMiniMap();
        doesMapExist = mapHelper.getWhichRoomsExist();
        double width = container.getPreferredSize().width / currentMap.length;

        mapSquareSize = (int) width;
        mapPos = mapHelper.getMapPos();
        exploredRooms = mapHelper.getExploredRooms();
    }


    //TODO: draw squares around all rooms
    public void draw(Graphics2D g) {
        for (int i = 0; i < currentMap.length; i++) {
            for (int j = 0; j < currentMap[i].length; j++) {
                if (doesMapExist[i][j]) {
                    int expRoom = exploredRooms[i][j];
                    if (expRoom != 0 || testing) {
                        if (testing) {
                            g.setColor(currentMap[i][j]);
                        } else if (expRoom == 1) {
                            g.setColor(NEIGHBOUR_ROOM_COLOR);
                        } else {
                            g.setColor(currentMap[i][j]);
                        }
                        g.fillRect(j * mapSquareSize, i * mapSquareSize, mapSquareSize, mapSquareSize);
                        g.setColor(ROOM_MAP_SQUARE);
                        g.drawRect(j * mapSquareSize, i * mapSquareSize, mapSquareSize, mapSquareSize);

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
        int posY = mapPos[0] * mapSquareSize + (mapSquareSize / 2);
        g.translate(posX, posY);
        g.drawImage(PLAYER_SHIP_IMAGE, t, null);
        g.setTransform(t0);

        int mapSize = mapSquareSize * currentMap.length;

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, mapSize, mapSize);
    }
}
