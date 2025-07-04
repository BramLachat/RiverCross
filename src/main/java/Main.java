import java.util.Random;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.ClearBackground;
import static com.raylib.Raylib.CloseWindow;
import static com.raylib.Raylib.DrawCircleV;
import static com.raylib.Raylib.DrawFPS;
import static com.raylib.Raylib.EndDrawing;
import static com.raylib.Raylib.GetFrameTime;
import static com.raylib.Raylib.InitWindow;
import static com.raylib.Raylib.SetTargetFPS;
import static com.raylib.Raylib.WindowShouldClose;

public class Main {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
    public static final int LANE_HEIGHT = WINDOW_WIDTH / 20;
    public static final int OBSTACLE_WIDTH = WINDOW_WIDTH / 20;
    public static final int PLAYER_RADIUS = LANE_HEIGHT / 2 - 2;
    public static final Random RANDOM_GENERATOR = new Random();
    public static Player PLAYER = null;

    public static void main(String args[]) {
        InitWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "RiverCross");
        SetTargetFPS(60);

        PLAYER = new Player((float) WINDOW_WIDTH / 2, WINDOW_HEIGHT - PLAYER_RADIUS - 1);

        Lane mortalLane1 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 2 * (LANE_HEIGHT), WINDOW_HEIGHT - 1 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane mortalLane2 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 3 * (LANE_HEIGHT), WINDOW_HEIGHT - 2 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane mortalLane3 = new Lane(LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 4 * (LANE_HEIGHT), WINDOW_HEIGHT - 3 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane survivalLane1 = new Lane(LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 5 * (LANE_HEIGHT), WINDOW_HEIGHT - 4 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
        Lane survivalLane2 = new Lane(LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 6 * (LANE_HEIGHT), WINDOW_HEIGHT - 5 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
        Lane survivalLane3 = new Lane(LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 7 * (LANE_HEIGHT), WINDOW_HEIGHT - 6 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
        Lane mudLane = new Lane(LaneType.MUD, LANE_HEIGHT * 2, WINDOW_HEIGHT - 9 * (LANE_HEIGHT), WINDOW_HEIGHT - 7 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane survivalLane4 = new Lane(LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 10 * (LANE_HEIGHT), WINDOW_HEIGHT - 9 * (LANE_HEIGHT), OBSTACLE_WIDTH * 10);

        while (!WindowShouldClose()) {
            BeginDrawing();
            ClearBackground(RAYWHITE);
            DrawFPS(0, 0);
            float deltaTime = GetFrameTime(); // Get the time elapsed since last frame

            mortalLane1.start(deltaTime);
            mortalLane2.start(deltaTime);
            mortalLane3.start(deltaTime);
            survivalLane1.start(deltaTime);
            survivalLane2.start(deltaTime);
            survivalLane3.start(deltaTime);
            mudLane.start(deltaTime);
            survivalLane4.start(deltaTime);

            DrawCircleV(PLAYER.getPosition(), PLAYER_RADIUS, BLACK);

            PLAYER.listenForUserInput();

            EndDrawing();
        }
        CloseWindow();
    }
}
