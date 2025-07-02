import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Lane {
    private List<Obstacle> obstacleList;
    private Instant lastCreatedObstacleTimestamp; // milliseconds
    private int nextObstacleCreationDelay; // milliseconds

    public Lane() {
        this.obstacleList = new ArrayList<>();
        this.lastCreatedObstacleTimestamp = Instant.now();
        this.nextObstacleCreationDelay = 0;
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
}
