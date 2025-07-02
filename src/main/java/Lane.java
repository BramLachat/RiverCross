import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.raylib.Colors.RED;
import static com.raylib.Raylib.DrawRectangle;

public class Lane {
    private List<Obstacle> obstacleList;
    private Instant lastCreatedObstacleTimestamp; // milliseconds
    private int nextObstacleCreationDelay; // milliseconds
    private float speed;
    private LaneDirection direction;

    public Lane(float speed, LaneDirection direction) {
        this.obstacleList = new ArrayList<>();
        this.lastCreatedObstacleTimestamp = Instant.now();
        this.nextObstacleCreationDelay = 0;
        this.speed = speed;
        this.direction = direction;
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

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public void removeObstacles(List<Obstacle> obstaclesToRemove) {
        this.obstacleList.removeAll(obstaclesToRemove);
    }

    public void draw(float deltaTime) {
        List<Obstacle> obstaclesToRemove = new ArrayList<>();
        for (Obstacle obstacle : this.obstacleList) {
            if (obstacle.isInsideWindow(this.direction)) {
                DrawRectangle((int) obstacle.getX(), (int) obstacle.getY(), (int) obstacle.getWidth(), (int) obstacle.getHeight(), RED); // DrawRectangle(rectangleXPos_1, 800, 50,50, RED);
                obstacle.moveToNextPosition(deltaTime, this.direction, this.speed);
            } else {
                obstaclesToRemove.add(obstacle);
            }
        }
        this.removeObstacles(obstaclesToRemove);
    }
}
