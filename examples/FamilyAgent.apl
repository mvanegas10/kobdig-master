// Initial mental state of a family agent
agent(Family)

// c: Family wants to change home
// b: Family wants to buy a home
// r: Family wants to rent a home
// l: Family is landlord
// ls: Family is landlord and is selling
// lst: Family is landlord and is seeking tenant
// lr: Family is landlord and is renting


{
    knowledge { }

    // Families initial belief base:
    beliefs
    {

        // If the family is landlord and the neighborhood becomes unaffordable, the family wants to sell
        l: p

        // The family wants to change home with a "Possibility degree" p (BELIEF OR DESIRE?)


        // The family wants to change home and buy with a "Possibility degree" p (BELIEF OR DESIRE?)

        // The family wants to change home and rent with a "Possibility degree" p (BELIEF OR DESIRE?)


    }

    desires
	{

        // If the family is landlord and
        (l and ls): p

        // If the family is landlord and they have no tenant is seeking tenant with a "Possibility degree" p
        (l and lst): p

        // The family is landlord and home is rented with a "Possibility degree" p
        (l and lr): p

	}

    obligations { }
}
