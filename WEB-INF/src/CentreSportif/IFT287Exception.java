package CentreSportif;

/**
 * L'exception IFT287Exception est levee lorsqu'une transaction est inadequate.
 */
public final class IFT287Exception extends Exception
{
    private static final long serialVersionUID = 1L;
    String message;

    public IFT287Exception(String message)
    {
        super(message);
        this.message = message;
    }

	@Override
	public String toString() {
		return this.message;
	}
}