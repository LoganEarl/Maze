package maze.view;

public interface ResultPrompter {
	void promptForResult(Class<? extends ResultProvider> resultProvider, ResultReceiver resultProcessor, Object object);
}