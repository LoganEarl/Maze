package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.Door;
import maze.model.Item;
import maze.model.World;
import maze.model.question.Question;
import maze.view.PanelType;
import maze.view.ResultReceiver;
import maze.view.View;
import maze.view.panel.ItemSelectorPanel;
import maze.view.panel.QuestionPanel;

public class AccessDoorEvent implements GameEvent, ResultReceiver {
	private Controller controller;
	private View view;
	private Door door;
	
	AccessDoorEvent(Door door) {
		this.door = door;
	}

	@Override
	public void resolveTo(Controller controller, View view, World world) {
		this.controller = controller;
		this.view = view;
		
		if (!door.isLocked()) {
//			View.QuestionDetailView questionPanel = view.getQuestionDetailView();
//			questionPanel.setQuestion(door.getQuestion());
//			view.switchToPanel(PanelType.QUESTION);
			
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
		
		view.switchToPanel(PanelType.GRAPHICS);
	}
}
