package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.MainFrame;
import maze.view.ResultProvider;
import maze.view.ResultReceiver;

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
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		mainFrame.promptForResult(resultProvider, resultReceiver, object);
	}

}
