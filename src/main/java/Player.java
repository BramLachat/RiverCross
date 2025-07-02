import com.raylib.Raylib;

public class Player {
    private Raylib.Vector2 position;
    private float startPosX;
    private float startPosY;

    public Player(float startPosX, float startPosY) {
        this.position = new Raylib.Vector2();
        this.position.x(startPosX);
        this.position.y(startPosY);
        this.startPosX = startPosX;
        this.startPosY = startPosY;
    }

    public void reset() {
        this.position.x(this.startPosX);
        this.position.y(this.startPosY);
    }

    public Raylib.Vector2 getPosition() {
        return position;
    }

    public float getPosY() {
        return this.position.y();
    }

    public float getPosX() {
        return this.position.x();
    }

    public void setPosY(float y) {
        this.position.y(y);
    }

    public void setPosX(float x) {
        this.position.x(x);
    }

    // Move the player in the same direction and with the same speed as the obstacles in the lane.
    public void move(LaneDirection direction, float laneSpeed, float deltaTime) {
        if (direction == LaneDirection.LEFT_TO_RIGHT) {
            this.position.x(this.position.x() + (int) (laneSpeed * deltaTime));
        } else {
            this.position.x(this.position.x() - (int) (laneSpeed * deltaTime));
        }
    }

    public boolean isInsideLane(Lane lane) {
        return this.position.y() < lane.getBottomY() && this.position.y() > lane.getTopY();
    }
}
