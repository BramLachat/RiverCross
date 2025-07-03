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
import static com.raylib.Raylib.IsKeyPressed;
import static com.raylib.Raylib.KEY_DOWN;
import static com.raylib.Raylib.KEY_LEFT;
import static com.raylib.Raylib.KEY_RIGHT;
import static com.raylib.Raylib.KEY_UP;
import static com.raylib.Raylib.SetTargetFPS;
import static com.raylib.Raylib.WindowShouldClose;

public class Main {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
    public static final int LANE_HEIGHT = WINDOW_WIDTH / 20;
    public static final int OBSTACLE_WIDTH = WINDOW_WIDTH / 20;
    public static final int PLAYER_RADIUS = LANE_HEIGHT / 2 - 2;
    public static final float OBSTACLE_SPEED = WINDOW_WIDTH / (600 / 150.0f);
    public static final Random RANDOM_GENERATOR = new Random();
    public static Player PLAYER = null;

    public static void main(String args[]) {
        InitWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "RiverCross");
        SetTargetFPS(60);

        PLAYER = new Player((float) WINDOW_WIDTH / 2, WINDOW_HEIGHT - PLAYER_RADIUS - 1);

        Lane mortalLane1 = new Lane(OBSTACLE_SPEED, LaneDirection.LEFT_TO_RIGHT, LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 2 * (LANE_HEIGHT), WINDOW_HEIGHT - 1 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane mortalLane2 = new Lane(OBSTACLE_SPEED - 50, LaneDirection.RIGHT_TO_LEFT, LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 3 * (LANE_HEIGHT), WINDOW_HEIGHT - 2 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane mortalLane3 = new Lane(OBSTACLE_SPEED + 50, LaneDirection.LEFT_TO_RIGHT, LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 4 * (LANE_HEIGHT), WINDOW_HEIGHT - 3 * (LANE_HEIGHT), OBSTACLE_WIDTH);
        Lane survivalLane1 = new Lane(OBSTACLE_SPEED, LaneDirection.RIGHT_TO_LEFT, LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 5 * (LANE_HEIGHT), WINDOW_HEIGHT - 4 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
        Lane survivalLane2 = new Lane(OBSTACLE_SPEED + 50, LaneDirection.RIGHT_TO_LEFT, LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 6 * (LANE_HEIGHT), WINDOW_HEIGHT - 5 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
        Lane survivalLane3 = new Lane(OBSTACLE_SPEED, LaneDirection.LEFT_TO_RIGHT, LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 7 * (LANE_HEIGHT), WINDOW_HEIGHT - 6 * (LANE_HEIGHT), OBSTACLE_WIDTH * 2);
        Lane mudLane = new Lane(OBSTACLE_SPEED * 0.5f, LaneDirection.LEFT_TO_RIGHT, LaneType.MUD, LANE_HEIGHT * 2, WINDOW_HEIGHT - 9 * (LANE_HEIGHT), WINDOW_HEIGHT - 7 * (LANE_HEIGHT), OBSTACLE_WIDTH);

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

            DrawCircleV(PLAYER.getPosition(), PLAYER_RADIUS, BLACK);


            // https://www.glfw.org/docs/latest/group__keys.html
            if (IsKeyPressed(KEY_UP)) {
                PLAYER.setPosY(PLAYER.getPosY() - LANE_HEIGHT);
            }
            if (IsKeyPressed(KEY_DOWN)) {
                PLAYER.setPosY(PLAYER.getPosY() + LANE_HEIGHT);
            }
            if (IsKeyPressed(KEY_LEFT)) {
                PLAYER.setPosX(PLAYER.getPosX() - OBSTACLE_WIDTH);
            }
            if (IsKeyPressed(KEY_RIGHT)) {
                PLAYER.setPosX(PLAYER.getPosX() + OBSTACLE_WIDTH);
            }

            EndDrawing();
        }
        CloseWindow();
    }
}
