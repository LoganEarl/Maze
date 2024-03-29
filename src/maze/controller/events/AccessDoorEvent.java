package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.Door;
import maze.model.Item;
import maze.model.World;
import maze.model.question.Question;
import maze.view.PanelType;
import maze.view.View;
import maze.view.panel.ItemSelectorPanel;
import maze.view.panel.QuestionPanel;
import utils.ResultReceiver;

public class AccessDoorEvent implements GameEvent, ResultReceiver {
	private Controller controller;
	private Door door;
	
	AccessDoorEvent(Door door) {
		this.door = door;
	}

	@Override
	public void resolveTo(Controller controller, View view, World world) {
		this.controller = controller;
		
		if (!door.isLocked()) {
			GameEvent event = new ResultEvent(QuestionPanel.class, this, door.getQuestion());
			controller.getEventListener().onGameEvent(event);
		} else {
			GameEvent event = new ResultEvent(ItemSelectorPanel.class, this, door.getQuestion());
			controller.getEventListener().onGameEvent(event);
		}
	}

	@Override
	public void processResult(Object object) {
		Question question = door.getQuestion();
		
		if (object instanceof String) {
			String answer = (String) object;
			if (question.isCorrect(answer)) {
				door.open();
			} else {
				door.lock();
			}
		} else if (object instanceof Item) {
			Item item = (Item) object;
			if (item.answersQuestion(question)) {
				door.open();
			}
			GameEvent event = new ItemUsedEvent(item);
			controller.getEventListener().onGameEvent(event);
		}
		
		if (controller.getWorld().currentRouteExists()) {
			GameEvent event = new SwitchPanelEvent(PanelType.GRAPHICS);
			controller.getEventListener().onGameEvent(event);
		} else {
			GameEvent event = new SwitchPanelEvent(PanelType.GAME_OVER);
			controller.getEventListener().onGameEvent(event);
		}
	}
}
