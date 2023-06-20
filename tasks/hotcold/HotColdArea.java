package tasks.hotcold;

public enum HotColdArea
{
	ASGARNIA("Asgarnia"),
	DESERT("Desert"),
	FELDIP_HILLS("Feldip Hills"),
	FREMENNIK_PROVINCE("Fremennik Province"),
	KANDARIN("Kandarin"),
	KARAMJA("Karamja"),
	MISTHALIN("Misthalin"),
	MORYTANIA("Morytania"),
	WESTERN_PROVINCE("Western Province"),
	WILDERNESS("Wilderness"),
	ZEAH("Zeah");

	private final String name;

	HotColdArea(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}