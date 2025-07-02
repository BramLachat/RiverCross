import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Colors.RED;
import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.CheckCollisionCircleRec;
import static com.raylib.Raylib.ClearBackground;
import static com.raylib.Raylib.CloseWindow;
import static com.raylib.Raylib.DrawCircleV;
import static com.raylib.Raylib.DrawFPS;
import static com.raylib.Raylib.DrawRectangle;
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

    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
    public static final int OBSTACLE_HEIGHT = WINDOW_WIDTH / 20;
    public static final int OBSTACLE_WIDTH = WINDOW_WIDTH / 20;
    public static final int PLAYER_RADIUS = OBSTACLE_HEIGHT / 2 - 2;
    public static final float OBSTACLE_SPEED = WINDOW_WIDTH / (600 / 150.0f);
    public static final Random RANDOM_GENERATOR = new Random();
    public static Player PLAYER = null;

    public static void main(String args[]) {
        InitWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "RiverCross");
        SetTargetFPS(60);

        PLAYER = new Player((float) WINDOW_WIDTH / 2, WINDOW_HEIGHT - PLAYER_RADIUS - 1);

        Lane lane1 = new Lane(OBSTACLE_SPEED, LaneDirection.LEFT_TO_RIGHT);
        lane1.setNextObstacleCreationDelay(RANDOM_GENERATOR.nextInt(2000) + 1000);
        Lane lane2 = new Lane(OBSTACLE_SPEED, LaneDirection.RIGHT_TO_LEFT);
        lane2.setNextObstacleCreationDelay(RANDOM_GENERATOR.nextInt(2000) + 1000);

        while (!WindowShouldClose()) {
            BeginDrawing();
            ClearBackground(RAYWHITE);
            DrawFPS(0, 0);
            float deltaTime = GetFrameTime(); // Get the time elapsed since last frame


            if (lane1.shouldSpawnNewObstacle()) {
                int obstaclePosY = WINDOW_HEIGHT - 2 * (OBSTACLE_HEIGHT);
                lane1.addObstacle(new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, 0, obstaclePosY));
                lane1.setNextObstacleCreationDelay(RANDOM_GENERATOR.nextInt(2000) + 1000);
            }

            if (lane2.shouldSpawnNewObstacle()) {
                int obstaclePosY = WINDOW_HEIGHT - 3 * (OBSTACLE_HEIGHT);
                lane2.addObstacle(new Obstacle(OBSTACLE_HEIGHT, OBSTACLE_WIDTH, WINDOW_WIDTH, obstaclePosY));
                lane2.setNextObstacleCreationDelay(RANDOM_GENERATOR.nextInt(2000) + 1000);
            }

            lane1.draw(deltaTime);
            lane2.draw(deltaTime);

            DrawCircleV(PLAYER.getPosition(), PLAYER_RADIUS, BLACK);

            // https://www.glfw.org/docs/latest/group__keys.html
            if (IsKeyPressed(KEY_UP)) {
                PLAYER.setPosY(PLAYER.getPosY() - OBSTACLE_HEIGHT);
            }
            if (IsKeyPressed(KEY_DOWN)) {
                PLAYER.setPosY(PLAYER.getPosY() + OBSTACLE_HEIGHT);
            }
            if (IsKeyPressed(KEY_LEFT)) {
                PLAYER.setPosX(PLAYER.getPosX() - OBSTACLE_WIDTH);
            }
            if (IsKeyPressed(KEY_RIGHT)) {
                PLAYER.setPosX(PLAYER.getPosX() + OBSTACLE_WIDTH);
            }

            EndDrawing();

            for (Obstacle obstacle : lane1.getObstacleList()) {
                if (CheckCollisionCircleRec(PLAYER.getPosition(), PLAYER_RADIUS, obstacle.getRectangle())) {
                    PLAYER.reset();
                }
            }
        }
        CloseWindow();
    }
}
