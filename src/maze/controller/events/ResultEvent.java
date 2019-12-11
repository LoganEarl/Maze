package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.View;
import utils.ResultProvider;
import utils.ResultReceiver;

public class ResultEvent implements GameEvent {
	private Class<? extends ResultProvider> resultProvider;
	private ResultReceiver resultReceiver;
	private Object object;
	
	public ResultEvent(Class<? extends ResultProvider> resultProvider, ResultReceiver resultReceiver, Object object) {
		this.resultProvider = resultProvider;
		this.resultReceiver = resultReceiver;
		this.object = object;
	}

	@Override
	public void resolveTo(Controller controller, View view, World world) {
		view.promptForResult(resultProvider, resultReceiver, object);
	}

}
