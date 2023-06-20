package tasks.hotcold;

public enum HotColdTemperatureChange
{
	WARMER("and warmer than"),
	SAME("and the same temperature as"),
	COLDER("but colder than");

	private final String text;

	HotColdTemperatureChange(String text) {
		this.text = text;
	}

	public static HotColdTemperatureChange of(final String message)
	{
		if (!message.endsWith(" last time."))
		{
			return null;
		}

		for (final HotColdTemperatureChange change : values())
		{
			if (message.contains(change.text))
			{
				return change;
			}
		}

		return null;
	}
}