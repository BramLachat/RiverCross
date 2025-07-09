import java.util.ArrayList;
import java.util.List;

import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Raylib.*;

public class Main {

    public static final int WINDOW_WIDTH = 1000;
    public static final int NUMBER_OF_LANES = 11;
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9 - ((WINDOW_WIDTH / 16 * 9) % NUMBER_OF_LANES);
    public static final int LANE_HEIGHT = WINDOW_HEIGHT / NUMBER_OF_LANES;
    public static final int OBSTACLE_WIDTH = WINDOW_WIDTH / 20;
    public static final int PLAYER_RADIUS = LANE_HEIGHT / 2 - 2;
    public static Player PLAYER = null;

    public static void main(String args[]) {
        InitWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "RiverCross");
        SetTargetFPS(60);

        // IMPORTANT: Set texture filter to Nearest-Neighbor for crisp pixel scaling
        SetTextureFilter(Wall.TEXTURE, TEXTURE_FILTER_BILINEAR);
        SetTextureFilter(VirtualGuy.TEXTURE, TEXTURE_FILTER_BILINEAR);
        SetTextureFilter(ChainSaw.TEXTURE, TEXTURE_FILTER_BILINEAR);

        PLAYER = new Player((float) WINDOW_WIDTH / 2, WINDOW_HEIGHT - PLAYER_RADIUS - 1);
        List<Lane> lanes = new ArrayList<>();

        lanes.add(new Lane(LaneType.BASE, WINDOW_HEIGHT - 1 * (LANE_HEIGHT), WINDOW_HEIGHT, 0));
        for (int laneIndex = 2; laneIndex < NUMBER_OF_LANES; laneIndex++) {
            LaneType laneType = RandomSettingsGenerator.getLaneType();
            lanes.add(new Lane(laneType, WINDOW_HEIGHT - laneIndex * (LANE_HEIGHT), WINDOW_HEIGHT - (laneIndex-1) * (LANE_HEIGHT), OBSTACLE_WIDTH));
        }
        lanes.add(new Lane(LaneType.BASE, WINDOW_HEIGHT - NUMBER_OF_LANES * (LANE_HEIGHT), WINDOW_HEIGHT - (NUMBER_OF_LANES - 1) * (LANE_HEIGHT), 0));

//        Lane mortalLane1 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 2 * (LANE_HEIGHT), WINDOW_HEIGHT - 1 * (LANE_HEIGHT), OBSTACLE_WIDTH);
//        Lane mortalLane2 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 3 * (LANE_HEIGHT), WINDOW_HEIGHT - 2 * (LANE_HEIGHT), OBSTACLE_WIDTH);
//        Lane mortalLane3 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 4 * (LANE_HEIGHT), WINDOW_HEIGHT - 3 * (LANE_HEIGHT), OBSTACLE_WIDTH);
//        Lane survivalLane1 = new Lane(LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 5 * (LANE_HEIGHT), WINDOW_HEIGHT - 4 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
//        Lane survivalLane2 = new Lane(LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 6 * (LANE_HEIGHT), WINDOW_HEIGHT - 5 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
//        Lane survivalLane3 = new Lane(LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 7 * (LANE_HEIGHT), WINDOW_HEIGHT - 6 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
//        Lane mudLane = new Lane(LaneType.MUD, LANE_HEIGHT * 2, WINDOW_HEIGHT - 9 * (LANE_HEIGHT), WINDOW_HEIGHT - 7 * (LANE_HEIGHT), OBSTACLE_WIDTH);
//        Lane mortalLane4 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 10 * (LANE_HEIGHT), WINDOW_HEIGHT - 9 * (LANE_HEIGHT), OBSTACLE_WIDTH);

        while (!WindowShouldClose()) {
            BeginDrawing();
            ClearBackground(RAYWHITE);

            for (int laneIndex = 0; laneIndex < NUMBER_OF_LANES; laneIndex++) {
                lanes.get(laneIndex).start();
            }

            PLAYER.render();
            DrawFPS(0, 0);
            EndDrawing();
        }
        CloseWindow();
    }
}
