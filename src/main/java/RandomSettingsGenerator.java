import java.util.Random;

public class RandomSettingsGenerator {

    private static final Random RANDOM_GENERATOR = new Random();

    public static float getObstacleSpeed() {
        float randomFloat = RANDOM_GENERATOR.nextFloat(2.0f, 10.0f);
        return Main.WINDOW_WIDTH / randomFloat;
    }

    public static LaneDirection getDirection() {
        boolean randomBoolean = RANDOM_GENERATOR.nextBoolean();
        if (randomBoolean) {
            return LaneDirection.LEFT_TO_RIGHT;
        }
        return LaneDirection.RIGHT_TO_LEFT;
    }

    public static int getObstacleCreationDelay(LaneType laneType) {
        if (laneType == LaneType.MUD) {
            return RANDOM_GENERATOR.nextInt(2000, 4000);
        }
        return RANDOM_GENERATOR.nextInt(1000, 3000);
    }

    public static LaneType getLaneType() {
        int randomInt = RANDOM_GENERATOR.nextInt(0, 2);
        if (randomInt == 0) {
            return LaneType.MORTAL;
        }
        if (randomInt == 1) {
            return LaneType.SURVIVAL;
        }
        if (randomInt == 2) {
            return LaneType.MUD;
        }
        throw new RuntimeException("UNKNOWN_RANDOM_LANE_GENERATED");
    }
}
