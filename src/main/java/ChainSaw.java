import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

public class ChainSaw {
    private static final int ANIM_FRAMES = 8;       // Total number of animation frames
    private static final int FRAME_WIDTH = 38;      // Width of a single animation frame
    private static final int FRAME_HEIGHT = 38;      // Height of a single animation frame
    private static final float ANIM_SPEED = 0.05f;    // Seconds per frame (e.g., 0.1s = 10 frames per second)
    private static final float SCALE_FACTOR = 1.0f;    // How much to scale up the pixel art
    private static final float ROTATION = 0.0f;         // No rotation
    private static final Image IMAGE = LoadImage("D:\\PersonalWorkspace\\RiverCross\\resources\\chain_saw_animation.png");
    public static final Texture TEXTURE = LoadTextureFromImage(IMAGE);

    // Animation variables
    private int currentFrame = 0;           // Current frame being displayed
    private float frameTimer = 0.0f;        // Timer to control frame switching

    private final Vector2 ROTATION_ORIGIN = new Vector2();
    private final Rectangle FRAME_REC = new Rectangle();
    private final Rectangle DESTINATION_REC = new Rectangle();

    public ChainSaw() {
        // Source rectangle for the current frame
        FRAME_REC.x(0.0f);
        FRAME_REC.y(0.0f);
        FRAME_REC.width(FRAME_WIDTH);
        FRAME_REC.height(FRAME_HEIGHT);

        // Destination rectangle for drawing the scaled animation
        DESTINATION_REC.width(FRAME_WIDTH * SCALE_FACTOR);
        DESTINATION_REC.height(FRAME_HEIGHT * SCALE_FACTOR);

        // No rotation origin
        ROTATION_ORIGIN.x(0.0f);
        ROTATION_ORIGIN.y(0.0f);
    }

    public void renderChainSawAnimation(float posX, float posY, float width, float height) {
        frameTimer += GetFrameTime(); // GetFrameTime() gives the time elapsed since last frame

        if (frameTimer >= ANIM_SPEED)
        {
            frameTimer = 0.0f; // Reset timer
            currentFrame++;    // Move to the next frame

            // Loop animation back to the first frame if it goes beyond the last
            if (currentFrame >= ANIM_FRAMES)
            {
                currentFrame = 0;
            }

            // Update the source rectangle to point to the new frame
            FRAME_REC.x(currentFrame * FRAME_WIDTH);
        }

        DESTINATION_REC.x(posX);
        DESTINATION_REC.y(posY);
        DESTINATION_REC.width(width);
        DESTINATION_REC.height(height);
        // Draw the current frame of the animation
        // Using DrawTexturePro for scaling
        DrawTexturePro(TEXTURE, FRAME_REC, DESTINATION_REC, ROTATION_ORIGIN, ROTATION, WHITE);
    }
}
