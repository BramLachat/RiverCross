import java.util.Random;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class Main {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9 - ((WINDOW_WIDTH / 16 * 9) % 11);
    public static final int LANE_HEIGHT = WINDOW_HEIGHT / 11;
    public static final int OBSTACLE_WIDTH = WINDOW_WIDTH / 20;
    public static final int PLAYER_RADIUS = LANE_HEIGHT / 2 - 2;
    public static Player PLAYER = null;

    public static void main(String args[]) {
        InitWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "RiverCross");
        SetTargetFPS(60);

        PLAYER = new Player((float) WINDOW_WIDTH / 2, WINDOW_HEIGHT - PLAYER_RADIUS - 1);

        Lane baseLane = new Lane(LaneType.BASE, LANE_HEIGHT, WINDOW_HEIGHT - 1 * (LANE_HEIGHT), WINDOW_HEIGHT, 0);
        Lane mortalLane1 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 2 * (LANE_HEIGHT), WINDOW_HEIGHT - 1 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane mortalLane2 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 3 * (LANE_HEIGHT), WINDOW_HEIGHT - 2 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane mortalLane3 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 4 * (LANE_HEIGHT), WINDOW_HEIGHT - 3 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane survivalLane1 = new Lane(LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 5 * (LANE_HEIGHT), WINDOW_HEIGHT - 4 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
        Lane survivalLane2 = new Lane(LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 6 * (LANE_HEIGHT), WINDOW_HEIGHT - 5 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
        Lane survivalLane3 = new Lane(LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 7 * (LANE_HEIGHT), WINDOW_HEIGHT - 6 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
        Lane mudLane = new Lane(LaneType.MUD, LANE_HEIGHT * 2, WINDOW_HEIGHT - 9 * (LANE_HEIGHT), WINDOW_HEIGHT - 7 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane mortalLane4 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 10 * (LANE_HEIGHT), WINDOW_HEIGHT - 9 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane finishLane = new Lane(LaneType.BASE, LANE_HEIGHT, WINDOW_HEIGHT - 11 * (LANE_HEIGHT), WINDOW_HEIGHT - 10 * (LANE_HEIGHT), 0);

        while (!WindowShouldClose()) {
            BeginDrawing();
            ClearBackground(RAYWHITE);
            float deltaTime = GetFrameTime(); // Get the time elapsed since last frame

            baseLane.start(deltaTime);
            mortalLane1.start(deltaTime);
            mortalLane2.start(deltaTime);
            mortalLane3.start(deltaTime);
            survivalLane1.start(deltaTime);
            survivalLane2.start(deltaTime);
            survivalLane3.start(deltaTime);
            mudLane.start(deltaTime);
            mortalLane4.start(deltaTime);
            finishLane.start(deltaTime);

            PLAYER.render();
            DrawFPS(0, 0);
            EndDrawing();
        }
        CloseWindow();
    }
}
