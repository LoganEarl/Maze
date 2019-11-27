package maze.view;

public interface ResultPrompter {
	public void promptForResult(Class<? extends ResultProvider> resultProvider, ResultReceiver resultProcessor, Object object);
}