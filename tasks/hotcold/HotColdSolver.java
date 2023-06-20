package tasks.hotcold;

import org.dreambot.api.methods.map.Tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Set;

public class HotColdSolver
{
	private final Set<HotColdLocation> possibleLocations;
	@Nullable
	private Tile lastTile;

	public HotColdSolver(Set<HotColdLocation> possibleLocations)
	{
		this.possibleLocations = possibleLocations;
	}
	
	public Set<HotColdLocation> signal(@Nonnull final Tile worldTile, @Nonnull final HotColdTemperature temperature, @Nullable final HotColdTemperatureChange temperatureChange)
	{
		// when the strange device reads a temperature, that means that the center of the final dig location
		// is a range of squares away from the player's current location (Chebyshev AKA Chess-board distance)
		int maxSquaresAway = temperature.getMaxDistance();
		int minSquaresAway = temperature.getMinDistance();

		// maxDistanceArea encompasses all of the points that are within the max possible distance from the player
		final Rectangle maxDistanceArea = new Rectangle(
			worldTile.getX() - maxSquaresAway,
			worldTile.getY() - maxSquaresAway,
			2 * maxSquaresAway + 1,
			2 * maxSquaresAway + 1);
		// minDistanceArea encompasses all of the points that are within the min possible distance from the player
		final Rectangle minDistanceArea = new Rectangle(
			worldTile.getX() - minSquaresAway,
			worldTile.getY() - minSquaresAway,
			2 * minSquaresAway + 1,
			2 * minSquaresAway + 1);

		// eliminate from consideration dig spots that lie entirely within the min range or entirely outside of the max range
		possibleLocations.removeIf(entry -> minDistanceArea.contains(entry.getRect()) || !maxDistanceArea.intersects(entry.getRect()));

		// if a previous world point has been recorded, we can consider the warmer/colder result from the strange device
		if (lastTile != null && temperatureChange != null)
		{
			switch (temperatureChange)
			{
				case COLDER:
					// eliminate spots that are warmer or same temperature
					possibleLocations.removeIf(location ->
					{
						final Tile locationPoint = location.getTile();
						return locationPoint.distance(worldTile) <= locationPoint.distance(lastTile);
					});
					break;
				case WARMER:
					// eliminate spots that are colder or same temperature
					possibleLocations.removeIf(location ->
					{
						final Tile locationPoint = location.getTile();
						return locationPoint.distance(worldTile) >= locationPoint.distance(lastTile);
					});
					break;
				case SAME:
					// eliminate spots which are colder or warmer (as they would not yield a SAME temperature change)
					possibleLocations.removeIf(location ->
					{
						final Tile locationPoint = location.getTile();
						return locationPoint.distance(worldTile) != locationPoint.distance(lastTile);
					});
			}
		}

		lastTile = worldTile;
		return getPossibleLocations();
	}

	public Set<HotColdLocation> getPossibleLocations() {
		return possibleLocations;
	}

	public Tile getlastTile() {
		return lastTile;
	}
}