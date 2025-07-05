import com.raylib.Raylib;

public class Obstacle {

    private Raylib.Rectangle rectangle;
    private ChainSaw chainSaw;
    private Wall wall;

    public Obstacle(int height, int width, int x, int y) {
        this.rectangle = new Raylib.Rectangle();
        this.rectangle.x(x);
        this.rectangle.y(y);
        this.rectangle.width(width);
        this.rectangle.height(height);
        this.chainSaw = new ChainSaw();
        this.wall = new Wall();
    }

    public boolean isInsideWindow(LaneDirection direction) {
        if (direction == LaneDirection.LEFT_TO_RIGHT) {
            return this.rectangle.x() < Main.WINDOW_WIDTH;
        } else {
            return this.rectangle.x() > 0 - this.rectangle.width();
        }
    }

    public void moveToNextPosition(float deltaTime, LaneDirection direction, float laneSpeed) {
        if (direction == LaneDirection.LEFT_TO_RIGHT) {
            this.rectangle.x(this.rectangle.x() + (int) (laneSpeed * deltaTime)); // Move obstacle to next right position
        } else {
            this.rectangle.x(this.rectangle.x() - (int) (laneSpeed * deltaTime)); // Move obstacle to next left position
        }
    }

    public float getHeight() {
        return this.rectangle.height();
    }

    public float getWidth() {
        return this.rectangle.width();
    }

    public float getX() {
        return this.rectangle.x();
    }
    public float getY() {
        return this.rectangle.y();
    }

    public Raylib.Rectangle getRectangle() {
        return rectangle;
    }

    public void renderChainSawAnimation() {
        this.chainSaw.renderChainSawAnimation(this.rectangle.x(), this.rectangle.y(), this.rectangle.width(), this.rectangle.height());
    }

    public void renderStaticWall() {
        this.wall.renderStaticWall(this.rectangle.x(), this.rectangle.y(), this.rectangle.width(), this.rectangle.height());
    }
}
