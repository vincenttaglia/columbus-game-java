
public class NoSailsDecorator implements SailStrategy{
	private SailStrategy decorated;

	/**************************************************
			* Constructor of NoSailsDecorator Class *
	***************************************************/
    public NoSailsDecorator(SailStrategy decorated)
    {
         this.decorated = decorated;
    }

	/**************************************************
						* Override to Do Nothing *
	***************************************************/
	@Override
	public void sail(Ship ship, PirateShip pirateShip) {
		//do nothing

	}
}
