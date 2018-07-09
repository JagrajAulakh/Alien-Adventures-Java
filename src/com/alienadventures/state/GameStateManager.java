package com.alienadventures.state;

import java.util.Stack;

public class GameStateManager {
	private Stack<GameState> states;
	public GameStateManager() {
		states = new Stack<GameState>();
	}

	public GameState currentState() {
		return states.peek();
	}

	public void push(GameState state) {
		states.push(state);
	}
}
