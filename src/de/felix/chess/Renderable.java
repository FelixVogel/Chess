package de.felix.chess;

import java.awt.Graphics2D;

/**
 * Represents a render-able Object
 *
 * @author Felix Vogel
 */
public interface Renderable {

	/**
	 * Render this {@link Renderable}
	 *
	 * @param g The {@link Graphics2D} passed down by the render implementer
	 */
	void render(Graphics2D g);

}
