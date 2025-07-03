import com.raylib.Raylib;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.IsKeyPressed;
import static com.raylib.Raylib.KEY_LEFT;
import static com.raylib.Raylib.KEY_RIGHT;

public class Player {
    private Raylib.Vector2 position;
    private float startPosX;
    private float startPosY;
    private boolean isInsideMud;

    public Player(float startPosX, float startPosY) {
        this.position = new Raylib.Vector2();
        this.position.x(startPosX);
        this.position.y(startPosY);
        this.startPosX = startPosX;
        this.startPosY = startPosY;
    }

    public void listenForUserInput() {
        // https://www.glfw.org/docs/latest/group__keys.html
        if (IsKeyPressed(KEY_UP)) {
            if (isInsideMud) {
                this.setPosY(this.getPosY() - Main.LANE_HEIGHT * 0.2f);
            } else {
                this.setPosY(this.getPosY() - Main.LANE_HEIGHT);
            }
        }
        if (IsKeyPressed(KEY_DOWN)) {
            if (isInsideMud) {
                this.setPosY(this.getPosY() + Main.LANE_HEIGHT * 0.2f);
            } else {
                this.setPosY(this.getPosY() + Main.LANE_HEIGHT);
            }
        }
        if (IsKeyPressed(KEY_LEFT)) {
            this.setPosX(this.getPosX() - Main.OBSTACLE_WIDTH);
        }
        if (IsKeyPressed(KEY_RIGHT)) {
            this.setPosX(this.getPosX() + Main.OBSTACLE_WIDTH);
        }
    }

    public void reset() {
        this.position.x(this.startPosX);
        this.position.y(this.startPosY);
    }

    public void setInsideMud(boolean insideMud) {
        isInsideMud = insideMud;
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
        boolean isInsideLane = this.position.y() < lane.getBottomY() && this.position.y() > lane.getTopY();
        if (isInsideLane && lane.getType() != LaneType.MUD) {
            this.position.y(lane.getPlayerCenterPosY());
        }
        return isInsideLane;
    }
}
