public class train {
    public static void runMovingTrain() {
        // The ASCII art train broken down line by line
        String[] trainArt = {
                "      ====        ________                ___________ ",
                "  _D _|  L_Y_   |            |          |           |",
                " [__]-----|_|   |____________|          |___________|",
                "  B-O--B-O   === (o)(o)    (o)(o)   ===  (o)(o) (o)(o)"
        };

        // Fallback default screen width
        int terminalWidth = 80;

        // Safely check system environment variables
        try {
            String columns = System.getenv("COLUMNS");
            if (columns != null) {
                terminalWidth = Integer.parseInt(columns);
            }
        } catch (Exception e) {
            // Fallback gracefully if system properties are restricted
        }

        int trainWidth = trainArt[0].length();
        int totalFrames = terminalWidth + trainWidth;

        for (int frame = 0; frame < totalFrames; frame++) {
            // Clear terminal screen and reset cursor using ANSI escape sequences
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Calculate starting position (moves from right to left)
            int currentPos = terminalWidth - frame;

            // Render each row of the locomotive
            for (int i = 0; i < trainArt.length; i++) {
                String line = trainArt[i];

                if (currentPos >= 0) {
                    // Train is arriving or actively traversing the screen
                    // Manual padding loop replacing Java 11's String.repeat()
                    for (int j = 0; j < currentPos; j++) {
                        System.out.print(" ");
                    }
                    System.out.println(line);
                } else {
                    // Train is passing off the left edge of the screen (horizontal clipping)
                    int charactersToClip = Math.abs(currentPos);
                    if (charactersToClip < line.length()) {
                        System.out.println(line.substring(charactersToClip));
                    } else {
                        System.out.println(); // Completely cleared off-screen
                    }
                }
            }

            // Frame-rate throttle (roughly 30 frames per second)
            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Final screen clean up when the animation cycle finishes
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
