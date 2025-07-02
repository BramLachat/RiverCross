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
}
