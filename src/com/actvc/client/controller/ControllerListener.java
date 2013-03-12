package com.actvc.client.controller;

public interface ControllerListener<T extends Event> {
	void event(T event);
}
