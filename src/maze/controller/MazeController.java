package maze.controller;

import maze.model.World;
import maze.model.question.QuestionDataSource;
import maze.model.question.sqlite.SQLiteQuestionDataSource;
import maze.view.View;

public class MazeController implements GameEventListener, Controller {
	private View view;
	private World world;
	private QuestionDataSource dataSource;

	public MazeController(){
		dataSource = new SQLiteQuestionDataSource("questions.db");
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public QuestionDataSource getDataSource() {
		return dataSource;
	}

	@Override
	public GameEventListener getEventListener() {
		return this;
	}

	@Override
	public void onGameEvent(GameEvent gameEvent) {
		gameEvent.resolveTo(this, view, world);
		View.MapDetailView mapView = view.getMapDetailView();
		if(mapView != null)
			mapView.setWorld(world);

	}
}