package maze.controller.events;

import maze.Direction;
import maze.controller.Controller;
import maze.controller.MazeController;
import maze.controller.GameEvent;
import maze.model.Door;
import maze.model.Player;
import maze.model.Room;
import maze.model.World;
import maze.model.question.Question;
import maze.view.Panel;
import maze.view.View;

public class QuestionAnsweredEvent implements GameEvent {
	private Question question;
	private String userAnswer;
	
	public QuestionAnsweredEvent(Question question, String userAnswer) {
		this.question = question;
		this.userAnswer = userAnswer;
	}
	
	@Override
	public void resolveTo(Controller controller, View view, World world) {
		Player player = world.getPlayer();
		Room room = player.getCurrentRoom();
		Direction direction = player.getFacing();
		Door door = room.getDoor(direction);
		
		if (question.isCorrect(userAnswer)) {
			door.open();
		} else {
			door.lock();
		}

		view.switchToPanel(Panel.GRAPHICS);
	}
}
