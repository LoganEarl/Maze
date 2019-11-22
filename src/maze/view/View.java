package maze.view;

import maze.model.Player;
import maze.model.World;

public interface View {
    void switchToPanel(Panel panel);
    void initialize(Player player, World world);
}
