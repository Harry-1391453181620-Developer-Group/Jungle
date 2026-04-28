package constants;

public class GameConstants {
    // The size of the window
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;
    // The background
    public static final String BACKGROUND_IMAGE_PATH = "D:/Programing_materials/Java/java_Projects/Jungle/jungle/resources/jungle_background.png";
    public static final int BACKGROUND_REPEAT_INTERVAL = 1000;
    // The music
    public static final String MUSIC_PATH = "D:/Programing_materials/Java/java_Projects/Jungle/jungle/resources/jungle_pcm.wav";
    // The heart
    public static final int TOTAL_HEARTS = 10;
    public static final String HEART_IMAGE_PATH = "D:/Programing_materials/Java/java_Projects/Jungle/jungle/resources/heart.png";
    // The explosion
    public static final String EXPLOSION_IMAGE_PATH = "D:/Programing_materials/Java/java_Projects/Jungle/jungle/resources/explosion.png";
    // The characters
    public static final String PLAYER_IMAGE_PATH = "D:/Programing_materials/Java/java_Projects/Jungle/jungle/resources/player.png";
    public static final String BANANA_IMAGE_PATH = "D:/Programing_materials/Java/java_Projects/Jungle/jungle/resources/banana.png";
    public static final String MONKEY_IMAGE_PATH = "D:/Programing_materials/Java/java_Projects/Jungle/jungle/resources/monkey.png";
    // Controlling the game
    public static final int MOVE_SPEED = 5;
    public static final int JUMP_SPEED = 15;
    public static final int HIGH_JUMP_SPEED = 25;
    public static final int HIGH_JUMP_INTERVAL = 2000; //2 seconds
    // The moving area
    public static final int PLAYER_GROUND_Y = HEIGHT - 110;
    public static final int MONKEY_MIN_Y = 200;
    public static final int BANANA_GROUND_Y = HEIGHT - 110;
    // The game states
    public static final int STATE_MENU = 0;
    public static final int STATE_GAME = 1;
    public static final int STATE_GAME_OVER = 2;
}
