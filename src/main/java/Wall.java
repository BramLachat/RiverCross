import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

public class Wall {
    private static final int FRAME_WIDTH = 48;      // Width of a single animation frame
    private static final int FRAME_HEIGHT = 48;      // Height of a single animation frame
    private static final float SCALE_FACTOR = 1.0f;    // How much to scale up the pixel art
    private static final float ROTATION = 0.0f;         // No rotation
    private static final Image IMAGE = LoadImage("D:\\PersonalWorkspace\\RiverCross\\resources\\terrain.png");
    public static final Texture TEXTURE = LoadTextureFromImage(IMAGE);

    private final Vector2 ROTATION_ORIGIN = new Vector2();
    private final Rectangle FRAME_REC = new Rectangle();
    private final Rectangle DESTINATION_REC = new Rectangle();

    public Wall() {
        // Source rectangle for the current frame
        FRAME_REC.x(272.0f);
        FRAME_REC.y(64.0f);
        FRAME_REC.width(FRAME_WIDTH);
        FRAME_REC.height(FRAME_HEIGHT);

        // Destination rectangle for drawing the scaled animation
        DESTINATION_REC.width(FRAME_WIDTH * SCALE_FACTOR);
        DESTINATION_REC.height(FRAME_HEIGHT * SCALE_FACTOR);

        // No rotation origin
        ROTATION_ORIGIN.x(0.0f);
        ROTATION_ORIGIN.y(0.0f);
    }

    public void renderStaticWall(float posX, float posY, float width, float height) {
        DESTINATION_REC.x(posX);
        DESTINATION_REC.y(posY);
        DESTINATION_REC.width(width);
        DESTINATION_REC.height(height);
        // Draw the current frame of the animation
        // Using DrawTexturePro for scaling
        DrawTexturePro(TEXTURE, FRAME_REC, DESTINATION_REC, ROTATION_ORIGIN, ROTATION, WHITE);
    }
}
