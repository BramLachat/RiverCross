import com.raylib.Raylib;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.raylib.Colors.GREEN;
import static com.raylib.Colors.RED;
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

    public Lane(float speed, LaneDirection direction, int nextObstacleCreationDelay, LaneType type, int height, int topY, int bottomY) {
        this.obstacleList = new ArrayList<>();
        this.lastCreatedObstacleTimestamp = Instant.now();
        this.nextObstacleCreationDelay = 0;
        this.speed = speed;
        this.direction = direction;
        this.nextObstacleCreationDelay = nextObstacleCreationDelay;
        this.type = type;
        this.height = height;
        this.topY = topY;
        this.bottomY = bottomY;
    }

    public void setNextObstacleCreationDelay(int nextObstacleCreationDelay) {
        this.nextObstacleCreationDelay = nextObstacleCreationDelay;
    }

    public boolean shouldSpawnNewObstacle() {
        return Duration.between(lastCreatedObstacleTimestamp, Instant.now()).toMillis() > nextObstacleCreationDelay;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacleList.add(obstacle);
        lastCreatedObstacleTimestamp = Instant.now();
    }

    public void addObstacle(int obstacleWidth) {
        if (direction == LaneDirection.LEFT_TO_RIGHT) {
            obstacleList.add(new Obstacle(height, obstacleWidth, 0, topY));
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

    public void draw(float deltaTime) {
        List<Obstacle> obstaclesToRemove = new ArrayList<>();
        Raylib.Color obstacleColor = this.type == LaneType.MORTAL ? RED : GREEN;
        for (Obstacle obstacle : this.obstacleList) {
            if (obstacle.isInsideWindow(this.direction)) {
                DrawRectangle((int) obstacle.getX(), (int) obstacle.getY(), (int) obstacle.getWidth(), (int) obstacle.getHeight(), obstacleColor);
                obstacle.moveToNextPosition(deltaTime, this.direction, this.speed);
            } else {
                obstaclesToRemove.add(obstacle);
            }
        }
        this.removeObstacles(obstaclesToRemove);
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
}
