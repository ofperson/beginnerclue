package tasks.hotcold;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum HotColdTemperature
{
	ICE_COLD("ice cold", 500, 5000),
	VERY_COLD("very cold", 200, 499),
	COLD("cold", 150, 199),
	WARM("warm", 100, 149),
	HOT("hot", 70, 99),
	VERY_HOT("very hot", 30, 69),
	BEGINNER_INCREDIBLY_HOT("incredibly hot", 4, 29),
	BEGINNER_VISIBLY_SHAKING("visibly shaking", 0, 3);

	HotColdTemperature(String text, int minDistance, int maxDistance) {
		this.text = text;
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
	}

	public static final Set<HotColdTemperature> BEGINNER_HOT_COLD_TEMPERATURES = Stream.of(
		ICE_COLD,
		VERY_COLD,
		COLD,
		WARM,
		HOT,
		VERY_HOT,
		BEGINNER_INCREDIBLY_HOT,
		BEGINNER_VISIBLY_SHAKING
	).collect(Collectors.toSet());

	private final String text;
	private final int minDistance;
	private final int maxDistance;

	private static final String DEVICE_USED_START_TEXT = "The device is ";

	public static HotColdTemperature getFromTemperatureSet(final Set<HotColdTemperature> temperatureSet, final String message)
	{
		if (!message.startsWith(DEVICE_USED_START_TEXT) || temperatureSet == null)
		{
			return null;
		}

		final List<HotColdTemperature> possibleTemperatures = new ArrayList<>();

		for (final HotColdTemperature temperature : temperatureSet)
		{
			if (message.contains(temperature.getText()))
			{
				possibleTemperatures.add(temperature);
			}
		}

		return possibleTemperatures.stream()
			// For messages such as "The device is very cold", this will choose the Enum with text of greatest length so
			// that VERY_COLD would be selected over COLD, though both Enums have matching text for this message.
			.max(Comparator.comparingInt(x -> (x.getText()).length()))
			.orElse(null);
	}

	public String getText() {
		return text;
	}

	public int getMinDistance() {
		return minDistance;
	}

	public int getMaxDistance() {
		return maxDistance;
	}
}