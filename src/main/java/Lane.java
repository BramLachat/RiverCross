import com.raylib.Raylib;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.CheckCollisionCircleRec;
import static com.raylib.Raylib.DrawRectangle;

public class Lane {
    private List<Obstacle> obstacleList;
    private Instant lastCreatedObstacleTimestamp; // milliseconds
    private int nextObstacleCreationDelay; // milliseconds
    private float speed;
    private LaneDirection direction;
    private LaneType type;
    private int height;
    private int topY;
    private int bottomY;
    private int obstacleWidth;

    public Lane(LaneType type, int topY, int bottomY, int obstacleWidth) {
        this.obstacleList = new ArrayList<>();
        this.lastCreatedObstacleTimestamp = Instant.now();
        this.nextObstacleCreationDelay = 0;
        this.speed = RandomSettingsGenerator.getObstacleSpeed();
        this.direction = RandomSettingsGenerator.getDirection();
        this.nextObstacleCreationDelay = 0;
        this.type = type;
        this.height = Main.LANE_HEIGHT;
        this.topY = topY;
        this.bottomY = bottomY;
        this.obstacleWidth = obstacleWidth;
    }

    public boolean shouldSpawnNewObstacle() {
        if (type == LaneType.START || type == LaneType.FINISH) {
            return false;
        }
        return Duration.between(lastCreatedObstacleTimestamp, Instant.now()).toMillis() > nextObstacleCreationDelay;
    }

    public void addObstacle() {
        if (direction == LaneDirection.LEFT_TO_RIGHT) {
            obstacleList.add(new Obstacle(height, obstacleWidth, 0 - obstacleWidth, topY));
            lastCreatedObstacleTimestamp = Instant.now();
        }
        if (direction == LaneDirection.RIGHT_TO_LEFT) {
            obstacleList.add(new Obstacle(height, obstacleWidth, Main.WINDOW_WIDTH, topY));
            lastCreatedObstacleTimestamp = Instant.now();
        }
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public void removeObstacles(List<Obstacle> obstaclesToRemove) {
        this.obstacleList.removeAll(obstaclesToRemove);
    }

    public void start() {
        if (this.shouldSpawnNewObstacle()) {
            this.addObstacle();
            this.nextObstacleCreationDelay = RandomSettingsGenerator.getObstacleCreationDelay(this.type);
        }

        if (type == LaneType.MORTAL) {
            checkCollision();
        }
        if (type == LaneType.SURVIVAL) {
            checkOnTop();
            DrawRectangle(0, topY, Main.WINDOW_WIDTH, height, DARKBLUE);
        }
        if (type == LaneType.MUD) {
            checkCollision();
            DrawRectangle(0, topY, Main.WINDOW_WIDTH, height, BROWN);
        }
        if (type == LaneType.START || type == LaneType.FINISH) {
            DrawRectangle(0, topY, Main.WINDOW_WIDTH, height, SKYBLUE);
        }

        List<Obstacle> obstaclesToRemove = new ArrayList<>();
        // TODO @Bram: clean this up!
        Raylib.Color obstacleColor = (this.type == LaneType.MORTAL || this.type == LaneType.MUD) ? RED : GREEN;
        // TODO @Bram: end!
        for (Obstacle obstacle : this.obstacleList) {
            if (obstacle.isInsideWindow(this.direction)) {
                if (type == LaneType.MORTAL) {
                    obstacle.renderChainSawAnimation();
                } else if (type == LaneType.SURVIVAL) {
                    obstacle.renderStaticWall();
                } else {
                    DrawRectangle((int) obstacle.getX(), (int) obstacle.getY(), (int) obstacle.getWidth(), (int) obstacle.getHeight(), obstacleColor);
                }
                obstacle.moveToNextPosition(this.direction, this.speed);
            } else {
                obstaclesToRemove.add(obstacle);
            }
        }
        this.removeObstacles(obstaclesToRemove);
        
        this.centerPlayerPosY();
        this.checkPlayerFinished();
    }

    private void centerPlayerPosY() {
        if (Main.PLAYER.isInsideLane(this) && type != LaneType.MUD) {
            Main.PLAYER.setPosY(this.getPlayerCenterPosY());
        }
    }

    private void checkPlayerFinished() {
        if (Main.PLAYER.isInsideLane(this) && type == LaneType.FINISH) {
            Main.PLAYER.addPoint();
        }
    }

    private void checkCollision() {
        Main.PLAYER.setInsideMud(false);
        if (type == LaneType.MUD && Main.PLAYER.isInsideLane(this)) {
            Main.PLAYER.setInsideMud(true);
        }
        for (Obstacle obstacle : this.getObstacleList()) {
            if (CheckCollisionCircleRec(Main.PLAYER.getPosition(), Main.PLAYER_RADIUS * 0.5f, obstacle.getRectangle())) {
                Main.PLAYER.reset();
            }
        }
    }

    private void checkOnTop() {
        boolean isOnTopOfAnObstacle = false;
        for (Obstacle obstacle : this.getObstacleList()) {
            if (CheckCollisionCircleRec(Main.PLAYER.getPosition(), 0, obstacle.getRectangle())) {
                Main.PLAYER.move(this.getDirection(), this.getSpeed());
                isOnTopOfAnObstacle = true;
            }
        }
        if (Main.PLAYER.isInsideLane(this) && !isOnTopOfAnObstacle) {
            Main.PLAYER.reset();
        }
    }

    public LaneDirection getDirection() {
        return direction;
    }

    public float getSpeed() {
        return speed;
    }

    public int getTopY() {
        return topY;
    }

    public int getBottomY() {
        return bottomY;
    }

    public float getPlayerCenterPosY() {
        return getBottomY() - (Main.LANE_HEIGHT * 0.5f) + 1;
    }
}
