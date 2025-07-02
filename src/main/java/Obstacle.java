import com.raylib.Raylib;

public class Obstacle {

    private Raylib.Rectangle rectangle;
    private float speed;
    private int direction;

    public Obstacle(int height, int width, int x, int y, float speed, int direction) {
        this.rectangle = new Raylib.Rectangle();
        this.rectangle.x(x);
        this.rectangle.y(y);
        this.rectangle.width(width);
        this.rectangle.height(height);
        this.speed = speed;
        this.direction = direction;
    }

    public boolean isInsideWindow() {
        if (direction > 0) {
            return this.rectangle.x() < Main.WINDOW_WIDTH;
        } else {
            return this.rectangle.x() > 0;
        }
    }

    public void moveToNextPosition(float deltaTime) {
        if (direction > 0) {
            this.rectangle.x(this.rectangle.x() + (int) (speed * deltaTime)); // Move obstacle to next right position
        } else {
            this.rectangle.x(this.rectangle.x() - (int) (speed * deltaTime)); // Move obstacle to next left position
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
}
