import com.raylib.Raylib;

import static com.raylib.Raylib.*;

public class Player {
    private Raylib.Vector2 position;
    private float startPosX;
    private float startPosY;
    private boolean isInsideMud;

    private VirtualGuy virtualGuy;

    public Player(float startPosX, float startPosY) {
        this.position = new Raylib.Vector2();
        this.position.x(startPosX);
        this.position.y(startPosY);
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.virtualGuy = new VirtualGuy();
    }

    public void render() {
        this.virtualGuy.renderVirtualGuyAnimation(position.x() - Main.PLAYER_RADIUS, position.y() - Main.PLAYER_RADIUS, Main.PLAYER_RADIUS * 2, Main.PLAYER_RADIUS * 2);
        listenForUserInput();
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
        if (y < Main.WINDOW_HEIGHT && y > 0) {
            this.position.y(y);
        }
    }

    public void setPosX(float x) {
        if (x < Main.WINDOW_WIDTH && x > 0) {
            this.position.x(x);
        } else if (x >= Main.WINDOW_WIDTH) {
            this.position.x(Main.WINDOW_WIDTH - Main.PLAYER_RADIUS);
        } else if (x <= 0) {
            this.position.x(Main.PLAYER_RADIUS);
        }
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
