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

	public void pop() {
		states.pop();
	}

	public void set(GameState state) {
		states.pop();
		states.push(state);
	}
}
