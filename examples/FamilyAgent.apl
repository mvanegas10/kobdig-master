// Initial mental state of a family agent
agent(Family)
{
    knowledge { }

    // Families initial belief base:
    beliefs
    {
        // If a property's house value is high, the property is in a good condition:
        hhv and pgc: 0.9,
    }

    desires
	{
        // If the property is in good condition, the family wants to buy it
        if B(pgc) then bp,
	}

    obligations { }
}
