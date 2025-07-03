import java.util.Random;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.RAYWHITE;
import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.CheckCollisionCircleRec;
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
        System.out.println("OBSTACLE_SPEED: " + OBSTACLE_SPEED);

        PLAYER = new Player((float) WINDOW_WIDTH / 2, WINDOW_HEIGHT - PLAYER_RADIUS - 1);

        Lane lane1 = new Lane(OBSTACLE_SPEED, LaneDirection.LEFT_TO_RIGHT, RANDOM_GENERATOR.nextInt(2000) + 1000, LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 2 * (LANE_HEIGHT), WINDOW_HEIGHT - 1 * (LANE_HEIGHT));
        Lane lane2 = new Lane(OBSTACLE_SPEED - 50, LaneDirection.RIGHT_TO_LEFT, RANDOM_GENERATOR.nextInt(2000) + 1000, LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 3 * (LANE_HEIGHT), WINDOW_HEIGHT - 2 * (LANE_HEIGHT));
        Lane lane3 = new Lane(OBSTACLE_SPEED + 50, LaneDirection.LEFT_TO_RIGHT, RANDOM_GENERATOR.nextInt(2000) + 1000, LaneType.MORTAL, LANE_HEIGHT, WINDOW_HEIGHT - 4 * (LANE_HEIGHT), WINDOW_HEIGHT - 3 * (LANE_HEIGHT));
        Lane lane4 = new Lane(OBSTACLE_SPEED, LaneDirection.RIGHT_TO_LEFT, RANDOM_GENERATOR.nextInt(2000) + 1000, LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 5 * (LANE_HEIGHT), WINDOW_HEIGHT - 4 * (LANE_HEIGHT));
        Lane lane5 = new Lane(OBSTACLE_SPEED + 50, LaneDirection.RIGHT_TO_LEFT, RANDOM_GENERATOR.nextInt(2000) + 1000, LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 6 * (LANE_HEIGHT), WINDOW_HEIGHT - 5 * (LANE_HEIGHT));
        Lane lane6 = new Lane(OBSTACLE_SPEED, LaneDirection.LEFT_TO_RIGHT, RANDOM_GENERATOR.nextInt(2000) + 1000, LaneType.SURVIVAL, LANE_HEIGHT, WINDOW_HEIGHT - 7 * (LANE_HEIGHT), WINDOW_HEIGHT - 6 * (LANE_HEIGHT));

        while (!WindowShouldClose()) {
            BeginDrawing();
            ClearBackground(RAYWHITE);
            DrawFPS(0, 0);
            float deltaTime = GetFrameTime(); // Get the time elapsed since last frame

            lane1.draw(deltaTime);
            lane2.draw(deltaTime);
            lane3.draw(deltaTime);
            lane4.draw(deltaTime);
            lane5.draw(deltaTime);
            lane6.draw(deltaTime);

            DrawCircleV(PLAYER.getPosition(), PLAYER_RADIUS, BLACK);

            if (lane1.shouldSpawnNewObstacle()) {
                lane1.addObstacle(OBSTACLE_WIDTH);
                lane1.setNextObstacleCreationDelay(RANDOM_GENERATOR.nextInt(2000) + 1000);
            }

            if (lane2.shouldSpawnNewObstacle()) {
                lane2.addObstacle(OBSTACLE_WIDTH);
                lane2.setNextObstacleCreationDelay(RANDOM_GENERATOR.nextInt(2000) + 1000);
            }

            if (lane3.shouldSpawnNewObstacle()) {
                lane3.addObstacle(OBSTACLE_WIDTH);
                lane3.setNextObstacleCreationDelay(RANDOM_GENERATOR.nextInt(2000) + 1000);
            }

            if (lane4.shouldSpawnNewObstacle()) {
                lane4.addObstacle(OBSTACLE_WIDTH * 2);
                lane4.setNextObstacleCreationDelay(RANDOM_GENERATOR.nextInt(2000) + 1000);
            }

            if (lane5.shouldSpawnNewObstacle()) {
                lane5.addObstacle(OBSTACLE_WIDTH * 2);
                lane5.setNextObstacleCreationDelay(RANDOM_GENERATOR.nextInt(2000) + 1000);
            }

            if (lane6.shouldSpawnNewObstacle()) {
                lane6.addObstacle(OBSTACLE_WIDTH * 2);
                lane6.setNextObstacleCreationDelay(RANDOM_GENERATOR.nextInt(2000) + 1000);
            }

            for (Obstacle obstacle : lane1.getObstacleList()) {
                if (CheckCollisionCircleRec(PLAYER.getPosition(), PLAYER_RADIUS * 0.5f, obstacle.getRectangle())) {
                    PLAYER.reset();
                }
            }

            for (Obstacle obstacle : lane2.getObstacleList()) {
                if (CheckCollisionCircleRec(PLAYER.getPosition(), PLAYER_RADIUS * 0.5f, obstacle.getRectangle())) {
                    PLAYER.reset();
                }
            }

            for (Obstacle obstacle : lane3.getObstacleList()) {
                if (CheckCollisionCircleRec(PLAYER.getPosition(), PLAYER_RADIUS * 0.5f, obstacle.getRectangle())) {
                    PLAYER.reset();
                }
            }

            boolean isOnTopOfAnObstacle = false;
            for (Obstacle obstacle : lane4.getObstacleList()) {
                if (CheckCollisionCircleRec(PLAYER.getPosition(), 0, obstacle.getRectangle())) {
                    PLAYER.move(lane4.getDirection(), lane4.getSpeed(), deltaTime);
                    isOnTopOfAnObstacle = true;
                }
            }
            if (PLAYER.isInsideLane(lane4) && !isOnTopOfAnObstacle) {
                PLAYER.reset();
            }

            isOnTopOfAnObstacle = false;
            for (Obstacle obstacle : lane5.getObstacleList()) {
                if (CheckCollisionCircleRec(PLAYER.getPosition(), 0, obstacle.getRectangle())) {
                    PLAYER.move(lane5.getDirection(), lane5.getSpeed(), deltaTime);
                    isOnTopOfAnObstacle = true;
                }
            }
            if (PLAYER.isInsideLane(lane5) && !isOnTopOfAnObstacle) {
                PLAYER.reset();
            }

            isOnTopOfAnObstacle = false;
            for (Obstacle obstacle : lane6.getObstacleList()) {
                if (CheckCollisionCircleRec(PLAYER.getPosition(), 0, obstacle.getRectangle())) {
                    PLAYER.move(lane6.getDirection(), lane6.getSpeed(), deltaTime);
                    isOnTopOfAnObstacle = true;
                }
            }
            if (PLAYER.isInsideLane(lane6) && !isOnTopOfAnObstacle) {
                PLAYER.reset();
            }


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
